package stackover.resource.service.rest.out;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import stackover.resource.service.ResourceServiceApplicationTests;
import stackover.resource.service.dto.request.AnswerRequestDto;
import stackover.resource.service.entity.question.answer.VoteTypeAnswer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ResourceAnswerControllerTest extends ResourceServiceApplicationTests {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "scripts/out/ResourceAnswerControllerTest/addAnswerToQuestion_ShouldPass/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "scripts/out/ResourceAnswerControllerTest/addAnswerToQuestion_ShouldPass/AfterTest.sql")
    void addAnswerToQuestion_ShouldPass_Created() throws Exception {
        AnswerRequestDto requestDto = new AnswerRequestDto(
                null,
                101L,
                101L,
                "test answer",
                null,
                false,
                null,
                0L,
                0L,
                "",
                "",
                0L,
                VoteTypeAnswer.UP
                );
        mockMvc.perform(post("/api/user/question/101/answer?accountId=1001")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value ="scripts/out/ResourceAnswerControllerTest/addAnswerToQuestion_ShouldFail/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value ="scripts/out/ResourceAnswerControllerTest/addAnswerToQuestion_ShouldFail/AfterTest.sql")
    void addAnswerToQuestion_ShouldFail_UnprocessableEntity() throws Exception {
        AnswerRequestDto requestDto = new AnswerRequestDto(
                1L,
                111L,
                100001L,
                "test answer",
                null,
                false,
                null,
                0L,
                0L,
                "",
                "",
                0L,
                VoteTypeAnswer.UP
        );
        mockMvc.perform(post("/api/user/question/101/answer?accountId=1004")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value ="scripts/out/ResourceAnswerControllerTest/addAnswerToQuestion_ShouldFail/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value ="scripts/out/ResourceAnswerControllerTest/addAnswerToQuestion_ShouldFail/AfterTest.sql")
    void addAnswerToQuestion_ShouldFail_BadRequest() throws Exception {
        AnswerRequestDto requestDto = new AnswerRequestDto(
                1L,
                1001L,
                101L,
                "test answer",
                null,
                false,
                null,
                0L,
                0L,
                "",
                "",
                0L,
                VoteTypeAnswer.UP
        );
        mockMvc.perform(post("/api/user/question/101/answer?accountId=10011")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value ="scripts/out/ResourceAnswerControllerTest/addAnswerToQuestion_ShouldFail/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value ="scripts/out/ResourceAnswerControllerTest/addAnswerToQuestion_ShouldFail/AfterTest.sql")
    void addAnswerToQuestion_ShouldFail_NotFound() throws Exception {
        AnswerRequestDto requestDto = new AnswerRequestDto(
                1L,
                1001L,
                101L,
                "test answer",
                null,
                false,
                null,
                0L,
                0L,
                "",
                "",
                0L,
                VoteTypeAnswer.UP
        );
        mockMvc.perform(post("/api/user/question/123456/answer?accountId=1001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound());
    }
}