package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.app.Aplicacion;
import co.edu.uniquindio.agenciaViajes.enums.TipoClima;
import co.edu.uniquindio.agenciaViajes.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Data;
import lombok.extern.java.Log;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public Administrador administrador;

    public AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();

    public Stage ventana;

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
            controlador.setCliente(this.cliente);
            controlador.setAdministrador(this.administrador);
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
            controlador.setCliente(this.cliente);
            controlador.setAdministrador(this.administrador);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarPanelIzquierdoAdmin(){
        try {
            panelIzquierdo.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/TableroAdmin.fxml"));
            Node node = loader.load();
            panelIzquierdo.getChildren().add(node);
            TableroAdminController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setCliente(this.cliente);
            controlador.setAdministrador(this.administrador);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarPanelDerechoPaquetes(Cliente cliente, Administrador admin){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaFiltrarPaquetes.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaFiltrarPaquetesController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setCliente(cliente);
            controlador.setAdministrador(admin);
            controlador.establecerListaPaquetes();
            controlador.iniciarGridPane();

        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarPanelDerechosMisReservas(Cliente cliente, Administrador administrador){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaGestionReserva.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaGestionReservaController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setCliente(cliente);
            controlador.setAdministrador(administrador);
            controlador.iniciarDatos();
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarPanelDerechoReservas(Paquete paquete, Cliente cliente){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaReserva.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaReservaController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setCliente(cliente);
            controlador.setPaquete(paquete);
            controlador.iniciarDatos();
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }


    public void mostrarPanelDerechoDetallesPaquete(Paquete paquete, Cliente cliente){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaDetallesPaquete.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaDetallesPaqueteController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setCliente(cliente);
            controlador.setPaquete(paquete);
            controlador.iniciarGridPaneDetallesPaquete();
            controlador.iniciarDatosDetallesPaquete();
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

    public void mostrarPanelDerechoEstadisticas(){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaEstadisticas.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaEstadisticasController controlador = loader.getController();
            controlador.iniciarDatos();
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarPanelDerechoGuias(){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaFiltrarGuias.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaFiltrarGuiasController controlador = loader.getController();
            controlador.establecerListaGuias();
            controlador.iniciarGridPane();
            controlador.setAplicacion(this.aplicacion);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarPanelDerechoGestionarPaquetes(Cliente cliente, Administrador admin){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaGestionar.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaGestionarController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setCliente(cliente);
            controlador.setAdministrador(admin);
            controlador.inicarGestionPaquetes();
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarPanelDerechoGestionarDestinos(Cliente cliente, Administrador admin){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaGestionar.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaGestionarController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setCliente(cliente);
            controlador.setAdministrador(admin);
            controlador.iniciarGestionDestinos();
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarPanelDerechoGestionGuias(Cliente cliente, Administrador admin){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaGestionar.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaGestionarController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setCliente(cliente);
            controlador.setAdministrador(admin);
            controlador.iniciarGestionGuias();
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarPanelDerechoCrearEditarPaquete(Paquete paquete, Administrador administrador){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaDetallesPaquete.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaDetallesPaqueteController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setAdministrador(administrador);
            //para saber si se esta creando o editando
            controlador.setPaquete(paquete);
            controlador.iniciarDatosCrearEditar();

        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarPanelDerechoCrearEditarGuia(GuiaTuristico guia, Administrador administrador){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaCrearEditarGuia.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaCrearEditarGuiaController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setAdministrador(administrador);
            controlador.setVentana(ventana);
            //para saber si se esta creando o editando
            controlador.setGuiaTuristico(guia);
            controlador.iniciarDatosCrearEditar();

        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarPanelDerechoCrearEditarDestino(Destino destino, Administrador administrador){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaCrearEditarDestino.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaCrearEditarDestinoController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setAdministrador(administrador);
            controlador.setVentana(ventana);
            //para saber si se esta creando o editando
            controlador.setDestino(destino);
            controlador.iniciarDatosCrearEditar();

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

    public void mostrarBarraSuperiorAdmin(){
        try {
            barraSuperior.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/BarraCliente.fxml"));
            Node node = loader.load();
            barraSuperior.getChildren().add(node);
            BarraClienteController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setAdministrador(this.administrador);
            controlador.cargarInfo();
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void mostrarPanelDerechoPerfil(Aplicacion aplicacion, Cliente cliente){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/VentanaPerfilCliente.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaPerfilClienteController controlador = loader.getController();
            controlador.setAplicacion(aplicacion);
            controlador.setCliente(cliente);
            controlador.setearCliente(cliente);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    public void quemarDatosPrueba() {

        ArrayList<Destino> listaDestinosQuemados = new ArrayList<>();
        ArrayList<Cliente> listaClientesQuemados = new ArrayList<>();
        ArrayList<Paquete> listaPaquetesQuemados = new ArrayList<>();
        ArrayList<GuiaTuristico> listaGuiasQuemados = new ArrayList<>();
        ArrayList<String> listaIdiomas1 = new ArrayList<>();
        ArrayList<String> listaIdiomas2 = new ArrayList<>();
        ArrayList<String> listaIdiomas3 = new ArrayList<>();
        ArrayList<Destino> listaDestinos1 = new ArrayList<>();
        ArrayList<Destino> listaDestinos2 = new ArrayList<>();
        listaIdiomas1.add("Español");
        listaIdiomas1.add("Ingles");
        listaIdiomas1.add("Frances");
        listaIdiomas2.add("Ingles");
        listaIdiomas2.add("Frances");
        listaIdiomas2.add("Portugues");
        listaIdiomas2.add("Italiano");
        listaIdiomas3.add("Español");
        listaIdiomas3.add("Portugues");
        listaIdiomas3.add("Ruso");

        ArrayList<Valoracion> listaValoracionesQuemadas;
        Paquete paquete;
        Destino destino;
        Cliente cliente;
        Valoracion valoracion;
        GuiaTuristico guia;


        //[1ER PAQUETE] hay un paquete por el momento, con 3 destinos, estos ultimos con 5 clientes cada uno

        // (1ER DESTINO) para la primera lista de valoraciones del primer destino
        listaValoracionesQuemadas = new ArrayList<Valoracion>();

        //1ER CLIENTE
        cliente = Cliente.builder()
                .cedula("6789090")
                .nombre("Jane Smith")
                .telefono("310291212")
                .foto("/imagenes/userFem1.jpeg")
                .correo("JaneSmith@gmail.com")
                .direccion("Calle 456 Oak St")
                .contrasenia("jane123")
                .paquetesReservados(new ArrayList<>())
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
                .paquetesReservados(new ArrayList<>())
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
                .paquetesReservados(new ArrayList<>())
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
                .paquetesReservados(new ArrayList<>())
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
                .paquetesReservados(new ArrayList<>())
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
                .fotos(new ArrayList<>(List.of("/imagenes/tulum1.png", "/imagenes/tulum2.png", "/imagenes/tulum3.png")))
                .valoraciones(listaValoracionesQuemadas)
                .build();

        listaDestinos1.add(destino);
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
                .paquetesReservados(new ArrayList<>())
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
                .paquetesReservados(new ArrayList<>())
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
                .paquetesReservados(new ArrayList<>())
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
                .paquetesReservados(new ArrayList<>())
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
                .paquetesReservados(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(4)
                .cliente(cliente)
                .comentario("¡Todo fue increible! Disfrutamos de las recomendaciones de destinos y " +
                        "de la organización del viaje en general. Los alojamientos y excursiones cumplieron con nuestras expectativas. " +
                        "Recomendamos su agencia a nuestros amigos y familiares. Uno de los plus que nos gustó fue" +
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
                .fotos(new ArrayList<>(List.of("/imagenes/hawai1.png", "/imagenes/hawai2.png", "/imagenes/hawai3.png")))
                .valoraciones(listaValoracionesQuemadas)
                .build();

        listaDestinos1.add(destino);
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
                .paquetesReservados(new ArrayList<>())
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
                .paquetesReservados(new ArrayList<>())
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
                .paquetesReservados(new ArrayList<>())
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
                .paquetesReservados(new ArrayList<>())
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
                .paquetesReservados(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(5)
                .cliente(cliente)
                .comentario("Nuestra experiencia de viaje fue inolvidable. A pesar de que hubo problemas en la organización " +
                        " de fechas que se tenian planeadas, fueron bastantes rapidos en solucionar el problema." +
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
                .fotos(new ArrayList<>(List.of("/imagenes/yellowstone1.png", "/imagenes/yellowstone2.png", "/imagenes/yellowstone3.png")))
                .valoraciones(listaValoracionesQuemadas)
                .build();

        listaDestinos1.add(destino);
        listaDestinosQuemados.add(destino);

        //[Creacion PAQUETE 1]
        //fecha para el paquete
        LocalDate fecha = LocalDate.of(2023, 12, 25);
        paquete = Paquete.builder()
                .nombre("Expedición cultural y reconexión con la naturaleza")
                .destinos(listaDestinos1)
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

        //DATOS DEL NUEVO PAQUETE
        listaValoracionesQuemadas = new ArrayList<Valoracion>();
        // 1ER CLIENTE
        cliente = Cliente.builder()
                .cedula("111222333")
                .nombre("Juan Pérez")
                .telefono("3156789012")
                .foto("/imagenes/userMale1.jpeg")
                .correo("juanperez@gmail.com")
                .direccion("Av. Principal 123")
                .contrasenia("juanito123")
                .paquetesReservados(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(4)
                .cliente(cliente)
                .comentario("¡Una experiencia maravillosa! La atención al cliente fue " +
                        "excepcional y las recomendaciones para el itinerario fueron " +
                        "muy útiles. Disfruté cada momento del viaje.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // 2DO CLIENTE
        cliente = Cliente.builder()
                .cedula("44453455666")
                .nombre("María González")
                .telefono("3009876543")
                .foto("/imagenes/userFem1.jpeg")
                .correo("mariagonzalez@gmail.com")
                .direccion("Calle 7 Sur #45")
                .contrasenia("maria123")
                .paquetesReservados(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(5)
                .cliente(cliente)
                .comentario("¡Una experiencia fantástica! Todos los servicios fueron " +
                        "más allá de mis expectativas. La organización del viaje, " +
                        "los alojamientos y las excursiones fueron excelentes.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // 3ER CLIENTE
        cliente = Cliente.builder()
                .cedula("88821999000")
                .nombre("Carlos López")
                .telefono("3187654321")
                .foto("/imagenes/userMale3.jpeg")
                .correo("carloslopez@gmail.com")
                .direccion("Calle 15 #20-30")
                .contrasenia("carlos123")
                .paquetesReservados(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(3)
                .cliente(cliente)
                .comentario("Mi experiencia fue aceptable, aunque hubo algunos contratiempos " +
                        "durante el viaje. Sin embargo, en general, disfruté de la " +
                        "experiencia.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // 4TO CLIENTE
        cliente = Cliente.builder()
                .cedula("123213456789")
                .nombre("Laura Ramírez")
                .telefono("3056781234")
                .foto("/imagenes/userFem4.jpeg")
                .correo("lauraramirez@gmail.com")
                .direccion("Av. Libertador #10-15")
                .contrasenia("laura123")
                .paquetesReservados(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(4)
                .cliente(cliente)
                .comentario("Mi experiencia fue bastante buena. Hubo algunos detalles menores, " +
                        "pero en general, disfruté del viaje.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // 5TO CLIENTE
        cliente = Cliente.builder()
                .cedula("11122333333")
                .nombre("Gabriela Fernández")
                .telefono("3012345678")
                .foto("/imagenes/userFem5.jpeg")
                .correo("gabrielafernandez@gmail.com")
                .direccion("Calle 20 #30-40")
                .contrasenia("gabriela123")
                .paquetesReservados(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(5)
                .cliente(cliente)
                .comentario("¡Mi viaje fue excepcional! Disfruté de cada momento, sin duda una experiencia que repetiría.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // NUEVO DESTINO
        destino = Destino.builder()
                .nombre("Montañas Rocosas")
                .ciudad("Colorado")
                .descripcion("Descubre la majestuosidad de las Montañas Rocosas en Colorado. Un paraíso " +
                        "para los amantes de la naturaleza y los deportes al aire libre.")
                .clima(TipoClima.TEMPLADO) // Podrías tener un enum con diferentes tipos de clima
                .fotos(new ArrayList<>(List.of("/imagenes/montana1.jpg", "/imagenes/montana2.jpg", "/imagenes/montana3.jpg")))
                .valoraciones(listaValoracionesQuemadas)
                .build();

        // Agregar el nuevo destino a la lista de destinos quemados
        listaDestinosQuemados.add(destino);
        listaDestinos2.add(destino);

        // (2DO DESTINO)
        listaValoracionesQuemadas = new ArrayList<>(); // Limpiar lista de valoraciones

        // 1ER CLIENTE DEL 2DO DESTINO
        cliente = Cliente.builder()
                .cedula("7778832896799")
                .nombre("Luis Martínez")
                .telefono("3101122334")
                .foto("/imagenes/userMale2.jpeg")
                .correo("luismartinez@gmail.com")
                .direccion("Carrera 25 #67")
                .contrasenia("luisillo123")
                .paquetesReservados(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente);

        valoracion = Valoracion.builder()
                .puntuacion(4)
                .cliente(cliente)
                .comentario("¡Una experiencia increíble! Recomendaría este destino a cualquier viajero. " +
                        "Todo estuvo perfectamente planificado y los alojamientos fueron excepcionales.")
                .build();
        listaValoracionesQuemadas.add(valoracion);
        // 2DO CLIENTE
        Cliente cliente1 = Cliente.builder()
                .cedula("111222333")
                .nombre("Luisa García")
                .telefono("3156789012")
                .foto("/imagenes/userFem1.jpeg")
                .correo("luisagarcia@gmail.com")
                .direccion("Calle 7 Sur #45")
                .contrasenia("luisa123")
                .paquetesReservados(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente1);
        valoracion = Valoracion.builder()
                .puntuacion(4)
                .cliente(cliente1)
                .comentario("¡Excelente servicio y atención al cliente! El viaje superó mis expectativas.")
                .build();
        listaValoracionesQuemadas.add(valoracion);


        // 2ER CLIENTE
        Cliente cliente2 = Cliente.builder()
                .cedula("444555666")
                .nombre("Pedro Martínez")
                .telefono("3009876543")
                .foto("/imagenes/userMale1.jpeg")
                .correo("pedromartinez@gmail.com")
                .direccion("Carrera 25 #67")
                .contrasenia("pedro123")
                .paquetesReservados(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente2);

        valoracion = Valoracion.builder()
                .puntuacion(3)
                .cliente(cliente2)
                .comentario("Buen servicio, aunque hubo pequeños problemas con las reservas.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // 4TO CLIENTE
        Cliente cliente3 = Cliente.builder()
                .cedula("777888999")
                .nombre("Laura Ramírez")
                .telefono("3101122334")
                .foto("/imagenes/userFem2.jpeg")
                .correo("lauraramirez@gmail.com")
                .direccion("Av. Principal 123")
                .contrasenia("laura123")
                .paquetesReservados(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente3);

        valoracion = Valoracion.builder()
                .puntuacion(5)
                .cliente(cliente3)
                .comentario("¡Una experiencia increíble! Todo salió perfecto, sin ningún contratiempo.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        // 5TO CLIENTE
        Cliente cliente4 = Cliente.builder()
                .cedula("123456789")
                .nombre("Jorge Rodríguez")
                .telefono("3056781234")
                .foto("/imagenes/userMale2.jpeg")
                .correo("jorgerodriguez@gmail.com")
                .direccion("Calle 15 #20-30")
                .contrasenia("jorge123")
                .paquetesReservados(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente4);

        valoracion = Valoracion.builder()
                .puntuacion(1)
                .cliente(cliente4)
                .comentario("Tremenda porquería de vacaciones, 0 recomendado.")
                .build();
        listaValoracionesQuemadas.add(valoracion);

        destino = Destino.builder()
                .nombre("Playa Paraíso")
                .ciudad("Riviera Maya")
                .descripcion("Descubre la belleza de Playa Paraíso en la Riviera Maya. Aguas cristalinas y " +
                        "arena blanca te esperan en este paraíso tropical.")
                .clima(TipoClima.TROPICAL)
                .fotos(new ArrayList<>(List.of("/imagenes/playaparaiso1.jpg", "/imagenes/playaparaiso2.jpg", "/imagenes/playaparaiso3.jpg")))
                .valoraciones(listaValoracionesQuemadas)
                .build();

        // Agregar el nuevo destino a la lista de destinos existente
        listaDestinosQuemados.add(destino);
        listaDestinos2.add(destino);

        // (3ER DESTINO)
        listaValoracionesQuemadas = new ArrayList<>();

        // 1ER CLIENTE DEL 3ER DESTINO
        Cliente cliente1TercerDestino = Cliente.builder()
                .cedula("1239832452347654")
                .nombre("Ana Rodríguez")
                .telefono("3505432198")
                .foto("/imagenes/userFem3.jpeg")
                .correo("anarodriguez@gmail.com")
                .direccion("Calle 10 #30-15")
                .contrasenia("ana123")
                .paquetesReservados(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente1TercerDestino);

        Valoracion valoracionCliente1TercerDestino = Valoracion.builder()
                .puntuacion(5)
                .cliente(cliente1TercerDestino)
                .comentario("¡Una experiencia asombrosa! Todo estuvo a la altura de mis expectativas. La agencia proporcionó un servicio excepcional.")
                .build();
        listaValoracionesQuemadas.add(valoracionCliente1TercerDestino);

// 2DO CLIENTE DEL 3ER DESTINO
        Cliente cliente2TercerDestino = Cliente.builder()
                .cedula("5678912345678901")
                .nombre("Javier Gómez")
                .telefono("3554321987")
                .foto("/imagenes/userMale1.jpeg")
                .correo("javiergomez@gmail.com")
                .direccion("Av. Libertadores #45")
                .contrasenia("javier123")
                .paquetesReservados(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente2TercerDestino);

        Valoracion valoracionCliente2TercerDestino = Valoracion.builder()
                .puntuacion(4)
                .cliente(cliente2TercerDestino)
                .comentario("Una experiencia muy satisfactoria. La agencia brindó un buen servicio en general.")
                .build();
        listaValoracionesQuemadas.add(valoracionCliente2TercerDestino);

// 3ER CLIENTE DEL 3ER DESTINO
        Cliente cliente3TercerDestino = Cliente.builder()
                .cedula("7890123456789012")
                .nombre("María Pérez")
                .telefono("3605432198")
                .foto("/imagenes/userFem2.jpeg")
                .correo("mariaperez@gmail.com")
                .direccion("Calle 20 #15-30")
                .contrasenia("maria123")
                .paquetesReservados(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente3TercerDestino);

        Valoracion valoracionCliente3TercerDestino = Valoracion.builder()
                .puntuacion(3)
                .cliente(cliente3TercerDestino)
                .comentario("Una experiencia promedio. Hubo algunos inconvenientes durante el viaje.")
                .build();
        listaValoracionesQuemadas.add(valoracionCliente3TercerDestino);

// 4TO CLIENTE DEL 3ER DESTINO
        Cliente cliente4TercerDestino = Cliente.builder()
                .cedula("3456789012345678")
                .nombre("Carlos Martínez")
                .telefono("3754321987")
                .foto("/imagenes/userMale2.jpeg")
                .correo("carlosmartinez@gmail.com")
                .direccion("Av. Bolívar #50")
                .contrasenia("carlos123")
                .paquetesReservados(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente4TercerDestino);

        Valoracion valoracionCliente4TercerDestino = Valoracion.builder()
                .puntuacion(2)
                .cliente(cliente4TercerDestino)
                .comentario("No fue una experiencia satisfactoria. Varios problemas afectaron mi viaje.")
                .build();
        listaValoracionesQuemadas.add(valoracionCliente4TercerDestino);

        // 5TO CLIENTE DEL 3ER DESTINO
        Cliente cliente5TercerDestino = Cliente.builder()
                .cedula("9876543210987654")
                .nombre("Sofía López")
                .telefono("3854321987")
                .foto("/imagenes/userFem3.jpeg")
                .correo("sofialopez@gmail.com")
                .direccion("Carrera 30 #40-50")
                .contrasenia("sofia123")
                .paquetesReservados(new ArrayList<>())
                .build();
        listaClientesQuemados.add(cliente5TercerDestino);

        Valoracion valoracionCliente5TercerDestino = Valoracion.builder()
                .puntuacion(4)
                .cliente(cliente5TercerDestino)
                .comentario("Una experiencia bastante buena. La agencia hizo un trabajo satisfactorio en general.")
                .build();
        listaValoracionesQuemadas.add(valoracionCliente5TercerDestino);

        destino = Destino.builder()
                .nombre("Selva Amazónica")
                .ciudad("Amazonas")
                .descripcion("Descubre la exuberante Selva Amazónica en la región del Amazonas. " +
                        "Sumérgete en la diversidad de la flora y fauna de este ecosistema único.")
                .clima(TipoClima.TROPICAL)
                .fotos(new ArrayList<>(List.of("/imagenes/amazon1.png", "/imagenes/amazon2.png", "/imagenes/amazon3.jpeg")))
                .valoraciones(listaValoracionesQuemadas)
                .build();

        // Agregar el nuevo destino a la lista de destinos existente
        listaDestinosQuemados.add(destino);
        listaDestinos2.add(destino);

        // Creación del paquete con los destinos existentes y los nuevos destinos
        LocalDate fechaPaquete = LocalDate.of(2023, 3, 15);
        Paquete paquete2 = Paquete.builder()
                .nombre("Experiencia Natural y Aventura Cultural")
                .destinos(listaDestinos2)
                .diasDuracion(10)
                .descripcion("Embárcate en una inolvidable expedición por la majestuosa Selva Amazónica, " +
                        "las Montañas Rocosas de Colorado y la paradisíaca Playa Paraíso. " +
                        "Este viaje combina la belleza natural y la aventura cultural en un único " +
                        "recorrido que te asombrará.")
                .precio(3500000.0)
                .cupoMaximo(25)
                .fecha(fechaPaquete)
                .build();

        listaPaquetesQuemados.add(paquete2);

        //Datos Holdan
        cliente = Cliente.builder().cedula("111222")
                .nombre("gg")
                .correo("holdann.lopezs@gmail.com")
                .telefono("312313112")
                .contrasenia("123")
                .direccion("dsfds")
                .foto("imagenes/user.png")
                .paquetesReservados(new ArrayList<>()).build();
        listaClientesQuemados.add(cliente);
        //-------------- GUIAS QUEMADOS ----------------//

        //Guia 1
        guia = GuiaTuristico.builder()
                .cedula("1113443234")
                .aniosExperiencia(3)
                .nombre("Esteban Valverde")
                .foto("imagenes/userMale7.jpeg")
                .telefono("3122345467")
                .edad(26)
                .listaIdiomas(listaIdiomas1)
                .valoraciones(listaValoracionesQuemadas)
                .build();
        listaGuiasQuemados.add(guia);

        //Guia 2
        guia = GuiaTuristico.builder()
                .cedula("1113543234")
                .aniosExperiencia(2)
                .nombre("Sahara Brooks")
                .foto("imagenes/userFem7.jpeg")
                .telefono("3190345467")
                .edad(24)
                .listaIdiomas(listaIdiomas3)
                .valoraciones(listaValoracionesQuemadas)
                .build();
        listaGuiasQuemados.add(guia);

        //Guia 3
        guia = GuiaTuristico.builder()
                .cedula("1002334438")
                .aniosExperiencia(6)
                .nombre("Juan Mesa")
                .foto("imagenes/userMale11.jpeg")
                .telefono("3012348797")
                .edad(29)
                .listaIdiomas(listaIdiomas2)
                .valoraciones(listaValoracionesQuemadas)
                .build();
        listaGuiasQuemados.add(guia);

        //Guia 4
        guia = GuiaTuristico.builder()
                .cedula("1847292810")
                .aniosExperiencia(1)
                .nombre("Juliana Giraldo")
                .foto("imagenes/userFem11.jpeg")
                .telefono("3128945647")
                .edad(21)
                .listaIdiomas(listaIdiomas1)
                .valoraciones(listaValoracionesQuemadas)
                .build();
        listaGuiasQuemados.add(guia);

        agenciaViajes.setListaGuias(listaGuiasQuemados);
        agenciaViajes.setListaDestinos(listaDestinosQuemados);
        agenciaViajes.setListaPaquetes(listaPaquetesQuemados);
        agenciaViajes.setListaClientes(listaClientesQuemados);
    }
}
