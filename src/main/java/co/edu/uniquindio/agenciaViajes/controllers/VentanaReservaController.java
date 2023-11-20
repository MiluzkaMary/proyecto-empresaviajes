package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.enums.EstadoReserva;
import co.edu.uniquindio.agenciaViajes.exceptions.AtributoVacioException;
import co.edu.uniquindio.agenciaViajes.exceptions.EnviarCorreoException;
import co.edu.uniquindio.agenciaViajes.model.*;
import co.edu.uniquindio.agenciaViajes.util.ArchivoUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import lombok.Data;

import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;


@Data


public class VentanaReservaController implements Initializable {

    @FXML
    private TextField txtCorreoCliente;

    @FXML
    private TextField txtTelefonoCliente;

    @FXML
    private DatePicker pickerFinalViaje;

    @FXML
    private Button btnReservar;

    @FXML
    private Text txtSubtotal;

    @FXML
    private Text txtUnidades;

    @FXML
    private DatePicker pickerInicialViaje;

    @FXML
    private Text txtPrecio;

    @FXML
    private ComboBox comboGuia;

    @FXML
    private ImageView imagenPaquete;

    @FXML
    private Text txtNombrePaquete;

    @FXML
    private Button btnMas;

    @FXML
    private TextField txtNombreCliente;

    @FXML
    private Button btnMenos;

    @FXML
    private Text txtTitulo11;

    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    public Aplicacion aplicacion;
    public Cliente cliente;
    public Paquete paquete;
    public int unidades=1;
    public Administrador administrador;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void iniciarDatos(){

        String foto=paquete.getDestinos().get(0).getFotos().get(0);
        //se toma la primera foto del primer destino que tiene el paquete
        try {
            Image image = new Image(getClass().getResourceAsStream(foto));
            imagenPaquete.setImage(image);
        } catch (Exception e) {
            if (!foto.isEmpty()) {
                Image image = new Image(foto);
                imagenPaquete.setImage(image);
            }
        }

        txtNombrePaquete.setText(paquete.getNombre());
        String valorPrecioCadena = String.format("%,.0f", paquete.getPrecio());
        txtPrecio.setText("$ "+valorPrecioCadena);
        txtUnidades.setText("1");
        Double subtotal=paquete.getPrecio()*unidades;
        String valorSubtotalCadena = String.format("%,.0f", subtotal);
        txtSubtotal.setText("$ "+valorSubtotalCadena);

        //datos cliente
        txtNombreCliente.setText(cliente.getNombre());
        txtCorreoCliente.setText(cliente.getCorreo());
        txtTelefonoCliente.setText(cliente.getTelefono());
        LocalDate fechaFinal = paquete.getFecha().plusDays(paquete.getDiasDuracion());
        pickerFinalViaje.setValue(fechaFinal);
        pickerInicialViaje.setValue(paquete.getFecha());

        //combobox
        ObservableList<String> guias= FXCollections.observableArrayList();
        ArrayList <String> listaGuiasDisponibles = agenciaViajes.obtenerNombresGuiasDisponiblesEnFecha(paquete, fechaFinal, 0, new ArrayList<>());
        guias.addAll(listaGuiasDisponibles);
        comboGuia.setItems(guias);

    }

    public void menos(){
        if (unidades!=1){
            unidades--;
            txtUnidades.setText(String.valueOf(unidades));
            Double subtotal=paquete.getPrecio()*unidades;
            String valorSubtotalCadena = String.format("%,.0f", subtotal);
            txtSubtotal.setText("$ "+valorSubtotalCadena);
        }
        else{
            ArchivoUtils.mostrarMensaje("ERROR DE ENTRADA", "Número de unidades incorrecta", "Eliga un número de unidades valida", Alert.AlertType.WARNING);
        }
    }

    public void mas(){
        int unidadesDisponibles=agenciaViajes.buscarUnidadesDisponibles(paquete, EstadoReserva.PENDIENTE, 0);
        if (unidades!=unidadesDisponibles){
            unidades++;
            txtUnidades.setText(String.valueOf(unidades));
            Double subtotal=paquete.getPrecio()*unidades;
            String valorSubtotalCadena = String.format("%,.0f", subtotal);
            txtSubtotal.setText("$ "+valorSubtotalCadena);
        }else{
            ArchivoUtils.mostrarMensaje("ERROR DE ENTRADA", "Número de unidades incorrecta", "Solo hay "+unidadesDisponibles+" cupos disponibles", Alert.AlertType.WARNING);

        }
    }

    public void reservar(){
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        LocalDate fechaFinal = paquete.getFecha().plusDays(paquete.getDiasDuracion());
        Double valorTotal = paquete.getPrecio()*unidades;
        try {
            Reserva reserva = agenciaViajes.registrarReserva(
                    fechaHoraActual,
                    paquete.getFecha(),//fecha inicio
                    fechaFinal,
                    this.cliente,
                    unidades,
                    (String) comboGuia.getSelectionModel().getSelectedItem(), //cedula guia
                    EstadoReserva.PENDIENTE,
                    this.paquete,
                    valorTotal);
            ArchivoUtils.mostrarMensaje("Reserva completada", "Operación completada", "Se ha reservado correctamente el paquete, por favor revise su correo", Alert.AlertType.INFORMATION);
            enviarCorreoReserva(reserva);
            aplicacion.mostrarVentanaPrincipal(this.cliente, this.administrador);
        } catch (AtributoVacioException e) {
            ArchivoUtils.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        } catch (EnviarCorreoException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelar(){
        aplicacion.motrarVentanaPaquetes(this.cliente, this.administrador);
    }

    private void enviarCorreoReserva(Reserva reserva) throws EnviarCorreoException {
        agenciaViajes.enviarCorreo("Detalles de la reserva",
                "Hola " + reserva.getCliente().getNombre() + ". Gracias por reservar con nosotros, los detalles de tu reserva son:\n" +
                        "Paquete: " + reserva.getPaquete().getNombre() + "\n" +
                        "Número de personas: " + reserva.getNumPersonas() + "\n" +
                        "Fecha de inicio: " + reserva.getFechaInicio() + "\n" +
                        "Fecha de finalización: " + reserva.getFechaFin() + "\n" +
                        "Estado: " + reserva.getEstado() + "\n" +
                        "Guía: " + reserva.getGuia().getNombre() + "\n" +
                        "Valor total: " + reserva.getValorTotal(), reserva.getCliente().getCorreo());
    }
}
