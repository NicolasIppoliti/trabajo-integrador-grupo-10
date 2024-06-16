package org.acme.security;

import org.acme.models.entities.PatientEntity;
import org.acme.repositories.PatientRepository;
import org.acme.security.utils.TokenUtils;
import org.eclipse.microprofile.jwt.Claims;

import org.jboss.logmanager.Logger;

import org.jose4j.jwt.JwtClaims;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@ApplicationScoped
public class TokenService {

  @Inject
  PatientRepository patientRepository;



    public final static Logger LOGGER = Logger.getLogger(TokenService.class.getSimpleName());
  
  
  
    public String generatePatientToken(PatientEntity patient) {
      try {

          JwtClaims jwtClaims = new JwtClaims();
          jwtClaims.setIssuer("alMedin");
          jwtClaims.setJwtId(UUID.randomUUID().toString());
          jwtClaims.setSubject(patient.getEmail());
          jwtClaims.setClaim(Claims.upn.name(), patient.getEmail());
          jwtClaims.setClaim(Claims.preferred_username.name(), patient.getEmail());
          jwtClaims.setClaim("id", patient.getId());
          jwtClaims.setClaim("firstName", patient.getFirstName());
          jwtClaims.setClaim("lastName", patient.getLastName());
          jwtClaims.setClaim(Claims.groups.name(), Collections.singletonList(patient.getRole().name()));
          jwtClaims.setAudience("using-jwt");
          jwtClaims.setExpirationTimeMinutesInTheFuture(60);
  
          String token = TokenUtils.generateTokenString(jwtClaims);
          LOGGER.info("TOKEN generated: " + token);
          return token;
  
      } catch (Exception e) {
          e.printStackTrace();
          throw new RuntimeException(e);
      }
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