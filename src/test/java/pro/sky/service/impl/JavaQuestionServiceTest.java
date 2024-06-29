package pro.sky.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pro.sky.exception.QuestionAlreadyAddedException;
import pro.sky.exception.QuestionNotFoundException;
import pro.sky.exception.QuestionsAreEmptyException;
import pro.sky.model.Question;
import pro.sky.service.QuestionService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class JavaQuestionServiceTest {

    private final QuestionService questionService = new JavaQuestionService();

    private final List<Question> questions = List.of(
            new Question("Вопрос 1", "Ответ 1"),
            new Question("Вопрос 2", "Ответ 2"),
            new Question("Вопрос 3", "Ответ 3")
    );

    @BeforeEach
    public void beforeEach() {
        questions.forEach(questionService::add);
    }

    @AfterEach
    public void afterEach() {
        new ArrayList<>(questionService.getAll()).forEach(questionService::remove);
    }

    @Test
    public void add1Test() {
        int was = questionService.getAll().size();
        Question expected = new Question("Вопрос 4", "Ответ 4");
        assertThat(questionService.getAll()).doesNotContain(expected);

        Question actual = questionService.add(new Question("Вопрос 4", "Ответ 4"));

        assertThat(actual).isEqualTo(expected);
        assertThat(questionService.getAll()).hasSize(was + 1);
        assertThat(questionService.getAll()).contains(expected);
    }

    @Test
    public void add2Test() {
        int was = questionService.getAll().size();
        Question expected = new Question("Вопрос 4", "Ответ 4");
        assertThat(questionService.getAll()).doesNotContain(expected);

        Question actual = questionService.add("Вопрос 4", "Ответ 4");

        assertThat(actual).isEqualTo(expected);
        assertThat(questionService.getAll()).hasSize(was + 1);
        assertThat(questionService.getAll()).contains(expected);
    }

    @Test
    public void add1NegativeTest() {
        assertThatExceptionOfType(QuestionAlreadyAddedException.class)
                .isThrownBy(() -> questionService.add(new Question("Вопрос 1", "Ответ 1")));
    }

    @Test
    public void add2NegativeTest() {
        assertThatExceptionOfType(QuestionAlreadyAddedException.class)
                .isThrownBy(() -> questionService.add("Вопрос 1", "Ответ 1"));
    }

    @Test
    public void removeTest() {
        int was = questionService.getAll().size();
        Question expected = new Question("Вопрос 1", "Ответ 1");
        assertThat(questionService.getAll()).contains(expected);

        Question actual = questionService.remove(new Question("Вопрос 1", "Ответ 1"));

        assertThat(actual).isEqualTo(expected);
        assertThat(questionService.getAll()).hasSize(was - 1);
        assertThat(questionService.getAll()).doesNotContain(expected);
    }

    @Test
    public void removeNegativeTest() {
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() -> questionService.remove(new Question("Вопрос 4", "Ответ 4")));
    }

    @Test
    public void getAllTest() {
        assertThat(questionService.getAll()).containsExactlyInAnyOrderElementsOf(questions);
    }

    @Test
    public void getRandomQuestionTest() {
        assertThat(questionService.getRandomQuestion()).isIn(questionService.getAll());
    }

    @Test
    public void getRandomQuestionNegativeTest() {
        afterEach();

        assertThatExceptionOfType(QuestionsAreEmptyException.class).isThrownBy(questionService::getRandomQuestion);
    }

}
