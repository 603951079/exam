package com.crcc.exam.web;

import com.crcc.exam.domain.dto.BasePageDTO;
import com.crcc.exam.domain.po.SysRole;
import com.crcc.exam.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/sysrole")
@Api(description = "角色管理")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 新增系统角色
     */
    @PostMapping("/v1.0/save")
    @ApiOperation("新增系统角色")
    public SysRole saveSysRole(@RequestBody SysRole sysRole) {
        return sysRoleService.saveSysRole(sysRole);
    }

    /**
     * 根据ID删除系统角色
     */
    @DeleteMapping("/v1.0/delete/{id}")
    @ApiOperation("根据ID删除系统角色")
    public void deleteSysRole(@ApiParam("主键") @PathVariable("id") String id) {
        sysRoleService.deleteSysRole(id);
    }

    /**
     * 更新系统角色
     */
    @PutMapping("/v1.0/update")
    @ApiOperation("更新系统角色")
    public SysRole updateSysRole(@RequestBody SysRole sysRole) {
        log.debug(sysRole.toString());
        log.debug(sysRole.getId().toString());
        sysRole.setId(sysRole.getId());
        return sysRoleService.updateSysRole(sysRole);
    }

    /**
     * 查询系统角色列表
     */
    @PostMapping("/v1.0/list")
    @ApiOperation("查询系统角色列表")
    public Page<SysRole> listSysRole(@RequestBody BasePageDTO<SysRole> page) {
        return sysRoleService.listSysRole(page);
    }

    @GetMapping("/v1.0/list/{id}")
    @ApiOperation("查询指定角色信息")
    public SysRole listSysRoleOne(@PathVariable("id") String id) {
        return sysRoleService.listSysRoleOne(id);
    }

    @GetMapping("/v1.0/list/userroles/{userId}")
    @ApiOperation("查询指定用户拥有的角色")
    public Page<SysRole> listSysRolesByUserId(@PathVariable("userId") String userId) {
        return sysRoleService.listSysRolesByUserId(userId);
    }

    @GetMapping("/v1.0/list/all/{rolename}")
    @ApiIgnore
    public List<SysRole> listSysRole(@PathVariable("rolename") String rolename) {
        return sysRoleService.listSysRole(rolename);
    }

    @PostMapping("/v1.0/list/all/bycode")
    @ApiIgnore
    public Page<SysRole> listSysRoleByCode(@RequestBody BasePageDTO<SysRole> basePageDTO) {
        log.debug(basePageDTO.toString());
        log.debug(basePageDTO.getCondition().toString());
        return sysRoleService.listSysRoleByCode(basePageDTO);
    }
}
