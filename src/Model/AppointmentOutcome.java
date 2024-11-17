package src.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import src.Enum.PaymentStatus;
import src.Enum.ServiceType;

/**
 * The {@code AppointmentOutcome} class represents the outcome of a medical
 * appointment, including prescribed medicines, diagnoses, services provided,
 * and payment status.
 *
 * <p>
 * This class provides functionality to manage the details of an appointment
 * outcome, including adding and removing prescribed medicines, diagnoses, and
 * services, as well as managing payment and diagnostic information.</p>
 *
 * <p>
 * <b>Features:</b></p>
 * <ul>
 * <li>Tracks prescribed medicines, their amounts, and statuses.</li>
 * <li>Tracks patient diagnoses associated with the appointment.</li>
 * <li>Tracks services provided during the appointment.</li>
 * <li>Supports payment status management.</li>
 * </ul>
 *
 * @author Cheah Wei Jun
 * @version 1.0
 * @since 2024-11-17
 */
public class AppointmentOutcome implements Serializable {

    /**
     * For Java Serializable, used to ensure compatibility during
     * deserialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier for the appointment outcome.
     */
    private String outcomeId;

    /**
     * List of prescribed medicines for the appointment.
     */
    private List<Medicine> prescribedMedicines;

    /**
     * List of amounts for each prescribed medicine.
     */
    private List<Integer> medicineAmount;

    /**
     * List of diagnoses associated with the patient.
     */
    private List<Diagnosis> patientDiagnosis;

    /**
     * List of services provided during the appointment.
     */
    private List<ServiceType> services;

    /**
     * Notes provided by the doctor regarding the appointment outcome.
     */
    private String doctorNotes;

    /**
     * Date and time when the diagnosis was made.
     */
    private LocalDateTime dateDiagnosed;

    /**
     * Payment status for the appointment outcome.
     */
    private PaymentStatus paymentStatus;

