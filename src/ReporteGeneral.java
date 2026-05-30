package vistaverde.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LoginUI extends JFrame {

    private JTextField    txtUsuario;
    private JPasswordField txtContrasena;
    private JButton       btnIngresar;

    private static final String USUARIO_VALIDO    = "iusr_vistaverde";
    private static final String CONTRASENA_VALIDA = "R3sidencial2026%";

    public LoginUI() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Vista Verde - Login");
        setMinimumSize(new Dimension(600, 400));
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2)) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, new Color(10,30,15), 0, getHeight(), new Color(15,45,25)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // ── Panel izquierdo (formulario) ──
        JPanel panelIzq = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setPaint(new GradientPaint(0, 0, new Color(10,30,15), 0, getHeight(), new Color(15,45,25)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelIzq.setLayout(new GridBagLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);

        JLabel lblTitulo = label("Condominio Vista Verde", new Font("Segoe UI", Font.BOLD, 22), new Color(144,238,144));
        JLabel lblSub    = label("Ingresa tus credenciales para continuar", new Font("Segoe UI", Font.PLAIN, 12), new Color(100,180,120));
        JLabel lblUsr    = label("Usuario",     new Font("Segoe UI", Font.PLAIN, 12), new Color(100,180,120));
        JLabel lblPass   = label("Contraseña",  new Font("Segoe UI", Font.PLAIN, 12), new Color(100,180,120));

        txtUsuario = styledTextField();
        txtContrasena = styledPasswordField();

        btnIngresar = new JButton("Ingresar") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(34,139,60));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setContentAreaFilled(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnIngresar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnIngresar.addActionListener(e -> validarLogin());
        btnIngresar.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btnIngresar.setForeground(new Color(200,255,200)); btnIngresar.repaint(); }
            @Override public void mouseExited (MouseEvent e) { btnIngresar.setForeground(Color.WHITE);           btnIngresar.repaint(); }
        });

        txtContrasena.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) validarLogin(); }
        });

        formPanel.add(lblTitulo);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(lblSub);
        formPanel.add(Box.createVerticalStrut(40));
        formPanel.add(lblUsr);
        formPanel.add(Box.createVerticalStrut(6));
        formPanel.add(txtUsuario);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(lblPass);
        formPanel.add(Box.createVerticalStrut(6));
        formPanel.add(txtContrasena);
        formPanel.add(Box.createVerticalStrut(30));
        formPanel.add(btnIngresar);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20,40,20,40);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelIzq.add(formPanel, gbc);

        // ── Panel derecho (decorativo) ──
        JPanel panelDer = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth(), h = getHeight();
                g2d.setPaint(new GradientPaint(0,0,new Color(10,40,20),w,h,new Color(5,60,30)));
                g2d.fillRect(0,0,w,h);
                g2d.setPaint(new GradientPaint(0,0,new Color(34,180,80,180),w/2,h/2,new Color(20,120,50,100)));
                g2d.fillOval(-w/8,h/10,(int)(w*0.7),(int)(h*0.7));
                g2d.setPaint(new GradientPaint(w/3,h/3,new Color(80,200,80,150),w,h,new Color(40,160,60,80)));
                g2d.fillOval(w/4,(int)(h*0.3),(int)(w*0.75),(int)(h*0.6));
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 18));
                g2d.setColor(new Color(144,238,144,180));
                FontMetrics fm = g2d.getFontMetrics();
                String t1 = "Sistema de Administración", t2 = "Vista Verde";
                g2d.drawString(t1,(w-fm.stringWidth(t1))/2,h/2-15);
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                fm = g2d.getFontMetrics();
                g2d.setColor(new Color(100,200,120,160));
                g2d.drawString(t2,(w-fm.stringWidth(t2))/2,h/2+15);
            }
        };

        panelPrincipal.add(panelIzq);
        panelPrincipal.add(panelDer);
        setContentPane(panelPrincipal);
    }

    private void validarLogin() {
        String usuario   = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        if (usuario.equals(USUARIO_VALIDO) && contrasena.equals(CONTRASENA_VALIDA)) {
            new MenuPrincipal().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Credenciales incorrectas. Intente nuevamente.",
                "Error de acceso", JOptionPane.ERROR_MESSAGE);
            txtContrasena.setText("");
            txtUsuario.requestFocus();
        }
    }

    // ── Helpers de estilo ──
    private JLabel label(String text, Font font, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(font);
        l.setForeground(color);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JTextField styledTextField() {
        JTextField f = new JTextField();
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        f.setPreferredSize(new Dimension(280, 40));
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        f.setBackground(new Color(15,45,25));
        f.setForeground(Color.WHITE);
        f.setCaretColor(Color.WHITE);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0,0,1,0,new Color(50,180,80)),
            BorderFactory.createEmptyBorder(5,5,5,5)));
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
        return f;
    }

    private JPasswordField styledPasswordField() {
        JPasswordField f = new JPasswordField();
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        f.setPreferredSize(new Dimension(280, 40));
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        f.setBackground(new Color(15,45,25));
        f.setForeground(Color.WHITE);
        f.setCaretColor(Color.WHITE);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0,0,1,0,new Color(50,180,80)),
            BorderFactory.createEmptyBorder(5,5,5,5)));
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
        return f;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginUI().setVisible(true));
    }
}
