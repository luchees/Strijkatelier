package com.strike.strijkatelier.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(String token);

}
