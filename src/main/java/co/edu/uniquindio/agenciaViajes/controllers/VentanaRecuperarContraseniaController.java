package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.exceptions.CorreoInexistenteException;
import co.edu.uniquindio.agenciaViajes.exceptions.CorreoInvalidoException;
import co.edu.uniquindio.agenciaViajes.model.AgenciaViajes;
import co.edu.uniquindio.agenciaViajes.model.Cliente;
import co.edu.uniquindio.agenciaViajes.util.ArchivoUtils;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
        try{
            agenciaViajes.validarRecuperarCuenta(txtCorreo);
            abrirVentanaCambiarContrasenia();
        }catch (CorreoInvalidoException | CorreoInexistenteException e){
            ArchivoUtils.mostrarMensaje("Error", "Correo no enviado", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void abrirVentanaCambiarContrasenia() {
        ventana.close();
        aplicacion.ventanaCambiarContrasenia(aplicacion, code, correo);
    }
}
