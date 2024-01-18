package ke.co.nectar.token.domain.token;

import ke.co.nectar.ca.keys.utils.HexByteUtils;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.StandardTransferAlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.keys.vending.VendingUniqueDESKey;
import ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber;
import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.domain.token.class0.TransferElectricityCreditToken;
import ke.co.nectar.token.domain.token.class0.TransferGasCreditToken;
import ke.co.nectar.token.domain.token.class0.TransferWaterCreditToken;
import ke.co.nectar.token.generators.decoderkeygenerator.DecoderKeyGeneratorAlgorithm04;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class0.TransferElectricityCreditTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class0.TransferGasCreditTokenGenerator;
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

public class STSComplianceTests_STS_531_1_0_02_CTSA25 {

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
    private IssuerIdentificationNumber issuerIdentificationNumber;
    private IndividualAccountIdentificationNumber iain;
    private StandardTransferAlgorithmEncryptionAlgorithm staEncryptionAlgorithm;
    private DecoderKey decoderKey;
    private RandomNo randomNo;

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

            vudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab949494949494949401234567"));
            supplyGroupCode = new SupplyGroupCode("123457");
            keyType = new KeyType(2);
            keyExpiryNumber = new KeyExpiryNumber(255);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            randomNo = new RandomNo(randomBitString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void step1CTSA25Test() {
        try {
            BaseDate baseDate = BaseDate._1993;
            keyRevisionNumber = new KeyRevisionNumber(1);
            decoderKey = generateDecoderKey(baseDate, keyRevisionNumber);

            String requestID = "request_id";
            String dateTime = "01/01/2009 08:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._1993);
            Amount amountPurchased = new Amount(0.1);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID,
                                                                                                            tokenIdentifier,
                                                                                                            randomNo,
                                                                                                            amountPurchased,
                                                                                                            keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "15697331168573253829", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2CTSA20Test() {
        try {
            BaseDate baseDate = BaseDate._1993;
            keyRevisionNumber = new KeyRevisionNumber(1);
            decoderKey = generateDecoderKey(baseDate, keyRevisionNumber);

            String requestID = "request_id";
            String dateTime = "01/01/2009 08:05:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._1993);
            Amount amountPurchased = new Amount(0.1);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID,
                                                                                                tokenIdentifier,
                                                                                                randomNo,
                                                                                                amountPurchased,
                                                                                                keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "56727749990719585416", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step3CTSA20Test() {
        try {
            BaseDate baseDate = BaseDate._1993;
            keyRevisionNumber = new KeyRevisionNumber(1);
            decoderKey = generateDecoderKey(baseDate, keyRevisionNumber);

            String requestID = "request_id";
            String dateTime = "01/01/2009 08:10:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._1993);
            Amount amountPurchased = new Amount(0.1);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID,
                                                                                            tokenIdentifier,
                                                                                            randomNo,
                                                                                            amountPurchased,
                                                                                            keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "25938479605175185937", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step5CTSA20Test() {
        try {
            BaseDate baseDate = BaseDate._2014;
            keyRevisionNumber = new KeyRevisionNumber(4);
            decoderKey = generateDecoderKey(baseDate, keyRevisionNumber);

            String requestID = "request_id";
            String dateTime = "01/01/2014 08:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._2014);
            Amount amountPurchased = new Amount(0.1);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID,
                                                                                                            tokenIdentifier,
                                                                                                            randomNo,
                                                                                                            amountPurchased,
                                                                                                            keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "20324881626382980759", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step6CTSA20Test() {
        try {
            BaseDate baseDate = BaseDate._2014;
            keyRevisionNumber = new KeyRevisionNumber(4);
            decoderKey = generateDecoderKey(baseDate, keyRevisionNumber);

            String requestID = "request_id";
            String dateTime = "01/01/2014 08:05:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._2014);
            Amount amountPurchased = new Amount(0.1);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID,
                                                                                                tokenIdentifier,
                                                                                                randomNo,
                                                                                                amountPurchased,
                                                                                                keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "09907513011694393160", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step7CTSA20Test() {
        try {
            BaseDate baseDate = BaseDate._2014;
            keyRevisionNumber = new KeyRevisionNumber(4);
            decoderKey = generateDecoderKey(baseDate, keyRevisionNumber);

            String requestID = "request_id";
            String dateTime = "01/01/2014 08:10:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._2014);
            Amount amountPurchased = new Amount(0.1);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID,
                                                                                            tokenIdentifier,
                                                                                            randomNo,
                                                                                            amountPurchased,
                                                                                            keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "50054427724775110925", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step9CTSA20Test() {
        try {
            BaseDate baseDate = BaseDate._2035;
            keyRevisionNumber = new KeyRevisionNumber(5);
            decoderKey = generateDecoderKey(baseDate, keyRevisionNumber);

            String requestID = "request_id";
            String dateTime = "01/01/2035 08:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._2035);
            Amount amountPurchased = new Amount(0.1);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID,
                                                                                                            tokenIdentifier,
                                                                                                            randomNo,
                                                                                                            amountPurchased,
                                                                                                            keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "09239624803025986815", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step10CTSA20Test() {
        try {
            BaseDate baseDate = BaseDate._2035;
            keyRevisionNumber = new KeyRevisionNumber(5);
            decoderKey = generateDecoderKey(baseDate, keyRevisionNumber);

            String requestID = "request_id";
            String dateTime = "01/01/2035 08:05:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._2035);
            Amount amountPurchased = new Amount(0.1);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID,
                                                                                                tokenIdentifier,
                                                                                                randomNo,
                                                                                                amountPurchased,
                                                                                                keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "31176414469542247929", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step11CTSA20Test() {
        try {
            BaseDate baseDate = BaseDate._2035;
            keyRevisionNumber = new KeyRevisionNumber(5);
            decoderKey = generateDecoderKey(baseDate, keyRevisionNumber);

            String requestID = "request_id";
            String dateTime = "01/01/2035 08:10:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._2035);
            Amount amountPurchased = new Amount(0.1);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID,
                                                                                            tokenIdentifier,
                                                                                            randomNo,
                                                                                            amountPurchased,
                                                                                            keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "13512126869939531125", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private DecoderKey generateDecoderKey(BaseDate baseDate, KeyRevisionNumber keyRevisionNumber) throws Exception {
        decoderKeyAlgorithm04Generator = new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                                            keyType, keyRevisionNumber,
                                                                            staEncryptionAlgorithm,
                                                                            meterPrimaryAccountNumber, vudk);
        return decoderKeyAlgorithm04Generator.generate();
    }
}
