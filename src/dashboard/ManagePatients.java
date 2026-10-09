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

public class ManagePatients extends JFrame implements ActionListener {

    JTable patientTable;
    DefaultTableModel model;

    JTextField txtSearch;
    JComboBox<String> cmbFilter;

    JButton btnSearch;
    JButton btnReset;
    JButton btnView;
    JButton btnRefresh;
    JButton btnClose;

    public ManagePatients() {

        setTitle("Manage Patients");
        setSize(1200, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ================= TITLE =================

        JLabel heading =
                new JLabel("MANAGE PATIENTS");

        heading.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        heading.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        add(heading, BorderLayout.NORTH);

        // ================= SEARCH PANEL =================

        JPanel searchPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 10)
        );

        JLabel lblSearch =
                new JLabel("Search:");

        txtSearch =
                new JTextField(18);

        cmbFilter =
                new JComboBox<>();

        cmbFilter.addItem("Patient ID");
        cmbFilter.addItem("Patient Name");
        cmbFilter.addItem("Doctor ID");
        cmbFilter.addItem("Department");

        btnSearch =
                new JButton("Search");

        btnReset =
                new JButton("Reset");

        btnSearch.addActionListener(this);
        btnReset.addActionListener(this);

        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(cmbFilter);
        searchPanel.add(btnSearch);
        searchPanel.add(btnReset);

