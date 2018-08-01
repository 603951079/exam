package com.crcc.exam.repository;

import com.crcc.exam.domain.po.TestPaper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TestPaperRepository extends BaseRepository<TestPaper, String> {

    @Modifying
    @Query(value = "update TestPaper t set t.testBeginTime=:beginTime where t.id=:testPaperId")
    void setBeginTime(@Param("beginTime") Date beginTime, @Param("testPaperId") String testPaperId);

    @Modifying
    @Query(value = "update TestPaper t set t.testEndTime=:endTime where t.id=:testPaperId")
    void setEndTime(@Param("endTime") Date endTime, @Param("testPaperId") String testPaperId);

    @Modifying
    @Query(value = "update TestPaper t set t.orrectPsn=:orrectPsn ," +
            " t.testScore = :testScore, t.orrectTime=:orrectTime where t.id=:testPaperId")
    void setCorrentInfo(@Param("orrectTime") Date orrectTime,
                        @Param("orrectPsn") String orrectPsn,
                        @Param("testPaperId") String testPaperId,
                        @Param("testScore") Double testScore);

    @Query(value = "select t.id from TestPaper t where t.testPhone=:testPhone " +
            "and t.testEndTime is null and t.testScore is null ")
    String getTestPaperIdByPhone(@Param("testPhone") String testPhone);
}
