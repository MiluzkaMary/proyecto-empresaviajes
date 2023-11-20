package co.edu.uniquindio.agenciaViajes.model;


import co.edu.uniquindio.agenciaViajes.enums.EstadoReserva;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private Double valorTotal;

    public String obtenerEstadoCadena() {
        String msj="";
        switch(estado) {
            case PENDIENTE: msj="Pendiente"; break;
            case CANCELADA: msj="Cancelada"; break;
            case CONFIRMADA: msj="Confirmada"; break;
        }
        return msj;
    }

    public String obtenerFechaSolicitudComoString() {
        // Formatear la fecha de solicitud como una cadena
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return fechaSolicitud.format(formatter);
    }


}
