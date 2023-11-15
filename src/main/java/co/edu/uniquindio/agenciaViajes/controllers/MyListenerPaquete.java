package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.model.Paquete;

/**
 * La interfaz MyListener representa un listener para eventos de clic en paquetes.
 * Las clases que implementan esta interfaz pueden registrarse como listeners
 * para recibir notificaciones cuando se hace clic en un paquete.
 */
public interface MyListenerPaquete {

    /**
     * Se llama cuando se produce un evento de clic en un paquete.
     * @param paquete El paquete en el que se ha hecho clic.
     */
    public void onClickListener (Paquete paquete);
}
