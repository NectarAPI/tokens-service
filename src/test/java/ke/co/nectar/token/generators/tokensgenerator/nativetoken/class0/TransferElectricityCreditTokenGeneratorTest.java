package ke.co.nectar.token.generators.tokensgenerator.nativetoken.class0;

import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.StandardTransferAlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.token.class0.TransferElectricityCreditToken;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TransferElectricityCreditTokenGeneratorTest {

    @Test
    public void testThatTransferElectricityCreditTokenGeneratorGeneratesValidTokens() {
        try {
            String requestID = "request_id";

            // initialize the TransferElectricityCreditToken Identifier
            String dateTime = "25/03/1996 13:55:22";
            DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._1993);

            // initialize the random value
            BitString randomValueBitString = new BitString(0xbl);
            randomValueBitString.setLength(4);
            RandomNo randomNo = new RandomNo(randomValueBitString);

            // initialize the amount
            double unitsPurchased = 25.6;
            Amount amountPurchased = new Amount(unitsPurchased);

            // Initialize the encryption algorithm keys
            DecoderKey decoderKey = createDecoderKey();

            // Set the Key Expiry number
            KeyExpiryNumber keyExpiryNumber = new KeyExpiryNumber(255);

            // initialize the transfer credit token generator instance
            StandardTransferAlgorithmEncryptionAlgorithm staEncryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
            TransferElectricityCreditTokenGenerator tokenGenerator
                    = new TransferElectricityCreditTokenGenerator(requestID, tokenIdentifier, randomNo, amountPurchased, keyExpiryNumber,
                                                                    decoderKey, staEncryptionAlgorithm );
            TransferElectricityCreditToken generatedToken = tokenGenerator.generate();

            // run tests
            assertEquals("test that the generated token is valid", "29054347139309851356", generatedToken.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    //-- UTILS
    private DecoderKey createDecoderKey() {
        DecoderKey decoderKey = new DecoderKey();

        // keys is as specified in the standard
        byte[] keyBytes = { -119, 103, 69, -13, -34, 18, -68, 10 };
        decoderKey.setKeyData(keyBytes);
        return decoderKey;
    }
}