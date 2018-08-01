package com.crcc.exam.web;

import com.crcc.exam.domain.dto.BasePageDTO;
import com.crcc.exam.domain.dto.TestPaperDetailDTO;
import com.crcc.exam.domain.po.TestPaper;
import com.crcc.exam.domain.po.TestPaperDetail;
import com.crcc.exam.service.TestPaperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(description = "试卷管理")
@RequestMapping("/testpaper")
@Slf4j
public class TestPaperController {
    @Autowired
    private TestPaperService testPaperService;


    @PostMapping("/v1.0/save")
    @ApiOperation("生成试卷")
    public void saveTestPaper(@RequestBody TestPaper testPaper) {
        log.debug(testPaper.toString());
        testPaperService.saveTestPaper(testPaper);
    }

    /**
     * 查询参考人列表
     */
    @PostMapping("/v1.0/list")
    @ApiOperation("查询参考人列表")
    public Page<TestPaper> listTestPaper(@RequestBody BasePageDTO<TestPaper> basePageDTO) {
        return testPaperService.listTestPaper(basePageDTO);
    }

    /**
     * 查询试题明细
     */
    @GetMapping("/v1.0/list/detail/{id}")
    @ApiOperation("查询试题明细")
    public List<TestPaperDetailDTO> listTestPaperDetails(@PathVariable String id) {
        return this.testPaperService.listTestPaperDetail(id);
    }

    /**
     * 保存考试结果
     */
    @PostMapping("/v1.0/list/detail/save")
    @ApiOperation("保存考试结果")
    public List<TestPaperDetailDTO> saveTestPaperdetails(@RequestBody List<TestPaperDetailDTO> testPaperDetails) {
        return this.testPaperService.saveTestPaperdetails(testPaperDetails);
    }

    /**
     * 设置考试开始时间、考试结束时间
     */
    @PutMapping("/v1.0/update/testtime")
    @ApiOperation("更新考试开始与结束时间")
    public void updateTestTime(@RequestBody Map<String, String> map) {
        log.debug(map.toString());
        this.testPaperService.updateTestTime(map);
    }

    /**
     * 设置批改人信息
     */
    @PutMapping("/v1.0/update/correct")
    @ApiOperation("设置批改人信息")
    public void updateCorrectInfo(@RequestBody Map<String, String> map) {
        this.testPaperService.updateCorrectInfo(map);
    }


    /**
     * 根据当前登录人手机号获取关联的试卷ID
     */
    @GetMapping("/v1.0/get/testpaperid/{phone}")
    @ApiOperation("根据当前登录人手机号获取关联的试卷")
    public String getTestPaperIdByPhone(@PathVariable String phone) {
        String testPaperId = this.testPaperService.getTestPaperIdPhone(phone);
        log.debug("testPaperId=" + testPaperId);
        return (testPaperId == null ? "" : testPaperId);
    }

}
