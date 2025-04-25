import java.awt.*;
import java.awt.event.*;
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
class Menu{
    JLabel StartGame;
    boolean run;
    public Menu(int x,int y,int width,int height,Ventana ventana,boolean run){
        this.StartGame = new JLabel("Start");
        StartGame.setBounds(x, y, width, height);
        ventana.getVentana().add(StartGame);
        this.run = run;
    }
    public void clickMenu(){
        this.StartGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                int boton = e.getButton();
                if(boton == MouseEvent.BUTTON1){
                    System.out.println("Botón izquierdo presionado");
                    run = true;
                }else if (boton == MouseEvent.BUTTON2){
                    System.out.println("Botón central presionado");
                }else if(boton == MouseEvent.BUTTON3){
                    System.out.println("Boton derecho presionado");
                }
            }
        });
    }

    
    
}
//conseguido array array con filas y columnas tanto como el texto ahora
class Campo {
    String campoTexto = "nnnnnnnnnn\n" + 
                        "ssssssssss\n" ;
    int fila;
    int columna;
    char array[][];
    int index = 0;
    int numeroImagenes;
    JLabel imagenes[];
    int altoCelda = 100;
    int anchoCelda = 100;

    public Campo(){
        this.fila = (int)this.campoTexto.lines().count();
        this.campoTexto.lines().forEach(linea -> {
           
            // Contamos los caracteres de cada línea usando el método 'length()'
            this.columna = linea.length();
            if( this.array == null){
                this.array = new char[fila][columna];

            }
            for(int i = 0; i < linea.length(); i++){
                this.array[this.index][i] = linea.charAt(i);
            }
            this.index+= 1;
            // Imprimimos la línea y el número de caracteres
        });

    }
    public void dibujarCampo(JFrame ventana){
        this.numeroImagenes = fila * columna;
        this.imagenes = new JLabel[this.numeroImagenes];
    
       // this.imagenes = new JLabel[this.numeroImagenes];
       
        int i = 0;
            for(int a = 0; a < this.fila; a++){
                for(int b = 0; b < this.columna; b++){
                    this.imagenes[i] = new JLabel("HOLA");
                    this.imagenes[i].setText(Character.toString(this.array[a][b]));
                    this.imagenes[i].setBounds(b * this.anchoCelda,a * this.altoCelda, 100,100);
                    ventana.add(this.imagenes[i]);
                    i++;
                }
            }
        
       
       
    }

   
}

public class Main {
    boolean posicionrepintanda = false;
    public static void main(String[] args) {
        Main MainApp = new Main();
        boolean run = false;
        int anchoVentana = 1000;
        int altoVentana = 800;
        Ventana ventanaObjeto = new Ventana(anchoVentana, altoVentana, "Sprite");
        JFrame ventana = ventanaObjeto.getVentana();
        Campo campo = new Campo();
        campo.dibujarCampo(ventana);
    
        Menu menu = new Menu(500,300 , 100, 100, ventanaObjeto, run);


        // Creamos el objeto ImagenSprite con velocidad horizontal (speedx) y sin movimiento vertical (speedy)
        ImagenSprite imagen = new ImagenSprite(-200, 200, 5, 5); // Empezamos con velocidad 0 en X (sin movimiento)
        ventana.add(imagen); // Añadimos el panel con el sprite a la ventana;
        ventana.setVisible(true);
        imagen.setVisible(true);
        menu.clickMenu();
        // Configuramos un Timer para mover la imagen cada 20ms (50 FPS).*
        Timer timer = new Timer(20, e -> {
            if(menu.run){
                imagen.moverSprite();
                if(MainApp.posicionrepintanda == false){
                    imagen.x = 400;
                    imagen.y = 400;
                    MainApp.posicionrepintanda = true;
                    ventana.remove(menu.StartGame);
                }
                
            }
            imagen.cambiarImagen();
            ventana.repaint();  
            // Redibuja la imagen en la nueva posición
           
            
        });
        timer.start(); // Inicia el timer
    }
}
