package co.edu.uniquindio.agenciaViajes.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GuiaTuristico implements Serializable {

    private String cedula;
    private String nombre;
    private String telefono;
    private String foto;
    private String edad;
    private ArrayList<Valoracion> valoraciones;
    private ArrayList<String> listaIdiomas;
    private ArrayList<LocalDate> listaDiasReservados;
    private int aniosExperiencia;

}
