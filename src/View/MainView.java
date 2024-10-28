package src.View;

public abstract class MainView {
	/**
	 * Abstract method for View Actions
	 */
	protected abstract void printActions();
	/**
	 * Abstract method for View Applications
	 */
	public abstract void viewApp();
	/**
	 * Default constructor for MainView
	 */
	public MainView() {
		
	}
	/**
	 * Method to print breadcrumbs for navigation purposes
	 * @param breadcrumb breadcrumbs description
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
