package ke.co.nectar.impl;

import ke.co.nectar.hsm.prism.*;
import ke.co.nectar.hsm.prism.impl.PrismClientFacade;
import ke.co.nectar.hsm.prism.impl.PrismHSMConnector;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.EncryptionAlgorithm;
import ke.co.nectar.token.domain.encryptionalgorithm.StandardTransferAlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.domain.token.class0.TransferElectricityCreditToken;
import ke.co.nectar.token.domain.token.class0.TransferGasCreditToken;
import ke.co.nectar.token.domain.token.class0.TransferWaterCreditToken;
import ke.co.nectar.token.domain.token.class1.InitiateMeterTestOrDisplay1Token;
import ke.co.nectar.token.domain.token.class1.InitiateMeterTestOrDisplay2Token;
import ke.co.nectar.token.domain.token.class2.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PrismClientFacadeTest {

    private String host = "192.0.0.1";
    private int port = 9443;
    private String realm = "local";
    private String username = "username";
    private String password = "password";

    private final String DRN = "58000079523";
    private final String PAN = "600727580000795237";
    private final short EA = 7;
    private final short TCT = 2;
    private final int SGC = 600675;
    private final short KRN = 1;
    private final short TI = 21;
    private final int TID = 15891088; // 03/21/2023, 02:28PM UTC
    private final double TRANSFER_AMOUNT = 102.0;
    private final boolean IS_RESERVED_TID = false;
    private final String STS_UNIT_NAME = "hWh";
    private final String SCALED_AMOUNT = "10.2";
    private final String SCALED_UNIT_NAME = "kWh";
    private final String TOKEN_DEC = "67819696861845956084";
    private final String TOKEN_HEX = "3AD2FF6F8C100A5F4";
    private final String IS_SM = "SMID.1:Prism:94001632:20210204T081427Z:039F2C48F2030174:AEEA";
    private final String VK_KCV = "7915C8";

    private PrismHSMConnector connector;
    private TokenApi.Client client;

    @Before
    public void setUp() {
        connector = mock(PrismHSMConnector.class);
        client = mock(TokenApi.Client.class);
    }

    @Test
    public void testThatPrismClientFacadeIsInitialized() {
        PrismClientFacade facade = new PrismClientFacade(host, port, realm, username, password, connector);
        assertEquals("test that host is initialized", "192.0.0.1", facade.getHost());
        assertEquals("test that port is initialized", 9443, facade.getPort());
        assertEquals("test that realm is initialized", "local", facade.getRealm());
        assertEquals("test that username is initialized", "username", facade.getUsername());
        assertEquals("test that password is initialized", "password", facade.getPassword());
    }

    @Test
    public void testThatAuthenticationIsTriggered() throws Exception {
        IndividualAccountIdentificationNumber iain = new IndividualAccountIdentificationNumber("58000079523");
        EncryptionAlgorithm encryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
        TokenCarrierType tokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.NUMERIC);
        SupplyGroupCode supplyGroupCode = new SupplyGroupCode("600675");
        KeyRevisionNumber keyRevisionNumber = new KeyRevisionNumber(1);
        KeyExpiryNumber keyExpiryNumber = new KeyExpiryNumber(255);
        TariffIndex tariffIndex = new TariffIndex("21");
        boolean allow3Kct = false;

        final String ACCESS_TOKEN = "access-token";

        when(connector.getClient(anyString(), anyInt())).thenReturn(client);
        when(connector.signInWithPassword(any(), anyString(), anyString(), anyString())).thenReturn(ACCESS_TOKEN);

        PrismClientFacade facade = new PrismClientFacade(host, port, realm, username, password, connector);
        facade.initialize(iain, encryptionAlgorithm, tokenCarrierType, supplyGroupCode,
                keyRevisionNumber, keyExpiryNumber, tariffIndex, allow3Kct);

        assertEquals("test that correct DRN is set", "58000079523", facade.meterConfigIn.getDrn());
        assertEquals("test that correct EA is set", 7, facade.meterConfigIn.getEa());
        assertEquals("test that correct TCT is set", 2, facade.meterConfigIn.getTct());
        assertEquals("test that correct SGC is set", 600675, facade.meterConfigIn.getSgc());
        assertEquals("test that correct KRN is set", 1, facade.meterConfigIn.getKrn());
        assertEquals("test that correct TI is set", 21, facade.meterConfigIn.getTi());
        assertEquals("test that correct KEN is set", 255, facade.meterConfigIn.getKen());
        assertFalse("test that correct allow3Kct flag is set",
                (boolean) facade.meterConfigIn.getFieldValue(MeterConfigIn._Fields.ALLOW3_KCT));
    }

    @Test
    public void testGenerateTransferElectricityCreditToken() throws Exception {

        final short TOKEN_CLASS = 0;
        final short SUBCLASS = 0;
        final String ACCESS_TOKEN = "access-token";
        final String REQUEST_ID = "request-id";
        final String DESCRIPTION = "Credit:Electricity";

        when(connector.getClient(anyString(), anyInt())).thenReturn(client);
        when(connector.signInWithPassword(any(), anyString(), anyString(), anyString())).thenReturn(ACCESS_TOKEN);

        Token token = new Token(DRN, PAN, EA, TCT, SGC, KRN, TI, TOKEN_CLASS, SUBCLASS,
                TID, TRANSFER_AMOUNT, IS_RESERVED_TID, DESCRIPTION, STS_UNIT_NAME, SCALED_AMOUNT,
                SCALED_UNIT_NAME, TOKEN_DEC, TOKEN_HEX, IS_SM, VK_KCV);
        List<Token> mockTokens = new ArrayList<>();
        mockTokens.add(token);

        when(connector.generateCreditToken(any(), anyString(), anyString(),
                any(), any(), anyFloat(), anyLong())).thenReturn(mockTokens);

        IndividualAccountIdentificationNumber iain = new IndividualAccountIdentificationNumber("58000079523");
        EncryptionAlgorithm encryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
        TokenCarrierType tokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.NUMERIC);
        SupplyGroupCode supplyGroupCode = new SupplyGroupCode("600675");
        KeyRevisionNumber keyRevisionNumber = new KeyRevisionNumber(1);
        KeyExpiryNumber keyExpiryNumber = new KeyExpiryNumber(255);
        TariffIndex tariffIndex = new TariffIndex("21");
        Amount amountPurchased =  new Amount(10);

        PrismClientFacade facade = new PrismClientFacade(host, port, realm, username, password, connector);
        TransferElectricityCreditToken generatedToken
                    = facade.generateTransferElectricityCreditToken(REQUEST_ID, iain,
                                                                    encryptionAlgorithm, tokenCarrierType,
                                                                    supplyGroupCode, keyRevisionNumber, amountPurchased,
                                                                    keyExpiryNumber, tariffIndex);

        assertEquals("test that the correct token class is returned", "Electricity Credit Transfer", generatedToken.getTokenClass().getName());
        assertEquals("test that the correct token class is returned", "Electricity", generatedToken.getTokenSubClass().getName());
        // assertEquals("test that the correct token class is returned", 15891088, generatedToken.getTokenIdentifier().getDifferenceFromBaseTimeInMinutes());
        assertEquals("test that the correct token class is returned", 10.2, generatedToken.getAmountPurchased().getAmountPurchased(), 0.2);
        assertEquals("test that the correct token class is returned", "67819696861845956084", generatedToken.getTokenNo());
    }

    @Test
    public void testGenerateTransferWaterCreditToken() throws Exception {

        final short TOKEN_CLASS = 0;
        final short SUBCLASS = 0;
        final String ACCESS_TOKEN = "access-token";
        final String REQUEST_ID = "request-id";
        final String DESCRIPTION = "Credit:Water";

        when(connector.getClient(anyString(), anyInt())).thenReturn(client);
        when(connector.signInWithPassword(any(), anyString(), anyString(), anyString())).thenReturn(ACCESS_TOKEN);

        Token token = new Token(DRN, PAN, EA, TCT, SGC, KRN, TI, TOKEN_CLASS, SUBCLASS,
                TID, TRANSFER_AMOUNT, IS_RESERVED_TID, DESCRIPTION, STS_UNIT_NAME, SCALED_AMOUNT,
                SCALED_UNIT_NAME, TOKEN_DEC, TOKEN_HEX, IS_SM, VK_KCV);
        List<Token> mockTokens = new ArrayList<>();
        mockTokens.add(token);

        when(connector.generateCreditToken(any(), anyString(), anyString(),
                any(), any(), anyFloat(), anyLong())).thenReturn(mockTokens);

        IndividualAccountIdentificationNumber iain = new IndividualAccountIdentificationNumber("58000079523");
        EncryptionAlgorithm encryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
        TokenCarrierType tokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.NUMERIC);
        SupplyGroupCode supplyGroupCode = new SupplyGroupCode("600675");
        KeyRevisionNumber keyRevisionNumber = new KeyRevisionNumber(1);
        KeyExpiryNumber keyExpiryNumber = new KeyExpiryNumber(255);
        TariffIndex tariffIndex = new TariffIndex("21");
        Amount amountPurchased =  new Amount(10);

        PrismClientFacade facade = new PrismClientFacade(host, port, realm, username, password, connector);
        TransferWaterCreditToken generatedToken
                = facade.generateTransferWaterCreditToken(REQUEST_ID, iain,
                                                                encryptionAlgorithm, tokenCarrierType,
                                                                supplyGroupCode, keyRevisionNumber, amountPurchased,
                                                                keyExpiryNumber, tariffIndex);

        assertEquals("test that the correct token class is returned", "Water Credit Transfer", generatedToken.getTokenClass().getName());
        assertEquals("test that the correct token class is returned", "Water", generatedToken.getTokenSubClass().getName());
        // assertEquals("test that the correct token class is returned", 15891088, generatedToken.getTokenIdentifier().getDifferenceFromBaseTimeInMinutes());
        assertEquals("test that the correct token class is returned", 10.2, generatedToken.getAmountPurchased().getAmountPurchased(), 0.2);
        assertEquals("test that the correct token class is returned", "67819696861845956084", generatedToken.getTokenNo());
    }

    @Test
    public void testGenerateTransferGasCreditToken() throws Exception {

        final String ACCESS_TOKEN = "access-token";
        final String REQUEST_ID = "request-id";
        final String DESCRIPTION = "Credit:Gas";
        final short TOKEN_CLASS = 0;
        final short SUBCLASS = 0;

        when(connector.getClient(anyString(), anyInt())).thenReturn(client);
        when(connector.signInWithPassword(any(), anyString(), anyString(), anyString())).thenReturn(ACCESS_TOKEN);

        Token token = new Token(DRN, PAN, EA, TCT, SGC, KRN, TI, TOKEN_CLASS, SUBCLASS,
                TID, TRANSFER_AMOUNT, IS_RESERVED_TID, DESCRIPTION, STS_UNIT_NAME, SCALED_AMOUNT,
                SCALED_UNIT_NAME, TOKEN_DEC, TOKEN_HEX, IS_SM, VK_KCV);
        List<Token> mockTokens = new ArrayList<>();
        mockTokens.add(token);

        when(connector.generateCreditToken(any(), anyString(), anyString(),
                any(), any(), anyFloat(), anyLong())).thenReturn(mockTokens);

        IndividualAccountIdentificationNumber iain = new IndividualAccountIdentificationNumber("58000079523");
        EncryptionAlgorithm encryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
        TokenCarrierType tokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.NUMERIC);
        SupplyGroupCode supplyGroupCode = new SupplyGroupCode("600675");
        KeyRevisionNumber keyRevisionNumber = new KeyRevisionNumber(1);
        KeyExpiryNumber keyExpiryNumber = new KeyExpiryNumber(255);
        TariffIndex tariffIndex = new TariffIndex("21");
        Amount amountPurchased =  new Amount(10);

        PrismClientFacade facade = new PrismClientFacade(host, port, realm, username, password, connector);
        TransferGasCreditToken generatedToken
                = facade.generateTransferGasCreditToken(REQUEST_ID, iain,
                                                        encryptionAlgorithm, tokenCarrierType,
                                                        supplyGroupCode, keyRevisionNumber, amountPurchased,
                                                        keyExpiryNumber, tariffIndex);

        assertEquals("test that the correct token class is returned", "Gas Credit Transfer", generatedToken.getTokenClass().getName());
        assertEquals("test that the correct token class is returned", "Gas", generatedToken.getTokenSubClass().getName());
        // assertEquals("test that the correct token class is returned", 15891088, generatedToken.getTokenIdentifier().getDifferenceFromBaseTimeInMinutes());
        assertEquals("test that the correct token class is returned", 10.2, generatedToken.getAmountPurchased().getAmountPurchased(), 0.2);
        assertEquals("test that the correct token class is returned", "67819696861845956084", generatedToken.getTokenNo());
    }

    @Test
    public void testGenerateInitiateMeterTestOrDisplay1Token() throws Exception {

        final String ACCESS_TOKEN = "access-token";
        final String REQUEST_ID = "request-id";
        final long CONTROL = 32;
        final short MFR_CODE = 58;
        final String DESCRIPTION = "InitMeterTest";
        final short TOKEN_CLASS = 1;
        final short SUBCLASS = 0;

        when(connector.getClient(anyString(), anyInt())).thenReturn(client);
        when(connector.signInWithPassword(any(), anyString(), anyString(), anyString())).thenReturn(ACCESS_TOKEN);

        MeterTestToken token = new MeterTestToken(DRN, PAN, TOKEN_CLASS, SUBCLASS,
                CONTROL, MFR_CODE, DESCRIPTION, TOKEN_DEC, TOKEN_HEX);

        when(connector.generateNMseToken(any(), anyString(), anyString(),
                any(), any(), any())).thenReturn(token);

        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber(DRN);

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000100000"), manufacturerCode);

        PrismClientFacade facade = new PrismClientFacade(host, port, realm, username, password, connector);
        InitiateMeterTestOrDisplay1Token generatedToken
                = facade.generateInitiateMeterTestOrDisplay1Token(REQUEST_ID, iain,
                                                                control, manufacturerCode);

        assertEquals("test that the correct token class is returned", "Initiate Meter Test/Display", generatedToken.getTokenClass().getName());
        assertEquals("test that the correct token class is returned", "InitiateMeterTestDisplay1", generatedToken.getTokenSubClass().getName());
        assertEquals("test that the correct token class is returned", "67819696861845956084", generatedToken.getTokenNo());
    }

    @Test
    public void testGenerateInitiateMeterTestOrDisplay2Token() throws Exception {

        final String ACCESS_TOKEN = "access-token";
        final String REQUEST_ID = "request-id";
        final long CONTROL = 32;
        final short MFR_CODE = 58;
        final String DESCRIPTION = "InitMeterTest";
        final String DRN = "1000000000082";
        final short TOKEN_CLASS = 1;
        final short SUBCLASS = 1;

        when(connector.getClient(anyString(), anyInt())).thenReturn(client);
        when(connector.signInWithPassword(any(), anyString(), anyString(), anyString())).thenReturn(ACCESS_TOKEN);

        MeterTestToken token = new MeterTestToken(DRN, PAN, TOKEN_CLASS, SUBCLASS,
                CONTROL, MFR_CODE, DESCRIPTION, TOKEN_DEC, TOKEN_HEX);

        when(connector.generateNMseToken(any(), anyString(), anyString(),
                any(), any(), any())).thenReturn(token);

        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber(DRN);

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000100000"), manufacturerCode);

        PrismClientFacade facade = new PrismClientFacade(host, port, realm, username, password, connector);
        InitiateMeterTestOrDisplay2Token generatedToken
                = facade.generateInitiateMeterTestOrDisplay2Token(REQUEST_ID, iain, control, manufacturerCode);

        assertEquals("test that the correct token class is returned", "Initiate Meter Test/Display", generatedToken.getTokenClass().getName());
        assertEquals("test that the correct token class is returned", "InitiateMeterTestDisplay2", generatedToken.getTokenSubClass().getName());
        assertEquals("test that the correct token class is returned", "67819696861845956084", generatedToken.getTokenNo());
    }

    @Test
    public void testGenerateClearCreditToken() throws Exception {

        final String ACCESS_TOKEN = "access-token";
        final String REQUEST_ID = "request-id";
        final String DESCRIPTION = "ClearCredit";
        final short TOKEN_CLASS = 2;
        final short SUBCLASS = 1;

        when(connector.getClient(anyString(), anyInt())).thenReturn(client);
        when(connector.signInWithPassword(any(), anyString(), anyString(), anyString())).thenReturn(ACCESS_TOKEN);

        Token token = new Token(DRN, PAN, EA, TCT, SGC, KRN, TI, TOKEN_CLASS, SUBCLASS,
                TID, TRANSFER_AMOUNT, IS_RESERVED_TID, DESCRIPTION, STS_UNIT_NAME, SCALED_AMOUNT,
                SCALED_UNIT_NAME, TOKEN_DEC, TOKEN_HEX, IS_SM, VK_KCV);
        List<Token> mockTokens = new ArrayList<>();
        mockTokens.add(token);

        when(connector.generateMseToken(any(), anyString(), anyString(),
                any(), any(), any(), any(), any(), anyLong())).thenReturn(mockTokens);

        IndividualAccountIdentificationNumber iain = new IndividualAccountIdentificationNumber("58000079523");
        EncryptionAlgorithm encryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
        TokenCarrierType tokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.NUMERIC);
        SupplyGroupCode supplyGroupCode = new SupplyGroupCode("600675");
        KeyRevisionNumber keyRevisionNumber = new KeyRevisionNumber(1);
        KeyExpiryNumber keyExpiryNumber = new KeyExpiryNumber(255);
        TariffIndex tariffIndex = new TariffIndex("21");

        PrismClientFacade facade = new PrismClientFacade(host, port, realm, username, password, connector);
        ClearCreditToken generatedToken = facade.generateClearCreditToken(REQUEST_ID, iain,
                                                                        encryptionAlgorithm, tokenCarrierType,
                                                                        supplyGroupCode, keyRevisionNumber,
                                                                        keyExpiryNumber, tariffIndex);

        assertEquals("test that the correct token class is returned", "Set Maximum Power Limit", generatedToken.getTokenClass().getName());
        assertEquals("test that the correct token class is returned", "ClearCreditTokenSubClass", generatedToken.getTokenSubClass().getName());
        assertEquals("test that the correct token class is returned", "ClearCredit_21", generatedToken.getType());
        assertEquals("test that the correct token class is returned", "67819696861845956084", generatedToken.getTokenNo());
    }

    @Test
    public void testGenerateClearTamperToken() throws Exception {

        final String ACCESS_TOKEN = "access-token";
        final String REQUEST_ID = "request-id";
        final String DESCRIPTION = "ClearTamperCondition";
        final short TOKEN_CLASS = 2;
        final short SUBCLASS = 5;

        when(connector.getClient(anyString(), anyInt())).thenReturn(client);
        when(connector.signInWithPassword(any(), anyString(), anyString(), anyString())).thenReturn(ACCESS_TOKEN);

        Token token = new Token(DRN, PAN, EA, TCT, SGC, KRN, TI, TOKEN_CLASS, SUBCLASS,
                TID, TRANSFER_AMOUNT, IS_RESERVED_TID, DESCRIPTION, STS_UNIT_NAME, SCALED_AMOUNT,
                SCALED_UNIT_NAME, TOKEN_DEC, TOKEN_HEX, IS_SM, VK_KCV);
        List<Token> mockTokens = new ArrayList<>();
        mockTokens.add(token);

        when(connector.generateMseToken(any(), anyString(), anyString(),
                any(), any(), any(), any(), any(), anyLong())).thenReturn(mockTokens);

        IndividualAccountIdentificationNumber iain = new IndividualAccountIdentificationNumber("58000079523");
        EncryptionAlgorithm encryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
        TokenCarrierType tokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.NUMERIC);
        SupplyGroupCode supplyGroupCode = new SupplyGroupCode("600675");
        KeyRevisionNumber keyRevisionNumber = new KeyRevisionNumber(1);
        KeyExpiryNumber keyExpiryNumber = new KeyExpiryNumber(255);
        TariffIndex tariffIndex = new TariffIndex("21");

        PrismClientFacade facade = new PrismClientFacade(host, port, realm, username, password, connector);
        ClearTamperConditionToken generatedToken = facade.generateClearTamperConditionToken(REQUEST_ID, iain,
                                                                        encryptionAlgorithm, tokenCarrierType,
                                                                        supplyGroupCode, keyRevisionNumber,
                                                                        keyExpiryNumber, tariffIndex);

        assertEquals("test that the correct token class is returned", "Clear Tamper", generatedToken.getTokenClass().getName());
        assertEquals("test that the correct token class is returned", "ClearTamperConditionTokenSubClass", generatedToken.getTokenSubClass().getName());
        assertEquals("test that the correct token class is returned", "ClearTamperCondition_25", generatedToken.getType());
        assertEquals("test that the correct token class is returned", "67819696861845956084", generatedToken.getTokenNo());
    }

    @Test
    public void testGenerateDecoderKeyTokens() throws Exception {

        final String ACCESS_TOKEN = "access-token";
        final String REQUEST_ID = "request-id";

        when(connector.getClient(anyString(), anyInt())).thenReturn(client);
        when(connector.signInWithPassword(any(), anyString(), anyString(), anyString())).thenReturn(ACCESS_TOKEN);

        short Set1stSectionDecoderKeyClass = 2;
        short Set1stSectionDecoderKeySubclass = 3;
        String descSet1stSectionDecoderKey = "Set1stSectionDecoderKey";
        Token set1stSectionDecoderKeyToken = new Token(DRN, PAN, EA, TCT, SGC, KRN, TI, Set1stSectionDecoderKeyClass, Set1stSectionDecoderKeySubclass,
                TID, TRANSFER_AMOUNT, IS_RESERVED_TID, descSet1stSectionDecoderKey, STS_UNIT_NAME, SCALED_AMOUNT,
                SCALED_UNIT_NAME, TOKEN_DEC, TOKEN_HEX, IS_SM, VK_KCV);

        short Set2ndSectionDecoderKeyClass = 2;
        short Set2ndSectionDecoderKeySubclass = 4;
        String descSet2ndSectionDecoderKey = "Set2ndSectionDecoderKey";
        Token set2ndSectionDecoderKeyToken = new Token(DRN, PAN, EA, TCT, SGC, KRN, TI,
                Set2ndSectionDecoderKeyClass, Set2ndSectionDecoderKeySubclass,
                TID, TRANSFER_AMOUNT, IS_RESERVED_TID, descSet2ndSectionDecoderKey, STS_UNIT_NAME, SCALED_AMOUNT,
                SCALED_UNIT_NAME, TOKEN_DEC, TOKEN_HEX, IS_SM, VK_KCV);

        List<Token> mockTokens = new ArrayList<>();
        mockTokens.add(set1stSectionDecoderKeyToken);
        mockTokens.add(set2ndSectionDecoderKeyToken);

        when(connector.generateKCTs(any(), anyString(), anyString(),
                any(), any())).thenReturn(mockTokens);

        IndividualAccountIdentificationNumber iain = new IndividualAccountIdentificationNumber("58000079523");
        EncryptionAlgorithm encryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
        TokenCarrierType tokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.NUMERIC);
        SupplyGroupCode supplyGroupCode = new SupplyGroupCode("600675");
        KeyRevisionNumber keyRevisionNumber = new KeyRevisionNumber(1);
        KeyExpiryNumber keyExpiryNumber = new KeyExpiryNumber(255);
        TariffIndex tariffIndex = new TariffIndex("21");
        boolean allow3Kct = true;

        SupplyGroupCode newSupplyGroupCode = new SupplyGroupCode("600675");
        KeyRevisionNumber newKeyRevisionNumber = new KeyRevisionNumber(1);
        TariffIndex newTariffIndex = new TariffIndex("21");

        PrismClientFacade facade = new PrismClientFacade(host, port, realm, username, password, connector);
        List<Class2Token> generatedTokens = facade.generateDecoderKeyTokens(REQUEST_ID, iain,
                                                                            encryptionAlgorithm, tokenCarrierType,
                                                                            supplyGroupCode, keyRevisionNumber,
                                                                            keyExpiryNumber, tariffIndex, newSupplyGroupCode,
                                                                            newKeyRevisionNumber, newTariffIndex, allow3Kct);

        Class2Token _1KCT = generatedTokens.get(0);
        Class2Token _2KCT = generatedTokens.get(1);

        assertEquals("test that the correct token class is returned", "Set 1st Section Decoder Key", _1KCT.getTokenClass().getName());
        assertEquals("test that the correct token class is returned", "Set1stSectionDecoderKeyTokenSubClass", _1KCT.getTokenSubClass().getName());
        assertEquals("test that the correct token class is returned", "Set1stSectionDecoderKey_23", _1KCT.getType());
        assertEquals("test that the correct token class is returned", "67819696861845956084", _1KCT.getTokenNo());

        assertEquals("test that the correct token class is returned", "Set 2nd Section Decoder Key", _2KCT.getTokenClass().getName());
        assertEquals("test that the correct token class is returned", "Set2ndSectionDecoderKeyTokenSubClass", _2KCT.getTokenSubClass().getName());
        assertEquals("test that the correct token class is returned", "Set2ndSectionDecoderKey_24", _2KCT.getType());
        assertEquals("test that the correct token class is returned", "67819696861845956084", _2KCT.getTokenNo());
    }

    @Test
    public void testGenerateSetMaximumPhasePowerUnbalanceLimitToken() throws Exception {

        final String ACCESS_TOKEN = "access-token";
        final String REQUEST_ID = "request-id";
        final String DESCRIPTION = "SetMaxPhaseUnbalanceLmt";
        final short TOKEN_CLASS = 2;
        final short SUBCLASS = 6;

        when(connector.getClient(anyString(), anyInt())).thenReturn(client);
        when(connector.signInWithPassword(any(), anyString(), anyString(), anyString())).thenReturn(ACCESS_TOKEN);

        Token token = new Token(DRN, PAN, EA, TCT, SGC, KRN, TI, TOKEN_CLASS, SUBCLASS,
                TID, TRANSFER_AMOUNT, IS_RESERVED_TID, DESCRIPTION, STS_UNIT_NAME, SCALED_AMOUNT,
                SCALED_UNIT_NAME, TOKEN_DEC, TOKEN_HEX, IS_SM, VK_KCV);
        List<Token> mockTokens = new ArrayList<>();
        mockTokens.add(token);

        when(connector.generateMseToken(any(), anyString(), anyString(),
                any(), any(), any(), any(), any(), anyLong())).thenReturn(mockTokens);

        IndividualAccountIdentificationNumber iain = new IndividualAccountIdentificationNumber("58000079523");
        EncryptionAlgorithm encryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
        TokenCarrierType tokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.NUMERIC);
        SupplyGroupCode supplyGroupCode = new SupplyGroupCode("600675");
        KeyRevisionNumber keyRevisionNumber = new KeyRevisionNumber(1);
        KeyExpiryNumber keyExpiryNumber = new KeyExpiryNumber(255);
        TariffIndex tariffIndex = new TariffIndex("21");

        PrismClientFacade facade = new PrismClientFacade(host, port, realm, username, password, connector);
        SetMaximumPhasePowerUnbalanceLimitToken generatedToken = facade.generateSetMaximumPhasePowerUnbalanceLimitToken(REQUEST_ID, iain,
                                                                                            encryptionAlgorithm, tokenCarrierType,
                                                                                            supplyGroupCode, keyRevisionNumber,
                                                                                            keyExpiryNumber, tariffIndex);

        assertEquals("test that the correct token class is returned", "Set Maximum Phase Power Unbalance Limit", generatedToken.getTokenClass().getName());
        assertEquals("test that the correct token class is returned", "SetMaximumPhasePowerUnbalanceLimitTokenSubClass", generatedToken.getTokenSubClass().getName());
        assertEquals("test that the correct token class is returned", "SetMaximumPhasePowerUnbalanceLimit_26", generatedToken.getType());
        assertEquals("test that the correct token class is returned", "67819696861845956084", generatedToken.getTokenNo());
    }

    @Test
    public void testGenerateSetMaximumPowerLimitToken() throws Exception {

        final String ACCESS_TOKEN = "access-token";
        final String REQUEST_ID = "request-id";
        final String DESCRIPTION = "SetMaximumPowerLimit";
        final short TOKEN_CLASS = 2;
        final short SUBCLASS = 0;

        when(connector.getClient(anyString(), anyInt())).thenReturn(client);
        when(connector.signInWithPassword(any(), anyString(), anyString(), anyString())).thenReturn(ACCESS_TOKEN);

        Token token = new Token(DRN, PAN, EA, TCT, SGC, KRN, TI, TOKEN_CLASS, SUBCLASS,
                TID, TRANSFER_AMOUNT, IS_RESERVED_TID, DESCRIPTION, STS_UNIT_NAME, SCALED_AMOUNT,
                SCALED_UNIT_NAME, TOKEN_DEC, TOKEN_HEX, IS_SM, VK_KCV);
        List<Token> mockTokens = new ArrayList<>();
        mockTokens.add(token);

        when(connector.generateMseToken(any(), anyString(), anyString(),
                any(), any(), any(), any(), any(), anyLong())).thenReturn(mockTokens);

        IndividualAccountIdentificationNumber iain = new IndividualAccountIdentificationNumber("58000079523");
        EncryptionAlgorithm encryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
        TokenCarrierType tokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.NUMERIC);
        SupplyGroupCode supplyGroupCode = new SupplyGroupCode("600675");
        KeyRevisionNumber keyRevisionNumber = new KeyRevisionNumber(1);
        KeyExpiryNumber keyExpiryNumber = new KeyExpiryNumber(255);
        TariffIndex tariffIndex = new TariffIndex("21");

        MaximumPowerLimit maximumPowerLimit = new MaximumPowerLimit(10);
        PrismHSMConnector.FlagTokenType flagTokenType = PrismHSMConnector.FlagTokenType.SetFlagCTSTest;
        PrismHSMConnector.FlagTokenValue flagTokenValue = PrismHSMConnector.FlagTokenValue.Enable;

        PrismClientFacade facade = new PrismClientFacade(host, port, realm, username, password, connector);
        SetMaximumPowerLimitToken generatedToken = facade.generateSetMaximumPowerLimitToken(REQUEST_ID, maximumPowerLimit,
                                                                                            flagTokenType, flagTokenValue, iain,
                                                                                            encryptionAlgorithm, tokenCarrierType,
                                                                                            supplyGroupCode, keyRevisionNumber,
                                                                                            keyExpiryNumber, tariffIndex);

        assertEquals("test that the correct token class is returned", "Set Maximum Power Limit", generatedToken.getTokenClass().getName());
        assertEquals("test that the correct token class is returned", "SetMaximumPowerLimitTokenSubClass", generatedToken.getTokenSubClass().getName());
        assertEquals("test that the correct token class is returned", "SetMaximumPowerLimit_20", generatedToken.getType());
        assertEquals("test that the correct token class is returned", "67819696861845956084", generatedToken.getTokenNo());
    }

    @Test
    public void testGenerateSetTariffRateToken() throws Exception {

        final String ACCESS_TOKEN = "access-token";
        final String REQUEST_ID = "request-id";
        final String DESCRIPTION = "SetTariffRate";
        final short TOKEN_CLASS = 2;
        final short SUBCLASS = 2;

        when(connector.getClient(anyString(), anyInt())).thenReturn(client);
        when(connector.signInWithPassword(any(), anyString(), anyString(), anyString())).thenReturn(ACCESS_TOKEN);

        Token token = new Token(DRN, PAN, EA, TCT, SGC, KRN, TI, TOKEN_CLASS, SUBCLASS,
                TID, TRANSFER_AMOUNT, IS_RESERVED_TID, DESCRIPTION, STS_UNIT_NAME, SCALED_AMOUNT,
                SCALED_UNIT_NAME, TOKEN_DEC, TOKEN_HEX, IS_SM, VK_KCV);
        List<Token> mockTokens = new ArrayList<>();
        mockTokens.add(token);

        when(connector.generateMseToken(any(), anyString(), anyString(),
                any(), any(), any(), any(), any(), anyLong())).thenReturn(mockTokens);

        IndividualAccountIdentificationNumber iain = new IndividualAccountIdentificationNumber("58000079523");
        EncryptionAlgorithm encryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
        TokenCarrierType tokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.NUMERIC);
        SupplyGroupCode supplyGroupCode = new SupplyGroupCode("600675");
        KeyRevisionNumber keyRevisionNumber = new KeyRevisionNumber(1);
        KeyExpiryNumber keyExpiryNumber = new KeyExpiryNumber(255);
        TariffIndex tariffIndex = new TariffIndex("21");

        PrismClientFacade facade = new PrismClientFacade(host, port, realm, username, password, connector);
        SetTariffRateToken generatedToken = facade.generateSetTariffRateToken(REQUEST_ID, iain,
                                                                            encryptionAlgorithm, tokenCarrierType,
                                                                            supplyGroupCode, keyRevisionNumber,
                                                                            keyExpiryNumber, tariffIndex);

        assertEquals("test that the correct token class is returned", "Set Tariff Rate", generatedToken.getTokenClass().getName());
        assertEquals("test that the correct token class is returned", "SetTariffRateTokenSubClass", generatedToken.getTokenSubClass().getName());
        assertEquals("test that the correct token class is returned", "SetTariffRate_22", generatedToken.getType());
        assertEquals("test that the correct token class is returned", "67819696861845956084", generatedToken.getTokenNo());
    }

    @Test
    public void testGenerateSetWaterMeterFactorToken() throws Exception {

        final String ACCESS_TOKEN = "access-token";
        final String REQUEST_ID = "request-id";
        final String DESCRIPTION = "SetWaterMeterFactor";
        final short TOKEN_CLASS = 2;
        final short SUBCLASS = 7;

        when(connector.getClient(anyString(), anyInt())).thenReturn(client);
        when(connector.signInWithPassword(any(), anyString(), anyString(), anyString())).thenReturn(ACCESS_TOKEN);

        Token token = new Token(DRN, PAN, EA, TCT, SGC, KRN, TI, TOKEN_CLASS, SUBCLASS,
                TID, TRANSFER_AMOUNT, IS_RESERVED_TID, DESCRIPTION, STS_UNIT_NAME, SCALED_AMOUNT,
                SCALED_UNIT_NAME, TOKEN_DEC, TOKEN_HEX, IS_SM, VK_KCV);
        List<Token> mockTokens = new ArrayList<>();
        mockTokens.add(token);

        when(connector.generateMseToken(any(), anyString(), anyString(),
                any(), any(), any(), any(), any(), anyLong())).thenReturn(mockTokens);

        IndividualAccountIdentificationNumber iain = new IndividualAccountIdentificationNumber("58000079523");
        EncryptionAlgorithm encryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
        TokenCarrierType tokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.NUMERIC);
        SupplyGroupCode supplyGroupCode = new SupplyGroupCode("600675");
        KeyRevisionNumber keyRevisionNumber = new KeyRevisionNumber(1);
        KeyExpiryNumber keyExpiryNumber = new KeyExpiryNumber(255);
        TariffIndex tariffIndex = new TariffIndex("21");

        PrismClientFacade facade = new PrismClientFacade(host, port, realm, username, password, connector);
        SetWaterMeterFactorToken generatedToken = facade.generateSetWaterMeterFactorToken(REQUEST_ID, iain,
                                                                                            encryptionAlgorithm, tokenCarrierType,
                                                                                            supplyGroupCode, keyRevisionNumber,
                                                                                            keyExpiryNumber, tariffIndex);

        assertEquals("test that the correct token class is returned", "Set Water Meter Factor", generatedToken.getTokenClass().getName());
        assertEquals("test that the correct token class is returned", "SetWaterMeterFactorTokenSubClass", generatedToken.getTokenSubClass().getName());
        assertEquals("test that the correct token class is returned", "SetWaterMeterFactor_27", generatedToken.getType());
        assertEquals("test that the correct token class is returned", "67819696861845956084", generatedToken.getTokenNo());
    }

    @Test
    public void testThatVerifyTokenReturnsCorrectly() throws Exception {

        final String ACCESS_TOKEN = "access-token";
        final String DESCRIPTION = "SetWaterMeterFactor";
        final String REQUEST_ID = "request-id";
        final short TOKEN_CLASS = 2;
        final short SUBCLASS = 7;

        when(connector.getClient(anyString(), anyInt())).thenReturn(client);
        when(connector.signInWithPassword(any(), anyString(), anyString(), anyString())).thenReturn(ACCESS_TOKEN);

        Token token = new Token(DRN, PAN, EA, TCT, SGC, KRN, TI, TOKEN_CLASS, SUBCLASS,
                TID, TRANSFER_AMOUNT, IS_RESERVED_TID, DESCRIPTION, STS_UNIT_NAME, SCALED_AMOUNT,
                SCALED_UNIT_NAME, TOKEN_DEC, TOKEN_HEX, IS_SM, VK_KCV);

        VerifyResult result = new VerifyResult();
        result.setToken(token);
        result.setValidationResult("EVerify.Ok");

        when(connector.verifyToken(any(), anyString(), anyString(), anyString(), any())).thenReturn(result);

        IndividualAccountIdentificationNumber iain = new IndividualAccountIdentificationNumber("58000079523");
        EncryptionAlgorithm encryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
        TokenCarrierType tokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.NUMERIC);
        SupplyGroupCode supplyGroupCode = new SupplyGroupCode("600675");
        KeyRevisionNumber keyRevisionNumber = new KeyRevisionNumber(1);
        KeyExpiryNumber keyExpiryNumber = new KeyExpiryNumber(255);
        TariffIndex tariffIndex = new TariffIndex("21");

        PrismClientFacade facade = new PrismClientFacade(host, port, realm, username, password, connector);

        ke.co.nectar.token.domain.token.Token generatedToken = facade.verifyToken(REQUEST_ID, iain,
                                                                                encryptionAlgorithm, tokenCarrierType,
                                                                                supplyGroupCode, keyRevisionNumber,
                                                                                keyExpiryNumber, tariffIndex, TOKEN_DEC);

        assertEquals("test that token class name is correct", "Set Water Meter Factor", generatedToken.getTokenClass().getName());
        assertEquals("test that token subclass is correct", "SetWaterMeterFactorTokenSubClass", generatedToken.getTokenSubClass().getName());
        assertEquals("test that request id is correct", "request-id", generatedToken.getRequestID());
        assertEquals("test that token no is correct", "67819696861845956084", generatedToken.getTokenNo());
        assertEquals("test that type is correct", "SetWaterMeterFactor_27", generatedToken.getType());
    }
}
