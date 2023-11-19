package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.*;

public class VentanaEstadisticasController implements Initializable {
    @FXML
    private NumberAxis yAxis;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private Button btnAnalizar;

    @FXML
    private BarChart<String, Double> chart;

    @FXML
    private PieChart pieChart;

    @FXML
    private Text txtTituloEstadistica;

    @FXML
    private ComboBox comboEstadistica;

    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    public Aplicacion aplicacion;
    public Cliente cliente;
    public Administrador administrador;
    public ArrayList<GuiaTuristico> listaGuias;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void iniciarDatos(){
        //inicio de combo
        ObservableList<String> opciones= FXCollections.observableArrayList(
                "PAQUETES",
                "GUÍAS TURISTICOS",
                "DESTINOS"
        );
        comboEstadistica.setItems(opciones);
        comboEstadistica.setValue("GUÍAS TURISTICOS");

        iniciarDatosGuias();
    }

    public void analizar(){
        String chosen=(String)comboEstadistica.getSelectionModel().getSelectedItem();
        if (chosen!=null || !chosen.isBlank()){
            if(chosen.equals("PAQUETES")){
                iniciarDatosPaquetes();
            }
            else if (chosen.equals("GUÍAS TURISTICOS")){
                iniciarDatosGuias();
            }
            else if (chosen.equals("DESTINOS")){
                iniciarDatosDestinos();
            }
        }
    }

    public void iniciarDatosGuias(){

        txtTituloEstadistica.setText("Estadísticas de Guías Turisticos");
        listaGuias=agenciaViajes.getListaGuias();

        chart.getData().removeAll(chart.getData());
        pieChart.getData().clear();

        xAxis.setLabel("Guías");
        yAxis.setLabel("Promedio de Calificaciones");


        // Crea la serie de datos para el gráfico
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (GuiaTuristico guia : listaGuias) {
            series.getData().add(new XYChart.Data<>(guia.getNombre(), guia.obtenerPromedioValoracion()));
        }
        chart.getData().add(series);


        for (GuiaTuristico guia : listaGuias) {
            PieChart.Data slice = new PieChart.Data(guia.getNombre(), guia.obtenerPromedioValoracion());
            pieChart.getData().add(slice);
        }
    }

    public void iniciarDatosDestinos() {
        txtTituloEstadistica.setText("Estadísticas de Destinos reservados");

        // Limpiar los datos anteriores del BarChart y PieChart
        chart.getData().removeAll(chart.getData());
        pieChart.getData().clear();

        xAxis.setLabel("Destinos");
        yAxis.setLabel("Destinos más reservados");
        // Obtener la lista de reservas
        List<Reserva> listaReservas = agenciaViajes.getListaReservas();
        Map<String, Integer> mapaDatos = agenciaViajes.obtenerDatosReservaCantidadDestinos(listaReservas);



        // Crear la serie de datos para el gráfico
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : mapaDatos.entrySet()) {
            // Convertir el valor Integer a Double antes de agregarlo a la serie
            Double valorDouble = entry.getValue().doubleValue();
            series.getData().add(new XYChart.Data<>(entry.getKey(), valorDouble));
        }
        chart.getData().add(series);

        // Crear el PieChart
        for (Map.Entry<String, Integer> entry : mapaDatos.entrySet()) {
            // Convertir el valor Integer a Double antes de agregarlo al PieChart
            Double valorDouble = entry.getValue().doubleValue();
            PieChart.Data slice = new PieChart.Data(entry.getKey(), valorDouble);
            pieChart.getData().add(slice);
        }
    }

    public void iniciarDatosPaquetes() {
        txtTituloEstadistica.setText("Estadísticas de Paquetes reservados");

        // Limpiar los datos anteriores del BarChart y PieChart
        chart.getData().removeAll(chart.getData());
        pieChart.getData().clear();


        xAxis.setLabel("Paquetes");
        yAxis.setLabel("Paquetes más reservados");

        // Obtener la lista de reservas
        List<Reserva> listaReservas = agenciaViajes.getListaReservas();

        // Llamar al método para obtener datos de paquetes
        Map<String, Integer> mapaDatos = agenciaViajes.obtenerDatosReservaCantidadPaquetes(listaReservas);


        // Crear la serie de datos para el gráfico
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : mapaDatos.entrySet()) {
            // Convertir el valor Integer a Double antes de agregarlo a la serie
            Double valorDouble = entry.getValue().doubleValue();
            series.getData().add(new XYChart.Data<>(entry.getKey(), valorDouble));
        }
        chart.getData().add(series);

        // Crear el PieChart
        for (Map.Entry<String, Integer> entry : mapaDatos.entrySet()) {
            // Convertir el valor Integer a Double antes de agregarlo al PieChart
            Double valorDouble = entry.getValue().doubleValue();
            PieChart.Data slice = new PieChart.Data(entry.getKey(), valorDouble);
            pieChart.getData().add(slice);
        }
    }

}
