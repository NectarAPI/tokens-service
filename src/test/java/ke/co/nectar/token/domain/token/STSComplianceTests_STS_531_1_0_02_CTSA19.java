package ke.co.nectar.token.domain.token;

import ke.co.nectar.ca.keys.utils.HexByteUtils;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.StandardTransferAlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.keys.vending.VendingUniqueDESKey;
import ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber;
import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.generators.decoderkeygenerator.DecoderKeyGeneratorAlgorithm02;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class0.TransferElectricityCreditTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.Set1stSectionDecoderKeyTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.Set2ndSectionDecoderKeyTokenGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;
import static org.junit.Assert.assertEquals;

public class STSComplianceTests_STS_531_1_0_02_CTSA19 {

    private MeterPrimaryAccountNumber meterPrimaryAccountNumber;
    private TokenCarrierType magneticCardTokenCarrierType;
    private TokenCarrierType virtualCardTokenCarrierType;

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
            staEncryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();

            vudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab"));
            supplyGroupCode = new SupplyGroupCode("123456");
            keyType = new KeyType(2);
            keyExpiryNumber = new KeyExpiryNumber(85);

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

        keyRevisionNumber = new KeyRevisionNumber(1);
        tariffIndex = new TariffIndex("02");
        generateDecoderKey();

