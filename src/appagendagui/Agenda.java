package appagendagui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.*;
import java.io.*;
import java.util.Scanner;

public class Agenda {

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

    public boolean compararNum(int numero) {
        for (int i = 0; i < contactos.size(); i++) {
            if (contactos.get(i).getNumero() == numero) {
                return true;
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

    public void agregarContacto(String nombre, int numero, String email, String direccion) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // JSON bonito
        Contacto l = new Contacto(nombre, numero, email, direccion);
        contactos.add(l);

        // Ordenar por nombre (ignorar mayúsculas/minúsculas)
        Collections.sort(contactos, new Comparator<Contacto>() {
            public int compare(Contacto c1, Contacto c2) {
                return c1.getNombre().compareToIgnoreCase(c2.getNombre());
            }
        });

        // Guardar en JSON
        try (FileWriter writer = new FileWriter("Contactos.json")) {
            gson.toJson(contactos, writer); // convierte la lista a JSON
        } catch (IOException e) {
            System.out.println("Error al guardar contactos: " + e.getMessage());
        }
    }

    public void eliminarContacto(Contacto contacto) {
        if (contactos.remove(contacto)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try (FileWriter writer = new FileWriter("Contactos.json")) {
                gson.toJson(contactos, writer); // sobrescribe el archivo JSON con la lista actualizada
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

    public ArrayList<Contacto> buscarContactoPorNumero(int numero) {
        ArrayList<Contacto> resultado = new ArrayList<>();
        for (Contacto c : contactos) {
            if (c.getNumero() == numero) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public ArrayList<Contacto> buscarContactoPorDireccion(String direccion) {
        ArrayList<Contacto> resultado = new ArrayList<>();
        for (Contacto c : contactos) {
            if (c.getDireccion().equalsIgnoreCase(direccion)) {
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

    public ArrayList<Contacto> getContactos() {
        return contactos;
    }
}
