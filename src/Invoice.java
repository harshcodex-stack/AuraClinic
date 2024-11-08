public class Invoice {
    private static final double TAX_RATE = 0.025;
    private Patient patient;
    private Treatment treatment;
    private double finalAmount;

    public Invoice(Patient patient, Treatment treatment) {
        this.patient = patient;
        this.treatment = treatment;
        this.finalAmount = calculateTotal();
    }

    private double calculateTotal() {
        double total = treatment.getPrice() * (1 + TAX_RATE);
        return Math.round(total * 100.0) / 100.0;
    }

    public void generateInvoice() {
        System.out.println("Invoice for: " + patient.getName());
        System.out.println("Treatment: " + treatment.getName());
        System.out.println("Cost: LKR " + treatment.getPrice());
        System.out.println("Tax (2.5%): LKR " + (treatment.getPrice() * TAX_RATE));
        System.out.println("Total Amount: LKR " + finalAmount);
    }
}
