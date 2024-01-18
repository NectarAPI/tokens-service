package ke.co.nectar.token.domain.token;

import ke.co.nectar.ca.keys.utils.HexByteUtils;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.Misty1AlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.keys.vending.VendingUniqueDESKey;
import ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber;
import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.generators.decoderkeygenerator.DecoderKeyGeneratorAlgorithm04;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class0.TransferElectricityCreditTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.Set1stSectionDecoderKeyTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.Set2ndSectionDecoderKeyTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.Set3rdSectionDecoderKeyTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.Set4thSectionDecoderKeyTokenGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;
import static org.junit.Assert.assertEquals;

public class STSComplianceTests_STS_531_1_0_04_CTSA19 {

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
    private Misty1AlgorithmEncryptionAlgorithm misty1EncryptionAlgorithm;
    private BaseDate baseDate;
    private DecoderKey decoderKey;
    private Amount amountPurchased;

    private KeyExpiryNumberHighOrder keyExpiryNumberHighOrder;
    private KeyExpiryNumberLowOrder keyExpiryNumberLowOrder;
    private RolloverKeyChange rolloverKeyChange;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        try {
            meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            magneticCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.MAGNETIC_CARD);
            virtualCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.VIRTUAL_TOKEN_CARRIER);
            tariffIndex = new TariffIndex("01");
            amountPurchased = new Amount(0.1);
            baseDate = BaseDate._1993;

            issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            misty1EncryptionAlgorithm = new Misty1AlgorithmEncryptionAlgorithm();

            vudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab949494949494949401234567"));
            supplyGroupCode = new SupplyGroupCode("123457");
            keyType = new KeyType(2);
            keyRevisionNumber = new KeyRevisionNumber(1);

            keyExpiryNumber = new KeyExpiryNumber(255);
            BitString kenhoBitString = new BitString(keyExpiryNumber.getValue()).extractBits(4, 4);
            keyExpiryNumberHighOrder = new KeyExpiryNumberHighOrder(kenhoBitString);
            BitString kenloBitString = new BitString(keyExpiryNumber.getValue()).extractBits(0, 4);
            keyExpiryNumberLowOrder = new KeyExpiryNumberLowOrder(kenloBitString);

            BitString rolloverKeyChangeBitString = new BitString("0");
            rolloverKeyChangeBitString.setLength(1);
            rolloverKeyChange = new RolloverKeyChange(rolloverKeyChangeBitString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void step1CTSA19Test() throws Exception {
        generateDecoderKey();
        TariffIndex newTariffIndex = new TariffIndex("02");
        DecoderKey newDecoderKey = generateNewDecoderKey(newTariffIndex, keyRevisionNumber, supplyGroupCode);

        String requestID = "request_id";
        String dateTime = "01/04/2004 10:00:00";
        DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
        TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, baseDate);
        BitString randomBitString = new BitString(0x5l);
        randomBitString.setLength(4);
        RandomNo randomNo = new RandomNo(randomBitString);

        Set1stSectionDecoderKeyTokenGenerator set1stSectionDecoderKeyTokenGenerator = new Set1stSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                keyExpiryNumberHighOrder,
                                                                                                                                keyRevisionNumber,
                                                                                                                                rolloverKeyChange,
                                                                                                                                keyType,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                misty1EncryptionAlgorithm);
        Set2ndSectionDecoderKeyTokenGenerator set2ndSectionDecoderKeyTokenGenerator = new Set2ndSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                keyExpiryNumberLowOrder,
                                                                                                                                newTariffIndex,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                misty1EncryptionAlgorithm);
        Set3rdSectionDecoderKeyTokenGenerator set3rdSectionDecoderKeyTokenGenerator = new Set3rdSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                supplyGroupCode,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                misty1EncryptionAlgorithm);
        Set4thSectionDecoderKeyTokenGenerator set4thSectionDecoderKeyTokenGenerator = new Set4thSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                supplyGroupCode,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                misty1EncryptionAlgorithm);
        TransferElectricityCreditTokenGenerator transferElectricityCreditTokenGenerator = new TransferElectricityCreditTokenGenerator(requestID,
                                                                                                                                        tokenIdentifier,
                                                                                                                                        randomNo,
                                                                                                                                        amountPurchased,
                                                                                                                                        keyExpiryNumber,
                                                                                                                                        newDecoderKey,
                                                                                                                                        misty1EncryptionAlgorithm);
        assertEquals("test that the correct Set1stSectionDecoderKeyTokenGenerator is generated", "34812744915211133004", set1stSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set2ndSectionDecoderKeyTokenGenerator is generated", "46903925208523674737", set2ndSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set3rdSectionDecoderKeyTokenGenerator is generated", "71464563847088610152", set3rdSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set4thSectionDecoderKeyTokenGenerator is generated", "67904239402617643990", set4thSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct TransferElectricityCreditTokenGenerator is generated", "52522044994700766563", transferElectricityCreditTokenGenerator.generate().getTokenNo());
    }

    @Test
    public void step2CTSA19Test() throws Exception {
        keyRevisionNumber = new KeyRevisionNumber(1);
        generateDecoderKey();

        KeyRevisionNumber newKeyRevisionNumber = new KeyRevisionNumber(2);
        DecoderKey newDecoderKey = generateNewDecoderKey(tariffIndex, newKeyRevisionNumber, supplyGroupCode);

        String requestID = "request_id";
        String dateTime = "01/04/2004 10:10:00";
        DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
        TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, baseDate);
        BitString randomBitString = new BitString(0x5l);
        randomBitString.setLength(4);
        RandomNo randomNo = new RandomNo(randomBitString);

        Set1stSectionDecoderKeyTokenGenerator set1stSectionDecoderKeyTokenGenerator = new Set1stSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                keyExpiryNumberHighOrder,
                                                                                                                                newKeyRevisionNumber,
                                                                                                                                rolloverKeyChange,
                                                                                                                                keyType,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                misty1EncryptionAlgorithm);
        Set2ndSectionDecoderKeyTokenGenerator set2ndSectionDecoderKeyTokenGenerator = new Set2ndSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                keyExpiryNumberLowOrder,
                                                                                                                                tariffIndex,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                misty1EncryptionAlgorithm);
        Set3rdSectionDecoderKeyTokenGenerator set3rdSectionDecoderKeyTokenGenerator = new Set3rdSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                supplyGroupCode,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                misty1EncryptionAlgorithm);
        Set4thSectionDecoderKeyTokenGenerator set4thSectionDecoderKeyTokenGenerator = new Set4thSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                supplyGroupCode,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                misty1EncryptionAlgorithm);
        TransferElectricityCreditTokenGenerator transferElectricityCreditTokenGenerator = new TransferElectricityCreditTokenGenerator(requestID,
                                                                                                                                        tokenIdentifier,
                                                                                                                                        randomNo,
                                                                                                                                        amountPurchased,
                                                                                                                                        keyExpiryNumber,
                                                                                                                                        newDecoderKey,
                                                                                                                                        misty1EncryptionAlgorithm);
        assertEquals("test that the correct Set1stSectionDecoderKeyTokenGenerator is generated", "40937788669556693706", set1stSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set2ndSectionDecoderKeyTokenGenerator is generated", "55900126766830063715", set2ndSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set3rdSectionDecoderKeyTokenGenerator is generated", "70168064023104948668", set3rdSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set4thSectionDecoderKeyTokenGenerator is generated", "31051353433275215300", set4thSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct TransferElectricityCreditTokenGenerator is generated", "10334212364124071208", transferElectricityCreditTokenGenerator.generate().getTokenNo());
    }

    @Test
    public void step3CTSA19Test() throws Exception {
        keyRevisionNumber = new KeyRevisionNumber(2);
        generateDecoderKey();

        KeyExpiryNumber keyExpiryNumber = new KeyExpiryNumber(170);
        BitString kenhoBitString = new BitString(keyExpiryNumber.getValue()).extractBits(4, 4);
        KeyExpiryNumberHighOrder newkeyExpiryNumberHighOrder = new KeyExpiryNumberHighOrder(kenhoBitString);
        BitString kenloBitString = new BitString(keyExpiryNumber.getValue()).extractBits(0, 4);
        KeyExpiryNumberLowOrder newKeyExpiryNumberLowOrder = new KeyExpiryNumberLowOrder(kenloBitString);

        KeyRevisionNumber newKeyRevisionNumber = new KeyRevisionNumber(7);
        DecoderKey newDecoderKey = generateNewDecoderKey(tariffIndex, newKeyRevisionNumber, supplyGroupCode);

        String requestID = "request_id";
        String dateTime = "01/04/2004 10:15:00";
        DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
        TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, baseDate);
        BitString randomBitString = new BitString(0x5l);
        randomBitString.setLength(4);
        RandomNo randomNo = new RandomNo(randomBitString); // Tokens in tests generated using RND = 5

        Set1stSectionDecoderKeyTokenGenerator set1stSectionDecoderKeyTokenGenerator = new Set1stSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                newkeyExpiryNumberHighOrder,
                                                                                                                                newKeyRevisionNumber,
                                                                                                                                rolloverKeyChange,
                                                                                                                                keyType,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                misty1EncryptionAlgorithm);
        Set2ndSectionDecoderKeyTokenGenerator set2ndSectionDecoderKeyTokenGenerator = new Set2ndSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                newKeyExpiryNumberLowOrder,
                                                                                                                                tariffIndex,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                misty1EncryptionAlgorithm);
        Set3rdSectionDecoderKeyTokenGenerator set3rdSectionDecoderKeyTokenGenerator = new Set3rdSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                supplyGroupCode,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                misty1EncryptionAlgorithm);
        Set4thSectionDecoderKeyTokenGenerator set4thSectionDecoderKeyTokenGenerator = new Set4thSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                supplyGroupCode,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                misty1EncryptionAlgorithm);
        TransferElectricityCreditTokenGenerator transferElectricityCreditTokenGenerator = new TransferElectricityCreditTokenGenerator(requestID,
                                                                                                                                        tokenIdentifier,
                                                                                                                                        randomNo,
                                                                                                                                        amountPurchased,
                                                                                                                                        keyExpiryNumber,
                                                                                                                                        newDecoderKey,
                                                                                                                                        misty1EncryptionAlgorithm);
        assertEquals("test that the correct Set1stSectionDecoderKeyTokenGenerator is generated", "65626097193581652906", set1stSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set2ndSectionDecoderKeyTokenGenerator is generated", "45273086784967754458", set2ndSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set3rdSectionDecoderKeyTokenGenerator is generated", "60343449063848563711", set3rdSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set4thSectionDecoderKeyTokenGenerator is generated", "62570694794795906368", set4thSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct TransferElectricityCreditTokenGenerator is generated", "06390659512322397973", transferElectricityCreditTokenGenerator.generate().getTokenNo());
    }

    @Test
    public void step4CTSA19Test() throws Exception {
        generateDecoderKey();

        SupplyGroupCode newSupplyGroupCode = new SupplyGroupCode("123461");
        DecoderKey newDecoderKey = generateNewDecoderKey(tariffIndex, keyRevisionNumber, newSupplyGroupCode);

        String requestID = "request_id";
        String dateTime = "01/04/2004 10:20:00";
        DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
        TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, baseDate);
        BitString randomBitString = new BitString(0x5l);
        randomBitString.setLength(4);
        RandomNo randomNo = new RandomNo(randomBitString);

        Set1stSectionDecoderKeyTokenGenerator set1stSectionDecoderKeyTokenGenerator = new Set1stSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                keyExpiryNumberHighOrder,
                                                                                                                                keyRevisionNumber,
                                                                                                                                rolloverKeyChange,
                                                                                                                                keyType,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                misty1EncryptionAlgorithm);
        Set2ndSectionDecoderKeyTokenGenerator set2ndSectionDecoderKeyTokenGenerator = new Set2ndSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                keyExpiryNumberLowOrder,
                                                                                                                                tariffIndex,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                misty1EncryptionAlgorithm);
        Set3rdSectionDecoderKeyTokenGenerator set3rdSectionDecoderKeyTokenGenerator = new Set3rdSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                newSupplyGroupCode,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                misty1EncryptionAlgorithm);
        Set4thSectionDecoderKeyTokenGenerator set4thSectionDecoderKeyTokenGenerator = new Set4thSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                newSupplyGroupCode,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                misty1EncryptionAlgorithm);
        TransferElectricityCreditTokenGenerator transferElectricityCreditTokenGenerator = new TransferElectricityCreditTokenGenerator(requestID,
                                                                                                                                tokenIdentifier,
                                                                                                                                randomNo,
                                                                                                                                amountPurchased,
                                                                                                                                keyExpiryNumber,
                                                                                                                                newDecoderKey,
                                                                                                                                misty1EncryptionAlgorithm);
        assertEquals("test that the correct Set1stSectionDecoderKeyTokenGenerator is generated", "14459740122691785207", set1stSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set2ndSectionDecoderKeyTokenGenerator is generated", "16084994560056931733", set2ndSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set3rdSectionDecoderKeyTokenGenerator is generated", "38603700611597183668", set3rdSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set4thSectionDecoderKeyTokenGenerator is generated", "07926972461094669048", set4thSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct TransferElectricityCreditTokenGenerator is generated", "22571219105476013350", transferElectricityCreditTokenGenerator.generate().getTokenNo());
    }

    private void generateDecoderKey() throws Exception {
        BaseDate baseDate = BaseDate._1993;
        decoderKeyAlgorithm04Generator = new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                                            keyType, keyRevisionNumber, misty1EncryptionAlgorithm,
                                                                            meterPrimaryAccountNumber, vudk);
        decoderKey = decoderKeyAlgorithm04Generator.generate();
    }

    private DecoderKey generateNewDecoderKey(TariffIndex tariffIndex,
                                             KeyRevisionNumber keyRevisionNumber,
                                             SupplyGroupCode supplyGroupCode)
            throws Exception {
        BaseDate baseDate = BaseDate._1993;
        return new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                    keyType, keyRevisionNumber, misty1EncryptionAlgorithm,
                                                    meterPrimaryAccountNumber, vudk).generate();
    }
}