        add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);

        // ================= TABLE =================

        model =
                new DefaultTableModel();

        model.addColumn("Patient ID");
        model.addColumn("Patient Name");
        model.addColumn("Age");
        model.addColumn("Gender");
        model.addColumn("Blood Group");
        model.addColumn("Contact");
        model.addColumn("Doctor ID");
        model.addColumn("Department");

        patientTable =
                new JTable(model);

        patientTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        patientTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_OFF
        );

        JScrollPane scrollPane =
                new JScrollPane(patientTable);

        add(scrollPane, BorderLayout.CENTER);

        // ================= BUTTON PANEL =================

        JPanel buttonPanel =
                new JPanel();

        btnView =
                new JButton("View Patient Details");

        btnRefresh =
                new JButton("Refresh");

        btnClose =
                new JButton("Close");

        btnView.addActionListener(this);
        btnRefresh.addActionListener(this);
        btnClose.addActionListener(this);

        buttonPanel.add(btnView);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnClose);

        add(buttonPanel, BorderLayout.SOUTH);

        // ================= LOAD =================

        loadPatients();

        setVisible(true);
    }

    // =====================================================
    // LOAD ALL PATIENTS
    // =====================================================

    private void loadPatients() {

        model.setRowCount(0);

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT " +
                            "p.patient_id, " +
                            "p.patient_name, " +
                            "p.age, " +
                            "p.gender, " +
                            "p.blood_group, " +
                            "p.contact, " +
                            "p.doctor_id, " +
                            "u.department " +
                            "FROM patients p " +
                            "LEFT JOIN users u " +
                            "ON p.doctor_id = u.user_id " +
                            "ORDER BY p.patient_id";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{

                        rs.getInt("patient_id"),
                        rs.getString("patient_name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("blood_group"),
                        rs.getString("contact"),
                        rs.getInt("doctor_id"),
                        rs.getString("department")
                });
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load patients.\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // SEARCH PATIENTS
    // =====================================================

    private void searchPatients() {

        String searchText =
                txtSearch.getText().trim();

        if (searchText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter something to search."
            );

            return;
        }

        model.setRowCount(0);

        String selectedFilter =
                cmbFilter.getSelectedItem().toString();

        try {

            Connection con =
                    DBConnection.getConnection();

            String column;

            if (selectedFilter.equals("Patient ID")) {

                column = "p.patient_id";

            } else if (
                    selectedFilter.equals("Patient Name")
            ) {

                column = "p.patient_name";

            } else if (
                    selectedFilter.equals("Doctor ID")
            ) {

                column = "p.doctor_id";

            } else {

                column = "u.department";
            }

            String query;

            // ID fields use exact matching
            if (selectedFilter.equals("Patient ID") ||
                    selectedFilter.equals("Doctor ID")) {

                query =
                        "SELECT " +
                                "p.patient_id, " +
                                "p.patient_name, " +
                                "p.age, " +
                                "p.gender, " +
                                "p.blood_group, " +
                                "p.contact, " +
                                "p.doctor_id, " +
                                "u.department " +
                                "FROM patients p " +
                                "LEFT JOIN users u " +
                                "ON p.doctor_id = u.user_id " +
                                "WHERE " + column + " = ? " +
                                "ORDER BY p.patient_id";

            } else {

                query =
                        "SELECT " +
                                "p.patient_id, " +
                                "p.patient_name, " +
                                "p.age, " +
                                "p.gender, " +
                                "p.blood_group, " +
                                "p.contact, " +
                                "p.doctor_id, " +
                                "u.department " +
                                "FROM patients p " +
                                "LEFT JOIN users u " +
                                "ON p.doctor_id = u.user_id " +
                                "WHERE " + column + " LIKE ? " +
                                "ORDER BY p.patient_id";
            }

            PreparedStatement ps =
                    con.prepareStatement(query);

            if (selectedFilter.equals("Patient ID") ||
                    selectedFilter.equals("Doctor ID")) {

                ps.setInt(
                        1,
                        Integer.parseInt(searchText)
                );

            } else {

                ps.setString(
                        1,
                        "%" + searchText + "%"
                );
            }

            ResultSet rs =
                    ps.executeQuery();

            int count = 0;

            while (rs.next()) {

                model.addRow(new Object[]{

                        rs.getInt("patient_id"),
                        rs.getString("patient_name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("blood_group"),
                        rs.getString("contact"),
                        rs.getInt("doctor_id"),
                        rs.getString("department")
                });

                count++;
            }

            if (count == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "No patients found."
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Patient ID or Doctor ID must be a number."
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Search failed.\n"
                            + ex.getMessage(),
                    "Search Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // RESET SEARCH
    // =====================================================

    private void resetSearch() {

        txtSearch.setText("");

        cmbFilter.setSelectedIndex(0);

        loadPatients();
    }

    // =====================================================
    // VIEW PATIENT DETAILS
    // =====================================================

    private void viewPatientDetails() {

        int row =
                patientTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a patient first."
            );

            return;
        }

        String patientId =
                model.getValueAt(row, 0).toString();

        String patientName =
                model.getValueAt(row, 1).toString();

        String age =
                model.getValueAt(row, 2).toString();

        String gender =
                model.getValueAt(row, 3).toString();

        String bloodGroup =
                model.getValueAt(row, 4).toString();

        String contact =
                model.getValueAt(row, 5).toString();

        String doctorId =
                model.getValueAt(row, 6).toString();

        String department =
                model.getValueAt(row, 7).toString();

        String details =
                "PATIENT DETAILS\n\n"
                        + "Patient ID       : " + patientId + "\n"
                        + "Patient Name     : " + patientName + "\n"
                        + "Age              : " + age + "\n"
                        + "Gender           : " + gender + "\n"
                        + "Blood Group      : " + bloodGroup + "\n"
                        + "Contact          : " + contact + "\n"
                        + "Doctor ID        : " + doctorId + "\n"
                        + "Department       : " + department;

        JOptionPane.showMessageDialog(
                this,
                details,
                "Patient Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =====================================================
    // BUTTON ACTION
    // =====================================================

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnSearch) {

            searchPatients();
        }

        if (e.getSource() == btnReset) {

            resetSearch();
        }

        if (e.getSource() == btnView) {

            viewPatientDetails();
        }

        if (e.getSource() == btnRefresh) {

            loadPatients();
        }

        if (e.getSource() == btnClose) {

            dispose();
        }
    }
}