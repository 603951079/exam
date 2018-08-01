package com.crcc.exam.domain.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 通用基类，提供公共的映射字段
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Setter
@Getter
public class BaseEntity implements Serializable {
    /**
     * 主键
     */

    @Id
    @GenericGenerator(name = "system-uuid",
            strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "id")
    private String id;

    /**
     * 版本号
     */
    @Column(name = "version", nullable = false)
    @Version
    private Integer version;

    /**
     * 创建人
     */
    @Column(name = "created_by_user", nullable = false)
    @CreatedBy
    private String createByUser;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    @CreatedDate
    private Date createTime;

    /**
     * 修改人
     */
    @Column(name = "modify_by_user", nullable = false)
    @LastModifiedBy
    private String modifyByUser;

    /**
     * 修饰时间
     */
    @Column(name = "modify_time", nullable = false)
    @LastModifiedDate
    private Date modifyTime;
}
