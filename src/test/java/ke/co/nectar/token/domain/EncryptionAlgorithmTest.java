package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.encryptionalgorithm.EncryptionAlgorithm;
import ke.co.nectar.token.domain.encryptionalgorithm.StandardTransferAlgorithmEncryptionAlgorithm;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EncryptionAlgorithmTest {

    @Test
    public void testThatEncryptionAlgorithmReturnsCorrectProperties() {
        StandardTransferAlgorithmEncryptionAlgorithm encryptionAlgorithm = new StandardTransferAlgorithmEncryptionAlgorithm();
        assertEquals("test that the correct encryption algorithm is set", EncryptionAlgorithm.Code.STA, encryptionAlgorithm.getCode());
        assertEquals("test that the name of the encryption algorithm is set", "Encryption Algorithm", encryptionAlgorithm.getName());
    }

}