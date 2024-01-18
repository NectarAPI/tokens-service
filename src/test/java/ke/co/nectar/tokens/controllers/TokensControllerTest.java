package ke.co.nectar.tokens.controllers;

import ke.co.nectar.tokens.NectarTokensServiceApplication;
import ke.co.nectar.tokens.annotation.NotificationProcessor;
import ke.co.nectar.tokens.entity.Token;
import ke.co.nectar.tokens.repository.TokensRepository;
import ke.co.nectar.tokens.service.TokensService;
import ke.co.nectar.tokens.service.impl.TimelineRequest;
import ke.co.nectar.tokens.service.impl.validate.TokenTypeCount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NectarTokensServiceApplication.class)
@AutoConfigureMockMvc
public class TokensControllerTest {

    @InjectMocks
    private TokensController tokens;

    @MockBean
    private TokensService tokensService;

    @MockBean
    private TokensRepository tokensRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationProcessor notificationProcessor;

    private final long EPOCH_TIME = 1606754076302l;

    private Token token;

    @Test
    public void contextLoads() {
        Assert.assertNotNull(tokens);
    }

    @Before
    public void setup() throws Exception {
        final String REF = "3ae36113-c4ca-4567-b00f-b186d75e99f4";
        final String TOKEN = "12345678901234567890";
        final String USER_REF = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        final String TOKEN_TYPE = "Electricity_00";
        final String METER_NO = "12345678901";
        final String REQUEST_ID = "2b15bfca-1212-4237-a5c4-ca4e468357ef";

        token = new Token(REF,
                            TOKEN,
                            USER_REF,
                            TOKEN_TYPE,
                            METER_NO,
                            REQUEST_ID,
                            Instant.ofEpochMilli(EPOCH_TIME));
        tokensRepository.save(token);
    }

