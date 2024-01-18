package ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2;

import ke.co.nectar.token.domain.token.Token;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.TokenGenerator;

public abstract class Class2TokenGenerator<T extends Token> extends TokenGenerator<T>{

    public Class2TokenGenerator(String requestID) {
        super(requestID);
    }
}
