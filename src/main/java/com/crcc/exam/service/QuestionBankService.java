package com.crcc.exam.service;

import com.crcc.exam.domain.dto.BasePageDTO;
import com.crcc.exam.domain.po.QuestionBank;
import org.springframework.data.domain.Page;

public interface QuestionBankService {

    /**
     * 新增试题
     */
    QuestionBank saveQuestion(QuestionBank questionBank);

    /**
     * 删除试题
     */
    Integer deleteQuestion(String id);

    /**
     * 修改试题
     */
    QuestionBank updateQuestion(QuestionBank questionBank);

    /**
     * 试题查询
     */
    Page<QuestionBank> listQuestions(BasePageDTO<QuestionBank> basePage);

    /**
     * 根据ID查询试题信息
     */
    QuestionBank listQuestionsOne(String id);
}
