package com.crcc.exam.repository;

import com.crcc.exam.domain.po.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleRepository extends BaseRepository<SysRole, String> {
    @Query(value = "select t.* from sys_role t where t.role_name = :rolename ",
            nativeQuery = true)
    List<SysRole> findByRoleName(@Param("rolename") String roleName);

    @Query(value = "select t.* from sys_role t where t.role_code = :rolecode ",
            countQuery = "select count(t.id) from sys_role t where t.role_code = :rolecode",
            nativeQuery = true)
    Page<SysRole> findByRoleCode(@Param("rolecode") String roleCode, Pageable pageable);

    @Query(value = "select r from SysRole r " +
            "left join SysAuthorize a on r.id = a.roleId " +
            "where a.userId=:userId")
    Page<SysRole> findByUserId(@Param("userId") String userId, Pageable pageable);
}
