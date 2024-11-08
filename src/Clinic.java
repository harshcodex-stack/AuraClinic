import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Clinic {
    private List<Dermatologist> dermatologists = new ArrayList<>();
    private Map<String, Patient> patients = new HashMap<>();
    private Map<String, Appointment> appointments = new HashMap<>();
    private int appointmentIdCounter = 1;

    // Add a dermatologist
    public void addDermatologist(Dermatologist dermatologist) {
        dermatologists.add(dermatologist);
    }

    // Register
    public Patient registerPatient(String name, String nic, String email, String phone) {
        if (patients.containsKey(nic)) {
            System.out.println("Patient with this NIC is already registered.");
            return null;
        }

        Patient patient = new Patient(name, nic, email, phone); // Use NIC as ID
        patients.put(nic, patient);
        return patient;
    }

    // Get a list of dermatologists
    public List<Dermatologist> getDermatologists() {
        return dermatologists;
    }

    // Get a patient by ID (which is now the NIC)
    public Patient getPatient(String patientId) {
        return patients.get(patientId);
    }

    // appointment schedule
    public Appointment makeAppointment(Patient patient, Dermatologist dermatologist,
                                       String date, String time) {
        String appointmentId = "A" + appointmentIdCounter++;
        Appointment appointment = new Appointment(appointmentId, patient, dermatologist, date, time);

        // Check appointment conflict
        for (Appointment existing : appointments.values()) {
            if (existing.getDermatologist().equals(dermatologist) && existing.getDate().equals(date)
                    && existing.getTime().equals(time)) {
                throw new IllegalArgumentException("The selected time slot is already booked for this dermatologist.");
            }
        }

        appointments.put(appointmentId, appointment);
        return appointment;
    }

    // Get an appointment by ID
    public Appointment getAppointment(String appointmentId) {
        return appointments.get(appointmentId);
    }

    // Update appointment
    public void updateAppointment(String appointmentId, String newDate, String newTime) {
        Appointment appointment = appointments.get(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment not found.");
        }

        // Check for time conflicts before updating
        for (Appointment existing : appointments.values()) {
            if (existing.getDermatologist().equals(appointment.getDermatologist()) &&
                    existing.getDate().equals(newDate) && existing.getTime().equals(newTime)) {
                throw new IllegalArgumentException("The selected time slot is already booked for this dermatologist.");
            }
        }

        appointment.setDate(newDate);
        appointment.setTime(newTime);
    }

    //view appointment by date
    public List<Appointment> getAppointmentsByDate(String date) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments.values()) {
            if (appointment.getDate().equals(date)) {
                result.add(appointment);
            }
        }
        return result;
    }

    // Search by patient name or appointment ID
    public List<Appointment> searchAppointments(String query) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments.values()) {
            if (appointment.getId().equalsIgnoreCase(query) || appointment.getPatient().getName().equalsIgnoreCase(query)) {
                result.add(appointment);
            }
        }
        return result;
    }

    //print invoice with tax
    public void calculateAndPrintInvoice(Patient patient, List<Treatment> treatments) {
        double taxRate = 0.025;
        double subtotal = 0.0;

        System.out.printf("Invoice for %s\n", patient.getName());
        System.out.println("Selected Treatments:");
        for (Treatment treatment : treatments) {
            System.out.printf(" - %s: LKR %.2f\n", treatment.getName(), treatment.getPrice());
            subtotal += treatment.getPrice();
        }

        double tax = subtotal * taxRate;
        double total = subtotal + tax;

        System.out.printf("Subtotal: LKR %.2f\n", subtotal);
        System.out.printf("Tax (2.5%%): LKR %.2f\n", tax);
        System.out.printf("Total: LKR %.2f\n", Math.ceil(total * 100) / 100);
    }

}
