package stackover.resource.service.rest.out;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import stackover.resource.service.ResourceServiceApplicationTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourceQuestionControllerTest extends ResourceServiceApplicationTests {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "scripts/out/ResourceQuestionControllerTest/addDownVoteToQuestion/shouldPass/noReputationNoVoteQuestion/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "scripts/out/ResourceQuestionControllerTest/addDownVoteToQuestion/AfterTest.sql")
    void addDownVoteToQuestionPassedNoReputationNoVoteQuestion() throws Exception {
        mockMvc.perform(post("/api/user/question/101/downVote?accountId=101"))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "scripts/out/ResourceQuestionControllerTest/addDownVoteToQuestion/shouldPass/noVoteQuestion/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "scripts/out/ResourceQuestionControllerTest/addDownVoteToQuestion/AfterTest.sql")
    void addDownVoteToQuestionPassedNoVoteQuestion() throws Exception {
        mockMvc.perform(post("/api/user/question/101/downVote?accountId=101"))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "scripts/out/ResourceQuestionControllerTest/addDownVoteToQuestion/shouldPass/noReputation/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "scripts/out/ResourceQuestionControllerTest/addDownVoteToQuestion/AfterTest.sql")
    void addDownVoteToQuestionPassedNoReputation() throws Exception {
        mockMvc.perform(post("/api/user/question/101/downVote?accountId=101"))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "scripts/out/ResourceQuestionControllerTest/addDownVoteToQuestion/shouldPass/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "scripts/out/ResourceQuestionControllerTest/addDownVoteToQuestion/AfterTest.sql")
    void addDownVoteQuestionPassed() throws Exception {
        mockMvc.perform(post("/api/user/question/101/downVote?accountId=101"))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "scripts/out/ResourceQuestionControllerTest/addDownVoteToQuestion/shouldFail/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "scripts/out/ResourceQuestionControllerTest/addDownVoteToQuestion/AfterTest.sql")
    void addDownVoteQuestionFailedNoUser() throws Exception {
        mockMvc.perform(post("/api/user/question/101/downVote?accountId=103"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "scripts/out/ResourceQuestionControllerTest/addDownVoteToQuestion/shouldFail/BeforeTest.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "scripts/out/ResourceQuestionControllerTest/addDownVoteToQuestion/AfterTest.sql")
    void addDownVoteQuestionFailedNoQuestion() throws Exception {
        mockMvc.perform(post("/api/user/question/103/downVote?accountId=101"))
                .andExpect(status().isNotFound());
    }
}
