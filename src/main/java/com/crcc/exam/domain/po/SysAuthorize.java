package com.crcc.exam.domain.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class SysAuthorize extends BaseEntity {
    private String userId;
    private String roleId;
}
