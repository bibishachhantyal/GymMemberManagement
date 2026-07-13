import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import javax.swing.SpinnerDateModel;

// Main GUI class for Gym Membership Management System
public class GymGUI {
    private JFrame frame;
    private JTextField idField, nameField, locationField, phoneField, emailField, referralField, amountField, 
            removalField, trainerField, regularPriceField, premiumChargeField, discountField;
    private JRadioButton maleButton, femaleButton;
    private JComboBox<String> planBox;
    private ArrayList<GymMember> members; // ArrayList of GymMember to store both Regular and Premium members
    private JTextArea displayArea;

    // Date pickers using JSpinner
    private JSpinner dobSpinner;
    private JSpinner startDateSpinner;

    public GymGUI() {
        members = new ArrayList<>();
        frame = new JFrame("Gym Membership Management");
        frame.setSize(900, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel for input form using GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        idField = new JTextField(); nameField = new JTextField(); locationField = new JTextField();
        phoneField = new JTextField(); emailField = new JTextField(); referralField = new JTextField();
        amountField = new JTextField("0"); removalField = new JTextField(); trainerField = new JTextField();
        regularPriceField = new JTextField("6500"); premiumChargeField = new JTextField("50000"); discountField = new JTextField("0");

        regularPriceField.setEditable(false); premiumChargeField.setEditable(false); discountField.setEditable(false);

        maleButton = new JRadioButton("Male"); femaleButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup(); genderGroup.add(maleButton); genderGroup.add(femaleButton);
        JPanel genderPanel = new JPanel(); genderPanel.add(maleButton); genderPanel.add(femaleButton);

        planBox = new JComboBox<>(new String[]{"Basic", "Standard", "Deluxe"});

        // Create DOB spinner with today's date and date format yyyy-MM-dd
        SpinnerDateModel dobModel = new SpinnerDateModel();
        dobSpinner = new JSpinner(dobModel);
        JSpinner.DateEditor dobEditor = new JSpinner.DateEditor(dobSpinner, "yyyy-MM-dd");
        dobSpinner.setEditor(dobEditor);
        JPanel dobPanel = new JPanel(); 
        dobPanel.add(dobSpinner);

        // Create Start Date spinner with today's date and date format yyyy-MM-dd
        SpinnerDateModel startDateModel = new SpinnerDateModel();
        startDateSpinner = new JSpinner(startDateModel);
        JSpinner.DateEditor startEditor = new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd");
        startDateSpinner.setEditor(startEditor);
        JPanel startPanel = new JPanel();
        startPanel.add(startDateSpinner);

        String[] labels = {"ID", "Name", "Location", "Phone", "Email", "Gender", "DOB", "Start Date", "Referral Source", 
                           "Paid Amount", "Removal Reason", "Trainer's Name", "Plan", "Regular Price", "Premium Charge", "Discount"};
        Component[] fields = {idField, nameField, locationField, phoneField, emailField, genderPanel, dobPanel, startPanel,
                referralField, amountField, removalField, trainerField, planBox, regularPriceField, premiumChargeField, discountField};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            formPanel.add(new JLabel(labels[i] + ":"), gbc);
            gbc.gridx = 1;
            formPanel.add(fields[i], gbc);
        }

        JPanel buttonPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        JButton addRegularBtn = new JButton("Add Regular Member");
        JButton addPremiumBtn = new JButton("Add Premium Member");
        JButton activateBtn = new JButton("Activate Membership");
        JButton deactivateBtn = new JButton("Deactivate Membership");
        JButton markAttendanceBtn = new JButton("Mark Attendance");
        JButton upgradePlanBtn = new JButton("Upgrade Plan");
        JButton calculateDiscountBtn = new JButton("Calculate Discount");
        JButton revertBtn = new JButton("Revert Member");
        JButton payDueBtn = new JButton("Pay Due Amount");
        JButton displayBtn = new JButton("Display Members");
        JButton clearBtn = new JButton("Clear Fields");
        JButton saveToFileBtn = new JButton("Save to File");
        JButton readFromFileBtn = new JButton("Read from File");

        // Button functionalities
        addRegularBtn.addActionListener(e -> addRegularMember());
        addPremiumBtn.addActionListener(e -> addPremiumMember());
        activateBtn.addActionListener(e -> activateMembership());
        deactivateBtn.addActionListener(e -> deactivateMembership());
        markAttendanceBtn.addActionListener(e -> markAttendance());
        upgradePlanBtn.addActionListener(e -> upgradePlan());
        calculateDiscountBtn.addActionListener(e -> calculateDiscount());
        revertBtn.addActionListener(e -> revertMember());
        payDueBtn.addActionListener(e -> payDueAmount());
        displayBtn.addActionListener(e -> displayMembers());
        clearBtn.addActionListener(e -> clearFields());
        saveToFileBtn.addActionListener(e -> saveToFile());
        readFromFileBtn.addActionListener(e -> readFromFile());

        buttonPanel.add(addRegularBtn); buttonPanel.add(addPremiumBtn);
        buttonPanel.add(activateBtn); buttonPanel.add(deactivateBtn);
        buttonPanel.add(markAttendanceBtn); buttonPanel.add(upgradePlanBtn);
        buttonPanel.add(calculateDiscountBtn); buttonPanel.add(revertBtn)
        ; buttonPanel.add(payDueBtn);
        buttonPanel.add(displayBtn); buttonPanel.add(clearBtn);
        buttonPanel.add(saveToFileBtn); buttonPanel.add(readFromFileBtn);

        frame.add(new JScrollPane(formPanel), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    
    private GymMember getMemberById(String id) {
        for (GymMember m : members) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }

    // Add a new Regular Member
    private void addRegularMember() {
        String id = idField.getText();
        if (getMemberById(id) != null) {
            JOptionPane.showMessageDialog(frame, "Member with this ID already exists.");
            return;
        }
        
        try {
            String name = nameField.getText(); 
            String location = locationField.getText(); 
            String phone = phoneField.getText();
            String email = emailField.getText(); 
            String gender = maleButton.isSelected() ? "Male" : "Female";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dob = sdf.format((Date)dobSpinner.getValue());
            String startDate = sdf.format((Date)startDateSpinner.getValue());
            String referralSource = referralField.getText();

            // Basic validation
            if (name.isEmpty() || location.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all required fields.");
                return;
            }

            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(frame, "Invalid phone number. It must be 10 digits.");
                return;
            }

            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(frame, "Invalid email address. It must contain '@'.");
                return;
            }

            // Create a new RegularMember object and add it to the ArrayList
            RegularMember member = new RegularMember(id, name, location, phone, email, gender, 
                                              dob, startDate, referralSource);
            members.add(member);
            
            JOptionPane.showMessageDialog(frame, "Regular Member Added Successfully");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error adding member: " + ex.getMessage());
        }
    }

