package dashboard;

import database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class BookAppointment extends JFrame {

    private int patientId;

    private JComboBox<String> doctorCombo;
    private JTextField departmentField;
    private JTextField specializationField;
    private JTextField dateField;
    private JComboBox<String> timeCombo;

    private JButton btnBook;
    private JButton btnBack;

    public BookAppointment(int patientId) {

        this.patientId = patientId;

        setTitle("Book Appointment");
        setSize(650, 550);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ---------------- TITLE ----------------

        JLabel heading = new JLabel("BOOK APPOINTMENT");
        heading.setBounds(200, 30, 300, 40);
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        add(heading);


        // ---------------- DOCTOR ----------------

        JLabel doctorLabel = new JLabel("Select Doctor:");
        doctorLabel.setBounds(80, 100, 150, 30);
        add(doctorLabel);

        doctorCombo = new JComboBox<>();
        doctorCombo.setBounds(250, 100, 280, 30);
        add(doctorCombo);


        // ---------------- DEPARTMENT ----------------

        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setBounds(80, 150, 150, 30);
        add(departmentLabel);

        departmentField = new JTextField();
        departmentField.setBounds(250, 150, 280, 30);
        departmentField.setEditable(false);
        add(departmentField);


        // ---------------- SPECIALIZATION ----------------

        JLabel specializationLabel = new JLabel("Specialization:");
        specializationLabel.setBounds(80, 200, 150, 30);
        add(specializationLabel);

        specializationField = new JTextField();
        specializationField.setBounds(250, 200, 280, 30);
        specializationField.setEditable(false);
        add(specializationField);


        // ---------------- DATE ----------------

        JLabel dateLabel = new JLabel("Appointment Date:");
        dateLabel.setBounds(80, 250, 150, 30);
        add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(250, 250, 280, 30);

        // Today's date
        dateField.setText(LocalDate.now().toString());

        add(dateField);


        // ---------------- TIME ----------------

        JLabel timeLabel = new JLabel("Appointment Time:");
        timeLabel.setBounds(80, 300, 150, 30);
        add(timeLabel);

        timeCombo = new JComboBox<>();

        timeCombo.addItem("09:00:00");
        timeCombo.addItem("10:00:00");
        timeCombo.addItem("11:00:00");
        timeCombo.addItem("12:00:00");
        timeCombo.addItem("14:00:00");
        timeCombo.addItem("15:00:00");
        timeCombo.addItem("16:00:00");

        timeCombo.setBounds(250, 300, 280, 30);
        add(timeCombo);


        // ---------------- BOOK BUTTON ----------------

        btnBook = new JButton("Book Appointment");
        btnBook.setBounds(180, 370, 180, 40);
        add(btnBook);


        // ---------------- BACK BUTTON ----------------

        btnBack = new JButton("Back");
        btnBack.setBounds(380, 370, 120, 40);
        add(btnBack);


        // ---------------- LOAD DOCTORS ----------------

        loadDoctors();


        // ---------------- DOCTOR SELECTION ----------------

        doctorCombo.addActionListener(e -> {

            String selectedDoctor =
                    (String) doctorCombo.getSelectedItem();

            if (selectedDoctor != null &&
                    !selectedDoctor.isEmpty()) {

                loadDoctorDetails(selectedDoctor);
            }
        });


        // ---------------- BOOK APPOINTMENT ----------------

        btnBook.addActionListener(e -> bookAppointment());


        // ---------------- BACK ----------------

        btnBack.addActionListener(e -> dispose());


        setVisible(true);
    }


    // =========================================================
    // LOAD ACTIVE DOCTORS
    // =========================================================

    private void loadDoctors() {

        String sql =
                "SELECT full_name " +
                        "FROM users " +
                        "WHERE role = 'Doctor' " +
                        "AND status = 'ACTIVE'";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            doctorCombo.removeAllItems();

            while (rs.next()) {

                doctorCombo.addItem(
                        rs.getString("full_name")
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading doctors:\n" +
                            e.getMessage()
            );
        }
    }


    // =========================================================
    // LOAD DOCTOR DEPARTMENT + SPECIALIZATION
    // =========================================================

    private void loadDoctorDetails(String doctorName) {

        String sql =
                "SELECT department, specialization " +
                        "FROM users " +
                        "WHERE full_name = ? " +
                        "AND role = 'Doctor'";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, doctorName);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                departmentField.setText(
                        rs.getString("department")
                );

                specializationField.setText(
                        rs.getString("specialization")
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading doctor details:\n" +
                            e.getMessage()
            );
        }
    }


    // =========================================================
    // BOOK APPOINTMENT
    // =========================================================

    private void bookAppointment() {

        String doctor =
                (String) doctorCombo.getSelectedItem();

        String department =
                departmentField.getText();

        String specialization =
                specializationField.getText();

        String date =
                dateField.getText().trim();

        String time =
                (String) timeCombo.getSelectedItem();


        // ---------------- BASIC VALIDATION ----------------

        if (doctor == null || doctor.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a doctor."
            );

            return;
        }


        if (date.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter appointment date."
            );

            return;
        }


        if (time == null || time.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select appointment time."
            );

            return;
        }


        try {

            // Convert String date/time
            Date appointmentDate =
                    Date.valueOf(date);

            Time appointmentTime =
                    Time.valueOf(time);


            // =================================================
            // CHECK PAST DATE
            // =================================================

            LocalDate selectedDate =
                    appointmentDate.toLocalDate();

            LocalDate today =
                    LocalDate.now();


            if (selectedDate.isBefore(today)) {

                JOptionPane.showMessageDialog(
                        this,
                        "You cannot book an appointment for a past date."
                );

                return;
            }


            // =================================================
            // CHECK SLOT AVAILABILITY
            // =================================================

            String checkSql =
                    "SELECT COUNT(*) " +
                            "FROM appointments " +
                            "WHERE doctor_name = ? " +
                            "AND appointment_date = ? " +
                            "AND appointment_time = ? " +
                            "AND appointment_status IN ('Pending', 'Confirmed')";


            try (
                    Connection con =
                            DBConnection.getConnection();

                    PreparedStatement checkPs =
                            con.prepareStatement(checkSql)
            ) {

                checkPs.setString(1, doctor);

                checkPs.setDate(
                        2,
                        appointmentDate
                );

                checkPs.setTime(
                        3,
                        appointmentTime
                );


                ResultSet rs =
                        checkPs.executeQuery();


                if (rs.next()) {

                    int count =
                            rs.getInt(1);


                    // Slot already occupied
                    if (count > 0) {

                        JOptionPane.showMessageDialog(
                                this,
                                "This time slot is already booked " +
                                        "for Dr. " + doctor +
                                        ".\n\nPlease select another time."
                        );

                        return;
                    }
                }
            }


            // =================================================
            // INSERT APPOINTMENT
            // =================================================

            String insertSql =
                    "INSERT INTO appointments " +
                            "(patient_id, doctor_name, department, " +
                            "specialization, appointment_date, " +
                            "appointment_time, appointment_status) " +
                            "VALUES (?, ?, ?, ?, ?, ?, 'Pending')";


            try (
                    Connection con =
                            DBConnection.getConnection();

                    PreparedStatement ps =
                            con.prepareStatement(insertSql)
            ) {

                ps.setInt(
                        1,
                        patientId
                );

                ps.setString(
                        2,
                        doctor
                );

                ps.setString(
                        3,
                        department
                );

                ps.setString(
                        4,
                        specialization
                );

                ps.setDate(
                        5,
                        appointmentDate
                );

                ps.setTime(
                        6,
                        appointmentTime
                );


                ps.executeUpdate();


                // =================================================
                // SUCCESS MESSAGE
                // =================================================

                JOptionPane.showMessageDialog(
                        this,
                        "Appointment booked successfully!\n\n" +
                                "Doctor: " + doctor +
                                "\nDepartment: " + department +
                                "\nSpecialization: " + specialization +
                                "\nDate: " + date +
                                "\nTime: " + time +
                                "\nStatus: Pending"
                );


                // Clear / reset fields
                dateField.setText(
                        LocalDate.now().toString()
                );

                timeCombo.setSelectedIndex(0);

            }


        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date format.\n\n" +
                            "Please enter date like:\n" +
                            "2026-10-01"
            );


        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error booking appointment:\n" +
                            ex.getMessage()
            );
        }
    }
}