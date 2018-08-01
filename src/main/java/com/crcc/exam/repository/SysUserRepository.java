package com.crcc.exam.repository;

import com.crcc.exam.domain.po.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository extends BaseRepository<SysUser, String> {

    @Query(value = "select t from SysUser t where t.username=:username")
    SysUser loadUserByUsername(@Param("username") String username);

    @Modifying
    @Query("update SysUser u set u.isEnabled=:enable where u.id=:id")
    void abledSysUser(@Param("enable") Boolean enable, @Param("id") String id);
}
