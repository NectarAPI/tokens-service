package ke.co.nectar.token.domain.token;

import ke.co.nectar.ca.keys.utils.HexByteUtils;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.Misty1AlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.keys.vending.VendingUniqueDESKey;
import ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber;
import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.domain.token.class2.SetMaximumPowerLimitToken;
import ke.co.nectar.token.generators.decoderkeygenerator.DecoderKeyGeneratorAlgorithm04;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.SetMaximumPowerLimitTokenGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;

public class STSComplianceTests_STS_531_1_0_04_CTSA12 {

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
    private MaximumPowerLimit maximumPowerLimit;

    private IssuerIdentificationNumber issuerIdentificationNumber;
    private IndividualAccountIdentificationNumber iain;
    private Misty1AlgorithmEncryptionAlgorithm misty1EncryptionAlgorithm = new Misty1AlgorithmEncryptionAlgorithm();
    private final BaseDate BASE_DATE = BaseDate._1993;
    private DecoderKey decoderKey;

    private SetMaximumPowerLimitToken token = null;
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

            BaseDate baseDate = BaseDate._1993;
            decoderKeyAlgorithm04Generator = new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                                                keyType, keyRevisionNumber, misty1EncryptionAlgorithm,
                                                                                meterPrimaryAccountNumber, vudk);
            decoderKey = this.decoderKeyAlgorithm04Generator.generate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void step1CTSA12Test() {
        try {
            String dateTime = "01/04/2004 07:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPowerLimit = new MaximumPowerLimit(256);
            token = generateSetMaximumPowerLimitToken(dateOfIssue, maximumPowerLimit);
            Assert.assertEquals("test that generated token is correct", "58601433826945463485", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2CTSA12Test() {
        try {
            String dateTime = "01/04/2004 07:05:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPowerLimit = new MaximumPowerLimit(16383);
            token = generateSetMaximumPowerLimitToken(dateOfIssue, maximumPowerLimit);
            Assert.assertEquals("test that generated token is correct", "13997395479415026219", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step3CTSA12Test() {
        try {
            String dateTime = "01/04/2004 07:10:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPowerLimit = new MaximumPowerLimit(16384);
            token = generateSetMaximumPowerLimitToken(dateOfIssue, maximumPowerLimit);
            Assert.assertEquals("test that generated token is correct", "18037738085263294820", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step4CTSA12Test() {
        try {
            String dateTime = "01/04/2004 07:15:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPowerLimit = new MaximumPowerLimit(20000);
            token = generateSetMaximumPowerLimitToken(dateOfIssue, maximumPowerLimit);
            Assert.assertEquals("test that generated token is correct", "06738975074638745925", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step5CTSA12Test() {
        try {
            String dateTime = "01/04/2004 07:20:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPowerLimit = new MaximumPowerLimit(180223);
            token = generateSetMaximumPowerLimitToken(dateOfIssue, maximumPowerLimit);
            Assert.assertEquals("test that generated token is correct", "66354091567942569601", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step6CTSA12Test() {
        try {
            String dateTime = "01/04/2004 07:25:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPowerLimit = new MaximumPowerLimit(180224);
            token = generateSetMaximumPowerLimitToken(dateOfIssue, maximumPowerLimit);
            Assert.assertEquals("test that generated token is correct", "62853351521017859877", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step7CTSA12Test() {
        try {
            String dateTime = "01/04/2004 07:30:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPowerLimit = new MaximumPowerLimit(1818623);
            token = generateSetMaximumPowerLimitToken(dateOfIssue, maximumPowerLimit);
            Assert.assertEquals("test that generated token is correct", "40984149905900332649", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step8CTSA12Test() {
        try {
            String dateTime = "01/04/2004 07:35:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPowerLimit = new MaximumPowerLimit(1818624);
            token = generateSetMaximumPowerLimitToken(dateOfIssue, maximumPowerLimit);
            Assert.assertEquals("test that generated token is correct", "26882838654580901052", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step9CTSA12Test() {
        try {
            String dateTime = "01/04/2004 07:40:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPowerLimit = new MaximumPowerLimit(18201624);
            token = generateSetMaximumPowerLimitToken(dateOfIssue, maximumPowerLimit);
            Assert.assertEquals("test that generated token is correct", "29255956459122361629", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private SetMaximumPowerLimitToken generateSetMaximumPowerLimitToken(DateTime timeOfIssue, MaximumPowerLimit maximumPowerLimit) {
        SetMaximumPowerLimitToken token = null;
        try {
            String requestID = "request_id";
            TokenIdentifier tokenIdentifier = new TokenIdentifier(timeOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            DecoderKeyGeneratorAlgorithm04 decoderKeyAlgorithm04Generator = new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                                                                                keyType, keyRevisionNumber, misty1EncryptionAlgorithm,
                                                                                                                meterPrimaryAccountNumber, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm04Generator.generate();
            SetMaximumPowerLimitTokenGenerator generator = new SetMaximumPowerLimitTokenGenerator(requestID,
                                                                                                    randomNo,
                                                                                                    tokenIdentifier,
                                                                                                    maximumPowerLimit,
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
