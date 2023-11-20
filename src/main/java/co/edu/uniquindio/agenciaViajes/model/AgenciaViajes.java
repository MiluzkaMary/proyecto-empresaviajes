package co.edu.uniquindio.agenciaViajes.model;

import co.edu.uniquindio.agenciaViajes.enums.EstadoReserva;
import co.edu.uniquindio.agenciaViajes.enums.TipoClima;
import co.edu.uniquindio.agenciaViajes.exceptions.*;
import co.edu.uniquindio.agenciaViajes.util.ArchivoUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

@Getter
@Log
@Setter

public class AgenciaViajes {

    /**
     * Se declaran las listas y también a AgenciaViajes
     */
    private ArrayList<Cliente> listaClientes; //necesario que sea private final???
    private final ArrayList<Administrador> listaAdministradores;
    private ArrayList<GuiaTuristico> listaGuias;
    private ArrayList<Destino> listaDestinos;
    private ArrayList<Paquete> listaPaquetes;
    private ArrayList<Reserva> listaReservas;
    private static AgenciaViajes agenciaViajes;

    /**
     * Serializacion
     */
    private static final String RUTA_DESTINOS = "src/main/resources/persistencia/destinos.ser";
    private static final String RUTA_PAQUETES = "src/main/resources/persistencia/paquetes.ser";
    private static final String RUTA_RESERVAS = "src/main/resources/persistencia/reservas.ser";

    /**
     * Elementos necesarios para la implementación de JavaMail
     */
    private static String emailFrom = "traveldreamshelp@gmail.com";
    private static String passwordFrom = "dvctnkacqmfndtqv";
    private String emailTo;
    private String subject;
    private String content;
    private static Properties mProperties = new Properties();
    private static Session mSession;
    private MimeMessage mCorreo;

    /**
     * Instanciar AgenciaViajes
     */
    private AgenciaViajes() {
        inicializarLogger();
        log.info("Se crea una nueva instancia de AgenciaViajes");

        this.listaClientes = new ArrayList<>();
        //leerClientes();

        this.listaDestinos = new ArrayList<>();
        //leerDestinos();

        this.listaPaquetes = new ArrayList<>();
        //leerPaquetes();

        this.listaReservas = new ArrayList<>();
        //leerReservas();

        this.listaAdministradores = new ArrayList<>();
        //leerAdministradores();

        this.listaGuias = new ArrayList<>();
        //leerGuias();
    }

