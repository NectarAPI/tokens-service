package ke.co.nectar.tokens.service;

import ke.co.nectar.token.domain.Amount;
import ke.co.nectar.token.domain.BaseDate;
import ke.co.nectar.token.domain.TokenIdentifier;
import ke.co.nectar.token.domain.token.class0.TransferElectricityCreditToken;
import ke.co.nectar.tokens.NectarTokensServiceApplication;
import ke.co.nectar.tokens.entity.Token;
import ke.co.nectar.tokens.repository.TokensRepository;
import ke.co.nectar.tokens.service.impl.decoder.TokenDecoderManager;
import ke.co.nectar.tokens.service.impl.generate.TokenGeneratorManager;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import jakarta.transaction.Transactional;
import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NectarTokensServiceApplication.class)
@FixMethodOrder(MethodSorters.JVM)
@Transactional
public class TokensServicePrismTest {

    @Autowired
    TokensService tokensService;

    @MockBean
    private TokenDecoderManager decoderManager;

    @MockBean
    private TokenGeneratorManager generatorManager;

    @MockBean
    private TokensRepository tokensRepository;

    private final String REQUEST_ID = "2b15bfca-1212-4237-a5c4-ca4e468357ef";
    private final String USER_REF = "cee0fcf2-fc07-4107-8cff-c08037beee02";

    @Test
    public void testThatPrismTokenIsDecoded() throws Exception {

        String ref = "3ae36113-c4ca-4567-b00f-b186d75e99f4";
        String tokenNo = "12345678901234567890";
        String userRef = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        String tokenType = "Electricity_00";
        String meterNo = "12345678901";
        String requestID = "2b15bfca-1212-4237-a5c4-ca4e468357ef";
        Instant createdAt = Instant.now();
        Token mockToken = new Token(ref, tokenNo, userRef, tokenType, meterNo, requestID, createdAt);

        when(tokensRepository.findByTokenNo(anyString())).thenReturn(mockToken);

        Map<String, String> params = new HashMap<>();
        params.put("type", "prism-thrift");
        params.put("host", "197.10.10.10");
        params.put("port", "9443");
        params.put("realm", "local");
        params.put("username", "username");
        params.put("password", "password");
        params.put("decoder_reference_number", "47500150231");
        params.put("encryption_algorithm", "sta");
        params.put("token_carrier_type", "numeric");
        params.put("supply_group_code", "000046");
        params.put("key_revision_no", "1");
        params.put("key_expiry_no", "255");
        params.put("tariff_index", "01");

        String dateTime = "01/04/2002 10:10:00";
        DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);

        TransferElectricityCreditToken token = new TransferElectricityCreditToken(REQUEST_ID);
        token.setEncryptedTokenBitString(new BigInteger("12345678901234567890").toString(2));
        token.setAmountPurchased(new Amount(10));
        token.setTokenIdentifier(new TokenIdentifier(dateOfIssue, BaseDate._1993));
        token.setAmountPurchased(new Amount(10));

        when(decoderManager.decode(anyString(), anyString(), anyMap())).thenReturn(token);

        HashMap<String, Object> decodedToken = tokensService.decodeToken(REQUEST_ID, "12345678901234567890", params, USER_REF);

