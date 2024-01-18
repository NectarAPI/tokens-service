package ke.co.nectar.token.generators.tokensdecoder.class0;

import ke.co.nectar.token.domain.keys.decoder.DecoderKey;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Utils {

    //-- UTILS
    public static DecoderKey createDecoderKey() throws NoSuchAlgorithmException, InvalidKeyException,
            InvalidKeySpecException {
        DecoderKey decoderKey = new DecoderKey();

        // keys is as specified in the standard
        byte[] keyBytes = { -119, 103, 69, -13, -34, 18, -68, 10 };
        decoderKey.setKeyData(keyBytes);
        return decoderKey;
    }
}
