package org.acme.services;

import org.acme.domain.Schedule;
import org.acme.mappers.ScheduleMapper;
import org.acme.models.entities.ScheduleEntity;
import org.acme.repositories.ScheduleRepository;
import org.acme.utils.Day;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("ScheduleService Tests")
public class ScheduleServiceTest {

    @InjectMocks
    ScheduleService scheduleService;

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    ScheduleMapper scheduleMapper;

    @Mock
    EntityManager entityManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getAll() method")
    public void testGetAllSchedules() {
        List<ScheduleEntity> mockEntities = new ArrayList<>();
        mockEntities.add(new ScheduleEntity(1L, Day.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)));
        when(scheduleRepository.listAll()).thenReturn(mockEntities);

        when(scheduleMapper.toDomain(any(ScheduleEntity.class))).thenAnswer(invocation -> {
            ScheduleEntity entity = invocation.getArgument(0);
            return new Schedule(entity.getId(), entity.getDay(), entity.getEntryTime(), entity.getDepartureTime());
        });

        List<Schedule> schedules = scheduleService.getAll();

        assertNotNull(schedules);
        assertEquals(1, schedules.size());
        assertEquals(Day.MONDAY, schedules.get(0).getDay());

        verify(scheduleRepository, times(1)).listAll();
    }

    @Test
    @DisplayName("Test getById() method")
    public void testGetScheduleById() {
        ScheduleEntity mockEntity = new ScheduleEntity(1L, Day.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));
        when(scheduleRepository.findById(1L)).thenReturn(mockEntity);

        when(scheduleMapper.toDomain(mockEntity)).thenReturn(new Schedule(1L, Day.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0)));

        Schedule schedule = scheduleService.getById(1L);

        assertNotNull(schedule);
        assertEquals(1L, schedule.getId());
        assertEquals(Day.MONDAY, schedule.getDay());

        verify(scheduleRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test create() method")
    @Transactional
    public void testCreateSchedule() {
        Schedule schedule = new Schedule(1L, Day.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));
        ScheduleEntity mockEntity = new ScheduleEntity(1L, Day.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));

        when(scheduleMapper.toEntity(schedule)).thenReturn(mockEntity);
        when(entityManager.merge(any(ScheduleEntity.class))).thenReturn(mockEntity);
        when(scheduleMapper.toDomain(mockEntity)).thenReturn(schedule);
        doNothing().when(scheduleRepository).persist(any(ScheduleEntity.class));

        Schedule createdSchedule = scheduleService.create(schedule);

        assertNotNull(createdSchedule);
        assertEquals(Day.MONDAY, createdSchedule.getDay());

        verify(scheduleRepository, times(1)).persist(any(ScheduleEntity.class));
    }

    @Test
    @DisplayName("Test update() method")
    @Transactional
    public void testUpdateSchedule() {
        Schedule schedule = new Schedule(1L, Day.TUESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0));
        ScheduleEntity existingEntity = new ScheduleEntity(1L, Day.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));

        when(scheduleRepository.findById(1L)).thenReturn(existingEntity);
        ScheduleEntity updatedEntity = new ScheduleEntity(1L, Day.TUESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0));
        when(scheduleMapper.toEntity(schedule)).thenReturn(updatedEntity);
        when(entityManager.merge(any(ScheduleEntity.class))).thenReturn(updatedEntity);
        when(scheduleMapper.toDomain(updatedEntity)).thenReturn(schedule);

        Schedule updatedSchedule = scheduleService.update(1L, schedule);

        assertNotNull(updatedSchedule);
        assertEquals(Day.TUESDAY, updatedSchedule.getDay());

        verify(scheduleRepository, times(1)).findById(1L);
        verify(entityManager, times(1)).merge(any(ScheduleEntity.class));
    }

    @Test
    @DisplayName("Test delete() method")
    @Transactional
    public void testDeleteSchedule() {
        ScheduleEntity existingEntity = new ScheduleEntity(1L, Day.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));
        when(scheduleRepository.findById(1L)).thenReturn(existingEntity);

        boolean deleted = scheduleService.delete(1L);

        assertTrue(deleted);

        verify(scheduleRepository, times(1)).findById(1L);
        verify(scheduleRepository, times(1)).delete(any(ScheduleEntity.class));
    }
}