    @Test
    public void testThatTokenByRefIsObtained() throws Exception {
        when(tokensService.getToken(anyString())).thenReturn(token);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/tokens?ref=ref")
                .param("request_id", "requestid")
                .with(httpBasic("tokens_service", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{'status':{'code':200,'message':'Successfully obtained token details','request_id':'requestid'},'data':{'token':{'ref':'3ae36113-c4ca-4567-b00f-b186d75e99f4','token_no':'12345678901234567890','user_ref':'cee0fcf2-fc07-4107-8cff-c08037beee02','token_type':'Electricity_00','meter_no':'12345678901','created_at':'2020-11-30T16:34:36.302Z'}}}"));
    }

    @Test
    public void testThatTokensByUserRefIsObtained() throws Throwable {

        List<Token> generatedTokens = new ArrayList<>();
        generatedTokens.add(token);

        when(notificationProcessor.process(any())).thenReturn(true);
        when(tokensService.getTokens(anyString())).thenReturn(generatedTokens);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/tokens?user=ref")
                .param("request_id", "requestid")
                .param("user_ref", "userref")
                .with(httpBasic("tokens_service", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{'status':{'code':200,'message':'Successfully obtained token details','request_id':'requestid'},'data':{'tokens':[{'ref':'3ae36113-c4ca-4567-b00f-b186d75e99f4','token_no':'12345678901234567890','user_ref':'cee0fcf2-fc07-4107-8cff-c08037beee02','token_type':'Electricity_00','meter_no':'12345678901','created_at':'2020-11-30T16:34:36.302Z'}]}}"));
    }

    @Test
    public void testThatTokensTimelineRequestsAreReturned() throws Exception {
        TimelineRequest firstRequest = new TimelineRequest() {
            @Override
            public int getMonth() {
                return 1;
            }

            @Override
            public int getYear() {
                return 2021;
            }

            @Override
            public int getTokens() {
                return 10;
            }
        };

        TimelineRequest secondRequest = new TimelineRequest() {
            @Override
            public int getMonth() {
                return 2;
            }

            @Override
            public int getYear() {
                return 2021;
            }

            @Override
            public int getTokens() {
                return 11;
            }
        };

        List<TimelineRequest> requests = new ArrayList<>();
        requests.add(firstRequest);
        requests.add(secondRequest);

        when(tokensService.getTimelineRequests(anyString(), anyInt())).thenReturn(requests);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/tokens?user_ref=ref")
                .param("request_id", "requestid")
                .param("months",  "10")
                .with(httpBasic("tokens_service", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{'status':{'code':200,'message':'Successfully obtained user token details','request_id':'requestid'},'data':{'requests':[{'year':2021,'month':1,'tokens':10},{'year':2021,'month':2,'tokens':11}]}}"));
    }

    @Test
    public void testThatGeneratedTokensParamIsReturned() throws Throwable {

        when(notificationProcessor.process(any())).thenReturn(true);
        when(tokensService.getGeneratedNoOfTokens(anyString())).thenReturn(10);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/tokens?user_ref=ref")
                .param("request_id", "requestid")
                .param("param",  "generated-tokens")
                .with(httpBasic("tokens_service", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{'status':{'code':200,'message':'Successfully obtained user token details','request_id':'requestid'},'data':{'result':10}}"));
    }

    @Test
    public void testThatTokensTypesParamIsReturned() throws Throwable {

        when(notificationProcessor.process(any())).thenReturn(true);
        when(tokensService.getNoOfTokenTypes(anyString())).thenReturn(13);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/tokens?user_ref=ref")
                .param("request_id", "requestid")
                .param("param",  "token-types")
                .with(httpBasic("tokens_service", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{'status':{'code':200,'message':'Successfully obtained user token details','request_id':'requestid'},'data':{'result':13}}"));
    }

    @Test
    public void testThatTokenTypesParamDetailsAreReturned() throws Exception {

        TokenTypeCount tokenTypeCount = new TokenTypeCount() {
            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public String getType() {
                return "Electricity_00";
            }
        };

        List<TokenTypeCount> details = new ArrayList<>();
        details.add(tokenTypeCount);

        when(tokensService.getTokenTypes(anyString())).thenReturn(details);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/tokens?user_ref=ref")
                .param("request_id", "requestid")
                .param("detailed_param",  "token-types")
                .with(httpBasic("tokens_service", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{'status':{'code':200,'message':'Successfully obtained user token details','request_id':'requestid'},'data':{'result':[{'count':10,'type':'Electricity_00'}]}}"));
    }

    @Test
    public void testThatUniqueMetersParamIsReturned() throws Exception {

        when(tokensService.getUniqueMetersNo(anyString())).thenReturn(3);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/tokens?user_ref=ref")
                .param("request_id", "requestid")
                .param("param",  "unique-meters")
                .with(httpBasic("tokens_service", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{'status':{'code':200,'message':'Successfully obtained user token details','request_id':'requestid'},'data':{'result':3}}"));
    }

    @Test
    public void testThatGeneratedTokenIsReturned() throws Throwable {

        String configStr = "{\"param\":\"param\"}";

        List<Token> generatedTokens = new ArrayList<>();
        generatedTokens.add(token);

        when(notificationProcessor.process(any())).thenReturn(true);
        when(tokensService.generateTokens(anyString(), anyMap(), anyString())).thenReturn(generatedTokens);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/tokens")
                .param("user_ref", "user-ref")
                .param("request_id", "requestid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(configStr)
                .with(httpBasic("tokens_service", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{'status':{'code':200,'message':'Successfully generated token','request_id':'requestid'},'data':{'tokens':[{'ref':'3ae36113-c4ca-4567-b00f-b186d75e99f4','token_no':'12345678901234567890','user_ref':'cee0fcf2-fc07-4107-8cff-c08037beee02','token_type':'Electricity_00','meter_no':'12345678901','request_id':'2b15bfca-1212-4237-a5c4-ca4e468357ef', 'created_at':'2020-11-30T16:34:36.302Z'}]}}"))
                .andReturn();
    }

    @Test
    public void testThatTokenIsDecoded() throws Throwable {
        String configStr = "{\"param\":\"param\"}";

        HashMap<String, Object> params = new HashMap<>();
        params.put("class", 0);
        params.put("subclass", 0);
        params.put("token_identifier", "2023-05-12T11:43:00.000+03:00");
        params.put("amount", 7);
        params.put("type", "Electricity_00");
        params.put("crc", 35942);
        params.put("ref", "566d7f63-2337-4fa7-ad17-ed8251fe8201");
        params.put("user_ref", "f6080ca3-2b75-4beb-97d6-72f98082bdf5");
        params.put("created_at", "2023-05-12T08:44:02.994408Z");

        when(notificationProcessor.process(any())).thenReturn(true);
        when(tokensService.decodeToken(anyString(), anyString(),
                anyMap(), anyString())).thenReturn(params);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/tokens/12345678901234567890")
                .param("request_id", "requestid")
                .param("user_ref", "userref")
                .contentType(MediaType.APPLICATION_JSON)
                .content(configStr)
                .with(httpBasic("tokens_service", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{'status':{'code':200,'message':'Successfully decoded token','request_id':'requestid'},'data':{'token_details':{'amount':7,'ref':'566d7f63-2337-4fa7-ad17-ed8251fe8201','token_identifier':'2023-05-12T11:43:00.000+03:00','crc':35942,'subclass':0,'created_at':'2023-05-12T08:44:02.994408Z','type':'Electricity_00','class':0,'user_ref':'f6080ca3-2b75-4beb-97d6-72f98082bdf5'}}}"))
                .andReturn();
    }

    @Test
    public void testThatTokenIsDeleted() throws Throwable {

        when(notificationProcessor.process(any())).thenReturn(true);
        doNothing().when(tokensService).deleteToken(anyString(), anyString());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/tokens?ref=ref")
                .param("request_id", "requestid")
                .param("user_ref", "userref")
                .with(httpBasic("tokens_service", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{'status':{'code':200,'message':'Successfully deleted token','request_id':'requestid'}}"));
    }

    @Test
    public void testThatTokenIsDeletedByTokenNo() throws Throwable {

        when(notificationProcessor.process(any())).thenReturn(true);
        doNothing().when(tokensService).deleteTokenByTokenNo(anyString(), anyString());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/tokens?token=12345678901234567890")
                .param("request_id", "requestid")
                .param("user_ref", "userref")
                .with(httpBasic("tokens_service", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("{'status':{'code':200,'message':'Successfully deleted token by token no','request_id':'requestid'}}"));
    }
}
