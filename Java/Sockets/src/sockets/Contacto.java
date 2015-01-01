package sockets;

/**
 *
 * @author javinavarroalaman
 */
public class Contacto {
    private String nombre;
    private String telefono;

    public Contacto(String n, String t) {
        this.nombre = n;
        this.telefono= t;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String toString() {
        return "El numero de telefono de "+this.nombre+" es "+this.telefono;
    }

    public boolean equals(Object obj) {
        String n;
        if (obj instanceof Contacto){
            Contacto cont = (Contacto)obj;
            n = cont.getNombre();
        }else{
            n = "";
        }
        return this.nombre.equals(n);
    }
}
