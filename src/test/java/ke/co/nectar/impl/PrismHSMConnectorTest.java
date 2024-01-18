package ke.co.nectar.impl;

import ke.co.nectar.hsm.prism.*;
import ke.co.nectar.hsm.prism.impl.PrismHSMConnector;
import ke.co.nectar.token.domain.ManufacturerCode;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

;

public class PrismHSMConnectorTest {

    private TokenApi.Client client;
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

    @Before
    public void setUp() {
        client = mock(TokenApi.Client.class);
        connector = new PrismHSMConnector();
    }

    @Test
    public void testThatPingsAreMade() throws Exception {
        String resp = "response";
        when(client.ping(anyInt(), anyString())).thenReturn(resp);
        assertEquals("test that response is returned", "response",
                        connector.ping(client, 100, "ping"));
    }

    @Test
    public void testThatGetStatusIsReturned() throws Exception {
        // [NodeStatus(info={'ApiType': 'vending', 'Counter': '8643', 'FirmwareId': 'STS65V10',
        // 'HardwareId': 'Prism-TSM250-1', 'ModuleId': '94001632', 'TxCounter': '93080', 'port.Class': 'SERIAL_CR',
        // 'port.Logical': 'COM5', 'port.Physical': 'COM5 9600,n,8,1', 'port.Uri': 'com://5/9600,n,8,1',
        // 'smcq.Nonce': '63735BAA', 'smcq.ex.InhibitVendDateLocal': '2023-11-15 23:00:00 WAT',
        // 'smcq.ex.InhibitVendDateUtc': '20231115T220000Z', 'smpd.AcMode': 'AC:NONE',
        // 'smpd.CertMode': 'PCI-HSM,NON-FIPS', 'smpd.FwApi': 'STS6', 'smpd.FwBinary': 'BIN:STS6',
        // 'smpd.FwVer': '6.10.0.0', 'smpd.HwFamily': 'TSM250', 'smpd.Tps': '3', 'smqd.WindowSize': '7',
        // 'smqd.ex.RtcDriftSeconds': '-2450', 'smqd.ex.RtcTimeLocal': '2023-04-06 12:19:04 WAT',
        // 'smqd.ex.RtcTimeUtc': '20230406T111904Z', 'smqi.FirmwareHashHex': 'ACBCA855551CE914',
        // 'smqi.IdSm': 'SMID.1:Prism:94001632:20210204T081427Z:039F2C48F2030174:AEEA', 'smud.Uid': '633CE9020040004A'},
        // alerts=[])]
        Map<String, String> info = new HashMap<>();
        info.put("ApiType", "vending");

        NodeStatus status = new NodeStatus();
        status.setInfo(info);

        List<NodeStatus> nodeStatuses = new ArrayList<>();
        nodeStatuses.add(status);

        when(client.getStatus(anyString(), anyString())).thenReturn(nodeStatuses);

        List<NodeStatus> statuses = connector.getStatus(client, anyString(), anyString());

        assertEquals("test that node status is returned", "vending",
                statuses.get(0).getInfo().get("ApiType"));

    }

    @Test
    public void testThatFetchTokenResultReturnsResult() throws Exception {
        // [Token(drn='58000079523', pan='600727580000795237', ea=7, tct=2, sgc=600675, krn=1, ti=21,
        // tokenClass=2, subclass=10, tid=15915641, transferAmount=64535.0, isReservedTid=False, newConfig=None,
        // description='SetControlElement', stsUnitName='', scaledAmount='64535', scaledUnitName='',
        // tokenDec='05434142000626935411', tokenHex='04B69F35BB6662673',
        // idSm='SMID.1:Prism:94001632:20210204T081427Z:039F2C48F2030174:AEEA', vkKcv='7915C8')]

        String description = "SetControlElement";
        short tokenClass = 2;
        short subclass = 10;

        Token token = new Token(DRN, PAN, EA, TCT, SGC, KRN, TI, tokenClass, subclass,
                TID, TRANSFER_AMOUNT, IS_RESERVED_TID, description, STS_UNIT_NAME, SCALED_AMOUNT,
                SCALED_UNIT_NAME, TOKEN_DEC, TOKEN_HEX, IS_SM, VK_KCV);
        List<Token> tokens = new ArrayList<>();
        tokens.add(token);

        when(client.fetchTokenResult(anyString(), anyString(), anyString())).thenReturn(tokens);

        assertEquals("test that token DRN is returned",
                "58000079523", connector.fetchTokenResult(client,"messageId",
                        "accessToken", "requestId")
                    .get(0).getDrn());
    }

