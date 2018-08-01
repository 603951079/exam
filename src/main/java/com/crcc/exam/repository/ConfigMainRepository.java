package com.crcc.exam.repository;

import com.crcc.exam.domain.po.ConfigMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigMainRepository extends BaseRepository<ConfigMain, String> {

    @Query(value = "select m from ConfigMain m where m.classCode in (:classCodes) ")
    List<ConfigMain> findClassNames(@Param("classCodes") List<String> classCodes);
}
