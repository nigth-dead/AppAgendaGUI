package appagendagui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AppAgendaGUI extends JFrame {

    JPanel panel1, panel2, tabla, añadirGUI, regreso1, regreso2, regreso3, lista,
            mostrarGUI, buscarGUI, sBuscar;
    JTextField campoDeTextoNombre, campoDeTextoApellidoPaterno,
            campoDeTextoApellidoMaterno, campoDeTextoNumero, campoDeTextoEmail,
            campoDeTextoEdad, campoDeTextoFecha;
    Agenda agenda = new Agenda();
    JScrollPane scroll;
    JButton volver, actualizar, borrar, volverInicio;
    JLabel imagen;

    public AppAgendaGUI() {
        //interfaz principal
        setTitle("Agenda De Revoqueros");
        setLayout(new BorderLayout());
        panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());
        JLabel titulo = new JLabel("Agenda De Revoqueros");
        titulo.setForeground(Color.white);
        panel2.add(titulo);
        panel2.setBackground(Color.black);
        add(panel2, BorderLayout.NORTH);
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(0, 1));
        JButton mostrar = new JButton("Mostrar");
        mostrar.setBackground(new Color(59, 55, 52));
        mostrar.setForeground(Color.white);
        mostrar.addActionListener(new OyenteMostrar(agenda.getContactos(), "mostrar"));
        panel1.add(mostrar);
        JButton añadir = new JButton("Añadir");
        añadir.addActionListener(new OyenteAñadir());
        añadir.setBackground(new Color(59, 55, 52));
        añadir.setForeground(Color.white);
        panel1.add(añadir);
        JButton buscar = new JButton("Buscar");
        buscar.setBackground(new Color(59, 55, 52));
        buscar.setForeground(Color.white);
        buscar.addActionListener(new OyenteBuscar());
        panel1.add(buscar);
        add(panel1, BorderLayout.CENTER);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("imagen.png");
        Image imagenEscalada = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon iconFinal = new ImageIcon(imagenEscalada);
        imagen = new JLabel(iconFinal);
    }

    class OyenteMostrar implements ActionListener {

        private ArrayList<Contacto> contactos;
        private String opc;

        public OyenteMostrar(ArrayList<Contacto> contactos, String opc) {
            this.contactos = contactos;
            this.opc = opc;
        }

        public void actionPerformed(ActionEvent evento) {
            lista = new JPanel();
            lista.setLayout(new GridLayout(0, 1));
            volver = new JButton("Volver");

            if (opc.equals("mostrar")) {
                remove(panel1);
                for (Contacto c : contactos) {
                    JButton contacto = new JButton(c.getNombre() + " "
                            + c.getApellidoPaterno() + " " + c.getApellidoMaterno());
                    contacto.addActionListener(new OyenteVerDatos(c));
                    lista.add(contacto);
                }
                volver.addActionListener(new OyenteVolver2());
            } else {
                if (!contactos.isEmpty()) {
                    remove(sBuscar);
                    remove(regreso2);
                    lista = new JPanel();
                    lista.setLayout(new GridLayout(0, 1));
                    for (Contacto c : contactos) {
                        JButton contacto = new JButton(c.getNombre());
                        contacto.addActionListener(new OyenteVerDatos(c));
                        lista.add(contacto);
                    }
                    volver.addActionListener(new OyenteVolver4());
                }
            }
            lista.setPreferredSize(new Dimension(400, contactos.size() * 50));
            JPanel contenedor = new JPanel(new BorderLayout());
            contenedor.add(lista, BorderLayout.NORTH);
            scroll = new JScrollPane(contenedor);
            scroll.setPreferredSize(new Dimension(400, 300));
            add(volver, BorderLayout.SOUTH);
            add(scroll, BorderLayout.CENTER);
            revalidate();
            repaint();

        }
    }

    class OyenteVolver2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evento) {
            remove(volver);
            remove(scroll);
            add(panel1);
            revalidate();
            repaint();
        }
    }

    class OyenteAñadir implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evento) {
            remove(panel1);
            añadirGUI = new JPanel();
            añadirGUI.setLayout(new GridLayout(0, 1));
            añadirGUI.add(imagen);
            tabla = new JPanel();
            tabla.setLayout(new GridLayout(0, 3));

            //nombres del usuario
            JLabel nombre = new JLabel("Nombre");
            nombre.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(nombre);
            JLabel tituloApellidoPaterno = new JLabel("Apellido Paterno");
            tituloApellidoPaterno.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(tituloApellidoPaterno);
            JLabel apellidoMaterno = new JLabel("Apellido Materno");
            apellidoMaterno.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(apellidoMaterno);
            campoDeTextoNombre = new JTextField();
            tabla.add(campoDeTextoNombre);
            campoDeTextoApellidoPaterno = new JTextField();
            tabla.add(campoDeTextoApellidoPaterno);
            campoDeTextoApellidoMaterno = new JTextField();
            tabla.add(campoDeTextoApellidoMaterno);

            //titulos de informacion de contacto
            JLabel tituloNumero = new JLabel("Numero");
            tituloNumero.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(tituloNumero);
            JLabel tituloEmail = new JLabel("E-mail");
            tituloEmail.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(tituloEmail);
            JLabel TituloFechaDeNAcimiento = new JLabel("Fecha de nacimiento");
            TituloFechaDeNAcimiento.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(TituloFechaDeNAcimiento);

            //cuadros de texto para informacion del contacto
            campoDeTextoNumero = new JTextField();
            tabla.add(campoDeTextoNumero);
            campoDeTextoEmail = new JTextField();
            tabla.add(campoDeTextoEmail);
            campoDeTextoEdad = new JTextField();
            tabla.add(campoDeTextoEdad);
            añadirGUI.add(tabla);
            regreso1 = new JPanel();
            regreso1.setLayout(new GridLayout(0, 1));
            JButton guardar = new JButton("Guardar");
            guardar.addActionListener(new OyenteGuardar("Guardar", null));
            regreso1.add(guardar);
            JButton volver = new JButton("Volver");
            volver.addActionListener(new OyenteVolver());
            regreso1.add(volver);
            add(regreso1, BorderLayout.SOUTH);
            add(añadirGUI, BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }

    class OyenteVolver implements ActionListener {

        public void actionPerformed(ActionEvent evento) {
            remove(regreso1);
            remove(añadirGUI);
            add(panel1);
            revalidate();
            repaint();
        }
    }

    class OyenteGuardar implements ActionListener {

        private String opcion;
        private Contacto contacto;

        public OyenteGuardar(String opcion, Contacto contacto) {
            this.opcion = opcion;
            this.contacto = contacto;
        }

        public void actionPerformed(ActionEvent evento) {
            int band = 0;
            try {
                String nombre = campoDeTextoNombre.getText();
                String numero = campoDeTextoNumero.getText();
                String email = campoDeTextoEmail.getText();
                String apellidoPaterno = campoDeTextoApellidoPaterno.getText();
                String apellidoMaterno = campoDeTextoApellidoMaterno.getText();
                String fechaTexto;
                if (opcion.equalsIgnoreCase("Guardar")){
                    fechaTexto = campoDeTextoEdad.getText();
                }else{
                    fechaTexto = campoDeTextoFecha.getText();
                }

                // Validar campos vacíos
                if (nombre.isEmpty() || numero.isEmpty() || email.isEmpty()
                        || fechaTexto.isEmpty() || apellidoPaterno.isEmpty()
                        || apellidoMaterno.isEmpty()) {
                    JOptionPane.showMessageDialog(añadirGUI,
                            "Todos los campos son obligatorios",
                            "Error de entrada",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar número
                if (!numero.matches("\\d{10}")) {
                    JOptionPane.showMessageDialog(añadirGUI,
                            "El número telefónico debe tener exactamente 10 dígitos",
                            "Error de entrada",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar email
                if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                    JOptionPane.showMessageDialog(
                            añadirGUI,
                            "Debe introducir un correo electrónico válido",
                            "Error de entrada",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Convertir fecha de String a Date
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                formato.setLenient(false);
                Date fechaDeNacimiento = null;
                try {
                    fechaDeNacimiento = formato.parse(fechaTexto);
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(añadirGUI,
                            "La fecha debe tener el formato dd/MM/yyyy",
                            "Error de entrada",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verificar duplicados
                if (agenda.compararNumero(numero, contacto)) {
                    JOptionPane.showMessageDialog(añadirGUI,
                            "El número de contacto ya existe en otro contacto",
                            "Contacto existente",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Guardar o actualizar
                if (opcion.equalsIgnoreCase("Guardar")) {
                    agenda.agregarContacto(nombre, apellidoPaterno, apellidoMaterno,
                            numero, email, fechaDeNacimiento); // pasa Date
                } else if (opcion.equalsIgnoreCase("actualizar")) {
                    contacto.setNombre(nombre);
                    contacto.setApellidoPaterno(apellidoPaterno);
                    contacto.setApellidoMaterno(apellidoMaterno);
                    contacto.setNumero(numero);
                    contacto.setEmail(email);
                    contacto.setFechaDeNacimiento(fechaDeNacimiento); // guarda Date
                }

                band = 1;

            } catch (Exception e) {
                JOptionPane.showMessageDialog(añadirGUI,
                        "Error inesperado: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            if (band == 1) {
                JOptionPane.showMessageDialog(añadirGUI,
                        "Contacto guardado exitosamente",
                        "Acción realizada exitosamente",
                        JOptionPane.INFORMATION_MESSAGE);
                if (regreso1 != null) {
                    remove(regreso1);
                }
                if (añadirGUI != null) {
                    remove(añadirGUI);
                }
                if (regreso3 != null) {
                    remove(regreso3);
                }
                if (mostrarGUI != null) {
                    remove(mostrarGUI);
                }
                add(panel1);
                revalidate();
                repaint();
            }
        }
    }

    class OyenteBorrar implements ActionListener {

        private Contacto contacto;

        public OyenteBorrar(Contacto contacto) {
            this.contacto = contacto;
        }

        public void actionPerformed(ActionEvent evento) {
            int respuesta = JOptionPane.showConfirmDialog(mostrarGUI, "¿Estás "
                    + "seguro de que deseas borrar este contacto?", "Confirmar "
                    + "eliminación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(mostrarGUI, "Contacto borrado "
                        + "exitosamente", "Accion realizada con exito",
                        JOptionPane.INFORMATION_MESSAGE);
                agenda.eliminarContacto(contacto);
                remove(regreso1);
                remove(mostrarGUI);
                new OyenteMostrar(agenda.getContactos(),
                        "mostrar").actionPerformed(null);
            }
        }
    }

    class OyenteBuscar implements ActionListener {

        public void actionPerformed(ActionEvent evento) {
            remove(panel1);
            buscarGUI = new JPanel();
            buscarGUI.setLayout(new GridLayout(0, 1));
            JLabel tBuscar = new JLabel("Seleccione el metodo de busqueda");
            tBuscar.setHorizontalAlignment(SwingConstants.CENTER);
            buscarGUI.add(tBuscar);
            JPanel fila1 = new JPanel();
            fila1.setLayout(new GridLayout(0, 2));
            JButton bNombre = new JButton("Nombre");
            bNombre.addActionListener(new OyenteBuscaropc(1));
            fila1.add(bNombre);
            JButton bNumero = new JButton("Numero");
            bNumero.addActionListener(new OyenteBuscaropc(2));
            fila1.add(bNumero);
            buscarGUI.add(fila1);
            JPanel fila2 = new JPanel();
            fila2.setLayout(new GridLayout(0, 2));
            JButton bEmail = new JButton("E-mail");
            bEmail.addActionListener(new OyenteBuscaropc(3));
            fila2.add(bEmail);
            JButton botonApellidoPaterno = new JButton("Apellido Paterno");
            botonApellidoPaterno.addActionListener(new OyenteBuscaropc(4));
            fila2.add(botonApellidoPaterno);
            buscarGUI.add(fila2);
            JButton volver = new JButton("Volver");
            volver.addActionListener(new OyenteVolver3());
            regreso1 = new JPanel(new GridLayout());
            regreso1.add(volver);
            add(regreso1, BorderLayout.SOUTH);
            add(buscarGUI, BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }

    class OyenteBuscaropc implements ActionListener {

        private int opc;
        private JTextField dato;

        public OyenteBuscaropc(int opc) {
            this.opc = opc;
            this.dato = new JTextField();
            this.dato.setHorizontalAlignment(SwingConstants.CENTER);
        }

        public void actionPerformed(ActionEvent evento) {
            remove(regreso1);
            remove(buscarGUI);
            sBuscar = new JPanel(new GridLayout(0, 1));
            JLabel titulo = new JLabel("Ingrese el dato del contacto a buscar");
            titulo.setHorizontalAlignment(SwingConstants.CENTER);
            sBuscar.add(titulo);
            sBuscar.add(dato);
            JButton bBuscar = new JButton("Buscar");
            bBuscar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String texto = dato.getText();
                    int band = 0;
                    if (texto.isEmpty()) {
                        JOptionPane.showMessageDialog(sBuscar, "Ingrese un dato"
                                + " valido", "Entrada invalida",
                                JOptionPane.ERROR_MESSAGE);
                        band = 1;
                    }
                    ArrayList<Contacto> resultado = new ArrayList<>();
                    switch (opc) {
                        case 1:
                            resultado = agenda.buscarContactoPorNombre(texto);
                            break;
                        case 2:
                            try {
                                int num = Integer.parseInt(texto);
                                resultado = agenda.buscarContactoPorNumero(num);
                            } catch (NumberFormatException ex) {
                                if (band != 1) {
                                    JOptionPane.showMessageDialog(sBuscar,
                                            "Ingrese un número válido",
                                            "Entrada invalida",
                                            JOptionPane.ERROR_MESSAGE);
                                    band = 1;
                                }
                            }
                            break;
                        case 3:
                            resultado = agenda.buscarContactoPorEmail(texto);
                            break;
                        case 4:
                            resultado = agenda.buscarContactoPorApellidoPaterno(texto);
                            break;
                    }
                    if (resultado.isEmpty() && band == 0) {
                        JOptionPane.showMessageDialog(sBuscar,
                                "Ningun contacto coincide con esta informacion",
                                "El dato no coincidió",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        if (band != 1) {
                            new OyenteMostrar(resultado, "buscar").actionPerformed(null);
                        }
                    }
                }
            });
            sBuscar.add(bBuscar);
            volver = new JButton("Volver");
            volver.addActionListener(new OyenteVolverBuscar());
            regreso2 = new JPanel(new GridLayout(0, 1));
            regreso2.add(volver);
            add(sBuscar, BorderLayout.CENTER);
            add(regreso2, BorderLayout.SOUTH);
            revalidate();
            repaint();
        }
    }

    class OyenteVerDatos implements ActionListener {

        private Contacto contacto;

        public OyenteVerDatos(Contacto contacto) {
            this.contacto = contacto;
        }

        public void actionPerformed(ActionEvent e) {
            remove(scroll);
            remove(volver);
            mostrarGUI = new JPanel();
            mostrarGUI.setLayout(new GridLayout(0, 1));
            mostrarGUI.add(imagen);
            tabla = new JPanel();
            tabla.setLayout(new GridLayout(0, 3));
            JLabel nombre = new JLabel("Nombre");
            nombre.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(nombre);
            JLabel tituloApellidoPaterno = new JLabel("Apellido Paterno");
            tituloApellidoPaterno.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(tituloApellidoPaterno);
            JLabel tituloApellidoMaterno = new JLabel("Apellido Materno");
            tituloApellidoMaterno.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(tituloApellidoMaterno);
            campoDeTextoNombre = new JTextField();
            campoDeTextoNombre.setEditable(false);
            tabla.add(campoDeTextoNombre);
            campoDeTextoApellidoPaterno = new JTextField();
            campoDeTextoApellidoPaterno.setEditable(false);
            tabla.add(campoDeTextoApellidoPaterno);
            campoDeTextoApellidoMaterno = new JTextField();
            campoDeTextoApellidoMaterno.setEditable(false);
            tabla.add(campoDeTextoApellidoMaterno);

            JLabel tituloNumero = new JLabel("Numero");
            tituloNumero.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(tituloNumero);
            JLabel tituloEmail = new JLabel("E-mail");
            tituloEmail.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(tituloEmail);
            JLabel tituloEdad = new JLabel("Edad");
            tituloEdad.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(tituloEdad);
            campoDeTextoNumero = new JTextField();
            campoDeTextoNumero.setEditable(false);
            tabla.add(campoDeTextoNumero);
            campoDeTextoEmail = new JTextField();
            campoDeTextoEmail.setEditable(false);
            tabla.add(campoDeTextoEmail);
            campoDeTextoEdad = new JTextField();
            campoDeTextoEdad.setEditable(false);
            tabla.add(campoDeTextoEdad);

            mostrarGUI.add(tabla);
            regreso3 = new JPanel();
            regreso3.setLayout(new GridLayout(0, 1));
            campoDeTextoNombre.setText(contacto.getNombre());
            campoDeTextoApellidoPaterno.setText(contacto.getApellidoPaterno());
            campoDeTextoApellidoMaterno.setText(contacto.getApellidoMaterno());
            campoDeTextoNumero.setText(contacto.getNumero());
            campoDeTextoEmail.setText(contacto.getEmail());
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaNacimiento = contacto.getFechaDeNacimiento();
            int edad = agenda.calcularEdad(fechaNacimiento);
            campoDeTextoEdad.setText(String.valueOf(edad));
            borrar = new JButton("Borrar");
            borrar.addActionListener(new OyenteBorrar2(contacto));
            regreso3.add(borrar);
            actualizar = new JButton("Actualizar");
            actualizar.addActionListener(new OyenteActualizar(contacto,tituloEdad, campoDeTextoEdad));
            regreso3.add(actualizar);
            volver = new JButton("Volver a inicio");
            volver.addActionListener(new OyenteVolverInicio());
            regreso3.add(volver);
            add(regreso3, BorderLayout.SOUTH);
            add(mostrarGUI, BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }

    class OyenteBorrar2 implements ActionListener {

        private Contacto contacto;

        public OyenteBorrar2(Contacto contacto) {
            this.contacto = contacto;
        }

        public void actionPerformed(ActionEvent evento) {
            int respuesta = JOptionPane.showConfirmDialog(mostrarGUI, "¿Estás seguro de que deseas borrar este contacto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(mostrarGUI, "Contacto borrado exitosamente", "Accion realizada con exito", JOptionPane.INFORMATION_MESSAGE);
                agenda.eliminarContacto(contacto);
                remove(regreso3);
                remove(mostrarGUI);
                add(panel1, BorderLayout.CENTER);

                revalidate();
                repaint();
            }
        }
    }

    class OyenteVolver5 implements ActionListener {

        public void actionPerformed(ActionEvent evento) {
            remove(regreso3);
            remove(mostrarGUI);
            add(scroll, BorderLayout.CENTER);
            add(regreso3, BorderLayout.SOUTH);
            revalidate();
            repaint();
        }
    }

    class OyenteVolver4 implements ActionListener {

        public void actionPerformed(ActionEvent evento) {
            remove(volver);
            remove(scroll);
            add(sBuscar, BorderLayout.CENTER);
            add(regreso2, BorderLayout.SOUTH);
            revalidate();
            repaint();
        }
    }

    class OyenteVolverBuscar implements ActionListener {

        public void actionPerformed(ActionEvent evento) {
            remove(regreso2);
            remove(sBuscar);
            add(regreso1, BorderLayout.SOUTH);
            add(buscarGUI, BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }

    class OyenteVolver3 implements ActionListener {

        public void actionPerformed(ActionEvent evento) {
            remove(regreso1);
            remove(buscarGUI);
            add(panel1);
            revalidate();
            repaint();
        }
    }

    class OyenteActualizar implements ActionListener {

        private Contacto contacto;
        private JLabel tituloEdad;
        private JTextField campoDeTextoEdad;

        public OyenteActualizar(Contacto contacto, JLabel tituloEdad, JTextField campoDeTextoEdad) {
            this.contacto = contacto;
            this.tituloEdad = tituloEdad;
            this.campoDeTextoEdad = campoDeTextoEdad;
        }

        public void actionPerformed(ActionEvent evento) {

            regreso3.remove(actualizar);
            regreso3.remove(borrar);
            regreso3.remove(volver);
            tituloEdad.setText("Fecha de nacimiento");
            tabla.remove(campoDeTextoEdad);
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            campoDeTextoFecha=new JTextField();
            campoDeTextoFecha.setText(formato.format(contacto.getFechaDeNacimiento()));
            tabla.add(campoDeTextoFecha);
            campoDeTextoNombre.setEditable(true);
            campoDeTextoApellidoPaterno.setEditable(true);
            campoDeTextoApellidoMaterno.setEditable(true);
            campoDeTextoNumero.setEditable(true);
            campoDeTextoEmail.setEditable(true);
            campoDeTextoFecha.setEditable(true);
            JButton botonGuardarCambios = new JButton("Guardar cambios");
            botonGuardarCambios.addActionListener(new OyenteGuardar("actualizar",
                    contacto));
            regreso3.add(botonGuardarCambios);
            JButton volverInicio = new JButton("Volver al inicio");
            volverInicio.addActionListener(new OyenteVolverInicio());
            regreso3.add(volverInicio);
            revalidate();
            repaint();
        }
    }

    class OyenteVolverInicio implements ActionListener {

        public void actionPerformed(ActionEvent evento) {
            remove(regreso3);
            if (mostrarGUI != null) {
                remove(mostrarGUI);
            }
            if (volver != null) {
                remove(volver);
            }

            remove(mostrarGUI);
            add(panel1);
            revalidate();
            repaint();
        }
    }

    public static void main(String[] args) {
        AppAgendaGUI g = new AppAgendaGUI();
    }
}
