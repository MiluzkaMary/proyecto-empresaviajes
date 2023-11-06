package co.edu.uniquindio.agenciaViajes.exceptions;

public class UsuarioNoExistenteException extends Exception{
    public UsuarioNoExistenteException(String mensaje){
        super(mensaje);
    }
}
