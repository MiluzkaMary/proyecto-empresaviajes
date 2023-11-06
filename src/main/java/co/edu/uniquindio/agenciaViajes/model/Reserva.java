package co.edu.uniquindio.agenciaViajes.model;


import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class Reserva implements Serializable {

    @Serial
    private static final long serialVersionUID = 292587L;

    private LocalDate fechaSolicitud;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Cliente cliente;
    private int numPersonas;
    private GuiaTuristico guia;
    private boolean estado;
    private Paquete paquete;


}
