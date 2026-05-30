package vistaverde.modelos;

import java.util.ArrayList;

/**
 * Representa una casa del Condominio Vista Verde.
 * Cada casa tiene un número único (1-30), un propietario opcional
 * y un historial de pagos de cuota mensual.
 */
public class Casa {

    private int              numeroCasa;
    private Propietario      propietario;
    private ArrayList<Pago>  pagos;

    /**
     * Constructor de la casa.
     * @param numeroCasa  Número de la casa (1 al 30)
     */
    public Casa(int numeroCasa) {
        this.numeroCasa = numeroCasa;
        this.pagos      = new ArrayList<>();
    }

    public int          getNumeroCasa()  { return numeroCasa;  }
    public Propietario  getPropietario() { return propietario; }
    public ArrayList<Pago> getPagos()   { return pagos;       }

    public boolean tienePropietario() { return propietario != null; }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    /** Agrega un pago al historial de esta casa */
    public void agregarPago(Pago pago) {
        pagos.add(pago);
    }

    /**
     * Verifica si ya existe un pago para el mes y año indicados.
     * @param mes   Nombre del mes
     * @param anio  Año como String
     */
    public boolean yaPago(String mes, String anio) {
        for (Pago p : pagos) {
            if (p.getMes().equalsIgnoreCase(mes) && p.getAnio().equals(anio)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si la casa tiene meses anteriores pendientes antes del mes indicado.
     * Se compara con el orden real del año basado en el array de meses.
     * Solo valida dentro del mismo año.
     * @param mes       Mes que se intenta pagar
     * @param anio      Año del pago
     * @param meses     Array de meses en orden (Enero=0 ... Diciembre=11)
     */
    public boolean tienePendientesAnteriores(String mes, String anio, String[] meses) {
        int indiceMesPagar = -1;
        for (int i = 0; i < meses.length; i++) {
            if (meses[i].equalsIgnoreCase(mes)) { indiceMesPagar = i; break; }
        }
        if (indiceMesPagar <= 0) return false;

        for (int i = 0; i < indiceMesPagar; i++) {
            if (!yaPago(meses[i], anio)) return true;
        }
        return false;
    }

    /**
     * Calcula el total pagado por esta casa (todos los periodos).
     */
    public double getTotalPagado() {
        double total = 0;
        for (Pago p : pagos) total += p.getMonto();
        return total;
    }

    /**
     * Calcula el total pagado en un año específico.
     * @param anio  Año a filtrar
     */
    public double getTotalPagadoAnio(String anio) {
        double total = 0;
        for (Pago p : pagos) {
            if (p.getAnio().equals(anio)) total += p.getMonto();
        }
        return total;
    }
}
