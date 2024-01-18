package ke.co.nectar.tokens.service;

import ke.co.nectar.tokens.NectarTokensServiceApplication;
import ke.co.nectar.tokens.entity.Token;
import ke.co.nectar.tokens.repository.TokensRepository;
import ke.co.nectar.tokens.service.impl.TimelineRequest;
import ke.co.nectar.tokens.service.impl.exceptions.InvalidTokenRefException;
import ke.co.nectar.tokens.service.impl.validate.TokenTypeCount;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NectarTokensServiceApplication.class)
@FixMethodOrder(MethodSorters.JVM)
@Transactional
public class TokensServiceTest {

    @Autowired
    TokensService tokensService;

    @Autowired
    TokensRepository tokensRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final long EPOCH_TIME = 1606754076302l;
    private final String REF = "3ae36113-c4ca-4567-b00f-b186d75e99f4";
    private final String TOKEN = "12345678901234567890";
    private final String USER_REF = "cee0fcf2-fc07-4107-8cff-c08037beee02";
    private final String TOKEN_TYPE = "Electricity_00";
    private final String METER_NO = "12345678901";
    private final String REQUEST_ID = "2b15bfca-1212-4237-a5c4-ca4e468357ef";

    @Test
    public void testThatCanGetTokenByRef() throws Exception {
        Token token = new Token(REF,
                                TOKEN,
                                USER_REF,
                                TOKEN_TYPE,
                                METER_NO,
                                REQUEST_ID,
                                Instant.ofEpochMilli(EPOCH_TIME));
        tokensRepository.save(token);

        Token obtainedToken = tokensService.getToken("3ae36113-c4ca-4567-b00f-b186d75e99f4");
        Assert.assertEquals("12345678901234567890", obtainedToken.getTokenNo());
        Assert.assertEquals("cee0fcf2-fc07-4107-8cff-c08037beee02", obtainedToken.getUserRef());
        Assert.assertEquals("Electricity_00", obtainedToken.getTokenType());
        Assert.assertEquals("12345678901", obtainedToken.getMeterNo());
        Assert.assertEquals(Instant.ofEpochMilli(EPOCH_TIME), obtainedToken.getCreatedAt());
    }

    @Test
    public void testThatCanGetTokenByUserRef() throws Exception {
        Token token = new Token(REF,
                                TOKEN,
                                USER_REF,
                                TOKEN_TYPE,
                                METER_NO,
                                REQUEST_ID,
                Instant.ofEpochMilli(EPOCH_TIME));
        tokensRepository.save(token);

        List<Token> obtainedTokens = tokensService.getTokens("cee0fcf2-fc07-4107-8cff-c08037beee02");
        Assert.assertEquals(1, obtainedTokens.size());
        Assert.assertEquals("12345678901234567890", obtainedTokens.get(0).getTokenNo());
        Assert.assertEquals("cee0fcf2-fc07-4107-8cff-c08037beee02", obtainedTokens.get(0).getUserRef());
        Assert.assertEquals("Electricity_00", obtainedTokens.get(0).getTokenType());
        Assert.assertEquals("12345678901", obtainedTokens.get(0).getMeterNo());
        Assert.assertEquals(Instant.ofEpochMilli(EPOCH_TIME), obtainedTokens.get(0).getCreatedAt());
    }

    @Test
    public void testThatTokenParamCanBeReturned() throws Exception {
        Token token = new Token(REF,
                                TOKEN,
                                USER_REF,
                                TOKEN_TYPE,
                                METER_NO,
                                REQUEST_ID,
                                Instant.ofEpochMilli(EPOCH_TIME));
        tokensRepository.save(token);

        List<TokenTypeCount> tokenTypes = tokensService.getTokenTypes("cee0fcf2-fc07-4107-8cff-c08037beee02");

        Assert.assertEquals(1, tokenTypes.size());
        Assert.assertEquals(1, tokenTypes.get(0).getCount());
        Assert.assertEquals("Electricity_00", tokenTypes.get(0).getType());
    }

