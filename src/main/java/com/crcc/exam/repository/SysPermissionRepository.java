package com.crcc.exam.repository;

import com.crcc.exam.domain.po.SysPermission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPermissionRepository extends BaseRepository<SysPermission, String> {

    @Query(value = "select t from SysPermission  t " +
            "left join SysRolePermission rp on t.id=rp.permissionId " +
            "left join SysRole r on rp.roleId = r.id " +
            "where r.roleCode in (:roleCodes)")
    List<SysPermission> listPermission(@Param("roleCodes") List<String> roleCodes);
}
