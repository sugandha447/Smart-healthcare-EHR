package login;

import database.DBConnection;
import dashboard.AdminDashboard;
import dashboard.DoctorDashboard;
import dashboard.ReceptionistDashboard;
import dashboard.PatientDashboard;
import dashboard.LaboratoryDashboard;
import security.PasswordUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    private final Color BLUE = new Color(25, 137, 210);
    private final Color DARK_BLUE = new Color(10, 65, 115);
    private final Color TEXT_BLUE = new Color(24, 68, 108);
    private final Color PLACEHOLDER = new Color(115, 145, 175);
    private final Color BORDER = new Color(174, 211, 239);

    private JTextField txtEmployeeId;
    private JPasswordField txtPassword;
    private RoundedButton btnLogin;
    private RoundedButton btnClear;
    private JCheckBox rememberMe;
    private boolean passwordVisible = false;

    public Login() {
        setTitle("EHR Login");
        setSize(1200, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(1, 2));

        // ================= LEFT SIDE =================
        HospitalPanel leftPanel = new HospitalPanel();
        leftPanel.setLayout(null);
        add(leftPanel);

        JLabel heartIcon = new JLabel("♥");
        heartIcon.setBounds(48, 35, 105, 70);
        heartIcon.setFont(new Font("Arial", Font.BOLD, 65));
        heartIcon.setForeground(new Color(15, 112, 180));
        leftPanel.add(heartIcon);

        JPanel verticalLine = new JPanel();
        verticalLine.setBackground(new Color(20, 95, 145));
        verticalLine.setBounds(158, 48, 3, 92);
        leftPanel.add(verticalLine);

        JLabel title = new JLabel("SMART HEALTHCARE");
        title.setBounds(188, 45, 410, 42);
        title.setFont(new Font("Arial", Font.BOLD, 29));
        title.setForeground(new Color(10, 65, 115));
        leftPanel.add(title);

        JLabel subtitle = new JLabel("Electronic Health Record Management System");
        subtitle.setBounds(188, 88, 430, 30);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitle.setForeground(new Color(20, 80, 135));
        leftPanel.add(subtitle);

        JLabel tagline = new JLabel(
                "Better Records   •   Better Care   •   Healthier Tomorrow");
        tagline.setBounds(135, 145, 500, 30);
        tagline.setFont(new Font("Arial", Font.ITALIC, 16));
        tagline.setForeground(new Color(15, 88, 145));
        leftPanel.add(tagline);

        leftPanel.add(new FeaturePanel());

        // ================= RIGHT SIDE =================
        GradientPanel rightPanel = new GradientPanel();
        rightPanel.setLayout(null);
        add(rightPanel);

        LoginCard card = new LoginCard();
        card.setLayout(null);
        card.setBounds(45, 25, 510, 650);
        rightPanel.add(card);

        HospitalIcon hospitalIcon = new HospitalIcon();
        hospitalIcon.setBounds(215, 18, 80, 72);
        card.add(hospitalIcon);

        JLabel cardTitle = new JLabel("SMART HEALTHCARE EHR");
        cardTitle.setBounds(35, 90, 440, 36);
        cardTitle.setHorizontalAlignment(SwingConstants.CENTER);
        cardTitle.setFont(new Font("Arial", Font.BOLD, 25));
        cardTitle.setForeground(new Color(10, 52, 90));
        card.add(cardTitle);

        JLabel welcome = new JLabel("Welcome Back!");
        welcome.setBounds(35, 130, 440, 34);
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 23));
        welcome.setForeground(new Color(17, 62, 105));
        card.add(welcome);

        JLabel loginSubtitle = new JLabel("Please login to continue");
        loginSubtitle.setBounds(35, 164, 440, 25);
        loginSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        loginSubtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        loginSubtitle.setForeground(new Color(85, 120, 150));
        card.add(loginSubtitle);

        HeartbeatPanel heartbeat = new HeartbeatPanel();
        heartbeat.setBounds(50, 195, 410, 30);
        card.add(heartbeat);

        RoundedTextField userField = new RoundedTextField();
        userField.setBounds(55, 245, 400, 55);
        userField.setFont(new Font("Arial", Font.PLAIN, 15));
        userField.setBorder(new RoundedBorder(BORDER, 16));
        userField.setBackground(new Color(248, 252, 255));
        userField.setIcon(new UserIcon());
        userField.setPlaceholder("Employee ID / Username");
        txtEmployeeId = userField;
        card.add(userField);

        RoundedPasswordField passwordField = new RoundedPasswordField();
        passwordField.setBounds(55, 315, 400, 55);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordField.setBorder(new RoundedBorder(BORDER, 16));
        passwordField.setBackground(new Color(248, 252, 255));
        passwordField.setIcon(new LockIcon());
        passwordField.setPlaceholder("Password");
        txtPassword = passwordField;
        card.add(passwordField);

        EyeButton eyeButton = new EyeButton();
        eyeButton.setBounds(405, 327, 38, 30);
        eyeButton.addActionListener(e -> togglePassword());
        card.add(eyeButton);

        rememberMe = new JCheckBox("Remember Me");
        rememberMe.setBounds(55, 385, 170, 30);
        rememberMe.setFont(new Font("Arial", Font.PLAIN, 14));
        rememberMe.setForeground(TEXT_BLUE);
        rememberMe.setFocusPainted(false);
        rememberMe.setOpaque(false);
        rememberMe.setIcon(new CheckBoxIcon(false));
        rememberMe.setSelectedIcon(new CheckBoxIcon(true));
        card.add(rememberMe);

        JLabel forgot = new JLabel("Forgot Password?");
        forgot.setBounds(315, 385, 140, 30);
        forgot.setHorizontalAlignment(SwingConstants.RIGHT);
        forgot.setFont(new Font("Arial", Font.BOLD, 13));
        forgot.setForeground(new Color(15, 115, 195));
        forgot.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgot.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(
                        Login.this,
                        "Please contact the administrator to reset your password.",
                        "Forgot Password",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        card.add(forgot);

        btnLogin = new RoundedButton("Login", BLUE);
        btnLogin.setBounds(55, 435, 400, 55);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 19));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setIcon(new LoginArrowIcon());
        btnLogin.addActionListener(this);
        card.add(btnLogin);

        btnClear = new RoundedButton("Clear", Color.WHITE);
        btnClear.setBounds(55, 505, 400, 50);
        btnClear.setFont(new Font("Arial", Font.BOLD, 17));
        btnClear.setForeground(new Color(15, 105, 175));
        btnClear.setBorderColor(new Color(85, 180, 235));
        btnClear.setIcon(new RefreshIcon());
        btnClear.addActionListener(this);
        card.add(btnClear);

        JLabel bottomHeart = new JLabel("♥");
        bottomHeart.setBounds(230, 560, 50, 25);
        bottomHeart.setHorizontalAlignment(SwingConstants.CENTER);
        bottomHeart.setFont(new Font("Arial", Font.BOLD, 22));
        bottomHeart.setForeground(BLUE);
        card.add(bottomHeart);

        JLabel bottomText = new JLabel("Smart Healthcare EHR Management System");
        bottomText.setBounds(35, 585, 440, 22);
        bottomText.setHorizontalAlignment(SwingConstants.CENTER);
        bottomText.setFont(new Font("Arial", Font.PLAIN, 12));
        bottomText.setForeground(new Color(45, 90, 130));
        card.add(bottomText);

        JLabel priorityText = new JLabel("Your Health  •  Our Priority");
        priorityText.setBounds(35, 607, 440, 20);
        priorityText.setHorizontalAlignment(SwingConstants.CENTER);
        priorityText.setFont(new Font("Arial", Font.ITALIC, 11));
        priorityText.setForeground(new Color(100, 140, 175));
        card.add(priorityText);

        txtPassword.addActionListener(e -> btnLogin.doClick());

        setVisible(true);
    }

    private void togglePassword() {
        passwordVisible = !passwordVisible;
        txtPassword.setEchoChar(passwordVisible ? (char) 0 : '•');
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnClear) {
            txtEmployeeId.setText("");
            txtPassword.setText("");
            txtEmployeeId.requestFocus();
            return;
        }

        if (e.getSource() != btnLogin) return;

        String loginId = txtEmployeeId.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (loginId.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Employee ID / Username and Password.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();

            String query =
                    "SELECT * FROM users " +
                    "WHERE (employee_id=? OR username=?) " +
                    "AND status='ACTIVE'";

            ps = con.prepareStatement(query);
            ps.setString(1, loginId);
            ps.setString(2, loginId);
            rs = ps.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Employee ID / Username or Password\n" +
                        "or your account is inactive.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String storedPassword = rs.getString("password");
            boolean passwordValid;

            if (PasswordUtil.isHashed(storedPassword)) {
                passwordValid = PasswordUtil.verifyPassword(
                        password, storedPassword);
            } else {
                passwordValid = password.equals(storedPassword);

                if (passwordValid) {
                    String newHash = PasswordUtil.hashPassword(password);

                    try (PreparedStatement updatePassword =
                                 con.prepareStatement(
                                         "UPDATE users SET password=? WHERE user_id=?")) {
                        updatePassword.setString(1, newHash);
                        updatePassword.setInt(2, rs.getInt("user_id"));
                        updatePassword.executeUpdate();
                    }
                }
            }

            if (!passwordValid) {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Employee ID / Username or Password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String role = rs.getString("role");
            String fullName = rs.getString("full_name");
            int patientId = rs.getInt("patient_id");
            String employeeId = rs.getString("employee_id");

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful!\nWelcome " + fullName,
                    "Login Successful",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

            if (role.equalsIgnoreCase("Admin")) {
                new AdminDashboard();
            } else if (role.equalsIgnoreCase("Doctor")) {
                new DoctorDashboard(employeeId);
            } else if (role.equalsIgnoreCase("Receptionist")) {
                new ReceptionistDashboard();
            } else if (role.equalsIgnoreCase("Laboratory")) {
                new LaboratoryDashboard(employeeId);
            } else if (role.equalsIgnoreCase("Patient")) {
                new PatientDashboard(patientId);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid user role.",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE);
                new Login();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Database connection error.\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // ================= ROUNDED BUTTON =================
    class RoundedButton extends JButton {
        private final Color buttonColor;
        private Color borderColor = new Color(85, 180, 235);

        RoundedButton(String text, Color color) {
            super(text);
            buttonColor = color;
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setHorizontalAlignment(SwingConstants.CENTER);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        void setBorderColor(Color color) {
            borderColor = color;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            if (buttonColor.equals(BLUE)) {
                g2.setPaint(new GradientPaint(
                        0, 0, new Color(52, 170, 225),
                        getWidth(), 0, new Color(12, 105, 185)));
            } else {
                g2.setColor(buttonColor);
            }

            g2.fillRoundRect(
                    0, 0, getWidth(), getHeight(), 32, 32);

            if (!buttonColor.equals(BLUE)) {
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(
                        1, 1, getWidth() - 3,
                        getHeight() - 3, 32, 32);
            }

            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(getText());
            int iconWidth = getIcon() == null
                    ? 0 : getIcon().getIconWidth() + 10;
            int total = textWidth + iconWidth;
            int x = (getWidth() - total) / 2;

            if (getIcon() != null) {
                getIcon().paintIcon(
                        this, g2, x,
                        (getHeight() - getIcon().getIconHeight()) / 2);
                x += iconWidth;
            }

            g2.setColor(getForeground());
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(getText(), x, y);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {}
    }

    // ================= LOGIN CARD =================
    class LoginCard extends JPanel {
        LoginCard() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(70, 130, 170, 35));
            g2.fillRoundRect(
                    5, 7, getWidth() - 10,
                    getHeight() - 10, 32, 32);

            g2.setColor(new Color(255, 255, 255, 238));
            g2.fillRoundRect(
                    0, 0, getWidth() - 8,
                    getHeight() - 8, 32, 32);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ================= HOSPITAL BACKGROUND =================
    class HospitalPanel extends JPanel {
        private Image image;

        HospitalPanel() {
            URL url = getClass().getResource("/hospital_bg.png");
            if (url != null) {
                image = new ImageIcon(url).getImage();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (image == null) {
                g.setColor(new Color(220, 240, 250));
                g.fillRect(0, 0, getWidth(), getHeight());
                return;
            }

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            double imageRatio =
                    (double) image.getWidth(null) / image.getHeight(null);
            double panelRatio =
                    (double) getWidth() / getHeight();

            int w, h, x, y;

            if (imageRatio > panelRatio) {
                h = getHeight();
                w = (int) (h * imageRatio);
                x = (getWidth() - w) / 2;
                y = 0;
            } else {
                w = getWidth();
                h = (int) (w / imageRatio);
                x = 0;
                y = (getHeight() - h) / 2;
            }

            g2.drawImage(image, x, y, w, h, this);
            g2.dispose();
        }
    }

    // ================= FEATURES =================
    class FeaturePanel extends JPanel {
        FeaturePanel() {
            setOpaque(false);
            setBounds(0, 535, 600, 185);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(5, 66, 112, 225));
            g2.fillOval(-130, 35, 850, 280);

            drawFeature(g2, 80, "✓", "Secure", "Data");
            drawFeature(g2, 225, "♧", "Better", "Patient Care");
            drawFeature(g2, 370, "▣", "Easy Record", "Management");
            drawFeature(g2, 515, "♥", "Healthier", "Community");

            g2.dispose();
        }

        private void drawFeature(
                Graphics2D g2, int x, String icon,
                String line1, String line2) {

            g2.setColor(new Color(45, 170, 230));
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(x - 29, 60, 58, 58);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 25));
            FontMetrics fm = g2.getFontMetrics();

            g2.drawString(
                    icon,
                    x - fm.stringWidth(icon) / 2,
                    98);

            g2.setFont(new Font("Arial", Font.BOLD, 13));

            g2.drawString(
                    line1,
                    x - g2.getFontMetrics().stringWidth(line1) / 2,
                    143);

            g2.drawString(
                    line2,
                    x - g2.getFontMetrics().stringWidth(line2) / 2,
                    163);
        }
    }

    // ================= RIGHT BACKGROUND =================
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setPaint(new GradientPaint(
                    0, 0, new Color(239, 249, 255),
                    getWidth(), getHeight(),
                    new Color(220, 242, 255)));

            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(190, 220, 240, 80));
            g2.fillRoundRect(470, 45, 32, 82, 9, 9);
            g2.fillRoundRect(445, 70, 82, 32, 9, 9);

            g2.dispose();
        }
    }

    // ================= TEXT FIELD =================
    class RoundedTextField extends JTextField {
        private Icon icon;
        private String placeholder;

        void setIcon(Icon icon) {
            this.icon = icon;
            repaint();
        }

        void setPlaceholder(String text) {
            placeholder = text;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            if (icon != null) {
                icon.paintIcon(
                        this, g2, 18,
                        (getHeight() - icon.getIconHeight()) / 2);
            }

            if (getText().isEmpty() && placeholder != null) {
                g2.setColor(PLACEHOLDER);
                g2.setFont(getFont());
                g2.drawString(placeholder, 62, 35);
            }

            g2.dispose();
        }

        @Override
        public Insets getInsets() {
            return new Insets(5, 62, 5, 15);
        }
    }

    // ================= PASSWORD FIELD =================
    class RoundedPasswordField extends JPasswordField {
        private Icon icon;
        private String placeholder;

        void setIcon(Icon icon) {
            this.icon = icon;
            repaint();
        }

        void setPlaceholder(String text) {
            placeholder = text;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            if (icon != null) {
                icon.paintIcon(
                        this, g2, 18,
                        (getHeight() - icon.getIconHeight()) / 2);
            }

            if (getPassword().length == 0 && placeholder != null) {
                g2.setColor(PLACEHOLDER);
                g2.setFont(getFont());
                g2.drawString(placeholder, 62, 35);
            }

            g2.dispose();
        }

        @Override
        public Insets getInsets() {
            return new Insets(5, 62, 5, 55);
        }
    }

    // ================= BORDER =================
    class RoundedBorder implements Border {
        private final Color color;
        private final int radius;

        RoundedBorder(Color color, int radius) {
            this.color = color;
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(5, 15, 5, 15);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(
                Component c, Graphics g,
                int x, int y, int width, int height) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.5f));

            g2.drawRoundRect(
                    x + 1, y + 1,
                    width - 3, height - 3,
                    radius, radius);

            g2.dispose();
        }
    }

    // ================= HOSPITAL ICON =================
    class HospitalIcon extends JPanel {
        HospitalIcon() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(38, 165, 220));
            g2.fillRoundRect(24, 18, 35, 45, 5, 5);
            g2.fillRoundRect(18, 13, 47, 10, 4, 4);

            g2.setColor(Color.WHITE);
            g2.fillRect(36, 23, 10, 28);
            g2.fillRect(27, 32, 28, 10);

            g2.setColor(new Color(35, 185, 180));
            g2.fillRect(7, 30, 13, 33);
            g2.fillRect(63, 27, 13, 36);
            g2.fillRoundRect(4, 62, 72, 7, 4, 4);

            g2.dispose();
        }
    }

    // ================= USER ICON =================
    class UserIcon implements Icon {
        public int getIconWidth() { return 28; }
        public int getIconHeight() { return 28; }

        public void paintIcon(
                Component c, Graphics g, int x, int y) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(18, 76, 125));
            g2.setStroke(new BasicStroke(2.3f));

            g2.drawOval(x + 8, y + 2, 11, 11);
            g2.drawArc(x + 4, y + 13, 20, 14, 0, 180);

            g2.dispose();
        }
    }

    // ================= LOCK ICON =================
    class LockIcon implements Icon {
        public int getIconWidth() { return 28; }
        public int getIconHeight() { return 28; }

        public void paintIcon(
                Component c, Graphics g, int x, int y) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(18, 76, 125));
            g2.setStroke(new BasicStroke(2.3f));

            g2.drawRoundRect(x + 5, y + 12, 18, 13, 3, 3);
            g2.drawArc(x + 8, y + 3, 12, 17, 0, 180);
            g2.fillOval(x + 13, y + 17, 3, 5);

            g2.dispose();
        }
    }

    // ================= EYE BUTTON =================
    class EyeButton extends JButton {
        EyeButton() {
            setBorderPainted(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(100, 135, 165));
            g2.setStroke(new BasicStroke(2));

            g2.drawOval(5, 7, 25, 15);
            g2.fillOval(14, 12, 7, 7);

            if (!passwordVisible) {
                g2.drawLine(5, 25, 29, 4);
            }

            g2.dispose();
        }
    }

    // ================= CHECKBOX =================
    class CheckBoxIcon implements Icon {
        private final boolean selected;

        CheckBoxIcon(boolean selected) {
            this.selected = selected;
        }

        public int getIconWidth() { return 25; }
        public int getIconHeight() { return 25; }

        public void paintIcon(
                Component c, Graphics g, int x, int y) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            if (selected) {
                g2.setColor(new Color(30, 135, 210));
                g2.fillRoundRect(x + 2, y + 2, 21, 21, 4, 4);

                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2.5f));
                g2.drawLine(x + 7, y + 13, x + 11, y + 17);
                g2.drawLine(x + 11, y + 17, x + 19, y + 8);
            } else {
                g2.setColor(new Color(150, 180, 205));
                g2.drawRoundRect(x + 2, y + 2, 20, 20, 4, 4);
            }

            g2.dispose();
        }
    }

    // ================= LOGIN ICON =================
    class LoginArrowIcon implements Icon {
        public int getIconWidth() { return 28; }
        public int getIconHeight() { return 28; }

        public void paintIcon(
                Component c, Graphics g, int x, int y) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2.4f));

            g2.drawLine(x + 2, y + 14, x + 22, y + 14);
            g2.drawLine(x + 15, y + 7, x + 22, y + 14);
            g2.drawLine(x + 15, y + 21, x + 22, y + 14);
            g2.drawRoundRect(x + 6, y + 4, 17, 20, 3, 3);

            g2.dispose();
        }
    }

    // ================= REFRESH ICON =================
    class RefreshIcon implements Icon {
        public int getIconWidth() { return 25; }
        public int getIconHeight() { return 25; }

        public void paintIcon(
                Component c, Graphics g, int x, int y) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(20, 120, 185));
            g2.setStroke(new BasicStroke(2.3f));

            g2.drawArc(x + 2, y + 2, 19, 19, 45, 270);
            g2.drawLine(x + 17, y + 4, x + 23, y + 6);
            g2.drawLine(x + 17, y + 4, x + 18, y + 10);

            g2.dispose();
        }
    }

    // ================= HEARTBEAT =================
    class HeartbeatPanel extends JPanel {
        HeartbeatPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(185, 220, 238));
            g2.setStroke(new BasicStroke(1.5f));

            g2.drawLine(0, 15, 145, 15);
            g2.drawLine(265, 15, 410, 15);

            g2.setColor(new Color(25, 130, 205));
            g2.setStroke(new BasicStroke(2.5f));

            int[] xs = {180, 195, 203, 211, 220, 230, 240, 250};
            int[] ys = {15, 15, 7, 25, 15, 15, 15, 15};

            for (int i = 0; i < xs.length - 1; i++) {
                g2.drawLine(xs[i], ys[i], xs[i + 1], ys[i + 1]);
            }

            g2.dispose();
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
