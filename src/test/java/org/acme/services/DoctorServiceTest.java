package org.acme.services;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.acme.domain.DoctorRequestDTO;
import org.acme.domain.DoctorResponseDTO;
import org.acme.mappers.DoctorRequestMapper;
import org.acme.mappers.DoctorResponseMapper;
import org.acme.models.entities.BranchEntity;
import org.acme.models.entities.DoctorEntity;
import org.acme.models.entities.ScheduleEntity;
import org.acme.repositories.BranchRepository;
import org.acme.repositories.DoctorRepository;
import org.acme.repositories.ScheduleRepository;
import org.acme.utils.Day;
import org.acme.utils.Speciality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@QuarkusTest
public class DoctorServiceTest {

    @InjectMocks
    DoctorService doctorService;

    @Mock
    DoctorRepository doctorRepository;

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    BranchRepository branchRepository;

    @Mock
    DoctorRequestMapper requestMapper;

    @Mock
    DoctorResponseMapper responseMapper;

    @Mock
    EntityManager entityManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getAll() method")
    public void testGetAllDoctors() {
        List<DoctorEntity> mockEntities = new ArrayList<>();
        mockEntities.add(new DoctorEntity(1L, "John", "Doe", 12345678, Speciality.TRAUMATOLOGIA, new HashSet<>(), null, new HashSet<>()));
        when(doctorRepository.listAll()).thenReturn(mockEntities);

        when(responseMapper.toDomain(any(DoctorEntity.class))).thenAnswer(invocation -> {
            DoctorEntity entity = invocation.getArgument(0);
            return new DoctorResponseDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getSpeciality(), entity.getSchedules(), entity.getBranch());
        });

        List<DoctorResponseDTO> doctors = doctorService.getAll();

        assertNotNull(doctors);
        assertEquals(1, doctors.size());
        assertEquals("John", doctors.get(0).getFirstName());

