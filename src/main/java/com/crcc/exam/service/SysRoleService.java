package com.crcc.exam.service;

import com.crcc.exam.domain.dto.BasePageDTO;
import com.crcc.exam.domain.po.SysRole;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SysRoleService {
    /**
     * 新增系统角色
     */
    SysRole saveSysRole(SysRole sysRole);

    /**
     * 根据主键删除系统角色
     */
    void deleteSysRole(String id);

    /**
     * 更新系统角色
     */
    SysRole updateSysRole(SysRole sysRole);

    /**
     * 查询系统角色列表
     */
    Page<SysRole> listSysRole(BasePageDTO<SysRole> basePageDTO);

    /**
     * 查询指定角色信息
     */
    SysRole listSysRoleOne(String id);

    /**
     * 查询指定用户拥有的角色
     */
    Page<SysRole> listSysRolesByUserId(String userId);

    /**
     * 根据角色名字查询角色列表
     */
    List<SysRole> listSysRole(String rolename);

    /**
     * 根据角色编码查询角色列表：分页
     */
    Page<SysRole> listSysRoleByCode(BasePageDTO<SysRole> basePageDTO);
}
