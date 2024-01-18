package ke.co.nectar.token.generators.tokensdecoder.error;

public class KeyExpiredError extends ValidationError {

    private final static String NAME = "KeyExpiredError" ;
    private final static String VALUE = "The TID value as recorded in the ke.co.nectar.ke.co.nectar.token is larger than the KEN\n" +
            "stored in the payment meter memory" ;

    public  KeyExpiredError() {
        super (NAME, VALUE) ;
    }
}