    @Test
    public void testThatTokenIsDecoded() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "0");
        params.put("subclass", "0");
        params.put("token_id", "2018-05-16T07:29");
        params.put("amount", "10");
        params.put("random_no", "5");
        params.put("is_stid", "false");
        params.put("decoder_reference_number", "47500150231");
        params.put("key_type", "0");
        params.put("supply_group_code", "000046");
        params.put("tariff_index", "01");
        params.put("key_revision_no", "1");
        params.put("issuer_identification_no", "600727");
        params.put("base_date", "1993");
        params.put("key_expiry_no", "255");
        params.put("encryption_algorithm", "sta");
        params.put("decoder_key_generation_algorithm", "02");
        params.put("vending_key", "0abc12def3456789");
        params.put("debug", "false");

        List<Token> tokens = tokensService.generateTokens(REQUEST_ID, params, USER_REF);
        HashMap<String, Object> decodedToken = tokensService.decodeToken(REQUEST_ID, "12855806557697228949", params, USER_REF);

        Assert.assertEquals(0l, decodedToken.get("class"));
        Assert.assertEquals(0l, decodedToken.get("subclass"));
        Assert.assertEquals("2018-05-16T07:29:00.000+03:00", decodedToken.get("token_identifier"));
        Assert.assertEquals("Electricity_00", decodedToken.get("type"));
        Assert.assertEquals(5l, decodedToken.get("rnd"));
        Assert.assertEquals("cee0fcf2-fc07-4107-8cff-c08037beee02", decodedToken.get("user_ref"));
        Assert.assertEquals(10.0, decodedToken.get("amount"));
    }

    @Test
    public void testThatElectricity_00TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "0");
        params.put("subclass", "0");
        params.put("token_id", "2018-05-16T07:29");
        params.put("amount", "10");
        params.put("random_no", "5");
        params.put("is_stid", "false");
        params.put("decoder_reference_number", "47500150231");
        params.put("key_type", "0");
        params.put("supply_group_code", "000046");
        params.put("tariff_index", "01");
        params.put("key_revision_no", "1");
        params.put("issuer_identification_no", "600727");
        params.put("base_date", "1993");
        params.put("key_expiry_no", "255");
        params.put("encryption_algorithm", "sta");
        params.put("decoder_key_generation_algorithm", "02");
        params.put("vending_key", "0abc12def3456789");
        params.put("debug", "false");

        final String USER_REF = "85d8780a-8985-4448-850f-38f7b98a5239";

        Token generatedToken = tokensService.generateTokens(REQUEST_ID, params, USER_REF).get(0);
        Assert.assertEquals("Electricity_00", generatedToken.getTokenType());
        Assert.assertEquals("85d8780a-8985-4448-850f-38f7b98a5239", generatedToken.getUserRef());
        Assert.assertEquals("12855806557697228949", generatedToken.getTokenNo());
        Assert.assertEquals("47500150231", generatedToken.getMeterNo());
        Assert.assertNotNull(generatedToken.getRef());
        Assert.assertNotNull(generatedToken.getCreatedAt());
    }

    @Test
    public void testThatWater_01TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "0");
        params.put("subclass", "1");
        params.put("token_id", "2018-05-16T07:29");
        params.put("amount", "10");
        params.put("random_no", "5");
        params.put("is_stid", "false");
        params.put("decoder_reference_number", "47500150231");
        params.put("key_type", "0");
        params.put("supply_group_code", "000046");
        params.put("tariff_index", "01");
        params.put("key_revision_no", "1");
        params.put("issuer_identification_no", "600727");
        params.put("base_date", "1993");
        params.put("key_expiry_no", "255");
        params.put("encryption_algorithm", "sta");
        params.put("decoder_key_generation_algorithm", "02");
        params.put("vending_key", "0abc12def3456789");
        params.put("debug", "false");

        String userRef = "85d8780a-8985-4448-850f-38f7b98a5239";

        Token generatedToken = tokensService.generateTokens(REQUEST_ID, params, userRef).get(0);
        Assert.assertEquals("Water_01", generatedToken.getTokenType());
        Assert.assertEquals("85d8780a-8985-4448-850f-38f7b98a5239", generatedToken.getUserRef());
        Assert.assertEquals("62163371512291190907", generatedToken.getTokenNo());
        Assert.assertEquals("47500150231", generatedToken.getMeterNo());
        Assert.assertNotNull(generatedToken.getRef());
        Assert.assertNotNull(generatedToken.getCreatedAt());
    }

    @Test
    public void testThatGas_02TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "0");
        params.put("subclass", "2");
        params.put("token_id", "2018-05-16T07:29");
        params.put("amount", "10");
        params.put("random_no", "5");
        params.put("is_stid", "false");
        params.put("decoder_reference_number", "47500150231");
        params.put("key_type", "0");
        params.put("supply_group_code", "000046");
        params.put("tariff_index", "01");
        params.put("key_revision_no", "1");
        params.put("issuer_identification_no", "600727");
        params.put("base_date", "1993");
        params.put("key_expiry_no", "255");
        params.put("encryption_algorithm", "sta");
        params.put("decoder_key_generation_algorithm", "02");
        params.put("vending_key", "0abc12def3456789");
        params.put("debug", "false");

        String userRef = "85d8780a-8985-4448-850f-38f7b98a5239";

        Token generatedToken = tokensService.generateTokens(REQUEST_ID, params, userRef).get(0);
        Assert.assertEquals("Gas_02", generatedToken.getTokenType());
        Assert.assertEquals("85d8780a-8985-4448-850f-38f7b98a5239", generatedToken.getUserRef());
        Assert.assertEquals("08179929825337759496", generatedToken.getTokenNo());
        Assert.assertEquals("47500150231", generatedToken.getMeterNo());
        Assert.assertNotNull(generatedToken.getRef());
        Assert.assertNotNull(generatedToken.getCreatedAt());
    }

    @Test
    public void testThatInitiateMeterTestDisplay_10TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "1");
        params.put("subclass", "0");
        params.put("token_id", "2018-05-16T07:29");
        params.put("control", "68719476735");
        params.put("manufacturer_code", "21");
        params.put("is_stid", "false");
        params.put("decoder_reference_number", "47500150231");
        params.put("key_type", "0");
        params.put("supply_group_code", "000046");
        params.put("tariff_index", "01");
        params.put("key_revision_no", "1");
        params.put("issuer_identification_no", "600727");
        params.put("base_date", "1993");
        params.put("key_expiry_no", "255");
        params.put("encryption_algorithm", "sta");
        params.put("decoder_key_generation_algorithm", "02");
        params.put("vending_key", "0abc12def3456789");
        params.put("debug", "false");

        String userRef = "85d8780a-8985-4448-850f-38f7b98a5239";

        Token generatedToken = tokensService.generateTokens(REQUEST_ID, params, userRef).get(0);
        Assert.assertEquals("InitiateMeterTestOrDisplay1_10", generatedToken.getTokenType());
        Assert.assertEquals("85d8780a-8985-4448-850f-38f7b98a5239", generatedToken.getUserRef());
        Assert.assertEquals("56493153725450313471", generatedToken.getTokenNo());
        Assert.assertEquals("47500150231", generatedToken.getMeterNo());
        Assert.assertNotNull(generatedToken.getRef());
        Assert.assertNotNull(generatedToken.getCreatedAt());
    }

    @Test
    public void testThatInitiateMeterTestDisplay_11TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "1");
        params.put("subclass", "1");
        params.put("token_id", "2018-05-16T07:29");
        params.put("control", "268435455");
        params.put("manufacturer_code", "1234");
        params.put("is_stid", "false");
        params.put("decoder_reference_number", "47500150231");
        params.put("key_type", "0");
        params.put("supply_group_code", "000046");
        params.put("tariff_index", "01");
        params.put("key_revision_no", "1");
        params.put("issuer_identification_no", "600727");
        params.put("base_date", "1993");
        params.put("key_expiry_no", "255");
        params.put("encryption_algorithm", "sta");
        params.put("decoder_key_generation_algorithm", "02");
        params.put("vending_key", "0abc12def3456789");
        params.put("debug", "false");

        String userRef = "85d8780a-8985-4448-850f-38f7b98a5239";

        Token generatedToken = tokensService.generateTokens(REQUEST_ID, params, userRef).get(0);
        Assert.assertEquals("InitiateMeterTestOrDisplay1_11", generatedToken.getTokenType());
        Assert.assertEquals("85d8780a-8985-4448-850f-38f7b98a5239", generatedToken.getUserRef());
        Assert.assertEquals("02305843005052951967", generatedToken.getTokenNo());
        Assert.assertEquals("47500150231", generatedToken.getMeterNo());
        Assert.assertNotNull(generatedToken.getRef());
        Assert.assertNotNull(generatedToken.getCreatedAt());
    }

    @Test
    public void testThatSetMaximumPowerLimit_20TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "2");
        params.put("subclass", "0");
        params.put("token_id", "2018-05-16T07:29");
        params.put("maximum_power_limit", "100");
        params.put("random_no", "5");
        params.put("is_stid", "false");
        params.put("decoder_reference_number", "47500150231");
        params.put("key_type", "0");
        params.put("supply_group_code", "000046");
        params.put("tariff_index", "01");
        params.put("key_revision_no", "1");
        params.put("issuer_identification_no", "600727");
        params.put("base_date", "1993");
        params.put("key_expiry_no", "255");
        params.put("encryption_algorithm", "sta");
        params.put("decoder_key_generation_algorithm", "02");
        params.put("vending_key", "0abc12def3456789");
        params.put("debug", "false");

        String userRef = "85d8780a-8985-4448-850f-38f7b98a5239";

        Token generatedToken = tokensService.generateTokens(REQUEST_ID, params, userRef).get(0);
        Assert.assertEquals("SetMaximumPowerLimit_20", generatedToken.getTokenType());
        Assert.assertEquals("85d8780a-8985-4448-850f-38f7b98a5239", generatedToken.getUserRef());
        Assert.assertEquals("30785131371013401998", generatedToken.getTokenNo());
        Assert.assertEquals("47500150231", generatedToken.getMeterNo());
        Assert.assertNotNull(generatedToken.getRef());
        Assert.assertNotNull(generatedToken.getCreatedAt());
    }

    @Test
    public void testThatClearCredit_21TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "2");
        params.put("subclass", "1");
        params.put("token_id", "2018-05-16T07:29");
        params.put("register", "0");
        params.put("random_no", "5");
        params.put("is_stid", "false");
        params.put("decoder_reference_number", "47500150231");
        params.put("key_type", "0");
        params.put("supply_group_code", "000046");
        params.put("tariff_index", "01");
        params.put("key_revision_no", "1");
        params.put("issuer_identification_no", "600727");
        params.put("base_date", "1993");
        params.put("key_expiry_no", "255");
        params.put("encryption_algorithm", "sta");
        params.put("decoder_key_generation_algorithm", "02");
        params.put("vending_key", "0abc12def3456789");
        params.put("debug", "false");

        String userRef = "85d8780a-8985-4448-850f-38f7b98a5239";

        Token generatedToken = tokensService.generateTokens(REQUEST_ID, params, userRef).get(0);
        Assert.assertEquals("ClearCredit_21", generatedToken.getTokenType());
        Assert.assertEquals("85d8780a-8985-4448-850f-38f7b98a5239", generatedToken.getUserRef());
        Assert.assertEquals("64054668175996030872", generatedToken.getTokenNo());
        Assert.assertEquals("47500150231", generatedToken.getMeterNo());
        Assert.assertNotNull(generatedToken.getRef());
        Assert.assertNotNull(generatedToken.getCreatedAt());
    }

    @Test
    public void testThatSetTariffRate_22TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "2");
        params.put("subclass", "2");
        params.put("token_id", "2018-05-16T07:29");
        params.put("tariff_rate", "10");
        params.put("random_no", "5");
        params.put("is_stid", "false");
        params.put("decoder_reference_number", "47500150231");
        params.put("key_type", "0");
        params.put("supply_group_code", "000046");
        params.put("tariff_index", "01");
        params.put("key_revision_no", "1");
        params.put("issuer_identification_no", "600727");
        params.put("base_date", "1993");
        params.put("key_expiry_no", "255");
        params.put("encryption_algorithm", "sta");
        params.put("decoder_key_generation_algorithm", "02");
        params.put("vending_key", "0abc12def3456789");
        params.put("debug", "false");

        String userRef = "85d8780a-8985-4448-850f-38f7b98a5239";

        Token generatedToken = tokensService.generateTokens(REQUEST_ID, params, userRef).get(0);
        Assert.assertEquals("SetTariffRate_22", generatedToken.getTokenType());
        Assert.assertEquals("85d8780a-8985-4448-850f-38f7b98a5239", generatedToken.getUserRef());
        Assert.assertEquals("00080474005256738803", generatedToken.getTokenNo());
        Assert.assertEquals("47500150231", generatedToken.getMeterNo());
        Assert.assertNotNull(generatedToken.getRef());
        Assert.assertNotNull(generatedToken.getCreatedAt());
    }

    @Test
    public void testThatSet1stSectionDecoderKey_23TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "2");
        params.put("subclass", "3");
        params.put("token_id", "2018-05-16T07:29");
        params.put("new_vending_key", "0abc12def3456789");
        params.put("new_supply_group_code", "000046");
        params.put("new_tariff_index", "01");
        params.put("new_key_revision_no", "1");
        params.put("new_key_type", "0");
        params.put("new_key_expiry_no", "255");
        params.put("new_decoder_reference_number", "47500150231");
        params.put("new_issuer_identification_no", "600727");
        params.put("ro", "0");
        params.put("is_stid", "false");
        params.put("decoder_reference_number", "47500150231");
        params.put("key_type", "0");
        params.put("supply_group_code", "000046");
        params.put("tariff_index", "01");
        params.put("key_revision_no", "1");
        params.put("issuer_identification_no", "600727");
        params.put("base_date", "1993");
        params.put("key_expiry_no", "255");
        params.put("encryption_algorithm", "sta");
        params.put("decoder_key_generation_algorithm", "02");
        params.put("vending_key", "0abc12def3456789");
        params.put("debug", "false");

        String userRef = "85d8780a-8985-4448-850f-38f7b98a5239";

        Token generatedToken = tokensService.generateTokens(REQUEST_ID, params, userRef).get(0);
        Assert.assertEquals("Set1stSectionDecoderKey_23", generatedToken.getTokenType());
        Assert.assertEquals("85d8780a-8985-4448-850f-38f7b98a5239", generatedToken.getUserRef());
        Assert.assertEquals("61118509476014644691", generatedToken.getTokenNo());
        Assert.assertEquals("47500150231", generatedToken.getMeterNo());
        Assert.assertNotNull(generatedToken.getRef());
        Assert.assertNotNull(generatedToken.getCreatedAt());
    }

    @Test
    public void testThatSet2ndSectionDecoderKey_24TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "2");
        params.put("subclass", "4");
        params.put("token_id", "2018-05-16T07:29");
        params.put("new_vending_key", "0abc12def3456789");
        params.put("new_supply_group_code", "000046");
        params.put("new_tariff_index", "01");
        params.put("new_key_revision_no", "1");
        params.put("new_key_type", "0");
        params.put("new_key_expiry_no", "255");
        params.put("new_decoder_reference_number", "47500150231");
        params.put("new_issuer_identification_no", "600727");
        params.put("ro", "0");
        params.put("is_stid", "false");
        params.put("decoder_reference_number", "47500150231");
        params.put("key_type", "0");
        params.put("supply_group_code", "000046");
        params.put("tariff_index", "01");
        params.put("key_revision_no", "1");
        params.put("issuer_identification_no", "600727");
        params.put("base_date", "1993");
        params.put("key_expiry_no", "255");
        params.put("encryption_algorithm", "sta");
        params.put("decoder_key_generation_algorithm", "02");
        params.put("vending_key", "0abc12def3456789");
        params.put("debug", "false");

        String userRef = "85d8780a-8985-4448-850f-38f7b98a5239";

        List<Token> tokens = tokensService.generateTokens(REQUEST_ID, params, userRef);
        Token _1stKCT = tokens.get(0);
        Token _2ndKCT = tokens.get(1);

        Assert.assertEquals("Set1stSectionDecoderKey_23", _1stKCT.getTokenType());
        Assert.assertEquals("85d8780a-8985-4448-850f-38f7b98a5239", _1stKCT.getUserRef());
        Assert.assertEquals("61118509476014644691", _1stKCT.getTokenNo());
        Assert.assertEquals("47500150231", _1stKCT.getMeterNo());
        Assert.assertNotNull(_1stKCT.getRef());
        Assert.assertNotNull(_1stKCT.getCreatedAt());

        Assert.assertEquals("Set2ndSectionDecoderKey_24", _2ndKCT.getTokenType());
        Assert.assertEquals("85d8780a-8985-4448-850f-38f7b98a5239", _2ndKCT.getUserRef());
        Assert.assertEquals("29643408183974440183", _2ndKCT.getTokenNo());
        Assert.assertEquals("47500150231", _2ndKCT.getMeterNo());
        Assert.assertNotNull(_2ndKCT.getRef());
        Assert.assertNotNull(_2ndKCT.getCreatedAt());
    }

    @Test
    public void testThatClearTamperCondition_25TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "2");
        params.put("subclass", "5");
        params.put("token_id", "2018-05-16T07:29");
        params.put("pad", "10");
        params.put("random_no", "5");
        params.put("is_stid", "false");
        params.put("decoder_reference_number", "47500150231");
        params.put("key_type", "0");
        params.put("supply_group_code", "000046");
        params.put("tariff_index", "01");
        params.put("key_revision_no", "1");
        params.put("issuer_identification_no", "600727");
        params.put("base_date", "1993");
        params.put("key_expiry_no", "255");
        params.put("encryption_algorithm", "sta");
        params.put("decoder_key_generation_algorithm", "02");
        params.put("vending_key", "0abc12def3456789");
        params.put("debug", "false");

        String userRef = "85d8780a-8985-4448-850f-38f7b98a5239";

        Token generatedToken = tokensService.generateTokens(REQUEST_ID, params, userRef).get(0);
        Assert.assertEquals("ClearTamperCondition_25", generatedToken.getTokenType());
        Assert.assertEquals("85d8780a-8985-4448-850f-38f7b98a5239", generatedToken.getUserRef());
        Assert.assertEquals("41325328900347961855", generatedToken.getTokenNo());
        Assert.assertEquals("47500150231", generatedToken.getMeterNo());
        Assert.assertNotNull(generatedToken.getRef());
        Assert.assertNotNull(generatedToken.getCreatedAt());
    }

    @Test
    public void testThatSetMaximumPhasePowerUnbalanceLimit_26TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "2");
        params.put("subclass", "6");
        params.put("token_id", "2018-05-16T07:29");
        params.put("mppul", "10");
        params.put("random_no", "5");
        params.put("is_stid", "false");
        params.put("decoder_reference_number", "47500150231");
        params.put("key_type", "0");
        params.put("supply_group_code", "000046");
        params.put("tariff_index", "01");
        params.put("key_revision_no", "1");
        params.put("issuer_identification_no", "600727");
        params.put("base_date", "1993");
        params.put("key_expiry_no", "255");
        params.put("encryption_algorithm", "sta");
        params.put("decoder_key_generation_algorithm", "02");
        params.put("vending_key", "0abc12def3456789");
        params.put("debug", "false");

        String userRef = "85d8780a-8985-4448-850f-38f7b98a5239";

        Token generatedToken = tokensService.generateTokens(REQUEST_ID, params, userRef).get(0);
        Assert.assertEquals("SetMaximumPhasePowerUnbalanceLimit_26", generatedToken.getTokenType());
        Assert.assertEquals("85d8780a-8985-4448-850f-38f7b98a5239", generatedToken.getUserRef());
        Assert.assertEquals("34418209051324721134", generatedToken.getTokenNo());
        Assert.assertEquals("47500150231", generatedToken.getMeterNo());
        Assert.assertNotNull(generatedToken.getRef());
        Assert.assertNotNull(generatedToken.getCreatedAt());
    }

    @Test
    public void testThatSetWaterMeterFactor_27TokenIsGenerated() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("class", "2");
        params.put("subclass", "7");
        params.put("token_id", "2018-05-16T07:29");
        params.put("wm_factor", "10");
        params.put("random_no", "9");
        params.put("is_stid", "false");
        params.put("decoder_reference_number", "47500150231");
        params.put("key_type", "0");
        params.put("supply_group_code", "000046");
        params.put("tariff_index", "01");
        params.put("key_revision_no", "1");
        params.put("issuer_identification_no", "600727");
        params.put("base_date", "1993");
        params.put("key_expiry_no", "255");
        params.put("encryption_algorithm", "sta");
        params.put("decoder_key_generation_algorithm", "02");
        params.put("vending_key", "0abc12def3456789");
        params.put("debug", "false");

        String userRef = "85d8780a-8985-4448-850f-38f7b98a5239";

        Token generatedToken = tokensService.generateTokens(REQUEST_ID, params, userRef).get(0);
        Assert.assertEquals("SetWaterMeterFactor_27", generatedToken.getTokenType());
        Assert.assertEquals("85d8780a-8985-4448-850f-38f7b98a5239", generatedToken.getUserRef());
        Assert.assertEquals("44960158815642711003", generatedToken.getTokenNo());
        Assert.assertEquals("47500150231", generatedToken.getMeterNo());
        Assert.assertNotNull(generatedToken.getRef());
        Assert.assertNotNull(generatedToken.getCreatedAt());
    }

    @Test
    public void testThatTokenIsDeleted() throws Exception {
        thrown.expect(InvalidTokenRefException.class);
        thrown.expectMessage("Invalid token ref 3ae36113-c4ca-4567-b00f-b186d75e99f4");

        Token token = new Token(REF,
                                TOKEN,
                                USER_REF,
                                TOKEN_TYPE,
                                METER_NO,
                                REQUEST_ID,
                                Instant.ofEpochMilli(EPOCH_TIME));
        tokensRepository.save(token);

        tokensService.deleteToken("3ae36113-c4ca-4567-b00f-b186d75e99f4", USER_REF);

        tokensService.getToken("3ae36113-c4ca-4567-b00f-b186d75e99f4");
    }

    @Test
    public void testThatTokenNoIsDeleted() throws Exception {
        thrown.expect(InvalidTokenRefException.class);
        thrown.expectMessage("Invalid token ref 3ae36113-c4ca-4567-b00f-b186d75e99f4");
        final String USER_REF = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        Token token = new Token(REF,
                                TOKEN,
                                USER_REF,
                                TOKEN_TYPE,
                                METER_NO,
                                REQUEST_ID,
                                Instant.ofEpochMilli(EPOCH_TIME));
        tokensRepository.save(token);

        tokensService.deleteTokenByTokenNo("12345678901234567890", USER_REF);

        tokensService.getToken("3ae36113-c4ca-4567-b00f-b186d75e99f4");
    }

    @Test
    public void testThatGeneratedNoOfTokensAreReturned() throws Exception {
        final String USER_REF = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        Token token = new Token(REF,
                                TOKEN,
                                USER_REF,
                                TOKEN_TYPE,
                                METER_NO,
                                REQUEST_ID,
                                Instant.ofEpochMilli(EPOCH_TIME));
        tokensRepository.save(token);
        
        Assert.assertEquals(1, tokensService.getGeneratedNoOfTokens(USER_REF));
    }

    @Test
    public void testThatGetTokenTypesAreReturned() throws Exception {
        final String USER_REF = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        Token token = new Token(REF,
                                TOKEN,
                                USER_REF,
                                TOKEN_TYPE,
                                METER_NO,
                                REQUEST_ID,
                                Instant.ofEpochMilli(EPOCH_TIME));
        tokensRepository.save(token);

        Assert.assertEquals(1, tokensService.getNoOfTokenTypes(USER_REF));
    }

    @Test
    public void testThatUniqueMetersAreReturned() throws Exception {
        final String USER_REF = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        Token token = new Token(REF,
                                TOKEN,
                                USER_REF,
                                TOKEN_TYPE,
                                METER_NO,
                                REQUEST_ID,
                                Instant.ofEpochMilli(EPOCH_TIME));
        tokensRepository.save(token);

        Assert.assertEquals(1, tokensService.getUniqueMetersNo(USER_REF));
    }

    @Test
    public void testThatNoOfTokensPerMonthAreReturned() throws Exception {
        final String USER_REF = "cee0fcf2-fc07-4107-8cff-c08037beee02";
        Token token = new Token(REF,
                                TOKEN,
                                USER_REF,
                                TOKEN_TYPE,
                                METER_NO,
                                REQUEST_ID,
                                Instant.ofEpochMilli(EPOCH_TIME));
        tokensRepository.save(token);

        List<TimelineRequest> requests
                = tokensRepository.getTimelineRequests(token.getUserRef(),100);

        Assert.assertEquals(1, requests.size());
        Assert.assertEquals(11, requests.get(0).getMonth());
        Assert.assertEquals(1, requests.get(0).getTokens());
        Assert.assertEquals(2020, requests.get(0).getYear());
    }
}
