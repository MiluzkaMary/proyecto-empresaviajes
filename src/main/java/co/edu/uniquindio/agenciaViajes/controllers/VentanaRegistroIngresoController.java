package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.exceptions.*;
import co.edu.uniquindio.agenciaViajes.model.Administrador;
import co.edu.uniquindio.agenciaViajes.model.AgenciaViajes;
import co.edu.uniquindio.agenciaViajes.model.Cliente;
import co.edu.uniquindio.agenciaViajes.util.ArchivoUtils;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;
@Data

public class VentanaRegistroIngresoController implements Initializable {

    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    private Aplicacion aplicacion;
    private Stage ventana;

    @FXML
    private Pane paneCrearCuenta;

    @FXML
    private AnchorPane paneSolid;

    @FXML
    private Button btnYaTengoCuenta;

    @FXML
    private Button btnRegistrarmePanel;

    @FXML
    private Pane paneIngresar;

    @FXML
    private Label txtNoRegistrado;

    @FXML
    private Label txtYaRegistrado;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    private PasswordField txtHidePassword;

    @FXML
    private TextField txtShowPassword;

    @FXML
    private TextField txtShowPassword2;

    @FXML
    private PasswordField txtHidePassword2;


    @FXML
    private ImageView closeEye2;

    @FXML
    private ImageView openEye2;

    @FXML
    private ImageView openEye1;

    @FXML
    private ImageView closeEye1;

    @FXML
    private TextField txtCorreo2;

    @FXML
    private TextField txtNombre2;

