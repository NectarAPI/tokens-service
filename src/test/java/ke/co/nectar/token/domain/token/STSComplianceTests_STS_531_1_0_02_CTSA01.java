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
import ke.co.nectar.token.generators.decoderkeygenerator.DecoderKeyGeneratorAlgorithm02;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class0.TransferElectricityCreditTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class0.TransferGasCreditTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class0.TransferWaterCreditTokenGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class STSComplianceTests_STS_531_1_0_02_CTSA01 {

    private StandardTransferAlgorithmEncryptionAlgorithm staEncryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
    private KeyExpiryNumber keyExpiryNumber;
    private KeyType keyType;
    private SupplyGroupCode supplyGroupCode;
    private TariffIndex tariffIndex;
    private KeyRevisionNumber keyRevisionNumber;
    private VendingUniqueDESKey vudk;

    @Before
    public void setUp() {
        try {
            keyExpiryNumber = new KeyExpiryNumber(255);
            keyType = new KeyType(2);
            supplyGroupCode = new SupplyGroupCode("123456");
            tariffIndex = new TariffIndex("01");
            keyRevisionNumber = new KeyRevisionNumber(1);
            vudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab"));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step1Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            IssuerIdentificationNumber issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            IndividualAccountIdentificationNumber iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            DecoderKeyGeneratorAlgorithm02 decoderKeyAlgorithm02Generator
                    = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                         keyRevisionNumber, issuerIdentificationNumber, iain, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm02Generator.generate();

            String requestID = "request_id";
            String dateTime = "01/03/2004 13:55:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._1993);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            Amount amountPurchased = new Amount(0.1);
            String generatedDecoderKey = new BigInteger(1, decoderKey.getKeyData()).toString(16);

            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct decoder key is generated", "6ff35b9d1f3453e6", generatedDecoderKey);
            assertEquals("test that the correct token is generated", "23716100501183194197", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("000001000000000082", NO_METER_PAN_VALIDATION);
            IssuerIdentificationNumber issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            IndividualAccountIdentificationNumber iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            DecoderKeyGeneratorAlgorithm02 decoderKeyAlgorithm02Generator
                                        = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                            keyRevisionNumber, issuerIdentificationNumber, iain, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm02Generator.generate();

            String requestID = "request_id";
            String dateTime = "01/03/2004 14:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._1993);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            Amount amountPurchased = new Amount(0.1);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "67206107716095682372", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step3Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            IssuerIdentificationNumber issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            IndividualAccountIdentificationNumber iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            DecoderKeyGeneratorAlgorithm02 decoderKeyAlgorithm02Generator
                                                = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                     keyRevisionNumber, issuerIdentificationNumber, iain, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm02Generator.generate();

            String requestID = "request_id";
            String dateTime = "01/03/2004 14:05:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._1993);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            Amount amountPurchased = new Amount(0.1);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "42502136492215507402", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step4Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("000001000000000082", NO_METER_PAN_VALIDATION);
            IssuerIdentificationNumber issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            IndividualAccountIdentificationNumber iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            DecoderKeyGeneratorAlgorithm02 decoderKeyAlgorithm02Generator
                                                = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                     keyRevisionNumber, issuerIdentificationNumber, iain, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm02Generator.generate();

            String requestID = "request_id";
            String dateTime = "01/03/2004 14:10:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._1993);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            Amount amountPurchased = new Amount(0.1);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "09109691696351271646", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step5Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            IssuerIdentificationNumber issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            IndividualAccountIdentificationNumber iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            DecoderKeyGeneratorAlgorithm02 decoderKeyAlgorithm02Generator
                                            = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                 keyRevisionNumber, issuerIdentificationNumber, iain, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm02Generator.generate();

            String requestID = "request_id";
            String dateTime = "01/03/2004 14:15:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._1993);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            Amount amountPurchased = new Amount(0.1);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                            amountPurchased, keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "67586531586639825066", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step6Test() {
        try {
            MeterPrimaryAccountNumber meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("000001000000000082", NO_METER_PAN_VALIDATION);
            IssuerIdentificationNumber issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            IndividualAccountIdentificationNumber iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            DecoderKeyGeneratorAlgorithm02 decoderKeyAlgorithm02Generator
                                                        = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                             keyRevisionNumber, issuerIdentificationNumber, iain, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm02Generator.generate();

            String requestID = "request_id";
            String dateTime = "01/03/2004 14:20:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._1993);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString); // Tokens in tests generated using RND = 5
            Amount amountPurchased = new Amount(0.1);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                            amountPurchased, keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "35758660990071466853", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}