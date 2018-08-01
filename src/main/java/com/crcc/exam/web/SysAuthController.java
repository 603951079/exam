package com.crcc.exam.web;

import com.crcc.exam.domain.po.SysAuthorize;
import com.crcc.exam.service.SysAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/sysauth")
@Api(description = "授权管理")
public class SysAuthController {

    @Autowired
    private SysAuthService sysAuthService;

    /**
     * 分配授权
     */
    @PostMapping("/v1.0/save")
    @ApiOperation("创建系统用户")
    public List<SysAuthorize> saveSysUser(@RequestBody List<SysAuthorize> auths) {
        return sysAuthService.save(auths);
    }
}
