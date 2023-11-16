package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.enums.EstadoReserva;
import co.edu.uniquindio.agenciaViajes.exceptions.AtributoVacioException;
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
    private static String emailFrom = "travelofficialdreams@gmail.com";
    private static String passwordFrom = "wftctrjagoucarcz";
    private String emailTo;
    private String subject;
    private String content;
    private Properties mProperties = new Properties();
    private Session mSession;
    private MimeMessage mCorreo;

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
        for (GuiaTuristico turistico : agenciaViajes.obtenerGuiasDisponiblesEnFecha(paquete.getFecha(), fechaFinal)) {
            String nombreCedula=turistico.getNombre() + " - "+ turistico.getCedula()+"";
            guias.add(nombreCedula);
        }
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
        int unidadesDisponibles=agenciaViajes.buscarUnidadesDisponibles(paquete);
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
        try {
            Reserva reserva = agenciaViajes.registrarReserva(
                    fechaHoraActual,
                    paquete.getFecha(),//fecha inicio
                    fechaFinal,
                    this.cliente,
                    unidades,
                    (String) comboGuia.getSelectionModel().getSelectedItem(), //cedula guia
                    EstadoReserva.PENDIENTE,
                    this.paquete);
            ArchivoUtils.mostrarMensaje("Reserva completada", "Operación completada", "Se ha reservado correctamente el paquete, por favor revise su correo", Alert.AlertType.INFORMATION);
            enviarCorreo(reserva);


        } catch (AtributoVacioException e) {
            ArchivoUtils.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }

    }
    private void enviarCorreo(Reserva reserva) {

        // Simple mail transfer protocol
        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable", "true");
        mProperties.setProperty("mail.smtp.port", "587");
        mProperties.setProperty("mail.smtp.user",emailFrom);
        mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.setProperty("mail.smtp.auth", "true");

        mSession = Session.getDefaultInstance(mProperties);


        try {
            // Crear un mensaje de correo
            Message mensaje = new MimeMessage(mSession);
            mensaje.setFrom(new InternetAddress(emailFrom)); // Dirección del remitente
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reserva.getCliente().getCorreo())); // Dirección del destinatario
            mensaje.setSubject("Reservación en Travel Dreams"); // Asunto del correo
            mensaje.setText("Detalles de la reservación: \n" +
                    "Persona que reserva: " + reserva.getCliente().getNombre() + "\n" +
                    "Nombre del paquete: "+ reserva.getPaquete() + "\n" +
                    "Fecha de reservación: "+ reserva.getFechaSolicitud() + "\n" +
                    "Fecha de inicio del paquete: " + reserva.getFechaInicio() + "\n" +
                    "Fecha de finalización del paquete: " + reserva.getFechaFin() + "\n" +
                    "Número de personas: " + reserva.getNumPersonas() + "\n" +
                    "Gracias por su preferencia :)"); // Contenido del correo

            // Enviar el mensaje
            Transport transport = mSession.getTransport("smtp");
            transport.connect("smtp.gmail.com", 587, emailFrom, passwordFrom);
            transport.sendMessage(mensaje, mensaje.getAllRecipients());
            transport.close();

            System.out.println("Correo enviado exitosamente.");

        } catch (MessagingException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
        }
    }
}
