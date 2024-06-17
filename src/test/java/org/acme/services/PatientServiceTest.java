package org.acme.services;

import org.acme.domain.Patient;
import org.acme.mappers.PatientMapper;
import org.acme.models.entities.PatientEntity;
import org.acme.repositories.PatientRepository;
import org.acme.security.TokenService;
import org.acme.utils.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("PatientService Tests")
public class PatientServiceTest {

    @InjectMocks
    PatientService patientService;

    @Mock
    PatientRepository patientRepository;

    @Mock
    PatientMapper patientMapper;

    @Mock
    TokenService tokenService;

    @Mock
    EntityManager entityManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getAll() method")
    public void testGetAllPatients() {
        List<PatientEntity> mockEntities = new ArrayList<>();
        mockEntities.add(new PatientEntity(1L, "John", "Doe", "john.doe@example.com", "password", null, Role.PATIENT, null));
        when(patientRepository.listAll()).thenReturn(mockEntities);

        when(patientMapper.toDomain(any(PatientEntity.class))).thenAnswer(invocation -> {
            PatientEntity entity = invocation.getArgument(0);
            return new Patient(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPassword(), null, entity.getRole());
        });

        List<Patient> patients = patientService.getAll();

        assertNotNull(patients);
        assertEquals(1, patients.size());
        assertEquals("John", patients.get(0).getFirstName());

        verify(patientRepository, times(1)).listAll();
    }

    @Test
    @DisplayName("Test getById() method")
    public void testGetPatientById() {
        PatientEntity mockEntity = new PatientEntity(1L, "John", "Doe", "john.doe@example.com", "password", null, Role.PATIENT, null);
        when(patientRepository.findById(1L)).thenReturn(mockEntity);

        when(patientMapper.toDomain(mockEntity)).thenReturn(new Patient(1L, "John", "Doe", "john.doe@example.com", "password", null, Role.PATIENT));

        Patient patient = patientService.getById(1L);

        assertNotNull(patient);
        assertEquals(1L, patient.getId());
        assertEquals("John", patient.getFirstName());

        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test create() method")
    @Transactional
    public void testCreatePatient() {
        Patient patient = new Patient(1L, "John", "Doe", "john.doe@example.com", "password", null, Role.PATIENT);
        PatientEntity mockEntity = new PatientEntity(1L, "John", "Doe", "john.doe@example.com", "password", null, Role.PATIENT, null);

        when(patientMapper.toEntity(patient)).thenReturn(mockEntity);
        doNothing().when(patientRepository).persist(any(PatientEntity.class));
        when(patientMapper.toDomain(mockEntity)).thenReturn(patient);

        Patient createdPatient = patientService.create(patient);

        assertNotNull(createdPatient);
        assertEquals("John", createdPatient.getFirstName());

        verify(patientRepository, times(1)).persist(any(PatientEntity.class));
    }

    @Test
    @DisplayName("Test update() method")
    @Transactional
    public void testUpdatePatient() {
        Patient patient = new Patient(1L, "John", "Smith", "john.smith@example.com", "newpassword", null, Role.PATIENT);
        PatientEntity existingEntity = new PatientEntity(1L, "John", "Doe", "john.doe@example.com", "password", null, Role.PATIENT, null);

        when(patientRepository.findById(1L)).thenReturn(existingEntity);
        PatientEntity updatedEntity = new PatientEntity(1L, "John", "Smith", "john.smith@example.com", "newpassword", null, Role.PATIENT, null);
        when(patientMapper.toEntity(patient)).thenReturn(updatedEntity);
        when(entityManager.merge(any(PatientEntity.class))).thenReturn(updatedEntity);
        when(patientMapper.toDomain(updatedEntity)).thenReturn(patient);

        Patient updatedPatient = patientService.update(1L, patient);

        assertNotNull(updatedPatient);
        assertEquals("John", updatedPatient.getFirstName());
        assertEquals("Smith", updatedPatient.getLastName());

        verify(patientRepository, times(1)).findById(1L);
        verify(entityManager, times(1)).merge(any(PatientEntity.class));
    }

    @Test
    @DisplayName("Test delete() method")
    @Transactional
    public void testDeletePatient() {
        PatientEntity existingEntity = new PatientEntity(1L, "John", "Doe", "john.doe@example.com", "password", null, Role.PATIENT, null);
        when(patientRepository.findById(1L)).thenReturn(existingEntity);

        boolean deleted = patientService.delete(1L);

        assertTrue(deleted);

        verify(patientRepository, times(1)).findById(1L);
        verify(patientRepository, times(1)).delete(any(PatientEntity.class));
    }

    @Test
    @DisplayName("Test RegisterAndGetToken() method")
    @Transactional
    public void testRegisterAndGetToken() {
        Patient patient = new Patient(1L, "John", "Doe", "john.doe@example.com", "password", null, Role.PATIENT);
        PatientEntity mockEntity = new PatientEntity(1L, "John", "Doe", "john.doe@example.com", "password", null, Role.PATIENT, null);
        String expectedToken = "mocked-token";

        when(patientMapper.toEntity(patient)).thenReturn(mockEntity);
        doNothing().when(patientRepository).persist(any(PatientEntity.class));
        when(tokenService.generatePatientToken(mockEntity)).thenReturn(expectedToken);

        String token = patientService.RegisterAndGetToken(patient);

        assertNotNull(token);
        assertEquals(expectedToken, token);

        verify(patientRepository, times(1)).persist(any(PatientEntity.class));
        verify(tokenService, times(1)).generatePatientToken(mockEntity);
    }
}
