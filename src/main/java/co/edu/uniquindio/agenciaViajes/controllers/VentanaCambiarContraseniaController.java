package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.exceptions.*;
import co.edu.uniquindio.agenciaViajes.model.AgenciaViajes;
import co.edu.uniquindio.agenciaViajes.model.Cliente;
import co.edu.uniquindio.agenciaViajes.util.ArchivoUtils;
import javafx.application.Platform;
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

public class VentanaCambiarContraseniaController implements Initializable {

    public Aplicacion aplicacion;
    public String code;
    public String correo;
    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    public Stage ventana;
    public Pane paneIngresar;
    public Button btnConfirmar;
    public TextField txtNuevaContra;
    public TextField txtConfirmContra;
    public Pane paneIngresar1;
    public Button btnConfirmar2;
    public TextField txtCodigo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void confirmarCodigo() {
        if (txtCodigo.getText().equals(code)) {
            paneIngresar1.setVisible(false);
            paneIngresar.setVisible(true);
            Platform.runLater(() -> txtCodigo.requestFocus()); // Forzar foco en el campo de texto
        }else {
            ArchivoUtils.mostrarMensaje("Error", "Entradas No Válidas", "El código no coincide", Alert.AlertType.ERROR);
        }
    }

    public void cambiarContrasenia() throws InformacionNoRepetidaException {
        try{
            Cliente cliente = agenciaViajes.cambiarContraseniaCliente(correo, txtNuevaContra.getText(), txtConfirmContra.getText());
            ArchivoUtils.mostrarMensaje("Cambio exitoso", "Operación completada", "¡Felicidades "+cliente.getNombre()+", su contraseña ha sido cambiada exitosamente!", Alert.AlertType.INFORMATION);
            ventana.close();
            aplicacion.mostrarVentanaRegistroIngreso(aplicacion);
        } catch (AtributoVacioException e) {
            ArchivoUtils.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}