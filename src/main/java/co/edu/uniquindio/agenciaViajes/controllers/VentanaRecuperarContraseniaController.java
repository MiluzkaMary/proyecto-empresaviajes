package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.model.AgenciaViajes;
import co.edu.uniquindio.agenciaViajes.model.Reserva;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Data;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

@Data

public class VentanaRecuperarContraseniaController implements Initializable {
    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    public Pane paneIngresar;
    public TextField txtCorreo;
    public Button btnContinuar;
    private Aplicacion aplicacion;
    private Stage ventana;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void enviarCorreoRecuperacion(){
        String asunto = "Código de recuperación para su cuenta";
        String mensaje = "Su código de verificación es: \n" + AgenciaViajes.generarCodigo() +
                "\nIMPORTANTE: Si usted no solicitó este código, haga caso omiso a este correo electrónico";
        AgenciaViajes.enviarCorreo(asunto, mensaje, txtCorreo.getText());
    }

}