        verify(doctorRepository, times(1)).listAll();
    }

    @Test
    @DisplayName("Test getById() method")
    public void testGetDoctorById() {
        DoctorEntity mockEntity = new DoctorEntity(1L, "John", "Doe", 12345678, Speciality.TRAUMATOLOGIA, new HashSet<>(), null, new HashSet<>());
        when(doctorRepository.findById(1L)).thenReturn(mockEntity);

        when(responseMapper.toDomain(mockEntity)).thenReturn(new DoctorResponseDTO(1L, "John", "Doe", Speciality.TRAUMATOLOGIA, mockEntity.getSchedules(), mockEntity.getBranch()));

        DoctorResponseDTO doctor = doctorService.getById(1L);

        assertNotNull(doctor);
        assertEquals(1L, doctor.getId());
        assertEquals("John", doctor.getFirstName());

        verify(doctorRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test create() method")
    @Transactional
    public void testCreateDoctor() {
        DoctorRequestDTO doctorDTO = new DoctorRequestDTO(null, null, 0, null, null);
        doctorDTO.setFirstName("John");
        doctorDTO.setLastName("Doe");
        doctorDTO.setDni(12345678);
        doctorDTO.setSpeciality(Speciality.TRAUMATOLOGIA);
        doctorDTO.setBranch_id(1L);

        BranchEntity branchEntity = new BranchEntity(1L, "Branch 1", "Address 1", "City 1");
        when(branchRepository.findById(1L)).thenReturn(branchEntity);

        DoctorEntity mockEntity = new DoctorEntity(1L, "John", "Doe", 12345678, Speciality.TRAUMATOLOGIA, new HashSet<>(), branchEntity, new HashSet<>());
        when(entityManager.merge(any(DoctorEntity.class))).thenReturn(mockEntity);

        doNothing().when(doctorRepository).persist(any(DoctorEntity.class));

        DoctorEntity createdDoctor = doctorService.create(doctorDTO);

        assertNotNull(createdDoctor);
        assertEquals("John", createdDoctor.getFirstName());

        verify(doctorRepository, times(1)).persist(any(DoctorEntity.class));
    }

    @Test
    @DisplayName("Test update() method")
    @Transactional
    public void testUpdateDoctor() {
        DoctorRequestDTO doctorDTO = new DoctorRequestDTO("John", "Smith", 87654321, Speciality.ENDOCRINOLOGIA, 1L);
        BranchEntity branchEntity = new BranchEntity(1L, "Branch 1", "Address 1", "City 1");
        when(branchRepository.findById(1L)).thenReturn(branchEntity);

        DoctorEntity existingEntity = new DoctorEntity(1L, "John", "Doe", 12345678, Speciality.TRAUMATOLOGIA, new HashSet<>(), branchEntity, new HashSet<>());
        when(doctorRepository.findById(1L)).thenReturn(existingEntity);

        DoctorEntity updatedEntity = new DoctorEntity(1L, "John", "Smith", 87654321, Speciality.ENDOCRINOLOGIA, new HashSet<>(), branchEntity, new HashSet<>());
        when(requestMapper.toEntity(doctorDTO)).thenReturn(updatedEntity);
        when(entityManager.merge(any(DoctorEntity.class))).thenReturn(updatedEntity);

        DoctorEntity updatedDoctor = doctorService.update(1L, doctorDTO);

        assertNotNull(updatedDoctor);
        assertEquals("John", updatedDoctor.getFirstName());
        assertEquals("Smith", updatedDoctor.getLastName());

        verify(doctorRepository, times(1)).findById(1L);
        verify(entityManager, times(1)).merge(any(DoctorEntity.class));
    }

    @Test
    @DisplayName("Test delete() method")
    @Transactional
    public void testDeleteDoctor() {
        DoctorEntity existingEntity = new DoctorEntity(1L, "John", "Doe", 12345678, Speciality.TRAUMATOLOGIA, new HashSet<>(), null, new HashSet<>());
        when(doctorRepository.findById(1L)).thenReturn(existingEntity);

        boolean deleted = doctorService.delete(1L);

        assertTrue(deleted);

        verify(doctorRepository, times(1)).findById(1L);
        verify(doctorRepository, times(1)).delete(any(DoctorEntity.class));
    }

    @Test
    @DisplayName("Test assignScheduleToDoctor() method")
    @Transactional
    public void testAssignScheduleToDoctor() {
        DoctorEntity doctorEntity = new DoctorEntity(1L, "John", "Doe", 12345678, Speciality.TRAUMATOLOGIA, new HashSet<>(), null, new HashSet<>());
        ScheduleEntity scheduleEntity = new ScheduleEntity(1L, Day.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));

        when(doctorRepository.findById(1L)).thenReturn(doctorEntity);
        when(scheduleRepository.findById(1L)).thenReturn(scheduleEntity);

        Set<Day> existingScheduleDays = new HashSet<>();
        when(doctorRepository.findScheduleDaysByDoctorId(1L)).thenReturn(existingScheduleDays);

        doctorService.assignScheduleToDoctor(1L, 1L);

        assertTrue(doctorEntity.getSchedules().contains(scheduleEntity));

        verify(doctorRepository, times(1)).findById(1L);
        verify(scheduleRepository, times(1)).findById(1L);
        verify(doctorRepository, times(1)).findScheduleDaysByDoctorId(1L);
    }

    @Test
    @DisplayName("Test assignScheduleToDoctor() method - Doctor not found")
    @Transactional
    public void testAssignScheduleToDoctorDoctorNotFound() {
        when(doctorRepository.findById(1L)).thenReturn(null);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            doctorService.assignScheduleToDoctor(1L, 1L);
        });

        assertEquals("Doctor with id 1 not found", exception.getMessage());

        verify(doctorRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test assignScheduleToDoctor() method - Schedule not found")
    @Transactional
    public void testAssignScheduleToDoctorScheduleNotFound() {
        DoctorEntity doctorEntity = new DoctorEntity(1L, "John", "Doe", 12345678, Speciality.TRAUMATOLOGIA, new HashSet<>(), null, new HashSet<>());
        when(doctorRepository.findById(1L)).thenReturn(doctorEntity);
        when(scheduleRepository.findById(1L)).thenReturn(null);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            doctorService.assignScheduleToDoctor(1L, 1L);
        });

        assertEquals("Schedule with id 1 not found", exception.getMessage());

        verify(doctorRepository, times(1)).findById(1L);
        verify(scheduleRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test assignScheduleToDoctor() method - Duplicate Schedule Day")
    @Transactional
    public void testAssignScheduleToDoctorDuplicateScheduleDay() {
        DoctorEntity doctorEntity = new DoctorEntity(1L, "John", "Doe", 12345678, Speciality.TRAUMATOLOGIA, new HashSet<>(), null, new HashSet<>());
        ScheduleEntity scheduleEntity = new ScheduleEntity(1L, Day.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));

        when(doctorRepository.findById(1L)).thenReturn(doctorEntity);
        when(scheduleRepository.findById(1L)).thenReturn(scheduleEntity);

        Set<Day> existingScheduleDays = new HashSet<>();
        existingScheduleDays.add(Day.MONDAY);
        when(doctorRepository.findScheduleDaysByDoctorId(1L)).thenReturn(existingScheduleDays);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            doctorService.assignScheduleToDoctor(1L, 1L);
        });

        assertEquals("El doctor ya tiene un horario cargado ese mismo diaMONDAY", exception.getMessage());

        verify(doctorRepository, times(1)).findById(1L);
        verify(scheduleRepository, times(1)).findById(1L);
        verify(doctorRepository, times(1)).findScheduleDaysByDoctorId(1L);
    }
}
