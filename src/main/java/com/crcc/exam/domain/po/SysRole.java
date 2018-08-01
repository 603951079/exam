package com.crcc.exam.domain.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class SysRole extends BaseEntity {
    private String roleName;
    private String roleCode;
    private String roleDesc;
}
