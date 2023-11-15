package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.model.AgenciaViajes;
import co.edu.uniquindio.agenciaViajes.model.Destino;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
@Data

public class VentanaFiltrarDestinosController implements Initializable {

    @FXML
    private ComboBox<?> comboCiudad;

    @FXML
    private ComboBox<?> comboValoracion;

    @FXML
    private ComboBox<?> comboClima;

    @FXML
    private Button btnBuscador;

    @FXML
    private Button btnFiltrar;

    @FXML
    private GridPane gridDestino;

    @FXML
    private ScrollPane scrollDestinos;

    @FXML
    private TextField txtBuscador;

    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    public Aplicacion aplicacion;
    public List<Destino> listaDestinos = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



    public void iniciarGridPane() {
        int column = 0;
        int row = 1;

        try {
            for (int i = 0; i < listaDestinos.size(); i++) { // Cambio de 6 a 1 para mostrar solo una columna
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/ItemDestino.fxml"));
                Pane pane = loader.load();

                ItemDestinoController controlador = loader.getController();
                controlador.cargarDatos(listaDestinos.get(i));

                gridDestino.add(pane, column, row);

                column=0;
                row++;

                gridDestino.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridDestino.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridDestino.setMaxWidth(Region.USE_PREF_SIZE);
                //grid height
                gridDestino.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridDestino.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridDestino.setMaxHeight(Region.USE_PREF_SIZE);



                // Elimina las líneas relacionadas con el ancho y alto del grid

                GridPane.setMargin(pane, new Insets(8, 8, 8, 8));
            }
            scrollDestinos.setVvalue(0.05); // para eliminar el espacio en blanco entre el filtro y el panel
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void establecerListaDestinos(){
        listaDestinos=agenciaViajes.getListaDestinos();
    }

}
