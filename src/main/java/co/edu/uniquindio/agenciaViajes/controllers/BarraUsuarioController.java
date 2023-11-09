package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.exceptions.AtributoVacioException;
import co.edu.uniquindio.agenciaViajes.exceptions.CorreoInvalidoException;
import co.edu.uniquindio.agenciaViajes.exceptions.DatoNoNumericoException;
import co.edu.uniquindio.agenciaViajes.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agenciaViajes.model.AgenciaViajes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import lombok.Data;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

@Data

public class BarraUsuarioController implements Initializable {

    public Aplicacion aplicacion;

    @FXML
    private Button btnIngresar;

    @FXML
    private Button btnRegistrarse;

    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void mostrarVentanaRegistroIngreso () throws AtributoVacioException, DatoNoNumericoException, CorreoInvalidoException, InformacionRepetidaException {
        agenciaViajes.registrarCliente("476299", //aqui queme el admin pero solo como prueba
                "Miluzka Mary Saire",
                "317",
                "foto",
                "miluska@gmail.com",
                "armenia",
                "12345");
        aplicacion.mostrarVentanaRegistroIngreso(aplicacion);

    }
}
