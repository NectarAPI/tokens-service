package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControlBlockTest {

    private ControlBlock controlBlock;
    private KeyType keyType;
    private SupplyGroupCode supplyGroupCode;
    private TariffIndex tariffIndex;
    private KeyRevisionNumber keyRevisionNumber;

    @Before
    public void setUp() {
        keyType = mock(KeyType.class);
        supplyGroupCode = mock(SupplyGroupCode.class);
        tariffIndex = mock(TariffIndex.class);
        keyRevisionNumber = mock(KeyRevisionNumber.class);
    }

    @Test
    public void testThatCorrectControlBlockIsGenerated() {
        when(keyType.getValue()).thenReturn(1);
        when(supplyGroupCode.getValue()).thenReturn("123456");
        when(tariffIndex.getValue()).thenReturn("01");
        when(keyRevisionNumber.getValue()).thenReturn(1);
        controlBlock = new ControlBlock(keyType, supplyGroupCode, tariffIndex, keyRevisionNumber);
        assertEquals("test that the correct controlblock is generated", "1123456011FFFFFF", controlBlock.getValue());
    }

}