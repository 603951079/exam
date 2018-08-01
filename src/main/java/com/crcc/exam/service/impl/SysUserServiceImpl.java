package com.crcc.exam.service.impl;

import com.crcc.exam.domain.dto.BasePageDTO;
import com.crcc.exam.domain.po.SysPermission;
import com.crcc.exam.domain.po.SysRole;
import com.crcc.exam.domain.po.SysUser;
import com.crcc.exam.repository.SysAuthorizeRepository;
import com.crcc.exam.repository.SysPermissionRepository;
import com.crcc.exam.repository.SysRoleRepository;
import com.crcc.exam.repository.SysUserRepository;
import com.crcc.exam.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysAuthorizeRepository sysAuthorizeRepository;

    @Autowired
    private SysPermissionRepository sysPermissionRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public SysUser saveSysUser(SysUser sysUser) {
        /**
         * 对用户输入的密码进行加密处理，存储到数据库中的是密文
         */
        sysUser.setPassword(bCryptPasswordEncoder.encode(sysUser.getPhone()));
        sysUser.setFlag("1");
        return sysUserRepository.save(sysUser);
    }

    @Transactional
    @Override
    public void deleteSysUser(String id) {
        this.sysUserRepository.deleteById(id);
        // 同步删除授权信息
        this.sysAuthorizeRepository.deleteAuthByUserId(id);
    }

    @Transactional
    @Override
    public void disabledSysUser(String id) {
        this.sysUserRepository.abledSysUser(false, id);
    }

    @Transactional
    @Override
    public void enabledSysUser(String id) {
        this.sysUserRepository.abledSysUser(true, id);
    }

    @Override
    public SysUser updateSysUser(SysUser sysUser) {
        return this.sysUserRepository.save(sysUser);
    }

    @Override
    public Page<SysUser> listSysUser(BasePageDTO<SysUser> basePageDTO) {
        int number = basePageDTO.getNumber();
        int size = basePageDTO.getSize();
        Sort sort = basePageDTO.getSort();
        SysUser sysUser = basePageDTO.getCondition();
        log.debug("sysUser_list=" + sysUser.toString());
        Pageable pageable = null;
        if (sort != null) {
            pageable = PageRequest.of(number, size, sort);
        } else {
            pageable = PageRequest.of(number, size);
        }

        log.debug(pageable.toString());

        Specification<SysUser> dynamicCondition = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicatesList = new ArrayList<>();
            //zhName ,like语句
            if (!StringUtils.isEmpty(sysUser.getZhName())) {
                predicatesList.add(
                        criteriaBuilder.and(
                                criteriaBuilder.like(root.get("zhName"), "%" + sysUser.getZhName() + "%")));
            }
            // phone模糊查询 ,like语句
            if (!StringUtils.isEmpty(sysUser.getPhone())) {
                predicatesList.add(
                        criteriaBuilder.and(
                                criteriaBuilder.like(
                                        root.get("phone"), "%" + sysUser.getPhone() + "%")));
            }

            predicatesList.add(
                    criteriaBuilder.equal(root.get("flag"), "1")
            );

            return criteriaBuilder.and(
                    predicatesList.toArray(new Predicate[predicatesList.size()]));
        };

        Page<SysUser> page = sysUserRepository.findAll(dynamicCondition, pageable);
        List<SysUser> list = page.getContent();
        for (int i = 0; i < list.size(); i++) {
            SysUser user = list.get(i);
            String userId = user.getId();
            List<SysRole> roles = this.sysRoleRepository.findByUserId(userId, PageRequest.of(0, 500)).getContent();
            user.setAuthorities(roles.stream().map((sysRole) -> {
                return new SimpleGrantedAuthority(sysRole.getRoleName());
            }).collect(Collectors.toList()));
            log.debug(user.toString());
        }
        return page;
    }

    @Override
    public SysUser listSysUserById(String id) {
        return this.sysUserRepository.findById(id).get();
    }

    @Override
    public SysUser loadUserByUsername(String userName) {
        SysUser sysUser = this.sysUserRepository.loadUserByUsername(userName);
        if (sysUser != null) {
            /**
             * 查询当前用户所拥有的角色
             */
            List<SysRole> roles = this.sysRoleRepository.
                    findByUserId(sysUser.getId(), PageRequest.of(0, 500)).getContent();
            sysUser.setAuthorities(roles.stream().map(role -> {
                return new SimpleGrantedAuthority(role.getRoleCode());
            }).collect(Collectors.toList()));
        }
        return sysUser;

    }

    @Override
    public Map<String, Object> sysUserInfo() {
        /**
         * 从SecurityContext中获取SysUser对象
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<SimpleGrantedAuthority> roles = (List<SimpleGrantedAuthority>)
                authentication.getAuthorities();

        List<String> role_code_list = roles.stream().map((role) -> {
            return role.getAuthority();
        }).collect(Collectors.toList());
        // 如果role_code_list为空，查询时汇报错，所以为空时，默认插入一个值
        if (role_code_list.size() == 0) {
            role_code_list.add("");
        }

        log.debug(role_code_list.toString());
        // 原始菜单树数据
        List<SysPermission> menus = this.sysPermissionRepository.listPermission(role_code_list);
        log.debug(menus.toString());

        List<String> menuIndexs = new ArrayList<>();

        // 处理之后的菜单树
        List<SysPermission> roots = new ArrayList<>();

        // 先查找所有的一级菜单
        for (SysPermission root : menus) {
            if (root.getMenuParent() == null) {
                roots.add(root);
            }
            menuIndexs.add("/" + root.getMenuIndex());
        }

        // 为一级菜单设置子菜单
        for (SysPermission root : roots) {
            root.setSubs(this.getChild(root.getId(), menus));
        }

        log.debug(roots.toString());

        SysUser sysUser = (SysUser) authentication.getPrincipal();
        log.debug(sysUser.toString());
        Map<String, Object> result = new HashMap<>();
        result.put("user", authentication.getPrincipal());
        result.put("menus", roots);
        result.put("menuindexs", menuIndexs);
        return result;

    }

    private List<SysPermission> getChild(String rootId, List<SysPermission> menus) {
        List<SysPermission> childList = new ArrayList<>();
        for (SysPermission sysPermission : menus) {
            if (rootId.equals(sysPermission.getMenuParent())) {
                childList.add(sysPermission);
            }
        }

        // 把子菜单的子菜单再循环一次
        for (SysPermission sysPermission : childList) {
            sysPermission.setSubs(this.getChild(sysPermission.getId(), menus));
        }

        // 退出递归条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
}
