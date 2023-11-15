package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.model.AgenciaViajes;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;

@Data

public class VentanaRecuperarContraseniaController implements Initializable {
    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    private Aplicacion aplicacion;
    private Stage ventana;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
