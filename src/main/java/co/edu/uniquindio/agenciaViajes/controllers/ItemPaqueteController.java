package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.model.Administrador;
import co.edu.uniquindio.agenciaViajes.model.AgenciaViajes;
import co.edu.uniquindio.agenciaViajes.model.Cliente;
import co.edu.uniquindio.agenciaViajes.model.Paquete;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import lombok.Data;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
@Data

public class ItemPaqueteController implements Initializable {
    public Aplicacion aplicacion;
    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    private Paquete paquete;
    private Cliente cliente;
    private Administrador administrador;
    private boolean esGestion=false;
    private MyListenerPaquete myListenerPaquete;

    @FXML
    private Button btnGestionar;

    @FXML
    private ImageView starTwo;

    @FXML
    private ImageView starFour;

    @FXML
    private Text txtValoracion;

    @FXML
    private ImageView starFive;

    @FXML
    private ImageView starThree;

    @FXML
    private Text txtNumValoraciones;

    @FXML
    private ImageView mainPicture;

    @FXML
    private Text txtDias;

    @FXML
    private Text txtPrecio;

    @FXML
    private Text txtTitulo;

    @FXML
    private ImageView starOne;

    @FXML
    private Text txtDescripcion;

    @FXML
    private Button btnDetalles;

    @FXML
    private Text txtDestinos;

    @FXML
    private Text lblDestino;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Método invocado cuando se hace clic en el elemento.
     * Notifica al listener de clic con el vehículo asociado.
     */
    @FXML
    private void click() {
        myListenerPaquete.onClickListener(paquete);

    }

    public void cargarDatos(Paquete paquete){
        this.paquete=paquete;
        txtTitulo.setText(paquete.getNombre());
        txtDias.setText(String.valueOf("Duración: "+paquete.getDiasDuracion())+" días");
        txtValoracion.setText(String.valueOf(paquete.obtenerPromedioValoraciones()));
        txtNumValoraciones.setText(String.valueOf("("+paquete.obtenerNumValoraciones()+")"));
        String valorPrecioCadena = String.format("%,.0f", paquete.getPrecio());
        txtPrecio.setText("Desde "+ valorPrecioCadena+"COL$ por persona");
        txtDescripcion.setText(paquete.getDescripcion());
        txtDestinos.setText("                "+String.join(", ", paquete.obtenerNombresDestinos()));
        String foto=paquete.getDestinos().get(1).getFotos().get(1);
        //se toma la primera foto del primer destino que tiene el paquete (edit: ahora sera random la foto entre
        //los destinos q tenga el paquete
        try {
            Image image = new Image(getClass().getResourceAsStream(foto));
            mainPicture.setImage(image);
        } catch (Exception e) {
            if (!foto.isEmpty()) {
                Image image = new Image(foto);
                mainPicture.setImage(image);
            }
        }

        if (esGestion){
            btnDetalles.setVisible(false);
            btnGestionar.setVisible(true);
        }


        /**
         * InputStream inputStream = getClass().getResourceAsStream("/imagenes/chichenitza.png");
         *         Image image = new Image(inputStream);
         *         mainPicture.setImage(image);
         */
    }


    public void mostrarDetallesPaquete(){
        aplicacion.mostrarDetallePaquete(this.paquete, this.cliente);
    }
}
