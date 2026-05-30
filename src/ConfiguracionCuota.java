package vistaverde.modelos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Clase principal que administra el estado del Condominio Vista Verde.
 * Implementa el patrón Singleton para que todas las pantallas compartan
 * los mismos datos durante la sesión.
 *
 * Contiene las 30 casas, el listado global de pagos y la cuota vigente.
 */
public class Condominio {

    // ── Singleton ──────────────────────────────────────────────────────────
    private static final Condominio INSTANCIA = new Condominio();

    /** Retorna la instancia única del condominio */
    public static Condominio getInstance() { return INSTANCIA; }

    // ── Constantes ─────────────────────────────────────────────────────────
    public static final int TOTAL_CASAS = 30;

    /** Meses del año en orden, usados para validar pagos a futuro */
    public static final String[] MESES = {
        "Enero","Febrero","Marzo","Abril","Mayo","Junio",
        "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"
    };

    // ── Atributos ──────────────────────────────────────────────────────────
    private final ArrayList<Casa> casas;
    private final ArrayList<Pago> pagos;
    private double cuotaMensual = 1500.0;

    // ── Constructor privado ────────────────────────────────────────────────
    private Condominio() {
        casas = new ArrayList<>();
        pagos = new ArrayList<>();
        for (int i = 1; i <= TOTAL_CASAS; i++) {
            casas.add(new Casa(i));
        }
    }

    // ── Cuota ──────────────────────────────────────────────────────────────

    /** Retorna la cuota mensual vigente */
    public double getCuotaMensual() { return cuotaMensual; }

    /** Actualiza la cuota mensual. Aplica a los pagos registrados de ahora en adelante */
    public void setCuotaMensual(double nueva) { this.cuotaMensual = nueva; }

    // ── Propietarios ───────────────────────────────────────────────────────

    /**
     * Registra un propietario en su casa correspondiente.
     * @param propietario  Propietario a registrar
     * @return true si se registró, false si la casa ya tenía propietario
     */
    public boolean agregarPropietario(Propietario propietario) {
        Casa casa = propietario.getCasa();
        if (casa.tienePropietario()) return false;
        casa.setPropietario(propietario);
        return true;
    }

    // ── Pagos ──────────────────────────────────────────────────────────────

    /**
     * Registra un pago. Antes de llamar este método se debe validar
     * que no exista duplicado ni meses anteriores pendientes.
     * @param pago  Pago a registrar
     */
    public void agregarPago(Pago pago) {
        pagos.add(pago);
        pago.getCasa().agregarPago(pago);
    }

    /**
     * Verifica si ya existe un pago para una casa, mes y año dados.
     */
    public boolean existePago(int numeroCasa, String mes, String anio) {
        Casa c = buscarCasa(numeroCasa);
        return c != null && c.yaPago(mes, anio);
    }

    /**
     * Verifica si la casa tiene meses anteriores sin pagar en el mismo año.
     * @param numeroCasa  Número de la casa
     * @param mes         Mes que se intenta pagar
     * @param anio        Año del pago
     */
    public boolean tienePendientesAnteriores(int numeroCasa, String mes, String anio) {
        Casa c = buscarCasa(numeroCasa);
        return c != null && c.tienePendientesAnteriores(mes, anio, MESES);
    }

    /** Retorna todos los pagos registrados en el sistema */
    public ArrayList<Pago> getPagos() { return pagos; }

    // ── Casas ──────────────────────────────────────────────────────────────

    /**
     * Busca una casa por su número.
     * @param numero  Número de casa (1-30)
     * @return Casa encontrada, o null si no existe
     */
    public Casa buscarCasa(int numero) {
        for (Casa c : casas) {
            if (c.getNumeroCasa() == numero) return c;
        }
        return null;
    }

    /** Retorna la lista completa de 30 casas */
    public ArrayList<Casa> getCasas() { return casas; }

    /**
     * Retorna las casas que NO han pagado el mes y año indicados.
     * @param mes   Mes a evaluar
     * @param anio  Año a evaluar
     */
    public ArrayList<Casa> getCasasMorosas(String mes, String anio) {
        ArrayList<Casa> morosas = new ArrayList<>();
        for (Casa c : casas) {
            if (!c.yaPago(mes, anio)) morosas.add(c);
        }
        return morosas;
    }

    /**
     * Calcula el total recaudado en el mes y año indicados.
     * @param mes   Mes a filtrar
     * @param anio  Año a filtrar
     */
    public double getTotalRecaudadoMes(String mes, String anio) {
        double total = 0;
        for (Pago p : pagos) {
            if (p.getMes().equalsIgnoreCase(mes) && p.getAnio().equals(anio)) {
                total += p.getMonto();
            }
        }
        return total;
    }

    // ── Utilidades de fecha ────────────────────────────────────────────────

    /** Retorna el nombre del mes actual en español (ej. "Mayo") */
    public String getMesActual() {
        return LocalDate.now().format(
            DateTimeFormatter.ofPattern("MMMM", new Locale("es","GT")));
    }

    /** Retorna el año actual como String (ej. "2026") */
    public String getAnioActual() {
        return String.valueOf(LocalDate.now().getYear());
    }
}
