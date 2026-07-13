// Premium Member implementation
public class PremiumMember extends GymMember {
    private final double premiumCharge = 50000;
    private String personalTrainer;
    private double paidAmount;
    private boolean isFullPayment;
    private double discountAmount;
    
    public PremiumMember(String id, String name, String location, String phone, String email, 
                    String gender, String dateOfBirth, String membershipStartDate, String personalTrainer) {
        super(id, name, location, phone, email, gender, dateOfBirth, membershipStartDate);
        this.personalTrainer = personalTrainer;
        this.paidAmount = 0;
        this.isFullPayment = false;
        this.discountAmount = 0;
    }
    
    // Getters
    public double getPremiumCharge() { return premiumCharge; }
    public String getPersonalTrainer() { return personalTrainer; }
    public double getPaidAmount() { return paidAmount; }
    public boolean getIsFullPayment() { return isFullPayment; }
    public double getDiscountAmount() { return discountAmount; }
    
    // Setters
    public void setPersonalTrainer(String personalTrainer) { this.personalTrainer = personalTrainer; }
    public void setPaidAmount(double paidAmount) { this.paidAmount = paidAmount; }
    public void setIsFullPayment(boolean isFullPayment) { this.isFullPayment = isFullPayment; }
    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }
    
    // Method to pay due amount
    public String payDueAmount(double amount) {
        if (amount <= 0) {
            return "Amount must be greater than 0";
        }
        
        double remaining = premiumCharge - paidAmount;
        
        if (remaining <= 0) {
            return "No remaining payment. Member has already paid in full.";
        }
        
        if (amount > remaining) {
            paidAmount = premiumCharge;
            isFullPayment = true;
            return "Payment of " + remaining + " received. No more payments required.";
        } else {
            paidAmount += amount;
            remaining = premiumCharge - paidAmount;
            
            if (remaining == 0) {
                isFullPayment = true;
                return "Payment of " + amount + " received. Member has paid in full.";
            } else {
                return "Payment of " + amount + " received. Remaining amount: " + remaining;
            }
        }
    }
    
    // Method to calculate discount
    public String calculateDiscount() {
        if (!isFullPayment) {
            return "No discount available. Full payment required.";
        }
        
        // Calculate discount based on loyalty points
        double loyaltyPoints = getLoyaltyPoints();
        
        if (loyaltyPoints < 100) {
            discountAmount = 0;
            return "Not enough loyalty points for discount.";
        } else if (loyaltyPoints < 200) {
            discountAmount = premiumCharge * 0.05; // 5% discount
            return "5% discount applied. Discount amount: " + discountAmount;
        } else if (loyaltyPoints < 300) {
            discountAmount = premiumCharge * 0.10; // 10% discount
            return "10% discount applied. Discount amount: " + discountAmount;
        } else {
            discountAmount = premiumCharge * 0.15; // 15% discount
            return "15% discount applied. Discount amount: " + discountAmount;
        }
    }
    
    // Method to revert premium member
    public void revertPremiumMember() {
        this.deactivateMembership();
    }
    }