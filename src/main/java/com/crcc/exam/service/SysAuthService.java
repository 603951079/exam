package com.crcc.exam.service;

import com.crcc.exam.domain.po.SysAuthorize;

import java.util.List;

public interface SysAuthService {

    /**
     * 分配授权
     */
    List<SysAuthorize> save(List<SysAuthorize> auths);
}
