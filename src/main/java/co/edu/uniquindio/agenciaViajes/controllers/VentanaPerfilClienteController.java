package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.exceptions.AtributoVacioException;
import co.edu.uniquindio.agenciaViajes.exceptions.CorreoNoDisponibleException;
import co.edu.uniquindio.agenciaViajes.exceptions.DatoInvalidoException;
import co.edu.uniquindio.agenciaViajes.exceptions.DatoNoNumericoException;
import co.edu.uniquindio.agenciaViajes.model.Administrador;
import co.edu.uniquindio.agenciaViajes.model.AgenciaViajes;
import co.edu.uniquindio.agenciaViajes.model.Cliente;
import co.edu.uniquindio.agenciaViajes.util.ArchivoUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Data;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
@Data

public class VentanaPerfilClienteController implements Initializable {
    public ImageView userPic;
    public ImageView userPic2;
    private Aplicacion aplicacion;
    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    public Cliente cliente;
    public Administrador administrador;
    public String password;
    public TextField txtNombre;
    public TextField txtCedula;
    public TextField txtCorreo;
    public TextField txtTelefono;
    public TextField txtDireccion;
    public ImageView closeEye;
    public ImageView openEye;
    public TextField txtShowPassword;
    public PasswordField txtHidePassword;
    public Button btnEditar;
    public Button btnConfirmar;
    public String fotoElegida;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setearCliente(Cliente cliente) {
        this.cliente = cliente;
        cargarDatos(); // Cargar los datos del cliente al iniciar el controlador
        txtShowPassword.setVisible(false);
        openEye.setVisible(false);
        password=cliente.getContrasenia();
        fotoElegida=cliente.getFoto();
    }
    public void cargarDatos(){
        try {
            Image image = new Image(getClass().getResourceAsStream(cliente.getFoto()));
            userPic.setImage(image);
            userPic2.setImage(image);
        } catch (Exception e) {
            if (!cliente.getFoto().equals("")) {
                Image image = new Image(cliente.getFoto());
                userPic.setImage(image);
                userPic2.setImage(image);
            }
        }
        Circle clip = new Circle(userPic.getFitWidth() / 2, userPic.getFitHeight() / 2, userPic.getFitWidth() / 2);
        userPic.setClip(clip);
        Circle clip2 = new Circle(userPic2.getFitWidth() / 2, userPic2.getFitHeight() / 2, userPic2.getFitWidth() / 2);
        userPic2.setClip(clip2);
        txtNombre.setText(cliente.getNombre());
        txtCedula.setText(cliente.getCedula());
        txtCorreo.setText(cliente.getCorreo());
        txtTelefono.setText(cliente.getTelefono());
        txtDireccion.setText(cliente.getDireccion());
        txtHidePassword.setText(cliente.getContrasenia());
        txtShowPassword.setText(cliente.getContrasenia());


    }
    public void close_Eye_ClickOnAction(MouseEvent mousevent) {
        txtShowPassword.setVisible(true);
        openEye.setVisible(true);
        closeEye.setVisible(false);
        txtHidePassword.setVisible(false);
    }
    public void HidePasswordKeyReleased(KeyEvent keyevent) {
        password = txtHidePassword.getText();
        txtShowPassword.setText(String.valueOf(password));
    }

    @FXML
    public void Open_Eye_OnClickAction(MouseEvent mousevent) {
        txtShowPassword.setVisible(false);
        openEye.setVisible(false);
        closeEye.setVisible(true);
        txtHidePassword.setVisible(true);
    }

    @FXML
    public void ShowPasswordOnAction(KeyEvent keyevent) {
        password = txtShowPassword.getText();
        txtHidePassword.setText(password);
    }

    public void habilitarEdicion(){
        txtNombre.setEditable(true);
        txtCedula.setEditable(true);
        txtCorreo.setEditable(true);
        txtTelefono.setEditable(true);
        txtDireccion.setEditable(true);
        txtHidePassword.setEditable(true);
        txtShowPassword.setEditable(true);
        userPic.setVisible(false);
        userPic2.setVisible(true);
        btnEditar.setVisible(false);
        btnConfirmar.setVisible(true);
    }

    public void confirmarEdicion() {
        try {
            Cliente cliente = agenciaViajes.editarCliente(
                    this.cliente,
                    txtCedula.getText(),
                    txtNombre.getText(),
                    txtTelefono.getText(),
                    fotoElegida,
                    txtDireccion.getText(),
                    txtCorreo.getText(),
                    password);
            aplicacion.mostrarVentanaPrincipal(cliente, this.administrador);
            ArchivoUtils.mostrarMensaje("Registro Confirmado", "Operación completada", "Se ha editado correctamente al cliente: " + cliente.getNombre(), Alert.AlertType.INFORMATION);

        } catch (AtributoVacioException | DatoInvalidoException | CorreoNoDisponibleException |
                 DatoNoNumericoException e) {
            ArchivoUtils.mostrarMensaje("Error", "Cambio Fallido", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void cambiarFoto(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar una imagen");

        // Filtrar por extensiones de imagen
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de imagen", "*.jpg", "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        // Obtener la ventana actual
        Stage stage = (Stage) userPic.getScene().getWindow();

        // Mostrar el explorador de archivos y obtener la nueva imagen seleccionada
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Image newImage = new Image(selectedFile.toURI().toString());
            userPic.setImage(newImage);
            userPic2.setImage(newImage);
            fotoElegida=userPic2.getImage().getUrl();
        }
    }
}
