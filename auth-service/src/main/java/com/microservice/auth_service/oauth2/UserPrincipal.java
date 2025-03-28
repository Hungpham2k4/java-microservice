//package com.microservice.auth_service.oauth2;
//
//import com.microservice.auth_service.model.User;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//@Setter
//public class UserPrincipal implements OAuth2User, UserDetails {
//    private int id;
//    private String username;
//    private String password;
//    private Collection<? extends GrantedAuthority> authorities;
//    private Map<String, Object> attributes;
//
//    public UserPrincipal(int id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.authorities = authorities;
//    }
//
//    public static UserPrincipal create(User user) {
//        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), authorities);
//    }
//
//    public static UserPrincipal create(User user, Map<String, Object> attributes) {
//        UserPrincipal userPrincipal = UserPrincipal.create(user);
//        userPrincipal.setAttributes(attributes);
//        return userPrincipal;
//    }
//
//    @Override
//    public String getPassword() {
//        return "";
//    }
//
//    @Override
//    public String getUsername() {
//        return "";
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return Map.of();
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }
//
//    @Override
//    public String getName() {
//        return "";
//    }
//}