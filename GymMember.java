public abstract class GymMember {
    private String id;
    private String name;
    private String location;
    private String phone;
    private String email;
    private String gender;
    private String dateOfBirth;
    private String membershipStartDate;
    private boolean activeStatus;
    private int attendance;
    private double loyaltyPoints;

    public GymMember(String id, String name, String location, String phone, String email, 
                    String gender, String dateOfBirth, String membershipStartDate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.membershipStartDate = membershipStartDate;
        this.activeStatus = true;
        this.attendance = 0;
        this.loyaltyPoints = 0;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getGender() { return gender; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getMembershipStartDate() { return membershipStartDate; }
    public boolean getActiveStatus() { return activeStatus; }
    public int getAttendance() { return attendance; }
    public double getLoyaltyPoints() { return loyaltyPoints; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setGender(String gender) { this.gender = gender; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setMembershipStartDate(String membershipStartDate) { this.membershipStartDate = membershipStartDate; }
    
    // Common methods
    public void activateMembership() {
        this.activeStatus = true;
    }
    
    public void deactivateMembership() {
        this.activeStatus = false;
    }
    
    public void markAttendance() {
        this.attendance++;
        // Increase loyalty points by 10 for each attendance
        this.loyaltyPoints += 10;
    }
}



