package ke.co.nectar.token.generators.tokensdecoder.error;

public class DDTKError extends ValidationError {

    private static final String NAME = "DDTKError" ;
    private static final String VALUE = "The Decoder has a DDTK value in the DKR; a TransferCredit\n" +
            "ke.co.nectar.ke.co.nectar.token may not be processed by the MeterApplicationProcess in\n" +
            "accordance with the rules given in 6.5.2.3.3" ;

    public DDTKError () {
        super(NAME, VALUE) ;
    }
}