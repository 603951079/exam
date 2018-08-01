package com.crcc.exam.domain.dto;

import com.crcc.exam.domain.po.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@ToString
@NoArgsConstructor
@Setter
@Getter
public class TestPaperDetailDTO extends BaseEntity {
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
    private List<String> questionOptionList;
    /**
     * 答案
     */
    private String questionResult;

    /**
     * 用户解答
     */
    private String questionAnswer;
    private List<String> questionAnswerList;

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

    public TestPaperDetailDTO(
            String id,
            Integer version,
            String createByUser,
            String modifyByUser,
            String testPaperId,
            String questionNum,
            String questionTitle,
            String questionOption,
            String questionResult,
            String questionAnswer,
            Integer questionScore,
            String questionClass,
            String questinClassCode,
            String questionType,
            String questionTypeCode) {
        super.setId(id);
        super.setVersion(version);
        super.setCreateByUser(createByUser);
        super.setModifyByUser(modifyByUser);
        this.testPaperId = testPaperId;
        this.questionNum = questionNum;
        this.questionTitle = questionTitle;
        this.questionOption = questionOption;
        this.questionResult = questionResult;
        this.questionAnswer = questionAnswer;
        this.questionScore = questionScore;
        this.questionClass = questionClass;
        this.questinClassCode = questinClassCode;
        this.questionType = questionType;
        this.questionTypeCode = questionTypeCode;
    }
}
