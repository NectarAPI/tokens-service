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

import static junit.framework.TestCase.fail;
import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;
import static org.junit.Assert.assertEquals;

public class STSComplianceTests_STS_531_1_0_02_CTSA10 {

    private MeterPrimaryAccountNumber meterPrimaryAccountNumber;
    private TokenCarrierType magneticCardTokenCarrierType;
    private TokenCarrierType virtualCardTokenCarrierType;

    private VendingUniqueDESKey vudk;
    private SupplyGroupCode supplyGroupCode;
    private TariffIndex tariffIndex;
    private KeyRevisionNumber keyRevisionNumber;
    private KeyType keyType;
    private KeyExpiryNumber keyExpiryNumber;
    private Amount amount;

    private IssuerIdentificationNumber issuerIdentificationNumber;
    private IndividualAccountIdentificationNumber iain;
    private StandardTransferAlgorithmEncryptionAlgorithm staEncryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
    private final BaseDate BASE_DATE = BaseDate._1993;
    private DecoderKey decoderKey;

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

            DecoderKeyGeneratorAlgorithm02 decoderKeyAlgorithm02Generator = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                                                keyRevisionNumber, issuerIdentificationNumber,
                                                                                                                iain, vudk);

            decoderKey = decoderKeyAlgorithm02Generator.generate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void step1CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:30:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(25.6);
            TransferElectricityCreditToken token = generateTransferElectricityToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "26456622012185850752", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:35:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(1638.3);
            TransferElectricityCreditToken token = generateTransferElectricityToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "02194538019157867319", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step3CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:40:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(1638.4);
            TransferElectricityCreditToken token = generateTransferElectricityToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "49848950875249585071", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step4CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:45:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(2000.0);
            TransferElectricityCreditToken token = generateTransferElectricityToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "71997443697501228179", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step5CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:50:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(18022.3);
            TransferElectricityCreditToken token = generateTransferElectricityToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "58589277912776864555", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step6CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:55:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(18022.4);
            TransferElectricityCreditToken token = generateTransferElectricityToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "16328229234437142451", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step7CTSA10Test() {
        try {
            String dateTime = "01/04/2004 01:44:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(181862.3);
            TransferElectricityCreditToken token = generateTransferElectricityToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "45001756646344378677", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step8CTSA10Test() {
        try {
            String dateTime = "01/04/2004 01:49:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(181862.4);
            TransferElectricityCreditToken token = generateTransferElectricityToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "15810488151857362998", token.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step9CTSA10Test() {
        try {
            String dateTime = "01/04/2004 01:54:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(1820162.4);
            TransferElectricityCreditToken token = generateTransferElectricityToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "42222423067848970276", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step10CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:30:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(25.6);
            TransferWaterCreditToken token = generateTransferWaterToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "44275716003808438853", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step11CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:35:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(1638.3);
            TransferWaterCreditToken token = generateTransferWaterToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "18252763951429538816", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step12CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:40:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(1638.4);
            TransferWaterCreditToken token = generateTransferWaterToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "27315011099256270180", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step13CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:45:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(2000.0);
            TransferWaterCreditToken token = generateTransferWaterToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "63822263014624002901", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step14CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:50:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(18022.3);
            TransferWaterCreditToken token = generateTransferWaterToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "09641054211308025667", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step15CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:55:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(18022.4);
            TransferWaterCreditToken token = generateTransferWaterToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "44446096428101342168", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step16CTSA10Test() {
        try {
            String dateTime = "01/04/2004 01:44:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(181862.3);
            TransferWaterCreditToken token = generateTransferWaterToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "28708466031297612806", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step17CTSA10Test() {
        try {
            String dateTime = "01/04/2004 01:49:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(181862.4);
            TransferWaterCreditToken token = generateTransferWaterToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "10341114843091227763", token.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step18CTSA10Test() {
        try {
            String dateTime = "01/04/2004 01:54:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(1820162.4);
            TransferWaterCreditToken token = generateTransferWaterToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "05942777944950076038", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step19CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:30:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(25.6);
            TransferGasCreditToken token = generateTransferGasToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "55421978213496083894", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step20CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:35:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(1638.3);
            TransferGasCreditToken token = generateTransferGasToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "19542256788533803049", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step21CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:40:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(1638.4);
            TransferGasCreditToken token = generateTransferGasToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "00240351248989131846", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step22CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:45:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(2000.0);
            TransferGasCreditToken token = generateTransferGasToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "09767096307961035977", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step23CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:50:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(18022.3);
            TransferGasCreditToken token = generateTransferGasToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "48113663060937699726", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step24CTSA10Test() {
        try {
            String dateTime = "01/04/2004 00:55:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(18022.4);
            TransferGasCreditToken token = generateTransferGasToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "03981404420542897416", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step25CTSA10Test() {
        try {
            String dateTime = "01/04/2004 01:44:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(181862.3);
            TransferGasCreditToken token = generateTransferGasToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "14196243940519813244", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step26CTSA10Test() {
        try {
            String dateTime = "01/04/2004 01:49:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(181862.4);
            TransferGasCreditToken token = generateTransferGasToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "49902394914324621132", token.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step27CTSA10Test() {
        try {
            String dateTime = "01/04/2004 01:54:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            amount = new Amount(1820162.4);
            TransferGasCreditToken token = generateTransferGasToken(dateOfIssue, amount);
            assertEquals("test that generated token is correct", "13610552847224033160", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private TransferElectricityCreditToken generateTransferElectricityToken(DateTime timeOfIssue, Amount amount) {
        TransferElectricityCreditToken token = null;
        try {
            String requestID = "request_id";
            TokenIdentifier tokenIdentifier = new TokenIdentifier(timeOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();

            DecoderKeyGeneratorAlgorithm02 decoderKeyAlgorithm02Generator = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                                                keyRevisionNumber, issuerIdentificationNumber,
                                                                                                                iain, vudk);

            DecoderKey decoderKey = decoderKeyAlgorithm02Generator.generate();
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amount, keyExpiryNumber, decoderKey,
                                                                                                            staEncryptionAlgorithm);
            token = generator.generate();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        return token;
    }

    private TransferWaterCreditToken generateTransferWaterToken(DateTime timeOfIssue, Amount amount) {
        TransferWaterCreditToken token = null;
        try {
            String requestID = "request_id";
            TokenIdentifier tokenIdentifier = new TokenIdentifier(timeOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();

            DecoderKeyGeneratorAlgorithm02 decoderKeyAlgorithm02Generator = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                                                keyRevisionNumber, issuerIdentificationNumber,
                                                                                                                iain, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm02Generator.generate();
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amount, keyExpiryNumber, decoderKey,
                                                                                                staEncryptionAlgorithm);
            token = generator.generate();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        return token;
    }

    private TransferGasCreditToken generateTransferGasToken(DateTime timeOfIssue, Amount amount) {
        TransferGasCreditToken token = null;
        try {
            String requestID = "request_id";
            TokenIdentifier tokenIdentifier = new TokenIdentifier(timeOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            DecoderKeyGeneratorAlgorithm02 decoderKeyAlgorithm02Generator = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                                                keyRevisionNumber, issuerIdentificationNumber,
                                                                                                                iain, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm02Generator.generate();
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amount, keyExpiryNumber, decoderKey,
                                                                                                staEncryptionAlgorithm);
            token = generator.generate();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        return token;
    }
}
