package org.acme.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Appointment {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Patient patient;

    private LocalDateTime dateHour;

    @ManyToOne
    private Doctor doctor;
    private String queryReason;

    // Getters y Setters
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public LocalDateTime getDateHour() {
		return dateHour;
	}
	public void setDateHour(LocalDateTime dateHour) {
		this.dateHour = dateHour;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public String getQueryReason() {
		return queryReason;
	}
	public void setQueryReason(String queryReason) {
		this.queryReason = queryReason;
	}
}
