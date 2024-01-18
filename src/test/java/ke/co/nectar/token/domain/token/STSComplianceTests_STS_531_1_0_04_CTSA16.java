package ke.co.nectar.token.domain.token;

import ke.co.nectar.ca.keys.utils.HexByteUtils;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.Misty1AlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.keys.vending.VendingUniqueDESKey;
import ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber;
import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.exceptions.InvalidVendingOrDecoderKeyException;
import ke.co.nectar.token.generators.decoderkeygenerator.DecoderKeyGeneratorAlgorithm04;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class0.TransferElectricityCreditTokenGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;

public class STSComplianceTests_STS_531_1_0_04_CTSA16 {

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
    private Misty1AlgorithmEncryptionAlgorithm misty1EncryptionAlgorithm = new Misty1AlgorithmEncryptionAlgorithm();
    private final BaseDate BASE_DATE = BaseDate._1993;
    private DecoderKey decoderKey;
    private BaseDate baseDate;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        try {
            baseDate = BaseDate._1993;
            meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            magneticCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.MAGNETIC_CARD);
            virtualCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.VIRTUAL_TOKEN_CARRIER);
            issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();

            vudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab949494949494949401234567"));
            supplyGroupCode = new SupplyGroupCode("123456");
            tariffIndex = new TariffIndex("01");
            keyRevisionNumber = new KeyRevisionNumber(1);
            keyType = new KeyType(2);
            keyExpiryNumber = new KeyExpiryNumber(85);
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();

            decoderKeyAlgorithm04Generator = new DecoderKeyGeneratorAlgorithm04(baseDate, tariffIndex, supplyGroupCode,
                                                                                keyType, keyRevisionNumber, misty1EncryptionAlgorithm,
                                                                                meterPrimaryAccountNumber, vudk);
            decoderKey = decoderKeyAlgorithm04Generator.generate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void step1CTSA14Test() throws Exception {
        String requestID = "request_id";
        String dateTime = "01/03/2014 09:00:59";
        DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
        TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BASE_DATE);
        BitString randomBitString = new BitString(13l);
        randomBitString.setLength(4);
        RandomNo randomNo = new RandomNo(randomBitString); // Tokens in tests generated using RND = 5
        Amount amountPurchased = new Amount(25.6);
        TransferElectricityCreditTokenGenerator generator = new TransferElectricityCreditTokenGenerator(requestID,
                                                                                                        tokenIdentifier,
                                                                                                        randomNo,
                                                                                                        amountPurchased,
                                                                                                        keyExpiryNumber,
                                                                                                        decoderKey,
                                                                                                        misty1EncryptionAlgorithm);
        expectedException.expect(InvalidVendingOrDecoderKeyException.class);
        expectedException.expectMessage("Invalid Vending or Decoder Key Exception");
        generator.generate();
    }
}
