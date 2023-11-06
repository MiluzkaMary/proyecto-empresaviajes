package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.model.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import lombok.extern.java.Log;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
@Log
@Data

public class VentanaInicioController  implements Initializable {

    @FXML
    private AnchorPane panelIzquierdo;

    @FXML
    private AnchorPane panelDerecho;

    @FXML
    private AnchorPane barraSuperior;

    @FXML
    private AnchorPane panelPrincipal;

    private Aplicacion aplicacion;

    public Cliente cliente;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void mostrarPanelIzquierdo(){
        try {
            panelIzquierdo.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/TableroUsuario.fxml"));
            Node node = loader.load();
            panelIzquierdo.getChildren().add(node);
            TableroUsuarioController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarPanelIzquierdoCliente(){
        try {
            panelIzquierdo.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/TableroCliente.fxml"));
            Node node = loader.load();
            panelIzquierdo.getChildren().add(node);
            TableroClienteController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarPanelDerecho(){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaFiltrarPaquetes.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaFiltrarPaquetesController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarBarraSuperior(){
        try {
            barraSuperior.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/BarraUsuario.fxml"));
            Node node = loader.load();
            barraSuperior.getChildren().add(node);
            BarraUsuarioController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarBarraSuperiorCliente(){
        try {
            barraSuperior.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/BarraCliente.fxml"));
            Node node = loader.load();
            barraSuperior.getChildren().add(node);
            BarraClienteController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setCliente(this.cliente);
            controlador.cargarInfo();
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }



}
