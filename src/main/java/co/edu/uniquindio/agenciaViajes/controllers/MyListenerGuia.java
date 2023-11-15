package co.edu.uniquindio.agenciaViajes.controllers;

import co.edu.uniquindio.agenciaViajes.model.Destino;
import co.edu.uniquindio.agenciaViajes.model.GuiaTuristico;

public interface MyListenerGuia {
    /**
     * Se llama cuando se produce un evento de clic en un guia.
     * @param guia El destino en el que se ha hecho clic.
     */
    public void onClickListener (GuiaTuristico guia);
}