    @Test
    public void testThatGenerateCreditTokenCalculatesCorrectTransferAmount() throws Exception {
        // [Token(drn='58000079523', pan='600727580000795237', ea=7, tct=2, sgc=600675, krn=1, ti=21,
        // tokenClass=0, subclass=0, tid=15915642, transferAmount=102.0, isReservedTid=False, newConfig=None,
        // description='Credit:Electricity', stsUnitName='hWh', scaledAmount='10.2', scaledUnitName='kWh',
        // tokenDec='49168635649622383997', tokenHex='2AA5A159DE2599D7D',
        // idSm='SMID.1:Prism:94001632:20210204T081427Z:039F2C48F2030174:AEEA', vkKcv='7915C8')]

        String description = "Credit:Electricity";
        short tokenClass = 0;
        short subclass = 0;
        float transferAmount = 10.2f;
        long tokenTime = 15891088;

        Token token = new Token(DRN, PAN, EA, TCT, SGC, KRN, TI, tokenClass, subclass,
                                TID, TRANSFER_AMOUNT, IS_RESERVED_TID, description, STS_UNIT_NAME,
                                SCALED_AMOUNT, SCALED_UNIT_NAME, TOKEN_DEC, TOKEN_HEX, IS_SM, VK_KCV);
        List<Token> tokens = new ArrayList<>();
        tokens.add(token);

        when(client.issueCreditToken(anyString(), anyString(), any(), anyShort(),
                anyDouble(), anyLong(), anyLong())).thenReturn(tokens);

        String messageID = "message-id";
        String accessToken = "access-token";
        MeterConfigIn meterConfigIn = new MeterConfigIn();
        meterConfigIn.setAllow3Kct(false);

        List<Token> generatedTokens = connector.generateCreditToken(client, messageID, accessToken, meterConfigIn,
                PrismHSMConnector.CreditTokenType.Electricity, transferAmount, tokenTime);

        assertEquals("test that tokens are generated", "58000079523", generatedTokens.get(0).getDrn());
        assertEquals("test that token TI is correct", 21, generatedTokens.get(0).getTi());
        assertEquals("test that token EA is correct", 7, generatedTokens.get(0).getEa());
        assertEquals("test that token SGC is correct", 600675, generatedTokens.get(0).getSgc());
        assertEquals("test that token description is correct", "Credit:Electricity", generatedTokens.get(0).getDescription());
        assertEquals("test that SM is correct", "SMID.1:Prism:94001632:20210204T081427Z:039F2C48F2030174:AEEA", generatedTokens.get(0).getIdSm());
        assertEquals("test that tokens PAN is correct", "600727580000795237", generatedTokens.get(0).getPan());
        assertEquals("test that token scaled amount is correct", "10.2", generatedTokens.get(0).getScaledAmount());
        assertEquals("test that token scaled unit name is correct", "kWh", generatedTokens.get(0).getScaledUnitName());
        assertEquals("test that token sts unit name is correct", "hWh", generatedTokens.get(0).getStsUnitName());
        assertEquals("test that token is correct", "67819696861845956084", generatedTokens.get(0).getTokenDec());
        assertEquals("test that token hex is correct", "3AD2FF6F8C100A5F4", generatedTokens.get(0).getTokenHex());
        assertEquals("test that token class is correct", 0, generatedTokens.get(0).getTokenClass());
        assertEquals("test that token subclass is correct", 0, generatedTokens.get(0).getSubclass());
        verify(client, times(1)).issueCreditToken("message-id",
                "access-token", meterConfigIn, (short) 0, 102.0, 15891088, 1);
    }

    @Test
    public void testThatGenerateMseTokenCalculatesCorrectTransferAmount() throws Exception {

        //     # Example response
        //    # [Token(drn='58000079523', pan='600727580000795237', ea=7, tct=2, sgc=600675, krn=1, ti=21, tokenClass=2, subclass=0,
        //    #        tid=15914234, transferAmount=0.0, isReservedTid=False, newConfig=None, description='SetMaximumPowerLimit',
        //    #        stsUnitName='Watt', scaledAmount='0', scaledUnitName='Watt', tokenDec='69523898956325925704',
        //    #        tokenHex='3C4D681BF53F53348', idSm='SMID.1:Prism:94001632:20210204T081427Z:039F2C48F2030174:AEEA',
        //    #        vkKcv='7915C8')]

        short tokenClass = 2;
        short subclass = 0;
        String description = "SetMaximumPowerLimit";
        float maxPower = 10;

        Token token = new Token(DRN, PAN, EA, TCT, SGC, KRN, TI, tokenClass, subclass,
                TID, TRANSFER_AMOUNT, IS_RESERVED_TID, description, STS_UNIT_NAME,
                SCALED_AMOUNT, SCALED_UNIT_NAME, TOKEN_DEC, TOKEN_HEX, IS_SM, VK_KCV);
        List<Token> tokens = new ArrayList<>();
        tokens.add(token);

        when(client.issueMseToken(anyString(), anyString(), any(), anyShort(),
                anyDouble(), anyLong(), anyLong())).thenReturn(tokens);

        String messageID = "message-id";
        String accessToken = "access-token";
        MeterConfigIn meterConfigIn = new MeterConfigIn();
        meterConfigIn.setAllow3Kct(false);

        List<Token> generatedTokens = connector.generateMseToken(client, messageID, accessToken,
                PrismHSMConnector.MseToken.ClearCredit, meterConfigIn, Optional.of(maxPower),
                Optional.of(PrismHSMConnector.FlagTokenType.DetectTamper),
                        Optional.of(PrismHSMConnector.FlagTokenValue.Enable), TID);

        assertEquals("test that tokens are generated", "58000079523",
                generatedTokens.get(0).getDrn());
        verify(client, times(1)).issueMseToken("message-id",
                "access-token", meterConfigIn, (short) 1, 10, 15891088, 1);
    }

