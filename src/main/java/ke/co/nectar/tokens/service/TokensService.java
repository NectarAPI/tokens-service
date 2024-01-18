package ke.co.nectar.tokens.service;

import ke.co.nectar.tokens.entity.Token;
import ke.co.nectar.tokens.service.impl.TimelineRequest;
import ke.co.nectar.tokens.service.impl.validate.TokenTypeCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TokensService {

    Token getToken(String ref) throws Exception;

    List<Token> getTokens(String userRef) throws Exception;

    List<TimelineRequest> getTimelineRequests(String userRef, int months) throws Exception;

    List<Token> generateTokens(String requestID, Map<String, String> params, String userRef) throws Exception;

    void deleteToken(String ref, String userRef) throws Exception;

    int getGeneratedNoOfTokens(String userRef) throws Exception;

    int getNoOfTokenTypes(String userRef) throws Exception;

    List<TokenTypeCount> getTokenTypes(String userRef) throws Exception;

    int getUniqueMetersNo(String userRef) throws Exception;

    HashMap<String, Object> decodeToken(String requestID, String token, Map<String, String> params, String userRef) throws Exception;

    void deleteTokenByTokenNo(String tokenNo, String userRef) throws Exception;
}
