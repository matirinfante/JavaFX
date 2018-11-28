package gof.core;

import java.util.concurrent.Callable;

public class Tarea implements Callable {

    private Tablero tablero;
    private int nroFila;
    private boolean modo;

    public Tarea(Tablero tablero, int nroFila, boolean modo) {
        this.tablero = tablero;
        this.nroFila = nroFila;
        this.modo = modo;
    }

    public void setModo(boolean modo) {
        this.modo = modo;
    }

    @Override
    public Object call() throws Exception {
        //Dependiendo del modo, verifica o modifica.
        if (this.modo) {
            tablero.verificar(nroFila);
        } else {
            tablero.actualizar(nroFila);
        }
        return null;
    }
}
