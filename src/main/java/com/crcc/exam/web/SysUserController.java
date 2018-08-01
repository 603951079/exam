package com.crcc.exam.web;

import com.crcc.exam.domain.dto.BasePageDTO;
import com.crcc.exam.domain.po.SysUser;
import com.crcc.exam.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
@RequestMapping("/sysuser")
@Slf4j
@Api(description = "系统用户管理")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 创建系统用户
     */
    @PostMapping("/v1.0/save")
    @ApiOperation("创建系统用户")
    public SysUser saveSysUser(@RequestBody SysUser sysUser) {
        log.debug(sysUser.toString());
        return sysUserService.saveSysUser(sysUser);
    }

    /**
     * 删除系统用户
     */
    @DeleteMapping("/v1.0/delete/{id}")
    @ApiOperation("删除系统用户")
    public void deleteSysUser(@ApiParam("主键") @PathVariable String id) {
        sysUserService.deleteSysUser(id);
    }

    /**
     * 禁用系统用户
     */
    @PutMapping("/v1.0/disabled/{id}")
    @ApiOperation("禁用系统用户")
    public void disabledSysUser(@ApiParam("主键") @PathVariable String id) {
        sysUserService.disabledSysUser(id);
    }

    /**
     * 启用系统用户
     */
    @PutMapping("/v1.0/enabled/{id}")
    @ApiOperation("启用系统用户")
    public void enabledSysUser(@ApiParam("主键") @PathVariable String id) {
        sysUserService.enabledSysUser(id);
    }

    /**
     * 更新系统用户
     */
    @PutMapping("/v1.0/update")
    @ApiOperation("更新系统用户")
    public SysUser updateSysUser(@RequestBody SysUser sysUser) {
        log.debug("updateSysUser="+sysUser.toString());
        return sysUserService.updateSysUser(sysUser);
    }

    /**
     * 查询系统用户列表
     */
    @PostMapping("/v1.0/list")
    @ApiOperation("查询系统用户列表")
    public Page<SysUser> listSysUser(@RequestBody BasePageDTO<SysUser> basePageDTO) {
        Page<SysUser> page = sysUserService.listSysUser(basePageDTO);
        return page;
    }

    /**
     * 根据ID查询系统用户信息
     */
    @GetMapping("/v1.0/list/{id}")
    @ApiOperation("根据ID查询用户信息")
    public SysUser listSysUserOne(@PathVariable String id) {
        log.debug("id=" + id);
        return sysUserService.listSysUserById(id);
    }


    @PostMapping("/v1.0/sort")
    @ApiIgnore
    public void add(@RequestBody Sort sort) {
        log.debug(sort.toString());
    }

    /**
     * 登录成功后，获取当前用户信息、授权信息
     */
    @GetMapping("/v1.0/info")
    @ApiOperation("获取当前登录用户信息、授权信息")
    public Map<String, Object> sysUserInfo() {
        return this.sysUserService.sysUserInfo();
    }
}
