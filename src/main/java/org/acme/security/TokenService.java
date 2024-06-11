package org.acme.security;

import org.acme.security.utils.TokenUtils;
import org.eclipse.microprofile.jwt.Claims;

import org.jboss.logmanager.Logger;

import org.jose4j.jwt.JwtClaims;

//import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Arrays;

import java.util.UUID;

@ApplicationScoped
public class TokenService {



    public final static Logger LOGGER = Logger.getLogger(TokenService.class.getSimpleName());
  
  
  
    public String generatePatientToken(String email, String username) {
  
      return generateToken(email, username, Roles.PATIENT);
  
    }
  
  
  
    public String generateAuthorizedPatientToken(String serviceId, String serviceName) {
  
      return generateToken(serviceId,serviceName,Roles.AUTHORIZED_PATIENT);
  
    }
  
  
  
    public String generateToken(String subject, String name, String... roles) {
  
      try {
  
        JwtClaims jwtClaims = new JwtClaims();
  
        jwtClaims.setIssuer("alMedin"); // change to your company
  
        jwtClaims.setJwtId(UUID.randomUUID().toString());
  
        jwtClaims.setSubject(subject);
  
        jwtClaims.setClaim(Claims.upn.name(), subject);
  
        jwtClaims.setClaim(Claims.preferred_username.name(), name); //add more
  
        jwtClaims.setClaim(Claims.groups.name(), Arrays.asList(roles));
  
        jwtClaims.setAudience("using-jwt");
  
        jwtClaims.setExpirationTimeMinutesInTheFuture(60); // TODO specify how long do you need
  
  
  
  
  
        String token = TokenUtils.generateTokenString(jwtClaims);
  
        LOGGER.info("TOKEN generated: " + token);
  
        return token;
  
      } catch (Exception e) {
  
        e.printStackTrace();
  
        throw new RuntimeException(e);
  
      }
  
    }
  
  }