    /**
     * Constructs an {@code AppointmentOutcome} with default payment status set
     * to {@code PENDING}.
     *
     * @param outcomeId Unique identifier for the outcome.
     * @param prescribedMedicines List of prescribed medicines.
     * @param medicineAmount List of amounts for the prescribed medicines.
     * @param patientDiagnosis List of diagnoses for the patient.
     * @param services List of services provided during the appointment.
     * @param doctorNotes Notes from the doctor.
     * @param dateDiagnosed Date and time of the diagnosis.
     */
    public AppointmentOutcome(String outcomeId, List<Medicine> prescribedMedicines, List<Integer> medicineAmount,
            List<Diagnosis> patientDiagnosis, List<ServiceType> services, String doctorNotes,
            LocalDateTime dateDiagnosed) {
        this.outcomeId = outcomeId;
        this.prescribedMedicines = new ArrayList<>(prescribedMedicines);
        this.medicineAmount = new ArrayList<>(medicineAmount);
        this.patientDiagnosis = new ArrayList<>(patientDiagnosis);
        this.services = new ArrayList<>(services);
        this.doctorNotes = doctorNotes;
        this.dateDiagnosed = dateDiagnosed;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    /**
     * Constructs an {@code AppointmentOutcome} with a specified payment status.
     *
     * @param outcomeId Unique identifier for the outcome.
     * @param prescribedMedicines List of prescribed medicines.
     * @param medicineAmount List of amounts for the prescribed medicines.
     * @param patientDiagnosis List of diagnoses for the patient.
     * @param services List of services provided during the appointment.
     * @param doctorNotes Notes from the doctor.
     * @param dateDiagnosed Date and time of the diagnosis.
     * @param paymentStatus Payment status for the appointment.
     */
    public AppointmentOutcome(String outcomeId, List<Medicine> prescribedMedicines, List<Integer> medicineAmount,
            List<Diagnosis> patientDiagnosis, List<ServiceType> services, String doctorNotes,
            LocalDateTime dateDiagnosed, PaymentStatus paymentStatus) {
        this(outcomeId, prescribedMedicines, medicineAmount, patientDiagnosis, services, doctorNotes, dateDiagnosed);
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters with Javadoc descriptions...
    /**
     * Retrieves the unique identifier for the outcome.
     *
     * @return The unique outcome ID.
     */
    public String getOutcomeId() {
        return outcomeId;
    }

    /**
     * Sets the unique identifier for the outcome.
     *
     * @param outcomeId The outcome ID to set.
     */
    public void setOutcomeId(String outcomeId) {
        this.outcomeId = outcomeId;
    }

    /**
     * Retrieves the list of prescribed medicines.
     *
     * @return A list of {@link Medicine} objects.
     */
    public List<Medicine> getPrescribedMedicines() {
        return prescribedMedicines;
    }

    /**
     * Adds a prescribed medicine to the appointment outcome.
     *
     * @param medicine The {@link Medicine} object to add. If null, it will not
     * be added.
     */
    public void addPrescribedMedicine(Medicine medicine) {
        if (medicine != null) {
            this.prescribedMedicines.add(medicine);
        }
    }

    /**
     * Retrieves the list of amounts for each prescribed medicine.
     *
     * @return A list of integers representing the prescribed quantities for
     * each medicine.
     */
    public List<Integer> getMedicineAmount() {
        return medicineAmount;
    }

    /**
     * Sets the quantity for a prescribed medicine.
     *
     * @param medicine The {@link Medicine} to associate with the specified
     * amount.
     * @param amount The quantity of the medicine prescribed. Must be greater
     * than 0.
     */
    public void setMedicineAmount(Medicine medicine, int amount) {
        if (medicine != null && amount > 0) {
            this.medicineAmount.add(amount);
        }
    }

    /**
     * Removes a prescribed medicine from the appointment outcome.
     *
     * @param medicine The {@link Medicine} object to remove. If the medicine is
     * not found, no action is taken.
     */
    public void removePrescribedMedicine(Medicine medicine) {
        this.prescribedMedicines.remove(medicine);
    }

    /**
     * Retrieves the list of patient diagnoses associated with the appointment
     * outcome.
     *
     * @return A list of {@link Diagnosis} objects representing the patient's
     * diagnoses.
     */
    public List<Diagnosis> getPatientDiagnosis() {
        return patientDiagnosis;
    }

    /**
     * Adds a patient diagnosis to the appointment outcome.
     *
     * @param diagnosis The {@link Diagnosis} to add. If null, it will not be
     * added.
     */
    public void addPatientDiagnosis(Diagnosis diagnosis) {
        if (diagnosis != null) {
            this.patientDiagnosis.add(diagnosis);
        }
    }

    /**
     * Removes a patient diagnosis from the appointment outcome.
     *
     * @param diagnosis The {@link Diagnosis} to remove. If not found, no action
     * is taken.
     */
    public void removePatientDiagnosis(Diagnosis diagnosis) {
        this.patientDiagnosis.remove(diagnosis);
    }

    /**
     * Retrieves the doctor's notes associated with the appointment outcome.
     *
     * @return A string containing the doctor's notes.
     */
    public String getDoctorNotes() {
        return doctorNotes;
    }

    /**
     * Sets the doctor's notes for the appointment outcome.
     *
     * @param doctorNotes A string containing the notes to set.
     */
    public void setDoctorNotes(String doctorNotes) {
        this.doctorNotes = doctorNotes;
    }

    /**
     * Retrieves the date and time when the diagnosis was made.
     *
     * @return A {@link LocalDateTime} object representing the diagnosis date
     * and time.
     */
    public LocalDateTime getDateDiagnosed() {
        return dateDiagnosed;
    }

    /**
     * Sets the date and time when the diagnosis was made.
     *
     * @param dateDiagnosed A {@link LocalDateTime} object representing the
     * diagnosis date and time.
     */
    public void setDateDiagnosed(LocalDateTime dateDiagnosed) {
        this.dateDiagnosed = dateDiagnosed;
    }

    /**
     * Retrieves the payment status of the appointment outcome.
     *
     * @return The {@link PaymentStatus} of the appointment outcome.
     */
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Sets the payment status of the appointment outcome.
     *
     * @param paymentStatus The {@link PaymentStatus} to set.
     */
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * Retrieves the list of services provided during the appointment.
     *
     * @return A list of {@link ServiceType} objects representing the services.
     */
    public List<ServiceType> getServices() {
        return services;
    }

    /**
     * Adds a service to the list of services provided during the appointment.
     *
     * @param service The {@link ServiceType} to add.
     */
    public void addServices(ServiceType service) {
        this.services.add(service);
    }

    /**
     * Removes a service from the list of services provided during the
     * appointment.
     *
     * @param service The {@link ServiceType} to remove. If not found, no action
     * is taken.
     */
    public void removeServices(ServiceType service) {
        if (services.contains(service)) {
            this.services.remove(service);
        } else {
            System.out.println("Service is not in this appointment outcome.");
        }

    }

}
