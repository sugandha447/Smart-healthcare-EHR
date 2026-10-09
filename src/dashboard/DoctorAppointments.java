package dashboard;

import database.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class DoctorAppointments extends JFrame {

    private String employeeId;

    private JTable appointmentTable;
    private DefaultTableModel tableModel;

    private JButton btnConfirm;
    private JButton btnCancel;
    private JButton btnRefresh;
    private JButton btnBack;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public DoctorAppointments(String employeeId) {

        this.employeeId = employeeId;


        // =====================================================
        // WINDOW SETTINGS
        // =====================================================

        setTitle("Doctor Appointment Requests");

        setSize(1100, 600);

        setLayout(null);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        // =====================================================
        // HEADING
        // =====================================================

        JLabel heading =
                new JLabel("APPOINTMENT REQUESTS");

        heading.setBounds(
                400,
                20,
                350,
                40
        );

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        add(heading);


        // =====================================================
        // DOCTOR INFORMATION
        // =====================================================

        JLabel doctorLabel =
                new JLabel(
                        "Doctor: " + employeeId
                );

        doctorLabel.setBounds(
                40,
                70,
                300,
                30
        );

        doctorLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        add(doctorLabel);


        // =====================================================
        // TABLE
        // =====================================================

        String[] columns = {

                "Appointment ID",
                "Patient ID",
                "Patient Name",
                "Department",
                "Specialization",
                "Date",
                "Time",
                "Status"
        };


        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };


        appointmentTable =
                new JTable(tableModel);


        appointmentTable.setRowHeight(25);


        JScrollPane scrollPane =
                new JScrollPane(
                        appointmentTable
                );


        scrollPane.setBounds(
                30,
                110,
                1020,
                300
        );


        add(scrollPane);


        // =====================================================
        // CONFIRM BUTTON
        // =====================================================

        btnConfirm =
                new JButton("Confirm Appointment");

        btnConfirm.setBounds(
                180,
                450,
                200,
                45
        );

        add(btnConfirm);


        // =====================================================
        // CANCEL BUTTON
        // =====================================================

        btnCancel =
                new JButton("Cancel Appointment");

        btnCancel.setBounds(
                400,
                450,
                200,
                45
        );

        add(btnCancel);


        // =====================================================
        // REFRESH BUTTON
        // =====================================================

        btnRefresh =
                new JButton("Refresh");

        btnRefresh.setBounds(
                620,
                450,
                120,
                45
        );

        add(btnRefresh);


        // =====================================================
        // BACK BUTTON
        // =====================================================

        btnBack =
                new JButton("Back");

        btnBack.setBounds(
                760,
                450,
                120,
                45
        );

        add(btnBack);


        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        btnConfirm.addActionListener(e ->
                updateAppointmentStatus("Confirmed")
        );


        btnCancel.addActionListener(e ->
                updateAppointmentStatus("Cancelled")
        );


        btnRefresh.addActionListener(e ->
                loadAppointments()
        );


        btnBack.addActionListener(e ->
                dispose()
        );


        // =====================================================
        // LOAD APPOINTMENTS
        // =====================================================

        loadAppointments();


        // =====================================================
        // SHOW WINDOW
        // =====================================================

        setVisible(true);
    }


    // =========================================================
    // LOAD DOCTOR'S APPOINTMENTS
    // =========================================================

    private void loadAppointments() {

        tableModel.setRowCount(0);


        /*
         * We first find the doctor's full name
         * using employee_id.
         *
         * Then we find appointments for that doctor.
         *
         * Patient information comes from patients table.
         */

        String sql =
                "SELECT a.appointment_id, " +
                        "a.patient_id, " +
                        "p.patient_name, " +
                        "a.department, " +
                        "a.specialization, " +
                        "a.appointment_date, " +
                        "a.appointment_time, " +
                        "a.appointment_status " +

                        "FROM appointments a " +

                        "LEFT JOIN patients p " +
                        "ON a.patient_id = p.patient_id " +

                        "WHERE a.doctor_name = " +

                        "(SELECT full_name " +
                        "FROM users " +
                        "WHERE employee_id = ? " +
                        "AND role = 'Doctor') " +

                        "ORDER BY a.appointment_date, " +
                        "a.appointment_time";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {


            ps.setString(
                    1,
                    employeeId
            );


            ResultSet rs =
                    ps.executeQuery();


            boolean found = false;


            while (rs.next()) {

                found = true;


                Object[] row = {

                        rs.getInt(
                                "appointment_id"
                        ),

                        rs.getInt(
                                "patient_id"
                        ),

                        rs.getString(
                                "patient_name"
                        ),

                        rs.getString(
                                "department"
                        ),

                        rs.getString(
                                "specialization"
                        ),

                        rs.getDate(
                                "appointment_date"
                        ),

                        rs.getTime(
                                "appointment_time"
                        ),

                        rs.getString(
                                "appointment_status"
                        )
                };


                tableModel.addRow(row);
            }


            if (!found) {

                JOptionPane.showMessageDialog(
                        this,
                        "No appointments found."
                );
            }


        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading appointments:\n"
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // UPDATE APPOINTMENT STATUS
    // =========================================================

    private void updateAppointmentStatus(
            String newStatus) {


        // =====================================================
        // CHECK TABLE SELECTION
        // =====================================================

        int selectedRow =
                appointmentTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an appointment first."
            );

            return;
        }


        // =====================================================
        // GET DATA FROM SELECTED ROW
        // =====================================================

        int appointmentId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );


        String currentStatus =
                tableModel.getValueAt(
                        selectedRow,
                        7
                ).toString();


        String patientName =
                tableModel.getValueAt(
                        selectedRow,
                        2
                ).toString();


        // =====================================================
        // PREVENT INVALID STATUS CHANGES
        // =====================================================

        if (!currentStatus.equalsIgnoreCase(
                "Pending")) {

            JOptionPane.showMessageDialog(
                    this,
                    "This appointment is already " +
                            currentStatus +
                            "."
            );

            return;
        }


        // =====================================================
        // CONFIRM ACTION
        // =====================================================

        String message;


        if (newStatus.equals("Confirmed")) {

            message =
                    "Confirm appointment for "
                            + patientName
                            + "?";

        } else {

            message =
                    "Cancel appointment for "
                            + patientName
                            + "?";
        }


        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        message,
                        "Confirm Action",
                        JOptionPane.YES_NO_OPTION
                );


        if (choice != JOptionPane.YES_OPTION) {

            return;
        }


        // =====================================================
        // UPDATE DATABASE
        // =====================================================

        String sql =
                "UPDATE appointments " +
                        "SET appointment_status = ? " +
                        "WHERE appointment_id = ? " +
                        "AND appointment_status = 'Pending'";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {


            ps.setString(
                    1,
                    newStatus
            );


            ps.setInt(
                    2,
                    appointmentId
            );


            int rowsUpdated =
                    ps.executeUpdate();


            if (rowsUpdated > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Appointment status updated to "
                                + newStatus
                                + "."
                );


                // Reload table
                loadAppointments();


            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Appointment could not be updated."
                );
            }


        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error updating appointment:\n"
                            + e.getMessage()
            );
        }
    }
}
