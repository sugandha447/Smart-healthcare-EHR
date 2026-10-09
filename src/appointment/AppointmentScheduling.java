package appointment;

import database.DBConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AppointmentScheduling extends JFrame implements ActionListener {

    JLabel lblPatientId;
    JLabel lblDoctorName;
    JLabel lblDate;
    JLabel lblTime;

    JTextField txtPatientId;
    JTextField txtDoctorName;
    JTextField txtDate;
    JTextField txtTime;

    JButton btnSave;
    JButton btnClear;

    public AppointmentScheduling() {

        setTitle("Appointment Scheduling");
        setSize(500, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lblPatientId = new JLabel("Patient ID");
        lblPatientId.setBounds(50, 50, 120, 30);
        add(lblPatientId);

        txtPatientId = new JTextField();
        txtPatientId.setBounds(180, 50, 200, 30);
        add(txtPatientId);

        lblDoctorName = new JLabel("Doctor Name");
        lblDoctorName.setBounds(50, 100, 120, 30);
        add(lblDoctorName);

        txtDoctorName = new JTextField();
        txtDoctorName.setBounds(180, 100, 200, 30);
        add(txtDoctorName);

        lblDate = new JLabel("Date (YYYY-MM-DD)");
        lblDate.setBounds(50, 150, 150, 30);
        add(lblDate);

        txtDate = new JTextField();
        txtDate.setBounds(180, 150, 200, 30);
        add(txtDate);

        lblTime = new JLabel("Time (HH:MM:SS)");
        lblTime.setBounds(50, 200, 150, 30);
        add(lblTime);

        txtTime = new JTextField();
        txtTime.setBounds(180, 200, 200, 30);
        add(txtTime);

        btnSave = new JButton("Save");
        btnSave.setBounds(120, 280, 100, 35);
        btnSave.addActionListener(this);
        add(btnSave);

        btnClear = new JButton("Clear");
        btnClear.setBounds(250, 280, 100, 35);
        btnClear.addActionListener(this);
        add(btnClear);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnSave) {

            String patientId = txtPatientId.getText().trim();
            String doctorName = txtDoctorName.getText().trim();
            String appointmentDate = txtDate.getText().trim();
            String appointmentTime = txtTime.getText().trim();

            if (patientId.isEmpty() ||
                    doctorName.isEmpty() ||
                    appointmentDate.isEmpty() ||
                    appointmentTime.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Please Fill All Fields");

                return;
            }

            try {

                Connection con = DBConnection.getConnection();

                // Check whether slot is already booked
                PreparedStatement check = con.prepareStatement(
                        "SELECT * FROM appointments WHERE doctor_name=? AND appointment_date=? AND appointment_time=?"
                );

                check.setString(1, doctorName);
                check.setDate(2, java.sql.Date.valueOf(appointmentDate));
                check.setTime(3, java.sql.Time.valueOf(appointmentTime));

                ResultSet rs = check.executeQuery();

                if (rs.next()) {

                    String message =
                            "❌ Slot Already Booked!\n\n" +
                                    "Suggested Available Slots:\n\n" +
                                    "✅ 11:00:00\n" +
                                    "✅ 11:30:00\n" +
                                    "✅ 12:00:00";

                    JOptionPane.showMessageDialog(this, message);

                    con.close();
                    return;
                }

                // Save Appointment
                String query =
                        "INSERT INTO appointments(patient_id,doctor_name,appointment_date,appointment_time) VALUES(?,?,?,?)";

                PreparedStatement ps = con.prepareStatement(query);

                ps.setInt(1, Integer.parseInt(patientId));
                ps.setString(2, doctorName);
                ps.setDate(3, java.sql.Date.valueOf(appointmentDate));
                ps.setTime(4, java.sql.Time.valueOf(appointmentTime));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Appointment Scheduled Successfully");

                txtPatientId.setText("");
                txtDoctorName.setText("");
                txtDate.setText("");
                txtTime.setText("");

                con.close();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this, ex.getMessage());

            }
        }
    }
}