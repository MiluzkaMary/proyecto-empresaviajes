package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.exceptions.*;
import co.edu.uniquindio.agenciaViajes.model.*;
import co.edu.uniquindio.agenciaViajes.util.ArchivoUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Data;

@Data

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
    public Stage ventana;
    public GuiaTuristico guiaTuristico;
    public ArrayList<String> listaIdiomasActuales;

    public String fotoElegida;

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
            fotoElegida=guiaTuristico.getFoto();

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

            Image image = new Image("/imagenes/user.png");
            fotoGuia.setImage(image);
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
        ArrayList<String> listaDisponibles= agenciaViajes.obtenerIdiomasPermitidos(listaIdiomasActuales, 0 , new ArrayList<>());
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

    public void crearGuia(){

        //para la foto
        if (fotoGuia.getImage()!=null) {
            fotoElegida=fotoGuia.getImage().getUrl();
        }

        try {
            GuiaTuristico guia = agenciaViajes.registrarGuia(
                    txtCedulaGuia.getText(),
                    txtNombreGuia.getText(),
                    txtTelefonoGuia.getText(),
                    fotoElegida,
                    txtEdadGuia.getText(),
                    listaIdiomasActuales,
                    txtExperienciaGuia.getText());
            ArchivoUtils.mostrarMensaje("Registro Confirmado", "Operación completada", "Se ha registrado correctamente el guia: "+guia.getNombre(), Alert.AlertType.INFORMATION);
            aplicacion.mostrarVentanaGestionGuias(this.cliente, this.administrador);

        } catch (AtributoVacioException | InformacionRepetidaException | DatoNoNumericoException e) {
            ArchivoUtils.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void guardarCambiosGuia(){

        //para la foto
        if (fotoGuia.getImage()!=null) {
            fotoElegida=fotoGuia.getImage().getUrl();
        }
        try {
            GuiaTuristico guia = agenciaViajes.editarGuia(
                    this.guiaTuristico,
                    txtCedulaGuia.getText(),
                    txtNombreGuia.getText(),
                    txtTelefonoGuia.getText(),
                    fotoElegida,
                    txtEdadGuia.getText(),
                    listaIdiomasActuales,
                    txtExperienciaGuia.getText()
            );
            ArchivoUtils.mostrarMensaje("Edición Confirmada", "Operación completada", "Se ha editado correctamente el guia: "+guia.getNombre(), Alert.AlertType.INFORMATION);
            aplicacion.mostrarVentanaGestionGuias(this.cliente, this.administrador);

        } catch (AtributoVacioException | DatoNoNumericoException | InformacionRepetidaException e) {
            ArchivoUtils.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void subirFoto(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de Imagen", "*.jpg", "*.png", "*.jpeg"));

        // Mostrar el cuadro de diálogo para seleccionar un archivo
        File selectedFile = fileChooser.showOpenDialog(ventana);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            fotoGuia.setImage(image);
        } else {
            // Restaurar la imagen a null si no se selecciona ninguna
            Image image =  new Image ("/imagenes/user.png");
            fotoGuia.setImage(image);
        }

    }

    public void volver(){
        aplicacion.mostrarVentanaGestionGuias(this.cliente, this.administrador);
    }


}
