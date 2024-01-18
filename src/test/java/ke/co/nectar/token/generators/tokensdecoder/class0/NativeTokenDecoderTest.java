package ke.co.nectar.token.generators.tokensdecoder.class0;

import ke.co.nectar.token.domain.encryptionalgorithm.StandardTransferAlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.token.class0.TransferElectricityCreditToken;
import ke.co.nectar.token.domain.token.class0.TransferGasCreditToken;
import ke.co.nectar.token.domain.token.class0.TransferWaterCreditToken;
import ke.co.nectar.token.domain.token.class1.InitiateMeterTestOrDisplay1Token;
import ke.co.nectar.token.domain.token.class1.InitiateMeterTestOrDisplay2Token;
import ke.co.nectar.token.domain.token.class2.*;
import ke.co.nectar.token.generators.tokensdecoder.Meter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class NativeTokenDecoderTest {

    @Test
    public void testThatElectricityTokenIsDecodedCorrectly() {
        try {
            String requestID = "request_id";
            DecoderKey decoderKey = Utils.createDecoderKey();
            Meter meter = new Meter("29054347139309851356", decoderKey,
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferElectricityCreditToken decodedToken = (TransferElectricityCreditToken) meter.decodeNative(requestID);

            assertEquals("test that correct request ID is returned", "request_id", decodedToken.getRequestID());
            assertEquals("test that correct tid is returned", "1996-03-25T13:55:00.000+03:00", decodedToken.getTokenIdentifier().getTimeOfIssue().toString());
            assertEquals("test that correct random number is returned", "1011", decodedToken.getRandomNo().get().bitsToString());
            assertEquals("test that correct amount is returned", 25.6, decodedToken.getAmountPurchased().getAmountPurchased(), 0.1);
            assertEquals("test that correct type is returned", "Electricity_00", decodedToken.getType());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatWaterTokenIsDecodedCorrectly() {
        try {
            String requestID = "request_id";
            DecoderKey decoderKey = Utils.createDecoderKey();
            Meter meter = new Meter("47445274807975686464", decoderKey,
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferWaterCreditToken decodedToken = (TransferWaterCreditToken) meter.decodeNative(requestID);

            assertEquals("test that correct request ID is returned", "request_id", decodedToken.getRequestID());
            assertEquals("test that correct tid is returned", "1996-03-25T13:55:00.000+03:00", decodedToken.getTokenIdentifier().getTimeOfIssue().toString());
            assertEquals("test that correct random number is returned", "1011", decodedToken.getRandomNo().get().bitsToString());
            assertEquals("test that correct amount is returned", 35.6, decodedToken.getAmountPurchased().getAmountPurchased(), 0.1);
            assertEquals("test that correct type is returned", "Water_01", decodedToken.getType());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatGasTokenIsDecodedCorrectly() {
        try {
            String requestID = "request_id";
            DecoderKey decoderKey = Utils.createDecoderKey();
            Meter meter = new Meter("36694449120075654222", decoderKey,
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            TransferGasCreditToken decodedToken = (TransferGasCreditToken) meter.decodeNative(requestID);

            assertEquals("test that correct request ID is returned", "request_id", decodedToken.getRequestID());
            assertEquals("test that correct tid is returned", "1996-03-25T13:55:00.000+03:00", decodedToken.getTokenIdentifier().getTimeOfIssue().toString());
            assertEquals("test that correct random number is returned", "1011", decodedToken.getRandomNo().get().bitsToString());
            assertEquals("test that correct amount is returned", 45.6, decodedToken.getAmountPurchased().getAmountPurchased(), 0.1);
            assertEquals("test that correct type is returned", "Gas_02", decodedToken.getType());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatInitiateMeterTestOrDisplay1TokenIsDecodedCorrectly() {
        try {
            String requestID = "request_id";
            DecoderKey decoderKey = Utils.createDecoderKey();
            Meter meter = new Meter("00000000000150997584", decoderKey,
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            InitiateMeterTestOrDisplay1Token decodedToken = (InitiateMeterTestOrDisplay1Token) meter.decodeNative(requestID);

            assertEquals("test that correct request ID is returned", "request_id", decodedToken.getRequestID());
            assertEquals("test that correct tid is returned", "0000", decodedToken.getManufacturerCode().getValue());
            assertEquals("test that correct random number is returned", 1, decodedToken.getControl().getBitString().getValue());
            assertEquals("test that the correct type is returned", "InitiateMeterTestOrDisplay1_10", decodedToken.getType());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatInitiateMeterTestOrDisplay2TokenIsDecodedCorrectly() {
        try {
            String requestID = "request_id";
            DecoderKey decoderKey = Utils.createDecoderKey();
            Meter meter = new Meter("01152921509036054672", decoderKey,
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            InitiateMeterTestOrDisplay2Token decodedToken = (InitiateMeterTestOrDisplay2Token) meter.decodeNative(requestID);

            assertEquals("test that correct request ID is returned", "request_id", decodedToken.getRequestID());
            assertEquals("test that correct tid is returned", "0000000000000000", decodedToken.getManufacturerCode().getValue());
            assertEquals("test that correct random number is returned", 1, decodedToken.getControl().getBitString().getValue());
            assertEquals("test that the correct type is returned", "InitiateMeterTestOrDisplay1_11", decodedToken.getType());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatSetMaximumPowerLimitTokenIsDecodedCorrectly() {
        try {
            String requestID = "request_id";
            DecoderKey decoderKey = Utils.createDecoderKey();
            Meter meter = new Meter("36190854030032778902", decoderKey,
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            SetMaximumPowerLimitToken decodedToken = (SetMaximumPowerLimitToken) meter.decodeNative(requestID);

            assertEquals("test that correct request ID is returned", "request_id", decodedToken.getRequestID());
            assertEquals("test that correct tid is returned", "1996-03-25T13:55:00.000+03:00", decodedToken.getTokenIdentifier().getTimeOfIssue().toString());
            assertEquals("test that correct random number is returned", "1011", decodedToken.getRandomNo().get().bitsToString());
            assertEquals("test that correct mppul is returned", 10, decodedToken.getMaximumPowerLimit().getMaximumPowerLimit());
            assertEquals("test that the correct type is returned", "SetMaximumPowerLimit_20", decodedToken.getType());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatClearCreditTokenIsDecodedCorrectly() {
        try {
            String requestID = "request_id";
            DecoderKey decoderKey = Utils.createDecoderKey();
            Meter meter = new Meter("64677297699170608942", decoderKey,
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            ClearCreditToken decodedToken = (ClearCreditToken) meter.decodeNative(requestID);

            assertEquals("test that correct request ID is returned", "request_id", decodedToken.getRequestID());
            assertEquals("test that correct tid is returned", "1996-03-25T13:55:00.000+03:00", decodedToken.getTokenIdentifier().getTimeOfIssue().toString());
            assertEquals("test that correct random number is returned", "1011", decodedToken.getRandomNo().get().bitsToString());
            assertEquals("test that correct register is returned", "1111111111111111", decodedToken.getRegister().get().getBitString().toString());
            assertEquals("test that the correct type is returned", "ClearCredit_21", decodedToken.getType());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatSetTariffRateTokenIsDecodedCorrectly() {
        try {
            String requestID = "request_id";
            DecoderKey decoderKey = Utils.createDecoderKey();
            Meter meter = new Meter("19631747338835776219", decoderKey,
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            SetTariffRateToken decodedToken = (SetTariffRateToken) meter.decodeNative(requestID);

            assertEquals("test that correct request ID is returned", "request_id", decodedToken.getRequestID());
            assertEquals("test that correct tid is returned", "1996-03-25T13:55:00.000+03:00", decodedToken.getTokenIdentifier().getTimeOfIssue().toString());
            assertEquals("test that correct random number is returned", "1011", decodedToken.getRandomNo().get().bitsToString());
            assertEquals("test that correct rate is returned", "1111111111111111", decodedToken.getRate().get().getRateBitString().toString());
            assertEquals("test that the correct type is returned", "SetTariffRate_22", decodedToken.getType());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatSet1stSectionDecoderKeyTokenIsDecodedCorrectly() {
        try {
            String requestID = "request_id";
            DecoderKey decoderKey = Utils.createDecoderKey();
            Meter meter = new Meter("33981812261387035127", decoderKey,
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            Set1stSectionDecoderKeyToken decodedToken = (Set1stSectionDecoderKeyToken) meter.decodeNative(requestID);

            assertEquals("test that correct request ID is returned", "request_id", decodedToken.getRequestID());
            assertEquals("test that correct tid is returned", "0", decodedToken.get3KCT().getBitString().toString());
            assertEquals("test that correct random number is returned", "1111", decodedToken.getKeyExpiryNumberHighOrder().getBitString().toString());
            assertEquals("test that correct random number is returned", 2, decodedToken.getKeyRevisionNumber().getValue());
            assertEquals("test that correct random number is returned", 2, decodedToken.getKeyType().getValue());
            assertEquals("test that correct random number is returned", "1010101111000001001011011110", decodedToken.getNewKeyHighOrder().getBitString().toString());
            assertEquals("test that correct random number is returned", 0, decodedToken.getRolloverKeyChange().getBitString().getValue());
            assertEquals("test that the correct type is returned", "Set1stSectionDecoderKey_23", decodedToken.getType());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatSet2ndSectionDecoderKeyTokenIsDecodedCorrectly() {
        try {
            String requestID = "request_id";
            DecoderKey decoderKey = Utils.createDecoderKey();
            Meter meter = new Meter("14367398748900528122", decoderKey,
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            Set2ndSectionDecoderKeyToken decodedToken = (Set2ndSectionDecoderKeyToken) meter.decodeNative(requestID);

            assertEquals("test that correct request ID is returned", "request_id", decodedToken.getRequestID());
            assertEquals("test that correct tid is returned", "1111", decodedToken.getKeyExpiryNumberLowOrder().getKenloBitString().toString());
            assertEquals("test that correct random number is returned", "11110011010001010110011110001001", decodedToken.getNewKeyLowOrder().getNkloBitString().toString());
            assertEquals("test that correct random number is returned", "01", decodedToken.getTariffIndex().getValue());
            assertEquals("test that correct random number is returned", "Set2ndSectionDecoderKey_24", decodedToken.getType());
            assertEquals("test that the correct type is returned", "Set2ndSectionDecoderKey_24", decodedToken.getType());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatClearTamperConditionTokenIsDecodedCorrectly() {
        try {
            String requestID = "request_id";
            DecoderKey decoderKey = Utils.createDecoderKey();
            Meter meter = new Meter("61329751000448523691", decoderKey,
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            ClearTamperConditionToken decodedToken = (ClearTamperConditionToken) meter.decodeNative(requestID);

            assertEquals("test that correct request ID is returned", "request_id", decodedToken.getRequestID());
            assertEquals("test that correct tid is returned", "1996-03-25T13:55:00.000+03:00", decodedToken.getTokenIdentifier().getTimeOfIssue().toString());
            assertEquals("test that correct random number is returned", "0", decodedToken.getPad().get().getBitString().toString());
            assertEquals("test that correct random number is returned", "ClearTamperCondition_25", decodedToken.getType());
            assertEquals("test that the correct type is returned", "ClearTamperCondition_25", decodedToken.getType());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatSetMaximumPhasePowerUnbalanceLimitTokenIsDecodedCorrectly() {
        try {
            String requestID = "request_id";
            DecoderKey decoderKey = Utils.createDecoderKey();
            Meter meter = new Meter("52187765980459788390", decoderKey,
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            SetMaximumPhasePowerUnbalanceLimitToken decodedToken = (SetMaximumPhasePowerUnbalanceLimitToken) meter.decodeNative(requestID);

            assertEquals("test that correct request ID is returned", "request_id", decodedToken.getRequestID());
            assertEquals("test that correct tid is returned", "1996-03-25T13:55:00.000+03:00", decodedToken.getTokenIdentifier().getTimeOfIssue().toString());
            assertEquals("test that correct random number is returned", "1011", decodedToken.getRandomNo().get().bitsToString());
            assertEquals("test that correct random number is returned", 256, decodedToken.getMaximumPhasePowerUnbalanceLimit().get().getMppulBitString().getValue());
            assertEquals("test that the correct type is returned", "SetMaximumPhasePowerUnbalanceLimit_26", decodedToken.getType());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatSetWaterMeterFactorTokenIsDecodedCorrectly() {
        try {
            String requestID = "request_id";
            DecoderKey decoderKey = Utils.createDecoderKey();
            Meter meter = new Meter("32930166484451313864", decoderKey,
                    new StandardTransferAlgorithmEncryptionAlgorithm());
            SetWaterMeterFactorToken decodedToken = (SetWaterMeterFactorToken) meter.decodeNative(requestID);
            assertEquals("test that correct request ID is returned", "request_id", decodedToken.getRequestID());
            assertEquals("test that correct tid is returned", "1996-03-25T13:55:00.000+03:00", decodedToken.getTokenIdentifier().getTimeOfIssue().toString());
            assertEquals("test that correct random number is returned", "1011", decodedToken.getRandomNo().get().bitsToString());
            assertEquals("test that correct random number is returned", "1111111111111111", decodedToken.getWaterMeterFactor().get().getWmfBitString().toString());
            assertEquals("test that the correct type is returned", "SetWaterMeterFactor_27", decodedToken.getType());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
