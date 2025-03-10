package com.veterinaria.controlador;

import com.veterinaria.database.ConexionSQL;
import com.veterinaria.modelo.Cita;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class CitaController {

    // Obtener todas las citas desde la BD
    public static ArrayList<Cita> obtenerCitas() {
        ArrayList<Cita> citas = new ArrayList<>();
        try (Connection conn = ConexionSQL.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Citas")) {
            while (rs.next()) {
                citas.add(new Cita(
                        rs.getInt("id"),
                        rs.getString("nombre_dueño"),
                        rs.getString("documento_dueño"),
                        rs.getString("nombre_mascota"),
                        rs.getString("tipo_animal"),
                        rs.getString("raza"),
                        rs.getInt("edad"),
                        rs.getDouble("peso"),
                        rs.getString("motivo"),
                        rs.getTimestamp("fecha_recogida") != null ? rs.getTimestamp("fecha_recogida") : null
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las citas: " + e.getMessage());
        }
        return citas;
    }

    // Agregar una nueva cita
    public static boolean agregarCita(String dueño, String documento, String mascota, String tipoAnimal, String raza, int edad, double peso, String motivo) {
        String sql = "INSERT INTO Citas (nombre_dueño, documento_dueño, nombre_mascota, tipo_animal, raza, edad, peso, motivo, fecha_hora) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";

        try (Connection conn = ConexionSQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, dueño);
            stmt.setString(2, documento);
            stmt.setString(3, mascota);
            stmt.setString(4, tipoAnimal);
            stmt.setString(5, raza);
            stmt.setInt(6, edad);
            stmt.setDouble(7, peso);
            stmt.setString(8, motivo);
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar la cita: " + e.getMessage());
            return false;
        }
    }
    public static ArrayList<Cita> buscarCitasPorDocumento(String documento) {
        ArrayList<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM Citas WHERE documento_dueño = ?";
    
        try (Connection conn = ConexionSQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, documento);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                citas.add(new Cita(
                        rs.getInt("id"),
                        rs.getString("nombre_dueño"),
                        rs.getString("documento_dueño"),
                        rs.getString("nombre_mascota"),
                        rs.getString("tipo_animal"),
                        rs.getString("raza"),
                        rs.getInt("edad"),
                        rs.getDouble("peso"),
                        rs.getString("motivo"),
                        rs.getTimestamp("fecha_recogida") != null ? rs.getTimestamp("fecha_recogida") : null
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar citas: " + e.getMessage());
        }
        return citas;
    }
    

        // Editar la cita (actualizar el motivo de consulta)
    public static boolean editarCita(int id, String nuevoDueño, String nuevoDocumento, String nuevaMascota, String tipoAnimal, String nuevaRaza, int nuevaEdad, double nuevoPeso, String nuevoMotivo) {
        String sql = "UPDATE Citas SET nombre_dueño = ?, documento_dueño = ?, nombre_mascota = ?, tipo_animal = ?, raza = ?, edad = ?, peso = ?, motivo = ? WHERE id = ?";
    
        try (Connection conn = ConexionSQL.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, nuevoDueño);
            stmt.setString(2, nuevoDocumento);
            stmt.setString(3, nuevaMascota);
            stmt.setString(4, tipoAnimal);
            stmt.setString(5, nuevaRaza);
            stmt.setInt(6, nuevaEdad);
            stmt.setDouble(7, nuevoPeso);
            stmt.setString(8, nuevoMotivo);
            stmt.setInt(9, id);
    
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
    
        } catch (SQLException e) {
            System.out.println("Error al actualizar la cita: " + e.getMessage());
            return false;
        }
    }
        
        

    // Eliminar una cita por ID
    public static boolean eliminarCita(int id) {
        String sql = "DELETE FROM Citas WHERE id = ?";

        try (Connection conn = ConexionSQL.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar la cita: " + e.getMessage());
            return false;
        }
    }



    // Actualizar la fecha de recogida
    public static boolean actualizarFechaRecogida(int id, Date fechaRecogida) {
        String sql = "UPDATE Citas SET fecha_recogida = ? WHERE id = ?";

        try (Connection conn = ConexionSQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, new Timestamp(fechaRecogida.getTime()));
            stmt.setInt(2, id);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar la fecha de recogida: " + e.getMessage());
            return false;
        }
    }
}
