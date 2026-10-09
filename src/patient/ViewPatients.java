package patient;

import database.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewPatients extends JFrame implements ActionListener {

    JLabel lblTitle;
    JLabel lblSearch;

    JTextField txtSearch;

    JButton btnSearch;
    JButton btnRefresh;
    JButton btnClose;

    JTable table;
    DefaultTableModel model;

    public ViewPatients() {

        setTitle("View Patients");
        setSize(1200, 680);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // =====================================================
        // TITLE
        // =====================================================

        lblTitle = new JLabel("PATIENT RECORDS");

        lblTitle.setBounds(480, 20, 300, 35);
        lblTitle.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        add(lblTitle);

        // =====================================================
        // SEARCH LABEL
        // =====================================================

        lblSearch = new JLabel("Search Patient:");

        lblSearch.setBounds(50, 75, 120, 30);
        lblSearch.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        add(lblSearch);

        // =====================================================
        // SEARCH FIELD
        // =====================================================

        txtSearch = new JTextField();

        txtSearch.setBounds(170, 75, 350, 30);

        add(txtSearch);

        // =====================================================
        // SEARCH BUTTON
        // =====================================================

        btnSearch = new JButton("Search");

        btnSearch.setBounds(540, 75, 110, 30);

        btnSearch.addActionListener(this);

        add(btnSearch);

        // =====================================================
        // REFRESH BUTTON
        // =====================================================

        btnRefresh = new JButton("Refresh");

        btnRefresh.setBounds(670, 75, 110, 30);

        btnRefresh.addActionListener(this);

        add(btnRefresh);

        // =====================================================
        // TABLE MODEL
        // =====================================================

        model = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {

                return false;
            }
        };

        model.addColumn("Patient ID");
        model.addColumn("Patient Name");
        model.addColumn("Age");
        model.addColumn("Gender");
        model.addColumn("Contact");
        model.addColumn("Address");
        model.addColumn("Blood Group");
        model.addColumn("Allergies");
        model.addColumn("Emergency Contact");
        model.addColumn("Assigned Doctor");

        // =====================================================
        // TABLE
        // =====================================================

        table = new JTable(model);

        table.setRowHeight(28);

        table.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        table.setAutoResizeMode(
                JTable.AUTO_RESIZE_OFF
        );

        // =====================================================
        // COLUMN WIDTHS
        // =====================================================

        table.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(80);

        table.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(150);

        table.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(60);

        table.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(80);

        table.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(120);

        table.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(150);

        table.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(100);

        table.getColumnModel()
                .getColumn(7)
                .setPreferredWidth(150);

        table.getColumnModel()
                .getColumn(8)
                .setPreferredWidth(150);

        table.getColumnModel()
                .getColumn(9)
                .setPreferredWidth(160);

        // =====================================================
        // SCROLL PANE
        // =====================================================

        JScrollPane scrollPane =
                new JScrollPane(table);

        scrollPane.setBounds(
                40,
                125,
                1110,
                420
        );

        add(scrollPane);

        // =====================================================
        // CLOSE BUTTON
        // =====================================================

        btnClose = new JButton("Close");

        btnClose.setBounds(
                500,
                570,
                150,
                40
        );

        btnClose.addActionListener(this);

        add(btnClose);

        // =====================================================
        // LOAD PATIENTS
        // =====================================================

        loadPatients();

        setVisible(true);
    }

    // =========================================================
    // LOAD ALL PATIENTS
    // =========================================================

    private void loadPatients() {

        model.setRowCount(0);

        String sql =
                "SELECT " +
                        "p.patient_id, " +
                        "p.patient_name, " +
                        "p.age, " +
                        "p.gender, " +
                        "p.contact, " +
                        "p.address, " +
                        "p.blood_group, " +
                        "p.allergies, " +
                        "p.emergency_contact, " +
                        "COALESCE(u.full_name, 'Not Assigned') AS doctor_name " +
                        "FROM patients p " +
                        "LEFT JOIN users u " +
                        "ON p.doctor_id = u.user_id " +
                        "ORDER BY p.patient_id";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                model.addRow(
                        new Object[]{
                                rs.getInt("patient_id"),
                                rs.getString("patient_name"),
                                rs.getInt("age"),
                                rs.getString("gender"),
                                rs.getString("contact"),
                                rs.getString("address"),
                                rs.getString("blood_group"),
                                rs.getString("allergies"),
                                rs.getString("emergency_contact"),
                                rs.getString("doctor_name")
                        }
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading patient records."
            );
        }
    }

    // =========================================================
    // SEARCH PATIENT
    // =========================================================

    private void searchPatients() {

        String search =
                txtSearch.getText().trim();

        if (search.isEmpty()) {

            loadPatients();

            return;
        }

        model.setRowCount(0);

        String sql =
                "SELECT " +
                        "p.patient_id, " +
                        "p.patient_name, " +
                        "p.age, " +
                        "p.gender, " +
                        "p.contact, " +
                        "p.address, " +
                        "p.blood_group, " +
                        "p.allergies, " +
                        "p.emergency_contact, " +
                        "COALESCE(u.full_name, 'Not Assigned') AS doctor_name " +
                        "FROM patients p " +
                        "LEFT JOIN users u " +
                        "ON p.doctor_id = u.user_id " +
                        "WHERE " +
                        "CAST(p.patient_id AS CHAR) = ? " +
                        "OR LOWER(p.patient_name) LIKE LOWER(?) " +
                        "OR p.contact = ? " +
                        "OR LOWER(p.blood_group) = LOWER(?) " +
                        "OR p.emergency_contact = ? " +
                        "ORDER BY p.patient_id";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            // Patient ID → exact match
            ps.setString(1, search);

            // Patient Name → partial match
            ps.setString(2, "%" + search + "%");

            // Contact → exact match
            ps.setString(3, search);

            // Blood Group → exact match
            ps.setString(4, search);

            // Emergency Contact → exact match
            ps.setString(5, search);

            ResultSet rs =
                    ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                model.addRow(
                        new Object[]{
                                rs.getInt("patient_id"),
                                rs.getString("patient_name"),
                                rs.getInt("age"),
                                rs.getString("gender"),
                                rs.getString("contact"),
                                rs.getString("address"),
                                rs.getString("blood_group"),
                                rs.getString("allergies"),
                                rs.getString("emergency_contact"),
                                rs.getString("doctor_name")
                        }
                );
            }

            if (!found) {

                JOptionPane.showMessageDialog(
                        this,
                        "No patient found."
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error searching patient records."
            );
        }
    }
    // =========================================================
    // BUTTON ACTIONS
    // =========================================================

    @Override
    public void actionPerformed(ActionEvent e) {

        // Search

        if (e.getSource() == btnSearch) {

            searchPatients();
        }

        // Refresh

        if (e.getSource() == btnRefresh) {

            txtSearch.setText("");

            loadPatients();
        }

        // Close

        if (e.getSource() == btnClose) {

            dispose();
        }
    }

    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                ViewPatients::new
        );
    }
}