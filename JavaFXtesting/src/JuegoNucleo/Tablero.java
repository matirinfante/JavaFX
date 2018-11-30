package JuegoNucleo;

public class Tablero {

    private Celula[][] tablero;
    private int alto = 3;
    private int ancho = 3;

    public Tablero(Celula[][] grid) {
        this.tablero = grid;
        alto = ancho = grid.length;
    }

    /**
     * @param alto
     * @param ancho
     * @param p probabilidad de vida //test
     */
    public Tablero(int alto, int ancho, double p) {
        this.alto = alto;
        this.ancho = ancho;
        tablero = new Celula[alto][ancho];

        for (int h = 0; h < tablero.length; h++) {
            for (int w = 0; w < tablero[h].length; w++) {
                tablero[h][w] = new Celula();
                if (Math.random() <= p) {
                    tablero[h][w].cambiarEstado(true);
                    tablero[h][w].actualizarEstado();
                }
            }
        }
    }

    public Celula[][] getTablero() {
        return tablero;
    }

    public int getTamano() {
        return ancho;
    }

    public int verificarVecinos(int fila, int col) {
        int contarVivas = 0;
        int aux1, aux2;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                if (i != 0 || j != 0) {
                    aux1 = fila + i;
                    aux2 = col + j;

                    if (aux1 < 0) {
                        aux1 += ancho;
                    }

                    if (aux1 >= ancho) {
                        aux1 -= ancho;
                    }

                    if (aux2 < 0) {
                        aux2 += ancho;
                    }

                    if (aux2 >= ancho) {
                        aux2 -= ancho;
                    }

                    if (estaViva(aux1, aux2)) {
                        contarVivas++;
                    }
                }
            }
        }
        return contarVivas;
    }

    public boolean estaViva(int fila, int col) {
        return tablero[fila][col].obtenerEstado();
    }

    public void verificar(int fila) {
        for (int w = 0; w < tablero[fila].length; w++) {
            int nr = verificarVecinos(fila, w);
            if (nr < 2) {
                tablero[fila][w].cambiarEstado(false);
            } //soledad
            else if (nr > 3) {
                tablero[fila][w].cambiarEstado(false);
            } //sobrepoblacion
            else if (nr == 3) {
                tablero[fila][w].cambiarEstado(true);
            } //nace
            else if (nr == 2) {
                tablero[fila][w].cambiarEstado(tablero[fila][w].obtenerEstado());
            } // nada
        }
    }

    public void actualizar(int fila) {
        for (int w = 0; w < tablero[fila].length; w++) {
            tablero[fila][w].actualizarEstado();
        }
    }
}
