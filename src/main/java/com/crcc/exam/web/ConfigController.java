package com.crcc.exam.web;

import com.crcc.exam.domain.dto.BasePageDTO;
import com.crcc.exam.domain.po.ConfigDetail;
import com.crcc.exam.domain.po.ConfigMain;
import com.crcc.exam.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
@Slf4j
@Api(description = "规则配置")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    /**
     * 获取规则主表列表
     */
    @PostMapping("/v1.0/main/list")
    @ApiOperation("获取规则主表列表")
    public Page<ConfigMain> listConfigMains(@RequestBody BasePageDTO<ConfigMain> pageDTO) {
        log.debug(pageDTO.toString());
        return configService.listConfigMains(pageDTO);
    }

    /**
     * 获取指定的规则主表信息
     */
    @GetMapping("/v1.0/main/list/{id}")
    @ApiOperation("获取指定的规则主表信息")
    public ConfigMain listConfigMainOne(@PathVariable String id) {
        return configService.listConfigMainOne(id);
    }

    /**
     * 获取规则明细列表
     * 主表ID存放于condition中
     */
    @PostMapping("/v1.0/detail/list")
    @ApiOperation("获取规则明细列表")
    public Page<ConfigDetail> listConfigDetails(@RequestBody BasePageDTO<ConfigDetail> basePageDTO) {
        return configService.listConfigDetails(basePageDTO);
    }

    /**
     * 获取指定的规则明细信息
     */
    @GetMapping("/v1.0/detail/list/{id}")
    @ApiOperation("获取指定的规则明细信息")
    public ConfigDetail listConfigDetailOne(@PathVariable String id) {
        return configService.listConfigDetailOne(id);
    }

    /**
     * 新增主规则数据
     */
    @PostMapping("/v1.0/main/save")
    @ApiOperation("新增主规则数据")
    public ConfigMain saveConfigMain(@RequestBody ConfigMain configMain) {
        return configService.saveConfigMain(configMain);
    }

    /**
     * 新增规则明细数据
     */
    @PostMapping("/v1.0/detail/save")
    @ApiOperation("新增规则明细数据")
    public ConfigDetail saveConfigDetail(@RequestBody ConfigDetail configDetail) {
        log.debug(configDetail.toString());
        return configService.saveConfigDetail(configDetail);
    }

    /**
     * 删除规则明细数据：单条删除
     */
    @DeleteMapping("/v1.0/detail/delete/{id}")
    @ApiOperation("删除规则明细数据")
    public Integer deleteConfigDetail(@PathVariable String id) {
        configService.deleteConfigDetail(id);
        return 1;
    }

    /**
     * 删除规则主表数据：同步删除对应的明细数据
     */
    @DeleteMapping("/v1.0/main/delete/{id}")
    @ApiOperation("删除规则主数据")
    public Integer deleteConfigMain(@PathVariable String id) {
        configService.deleteConfigMain(id);
        return 1;
    }

    @PutMapping("/v1.0/main/update")
    @ApiOperation("更新规则主数据")
    public ConfigMain updateConfigMain(@RequestBody ConfigMain configMain) {
        return configService.updateConfigMain(configMain);
    }

    @PutMapping("/v1.0/detail/update")
    @ApiOperation("更新规则明细数据")
    public ConfigDetail updateConfigDetail(@RequestBody ConfigDetail configDetail) {
        return configService.updateConfigDetail(configDetail);
    }
}
