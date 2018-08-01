package com.crcc.exam.repository;

import com.crcc.exam.domain.po.SysAuthorize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysAuthorizeRepository extends JpaRepository<SysAuthorize, String> {

    @Modifying
    @Query(value = "delete from SysAuthorize  t where t.userId=:userId")
    void deleteAuthByUserId(@Param("userId") String userId);
}
