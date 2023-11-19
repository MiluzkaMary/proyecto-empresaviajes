package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.enums.EstadoReserva;
import co.edu.uniquindio.agenciaViajes.exceptions.*;
import co.edu.uniquindio.agenciaViajes.model.*;
import co.edu.uniquindio.agenciaViajes.util.ArchivoUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import lombok.Data;

@Data

public class VentanaDetallesPaqueteController implements Initializable {

    @FXML
    private ImageView starFive;

    @FXML
    private Button btnReservar;

    @FXML
    private Text txtCupoDisponible;

    @FXML
    private TableColumn<Destino, String> columnaDisponiblesNombres;

    @FXML
    private TableColumn<Destino, String> columnaDisponiblesCiudad;

    @FXML
    private ImageView mainPicture;

    @FXML
    private Text txtPrecio;

    @FXML
    private Pane panelInferiorGestion;

    @FXML
    private TextField txtCuposGestion;

    @FXML
    private TableColumn<Destino, String> columnaValoracion;

    @FXML
    private TextArea txtAreaDescripcionGestion;

    @FXML
    private Text txtCupoMaximo;

    @FXML
    private Text txtNumValoraciones;

    @FXML
    private TableColumn<Destino, String> columnaDisponiblesValoraciones;

    @FXML
    private Text txtDias;

    @FXML
    private DatePicker txtFecha;

    @FXML
    private Text txtDescripcion;

    @FXML
    private ImageView starOne;

    @FXML
    private TextField txtNombreGestion;

    @FXML
    private TextField txtPrecioGestion;

    @FXML
    private DatePicker txtPickerDateGestion;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button btnVolverGestion;

    @FXML
    private Button btnAgregarDestinoGestion;

    @FXML
    private TableView<Destino> tablaDisponiblesAgencia;

    @FXML
    private ImageView starTwo;

    @FXML
    private ImageView starFour;

    @FXML
    private TableColumn<Destino, String> columnaCiudad;

    @FXML
    private Text txtValoracion;

    @FXML
    private Pane panelDetallesSuperior;

    @FXML
    private Text labelListaDestinos;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView starThree;

    @FXML
    private Button btnGuardarGestion;

    @FXML
    private Button btnCrearGestion;

    @FXML
    private TableColumn<Destino, String> columnaDisponiblesClima;

    @FXML
    private TableColumn<Destino, String> columnaNombre;

    @FXML
    private TableColumn<Destino, String> columnaClima;

    @FXML
    private Text txtTitulo;

    @FXML
    private TextField txtDiasGestion;

    @FXML
    private TableView<Destino> tablaDestinosActules;

    @FXML
    private Button btnEliminarDestinoGestion;

    @FXML
    private Pane panelSuperiorGestion;



    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    public Aplicacion aplicacion;
    public Cliente cliente;
    public Administrador administrador;
    public Paquete paquete;
    public List<Destino> listaTemporalDestinos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void iniciarDatosDetallesPaquete(){

        //ocultarOtrospaneles
        panelSuperiorGestion.setVisible(false);
        panelInferiorGestion.setVisible(false);

        panelDetallesSuperior.setVisible(true);
        labelListaDestinos.setVisible(true);
        scroll.setVisible(true);

        txtDias.setText(String.valueOf(paquete.getDiasDuracion()));
        txtTitulo.setText(paquete.getNombre());
        txtCupoMaximo.setText(String.valueOf(paquete.getCupoMaximo()));
        int unidadesDisponibles=agenciaViajes.buscarUnidadesDisponibles(paquete, EstadoReserva.PENDIENTE, 0);
        txtCupoDisponible.setText(String.valueOf(unidadesDisponibles));
        txtValoracion.setText(String.valueOf(paquete.obtenerPromedioValoraciones()));
        txtNumValoraciones.setText(String.valueOf("("+paquete.obtenerNumValoraciones()+")"));
        String valorPrecioCadena = String.format("%,.0f", paquete.getPrecio());
        txtPrecio.setText(valorPrecioCadena);
        txtDescripcion.setText(paquete.getDescripcion());
        txtFecha.setValue(paquete.getFecha());

        String foto=paquete.getDestinos().get(1).getFotos().get(1);
        //se toma la segunda foto del segundo destino del paquete
        try {
            Image image = new Image(getClass().getResourceAsStream(foto));
            mainPicture.setImage(image);
        } catch (Exception e) {
            if (!foto.isEmpty()) {
                Image image = new Image(foto);
                mainPicture.setImage(image);
            }
        }

        if (cliente==null){
            btnReservar.setVisible(false);
        }
    }