    // Add a new Premium Member
    private void addPremiumMember() {
        String id = idField.getText();
        if (getMemberById(id) != null) {
            JOptionPane.showMessageDialog(frame, "Member with this ID already exists.");
            return;
        }
        
        try {
            String name = nameField.getText(); 
            String location = locationField.getText(); 
            String phone = phoneField.getText();
            String email = emailField.getText(); 
            String gender = maleButton.isSelected() ? "Male" : "Female";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dob = sdf.format((Date)dobSpinner.getValue());
            String startDate = sdf.format((Date)startDateSpinner.getValue());
            String personalTrainer = trainerField.getText();

            // Basic validation
            if (name.isEmpty() || location.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all required fields.");
                return;
            }

            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(frame, "Invalid phone number. It must be 10 digits.");
                return;
            }

            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(frame, "Invalid email address. It must contain '@'.");
                return;
            }

            // Create a new PremiumMember object and add it to the ArrayList
            PremiumMember member = new PremiumMember(id, name, location, phone, email, gender, 
                                               dob, startDate, personalTrainer);
            members.add(member);
            
            JOptionPane.showMessageDialog(frame, "Premium Member Added Successfully");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error adding member: " + ex.getMessage());
        }
    }

    // Activate a member's membership
    private void activateMembership() {
        String id = idField.getText();
        GymMember member = getMemberById(id);
        
        if (member == null) {
            JOptionPane.showMessageDialog(frame, "Member not found");
            return;
        }
        
        member.activateMembership();
        JOptionPane.showMessageDialog(frame, "Membership Activated for Member ID: " + id);
    }

    // Deactivate a member's membership
    private void deactivateMembership() {
        String id = idField.getText();
        GymMember member = getMemberById(id);
        
        if (member == null) {
            JOptionPane.showMessageDialog(frame, "Member not found");
            return;
        }
        
        if (!member.getActiveStatus()) {
            JOptionPane.showMessageDialog(frame, "Membership is already inactive");
            return;
        }
        
        member.deactivateMembership();
        JOptionPane.showMessageDialog(frame, "Membership Deactivated for Member ID: " + id);
    }

    // Mark attendance for a member
    private void markAttendance() {
        String id = idField.getText();
        GymMember member = getMemberById(id);
        
        if (member == null) {
            JOptionPane.showMessageDialog(frame, "Member not found");
            return;
        }
        
        if (!member.getActiveStatus()) {
            JOptionPane.showMessageDialog(frame, "Cannot mark attendance. Membership is not active.");
            return;
        }
        
        member.markAttendance();
        JOptionPane.showMessageDialog(frame, "Attendance Marked for Member ID: " + id + 
                                     "\nCurrent Attendance: " + member.getAttendance() + 
                                     "\nLoyalty Points: " + member.getLoyaltyPoints());
    }

    // Upgrade plan for a Regular Member
    private void upgradePlan() {
        String id = idField.getText();
        GymMember member = getMemberById(id);
        
        if (member == null) {
            JOptionPane.showMessageDialog(frame, "Member not found");
            return;
        }
        
        if (!member.getActiveStatus()) {
            JOptionPane.showMessageDialog(frame, "Cannot upgrade plan. Membership is not active.");
            return;
        }
        
        // Check if the member is a RegularMember
        if (member instanceof RegularMember) {
            RegularMember regularMember = (RegularMember)member;
            String newPlan = (String)planBox.getSelectedItem();
            String result = regularMember.upgradePlan(newPlan);
            JOptionPane.showMessageDialog(frame, result);
        } else {
            JOptionPane.showMessageDialog(frame, "This operation is only available for Regular Members");
        }
    }

    // Calculate discount for a Premium Member
    private void calculateDiscount() {
        String id = idField.getText();
        GymMember member = getMemberById(id);
        
        if (member == null) {
            JOptionPane.showMessageDialog(frame, "Member not found");
            return;
        }
        
        // Check if the member is a PremiumMember
        if (member instanceof PremiumMember) {
            PremiumMember premiumMember = (PremiumMember)member;
            String result = premiumMember.calculateDiscount();
            discountField.setText(String.valueOf(premiumMember.getDiscountAmount()));
            JOptionPane.showMessageDialog(frame, result);
        } else {
            JOptionPane.showMessageDialog(frame, "This operation is only available for Premium Members");
        }
    }

    // Pay due amount for a Premium Member
    private void payDueAmount() {
        String id = idField.getText();
        GymMember member = getMemberById(id);
        
        if (member == null) {
            JOptionPane.showMessageDialog(frame, "Member not found");
            return;
        }
        
        // Check if the member is a PremiumMember
        if (member instanceof PremiumMember) {
            PremiumMember premiumMember = (PremiumMember)member;
            try {
                double amount = Double.parseDouble(amountField.getText());
                String result = premiumMember.payDueAmount(amount);
                JOptionPane.showMessageDialog(frame, result);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid amount. Please enter a valid number.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "This operation is only available for Premium Members");
        }
    }

    // Revert Regular Member
    private void revertRegularMember() {
        String id = idField.getText();
        GymMember member = getMemberById(id);
        
        if (member == null) {
            JOptionPane.showMessageDialog(frame, "Member not found");
            return;
        }
        
        // Check if the member is a RegularMember
        if (member instanceof RegularMember) {
            RegularMember regularMember = (RegularMember)member;
            String reason = removalField.getText();
            regularMember.revertRegularMember(reason);
            JOptionPane.showMessageDialog(frame, "Regular Member reverted successfully");
        } else {
            JOptionPane.showMessageDialog(frame, "This operation is only available for Regular Members");
        }
    }

    // Revert Premium Member
    private void revertMember() {
        String id = idField.getText();
        GymMember member = getMemberById(id);
        if (member != null) {
            members.remove(member);
            JOptionPane.showMessageDialog(frame, "Member Removed");
        } else {
            JOptionPane.showMessageDialog(frame, "Member not found");
        }
    }

    

    // Display all members
    private void displayMembers() {
        JFrame displayFrame = new JFrame("Member Details");
        displayFrame.setSize(800, 600);
        
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        
        StringBuilder content = new StringBuilder();
        content.append(String.format("%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10s %-15s %-15s\n", 
                        "ID", "Name", "Location", "Phone", "Email", "Membership Start", "Active", "Attendance", "Loyalty", "Type"));
        content.append("-".repeat(130) + "\n");
        
        for (GymMember member : members) {
            String type = (member instanceof RegularMember) ? "Regular" : "Premium";
            content.append(String.format("%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10d %-15.2f %-15s\n", 
                            member.getId(), 
                            member.getName(), 
                            member.getLocation(), 
                            member.getPhone(), 
                            member.getEmail(), 
                            member.getMembershipStartDate(), 
                            member.getActiveStatus() ? "Yes" : "No",
                            member.getAttendance(),
                            member.getLoyaltyPoints(),
                            type));
            
            // Add specific details based on member type
            if (member instanceof RegularMember) {
                RegularMember regMember = (RegularMember)member;
                content.append(String.format("   - Plan: %s, Price: %.2f, Referral: %s\n", 
                                regMember.getPlan(), regMember.getPrice(), regMember.getReferralSource()));
                if (!regMember.getRemovalReason().isEmpty()) {
                    content.append(String.format("   - Removal Reason: %s\n", regMember.getRemovalReason()));
                }
            } else if (member instanceof PremiumMember) {
                PremiumMember premMember = (PremiumMember)member;
                content.append(String.format("   - Trainer: %s, Paid: %.2f, Full Payment: %s\n", 
                                premMember.getPersonalTrainer(), 
                                premMember.getPaidAmount(), 
                                premMember.getIsFullPayment() ? "Yes" : "No"));
                if (premMember.getIsFullPayment()) {
                    content.append(String.format("   - Discount: %.2f\n", premMember.getDiscountAmount()));
                }
                content.append(String.format("   - Remaining: %.2f\n", 
                                premMember.getPremiumCharge() - premMember.getPaidAmount()));
            }
            content.append("\n");
        }
        
        displayArea.setText(content.toString());
        
        displayFrame.add(new JScrollPane(displayArea));
        displayFrame.setVisible(true);
    }

    // Save all members to a file
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("MemberDetails.txt"))) {
            // Write header
            writer.write(String.format("%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10s %-15s %-15s\n", 
                        "ID", "Name", "Location", "Phone", "Email", "Membership Start", "Active", "Attendance", "Loyalty", "Type"));
            writer.write("-".repeat(130) + "\n");
            
            // Write member details
            for (GymMember member : members) {
                String type = (member instanceof RegularMember) ? "Regular" : "Premium";
                writer.write(String.format("%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10d %-15.2f %-15s\n", 
                              member.getId(), 
                              member.getName(), 
                              member.getLocation(), 
                              member.getPhone(), 
                              member.getEmail(), 
                              member.getMembershipStartDate(), 
                              member.getActiveStatus() ? "Yes" : "No",
                              member.getAttendance(),
                              member.getLoyaltyPoints(),
                              type));
                
                // Write type-specific details
                if (member instanceof RegularMember) {
                    RegularMember regMember = (RegularMember)member;
                    writer.write(String.format("   - Plan: %s, Price: %.2f, Referral: %s\n", 
                                  regMember.getPlan(), regMember.getPrice(), regMember.getReferralSource()));
                    if (!regMember.getRemovalReason().isEmpty()) {
                        writer.write(String.format("   - Removal Reason: %s\n", regMember.getRemovalReason()));
                    }
                } else if (member instanceof PremiumMember) {
                    PremiumMember premMember = (PremiumMember)member;
                    writer.write(String.format("   - Trainer: %s, Paid: %.2f, Full Payment: %s\n", 
                                  premMember.getPersonalTrainer(), 
                                  premMember.getPaidAmount(), 
                                  premMember.getIsFullPayment() ? "Yes" : "No"));
                    if (premMember.getIsFullPayment()) {
                        writer.write(String.format("   - Discount: %.2f\n", premMember.getDiscountAmount()));
                    }
                    writer.write(String.format("   - Remaining: %.2f\n", 
                                  premMember.getPremiumCharge() - premMember.getPaidAmount()));
                }
                writer.write("\n");
            }
            
            JOptionPane.showMessageDialog(frame, "Member details saved to file successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving to file: " + e.getMessage());
        }
    }

    // Read members from a file
    private void readFromFile() {
        try {
            JFrame readFrame = new JFrame("Member Details from File");
            readFrame.setSize(800, 600);
            
            JTextArea readArea = new JTextArea();
            readArea.setEditable(false);
            
            StringBuilder content = new StringBuilder();
            
            try (BufferedReader reader = new BufferedReader(new FileReader("MemberDetails.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
            
            readArea.setText(content.toString());
            readFrame.add(new JScrollPane(readArea));
            readFrame.setVisible(true);
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading from file: " + e.getMessage());
        }
    }

    // Clear all input fields
    private void clearFields() {
        idField.setText(""); nameField.setText(""); locationField.setText(""); phoneField.setText("");
        emailField.setText(""); referralField.setText(""); amountField.setText("0");
        removalField.setText(""); trainerField.setText(""); discountField.setText("0");
        
        ButtonGroup group = new ButtonGroup();
        group.add(maleButton);
        group.add(femaleButton);
        group.clearSelection();
        
        planBox.setSelectedIndex(0);
        
        dobSpinner.setValue(new Date());
        startDateSpinner.setValue(new Date());
    }

    // Main method
    public static void main(String[] args) {
     new GymGUI();
    }
}