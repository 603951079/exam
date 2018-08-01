package com.crcc.exam.repository;

import com.crcc.exam.domain.dto.TestPaperDetailDTO;
import com.crcc.exam.domain.po.TestPaperDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestPaperDetailRepository extends JpaRepository<TestPaperDetail, String> {
    /**
     * 从试题库中随机查询
     */
    @Query("select new com.crcc.exam.domain.po.TestPaperDetail(t.questionNum,t.questionTitle,t.questionOption,t.questionResult," +
            "t.questionClass,t.questionType,t.questionClassCode,t.questionTypeCode) " +
            "from QuestionBank t where t.questionClassCode=:classCode and t.questionTypeCode=:typeCode order by rand()")
    List<TestPaperDetail> findRand(@Param("classCode") String classCode, @Param("typeCode") String typeCode, Pageable pageable);


    /**
     * 获取考题明细
     */
    @Query(value = "select new com.crcc.exam.domain.dto.TestPaperDetailDTO" +
            "(t.id,t.version,t.createByUser,t.modifyByUser," +
            "t.testPaperId,t.questionNum,t.questionTitle,t.questionOption,t.questionResult,t.questionAnswer,t.questionScore," +
            "t.questionClass,t.questinClassCode,t.questionType,t.questionTypeCode) " +
            "from TestPaperDetail t " +
            "where t.testPaperId=:testPaperId " +
            "order by t.questinClassCode asc , t.questionTypeCode asc ,t.questionNum asc ")
    List<TestPaperDetailDTO> findByTestPaperId(@Param("testPaperId") String id);
}
