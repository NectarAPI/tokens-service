package ke.co.nectar.token.domain.token;

import ke.co.nectar.ca.keys.utils.HexByteUtils;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.StandardTransferAlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.keys.vending.VendingUniqueDESKey;
import ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber;
import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.domain.token.class2.Set1stSectionDecoderKeyToken;
import ke.co.nectar.token.domain.token.class2.Set2ndSectionDecoderKeyToken;
import ke.co.nectar.token.generators.decoderkeygenerator.DecoderKeyGeneratorAlgorithm02;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.Set1stSectionDecoderKeyTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.Set2ndSectionDecoderKeyTokenGenerator;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;

public class STSComplianceTests_STS_531_1_0_02_CTSA05 {

    private MeterPrimaryAccountNumber meterPrimaryAccountNumber;
    private TokenCarrierType magneticCardTokenCarrierType;
    private TokenCarrierType virtualCardTokenCarrierType;

    // first set of components
    private VendingUniqueDESKey initialVudk;
    private DecoderKeyGeneratorAlgorithm02 initialDecoderKeyGenerator;
    private SupplyGroupCode initialSupplyGroupCode;
    private TariffIndex initialTariffIndex;
    private KeyRevisionNumber initialKeyRevisionNumber;
    private KeyType initialKeyType;
    private KeyExpiryNumber initialKeyExpiryNumber;

    // second set of components
    private VendingUniqueDESKey newVudk;
    private DecoderKeyGeneratorAlgorithm02 newDecoderKeyGenerator;
    private SupplyGroupCode newSupplyGroupCode;
    private TariffIndex newTariffIndex;
    private KeyRevisionNumber newKeyRevisionNumber;
    private KeyType newKeyType;
    private KeyExpiryNumber newKeyExpiryNumber;

    private IssuerIdentificationNumber issuerIdentificationNumber;
    private IndividualAccountIdentificationNumber iain;
    private StandardTransferAlgorithmEncryptionAlgorithm staEncryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
    private DecoderKey initialDecoderKey;
    private DecoderKey newDecoderKey;

    @Before
    public void setUp() {
        try {
            meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            magneticCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.MAGNETIC_CARD);
            virtualCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.VIRTUAL_TOKEN_CARRIER);
            issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();

            // first set of parameters
            initialVudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab"));
            initialSupplyGroupCode = new SupplyGroupCode("123456");
            initialTariffIndex = new TariffIndex("01");
            initialKeyRevisionNumber = new KeyRevisionNumber(1);
            initialKeyType = new KeyType(2);
            initialKeyExpiryNumber = new KeyExpiryNumber(255);
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();

            initialDecoderKeyGenerator = new DecoderKeyGeneratorAlgorithm02(initialKeyType, initialSupplyGroupCode, initialTariffIndex,
                                                                            initialKeyRevisionNumber, issuerIdentificationNumber, iain, initialVudk);

            // second set of parameters
            newVudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab"));
            newSupplyGroupCode = new SupplyGroupCode("123456");
            newTariffIndex = new TariffIndex("02");
            newKeyRevisionNumber = new KeyRevisionNumber(1);
            newKeyType = new KeyType(2);
            newKeyExpiryNumber = new KeyExpiryNumber(255);
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();

