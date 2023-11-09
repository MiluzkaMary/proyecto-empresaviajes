package co.edu.uniquindio.agenciaViajes.util;

import javafx.scene.control.Alert;

public class ArchivoUtils {

    /**
     * Metodo que muestra un mensaje
     * @param titulo Titulo del mensaje
     * @param encabezado Cabecera del mensaje
     * @param texto Texto principal del mensaje
     * @param alerta Alerta del mensaje
     */
    public static void mostrarMensaje(String titulo, String encabezado, String texto, Alert.AlertType alertaTipo) {
        Alert alert = new Alert(alertaTipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(texto);
        alert.showAndWait();
    }

}
