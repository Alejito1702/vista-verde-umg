package vistaverde.ui;

import vistaverde.modelos.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Pantalla 8 — Casas Morosas.
 * Lista únicamente las casas que NO han pagado el mes actual.
 * Muestra: número de casa, nombre del propietario y teléfono de contacto.
 */
public class CasasMorosas extends VistaBase {

    public CasasMorosas(JFrame menuRef) {
        super("Casas Morosas", menuRef);
        setSize(520, 480);

        Condominio cond       = Condominio.getInstance();
        String     mesActual  = cond.getMesActual();
        mesActual = mesActual.substring(0,1).toUpperCase() + mesActual.substring(1);
        String     anioActual = cond.getAnioActual();

        JPanel fondo = crearPanelFondo();
        fondo.setLayout(new BorderLayout(10, 10));

        // ── Título ──
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.setOpaque(false);
        top.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));
        top.add(lblTitulo("🏠  Morosas — " + mesActual + " " + anioActual));
        fondo.add(top, BorderLayout.NORTH);

        // ── Tabla ──
        String[] cols = {"No. Casa", "Propietario", "Teléfono", "Correo"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        ArrayList<Casa> morosas = cond.getCasasMorosas(mesActual, anioActual);
        for (Casa c : morosas) {
            String prop = c.tienePropietario() ? c.getPropietario().getNombreCompleto() : "(sin propietario)";
            String tel  = c.tienePropietario() ? c.getPropietario().getTelefono()       : "—";
            String mail = c.tienePropietario() ? c.getPropietario().getCorreo()         : "—";
            model.addRow(new Object[]{c.getNumeroCasa(), prop, tel, mail});
        }

        JTable tabla = new JTable(model);
        tabla.setBackground(new Color(55,10,10));
        tabla.setForeground(new Color(255,170,170));
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.setGridColor(new Color(110,40,40));
        tabla.setRowHeight(24);
        tabla.getTableHeader().setBackground(new Color(80,15,15));
        tabla.getTableHeader().setForeground(new Color(255,200,200));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(new Color(55,10,10));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(180,40,40)));

        // ── Resumen ──
        double montoEsperado  = morosas.size() * cond.getCuotaMensual();
        double totalRecaudado = cond.getTotalRecaudadoMes(mesActual, anioActual);
        int    casasPagaron   = Condominio.TOTAL_CASAS - morosas.size();

        JTextArea pie = new JTextArea();
        pie.setEditable(false);
        pie.setBackground(new Color(80,15,15));
        pie.setForeground(new Color(255,200,200));
        pie.setFont(new Font("Segoe UI", Font.BOLD, 12));
        pie.setBorder(BorderFactory.createEmptyBorder(8,14,8,14));
        pie.setText(
            "Casas morosas en " + mesActual + " " + anioActual
            + ": " + morosas.size() + " de " + Condominio.TOTAL_CASAS
            + "   |   Casas al día: " + casasPagaron
            + "\nMonto pendiente: Q" + String.format("%.2f", montoEsperado)
            + "   |   Ya recaudado este mes: Q" + String.format("%.2f", totalRecaudado));

        JPanel centro = new JPanel(new BorderLayout(0, 6));
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
        centro.add(scroll, BorderLayout.CENTER);
        centro.add(pie,    BorderLayout.SOUTH);
        fondo.add(centro, BorderLayout.CENTER);

        JPanel bot = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 8));
        bot.setOpaque(false);
        bot.add(btnRegresar());
        fondo.add(bot, BorderLayout.SOUTH);

        setContentPane(fondo);
    }
}
