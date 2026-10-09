package dashboard;

import database.DBConnection;
import security.PasswordUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeManagement extends JFrame implements ActionListener {

    JLabel lblEmployeeId, lblFullName, lblRole, lblDepartment;
    JLabel lblSpecialization, lblPhone, lblEmail, lblUsername, lblPassword;

    JTextField txtEmployeeId, txtFullName;
    JTextField txtPhone, txtEmail;
    JTextField txtUsername;
    JPasswordField txtPassword;

    JComboBox<String> cmbRole;
    JComboBox<String> cmbDepartment;

    JTextField txtSpecialization;

    JButton btnCreate;
    JButton btnClear;

    // ================= CONSTRUCTOR =================

    public EmployeeManagement() {

        setTitle("Employee Management");
        setSize(650, 700);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ================= HEADING =================

        JLabel heading =
                new JLabel("EMPLOYEE MANAGEMENT");

        heading.setBounds(190, 20, 300, 35);
        heading.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        add(heading);

        // ================= EMPLOYEE ID =================

        lblEmployeeId =
                new JLabel("Employee ID");

        lblEmployeeId.setBounds(
                70, 80, 150, 30
        );

        add(lblEmployeeId);

        txtEmployeeId =
                new JTextField();

        txtEmployeeId.setBounds(
                250, 80, 250, 30
        );

        txtEmployeeId.setEditable(false);
        txtEmployeeId.setText("Auto Generated");

        add(txtEmployeeId);

        // ================= FULL NAME =================

        lblFullName =
                new JLabel("Full Name");

        lblFullName.setBounds(
                70, 125, 150, 30
        );

        add(lblFullName);

        txtFullName =
                new JTextField();

        txtFullName.setBounds(
                250, 125, 250, 30
        );

        add(txtFullName);

        // ================= ROLE =================

        lblRole =
                new JLabel("Role");

        lblRole.setBounds(
                70, 170, 150, 30
        );

        add(lblRole);

        cmbRole =
                new JComboBox<>(
                        new String[]{
                                "Select Role",
                                "Doctor",
                                "Receptionist",
                                "Laboratory"
                        }
                );

        cmbRole.setBounds(
                250, 170, 250, 30
        );

        cmbRole.addActionListener(this);

        add(cmbRole);

        // ================= DEPARTMENT =================

        lblDepartment =
                new JLabel("Department");

        lblDepartment.setBounds(
                70, 215, 150, 30
        );

        add(lblDepartment);

        cmbDepartment =
                new JComboBox<>();

        cmbDepartment.setBounds(
                250, 215, 250, 30
        );

        add(cmbDepartment);

        // Load departments from database
        loadDepartments();

        // ================= SPECIALIZATION =================

        lblSpecialization =
                new JLabel("Specialization");

        lblSpecialization.setBounds(
                70, 260, 150, 30
        );

        add(lblSpecialization);

        txtSpecialization =
                new JTextField();

        txtSpecialization.setBounds(
                250, 260, 250, 30
        );

        add(txtSpecialization);

        // ================= PHONE =================

        lblPhone =
                new JLabel("Phone");

        lblPhone.setBounds(
                70, 305, 150, 30
        );

        add(lblPhone);

        txtPhone =
                new JTextField();

        txtPhone.setBounds(
                250, 305, 250, 30
        );

        add(txtPhone);

        // ================= EMAIL =================

        lblEmail =
                new JLabel("Email");

        lblEmail.setBounds(
                70, 350, 150, 30
        );

        add(lblEmail);

        txtEmail =
                new JTextField();

        txtEmail.setBounds(
                250, 350, 250, 30
        );

        add(txtEmail);

        // ================= USERNAME =================

        lblUsername =
                new JLabel("Username");

        lblUsername.setBounds(
                70, 395, 150, 30
        );

        add(lblUsername);

        txtUsername =
                new JTextField();

        txtUsername.setBounds(
                250, 395, 250, 30
        );

        add(txtUsername);

        // ================= PASSWORD =================

        lblPassword =
                new JLabel("Password");

        lblPassword.setBounds(
                70, 440, 150, 30
        );

        add(lblPassword);

        txtPassword =
                new JPasswordField();

        txtPassword.setBounds(
                250, 440, 250, 30
        );

        add(txtPassword);

        // ================= CREATE =================

        btnCreate =
                new JButton("Create Account");

        btnCreate.setBounds(
                150, 510, 150, 40
        );

        btnCreate.addActionListener(this);

        add(btnCreate);

        // ================= CLEAR =================

        btnClear =
                new JButton("Clear");

        btnClear.setBounds(
                330, 510, 150, 40
        );

        btnClear.addActionListener(this);

        add(btnClear);

        // ================= INITIAL STATE =================

        cmbDepartment.setEnabled(false);
        txtSpecialization.setEnabled(false);

        // ================= SHOW WINDOW =================

        setVisible(true);
    }


    // =========================================================
    // LOAD ACTIVE DEPARTMENTS FROM DATABASE
    // =========================================================

    private void loadDepartments() {

        cmbDepartment.removeAllItems();

        cmbDepartment.addItem("Select Department");

        String sql =
                "SELECT department_name " +
                        "FROM departments " +
                        "WHERE LOWER(status) = 'active' " +
                        "ORDER BY department_name";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                cmbDepartment.addItem(
                        rs.getString("department_name")
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading departments."
            );
        }
    }


    // =========================================================
    // BUTTON ACTIONS
    // =========================================================

    @Override
    public void actionPerformed(ActionEvent e) {

        // ================= ROLE CHANGE =================

        if (e.getSource() == cmbRole) {

            String role =
                    (String) cmbRole.getSelectedItem();


            // ================= DOCTOR =================

            if ("Doctor".equals(role)) {

                cmbDepartment.setEnabled(true);
                txtSpecialization.setEnabled(true);
            }


            // ================= RECEPTIONIST =================

            else if ("Receptionist".equals(role)) {

                cmbDepartment.setEnabled(true);

                txtSpecialization.setText("");
                txtSpecialization.setEnabled(false);
            }


            // ================= LABORATORY =================

            else if ("Laboratory".equals(role)) {

                cmbDepartment.setSelectedIndex(0);
                cmbDepartment.setEnabled(false);

                txtSpecialization.setText("");
                txtSpecialization.setEnabled(false);
            }


            // ================= NO ROLE =================

            else {

                cmbDepartment.setEnabled(false);
                txtSpecialization.setEnabled(false);
            }
        }


        // ================= CREATE ACCOUNT =================

        if (e.getSource() == btnCreate) {

            createEmployee();
        }


        // ================= CLEAR =================

        if (e.getSource() == btnClear) {

            clearFields();
        }
    }


    // =========================================================
    // CREATE EMPLOYEE
    // =========================================================

    private void createEmployee() {

        String fullName =
                txtFullName.getText().trim();

        String role =
                (String) cmbRole.getSelectedItem();

        String department =
                (String) cmbDepartment.getSelectedItem();

        String specialization =
                txtSpecialization.getText().trim();

        String phone =
                txtPhone.getText().trim();

        String email =
                txtEmail.getText().trim();

        String username =
                txtUsername.getText().trim();

        String password =
                new String(
                        txtPassword.getPassword()
                );


        // ================= BASIC VALIDATION =================

        if (fullName.isEmpty() ||
                role.equals("Select Role") ||
                phone.isEmpty() ||
                email.isEmpty() ||
                username.isEmpty() ||
                password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields."
            );

            return;
        }


        // ================= DOCTOR VALIDATION =================

        if (role.equals("Doctor")) {

            if (department.equals("Select Department") ||
                    specialization.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Department and Specialization for Doctor."
                );

                return;
            }
        }


        // ================= RECEPTIONIST =================

        if (role.equals("Receptionist")) {

            if (department.equals("Select Department")) {

                department = "";
            }
        }


        // ================= LABORATORY =================

        if (role.equals("Laboratory")) {

            department = "";
            specialization = "";
        }


        // ================= PHONE VALIDATION =================

        if (!phone.matches("\\d{10}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Phone number must contain exactly 10 digits."
            );

            return;
        }


        // ================= EMAIL VALIDATION =================

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email."
            );

            return;
        }


        Connection con = null;

        try {

            con =
                    DBConnection.getConnection();


            // =================================================
            // CHECK USERNAME
            // =================================================

            String checkUsername =
                    "SELECT user_id " +
                            "FROM users " +
                            "WHERE username=?";

            PreparedStatement ps1 =
                    con.prepareStatement(
                            checkUsername
                    );

            ps1.setString(
                    1,
                    username
            );

            ResultSet rs =
                    ps1.executeQuery();

            if (rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Username already exists."
                );

                rs.close();
                ps1.close();

                return;
            }

            rs.close();
            ps1.close();


            // =================================================
            // GENERATE EMPLOYEE ID
            // =================================================

            String prefix;

            if (role.equals("Doctor")) {

                prefix = "DOC";

            } else if (role.equals("Laboratory")) {

                prefix = "LAB";

            } else {

                prefix = "REC";
            }


            String generateId =
                    "SELECT employee_id " +
                            "FROM users " +
                            "WHERE employee_id LIKE ? " +
                            "ORDER BY user_id DESC " +
                            "LIMIT 1";

            PreparedStatement ps2 =
                    con.prepareStatement(
                            generateId
                    );

            ps2.setString(
                    1,
                    prefix + "%"
            );

            ResultSet rs2 =
                    ps2.executeQuery();

            int nextNumber = 1;

            if (rs2.next()) {

                String lastId =
                        rs2.getString(
                                "employee_id"
                        );

                try {

                    String numberPart =
                            lastId.substring(3);

                    nextNumber =
                            Integer.parseInt(
                                    numberPart
                            ) + 1;

                } catch (Exception ignored) {

                    nextNumber = 1;
                }
            }

            rs2.close();
            ps2.close();


            String employeeId =
                    prefix +
                            String.format(
                                    "%03d",
                                    nextNumber
                            );


            // =================================================
            // HASH PASSWORD
            // =================================================

            String hashedPassword =
                    PasswordUtil.hashPassword(
                            password
                    );


            // =================================================
            // INSERT EMPLOYEE
            // =================================================

            String query =
                    "INSERT INTO users " +
                            "(employee_id, username, password, role, " +
                            "full_name, department, specialization, phone, email) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps =
                    con.prepareStatement(
                            query
                    );

            ps.setString(1, employeeId);
            ps.setString(2, username);
            ps.setString(3, hashedPassword);
            ps.setString(4, role);
            ps.setString(5, fullName);
            ps.setString(6, department);
            ps.setString(7, specialization);
            ps.setString(8, phone);
            ps.setString(9, email);


            ps.executeUpdate();


            // =================================================
            // SUCCESS MESSAGE
            // =================================================

            JOptionPane.showMessageDialog(
                    this,
                    "Employee account created successfully!\n\n"
                            + "Employee ID: "
                            + employeeId
                            + "\nRole: "
                            + role
                            + "\nUsername: "
                            + username
            );


            ps.close();

            clearFields();


        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error creating employee account:\n"
                            + ex.getMessage()
            );

        } finally {

            try {

                if (con != null) {
                    con.close();
                }

            } catch (Exception ignored) {
            }
        }
    }


    // =========================================================
    // CLEAR FIELDS
    // =========================================================

    private void clearFields() {

        txtEmployeeId.setText(
                "Auto Generated"
        );

        txtFullName.setText("");

        cmbRole.setSelectedIndex(0);

        if (cmbDepartment.getItemCount() > 0) {
            cmbDepartment.setSelectedIndex(0);
        }

        txtSpecialization.setText("");

        txtPhone.setText("");

        txtEmail.setText("");

        txtUsername.setText("");

        txtPassword.setText("");

        cmbDepartment.setEnabled(false);

        txtSpecialization.setEnabled(false);
    }


    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                EmployeeManagement::new
        );
    }
}