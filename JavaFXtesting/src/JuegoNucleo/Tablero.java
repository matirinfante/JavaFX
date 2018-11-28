package JuegoNucleo;

public class Tablero {

    private Celula[][] tablero;
    private int height = 3; //bottom right pos: grid[height-1][width-1]
    private int width = 3;

    public Tablero(Celula[][] grid) {
        this.tablero = grid;
        height = width = grid.length;
    }

    /**
     * @param alto
     * @param ancho
     * @param p probabilidad de vida //test
     */
    public Tablero(int alto, int ancho, double p) {
        this.height = alto;
        this.width = ancho;
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
        return width;
    }

    public int verificarVecinos(int fila, int col) {
        int sum = 0;
        // Positions numbered as phone dial
        if (fila != 0 && col != 0) {    //1
            if (estaViva(fila - 1, col - 1)) {
                sum++;
            }
        }

        if (fila != 0) {
            if (estaViva(fila - 1, col)) { //2
                sum++;
            }
        }

        if (fila != 0 && col != width - 1) {//3
            if (estaViva(fila - 1, col + 1)) {
                sum++;
            }
        }
        if (col != 0) {
            if (estaViva(fila, col - 1)) { //4
                sum++;
            }
        }
        //self
        if (col != width - 1) {
            if (estaViva(fila, col + 1)) { //6
                sum++;
            }
        }

        if (fila != height - 1 && col != 0) {
            if (estaViva(fila + 1, col - 1)) { //7
                sum++;
            }
        }

        if (fila != height - 1) {
            if (estaViva(fila + 1, col)) { //8
                sum++;
            }
        }

        if (fila != height - 1 && col != width - 1) {
            if (estaViva(fila + 1, col + 1)) { //9
                sum++;
            }
        }

        return sum;
    }

    public boolean estaViva(int fila, int col) {
        return tablero[fila][col].obtenerEstado();
    }

    /**
     * Assigns new state to individual Cells according to GoF rules
     *
     * @param fila
     */
    public void verificar(int fila) {
        for (int w = 0; w < tablero[fila].length; w++) {
            int nr = verificarVecinos(fila, w);
            if (nr < 2) {
                tablero[fila][w].cambiarEstado(false);
            } //underpop
            else if (nr > 3) {
                tablero[fila][w].cambiarEstado(false);
            } //overcrowd
            else if (nr == 3) {
                tablero[fila][w].cambiarEstado(true);
            } //born
            else if (nr == 2) {
                tablero[fila][w].cambiarEstado(tablero[fila][w].obtenerEstado());
            } // stay same
        }
    }

    public void actualizar(int fila) {
        for (int w = 0; w < tablero[fila].length; w++) {
            tablero[fila][w].actualizarEstado();
        }
    }
}
