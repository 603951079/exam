package com.crcc.exam.web;

import com.crcc.exam.domain.dto.BasePageDTO;
import com.crcc.exam.domain.po.QuestionBank;
import com.crcc.exam.service.QuestionBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questionbank")
@Slf4j
@Api(description = "题库管理")
public class QuestionBankController {
    @Autowired
    private QuestionBankService questionBankService;

    /**
     * 新增试题
     */
    @PostMapping("/v1.0/save")
    @ApiOperation("新增考试试题")
    public QuestionBank saveQuestion(@RequestBody QuestionBank questionBank) {
        return questionBankService.saveQuestion(questionBank);
    }

    /**
     * 删除试题
     */
    @DeleteMapping("/v1.0/delete/{id}")
    @ApiOperation("删除考试试题")
    public Integer deleteQuestion(@ApiParam("试题主键") @PathVariable String id) {
        return questionBankService.deleteQuestion(id);
    }

    /**
     * 修改试题
     */
    @PutMapping("/v1.0/update")
    @ApiOperation("修改考试试题")
    public QuestionBank updateQuestion(@RequestBody QuestionBank questionBank) {
        log.debug(questionBank.toString());
        return questionBankService.updateQuestion(questionBank);
    }

    /**
     * 试题查询
     */
    @PostMapping("/v1.0/list")
    @ApiOperation("获取试题列表")
    public Page<QuestionBank> listQuestions(@RequestBody BasePageDTO<QuestionBank> basePage) {
        log.debug(basePage.toString());
        return questionBankService.listQuestions(basePage);
    }

    /**
     * 试题查询
     */
    @GetMapping("/v1.0/list/{id}")
    @ApiOperation("根据ID查询试题信息")
    public QuestionBank listQuestionsOne(@PathVariable String id) {
        return questionBankService.listQuestionsOne(id);
    }
}
