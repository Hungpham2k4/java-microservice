//package com.microservice.auth_service.oauth2.user;
//
//import java.util.Map;
//
//public class GithubOAuth2UserInfo extends OAuth2UserInfo {
//
//    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
//        super(attributes);
//    }
//
//    @Override
//    public String getId() {
//        return ((Integer)attributes.get("id")).toString();
//    }
//
//    @Override
//    public String getName() {
//        return attributes.get("name") != null ? (String) attributes.get("name") : (String)attributes.get("login");
//    }
//
//    @Override
//    public String getEmail() {
//        return (String)attributes.get("email");
//    }
//
//    @Override
//    public String getImageUrl() {
//        return (String) attributes.get("avatar_url");
//    }
//
//    public String getLogin() {
//        return (String) attributes.get("login");
//    }
//}