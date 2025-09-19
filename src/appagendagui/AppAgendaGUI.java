package appagendagui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AppAgendaGUI extends JFrame {

    JPanel p1, p2, tabla, añadirGUI, regreso1, regreso2, regreso3, lista, mostrarGUI, buscarGUI, sBuscar;
    JTextField tNombre, tNumero, tEmail, tDireccion;
    Agenda agenda = new Agenda();
    JScrollPane scroll;
    JButton volver;
    JLabel imagen;

    public AppAgendaGUI() {
        //interfaz principal
        setTitle("Agenda De Revoqueros");
        setLayout(new BorderLayout());
        p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        JLabel titulo = new JLabel("Agenda De Revoqueros");
        titulo.setForeground(Color.white);
        p2.add(titulo);
        p2.setBackground(Color.black);
        add(p2, BorderLayout.NORTH);
        p1 = new JPanel();
        p1.setLayout(new GridLayout(0, 1));
        JButton mostrar = new JButton("Mostrar");
        mostrar.setBackground(new Color(59, 55, 52));
        mostrar.setForeground(Color.white);
        mostrar.addActionListener(new OyenteMostrar(agenda.getContactos(), "mostrar"));
        p1.add(mostrar);
        JButton añadir = new JButton("Añadir");
        añadir.addActionListener(new OyenteAñadir());
        añadir.setBackground(new Color(59, 55, 52));
        añadir.setForeground(Color.white);
        p1.add(añadir);
        JButton buscar = new JButton("Buscar");
        buscar.setBackground(new Color(59, 55, 52));
        buscar.setForeground(Color.white);
        buscar.addActionListener(new OyenteBuscar());
        p1.add(buscar);
        add(p1, BorderLayout.CENTER);
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
                remove(p1);
                for (Contacto c : contactos) {
                    JButton contacto = new JButton(c.getNombre());
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
            add(p1);
            revalidate();
            repaint();
        }
    }

    class OyenteAñadir implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evento) {
            remove(p1);
            añadirGUI = new JPanel();
            añadirGUI.setLayout(new GridLayout(0, 1));
            añadirGUI.add(imagen);
            tabla = new JPanel();
            tabla.setLayout(new GridLayout(0, 2));
            JLabel nombre = new JLabel("Nombre");
            nombre.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(nombre);
            JLabel numero = new JLabel("Numero");
            numero.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(numero);
            tNombre = new JTextField();
            tabla.add(tNombre);
            tNumero = new JTextField();
            tabla.add(tNumero);
            JLabel email = new JLabel("E-mail");
            email.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(email);
            JLabel direccion = new JLabel("Direccion");
            direccion.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(direccion);
            tEmail = new JTextField();
            tabla.add(tEmail);
            tDireccion = new JTextField();
            tabla.add(tDireccion);
            añadirGUI.add(tabla);
            regreso1 = new JPanel();
            regreso1.setLayout(new GridLayout(0, 1));
            JButton guardar = new JButton("Guardar");
            guardar.addActionListener(new OyenteGuardar());
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
            add(p1);
            revalidate();
            repaint();
        }
    }

    class OyenteGuardar implements ActionListener {

        public void actionPerformed(ActionEvent evento) {
            int band = 0;
            try {
                String nomb = tNombre.getText();
                int num = Integer.parseInt(tNumero.getText());
                String email = tEmail.getText();
                String dir = tDireccion.getText();
                if (nomb.isEmpty()) {
                    JOptionPane.showMessageDialog(añadirGUI, "El nombre es un campo obligatorio", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (agenda.compararNum(num) == true) {
                        JOptionPane.showMessageDialog(añadirGUI, "El numero de contacto ya existe", "Contacto existente", JOptionPane.ERROR_MESSAGE);
                    } else if (agenda.compararNomb(nomb) == true) {
                        int respuesta = JOptionPane.showConfirmDialog(añadirGUI, "El nombre de contacto ya existe, ¿Desea guardar otro contacto con el mismo nombre?", "Contacto existente", JOptionPane.YES_NO_OPTION);
                        if (respuesta == JOptionPane.YES_OPTION) {
                            agenda.agregarContacto(nomb, num, email, dir);
                            band = 1;
                        } else {
                            JOptionPane.showMessageDialog(añadirGUI, "Accion cancelada", "Accion cancelada", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        agenda.agregarContacto(nomb, num, email, dir);
                        band = 1;
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(añadirGUI, "Debe introducir un numero telefonico valido", "Error de entrada", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(añadirGUI, "Error inesperado" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (band == 1) {
                JOptionPane.showMessageDialog(añadirGUI, "Contacto guardado exitosamente", "Accion realizada exitosamente", JOptionPane.INFORMATION_MESSAGE);
                remove(regreso1);
                remove(añadirGUI);
                add(p1);
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
            int respuesta = JOptionPane.showConfirmDialog(mostrarGUI, "¿Estás seguro de que deseas borrar este contacto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(mostrarGUI, "Contacto borrado exitosamente", "Accion realizada con exito", JOptionPane.INFORMATION_MESSAGE);
                agenda.eliminarContacto(contacto);
                remove(regreso1);
                remove(mostrarGUI);
                new OyenteMostrar(agenda.getContactos(), "mostrar").actionPerformed(null);
            }
        }
    }

    class OyenteBuscar implements ActionListener {

        public void actionPerformed(ActionEvent evento) {
            remove(p1);
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
            JButton bDireccion = new JButton("Direccion");
            bDireccion.addActionListener(new OyenteBuscaropc(4));
            fila2.add(bDireccion);
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
                        JOptionPane.showMessageDialog(sBuscar, "Ingrese un dato valido", "Entrada invalida", JOptionPane.ERROR_MESSAGE);
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
                                    JOptionPane.showMessageDialog(sBuscar, "Ingrese un número válido", "Entrada invalida", JOptionPane.ERROR_MESSAGE);
                                    band = 1;
                                }
                            }
                            break;
                        case 3:
                            resultado = agenda.buscarContactoPorEmail(texto);
                            break;
                        case 4:
                            resultado = agenda.buscarContactoPorDireccion(texto);
                            break;
                    }
                    if (resultado.isEmpty() && band == 0) {
                        JOptionPane.showMessageDialog(sBuscar, "Ningun contacto coincide con esta informacion", "El dato no coincidió", JOptionPane.INFORMATION_MESSAGE);
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
            tabla.setLayout(new GridLayout(0, 2));
            JLabel nombre = new JLabel("Nombre");
            nombre.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(nombre);
            JLabel numero = new JLabel("Numero");
            numero.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(numero);
            tNombre = new JTextField();
            tabla.add(tNombre);
            tNumero = new JTextField();
            tabla.add(tNumero);
            JLabel email = new JLabel("E-mail");
            email.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(email);
            JLabel direccion = new JLabel("Direccion");
            direccion.setHorizontalAlignment(SwingConstants.CENTER);
            tabla.add(direccion);
            tEmail = new JTextField();
            tabla.add(tEmail);
            tDireccion = new JTextField();
            tabla.add(tDireccion);
            mostrarGUI.add(tabla);
            regreso3 = new JPanel();
            regreso3.setLayout(new GridLayout(0, 1));
            tNombre.setText(contacto.getNombre());
            tNumero.setText(String.valueOf(contacto.getNumero()));
            tEmail.setText(contacto.getEmail());
            tDireccion.setText(contacto.getDireccion());
            JButton borrar = new JButton("Borrar");
            borrar.addActionListener(new OyenteBorrar2(contacto));
            regreso3.add(borrar);
            JButton volver = new JButton("Volver");
            volver.addActionListener(new OyenteVolver5());
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
                add(p1, BorderLayout.CENTER);

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
            add(volver, BorderLayout.SOUTH);
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
            add(p1);
            revalidate();
            repaint();
        }
    }

    public static void main(String[] args) {
        AppAgendaGUI g = new AppAgendaGUI();
    }
}
