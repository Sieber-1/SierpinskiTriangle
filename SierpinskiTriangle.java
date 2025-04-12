


import java.awt.Color;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

public class SierpinskiTriangle extends SierpinskiTriangleAbstract {

    private static final int MIN_DEPTH = 0; // Minimale Rekursionstiefe
    private static final int MAX_DEPTH = 7; // Maximale Rekursionstiefe

    public SierpinskiTriangle() {
        super();
    }

    @Override
    protected void drawTriangleRec(int ax, int ay, int bx, int by, int cx, int cy, int depth, Color color) {
        // Abbruchbedingung: Wenn die Seitenlaenge < 2 ist oder die Rekursionstiefe erreicht ist
        int baseWidth = bx - ax;
        int height = cy - ay;

        if (baseWidth < 2 || depth == 0) {
            // Zeichne das Dreieck
            g.setColor(color);
            int[] xPoints = { ax, bx, cx };
            int[] yPoints = { ay, by, cy };
            g.fillPolygon(xPoints, yPoints, 3);
            return;
        }

        // Berechne die Punkte D, E, F (Mittelpunkte der Seiten)
        int dx = (ax + bx) / 2;
        int dy = (ay + by) / 2;

        int ex = (bx + cx) / 2;
        int ey = (by + cy) / 2;

        int fx = (ax + cx) / 2;
        int fy = (ay + cy) / 2;

        // Zeichne das aktuelle Dreieck und "loesche" das mittlere Dreieck durch ein weisses Dreieck
        g.setColor(color);
        int[] xPoints = { ax, bx, cx };
        int[] yPoints = { ay, by, cy };
        g.fillPolygon(xPoints, yPoints, 3);

        g.setColor(Color.WHITE);
        int[] xWhite = { dx, ex, fx };
        int[] yWhite = { dy, ey, fy };
        g.fillPolygon(xWhite, yWhite, 3);

        // Rekursiv die 3 verbleibenden Dreiecke zeichnen
        drawTriangleRec(ax, ay, dx, dy, fx, fy, depth - 1, color); // Linkes Dreieck
        drawTriangleRec(dx, dy, bx, by, ex, ey, depth - 1, color); // Rechtes Dreieck
        drawTriangleRec(fx, fy, ex, ey, cx, cy, depth - 1, color); // Oberes Dreieck
    }

    @Override
    protected void handleInput(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP: // Pfeiltaste nach oben: Rekursionstiefe erh?hen
                depth = Math.min(depth + 1, MAX_DEPTH);
                break;
            case KeyEvent.VK_DOWN: // Pfeiltaste nach unten: Rekursionstiefe verringern
                depth = Math.max(depth - 1, MIN_DEPTH);
                break;
            case KeyEvent.VK_SPACE: // Leertaste: Zufallsfarben-Modus toggeln
                toggleRandomColor();
                break;
        }
        paint(getGraphics()); // Zeichenflaeche aktualisieren
    }

    @Override
    protected void toggleRandomColor() {
        useRandomColor = !useRandomColor; // Zufallsfarben-Modus toggeln
    }

    @Override
    protected void drawTriangle() {
        if (useRandomColor) {
            Random rand = new Random();
            color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)); // Zuf?llige Farbe
        } else {
            color = Color.BLACK; // Standardfarbe Schwarz
        }
        super.drawTriangle(); // Ruft die Methode der Oberklasse auf
    }

    public static void main(String[] args) {
        new SierpinskiTriangle();
    }
}
