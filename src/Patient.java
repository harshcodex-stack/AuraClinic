public class Patient {
    private String id;
    private String name;
    private String nic;
    private String email;
    private String phone;

    public Patient(String name, String nic, String email, String phone) {
        this.id = nic;  // Set id to NIC
        this.name = name;
        this.nic = nic;
        this.email = email;
        this.phone = phone;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getNic() { return nic; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }


    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
}
