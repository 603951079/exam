package com.crcc.exam.repository;

import com.crcc.exam.domain.po.QuestionBank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionBankRepository extends BaseRepository<QuestionBank, String> {
/*    @Query("select QuestionBank from QuestionBank  QuestionBank " +
            "left join ConfigMain m on QuestionBank.questionClassCode = m.classCode " +
            "left join ConfigDetail d on QuestionBank.questionTypeCode = d.typeCode ")
    Page<QuestionBank> findAll(Specification<QuestionBank> dynamicCondition, Pageable pageable);*/
}
