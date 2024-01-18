package ke.co.nectar.token.domain.keys.vending;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.keys.Key;
import ke.co.nectar.token.generators.utils.Utils;

public abstract class VendingKey extends Key {

    public VendingKey(){}

    public VendingKey(byte[] keyData) {
        super(keyData);
    }

    public String bitsToString() {
        return new String(keyData);
    }

    public BitString getBitString() {
        return new BitString(Utils.bytesToLong(keyData));
    }
}
