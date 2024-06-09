package org.acme.models.dto;

import org.acme.utils.Speciality;
import java.util.Set;

public class DoctorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private int dni;
    private Speciality speciality;
    private Set<AppointmentDTO> appointments;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	public Speciality getSpeciality() {
		return speciality;
	}
	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}
	public Set<AppointmentDTO> getAppointments() {
		return appointments;
	}
	public void setAppointments(Set<AppointmentDTO> appointments) {
		this.appointments = appointments;
	}
}
