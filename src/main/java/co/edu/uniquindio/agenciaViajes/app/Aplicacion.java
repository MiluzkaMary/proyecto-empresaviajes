package co.edu.uniquindio.agenciaViajes.app;

import co.edu.uniquindio.agenciaViajes.controllers.VentanaCambiarContraseniaController;
import co.edu.uniquindio.agenciaViajes.controllers.VentanaInicioController;
import co.edu.uniquindio.agenciaViajes.controllers.VentanaRecuperarContraseniaController;
import co.edu.uniquindio.agenciaViajes.controllers.VentanaRegistroIngresoController;
import co.edu.uniquindio.agenciaViajes.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;
@Data

public class Aplicacion extends Application {

    private Stage stage;
    private AnchorPane rootLayout;

    public VentanaInicioController ventanaInicioController;
    public VentanaRegistroIngresoController ventanaRegistroIngresoController;
    //para modificar el controlador ventanaInicio desde otros controladores de otras ventanas



    @Override
    public void start(Stage stage) throws Exception {

        Cliente cliente=null;
        Administrador admin=null;

        this.stage=stage;
        this.stage.setTitle("Travel Dreams");
        this.stage.setResizable(false); //para prohibir al usuario maximizar la ventana
        mostrarVentanaPrincipal(cliente, admin);

    }

    public void mostrarVentanaDestinos(){
        ventanaInicioController.mostrarPanelDerechoDestinos();
    }

    public void mostrarVentanaGuias(){
        ventanaInicioController.mostrarPanelDerechoGuias();
    }

    public void motrarVentanaPaquetes(Cliente cliente, Administrador administrador){
        ventanaInicioController.mostrarPanelDerechoPaquetes(cliente, administrador);
    }

    public void mostrarVentanaReserva(Paquete paquete, Cliente cliente){
        ventanaInicioController.mostrarPanelDerechoReservas(paquete, cliente);
    }

    public void mostrarDetallePaquete(Paquete paquete, Cliente cliente){
        ventanaInicioController.mostrarPanelDerechoDetallesPaquete(paquete, cliente);
    }

    public void motrarVentanaGestionPaquetes(Cliente cliente, Administrador administrador){
        ventanaInicioController.mostrarPanelDerechoGestionarPaquetes(cliente, administrador);
    }

    public void mostrarVentanaGestionDestinos(Cliente cliente, Administrador administrador){
        ventanaInicioController.mostrarPanelDerechoGestionarDestinos(cliente, administrador);
    }

    public void mostrarVentanaGestionGuias(Cliente cliente, Administrador administrador){
        ventanaInicioController.mostrarPanelDerechoGestionGuias(cliente, administrador);
    }

    public void mostrarVentanaCrearEditarPaquete(Paquete paquete, Administrador administrador){
        ventanaInicioController.mostrarPanelDerechoCrearEditarPaquete(paquete, administrador);
    }

    public void mostrarVentanaCrearEditarGuia(GuiaTuristico guia, Administrador administrador){
        ventanaInicioController.mostrarPanelDerechoCrearEditarGuia(guia, administrador);
    }

    public void mostrarVentanaCrearEditarDestino(Destino destino, Administrador administrador){
        ventanaInicioController.mostrarPanelDerechoCrearEditarDestino(destino, administrador);
    }

    public void mostrarVentanaEstadisticas(){
        ventanaInicioController.mostrarPanelDerechoEstadisticas();
    }

    public void mostrarVentanaPrincipal(Cliente cliente, Administrador admin){
        try{
            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/ventanas/VentanaInicio.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            VentanaInicioController controlador = loader.getController();
            ventanaInicioController = loader.getController(); //obteniendo valor editable del controlador
            controlador.setAplicacion(this);
            controlador.setVentana(stage);
            controlador.quemarDatosPrueba();



            //activa los paneles para un usuario que no es cliente ni admin
            if (cliente == null && admin == null) {
                controlador.mostrarPanelIzquierdo();
                controlador.mostrarPanelDerechoPaquetes(cliente, admin);
                controlador.mostrarBarraSuperior();
            }

            //activa los paneles para un cliente
            if (cliente!=null && admin == null) {
                controlador.setCliente(cliente);
                controlador.mostrarPanelIzquierdoCliente();
                controlador.mostrarPanelDerechoPaquetes(cliente, admin);
                controlador.mostrarBarraSuperiorCliente();
                System.out.println("se mando al cliente"+ cliente.getCedula());
            }

            //activa los paneles para un administrador
            if (cliente==null && admin != null) {
                controlador.setAdministrador(admin);
                controlador.mostrarPanelIzquierdoAdmin();
                controlador.mostrarPanelDerechoGestionarPaquetes(cliente, admin);
                controlador.mostrarBarraSuperiorAdmin();

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicia la Ventana de Registro e Ingreso
     */
    public void mostrarVentanaRegistroIngreso(Aplicacion aplicacion) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Aplicacion.class.getResource("/ventanas/VentanaRegistroIngreso.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.setTitle("Travel Dreams | Inicio de Sesion");
            stage.setResizable(false);
            stage.show();
            VentanaRegistroIngresoController controlador = loader.getController();
            ventanaRegistroIngresoController = loader.getController(); //obteniendo valor del controlador editable
            controlador.setVentana(stage);
            controlador.setAplicacion(aplicacion);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Inicia la Ventana de Recuperar contrasenia
     */
    public void ventanaRecuperarContrasenia(Aplicacion aplicacion) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Aplicacion.class.getResource("/ventanas/VentanaRecuperarContrasenia.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.setTitle("Travel Dreams | Recuperar Contraseña");
            stage.setResizable(false);
            stage.show();

            VentanaRecuperarContraseniaController controlador = loader.getController();
            controlador.setVentana(stage);
            controlador.setAplicacion(aplicacion);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ventanaCambiarContrasenia(Aplicacion aplicacion, String code, String correo) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Aplicacion.class.getResource("/ventanas/VentanaCambiarContrasenia.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.setTitle("Travel Dreams | Cambiar Contraseña");
            stage.setResizable(false);
            stage.show();
            VentanaCambiarContraseniaController controlador = loader.getController();
            controlador.setVentana(stage);
            controlador.setAplicacion(aplicacion);
            controlador.setCode(code);
            controlador.setCorreo(correo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarVentanaPerfilCliente(Cliente cliente) {
        ventanaInicioController.mostrarPanelDerechoPerfil(cliente);
    }

    public static void main(String[] args) {
        launch(Aplicacion.class, args);
    }

}
