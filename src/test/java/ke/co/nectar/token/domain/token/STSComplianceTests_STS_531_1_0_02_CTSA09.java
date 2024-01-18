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
import org.junit.Before;
import org.junit.Test;

import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class STSComplianceTests_STS_531_1_0_02_CTSA09 {

    private MeterPrimaryAccountNumber meterPrimaryAccountNumber;
    private TokenCarrierType magneticCardTokenCarrierType;
    private TokenCarrierType virtualCardTokenCarrierType;

    private VendingUniqueDESKey vudk;
    private DecoderKeyGeneratorAlgorithm02 decoderKeyGenerator;
    private SupplyGroupCode supplyGroupCode;
    private TariffIndex tariffIndex;
    private KeyRevisionNumber keyRevisionNumber;
    private KeyType keyType;
    private KeyExpiryNumber keyExpiryNumber;
    private Register register;

    private StandardTransferAlgorithmEncryptionAlgorithm staEncryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
    private final BaseDate BASE_DATE = BaseDate._1993;

    @Before
    public void setUp() {
        try {
            magneticCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.MAGNETIC_CARD);
            virtualCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.VIRTUAL_TOKEN_CARRIER);

            BitString registerBitString = new BitString(0xffff);
            registerBitString.setLength(16);
            register = new Register(registerBitString);
            vudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab"));
            supplyGroupCode = new SupplyGroupCode("123456");
            tariffIndex = new TariffIndex("01");
            keyRevisionNumber = new KeyRevisionNumber(1);
            keyType = new KeyType(2);
            keyExpiryNumber = new KeyExpiryNumber(255);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void step1CTSA09Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            IssuerIdentificationNumber issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            IndividualAccountIdentificationNumber iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();

            DecoderKeyGeneratorAlgorithm02 decoderKeyAlgorithm02Generator = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                                                keyRevisionNumber, issuerIdentificationNumber,
                                                                                                                iain, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm02Generator.generate();

            String requestID = "request_id";
            String dateTime = "29/03/2004 00:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            BitString registerBitString = new BitString(0xffff);
            registerBitString.setLength(16);
            Register register = new Register(registerBitString);

            ClearCreditTokenGenerator generator = new ClearCreditTokenGenerator(requestID,
                                                                                randomNo,
                                                                                tokenIdentifier,
                                                                                register,
                                                                                decoderKey,
                                                                                staEncryptionAlgorithm);
            ClearCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "10208942296089183521", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2CTSA09Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            IssuerIdentificationNumber issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            IndividualAccountIdentificationNumber iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();

            DecoderKeyGeneratorAlgorithm02 decoderKeyAlgorithm02Generator = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                                                keyRevisionNumber, issuerIdentificationNumber,
                                                                                                                iain, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm02Generator.generate();

            String requestID = "request_id";
            String dateTime = "29/03/2004 00:01:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            BitString registerBitString = new BitString(0xffff);
            registerBitString.setLength(16);
            Register register = new Register(registerBitString);

            ClearCreditTokenGenerator generator = new ClearCreditTokenGenerator(requestID,
                                                                                randomNo,
                                                                                tokenIdentifier,
                                                                                register,
                                                                                decoderKey,
                                                                                staEncryptionAlgorithm);
            ClearCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "20388873191656671689", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step3CTSA09Test() {
        /**
         * This test simulates that if a multiple token requests are
         * produced in the same minute, then an additional minute should
         * be added to the TID. After the set of tokens generated for
         * a minute are generated, the CDU should revert to current system
         * clock time (IEC 62055-51 v2.0 Section 6.3.5.3 Multiple tokens generated within the same minute)
         * Since multiple nodes are used in the generation of tokens in this implementation,
         * zookeeper will be used to monitor the currently processed set of tokens
         * and make the necessary additions. A CDU will therefore have to query
         * zookeeper for the processing status of a token, if present and make the required
         * TID manipulations before generating the token
         */
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            IssuerIdentificationNumber issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            IndividualAccountIdentificationNumber iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();

            DecoderKeyGeneratorAlgorithm02 decoderKeyAlgorithm02Generator = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                                                keyRevisionNumber, issuerIdentificationNumber,
                                                                                                                iain, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm02Generator.generate();

            String requestID = "request_id";
            String dateTime = "29/03/2004 00:03:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            BitString registerBitString = new BitString(0xffff);
            registerBitString.setLength(16);
            Register register = new Register(registerBitString);

            // * Simualates the first minute *
            ClearCreditTokenGenerator generator = new ClearCreditTokenGenerator(requestID,
                                                                                randomNo,
                                                                                tokenIdentifier,
                                                                                register,
                                                                                decoderKey,
                                                                                staEncryptionAlgorithm);
            ClearCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "43348834939937913498", token.getTokenNo());

            // * Simulates the second minute *
            dateTime = "29/03/2004 00:04:00";
            dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            generator = new ClearCreditTokenGenerator(requestID,
                                                        randomNo,
                                                        tokenIdentifier,
                                                        register,
                                                        decoderKey,
                                                        staEncryptionAlgorithm);
            ClearCreditToken token1 = generator.generate();
            assertEquals("test that the correct token is generated", "51681104150374451564", token1.getTokenNo());

            // * Simulates the third minute *
            dateTime = "29/03/2004 00:05:00";
            dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            generator = new ClearCreditTokenGenerator(requestID,
                                                        randomNo,
                                                        tokenIdentifier,
                                                        register,
                                                        decoderKey,
                                                        staEncryptionAlgorithm);
            ClearCreditToken token2 = generator.generate();
            assertEquals("test that the correct token is generated", "50526247723864280405", token2.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
