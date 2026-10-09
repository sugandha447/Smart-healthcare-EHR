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

public class ManageReceptionists extends JFrame implements ActionListener {

    JTable receptionistTable;
    DefaultTableModel model;

    JButton btnUpdate;
    JButton btnActivate;
    JButton btnDeactivate;
    JButton btnRefresh;
    JButton btnClose;

    public ManageReceptionists() {

        setTitle("Manage Receptionists");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ================= TITLE =================

        JLabel heading =
                new JLabel("MANAGE RECEPTIONISTS");

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
        model.addColumn("Phone");
        model.addColumn("Email");
        model.addColumn("Username");
        model.addColumn("Status");

        receptionistTable =
                new JTable(model);

        receptionistTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(receptionistTable);

        add(scrollPane, BorderLayout.CENTER);

        // ================= BUTTON PANEL =================

        JPanel buttonPanel =
                new JPanel();

        btnUpdate =
                new JButton("Update Receptionist");

        btnActivate =
                new JButton("Activate Receptionist");

        btnDeactivate =
                new JButton("Deactivate Receptionist");

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

        loadReceptionists();

        setVisible(true);
    }

    // =====================================================
    // LOAD RECEPTIONISTS
    // =====================================================

    private void loadReceptionists() {

        model.setRowCount(0);

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT employee_id, full_name, " +
                            "department, phone, email, " +
                            "username, status " +
                            "FROM users " +
                            "WHERE role='Receptionist' " +
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
                    "Unable to load receptionists.\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // UPDATE RECEPTIONIST
    // =====================================================

    private void updateReceptionist() {

        int row =
                receptionistTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a receptionist first."
            );

            return;
        }

        String employeeId =
                model.getValueAt(row, 0).toString();

        String oldName =
                model.getValueAt(row, 1).toString();

        String oldDepartment =
                model.getValueAt(row, 2).toString();

        String oldPhone =
                model.getValueAt(row, 3).toString();

        String oldEmail =
                model.getValueAt(row, 4).toString();

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
                            "phone=?, " +
                            "email=? " +
                            "WHERE employee_id=? " +
                            "AND role='Receptionist'";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, department);
            ps.setString(3, phone);
            ps.setString(4, email);
            ps.setString(5, employeeId);

            int result =
                    ps.executeUpdate();

            if (result > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Receptionist details updated successfully."
                );
            }

            ps.close();
            con.close();

            loadReceptionists();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update receptionist.\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // ACTIVATE RECEPTIONIST
    // =====================================================

    private void activateReceptionist() {

        int row =
                receptionistTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a receptionist first."
            );

            return;
        }

        String employeeId =
                model.getValueAt(row, 0).toString();

        String name =
                model.getValueAt(row, 1).toString();

        String status =
                model.getValueAt(row, 6).toString();

        if (status.equalsIgnoreCase("ACTIVE")) {

            JOptionPane.showMessageDialog(
                    this,
                    name + " is already active."
            );

            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Activate "
                                + name
                                + "?\n\n"
                                + "The receptionist will be allowed to login again.",
                        "Confirm Activation",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        changeReceptionistStatus(
                employeeId,
                "ACTIVE"
        );
    }

    // =====================================================
    // DEACTIVATE RECEPTIONIST
    // =====================================================

    private void deactivateReceptionist() {

        int row =
                receptionistTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a receptionist first."
            );

            return;
        }

        String employeeId =
                model.getValueAt(row, 0).toString();

        String name =
                model.getValueAt(row, 1).toString();

        String status =
                model.getValueAt(row, 6).toString();

        if (status.equalsIgnoreCase("INACTIVE")) {

            JOptionPane.showMessageDialog(
                    this,
                    name + " is already inactive."
            );

            return;
        }

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Deactivate "
                                + name
                                + "?\n\n"
                                + "Existing records will NOT be deleted.",
                        "Confirm Deactivation",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        changeReceptionistStatus(
                employeeId,
                "INACTIVE"
        );
    }

    // =====================================================
    // CHANGE STATUS
    // =====================================================

    private void changeReceptionistStatus(
            String employeeId,
            String newStatus
    ) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "UPDATE users SET status=? " +
                            "WHERE employee_id=? " +
                            "AND role='Receptionist'";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, newStatus);
            ps.setString(2, employeeId);

            int result =
                    ps.executeUpdate();

            if (result > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Receptionist status changed to "
                                + newStatus
                );
            }

            ps.close();
            con.close();

            loadReceptionists();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to change receptionist status.\n"
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
            updateReceptionist();
        }

        if (e.getSource() == btnActivate) {
            activateReceptionist();
        }

        if (e.getSource() == btnDeactivate) {
            deactivateReceptionist();
        }

        if (e.getSource() == btnRefresh) {
            loadReceptionists();
        }

        if (e.getSource() == btnClose) {
            dispose();
        }
    }
}