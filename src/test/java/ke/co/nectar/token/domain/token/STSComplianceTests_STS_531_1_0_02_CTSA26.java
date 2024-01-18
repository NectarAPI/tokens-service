package ke.co.nectar.token.domain.token;

import ke.co.nectar.ca.keys.utils.HexByteUtils;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.StandardTransferAlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.keys.vending.VendingUniqueDESKey;
import ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber;
import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.generators.decoderkeygenerator.DecoderKeyGeneratorAlgorithm04;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.Set1stSectionDecoderKeyTokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2.Set2ndSectionDecoderKeyTokenGenerator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;
import static org.junit.Assert.assertEquals;

public class STSComplianceTests_STS_531_1_0_02_CTSA26 {

    private MeterPrimaryAccountNumber meterPrimaryAccountNumber;
    private TokenCarrierType magneticCardTokenCarrierType;
    private TokenCarrierType virtualCardTokenCarrierType;

    private VendingUniqueDESKey vudk;
    private DecoderKeyGeneratorAlgorithm04 decoderKeyAlgorithm04Generator;
    private DecoderKeyGeneratorAlgorithm04 newDecoderKeyAlgorithm04Generator;
    private SupplyGroupCode supplyGroupCode;
    private TariffIndex tariffIndex;
    private KeyRevisionNumber keyRevisionNumber;
    private KeyRevisionNumber newKeyRevisionNumber;
    private KeyType keyType;
    private KeyExpiryNumber keyExpiryNumber;

    private IssuerIdentificationNumber issuerIdentificationNumber;
    private IndividualAccountIdentificationNumber iain;
    private StandardTransferAlgorithmEncryptionAlgorithm staEncryptionAlgorithm;
    private BaseDate baseDate;
    private BaseDate newBaseDate;
    private DecoderKey decoderKey;
    private DecoderKey newDecoderKey;
    private Amount amountPurchased;

    private KeyExpiryNumberHighOrder keyExpiryNumberHighOrder;
    private KeyExpiryNumberLowOrder keyExpiryNumberLowOrder;
    private NewKeyHighOrder newKeyHighOrder;
    private NewKeyLowOrder newKeyLowOrder;
    private RolloverKeyChange rolloverKeyChange;
    private RolloverKeyChange newRolloverKeyChange;

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
            baseDate = BaseDate._2014;
            newBaseDate = BaseDate._2035;

            issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            staEncryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();

            vudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab949494949494949401234567"));
            supplyGroupCode = new SupplyGroupCode("123457");
            keyType = new KeyType(2);
            keyExpiryNumber = new KeyExpiryNumber(255);

            BitString kenhoBitString = new BitString(keyExpiryNumber.getValue()).extractBits(4, 4);
            keyExpiryNumberHighOrder = new KeyExpiryNumberHighOrder(kenhoBitString);
            BitString kenloBitString = new BitString(keyExpiryNumber.getValue()).extractBits(0, 4);
            keyExpiryNumberLowOrder = new KeyExpiryNumberLowOrder(kenloBitString);

            BitString rolloverKeyChangeBitString = new BitString("0");
            rolloverKeyChangeBitString.setLength(1);
            rolloverKeyChange = new RolloverKeyChange(rolloverKeyChangeBitString);

            BitString newRolloverKeyChangeBitString = new BitString("1");
            newRolloverKeyChangeBitString.setLength(1);
            newRolloverKeyChange = new RolloverKeyChange(newRolloverKeyChangeBitString);

            keyRevisionNumber = new KeyRevisionNumber(4);
            newKeyRevisionNumber = new KeyRevisionNumber(5);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void step1CTSA19Test() throws Exception {
        generateDecoderKeys();
        String requestID = "request_id";
        Set1stSectionDecoderKeyTokenGenerator set1stSectionDecoderKeyTokenGenerator = new Set1stSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                keyExpiryNumberHighOrder,
                                                                                                                                newKeyRevisionNumber,
                                                                                                                                newRolloverKeyChange,
                                                                                                                                keyType,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                staEncryptionAlgorithm);
        Set2ndSectionDecoderKeyTokenGenerator set2ndSectionDecoderKeyTokenGenerator = new Set2ndSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                keyExpiryNumberLowOrder,
                                                                                                                                tariffIndex,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                staEncryptionAlgorithm);
        assertEquals("test that the correct Set1stSectionDecoderKeyTokenGenerator is generated", "44163577485799480640", set1stSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set2ndSectionDecoderKeyTokenGenerator is generated", "26556810679164981397", set2ndSectionDecoderKeyTokenGenerator.generate().getTokenNo());
    }

    @Test
    public void step2CTSA19Test() throws Exception {
        meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("000001000000000082", NO_METER_PAN_VALIDATION);
        String requestID = "request_id";
        generateDecoderKeys();
        Set1stSectionDecoderKeyTokenGenerator set1stSectionDecoderKeyTokenGenerator = new Set1stSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                keyExpiryNumberHighOrder,
                                                                                                                                newKeyRevisionNumber,
                                                                                                                                newRolloverKeyChange,
                                                                                                                                keyType,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                staEncryptionAlgorithm);
        Set2ndSectionDecoderKeyTokenGenerator set2ndSectionDecoderKeyTokenGenerator = new Set2ndSectionDecoderKeyTokenGenerator(requestID,
                                                                                                                                keyExpiryNumberLowOrder,
                                                                                                                                tariffIndex,
                                                                                                                                decoderKey,
                                                                                                                                newDecoderKey,
                                                                                                                                staEncryptionAlgorithm);
        assertEquals("test that the correct Set1stSectionDecoderKeyTokenGenerator is generated", "09658886361133612086", set1stSectionDecoderKeyTokenGenerator.generate().getTokenNo());
        assertEquals("test that the correct Set2ndSectionDecoderKeyTokenGenerator is generated", "22434017728466234784", set2ndSectionDecoderKeyTokenGenerator.generate().getTokenNo());
    }

    private void generateDecoderKeys()
        throws Exception {
        decoderKeyAlgorithm04Generator = new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                                            keyType, keyRevisionNumber, staEncryptionAlgorithm,
                                                                            meterPrimaryAccountNumber, vudk);
        newDecoderKeyAlgorithm04Generator = new DecoderKeyGeneratorAlgorithm04(newBaseDate, tariffIndex, supplyGroupCode,
                                                                                keyType, newKeyRevisionNumber, staEncryptionAlgorithm,
                                                                                meterPrimaryAccountNumber, vudk);
        decoderKey = decoderKeyAlgorithm04Generator.generate();
        newDecoderKey = newDecoderKeyAlgorithm04Generator.generate();
    }
}
