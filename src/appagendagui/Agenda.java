package appagendagui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

public final class Agenda {

    ArrayList<Contacto> contactos = new ArrayList<>();

    public Agenda() {
        contactos = new ArrayList<>();
        leerArchivo();
    }

    public void leerArchivo() {
        File archivo = new File("Contactos.json");
        if (archivo.exists()) {
            try (Reader reader = new FileReader(archivo)) {
                Gson gson = new Gson();
                Contacto[] lista = gson.fromJson(reader, Contacto[].class);
                contactos = new ArrayList<>(Arrays.asList(lista));
            } catch (Exception e) {
                System.out.println("Error al leer contactos: " + e.getMessage());
            }
        }
    }

    public boolean compararNumero(String numero, Contacto contactoInicial) {
        for (Contacto c : contactos) {
            if (c.getNumero().equals(numero)) {
                if (c != contactoInicial) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean compararNomb(String nombre) {
        for (int i = 0; i < contactos.size(); i++) {
            if (contactos.get(i).getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    public void agregarContacto(String nombre, String apellidoPaterno,
            String apellidoMaterno, String numero, String email,
            Date fechaDeNacimiento) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Contacto l = new Contacto(nombre, apellidoPaterno, apellidoMaterno,
                numero, email, fechaDeNacimiento);
        contactos.add(l);

        // Ordenar por nombre (ignorar mayúsculas/minúsculas)
        Collections.sort(contactos, (Contacto c1, Contacto c2)
                -> c1.getNombre().compareToIgnoreCase(c2.getNombre()));

        // Guardar en JSON
        try (FileWriter writer = new FileWriter("Contactos.json")) {
            gson.toJson(contactos, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar contactos: " + e.getMessage());
        }
    }

    public void eliminarContacto(Contacto contacto) {
        if (contactos.remove(contacto)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try (FileWriter writer = new FileWriter("Contactos.json")) {
                gson.toJson(contactos, writer);
            } catch (IOException e) {
                System.out.println("Error al guardar contactos: " + e.getMessage());
            }
        }
    }

    public ArrayList<Contacto> buscarContactoPorNombre(String nombre) {
        ArrayList<Contacto> resultado = new ArrayList<>();
        for (Contacto c : contactos) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public ArrayList<Contacto> buscarContactoPorNumero(String numero) {
        ArrayList<Contacto> resultado = new ArrayList<>();
        for (Contacto c : contactos) {
            if (c.getNumero().equals(numero)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public ArrayList<Contacto> buscarContactoPorApellidoPaterno(String apellidoPaterno) {
        ArrayList<Contacto> resultado = new ArrayList<>();
        for (Contacto c : contactos) {
            if (c.getApellidoPaterno().equalsIgnoreCase(apellidoPaterno)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public ArrayList<Contacto> buscarContactoPorEmail(String email) {
        ArrayList<Contacto> resultado = new ArrayList<>();
        for (Contacto c : contactos) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public int calcularEdad(Date fechaNacimiento) {

        LocalDate fechaNac = fechaNacimiento.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate hoy = LocalDate.now();

        return Period.between(fechaNac, hoy).getYears();
    }

    public ArrayList<Contacto> getContactos() {
        return contactos;
    }
}
