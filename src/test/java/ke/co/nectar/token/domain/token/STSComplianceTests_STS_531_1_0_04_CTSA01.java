package ke.co.nectar.token.domain.token;

import ke.co.nectar.ca.keys.utils.HexByteUtils;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.Misty1AlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.keys.vending.VendingKey;
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
import org.junit.Test;

import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class STSComplianceTests_STS_531_1_0_04_CTSA01 {

    private Misty1AlgorithmEncryptionAlgorithm misty1EncryptionAlgorithm = new Misty1AlgorithmEncryptionAlgorithm();
    private KeyExpiryNumber keyExpiryNumber;
    private KeyType keyType;
    private SupplyGroupCode supplyGroupCode;
    private TariffIndex tariffIndex;
    private KeyRevisionNumber keyRevisionNumber;
    private VendingUniqueDESKey vudk;
    private BaseDate baseDate ;

    @Before
    public void setUp() {
        try {
            keyExpiryNumber = new KeyExpiryNumber(255);
            keyType = new KeyType(2);
            supplyGroupCode = new SupplyGroupCode("123457");
            tariffIndex = new TariffIndex("01");
            vudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab949494949494949401234567"));
            baseDate = BaseDate._1993;
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step1Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            keyRevisionNumber = new KeyRevisionNumber(1);
            DecoderKey decoderKey = generateDecoderKey(meterPrimaryAccountNumber,
                                                        keyType,
                                                        supplyGroupCode,
                                                        tariffIndex,
                                                        keyRevisionNumber,
                                                        vudk,
                                                        baseDate);
            String requestID = "request_id";
            String dateTime = "01/03/2004 13:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._1993);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            Amount amountPurchased = new Amount(0.1);

            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            misty1EncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "59386323472137426967", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            keyRevisionNumber = new KeyRevisionNumber(1);
            DecoderKey decoderKey = generateDecoderKey(meterPrimaryAccountNumber,
                                                        keyType,
                                                        supplyGroupCode,
                                                        tariffIndex,
                                                        keyRevisionNumber,
                                                        vudk,
                                                        baseDate);
            String requestID = "request_id";
            String dateTime = "01/03/2004 13:05:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._1993);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            Amount amountPurchased = new Amount(0.1);

            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                misty1EncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "47186281207955155808", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step3Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            keyRevisionNumber = new KeyRevisionNumber(1);
            DecoderKey decoderKey = generateDecoderKey(meterPrimaryAccountNumber,
                                                        keyType,
                                                        supplyGroupCode,
                                                        tariffIndex,
                                                        keyRevisionNumber,
                                                        vudk,
                                                        baseDate);
            String requestID = "request_id";
            String dateTime = "01/03/2004 13:10:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._1993);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            Amount amountPurchased = new Amount(0.1);

            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                            amountPurchased, keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            misty1EncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "52059556253782091701", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step5Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("000001000000000082", NO_METER_PAN_VALIDATION);
            keyRevisionNumber = new KeyRevisionNumber(1);
            DecoderKey decoderKey = generateDecoderKey(meterPrimaryAccountNumber,
                                                        keyType,
                                                        supplyGroupCode,
                                                        tariffIndex,
                                                        keyRevisionNumber,
                                                        vudk,
                                                        baseDate);
            String requestID = "request_id";
            String dateTime = "01/03/2004 13:20:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._1993);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            Amount amountPurchased = new Amount(0.1);

            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            misty1EncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "25453597494250138964", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step6Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("000001000000000082", NO_METER_PAN_VALIDATION);
            keyRevisionNumber = new KeyRevisionNumber(1);
            DecoderKey decoderKey = generateDecoderKey(meterPrimaryAccountNumber,
                                                        keyType,
                                                        supplyGroupCode,
                                                        tariffIndex,
                                                        keyRevisionNumber,
                                                        vudk,
                                                        baseDate);
            String requestID = "request_id";
            String dateTime = "01/03/2004 13:25:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._1993);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            Amount amountPurchased = new Amount(0.1);

            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                misty1EncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "41136669315054818626", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step7Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("000001000000000082", NO_METER_PAN_VALIDATION);
            keyRevisionNumber = new KeyRevisionNumber(1);
            DecoderKey decoderKey = generateDecoderKey(meterPrimaryAccountNumber,
                                                        keyType,
                                                        supplyGroupCode,
                                                        tariffIndex,
                                                        keyRevisionNumber,
                                                        vudk,
                                                        baseDate);
            String requestID = "request_id";
            String dateTime = "01/03/2004 13:30:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._1993);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            Amount amountPurchased = new Amount(0.1);

            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                            amountPurchased, keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            misty1EncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "22735221987748758248", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step9Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            keyRevisionNumber = new KeyRevisionNumber(4);
            baseDate = BaseDate._2014;
            DecoderKey decoderKey = generateDecoderKey(meterPrimaryAccountNumber,
                                                        keyType,
                                                        supplyGroupCode,
                                                        tariffIndex,
                                                        keyRevisionNumber,
                                                        vudk,
                                                        baseDate);
            String requestID = "request_id";
            String dateTime = "01/01/2014 08:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._2014);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            Amount amountPurchased = new Amount(0.1);

            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            misty1EncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "13444522537517076834", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step10Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            keyRevisionNumber = new KeyRevisionNumber(4);
            baseDate = BaseDate._2014;
            DecoderKey decoderKey = generateDecoderKey(meterPrimaryAccountNumber,
                                                        keyType,
                                                        supplyGroupCode,
                                                        tariffIndex,
                                                        keyRevisionNumber,
                                                        vudk,
                                                        baseDate);
            String requestID = "request_id";
            String dateTime = "01/01/2014 08:05:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._2014);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            Amount amountPurchased = new Amount(0.1);

            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                misty1EncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "03477912490596695895", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step11Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            keyRevisionNumber = new KeyRevisionNumber(4);
            baseDate = BaseDate._2014;
            DecoderKey decoderKey = generateDecoderKey(meterPrimaryAccountNumber,
                                                        keyType,
                                                        supplyGroupCode,
                                                        tariffIndex,
                                                        keyRevisionNumber,
                                                        vudk,
                                                        baseDate);
            String requestID = "request_id";
            String dateTime = "01/01/2014 08:10:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._2014);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            Amount amountPurchased = new Amount(0.1);

            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                    amountPurchased, keyExpiryNumber,
                                                                                                    decoderKey,
                                                                                                    misty1EncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "06094571069413075467", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step13Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            keyRevisionNumber = new KeyRevisionNumber(5);
            baseDate = BaseDate._2035;
            DecoderKey decoderKey = generateDecoderKey(meterPrimaryAccountNumber,
                                                        keyType,
                                                        supplyGroupCode,
                                                        tariffIndex,
                                                        keyRevisionNumber,
                                                        vudk,
                                                        baseDate);
            String requestID = "request_id";
            String dateTime = "01/01/2035 08:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._2035);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            Amount amountPurchased = new Amount(0.1);

            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            misty1EncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "11907826947753213480", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step14Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            keyRevisionNumber = new KeyRevisionNumber(5);
            baseDate = BaseDate._2035;
            DecoderKey decoderKey = generateDecoderKey(meterPrimaryAccountNumber,
                                                        keyType,
                                                        supplyGroupCode,
                                                        tariffIndex,
                                                        keyRevisionNumber,
                                                        vudk,
                                                        baseDate);
            String requestID = "request_id";
            String dateTime = "01/01/2035 08:05:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._2035);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            Amount amountPurchased = new Amount(0.1);

            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                misty1EncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "19640099949346431996", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step15Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            keyRevisionNumber = new KeyRevisionNumber(5);
            baseDate = BaseDate._2035;
            DecoderKey decoderKey = generateDecoderKey(meterPrimaryAccountNumber,
                                                        keyType,
                                                        supplyGroupCode,
                                                        tariffIndex,
                                                        keyRevisionNumber,
                                                        vudk,
                                                        baseDate);
            String requestID = "request_id";
            String dateTime = "01/01/2035 08:10:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._2035);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            Amount amountPurchased = new Amount(0.1);

            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                            amountPurchased, keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            misty1EncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "11741155092330337876", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private DecoderKey generateDecoderKey(MeterPrimaryAccountNumber meterPrimaryAccountNumber,
                                          KeyType keyType,
                                          SupplyGroupCode supplyGroupCode,
                                          TariffIndex tariffIndex,
                                          KeyRevisionNumber keyRevisionNumber,
                                          VendingKey vudk,
                                          BaseDate baseDate) throws Exception {
        DecoderKeyGeneratorAlgorithm04 decoderKeyAlgorithm04Generator
                = new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                     keyType, keyRevisionNumber, misty1EncryptionAlgorithm,
                                                     meterPrimaryAccountNumber, vudk);
        return decoderKeyAlgorithm04Generator.generate();
    }
}