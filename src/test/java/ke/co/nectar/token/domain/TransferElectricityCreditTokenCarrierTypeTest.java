package ke.co.nectar.token.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransferElectricityCreditTokenCarrierTypeTest {

    @Test
    public void testThatCorrectTokenCarrierTypeTestsAreSet() {
        TokenCarrierType.Code reservedCode = TokenCarrierType.Code.RESERVED;
        TokenCarrierType tokenCarrierType = new TokenCarrierType(reservedCode);
        assertEquals("test that token carrier type is set", TokenCarrierType.Code.RESERVED, tokenCarrierType.getValue());
    }
}