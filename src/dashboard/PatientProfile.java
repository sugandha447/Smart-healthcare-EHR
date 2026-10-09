package dashboard;

import database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PatientProfile extends JFrame {

    private int patientId;

    public PatientProfile(int patientId) {

        this.patientId = patientId;

        setTitle("My Profile");
        setSize(600, 550);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ================= HEADING =================

        JLabel heading = new JLabel("MY PROFILE");

        heading.setBounds(220, 30, 200, 40);

        heading.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        add(heading);


        // ================= LABELS =================

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(80, 100, 150, 30);
        add(lblName);

        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(80, 140, 150, 30);
        add(lblAge);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(80, 180, 150, 30);
        add(lblGender);

        JLabel lblContact = new JLabel("Contact:");
        lblContact.setBounds(80, 220, 150, 30);
        add(lblContact);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(80, 260, 150, 30);
        add(lblAddress);

        JLabel lblBloodGroup = new JLabel("Blood Group:");
        lblBloodGroup.setBounds(80, 300, 150, 30);
        add(lblBloodGroup);

        JLabel lblAllergies = new JLabel("Allergies:");
        lblAllergies.setBounds(80, 340, 150, 30);
        add(lblAllergies);

        JLabel lblEmergency = new JLabel("Emergency Contact:");
        lblEmergency.setBounds(80, 380, 150, 30);
        add(lblEmergency);


        // ================= VALUE LABELS =================

        JLabel nameValue = new JLabel();
        nameValue.setBounds(250, 100, 280, 30);
        add(nameValue);

        JLabel ageValue = new JLabel();
        ageValue.setBounds(250, 140, 280, 30);
        add(ageValue);

        JLabel genderValue = new JLabel();
        genderValue.setBounds(250, 180, 280, 30);
        add(genderValue);

        JLabel contactValue = new JLabel();
        contactValue.setBounds(250, 220, 280, 30);
        add(contactValue);

        JLabel addressValue = new JLabel();
        addressValue.setBounds(250, 260, 280, 30);
        add(addressValue);

        JLabel bloodGroupValue = new JLabel();
        bloodGroupValue.setBounds(250, 300, 280, 30);
        add(bloodGroupValue);

        JLabel allergiesValue = new JLabel();
        allergiesValue.setBounds(250, 340, 280, 30);
        add(allergiesValue);

        JLabel emergencyValue = new JLabel();
        emergencyValue.setBounds(250, 380, 280, 30);
        add(emergencyValue);


        // ================= BACK BUTTON =================

        JButton btnBack = new JButton("Back");

        btnBack.setBounds(220, 440, 150, 40);

        add(btnBack);

        btnBack.addActionListener(e -> dispose());


        // ================= LOAD PATIENT DATA =================

        loadPatientData(
                nameValue,
                ageValue,
                genderValue,
                contactValue,
                addressValue,
                bloodGroupValue,
                allergiesValue,
                emergencyValue
        );


        setVisible(true);
    }


    // =====================================================
    // LOAD PATIENT DATA FROM DATABASE
    // =====================================================

    private void loadPatientData(
            JLabel nameValue,
            JLabel ageValue,
            JLabel genderValue,
            JLabel contactValue,
            JLabel addressValue,
            JLabel bloodGroupValue,
            JLabel allergiesValue,
            JLabel emergencyValue
    ) {

        String sql =
                "SELECT * FROM patients WHERE patient_id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, patientId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                nameValue.setText(rs.getString("patient_name"));

                ageValue.setText(
                        String.valueOf(rs.getInt("age"))
                );

                genderValue.setText(
                        rs.getString("gender")
                );

                contactValue.setText(
                        rs.getString("contact")
                );

                addressValue.setText(
                        rs.getString("address")
                );

                bloodGroupValue.setText(
                        rs.getString("blood_group")
                );

                allergiesValue.setText(
                        rs.getString("allergies")
                );

                emergencyValue.setText(
                        rs.getString("emergency_contact")
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Patient details not found."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading patient details:\n"
                            + e.getMessage()
            );
        }
    }
}
