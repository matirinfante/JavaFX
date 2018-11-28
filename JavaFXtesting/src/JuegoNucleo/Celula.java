package JuegoNucleo;

public class Celula {
    private boolean estado = false;
    private boolean debeCambiar;

    public Celula() {

    }

    public Celula(boolean estado) {
        this.estado = estado;
    }

    public void cambiarEstado(boolean estado) {
        debeCambiar = estado;
    }

    public void actualizarEstado() {
        estado = debeCambiar;
    }

    public boolean obtenerEstado() {
        return estado;
    }
}
