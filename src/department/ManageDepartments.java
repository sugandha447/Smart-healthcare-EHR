package department;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ManageDepartments extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtName;
    private JTextField txtDescription;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnRefresh;
    private JButton btnClose;

    private int selectedDepartmentId = -1;

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/healthcare_ehr";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Mysql@1234";

    public ManageDepartments() {

        setTitle("Manage Departments");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // =========================
        // TOP FORM
        // =========================

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        formPanel.add(new JLabel("Department Name:"));

        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Description:"));

        txtDescription = new JTextField();
        formPanel.add(txtDescription);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        // =========================
        // TABLE
        // =========================

        model = new DefaultTableModel(
                new String[]{
                        "Department ID",
                        "Department Name",
                        "Description",
                        "Status"
                }, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel = new JPanel(new FlowLayout());

        btnAdd = new JButton("Add Department");
        btnUpdate = new JButton("Update Department");
        btnDelete = new JButton("Delete Department");
        btnClear = new JButton("Clear");
        btnRefresh = new JButton("Refresh");
        btnClose = new JButton("Close");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnClose);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // =========================
        // BUTTON ACTIONS
        // =========================

        btnAdd.addActionListener(e -> addDepartment());

        btnUpdate.addActionListener(e -> updateDepartment());

        btnDelete.addActionListener(e -> deleteDepartment());

        btnClear.addActionListener(e -> clearFields());

        btnRefresh.addActionListener(e -> loadDepartments());

        btnClose.addActionListener(e -> dispose());

        // =========================
        // TABLE ROW SELECTION
        // =========================

        table.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int row = table.getSelectedRow();

                if (row >= 0) {

                    selectedDepartmentId =
                            Integer.parseInt(
                                    model.getValueAt(row, 0).toString()
                            );

                    txtName.setText(
                            model.getValueAt(row, 1).toString()
                    );

                    Object description =
                            model.getValueAt(row, 2);

                    txtDescription.setText(
                            description == null
                                    ? ""
                                    : description.toString()
                    );
                }
            }
        });

        loadDepartments();

        setVisible(true);
    }

    // =====================================================
    // DATABASE CONNECTION
    // =====================================================

    private Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                DB_URL,
                DB_USER,
                DB_PASSWORD
        );
    }

    // =====================================================
    // LOAD DEPARTMENTS
    // =====================================================

    private void loadDepartments() {

        model.setRowCount(0);

        String sql =
                "SELECT department_id, department_name, " +
                        "description, status " +
                        "FROM departments " +
                        "ORDER BY department_id";

        try (
                Connection con = getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()
        ) {

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("department_id"),
                        rs.getString("department_name"),
                        rs.getString("description"),
                        rs.getString("status")
                });
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load departments.\n\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // ADD DEPARTMENT
    // =====================================================

    private void addDepartment() {

        String name = txtName.getText().trim();
        String description = txtDescription.getText().trim();

        // Validation
        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter department name.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            txtName.requestFocus();
            return;
        }

        if (name.length() > 100) {

            JOptionPane.showMessageDialog(
                    this,
                    "Department name cannot exceed 100 characters.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (description.length() > 255) {

            JOptionPane.showMessageDialog(
                    this,
                    "Description cannot exceed 255 characters.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Duplicate check
        String checkSql =
                "SELECT department_id " +
                        "FROM departments " +
                        "WHERE LOWER(department_name) = LOWER(?)";

        try (
                Connection con = getConnection();
                PreparedStatement checkPst =
                        con.prepareStatement(checkSql)
        ) {

            checkPst.setString(1, name);

            ResultSet rs = checkPst.executeQuery();

            if (rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Department already exists!",
                        "Duplicate Department",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String insertSql =
                    "INSERT INTO departments " +
                            "(department_name, description, status) " +
                            "VALUES (?, ?, 'ACTIVE')";

            try (
                    PreparedStatement pst =
                            con.prepareStatement(insertSql)
            ) {

                pst.setString(1, name);
                pst.setString(2, description);

                pst.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Department added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();
                loadDepartments();
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to add department.\n\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // UPDATE DEPARTMENT
    // =====================================================

    private void updateDepartment() {

        if (selectedDepartmentId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a department from the table.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String name = txtName.getText().trim();
        String description = txtDescription.getText().trim();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Department name cannot be empty.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (name.length() > 100) {

            JOptionPane.showMessageDialog(
                    this,
                    "Department name cannot exceed 100 characters.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (description.length() > 255) {

            JOptionPane.showMessageDialog(
                    this,
                    "Description cannot exceed 255 characters.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Duplicate check excluding current department
        String checkSql =
                "SELECT department_id " +
                        "FROM departments " +
                        "WHERE LOWER(department_name) = LOWER(?) " +
                        "AND department_id <> ?";

        try (
                Connection con = getConnection();
                PreparedStatement checkPst =
                        con.prepareStatement(checkSql)
        ) {

            checkPst.setString(1, name);
            checkPst.setInt(2, selectedDepartmentId);

            ResultSet rs = checkPst.executeQuery();

            if (rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Another department with this name already exists!",
                        "Duplicate Department",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to update this department?",
                    "Confirm Update",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String updateSql =
                    "UPDATE departments " +
                            "SET department_name = ?, description = ? " +
                            "WHERE department_id = ?";

            try (
                    PreparedStatement pst =
                            con.prepareStatement(updateSql)
            ) {

                pst.setString(1, name);
                pst.setString(2, description);
                pst.setInt(3, selectedDepartmentId);

                pst.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Department updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();
                loadDepartments();
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update department.\n\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // DELETE DEPARTMENT
    // =====================================================

    private void deleteDepartment() {

        if (selectedDepartmentId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a department from the table.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String departmentName =
                txtName.getText().trim();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete\n\"" +
                        departmentName + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql =
                "DELETE FROM departments " +
                        "WHERE department_id = ?";

        try (
                Connection con = getConnection();
                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(1, selectedDepartmentId);

            int rows = pst.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Department deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();
                loadDepartments();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Department could not be deleted.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Department cannot be deleted because it may be " +
                            "being used by another record.\n\n" +
                            ex.getMessage(),
                    "Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // CLEAR
    // =====================================================

    private void clearFields() {

        txtName.setText("");
        txtDescription.setText("");

        selectedDepartmentId = -1;

        table.clearSelection();

        txtName.requestFocus();
    }

    // =====================================================
    // MAIN
    // =====================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                ManageDepartments::new
        );
    }
}