            newDecoderKeyGenerator = new DecoderKeyGeneratorAlgorithm02(newKeyType, newSupplyGroupCode, newTariffIndex,
                                                                        newKeyRevisionNumber, issuerIdentificationNumber, iain, newVudk);

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
            Set1stSectionDecoderKeyToken _1stSectionToken = generateSet1stSectionDecoderKeyToken(initialDecoderKey, newDecoderKey);
            assertEquals("test that the correct token is generated", "51638423060042734509", _1stSectionToken.getTokenNo());
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
                                                                                                    newKeyExpiryNumber,
                                                                                                    newTariffIndex);
            assertEquals("test that the correct token is generated", "15361891762113502242", _2ndSectionToken.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2CTSA05TestGeneratingFirstSectionFlippedTariffIndex() {
        try {
            DecoderKey[] flippedDecoderKeys = flipTariffIndexRegenerateDecoderKeys();
            DecoderKey initialDecoderKey = flippedDecoderKeys[0];
            DecoderKey newDecoderKey = flippedDecoderKeys[1];
            Set1stSectionDecoderKeyToken _1stSectionToken = generateSet1stSectionDecoderKeyToken(initialDecoderKey, newDecoderKey);
            assertEquals("test that the correct token is generated", "26553210520543055412", _1stSectionToken.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2CTSA05TestGeneratingSecondSectionFlippedTariffIndex() {
        try {
            DecoderKey[] flippedDecoderKeys = flipTariffIndexRegenerateDecoderKeys();
            DecoderKey initialDecoderKey = flippedDecoderKeys[0];
            DecoderKey newDecoderKey = flippedDecoderKeys[1];
            Set2ndSectionDecoderKeyToken _2ndSectionToken = generateSet2ndSectionDecoderKeyToken(initialDecoderKey,
                                                                                                    newDecoderKey,
                                                                                                    newKeyExpiryNumber,
                                                                                                    initialTariffIndex);
            assertEquals("test that the correct token is generated", "00943705441908264439", _2ndSectionToken.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step3CTSA05TestGeneratingFirstSection() {
        changeMeterPANValue();
        try {
            Set1stSectionDecoderKeyToken _1stSectionToken = generateSet1stSectionDecoderKeyToken(initialDecoderKey, newDecoderKey);
            assertEquals("test that the correct token is generated, test 3", "36495265416911568628", _1stSectionToken.getTokenNo());
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
                                                                                                    newKeyExpiryNumber,
                                                                                                    newTariffIndex);
            assertEquals("test that the correct token is generated, test 2", "35908059266238070883", _2ndSectionToken.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private DecoderKey[] flipTariffIndexRegenerateDecoderKeys() {
        DecoderKey initialDecoderKey = null;
        DecoderKey newDecoderKey = null;
        try {
            initialDecoderKeyGenerator = new DecoderKeyGeneratorAlgorithm02(initialKeyType, initialSupplyGroupCode, newTariffIndex,
                                                                            initialKeyRevisionNumber, issuerIdentificationNumber, iain, initialVudk);
            newDecoderKeyGenerator = new DecoderKeyGeneratorAlgorithm02(newKeyType, newSupplyGroupCode, initialTariffIndex,
                                                                        newKeyRevisionNumber, issuerIdentificationNumber, iain, newVudk);
            initialDecoderKey = initialDecoderKeyGenerator.generate();
            newDecoderKey = newDecoderKeyGenerator.generate();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        return new DecoderKey[] {initialDecoderKey, newDecoderKey};
    }

    private Set1stSectionDecoderKeyToken generateSet1stSectionDecoderKeyToken(DecoderKey initialDecoderKey, DecoderKey newDecoderKey) {
        Set1stSectionDecoderKeyToken _1stSectionToken = null;
        try {
            String requestID = "request_id";
            BitString kenhoBitString = new BitString(initialKeyExpiryNumber.getValue()).extractBits(4, 4);
            KeyExpiryNumberHighOrder keyExpiryNumberHighOrder = new KeyExpiryNumberHighOrder(kenhoBitString);
            BitString rolloverKeyChangeBitString = new BitString("0");
            rolloverKeyChangeBitString.setLength(1);
            RolloverKeyChange rolloverKeyChange = new RolloverKeyChange(rolloverKeyChangeBitString);
            KeyExpiryNumber keyExpiryNumber = new KeyExpiryNumber(255);
            Set1stSectionDecoderKeyTokenGenerator _1stSectionGenerator = new Set1stSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                    keyExpiryNumberHighOrder,
                                                                                                                    newKeyRevisionNumber,
                                                                                                                    rolloverKeyChange,
                                                                                                                    newKeyType,
                                                                                                                    initialDecoderKey,
                                                                                                                    newDecoderKey,
                                                                                                                    staEncryptionAlgorithm);
            _1stSectionToken = _1stSectionGenerator.generate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _1stSectionToken;
    }

    private Set2ndSectionDecoderKeyToken generateSet2ndSectionDecoderKeyToken(DecoderKey initialDecoderKey,
                                                                              DecoderKey newDecoderKey,
                                                                              KeyExpiryNumber newKeyExpiryNumber,
                                                                              TariffIndex newTariffIndex) {
        Set2ndSectionDecoderKeyToken _2ndSectionToken = null ;
        try {
            String requestID = "request_id";
            BitString kenloBitString = new BitString(newKeyExpiryNumber.getValue()).extractBits(0, 4);
            KeyExpiryNumberLowOrder kenlo = new KeyExpiryNumberLowOrder(kenloBitString);
            Set2ndSectionDecoderKeyTokenGenerator _2ndSectionGenerator = new Set2ndSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                    kenlo,
                                                                                                                    newTariffIndex,
                                                                                                                    initialDecoderKey,
                                                                                                                    newDecoderKey,
                                                                                                                    staEncryptionAlgorithm);
            _2ndSectionToken = _2ndSectionGenerator.generate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _2ndSectionToken;
    }

    private void changeMeterPANValue() {
        // @TODO: Generate Util to create PANBlock and CONTROLBlock from meterPAN
        try {
            meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("000001000000000082", NO_METER_PAN_VALIDATION);
            issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            initialDecoderKeyGenerator = new DecoderKeyGeneratorAlgorithm02(initialKeyType, initialSupplyGroupCode, initialTariffIndex,
                                                                            initialKeyRevisionNumber, issuerIdentificationNumber, iain, initialVudk);
            newDecoderKeyGenerator = new DecoderKeyGeneratorAlgorithm02(newKeyType, newSupplyGroupCode, newTariffIndex,
                                                                        newKeyRevisionNumber, issuerIdentificationNumber, iain, newVudk);
            initialDecoderKey = initialDecoderKeyGenerator.generate();
            newDecoderKey = newDecoderKeyGenerator.generate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
