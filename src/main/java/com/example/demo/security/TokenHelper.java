package com.example.demo.security;//package com.example.demo.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
//import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Collection;
//
//@Component
//public class TokenHelper {
//
//    @Resource(name = "tokenService")
//    private ConsumerTokenServices tokenService;
//    @Autowired
//    private TokenStore tokenStore;
//    @Value("${security.oauth2.client.id}")
//    private String clientId;
//
//
//
//
//    public void removeTokens(OAuth2Authentication auth) {
//        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
//        tokenService.revokeToken(details.getTokenValue());
//    }
//
//
//    public void removeTokensByUsername(String username){
//
//        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(clientId, username);
//        for (OAuth2AccessToken token : tokens) {
//            tokenService.revokeToken(token.getValue());
//        }
//    }
//
//}
