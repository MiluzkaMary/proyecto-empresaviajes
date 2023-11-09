package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.enums.TipoClima;
import co.edu.uniquindio.agenciaViajes.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import lombok.extern.java.Log;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
@Log
@Data

public class VentanaInicioController  implements Initializable {

    @FXML
    private AnchorPane panelIzquierdo;

    @FXML
    private AnchorPane panelDerecho;

    @FXML
    private AnchorPane barraSuperior;

    @FXML
    private AnchorPane panelPrincipal;

    private Aplicacion aplicacion;

    public Cliente cliente;

    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void mostrarPanelIzquierdo(){
        try {
            panelIzquierdo.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/TableroUsuario.fxml"));
            Node node = loader.load();
            panelIzquierdo.getChildren().add(node);
            TableroUsuarioController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarPanelIzquierdoCliente(){
        try {
            panelIzquierdo.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/TableroCliente.fxml"));
            Node node = loader.load();
            panelIzquierdo.getChildren().add(node);
            TableroClienteController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarPanelDerechoPaquetes(){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaFiltrarPaquetes.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaFiltrarPaquetesController controlador = loader.getController();
            controlador.establecerListaPaquetes();
            controlador.iniciarGridPane();
            controlador.setAplicacion(this.aplicacion);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarPanelDerechoDestinos(){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaFiltrarDestinos.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaFiltrarDestinosController controlador = loader.getController();
            controlador.establecerListaDestinos();
            controlador.iniciarGridPane();
            controlador.setAplicacion(this.aplicacion);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarBarraSuperior(){
        try {
            barraSuperior.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/BarraUsuario.fxml"));
            Node node = loader.load();
            barraSuperior.getChildren().add(node);
            BarraUsuarioController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarBarraSuperiorCliente(){
        try {
            barraSuperior.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/BarraCliente.fxml"));
            Node node = loader.load();
            barraSuperior.getChildren().add(node);
            BarraClienteController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setCliente(this.cliente);
            controlador.cargarInfo();
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void quemarDatosPrueba(){

        ArrayList<Destino> listaDestinosQuemados = new ArrayList<>();
        ArrayList<Cliente> listaClientesQuemados = new ArrayList<>();
        ArrayList<Paquete> listaPaquetesQuemados = new ArrayList<>();

        ArrayList<Valoracion> listaValoracionesQuemadas;
        Paquete paquete;
        Destino destino;
        Cliente cliente;
        Valoracion valoracion;

        //[1ER PAQUETE] hay un paquete por el momento, con 3 destinos, estos ultimos con 5 clientes cada uno

        // (1ER DESTINO) para la primera lista de valoraciones del primer destino
        listaValoracionesQuemadas=new ArrayList<Valoracion>();

        //1ER CLIENTE
        cliente = Cliente.builder()
                .cedula("6789090")
                .nombre("Jane Smith")
                .telefono("310291212")
                .foto("/imagenes/userFem1.jpeg")
                .correo("JaneSmith@gmail.com")
                .direccion("Calle 456 Oak St")
                .contrasenia("jane123")
                .paquetesFavoritos(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(4)
                .cliente(cliente)
                .comentario("Estoy muy contenta con la experiencia que tuve al reservar mi " +
                        "viaje a través de su agencia. La atención al cliente fue excelente y " +
                        "me proporcionaron recomendaciones valiosas para mi itinerario. El viaje " +
                        "transcurrió sin problemas, y los alojamientos y excursiones superaron " +
                        "mis expectativas. Gracias a su ayuda, disfruté de unas vacaciones increíbles " +
                        "y llenas de aventuras.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        //2DO CLIENTE
        cliente = Cliente.builder()
                .cedula("21717293")
                .nombre("John Doe")
                .telefono("312332344")
                .foto("/imagenes/userMale1.jpeg")
                .correo("JohnDoe@gmail.com")
                .direccion("NY calle 7 Main St")
                .contrasenia("john123")
                .paquetesFavoritos(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(5)
                .cliente(cliente)
                .comentario("Estoy muy contento con la experiencia que tuve al reservar mi " +
                        "viaje a través de su agencia. La atención al cliente fue excelente y " +
                        "me proporcionaron recomendaciones valiosas para mi itinerario. El viaje " +
                        "transcurrió sin problemas, y los alojamientos y excursiones superaron " +
                        "mis expectativas. Gracias a su ayuda, disfruté de unas vacaciones increíbles " +
                        "y llenas de aventuras.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // 3ER CLIENTE
        cliente = Cliente.builder()
                .cedula("45234775")
                .nombre("Alice Johnson")
                .telefono("3928247825")
                .foto("/imagenes/userFem2.jpeg")
                .correo("AliceJohnson@gmail.com")
                .direccion("456 Elm St")
                .contrasenia("alice123")
                .paquetesFavoritos(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(3)
                .cliente(cliente)
                .comentario("Mi experiencia con su agencia ha sido poco satisfactoria. Desde el " +
                        "primer contacto, hubo problemas de comunicación y confusión en la reserva de vuelos y alojamiento. " +
                        "Durante el viaje, enfrenté retrasos inesperados y alojamiento por debajo de las expectativas. Esperaba " +
                        "una experiencia inolvidable, pero lamentablemente, me sentí decepcionado en cada paso del camino.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // 4TO CLIENTE
        cliente = Cliente.builder()
                .cedula("5432109")
                .nombre("David Wilson")
                .telefono("987654321")
                .foto("/imagenes/userMale2.jpeg")
                .correo("DavidWilson@gmail.com")
                .direccion("789 Pine St")
                .contrasenia("david123")
                .paquetesFavoritos(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(2)
                .cliente(cliente)
                .comentario("Mi experiencia con su agencia fue decepcionante. Los servicios proporcionados no cumplieron " +
                        "con mis expectativas y tuve problemas con la organización del itinerario y el alojamiento. No puedo " +
                        "recomendar su agencia a otros viajeros.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // 5TO CLIENTE
        cliente = Cliente.builder()
                .cedula("9876543")
                .nombre("Michael Brown")
                .telefono("123789456")
                .foto("/imagenes/userMale3.jpeg")
                .correo("MichaelBrown@gmail.com")
                .direccion("123 Oak Ave")
                .contrasenia("michael123")
                .paquetesFavoritos(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(4)
                .cliente(cliente)
                .comentario("Tuvimos una experiencia agradable con su agencia de viajes. Las recomendaciones de destinos " +
                        "fueron acertadas, y la organización del viaje fue eficiente. Los alojamientos y las excursiones cumplieron " +
                        "con nuestras expectativas. Recomendamos su agencia a otros viajeros.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // Crear el destino con las valoraciones
        destino = Destino.builder()
                        .nombre("Las ruinas de Tulúm")
                        .ciudad("Cancún")
                        .descripcion("Las ruinas de Tulum en Cancún te transportarán en el tiempo a una " +
                        "época de esplendor maya. Explora este sitio arqueológico espectacular " +
                        "mientras descubres la rica historia de la civilización maya. Disfruta de " +
                        "vistas impresionantes del mar Caribe mientras exploras estas antiguas estructuras. " +
                        "Un destino fascinante que combina historia y belleza natural.")
                        .clima(TipoClima.TROPICAL)
                        .fotos(Arrays.asList("/imagenes/tulum1.png", "/imagenes/tulum2.png", "/imagenes/tulum3.png"))
                        .valoraciones(listaValoracionesQuemadas)
                        .build();


        listaDestinosQuemados.add(destino);

        // (2DO DESTINO)
        listaValoracionesQuemadas = new ArrayList<Valoracion>(); //limpiar lista valoraciones

        // 1ER CLIENTE DEL 2DO DESTINO
        cliente = Cliente.builder()
                .cedula("2123123")
                .nombre("Ella Anderson")
                .telefono("3123456789")
                .foto("/imagenes/userFem3.jpeg")
                .correo("EllaAnderson@gmail.com")
                .direccion("123 Palm St 3N-8")
                .contrasenia("ella123")
                .paquetesFavoritos(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(4)
                .cliente(cliente)
                .comentario("Una experiencia maravillosa desde el principio hasta el final. Los servicios de la agencia " +
                        "superaron mis expectativas. Recibí excelentes recomendaciones para mi viaje y todo salió sin problemas. " +
                        "Los alojamientos y excursiones fueron de primera categoría. ¡Gracias por unas vacaciones inolvidables!")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // 2DO CLIENTE
        cliente = Cliente.builder()
                .cedula("9876543")
                .nombre("John Smith")
                .telefono("3219876543")
                .foto("/imagenes/userMale4.jpeg")
                .correo("JohnSmith@gmail.com")
                .direccion("456 Beach Blvd")
                .contrasenia("john123")
                .paquetesFavoritos(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(3)
                .cliente(cliente)
                .comentario("Mi experiencia con su agencia fue aceptable. Hubo algunas áreas en las que esperaba más, " +
                        "pero en general, disfruté del viaje. La organización del itinerario fue buena, aunque hubo algunos " +
                        "problemas menores. Los alojamientos estuvieron bien, pero hubo margen para mejoras.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // 3ER CLIENTE
        cliente = Cliente.builder()
                .cedula("1234567")
                .nombre("Sophia Martinez")
                .telefono("3312367333")
                .foto("/imagenes/userFem4.jpeg")
                .correo("SophiaMartinez@gmail.com")
                .direccion("789 Ocean Dr")
                .contrasenia("sophia123")
                .paquetesFavoritos(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(5)
                .cliente(cliente)
                .comentario("Increíble experiencia de viaje. Todo fue perfecto, desde la organización del viaje hasta " +
                        "las recomendaciones y los servicios proporcionados. No podría haber pedido unas vacaciones mejores.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // 4TO CLIENTE
        cliente = Cliente.builder()
                .cedula("8765432")
                .nombre("Michael Brown")
                .telefono("203127890")
                .foto("/imagenes/userMale5.jpeg")
                .correo("MichaelBrown@gmail.com")
                .direccion("987 Forest Ln")
                .contrasenia("michael123")
                .paquetesFavoritos(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(2)
                .cliente(cliente)
                .comentario("¡Que horrible experiencia!. Los servicios proporcionados " +
                        "no cumplieron con mis expectativas y tuve problemas con la organización del itinerario y el alojamiento. " +
                        "No puedo recomendar su agencia a otros viajeros.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // 5TO CLIENTE
        cliente = Cliente.builder()
                .cedula("5432109")
                .nombre("Olivia Taylor")
                .telefono("1112223333")
                .foto("/imagenes/userFem5.jpeg")
                .correo("OliviaTaylor@gmail.com")
                .direccion("111 Hill Rd")
                .contrasenia("olivia123")
                .paquetesFavoritos(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(4)
                .cliente(cliente)
                .comentario("¡Todo fue increible! Disfrutamos de las recomendaciones de destinos y " +
                        "de la organización del viaje en general. Los alojamientos y excursiones cumplieron con nuestras expectativas. " +
                        "Recomendamos su agencia a nuestros amigos y familiares. Uno de los plus que nos gustó fue"+
                        " que pudimos llevar con nosotros a nuestro pequeño pug")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // Crear el destino con las valoraciones
        destino = Destino.builder()
                .nombre("Playas de Hawai")
                .ciudad("Honolulu")
                .descripcion("Las Playas de Hawái, ubicadas en la soleada ciudad de Honolulu, " +
                        "son un auténtico paraíso tropical que te invita a sumergirte en la belleza " +
                        "y la cultura hawaiana. Este destino te ofrece más que simplemente arena dorada " +
                        "y aguas cristalinas. Aquí, encontrarás un rincón del mundo donde el clima tropical " +
                        "crea un ambiente de eterno verano. ")
                .clima(TipoClima.TROPICAL)
                .fotos(Arrays.asList("/imagenes/hawai1.png", "/imagenes/hawai2.png", "/imagenes/hawai3.png"))
                .valoraciones(listaValoracionesQuemadas)
                .build();

        listaDestinosQuemados.add(destino);

        // (3ER DESTINO)
        listaValoracionesQuemadas = new ArrayList<Valoracion>();

        // 1ER CLIENTE DEL 3ER DESTINO
        cliente = Cliente.builder()
                .cedula("9871234")
                .nombre("Liam Johnson")
                .telefono("3334445555")
                .foto("/imagenes/userMale6.jpeg")
                .correo("LiamJohnson@gmail.com")
                .direccion("456 Sunset Blvd")
                .contrasenia("liam123")
                .paquetesFavoritos(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(5)
                .cliente(cliente)
                .comentario("Mi viaje a este destino fue realmente increíble. Desde el momento en que llegué, " +
                        "me sentí bienvenido y atendido. Todas las recomendaciones de la agencia fueron acertadas. " +
                        "Las excursiones y alojamientos superaron mis expectativas. ¡Definitivamente regresaré!")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // 2DO CLIENTE
        cliente = Cliente.builder()
                .cedula("4567890")
                .nombre("Sophie Davis")
                .telefono("7778889999")
                .foto("/imagenes/userFem6.jpeg")
                .correo("SophieDavis@gmail.com")
                .direccion("789 Sunrise Rd")
                .contrasenia("sophie123")
                .paquetesFavoritos(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(4)
                .cliente(cliente)
                .comentario("Tuvimos una experiencia de viaje fantástica. Todo estuvo bien organizado y planificado. " +
                        "Las recomendaciones de destinos fueron acertadas. Los alojamientos eran cómodos y las excursiones emocionantes. " +
                        "¡Unas vacaciones para recordar!")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // 3ER CLIENTE
        cliente = Cliente.builder()
                .cedula("2345678")
                .nombre("Mia Wilson")
                .telefono("5556667777")
                .foto("/imagenes/userFem7.jpeg")
                .correo("MiaWilson@gmail.com")
                .direccion("101 Palm Ave")
                .contrasenia("mia123")
                .paquetesFavoritos(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(5)
                .cliente(cliente)
                .comentario("Estoy muy agradecida con ustedes, en el 3er dia de viaje mi hermana estuvo " +
                        "a punto de ahogarse pero el personal capacitado que Travel Dreams tuvo fue lo que " +
                        "le salvó la vida.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // 4TO CLIENTE
        cliente = Cliente.builder()
                .cedula("8765432")
                .nombre("Benjamin Lewis")
                .telefono("2223334444")
                .foto("/imagenes/userMale7.jpeg")
                .correo("BenjaminLewis@gmail.com")
                .direccion("222 Ocean View Dr")
                .contrasenia("benjamin123")
                .paquetesFavoritos(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(3)
                .cliente(cliente)
                .comentario("Nuestra experiencia de viaje fue en su mayoría positiva. Algunas áreas podrían " +
                        "mejorar, pero en general, disfrutamos del destino. Las recomendaciones de la agencia fueron " +
                        "útiles, y la mayoría de las excursiones estuvieron bien.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // 5TO CLIENTE
        cliente = Cliente.builder()
                .cedula("3456789")
                .nombre("Emma Moore")
                .telefono("9990001111")
                .foto("/imagenes/userFem8.jpeg")
                .correo("EmmaMoore@gmail.com")
                .direccion("333 Hillside Ave")
                .contrasenia("emma123")
                .paquetesFavoritos(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(5)
                .cliente(cliente)
                .comentario("Nuestra experiencia de viaje fue inolvidable. A pesar de que hubo problemas en la organización " +
                        " de fechas que se tenian planeadas, fueron bastantes rapidos en solucionar el problema."+
                        " Tuvimos unas vacaciones muy relajantes, practicamente salvaron a mi familia del divorcio.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // Crear el tercer destino con las valoraciones
        destino = Destino.builder()
                .nombre("Parque Nacional de Yellowstone")
                .ciudad("Wyoming")
                .descripcion("El Parque Nacional de Yellowstone, ubicado en Wyoming, es un paraíso natural lleno de " +
                        "maravillas geotérmicas y vida silvestre. Con su clima templado y paisajes asombrosos, " +
                        "este destino te ofrece una experiencia única en la naturaleza.")
                .clima(TipoClima.TEMPLADO)
                .fotos(Arrays.asList("/imagenes/yellowstone1.png", "/imagenes/yellowstone2.png", "/imagenes/yellowstone3.png"))
                .valoraciones(listaValoracionesQuemadas)
                .build();

        listaDestinosQuemados.add(destino);

        //[Creacion PAQUETE 1]
        //fecha para el paquete
        LocalDate fecha = LocalDate.of(2023, 12, 25);
        paquete= Paquete.builder()
                .nombre("Expedición cultural y reconexión con la naturaleza")
                .destinos(listaDestinosQuemados)
                .diasDuracion(7)
                .descripcion("Sumérgete en una aventura inolvidable con nuestro paquete 'Expedición Natural y" +
                        " Cultural'. Explora la belleza tropical de Hawái, descubre las maravillas históricas" +
                        " de Cancún y maravíllate con la majestuosidad de Yellowstone Park. Este viaje único" +
                        " combina playas doradas, ruinas mayas y paisajes naturales asombrosos. ¡Una" +
                        " experiencia que te llevará a través del tiempo y la naturaleza en un solo viaje!")
                .precio(2500000.0)
                .cupoMaximo(30)
                .fecha(fecha)
                .build();
        listaPaquetesQuemados.add(paquete);


        agenciaViajes.setListaDestinos(listaDestinosQuemados);
        agenciaViajes.setListaPaquetes(listaPaquetesQuemados);
        agenciaViajes.setListaClientes(listaClientesQuemados);







    }



}
