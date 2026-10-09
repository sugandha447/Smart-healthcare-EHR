package dashboard;

import database.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManageDoctors extends JFrame implements ActionListener {

    JTable doctorTable;
    DefaultTableModel model;

    JButton btnUpdate;
    JButton btnActivate;
    JButton btnDeactivate;
    JButton btnRefresh;
    JButton btnClose;

    public ManageDoctors() {

        setTitle("Manage Doctors");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ================= TITLE =================

        JLabel heading = new JLabel("MANAGE DOCTORS");

        heading.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        heading.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        add(heading, BorderLayout.NORTH);

        // ================= TABLE =================

        model = new DefaultTableModel();

        model.addColumn("Employee ID");
        model.addColumn("Full Name");
        model.addColumn("Department");
        model.addColumn("Specialization");
        model.addColumn("Phone");
        model.addColumn("Email");
        model.addColumn("Username");
        model.addColumn("Status");

        doctorTable = new JTable(model);

        doctorTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        doctorTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_OFF
        );

        JScrollPane scrollPane =
                new JScrollPane(doctorTable);

        add(scrollPane, BorderLayout.CENTER);

        // ================= BUTTON PANEL =================

        JPanel buttonPanel = new JPanel();

        btnUpdate =
                new JButton("Update Doctor");

        btnActivate =
                new JButton("Activate Doctor");

        btnDeactivate =
                new JButton("Deactivate Doctor");

        btnRefresh =
                new JButton("Refresh");

        btnClose =
                new JButton("Close");

        btnUpdate.addActionListener(this);
        btnActivate.addActionListener(this);
        btnDeactivate.addActionListener(this);
        btnRefresh.addActionListener(this);
        btnClose.addActionListener(this);

        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnActivate);
        buttonPanel.add(btnDeactivate);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnClose);

        add(buttonPanel, BorderLayout.SOUTH);

        // ================= LOAD =================

        loadDoctors();

        setVisible(true);
    }

    // =====================================================
    // LOAD DOCTORS
    // =====================================================

    private void loadDoctors() {

        model.setRowCount(0);

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT employee_id, full_name, " +
                            "department, specialization, phone, " +
                            "email, username, status " +
                            "FROM users " +
                            "WHERE role = 'Doctor' " +
                            "ORDER BY employee_id";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{

                        rs.getString("employee_id"),
                        rs.getString("full_name"),
                        rs.getString("department"),
                        rs.getString("specialization"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("status")
                });
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load doctors.\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // UPDATE DOCTOR
    // =====================================================

    private void updateDoctor() {

        int row =
                doctorTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a doctor first."
            );

            return;
        }

        String employeeId =
                model.getValueAt(row, 0).toString();

        String oldName =
                model.getValueAt(row, 1).toString();

        String oldDepartment =
                model.getValueAt(row, 2).toString();

        String oldSpecialization =
                model.getValueAt(row, 3).toString();

        String oldPhone =
                model.getValueAt(row, 4).toString();

        String oldEmail =
                model.getValueAt(row, 5).toString();

        String name =
                JOptionPane.showInputDialog(
                        this,
                        "Full Name:",
                        oldName
                );

        if (name == null) return;

        String department =
                JOptionPane.showInputDialog(
                        this,
                        "Department:",
                        oldDepartment
                );

        if (department == null) return;

        String specialization =
                JOptionPane.showInputDialog(
                        this,
                        "Specialization:",
                        oldSpecialization
                );

        if (specialization == null) return;

        String phone =
                JOptionPane.showInputDialog(
                        this,
                        "Phone:",
                        oldPhone
                );

        if (phone == null) return;

        String email =
                JOptionPane.showInputDialog(
                        this,
                        "Email:",
                        oldEmail
                );

        if (email == null) return;

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "UPDATE users SET " +
                            "full_name=?, " +
                            "department=?, " +
                            "specialization=?, " +
                            "phone=?, " +
                            "email=? " +
                            "WHERE employee_id=? " +
                            "AND role='Doctor'";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, department);
            ps.setString(3, specialization);
            ps.setString(4, phone);
            ps.setString(5, email);
            ps.setString(6, employeeId);

            int result =
                    ps.executeUpdate();

            if (result > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Doctor details updated successfully."
                );
            }

            ps.close();
            con.close();

            loadDoctors();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update doctor.\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // ACTIVATE DOCTOR
    // =====================================================

    private void activateDoctor() {

        int row =
                doctorTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a doctor first."
            );

            return;
        }

        String employeeId =
                model.getValueAt(row, 0).toString();

        String doctorName =
                model.getValueAt(row, 1).toString();

        String status =
                model.getValueAt(row, 7).toString();

        if (status.equalsIgnoreCase("ACTIVE")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Dr. " + doctorName
                            + " is already active."
            );

            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Activate Dr. "
                                + doctorName
                                + "?\n\n"
                                + "The doctor will be allowed to login again.",
                        "Confirm Activation",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        changeDoctorStatus(
                employeeId,
                "ACTIVE"
        );
    }

    // =====================================================
    // DEACTIVATE DOCTOR
    // =====================================================

    private void deactivateDoctor() {

        int row =
                doctorTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a doctor first."
            );

            return;
        }

        String employeeId =
                model.getValueAt(row, 0).toString();

        String doctorName =
                model.getValueAt(row, 1).toString();

        String status =
                model.getValueAt(row, 7).toString();

        if (status.equalsIgnoreCase("INACTIVE")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Dr. " + doctorName
                            + " is already inactive."
            );

            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Deactivate Dr. "
                                + doctorName
                                + "?\n\n"
                                + "Existing patients and medical records "
                                + "will NOT be deleted.",
                        "Confirm Deactivation",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        changeDoctorStatus(
                employeeId,
                "INACTIVE"
        );
    }

    // =====================================================
    // CHANGE DOCTOR STATUS
    // =====================================================

    private void changeDoctorStatus(
            String employeeId,
            String newStatus
    ) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "UPDATE users SET status=? " +
                            "WHERE employee_id=? " +
                            "AND role='Doctor'";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, newStatus);
            ps.setString(2, employeeId);

            int result =
                    ps.executeUpdate();

            if (result > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Doctor status changed to "
                                + newStatus
                );
            }

            ps.close();
            con.close();

            loadDoctors();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to change doctor status.\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // BUTTON ACTION
    // =====================================================

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnUpdate) {
            updateDoctor();
        }

        if (e.getSource() == btnActivate) {
            activateDoctor();
        }

        if (e.getSource() == btnDeactivate) {
            deactivateDoctor();
        }

        if (e.getSource() == btnRefresh) {
            loadDoctors();
        }

        if (e.getSource() == btnClose) {
            dispose();
        }
    }
}