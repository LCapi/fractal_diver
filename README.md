# 🌌 Fractal Diver

**Fractal Diver** is an interactive Mandelbrot fractal explorer built with Java and JavaFX. It allows users to zoom in, pan around, and dynamically switch color palettes in real time to visualize the infinite beauty of fractals.

---

## ✨ Features

- 🔍 Zoom in/out with mouse scroll
- 🖱️ Click and drag to pan the view
- 🎨 Change color palettes on the fly using keyboard keys (`1` to `5`)
- ⚡ Fast rendering using JavaFX Canvas
- 🔧 Clean and modular code structure, ready for future fractals (Julia sets, Burning Ship, etc.)

---

## 🎮 Controls

| Action                 | Input                        |
|------------------------|------------------------------|
| Zoom                  | Mouse scroll                 |
| Pan / Move            | Click and drag               |
| Rainbow palette       | Press `1`                    |
| Black & white         | Press `2`                    |
| Fire (red tones)      | Press `3`                    |
| Ice (blue tones)      | Press `4`                    |
| Smooth coloring       | Press `5`                    |

---

## 🚀 Requirements

- Java **17** or higher  
- Maven **3.6+**

---

## 🛠️ How to Run

```bash
# Clone the repository
git clone https://github.com/LCapi/fractal_diver.git
cd fractal_diver

# Run the application
mvn clean javafx:run
