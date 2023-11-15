package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.fxml.Initializable;
import lombok.Data;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
@Data

public class VentanaFiltrarPaquetesController implements Initializable {

    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    public Aplicacion aplicacion;
    public Cliente cliente;
    public Administrador administrador;
    private List<Paquete> listaPaquetes = new ArrayList<>();

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void iniciarGridPane(){

        int column=0;
        int row=1;
        try{
            for (int i=0; i<listaPaquetes.size(); i++){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/ItemPaquete.fxml"));
                AnchorPane anchorPane = loader.load();

                ItemPaqueteController controlador = loader.getController();
                controlador.setAplicacion(this.aplicacion);
                controlador.setAdministrador(this.administrador);
                controlador.setCliente(this.cliente);
                controlador.cargarDatos(listaPaquetes.get(i));



                if (column == 3) {
                    column =0;
                    row++;
                }
                grid.add(anchorPane, column++, row);

                //grid width
                 grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                 grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                 grid.setMaxWidth(Region.USE_PREF_SIZE);
                 //grid height
                 grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                 grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                 grid.setMaxHeight(Region.USE_PREF_SIZE);


                GridPane.setMargin(anchorPane, new Insets(8, 8, 8, 8));
            }
            scroll.setVvalue(0.05); //para q no se vea el espacio en blanco de 2 cm entre el filtro y el panel
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void establecerListaPaquetes(){
        this.listaPaquetes=agenciaViajes.getListaPaquetes();
    }
}
