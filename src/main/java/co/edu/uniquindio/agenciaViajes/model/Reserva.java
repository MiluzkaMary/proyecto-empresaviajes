package co.edu.uniquindio.agenciaViajes.model;


import co.edu.uniquindio.agenciaViajes.enums.EstadoReserva;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class Reserva implements Serializable {

    @Serial
    private static final long serialVersionUID = 292587L;

    private LocalDateTime fechaSolicitud;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Cliente cliente;
    private int numPersonas;
    private GuiaTuristico guia;
    private EstadoReserva estado;
    private Paquete paquete;


}
