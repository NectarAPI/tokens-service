package ke.co.nectar.token.generators.tokensdecoder.state;

public abstract class TokenState extends State {

    public TokenState (boolean valid, String stateValue) {
        super(valid,  stateValue);
    }
}
