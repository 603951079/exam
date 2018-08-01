package com.crcc.exam.service.impl;

import com.crcc.exam.domain.dto.BasePageDTO;
import com.crcc.exam.domain.po.SysRole;
import com.crcc.exam.repository.SysRoleRepository;
import com.crcc.exam.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Override
    public SysRole saveSysRole(SysRole sysRole) {
        return sysRoleRepository.save(sysRole);
    }

    @Override
    public void deleteSysRole(String id) {
        sysRoleRepository.deleteById(id);
    }

    @Override
    public SysRole updateSysRole(SysRole sysRole) {
        log.debug(sysRole.toString());
        log.debug("sysRole.getId()=" + sysRole.getId());
        return sysRoleRepository.save(sysRole);
    }

    @Override
    public Page<SysRole> listSysRole(BasePageDTO<SysRole> basePageDTO) {
        int number = basePageDTO.getNumber();
        int size = basePageDTO.getSize();
        Sort sort = basePageDTO.getSort();
        SysRole sysRole = basePageDTO.getCondition();

        Pageable pageable = null;
        if (sort != null) {
            pageable = PageRequest.of(number, size, sort);
        } else {
            pageable = PageRequest.of(number, size);
        }

        Specification<SysRole> dynamicCondition = (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();

            if (!StringUtils.isEmpty(sysRole.getRoleName())) {
                list.add(
                        builder.and(builder.like(root.get("roleName"), "%" + sysRole.getRoleName() + "%"))
                );
            }

            if (!StringUtils.isEmpty(sysRole.getRoleCode())) {
                list.add(
                        builder.and(builder.like(root.get("roleCode"), "%" + sysRole.getRoleCode() + "%"))
                );
            }

            return builder.and(list.toArray(new Predicate[list.size()]));
        };
        return sysRoleRepository.findAll(dynamicCondition, pageable);
    }

    @Override
    public SysRole listSysRoleOne(String id) {
        return this.sysRoleRepository.findById(id).get();
    }

    @Override
    public Page<SysRole> listSysRolesByUserId(String userId) {
        Pageable pageable = PageRequest.of(0, 500);
        return this.sysRoleRepository.findByUserId(userId, pageable);
    }

    @Override
    public List<SysRole> listSysRole(String rolename) {
        return this.sysRoleRepository.findByRoleName(rolename);
    }

    @Override
    public Page<SysRole> listSysRoleByCode(BasePageDTO<SysRole> basePageDTO) {
        log.debug(basePageDTO.getCondition().getRoleCode());
        String rolecode = basePageDTO.getCondition().getRoleCode();
        Pageable pageable = PageRequest.of(basePageDTO.getNumber(), basePageDTO.getSize());
        return this.sysRoleRepository.findByRoleCode(rolecode, pageable);
    }


}
