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
                "Mary",
                "317",
                "/imagenes/pruebaUser.jpg",
                "miluskasaire512@gmail.com",
                "armenia",
                "12345");
        agenciaViajes.registrarAdministradorPrueba("5656",
                "Miluzka",
                "316",
                "/imagenes/pruebaUser.jpg",
                "mary@gmail.com",
                "54321");
        aplicacion.mostrarVentanaRegistroIngreso(aplicacion);

    }
}
