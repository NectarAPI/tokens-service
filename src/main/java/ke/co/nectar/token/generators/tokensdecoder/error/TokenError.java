package ke.co.nectar.token.generators.tokensdecoder.error;

public class TokenError extends Error {

    public TokenError(String name, String errorCodeValue) {
        super(name, errorCodeValue) ;
    }
}