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

public class TableroAdminController implements Initializable {

    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    public Aplicacion aplicacion;
    public Cliente cliente;
    public Administrador administrador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void gestionDestinos(){
        aplicacion.mostrarVentanaGestionDestinos(this.cliente, this.administrador);
    }

    public void gestionGuias(){
        aplicacion.mostrarVentanaGestionGuias(this.cliente, this.administrador);
    }

    public void gestionPaquetes(){
        aplicacion.motrarVentanaGestionPaquetes(this.cliente, this.administrador);
    }

    public void estadisticas(){
        aplicacion.mostrarVentanaEstadisticas();
    }
}
