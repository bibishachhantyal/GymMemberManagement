
// Regular Member implementation
public class RegularMember extends GymMember {
    private String plan;
    private double price;
    private String referralSource;
    private String removalReason;
    
    public RegularMember(String id, String name, String location, String phone, String email, 
                    String gender, String dateOfBirth, String membershipStartDate, String referralSource) {
        super(id, name, location, phone, email, gender, dateOfBirth, membershipStartDate);
        this.plan = "Basic";
        this.price = 6500;
        this.referralSource = referralSource;
        this.removalReason = "";
    }
    
    // Getters
    public String getPlan() { return plan; }
    public double getPrice() { return price; }
    public String getReferralSource() { return referralSource; }
    public String getRemovalReason() { return removalReason; }
    
    // Setters
    public void setPlan(String plan) { this.plan = plan; }
    public void setPrice(double price) { this.price = price; }
    public void setReferralSource(String referralSource) { this.referralSource = referralSource; }
    public void setRemovalReason(String removalReason) { this.removalReason = removalReason; }
    
    // Method to upgrade plan
    public String upgradePlan(String newPlan) {
        String oldPlan = this.plan;
        
        if (oldPlan.equals(newPlan)) {
            return "Member is already on " + newPlan + " plan.";
        }
        
        this.plan = newPlan;
        
        switch (newPlan) {
            case "Basic":
                this.price = 6500;
                break;
            case "Standard":
                this.price = 8500;
                break;
            case "Deluxe":
                this.price = 10000;
                break;
            default:
                this.price = 6500;
                break;
        }
        
        return "Plan upgraded from " + oldPlan + " to " + newPlan + " successfully. New price: " + this.price;
    }
    
    // Method to revert regular member
    public void revertRegularMember(String reason) {
        this.removalReason = reason;
        this.deactivateMembership();
    }
}