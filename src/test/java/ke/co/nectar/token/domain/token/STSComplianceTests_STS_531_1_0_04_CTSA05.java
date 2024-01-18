package ke.co.nectar.token.domain.token;

import ke.co.nectar.ca.keys.utils.HexByteUtils;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.EncryptionAlgorithm;
import ke.co.nectar.token.domain.encryptionalgorithm.Misty1AlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.keys.vending.VendingUniqueDESKey;
import ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber;
import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.domain.token.class2.Set1stSectionDecoderKeyToken;
import ke.co.nectar.token.domain.token.class2.Set2ndSectionDecoderKeyToken;
import ke.co.nectar.token.domain.token.class2.Set3rdSectionDecoderKeyToken;
import ke.co.nectar.token.domain.token.class2.Set4thSectionDecoderKeyToken;
import ke.co.nectar.token.generators.decoderkeygenerator.DecoderKeyGeneratorAlgorithm04;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.Set1stSectionDecoderKeyTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.Set2ndSectionDecoderKeyTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.Set3rdSectionDecoderKeyTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.Set4thSectionDecoderKeyTokenGenerator;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;

public class STSComplianceTests_STS_531_1_0_04_CTSA05 {

    private MeterPrimaryAccountNumber meterPrimaryAccountNumber;
    private TokenCarrierType magneticCardTokenCarrierType;
    private TokenCarrierType virtualCardTokenCarrierType;

    // first set of components
    private VendingUniqueDESKey initialVudk;
    private DecoderKeyGeneratorAlgorithm04 initialDecoderKeyGenerator;
    private SupplyGroupCode initialSupplyGroupCode;
    private TariffIndex initialTariffIndex;
    private KeyRevisionNumber initialKeyRevisionNumber;
    private KeyType initialKeyType;
    private KeyExpiryNumber initialKeyExpiryNumber;

    // second set of components
    private VendingUniqueDESKey newVudk;
    private DecoderKeyGeneratorAlgorithm04 newDecoderKeyGenerator;
    private SupplyGroupCode newSupplyGroupCode;
    private TariffIndex newTariffIndex;
    private KeyRevisionNumber newKeyRevisionNumber;
    private KeyType newKeyType;
    private KeyExpiryNumber newKeyExpiryNumber;

    private IssuerIdentificationNumber issuerIdentificationNumber;
    private IndividualAccountIdentificationNumber iain;
    private Misty1AlgorithmEncryptionAlgorithm misty1EncryptionAlgorithm = new Misty1AlgorithmEncryptionAlgorithm();
    private DecoderKey initialDecoderKey;
    private DecoderKey newDecoderKey;
    private BaseDate baseDate;

    @Before
    public void setUp() {
        try {
            baseDate = BaseDate._1993;

            meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            magneticCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.MAGNETIC_CARD);
            virtualCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.VIRTUAL_TOKEN_CARRIER);
            issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();

            // initialize first set of parameters
            initialVudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab949494949494949401234567"));
            initialSupplyGroupCode = new SupplyGroupCode("123457");
            initialTariffIndex = new TariffIndex("01");
            initialKeyRevisionNumber = new KeyRevisionNumber(1);
            initialKeyType = new KeyType(2);
            initialKeyExpiryNumber = new KeyExpiryNumber(255);
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();

            initialDecoderKeyGenerator = new DecoderKeyGeneratorAlgorithm04(baseDate, initialTariffIndex, initialSupplyGroupCode,
                                                                            initialKeyType, initialKeyRevisionNumber, misty1EncryptionAlgorithm,
                                                                            meterPrimaryAccountNumber, initialVudk);

            // initialise second set of parameters
            newVudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab949494949494949401234567"));
            newSupplyGroupCode = new SupplyGroupCode("123457");
            newTariffIndex = new TariffIndex("02");
            newKeyRevisionNumber = new KeyRevisionNumber(1);
            newKeyType = new KeyType(2);
            newKeyExpiryNumber = new KeyExpiryNumber(255);
            iain =  meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();

