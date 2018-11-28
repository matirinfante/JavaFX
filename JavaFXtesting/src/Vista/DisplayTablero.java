package Vista;

import JuegoNucleo.Tablero;
import JuegoNucleo.Celula;
import javafx.scene.Group;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DisplayTablero {
    private int sz;
    private TilePane panel = new TilePane(5,5);

    public DisplayTablero(int tamTablero, int tamCelula, Tablero tablero) {
        sz = tamTablero;
        panel.setPrefRows(tamTablero);
        panel.setPrefColumns(tamTablero);

        Celula[][] g = tablero.getTablero();
        for (int i = 0; i < tamTablero; i++) {
            for (int j = 0; j < tamTablero; j++) {
                Color c = g[i][j].obtenerEstado() ? Color.RED : Color.WHITE;
                Rectangle r = new Rectangle(tamCelula, tamCelula, c);
                panel.getChildren().add(r);
                
                setListener(r, g[i][j]);
            }
        }
    }

   public void mostrarTablero(Tablero t) {
        Celula[][] g = t.getTablero();
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                Rectangle r = (Rectangle) panel.getChildren().get(boardToPaneCoords(i, j));
                r.setFill(g[i][j].obtenerEstado() ? Color.RED : Color.WHITE);
            }
        }
    }

    public TilePane obtenerRectangulo() {
        return panel;
    }

    private int boardToPaneCoords(int i, int j) {
        return i * sz + j;
    }
    
    private void setListener(Rectangle r, Celula c) {
        r.setOnMousePressed(e -> { r.setFill(Color.GRAY); });

        r.setOnMouseClicked(e -> {
            r.setFill(c.obtenerEstado() ? Color.WHITE : Color.RED);
            c.cambiarEstado(!c.obtenerEstado());
            c.actualizarEstado();
        });
    }
}
