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
import ke.co.nectar.token.domain.token.class2.SetMaximumPowerLimitToken;
import ke.co.nectar.token.generators.decoderkeygenerator.DecoderKeyGeneratorAlgorithm02;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.ClearCreditTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.SetMaximumPowerLimitTokenGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;

public class STSComplianceTests_STS_531_1_0_02_CTSA03_CTSA04 {

    private MeterPrimaryAccountNumber meterPrimaryAccountNumber;
    private KeyExpiryNumber keyExpiryNumber;

    private IssuerIdentificationNumber issuerIdentificationNumber;
    private IndividualAccountIdentificationNumber iain;

    private KeyType keyType;
    private SupplyGroupCode supplyGroupCode;
    private TariffIndex tariffIndex;
    private KeyRevisionNumber keyRevisionNumber;
    private VendingUniqueDESKey vudk;
    private DecoderKeyGeneratorAlgorithm02 decoderKeyAlgorithm02Generator;
    private StandardTransferAlgorithmEncryptionAlgorithm staEncryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
    private final BaseDate BASE_DATE = BaseDate._1993;

    @Before
    public void setUp() {
        try {
            meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            keyExpiryNumber = new KeyExpiryNumber(255);
            issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            keyType = new KeyType(2);
            supplyGroupCode = new SupplyGroupCode("123456");
            tariffIndex = new TariffIndex("01");
            keyRevisionNumber = new KeyRevisionNumber(1);
            vudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab"));

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

            decoderKeyAlgorithm02Generator = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                keyRevisionNumber, issuerIdentificationNumber, iain, vudk);

            DecoderKey decoderKey = decoderKeyAlgorithm02Generator.generate();
            SetMaximumPowerLimitTokenGenerator generator = new SetMaximumPowerLimitTokenGenerator(requestID,
                                                                                                    randomNo,
                                                                                                    tokenIdentifier,
                                                                                                    maximumPowerLimit,
                                                                                                    decoderKey,
                                                                                                    staEncryptionAlgorithm  );
            SetMaximumPowerLimitToken token = generator.generate();
            assertEquals("test that the correct token is generated", "50901894209860263092", token.getTokenNo());
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

            decoderKeyAlgorithm02Generator = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                keyRevisionNumber, issuerIdentificationNumber, iain, vudk);

            DecoderKey decoderKey = decoderKeyAlgorithm02Generator.generate();
            ClearCreditTokenGenerator generator = new ClearCreditTokenGenerator(requestID,
                                                                                randomNo,
                                                                                tokenIdentifier,
                                                                                register,
                                                                                decoderKey,
                                                                                staEncryptionAlgorithm);
            ClearCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "29511990995826640868", token.getTokenNo());
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

            decoderKeyAlgorithm02Generator = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                keyRevisionNumber, issuerIdentificationNumber, iain, vudk);

            String requestID = "request_id";
            String dateTime = "28/03/2004 09:20:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            BitString registerBitString = new BitString(0xffff);
            registerBitString.setLength(16);
            Register register = new Register(registerBitString);
            DecoderKey decoderKey = decoderKeyAlgorithm02Generator.generate();
            ClearCreditTokenGenerator generator = new ClearCreditTokenGenerator(requestID,
                                                                                randomNo,
                                                                                tokenIdentifier,
                                                                                register,
                                                                                decoderKey,
                                                                                staEncryptionAlgorithm);
            ClearCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "69986678528351463847", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
