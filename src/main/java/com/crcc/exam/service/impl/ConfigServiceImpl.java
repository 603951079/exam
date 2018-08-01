package com.crcc.exam.service.impl;

import com.crcc.exam.domain.dto.BasePageDTO;
import com.crcc.exam.domain.po.ConfigDetail;
import com.crcc.exam.domain.po.ConfigMain;
import com.crcc.exam.repository.ConfigDetailRepository;
import com.crcc.exam.repository.ConfigMainRepository;
import com.crcc.exam.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMainRepository configMainRepository;

    @Autowired
    private ConfigDetailRepository configDetailRepository;

    @Override
    public Page<ConfigMain> listConfigMains(BasePageDTO<ConfigMain> page) {
        int number = page.getNumber();
        int size = page.getSize();
        ConfigMain configMain = page.getCondition();
        Sort sort = page.getSort();

        Pageable pageable = null;
        if (sort != null) {
            pageable = PageRequest.of(number, size, sort);
        } else {
            pageable = PageRequest.of(number, size);
        }

        log.debug(pageable.toString());
        Specification<ConfigMain> dynamicConfition = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            // 分类编码模糊查询 like
            if (!StringUtils.isEmpty(configMain.getClassCode())) {
                list.add(
                        criteriaBuilder.and(
                                criteriaBuilder.like(root.get("classCode"), "%" + configMain.getClassCode() + "%")));
            }
            // 分类名称模糊查询 like
            if (!StringUtils.isEmpty(configMain.getClassName())) {
                list.add(
                        criteriaBuilder.and(
                                criteriaBuilder.like(root.get("className"), "%" + configMain.getClassName() + "%")));
            }

            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };

        return configMainRepository.findAll(dynamicConfition, pageable);
    }

    @Override
    public Page<ConfigDetail> listConfigDetails(BasePageDTO<ConfigDetail> basePageDTO) {
        /**
         * Page 从0开始算第一页，1表示第二页
         */
        int number = basePageDTO.getNumber();
        int size = basePageDTO.getSize();
        ConfigDetail configDetail = basePageDTO.getCondition();
        Sort sort = basePageDTO.getSort();

        Pageable pageable = null;
        if (sort != null) {
            pageable = PageRequest.of(number, size, sort);
        } else {
            pageable = PageRequest.of(number, size);
        }

        Specification<ConfigDetail> dynamicConfition = (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(configDetail.getClassId())) {
                list.add(
                        builder.and(builder.equal(root.get("classId"), configDetail.getClassId()))
                );
            }

            if (!StringUtils.isEmpty(configDetail.getTypeCode())) {
                list.add(
                        builder.and(builder.like(root.get("typeCode"), "%" + configDetail.getTypeCode() + "%"))
                );
            }

            if (!StringUtils.isEmpty(configDetail.getTypeName())) {
                list.add(
                        builder.and(builder.like(root.get("typeName"), "%" + configDetail.getTypeName() + "%"))
                );
            }

            return builder.and(list.toArray(new Predicate[list.size()]));
        };

        Page<ConfigDetail> page = configDetailRepository.findAll(dynamicConfition, pageable);
        return page;
    }

    @Override
    public ConfigMain saveConfigMain(ConfigMain configMain) {
        return configMainRepository.save(configMain);
    }

    @Override
    public ConfigDetail saveConfigDetail(ConfigDetail configDetail) {
        return configDetailRepository.save(configDetail);
    }

    @Override
    public void deleteConfigDetail(String id) {
        configDetailRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteConfigMain(String id) {
        configDetailRepository.deleteByClassId(id);
        configMainRepository.deleteById(id);
    }

    @Override
    public ConfigMain updateConfigMain(ConfigMain configMain) {
        return configMainRepository.save(configMain);
    }

    @Override
    public ConfigDetail updateConfigDetail(ConfigDetail configDetail) {
        return configDetailRepository.save(configDetail);
    }

    @Override
    public ConfigMain listConfigMainOne(String id) {
        return configMainRepository.findById(id).get();
    }

    @Override
    public ConfigDetail listConfigDetailOne(String id) {
        return configDetailRepository.findById(id).get();
    }
}