    /**
     * Inicializar los loggers
     */
    private void inicializarLogger() {
        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter(new SimpleFormatter());
            log.addHandler(fh);
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    /**
     * Método que instancia a AgenciaViajes
     */
    public static AgenciaViajes getInstance() {
        if (agenciaViajes == null) {
            agenciaViajes = new AgenciaViajes();
        }

        return agenciaViajes;
    }

    /**
     * Metodo que permite encontrar a un cliente por su cedula
     *
     * @param cedula Cedula del cliente que se busca
     * @return Cliente al cual pertenece la cedula
     */
    public Cliente obtenerCliente(String cedula, int i) {
        // Caso base: Si el índice es igual o mayor que el tamaño de la lista, retorna null
        if (i >= listaClientes.size()) {
            return null;
        }
        Cliente clienteActual = listaClientes.get(i);
        // Si se encuentra el cliente con la cédula buscada, lo retorna
        if (clienteActual.getCedula().equals(cedula)) {
            return clienteActual;
        }
        // Llamada recursiva con el siguiente índice
        return obtenerCliente(cedula, i + 1);    }

    /**
     * Método que permite encontrar a un Destino por medio de su Nombre
     * @param nombre que tiene el Destino
     * @param i contador general
     * @return destinoActual que se busca
     */
    public Destino obtenerDestinoConNombre(String nombre, int i) {
        // Caso base: Si el índice es igual o mayor que el tamaño de la lista, retorna null
        if (i >= listaDestinos.size()) {
            return null;
        }
        Destino destinoActual = listaDestinos.get(i);
        // Si se encuentra el destino con el nombre buscado, lo retorna
        if (destinoActual.getNombre().equals(nombre)) {
            return destinoActual;
        }
        // Llamada recursiva con el siguiente índice
        return obtenerDestinoConNombre(nombre, i + 1);
    }

    /**
     * Método que permite encontrar a un Guia por medio de su cedula
     * @param cedula que tiene el guia
     * @param i contador general
     * @return guiaActual que se busca
     */
    public GuiaTuristico obtenerGuia(String cedula, int i) {
// Caso base: Si el índice es igual o mayor que el tamaño de la lista, retorna null
        if (i >= listaGuias.size()) {
            return null;
        }
        GuiaTuristico guiaActual = listaGuias.get(i);
        // Si se encuentra el guía con la cédula buscada, lo retorna
        if (guiaActual.getCedula().equals(cedula)) {
            return guiaActual;
        }
        // Llamada recursiva con el siguiente índice
        return obtenerGuia(cedula, i + 1);
    }

    /**
     * Método que permite buscar un cliente por nombre, correo y contraseña.
     *
     * @param nombre      Nombre del cliente que se busca.
     * @param correo      Correo del cliente que se busca.
     * @param contrasenia Contraseña del cliente que se busca.
     * @return Cliente al que pertenecen los datos de búsqueda. Si no se encuentra, retorna null.
     */
    public Cliente obtenerClienteIngreso(String nombre, String correo, String contrasenia) {
        return listaClientes.stream()
                .filter(c -> c.getNombre().equals(nombre) && c.getCorreo().equals(correo) && c.getContrasenia().equals(contrasenia))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método que permite buscar un administrador por nombre, correo y contraseña.
     *
     * @param nombre      Nombre del administrador que se busca.
     * @param correo      Correo del administrador que se busca.
     * @param contrasenia Contraseña del administrador que se busca.
     * @return Administrador al que pertenecen los datos de búsqueda. Si no se encuentra, retorna null.
     */
    public Administrador obtenerAdministradorIngreso(String nombre, String correo, String contrasenia) {
        return listaAdministradores.stream()
                .filter(a -> a.getNombre().equals(nombre) && a.getCorreo().equals(correo) && a.getContrasenia().equals(contrasenia))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método necesario para iniciar sesión, promero valida los datos de los
     * parámetros y luego revisa si quien inició sesión fue un administrador o
     * un cliente
     * @param nombre que contiene el TextField
     * @param correo que contiene el TextField
     * @param contrasenia que contiene el TextField
     */
    public String buscarTipoUsuario(String nombre, String correo, String contrasenia) throws AtributoVacioException, CorreoInvalidoException, UsuarioNoExistenteException {
        if (nombre == null || nombre.isBlank()) {
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if (correo == null || correo.isBlank()) {
            throw new AtributoVacioException("El correo es obligatorio");
        }

        if (!isValidEmail(correo)) {
            throw new CorreoInvalidoException("El correo no cumple con el formato usuario@dominio.ext");
        }

        if (contrasenia == null || contrasenia.isBlank()) {
            throw new AtributoVacioException("La contraseña es obligatoria");
        }

        if (obtenerClienteIngreso(nombre, correo, contrasenia) != null) {
            return "Cliente";
        } else if (obtenerAdministradorIngreso(nombre, correo, contrasenia) != null) {
            return "Administrador";
        } else {
            throw new UsuarioNoExistenteException("El usuario no está registrado");
        }
    }

    /**
     * Método que registra un administrador (Se registra desde consola,
     * no desde alguna parte de la interfaz
     * @param cedula      Cedula del administrador
     * @param nombre      Nombre del administrador
     * @param correo      Correo del administrador
     * @param contrasenia Contraseña del administrador
     * @param foto        foto del administrador
     * @param telefono    telefono del administrador
     * @return Administrador que fue creado
     */
    public Administrador registrarAdministradorPrueba(String cedula, String nombre, String telefono, String foto, String correo, String contrasenia) {

        Administrador administrador = Administrador.builder()
                .cedula(cedula)
                .nombre(nombre)
                .telefono(telefono)
                .foto(foto)
                .correo(correo)
                .contrasenia(contrasenia)
                .build();

        listaAdministradores.add(administrador);
        //escribirCliente(cliente);

        log.info("Se ha registrado un nuevo administrador con la cédula: " + cedula);
        return administrador;
    }

    /**
     * Método que registra un cliente, primero se encarga de
     * validar los datos que se encuentran en los parámetros
     * en el caso de la lista de reservas, esta no se registra
     * solo se instancia
     * @param cedula      Cedula del cliente
     * @param nombre      Nombre del cliente
     * @param correo      Correo del cliente
     * @param contrasenia Contraseña del cliente
     * @param foto        foto del cliente
     * @param telefono    telefono del cliente
     * @param direccion
     * @return Cliente que fue creado.
     */
    public Cliente registrarCliente(String cedula, String nombre, String telefono, String foto, String correo, String direccion, String contrasenia) throws AtributoVacioException, InformacionRepetidaException, DatoNoNumericoException, CorreoInvalidoException {

        if (cedula == null || cedula.isBlank()) {
            throw new AtributoVacioException("La cédula es obligatoria");
        }

        if (!cedula.trim().matches("[0-9]+")) {
            throw new DatoNoNumericoException("La cédula debe contener solo números");
        }

        if (obtenerCliente(cedula, 0) != null) {
            throw new InformacionRepetidaException("La cédula " + cedula + " ya está registrada");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if (telefono == null || telefono.isBlank()) {
            throw new AtributoVacioException("El teléfono es obligatorio");
        }

        if (!telefono.trim().matches("[0-9]+")) {
            throw new DatoNoNumericoException("El teléfono debe contener solo números");
        }

        if (correo == null || correo.isBlank()) {
            throw new AtributoVacioException("El email es obligatorio");
        }

        if (!isValidEmail(correo)) {
            throw new CorreoInvalidoException("El correo no cumple con el formato usuario@dominio.ext");
        }

        if (direccion == null || direccion.isBlank()) {
            throw new AtributoVacioException("La dirección es obligatoria");
        }

        if (contrasenia == null || contrasenia.isBlank()) {
            throw new AtributoVacioException("La contraseña es obligatoria");
        }

        Cliente cliente = Cliente.builder()
                .cedula(cedula)
                .nombre(nombre)
                .telefono(telefono)
                .foto(foto)
                .correo(correo)
                .direccion(direccion)
                .contrasenia(contrasenia)
                .paquetesReservados(new ArrayList<>())
                .build();

        listaClientes.add(cliente);
        //escribirCliente(cliente);

        log.info("Se ha registrado un nuevo cliente con la cédula: " + cedula);
        return cliente;
    }

    /**
     * Los métodos #eliminarPaquete, #eliminarGuia y #eliminarDestino
     * son unicamente usados por un administrador, no están disponibles
     * para los clientes, y son esenciales para la gestión apropiada
     * de la Agencia de Viajes
     */
    public void eliminarPaquete(Paquete paquete) {
        listaPaquetes.remove(paquete);
    }

    public void eliminarGuia(GuiaTuristico guia) {
        listaGuias.remove(guia);
    }

    public void eliminarDestino(Destino destino) {
        listaDestinos.remove(destino);
    }

    /**
     * Método que ctra un paquete, primero se encarga de validar
     * los datos de los parámetros para después construir en paquete
     * @param listaDestinos      Cedula del paquete
     * @param nombre             Nombre del paquete
     * @param descripcion        Correo del paquete
     * @param diasDuracion       Contraseña del paquete
     * @param precio             Foto del paquete
     * @param cupoMaximo         Telefono del paquete
     * @param fecha              Fecha del paquete
     * @return Paquete que fue creado.
     */
    public Paquete crearPaquete(String nombre, ArrayList<Destino> listaDestinos, String diasDuracion, String descripcion, String precio, String cupoMaximo, LocalDate fecha) throws AtributoVacioException, DatoNoNumericoException, FechaInvalidaException {

        if (nombre == null || nombre.isBlank()) {
            throw new AtributoVacioException("El nombre del paquete es obligatorio");
        }

        if (listaDestinos == null) {
            throw new AtributoVacioException("El paquete debe tener al menos un destino");
        }

        if (diasDuracion == null || diasDuracion.isBlank()) {
            throw new AtributoVacioException("El número de dias del paquete es obligatorio");
        } else if (!diasDuracion.trim().matches("[0-9]+")) {
            throw new DatoNoNumericoException("El numero de días debe contener solo números");
        }

        if (descripcion == null || descripcion.isBlank()) {
            throw new AtributoVacioException("La descripción del destino es obligatorio");
        }

        if (precio == null || precio.isBlank()) {
            throw new AtributoVacioException("El precio del paquete es obligatorio");
        } else if (!precio.trim().matches("[0-9]+")) {
            throw new DatoNoNumericoException("El precio del paquete debe contener solo números");
        }

        if (cupoMaximo == null || cupoMaximo.isBlank()) {
            throw new AtributoVacioException("El cupo máximo del paquete es obligatorio");
        } else if (!cupoMaximo.trim().matches("[0-9]+")) {
            throw new DatoNoNumericoException("El cupo máximo del paquete debe contener solo números");
        }

        if (fecha == null) {
            throw new AtributoVacioException("La fecha del paquete es obligatoria");
        } else if (fecha.isBefore(LocalDate.now())) {
            throw new FechaInvalidaException("La fecha del paquete no puede ser de un dia ya pasado");
        }
        Paquete paquete = Paquete.builder()
                .nombre(nombre)
                .destinos(listaDestinos)
                .diasDuracion(Integer.valueOf(diasDuracion))
                .descripcion(descripcion)
                .precio(Double.valueOf(precio))
                .cupoMaximo(Integer.valueOf(cupoMaximo))
                .fecha(fecha)
                .build();

        listaPaquetes.add(paquete);
        //escribirPaquete(paquete);

        log.info("Se ha registrado un nuevo paquete con nombre: " + nombre);
        return paquete;
    }

    /**
     * Método que registra a un guía, este método solo lo pueden usar
     * los administradores, los clientes no tienen acceso alguno a este
     * método. Primero se validan los datos de los parámetros, luego se
     * construye el guía y por último se agrega a la lista de guías
     * @param cedula           Cedula del guía
     * @param nombre           Nombre del guía
     * @param telefono         Telefono del guía
     * @param aniosExperiencia Años de experiencia del guía
     * @param edad             Edad del guía
     * @param foto             Foto del guía
     * @param listaIdiomas     Lista de idiomas que habla el guía del guía
     * @return guía que fue creado.
     */
    public GuiaTuristico registrarGuia(String cedula, String nombre, String telefono, String foto, String edad, ArrayList<String> listaIdiomas, String aniosExperiencia) throws AtributoVacioException, DatoNoNumericoException, InformacionRepetidaException {
        if (cedula == null || cedula.isBlank()) {
            throw new AtributoVacioException("La cédula es obligatoria");
        }

        if (!cedula.trim().matches("[0-9]+")) {
            throw new DatoNoNumericoException("La cédula debe contener solo números");
        }

        if (obtenerGuia(cedula, 0) != null) {
            throw new InformacionRepetidaException("La cédula " + cedula + " ya está registrada");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if (telefono == null || telefono.isBlank()) {
            throw new AtributoVacioException("El teléfono es obligatorio");
        }

        if (!telefono.trim().matches("[0-9]+")) {
            throw new DatoNoNumericoException("El teléfono debe contener solo números");
        }

        if (edad == null || edad.isBlank()) {
            throw new AtributoVacioException("La edad es obligatorio");
        } else if (!edad.trim().matches("[0-9]+")) {
            throw new DatoNoNumericoException("La edad debe contener solo números");
        }

        if (foto == null || foto.isBlank()) {
            throw new AtributoVacioException("La foto es obligatoria");
        }

        if (listaIdiomas == null || listaIdiomas.isEmpty()) {
            throw new AtributoVacioException("Al menos 1 idioma es obligatorio");
        }

        if (aniosExperiencia == null || aniosExperiencia.isBlank()) {
            throw new AtributoVacioException("Los años de experiencia son obligatorios");
        }

        GuiaTuristico guia = GuiaTuristico.builder()
                .cedula(cedula)
                .nombre(nombre)
                .telefono(telefono)
                .foto(foto)
                .edad(Integer.parseInt(edad))
                .valoraciones(new ArrayList<>())
                .listaIdiomas(listaIdiomas)
                .aniosExperiencia(Integer.parseInt(aniosExperiencia))
                .build();

        listaGuias.add(guia);
        //escribirGuia(guia);

        log.info("Se ha registrado un nuevo guia con la cédula: " + cedula);
        return guia;
    }

    /**
     * Método que registra a un Destino, este método solo lo pueden usar
     * los administradores, los clientes no tienen acceso alguno a este
     * método. Primero se validan los datos de los parámetros, luego se
     * construye el destino y por último se agrega a la lista de destinos
     * @param ciudad           Ciudad donde se encuentra el destino
     * @param nombre           Nombre del destino
     * @param clima            Clima del destino (Es un ENUM)
     * @param descripcion      Descripcion del destino
     * @param listaFotos       Lista de Fotos referentes al destino
     * @return destino que fue creado.
     */
    public Destino registrarDestino(String nombre, String ciudad, String descripcion, String clima, List<String> listaFotos) throws AtributoVacioException {
        if (nombre == null || nombre.isBlank()) {
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if (ciudad == null || ciudad.isBlank()) {
            throw new AtributoVacioException("La ciudad es obligatoria");
        }

        if (descripcion == null || descripcion.isBlank()) {
            throw new AtributoVacioException("La descripción es obligatoria");
        }

        if (clima == null || clima.isBlank()) {
            throw new AtributoVacioException("El clima es obligatorio");
        }

        if (listaFotos == null || listaFotos.isEmpty()) {
            throw new AtributoVacioException("Al menos una foto es requerida para crear el destino");
        }

        Destino destino = Destino.builder()
                .nombre(nombre)
                .ciudad(ciudad)
                .descripcion(descripcion)
                .clima(obtenerTipoClima(clima))
                .fotos(listaFotos)
                .valoraciones(new ArrayList<>())
                .build();

        listaDestinos.add(destino);
        //escribirDestino(destino);

        log.info("Se ha registrado un nuevo destino con nombre: " + nombre);
        return destino;
    }

    /**
     * Método que retorna el tipo de clima de un destino
     * @param cadenaClima un String donde se guarda el tipo de Clima
     *                    para poder saber qué TipoClima retornar
     * @return TipoClima que fue hallado
     */
    public TipoClima obtenerTipoClima(String cadenaClima) {
        TipoClima clima = null;
        switch (cadenaClima) {
            case "TROPICAL":
                clima = TipoClima.TROPICAL;
                break;
            case "SECO":
                clima = TipoClima.SECO;
                break;
            case "TEMPLADO":
                clima = TipoClima.TEMPLADO;
                break;
            case "CONTINENTAL":
                clima = TipoClima.CONTINENTAL;
                break;
            case "POLAR":
                clima = TipoClima.POLAR;
                break;
        }
        return clima;
    }

    /**
     * Método que se encarga en editar un paquete ya existente,
     * esta es una acción que unicamente pueden hacer los administradores.
     * Primero se validan los datos de los parámetros y luego se le setean los
     * nuevos o viejos datos del paquete actualizado
     * @param cupoMaximo       El cupo máximo de personas que pueden formar parte
     *                         del paquete
     * @param nombre           Nombre del paquete
     * @param diasDuracion     Dias que dura el paquete
     * @param descripcion      Descripcion del paquete
     * @param listaDestinos    Lista de los Destinos que posee el paquete
     * @param  paquete         El paquete que se desea actualizar
     * @param fecha            La fecha en la que inicia el paquete
     * @param precio           Precio del paquete
     * @return Paquete que fue editado
     */
    public Paquete editarPaquete(Paquete paquete, String nombre, ArrayList<Destino> listaDestinos, String diasDuracion, String descripcion, String precio, String cupoMaximo, LocalDate fecha) throws AtributoVacioException, DatoNoNumericoException, FechaInvalidaException {

        if (nombre == null || nombre.isBlank()) {
            throw new AtributoVacioException("El nombre del paquete es obligatorio");
        }

        if (listaDestinos == null) {
            throw new AtributoVacioException("El paquete debe tener al menos un destino");
        }

        if (diasDuracion == null || diasDuracion.isBlank()) {
            throw new AtributoVacioException("El número de dias del paquete es obligatorio");
        } else if (!diasDuracion.trim().matches("[0-9]+")) {
            throw new DatoNoNumericoException("El numero de días debe contener solo números");
        }

        if (descripcion == null || descripcion.isBlank()) {
            throw new AtributoVacioException("La descripción del destino es obligatorio");
        }

        if (precio == null || precio.isBlank()) {
            throw new AtributoVacioException("El precio del paquete es obligatorio");
        } else if (!precio.trim().matches("[0-9]+")) {
            throw new DatoNoNumericoException("El precio del paquete debe contener solo números");
        }

        if (cupoMaximo == null || cupoMaximo.isBlank()) {
            throw new AtributoVacioException("El cupo máximo del paquete es obligatorio");
        } else if (!cupoMaximo.trim().matches("[0-9]+")) {
            throw new DatoNoNumericoException("El cupo máximo del paquete debe contener solo números");
        }

        if (fecha == null) {
            throw new AtributoVacioException("La fecha del paquete es obligatoria");
        } else if (fecha.isBefore(LocalDate.now())) {
            throw new FechaInvalidaException("La fecha del paquete no puede ser de un dia ya pasado");
        }


        Paquete paqueteNuevo = Paquete.builder()
                .nombre(nombre)
                .destinos(listaDestinos)
                .diasDuracion(Integer.valueOf(diasDuracion))
                .descripcion(descripcion)
                .precio(Double.valueOf(precio))
                .cupoMaximo(Integer.valueOf(cupoMaximo))
                .fecha(fecha)
                .build();

        listaPaquetes.replaceAll(p -> p.equals(paquete) ? paqueteNuevo : p);
        //escribirPaquete(paquete);

        log.info("Se ha editado un nuevo paquete con nombre: " + nombre);
        return paquete;
    }

    /**
     * Método que se encarga en editar un guia ya existente,
     * esta es una acción que unicamente pueden hacer los administradores.
     * Primero se validan los datos de los parámetros y luego se le setean los
     * nuevos o viejos datos del guia actualizado
     * @param cedula           Cedula del guía
     * @param nombre           Nombre del guía
     * @param telefono         Telefono del guía
     * @param aniosExperiencia Años de experiencia del guía
     * @param edad             Edad del guía
     * @param foto             Foto del guía
     * @param listaIdiomas     Lista de idiomas que habla el guía del guía
     * @return guía que fue editado.
     */
    public GuiaTuristico editarGuia(GuiaTuristico guiaAnterior, String cedula, String nombre, String telefono, String foto, String edad, ArrayList<String> listaIdiomas, String aniosExperiencia) throws AtributoVacioException, DatoNoNumericoException, InformacionRepetidaException {
        if (cedula == null || cedula.isBlank()) {
            throw new AtributoVacioException("La cédula es obligatoria");
        }

        if (!cedula.trim().matches("[0-9]+")) {
            throw new DatoNoNumericoException("La cédula debe contener solo números");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if (telefono == null || telefono.isBlank()) {
            throw new AtributoVacioException("El teléfono es obligatorio");
        }

        if (!telefono.trim().matches("[0-9]+")) {
            throw new DatoNoNumericoException("El teléfono debe contener solo números");
        }

        if (edad == null || edad.isBlank()) {
            throw new AtributoVacioException("La edad es obligatorio");
        } else if (!edad.trim().matches("[0-9]+")) {
            throw new DatoNoNumericoException("La edad debe contener solo números");
        }

        if (foto == null || foto.isBlank()) {
            throw new AtributoVacioException("La foto es obligatoria");
        }

        if (listaIdiomas == null || listaIdiomas.isEmpty()) {
            throw new AtributoVacioException("Al menos 1 idioma es obligatorio");
        }

        if (aniosExperiencia == null || aniosExperiencia.isBlank()) {
            throw new AtributoVacioException("Los años de experiencia son obligatorios");
        }

        GuiaTuristico guia = GuiaTuristico.builder()
                .cedula(cedula)
                .nombre(nombre)
                .telefono(telefono)
                .foto(foto)
                .edad(Integer.parseInt(edad))
                .valoraciones(guiaAnterior.getValoraciones())
                .listaIdiomas(listaIdiomas)
                .aniosExperiencia(Integer.parseInt(aniosExperiencia))
                .build();
        reemplazarGuiaParte2(guiaAnterior, guia, 0);
        //escribirGuia(guia);

        log.info("Se ha editado un nuevo guia con nombre: " + nombre);
        return guia;
    }

    /**
     * Método recursivo  para reemplazar al guia
     * @param guiaAnterior guia a editar
     * @param guia guia siendo editado
     * @param i indice general
     */
    private void reemplazarGuiaParte2(GuiaTuristico guiaAnterior, GuiaTuristico guia, int i) {
        if (i < listaGuias.size()) {
            if (listaGuias.get(i).equals(guiaAnterior)) {
                listaGuias.set(i, guia);
            }
            reemplazarGuiaParte2(guiaAnterior, guia, i + 1);
        }
    }

    /**
     * Método que se encarga en editar un dwstuino ya existente,
     * esta es una acción que unicamente pueden hacer los administradores.
     * Primero se validan los datos de los parámetros y luego se le setean los
     * nuevos o viejos datos del destino actualizado
     * @param destinoAnterior  Destino antes de ser editado
     * @param ciudad           Ciudad donde se encuentra el destino
     * @param nombre           Nombre del destino
     * @param clima            Clima del destino (Es un ENUM)
     * @param descripcion      Descripcion del destino
     * @param listaFotos       Lista de Fotos referentes al destino
     * @return destino que fue editado.
     * @throws AtributoVacioException
     * @throws InformacionRepetidaException
     */
    public Destino editarDestino(Destino destinoAnterior, String nombre, String ciudad, String descripcion, String clima, List<String> listaFotos) throws AtributoVacioException, InformacionRepetidaException {
        if (nombre == null || nombre.isBlank()) {
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if (ciudad == null || ciudad.isBlank()) {
            throw new AtributoVacioException("La ciudad es obligatoria");
        }

        if (descripcion == null || descripcion.isBlank()) {
            throw new AtributoVacioException("La descripción es obligatoria");
        }

        if (clima == null || clima.isBlank()) {
            throw new AtributoVacioException("El clima es obligatorio");
        }

        if (listaFotos == null || listaFotos.isEmpty()) {
            throw new AtributoVacioException("Al menos una foto es requerida para crear el destino");
        }

        Destino destino = Destino.builder()
                .nombre(nombre)
                .ciudad(ciudad)
                .descripcion(descripcion)
                .clima(obtenerTipoClima(clima))
                .fotos(listaFotos)
                .valoraciones(destinoAnterior.getValoraciones())
                .build();

        editarDestinoParte2(destinoAnterior, destino, 0);

        //escribirDestino(destino);

        log.info("Se ha editado un nuevo destino con nombre: " + nombre);
        return destino;
    }

    /**
     * Método recursivo para editar destinos
     * @param destinoAnterior destino antes de ser editado
     * @param destino         destino editado
     * @param i               indice general
     */
    private void editarDestinoParte2(Destino destinoAnterior, Destino destino, int i) {
        if (i < listaDestinos.size()) {
            if (listaDestinos.get(i).equals(destinoAnterior)) {
                listaDestinos.set(i, destino);
            }
            editarDestinoParte2(destinoAnterior, destino, i + 1);
        }
    }

    /**
     * Metodo que permite encontrar a un guia por su cedula
     *
     * @param cedula Cedula del cliente que se busca
     * @param i      indice global
     * @return guia al cual pertenece la cedula
     */
    public GuiaTuristico buscarGuiaConCedula(String cedula, int i) {
        if (i >= listaGuias.size()) {
            return null;
        }
        GuiaTuristico guia = listaGuias.get(i);
        if (guia.getCedula().equals(cedula)) {
            return guia;
        }
        return buscarGuiaConCedula(cedula, i + 1);
    }

    /**
     * Método que se encarga en registrar una reservación
     * esta es una acción que unicamente pueden hacer los clientes
     * Primero se validan los datos de los parámetros y luego se
     * añade la reserva a la lista de reservas
     * @param fechaHora         fecha y hora  en la que se hizo
     *                          la reservación
     * @param fechaInicial      fecha en la que la reserva inicia
     * @param fechaFinal        fecha cuando la reserva acaba
     * @param cliente           cliente que hizo la reserva
     * @param numPersonas       numero de personas que forma parte de la reserva
     * @param cedulaGuia        cedula del guía
     * @param estadoReserva     estado de la reserva
     * @param paquete           paquete que se reserva
     * @param valorTotal        valor final de la reserva
     * @return Reserva que se creó
     * @throws AtributoVacioException
     */
    public Reserva registrarReserva(LocalDateTime fechaHora, LocalDate fechaInicial, LocalDate fechaFinal, Cliente cliente, int numPersonas,
                                    String cedulaGuia, EstadoReserva estadoReserva, Paquete paquete, Double valorTotal) throws AtributoVacioException {
        GuiaTuristico guiaElegido;
        String numerosCedula;
        valorTotal = paquete.getPrecio() * numPersonas;
        if (cedulaGuia == null || cedulaGuia.isBlank()) {
            throw new AtributoVacioException("Escoger un guia es obligatorio");
        } else {
            numerosCedula = cedulaGuia.substring(cedulaGuia.indexOf("-") + 1).trim();
            guiaElegido = buscarGuiaConCedula(numerosCedula, 0);
        }

        if (guiaElegido == null) {
            throw new AtributoVacioException("El guía elegido no existe");
        }

        Reserva reservaFinal = Reserva.builder()
                .fechaSolicitud(fechaHora)
                .fechaInicio(fechaInicial)
                .fechaFin(fechaFinal)
                .cliente(cliente)
                .numPersonas(numPersonas)
                .guia(guiaElegido)
                .estado(estadoReserva)
                .paquete(paquete)
                .valorTotal(valorTotal)
                .build();

        listaReservas.add(reservaFinal);
        //escribirCliente(cliente);

        log.info("Se ha registrado una nueva reserva a las horas: " + fechaHora);
        return reservaFinal;
    }

    /**
     * Metodo que verifica si el email string cumple con el formato de usuario@dominio.ext
     *
     * @param email
     * @return boolean true si es valido, false en caso contrario
     */
    public boolean isValidEmail(String email) {
        email = email.trim();
        String regex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9]+\\.)+[A-Za-z]{2,4}$";
        return email.matches(regex);
    }

    /**
     * Método que se encarga de buscar las unidades disponibles
     * de un paquete
     * @param paquete   Paquete del que se busca disponibilidad
     * @param estado    Estado de la reserva
     * @param i         Indice general
     * @return Numero de unidades disponibles
     */
    public int buscarUnidadesDisponibles(Paquete paquete, EstadoReserva estado, int i) {
        if (i >= listaReservas.size()) {
            return paquete.getCupoMaximo();
        }

        Reserva reserva = listaReservas.get(i);
        if (reserva.getPaquete().equals(paquete) && reserva.getEstado() == estado) {
            int personasReservadas = reserva.getNumPersonas();
            int unidadesOcupadas = buscarUnidadesDisponibles(paquete, estado, i + 1);
            return unidadesOcupadas - personasReservadas;
        }

        return buscarUnidadesDisponibles(paquete, estado, i + 1);
    }

    /**
     * Método que busca los guías turísticos que están trabajando en un rango de fechas.
     * Itera sobre las reservas existentes para encontrar los guías asociados a esas reservas
     * que están trabajando dentro del rango de fechas proporcionado.
     *
     * @param fechaInicial   Fecha inicial del rango de búsqueda.
     * @param fechaFinal     Fecha final del rango de búsqueda.
     * @param i              Índice de la reserva actual en el proceso de búsqueda.
     * @param listaTrabajando Lista actual de guías que están trabajando (puede estar vacía).
     * @return ArrayList con los guías turísticos que están trabajando en el rango de fechas.
     */
    public ArrayList<GuiaTuristico> obtenerGuiasTrabajandoEn(LocalDate fechaInicial, LocalDate fechaFinal, int i, ArrayList<GuiaTuristico> listaTrabajando) {
        if (i >= listaReservas.size()) {
            return listaTrabajando;
        }

        Reserva reserva = listaReservas.get(i);
        LocalDate fechaTrabajoInicio = reserva.getFechaInicio();
        LocalDate fechaTrabajoFin = reserva.getFechaFin();

        if ((fechaTrabajoInicio.isAfter(fechaInicial) || fechaTrabajoInicio.isEqual(fechaInicial)) &&
                (fechaTrabajoFin.isBefore(fechaFinal) || fechaTrabajoFin.isEqual(fechaFinal))) {
            listaTrabajando.add(reserva.getGuia());
        } else if ((fechaTrabajoInicio.isEqual(fechaInicial) || fechaTrabajoFin.isEqual(fechaFinal)) &&
                !listaTrabajando.contains(reserva.getGuia())) {
            listaTrabajando.add(reserva.getGuia());
        }

        return obtenerGuiasTrabajandoEn(fechaInicial, fechaFinal, i + 1, listaTrabajando);
    }

    /**
     * Método que busca los guías turísticos disponibles en un rango de fechas.
     * Itera sobre la lista de guías y verifica si cada guía está trabajando
     * en el rango de fechas proporcionado. Agrega a la lista los guías que están disponibles.
     *
     * @param fechaInicial       Fecha inicial del rango de búsqueda.
     * @param fechaFinal         Fecha final del rango de búsqueda.
     * @param i                  Índice de guía actual en el proceso de búsqueda.
     * @param guiasDisponibles   Lista actual de guías disponibles (puede estar vacía).
     * @return ArrayList con los guías turísticos disponibles en el rango de fechas.
     */
    public ArrayList<GuiaTuristico> obtenerGuiasDisponiblesEnFecha(LocalDate fechaInicial, LocalDate fechaFinal,
                                                                   int i, ArrayList<GuiaTuristico> guiasDisponibles) {
        if (i >= listaGuias.size()) {
            return guiasDisponibles;
        }

        GuiaTuristico guia = listaGuias.get(i);
        ArrayList<GuiaTuristico> guiasTrabajando = obtenerGuiasTrabajandoEn(fechaInicial, fechaFinal, 0, new ArrayList<>());

        if (!guiasTrabajando.contains(guia)) {
            guiasDisponibles.add(guia);
        }

        // Llamada recursiva para procesar el siguiente guía en la lista.
        return obtenerGuiasDisponiblesEnFecha(fechaInicial, fechaFinal, i + 1, guiasDisponibles);
    }

    /**
     * Método que obtiene los nombres y cédulas de los guías disponibles para un paquete
     * en un rango de fechas dado. Utiliza la recursión para iterar sobre los guías disponibles
     * y agregar sus nombres y cédulas a la lista.
     *
     * @param paquete           Paquete turístico para el cual se buscan guías disponibles.
     * @param fechaFinal        Fecha final del rango de búsqueda.
     * @param i                 Índice del guía actual en el proceso de búsqueda.
     * @param guias             Lista actual de nombres y cédulas de guías (puede estar vacía).
     * @return ArrayList con los nombres y cédulas de los guías disponibles.
     */
    public ArrayList<String> obtenerNombresGuiasDisponiblesEnFecha(Paquete paquete, LocalDate fechaFinal,
                                                                   int i, ArrayList<String> guias) {
        if (i >= agenciaViajes.obtenerGuiasDisponiblesEnFecha(paquete.getFecha(), fechaFinal, 0, new ArrayList<>()).size()) {
            return guias;
        }

        GuiaTuristico guia = agenciaViajes.obtenerGuiasDisponiblesEnFecha(paquete.getFecha(), fechaFinal, 0, new ArrayList<>()).get(i);
        String nombreCedula = guia.getNombre() + " - " + guia.getCedula();
        guias.add(nombreCedula);

        // Llamada recursiva para procesar el siguiente guía disponible.
        obtenerNombresGuiasDisponiblesEnFecha(paquete, fechaFinal, i + 1, guias);
        return guias;
    }

    /**
     * Método para enviar un correo electrónico.
     * Configura las propiedades del servidor SMTP de Gmail y utiliza una sesión para enviar un correo.
     *
     * @param asunto         Asunto del correo electrónico.
     * @param contenido      Contenido del correo electrónico.
     * @param correoDestino  Dirección de correo electrónico del destinatario.
     */
    public void enviarCorreo(String asunto, String contenido, String correoDestino) {

        // Configuración del servidor SMTP de Gmail
        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable", "true");
        mProperties.setProperty("mail.smtp.port", "587");
        mProperties.setProperty("mail.smtp.user", emailFrom);
        mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.setProperty("mail.smtp.auth", "true");
        mSession = Session.getDefaultInstance(mProperties);

        try {
            // Creación del mensaje de correo
            Message mensaje = new MimeMessage(mSession);
            mensaje.setFrom(new InternetAddress(emailFrom)); // Dirección del remitente
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino)); // Dirección del destinatario
            mensaje.setSubject(asunto); // Asunto del correo
            mensaje.setText(contenido); // Contenido del correo

            // Envío del mensaje
            Transport transport = mSession.getTransport("smtp");
            transport.connect("smtp.gmail.com", 587, emailFrom, passwordFrom);
            transport.sendMessage(mensaje, mensaje.getAllRecipients());
            transport.close();

            System.out.println("Correo enviado exitosamente.");

        } catch (MessagingException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
        }
    }

    /**
     * Método para obtener destinos permitidos, excluyendo los destinos ya incluidos.
     * Verifica los destinos disponibles en la lista principal y agrega aquellos que no están presentes
     * en la lista de destinos ya incluidos a la lista de respuesta.
     *
     * @param listaDestinosYaIncluidos Lista de destinos que ya han sido incluidos.
     * @param i                        Índice actual para iterar sobre los destinos disponibles.
     * @param listaRespuesta           Lista de destinos permitidos (puede estar vacía).
     * @return ArrayList con los destinos permitidos, excluyendo los ya incluidos.
     */
    public ArrayList<Destino> obtenerDestinosPermitidos(List<Destino> listaDestinosYaIncluidos,
                                                        int i, ArrayList<Destino> listaRespuesta) {
        if (i >= getListaDestinos().size()) {
            return listaRespuesta;
        }

        Destino destino = getListaDestinos().get(i);
        if (!listaDestinosYaIncluidos.contains(destino)) {
            listaRespuesta.add(destino);
        }

        // Llamada recursiva para procesar el siguiente destino en la lista.
        obtenerDestinosPermitidos(listaDestinosYaIncluidos, i + 1, listaRespuesta);
        return listaRespuesta;
    }

    /**
     * Método para obtener los idiomas permitidos, excluyendo los idiomas ya incluidos.
     * Verifica los idiomas disponibles en la lista predefinida y agrega aquellos que no están presentes
     * en la lista de idiomas ya incluidos a la lista de respuesta.
     *
     * @param listaIdiomasYaIncluidos Lista de idiomas que ya han sido incluidos.
     * @param index                   Índice actual para iterar sobre la lista predefinida de idiomas.
     * @param listaRespuesta          Lista de idiomas permitidos (puede estar vacía).
     * @return ArrayList con los idiomas permitidos, excluyendo los ya incluidos.
     */
    public ArrayList<String> obtenerIdiomasPermitidos(List<String> listaIdiomasYaIncluidos,
                                                      int index, ArrayList<String> listaRespuesta) {
        List<String> idiomas = Arrays.asList(
                // Lista predefinida de idiomas disponibles
                "Español", "Inglés", "Mandarín", "Hindi", "Árabe", "Portugués",
                "Bengalí", "Ruso", "Japonés", "Alemán", "Francés", "Italiano",
                "Turco", "Coreano", "Holandés", "Polaco", "Vietnamita", "Tagalo",
                "Swahili", "Hebreo", "Griego", "Checo", "Húngaro", "Sueco",
                "Noruego", "Danés", "Finlandés", "Tailandés", "Malayo", "Indonesio",
                "Rumano", "Búlgaro", "Croata", "Serbio", "Eslovaco", "Esloveno",
                "Lituano", "Letón", "Estonio", "Georgiano", "Armenio", "Azerí",
                "Kazajo", "Ucraniano", "Mongol", "Tibetano", "Nepalí", "Seychelense"
        );

        if (index >= idiomas.size()) {
            return listaRespuesta;
        }

        String idioma = idiomas.get(index);
        if (!listaIdiomasYaIncluidos.contains(idioma)) {
            listaRespuesta.add(idioma);
        }

        // Llamada recursiva para procesar el siguiente idioma en la lista predefinida.
        obtenerIdiomasPermitidos(listaIdiomasYaIncluidos, index + 1, listaRespuesta);
        return listaRespuesta;
    }

    /**
     * Método estático para generar un código alfanumérico aleatorio.
     * Genera un código compuesto por dos letras aleatorias seguidas de cuatro dígitos aleatorios.
     *
     * @return Código alfanumérico generado.
     */
    public static String generarCodigo() {
        // Caracteres válidos para las letras
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // Objeto Random para generar números aleatorios
        Random random = new Random();

        // Generar dos letras aleatorias
        char letra1 = letras.charAt(random.nextInt(letras.length()));
        char letra2 = letras.charAt(random.nextInt(letras.length()));

        // Generar cuatro números aleatorios
        int numero = random.nextInt(10000);

        // Formatear el código resultante con dos letras y cuatro dígitos
        String codigo = String.format("%c%c%04d", letra1, letra2, numero);

        return codigo;
    }
    /**
     * Método para cambiar la contraseña del cliente.
     * Verifica que la contraseña y su confirmación coincidan antes de cambiarla.
     *
     * @param correo           Correo electrónico del cliente.
     * @param contrasenia      Nueva contraseña a establecer.
     * @param contraseniaConfirm Confirmación de la nueva contraseña.
     * @throws AtributoVacioException        Cuando algún atributo requerido está vacío.
     * @throws InformacionNoRepetidaException Cuando las contraseñas no coinciden.
     */
    public void cambiarContraseniaCliente(String correo, String contrasenia, String contraseniaConfirm)
            throws AtributoVacioException, InformacionNoRepetidaException {
        Cliente cliente = buscarClientePorCorreo(correo, 0);

        if (contrasenia.equals(contraseniaConfirm)) {
            cliente.setContrasenia(contrasenia);
            ArchivoUtils.mostrarMensaje("Cambio exitoso", "Operación completada",
                    "¡Felicidades " + cliente.getNombre() + ", su contraseña ha sido cambiada exitosamente!",
                    Alert.AlertType.INFORMATION);

        } else {
            throw new InformacionNoRepetidaException("Las contraseñas no coinciden");
        }
    }
    /**
     * Método para buscar un cliente por su dirección de correo electrónico.
     * Recorre la lista de clientes y devuelve el cliente correspondiente al correo proporcionado.
     *
     * @param correoABuscar Correo electrónico a buscar en los clientes.
     * @param i             Índice actual para iterar sobre la lista de clientes.
     * @return Cliente que coincide con el correo proporcionado.
     * @throws AtributoVacioException Cuando no se introduce ningún correo.
     */
    public Cliente buscarClientePorCorreo(String correoABuscar, int i) throws AtributoVacioException {
        Cliente clienteActual = listaClientes.get(i);

        if (!correoABuscar.isBlank()) {
            if (clienteActual.getCorreo().equals(correoABuscar)) {
                return clienteActual;
            } else {
                return buscarClientePorCorreo(correoABuscar, i + 1);
            }
        } else {
            throw new AtributoVacioException("No se ha introducido ningún correo");
        }
    }

    /**
     * Método para editar los detalles de un cliente.
     * Valida y actualiza los datos del cliente utilizando los parámetros proporcionados.
     *
     * @param clienteAnterior Cliente antes de la edición.
     * @param cedula          Nueva cédula del cliente.
     * @param nombre          Nuevo nombre del cliente.
     * @param telefono        Nuevo número de teléfono del cliente.
     * @param foto            Nueva foto del cliente.
     * @param direccion       Nueva dirección del cliente.
     * @param correo          Nuevo correo electrónico del cliente.
     * @param contrasenia     Nueva contraseña del cliente.
     * @return Cliente con los detalles actualizados.
     * @throws AtributoVacioException        Cuando algún atributo requerido está vacío.
     * @throws DatoNoNumericoException       Cuando un dato que debería ser numérico no lo es.
     * @throws DatoInvalidoException         Cuando un dato no cumple con el formato esperado.
     * @throws CorreoNoDisponibleException  Cuando el nuevo correo ya está registrado a otra cuenta.
     */
    public Cliente editarCliente(Cliente clienteAnterior, String cedula, String nombre, String telefono, String foto, String direccion, String correo, String contrasenia) throws AtributoVacioException, DatoNoNumericoException, DatoInvalidoException, CorreoNoDisponibleException {
        if (nombre == null || nombre.isBlank() || !nombre.matches("^[a-zA-Z]+$")) {
            throw new DatoInvalidoException("El nombre es inválido");
        }
        if (cedula == null || cedula.isBlank() || !cedula.matches("^\\d+$")) {
            throw new DatoInvalidoException("La cédula es inválida");
        }
        if (foto == null || foto.isBlank()) {
            throw new DatoInvalidoException("La foto no puede estar vacía");
        }
        if (!isValidEmail(correo)) {
            throw new DatoInvalidoException("El correo es inválido");
        }
        if (verificarExistenciaClienteCorreo(correo, 0)) {
            if (!buscarClientePorCorreo(correo, 0).getCedula().equals(clienteAnterior.getCedula())) {
                throw new CorreoNoDisponibleException("El correo ya se encuentra registrado a otra cuenta");
            }
        }
        if (direccion == null || direccion.isBlank()) {
            throw new DatoInvalidoException("La dirección es inválida");
        }
        if (contrasenia == null || contrasenia.isBlank()) {
            throw new DatoInvalidoException("La contraseña es inválida");
        }
        if (telefono == null || telefono.isBlank() || !telefono.matches("^\\d+$")) {
            throw new DatoInvalidoException("La contraseña es inválida");
        }
        Cliente cliente = Cliente.builder()
                .cedula(cedula)
                .nombre(nombre)
                .telefono(telefono)
                .foto(foto)
                .correo(correo)
                .direccion(direccion)
                .contrasenia(contrasenia)
                .paquetesReservados(new ArrayList<>())
                .build();
        // Invocar al método auxiliar para editar el cliente en la lista
        editarClienteParte2(clienteAnterior, cliente, 0);
        return cliente;
    }

    /**
     * Método auxiliar para editar un cliente en la lista de clientes.
     * Recorre la lista y reemplaza el cliente anterior con el nuevo cliente.
     *
     * @param clienteAnterior Cliente antes de la edición.
     * @param nuevoCliente    Nuevo cliente con los detalles actualizados.
     * @param index           Índice actual para iterar sobre la lista de clientes.
     */
    public void editarClienteParte2(Cliente clienteAnterior, Cliente nuevoCliente, int index) {
        if (index < listaClientes.size()) {
            if (listaClientes.get(index).equals(clienteAnterior)) {
                listaClientes.set(index, nuevoCliente);
            }
            editarClienteParte2(clienteAnterior, nuevoCliente, index + 1);
        }
    }

    /**
     * Verifica si existe un cliente con un correo electrónico específico en la lista de clientes.
     *
     * @param correo Correo electrónico a verificar.
     * @param i      Índice actual para iterar sobre la lista de clientes.
     * @return true si existe un cliente con el correo electrónico dado, false de lo contrario.
     */
    public boolean verificarExistenciaClienteCorreo(String correo, int i){
        if (i<listaClientes.size()) {
            if (listaClientes.get(i).getCorreo().equals(correo)) {
                return true;
            } else {
                return verificarExistenciaClienteCorreo(correo, i + 1);
            }
        }
        return false;
    }

    /**
     * Verifica si un correo electrónico dado es válido.
     *
     * @param correo Correo electrónico a verificar.
     * @return true si el correo es válido, false de lo contrario.
     */
    public boolean verificarValidezCorreo(String correo) {
        if(correo != null && !correo.isBlank()){
            if (isValidEmail(correo)){
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Envía un correo para recuperar la contraseña de la cuenta asociada al correo electrónico dado.
     * Genera un código de verificación y lo envía al correo proporcionado.
     *
     * @param correo Correo electrónico al que se enviará el código de recuperación.
     */
    public void enviarCorreoRecuperarContrasenia(String correo) {
        // Asunto del correo electrónico
        String asunto = "Código de recuperación para su cuenta";

        // Generar un código de verificación
        String code = generarCodigo();

        // Mensaje con el código de verificación
        String mensaje = "Su código de verificación es: \n" + code +
                "\nIMPORTANTE: Si usted no solicitó este código, haga caso omiso a este correo electrónico";

        // Enviar el correo con el código de recuperación
        enviarCorreo(asunto, mensaje, correo);
    }

    /**
     * Valida el proceso de recuperación de cuenta.
     * Verifica la validez y existencia del correo electrónico para enviar el código de recuperación.
     *
     * @param txtCorreo Campo de texto que contiene el correo electrónico para recuperar la cuenta.
     * @throws CorreoInvalidoException    Cuando el campo está vacío o se introduce un correo inválido.
     * @throws CorreoInexistenteException Cuando el correo ingresado no está asociado a ningún cliente.
     */
    public void validarRecuperarCuenta(TextField txtCorreo) throws CorreoInvalidoException, CorreoInexistenteException {
        // Verificar si el correo introducido es válido
        if (verificarValidezCorreo(txtCorreo.getText())) {
            // Verificar si el correo existe en la lista de clientes
            if (verificarExistenciaClienteCorreo(txtCorreo.getText(), 0)) {
                // Enviar correo de recuperación si el correo existe
                enviarCorreoRecuperarContrasenia(txtCorreo.getText());
            } else {
                throw new CorreoInexistenteException("El correo ingresado no está asociado a ningún cliente");
            }
        } else {
            throw new CorreoInvalidoException("El campo está vacío o se introdujo un correo inválido");
        }
    }

    /**
     * Compara dos cadenas de texto para validar si son iguales.
     *
     * @param text Cadena de texto a comparar.
     * @param code Código de verificación a comparar con la cadena de texto.
     * @throws InformacionNoRepetidaException Cuando los códigos no coinciden.
     * @throws AtributoVacioException         Cuando el campo de código está vacío.
     */
    public void compararDatos(String text, String code) throws InformacionNoRepetidaException, AtributoVacioException {
        // Verificar si el campo de texto no es nulo ni está vacío
        if (text != null && !text.isBlank()) {
            // Comparar el texto con el código de verificación
            if (text.equals(code)) {
                ArchivoUtils.mostrarMensaje("Éxito", "Código Validado", "El código es correcto", Alert.AlertType.INFORMATION);
            } else {
                throw new InformacionNoRepetidaException("Los códigos no coinciden");
            }
        } else {
            throw new AtributoVacioException("El campo de código no puede estar vacío");
        }
    }


    /**
     * Obtiene la cantidad de reservas por destino a partir de una lista de reservas.
     *
     * @param reservas Lista de reservas a procesar.
     * @return Un mapa que contiene la cantidad de reservas por cada destino.
     */
    public Map<String, Integer> obtenerDatosReservaCantidadDestinos(List<Reserva> reservas) {
        // Crear un mapa para contar las reservas por destino
        Map<String, Integer> contadorNombresDestinos = new HashMap<>();
        // Llamar al método auxiliar que realiza el conteo
        return obtenerDatosReservaCantidadDestinosParte2(reservas, 0, contadorNombresDestinos);
    }
    /**
     * Método auxiliar para contar la cantidad de reservas por destino a partir de una lista de reservas.
     * Realiza la actualización del contador de destinos de las reservas.
     *
     * @param reservas              Lista de reservas a procesar.
     * @param index                 Índice para iterar sobre la lista de reservas.
     * @param contadorNombresDestinos Mapa que contiene el contador de reservas por destino.
     * @return El mapa actualizado con la cantidad de reservas por cada destino.
     */
    private Map<String, Integer> obtenerDatosReservaCantidadDestinosParte2(List<Reserva> reservas, int index, Map<String, Integer> contadorNombresDestinos) {
        // Verificar si el índice está dentro de los límites de la lista de reservas
        if (index < reservas.size()) {
            // Obtener el paquete asociado a la reserva en la posición del índice actual
            Paquete paquete = reservas.get(index).getPaquete();
            // Actualizar el contador de destinos basado en el paquete de la reserva actual
            actualizarContadorDestinos(paquete, contadorNombresDestinos);
            // Llamar recursivamente al método para procesar la siguiente reserva
            return obtenerDatosReservaCantidadDestinosParte2(reservas, index + 1, contadorNombresDestinos);
        }
        // Si se ha recorrido toda la lista, retornar el mapa actualizado
        return contadorNombresDestinos;
    }
    /**
     * Actualiza el contador de destinos para las reservas de un paquete dado.
     * Llama al método auxiliar para realizar la actualización del contador.
     *
     * @param paquete               Paquete del que se obtienen los destinos.
     * @param contadorNombresDestinos Mapa que contiene el contador de reservas por destino.
     */
    private void actualizarContadorDestinos(Paquete paquete, Map<String, Integer> contadorNombresDestinos) {
        // Llama al método auxiliar para realizar la actualización del contador
        actualizarContadorDestinosParte2(paquete.getDestinos(), 0, contadorNombresDestinos);
    }

    /**
     * Método auxiliar para actualizar el contador de destinos para las reservas de un paquete.
     * Recorre la lista de destinos y actualiza el contador por cada destino encontrado.
     *
     * @param destinos               Lista de destinos del paquete.
     * @param index                  Índice para iterar sobre la lista de destinos.
     * @param contadorNombresDestinos Mapa que contiene el contador de reservas por destino.
     */
    private void actualizarContadorDestinosParte2(List<Destino> destinos, int index, Map<String, Integer> contadorNombresDestinos) {
        // Verificar si el índice está dentro de los límites de la lista de destinos
        if (index < destinos.size()) {
            // Obtener el nombre del destino en la posición del índice actual
            String nombreDestino = destinos.get(index).getNombre();
            // Incrementar el contador para el destino actual en el mapa
            contadorNombresDestinos.put(nombreDestino, contadorNombresDestinos.getOrDefault(nombreDestino, 0) + 1);
            // Llamar recursivamente al método para procesar el siguiente destino
            actualizarContadorDestinosParte2(destinos, index + 1, contadorNombresDestinos);
        }
    }

    /**
     * Obtiene la cantidad de reservas por nombre de paquete.
     *
     * @param reservas               Lista de reservas de las que se obtendrá la información.
     * @return Mapa con el contador de reservas por nombre de paquete.
     */
    public Map<String, Integer> obtenerDatosReservaCantidadPaquetes(List<Reserva> reservas) {
        Map<String, Integer> contadorNombresPaquetes = new HashMap<>();
        return obtenerDatosReservaCantidadPaquetesParte2(reservas, 0, contadorNombresPaquetes);
    }

    /**
     * Método auxiliar para obtener la cantidad de reservas por nombre de paquete.
     *
     * @param reservas               Lista de reservas de las que se obtendrá la información.
     * @param index                  Índice para iterar sobre la lista de reservas.
     * @param contadorNombresPaquetes Mapa que contiene el contador de reservas por nombre de paquete.
     * @return Mapa con el contador de reservas por nombre de paquete.
     */
    private Map<String, Integer> obtenerDatosReservaCantidadPaquetesParte2(List<Reserva> reservas, int index, Map<String, Integer> contadorNombresPaquetes) {
        // Verificar si el índice está dentro de los límites de la lista de reservas
        if (index < reservas.size()) {
            // Obtener el paquete asociado a la reserva en la posición del índice actual
            Paquete paquete = reservas.get(index).getPaquete();
            // Obtener el nombre del paquete
            String nombrePaquete = paquete.getNombre();
            // Actualizar el contador de paquetes con el nombre del paquete actual
            actualizarContadorPaquetes(nombrePaquete, contadorNombresPaquetes);
            // Llamar recursivamente al método para procesar la siguiente reserva
            return obtenerDatosReservaCantidadPaquetesParte2(reservas, index + 1, contadorNombresPaquetes);
        }
        // Retornar el mapa con el contador de reservas por nombre de paquete
        return contadorNombresPaquetes;
    }
    /**
     * Actualiza el contador de paquetes en el mapa.
     *
     * @param nombrePaquete           Nombre del paquete que se utilizará como clave en el mapa.
     * @param contadorNombresPaquetes Mapa que contiene el contador de reservas por nombre de paquete.
     */
    private void actualizarContadorPaquetes(String nombrePaquete, Map<String, Integer> contadorNombresPaquetes) {
        // Incrementa el contador del nombre de paquete en el mapa o establece 1 si no existe
        contadorNombresPaquetes.put(nombrePaquete, contadorNombresPaquetes.getOrDefault(nombrePaquete, 0) + 1);
    }

    /**
     * Cancela el estado de una reserva dada, cambiando su estado a "CANCELADA".
     *
     * @param reserva Reserva que se desea cancelar.
     *                (Se asume que la clase Reserva tiene un método equals implementado para comparar reservas).
     */
    public void cancelarEstadoReserva(Reserva reserva){
        for (Reserva posibleReserva : listaReservas) {
            if (posibleReserva.equals(reserva)) {
                posibleReserva.setEstado(EstadoReserva.CANCELADA);
                break; // Se detiene después de cambiar el estado de la reserva
            }
        }
    }
    /**
     * Califica a un guía turístico con un puntaje y un comentario.
     *
     * @param guia       Guía que se va a calificar.
     * @param puntaje    Puntaje asignado al guía.
     * @param cliente    Cliente que califica al guía.
     * @param comentario Comentario asociado a la valoración del guía.
     * @throws AtributoVacioException si el puntaje o el comentario son nulos o están vacíos.
     */
    public void calificarGuia(GuiaTuristico guia, String puntaje, Cliente cliente, String comentario) throws AtributoVacioException {
        if (puntaje==null || puntaje.isBlank()){
            throw new AtributoVacioException("Elegir un puntaje es obligatorio");
        }
        if (comentario==null || comentario.isBlank()){
            throw new AtributoVacioException("Escribir un comentario es obligatorio");
        }

        Valoracion valoracion = Valoracion.builder()
                .puntuacion(Integer.parseInt(puntaje))
                .cliente(cliente)
                .comentario(comentario)
                .build();

        for (GuiaTuristico guiaPosible : listaGuias){
            if (guiaPosible.equals(guia)){
                ArrayList<Valoracion> anterioresValoracion = guiaPosible.getValoraciones();
                anterioresValoracion.add(valoracion);
                guiaPosible.setValoraciones(anterioresValoracion);
                break;
            }
        }
    }


    public void verificarEstadoReservaLista(){
        LocalDate diaHoy= LocalDate.now();
        for (Reserva reserva: listaReservas){
            if (diaHoy.isAfter(reserva.getFechaInicio())){
                reserva.setEstado(EstadoReserva.CONFIRMADA);
            }
        }
    }

    public ArrayList<Reserva> obtenerReservasDeCliente(Cliente cliente) {

        verificarEstadoReservaLista();

        ArrayList<Reserva> reservasCliente = new ArrayList<>();

        for (Reserva reserva : listaReservas) {
            if (reserva.getCliente().equals(cliente)) {
                reservasCliente.add(reserva);
            }
        }

        return reservasCliente;
    }

    public void calificarDestino(Destino destino, String puntaje, Cliente cliente, String comentario) throws AtributoVacioException {
        if (puntaje==null || puntaje.isBlank()){
            throw new AtributoVacioException("Elegir un puntaje es obligatorio");
        }
        if (comentario==null || comentario.isBlank()){
            throw new AtributoVacioException("Escribir un comentario es obligatorio");
        }

        Valoracion valoracion = Valoracion.builder()
                .puntuacion(Integer.parseInt(puntaje))
                .cliente(cliente)
                .comentario(comentario)
                .build();

        for (Destino destinoPosible : listaDestinos){
            if (destinoPosible.equals(destino)){
                ArrayList<Valoracion> anterioresValoracion = destinoPosible.getValoraciones();
                anterioresValoracion.add(valoracion);
                destinoPosible.setValoraciones(anterioresValoracion);
                break;
            }
        }
    }
}
