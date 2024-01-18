package ke.co.nectar.token.domain.keys.vending;

import ke.co.nectar.token.exceptions.InvalidKeyDataException;

public class VendingUniqueDESKey extends VendingKey {

    private final String NAME = "Vending Unique DES Key";

    public VendingUniqueDESKey(byte[] keyData)
        throws InvalidKeyDataException {
        super(keyData);
    }

    public String getName() {
        return NAME;
    }
}
