package dashboard;

import database.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MyAppointments extends JFrame {

    private int patientId;

    private JTable appointmentTable;
    private DefaultTableModel tableModel;

    // ================= CONSTRUCTOR =================

    public MyAppointments(int patientId) {

        this.patientId = patientId;

        setTitle("My Appointments");

        setSize(950, 550);

        setLayout(null);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        // ================= HEADING =================

        JLabel heading =
                new JLabel("MY APPOINTMENTS");

        heading.setBounds(
                350, 30, 300, 40
        );

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        add(heading);


        // ================= TABLE =================

        String[] columns = {
                "Appointment ID",
                "Doctor",
                "Department",
                "Specialization",
                "Date",
                "Time",
                "Status"
        };


        tableModel =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };


        appointmentTable =
                new JTable(tableModel);


        appointmentTable.setRowHeight(30);


        JScrollPane scrollPane =
                new JScrollPane(appointmentTable);

        scrollPane.setBounds(
                40, 100, 860, 280
        );

        add(scrollPane);


        // ================= BACK BUTTON =================

        JButton btnBack =
                new JButton("Back");

        btnBack.setBounds(
                380, 420, 150, 40
        );

        add(btnBack);


        btnBack.addActionListener(e -> {

            dispose();

        });


        // ================= LOAD APPOINTMENTS =================

        loadAppointments();


        setVisible(true);
    }


    // =====================================================
    // LOAD PATIENT APPOINTMENTS
    // =====================================================

    private void loadAppointments() {

        String sql =
                "SELECT appointment_id, doctor_name, " +
                        "department, specialization, " +
                        "appointment_date, appointment_time, " +
                        "appointment_status " +
                        "FROM appointments " +
                        "WHERE patient_id = ? " +
                        "ORDER BY appointment_date, appointment_time";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, patientId);


            ResultSet rs =
                    ps.executeQuery();


            tableModel.setRowCount(0);


            boolean found = false;


            while (rs.next()) {

                found = true;


                Object[] row = {

                        rs.getInt("appointment_id"),

                        rs.getString("doctor_name"),

                        rs.getString("department"),

                        rs.getString("specialization"),

                        rs.getDate("appointment_date"),

                        rs.getTime("appointment_time"),

                        rs.getString("appointment_status")
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
}
