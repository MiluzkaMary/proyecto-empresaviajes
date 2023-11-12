package co.edu.uniquindio.agenciaViajes.model;

import co.edu.uniquindio.agenciaViajes.enums.EstadoReserva;
import co.edu.uniquindio.agenciaViajes.exceptions.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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



    private AgenciaViajes(){
        inicializarLogger();
        log.info("Se crea una nueva instancia de AgenciaViajes" );

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

    private void inicializarLogger(){
        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter( new SimpleFormatter());
            log.addHandler(fh);
        }catch (IOException e){
            log.severe(e.getMessage() );
        }
    }

    public static AgenciaViajes getInstance(){
        if(agenciaViajes == null){
            agenciaViajes = new AgenciaViajes();
        }

        return agenciaViajes;
    }

    /**
     * Metodo que permite encontrar a un cliente por su cedula
     * @param cedula Cedula del cliente que se busca
     * @return Cliente al cual pertenece la cedula
     */
    public Cliente obtenerCliente(String cedula){
        return listaClientes.stream().filter(c -> c.getCedula().equals(cedula)).findFirst().orElse(null);
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
        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if(correo == null || correo.isBlank()){
            throw new AtributoVacioException("El correo es obligatorio");
        }

        if (!isValidEmail(correo)){
            throw new CorreoInvalidoException("El correo no cumple con el formato usuario@dominio.ext");
        }

        if(contrasenia == null || contrasenia.isBlank()){
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



    public Cliente registrarCliente(String cedula, String nombre, String telefono, String foto, String correo, String direccion,String contrasenia) throws AtributoVacioException, InformacionRepetidaException, DatoNoNumericoException, CorreoInvalidoException {

        if(cedula == null || cedula.isBlank()){
            throw new AtributoVacioException("La cédula es obligatoria");
        }

        if (!cedula.trim().matches("[0-9]+")){
            throw new DatoNoNumericoException("La cédula debe contener solo números");
        }

        if( obtenerCliente(cedula) != null ){
            throw new InformacionRepetidaException("La cédula "+cedula+" ya está registrada");
        }

        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if(telefono == null || telefono.isBlank()){
            throw new AtributoVacioException("El teléfono es obligatorio");
        }

        if (!telefono.trim().matches("[0-9]+")){
            throw new DatoNoNumericoException("El teléfono debe contener solo números");
        }

        if(correo == null || correo.isBlank()){
            throw new AtributoVacioException("El email es obligatorio");
        }

        if (!isValidEmail(correo)){
            throw new CorreoInvalidoException("El correo no cumple con el formato usuario@dominio.ext");
        }

        if(direccion == null || direccion.isBlank()){
            throw new AtributoVacioException("La dirección es obligatoria");
        }

        if(contrasenia == null || contrasenia.isBlank()){
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
                .paquetesFavoritos(new ArrayList<>())
                .build();

        listaClientes.add(cliente);
        //escribirCliente(cliente);

        log.info("Se ha registrado un nuevo cliente con la cédula: "+cedula);
        return cliente;
    }

    public GuiaTuristico buscarGuiaConCedula(String cedula){
        GuiaTuristico elegido=null;
        for(GuiaTuristico guia: listaGuias){
            if (guia.getCedula().equals(cedula)){
                elegido=guia;
            }
        }
        return elegido;
    }


    public Reserva registrarReserva(LocalDateTime fechaHora, LocalDate fechaInicial, LocalDate fechaFinal, Cliente cliente, int numPersonas,
                                    String cedulaGuia, EstadoReserva estadoReserva, Paquete paquete) throws AtributoVacioException {

        GuiaTuristico guiaElegido;
        String numerosCedula;
        if(cedulaGuia == null || cedulaGuia.isBlank()){
            throw new AtributoVacioException("Escoger un guia es obligatorio");
        }else{
            numerosCedula = cedulaGuia.substring(cedulaGuia.indexOf("-") + 1).trim();
            guiaElegido= buscarGuiaConCedula(numerosCedula);
        }

        if(guiaElegido == null){
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
                .build();

        listaReservas.add(reservaFinal);
        //escribirCliente(cliente);

        log.info("Se ha registrado una nueva reserva a las horas: "+fechaHora);
        return reservaFinal;
    }

    /**
     * Metodo que verifica si el email string cumple con el formato de usuario@dominio.ext
     * @param email
     * @return boolean true si es valido, false en caso contrario
     */
    public boolean isValidEmail(String email) {
        email = email.trim();
        String regex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9]+\\.)+[A-Za-z]{2,4}$";
        return email.matches(regex);
    }

    public int buscarUnidadesDisponibles(Paquete paquete){
        int unidadesDisponibles = paquete.getCupoMaximo();
        for (Reserva reserva : listaReservas) {
            if (reserva.getPaquete().equals(paquete) && reserva.getEstado() == EstadoReserva.PENDIENTE) {
                unidadesDisponibles -= reserva.getNumPersonas();
            }
        }
        return unidadesDisponibles;
    }

    public ArrayList<GuiaTuristico> obtenerGuiasTrabajandoEn(LocalDate fechaInicial, LocalDate fechaFinal) {
        ArrayList<GuiaTuristico> listaTrabajando = new ArrayList<>();

        for (Reserva reserva : listaReservas) {
            LocalDate fechaTrabajoInicio = reserva.getFechaInicio();
            LocalDate fechaTrabajoFin = reserva.getFechaFin();

            // Verificar si la reserva está dentro del rango especificado
            if ((fechaTrabajoInicio.isAfter(fechaInicial) || fechaTrabajoInicio.isEqual(fechaInicial)) &&
                    (fechaTrabajoFin.isBefore(fechaFinal) || fechaTrabajoFin.isEqual(fechaFinal))) {

                // Agregar el guía asociado a la reserva a la lista de guías trabajando
                GuiaTuristico guia = reserva.getGuia();
                listaTrabajando.add(guia);
            } else if ((fechaTrabajoInicio.isEqual(fechaInicial) || fechaTrabajoFin.isEqual(fechaFinal)) &&
                    !listaTrabajando.contains(reserva.getGuia())) {
                // Agregar el guía si trabaja el mismo día que fechaInicial o fechaFinal
                listaTrabajando.add(reserva.getGuia());
            }
        }

        return listaTrabajando;
    }

    public ArrayList<GuiaTuristico> obtenerGuiasDisponiblesEnFecha(LocalDate fechaInicial, LocalDate fechaFinal){
        ArrayList<GuiaTuristico> guiasDisponibles = new ArrayList<>();

        ArrayList<GuiaTuristico> guiasNoDisponibles= obtenerGuiasTrabajandoEn(fechaInicial, fechaFinal);
         for (GuiaTuristico guia: listaGuias){
             if (!guiasNoDisponibles.contains(guia)){
                 guiasDisponibles.add(guia);
             }
         }

        return guiasDisponibles;
    }
}
