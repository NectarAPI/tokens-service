package ke.co.nectar.token.domain.token;

import ke.co.nectar.ca.keys.utils.HexByteUtils;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.Misty1AlgorithmEncryptionAlgorithm;
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
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;
import static org.junit.Assert.assertEquals;

public class STSComplianceTests_STS_531_1_0_04_CTSA10 {

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
    private Misty1AlgorithmEncryptionAlgorithm misty1EncryptionAlgorithm = new Misty1AlgorithmEncryptionAlgorithm();
    private final BaseDate BASE_DATE = BaseDate._1993;
    private DecoderKey decoderKey;

    @Before
    public void setUp() {
        try {
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
            DecoderKeyGeneratorAlgorithm04 decoderKeyAlgorithm04Generator = new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                                                                                keyType, keyRevisionNumber, misty1EncryptionAlgorithm,
                                                                                                                meterPrimaryAccountNumber, vudk);
            decoderKey = decoderKeyAlgorithm04Generator.generate();

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
            assertEquals("test that generated token is correct", "63638916334124550935", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "06736163174944595611", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "45798100519745983712", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "08362487434932116862", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "33933484656539803471", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "40075282658655256325", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "00383912203740575049", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "32272089791250978565", token.getTokenNo());

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
            assertEquals("test that generated token is correct", "44964671935361377806", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "08844040967758161989", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "41707569065487034639", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "61826851589850099670", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "72478269627942954182", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "12311110365531155223", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "68979561791500831417", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "36130214068866912790", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "49560207524955523897", token.getTokenNo());

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
            assertEquals("test that generated token is correct", "47575512827888817714", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "34672027639183365663", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "70087969935165138265", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "60875440664020961982", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "16605563156243942378", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "16614677156798904170", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "49208263727993294856", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "38374198840578367339", token.getTokenNo());
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
            assertEquals("test that generated token is correct", "34384673009506006509", token.getTokenNo());

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
            assertEquals("test that generated token is correct", "40332503820747813730", token.getTokenNo());
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
            BaseDate baseDate = BaseDate._1993;
            DecoderKeyGeneratorAlgorithm04 decoderKeyAlgorithm04Generator = new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                                                                                keyType, keyRevisionNumber, misty1EncryptionAlgorithm,
                                                                                                                meterPrimaryAccountNumber, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm04Generator.generate();
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amount, keyExpiryNumber, decoderKey,
                                                                                                            misty1EncryptionAlgorithm);
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

            BaseDate baseDate = BaseDate._1993;
            DecoderKeyGeneratorAlgorithm04 decoderKeyAlgorithm04Generator = new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                                                                                keyType, keyRevisionNumber, misty1EncryptionAlgorithm,
                                                                                                                meterPrimaryAccountNumber, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm04Generator.generate();
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amount, keyExpiryNumber, decoderKey,
                                                                                                misty1EncryptionAlgorithm);
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
            BaseDate baseDate = BaseDate._1993;
            DecoderKeyGeneratorAlgorithm04 decoderKeyAlgorithm04Generator = new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                                                                                keyType, keyRevisionNumber, misty1EncryptionAlgorithm,
                                                                                                                meterPrimaryAccountNumber, vudk);
            DecoderKey decoderKey = decoderKeyAlgorithm04Generator.generate();
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amount, keyExpiryNumber, decoderKey,
                                                                                                misty1EncryptionAlgorithm);
            token = generator.generate();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        return token;
    }
}
