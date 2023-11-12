package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VentanaGuiaController implements Initializable {

    @FXML
    private Text txtAniosExperiencia;

    @FXML
    private ImageView starFive;

    @FXML
    private Text txtIdiomas;

    @FXML
    private ImageView userPic;

    @FXML
    private Text txtComentarioCliente;

    @FXML
    private Button btnDespuesCalificacion;

    @FXML
    private Button btnAntesCalificacion;

    @FXML
    private Text txtPuntajeCliente;

    @FXML
    private Text txtEdad;

    @FXML
    private ImageView starTwo;

    @FXML
    private ImageView starFour;

    @FXML
    private ImageView fotoGuia;

    @FXML
    private Text txtNombreGuia;

    @FXML
    private Text txtValoracion;

    @FXML
    private Text txtNombreCliente;

    @FXML
    private ImageView starThree;

    @FXML
    private Text txtTelefono;

    @FXML
    private Text txtNumValoraciones;

    @FXML
    private ImageView estrella4Cliente;

    @FXML
    private ImageView estrella3Cliente;

    @FXML
    private ImageView estrella5Cliente;

    @FXML
    private ImageView starOne;

    @FXML
    private ImageView estrella1Cliente;

    @FXML
    private ImageView estrella2Cliente;

    public Aplicacion aplicacion;
    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    private GuiaTuristico guia;
    private List<Valoracion>comentarios;
    private int indexComentarios=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void cargarDatos(GuiaTuristico guiaTuristico){
        this.guia=guiaTuristico;
        this.comentarios=guia.getValoraciones();

        txtNombreGuia.setText(guiaTuristico.getNombre());
        txtEdad.setText(guiaTuristico.getEdad()+" años");
        txtAniosExperiencia.setText(guiaTuristico.getAniosExperiencia()+" años");
        txtTelefono.setText("+57 "+guiaTuristico.getTelefono());
        txtIdiomas.setText("                     "+String.join(", ", guiaTuristico.getListaIdiomas()));
        txtValoracion.setText(String.valueOf(guiaTuristico.obtenerPromedioValoracion()));
        txtNumValoraciones.setText("("+guiaTuristico.obtenerNumValoraciones()+") calificaciones");
        try {
            Image image = new Image(getClass().getResourceAsStream(guiaTuristico.getFoto()));
            fotoGuia.setImage(image);
        } catch (Exception e) {
            if (!guiaTuristico.getFoto().equals("")) {
                Image image = new Image(guiaTuristico.getFoto());
                fotoGuia.setImage(image);
            }
        }

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


        }

    }
}
