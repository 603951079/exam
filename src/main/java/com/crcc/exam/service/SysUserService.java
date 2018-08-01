package com.crcc.exam.service;

import com.crcc.exam.domain.dto.BasePageDTO;
import com.crcc.exam.domain.po.SysPermission;
import com.crcc.exam.domain.po.SysUser;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface SysUserService {
    /**
     * 新增系统用户
     */
    SysUser saveSysUser(SysUser sysUser);

    /**
     * 删除系统用户
     */
    void deleteSysUser(String id);

    /**
     * 禁用系统用户
     */
    void disabledSysUser(String id);

    /**
     * 启用系统用户
     */
    void enabledSysUser(String id);

    /**
     * 更新系统用户
     */
    SysUser updateSysUser(SysUser sysUser);

    /**
     * 查询系统用户列表
     */
    Page<SysUser> listSysUser(BasePageDTO<SysUser> basePageDTO);

    /**
     * 根据用户ID查查用户
     */
    SysUser listSysUserById(String id);

    /**
     * 根据用户名查询当前用户
     */
    SysUser loadUserByUsername(String userName);

    /**
     * 获取当前登录用户信息
     */
    Map<String, Object> sysUserInfo();
}
