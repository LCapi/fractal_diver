package com.fractal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class MandelbrotExplorer extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int MAX_ITER = 1000;

    private double xMin = -2.0;
    private double xMax = 1.0;
    private double yMin = -1.5;
    private double yMax = 1.5;

    private int paletteIndex = 0; // 0: rainbow, 1: BW, 2: fire, 3: ice, 4: smooth
    private Canvas canvas;

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawMandelbrot(gc);

        // Zoom con scroll
        canvas.setOnScroll(e -> {
            double zoomFactor = e.getDeltaY() > 0 ? 0.9 : 1.1;
            double mouseX = e.getX() / WIDTH;
            double mouseY = e.getY() / HEIGHT;

            double dx = xMax - xMin;
            double dy = yMax - yMin;

            double newDx = dx * zoomFactor;
            double newDy = dy * zoomFactor;

            double centerX = xMin + mouseX * dx;
            double centerY = yMin + mouseY * dy;

            xMin = centerX - newDx * mouseX;
            xMax = centerX + newDx * (1 - mouseX);
            yMin = centerY - newDy * mouseY;
            yMax = centerY + newDy * (1 - mouseY);

            drawMandelbrot(gc);
        });

        // Teclado para cambiar paletas
        Scene scene = new Scene(new StackPane(canvas));
        scene.setOnKeyPressed(this::handleKeyPress);

        stage.setScene(scene);
        stage.setTitle("Mandelbrot Explorer - JavaFX");
        stage.show();
    }

    private void handleKeyPress(KeyEvent e) {
        switch (e.getCode()) {
            case DIGIT1 -> paletteIndex = 0;
            case DIGIT2 -> paletteIndex = 1;
            case DIGIT3 -> paletteIndex = 2;
            case DIGIT4 -> paletteIndex = 3;
            case DIGIT5 -> paletteIndex = 4;
            default -> { return; }
        }
        drawMandelbrot(canvas.getGraphicsContext2D());
    }

    private void drawMandelbrot(GraphicsContext gc) {
        PixelWriter pw = gc.getPixelWriter();

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                double zx = xMin + x * (xMax - xMin) / WIDTH;
                double zy = yMin + y * (yMax - yMin) / HEIGHT;

                int iter = mandelbrotIterations(zx, zy);
                Color color = getColor(iter);
                pw.setColor(x, y, color);
            }
        }
    }

    private int mandelbrotIterations(double zx, double zy) {
        double x = 0, y = 0;
        int iter = 0;
        while (x * x + y * y <= 4 && iter < MAX_ITER) {
            double temp = x * x - y * y + zx;
            y = 2 * x * y + zy;
            x = temp;
            iter++;
        }
        return iter;
    }

    private Color getColor(int iter) {
        if (iter == MAX_ITER) return Color.BLACK;

        return switch (paletteIndex) {
            case 1 -> Color.grayRgb(255 * iter / MAX_ITER); // Blanco y negro
            case 2 -> Color.rgb(255 * iter / MAX_ITER, 50, 0); // Fuego
            case 3 -> Color.rgb(0, 100 * iter / MAX_ITER, 255); // Hielo
            case 4 -> { // Smooth coloring
                double smooth = iter + 1 - Math.log(Math.log(Math.sqrt(iter))) / Math.log(2);
                yield Color.hsb(360 * smooth / MAX_ITER, 1.0, 1.0);
            }
            default -> Color.hsb(360.0 * iter / MAX_ITER, 1.0, 1.0); // HSB arco iris
        };
    }

    public static void main(String[] args) { launch();}
}
