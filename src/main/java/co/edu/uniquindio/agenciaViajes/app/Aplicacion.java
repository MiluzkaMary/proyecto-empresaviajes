package co.edu.uniquindio.agenciaViajes.app;

import co.edu.uniquindio.agenciaViajes.controllers.VentanaInicioController;
import co.edu.uniquindio.agenciaViajes.controllers.VentanaRegistroIngresoController;
import co.edu.uniquindio.agenciaViajes.model.Administrador;
import co.edu.uniquindio.agenciaViajes.model.AgenciaViajes;
import co.edu.uniquindio.agenciaViajes.model.Cliente;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Aplicacion extends Application {

    private Stage stage;
    private AnchorPane rootLayout;

    public VentanaInicioController ventanaInicioController;
    public VentanaRegistroIngresoController ventanaRegistroIngresoController;
    //para modificar el controlador ventanaInicio desde otros controladores de otras ventanas



    @Override
    public void start(Stage stage) throws Exception {

        this.stage=stage;
        this.stage.setTitle("Travel Dreams");
        this.stage.setResizable(false); //para prohibir al usuario maximizar la ventana
        Cliente cliente=null;
        Administrador admin=null;
        mostrarVentanaPrincipal(cliente, admin);

    }

    public void mostrarVentanaDestinos(){
        ventanaInicioController.mostrarPanelDerechoDestinos();
    }

    public void mostrarVentanaPrincipal(Cliente cliente, Administrador admin){
        try{
            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/ventanas/VentanaInicio.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            VentanaInicioController controlador = loader.getController();
            ventanaInicioController = loader.getController(); //obteniendo valor editable del controlador
            controlador.setAplicacion(this);
            controlador.quemarDatosPrueba();



            //activa los paneles para un usuario que no es cliente ni admin
            if (cliente == null && admin == null) {
                controlador.mostrarPanelIzquierdo();
                controlador.mostrarPanelDerechoPaquetes();
                controlador.mostrarBarraSuperior();
            }

            //activa los paneles para un cliente
            if (cliente!=null && admin == null) {
                controlador.setCliente(cliente);
                controlador.mostrarPanelIzquierdoCliente();
                controlador.mostrarPanelDerechoPaquetes();
                controlador.mostrarBarraSuperiorCliente();
            }

            //activa los paneles para un administrador
            if (cliente==null && admin != null) {

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
            AnchorPane page = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Travel Dreams | Inicio de Sesion");
            ventana.initModality(Modality.WINDOW_MODAL);
            ventana.initOwner(stage);
            Scene scene = new Scene(page);
            ventana.setScene(scene);
            VentanaRegistroIngresoController controlador = loader.getController();
            ventanaRegistroIngresoController = loader.getController(); //obteniendo valor del controlador editable
            controlador.setVentana(stage);
            controlador.setAplicacion(aplicacion);
            ventana.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(Aplicacion.class, args);
    }

}
