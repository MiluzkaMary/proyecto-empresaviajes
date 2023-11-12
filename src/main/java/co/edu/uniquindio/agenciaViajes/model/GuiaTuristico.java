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
    private int edad;
    private ArrayList<Valoracion> valoraciones;
    private ArrayList<String> listaIdiomas;
    private int aniosExperiencia;

    public double obtenerPromedioValoracion(){
        if (valoraciones.isEmpty()) {
            return 0.0;
        }
        double sumaPuntuaciones = 0.0;
        for (Valoracion valoracion : valoraciones) {
            sumaPuntuaciones += valoracion.getPuntuacion();
        }

        double promedio = sumaPuntuaciones / valoraciones.size();
        return Math.round(promedio * 10.0) / 10.0; //para tomar unicamente 1 cifra despues del decimal

    }

    public int obtenerNumValoraciones(){
        return valoraciones.size();
    }

}
