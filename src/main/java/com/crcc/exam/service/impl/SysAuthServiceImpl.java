package com.crcc.exam.service.impl;

import com.crcc.exam.domain.po.SysAuthorize;
import com.crcc.exam.repository.SysAuthorizeRepository;
import com.crcc.exam.service.SysAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SysAuthServiceImpl implements SysAuthService {
    @Autowired
    private SysAuthorizeRepository sysAuthorizeRepository;

    @Transactional
    @Override
    public List<SysAuthorize> save(List<SysAuthorize> auths) {
        String userId = auths.get(0).getUserId();
        String roleId = auths.get(0).getRoleId();
        if (StringUtils.isEmpty(roleId)) {
            // 删除指定用户的所有角色
            sysAuthorizeRepository.deleteAuthByUserId(userId);
            return null;
        } else {
            //先删除，在插入新数据
            sysAuthorizeRepository.deleteAuthByUserId(userId);
            return sysAuthorizeRepository.saveAll(auths);
        }
    }
}
