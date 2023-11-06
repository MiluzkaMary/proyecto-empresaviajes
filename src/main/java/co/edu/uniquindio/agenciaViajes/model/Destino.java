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

}
