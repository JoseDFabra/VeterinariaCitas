package com.veterinaria.modelo;

import java.util.Date;

public class Cita {
    private int id;
    private String dueño;
    private String documento;
    private String mascota;
    private String tipoAnimal;
    private String raza;
    private int edad;
    private double peso;
    private String motivo;
    private Date fechaRecogida;

    public Cita(int id, String dueño, String documento, String mascota, String tipoAnimal, String raza, int edad, double peso, String motivo, Date fechaRecogida) {
        this.id = id;
        this.dueño = dueño;
        this.documento = documento;
        this.mascota = mascota;
        this.tipoAnimal = tipoAnimal;
        this.raza = raza;
        this.edad = edad;
        this.peso = peso;
        this.motivo = motivo;
        this.fechaRecogida = fechaRecogida;
    }

    public int getId() { return id; }
    public String getDueño() { return dueño; }
    public String getDocumento() { return documento; }
    public String getMascota() { return mascota; }
    public String getTipoAnimal() { return tipoAnimal; }
    public String getRaza() { return raza; }
    public int getEdad() { return edad; }
    public double getPeso() { return peso; }
    public String getMotivo() { return motivo; }
    public Date getFechaRecogida() { return fechaRecogida; }

    @Override
    public String toString() {
        return dueño + " (" + documento + ") - " + mascota + " [" + tipoAnimal + "] (" + motivo + ")";
    }
}
