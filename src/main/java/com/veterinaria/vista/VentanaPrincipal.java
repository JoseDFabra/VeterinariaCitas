package com.veterinaria.vista;

import com.veterinaria.controlador.CitaController;
import com.veterinaria.modelo.Cita;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VentanaPrincipal extends JFrame {

    private JPanel panel;

    private JTextField buscarField;

public VentanaPrincipal() {
    setTitle("Gestión de Citas Veterinarias");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
        e.printStackTrace();
    }

    panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(new Color(240, 240, 240));

    JScrollPane scrollPane = new JScrollPane(panel);
    scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

    // Barra superior con botón de agregar y campo de búsqueda
    JPanel barraSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
    barraSuperior.setBackground(new Color(30, 136, 229));

    JButton btnAgregar = new JButton("Agregar Cita");
    btnAgregar.setForeground(Color.BLACK);
    btnAgregar.setBackground(new Color(46, 125, 50));
    btnAgregar.setFont(new Font("Arial", Font.BOLD, 14));
    btnAgregar.setFocusPainted(false);
    btnAgregar.addActionListener(e -> agregarCita());

    // Campo de búsqueda
    buscarField = new JTextField(15);
    JButton btnBuscar = new JButton("Buscar");
    btnBuscar.setForeground(Color.BLACK);
    btnBuscar.setBackground(new Color(255, 193, 7));
    btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
    btnBuscar.setFocusPainted(false);
    btnBuscar.addActionListener(e -> buscarCitas());

    barraSuperior.add(btnAgregar);
    barraSuperior.add(new JLabel("Buscar por documento:"));
    barraSuperior.add(buscarField);
    barraSuperior.add(btnBuscar);

    add(barraSuperior, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);

    actualizarLista();
    setVisible(true);
}


    private void actualizarLista() {
        panel.removeAll();
        ArrayList<Cita> citas = CitaController.obtenerCitas();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (Cita cita : citas) {
            JPanel card = new JPanel(new BorderLayout());
            card.setPreferredSize(new Dimension(700, 100));  // Tamaño fijo de la tarjeta
            card.setMaximumSize(new Dimension(700, 100));  // Evita que se expanda demasiado
            card.setBorder(BorderFactory.createLineBorder(new Color(30, 136, 229), 2));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel label = new JLabel("<html><b>" + cita.getMascota() + " (" + cita.getTipoAnimal() + ")</b> - " +
                    cita.getDueño() + " [" + cita.getDocumento() + "]<br/>Motivo: " + cita.getMotivo() +
                    (cita.getFechaRecogida() != null ? "<br/><span style='color:green;'>Recogida: " + sdf.format(cita.getFechaRecogida()) + "</span>" : "<br/><span style='color:red;'>Aún en atención</span>") +
                    "</html>");

            JButton btnEditar = new JButton("Editar");
            btnEditar.setBackground(new Color(255, 193, 7));  // Amarillo
            btnEditar.setFont(new Font("Arial", Font.BOLD, 12));
            btnEditar.setFocusPainted(false);
            btnEditar.addActionListener(e -> editarCita(cita));

            JButton btnEliminar = new JButton("Eliminar");
            btnEliminar.setBackground(new Color(211, 47, 47));  // Rojo
            btnEliminar.setForeground(Color.BLACK);
            btnEliminar.setFont(new Font("Arial", Font.BOLD, 12));
            btnEliminar.setFocusPainted(false);
            btnEliminar.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar esta cita?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    CitaController.eliminarCita(cita.getId());
                    actualizarLista();
                }
            });

            JButton btnRecogida = new JButton("Marcar como recogida");
            btnRecogida.setBackground(new Color(76, 175, 80));  // Verde
            btnRecogida.setForeground(Color.BLACK);
            btnRecogida.setFont(new Font("Arial", Font.BOLD, 12));
            btnRecogida.setFocusPainted(false);
            btnRecogida.addActionListener(e -> {
                CitaController.actualizarFechaRecogida(cita.getId(), new Date());
                actualizarLista();
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.add(btnEditar);
            buttonPanel.add(btnEliminar);
            if (cita.getFechaRecogida() == null) {
                buttonPanel.add(btnRecogida);
            }

            card.add(label, BorderLayout.CENTER);
            card.add(buttonPanel, BorderLayout.EAST);
            panel.add(card);
        }

        panel.revalidate();
        panel.repaint();
    }

    private void buscarCitas() {
        String documento = buscarField.getText().trim();
        if (documento.isEmpty()) {
            actualizarLista();
            return;
        }
    
        panel.removeAll();
        ArrayList<Cita> citas = CitaController.buscarCitasPorDocumento(documento);
        if (citas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron citas con ese documento.", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        }
    
        mostrarCitasEnPanel(citas);
    }
    
    private void mostrarCitasEnPanel(ArrayList<Cita> citas) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
        for (Cita cita : citas) {
            JPanel card = new JPanel(new BorderLayout());
            card.setPreferredSize(new Dimension(700, 100));
            card.setMaximumSize(new Dimension(700, 100));
            card.setBorder(BorderFactory.createLineBorder(new Color(30, 136, 229), 2));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
            JLabel label = new JLabel("<html><b>" + cita.getMascota() + " (" + cita.getTipoAnimal() + ")</b> - " +
                    cita.getDueño() + " [" + cita.getDocumento() + "]<br/>Motivo: " + cita.getMotivo() +
                    (cita.getFechaRecogida() != null ? "<br/><span style='color:green;'>Recogida: " + sdf.format(cita.getFechaRecogida()) + "</span>" : "<br/><span style='color:red;'>Aún en atención</span>") +
                    "</html>");
    
            JButton btnEditar = new JButton("Editar");
            btnEditar.setBackground(new Color(255, 193, 7));
            btnEditar.setFont(new Font("Arial", Font.BOLD, 12));
            btnEditar.setFocusPainted(false);
            btnEditar.addActionListener(e -> editarCita(cita));
    
            JButton btnEliminar = new JButton("Eliminar");
            btnEliminar.setBackground(new Color(211, 47, 47));
            btnEliminar.setForeground(Color.BLACK);
            btnEliminar.setFont(new Font("Arial", Font.BOLD, 12));
            btnEliminar.setFocusPainted(false);
            btnEliminar.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar esta cita?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    CitaController.eliminarCita(cita.getId());
                    actualizarLista();
                }
            });
    
            JButton btnRecogida = new JButton("Marcar como recogida");
            btnRecogida.setBackground(new Color(76, 175, 80));
            btnRecogida.setForeground(Color.WHITE);
            btnRecogida.setFont(new Font("Arial", Font.BOLD, 12));
            btnRecogida.setFocusPainted(false);
            btnRecogida.addActionListener(e -> {
                CitaController.actualizarFechaRecogida(cita.getId(), new Date());
                actualizarLista();
            });
    
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.add(btnEditar);
            buttonPanel.add(btnEliminar);
            if (cita.getFechaRecogida() == null) {
                buttonPanel.add(btnRecogida);
            }
    
            card.add(label, BorderLayout.CENTER);
            card.add(buttonPanel, BorderLayout.EAST);
            panel.add(card);
        }
    
        panel.revalidate();
        panel.repaint();
    }
    

    private void agregarCita() {
        JTextField dueñoField = new JTextField(15);
        JTextField documentoField = new JTextField(15);
        JTextField mascotaField = new JTextField(15);
    
        JRadioButton gatoButton = new JRadioButton("Gato");
        JRadioButton perroButton = new JRadioButton("Perro");
        JRadioButton otroButton = new JRadioButton("Otro");
        ButtonGroup tipoAnimalGroup = new ButtonGroup();
        tipoAnimalGroup.add(gatoButton);
        tipoAnimalGroup.add(perroButton);
        tipoAnimalGroup.add(otroButton);
    
        JTextField otroTipoField = new JTextField(10);
        otroTipoField.setEnabled(false); 
    
        otroButton.addActionListener(e -> otroTipoField.setEnabled(true));
        gatoButton.addActionListener(e -> otroTipoField.setEnabled(false));
        perroButton.addActionListener(e -> otroTipoField.setEnabled(false));
    
        JTextField razaField = new JTextField(15);
        JTextField edadField = new JTextField(5);
        JTextField pesoField = new JTextField(5);
        JTextField motivoField = new JTextField(15);
    
        JPanel panelForm = new JPanel(new GridLayout(9, 2, 5, 5));
        panelForm.add(new JLabel("Nombre del Dueño:"));
        panelForm.add(dueñoField);
        panelForm.add(new JLabel("Documento del Dueño:"));
        panelForm.add(documentoField);
        panelForm.add(new JLabel("Nombre de la Mascota:"));
        panelForm.add(mascotaField);
    
        panelForm.add(new JLabel("Tipo de Animal:"));
        JPanel tipoAnimalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tipoAnimalPanel.add(gatoButton);
        tipoAnimalPanel.add(perroButton);
        tipoAnimalPanel.add(otroButton);
        tipoAnimalPanel.add(otroTipoField);
        panelForm.add(tipoAnimalPanel);
    
        panelForm.add(new JLabel("Raza:"));
        panelForm.add(razaField);
        panelForm.add(new JLabel("Edad (en años):"));
        panelForm.add(edadField);
        panelForm.add(new JLabel("Peso (kg):"));
        panelForm.add(pesoField);
        panelForm.add(new JLabel("Motivo de la consulta:"));
        panelForm.add(motivoField);
    
        int result = JOptionPane.showConfirmDialog(null, panelForm, "Agregar Nueva Cita", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String dueño = dueñoField.getText();
                String documento = documentoField.getText();
                String mascota = mascotaField.getText();
    
                String tipoAnimal = "";
                if (gatoButton.isSelected()) {
                    tipoAnimal = "Gato";
                } else if (perroButton.isSelected()) {
                    tipoAnimal = "Perro";
                } else if (otroButton.isSelected()) {
                    tipoAnimal = otroTipoField.getText().trim();
                }
    
                String raza = razaField.getText();
                int edad = Integer.parseInt(edadField.getText().trim());  // Validación de número entero
                double peso = Double.parseDouble(pesoField.getText().trim());  // Validación de número decimal
                String motivo = motivoField.getText();
    
                if (!dueño.isEmpty() && !documento.isEmpty() && !mascota.isEmpty() && !tipoAnimal.isEmpty() && !raza.isEmpty() && !motivo.isEmpty()) {
                    CitaController.agregarCita(dueño, documento, mascota, tipoAnimal, raza, edad, peso, motivo);
                    actualizarLista();
                } else {
                    JOptionPane.showMessageDialog(null, "Todos los campos deben ser completados.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Edad y peso deben ser valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    private void editarCita(Cita cita) {
        JTextField dueñoField = new JTextField(cita.getDueño(), 15);
        JTextField documentoField = new JTextField(cita.getDocumento(), 15);
        JTextField mascotaField = new JTextField(cita.getMascota(), 15);
    
        // Crear botones de opción para seleccionar el tipo de animal
        JRadioButton gatoButton = new JRadioButton("Gato");
        JRadioButton perroButton = new JRadioButton("Perro");
        JRadioButton otroButton = new JRadioButton("Otro");
        ButtonGroup tipoAnimalGroup = new ButtonGroup();
        tipoAnimalGroup.add(gatoButton);
        tipoAnimalGroup.add(perroButton);
        tipoAnimalGroup.add(otroButton);
    
        JTextField otroTipoField = new JTextField(10);
        otroTipoField.setEnabled(false);
    
        // Determinar qué botón seleccionar según el tipo de animal
        if (cita.getTipoAnimal().equalsIgnoreCase("Gato")) {
            gatoButton.setSelected(true);
        } else if (cita.getTipoAnimal().equalsIgnoreCase("Perro")) {
            perroButton.setSelected(true);
        } else {
            otroButton.setSelected(true);
            otroTipoField.setText(cita.getTipoAnimal());
            otroTipoField.setEnabled(true);
        }
    
        // Evento para habilitar el campo "Otro" si se selecciona
        otroButton.addActionListener(e -> otroTipoField.setEnabled(true));
        gatoButton.addActionListener(e -> otroTipoField.setEnabled(false));
        perroButton.addActionListener(e -> otroTipoField.setEnabled(false));
    
        JTextField razaField = new JTextField(cita.getRaza(), 15);
        JTextField edadField = new JTextField(String.valueOf(cita.getEdad()), 5);
        JTextField pesoField = new JTextField(String.valueOf(cita.getPeso()), 5);
        JTextField motivoField = new JTextField(cita.getMotivo(), 15);
    
        JPanel panelForm = new JPanel(new GridLayout(9, 2, 5, 5));
        panelForm.add(new JLabel("Nombre del Dueño:"));
        panelForm.add(dueñoField);
        panelForm.add(new JLabel("Documento del Dueño:"));
        panelForm.add(documentoField);
        panelForm.add(new JLabel("Nombre de la Mascota:"));
        panelForm.add(mascotaField);
    
        panelForm.add(new JLabel("Tipo de Animal:"));
        JPanel tipoAnimalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tipoAnimalPanel.add(gatoButton);
        tipoAnimalPanel.add(perroButton);
        tipoAnimalPanel.add(otroButton);
        tipoAnimalPanel.add(otroTipoField);
        panelForm.add(tipoAnimalPanel);
    
        panelForm.add(new JLabel("Raza:"));
        panelForm.add(razaField);
        panelForm.add(new JLabel("Edad (en años):"));
        panelForm.add(edadField);
        panelForm.add(new JLabel("Peso (kg):"));
        panelForm.add(pesoField);
        panelForm.add(new JLabel("Motivo de la consulta:"));
        panelForm.add(motivoField);
    
        int result = JOptionPane.showConfirmDialog(null, panelForm, "Editar Cita", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nuevoDueño = dueñoField.getText();
                String nuevoDocumento = documentoField.getText();
                String nuevaMascota = mascotaField.getText();
    
                // Obtener la selección del tipo de animal
                String tipoAnimal = "";
                if (gatoButton.isSelected()) {
                    tipoAnimal = "Gato";
                } else if (perroButton.isSelected()) {
                    tipoAnimal = "Perro";
                } else if (otroButton.isSelected()) {
                    tipoAnimal = otroTipoField.getText().trim();
                }
    
                String nuevaRaza = razaField.getText();
                int nuevaEdad = Integer.parseInt(edadField.getText().trim());
                double nuevoPeso = Double.parseDouble(pesoField.getText().trim());
                String nuevoMotivo = motivoField.getText();
    
                if (!nuevoDueño.isEmpty() && !nuevoDocumento.isEmpty() && !nuevaMascota.isEmpty() && !tipoAnimal.isEmpty() && !nuevaRaza.isEmpty() && !nuevoMotivo.isEmpty()) {
                    boolean actualizado = CitaController.editarCita(
                            cita.getId(), nuevoDueño, nuevoDocumento, nuevaMascota, tipoAnimal, nuevaRaza, nuevaEdad, nuevoPeso, nuevoMotivo
                    );
    
                    if (actualizado) {
                        JOptionPane.showMessageDialog(null, "Cita actualizada correctamente.");
                        actualizarLista();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al actualizar la cita.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Todos los campos deben ser completados.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Edad y peso deben ser valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    
}
