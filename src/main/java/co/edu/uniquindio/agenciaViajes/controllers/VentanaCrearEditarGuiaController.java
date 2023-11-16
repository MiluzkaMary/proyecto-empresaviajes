package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.model.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.fxml.FXML;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


public class VentanaCrearEditarGuiaController implements Initializable {

    @FXML
    private TextField txtEdadGuia;

    @FXML
    private TableView<?> tablaIdiomasGuia;

    @FXML
    private TableColumn<?, ?> columnaIdiomasGuias;

    @FXML
    private Button btnEliminarIdioma;

    @FXML
    private TableColumn<?, ?> columnaIdiomasDisponibles;

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
    private TableView<?> tablaIdiomasDisponibles;

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



            listaTemporalDestinos = paquete.getDestinos(); //inicializacion de la lista destinos
            actualizarTablaDestinosPosibles();
            actualizarTablaDestinosActuales();

        }else{//si no se tratara de una creacion paquete
            btnCrearGestion.setVisible(true);
            btnGuardarGestion.setVisible(false);
            listaTemporalDestinos = new ArrayList<>();
            actualizarTablaDestinosPosibles();
        }
    }



}
