package vistaverde.modelos;

/**
 * Representa un pago de cuota mensual realizado por una casa.
 * Almacena el mes, año, monto y referencia a la casa que realizó el pago.
 */
public class Pago {

    private double monto;
    private String mes;
    private String anio;
    private Casa   casa;

    /**
     * Constructor del pago.
     * @param monto  Monto pagado (cuota vigente al momento del registro)
     * @param mes    Nombre del mes (ej. "Enero")
     * @param anio   Año en formato String (ej. "2026")
     * @param casa   Casa que realizó el pago
     */
    public Pago(double monto, String mes, String anio, Casa casa) {
        this.monto = monto;
        this.mes   = mes;
        this.anio  = anio;
        this.casa  = casa;
    }

    public double getMonto() { return monto; }
    public String getMes()   { return mes;   }
    public String getAnio()  { return anio;  }
    public Casa   getCasa()  { return casa;  }

    /** Retorna la fecha formateada como "Mes Año" */
    public String getFecha() { return mes + " " + anio; }
}
