package ke.co.nectar.token.domain.token;

import ke.co.nectar.ca.keys.utils.HexByteUtils;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.Misty1AlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.keys.vending.VendingUniqueDESKey;
import ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber;
import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.domain.token.class2.SetMaximumPhasePowerUnbalanceLimitToken;
import ke.co.nectar.token.generators.decoderkeygenerator.DecoderKeyGeneratorAlgorithm04;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.SetMaximumPhasePowerUnbalanceLimitTokenGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.fail;
import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;

public class STSComplianceTests_STS_531_1_0_04_CTSA13 {

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
    private MaximumPhasePowerUnbalanceLimit maximumPhasePowerUnbalanceLimit;

    private IssuerIdentificationNumber issuerIdentificationNumber;
    private IndividualAccountIdentificationNumber iain;
    private Misty1AlgorithmEncryptionAlgorithm misty1EncryptionAlgorithm = new Misty1AlgorithmEncryptionAlgorithm();
    private final BaseDate BASE_DATE = BaseDate._1993;
    private DecoderKey decoderKey;

    private SetMaximumPhasePowerUnbalanceLimitToken token = null;
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
            DecoderKeyGeneratorAlgorithm04 decoderKeyAlgorithm04Generator = new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                                                                                keyType, keyRevisionNumber, misty1EncryptionAlgorithm,
                                                                                                                meterPrimaryAccountNumber, vudk);
            decoderKey = decoderKeyAlgorithm04Generator.generate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void step1CTSA13Test() {
        try {
            String dateTime = "01/04/2004 08:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPhasePowerUnbalanceLimit = new MaximumPhasePowerUnbalanceLimit(256);
            token = generateMaximumPhasePowerUnbalanceLimitToken(dateOfIssue, maximumPhasePowerUnbalanceLimit);
            Assert.assertEquals("test that generated token is correct", "23509767215559230954", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2CTSA13Test() {
        try {
            String dateTime = "01/04/2004 08:05:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPhasePowerUnbalanceLimit = new MaximumPhasePowerUnbalanceLimit(16383);
            token = generateMaximumPhasePowerUnbalanceLimitToken(dateOfIssue, maximumPhasePowerUnbalanceLimit);
            Assert.assertEquals("test that generated token is correct", "70247784567899484178", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step3CTSA13Test() {
        try {
            String dateTime = "01/04/2004 08:10:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPhasePowerUnbalanceLimit = new MaximumPhasePowerUnbalanceLimit(16384);
            token = generateMaximumPhasePowerUnbalanceLimitToken(dateOfIssue, maximumPhasePowerUnbalanceLimit);
            Assert.assertEquals("test that generated token is correct", "36304916073545542570", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step4CTSA13Test() {
        try {
            String dateTime = "01/04/2004 08:15:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPhasePowerUnbalanceLimit = new MaximumPhasePowerUnbalanceLimit(20000);
            token = generateMaximumPhasePowerUnbalanceLimitToken(dateOfIssue, maximumPhasePowerUnbalanceLimit);
            Assert.assertEquals("test that generated token is correct", "11066406403853302811", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step5CTSA13Test() {
        try {
            String dateTime = "01/04/2004 08:20:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPhasePowerUnbalanceLimit = new MaximumPhasePowerUnbalanceLimit(180223);
            token = generateMaximumPhasePowerUnbalanceLimitToken(dateOfIssue, maximumPhasePowerUnbalanceLimit);
            Assert.assertEquals("test that generated token is correct", "25512033356036953640", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step6CTSA13Test() {
        try {
            String dateTime = "01/04/2004 08:25:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPhasePowerUnbalanceLimit = new MaximumPhasePowerUnbalanceLimit(180224);
            token = generateMaximumPhasePowerUnbalanceLimitToken(dateOfIssue, maximumPhasePowerUnbalanceLimit);
            Assert.assertEquals("test that generated token is correct", "13785431682542358258", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step7CTSA13Test() {
        try {
            String dateTime = "01/04/2004 08:30:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPhasePowerUnbalanceLimit = new MaximumPhasePowerUnbalanceLimit(1818623);
            token = generateMaximumPhasePowerUnbalanceLimitToken(dateOfIssue, maximumPhasePowerUnbalanceLimit);
            Assert.assertEquals("test that generated token is correct", "06889958680004063872", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step8CTSA13Test() {
        try {
            String dateTime = "01/04/2004 08:35:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPhasePowerUnbalanceLimit = new MaximumPhasePowerUnbalanceLimit(1818624);
            token = generateMaximumPhasePowerUnbalanceLimitToken(dateOfIssue, maximumPhasePowerUnbalanceLimit);
            Assert.assertEquals("test that generated token is correct", "27410125084608663818", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step9CTSA13Test() {
        try {
            String dateTime = "01/04/2004 08:40:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            maximumPhasePowerUnbalanceLimit = new MaximumPhasePowerUnbalanceLimit(18201624);
            token = generateMaximumPhasePowerUnbalanceLimitToken(dateOfIssue, maximumPhasePowerUnbalanceLimit);
            Assert.assertEquals("test that generated token is correct", "60786080230724485517", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    private SetMaximumPhasePowerUnbalanceLimitToken generateMaximumPhasePowerUnbalanceLimitToken(DateTime timeOfIssue,
                                                                                                 MaximumPhasePowerUnbalanceLimit maximumPhasePowerUnbalanceLimit) {
        SetMaximumPhasePowerUnbalanceLimitToken token = null;
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
            SetMaximumPhasePowerUnbalanceLimitTokenGenerator generator
                                        = new SetMaximumPhasePowerUnbalanceLimitTokenGenerator(requestID,
                                                                                                randomNo,
                                                                                                tokenIdentifier,
                                                                                                Optional.of(maximumPhasePowerUnbalanceLimit),
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
