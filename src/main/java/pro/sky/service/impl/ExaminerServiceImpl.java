package pro.sky.service.impl;

import org.springframework.stereotype.Service;
import pro.sky.exception.IncorrectAmountException;
import pro.sky.model.Question;
import pro.sky.service.ExaminerService;
import pro.sky.service.QuestionService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (questionService.getAll().size() < amount || amount <= 0) {
            throw new IncorrectAmountException();
        }
        Set<Question> questions = new HashSet<>();
        while (questions.size() < amount) {
            questions.add(questionService.getRandomQuestion());
        }
        return questions;
    }

}
