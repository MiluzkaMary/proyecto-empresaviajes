package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.model.*;
import co.edu.uniquindio.agenciaViajes.util.ArchivoUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import lombok.Data;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
@Data

public class ItemDestinoController implements Initializable {

    @FXML
    private ImageView starFive;

    @FXML
    private ImageView userPic;

    @FXML
    private Text txtComentarioCliente;

    @FXML
    private Button btnDespuesCalificacion;

    @FXML
    private Button btnAntesCalificacion;

    @FXML
    private Button btnAntesFoto;

    @FXML
    private ImageView mainPicture;

    @FXML
    private Text txtCiudadNombre;

    @FXML
    private Text txtNombreCliente;

    @FXML
    private Text txtNumValoraciones;

    @FXML
    private ImageView estrella4Cliente;

    @FXML
    private Text txtDescripcion;

    @FXML
    private ImageView starOne;

    @FXML
    private ImageView estrella3Cliente;

    @FXML
    private ImageView estrella5Cliente;

    @FXML
    private ImageView estrella1Cliente;

    @FXML
    private ImageView estrella2Cliente;

    @FXML
    private Text txtPuntajeCliente;

    @FXML
    private Text txtClima;

    @FXML
    private ImageView starTwo;

    @FXML
    private ImageView starFour;

    @FXML
    private Text txtValoracion;

    @FXML
    private Button btnDespuesFoto;

    @FXML
    private Text txtTituloDestino;

    @FXML
    private ImageView starThree;

    @FXML
    private Button btnGestionar;

    //listener
    private boolean esGestion=false;
    private MyListenerDestino myListenerDestino;

    public Administrador administrador;
    public Aplicacion aplicacion;
    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    private Destino destino;
    private List<String> fotos;
    private List<Valoracion>comentarios;
    private int indexFotos=0;
    private int indexComentarios=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Método invocado cuando se hace clic en el elemento.
     * Notifica al listener de clic con el destino asociado.
     */
    @FXML
    private void click() {
        myListenerDestino.onClickListener(destino);

    }

    public void cargarDatos(Destino destino){
        this.destino=destino;
        this.comentarios=destino.getValoraciones();
        txtTituloDestino.setText(destino.getNombre());
        txtCiudadNombre.setText(destino.getCiudad());
        txtClima.setText(destino.obtenerClimaCadena());
        txtDescripcion.setText("                         "+destino.getDescripcion());
        txtValoracion.setText(String.valueOf(destino.obtenerPromedioValoracion()));
        txtNumValoraciones.setText("("+destino.obtenerNumValoraciones()+") calificaciones");
        fotos=destino.getFotos();
        try {
            Image image = new Image(getClass().getResourceAsStream(fotos.get(indexFotos)));
            mainPicture.setImage(image);
        } catch (Exception e) {
            if (!fotos.get(indexFotos).equals("")) {
                Image image = new Image(fotos.get(indexFotos));
                mainPicture.setImage(image);
            }
        }

        //solo si el destino tiene comentarios
        if (!comentarios.isEmpty()){
            Cliente clienteComentario=comentarios.get(indexComentarios).getCliente();
            txtNombreCliente.setText(clienteComentario.getNombre());
            txtComentarioCliente.setText((comentarios.get(indexComentarios)).getComentario());
            txtPuntajeCliente.setText(String.valueOf((comentarios.get(indexComentarios)).getPuntuacion()));
            try {
                Image image = new Image(getClass().getResourceAsStream(clienteComentario.getFoto()));
                userPic.setImage(image);
            } catch (Exception e) {
                if (!clienteComentario.getFoto().equals("")) {
                    Image image = new Image(clienteComentario.getFoto());
                    userPic.setImage(image);
                }
            }
            Circle clip = new Circle(userPic.getFitWidth() / 2, userPic.getFitHeight() / 2, userPic.getFitWidth() / 2);
            userPic.setClip(clip);
        }else{
            Image image = new Image ("/imagenes/user.png");
            userPic.setImage(image);
            txtNombreCliente.setText("No hay calificaciones a este destino");
            txtComentarioCliente.setText("No hay comentarios");
            txtPuntajeCliente.setText("0.0");
            btnAntesCalificacion.setVisible(false);
            btnDespuesCalificacion.setVisible(false);
        }

        if (esGestion){
            btnGestionar.setVisible(true);
        }
    }

