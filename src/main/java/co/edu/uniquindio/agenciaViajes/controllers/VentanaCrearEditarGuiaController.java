package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.model.*;
import co.edu.uniquindio.agenciaViajes.util.ArchivoUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import javafx.fxml.FXML;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


public class VentanaCrearEditarGuiaController implements Initializable {

    @FXML
    private TextField txtEdadGuia;

    @FXML
    private TableView<String> tablaIdiomasGuia;

    @FXML
    private TableColumn<String, String> columnaIdiomasGuias;

    @FXML
    private Button btnEliminarIdioma;

    @FXML
    private TableColumn<String, String> columnaIdiomasDisponibles;

    @FXML
    private TextField txtTelefonoGuia;

    @FXML
    private Button btnGuardarGuia;

    @FXML
    private TextField txtCedulaGuia;

    @FXML
    private Button btnSubirFoto;

    @FXML
    private ImageView fotoGuia;

    @FXML
    private TextField txtNombreGuia;

    @FXML
    private Button btnCrearGuia;

    @FXML
    private TextField txtExperienciaGuia;

    @FXML
    private TableView<String> tablaIdiomasDisponibles;

    @FXML
    private Button btnAgregarIdioma;

    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    public Aplicacion aplicacion;
    public Cliente cliente;
    public Administrador administrador;
    public GuiaTuristico guiaTuristico;
    public List<String> listaIdiomasActuales;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void iniciarDatosCrearEditar(){
        if (guiaTuristico!=null){ // si el guia existe se tratara de una edicion guia
            txtNombreGuia.setText(guiaTuristico.getNombre());
            txtEdadGuia.setText(String.valueOf(guiaTuristico.getEdad()));
            txtTelefonoGuia.setText(guiaTuristico.getTelefono());
            txtCedulaGuia.setText(guiaTuristico.getCedula());
            txtExperienciaGuia.setText(String.valueOf(guiaTuristico.getAniosExperiencia()));
            try {
                Image image = new Image(getClass().getResourceAsStream(guiaTuristico.getFoto()));
                fotoGuia.setImage(image);
            } catch (Exception e) {
                if (!guiaTuristico.getFoto().equals("")) {
                    Image image = new Image(guiaTuristico.getFoto());
                    fotoGuia.setImage(image);
                }
            }

            listaIdiomasActuales= guiaTuristico.getListaIdiomas(); //inicializacion de la lista de idiomas
            actualizarTablaIdiomasPosibles();
            actualizarTablaIdiomasActuales();

        }else{//si no se tratara de una creacion guia
            btnCrearGuia.setVisible(true);
            btnGuardarGuia.setVisible(false);

            listaIdiomasActuales = new ArrayList<>();
            actualizarTablaIdiomasPosibles();
        }
    }

    public void actualizarTablaIdiomasActuales(){
        tablaIdiomasGuia.getItems().clear();
        ObservableList<String> listaIdiomasActualesProperty= FXCollections.observableArrayList();
        listaIdiomasActualesProperty.addAll(listaIdiomasActuales);

        tablaIdiomasGuia.setItems(listaIdiomasActualesProperty);
        columnaIdiomasGuias.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

    }

    public void actualizarTablaIdiomasPosibles(){
        tablaIdiomasDisponibles.getItems().clear();
        ObservableList<String> listaIdiomasDisponiblesProperty= FXCollections.observableArrayList();
        ArrayList<String> listaDisponibles= agenciaViajes.obtenerIdiomasPermitidos(listaIdiomasActuales);
        listaIdiomasDisponiblesProperty.addAll(listaDisponibles);

        tablaIdiomasDisponibles.setItems(listaIdiomasDisponiblesProperty);
        columnaIdiomasDisponibles.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
    }

    public void agregarIdiomaNuevo(){
        String idiomaElegido = tablaIdiomasDisponibles.getSelectionModel().getSelectedItem();
        if (idiomaElegido!=null){
            listaIdiomasActuales.add(idiomaElegido);
            actualizarTablaIdiomasPosibles();
            actualizarTablaIdiomasActuales(); //para mostrar el nuevo destino
        }else{
            ArchivoUtils.mostrarMensaje("Error", "Entrada no valida", "Debe elegir un idiomas", Alert.AlertType.ERROR);

        }
    }

    public void eliminarIdioma(){
        String idiomaElegido = tablaIdiomasGuia.getSelectionModel().getSelectedItem();
        if (idiomaElegido!=null){
            listaIdiomasActuales.remove(idiomaElegido);
            actualizarTablaIdiomasActuales();
            actualizarTablaIdiomasPosibles(); //para mostrar como posible el q fue eliminado
        }else{
            ArchivoUtils.mostrarMensaje("Error", "Entrada no valida", "Debe elegir un destino", Alert.AlertType.ERROR);

        }
    }


}
