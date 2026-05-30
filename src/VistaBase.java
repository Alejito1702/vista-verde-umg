package vistaverde.ui;

import vistaverde.modelos.*;
import javax.swing.*;
import java.awt.*;

public class RegistroPropietario extends VistaBase {

    private JTextField txtNombre, txtTelefono, txtCorreo;
    private JComboBox<String> cmbCasa;

    public RegistroPropietario(JFrame menuRef) {
        super("Registro de Propietario", menuRef);

        JPanel fondo = crearPanelFondo();
        fondo.setLayout(new BorderLayout());

        // ── Título ──
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        top.add(lblTitulo("👤  Registro de Propietario"));
        fondo.add(top, BorderLayout.NORTH);

        // ── Formulario ──
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(25, 40, 15, 40));

        txtNombre   = styledField();
        txtTelefono = styledField();
        txtCorreo   = styledField();

        String[] casas = new String[31];
        casas[0] = "Seleccione casa";
        for (int i = 1; i <= 30; i++) casas[i] = String.valueOf(i);
        cmbCasa = styledCombo(casas);

        form.add(filaForm("Nombre completo:", txtNombre));
        form.add(Box.createVerticalStrut(12));
        form.add(filaForm("Teléfono:", txtTelefono));
        form.add(Box.createVerticalStrut(12));
        form.add(filaForm("Correo electrónico:", txtCorreo));
        form.add(Box.createVerticalStrut(12));
        form.add(filaForm("Número de casa:", cmbCasa));
        fondo.add(form, BorderLayout.CENTER);

        // ── Botones ──
        JPanel bot = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bot.setOpaque(false);
        JButton btnReg = btnPrincipal("Registrar");
        btnReg.addActionListener(e -> registrar());
        bot.add(btnReg);
        bot.add(btnRegresar());
        fondo.add(bot, BorderLayout.SOUTH);

        setContentPane(fondo);
    }

    private void registrar() {
        String nombre   = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo   = txtCorreo.getText().trim();

        if (nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            mostrarError("Complete todos los campos."); return;
        }
        if (cmbCasa.getSelectedIndex() == 0) {
            mostrarError("Seleccione un número de casa."); return;
        }

        int numeroCasa = Integer.parseInt(cmbCasa.getSelectedItem().toString());
        Condominio cond = Condominio.getInstance();
        Casa casa = cond.buscarCasa(numeroCasa);

        if (casa.tienePropietario()) {
            mostrarError("La casa " + numeroCasa + " ya tiene propietario registrado."); return;
        }

        Propietario p = new Propietario(nombre, telefono, correo, casa);
        if (!p.validarCorreo()) {
            mostrarError("El correo ingresado no es válido."); return;
        }

        cond.agregarPropietario(p);
        mostrarOk("Propietario registrado exitosamente en la Casa " + numeroCasa + ".");
        txtNombre.setText(""); txtTelefono.setText(""); txtCorreo.setText("");
        cmbCasa.setSelectedIndex(0);
    }
}
