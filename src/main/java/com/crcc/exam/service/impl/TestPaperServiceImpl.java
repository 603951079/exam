package com.crcc.exam.service.impl;

import com.crcc.exam.domain.dto.BasePageDTO;
import com.crcc.exam.domain.dto.TestPaperDetailDTO;
import com.crcc.exam.domain.po.*;
import com.crcc.exam.repository.*;
import com.crcc.exam.service.TestPaperService;
import com.crcc.exam.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TestPaperServiceImpl implements TestPaperService {
    @Autowired
    private TestPaperRepository testPaperRepository;

    @Autowired
    private TestPaperDetailRepository testPaperDetailRepository;

    @Autowired
    private ConfigMainRepository configMainRepository;

    @Autowired
    private ConfigDetailRepository configDetailRepository;

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysAuthorizeRepository sysAuthorizeRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${app.roleId}")
    private String roleId;

    @Transactional
    @Override
    public void saveTestPaper(TestPaper testPaper) {

        StringBuffer classCodes = new StringBuffer();
        StringBuffer classNames = new StringBuffer();
        List<ConfigMain> configMains = configMainRepository.findClassNames(testPaper.getClassCodesList());
        for (ConfigMain configMain : configMains) {
            classCodes.append(configMain.getClassCode());
            classCodes.append(",");

            classNames.append(configMain.getClassName());
            classNames.append(",");
        }

        testPaper.setClassCodes(classCodes.substring(0, classCodes.length() - 1));
        testPaper.setClassNames(classNames.substring(0, classNames.length() - 1));
        // 保存试卷主表
        testPaper = testPaperRepository.save(testPaper);


        // 随机生成试卷明细
        List<TestPaperDetail> result = new ArrayList<>();
        for (int i = 0; i < configMains.size(); i++) {
            ConfigMain item = configMains.get(i);
            String classId = item.getId();
            String classCode = item.getClassCode();
            List<ConfigDetail> details = this.configDetailRepository.findByClassId(classId);
            log.debug(details.toString());
            for (int j = 0; j < details.size(); j++) {
                ConfigDetail detail = details.get(j);
                String typeCode = detail.getTypeCode();
                log.debug("typeCode=" + typeCode);
                int number = detail.getNumber();
                log.debug("number=" + number);
                Pageable pageable = PageRequest.of(0, number);
                List<TestPaperDetail> randList = testPaperDetailRepository.findRand(classCode, typeCode, pageable);
                result.addAll(randList);
            }
        }
        if (result.size() > 0) {
            for (int i = 0; i < result.size(); i++) {
                TestPaperDetail item = result.get(i);
                item.setTestPaperId(testPaper.getId());
            }
        }
        // 保存试卷明细数据
        testPaperDetailRepository.saveAll(result);

        // 插入用户数据
        SysUser sysUser = new SysUser();
        sysUser.setZhName(testPaper.getTestPsn());
        sysUser.setUsername(testPaper.getTestPhone());
        sysUser.setPhone(testPaper.getTestPhone());
        sysUser.setPassword(bCryptPasswordEncoder.encode(testPaper.getTestPhone()));
        sysUser.setAccountNonExpired(true);
        sysUser.setCredentialsNonExpired(true);
        sysUser.setAccountNonLocked(true);
        sysUser.setEnabled(true);
        sysUser.setFlag("0");

        sysUser = this.sysUserRepository.save(sysUser);

        // 插入授权数据
        SysAuthorize sysAuthorize = new SysAuthorize();
        sysAuthorize.setRoleId(roleId);
        sysAuthorize.setUserId(sysUser.getId());
        this.sysAuthorizeRepository.save(sysAuthorize);
    }

    @Override
    public Page<TestPaper> listTestPaper(BasePageDTO<TestPaper> basePageDTO) {
        log.debug(basePageDTO.toString());
        int number = basePageDTO.getNumber();
        int size = basePageDTO.getSize();
        TestPaper testPaper = basePageDTO.getCondition();
        Sort sort = basePageDTO.getSort();

        Pageable pageable = null;
        if (sort != null) {
            pageable = PageRequest.of(number, size, sort);
        } else {
            pageable = PageRequest.of(number, size);
        }

        Specification<TestPaper> dynamicCondition = (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(testPaper.getTestPsn())) {
                list.add(
                        builder.and(builder.like(root.get("testPsn"), "%" + testPaper.getTestPsn() + "%"))
                );
            }

            if (!StringUtils.isEmpty(testPaper.getTestPhone())) {
                list.add(
                        builder.and(builder.like(root.get("testPhone"), "%" + testPaper.getTestPhone() + "%"))
                );
            }

            return builder.and(list.toArray(new Predicate[list.size()]));
        };

        Page<TestPaper> page = testPaperRepository.findAll(dynamicCondition, pageable);
        List<TestPaper> list = page.getContent();
        for (TestPaper paper : list) {
            paper.setClassNamesList(Arrays.asList(paper.getClassNames().split(",")));
            paper.setClassCodesList(Arrays.asList(paper.getClassCodes().split(",")));
        }

        return page;
    }

    public List<TestPaperDetailDTO> listTestPaperDetail(String id) {
        List<TestPaperDetailDTO> list = this.testPaperDetailRepository.findByTestPaperId(id);
        for (TestPaperDetailDTO item : list) {
            if ("02".equals(item.getQuestionTypeCode()) || "03".equals(item.getQuestionTypeCode())) {
                if (!StringUtils.isEmpty(item.getQuestionOption())) {
                    item.setQuestionOptionList(Arrays.stream(item.getQuestionOption().split("\n")).filter(a -> {
                        return !StringUtils.isEmpty(a);
                    }).collect(Collectors.toList()));
                }

               if("03".equals(item.getQuestionTypeCode())){
                    if (!StringUtils.isEmpty(item.getQuestionAnswer())) {
                        item.setQuestionAnswerList(Arrays.stream(item.getQuestionAnswer().split("\n")).filter(a -> {
                            return !StringUtils.isEmpty(a);
                        }).collect(Collectors.toList()));
                    } else {
                        item.setQuestionAnswerList(new ArrayList<String>());
                    }
                }
            }
        }

        return list;
    }

    @Transactional
    @Override
    public List<TestPaperDetailDTO> saveTestPaperdetails(List<TestPaperDetailDTO> testPaperDetails) {
        String testPaperId = null;
        List<TestPaperDetail> list = new ArrayList<>();
        for (TestPaperDetailDTO item : testPaperDetails) {
            TestPaperDetail testPaperDetail = new TestPaperDetail();
            testPaperDetail.setId(item.getId());
            testPaperDetail.setCreateByUser(item.getCreateByUser());
            testPaperDetail.setCreateTime(new Date());
            testPaperDetail.setModifyByUser(item.getModifyByUser());
            testPaperDetail.setModifyTime(new Date());
            testPaperDetail.setVersion(item.getVersion());

            testPaperId = item.getTestPaperId();
            testPaperDetail.setTestPaperId(testPaperId);
            testPaperDetail.setQuestionNum(item.getQuestionNum());
            testPaperDetail.setQuestionTitle(item.getQuestionTitle());
            testPaperDetail.setQuestionClass(item.getQuestionClass());
            testPaperDetail.setQuestinClassCode(item.getQuestinClassCode());
            testPaperDetail.setQuestionType(item.getQuestionType());
            testPaperDetail.setQuestionTypeCode(item.getQuestionTypeCode());
            testPaperDetail.setQuestionOption(item.getQuestionOption());
            testPaperDetail.setQuestionResult(item.getQuestionResult());
            testPaperDetail.setQuestionScore(item.getQuestionScore());
            if("03".equals(item.getQuestionTypeCode())){// 多选题
                testPaperDetail.setQuestionAnswer(CommonUtil.listToString(item.getQuestionAnswerList(), "\n"));
            }else { // 填空题、单选题、问答题
                testPaperDetail.setQuestionAnswer(item.getQuestionAnswer());
            }

            list.add(testPaperDetail);
        }
        this.testPaperDetailRepository.saveAll(list);
        return this.listTestPaperDetail(testPaperId);
    }

    @Transactional
    @Override
    public void updateTestTime(Map<String, String> map) {
        String testPaperId = map.get("testPaperId");
        String beginTime = map.get("beginTime");
        String endTime = map.get("endTime");

        if (beginTime != null) {
            TestPaper testPaper = this.testPaperRepository.findById(testPaperId).get();
            if (testPaper != null) {
                // 判断考试开始时间没有值则执行更新操作，有值则不更新
                if (testPaper.getTestBeginTime() == null) {
                    this.testPaperRepository.setBeginTime(new Date(Long.parseLong(map.get("beginTime"))), testPaperId);
                }
            }
        } else if (endTime != null) {
            this.testPaperRepository.setEndTime(new Date(Long.parseLong(map.get("endTime"))), testPaperId);
        }
    }

    @Transactional
    @Override
    public void updateCorrectInfo(Map<String, String> map) {
        String testPaperId = map.get("testPaperId");
        Long orrectTime = Long.parseLong((String) map.get("orrectTime"));
        String orrectPsn = (String) map.get("orrectPsn");
        Double testScore = Double.parseDouble(map.get("testScore"));

        this.testPaperRepository.setCorrentInfo(new Date(orrectTime), orrectPsn, testPaperId, testScore);
    }

    @Override
    public String getTestPaperIdPhone(String phone) {
        log.debug("phone=" + phone);
        return this.testPaperRepository.getTestPaperIdByPhone(phone);
    }
}
