package vistaverde.ui;

import vistaverde.modelos.Condominio;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfiguracionCuota extends VistaBase {

    private JTextField txtCuotaActual, txtNuevaCuota, txtRecaudacion, txtFecha;

    public ConfiguracionCuota(JFrame menuRef) {
        super("Configuración de Cuota", menuRef);

        JPanel fondo = crearPanelFondo();
        fondo.setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        top.add(lblTitulo("⚙️  Configuración de Cuota"));
        fondo.add(top, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(25, 40, 10, 40));

        Condominio cond = Condominio.getInstance();

        txtCuotaActual = styledField();
        txtCuotaActual.setEditable(false);
        txtCuotaActual.setText(String.format("Q%.2f", cond.getCuotaMensual()));

        txtRecaudacion = styledField();
        txtRecaudacion.setEditable(false);
        txtRecaudacion.setText(String.format("Q%.2f", cond.getCuotaMensual() * 30));

        txtFecha = styledField();
        txtFecha.setEditable(false);
        txtFecha.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        txtNuevaCuota = styledField();
        txtNuevaCuota.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != java.awt.event.KeyEvent.VK_BACK_SPACE)
                    evt.consume();
                if (c == '.' && txtNuevaCuota.getText().contains("."))
                    evt.consume();
            }
        });

        form.add(filaForm("Cuota actual:",         txtCuotaActual));
        form.add(Box.createVerticalStrut(12));
        form.add(filaForm("Nueva cuota (Q):",      txtNuevaCuota));
        form.add(Box.createVerticalStrut(12));
        form.add(filaForm("Recaudación esperada:", txtRecaudacion));
        form.add(Box.createVerticalStrut(12));
        form.add(filaForm("Fecha:",                txtFecha));
        fondo.add(form, BorderLayout.CENTER);

        JPanel bot = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bot.setOpaque(false);
        JButton btnAct = btnPrincipal("Actualizar Cuota");
        btnAct.addActionListener(e -> actualizar());
        bot.add(btnAct);
        bot.add(btnRegresar());
        fondo.add(bot, BorderLayout.SOUTH);

        setContentPane(fondo);
    }

    private void actualizar() {
        String texto = txtNuevaCuota.getText().trim();
        if (texto.isEmpty()) { mostrarError("Ingrese el nuevo monto de cuota."); return; }
        double nueva;
        try { nueva = Double.parseDouble(texto); }
        catch (NumberFormatException ex) { mostrarError("Ingrese un monto numérico válido."); return; }
        if (nueva <= 0) { mostrarError("La cuota debe ser mayor a cero."); return; }

        Condominio.getInstance().setCuotaMensual(nueva);
        txtCuotaActual.setText(String.format("Q%.2f", nueva));
        txtRecaudacion.setText(String.format("Q%.2f", nueva * 30));
        txtNuevaCuota.setText("");
        mostrarOk("¡Cuota actualizada a Q" + String.format("%.2f", nueva) + "!\n" +
                  "Recaudación mensual esperada: Q" + String.format("%.2f", nueva * 30));
    }
}
