package com.crcc.exam.service;

import com.crcc.exam.domain.dto.BasePageDTO;
import com.crcc.exam.domain.po.ConfigDetail;
import com.crcc.exam.domain.po.ConfigMain;
import org.springframework.data.domain.Page;

public interface ConfigService {
    /**
     * 获取规则主表列表
     */
    Page<ConfigMain> listConfigMains(BasePageDTO<ConfigMain> page);

    /**
     * 根据规主表ID获取规则明细列表
     */
    Page<ConfigDetail> listConfigDetails(BasePageDTO<ConfigDetail> basePageDTO);

    /**
     * 新增规则主数据
     */
    ConfigMain saveConfigMain(ConfigMain configMain);

    /**
     * 新增规则明细数据
     */
    ConfigDetail saveConfigDetail(ConfigDetail configDetail);

    /**
     * 删除规则明细数据
     */
    void deleteConfigDetail(String id);

    /**
     * 删除规则主数据：同步删除明细数据
     */
    void deleteConfigMain(String id);

    /**
     * 更新规则主数据
     */
    ConfigMain updateConfigMain(ConfigMain configMain);

    /**
     * 更新规则明细数据
     */
    ConfigDetail updateConfigDetail(ConfigDetail configDetail);

    /**
     * 获取指定的规则主表信息
     */
    ConfigMain listConfigMainOne(String id);

    /**
     * 获取指定的规则明细信息
     */
    ConfigDetail listConfigDetailOne(String id);
}
