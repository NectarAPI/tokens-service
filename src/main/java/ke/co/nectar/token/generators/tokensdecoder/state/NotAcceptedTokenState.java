package ke.co.nectar.token.generators.tokensdecoder.state;

public class NotAcceptedTokenState extends TokenState {

    private final static boolean ACCEPTED = false ;
    final static String STATE_VALUE = "Token was not successfully processed" ;

    public NotAcceptedTokenState () {
        super (ACCEPTED, STATE_VALUE) ;
    }
}
