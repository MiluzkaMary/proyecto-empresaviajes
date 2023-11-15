package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.model.Administrador;
import co.edu.uniquindio.agenciaViajes.model.AgenciaViajes;
import co.edu.uniquindio.agenciaViajes.model.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import lombok.Data;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
@Data

public class BarraClienteController implements Initializable {
    public Aplicacion aplicacion;
    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    public Cliente cliente;
    public Administrador administrador;

    @FXML
    private ImageView userPic;

    @FXML
    private Text txtBienvenida;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void cargarInfo(){
        if (cliente!=null) {
            cargarDatosCliente();
        }else{
            cargarDatosAdmin();
        }
    }

    public void cargarDatosCliente(){
        InputStream inputStream = getClass().getResourceAsStream(cliente.getFoto());
        Image image = new Image(inputStream);
        userPic.setImage(image);
        Circle clip = new Circle(userPic.getFitWidth() / 2, userPic.getFitHeight() / 2, userPic.getFitWidth() / 2);
        userPic.setClip(clip);
        String nombre= cliente.getNombre();
        int espacio=nombre.length()*15;
        txtBienvenida.setText("¡Bienvenid@, "+nombre+"!");
        txtBienvenida.setX(txtBienvenida.getX()-espacio);
    }

    public void cargarDatosAdmin(){
        InputStream inputStream = getClass().getResourceAsStream(administrador.getFoto());
        Image image = new Image(inputStream);
        userPic.setImage(image);
        Circle clip = new Circle(userPic.getFitWidth() / 2, userPic.getFitHeight() / 2, userPic.getFitWidth() / 2);
        userPic.setClip(clip);
        String nombre= administrador.getNombre();
        int espacio=nombre.length()*15;
        txtBienvenida.setText("¡Bienvenid@, "+nombre+"!");
        txtBienvenida.setX(txtBienvenida.getX()-espacio);
    }


}
