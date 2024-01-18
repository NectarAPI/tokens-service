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
import ke.co.nectar.token.generators.tokensdecoder.Meter;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class0.TransferElectricityCreditTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class0.TransferGasCreditTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class0.TransferWaterCreditTokenGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertTrue;
import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class STSComplianceTests_Nectar_1 {

    private MeterPrimaryAccountNumber meterPrimaryAccountNumber;

    private VendingUniqueDESKey vudk;
    private DecoderKeyGeneratorAlgorithm02 decoderKeyAlgorithm02Generator;
    private SupplyGroupCode supplyGroupCode;
    private TariffIndex tariffIndex;
    private KeyRevisionNumber keyRevisionNumber;
    private KeyType keyType;
    private KeyExpiryNumber keyExpiryNumber;

    private IssuerIdentificationNumber issuerIdentificationNumber;
    private IndividualAccountIdentificationNumber iain;
    private StandardTransferAlgorithmEncryptionAlgorithm staEncryptionAlgorithm;
    private final BaseDate BASE_DATE = BaseDate._1993;
    private DecoderKey decoderKey;

    private final double MAX_ERROR = 0.00055;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        try {
            meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            tariffIndex = new TariffIndex("01");

            issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            staEncryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
            keyRevisionNumber = new KeyRevisionNumber(1);

            vudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab"));
            supplyGroupCode = new SupplyGroupCode("123456");
            keyType = new KeyType(2);
            keyExpiryNumber = new KeyExpiryNumber(255);

            decoderKeyAlgorithm02Generator = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode,
                                                                                    tariffIndex, keyRevisionNumber,
                                                                                    issuerIdentificationNumber, iain,
                                                                                    vudk);
            decoderKey = decoderKeyAlgorithm02Generator.generate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void step1Test() {
        try {
            String requestID = "request_id";
            String dateTime = "21/04/2004 10:01:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "66475648316756821785", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("66475648316756821785", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferElectricityCreditToken decodedToken = (TransferElectricityCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0000", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2004-04-21T10:01"));
            assertEquals("test that correct units purchased are generated", 1.0, amount, 0.001);
            assertEquals("test that correct CRC is generated", "0011010100000100", crc);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step1ATest() {
        try {
            String requestID = "request_id";
            String dateTime = "21/04/2004 10:01:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "26436017448909825227", token.getTokenNo());

            // -- DECODER

            Meter decoder = new Meter("26436017448909825227", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferWaterCreditToken decodedToken = (TransferWaterCreditToken) decoder.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0001", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2004-04-21T10:01"));
            assertEquals("test that correct units purchased are generated", 1.0, amount, 0.001);
            assertEquals("test that correct CRC is generated", "0011011110010100", crc);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step1BTest() {
        try {
            String requestID = "request_id";
            String dateTime = "21/04/2004 10:01:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                            amountPurchased, keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "47052671496957348697", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("47052671496957348697", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferGasCreditToken decodedToken = (TransferGasCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0010", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2004-04-21T10:01"));
            assertEquals("test that correct units purchased are generated", 1.0, amount, 0.001);
            assertEquals("test that correct CRC is generated", "0011001001100100", crc);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2CTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "21/05/2004 10:02:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(16383);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "14605694188309739537", token.getTokenNo());

            // -- DECODER

            Meter decoder = new Meter("14605694188309739537", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferWaterCreditToken decodedToken = (TransferWaterCreditToken) decoder.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0001", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith( "2004-05-21T10:02:00"));
            assertEquals("test that correct units purchased are generated", 16383, amount, MAX_ERROR * 16383);
            assertEquals("test that correct CRC is generated", "1010110000100001", crc);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2ACTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "21/05/2004 10:02:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(16383);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "36924780240841024731", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("36924780240841024731", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferElectricityCreditToken decodedToken = (TransferElectricityCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0000", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2004-05-21T10:02"));
            assertEquals("test that correct units purchased are generated", 16383, amount, MAX_ERROR * 16383);
            assertEquals("test that correct CRC is generated", "1010111010110001", crc);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    public void step2BCTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "21/05/2004 10:02:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(16383);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                            amountPurchased, keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "62668783003252886770", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("62668783003252886770", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferGasCreditToken decodedToken = (TransferGasCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0010", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2004-05-21T10:02:00"));
            assertEquals("test that correct units purchased are generated", 16383, amount, MAX_ERROR * 16383);
            assertEquals("test that correct CRC is generated", "1010100111010001", crc);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step3CTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "21/04/2005 10:03:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(16384);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "11019866711865218477", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("11019866711865218477", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferGasCreditToken decodedToken = (TransferGasCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0010", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-21T10:03:00"));
            assertEquals("test that correct units purchased are generated", 16384, amount, MAX_ERROR * 16384);
            assertEquals("test that correct CRC is generated", "0001001011111011", crc);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step3ACTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "21/04/2005 10:03:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(16384);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "65724708343212635258", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("65724708343212635258", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferElectricityCreditToken decodedToken = (TransferElectricityCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0000", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-21T10:03"));
            assertEquals("test that correct units purchased are generated", 16384, amount, MAX_ERROR * 16384);
            assertEquals("test that correct CRC is generated", "0001010110011011", crc);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step3BCTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "21/04/2005 10:03:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(16384);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "43068373842621717515", token.getTokenNo());

            // -- DECODER

            Meter decoder = new Meter("43068373842621717515", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferWaterCreditToken decodedToken = (TransferWaterCreditToken) decoder.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0001", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith( "2005-04-21T10:03:00"));
            assertEquals("test that correct units purchased are generated", 16384, amount, MAX_ERROR * 16384);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step4CTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 10:04:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(180224);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "42371551666535254341", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("42371551666535254341", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferElectricityCreditToken decodedToken = (TransferElectricityCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0000", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T10:04"));
            assertEquals("test that correct units purchased are generated", 180224, amount, MAX_ERROR * 180224);
            assertEquals("test that correct CRC is generated", "1010111101101110", crc);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step4ACTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 10:04:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(180224);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "08160869283280106226", token.getTokenNo());

            // -- DECODER

            Meter decoder = new Meter("08160869283280106226", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferWaterCreditToken decodedToken = (TransferWaterCreditToken) decoder.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0001", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith( "2005-04-22T10:04:00"));
            assertEquals("test that correct units purchased are generated", 180224, amount, MAX_ERROR * 180224);
            assertEquals("test that correct CRC is generated", "1010110111111110", crc);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step4BCTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 10:04:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(180224);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                            amountPurchased, keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "67143373817426898432", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("67143373817426898432", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferGasCreditToken decodedToken = (TransferGasCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0010", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T10:04:00"));
            assertEquals("test that correct units purchased are generated", 180224, amount, MAX_ERROR * 180224);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step5CTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1818624);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "70363045748300344617", token.getTokenNo());

            // -- DECODER

            Meter decoder = new Meter("70363045748300344617", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferWaterCreditToken decodedToken = (TransferWaterCreditToken) decoder.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0001", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith( "2005-04-22T11:00:00"));
            assertEquals("test that correct units purchased are generated", 1818624, amount, MAX_ERROR * 1818624);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step5ACTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1818624);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                            amountPurchased, keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "49261723634786610437", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("49261723634786610437", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferGasCreditToken decodedToken = (TransferGasCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0010", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T11:00:00"));
            assertEquals("test that correct units purchased are generated", 1818624, amount, MAX_ERROR * 1818624);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step5BCTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1818624);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "18009632033176370418", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("18009632033176370418", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferElectricityCreditToken decodedToken = (TransferElectricityCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0000", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T11:00"));
            assertEquals("test that correct units purchased are generated", 1818624, amount, MAX_ERROR * 1818624);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step6CTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:01:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1820162);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "72349224775172357552", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("72349224775172357552", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferGasCreditToken decodedToken = (TransferGasCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0010", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T11:01:00"));
            assertEquals("test that correct units purchased are generated", 1820162, amount, MAX_ERROR * 1820162);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step6ACTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:01:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1820162);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "12805131357955755939", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("12805131357955755939", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferElectricityCreditToken decodedToken = (TransferElectricityCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0000", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T11:01"));
            assertEquals("test that correct units purchased are generated", 1820162, amount, MAX_ERROR * 1820162);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step6BCTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:01:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1820162);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "39576835911798786718", token.getTokenNo());

            // -- DECODER

            Meter decoder = new Meter("39576835911798786718", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferWaterCreditToken decodedToken = (TransferWaterCreditToken) decoder.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0001", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith( "2005-04-22T11:01:00"));
            assertEquals("test that correct units purchased are generated", 1820162, amount, MAX_ERROR * 1820162);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step7CTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:02:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(182042);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "57144000167239742426", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("57144000167239742426", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferElectricityCreditToken decodedToken = (TransferElectricityCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0000", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T11:02"));
            assertEquals("test that correct units purchased are generated", 182042, amount, MAX_ERROR * 182042);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step7ACTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:02:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(182042);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "65588636375882192986", token.getTokenNo());

            // -- DECODER

            Meter decoder = new Meter("65588636375882192986", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferWaterCreditToken decodedToken = (TransferWaterCreditToken) decoder.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0001", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith( "2005-04-22T11:02:00"));
            assertEquals("test that correct units purchased are generated", 182042, amount, MAX_ERROR * 182042);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step7BCTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:02:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(182042);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                            amountPurchased, keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "41109480379913866024", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("41109480379913866024", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferGasCreditToken decodedToken = (TransferGasCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0010", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T11:02:00"));
            assertEquals("test that correct units purchased are generated", 182042, amount, MAX_ERROR * 182042);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step8CTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:03:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(123546);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "70308691906921010309", token.getTokenNo());

            // -- DECODER

            Meter decoder = new Meter("70308691906921010309", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferWaterCreditToken decodedToken = (TransferWaterCreditToken) decoder.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0001", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith( "2005-04-22T11:03:00"));
            assertEquals("test that correct units purchased are generated", 123546, amount, MAX_ERROR * 123546);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step8ACTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:03:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(123546);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                            amountPurchased, keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "11142952395253308689", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("11142952395253308689", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferGasCreditToken decodedToken = (TransferGasCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0010", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T11:03:00"));
            assertEquals("test that correct units purchased are generated", 123546, amount, MAX_ERROR * 123546);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    public void step8BCTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:03:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(123546);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "37909354224671858723", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("37909354224671858723", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferElectricityCreditToken decodedToken = (TransferElectricityCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0000", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T11:03"));
            assertEquals("test that correct units purchased are generated", 123546, amount, MAX_ERROR * 123546);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step9CTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:04:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1763427);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "45753099596249311388", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("45753099596249311388", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferGasCreditToken decodedToken = (TransferGasCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0010", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T11:04:00"));
            assertEquals("test that correct units purchased are generated", 1763427, amount, MAX_ERROR * 1763427);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step9ACTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:04:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1763427);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "26262948009387340368", token.getTokenNo());

            // -- DECODER

            Meter decoder = new Meter("26262948009387340368", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferWaterCreditToken decodedToken = (TransferWaterCreditToken) decoder.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0001", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith( "2005-04-22T11:04:00"));
            assertEquals("test that correct units purchased are generated", 1763427, amount, MAX_ERROR * 1763427);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step9BCTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:04:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1763427);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "71777743993390229056", token.getTokenNo());

            // DECODER

            Meter meter = new Meter("71777743993390229056", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferElectricityCreditToken decodedToken = (TransferElectricityCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0000", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T11:04"));
            assertEquals("test that correct units purchased are generated", 1763427, amount, MAX_ERROR * 1763427);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step10CTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:05:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(14782);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "73561917813841338074", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("73561917813841338074", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferElectricityCreditToken decodedToken = (TransferElectricityCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0000", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T11:05"));
            assertEquals("test that correct units purchased are generated", 14782, amount, MAX_ERROR * 14782);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step10ACTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:05:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(14782);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "67036392399646087418", token.getTokenNo());

            // -- DECODER

            Meter decoder = new Meter("67036392399646087418", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferWaterCreditToken decodedToken = (TransferWaterCreditToken) decoder.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0001", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith( "2005-04-22T11:05:00"));
            assertEquals("test that correct units purchased are generated", 14782, amount, MAX_ERROR * 14782);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step10BCTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:05:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(14782);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                            amountPurchased, keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "07128729223928426985", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("07128729223928426985", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferGasCreditToken decodedToken = (TransferGasCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0010", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T11:05:00"));
            assertEquals("test that correct units purchased are generated", 14782, amount, MAX_ERROR * 14782);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step11CTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 20:10:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1.82);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "73508892949665105367", token.getTokenNo());

            // -- DECODER

            Meter decoder = new Meter("73508892949665105367", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferWaterCreditToken decodedToken = (TransferWaterCreditToken) decoder.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0001", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith( "2005-04-22T20:10:00"));
            assertEquals("test that correct units purchased are generated", 1.8, amount, 0.001);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step11ACTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 20:10:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1.82);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                            amountPurchased, keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "58652293903821551890", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("58652293903821551890", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferGasCreditToken decodedToken = (TransferGasCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0010", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T20:10:00"));
            assertEquals("test that correct units purchased are generated", 1.8, amount, 0.001);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step11BCTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 20:10:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1.82);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "55160880109952893498", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("55160880109952893498", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferElectricityCreditToken decodedToken = (TransferElectricityCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0000", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T20:10"));
            assertEquals("test that correct units purchased are generated", 1.8, amount, 0.001);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step12CTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(18981.349);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "48655865329762276574", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("48655865329762276574", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferGasCreditToken decodedToken = (TransferGasCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0010", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T11:00:00"));
            assertEquals("test that correct units purchased are generated", 18981.349, amount, MAX_ERROR * 18981.349);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step12ACTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(18981.349);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "43183161221229495584", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("43183161221229495584", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferElectricityCreditToken decodedToken = (TransferElectricityCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0000", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T11:00"));
            assertEquals("test that correct units purchased are generated", 18981.349, amount, MAX_ERROR * 18981.349);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step12BCTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 11:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(18981.349);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "51607171463000835171", token.getTokenNo());

            // -- DECODER

            Meter decoder = new Meter("51607171463000835171", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferWaterCreditToken decodedToken = (TransferWaterCreditToken) decoder.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0001", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith( "2005-04-22T11:00:00"));
            assertEquals("test that correct units purchased are generated", 18981.349, amount, MAX_ERROR * 18981.349);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step13CTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 20:12:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1897.345);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "62624214800861085936", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("62624214800861085936", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferElectricityCreditToken decodedToken = (TransferElectricityCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0000", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T20:12"));
            assertEquals("test that correct units purchased are generated", 1897.345, amount, MAX_ERROR * 1897.345);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step13ACTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 20:12:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1897.345);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "73238915881253767035", token.getTokenNo());

            // -- DECODER

            Meter decoder = new Meter("73238915881253767035", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferWaterCreditToken decodedToken = (TransferWaterCreditToken) decoder.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0001", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith( "2005-04-22T20:12:00"));
            assertEquals("test that correct units purchased are generated", 1897.345, amount, MAX_ERROR * 1897.345);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step13BCTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 20:12:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1897.345);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                            amountPurchased, keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "60785799035261648393", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("60785799035261648393", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferGasCreditToken decodedToken = (TransferGasCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0010", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T20:12:00"));
            assertEquals("test that correct units purchased are generated", 1897.345, amount, MAX_ERROR * 1897.345);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step14CTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 20:14:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(10897.345);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "41347338266226172225", token.getTokenNo());

            // -- DECODER

            Meter decoder = new Meter("41347338266226172225", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferWaterCreditToken decodedToken = (TransferWaterCreditToken) decoder.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0001", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith( "2005-04-22T20:14:00"));
            assertEquals("test that correct units purchased are generated", 10897.345, amount, MAX_ERROR * 10897.345);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step14ACTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 20:14:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(10897.345);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "08032634883920046224", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("08032634883920046224", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferElectricityCreditToken decodedToken = (TransferElectricityCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0000", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T20:14:00"));
            assertEquals("test that correct units purchased are generated", 10897.345, amount, MAX_ERROR * 10897.345);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    public void step14BCTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "22/04/2005 20:14:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(10897.345);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                            amountPurchased, keyExpiryNumber,
                                                                                            decoderKey,
                                                                                            staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "32685870760859080983", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("32685870760859080983", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferGasCreditToken decodedToken = (TransferGasCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0010", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-04-22T20:14:00"));
            assertEquals("test that correct units purchased are generated", 10897.345, amount, MAX_ERROR * 10897.345);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    public void step15CTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "12/05/2005 20:15:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(0.4712);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferGasCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "20191987725982752848", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("20191987725982752848", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferGasCreditToken decodedToken = (TransferGasCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0010", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-05-12T20:15:00"));
            assertEquals("test that correct units purchased are generated", 0.5, amount, 0.01);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step15ACTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "12/05/2005 20:15:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(0.4712);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferWaterCreditTokenGenerator generator = new TransferWaterCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                decoderKey,
                                                                                                staEncryptionAlgorithm);
            TransferWaterCreditToken token = generator.generate();
            assertEquals("test that the correct token is generated", "28370771280824040728", token.getTokenNo());

            // -- DECODER

            Meter decoder = new Meter("28370771280824040728", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferWaterCreditToken decodedToken = (TransferWaterCreditToken) decoder.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0001", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith( "2005-05-12T20:15:00"));
            assertEquals("test that correct units purchased are generated", 0.5, amount, 0.001);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step15BCTSA20Test() {
        try {
            String requestID = "request_id";
            String dateTime = "12/05/2005 20:15:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(0.4712);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
            BitString randomBitString = new BitString(0x5l);
            randomBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomBitString);
            TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                                            amountPurchased, keyExpiryNumber,
                                                                                                            decoderKey,
                                                                                                            staEncryptionAlgorithm);
            TransferElectricityCreditToken token = generator.generate();

            assertEquals("test that the correct token is generated", "05983059757600918504", token.getTokenNo());

            // -- DECODER

            Meter meter = new Meter("05983059757600918504", decoderKey, 
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferElectricityCreditToken decodedToken = (TransferElectricityCreditToken) meter.decodeNative(requestID);

            String tokenClass = decodedToken.getTokenClass().bitsToString();
            String tokenSubclass = decodedToken.getTokenSubClass().bitsToString();
            String random = decodedToken.getRandomNo().get().bitsToString();
            String tid = decodedToken.getTokenIdentifier().getTimeOfIssue().toString();
            double amount = decodedToken.getAmountPurchased().getAmountPurchased();
            String crc = decodedToken.getCrc().get().bitsToString();

            assertEquals("test that correct token class is generated", "00", tokenClass);
            assertEquals("test that correct token subclass is generated", "0000", tokenSubclass);
            assertEquals("test that correct random value is generated", "0101", random);
            assertTrue("test that correct date of issue is generated", tid.startsWith("2005-05-12T20:15"));
            assertEquals("test that correct units purchased are generated", 0.5, amount, 0.001);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
