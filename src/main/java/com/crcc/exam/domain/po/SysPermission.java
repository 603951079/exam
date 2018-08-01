package com.crcc.exam.domain.po;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SysPermission extends BaseEntity {
    private String menuIcon; // 菜单图标
    private String menuTitle; // 菜单名称
    private String menuIndex; // 菜单路径
    private String menuParent; // 父菜单
    private String menuSequence; // 排序
    @Transient
    private List<SysPermission> subs; // 所拥有的子
}
