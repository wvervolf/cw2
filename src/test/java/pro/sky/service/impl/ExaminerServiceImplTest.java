package pro.sky.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.exception.IncorrectAmountException;
import pro.sky.model.Question;
import pro.sky.service.QuestionService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    private final List<Question> questions = List.of(
            new Question("Вопрос 1", "Ответ 1"),
            new Question("Вопрос 2", "Ответ 2"),
            new Question("Вопрос 3", "Ответ 3"),
            new Question("Вопрос 4", "Ответ 4"),
            new Question("Вопрос 5", "Ответ 5")
    );

    @BeforeEach
    public void beforeEach() {
        when(questionService.getAll()).thenReturn(questions);
    }

    @ParameterizedTest
    @ValueSource(ints = {6, -1, -5})
    public void getQuestionsNegativeTest(int amount) {
        assertThatExceptionOfType(IncorrectAmountException.class)
                .isThrownBy(() -> examinerService.getQuestions(amount));
    }

    @Test
    public void getQuestionsTest() {
        when(questionService.getRandomQuestion()).thenReturn(
                new Question("Вопрос 2", "Ответ 2"),
                new Question("Вопрос 2", "Ответ 2"),
                new Question("Вопрос 4", "Ответ 4"),
                new Question("Вопрос 1", "Ответ 1"),
                new Question("Вопрос 4", "Ответ 4"),
                new Question("Вопрос 5", "Ответ 5")
        );

        assertThat(examinerService.getQuestions(4)).containsExactlyInAnyOrder(
                new Question("Вопрос 1", "Ответ 1"),
                new Question("Вопрос 2", "Ответ 2"),
                new Question("Вопрос 4", "Ответ 4"),
                new Question("Вопрос 5", "Ответ 5")
        );
    }

}
