package com.crcc.exam.service.impl;

import com.crcc.exam.domain.dto.BasePageDTO;
import com.crcc.exam.domain.po.QuestionBank;
import com.crcc.exam.repository.QuestionBankRepository;
import com.crcc.exam.service.QuestionBankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class QuestionBankServiceImpl implements QuestionBankService {
    @Autowired
    private QuestionBankRepository questionBankRepository;

    @Override
    public QuestionBank saveQuestion(QuestionBank questionBank) {
        return questionBankRepository.save(questionBank);
    }

    @Override
    public Integer deleteQuestion(String id) {
        questionBankRepository.deleteById(id);
        return 1;
    }

    @Override
    public QuestionBank updateQuestion(QuestionBank questionBank) {
        return questionBankRepository.save(questionBank);
    }

    @Override
    public Page<QuestionBank> listQuestions(BasePageDTO<QuestionBank> basePage) {
        int number = basePage.getNumber();
        int size = basePage.getSize();
        QuestionBank questionBank = basePage.getCondition();
        Sort sort = basePage.getSort();

        Pageable pageable = PageRequest.of(number, size);
        if (sort != null) {
            pageable = PageRequest.of(number, size, sort);
        } else {
            pageable = PageRequest.of(number, size);
        }

        Specification<QuestionBank> dynamicCondition = (root, query, builder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(questionBank.getQuestionClassCode())) {
                list.add(
                        builder.and(builder.equal(root.get("questionClassCode"), questionBank.getQuestionClassCode()))
                );
            }
            if (!StringUtils.isEmpty(questionBank.getQuestionTypeCode())) {
                list.add(
                        builder.and(builder.equal(root.get("questionTypeCode"), questionBank.getQuestionTypeCode()))
                );
            }
            if (!StringUtils.isEmpty(questionBank.getQuestionTitle())) {
                list.add(
                        builder.and(builder.like(root.get("questionTitle"), "%" + questionBank.getQuestionTitle() + "%"))
                );
            }
            return builder.and(list.toArray(new Predicate[list.size()]));
        };


        return questionBankRepository.findAll(dynamicCondition, pageable);
    }

    @Override
    public QuestionBank listQuestionsOne(String id) {
        return questionBankRepository.findById(id).get();
    }
}