    @Test
    public void testThatNMseTokenIsGenerated() throws Exception {

        //  # Example Response
        //    # MeterTestToken(drn='00000000000', pan='600727000000000009', tokenClass=1, subclass=0, control=0, mfrcode=0,
        //    #                description='InitMeterTest', tokenDec='00000000000134220736', tokenHex='00000000008000BC0')
        //    # ---
        //    # MeterTestToken(drn='00000000000', pan='600727000000000009', tokenClass=1, subclass=0, control=0, mfrcode=0,
        //    #                description='InitMeterTest', tokenDec='00000000000134220736', tokenHex='00000000008000BC0')

        MeterTestToken initMeterTestToken = new MeterTestToken();
        initMeterTestToken.setTokenClass((short) 1);
        initMeterTestToken.setSubclass((short) 0);
        initMeterTestToken.setControl(0);
        initMeterTestToken.setMfrcode((short) 0);
        initMeterTestToken.setDescription("InitMeterTest");
        initMeterTestToken.setTokenDec("00000000000134220736");

        String messageID = "message-id";
        String accessToken = "access-token";
        short subclass = 0;
        MeterConfigIn meterConfigIn = new MeterConfigIn();
        meterConfigIn.setAllow3Kct(false);
        PrismHSMConnector.NMseType control = PrismHSMConnector.NMseType.Primary;
        ManufacturerCode manufacturerCode = new ManufacturerCode("00");

        when(client.issueMeterTestToken(anyString(), anyString(), anyShort(),
                anyLong(), anyShort())).thenReturn(initMeterTestToken);

        MeterTestToken token = connector.generateNMseToken(client, messageID, accessToken,
                                                subclass, control, manufacturerCode);

        assertEquals("test that init meter test token class is returned", (short) 1, token.getTokenClass());
        assertEquals("test that init meter test token subclass is returned", (short) 0,  token.getSubclass());
        assertEquals("test that init meter test token control is returned", (short) 0, token.getControl());
        assertEquals("test that init meter test token mfrcode is returned", (short) 0, token.getMfrcode());
        assertEquals("test that init meter test token description is returned", "InitMeterTest", token.getDescription());
        assertEquals("test that init meter test token dec is returned", "00000000000134220736", token.getTokenDec());

    }

    @Test
    public void testThatVerifyTokenWorks() throws Exception {

        //  # Example Response
        //    # VerifyResult(validationResult='EVerify.Ok',
        //    #              token=Token(drn='58000079523', pan='600727580000795237', ea=7, tct=2, sgc=600675, krn=1, ti=21,
        //    #                          tokenClass=0, subclass=0, tid=15891088, transferAmount=102.0, isReservedTid=False,
        //    #                          newConfig=None, description='Credit:Electricity', stsUnitName='hWh', scaledAmount='10.2',
        //    #                          scaledUnitName='kWh', tokenDec='67819696861845956084', tokenHex='3AD2FF6F8C100A5F4',
        //    #                          idSm='SMID.1:Prism:94001632:20210204T081427Z:039F2C48F2030174:AEEA', vkKcv='7915C8'),
        //    #              meterTestToken=None)

        short tokenClass = 2;
        short subclass = 0;
        String description = "SetMaximumPowerLimit";

        Token token = new Token(DRN, PAN, EA, TCT, SGC, KRN, TI, tokenClass, subclass,
                TID, TRANSFER_AMOUNT, IS_RESERVED_TID, description, STS_UNIT_NAME,
                SCALED_AMOUNT, SCALED_UNIT_NAME, TOKEN_DEC, TOKEN_HEX, IS_SM, VK_KCV);

        VerifyResult mockVerifyResult = new VerifyResult();
        mockVerifyResult.setToken(token);

        String messageID = "message-id";
        String accessToken = "access-token";
        MeterConfigIn meterConfigIn = new MeterConfigIn();
        meterConfigIn.setEa((short) 7);
        meterConfigIn.setAllow3Kct(false);
        meterConfigIn.setSgc(600675);
        meterConfigIn.setEa((short) 7);

        when(client.verifyToken(messageID, accessToken, meterConfigIn, TOKEN_DEC)).thenReturn(mockVerifyResult);

        VerifyResult verifyResult = connector.verifyToken(client, messageID, accessToken, TOKEN_DEC, meterConfigIn);

        assertEquals("test that correct drn is returned", "58000079523", verifyResult.getToken().getDrn());
        assertEquals("test that correct sgc is returned", 600675, verifyResult.getToken().getSgc());
        assertEquals("test that correct krn is returned", 1, verifyResult.getToken().getKrn());
        assertEquals("test that correct ea is returned", 7, verifyResult.getToken().getEa());
        assertEquals("test that correct ti is returned", 21, verifyResult.getToken().getTi());
    }
}
