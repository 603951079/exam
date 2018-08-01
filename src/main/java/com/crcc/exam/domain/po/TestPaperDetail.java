package com.crcc.exam.domain.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * 试卷明细表
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TestPaperDetail extends BaseEntity {
    /**
     * 试卷主表Id
     */
    private String testPaperId;
    /**
     * 序号
     */
    private String questionNum;
    /**
     * 题干
     */
    private String questionTitle;
    /**
     * 选项
     */
    private String questionOption;
    /**
     * 答案
     */
    private String questionResult;

    /**
     * 用户解答
     */
    private String questionAnswer;

    /**
     * 试题得分
     */
    private Integer questionScore;
    /**
     * 大分类：前端、java、服务器、数据库
     */
    private String questionClass;
    private String questinClassCode;
    /**
     * 小分类：填空题、单选题、多选题、问答题
     */
    private String questionType;
    private String questionTypeCode;

    public TestPaperDetail(String questionNum, String questionTitle, String questionOption,
                           String questionResult, String questionClass, String questionType,
                           String questinClassCode, String questionTypeCode) {
        this.questionNum = questionNum;
        this.questionTitle = questionTitle;
        this.questionOption = questionOption;
        this.questionResult = questionResult;
        this.questionClass = questionClass;
        this.questionType = questionType;
        this.questinClassCode = questinClassCode;
        this.questionTypeCode = questionTypeCode;
    }
}