    public void pasarFoto(){
        if (indexFotos>=0 && indexFotos!=fotos.size()-1){
            this.indexFotos+=1;
            try {
                Image image = new Image(getClass().getResourceAsStream(fotos.get(indexFotos)));
                mainPicture.setImage(image);
            } catch (Exception e) {
                if (!fotos.get(indexFotos).equals("")) {
                    Image image = new Image(fotos.get(indexFotos));
                    mainPicture.setImage(image);
                }
            }
        }else{
            ArchivoUtils.mostrarMensaje("Error", "Máximo valor alcanzado", "Alcanzaste la ultima foto en la lista de este destino", Alert.AlertType.ERROR);
        }
    }

    public void retrocederFoto(){
        if (indexFotos<fotos.size() && indexFotos!=0){
            this.indexFotos-=1;
            try {
                Image image = new Image(getClass().getResourceAsStream(fotos.get(indexFotos)));
                mainPicture.setImage(image);
            } catch (Exception e) {
                if (!fotos.get(indexFotos).equals("")) {
                    Image image = new Image(fotos.get(indexFotos));
                    mainPicture.setImage(image);
                }
            }
        }else{
            ArchivoUtils.mostrarMensaje("Error", "Mínimo valor alcanzado", "Alcanzaste la primera foto en la lista de este destino", Alert.AlertType.ERROR);
        }
    }

    public void pasarComentario(){

        if (indexComentarios>=0 && indexComentarios!=comentarios.size()-1){
            this.indexComentarios+=1;

            Cliente clienteComentario=comentarios.get(indexComentarios).getCliente();
            txtNombreCliente.setText(clienteComentario.getNombre());
            txtComentarioCliente.setText((comentarios.get(indexComentarios)).getComentario());
            txtPuntajeCliente.setText(String.valueOf((comentarios.get(indexComentarios)).getPuntuacion()));
            try {
                Image image = new Image(getClass().getResourceAsStream(clienteComentario.getFoto()));
                userPic.setImage(image);
            } catch (Exception e) {
                if (!clienteComentario.getFoto().equals("")) {
                    Image image = new Image(clienteComentario.getFoto());
                    userPic.setImage(image);
                }
            }
            Circle clip = new Circle(userPic.getFitWidth() / 2, userPic.getFitHeight() / 2, userPic.getFitWidth() / 2);
            userPic.setClip(clip);
        }else{
            ArchivoUtils.mostrarMensaje("Error", "Máximo valor alcanzado", "Alcanzaste el ultimo comentario calificado para este destino", Alert.AlertType.ERROR);
        }

    }

    public void retrocederComentario(){

        if (indexComentarios<comentarios.size() && indexComentarios!=0){
            this.indexComentarios-=1;

            Cliente clienteComentario=comentarios.get(indexComentarios).getCliente();
            txtNombreCliente.setText(clienteComentario.getNombre());
            txtComentarioCliente.setText((comentarios.get(indexComentarios)).getComentario());
            txtPuntajeCliente.setText(String.valueOf((comentarios.get(indexComentarios)).getPuntuacion()));
            try {
                Image image = new Image(getClass().getResourceAsStream(clienteComentario.getFoto()));
                userPic.setImage(image);
            } catch (Exception e) {
                if (!clienteComentario.getFoto().equals("")) {
                    Image image = new Image(clienteComentario.getFoto());
                    userPic.setImage(image);
                }
            }
            Circle clip = new Circle(userPic.getFitWidth() / 2, userPic.getFitHeight() / 2, userPic.getFitWidth() / 2);
            userPic.setClip(clip);


        }else{
            ArchivoUtils.mostrarMensaje("Error", "Mínimo valor alcanzado", "Alcanzaste el primer comentario calificado para este destino", Alert.AlertType.ERROR);

        }

    }

}
