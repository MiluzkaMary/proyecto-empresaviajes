package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.model.Administrador;
import co.edu.uniquindio.agenciaViajes.model.AgenciaViajes;
import co.edu.uniquindio.agenciaViajes.model.Cliente;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import lombok.Data;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private Label lblFecha;

    @FXML
    private Text txtBienvenida;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void cargarInfo(){
        runTime();
        if (cliente!=null) {
            cargarDatosCliente();
        }else{
            cargarDatosAdmin();
        }
    }

    public void salirCuenta(){
        Cliente clienteVacio=null;
        Administrador adminVacio=null;
        aplicacion.mostrarVentanaPrincipal(clienteVacio, adminVacio);
    }

    public void runTime(){
        new Thread(){
            public void run(){
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
                while(true){
                    try{
                        Thread.sleep(1000);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        Date date = new Date(System.currentTimeMillis());
                        lblFecha.setText(format.format(date));
                    });
                }
            }
        }.start();
    }

    public void cargarDatosCliente(){

        try {
            Image image = new Image(getClass().getResourceAsStream(cliente.getFoto()));
            userPic.setImage(image);
        } catch (Exception e) {
            if (!cliente.getFoto().equals("")) {
                Image image = new Image(cliente.getFoto());
                userPic.setImage(image);
            }
        }
        Circle clip = new Circle(userPic.getFitWidth() / 2, userPic.getFitHeight() / 2, userPic.getFitWidth() / 2);
        userPic.setClip(clip);
        String nombre= cliente.getNombre();
        System.out.println("el cliente barra superior"+ cliente.getCedula() );
        int espacio=nombre.length()*15;
        txtBienvenida.setText("¡Bienvenid@, "+nombre+"!");
        txtBienvenida.setX(txtBienvenida.getX()-espacio);
    }

    public void cargarDatosAdmin(){

        try {
            Image image = new Image(getClass().getResourceAsStream(administrador.getFoto()));
            userPic.setImage(image);
        } catch (Exception e) {
            if (!administrador.getFoto().equals("")) {
                Image image = new Image(administrador.getFoto());
                userPic.setImage(image);
            }
        }
        Circle clip = new Circle(userPic.getFitWidth() / 2, userPic.getFitHeight() / 2, userPic.getFitWidth() / 2);
        userPic.setClip(clip);

        /**
         * InputStream inputStream = getClass().getResourceAsStream(administrador.getFoto());
         *         Image image = new Image(inputStream);
         *         userPic.setImage(image);
         *         Circle clip = new Circle(userPic.getFitWidth() / 2, userPic.getFitHeight() / 2, userPic.getFitWidth() / 2);
         *         userPic.setClip(clip);
         */
        String nombre= administrador.getNombre();
        int espacio=nombre.length()*15;
        txtBienvenida.setText("¡Bienvenid@, "+nombre+"!");
        txtBienvenida.setX(txtBienvenida.getX()-espacio);
    }


}
