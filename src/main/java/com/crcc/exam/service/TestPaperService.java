package com.crcc.exam.service;

import com.crcc.exam.domain.dto.BasePageDTO;
import com.crcc.exam.domain.dto.TestPaperDetailDTO;
import com.crcc.exam.domain.po.TestPaper;
import com.crcc.exam.domain.po.TestPaperDetail;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface TestPaperService {
    /**
     * 生成试卷
     */
    void saveTestPaper(TestPaper testPaper);

    /**
     * 查询参考人列表
     */
    Page<TestPaper> listTestPaper(BasePageDTO<TestPaper> basePageDTO);

    /**
     * 查询考题明细
     */
    List<TestPaperDetailDTO> listTestPaperDetail(String id);

    /**
     * 保存考试结果
     */
    List<TestPaperDetailDTO> saveTestPaperdetails(List<TestPaperDetailDTO> testPaperDetails);

    /**
     * 设置考试开始时间、考试结束时间
     */
    void updateTestTime(Map<String, String> map);

    /**
     * 设置批改人信息
     */
    void updateCorrectInfo(Map<String, String> map);

    /**
     * 根据用户手机号获取用户关联的试卷id
     */
    String getTestPaperIdPhone(String phone);
}
