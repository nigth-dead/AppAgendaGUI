package appagendagui;

import java.io.Serializable;

public class Contacto implements Serializable {

    private String nombre;
    private int numero;
    private String email;
    private String direccion;

    public Contacto(String nombre, int numero, String email, String direccion) {
        this.nombre = nombre;
        this.numero = numero;
        this.email = email;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return nombre + " - " + numero + " - " + email + " - " + direccion;
    }
}
