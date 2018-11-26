package gof.gui;

import gof.core.Tablero;
import gof.core.Celula;
import gof.core.DisplayDriver;
import javafx.scene.Group;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class JavaFXDisplayDriver implements DisplayDriver {
    private int sz;
    private TilePane tilePane = new TilePane(5,5);

    public JavaFXDisplayDriver(int boardSize, int cellSizePx, Tablero board) {
        sz = boardSize;
        tilePane.setPrefRows(boardSize);
        tilePane.setPrefColumns(boardSize);

        Celula[][] g = board.getTablero();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Color c = g[i][j].obtenerEstado() ? Color.STEELBLUE : Color.WHITE;
                Rectangle r = new Rectangle(cellSizePx, cellSizePx, c);
                tilePane.getChildren().add(r);
                
                attachListeners(r, g[i][j]);
            }
        }
    }

    @Override
    public void displayBoard(Tablero board) {
        Celula[][] g = board.getTablero();
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                Rectangle r = (Rectangle) tilePane.getChildren().get(boardToPaneCoords(i, j));
                r.setFill(g[i][j].obtenerEstado() ? Color.STEELBLUE : Color.WHITE);
            }
        }
    }

    public TilePane getPane() {
        return tilePane;
    }

    private int boardToPaneCoords(int i, int j) {
        return i * sz + j;
    }
    
    private void attachListeners(Rectangle r, Celula c) {
        r.setOnMousePressed(e -> { r.setFill(Color.GRAY); });

        r.setOnMouseClicked(e -> {
            r.setFill(c.obtenerEstado() ? Color.WHITE : Color.STEELBLUE);
            c.cambiarEstado(!c.obtenerEstado());
            c.actualizarEstado();
        });
    }
}
