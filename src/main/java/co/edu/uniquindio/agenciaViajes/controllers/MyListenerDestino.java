package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.model.Destino;

public interface MyListenerDestino {
    /**
     * Se llama cuando se produce un evento de clic en un destino.
     * @param destino El destino en el que se ha hecho clic.
     */
    public void onClickListener (Destino destino);
}
