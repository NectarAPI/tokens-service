package ke.co.nectar.token.domain.token;

import ke.co.nectar.ca.keys.utils.HexByteUtils;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.Misty1AlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.keys.vending.VendingUniqueDESKey;
import ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber;
import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.domain.token.class2.ClearCreditToken;
import ke.co.nectar.token.generators.decoderkeygenerator.DecoderKeyGeneratorAlgorithm04;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.ClearCreditTokenGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;

public class STSComplianceTests_STS_531_1_0_04_CTSA14 {

    private MeterPrimaryAccountNumber meterPrimaryAccountNumber;
    private TokenCarrierType magneticCardTokenCarrierType;
    private TokenCarrierType virtualCardTokenCarrierType;

    private VendingUniqueDESKey vudk;
    private DecoderKeyGeneratorAlgorithm04 decoderKeyAlgorithm04Generator;
    private SupplyGroupCode supplyGroupCode;
    private TariffIndex tariffIndex;
    private KeyRevisionNumber keyRevisionNumber;
    private KeyType keyType;
    private KeyExpiryNumber keyExpiryNumber;
    private Register registerToClear;

    private IssuerIdentificationNumber issuerIdentificationNumber;
    private IndividualAccountIdentificationNumber iain;
    private Misty1AlgorithmEncryptionAlgorithm misty1EncryptionAlgorithm = new Misty1AlgorithmEncryptionAlgorithm();
    private final BaseDate BASE_DATE = BaseDate._1993;
    private DecoderKey decoderKey;

    private ClearCreditToken token = null;
    private BaseDate baseDate;

    @Before
    public void setUp() {
        try {
            baseDate = BaseDate._1993;
            meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            magneticCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.MAGNETIC_CARD);
            virtualCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.VIRTUAL_TOKEN_CARRIER);
            issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();

            vudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab949494949494949401234567"));
            supplyGroupCode = new SupplyGroupCode("123457");
            tariffIndex = new TariffIndex("01");
            keyRevisionNumber = new KeyRevisionNumber(1);
            keyType = new KeyType(2);
            keyExpiryNumber = new KeyExpiryNumber(255);
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            decoderKeyAlgorithm04Generator = new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                                                keyType, keyRevisionNumber, misty1EncryptionAlgorithm,
                                                                                meterPrimaryAccountNumber, vudk);
            decoderKey = decoderKeyAlgorithm04Generator.generate();

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
            Assert.assertEquals("test that generated token is correct", "06768431134031257922", token.getTokenNo());
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
            Assert.assertEquals("test that generated token is correct", "59338638600207707879", token.getTokenNo());
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
            Assert.assertEquals("test that generated token is correct", "48872720007959408665", token.getTokenNo());
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
            Assert.assertEquals("test that generated token is correct", "51810809087550125677", token.getTokenNo());
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
            Assert.assertEquals("test that generated token is correct", "13848051316848177124", token.getTokenNo());
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
            Assert.assertEquals("test that generated token is correct", "63506294564247105352", token.getTokenNo());
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

            DecoderKeyGeneratorAlgorithm04 decoderKeyAlgorithm04Generator = new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                                                                                keyType, keyRevisionNumber, misty1EncryptionAlgorithm,
                                                                                                                meterPrimaryAccountNumber, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm04Generator.generate();
            ClearCreditTokenGenerator generator = new ClearCreditTokenGenerator(requestID,
                                                                                randomNo,
                                                                                tokenIdentifier,
                                                                                registerToClear,
                                                                                decoderKey,
                                                                                misty1EncryptionAlgorithm);
            token = generator.generate();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        return token;
    }

}
