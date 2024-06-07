package org.acme.repositories;

import java.time.LocalTime;
import java.util.List;

import org.acme.models.entities.ScheduleEntity;
import org.acme.utils.Day;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ScheduleRepository implements PanacheRepository <ScheduleEntity>{
    
       public List<ScheduleEntity> findByDay(Day day) {
        return find("day", day).list();
    }

    public List<ScheduleEntity> findByEntryTime(LocalTime entryTime) {
        return find("entry_time", entryTime).list();
    }

    public List<ScheduleEntity> findByDepartureTime(LocalTime departureTime) {
        return find("departure_time", departureTime).list();
    }
}
