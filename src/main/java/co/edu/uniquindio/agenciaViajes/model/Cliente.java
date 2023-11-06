package co.edu.uniquindio.agenciaViajes.model;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Cliente implements Serializable {

    private String cedula;
    private String nombre;
    private String telefono;
    private String foto;
    private String correo;
    private String direccion;
    private String contrasenia;
    private ArrayList<Paquete> paquetesFavoritos;
}
