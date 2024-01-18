package ke.co.nectar.token.generators.tokensdecoder.result;

import ke.co.nectar.token.generators.tokensdecoder.state.AcceptTokenState;

public class TokenResult extends Result {

    public TokenResult (AcceptTokenState acceptedTokenState) {
        super (acceptedTokenState, null) ;
    }
}