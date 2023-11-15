package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.model.*;
import co.edu.uniquindio.agenciaViajes.util.ArchivoUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
@Data

public class VentanaGestionarController implements Initializable {

    @FXML
    private Button btnDetalles;

    @FXML
    private Text txtAdvertencia;

    @FXML
    private Button btnEliminar;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button btnCrear;

    @FXML
    private Text txtTituloGestion;

    @FXML
    private Button btnEditar;

    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    public Aplicacion aplicacion;
    public Cliente cliente;
    public Administrador administrador;
    //DATOS DE VALOR CAMBIANTE PARA LAS VENTANAS
    public Paquete paqueteElegido;
    public Destino destinoElegido;
    public GuiaTuristico guiaElegido;

    public boolean esGestionPaquetes;
    public boolean esGestionGuias;
    public boolean esGestionDestinos;

    //listeners
    private MyListenerPaquete myListenerPaquete;
    private MyListenerDestino myListenerDestino;
    private MyListenerGuia myListenerGuia;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void iniciarGestionGuias(){
        esGestionGuias=true;
        txtAdvertencia.setText("Antes de elegir una gestión, tenga en cuenta que debe seleccionar un guía");
        txtTituloGestion.setText("Gestión de Guías Turístico");
        iniciarScrollGuias();
    }

    public void iniciarScrollGuias(){
        ArrayList<GuiaTuristico> listaGuias= agenciaViajes.getListaGuias();
        if (listaGuias.size()>0){
            myListenerGuia = new MyListenerGuia() {
                @Override
                public void onClickListener(GuiaTuristico guia) {
                    setGuiaElegido(guia);
                }
            };
        }

        int column = 0;
        int row = 1;

        try {
            for (int i = 0; i < listaGuias.size(); i++) { // Cambio de 6 a 1 para mostrar solo una columna
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaGuias.fxml"));
                Pane pane = loader.load();

                VentanaGuiaController controlador = loader.getController();
                controlador.setEsGestion(true);
                controlador.setMyListenerGuia(myListenerGuia);
                controlador.setAdministrador(administrador);
                controlador.cargarDatos(listaGuias.get(i));

                grid.add(pane, column, row);

                column=0;
                row++;

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                //grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);



                // Elimina las líneas relacionadas con el ancho y alto del grid

                GridPane.setMargin(pane, new Insets(8, 8, 8, 8));
            }
            scroll.setVvalue(0.05); // para eliminar el espacio en blanco entre el filtro y el panel
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void iniciarGestionDestinos(){
        esGestionDestinos=true;
        txtAdvertencia.setText("Antes de elegir una gestión, tenga en cuenta que debe seleccionar un destino");
        txtTituloGestion.setText("Gestión de Destinos");
        iniciarScrollDestinos();
    }

    public void iniciarScrollDestinos() {

        ArrayList<Destino> listaDestinos= agenciaViajes.getListaDestinos();
        if (listaDestinos.size()>0){
            myListenerDestino = new MyListenerDestino() {
                @Override
                public void onClickListener(Destino destino) {
                    setDestinoElegido(destino);
                }
            };
        }

        int column = 0;
        int row = 1;

        try {
            for (int i = 0; i < listaDestinos.size(); i++) { // Cambio de 6 a 1 para mostrar solo una columna
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/ItemDestino.fxml"));
                Pane pane = loader.load();

                ItemDestinoController controlador = loader.getController();
                controlador.setEsGestion(true);
                controlador.setMyListenerDestino(myListenerDestino);
                controlador.setAdministrador(administrador);
                controlador.cargarDatos(listaDestinos.get(i));

                grid.add(pane, column, row);

                column=0;
                row++;

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                //grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                // Elimina las líneas relacionadas con el ancho y alto del grid

                GridPane.setMargin(pane, new Insets(8, 8, 8, 8));
            }
            scroll.setVvalue(0.05); // para eliminar el espacio en blanco entre el filtro y el panel
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inicarGestionPaquetes(){
        esGestionPaquetes=true;
        txtAdvertencia.setText("Antes de elegir una gestión, tenga en cuenta que debe seleccionar un paquete");
        txtTituloGestion.setText("Gestión de Paquetes");
        iniciarScrollPaquetes();
    }

    public void iniciarScrollPaquetes(){
        ArrayList<Paquete> listaPaquetes= agenciaViajes.getListaPaquetes();
        if (listaPaquetes.size()>0){
            myListenerPaquete = new MyListenerPaquete() {
                @Override
                public void onClickListener(Paquete paquete) {
                    setPaqueteElegido(paquete);
                }
            };
        }

        int column=0;
        int row=1;
        try{
            for (int i=0; i<listaPaquetes.size(); i++){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/ItemPaquete.fxml"));
                AnchorPane anchorPane = loader.load();

                ItemPaqueteController controlador = loader.getController();
                controlador.setEsGestion(true);
                controlador.setMyListenerPaquete(myListenerPaquete);
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

    public void crear(){
        if (esGestionPaquetes) {
            Paquete paqueteVacio=null;
            aplicacion.mostrarVentanaCrearPaquete(paqueteVacio, this.administrador);
        }else if (esGestionGuias){

        }else if (esGestionDestinos){

        }
    }

    public void editar(){
        if (esGestionPaquetes) {
            if (paqueteElegido!=null){
                aplicacion.mostrarVentanaCrearPaquete(paqueteElegido, this.administrador);
            }else{
                ArchivoUtils.mostrarMensaje("Error", "Entrada no valida", "Por favor seleccione un paquete primero", Alert.AlertType.ERROR);
            }
        }else if (esGestionGuias){

        }else if (esGestionDestinos){

        }
    }

    public void detalles(){
        if (esGestionPaquetes) {
            if (paqueteElegido!=null){
                aplicacion.mostrarDetallePaquete(paqueteElegido, this.cliente);
            }else{
                ArchivoUtils.mostrarMensaje("Error", "Entrada no valida", "Por favor seleccione un paquete primero", Alert.AlertType.ERROR);
            }
        }else if (esGestionGuias){
            if (guiaElegido!=null){
                ArchivoUtils.mostrarMensaje("Información", "Datos ya presentados", "Todos los detalles del guiía se encuentran ya expuestos", Alert.AlertType.INFORMATION);
            }else{
                ArchivoUtils.mostrarMensaje("Error", "Entrada no valida", "Por favor seleccione un paquete primero", Alert.AlertType.ERROR);
            }
        }else if (esGestionDestinos){
            if (destinoElegido!=null){
                ArchivoUtils.mostrarMensaje("Información", "Datos ya presentados", "Todos los detalles del destino se encuentran ya expuestos", Alert.AlertType.INFORMATION);
            }else{
                ArchivoUtils.mostrarMensaje("Error", "Entrada no valida", "Por favor seleccione un destino primero", Alert.AlertType.ERROR);
            }

        }
    }

    public void eliminar(){
        if (esGestionPaquetes) {
            if (paqueteElegido!=null){
                //URGENTE -> ANIADIR MINIVENTANA PARA CONFIRMAR SI EL ADMIN DESEA ELIMINAR EL PAQUETE DE LA AGENCIA
                agenciaViajes.eliminarPaquete(paqueteElegido);
                iniciarScrollPaquetes();
            }else{
                ArchivoUtils.mostrarMensaje("Error", "Entrada no valida", "Por favor seleccione un paquete primero", Alert.AlertType.ERROR);
            }
        }else if (esGestionGuias){

        }else if (esGestionDestinos){

        }
    }


}
