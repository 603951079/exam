package com.crcc.exam.repository;

import com.crcc.exam.domain.po.ConfigDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigDetailRepository extends BaseRepository<ConfigDetail, String> {

    List<ConfigDetail> findByClassId(String id);

    void deleteByClassId(String id);
}
