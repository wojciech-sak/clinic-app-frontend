package com.kodilla.clinic.backend.service;

import ch.qos.logback.core.Appender;
import com.kodilla.clinic.backend.enums.*;
import com.kodilla.clinic.backend.outerapi.client.ClinicClient;
import com.kodilla.clinic.backend.outerapi.dtos.AppointmentDto;
import com.kodilla.clinic.backend.outerapi.dtos.DoctorDto;
import com.kodilla.clinic.backend.outerapi.dtos.PatientDto;
import com.kodilla.clinic.backend.outerapi.dtos.StaffEvaluationDto;
import com.kodilla.clinic.backend.outerapi.dtos.medic.RecommendationDto;
import com.kodilla.clinic.backend.outerapi.dtos.medic.SymptomDto;
import com.kodilla.clinic.backend.outerapi.dtos.schedule.ClinicDoctorScheduleDto;
import com.kodilla.clinic.backend.outerapi.dtos.schedule.EmergencyHourDto;
import com.kodilla.clinic.backend.outerapi.dtos.schedule.WorkingDayDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClinicService {
    @Autowired
    private ClinicClient clinicClient;

    //WORKING DAYS
    public List<WorkingDayDto> getWorkingDays() {
        if (clinicClient.getWorkingDays() != null) {
            return clinicClient.getWorkingDays();
        } else
            return new ArrayList<>();
    }

    public List<WorkingDayDto> getWorkingDays_ByWeekday(Day day) {
        if (clinicClient.getWorkingDays() != null) {
            return clinicClient.getWorkingDays().stream()
                    .filter(workingDayDto -> workingDayDto.getDay().equals(day))
                    .collect(Collectors.toList());
        } else
            return new ArrayList<>();
    }

    public void deleteWorkingDay(Integer id) {
        clinicClient.deleteWorkingDayById(id);
    }

    public void saveWorkingDay(WorkingDayDto workingDayDto) {
        clinicClient.saveWorkingDay(workingDayDto);
    }

    //SCHEDULES
    public List<ClinicDoctorScheduleDto> getSchedules() {
        if (clinicClient.getSchedules() != null) {
            return clinicClient.getSchedules();
        } else
            return new ArrayList<>();
    }

    //EMERGENCY HOURS
    public void saveEmergencyHour(EmergencyHourDto emergencyHourDto) {
        clinicClient.saveEmergencyHour(emergencyHourDto);
    }

    public void deleteEmergencyHour(Integer emergencyHour_id) {
        clinicClient.deleteEmergencyHourById(emergencyHour_id);
    }

    public List<EmergencyHourDto> getEmergencyHours() {
        if (clinicClient.getEmergencyHours() != null) {
            return clinicClient.getEmergencyHours();
        } else
            return new ArrayList<>();
    }

    public List<EmergencyHourDto> getEmergencyHours_ByWeekday(Day day) {
        if (clinicClient.getEmergencyHours() != null) {
            return clinicClient.getEmergencyHours().stream()
                    .filter(emergencyHourDto -> emergencyHourDto.getDay().equals(day))
                    .collect(Collectors.toList());
        } else
            return new ArrayList<>();
    }

    //DOCTORS
    public void saveDoctor(DoctorDto doctorDto) {
        clinicClient.saveDoctor(doctorDto);
    }

    public void deleteDoctor(Integer doctor_Id) {
        clinicClient.deleteDoctorById(doctor_Id);
    }

    public List<DoctorDto> getDoctors() {
        if (clinicClient.getDoctors() != null) {
            return clinicClient.getDoctors();
        } else
            return new ArrayList<>();
    }

    public List<DoctorDto> getDoctors_ByDepartment(Department department) {
        if (clinicClient.getDoctors() != null) {
            return clinicClient.getDoctors().stream()
                    .filter(doctorDto -> doctorDto.getDepartment().equals(department))
                    .collect(Collectors.toList());
        } else
            return new ArrayList<>();
    }

    public List<DoctorDto> getDoctors_BySpecialization(Specialization specialization) {
        if (clinicClient.getDoctors() != null) {
            return clinicClient.getDoctors().stream()
                    .filter(doctorDto -> doctorDto.getSpecialization().equals(specialization))
                    .collect(Collectors.toList());
        } else
            return new ArrayList<>();
    }

    public DoctorDto getDoctorById(Integer doctor_id) {
        if (clinicClient.getDoctors() != null) {
            return clinicClient.getDoctors().stream()
                    .filter(doctorDto -> doctorDto.getDoctor_id().equals(doctor_id))
                    .findFirst()
                    .get();
        } else
            return null;
    }

    //PATIENTS
    public void savePatient(PatientDto patientDto) {
        clinicClient.savePatient(patientDto);
    }

    public void deletePatient(Integer patient_id) {
        clinicClient.deletePatientById(patient_id);
    }

    public List<PatientDto> getPatients() {
        if (clinicClient.getPatients() != null) {
            return clinicClient.getPatients();
        } else
            return new ArrayList<>();
    }

    public List<PatientDto> getPatients_BySurname(String surnameFragment) {
        if (clinicClient.getPatients() != null) {
            return clinicClient.getPatients().stream()
                    .filter(patientDto -> patientDto.getSurname().contains(surnameFragment))
                    .collect(Collectors.toList());
        } else
            return new ArrayList<>();
    }

    public PatientDto getPatient_ByPesel(BigDecimal pesel) {
        if (clinicClient.getPatients() != null) {
            return clinicClient.getPatients().stream()
                    .filter(patientDto -> patientDto.getPesel().equals(pesel))
                    .findFirst()
                    .get();
        } else
            return null;
    }

    public PatientDto getPatientById(Integer patient_id) {
        if (clinicClient.getPatients() != null) {
            return clinicClient.getPatients().stream()
                    .filter(patientDto -> patientDto.getPatient_id().equals(patient_id))
                    .findFirst()
                    .get();
        } else
            return null;
    }

    //ApiMedic

    public List<SymptomDto> getSymptoms() {
        return clinicClient.getSymptoms();
    }

    public String getRecommendations(Integer birthYear, Gender gender, int[] symptomsIdsInts) {
        List<RecommendationDto> recommendations =
                clinicClient.getRecommendations(birthYear, gender, symptomsIdsInts);
        if (recommendations.size() > 0) {
            if (recommendations.size() >= 2) {
                return recommendations.get(0) + ", " + recommendations.get(1);
            } else {
                return recommendations.get(0).toString();
            }
        } else return "No recommendations available for these symptoms.";
    }

    //STAFF EVALUATIONS
    public List<StaffEvaluationDto> getStaffEvaluations() {
        return clinicClient.getStaffEvaluations();
    }

    public List<StaffEvaluationDto> getEvaluations_ByStarsRating(Stars value) {
        if (clinicClient.getStaffEvaluations() != null) {
            return clinicClient.getStaffEvaluations().stream()
                    .filter(staffEvaluationDto -> staffEvaluationDto.getStars().equals(value))
                    .collect(Collectors.toList());
        } else
            return new ArrayList<>();
    }

    public List<StaffEvaluationDto> getEvaluations_ByPatientId(Integer patientId) {
        if (clinicClient.getStaffEvaluations() != null) {
            return clinicClient.getStaffEvaluations().stream()
                    .filter(staffEvaluationDto -> staffEvaluationDto.getPatient_Id().equals(patientId))
                    .collect(Collectors.toList());
        } else
            return new ArrayList<>();
    }

    public void saveStaffEvaluation(StaffEvaluationDto staffEvaluationDto) {
        clinicClient.saveStaffEvaluation(staffEvaluationDto);
    }

    public void deleteStaffEvaluation(Integer evaluation_id) {
        clinicClient.deleteStaffEvaluationById(evaluation_id);
    }

    //APPOINTMENTS
    public List<AppointmentDto> getAppointments() {
        return clinicClient.getAppointments();
    }

    public void saveAppointment(AppointmentDto appointmentDto) {
        clinicClient.saveAppointment(appointmentDto);
    }

    public void deleteAppointment(Integer appointment_id) {
        clinicClient.deleteAppointmentById(appointment_id);
    }
}
