package ke.co.nectar.token.generators.tokensdecoder.error;

public abstract class AuthenticationError extends Error {

    public AuthenticationError(String name, String errorCodeValue) {
        super(name, errorCodeValue) ;
    }
}

