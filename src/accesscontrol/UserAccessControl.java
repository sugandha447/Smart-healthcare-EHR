package accesscontrol;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserAccessControl extends JFrame implements ActionListener {

    JTable userTable;
    DefaultTableModel tableModel;

    JTextField txtSearch;

    JButton btnSearch;
    JButton btnRefresh;
    JButton btnActivate;
    JButton btnDeactivate;
    JButton btnClose;


    // ================= DATABASE DETAILS =================

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/healthcare_ehr";

    private static final String DB_USER =
            "root";

    private static final String DB_PASSWORD =
            "Mysql@1234";


    // ================= CONSTRUCTOR =================

    public UserAccessControl() {

        setTitle("User Access Control");

        setSize(1000, 600);

        setLayout(null);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );


        // ================= HEADING =================

        JLabel heading =
                new JLabel("USER ACCESS CONTROL");

        heading.setBounds(
                350,
                20,
                350,
                35
        );

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        add(heading);


        // ================= SEARCH LABEL =================

        JLabel lblSearch =
                new JLabel("Search User:");

        lblSearch.setBounds(
                50,
                80,
                100,
                30
        );

        add(lblSearch);


        // ================= SEARCH FIELD =================

        txtSearch =
                new JTextField();

        txtSearch.setBounds(
                150,
                80,
                250,
                30
        );

        add(txtSearch);


        // ================= SEARCH BUTTON =================

        btnSearch =
                new JButton("Search");

        btnSearch.setBounds(
                420,
                80,
                100,
                30
        );

        btnSearch.addActionListener(this);

        add(btnSearch);


        // ================= REFRESH BUTTON =================

        btnRefresh =
                new JButton("Refresh");

        btnRefresh.setBounds(
                540,
                80,
                100,
                30
        );

        btnRefresh.addActionListener(this);

        add(btnRefresh);


        // ================= TABLE =================

        String[] columns = {
                "User ID",
                "Employee ID",
                "Username",
                "Full Name",
                "Role",
                "Department",
                "Specialization",
                "Phone",
                "Email",
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


        userTable =
                new JTable(tableModel);


        userTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        JScrollPane scrollPane =
                new JScrollPane(userTable);

        scrollPane.setBounds(
                30,
                130,
                920,
                300
        );

        add(scrollPane);


        // ================= ACTIVATE BUTTON =================

        btnActivate =
                new JButton("Activate User");

        btnActivate.setBounds(
                150,
                460,
                150,
                40
        );

        btnActivate.addActionListener(this);

        add(btnActivate);


        // ================= DEACTIVATE BUTTON =================

        btnDeactivate =
                new JButton("Deactivate User");

        btnDeactivate.setBounds(
                330,
                460,
                170,
                40
        );

        btnDeactivate.addActionListener(this);

        add(btnDeactivate);


        // ================= CLOSE BUTTON =================

        btnClose =
                new JButton("Close");

        btnClose.setBounds(
                550,
                460,
                120,
                40
        );

        btnClose.addActionListener(this);

        add(btnClose);


        // ================= LOAD USERS =================

        loadUsers();


        setVisible(true);
    }


    // =========================================================
    // LOAD ALL USERS
    // =========================================================

    private void loadUsers() {

        tableModel.setRowCount(0);


        String sql =
                "SELECT user_id, employee_id, username, " +
                        "full_name, role, department, " +
                        "specialization, phone, email, status " +
                        "FROM users " +
                        "ORDER BY user_id";


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


            PreparedStatement pst =
                    con.prepareStatement(sql);


            ResultSet rs =
                    pst.executeQuery();


            while (rs.next()) {

                Object[] row = {

                        rs.getInt("user_id"),

                        rs.getString("employee_id"),

                        rs.getString("username"),

                        rs.getString("full_name"),

                        rs.getString("role"),

                        rs.getString("department"),

                        rs.getString("specialization"),

                        rs.getString("phone"),

                        rs.getString("email"),

                        rs.getString("status")
                };


                tableModel.addRow(row);
            }


            rs.close();

            pst.close();

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


    // =========================================================
    // SEARCH USERS
    // =========================================================

    private void searchUsers() {

        String searchText =
                txtSearch.getText().trim();


        if (searchText.isEmpty()) {

            loadUsers();

            return;
        }


        tableModel.setRowCount(0);


        String sql =
                "SELECT user_id, employee_id, username, " +
                        "full_name, role, department, " +
                        "specialization, phone, email, status " +
                        "FROM users " +
                        "WHERE employee_id LIKE ? " +
                        "OR username LIKE ? " +
                        "OR full_name LIKE ? " +
                        "OR role LIKE ? " +
                        "ORDER BY user_id";


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


            PreparedStatement pst =
                    con.prepareStatement(sql);


            String keyword =
                    "%" + searchText + "%";


            pst.setString(1, keyword);

            pst.setString(2, keyword);

            pst.setString(3, keyword);

            pst.setString(4, keyword);


            ResultSet rs =
                    pst.executeQuery();


            while (rs.next()) {

                Object[] row = {

                        rs.getInt("user_id"),

                        rs.getString("employee_id"),

                        rs.getString("username"),

                        rs.getString("full_name"),

                        rs.getString("role"),

                        rs.getString("department"),

                        rs.getString("specialization"),

                        rs.getString("phone"),

                        rs.getString("email"),

                        rs.getString("status")
                };


                tableModel.addRow(row);
            }


            rs.close();

            pst.close();

            con.close();


            if (tableModel.getRowCount() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "No user found."
                );
            }


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


    // =========================================================
    // CHANGE USER STATUS
    // =========================================================

    private void changeUserStatus(String newStatus) {

        int selectedRow =
                userTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user first."
            );

            return;
        }


        int userId =
                Integer.parseInt(
                        tableModel.getValueAt(
                                selectedRow,
                                0
                        ).toString()
                );


        String employeeId =
                tableModel.getValueAt(
                        selectedRow,
                        1
                ).toString();


        String currentStatus =
                tableModel.getValueAt(
                        selectedRow,
                        9
                ).toString();


        // ================= SAME STATUS CHECK =================

        if (currentStatus.equalsIgnoreCase(newStatus)) {

            JOptionPane.showMessageDialog(
                    this,
                    "User is already " +
                            newStatus + "."
            );

            return;
        }


        // ================= CONFIRMATION =================

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to " +
                                newStatus.toLowerCase() +
                                " user " +
                                employeeId +
                                "?",
                        "Confirm Action",
                        JOptionPane.YES_NO_OPTION
                );


        if (choice != JOptionPane.YES_OPTION) {

            return;
        }


        // ================= UPDATE DATABASE =================

        String sql =
                "UPDATE users " +
                        "SET status = ? " +
                        "WHERE user_id = ?";


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


            PreparedStatement pst =
                    con.prepareStatement(sql);


            pst.setString(
                    1,
                    newStatus
            );


            pst.setInt(
                    2,
                    userId
            );


            int rows =
                    pst.executeUpdate();


            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "User " +
                                newStatus.toLowerCase() +
                                "d successfully."
                );

                loadUsers();
            }


            pst.close();

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


    // =========================================================
    // BUTTON ACTIONS
    // =========================================================

    @Override
    public void actionPerformed(ActionEvent e) {


        // ================= SEARCH =================

        if (e.getSource() == btnSearch) {

            searchUsers();
        }


        // ================= REFRESH =================

        if (e.getSource() == btnRefresh) {

            txtSearch.setText("");

            loadUsers();
        }


        // ================= ACTIVATE =================

        if (e.getSource() == btnActivate) {

            changeUserStatus("Active");
        }


        // ================= DEACTIVATE =================

        if (e.getSource() == btnDeactivate) {

            changeUserStatus("Inactive");
        }


        // ================= CLOSE =================

        if (e.getSource() == btnClose) {

            dispose();
        }
    }
}
