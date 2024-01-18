package ke.co.nectar.token.domain.token;

import ke.co.nectar.ca.keys.utils.HexByteUtils;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.StandardTransferAlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.keys.vending.VendingUniqueDESKey;
import ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber;
import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.domain.token.class0.TransferGasCreditToken;
import ke.co.nectar.token.generators.decoderkeygenerator.DecoderKeyGeneratorAlgorithm02;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class0.TransferGasCreditTokenGenerator;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class STSComplianceTests_STS_531_1_0_02_CTSA22 {

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
    private final BaseDate BASE_DATE = BaseDate._1993;
    private DecoderKey decoderKey;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        try {
            meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            magneticCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.MAGNETIC_CARD);
            virtualCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.VIRTUAL_TOKEN_CARRIER);
            tariffIndex = new TariffIndex("01");
            issuerIdentificationNumber = meterPrimaryAccountNumber.getIssuerIdentificationNumber();
            iain = meterPrimaryAccountNumber.getIndividualAccountIdentificationNumber();
            staEncryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
            keyRevisionNumber = new KeyRevisionNumber(1);
            vudk = new VendingUniqueDESKey(HexByteUtils.hexStringToByteArr("abababababababab"));
            supplyGroupCode = new SupplyGroupCode("123456");
            keyType = new KeyType(2);
            keyExpiryNumber = new KeyExpiryNumber(255);

            decoderKeyAlgorithm02Generator = new DecoderKeyGeneratorAlgorithm02(keyType, supplyGroupCode, tariffIndex,
                                                                                keyRevisionNumber, issuerIdentificationNumber,
                                                                                iain, vudk);

            decoderKey = decoderKeyAlgorithm02Generator.generate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void step1CTSA22Test() {
        try {
            String dateTime = "26/04/2006 10:01:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(1);
            TransferGasCreditToken token = generateTransferGasCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "30790445533281098559", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2CTSA20Test() {
        try {
            String dateTime = "27/05/2006 10:02:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(16383);
            TransferGasCreditToken token = generateTransferGasCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "42894813892015838675", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step3CTSA20Test() {
        try {
            String dateTime = "23/04/2007 10:03:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(16384);
            TransferGasCreditToken token = generateTransferGasCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "52434363099394415104", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step4CTSA20Test() {
        try {
            String dateTime = "24/04/2007 10:04:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(180224);
            TransferGasCreditToken token = generateTransferGasCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "02188175533508938765", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step5CTSA20Test() {
        try {
            String dateTime = "25/04/2007 11:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(18824);
            TransferGasCreditToken token = generateTransferGasCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "37844075275013703544", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step6CTSA20Test() {
        try {
            String dateTime = "26/04/2007 11:01:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(182624);
            TransferGasCreditToken token = generateTransferGasCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "21792662786579369978", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step7CTSA20Test() {
        try {
            String dateTime = "26/04/2007 11:02:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(18204);
            TransferGasCreditToken token = generateTransferGasCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "49679852898684944605", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step8CTSA20Test() {
        try {
            String dateTime = "26/04/2007 11:03:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(42624);
            TransferGasCreditToken token = generateTransferGasCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "23701256401082544774", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step9CTSA20Test() {
        try {
            String dateTime = "26/04/2007 11:04:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(182044);
            TransferGasCreditToken token = generateTransferGasCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "37625880630538746268", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step10CTSA20Test() {
        try {
            String dateTime = "26/04/2007 11:05:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(17814);
            TransferGasCreditToken token = generateTransferGasCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "41502904237047264837", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step11CTSA20Test() {
        try {
            String dateTime = "26/04/2007 20:10:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(120345);
            TransferGasCreditToken token = generateTransferGasCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "69115163697886873528", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step12CTSA20Test() {
        try {
            String dateTime = "26/04/2007 11:00:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(10449);
            TransferGasCreditToken token = generateTransferGasCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "57141025105111259130", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step13CTSA20Test() {
        try {
            String dateTime = "26/04/2007 20:12:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(12449);
            TransferGasCreditToken token = generateTransferGasCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "07841085711493671481", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step14CTSA20Test() {
        try {
            String dateTime = "26/04/2007 20:14:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(80224);
            TransferGasCreditToken token = generateTransferGasCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "53540735038206224963", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step15CTSA20Test() {
        try {
            String dateTime = "26/04/2007 20:15:00";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            Amount amountPurchased = new Amount(120414);
            TransferGasCreditToken token = generateTransferGasCreditToken(dateOfIssue, amountPurchased);
            assertEquals("test that the correct token is generated", "64397019565756691049", token.getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private TransferGasCreditToken generateTransferGasCreditToken(DateTime timeOfIssue, Amount amount)
            throws Exception {
        String requestID = "request_id";
        TokenIdentifier tokenIdentifier = new TokenIdentifier(timeOfIssue, BASE_DATE);
        BitString randomBitString = new BitString(0x5l);
        randomBitString.setLength(4);
        RandomNo randomNo = new RandomNo(randomBitString);
        TransferGasCreditTokenGenerator generator = new TransferGasCreditTokenGenerator(requestID, tokenIdentifier, randomNo,
                                                                                        amount, keyExpiryNumber,
                                                                                        decoderKey,
                                                                                        staEncryptionAlgorithm);
        TransferGasCreditToken token = generator.generate();
        return token;
    }
}
