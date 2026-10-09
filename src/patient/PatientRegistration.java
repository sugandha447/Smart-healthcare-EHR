package patient;

import database.DBConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PatientRegistration extends JFrame implements ActionListener {

    JLabel lblName, lblAge, lblGender, lblContact, lblAddress;
    JLabel lblBloodGroup, lblAllergies, lblEmergency;
    JLabel lblDepartment, lblDoctor;

    JTextField txtName;
    JTextField txtAge;
    JTextField txtContact;
    JTextField txtAddress;
    JTextField txtAllergies;
    JTextField txtEmergency;

    JComboBox<String> cmbGender;
    JComboBox<String> cmbBloodGroup;
    JComboBox<String> cmbDepartment;
    JComboBox<String> cmbDoctor;

    JButton btnSave;
    JButton btnClear;

    public PatientRegistration() {

        setTitle("Patient Registration");
        setSize(600, 750);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ================= PATIENT NAME =================

        lblName = new JLabel("Patient Name");
        lblName.setBounds(50, 40, 130, 30);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(200, 40, 250, 30);
        add(txtName);

        // ================= AGE =================

        lblAge = new JLabel("Age");
        lblAge.setBounds(50, 80, 130, 30);
        add(lblAge);

        txtAge = new JTextField();
        txtAge.setBounds(200, 80, 250, 30);
        add(txtAge);

        // ================= GENDER =================

        lblGender = new JLabel("Gender");
        lblGender.setBounds(50, 120, 130, 30);
        add(lblGender);

        cmbGender = new JComboBox<>();

        cmbGender.addItem("Male");
        cmbGender.addItem("Female");
        cmbGender.addItem("Other");

        cmbGender.setBounds(200, 120, 250, 30);
        add(cmbGender);

        // ================= CONTACT =================

        lblContact = new JLabel("Contact");
        lblContact.setBounds(50, 160, 130, 30);
        add(lblContact);

        txtContact = new JTextField();
        txtContact.setBounds(200, 160, 250, 30);
        add(txtContact);

        // ================= ADDRESS =================

        lblAddress = new JLabel("Address");
        lblAddress.setBounds(50, 200, 130, 30);
        add(lblAddress);

        txtAddress = new JTextField();
        txtAddress.setBounds(200, 200, 250, 30);
        add(txtAddress);

        // ================= BLOOD GROUP =================

        lblBloodGroup = new JLabel("Blood Group");
        lblBloodGroup.setBounds(50, 240, 130, 30);
        add(lblBloodGroup);

        cmbBloodGroup = new JComboBox<>();

        cmbBloodGroup.addItem("A+");
        cmbBloodGroup.addItem("A-");
        cmbBloodGroup.addItem("B+");
        cmbBloodGroup.addItem("B-");
        cmbBloodGroup.addItem("AB+");
        cmbBloodGroup.addItem("AB-");
        cmbBloodGroup.addItem("O+");
        cmbBloodGroup.addItem("O-");

        cmbBloodGroup.setBounds(200, 240, 250, 30);
        add(cmbBloodGroup);

        // ================= ALLERGIES =================

        lblAllergies = new JLabel("Allergies");
        lblAllergies.setBounds(50, 280, 130, 30);
        add(lblAllergies);

        txtAllergies = new JTextField();
        txtAllergies.setBounds(200, 280, 250, 30);
        add(txtAllergies);

        // ================= EMERGENCY CONTACT =================

        lblEmergency = new JLabel("Emergency Contact");
        lblEmergency.setBounds(50, 320, 140, 30);
        add(lblEmergency);

        txtEmergency = new JTextField();
        txtEmergency.setBounds(200, 320, 250, 30);
        add(txtEmergency);

        // ================= DEPARTMENT =================

        lblDepartment = new JLabel("Department");
        lblDepartment.setBounds(50, 360, 130, 30);
        add(lblDepartment);

        cmbDepartment = new JComboBox<>();
        cmbDepartment.setBounds(200, 360, 250, 30);
        add(cmbDepartment);

        // ================= DOCTOR =================

        lblDoctor = new JLabel("Specialist Doctor");
        lblDoctor.setBounds(50, 400, 130, 30);
        add(lblDoctor);

        cmbDoctor = new JComboBox<>();
        cmbDoctor.setBounds(200, 400, 250, 30);
        add(cmbDoctor);

        // Load departments from database
        loadDepartments();

        // Department change listener
        cmbDepartment.addActionListener(e -> {

            if (cmbDepartment.getSelectedItem() != null) {

                String department =
                        cmbDepartment.getSelectedItem().toString();

                loadDoctors(department);
            }
        });

        // ================= SAVE BUTTON =================

        btnSave = new JButton("Register Patient");
        btnSave.setBounds(150, 480, 150, 40);
        btnSave.addActionListener(this);
        add(btnSave);

        // ================= CLEAR BUTTON =================

        btnClear = new JButton("Clear");
        btnClear.setBounds(320, 480, 120, 40);
        btnClear.addActionListener(this);
        add(btnClear);

        setVisible(true);
    }

    // =========================================================
    // LOAD DEPARTMENTS
    // =========================================================

    private void loadDepartments() {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT DISTINCT department " +
                            "FROM users " +
                            "WHERE role='Doctor' " +
                            "AND department IS NOT NULL " +
                            "AND department <> ''";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ResultSet rs =
                    ps.executeQuery();

            cmbDepartment.removeAllItems();

            while (rs.next()) {

                cmbDepartment.addItem(
                        rs.getString("department")
                );
            }

            rs.close();
            ps.close();
            con.close();

            // Load doctors for first department
            if (cmbDepartment.getItemCount() > 0) {

                loadDoctors(
                        cmbDepartment
                                .getSelectedItem()
                                .toString()
                );
            }

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load departments."
            );
        }
    }

    // =========================================================
    // LOAD DOCTORS ACCORDING TO DEPARTMENT
    // =========================================================

    private void loadDoctors(String department) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT user_id, full_name, specialization " +
                            "FROM users " +
                            "WHERE role='Doctor' " +
                            "AND department=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, department);

            ResultSet rs =
                    ps.executeQuery();

            cmbDoctor.removeAllItems();

            while (rs.next()) {

                int userId =
                        rs.getInt("user_id");

                String doctorName =
                        rs.getString("full_name");

                String specialization =
                        rs.getString("specialization");

                cmbDoctor.addItem(
                        userId + " - " +
                                doctorName + " (" +
                                specialization + ")"
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load doctors."
            );
        }
    }

    // =========================================================
    // GET SELECTED DOCTOR ID
    // =========================================================

    private int getSelectedDoctorId() {

        String selectedDoctor =
                cmbDoctor.getSelectedItem().toString();

        String doctorId =
                selectedDoctor.split(" - ")[0];

        return Integer.parseInt(doctorId);
    }

    // =========================================================
    // BUTTON ACTION
    // =========================================================

    @Override
    public void actionPerformed(ActionEvent e) {

        // =====================================================
        // SAVE
        // =====================================================

        if (e.getSource() == btnSave) {

            String name =
                    txtName.getText().trim();

            String age =
                    txtAge.getText().trim();

            String gender =
                    cmbGender.getSelectedItem().toString();

            String bloodGroup =
                    cmbBloodGroup.getSelectedItem().toString();

            String allergies =
                    txtAllergies.getText().trim();

            String emergency =
                    txtEmergency.getText().trim();

            String contact =
                    txtContact.getText().trim();

            String address =
                    txtAddress.getText().trim();

            // =================================================
            // VALIDATION
            // =================================================

            if (name.isEmpty() ||
                    age.isEmpty() ||
                    contact.isEmpty() ||
                    address.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please Fill All Required Fields"
                );

                return;
            }

            // Age validation

            try {

                int ageValue =
                        Integer.parseInt(age);

                if (ageValue <= 0 ||
                        ageValue > 120) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Enter a valid age between 1 and 120."
                    );

                    return;
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Age must contain numbers only."
                );

                return;
            }

            // Contact validation

            if (!contact.matches("\\d{10}")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enter a valid 10-digit contact number."
                );

                return;
            }

            // Emergency contact validation

            if (!emergency.isEmpty() &&
                    !emergency.matches("\\d{10}")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Emergency contact must contain 10 digits."
                );

                return;
            }

            // Doctor selection validation

            if (cmbDoctor.getSelectedItem() == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a specialist doctor."
                );

                return;
            }

            try {

                Connection con =
                        DBConnection.getConnection();

                // =================================================
                // CHECK DUPLICATE CONTACT
                // =================================================

                String checkQuery =
                        "SELECT * FROM patients WHERE contact=?";

                PreparedStatement check =
                        con.prepareStatement(checkQuery);

                check.setString(1, contact);

                ResultSet rs =
                        check.executeQuery();

                if (rs.next()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Patient already registered with this contact number."
                    );

                    rs.close();
                    check.close();
                    con.close();

                    return;
                }

                rs.close();
                check.close();

                // =================================================
                // GET DOCTOR ID
                // =================================================

                int doctorId =
                        getSelectedDoctorId();

                // =================================================
                // INSERT PATIENT
                // =================================================

                String query =
                        "INSERT INTO patients(" +
                                "patient_name," +
                                "age," +
                                "gender," +
                                "contact," +
                                "address," +
                                "blood_group," +
                                "allergies," +
                                "emergency_contact," +
                                "doctor_id" +
                                ") VALUES(?,?,?,?,?,?,?,?,?)";

                PreparedStatement ps =
                        con.prepareStatement(query);

                ps.setString(1, name);

                ps.setInt(
                        2,
                        Integer.parseInt(age)
                );

                ps.setString(3, gender);

                ps.setString(4, contact);

                ps.setString(5, address);

                ps.setString(6, bloodGroup);

                ps.setString(7, allergies);

                ps.setString(8, emergency);

                ps.setInt(9, doctorId);

                ps.executeUpdate();

                ps.close();

                // =================================================
                // SUCCESS MESSAGE
                // =================================================

                JOptionPane.showMessageDialog(
                        this,
                        "Patient Registered Successfully!\n" +
                                "Assigned Doctor: " +
                                cmbDoctor.getSelectedItem()
                );

                // =================================================
                // CLEAR FORM
                // =================================================

                clearForm();

                con.close();

            } catch (Exception ex) {

                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Error while registering patient:\n" +
                                ex.getMessage()
                );
            }
        }

        // =====================================================
        // CLEAR BUTTON
        // =====================================================

        if (e.getSource() == btnClear) {

            clearForm();
        }
    }

    // =========================================================
    // CLEAR FORM METHOD
    // =========================================================

    private void clearForm() {

        txtName.setText("");
        txtAge.setText("");

        cmbGender.setSelectedIndex(0);

        txtContact.setText("");
        txtAddress.setText("");

        cmbBloodGroup.setSelectedIndex(0);

        txtAllergies.setText("");
        txtEmergency.setText("");

        if (cmbDepartment.getItemCount() > 0) {

            cmbDepartment.setSelectedIndex(0);

            loadDoctors(
                    cmbDepartment
                            .getSelectedItem()
                            .toString()
            );
        }
    }
}