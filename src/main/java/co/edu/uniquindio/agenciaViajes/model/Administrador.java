package co.edu.uniquindio.agenciaViajes.model;

import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Administrador implements Serializable {

    private String cedula;
    private String nombre;
    private String telefono;
    private String foto;
    private String correo;
    private String contrasenia;



}