        Assert.assertEquals(decodedToken.get("amount"), 10.0);
    }

    @Test
    public void testThatElectricity_00TokenIsGenerated() throws Exception {

        Map<String, String> params = new HashMap<>();
        params.put("class", "0");
        params.put("subclass", "0");
        params.put("type", "prism-thrift");
        params.put("host", "197.156.230.188");
        params.put("port", "9443");
        params.put("realm", "local");
        params.put("username", "thrift_lagos");
        params.put("password", "z3WAnCHQ1");

        params.put("decoder_reference_number", "58000079523");
        params.put("encryption_algorithm", "sta");
        params.put("token_carrier_type", "numeric");
        params.put("supply_group_code", "600675");
        params.put("key_revision_no", "1");
        params.put("key_expiry_no", "255");
        params.put("tariff_index", "01");

        params.put("amount", "10");

        String ref = "3ae36113-c4ca-4567-b00f-b186d75e99f4";
        String tokenNo = "12345678901234567890";
        String userRef = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        String tokenType = "Electricity_00";
        String meterNo = "12345678901";
        String requestID = "2b15bfca-1212-4237-a5c4-ca4e468357ef";
        Instant createdAt = Instant.now();
        Token token = new Token(ref, tokenNo, userRef, tokenType, meterNo, requestID, createdAt);

        List<Token> tokens = new ArrayList<>();
        tokens.add(token);

         when(generatorManager.generate(anyString(), anyMap(), anyString())).thenReturn(tokens);

        Token electricityToken = tokensService.generateTokens(REQUEST_ID, params, USER_REF).get(0);

        assertEquals("test that token ref is returned", "3ae36113-c4ca-4567-b00f-b186d75e99f4", electricityToken.getRef());
        assertEquals("test that token no is returned", "12345678901234567890", electricityToken.getTokenNo());
        assertEquals("test that user ref is returned", "cee0fcf2-fc07-4107-8cff-c08037beee02", electricityToken.getUserRef());
        assertEquals("test that token type is returned", "Electricity_00", electricityToken.getTokenType());
        assertEquals("test that token meter no is returned", "12345678901", electricityToken.getMeterNo());
        assertEquals("test that token request ID is returned", "2b15bfca-1212-4237-a5c4-ca4e468357ef", electricityToken.getRequestID());
    }

    @Test
    public void testThatWater_01TokenIsGenerated() throws Exception {

        Map<String, String> params = new HashMap<>();
        params.put("class", "0");
        params.put("subclass", "1");
        params.put("type", "prism-thrift");
        params.put("host", "197.156.230.188");
        params.put("port", "9443");
        params.put("realm", "local");
        params.put("username", "thrift_lagos");
        params.put("password", "z3WAnCHQ1");

        params.put("decoder_reference_number", "58000079523");
        params.put("encryption_algorithm", "sta");
        params.put("token_carrier_type", "numeric");
        params.put("supply_group_code", "600675");
        params.put("key_revision_no", "1");
        params.put("key_expiry_no", "255");
        params.put("tariff_index", "01");

        params.put("amount", "10");

        String ref = "3ae36113-c4ca-4567-b00f-b186d75e99f4";
        String tokenNo = "12345678901234567890";
        String userRef = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        String tokenType = "Water_01";
        String meterNo = "12345678901";
        String requestID = "2b15bfca-1212-4237-a5c4-ca4e468357ef";
        Instant createdAt = Instant.now();
        Token token = new Token(ref, tokenNo, userRef, tokenType, meterNo, requestID, createdAt);

        List<Token> tokens = new ArrayList<>();
        tokens.add(token);

         when(generatorManager.generate(anyString(), anyMap(), anyString())).thenReturn(tokens);

        Token waterToken = tokensService.generateTokens(REQUEST_ID, params, USER_REF).get(0);

        assertEquals("test that token ref is returned", "3ae36113-c4ca-4567-b00f-b186d75e99f4", waterToken.getRef());
        assertEquals("test that token no is returned", "12345678901234567890", waterToken.getTokenNo());
        assertEquals("test that user ref is returned", "cee0fcf2-fc07-4107-8cff-c08037beee02", waterToken.getUserRef());
        assertEquals("test that token type is returned", "Water_01", waterToken.getTokenType());
        assertEquals("test that token meter no is returned", "12345678901", waterToken.getMeterNo());
        assertEquals("test that token request ID is returned", "2b15bfca-1212-4237-a5c4-ca4e468357ef", waterToken.getRequestID());
    }

    @Test
    public void testThatGas_02TokenIsGenerated() throws Exception {

        Map<String, String> params = new HashMap<>();
        params.put("class", "0");
        params.put("subclass", "2");
        params.put("type", "prism-thrift");
        params.put("host", "197.156.230.188");
        params.put("port", "9443");
        params.put("realm", "local");
        params.put("username", "thrift_lagos");
        params.put("password", "z3WAnCHQ1");

        params.put("decoder_reference_number", "58000079523");
        params.put("encryption_algorithm", "sta");
        params.put("token_carrier_type", "numeric");
        params.put("supply_group_code", "600675");
        params.put("key_revision_no", "1");
        params.put("key_expiry_no", "255");
        params.put("tariff_index", "01");

        params.put("amount", "10");

        String ref = "3ae36113-c4ca-4567-b00f-b186d75e99f4";
        String tokenNo = "12345678901234567890";
        String userRef = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        String tokenType = "Gas_02";
        String meterNo = "12345678901";
        String requestID = "2b15bfca-1212-4237-a5c4-ca4e468357ef";
        Instant createdAt = Instant.now();
        Token token = new Token(ref, tokenNo, userRef, tokenType, meterNo, requestID, createdAt);

        List<Token> tokens = new ArrayList<>();
        tokens.add(token);

         when(generatorManager.generate(anyString(), anyMap(), anyString())).thenReturn(tokens);

        Token gasToken = tokensService.generateTokens(REQUEST_ID, params, USER_REF).get(0);

        assertEquals("test that token ref is returned", "3ae36113-c4ca-4567-b00f-b186d75e99f4", gasToken.getRef());
        assertEquals("test that token no is returned", "12345678901234567890", gasToken.getTokenNo());
        assertEquals("test that user ref is returned", "cee0fcf2-fc07-4107-8cff-c08037beee02", gasToken.getUserRef());
        assertEquals("test that token type is returned", "Gas_02", gasToken.getTokenType());
        assertEquals("test that token meter no is returned", "12345678901", gasToken.getMeterNo());
        assertEquals("test that token request ID is returned", "2b15bfca-1212-4237-a5c4-ca4e468357ef", gasToken.getRequestID());
    }

    @Test
    public void testThatInitiateMeterTestDisplay_10TokenIsGenerated() throws Exception {

        Map<String, String> params = new HashMap<>();
        params.put("class", "1");
        params.put("subclass", "0");
        params.put("type", "prism-thrift");
        params.put("host", "197.156.230.188");
        params.put("port", "9443");
        params.put("realm", "local");
        params.put("username", "thrift_lagos");
        params.put("password", "z3WAnCHQ1");

        params.put("decoder_reference_number", "58000079523");

        params.put("control", "00");
        params.put("manufacturer_code", "58");

        String ref = "3ae36113-c4ca-4567-b00f-b186d75e99f4";
        String tokenNo = "12345678901234567890";
        String userRef = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        String tokenType = "InitiateMeterTestOrDisplay1_10";
        String meterNo = "12345678901";
        String requestID = "2b15bfca-1212-4237-a5c4-ca4e468357ef";
        Instant createdAt = Instant.now();
        Token token = new Token(ref, tokenNo, userRef, tokenType, meterNo, requestID, createdAt);

        List<Token> tokens = new ArrayList<>();
        tokens.add(token);

        when(generatorManager.generate(anyString(), anyMap(), anyString())).thenReturn(tokens);

        Token initiateMeterTestDisplayToken = tokensService.generateTokens(REQUEST_ID, params, USER_REF).get(0);

        assertEquals("test that token ref is returned", "3ae36113-c4ca-4567-b00f-b186d75e99f4", initiateMeterTestDisplayToken.getRef());
        assertEquals("test that token no is returned", "12345678901234567890", initiateMeterTestDisplayToken.getTokenNo());
        assertEquals("test that user ref is returned", "cee0fcf2-fc07-4107-8cff-c08037beee02", initiateMeterTestDisplayToken.getUserRef());
        assertEquals("test that token type is returned", "InitiateMeterTestOrDisplay1_10", initiateMeterTestDisplayToken.getTokenType());
        assertEquals("test that token meter no is returned", "12345678901", initiateMeterTestDisplayToken.getMeterNo());
        assertEquals("test that token request ID is returned", "2b15bfca-1212-4237-a5c4-ca4e468357ef", initiateMeterTestDisplayToken.getRequestID());
    }

    @Test
    public void testThatInitiateMeterTestDisplay_11TokenIsGenerated() throws Exception {

        Map<String, String> params = new HashMap<>();
        params.put("class", "1");
        params.put("subclass", "1");
        params.put("type", "prism-thrift");
        params.put("host", "197.156.230.188");
        params.put("port", "9443");
        params.put("realm", "local");
        params.put("username", "thrift_lagos");
        params.put("password", "z3WAnCHQ1");

        params.put("decoder_reference_number", "58000079523");

        params.put("control", "0000");
        params.put("manufacturer_code", "5812");

        String ref = "3ae36113-c4ca-4567-b00f-b186d75e99f4";
        String tokenNo = "12345678901234567890";
        String userRef = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        String tokenType = "InitiateMeterTestOrDisplay1_11";
        String meterNo = "12345678901";
        String requestID = "2b15bfca-1212-4237-a5c4-ca4e468357ef";
        Instant createdAt = Instant.now();
        Token token = new Token(ref, tokenNo, userRef, tokenType, meterNo, requestID, createdAt);

        List<Token> tokens = new ArrayList<>();
        tokens.add(token);

        when(generatorManager.generate(anyString(), anyMap(), anyString())).thenReturn(tokens);

        Token initiateMeterTestDisplayToken = tokensService.generateTokens(REQUEST_ID, params, USER_REF).get(0);

        assertEquals("test that token ref is returned", "3ae36113-c4ca-4567-b00f-b186d75e99f4", initiateMeterTestDisplayToken.getRef());
        assertEquals("test that token no is returned", "12345678901234567890", initiateMeterTestDisplayToken.getTokenNo());
        assertEquals("test that user ref is returned", "cee0fcf2-fc07-4107-8cff-c08037beee02", initiateMeterTestDisplayToken.getUserRef());
        assertEquals("test that token type is returned", "InitiateMeterTestOrDisplay1_11", initiateMeterTestDisplayToken.getTokenType());
        assertEquals("test that token meter no is returned", "12345678901", initiateMeterTestDisplayToken.getMeterNo());
        assertEquals("test that token request ID is returned", "2b15bfca-1212-4237-a5c4-ca4e468357ef", initiateMeterTestDisplayToken.getRequestID());
    }

    @Test
    public void testThatSetMaximumPowerLimit_20TokenIsGenerated() throws Exception {

        Map<String, String> params = new HashMap<>();
        params.put("class", "2");
        params.put("subclass", "0");
        params.put("type", "prism-thrift");
        params.put("host", "197.156.230.188");
        params.put("port", "9443");
        params.put("realm", "local");
        params.put("username", "thrift_lagos");
        params.put("password", "z3WAnCHQ1");

        params.put("decoder_reference_number", "58000079523");
        params.put("encryption_algorithm", "sta");
        params.put("token_carrier_type", "numeric");
        params.put("supply_group_code", "600675");
        params.put("key_revision_no", "1");
        params.put("key_expiry_no", "255");
        params.put("tariff_index", "01");

        params.put("maximum_power_limit", "10");
        params.put("flag_token_type", "10");
        params.put("flag_token_value", "0");

        String ref = "3ae36113-c4ca-4567-b00f-b186d75e99f4";
        String tokenNo = "12345678901234567890";
        String userRef = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        String tokenType = "SetMaximumPowerLimit_20";
        String meterNo = "12345678901";
        String requestID = "2b15bfca-1212-4237-a5c4-ca4e468357ef";
        Instant createdAt = Instant.now();
        Token token = new Token(ref, tokenNo, userRef, tokenType, meterNo, requestID, createdAt);

        List<Token> tokens = new ArrayList<>();
        tokens.add(token);

        when(generatorManager.generate(anyString(), anyMap(), anyString())).thenReturn(tokens);

        Token setMaximumPowerLimitToken = tokensService.generateTokens(REQUEST_ID, params, USER_REF).get(0);

        assertEquals("test that token ref is returned", "3ae36113-c4ca-4567-b00f-b186d75e99f4", setMaximumPowerLimitToken.getRef());
        assertEquals("test that token no is returned", "12345678901234567890", setMaximumPowerLimitToken.getTokenNo());
        assertEquals("test that user ref is returned", "cee0fcf2-fc07-4107-8cff-c08037beee02", setMaximumPowerLimitToken.getUserRef());
        assertEquals("test that token type is returned", "SetMaximumPowerLimit_20", setMaximumPowerLimitToken.getTokenType());
        assertEquals("test that token meter no is returned", "12345678901", setMaximumPowerLimitToken.getMeterNo());
        assertEquals("test that token request ID is returned", "2b15bfca-1212-4237-a5c4-ca4e468357ef", setMaximumPowerLimitToken.getRequestID());
    }

    @Test
    public void testThatClearCredit_21TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "2");
        params.put("subclass", "1");
        params.put("type", "prism-thrift");
        params.put("host", "197.156.230.188");
        params.put("port", "9443");
        params.put("realm", "local");
        params.put("username", "thrift_lagos");
        params.put("password", "z3WAnCHQ1");

        params.put("decoder_reference_number", "58000079523");
        params.put("encryption_algorithm", "sta");
        params.put("token_carrier_type", "numeric");
        params.put("supply_group_code", "600675");
        params.put("key_revision_no", "1");
        params.put("key_expiry_no", "255");
        params.put("tariff_index", "01");

        String ref = "3ae36113-c4ca-4567-b00f-b186d75e99f4";
        String tokenNo = "12345678901234567890";
        String userRef = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        String tokenType = "ClearCredit_21";
        String meterNo = "12345678901";
        String requestID = "2b15bfca-1212-4237-a5c4-ca4e468357ef";
        Instant createdAt = Instant.now();
        Token token = new Token(ref, tokenNo, userRef, tokenType, meterNo, requestID, createdAt);

        List<Token> tokens = new ArrayList<>();
        tokens.add(token);

        when(generatorManager.generate(anyString(), anyMap(), anyString())).thenReturn(tokens);

        Token clearCreditToken = tokensService.generateTokens(REQUEST_ID, params, USER_REF).get(0);

        assertEquals("test that token ref is returned", "3ae36113-c4ca-4567-b00f-b186d75e99f4", clearCreditToken.getRef());
        assertEquals("test that token no is returned", "12345678901234567890", clearCreditToken.getTokenNo());
        assertEquals("test that user ref is returned", "cee0fcf2-fc07-4107-8cff-c08037beee02", clearCreditToken.getUserRef());
        assertEquals("test that token type is returned", "ClearCredit_21", clearCreditToken.getTokenType());
        assertEquals("test that token meter no is returned", "12345678901", clearCreditToken.getMeterNo());
        assertEquals("test that token request ID is returned", "2b15bfca-1212-4237-a5c4-ca4e468357ef", clearCreditToken.getRequestID());
    }

    @Test
    public void testThatSetTariffRate_22TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "2");
        params.put("subclass", "2");
        params.put("type", "prism-thrift");
        params.put("host", "197.156.230.188");
        params.put("port", "9443");
        params.put("realm", "local");
        params.put("username", "thrift_lagos");
        params.put("password", "z3WAnCHQ1");

        params.put("decoder_reference_number", "58000079523");
        params.put("encryption_algorithm", "sta");
        params.put("token_carrier_type", "numeric");
        params.put("supply_group_code", "600675");
        params.put("key_revision_no", "1");
        params.put("key_expiry_no", "255");
        params.put("tariff_index", "01");

        String ref = "3ae36113-c4ca-4567-b00f-b186d75e99f4";
        String tokenNo = "12345678901234567890";
        String userRef = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        String tokenType = "SetTariffRate_22";
        String meterNo = "12345678901";
        String requestID = "2b15bfca-1212-4237-a5c4-ca4e468357ef";
        Instant createdAt = Instant.now();
        Token token = new Token(ref, tokenNo, userRef, tokenType, meterNo, requestID, createdAt);

        List<Token> tokens = new ArrayList<>();
        tokens.add(token);

        when(generatorManager.generate(anyString(), anyMap(), anyString())).thenReturn(tokens);

        Token setTariffRateToken = tokensService.generateTokens(REQUEST_ID, params, USER_REF).get(0);

        assertEquals("test that token ref is returned", "3ae36113-c4ca-4567-b00f-b186d75e99f4", setTariffRateToken.getRef());
        assertEquals("test that token no is returned", "12345678901234567890", setTariffRateToken.getTokenNo());
        assertEquals("test that user ref is returned", "cee0fcf2-fc07-4107-8cff-c08037beee02", setTariffRateToken.getUserRef());
        assertEquals("test that token type is returned", "SetTariffRate_22", setTariffRateToken.getTokenType());
        assertEquals("test that token meter no is returned", "12345678901", setTariffRateToken.getMeterNo());
        assertEquals("test that token request ID is returned", "2b15bfca-1212-4237-a5c4-ca4e468357ef", setTariffRateToken.getRequestID());
    }

    @Test
    public void testThatDecoderKCTs_23TokensAreGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "2");
        params.put("subclass", "3");
        params.put("type", "prism-thrift");
        params.put("host", "197.156.230.188");
        params.put("port", "9443");
        params.put("realm", "local");
        params.put("username", "thrift_lagos");
        params.put("password", "z3WAnCHQ1");

        params.put("decoder_reference_number", "58000079523");
        params.put("encryption_algorithm", "sta");
        params.put("token_carrier_type", "numeric");
        params.put("supply_group_code", "600675");
        params.put("key_revision_no", "1");
        params.put("key_expiry_no", "255");
        params.put("tariff_index", "01");

        params.put("allow_3Kct", "false");

        params.put("new_supply_group_code", "600675");
        params.put("new_key_revision_no", "1");
        params.put("new_tariff_index", "01");

        String ref = "3ae36113-c4ca-4567-b00f-b186d75e99f4";
        String tokenNo = "12345678901234567890";
        String userRef = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        String tokenType = "Set1stSectionDecoderKey_23";
        String meterNo = "12345678901";
        String requestID = "2b15bfca-1212-4237-a5c4-ca4e468357ef";
        Instant createdAt = Instant.now();
        Token token = new Token(ref, tokenNo, userRef, tokenType, meterNo, requestID, createdAt);

        List<Token> tokens = new ArrayList<>();
        tokens.add(token);

        when(generatorManager.generate(anyString(), anyMap(), anyString())).thenReturn(tokens);

        Token decoderKCTs = tokensService.generateTokens(REQUEST_ID, params, USER_REF).get(0);

        assertEquals("test that token ref is returned", "3ae36113-c4ca-4567-b00f-b186d75e99f4", decoderKCTs.getRef());
        assertEquals("test that token no is returned", "12345678901234567890", decoderKCTs.getTokenNo());
        assertEquals("test that user ref is returned", "cee0fcf2-fc07-4107-8cff-c08037beee02", decoderKCTs.getUserRef());
        assertEquals("test that token type is returned", "Set1stSectionDecoderKey_23", decoderKCTs.getTokenType());
        assertEquals("test that token meter no is returned", "12345678901", decoderKCTs.getMeterNo());
        assertEquals("test that token request ID is returned", "2b15bfca-1212-4237-a5c4-ca4e468357ef", decoderKCTs.getRequestID());
    }

    @Test
    public void testThatDecoderKCTs_24TokensAreGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "2");
        params.put("subclass", "4");
        params.put("type", "prism-thrift");
        params.put("host", "197.156.230.188");
        params.put("port", "9443");
        params.put("realm", "local");
        params.put("username", "thrift_lagos");
        params.put("password", "z3WAnCHQ1");

        params.put("decoder_reference_number", "58000079523");
        params.put("encryption_algorithm", "sta");
        params.put("token_carrier_type", "numeric");
        params.put("supply_group_code", "600675");
        params.put("key_revision_no", "1");
        params.put("key_expiry_no", "255");
        params.put("tariff_index", "01");

        params.put("allow_3Kct", "false");

        params.put("new_supply_group_code", "600675");
        params.put("new_key_revision_no", "1");
        params.put("new_tariff_index", "01");

        String ref = "3ae36113-c4ca-4567-b00f-b186d75e99f4";
        String tokenNo = "12345678901234567890";
        String userRef = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        String tokenType = "Set2ndSectionDecoderKey_24";
        String meterNo = "12345678901";
        String requestID = "2b15bfca-1212-4237-a5c4-ca4e468357ef";
        Instant createdAt = Instant.now();
        Token token = new Token(ref, tokenNo, userRef, tokenType, meterNo, requestID, createdAt);

        List<Token> tokens = new ArrayList<>();
        tokens.add(token);
        tokens.add(token);

        when(generatorManager.generate(anyString(), anyMap(), anyString())).thenReturn(tokens);

        Token decoderKCTs = tokensService.generateTokens(REQUEST_ID, params, USER_REF).get(1);

        assertEquals("test that token ref is returned", "3ae36113-c4ca-4567-b00f-b186d75e99f4", decoderKCTs.getRef());
        assertEquals("test that token no is returned", "12345678901234567890", decoderKCTs.getTokenNo());
        assertEquals("test that user ref is returned", "cee0fcf2-fc07-4107-8cff-c08037beee02", decoderKCTs.getUserRef());
        assertEquals("test that token type is returned", "Set2ndSectionDecoderKey_24", decoderKCTs.getTokenType());
        assertEquals("test that token meter no is returned", "12345678901", decoderKCTs.getMeterNo());
        assertEquals("test that token request ID is returned", "2b15bfca-1212-4237-a5c4-ca4e468357ef", decoderKCTs.getRequestID());
    }

    @Test
    public void testThatClearTamperCondition_25TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "2");
        params.put("subclass", "5");
        params.put("type", "prism-thrift");
        params.put("host", "197.156.230.188");
        params.put("port", "9443");
        params.put("realm", "local");
        params.put("username", "thrift_lagos");
        params.put("password", "z3WAnCHQ1");

        params.put("decoder_reference_number", "58000079523");
        params.put("encryption_algorithm", "sta");
        params.put("token_carrier_type", "numeric");
        params.put("supply_group_code", "600675");
        params.put("key_revision_no", "1");
        params.put("key_expiry_no", "255");
        params.put("tariff_index", "01");

        String ref = "3ae36113-c4ca-4567-b00f-b186d75e99f4";
        String tokenNo = "12345678901234567890";
        String userRef = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        String tokenType = "ClearTamperCondition_25";
        String meterNo = "12345678901";
        String requestID = "2b15bfca-1212-4237-a5c4-ca4e468357ef";
        Instant createdAt = Instant.now();
        Token token = new Token(ref, tokenNo, userRef, tokenType, meterNo, requestID, createdAt);

        List<Token> tokens = new ArrayList<>();
        tokens.add(token);

        when(generatorManager.generate(anyString(), anyMap(), anyString())).thenReturn(tokens);

        Token clearTamperConditionToken = tokensService.generateTokens(REQUEST_ID, params, USER_REF).get(0);

        assertEquals("test that token ref is returned", "3ae36113-c4ca-4567-b00f-b186d75e99f4", clearTamperConditionToken.getRef());
        assertEquals("test that token no is returned", "12345678901234567890", clearTamperConditionToken.getTokenNo());
        assertEquals("test that user ref is returned", "cee0fcf2-fc07-4107-8cff-c08037beee02", clearTamperConditionToken.getUserRef());
        assertEquals("test that token type is returned", "ClearTamperCondition_25", clearTamperConditionToken.getTokenType());
        assertEquals("test that token meter no is returned", "12345678901", clearTamperConditionToken.getMeterNo());
        assertEquals("test that token request ID is returned", "2b15bfca-1212-4237-a5c4-ca4e468357ef", clearTamperConditionToken.getRequestID());
    }

    @Test
    public void testThatSetMaximumPhasePowerUnbalanceLimit_26TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "2");
        params.put("subclass", "6");
        params.put("type", "prism-thrift");
        params.put("host", "197.156.230.188");
        params.put("port", "9443");
        params.put("realm", "local");
        params.put("username", "thrift_lagos");
        params.put("password", "z3WAnCHQ1");

        params.put("decoder_reference_number", "58000079523");
        params.put("encryption_algorithm", "sta");
        params.put("token_carrier_type", "numeric");
        params.put("supply_group_code", "600675");
        params.put("key_revision_no", "1");
        params.put("key_expiry_no", "255");
        params.put("tariff_index", "01");

        String ref = "3ae36113-c4ca-4567-b00f-b186d75e99f4";
        String tokenNo = "12345678901234567890";
        String userRef = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        String tokenType = "SetMaximumPhasePowerUnbalanceLimit_26";
        String meterNo = "12345678901";
        String requestID = "2b15bfca-1212-4237-a5c4-ca4e468357ef";
        Instant createdAt = Instant.now();
        Token token = new Token(ref, tokenNo, userRef, tokenType, meterNo, requestID, createdAt);

        List<Token> tokens = new ArrayList<>();
        tokens.add(token);

        when(generatorManager.generate(anyString(), anyMap(), anyString())).thenReturn(tokens);

        Token setMaximumPhasePowerUnbalanceLimitToken = tokensService.generateTokens(REQUEST_ID, params, USER_REF).get(0);

        assertEquals("test that token ref is returned", "3ae36113-c4ca-4567-b00f-b186d75e99f4", setMaximumPhasePowerUnbalanceLimitToken.getRef());
        assertEquals("test that token no is returned", "12345678901234567890", setMaximumPhasePowerUnbalanceLimitToken.getTokenNo());
        assertEquals("test that user ref is returned", "cee0fcf2-fc07-4107-8cff-c08037beee02", setMaximumPhasePowerUnbalanceLimitToken.getUserRef());
        assertEquals("test that token type is returned", "SetMaximumPhasePowerUnbalanceLimit_26", setMaximumPhasePowerUnbalanceLimitToken.getTokenType());
        assertEquals("test that token meter no is returned", "12345678901", setMaximumPhasePowerUnbalanceLimitToken.getMeterNo());
        assertEquals("test that token request ID is returned", "2b15bfca-1212-4237-a5c4-ca4e468357ef", setMaximumPhasePowerUnbalanceLimitToken.getRequestID());
    }

    @Test
    public void testThatSetWaterMeterFactor_27TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "2");
        params.put("subclass", "7");
        params.put("type", "prism-thrift");
        params.put("host", "197.156.230.188");
        params.put("port", "9443");
        params.put("realm", "local");
        params.put("username", "thrift_lagos");
        params.put("password", "z3WAnCHQ1");

        params.put("decoder_reference_number", "58000079523");
        params.put("encryption_algorithm", "sta");
        params.put("token_carrier_type", "numeric");
        params.put("supply_group_code", "600675");
        params.put("key_revision_no", "1");
        params.put("key_expiry_no", "255");
        params.put("tariff_index", "01");
        
        String ref = "3ae36113-c4ca-4567-b00f-b186d75e99f4";
        String tokenNo = "12345678901234567890";
        String userRef = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        String tokenType = "SetWaterMeterFactor_27";
        String meterNo = "12345678901";
        String requestID = "2b15bfca-1212-4237-a5c4-ca4e468357ef";
        Instant createdAt = Instant.now();
        Token token = new Token(ref, tokenNo, userRef, tokenType, meterNo, requestID, createdAt);

        List<Token> tokens = new ArrayList<>();
        tokens.add(token);

        when(generatorManager.generate(anyString(), anyMap(), anyString())).thenReturn(tokens);

        Token setWaterMeterFactorToken = tokensService.generateTokens(REQUEST_ID, params, USER_REF).get(0);

        assertEquals("test that token ref is returned", "3ae36113-c4ca-4567-b00f-b186d75e99f4", setWaterMeterFactorToken.getRef());
        assertEquals("test that token no is returned", "12345678901234567890", setWaterMeterFactorToken.getTokenNo());
        assertEquals("test that user ref is returned", "cee0fcf2-fc07-4107-8cff-c08037beee02", setWaterMeterFactorToken.getUserRef());
        assertEquals("test that token type is returned", "SetWaterMeterFactor_27", setWaterMeterFactorToken.getTokenType());
        assertEquals("test that token meter no is returned", "12345678901", setWaterMeterFactorToken.getMeterNo());
        assertEquals("test that token request ID is returned", "2b15bfca-1212-4237-a5c4-ca4e468357ef", setWaterMeterFactorToken.getRequestID());
    }

}
