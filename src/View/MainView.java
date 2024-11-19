package src.View;

/**
 * The MainView class is an abstract base class for all views in the hospital
 * management system.
 * <p>
 * This class defines the core structure and behavior shared by all views,
 * including:
 * </p>
 * <ul>
 * <li>Abstract methods for displaying actions and applications.</li>
 * <li>A method for displaying breadcrumbs for navigation purposes.</li>
 * </ul>
 *
 * <p>
 * <b>Key Features:</b></p>
 * <ul>
 * <li>Provides a consistent structure for views.</li>
 * <li>Includes a utility method for navigation breadcrumbs.</li>
 * </ul>
 *
 * @see HospitalManagementAppView
 * @see LoginView
 * @see AdminView
 * @see DisplayStaffView
 * @see InventoryView
 * @see DisplayMedicationInventory
 * @see DisplayAppointmentDetailView
 * @see DoctorView
 * @see PatientView
 * @see PharmacistView
 * @see ManageStaffAccountView
 * @see ManageMedicalInventory
 * @see ManageReplenishmentRequestView
 * @see Helper
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */
public abstract class MainView {

    /**
     * Abstract method to display actions in the view.
     * <p>
     * This method must be implemented by subclasses to define the specific
     * actions available in the view.
     * </p>
     */
    protected abstract void printActions();

    /**
     * Abstract method to manage the application workflow in the view.
     * <p>
     * This method must be implemented by subclasses to handle the application's
     * main logic for the view.
     * </p>
     */
    public abstract void viewApp();

    /**
     * Default constructor for MainView.
     * <p>
     * Initializes a new instance of MainView.
     * </p>
     */
    public MainView() {

    }

    /**
     * Prints navigation breadcrumbs for the current view.
     * <p>
     * This method helps users understand their location within the application
     * by displaying a breadcrumb trail at the top of the view.
     * </p>
     *
     * @param breadcrumb A string representing the breadcrumb trail for the
     * current view.
     */
    protected void printBreadCrumbs(String breadcrumb) {
        String spaces = String.format("%" + (105 - breadcrumb.length()) + "s", "");
        System.out.println(
                "╔══════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ " + breadcrumb + spaces + "║");
        System.out.println(
                "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }
}
