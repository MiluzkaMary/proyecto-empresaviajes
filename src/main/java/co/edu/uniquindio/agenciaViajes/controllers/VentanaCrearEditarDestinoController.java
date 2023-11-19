package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.exceptions.AtributoVacioException;
import co.edu.uniquindio.agenciaViajes.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agenciaViajes.model.*;
import co.edu.uniquindio.agenciaViajes.util.ArchivoUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Data;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
@Data
public class VentanaCrearEditarDestinoController implements Initializable {

    @FXML
    private Button btnSubirFoto;

    @FXML
    private Button btnEliminarFoto;

    @FXML
    private TextField txtNombreDestino;

    @FXML
    private TextArea txtDescripcionDestino;

    @FXML
    private Button btnDespuesFoto;

    @FXML
    private ComboBox comboClima;

    @FXML
    private ImageView fotoSubida;

    @FXML
    private Button btnCrearDestino;

    @FXML
    private Button btnAntesFoto;

    @FXML
    private ImageView mainPicture;

    @FXML
    private Button btnGuardarDestino;

    @FXML
    private TextField txtCiudadDestino;

    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    public Aplicacion aplicacion;
    public Cliente cliente;
    public Administrador administrador;
    public Stage ventana;
    public Destino destino;
    public List<String> listaFotosActuales;
    private int indexFotos=0;

    public String fotoElegida;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void iniciarDatosCrearEditar(){
        //inicio de combo
        ObservableList<String> climas= FXCollections.observableArrayList(
                "TROPICAL",
                "SECO",
                "TEMPLADO",
                "CONTINENTAL",
                "POLAR"
        );
        comboClima.setItems(climas);

        if (destino!=null){ // si el destino existe se tratara de una edicion destino
            txtNombreDestino.setText(destino.getNombre());
            txtCiudadDestino.setText(destino.getCiudad());
            txtDescripcionDestino.setText(destino.getDescripcion());

            comboClima.setValue(destino.obtenerClimaCadena().toUpperCase());

            //lista idiomas inicializndo
            listaFotosActuales=destino.getFotos();
            try {
                Image image = new Image(getClass().getResourceAsStream(listaFotosActuales.get(0)));
                mainPicture.setImage(image);
            } catch (Exception e) {
                if (!listaFotosActuales.get(0).equals("")) {
                    Image image = new Image(listaFotosActuales.get(0));
                    mainPicture.setImage(image);
                }
            }


        }else{//si no se tratara de una creacion destino
            btnCrearDestino.setVisible(true);
            btnGuardarDestino.setVisible(false);

            listaFotosActuales = new ArrayList<>();

        }
    }

    public void subirFoto(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de Imagen", "*.jpg", "*.png", "*.jpeg"));

        // Mostrar el cuadro de diálogo para seleccionar un archivo
        File selectedFile = fileChooser.showOpenDialog(ventana);

        if (selectedFile != null) {
            //añadir la foto elegida a la lista actual manejada
            String fotoPath = selectedFile.toURI().toString();
            listaFotosActuales.add(fotoPath);
            actualizarFotos();
        } else {
            ArchivoUtils.mostrarMensaje("ADVERTENCIA", "Entrada no recibida", "No se selecciono ningún archivo", Alert.AlertType.WARNING);
        }
    }

    public void actualizarFotos(){
        try {
            Image image = new Image(getClass().getResourceAsStream(listaFotosActuales.get(0)));
            mainPicture.setImage(image);
        } catch (Exception e) {
            if (!listaFotosActuales.get(0).equals("")) {
                Image image = new Image(listaFotosActuales.get(0));
                mainPicture.setImage(image);
            }
        }
    }

    public void eliminarFoto(){
        if (listaFotosActuales.size()>1){
            listaFotosActuales.remove(indexFotos);
            actualizarFotos();
        }else{
            ArchivoUtils.mostrarMensaje("ERROR", "Error de entrada", "El destino debe de tener al menos  una foto", Alert.AlertType.ERROR);
        }

    }

    public void crearDestino(){

        try {
            Destino destino = agenciaViajes.registrarDestino(
                    txtNombreDestino.getText(),
                    txtCiudadDestino.getText(),
                    txtDescripcionDestino.getText(),
                    (String) comboClima.getSelectionModel().getSelectedItem(),
                    listaFotosActuales);
            ArchivoUtils.mostrarMensaje("Registro Confirmado", "Operación completada", "Se ha registrado correctamente el destino: "+destino.getNombre(), Alert.AlertType.INFORMATION);
            aplicacion.mostrarVentanaGestionDestinos(this.cliente, this.administrador);

        } catch (AtributoVacioException e) {
            ArchivoUtils.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void guardarDestino(){

        try {
            Destino destino = agenciaViajes.editarDestino(
                    this.destino,
                    txtNombreDestino.getText(),
                    txtCiudadDestino.getText(),
                    txtDescripcionDestino.getText(),
                    (String) comboClima.getSelectionModel().getSelectedItem(),
                    listaFotosActuales);
            ArchivoUtils.mostrarMensaje("Edición Confirmada", "Operación completada", "Se ha guardado correctamente el destino: "+destino.getNombre(), Alert.AlertType.INFORMATION);
            aplicacion.mostrarVentanaGestionDestinos(this.cliente, this.administrador);

        } catch (AtributoVacioException | InformacionRepetidaException e) {
            ArchivoUtils.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void pasarFoto(){
        if (indexFotos>=0 && indexFotos!=listaFotosActuales.size()-1){
            this.indexFotos+=1;
            try {
                Image image = new Image(getClass().getResourceAsStream(listaFotosActuales.get(indexFotos)));
                mainPicture.setImage(image);
            } catch (Exception e) {
                if (!listaFotosActuales.get(indexFotos).equals("")) {
                    Image image = new Image(listaFotosActuales.get(indexFotos));
                    mainPicture.setImage(image);
                }
            }
        }else{
            ArchivoUtils.mostrarMensaje("Error", "Máximo valor alcanzado", "Alcanzaste la ultima foto en la lista de este destino", Alert.AlertType.ERROR);
        }
    }

    public void retrocederFoto(){
        if (indexFotos<listaFotosActuales.size() && indexFotos!=0){
            this.indexFotos-=1;
            try {
                Image image = new Image(getClass().getResourceAsStream(listaFotosActuales.get(indexFotos)));
                mainPicture.setImage(image);
            } catch (Exception e) {
                if (!listaFotosActuales.get(indexFotos).equals("")) {
                    Image image = new Image(listaFotosActuales.get(indexFotos));
                    mainPicture.setImage(image);
                }
            }
        }else{
            ArchivoUtils.mostrarMensaje("Error", "Mínimo valor alcanzado", "Alcanzaste la primera foto en la lista de este destino", Alert.AlertType.ERROR);
        }
    }

    public void volver(){
        aplicacion.mostrarVentanaGestionDestinos(this.cliente, this.administrador);
    }




}
