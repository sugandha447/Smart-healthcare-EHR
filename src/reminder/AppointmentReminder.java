package reminder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

public class AppointmentReminder extends JFrame implements ActionListener {

    JLabel heading;
    JLabel lblPatientId;
    JLabel lblName;
    JLabel lblDoctor;
    JLabel lblDate;
    JLabel lblStatus;

    JTextField txtPatientId;
    JTextField txtName;
    JTextField txtDoctor;
    JTextField txtDate;
    JTextField txtStatus;

    JButton btnSearch;
    JButton btnClear;

    // Logged-in Doctor Employee ID
    private final String doctorId;

    // ================= DATABASE DETAILS =================

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/healthcare_ehr";

    private static final String DB_USER =
            "root";

    private static final String DB_PASSWORD =
            "Mysql@1234";


    // ================= CONSTRUCTOR =================

    // ONLY this constructor will be used
    public AppointmentReminder(String doctorId) {

        this.doctorId = doctorId;

        createUI();
    }


    // ================= CREATE UI =================

    private void createUI() {

        setTitle("Appointment Reminder");

        setSize(700, 500);

        setLayout(null);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );


        // ================= HEADING =================

        heading =
                new JLabel("SMART APPOINTMENT REMINDER");

        heading.setBounds(
                180, 20, 350, 35
        );

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        add(heading);


        // ================= PATIENT ID =================

        lblPatientId =
                new JLabel("Patient ID");

        lblPatientId.setBounds(
                80, 90, 120, 30
        );

        add(lblPatientId);


        txtPatientId =
                new JTextField();

        txtPatientId.setBounds(
                220, 90, 180, 30
        );

        add(txtPatientId);


        // ================= SEARCH BUTTON =================

        btnSearch =
                new JButton("Search");

        btnSearch.setBounds(
                430, 90, 120, 30
        );

        btnSearch.addActionListener(this);

        add(btnSearch);


        // ================= PATIENT NAME =================

        lblName =
                new JLabel("Patient Name");

        lblName.setBounds(
                80, 150, 120, 30
        );

        add(lblName);


        txtName =
                new JTextField();

        txtName.setBounds(
                220, 150, 220, 30
        );

        txtName.setEditable(false);

        add(txtName);


        // ================= DOCTOR =================

        lblDoctor =
                new JLabel("Doctor");

        lblDoctor.setBounds(
                80, 200, 120, 30
        );

        add(lblDoctor);


        txtDoctor =
                new JTextField();

        txtDoctor.setBounds(
                220, 200, 220, 30
        );

        txtDoctor.setEditable(false);

        add(txtDoctor);


        // ================= APPOINTMENT DATE =================

        lblDate =
                new JLabel("Appointment Date");

        lblDate.setBounds(
                80, 250, 120, 30
        );

        add(lblDate);


        txtDate =
                new JTextField();

        txtDate.setBounds(
                220, 250, 220, 30
        );

        txtDate.setEditable(false);

        add(txtDate);


        // ================= REMINDER =================

        lblStatus =
                new JLabel("Reminder");

        lblStatus.setBounds(
                80, 300, 120, 30
        );

        add(lblStatus);


        txtStatus =
                new JTextField();

        txtStatus.setBounds(
                220, 300, 220, 30
        );

        txtStatus.setEditable(false);

        add(txtStatus);


        // ================= CLEAR BUTTON =================

        btnClear =
                new JButton("Clear");

        btnClear.setBounds(
                260, 370, 120, 35
        );

        btnClear.addActionListener(this);

        add(btnClear);


        setVisible(true);
    }


    // ================= BUTTON ACTION =================

    @Override
    public void actionPerformed(ActionEvent e) {

        // ================= SEARCH =================

        if (e.getSource() == btnSearch) {

            searchAppointment();
        }


        // ================= CLEAR =================

        if (e.getSource() == btnClear) {

            txtPatientId.setText("");

            txtName.setText("");

            txtDoctor.setText("");

            txtDate.setText("");

            txtStatus.setText("");
        }
    }


    // =========================================================
    // SEARCH APPOINTMENT
    // =========================================================

    public void searchAppointment() {

        // ---------- Check Patient ID ----------

        String patientText =
                txtPatientId.getText().trim();


        if (patientText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Patient ID."
            );

            return;
        }


        int patientId;

        try {

            patientId =
                    Integer.parseInt(patientText);

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Patient ID must be a number."
            );

            return;
        }


        // =====================================================
        // SEARCH ONLY APPOINTMENT BELONGING TO LOGGED-IN DOCTOR
        // =====================================================

        String sql =
                "SELECT p.patient_name, " +
                        "a.doctor_name, " +
                        "a.appointment_date, " +
                        "a.appointment_time " +
                        "FROM appointments a " +
                        "INNER JOIN patients p " +
                        "ON a.patient_id = p.patient_id " +
                        "INNER JOIN users u " +
                        "ON p.doctor_id = u.user_id " +
                        "WHERE a.patient_id = ? " +
                        "AND u.employee_id = ?";


        try {

            Class.forName(
                    "com.mysql.cj.jdbc.Driver"
            );


            Connection con =
                    DriverManager.getConnection(
                            DB_URL,
                            DB_USER,
                            DB_PASSWORD
                    );


            PreparedStatement ps =
                    con.prepareStatement(sql);


            ps.setInt(
                    1,
                    patientId
            );


            ps.setString(
                    2,
                    doctorId
            );


            ResultSet rs =
                    ps.executeQuery();


            if (rs.next()) {

                // Patient Name

                txtName.setText(
                        rs.getString(
                                "patient_name"
                        )
                );


                // Doctor Name

                txtDoctor.setText(
                        rs.getString(
                                "doctor_name"
                        )
                );


                // Appointment Date + Time

                txtDate.setText(
                        rs.getDate(
                                "appointment_date"
                        ).toString()
                                + "   "
                                +
                                rs.getTime(
                                        "appointment_time"
                                ).toString()
                );


                // =================================================
                // REMINDER LOGIC
                // =================================================

                LocalDate today =
                        LocalDate.now();


                LocalDate appDate =
                        rs.getDate(
                                "appointment_date"
                        ).toLocalDate();


                if (appDate.isEqual(today)) {

                    txtStatus.setText(
                            "Appointment Today"
                    );

                }

                else if (
                        appDate.isEqual(
                                today.plusDays(1)
                        )
                ) {

                    txtStatus.setText(
                            "Appointment Tomorrow"
                    );

                }

                else if (
                        appDate.isAfter(today)
                ) {

                    txtStatus.setText(
                            "Upcoming Appointment"
                    );

                }

                else {

                    txtStatus.setText(
                            "Appointment Missed"
                    );
                }


            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Access Denied!\n\n" +
                                "This patient is not assigned to you\n" +
                                "or no appointment was found.",
                        "Access Denied",
                        JOptionPane.INFORMATION_MESSAGE
                );


                // Clear old data

                txtName.setText("");

                txtDoctor.setText("");

                txtDate.setText("");

                txtStatus.setText("");
            }


            rs.close();

            ps.close();

            con.close();


        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error:\n" +
                            ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}