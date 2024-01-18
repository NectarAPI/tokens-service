package ke.co.nectar.token.generators.tokensdecoder.error;

public class FunctionError extends TokenError {

    private final static String NAME = "TokenError" ;
    private final static String MESSAGE = "The particular function to execute the ke.co.nectar.ke.co.nectar.token is not implemented" ;

    public FunctionError () {
        super (NAME, MESSAGE) ;
    }
}