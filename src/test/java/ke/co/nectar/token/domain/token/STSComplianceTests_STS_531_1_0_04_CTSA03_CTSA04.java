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
import ke.co.nectar.token.domain.token.class2.SetMaximumPowerLimitToken;
import ke.co.nectar.token.generators.decoderkeygenerator.DecoderKeyGeneratorAlgorithm04;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.ClearCreditTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.SetMaximumPowerLimitTokenGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;

public class STSComplianceTests_STS_531_1_0_04_CTSA03_CTSA04 {

    private MeterPrimaryAccountNumber meterPrimaryAccountNumber;
    private KeyExpiryNumber keyExpiryNumber;

    private IssuerIdentificationNumber issuerIdentificationNumber;
    private IndividualAccountIdentificationNumber iain;

    private KeyType keyType;
    private SupplyGroupCode supplyGroupCode;
    private TariffIndex tariffIndex;
    private KeyRevisionNumber keyRevisionNumber;
    private VendingUniqueDESKey vudk;
    private DecoderKeyGeneratorAlgorithm04 decoderKeyAlgorithm04Generator;
    private Misty1AlgorithmEncryptionAlgorithm misty1EncryptionAlgorithm = new Misty1AlgorithmEncryptionAlgorithm();
    private final BaseDate BASE_DATE = BaseDate._1993;

    @Before
    public void setUp() {
        try {
            meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            keyExpiryNumber = new KeyExpiryNumber(255);
            issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            keyType = new KeyType(2);
            supplyGroupCode = new SupplyGroupCode("123457");
            tariffIndex = new TariffIndex("01");
            keyRevisionNumber = new KeyRevisionNumber(1);
            vudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab949494949494949401234567"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void step1CTSA03Test() {
        try {
            String requestID = "request_id";
            String dateTime = "28/03/2004 09:01:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            MaximumPowerLimit maximumPowerLimit = new MaximumPowerLimit(1000);
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            BaseDate baseDate = BaseDate._1993;
            decoderKeyAlgorithm04Generator = new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                                                keyType, keyRevisionNumber, misty1EncryptionAlgorithm,
                                                                                meterPrimaryAccountNumber, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm04Generator.generate();
            SetMaximumPowerLimitTokenGenerator generator = new SetMaximumPowerLimitTokenGenerator(requestID,
                                                                                                    randomNo,
                                                                                                    tokenIdentifier,
                                                                                                    maximumPowerLimit,
                                                                                                    decoderKey,
                    misty1EncryptionAlgorithm);
            SetMaximumPowerLimitToken token = generator.generate();
            assertEquals("test that the correct token is generated", "26521936751055502278", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step1CTSA04Test() {
        try {
            String requestID = "request_id";
            String dateTime = "28/03/2004 09:15:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            BitString registerBitString = new BitString(0xffff);
            registerBitString.setLength(16);
            Register register = new Register(registerBitString);
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            BaseDate baseDate = BaseDate._1993;
            decoderKeyAlgorithm04Generator = new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                                                keyType, keyRevisionNumber, misty1EncryptionAlgorithm,
                                                                                meterPrimaryAccountNumber, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm04Generator.generate();
            ClearCreditTokenGenerator generator = new ClearCreditTokenGenerator(requestID,
                                                                                randomNo,
                                                                                tokenIdentifier,
                                                                                register,
                                                                                decoderKey,
                    misty1EncryptionAlgorithm);
            ClearCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "59725289138639529749", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2CTSA04Test() {
        try {
            meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("000001000000000082", NO_METER_PAN_VALIDATION);
            issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            BaseDate baseDate = BaseDate._1993;
            decoderKeyAlgorithm04Generator = new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                                                keyType, keyRevisionNumber, misty1EncryptionAlgorithm,
                                                                                meterPrimaryAccountNumber, vudk);
            String requestID = "request_id";
            String dateTime = "28/03/2004 09:16:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            BitString registerBitString = new BitString(0xffff);
            registerBitString.setLength(16);
            Register register = new Register(registerBitString);
            DecoderKey decoderKey = decoderKeyAlgorithm04Generator.generate();
            ClearCreditTokenGenerator generator = new ClearCreditTokenGenerator(requestID,
                                                                                randomNo,
                                                                                tokenIdentifier,
                                                                                register,
                                                                                decoderKey,
                    misty1EncryptionAlgorithm);
            ClearCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "43917986274716482997", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
