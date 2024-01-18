package ke.co.nectar.token.domain.token;

import ke.co.nectar.ca.keys.utils.HexByteUtils;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.StandardTransferAlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.keys.vending.VendingUniqueDESKey;
import ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber;
import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.domain.token.class2.ClearCreditToken;
import ke.co.nectar.token.generators.decoderkeygenerator.DecoderKeyGeneratorAlgorithm02;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.ClearCreditTokenGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;

public class STSComplianceTests_STS_531_1_0_02_CTSA14 {

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
    private Register registerToClear;

    private IssuerIdentificationNumber issuerIdentificationNumber;
    private IndividualAccountIdentificationNumber iain;
    private StandardTransferAlgorithmEncryptionAlgorithm staEncryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
    private final BaseDate BASE_DATE = BaseDate._1993;
    private DecoderKey decoderKey;

    private ClearCreditToken token = null;

    @Before
    public void setUp() {
        try {
            meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            magneticCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.MAGNETIC_CARD);
            virtualCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.VIRTUAL_TOKEN_CARRIER);
            issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();

            vudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab"));
            supplyGroupCode = new SupplyGroupCode("123456");
            tariffIndex = new TariffIndex("01");
            keyRevisionNumber = new KeyRevisionNumber(1);
            keyType = new KeyType(2);
            keyExpiryNumber = new KeyExpiryNumber(255);
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            decoderKeyAlgorithm02Generator = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                keyRevisionNumber, issuerIdentificationNumber,
                                                                                iain, vudk);
            decoderKey = decoderKeyAlgorithm02Generator.generate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void step1CTSA14Test() {
        try {
            String dateTime = "01/04/2004 09:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            BitString registerBitString = new BitString(0x0l);
            registerBitString.setLength(16);
            registerToClear = new Register(registerBitString);
            token = generateClearCreditToken(dateOfIssue, registerToClear);
            Assert.assertEquals("test that generated token is correct", "24406351748405762287", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2CTSA14Test() {
        try {
            String dateTime = "01/04/2004 09:05:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            BitString registerBitString = new BitString(0xffffl);
            registerBitString.setLength(16);
            registerToClear = new Register(registerBitString);
            token = generateClearCreditToken(dateOfIssue, registerToClear);
            Assert.assertEquals("test that generated token is correct", "48263195037886996694", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step3CTSA14Test() {
        try {
            String dateTime = "01/04/2004 09:10:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            BitString registerBitString = new BitString(0x4l);
            registerBitString.setLength(16);
            registerToClear = new Register(registerBitString);
            token = generateClearCreditToken(dateOfIssue, registerToClear);
            Assert.assertEquals("test that generated token is correct", "17696673116286267663", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step4CTSA14Test() {
        try {
            String dateTime = "01/04/2004 09:15:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            BitString registerBitString = new BitString(0x5l);
            registerBitString.setLength(16);
            registerToClear = new Register(registerBitString);
            token = generateClearCreditToken(dateOfIssue, registerToClear);
            Assert.assertEquals("test that generated token is correct", "47739859634763202644", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step5CTSA14Test() {
        try {
            String dateTime = "01/04/2004 09:20:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            BitString registerBitString = new BitString(0x6l);
            registerBitString.setLength(16);
            registerToClear = new Register(registerBitString);
            token = generateClearCreditToken(dateOfIssue, registerToClear);
            Assert.assertEquals("test that generated token is correct", "23456948011089526127", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step6CTSA14Test() {
        try {
            String dateTime = "01/04/2004 09:25:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            BitString registerBitString = new BitString(0x7l);
            registerBitString.setLength(16);
            registerToClear = new Register(registerBitString);
            token = generateClearCreditToken(dateOfIssue, registerToClear);
            Assert.assertEquals("test that generated token is correct", "51867282903899304686", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private ClearCreditToken generateClearCreditToken(DateTime timeOfIssue,
                                                      Register registerToClear) {
        ClearCreditToken token = null;
        try {
            String requestID = "request_id";
            TokenIdentifier tokenIdentifier = new TokenIdentifier(timeOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString); // Tokens in tests generated using RND = 5
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            decoderKeyAlgorithm02Generator = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                keyRevisionNumber, issuerIdentificationNumber,
                                                                                iain, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm02Generator.generate();
            ClearCreditTokenGenerator generator = new ClearCreditTokenGenerator(requestID,
                                                                                randomNo,
                                                                                tokenIdentifier,
                                                                                registerToClear,
                                                                                decoderKey,
                                                                                staEncryptionAlgorithm);
            token = generator.generate();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        return token;
    }

}
