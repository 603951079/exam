package com.crcc.exam.domain.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * 题库
 */
@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
public class QuestionBank extends BaseEntity {
    /**
     *序号
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
     * 大分类：前端(01)、java(02)、服务器(03)、数据库(04)
     */
    private String questionClass;
    private String questionClassCode;
    /**
     * 小分类：填空题(01)、单选题(02)、多选题(03)、问答题(04)
     */
    private String questionType;
    private String questionTypeCode;
}
