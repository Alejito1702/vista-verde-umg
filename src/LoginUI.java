package vistaverde.modelos;

/**
 * Representa al propietario de una casa en el Condominio Vista Verde.
 * Un propietario está asociado a una sola casa.
 */
public class Propietario {

    private String nombreCompleto;
    private String telefono;
    private String correo;
    private Casa   casa;

    /**
     * Constructor del propietario.
     * @param nombreCompleto  Nombre completo del propietario
     * @param telefono        Número de teléfono de contacto
     * @param correo          Correo electrónico
     * @param casa            Casa asignada al propietario
     */
    public Propietario(String nombreCompleto, String telefono, String correo, Casa casa) {
        this.nombreCompleto = nombreCompleto;
        this.telefono       = telefono;
        this.correo         = correo;
        this.casa           = casa;
    }

    public String getNombreCompleto() { return nombreCompleto; }
    public String getTelefono()       { return telefono;       }
    public String getCorreo()         { return correo;         }
    public Casa   getCasa()           { return casa;           }

    /**
     * Valida que el correo tenga formato básico (contiene @ y punto).
     * @return true si el correo es válido
     */
    public boolean validarCorreo() {
        return correo != null && correo.contains("@") && correo.contains(".");
    }
}