        String requestID = "request_id";
        String dateTime = "01/04/2002 10:05:00";
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
                                                                                                                                decoderKey,
                                                                                                                                staEncryptionAlgorithm);
        Set2ndSectionDecoderKeyTokenGenerator set2ndSectionDecoderKeyTokenGenerator = new Set2ndSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                keyExpiryNumberLowOrder,
                                                                                                                                tariffIndex,
                                                                                                                                decoderKey,
                                                                                                                                decoderKey,
                                                                                                                                staEncryptionAlgorithm);
        TransferElectricityCreditTokenGenerator transferElectricityCreditTokenGenerator = new TransferElectricityCreditTokenGenerator(requestID,
                                                                                                                                        tokenIdentifier, randomNo,
                                                                                                                                        amountPurchased,
                                                                                                                                        keyExpiryNumber,
                                                                                                                                        decoderKey,
                                                                                                                                        staEncryptionAlgorithm);
        assertEquals("test that the correct Set1stSectionDecoderKeyTokenGenerator is generated", "31337250623187821174", set1stSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set2ndSectionDecoderKeyTokenGenerator is generated", "25365690080149305690", set2ndSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct TransferElectricityCreditTokenGenerator is generated", "40823728429161791369", transferElectricityCreditTokenGenerator.generate().getTokenNo());
    }

    @Test
    public void step2CTSA19Test() throws Exception {
        keyRevisionNumber = new KeyRevisionNumber(2);
        generateDecoderKey();

        String requestID = "request_id";
        String dateTime = "01/04/2002 10:10:00";
        DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
        TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, baseDate);
        BitString randomBitString = new BitString(0x5l);
        randomBitString.setLength(4);
        RandomNo randomNo = new RandomNo(randomBitString); // Tokens in tests generated using RND = 5

        Set1stSectionDecoderKeyTokenGenerator set1stSectionDecoderKeyTokenGenerator = new Set1stSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                keyExpiryNumberHighOrder,
                                                                                                                                keyRevisionNumber,
                                                                                                                                rolloverKeyChange,
                                                                                                                                keyType,
                                                                                                                                decoderKey,
                                                                                                                                decoderKey,
                                                                                                                                staEncryptionAlgorithm);
        Set2ndSectionDecoderKeyTokenGenerator set2ndSectionDecoderKeyTokenGenerator = new Set2ndSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                keyExpiryNumberLowOrder,
                                                                                                                                tariffIndex,
                                                                                                                                decoderKey,
                                                                                                                                decoderKey,
                                                                                                                                staEncryptionAlgorithm);
        TransferElectricityCreditTokenGenerator transferElectricityCreditTokenGenerator = new TransferElectricityCreditTokenGenerator(requestID,
                                                                                                                                        tokenIdentifier, randomNo,
                                                                                                                                        amountPurchased, keyExpiryNumber,
                                                                                                                                        decoderKey,
                                                                                                                                        staEncryptionAlgorithm);
        assertEquals("test that the correct Set1stSectionDecoderKeyTokenGenerator is generated", "44144630105464684572", set1stSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set2ndSectionDecoderKeyTokenGenerator is generated", "31162823148845145254", set2ndSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct TransferElectricityCreditTokenGenerator is generated", "07731698895042112630", transferElectricityCreditTokenGenerator.generate().getTokenNo());
    }

    @Test
    public void step3CTSA19Test() throws Exception {
        keyRevisionNumber = new KeyRevisionNumber(2);
        keyExpiryNumber = new KeyExpiryNumber(255);

        BitString kenhoBitString = new BitString(keyExpiryNumber.getValue()).extractBits(4, 4);
        keyExpiryNumberHighOrder = new KeyExpiryNumberHighOrder(kenhoBitString);
        BitString kenloBitString = new BitString(keyExpiryNumber.getValue()).extractBits(0, 4);
        keyExpiryNumberLowOrder = new KeyExpiryNumberLowOrder(kenloBitString);

        generateDecoderKey();

        String requestID = "request_id";
        String dateTime = "01/04/2002 10:15:00";
        DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
        TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, baseDate);
        BitString randomBitString = new BitString(0x5l);
        randomBitString.setLength(4);
        RandomNo randomNo = new RandomNo(randomBitString); // Tokens in tests generated using RND = 5

        Set1stSectionDecoderKeyTokenGenerator set1stSectionDecoderKeyTokenGenerator = new Set1stSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                keyExpiryNumberHighOrder,
                                                                                                                                keyRevisionNumber,
                                                                                                                                rolloverKeyChange,
                                                                                                                                keyType,
                                                                                                                                decoderKey,
                                                                                                                                decoderKey,
                                                                                                                                staEncryptionAlgorithm);
        Set2ndSectionDecoderKeyTokenGenerator set2ndSectionDecoderKeyTokenGenerator = new Set2ndSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                keyExpiryNumberLowOrder,
                                                                                                                                tariffIndex,
                                                                                                                                decoderKey,
                                                                                                                                decoderKey,
                                                                                                                                staEncryptionAlgorithm);
        TransferElectricityCreditTokenGenerator transferElectricityCreditTokenGenerator = new TransferElectricityCreditTokenGenerator(requestID,
                                                                                                                                tokenIdentifier, randomNo,
                                                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                                                decoderKey,
                                                                                                                                staEncryptionAlgorithm);
        assertEquals("test that the correct Set1stSectionDecoderKeyTokenGenerator is generated", "27767097093580610394", set1stSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set2ndSectionDecoderKeyTokenGenerator is generated", "37287781995519266010", set2ndSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct TransferElectricityCreditTokenGenerator is generated", "02838142732283753296", transferElectricityCreditTokenGenerator.generate().getTokenNo());
    }

    @Test
    public void step4CTSA19Test() throws Exception {

        supplyGroupCode = new SupplyGroupCode("888888");
        keyRevisionNumber = new KeyRevisionNumber(1);
        tariffIndex = new TariffIndex("01");
        vudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("9494949494949494"));
        generateDecoderKey();

        String requestID = "request_id";
        String dateTime = "01/04/2002 10:20:00";
        DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
        TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, baseDate);
        BitString randomBitString = new BitString(0x5l);
        randomBitString.setLength(4);
        RandomNo randomNo = new RandomNo(randomBitString); // Tokens in tests generated using RND = 5

        Set1stSectionDecoderKeyTokenGenerator set1stSectionDecoderKeyTokenGenerator = new Set1stSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                keyExpiryNumberHighOrder,
                                                                                                                                keyRevisionNumber,
                                                                                                                                rolloverKeyChange,
                                                                                                                                keyType,
                                                                                                                                decoderKey,
                                                                                                                                decoderKey,
                                                                                                                                staEncryptionAlgorithm);
        Set2ndSectionDecoderKeyTokenGenerator set2ndSectionDecoderKeyTokenGenerator = new Set2ndSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                keyExpiryNumberLowOrder,
                                                                                                                                tariffIndex,
                                                                                                                                decoderKey,
                                                                                                                                decoderKey,
                                                                                                                                staEncryptionAlgorithm);
        TransferElectricityCreditTokenGenerator transferElectricityCreditTokenGenerator = new TransferElectricityCreditTokenGenerator(requestID,
                                                                                                                                tokenIdentifier, randomNo,
                                                                                                                                amountPurchased, keyExpiryNumber,
                                                                                                                                decoderKey,
                                                                                                                                staEncryptionAlgorithm);
        assertEquals("test that the correct Set1stSectionDecoderKeyTokenGenerator is generated", "54413905164151863438", set1stSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set2ndSectionDecoderKeyTokenGenerator is generated", "70335822849409372395", set2ndSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct TransferElectricityCreditTokenGenerator is generated", "17352963892501043261", transferElectricityCreditTokenGenerator.generate().getTokenNo());
    }

    private void generateDecoderKey()
        throws Exception {
        decoderKeyAlgorithm02Generator = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                            keyRevisionNumber, issuerIdentificationNumber,
                                                                            iain, vudk);
        decoderKey = decoderKeyAlgorithm02Generator.generate();
    }
}
