public class Appointment {
    private String id;
    private Patient patient;
    private Dermatologist dermatologist;
    private String date;
    private String time;

    public Appointment(String id, Patient patient, Dermatologist dermatologist, String date, String time) {
        this.id = id;
        this.patient = patient;
        this.dermatologist = dermatologist;
        this.date = date;
        this.time = time;
    }

    // Getters
    public String getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Dermatologist getDermatologist() {
        return dermatologist;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    // Setters
    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
