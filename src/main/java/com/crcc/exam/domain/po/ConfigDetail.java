package com.crcc.exam.domain.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
public class ConfigDetail extends BaseEntity {
    /**
     * 排序号
     */
    private String typeNum;
    /**
     * 大分类主键
     */
    private String classId;

    /**
     * 小分类名称
     */
    private String typeName;

    /**
     * 小分类编码
     */
    private String typeCode;

    /**
     * 试题数目
     */
    private Integer number;

    /**
     * 小分类描述
     */
    private String typeDesc;
}
