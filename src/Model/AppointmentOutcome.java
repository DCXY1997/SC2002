package src.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import src.Enum.PaymentStatus;

public class AppointmentOutcome implements Serializable{

    // Attributes
    private String outcomeId;
    private List<Medicine> prescribedMedicines;
    private List<Diagnosis> patientDiagnosis;
    private String doctorNotes;
    private LocalDateTime dateDiagnosed;
    private PaymentStatus paymentStatus;

    // Constructor
    public AppointmentOutcome(String outcomeId, List<Medicine> prescribedMedicines, List<Diagnosis> patientDiagnosis,
            String doctorNotes, LocalDateTime dateDiagnosed, PaymentStatus paymentStatus) {
        this.outcomeId = outcomeId;
        this.prescribedMedicines = new ArrayList<>(prescribedMedicines); // Initialize with a copy
        this.patientDiagnosis = new ArrayList<>(patientDiagnosis); // Initialize with a copy
        this.doctorNotes = doctorNotes;
        this.dateDiagnosed = dateDiagnosed;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    // Getters and Setters
    public String getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(String outcomeId) {
        this.outcomeId = outcomeId;
    }

    public List<Medicine> getPrescribedMedicines() {
        return prescribedMedicines;
    }

    public void addPrescribedMedicine(Medicine medicine) {
        if (medicine != null) {
            this.prescribedMedicines.add(medicine);
        }
    }

    public void removePrescribedMedicine(Medicine medicine) {
        this.prescribedMedicines.remove(medicine);
    }

    public List<Diagnosis> getPatientDiagnosis() {
        return patientDiagnosis;
    }

    public void addPatientDiagnosis(Diagnosis diagnosis) {
        if (diagnosis != null) {
            this.patientDiagnosis.add(diagnosis);
        }
    }

    public void removePatientDiagnosis(Diagnosis diagnosis) {
        this.patientDiagnosis.remove(diagnosis);
    }

    public String getDoctorNotes() {
        return doctorNotes;
    }

    public void setDoctorNotes(String doctorNotes) {
        this.doctorNotes = doctorNotes;
    }

    public LocalDateTime getDateDiagnosed() {
        return dateDiagnosed;
    }

    public void setDateDiagnosed(LocalDateTime dateDiagnosed) {
        this.dateDiagnosed = dateDiagnosed;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
