package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.exceptions.AtributoVacioException;
import co.edu.uniquindio.agenciaViajes.model.*;
import co.edu.uniquindio.agenciaViajes.util.ArchivoUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lombok.Data;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
@Data

public class VentanaGestionReservaController implements Initializable {

    @FXML
    private Button btnCalificarDestino;

    @FXML
    private Text txtFechaPaquete;

    @FXML
    private Text txtUnidades;

    @FXML
    private Button btnGuardarCalificacion;

    @FXML
    private ComboBox<String> comboCalificarGuia;

    @FXML
    private Text txtTotal;

    @FXML
    private TableView <Destino>tablaDestinos;

    @FXML
    private ImageView imagenPaquete;

    @FXML
    private Text txtNombrePaquete;

    @FXML
    private Button btnVolver;

    @FXML
    private TextArea txtAreaComentario;

    @FXML
    private ComboBox<String> comboCalificarDestino;

    @FXML
    private Text txtEstado;

    @FXML
    private Text txtTitulo11;

    @FXML
    private Text txtCupos;

    @FXML
    private TableColumn<Reserva, String> columnaFechaReserva;

    @FXML
    private TableColumn<Destino, String> columnaNombreDestino;

    @FXML
    private Button btnCancelarReserva;

    @FXML
    private Button btnVerReserva;

    @FXML
    private TableColumn<Destino, String> columnaClimaDestino;

    @FXML
    private Text txtNombreGuia;

    @FXML
    private Button btnCalificar;

    @FXML
    private TableColumn<Reserva, String> columnaNombrePaquete;

    @FXML
    private TableView<Reserva> tablaReservas;

    @FXML
    private TableColumn<Reserva, String> columnaEstado;

    @FXML
    private Text txtFechaReserva;

    @FXML
    private Pane paneReservaDetalles;

    @FXML
    private Button btnCalificarGuia;

    @FXML
    private Pane panelCalificaciones;

    @FXML
    private ImageView backgroundReserva;

    public Reserva chosenReserva;

    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    public Aplicacion aplicacion;
    public Cliente cliente;
    public Administrador administrador;

    public ArrayList<Destino> listaDestinosActuales;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void iniciarDatos(){
        listaDestinosActuales=new ArrayList<>();
        iniciarTablaReservas();
    }

    public void volver(){
        aplicacion.mostrarVentanaPrincipal(this.cliente, this.administrador);
    }

