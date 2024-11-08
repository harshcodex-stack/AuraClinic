import java.util.Scanner;
import java.util.List;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Clinic clinic = new Clinic();



    public static void main(String[] args) {
        initializeDermatologists();
        boolean exit = false;
        while (!exit) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> registerPatient();
                case 2 -> makeAppointment();
                case 3 -> updateAppointment();
                case 4 -> viewAppointmentsByDate();
                case 5 -> searchAppointment();
                case 6 -> generateInvoice();
                case 0 -> exit = true;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n--- Aurora Skin Care Management System ---");
        System.out.println("1. Register a Patient");
        System.out.println("2. Make an Appointment");
        System.out.println("3. Update Appointment Details");
        System.out.println("4. View Appointments by Date");
        System.out.println("5. Search for an Appointment");
        System.out.println("6. Generate Invoice");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }

    private static void initializeDermatologists() {
        clinic.addDermatologist(new Dermatologist("Dr. Silva"));
        clinic.addDermatologist(new Dermatologist("Dr. Fernando"));
    }

    private static void registerPatient() {
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter NIC: ");
        String nic = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        Patient patient = clinic.registerPatient(name, nic, email, phone);
        System.out.println("Patient registered with NIC as ID: " + patient.getId());
        System.out.println("Registration Fee 500");
    }

    private static void makeAppointment() {
        System.out.print("Enter patient NIC: ");
        String patientNic = scanner.nextLine();
        Patient patient = clinic.getPatient(patientNic);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        List<Dermatologist> dermatologists = clinic.getDermatologists();
        System.out.println("Available dermatologists:");
        for (int i = 0; i < dermatologists.size(); i++) {
            System.out.println((i + 1) + ". " + dermatologists.get(i).getName());
        }

        System.out.print("Select a dermatologist (enter number): ");
        int dermatologistIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (dermatologistIndex < 0 || dermatologistIndex >= dermatologists.size()) {
            System.out.println("Invalid dermatologist selection.");
            return;
        }

        Dermatologist selectedDermatologist = dermatologists.get(dermatologistIndex);

        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter appointment time (e.g., 10:00 AM): ");
        String time = scanner.nextLine();

        try {
            Appointment appointment = clinic.makeAppointment(patient, selectedDermatologist, date, time);
            System.out.println("Appointment booked with ID: " + appointment.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateAppointment() {
        System.out.print("Enter appointment ID to update: ");
        String appointmentId = scanner.nextLine();
        Appointment appointment = clinic.getAppointment(appointmentId);
        if (appointment == null) {
            System.out.println("Appointment not found.");
            return;
        }

        System.out.print("Enter new date (YYYY-MM-DD): ");
        String newDate = scanner.nextLine();
        System.out.print("Enter new time (e.g., 10:00 AM): ");
        String newTime = scanner.nextLine();

        try {
            clinic.updateAppointment(appointmentId, newDate, newTime);
            System.out.println("Appointment updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewAppointmentsByDate() {
        System.out.print("Enter date to view appointments (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        List<Appointment> appointments = clinic.getAppointmentsByDate(date);
        if (appointments.isEmpty()) {
            System.out.println("No appointments found for this date.");
        } else {
            System.out.println("Appointments for " + date + ":");
            for (Appointment appointment : appointments) {
                System.out.println("ID: " + appointment.getId() + ", Patient: " + appointment.getPatient().getName()
                        + ",Dermatologist: " + appointment.getDermatologist().getName() + ",Time:" +
                        appointment.getTime());
            }
        }
    }

    private static void searchAppointment() {
        System.out.print("Enter patient name or appointment ID: ");
        String query = scanner.nextLine();

        List<Appointment> results = clinic.searchAppointments(query);
        if (results.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            System.out.println("Search Results:");
            for (Appointment appointment : results) {
                System.out.println("ID: " + appointment.getId() + ", Patient: " + appointment.getPatient().getName()
                        + ",Dermatologist: " + appointment.getDermatologist().getName() +
                        ", Date: " + appointment.getDate() + ", Time: " + appointment.getTime());
            }
        }
    }

    private static void generateInvoice() {
        System.out.print("Enter patient NIC for invoice generation: ");
        String patientNic = scanner.nextLine();
        Patient patient = clinic.getPatient(patientNic);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("Available treatments:");
        System.out.println("1. Acne Treatment - LKR 2750.00");
        System.out.println("2. Skin Whitening - LKR 7650.00");
        System.out.println("3. Mole Removal - LKR 3850.00");
        System.out.println("4. Laser Treatment - LKR 12500.00");
        System.out.print("Select treatment (enter number): ");
        int treatmentChoice = scanner.nextInt();
        scanner.nextLine();

        Treatment treatment;
        switch (treatmentChoice) {
            case 1 -> treatment = new Treatment("Acne Treatment", 2750.00);
            case 2 -> treatment = new Treatment("Skin Whitening", 7650.00);
            case 3 -> treatment = new Treatment("Mole Removal", 3850.00);
            case 4 -> treatment = new Treatment("Laser Treatment", 12500.00);
            default -> {
                System.out.println("Invalid treatment selection.");
                return;
            }
        }

        clinic.calculateAndPrintInvoice(patient, treatment);
    }
}