            newDecoderKeyGenerator = new DecoderKeyGeneratorAlgorithm04(baseDate, newTariffIndex, newSupplyGroupCode,
                                                                        newKeyType, newKeyRevisionNumber, misty1EncryptionAlgorithm,
                                                                        meterPrimaryAccountNumber, newVudk);
            // initialise decoder keys
            initialDecoderKey = initialDecoderKeyGenerator.generate();
            newDecoderKey = newDecoderKeyGenerator.generate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void step1CTSA05TestGeneratingFirstSection() {
        try {
            Set1stSectionDecoderKeyToken _1stSectionToken = generateSet1stSectionDecoderKeyToken(initialDecoderKey, newDecoderKey,
                                                                                                    newKeyRevisionNumber, newKeyType,
                                                                                                    misty1EncryptionAlgorithm,
                                                                                                    false);
            assertEquals("test that the correct token is generated", "34812744915211133004", _1stSectionToken.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step1CTSA05TestGeneratingSecondSection() {
        try {
            Set2ndSectionDecoderKeyToken _2ndSectionToken = generateSet2ndSectionDecoderKeyToken(initialDecoderKey,
                                                                                                    newDecoderKey,
                                                                                                    newTariffIndex,
                                                                                                    misty1EncryptionAlgorithm);
            assertEquals("test that the correct token is generated", "46903925208523674737", _2ndSectionToken.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step1CTSA05TestGeneratingThirdSection() {
        try {
            Set3rdSectionDecoderKeyToken _3rdSectionToken = generateSet3rdSectionDecoderKeyToken(newSupplyGroupCode,
                                                                                                    initialDecoderKey,
                                                                                                    newDecoderKey,
                                                                                                    misty1EncryptionAlgorithm);
            assertEquals("test that the correct token is generated", "71464563847088610152", _3rdSectionToken.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step1CTSA05TestGeneratingFourthSection() {
        try {
            Set4thSectionDecoderKeyToken _4thSectionToken = generateSet4thSectionDecoderKeyToken(initialDecoderKey,
                                                                                                    newDecoderKey,
                                                                                                    misty1EncryptionAlgorithm);
            assertEquals("test that the correct token is generated", "67904239402617643990", _4thSectionToken.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2CTSA05TestGeneratingFirstSectionFlippedRollOverBit() {
        try {
            newVudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab949494949494949401234567"));
            newSupplyGroupCode = new SupplyGroupCode("123457");
            newTariffIndex = new TariffIndex("02");
            newKeyRevisionNumber = new KeyRevisionNumber(4);
            newKeyType = new KeyType(2);
            newKeyExpiryNumber = new KeyExpiryNumber(255);
            iain =  meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            baseDate = BaseDate._2014;
            newDecoderKeyGenerator = new DecoderKeyGeneratorAlgorithm04(baseDate, newTariffIndex, newSupplyGroupCode,
                                                                        newKeyType, newKeyRevisionNumber, misty1EncryptionAlgorithm,
                                                                        meterPrimaryAccountNumber, newVudk);
            DecoderKey newDecoderKey = newDecoderKeyGenerator.generate();
            Set1stSectionDecoderKeyToken _1stSectionToken = generateSet1stSectionDecoderKeyToken(initialDecoderKey,
                                                                                                    newDecoderKey,
                                                                                                    newKeyRevisionNumber,
                                                                                                    newKeyType,
                                                                                                    misty1EncryptionAlgorithm,
                                                                                                    true);
            assertEquals("test that the correct token is generated", "56493341861242437581", _1stSectionToken.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2CTSA05TestGeneratingSecondSectionFlippedRollOverBit() {
        try {
            newVudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab949494949494949401234567"));
            newSupplyGroupCode = new SupplyGroupCode("123457");
            newTariffIndex = new TariffIndex("02");
            newKeyRevisionNumber = new KeyRevisionNumber(4);
            newKeyType = new KeyType(2);
            newKeyExpiryNumber = new KeyExpiryNumber(255);
            iain =  meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            baseDate = BaseDate._2014;
            newDecoderKeyGenerator = new DecoderKeyGeneratorAlgorithm04(baseDate, newTariffIndex, newSupplyGroupCode,
                                                                        newKeyType, newKeyRevisionNumber, misty1EncryptionAlgorithm,
                                                                        meterPrimaryAccountNumber, newVudk);
            DecoderKey newDecoderKey = newDecoderKeyGenerator.generate();
            Set2ndSectionDecoderKeyToken _2ndSectionToken = generateSet2ndSectionDecoderKeyToken(initialDecoderKey,
                                                                                                    newDecoderKey,
                                                                                                    newTariffIndex,
                                                                                                    misty1EncryptionAlgorithm);
            assertEquals("test that the correct token is generated", "51757380361191578258", _2ndSectionToken.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step3CTSA05TestGeneratingFirstSection() {
        changeMeterPANValue();
        try {
            Set1stSectionDecoderKeyToken _1stSectionToken = generateSet1stSectionDecoderKeyToken(initialDecoderKey, newDecoderKey,
                                                                                                    newKeyRevisionNumber, newKeyType,
                                                                                                    misty1EncryptionAlgorithm,
                                                                                                    false);
            assertEquals("test that the correct token is generated, test 3", "29594465524699505864", _1stSectionToken.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step3CTSA05TestGeneratingSecondSection() {
        changeMeterPANValue();
        try {
            Set2ndSectionDecoderKeyToken _2ndSectionToken = generateSet2ndSectionDecoderKeyToken(initialDecoderKey,
                                                                                                    newDecoderKey,
                                                                                                    newTariffIndex,
                                                                                                    misty1EncryptionAlgorithm);
            assertEquals("test that the correct token is generated, test 2", "09506536067814156547", _2ndSectionToken.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private Set1stSectionDecoderKeyToken generateSet1stSectionDecoderKeyToken(DecoderKey initialDecoderKey,
                                                                              DecoderKey newDecoderKey,
                                                                              KeyRevisionNumber keyRevisionNumber,
                                                                              KeyType keyType,
                                                                              EncryptionAlgorithm encryptionAlgorithm,
                                                                              boolean isRollBitSet) {
        Set1stSectionDecoderKeyToken _1stSectionToken = null;
        String requestID = "request_id";
        try {
            BitString kenhoBitString = new BitString(newKeyExpiryNumber.getValue()).extractBits(4, 4);
            KeyExpiryNumberHighOrder keyExpiryNumberHighOrder = new KeyExpiryNumberHighOrder(kenhoBitString);

            BitString rolloverKeyChangeBitString;
            if (isRollBitSet)
                rolloverKeyChangeBitString = new BitString("1");
            else
                rolloverKeyChangeBitString = new BitString("0");

            rolloverKeyChangeBitString.setLength(1);
            RolloverKeyChange rolloverKeyChange = new RolloverKeyChange(rolloverKeyChangeBitString);

            Set1stSectionDecoderKeyTokenGenerator _1stSectionGenerator = new Set1stSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                    keyExpiryNumberHighOrder,
                                                                                                                    keyRevisionNumber,
                                                                                                                    rolloverKeyChange,
                                                                                                                    keyType,
                                                                                                                    initialDecoderKey,
                                                                                                                    newDecoderKey,
                                                                                                                    encryptionAlgorithm);
            _1stSectionToken = _1stSectionGenerator.generate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _1stSectionToken;
    }

    private Set2ndSectionDecoderKeyToken generateSet2ndSectionDecoderKeyToken(DecoderKey initialDecoderKey,
                                                                              DecoderKey newDecoderKey,
                                                                              TariffIndex newTariffIndex,
                                                                              EncryptionAlgorithm encryptionAlgorithm) {
        Set2ndSectionDecoderKeyToken _2ndSectionToken = null ;
        try {
            BitString kenloBitString = new BitString(newKeyExpiryNumber.getValue()).extractBits(0, 4);
            KeyExpiryNumberLowOrder kenlo = new KeyExpiryNumberLowOrder(kenloBitString);
            String requestID = "request_id";

            Set2ndSectionDecoderKeyTokenGenerator _2ndSectionGenerator = new Set2ndSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                    kenlo,
                                                                                                                    newTariffIndex,
                                                                                                                    initialDecoderKey,
                                                                                                                    newDecoderKey,
                                                                                                                    encryptionAlgorithm);
            _2ndSectionToken = _2ndSectionGenerator.generate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return _2ndSectionToken;
    }

    private Set3rdSectionDecoderKeyToken generateSet3rdSectionDecoderKeyToken(SupplyGroupCode supplyGroupCode,
                                                                              DecoderKey initialDecoderKey,
                                                                              DecoderKey newDecoderKey,
                                                                              EncryptionAlgorithm encryptionAlgorithm) {
        Set3rdSectionDecoderKeyToken _3rdSectionToken = null;
        String requestID = "request_id";
        try {
            Set3rdSectionDecoderKeyTokenGenerator _3rdSectionGenerator = new Set3rdSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                    supplyGroupCode,
                                                                                                                    initialDecoderKey,
                                                                                                                    newDecoderKey,
                                                                                                                    encryptionAlgorithm);
            _3rdSectionToken = _3rdSectionGenerator.generate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return _3rdSectionToken;
    }

    private Set4thSectionDecoderKeyToken generateSet4thSectionDecoderKeyToken(DecoderKey initialDecoderKey,
                                                                              DecoderKey newDecoderKey,
                                                                              EncryptionAlgorithm encryptionAlgorithm) {
        Set4thSectionDecoderKeyToken _4thSectionToken = null;
        String requestID = "request_id";
        try {
            Set4thSectionDecoderKeyTokenGenerator _4thSectionGenerator = new Set4thSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                    initialSupplyGroupCode,
                                                                                                                    initialDecoderKey,
                                                                                                                    newDecoderKey,
                                                                                                                    encryptionAlgorithm);
            _4thSectionToken = _4thSectionGenerator.generate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return _4thSectionToken;
    }

    private void changeMeterPANValue() {
        // @TODO: Generate Util to create PANBlock and CONTROLBlock from meterPAN
        try {
            meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("000001000000000082", NO_METER_PAN_VALIDATION);
            issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();

            initialDecoderKeyGenerator = new DecoderKeyGeneratorAlgorithm04(baseDate, initialTariffIndex, initialSupplyGroupCode,
                                                                            initialKeyType, initialKeyRevisionNumber, misty1EncryptionAlgorithm,
                                                                            meterPrimaryAccountNumber, initialVudk);
            newDecoderKeyGenerator = new DecoderKeyGeneratorAlgorithm04(baseDate, newTariffIndex, newSupplyGroupCode,
                                                                        newKeyType, newKeyRevisionNumber, misty1EncryptionAlgorithm,
                                                                        meterPrimaryAccountNumber, newVudk);
            initialDecoderKey = initialDecoderKeyGenerator.generate();
            newDecoderKey = newDecoderKeyGenerator.generate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
