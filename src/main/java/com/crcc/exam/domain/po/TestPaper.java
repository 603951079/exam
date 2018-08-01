package com.crcc.exam.domain.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 试卷主表
 */
@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
public class TestPaper extends BaseEntity {
    /**
     * 考试人名字
     */
    private String testPsn;
    /**
     * 考试人手机号
     */
    private String testPhone;
    /**
     * 考试开始时间
     */
    private Date testBeginTime;

    /**
     * 考试结束时间
     */
    private Date testEndTime;
    /**
     * 考试得分
     */
    private Double testScore;
    /**
     * 批改人名字
     */
    private String orrectPsn;
    /**
     * 批改时间
     */
    private Date orrectTime;

    /**
     * 试题类型
     */
    private String classCodes;
    private String classNames;

    @Transient
    private List<String> classNamesList;
    @Transient
    private List<String> classCodesList;

    public TestPaper(String testPsn, String testPhone) {
        this.testPhone = testPhone;
        this.testPsn = testPsn;
    }
}
