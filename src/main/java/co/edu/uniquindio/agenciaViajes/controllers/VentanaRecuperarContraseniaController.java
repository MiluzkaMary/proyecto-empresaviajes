package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.model.AgenciaViajes;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Data;
import java.net.URL;
import java.util.ResourceBundle;

@Data

public class VentanaRecuperarContraseniaController implements Initializable {
    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    public Pane paneIngresar;
    public TextField txtCorreo;
    public Button btnContinuar;
    private Aplicacion aplicacion;
    private Stage ventana;
    private String code;
    private String correo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void recuperarCuenta() {
        //faltan las verificaciones de campo vacio o si el correo siquiera existe
        String asunto = "Código de recuperación para su cuenta";
        code = AgenciaViajes.generarCodigo();
        correo = txtCorreo.getText();
        String mensaje = "Su código de verificación es: \n" + code +
                "\nIMPORTANTE: Si usted no solicitó este código, haga caso omiso a este correo electrónico";
        agenciaViajes.enviarCorreo(asunto, mensaje, correo);
        abrirVentanaCambiarContrasenia();
    }

    public void abrirVentanaCambiarContrasenia() {
        ventana.close();
        aplicacion.ventanaCambiarContrasenia(aplicacion, code, correo);
    }
}
