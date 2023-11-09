package co.edu.uniquindio.agenciaViajes.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class Paquete implements Serializable {

    @Serial
    private static final long serialVersionUID = 912713L;

    private String nombre;
    private List<Destino> destinos;
    private int diasDuracion;
    private  String descripcion;
    private Double precio;
    private int cupoMaximo;
    private LocalDate fecha;

    public double obtenerPromedioValoraciones(){
        double suma=0;
        int contador=0;
        for (Destino destino: destinos){
            suma+=destino.obtenerPromedioValoracion();
            contador++;
        }
        suma/=contador;
        suma = Math.round(suma * 10.0) / 10.0;
        return suma;
    }

    public int obtenerNumValoraciones(){
        int suma=0;
        for (Destino destino : destinos){
            suma+=destino.getValoraciones().size();
        }
        return suma;
    }


}
