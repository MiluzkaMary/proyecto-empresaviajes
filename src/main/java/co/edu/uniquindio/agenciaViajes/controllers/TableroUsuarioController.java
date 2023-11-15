package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.model.Administrador;
import co.edu.uniquindio.agenciaViajes.model.AgenciaViajes;
import co.edu.uniquindio.agenciaViajes.model.Cliente;
import javafx.fxml.Initializable;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;
@Data


public class TableroUsuarioController implements Initializable {

    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    public Aplicacion aplicacion;
    public Cliente cliente;
    public Administrador administrador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void mostrarDestinos(){
        aplicacion.mostrarVentanaDestinos();
    }
    public void mostrarGuias(){
        aplicacion.mostrarVentanaGuias();
    }
    public void mostrarPaquetes(){
        aplicacion.motrarVentanaPaquetes(cliente, administrador);
    }
}