    public String password2;
    public String password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtShowPassword2.setVisible(false);
        openEye2.setVisible(false);
        txtShowPassword.setVisible(false);
        openEye1.setVisible(false);
    }

    public void ingresarUsuario(){

        try {
            String tipoUsuario=agenciaViajes.buscarTipoUsuario(txtNombre2.getText(),
                    txtCorreo2.getText(),
                    password2);
            String nombreUsuario="";
            switch (tipoUsuario) {
                case "Administrador": Administrador admin= agenciaViajes.obtenerAdministradorIngreso(txtNombre2.getText(),
                        txtCorreo2.getText(),
                        password2);
                    nombreUsuario=admin.getNombre();
                    iniciarSesionAdministrador(admin); break;
                case "Cliente": Cliente cliente = agenciaViajes.obtenerClienteIngreso(txtNombre2.getText(),
                        txtCorreo2.getText(),
                        password2);
                    nombreUsuario=cliente.getNombre();
                    iniciarSesionCliente(cliente); break;
            }
            ArchivoUtils.mostrarMensaje("Ingreso Exitoso", "Operación completada", "¡Haz ingresado correctamente "+nombreUsuario+"!", Alert.AlertType.INFORMATION);

        } catch (AtributoVacioException | CorreoInvalidoException | UsuarioNoExistenteException e) {
            ArchivoUtils.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    /**
     * Inicia sesión para un usuario administrador.
     * @param admin El objeto Administrador correspondiente al administrador que ha iniciado sesión.
     */
    public void iniciarSesionAdministrador(Administrador admin) {
        ventana.close();
        Cliente cliente=null;
        aplicacion.mostrarVentanaPrincipal(cliente, admin);
    }

    /**
     * Inicia sesión para un cliente.
     * @param cliente El objeto Cliente correspondiente al cliente que ha iniciado sesión.
     */
    public void iniciarSesionCliente(Cliente cliente) {
        ventana.close();
        Administrador admin=null;
        aplicacion.setCliente(cliente);
        aplicacion.mostrarVentanaPrincipal(cliente, admin);
    }

    public void registrarCliente(){
        String foto=null;

        try {
            Cliente cliente = agenciaViajes.registrarCliente(
                    txtCedula.getText(),
                    txtNombre.getText(),
                    txtTelefono.getText(),
                    foto,
                    txtCorreo.getText(),
                    txtDireccion.getText(),
                    password);
            ArchivoUtils.mostrarMensaje("Registro Confirmado", "Operación completada", "Se ha registrado correctamente el cliente: "+cliente.getNombre(), Alert.AlertType.INFORMATION);
            limpiarRegistrarPanel();

        } catch (AtributoVacioException | InformacionRepetidaException | DatoNoNumericoException |
                 CorreoInvalidoException e) {
            ArchivoUtils.mostrarMensaje("Error", "Entradas no validas", e.getMessage(), Alert.AlertType.ERROR);
        }

    }


    public void switchFormbtnRegistrarmePanel(){
        TranslateTransition slider= new TranslateTransition();

        slider.setNode(paneSolid);
        slider.setToX(370);
        slider.setDuration(Duration.seconds(.7));
        slider.setOnFinished(event -> {
            btnYaTengoCuenta.setVisible(true);
            txtYaRegistrado.setVisible(true);
            btnRegistrarmePanel.setVisible(false);
            txtNoRegistrado.setVisible(false);
            limpiarIngresarPanel();
        });

        slider.play();

    }

    public void switchFormbtnIngresarPanel(){
        TranslateTransition slider= new TranslateTransition();

        slider.setNode(paneSolid);
        slider.setToX(0);
        slider.setDuration(Duration.seconds(.7));
        slider.setOnFinished(event -> {
            btnYaTengoCuenta.setVisible(false);
            txtYaRegistrado.setVisible(false);
            btnRegistrarmePanel.setVisible(true);
            txtNoRegistrado.setVisible(true);
            limpiarRegistrarPanel();

        });

        slider.play();

    }

    /**
     * Evento que limpiara los campos de textos del panel registro
     */
    public void limpiarRegistrarPanel(){
        txtNombre.clear();
        txtCedula.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtDireccion.clear();
        txtShowPassword.clear();
        txtHidePassword.clear();
        password="";
    }

    /**
     * Evento que limpiara los campos de textos del panel ingresar
     */
    public void limpiarIngresarPanel(){
        txtNombre2.clear();
        txtCorreo2.clear();
        txtShowPassword2.clear();
        txtHidePassword2.clear();
        password2="";
    }

    /**
     * Evento que se ejecuta cuando se libera una tecla en el campo de texto para ocultar la contraseña.
     *
     * Este método se ejecuta cuando se libera una tecla en el campo de texto designado para ocultar la contraseña.
     * Obtiene el texto ingresado en el campo de texto y lo establece como texto en el campo de texto para mostrar la
     * contraseña.
     * @param keyevent El evento de liberación de tecla.
     */
    @FXML
    public void HidePasswordKeyReleased2(KeyEvent keyevent) {

        password2 = txtHidePassword2.getText();
        txtShowPassword2.setText(String.valueOf(password2));

        password = txtHidePassword.getText();
        txtShowPassword.setText(String.valueOf(password));
    }

    /**
     * Evento que se ejecuta cuando se realiza una acción en el campo de texto para mostrar la contraseña.
     *
     * Este método se ejecuta cuando se realiza una acción en el campo de texto designado para mostrar la contraseña.
     * Obtiene el texto ingresado en el campo de texto y lo establece como texto en el campo de texto para ocultar la
     * contraseña.
     * @param keyevent La acción realizada en el campo de texto.
     */
    @FXML
    public void ShowPasswordOnAction2(KeyEvent keyevent) {
        password2 = txtShowPassword2.getText();
        txtHidePassword2.setText(password2);

        password = txtShowPassword.getText();
        txtHidePassword.setText(password);
    }

    /**
     * Evento que se ejecuta al hacer clic en el botón de cerrar ojo.
     *
     * Este método se ejecuta cuando se hace clic en el botón de cerrar ojo. Cambia la visibilidad de los elementos de la
     * interfaz relacionados con la contraseña para mostrarlos u ocultarlos según sea necesario.
     * @param mousevent El evento de clic del ratón.
     */
    @FXML
    public void Close_Eye_ClickOnAction2(MouseEvent mousevent) {
        txtShowPassword2.setVisible(true);
        openEye2.setVisible(true);
        closeEye2.setVisible(false);
        txtHidePassword2.setVisible(false);

        txtShowPassword.setVisible(true);
        openEye1.setVisible(true);
        closeEye1.setVisible(false);
        txtHidePassword.setVisible(false);

    }

    /**
     * Evento que se ejecuta al hacer clic en el botón de abrir ojo.
     *
     * Este método se ejecuta cuando se hace clic en el botón de abrir ojo. Cambia la visibilidad de los elementos de la
     * interfaz relacionados con la contraseña para mostrarlos u ocultarlos según sea necesario.
     * @param mousevent El evento de clic del ratón.
     */
    @FXML
    public void Open_Eye_OnClickAction2(MouseEvent mousevent) {
        txtShowPassword2.setVisible(false);
        openEye2.setVisible(false);
        closeEye2.setVisible(true);
        txtHidePassword2.setVisible(true);

        txtShowPassword.setVisible(false);
        openEye1.setVisible(false);
        closeEye1.setVisible(true);
        txtHidePassword.setVisible(true);

    }


}
