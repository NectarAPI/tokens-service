package ke.co.nectar.token.domain.token;

import ke.co.nectar.ca.keys.utils.HexByteUtils;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.StandardTransferAlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.keys.vending.VendingUniqueDESKey;
import ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber;
import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.domain.token.class0.TransferWaterCreditToken;
import ke.co.nectar.token.generators.decoderkeygenerator.DecoderKeyGeneratorAlgorithm02;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class0.TransferWaterCreditTokenGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class STSComplianceTests_STS_531_1_0_02_CTSA21 {
    private MeterPrimaryAccountNumber meterPrimaryAccountNumber;
    private TokenCarrierType magneticCardTokenCarrierType;
    private TokenCarrierType virtualCardTokenCarrierType;

    private VendingUniqueDESKey vudk;
    private DecoderKeyGeneratorAlgorithm02 decoderKeyAlgorithm02Generator;
    private SupplyGroupCode supplyGroupCode;
    private TariffIndex tariffIndex;
    private KeyRevisionNumber keyRevisionNumber;
    private KeyType keyType;
    private KeyExpiryNumber keyExpiryNumber;

    private IssuerIdentificationNumber issuerIdentificationNumber;
    private IndividualAccountIdentificationNumber iain;
    private StandardTransferAlgorithmEncryptionAlgorithm staEncryptionAlgorithm;
    private final BaseDate BASE_DATE = BaseDate._1993;
    private DecoderKey decoderKey;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        try {
            meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            magneticCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.MAGNETIC_CARD);
            virtualCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.VIRTUAL_TOKEN_CARRIER);
            tariffIndex = new TariffIndex("01");
            issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            staEncryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
            keyRevisionNumber = new KeyRevisionNumber(1);
            vudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab"));
            supplyGroupCode = new SupplyGroupCode("123456");
            keyType = new KeyType(2);
            keyExpiryNumber = new KeyExpiryNumber(255);
            decoderKeyAlgorithm02Generator = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                keyRevisionNumber, issuerIdentificationNumber,
                                                                                iain, vudk);
            decoderKey = decoderKeyAlgorithm02Generator.generate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void step1CTSA20Test() {
        try {
            String dateTime = "23/05/2005 10:01:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1);
            TransferWaterCreditToken token = generateTransferWaterCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "15415954497866603070", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2CTSA21Test() {
        try {
            String dateTime = "24/05/2005 10:02:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(16383);
            TransferWaterCreditToken token = generateTransferWaterCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "37577557589608193918", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step3CTSA20Test() {
        try {
            String dateTime = "23/04/2006 23:10:03";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(16384);
            TransferWaterCreditToken token = generateTransferWaterCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "64605231735471529556", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step4CTSA20Test() {
        try {
            String dateTime = "24/04/2006 10:04:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(180224);
            TransferWaterCreditToken token = generateTransferWaterCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "61396462165248534316", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step5CTSA20Test() {
        try {
            String dateTime = "24/04/2006 11:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1818624);
            TransferWaterCreditToken token = generateTransferWaterCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "27442853458265256809", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step6CTSA20Test() {
        try {
            String dateTime = "24/04/2006 11:01:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(182262);
            TransferWaterCreditToken token = generateTransferWaterCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "60366325560961667086", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step7CTSA20Test() {
        try {
            String dateTime = "24/04/2006 11:02:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(182264);
            TransferWaterCreditToken token = generateTransferWaterCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "49837510367703553651", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step8CTSA20Test() {
        try {
            String dateTime = "24/04/2006 11:03:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(182024);
            TransferWaterCreditToken token = generateTransferWaterCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "65892074689465900210", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step9CTSA20Test() {
        try {
            String dateTime = "24/04/2006 11:04:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(182.5);
            TransferWaterCreditToken token = generateTransferWaterCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "31056801750423680816", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step10CTSA20Test() {
        try {
            String dateTime = "24/04/2006 11:05:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1673.8);
            TransferWaterCreditToken token = generateTransferWaterCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "20363556736975891316", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step11CTSA20Test() {
        try {
            String dateTime = "24/04/2006 20:10:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(0);
            TransferWaterCreditToken token = generateTransferWaterCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "16838670950350007721", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step12CTSA20Test() {
        try {
            String dateTime = "24/04/2006 11:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(17654.3);
            TransferWaterCreditToken token = generateTransferWaterCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "20884251612329507769", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step13CTSA20Test() {
        try {
            String dateTime = "24/04/2006 20:12:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(123);
            TransferWaterCreditToken token = generateTransferWaterCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "29433837651179279051", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step14CTSA20Test() {
        try {
            String dateTime = "24/04/2006 20:14:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(180224);
            TransferWaterCreditToken token = generateTransferWaterCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "56221045810819914754", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step15CTSA20Test() {
        try {
            String dateTime = "12/05/2006 20:15:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(15671.4);
            TransferWaterCreditToken token = generateTransferWaterCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "07426208391727345260", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private TransferWaterCreditToken generateTransferWaterCreditToken(DateTime timeOfIssue, Amount amount)
            throws Exception {
        TokenIdentifier tokenIdentifier = new TokenIdentifier(timeOfIssue, BASE_DATE);
        BitString randomBitString = new BitString(0x5l);
        randomBitString.setLength(4);
        RandomNo randomNo = new RandomNo(randomBitString); // Tokens in tests generated using RND = 5
        String requestID = "request_id";
        TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                            amount, keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            staEncryptionAlgorithm);
        TransferWaterCreditToken token = generator.generate();
        return token;
    }
}
