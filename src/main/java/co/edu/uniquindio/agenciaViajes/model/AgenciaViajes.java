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

    private ArrayList<Cliente> listaClientes; //necesario que sea private final???
    private final ArrayList<Administrador> listaAdministradores;
    private ArrayList<GuiaTuristico> listaGuias;
    private ArrayList<Destino> listaDestinos;
    private ArrayList<Paquete> listaPaquetes;
    private ArrayList<Reserva> listaReservas;
    private static AgenciaViajes agenciaViajes;

    /**
     * Serializacion por texto plano
     */
    private static final String RUTA_CLIENTES = "src/main/resources/persistencia/clientes.txt";
    private static final String RUTA_ADMINISTRADORES = "src/main/resources/persistencia/administradores.txt";
    private static final String RUTA_GUIAS = "src/main/resources/persistencia/guias.txt";
    /**
     * Serializacion
     */
    private static final String RUTA_DESTINOS = "src/main/resources/persistencia/destinos.ser";
    private static final String RUTA_PAQUETES = "src/main/resources/persistencia/paquetes.ser";
    private static final String RUTA_RESERVAS = "src/main/resources/persistencia/reservas.ser";

    //Elementos para el envío de correos a través de JavaMail
    private static String emailFrom = "traveldreamshelp@gmail.com";
    private static String passwordFrom = "dvctnkacqmfndtqv";
    private String emailTo;
    private String subject;
    private String content;
    private static Properties mProperties = new Properties();
    private static Session mSession;
    private MimeMessage mCorreo;

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

    private void inicializarLogger() {
        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter(new SimpleFormatter());
            log.addHandler(fh);
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

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

    public void eliminarPaquete(Paquete paquete) {
        listaPaquetes.remove(paquete);
    }

    public void eliminarGuia(GuiaTuristico guia) {
        listaGuias.remove(guia);
    }

    public void eliminarDestino(Destino destino) {
        listaDestinos.remove(destino);
    }

    public Paquete crearPaquete(String nombre, List<Destino> listaDestinos, String diasDuracion, String descripcion, String precio, String cupoMaximo, LocalDate fecha) throws AtributoVacioException, DatoNoNumericoException, FechaInvalidaException {

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

    public Paquete editarPaquete(Paquete paquete, String nombre, List<Destino> listaDestinos, String diasDuracion, String descripcion, String precio, String cupoMaximo, LocalDate fecha) throws AtributoVacioException, DatoNoNumericoException, FechaInvalidaException {

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
    private void reemplazarGuiaParte2(GuiaTuristico guiaAnterior, GuiaTuristico guia, int i) {
        if (i < listaGuias.size()) {
            if (listaGuias.get(i).equals(guiaAnterior)) {
                listaGuias.set(i, guia);
            }
            reemplazarGuiaParte2(guiaAnterior, guia, i + 1);
        }
    }

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

        reemplazarDestinoParte2(destinoAnterior, destino, 0);

        //escribirDestino(destino);

        log.info("Se ha editado un nuevo destino con nombre: " + nombre);
        return destino;
    }

    private void reemplazarDestinoParte2(Destino destinoAnterior, Destino destino, int i) {
        if (i < listaDestinos.size()) {
            if (listaDestinos.get(i).equals(destinoAnterior)) {
                listaDestinos.set(i, destino);
            }
            reemplazarDestinoParte2(destinoAnterior, destino, i + 1);
        }
    }


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

    public ArrayList<GuiaTuristico> obtenerGuiasDisponiblesEnFecha(LocalDate fechaInicial, LocalDate fechaFinal,  int i, ArrayList<GuiaTuristico> guiasDisponibles) {
        if (i >= listaGuias.size()) {
            return guiasDisponibles;
        }

        GuiaTuristico guia = listaGuias.get(i);
        ArrayList<GuiaTuristico> guiasTrabajando = obtenerGuiasTrabajandoEn(fechaInicial, fechaFinal, 0, new ArrayList<>());

        if (!guiasTrabajando.contains(guia)) {
            guiasDisponibles.add(guia);
        }

        return obtenerGuiasDisponiblesEnFecha(fechaInicial, fechaFinal, i + 1, guiasDisponibles);
    }

    public void obtenerNombresGuiasDisponiblesEnFecha(Paquete paquete, LocalDate fechaFinal, int index, ArrayList<String> guias) {
        if (index >= agenciaViajes.obtenerGuiasDisponiblesEnFecha(paquete.getFecha(), fechaFinal, 0, new ArrayList<>()).size()) {
            return;
        }

        GuiaTuristico guia = agenciaViajes.obtenerGuiasDisponiblesEnFecha(paquete.getFecha(), fechaFinal, 0, new ArrayList<>()).get(index);
        String nombreCedula = guia.getNombre() + " - " + guia.getCedula();
        guias.add(nombreCedula);

        obtenerNombresGuiasDisponiblesEnFecha(paquete, fechaFinal, index + 1, guias);
    }

    public void enviarCorreo(String asunto, String contenido, String correoDestino) {

        // Simple mail transfer protocol
        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable", "true");
        mProperties.setProperty("mail.smtp.port", "587");
        mProperties.setProperty("mail.smtp.user", emailFrom);
        mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.setProperty("mail.smtp.auth", "true");
        mSession = Session.getDefaultInstance(mProperties);

        try {
            // Crear un mensaje de correo
            Message mensaje = new MimeMessage(mSession);
            mensaje.setFrom(new InternetAddress(emailFrom)); // Dirección del remitente
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino)); // Dirección del destinatario
            mensaje.setSubject(asunto); // Asunto del correo
            mensaje.setText(contenido); // Contenido del correo

            // Enviar el mensaje
            Transport transport = mSession.getTransport("smtp");
            transport.connect("smtp.gmail.com", 587, emailFrom, passwordFrom);
            transport.sendMessage(mensaje, mensaje.getAllRecipients());
            transport.close();

            System.out.println("Correo enviado exitosamente.");

        } catch (MessagingException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
        }
    }

    public ArrayList<Destino> obtenerDestinosPermitidos(List<Destino> listaDestinosYaIncluidos, int i, ArrayList<Destino> listaRespuesta) {
        if (i >= getListaDestinos().size()) {
            return listaRespuesta;
        }

        Destino destino = getListaDestinos().get(i);
        if (!listaDestinosYaIncluidos.contains(destino)) {
            listaRespuesta.add(destino);
        }

        obtenerDestinosPermitidos(listaDestinosYaIncluidos, i + 1, listaRespuesta);
        return listaRespuesta;
    }

    public ArrayList<String> obtenerIdiomasPermitidos(List<String> listaIdiomasYaIncluidos, int index, ArrayList<String> listaRespuesta) {
        List<String> idiomas = Arrays.asList(
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

        obtenerIdiomasPermitidos(listaIdiomasYaIncluidos, index + 1, listaRespuesta);
        return listaRespuesta;
    }


    // Método para generar un código aleatorio de 2 letras y 4 números
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

        // Formatear el código resultante
        String codigo = String.format("%c%c%04d", letra1, letra2, numero);

        return codigo;
    }

    public void cambiarContraseniaCliente(String correo, String contrasenia, String contraseniaConfirm) throws AtributoVacioException, InformacionNoRepetidaException {
        Cliente cliente = buscarClientePorCorreo(correo, 0);
        if (contrasenia.equals(contraseniaConfirm)) {
            cliente.setContrasenia(contrasenia);
            ArchivoUtils.mostrarMensaje("Cambio exitoso", "Operación completada", "¡Felicidades " + cliente.getNombre() + ", su contraseña ha sido cambiada exitosamente!", Alert.AlertType.INFORMATION);

        } else {
            throw new InformacionNoRepetidaException("Las contraseñas no coinciden");
        }
    }

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
        System.out.println("se mando al cliente"+ clienteAnterior.getCedula());
        editarClienteParte2(clienteAnterior, cliente, 0);
        return cliente;
    }

    public void editarClienteParte2(Cliente clienteAnterior, Cliente nuevoCliente, int index) {
        if (index < listaClientes.size()) {
            if (listaClientes.get(index).equals(clienteAnterior)) {
                listaClientes.set(index, nuevoCliente);
            }
            editarClienteParte2(clienteAnterior, nuevoCliente, index + 1);
        }
    }

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

    public boolean verificarValidezCorreo(String correo) {
        if(correo != null && !correo.isBlank()){
            if (isValidEmail(correo)){
                return true;
            }
            return false;
        }
        return false;
    }

    public void enviarCorreoRecuperarContrasenia(String correo) {
        String asunto = "Código de recuperación para su cuenta";
        String code = generarCodigo();
        String mensaje = "Su código de verificación es: \n" + code +
                "\nIMPORTANTE: Si usted no solicitó este código, haga caso omiso a este correo electrónico";
        enviarCorreo(asunto, mensaje, correo);
    }

    public void validarRecuperarCuenta(TextField txtCorreo) throws CorreoInvalidoException, CorreoInexistenteException {
        if (verificarValidezCorreo(txtCorreo.getText())){
            if (verificarExistenciaClienteCorreo(txtCorreo.getText(), 0)) {
                enviarCorreoRecuperarContrasenia(txtCorreo.getText());
            }
            else {
                throw new CorreoInexistenteException("El correo ingresado no está asociado a ningún cliente");
            }
        }
        else {
            throw new CorreoInvalidoException("El campo está vacío o se introdujo un correo inválido");
        }
    }

    public void compararDatos(String text, String code) throws InformacionNoRepetidaException, AtributoVacioException{
        if (text!=null && !text.isBlank()) {
            if (text.equals(code)) {
                ArchivoUtils.mostrarMensaje("Éxito", "Código Validado", "El código es correcto", Alert.AlertType.INFORMATION);
            } else {
                throw new InformacionNoRepetidaException("Los códigos no coinciden");
            }
        }
        else{
            throw new AtributoVacioException("El campo de código no puede estar vacío");
        }
    }

    public Map<String, Integer> obtenerDatosReservaCantidadDestinos(List<Reserva> reservas) {
        Map<String, Integer> contadorNombresDestinos = new HashMap<>();
        return obtenerDatosReservaCantidadDestinosParte2(reservas, 0, contadorNombresDestinos);
    }

    private Map<String, Integer> obtenerDatosReservaCantidadDestinosParte2(List<Reserva> reservas, int index, Map<String, Integer> contadorNombresDestinos) {
        if (index < reservas.size()) {
            Paquete paquete = reservas.get(index).getPaquete();
            actualizarContadorDestinos(paquete, contadorNombresDestinos);
            return obtenerDatosReservaCantidadDestinosParte2(reservas, index + 1, contadorNombresDestinos);
        }
        return contadorNombresDestinos;
    }

    private void actualizarContadorDestinos(Paquete paquete, Map<String, Integer> contadorNombresDestinos) {
        actualizarContadorDestinosParte2(paquete.getDestinos(), 0, contadorNombresDestinos);
    }

    private void actualizarContadorDestinosParte2(List<Destino> destinos, int index, Map<String, Integer> contadorNombresDestinos) {
        if (index < destinos.size()) {
            String nombreDestino = destinos.get(index).getNombre();
            contadorNombresDestinos.put(nombreDestino, contadorNombresDestinos.getOrDefault(nombreDestino, 0) + 1);
            actualizarContadorDestinosParte2(destinos, index + 1, contadorNombresDestinos);
        }
    }

    public Map<String, Integer> obtenerDatosReservaCantidadPaquetes(List<Reserva> reservas) {
        Map<String, Integer> contadorNombresPaquetes = new HashMap<>();
        return obtenerDatosReservaCantidadPaquetesParte2(reservas, 0, contadorNombresPaquetes);
    }

    private Map<String, Integer> obtenerDatosReservaCantidadPaquetesParte2(List<Reserva> reservas, int index, Map<String, Integer> contadorNombresPaquetes) {
        if (index < reservas.size()) {
            Paquete paquete = reservas.get(index).getPaquete();
            String nombrePaquete = paquete.getNombre();
            actualizarContadorPaquetes(nombrePaquete, contadorNombresPaquetes);
            return obtenerDatosReservaCantidadPaquetesParte2(reservas, index + 1, contadorNombresPaquetes);
        }
        return contadorNombresPaquetes;
    }

    private void actualizarContadorPaquetes(String nombrePaquete, Map<String, Integer> contadorNombresPaquetes) {
        contadorNombresPaquetes.put(nombrePaquete, contadorNombresPaquetes.getOrDefault(nombrePaquete, 0) + 1);
    }
}
