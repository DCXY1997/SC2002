package src.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import src.Enum.PaymentStatus;

public class AppointmentOutcome implements Serializable {

    private static final long serialVersionUID = 1L;

    // Attributes
    private String outcomeId;
    private List<Medicine> prescribedMedicines;
    private List<Integer> medicineAmount;
    private List<Diagnosis> patientDiagnosis;
    private String doctorNotes;
    private LocalDateTime dateDiagnosed;
    private PaymentStatus paymentStatus;

    // Constructor
    public AppointmentOutcome(String outcomeId, List<Medicine> prescribedMedicines, List<Integer> medicineAmount, List<Diagnosis> patientDiagnosis,
            String doctorNotes, LocalDateTime dateDiagnosed) {
        this.outcomeId = outcomeId;
        this.prescribedMedicines = new ArrayList<>(prescribedMedicines); // Initialize with a copy
        this.medicineAmount = new ArrayList<>(medicineAmount);
        this.patientDiagnosis = new ArrayList<>(patientDiagnosis); // Initialize with a copy
        this.doctorNotes = doctorNotes;
        this.dateDiagnosed = dateDiagnosed;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    public AppointmentOutcome(String outcomeId, List<Medicine> prescribedMedicines, List<Integer> medicineAmount, List<Diagnosis> patientDiagnosis,
            String doctorNotes, LocalDateTime dateDiagnosed, PaymentStatus paymentStatus) {
        this.outcomeId = outcomeId;
        this.prescribedMedicines = new ArrayList<>(prescribedMedicines); // Initialize with a copy
        this.medicineAmount = new ArrayList<>(medicineAmount);
        this.patientDiagnosis = new ArrayList<>(patientDiagnosis); // Initialize with a copy
        this.doctorNotes = doctorNotes;
        this.dateDiagnosed = dateDiagnosed;
        this.paymentStatus = paymentStatus;
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

    public List<Integer> getMedicineAmount() {
        return medicineAmount;
    }

    public void setMedicineAmount(Medicine medicine, int amount) {
        if (medicine!=null && amount>0) {
            this.medicineAmount.add(amount);
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
