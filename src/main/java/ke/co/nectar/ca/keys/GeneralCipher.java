package ke.co.nectar.ca.keys;

import ke.co.nectar.ca.Provider;

public abstract class GeneralCipher {

    private Provider provider ;

    public GeneralCipher(Provider provider) {
        this.provider = provider ;
        provider.setProvider() ;
    }

    protected String getProviderAbbr() {
        return provider.getProviderAbbr() ;
    }

    public abstract byte[] encrypt(byte[] plainText)
            throws Exception ;

    public abstract byte[] decrypt(byte[] cipherText)
            throws  Exception ;
}
