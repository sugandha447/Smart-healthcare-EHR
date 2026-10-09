package analytics;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AnalyticsDashboard extends JFrame {
    JLabel heading;

    JLabel lblPatients;
    JLabel lblAppointments;
    JLabel lblRecords;
    JLabel lblHighRisk;

    JLabel totalPatients;
    JLabel totalAppointments;
    JLabel totalRecords;
    JLabel totalHighRisk;

    public AnalyticsDashboard() {

        setTitle("Analytics octorDashboard");

        setSize(700, 500);

        setLayout(null);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(Color.WHITE);

        heading = new JLabel("HEALTHCARE ANALYTICS DASHBOARD");

        heading.setBounds(140, 20, 450, 40);

        heading.setFont(new Font("Arial", Font.BOLD, 24));

        add(heading);

        lblPatients = new JLabel("Total Patients");

        lblPatients.setBounds(80, 100, 150, 30);

        add(lblPatients);

        totalPatients = new JLabel("0");

        totalPatients.setBounds(250, 100, 150, 30);

        add(totalPatients);

        lblAppointments = new JLabel("Appointments");

        lblAppointments.setBounds(80, 150, 150, 30);

        add(lblAppointments);

        totalAppointments = new JLabel("0");

        totalAppointments.setBounds(250, 150, 150, 30);

        add(totalAppointments);

        lblRecords = new JLabel("Medical Records");

        lblRecords.setBounds(80, 200, 150, 30);

        add(lblRecords);

        totalRecords = new JLabel("0");

        totalRecords.setBounds(250, 200, 150, 30);

        add(totalRecords);

        lblHighRisk = new JLabel("High Risk Patients");

        lblHighRisk.setBounds(80, 250, 150, 30);

        add(lblHighRisk);

        totalHighRisk = new JLabel("0");

        totalHighRisk.setBounds(250, 250, 150, 30);

        add(totalHighRisk);

        loadAnalytics();

        setVisible(true);
    }

    public void loadAnalytics() {

        try {

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/healthcare_ehr",
                    "root",
                    "Mysql@1234"
            );

            Statement st = con.createStatement();

            // Total Patients
            ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM patients");

            if (rs1.next()) {

                totalPatients.setText(rs1.getString(1));

            }

            // Total Appointments
            ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM appointments");

            if (rs2.next()) {

                totalAppointments.setText(rs2.getString(1));

            }

            // Total Medical Records
            ResultSet rs3 = st.executeQuery("SELECT COUNT(*) FROM medical_records");

            if (rs3.next()) {

                totalRecords.setText(rs3.getString(1));

            }

            // High Risk Patients
            ResultSet rs4 = st.executeQuery(
                    "SELECT COUNT(*) FROM medical_records WHERE diagnosis='High Risk'"
            );

            if (rs4.next()) {

                totalHighRisk.setText(rs4.getString(1));

            }

            con.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, e);

        }

    }
}



