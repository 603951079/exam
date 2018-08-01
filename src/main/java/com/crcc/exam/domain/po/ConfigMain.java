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
public class ConfigMain extends BaseEntity {
    /**
     * 排序号
     */
    private String classNum;
    /**
     * 大分类名称
     */
    private String className;
    /**
     * 大分类编码
     */
    private String classCode;
    /**
     * 大分类描述
     */
    private String classDesc;
}
