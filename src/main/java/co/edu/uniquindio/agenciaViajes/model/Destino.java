package co.edu.uniquindio.agenciaViajes.model;

import co.edu.uniquindio.agenciaViajes.enums.TipoClima;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Destino implements Serializable {

    @Serial
    private static final long serialVersionUID = 421273L;

    private String nombre;
    private String ciudad;
    private String descripcion;
    private TipoClima clima;
    private List<String> fotos;
    private ArrayList<Valoracion> valoraciones;

    /**
     * Obtiene el tipo de vehículo en forma de cadena de texto basado en la instancia actual.
     * @return el tipo de vehículo como cadena de texto
     */
    public String obtenerClimaCadena() {
        String msj="";
        switch(clima) {
            case TROPICAL: msj="Tropical"; break;
            case SECO: msj="Seco"; break;
            case TEMPLADO: msj="Templado"; break;
            case CONTINENTAL: msj="Continental"; break;
            case POLAR: msj="Polar"; break;
        }
        return msj;
    }



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
