package co.edu.uniquindio.agenciaViajes.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class Valoracion implements Serializable {

    @Serial
    private static final long serialVersionUID = 334688L;

    private int puntacion;
    private Cliente cliente;
    private String comentario;

}
