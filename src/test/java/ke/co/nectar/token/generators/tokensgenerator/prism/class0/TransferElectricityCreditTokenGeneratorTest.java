package ke.co.nectar.token.generators.tokensgenerator.prism.class0;

import ke.co.nectar.token.domain.Amount;
import ke.co.nectar.token.domain.BaseDate;
import ke.co.nectar.token.domain.TokenIdentifier;
import ke.co.nectar.token.domain.token.class0.TransferElectricityCreditToken;
import ke.co.nectar.token.domain.tokenclass.class0.ElectricityCreditTransferTokenClass;
import ke.co.nectar.token.domain.tokensubclass.class0.ElectricityCreditTransferTokenSubClass;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransferElectricityCreditTokenGeneratorTest {

    private TransferElectricityCreditPrismTokenGenerator generator;


    @Before
    public void setUp() {
        generator = mock(TransferElectricityCreditPrismTokenGenerator.class);
    }

    @Test
    public void testThatTransferElectricityCreditTokenGeneratorGeneratesValidTokens() {
        try {
            String requestID = "request-id";
            String tokenStr = "67819696861845956084";

            Amount amountPurchased = new Amount(10.2);
            TokenIdentifier tokenIdentifier = new TokenIdentifier(DateTime.parse("2022-08-16T07:22:05Z"), BaseDate._1993);

            TransferElectricityCreditToken mockToken = new TransferElectricityCreditToken(requestID,
                    tokenIdentifier, Optional.empty(), amountPurchased);
            mockToken.setEncryptedTokenBitString(new BigInteger(tokenStr).toString(2));

            List<TransferElectricityCreditToken> mockTokens = new ArrayList<>();
            mockTokens.add(mockToken);

            when(generator.generate()).thenReturn(mockTokens);

            List<TransferElectricityCreditToken> generatedTokens = generator.generate();

            TransferElectricityCreditToken token = generatedTokens.get(0);
            assertEquals("test that correct request id is returned", requestID, token.getRequestID());
            assertEquals("test that correct token is returned", "67819696861845956084",token.getTokenNo());
            assertEquals("test that correct token type is returned", "Electricity_00",token.getType());
            assertEquals("test that correct amount purchased is returned", 10.2,token.getAmountPurchased().getAmountPurchased(), 0);
            assertEquals("test that correct token class is returned", ElectricityCreditTransferTokenClass.class, token.getTokenClass().getClass());
            assertEquals("test that correct token subclass is returned", ElectricityCreditTransferTokenSubClass.class, token.getTokenSubClass().getClass());
            assertEquals("test that correct token identifier is returned", 15579982,token.getTokenIdentifier().getDifferenceFromBaseTimeInMinutes());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
