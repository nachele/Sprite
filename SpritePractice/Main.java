import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

class Ventana {
    int width;
    int height;
    JFrame ventana;
    String titulo;

    public Ventana(int ancho, int alto, String titulo) {
        this.width = ancho;
        this.height = alto;
        this.titulo = titulo;
        this.ventana = new JFrame(this.titulo);
        this.ventana.setSize(this.width, this.height);
        this.ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    JFrame getVentana() {
        return this.ventana;
    }
}

class ImagenSprite extends JPanel {
    int x;
    int y;
    int speedx;
    int speedy;
    String imagenesName[] = new String[22];
    int indexImagen = 0;

    private BufferedImage image;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    public ImagenSprite(int posx, int posy, int speedx, int speedy) {
        try {
            // Carga la imagen desde un archivo
            image = ImageIO.read(new File("C:/Users/ignacio/Downloads/carpetaSprite/1.png"));
        } catch (IOException e) {
            System.out.println("No se pudo cargar la imagen.");
            e.printStackTrace();
        }
        this.x = posx;
        this.y = posy;
        this.speedx = speedx;
        this.speedy = speedy;
        for(int i = 0; i  < imagenesName.length; i++){
            imagenesName[i] = i + ".png";
        }
        // Añadimos el KeyListener al panel
        this.setFocusable(true); // Necesario para que el KeyListener funcione
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                // Cuando se presiona la tecla 'A' (izquierda)
                if (keyCode == KeyEvent.VK_A) {
                    movingLeft = true;  // Mover a la izquierda
                }
                // Cuando se presiona la tecla 'D' (derecha)
                else if (keyCode == KeyEvent.VK_D) {
                    movingRight = true;  // Mover a la derecha
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                // Cuando se suelta la tecla 'A' o 'D', detener el movimiento horizontal
                if (keyCode == KeyEvent.VK_A) {
                    movingLeft = false;  // Detener movimiento a la izquierda
                }
                if (keyCode == KeyEvent.VK_D) {
                    movingRight = false;  // Detener movimiento a la derecha

                }
            }
            
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, this.x, this.y, this); // Dibuja la imagen en la posición actual
        }
    }

    void moverSprite() {
        // Controlamos el movimiento según las teclas presionadas
        if (movingLeft) {
            this.x -= this.speedx; // Mueve a la izquierda
        }
        if (movingRight) {
            this.x += this.speedy; // Mueve a la derecha
        }
    }
    void cambiarImagen(){
        if(movingRight){
            this.indexImagen += 1;
            System.out.println(this.indexImagen);
                if(this.indexImagen >= imagenesName.length - 1){
                    this.indexImagen = 0;
                    System.out.println(this.indexImagen);
                }
            try {
                // Carga la imagen desde un archivo
                this.image = ImageIO.read(new File("C:/Users/ignacio/Downloads/carpetaSprite/" + this.indexImagen + ".png"));
                
            } catch (IOException e) {
                System.out.println("No se pudo cargar la imagen.");
                e.printStackTrace();
            }
                }else if(!movingRight){
                    try {
                        this.image = ImageIO.read(new File("C:/Users/ignacio/Downloads/carpetaSprite/1.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
    }
}

public class Main {
    public static void main(String[] args) {
        Ventana ventanaObjeto = new Ventana(1920, 1080, "Sprite");
        JFrame ventana = ventanaObjeto.getVentana();

        // Creamos el objeto ImagenSprite con velocidad horizontal (speedx) y sin movimiento vertical (speedy)
        ImagenSprite imagen = new ImagenSprite(500, 200, 5, 5); // Empezamos con velocidad 0 en X (sin movimiento)
        ventana.add(imagen); // Añadimos el panel con el sprite a la ventana
        ventana.setVisible(true);

        // Configuramos un Timer para mover la imagen cada 20ms (50 FPS)
        Timer timer = new Timer(20, e -> {
            imagen.moverSprite();  // Mueve el sprite
            imagen.repaint(); 
            imagen.cambiarImagen();     // Redibuja la imagen en la nueva posición
        });
        timer.start(); // Inicia el timer
    }
}