    public void iniciarTablaReservas(){



        tablaReservas.getItems().clear();
        ObservableList<Reserva> listaReservasProperty= FXCollections.observableArrayList();
        ArrayList<Reserva> listaReservas= agenciaViajes.obtenerReservasDeCliente(this.cliente);
        listaReservasProperty.addAll(listaReservas);

        tablaReservas.setItems(listaReservasProperty);

        columnaFechaReserva.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().obtenerFechaSolicitudComoString()));
        columnaNombrePaquete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaquete().getNombre()));
        columnaEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().obtenerEstadoCadena()));
    }

    public void verReserva(){
        Reserva reservaElegida = tablaReservas.getSelectionModel().getSelectedItem();

        if (listaDestinosActuales.isEmpty()){
            if (reservaElegida!=null){
                this.chosenReserva=reservaElegida;
                iniciarPanelReservaDetalles(reservaElegida);
                panelCalificaciones.setVisible(false);
                paneReservaDetalles.setVisible(true);
                backgroundReserva.setVisible(false);
            }else{
                ArchivoUtils.mostrarMensaje("ERROR", "Error de Entrada", "Tiene que seleccionar una reserva", Alert.AlertType.INFORMATION);
            }
        }else{
            ArchivoUtils.mostrarMensaje("ERROR", "Error de Entrada", "Tiene que terminar de calificar todos los destinos y al guia", Alert.AlertType.INFORMATION);

        }
    }

    public void iniciarPanelReservaDetalles(Reserva reservaElegida){
        //foto paquete detallles
        Paquete paqueteElegido=reservaElegida.getPaquete();
        String foto=paqueteElegido.getDestinos().get(0).getFotos().get(0);
        //se toma la primera foto del primer destino que tiene el paquete
        try {
            Image image = new Image(getClass().getResourceAsStream(foto));
            imagenPaquete.setImage(image);
        } catch (Exception e) {
            if (!foto.isEmpty()) {
                Image image = new Image(foto);
                imagenPaquete.setImage(image);
            }
        }

        txtNombrePaquete.setText(paqueteElegido.getNombre());
        txtFechaPaquete.setText(paqueteElegido.obtenerFechaComoString());
        txtUnidades.setText(String.valueOf(reservaElegida.getNumPersonas()));
        txtTotal.setText(String.valueOf(reservaElegida.getValorTotal()));
        txtFechaReserva.setText(reservaElegida.obtenerFechaSolicitudComoString());
        txtNombreGuia.setText(reservaElegida.getGuia().getNombre());
        txtEstado.setText(reservaElegida.obtenerEstadoCadena());

        if (reservaElegida.obtenerEstadoCadena().equals("Confirmada")){
            btnCancelarReserva.setVisible(false);
            btnCalificar.setVisible(true);
        }else if (reservaElegida.obtenerEstadoCadena().equals("Cancelada")){
            btnCancelarReserva.setVisible(false);
            btnCalificar.setVisible(false);
        }else if (reservaElegida.obtenerEstadoCadena().equals("Pendiente")){
            btnCancelarReserva.setVisible(true);
            btnCalificar.setVisible(false);

        }

    }

    public void iniciarPanelCalificar(){
        if (listaDestinosActuales.size()>=1){
            paneReservaDetalles.setVisible(false);
            panelCalificaciones.setVisible(true);
            tablaDestinos.getItems().clear();
            ObservableList<Destino> listaDestinosProperty= FXCollections.observableArrayList();
            ArrayList<Destino> listaDestinos= chosenReserva.getPaquete().getDestinos();
            this.listaDestinosActuales=listaDestinos;
            listaDestinosProperty.addAll(listaDestinos);

            tablaDestinos.setItems(listaDestinosProperty);

            columnaNombreDestino.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
            columnaClimaDestino.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().obtenerClimaCadena()));

        }else{
            panelCalificaciones.setVisible(false);
            backgroundReserva.setVisible(true);
        }
    }

    public void calificarGuia(){
        try {
            agenciaViajes.calificarGuia(
                    chosenReserva.getGuia(),
                    comboCalificarGuia.getSelectionModel().getSelectedItem(),
                    this.cliente,
                    txtAreaComentario.getText());
        } catch (AtributoVacioException e) {
            ArchivoUtils.mostrarMensaje("ERROR", "Error de Entrada", e.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    public void calificarDestino() {
        Destino destinoElegido= tablaDestinos.getSelectionModel().getSelectedItem();
        if (destinoElegido!=null){
            try{
                agenciaViajes.calificarDestino(
                        destinoElegido,
                        comboCalificarDestino.getSelectionModel().getSelectedItem(),
                        this.cliente,
                        txtAreaComentario.getText());
                listaDestinosActuales.remove(destinoElegido);
                iniciarPanelCalificar();
            }catch (AtributoVacioException e) {
                ArchivoUtils.mostrarMensaje("ERROR", "Error de Entrada", e.getMessage(), Alert.AlertType.INFORMATION);
            }
        }else{
            ArchivoUtils.mostrarMensaje("ERROR", "Error de Entrada", "Tiene que seleccionar una destino", Alert.AlertType.INFORMATION);
        }
    }

    public void iniciarComboBoxes(){
        ObservableList<String> opciones= FXCollections.observableArrayList(
                "1",
                "2",
                "3",
                "4",
                "5"
        );
        comboCalificarGuia.setItems(opciones);
        comboCalificarDestino.setItems(opciones);
    }



    public void cancelarReserva(){
        if (chosenReserva!=null){
            imagenPaquete.setImage(null);
            panelCalificaciones.setVisible(false);
            backgroundReserva.setVisible(true);
            agenciaViajes.cancelarEstadoReserva(chosenReserva);
            iniciarTablaReservas();
        }
    }

    public void calificar(){
        System.out.println("el nombre del paquete seleccionado es: " +chosenReserva.getPaquete().getNombre());
        if (chosenReserva!=null){
            backgroundReserva.setVisible(false);
            paneReservaDetalles.setVisible(false);
            panelCalificaciones.setVisible(true);
            listaDestinosActuales=chosenReserva.getPaquete().getDestinos();
            iniciarPanelCalificar();
            iniciarComboBoxes();
        }
    }
}