    public void iniciarGridPaneDetallesPaquete() {
        int column = 0;
        int row = 1;
        List<Destino> listaDestinos= paquete.getDestinos();
        try {
            for (int i = 0; i < listaDestinos.size(); i++) { // Cambio de 6 a 1 para mostrar solo una columna
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/ItemDestino.fxml"));
                Pane pane = loader.load();

                ItemDestinoController controlador = loader.getController();
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

    public void mostrarPanelReserva(){
        if (cliente!=null){
            aplicacion.mostrarVentanaReserva(this.paquete, this.cliente);
        }else{
            ArchivoUtils.mostrarMensaje("Error", "Usuario no valido", "Por favor registrarse o ingresar a su cuenta para reservar un paquete", Alert.AlertType.ERROR);
        }
    }

    public void iniciarDatosCrearEditar(){
        if (paquete!=null){ // si el paquete existe se tratara de una edicion paquete
            txtNombreGestion.setText(paquete.getNombre());
            txtPickerDateGestion.setValue(paquete.getFecha());
            String valorPrecioCadena = String.format("%,.0f", paquete.getPrecio());
            txtPrecioGestion.setText(valorPrecioCadena);
            txtCuposGestion.setText(String.valueOf(paquete.getCupoMaximo()));
            txtDiasGestion.setText(String.valueOf(paquete.getDiasDuracion()));
            txtAreaDescripcionGestion.setText(paquete.getDescripcion());

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

    public void actualizarTablaDestinosActuales(){
        tablaDestinosActules.getItems().clear();
        ObservableList<Destino> listaDestinosActualesProperty= FXCollections.observableArrayList();
        listaDestinosActualesProperty.addAll(listaTemporalDestinos);

        tablaDestinosActules.setItems(listaDestinosActualesProperty);
        columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnaCiudad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCiudad()));
        columnaClima.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().obtenerClimaCadena()));
        columnaValoracion.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().obtenerPromedioValoracion())));
    }

    public void actualizarTablaDestinosPosibles(){
        tablaDisponiblesAgencia.getItems().clear();
        ObservableList<Destino> listaDestinosPosiblesProperty= FXCollections.observableArrayList();
        ArrayList<Destino> listaDestinosPosibles= agenciaViajes.obtenerDestinosPermitidos(listaTemporalDestinos, 0, new ArrayList<>());
        for (Destino destino : listaDestinosPosibles) {
            listaDestinosPosiblesProperty.add(destino);
        }

        tablaDisponiblesAgencia.setItems(listaDestinosPosiblesProperty);
        columnaDisponiblesNombres.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnaDisponiblesCiudad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCiudad()));
        columnaDisponiblesClima.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().obtenerClimaCadena()));
        columnaDisponiblesValoraciones.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().obtenerPromedioValoracion())));
    }

    public void agregarDestinoNuevo(){
        Destino destinoElegido = tablaDisponiblesAgencia.getSelectionModel().getSelectedItem();
        if (destinoElegido!=null){
            listaTemporalDestinos.add(destinoElegido);
            actualizarTablaDestinosPosibles();
            actualizarTablaDestinosActuales(); //para mostrar el nuevo destino
        }else{
            ArchivoUtils.mostrarMensaje("Error", "Entrada no valida", "Debe elegir un destino", Alert.AlertType.ERROR);

        }
    }

    public void eliminarDestino(){
        Destino destinoElegido = tablaDestinosActules.getSelectionModel().getSelectedItem();
        if (destinoElegido!=null){
            listaTemporalDestinos.remove(destinoElegido);
            actualizarTablaDestinosActuales();
            actualizarTablaDestinosPosibles(); //para mostrar como posible el q fue eliminado
        }else{
            ArchivoUtils.mostrarMensaje("Error", "Entrada no valida", "Debe elegir un destino", Alert.AlertType.ERROR);

        }
    }

    public void volver(){
        aplicacion.motrarVentanaGestionPaquetes(this.cliente, this.administrador);
    }

    public void guardarCambiosPaquete(){
        try {
            Paquete paquete = agenciaViajes.editarPaquete(
                    this.paquete,
                    txtNombreGestion.getText(),
                    listaTemporalDestinos,
                    txtDiasGestion.getText(),
                    txtAreaDescripcionGestion.getText(),
                    txtPrecioGestion.getText(),
                    txtCuposGestion.getText(),
                    txtPickerDateGestion.getValue()
            );
            ArchivoUtils.mostrarMensaje("Edición Confirmada", "Operación completada", "Se ha editado correctamente el paquete: "+paquete.getNombre(), Alert.AlertType.INFORMATION);
            aplicacion.motrarVentanaGestionPaquetes(this.cliente, this.administrador);

        } catch (AtributoVacioException | DatoNoNumericoException | FechaInvalidaException e) {
            ArchivoUtils.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void crearPaquete(){
        try {
            Paquete paquete = agenciaViajes.crearPaquete(
                    txtNombreGestion.getText(),
                    listaTemporalDestinos,
                    txtDiasGestion.getText(),
                    txtAreaDescripcionGestion.getText(),
                    txtPrecioGestion.getText(),
                    txtCuposGestion.getText(),
                    txtPickerDateGestion.getValue()
                    );
            ArchivoUtils.mostrarMensaje("Registro Confirmado", "Operación completada", "Se ha registrado correctamente el paquete: "+paquete.getNombre(), Alert.AlertType.INFORMATION);
            aplicacion.motrarVentanaGestionPaquetes(this.cliente, this.administrador);

        } catch (AtributoVacioException  | DatoNoNumericoException | FechaInvalidaException e) {
            ArchivoUtils.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
