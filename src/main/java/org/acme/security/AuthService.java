package org.acme.security;

import io.smallrye.jwt.build.Jwt;
import java.util.HashSet;
import java.util.Set;

import org.acme.models.entities.PatientEntity;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthService {

    public String generatePatientToken(PatientEntity patient) {
        return Jwt.issuer("your-issuer")
                  .upn(patient.getEmail())
                  .groups(new HashSet<>(Set.of("AUTHORIZED_PATIENT")))
                  .claim("patientId", patient.getId())
                  .sign();
    }
}
