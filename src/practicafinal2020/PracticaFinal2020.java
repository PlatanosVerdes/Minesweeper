/**
 * - PRÁCTICA FINAL - PROGRAMACIÓN II -
 *
 * AUTORES: Luis Clar Fiol - 49480556N ; Jorge González Pascual - 45694336Y
 * FECHA: Mayo - Junio 2020
 *
 * - En esta clase encontraremos el Main del programa con los métodos correspondientes
 * para realizar la práctica. Podemos encontrar las inicialización de las componentes
 * gráficas y cada uno de sus métodos. También encontraremos el tratamiento de eventos.
 *
 * NOTA: Hemos puesto una partida ya guardada para facilitar la tarea evaluativa.
 */
package practicafinal2020;

import Clases.TableroInputStream;
import Clases.Tablero;
import Clases.TableroOutputStream;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PracticaFinal2020 extends JFrame {

    //ATRIBUTOS DE LA CLASE//
    private static JFileChooser selc = new JFileChooser("\\PracticaFinal2020");
    private static File archivo;

    private JMenuBar barra;
    private JMenu mArchivo;
    private JMenu mOpciones;
    private JMenuItem miAbrir;
    private JMenuItem miGuardar;
    private JMenuItem miNuevo;
    private JMenuItem miSol;

    private Tablero t;
    private final int ancho = 603;
    private final int alto = 603;

    //Variable para controlar si la partida se ha acabado.
    private boolean finish;

    //CONSTRUCTORES DE LA CLASE//
    public PracticaFinal2020() {
        super("BUSCAMINAS");
        initComponents();

        finish = false;
        //Ruta predeterminada.
        selc = new JFileChooser(new File(System.getProperty("user.dir") + "\\Partidas Guardadas"));
        //Filtros para la selección de archivos.
        selc.setAcceptAllFileFilterUsed(false);
        selc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        selc.addChoosableFileFilter(new FileNameExtensionFilter("Documentos", "txt"));
    }

    //MÉTODOS DE LA CLASE//
    /**
     * Inicializamos todas las componentes de la interfaz y las añadimos al
     * JFrame.
     */
    private void initComponents() {
        this.getContentPane().setLayout(null);
        this.setSize(ancho + 15, alto + 58);
        this.setLocationRelativeTo(null);

        //Inicializamos lo botones
        botones();
        barra.setBounds(0, 0, ancho + 8, 20);
        this.getContentPane().add(barra);

        t = new Tablero(ancho, alto);
        t.setBounds(0, 20, ancho + 14, alto + 58);
        this.getContentPane().add(t);

        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //Método para poner la opción de estar seguro de cerrar.
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(PracticaFinal2020.this,
                        "¿Estás seguro de que desas salir?", "Cerrar",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        //Actualizamos el tema de la interfaz respecto al sistema operativo.
        updateLF();
        barra.updateUI();
        selc.updateUI();

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
            }

            /**
             * Acciones correspondientes al pulsar el botón derecho o izquierdo.
             * A estos dos métodos les pasaremos dos números que serán la
             * posición de la casilla pulsada; los cuales haremos los cálculos
             * correspondientes para pasarlos por parámetro.
             *
             * @param me
             */
            @Override
            public void mousePressed(MouseEvent me) {
                int x = (me.getX() - 7) / t.getDimencasx();
                int y = (me.getY() - 50) / t.getDimencasy();

                //Controlamos el rango de la ventana. (alto+55) los 55 son de la
                //ventana + la barra de botones.
                if ((me.getX() < ancho) && (me.getY() < alto + 55)) {

                    if ((me.getButton() == MouseEvent.BUTTON3) && (!t.getGirada(x, y))) {
                        botonDer(x, y);
                    }
                    if ((me.getButton() == MouseEvent.BUTTON1) && (!t.getMarcada(x, y))) {
                        botonIzq(x, y);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {

            }

            @Override
            public void mouseEntered(MouseEvent arg0) {

            }

            @Override
            public void mouseExited(MouseEvent arg0) {

            }
        });

        miGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                opcionGuardar();
            }
        });
        miAbrir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                opcionAbrir();
            }
        });
        miSol.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                opcionResolver();
            }
        });
        miNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                opcionNuevo();
            }
        });
    }

    /**
     * Inicializamos la barra del menu de los botones, conjuntamente a los
     * botones que contendrá esta. Cada botón tendrá su "ActionListener" con su
     * método correspondiente.
     */
    private void botones() {
        barra = new JMenuBar();

        mArchivo = new JMenu("Archivo");
        mOpciones = new JMenu("Opciones");

        miAbrir = new JMenuItem("Abrir");
        miGuardar = new JMenuItem("Guardar");
        miNuevo = new JMenuItem("Nuevo");
        miSol = new JMenuItem("Resolver");

        mArchivo.add(miAbrir);
        mArchivo.add(miNuevo);
        mArchivo.add(miGuardar);
        mOpciones.add(miSol);

        barra.add(mArchivo);
        barra.add(mOpciones);
    }

    /**
     * Método para el botón izquierdo; este mirará si la casilla seleccionada
     * está girada (se habrá pasado por parámetro la posición). Si no esta
     * girada: Miraremos si es una bomba o un número. Y tanto si es una bomba o
     * si es la última casilla para ganar, se destaparán todas las casillas.
     *
     * @param x
     * @param y
     */
    private void botonIzq(int x, int y) {
        if (!t.getGirada(x, y)) {
            int valorc = t.getValorTablero(x, y);
            t.setGirada(x, y);

            if (valorc == 9) {
                t.setNombre("Explosion", x, y);
                t.repaint();

                ImageIcon icono = new ImageIcon("IMAGENES/calavera.png");
                icono = this.redimensionarImagen(icono, 60, 60);
                JOptionPane.showMessageDialog(null, " GAME OVER", "Partida finalizada", JOptionPane.PLAIN_MESSAGE, icono);

                opcionResolver();
            } else {
                t.setNombre(Integer.toString(valorc), x, y);
                t.repaint();
                //Si el usuario ha ganado destapamos todas las casillas.
                if (ganandor()) {
                    opcionResolver();
                }
            }
        }
    }

    /**
     * Método para el botón derecho; este mirará si la casilla seleccionada esta
     * marcada (se habrá pasado por parámetro la posición). Si no esta marcada
     * la marcaremos y si esta marcada la desmarcaremos. Asi tendremos la opción
     * de marcar y desmarcar.
     *
     * @param x
     * @param y
     */
    private void botonDer(int x, int y) {
        if (!t.getMarcada(x, y)) {
            t.setNombre("TapaX", x, y);
            t.setMarcada(true, x, y);
            t.repaint();
        } else {
            t.setNombre("Tapa", x, y);
            t.setMarcada(false, x, y);
            t.repaint();
        }

    }

    /**
     * Método para guardar archivos. Si el usuario quiere guardar un archivos
     * generaremos un "archivo" con el nombre escrito por el usuario y miraremos
     * que el formato del archivo sea el correcto. El programa notificará si se
     * ha guardado correctamente o hay algun error.
     *
     * Nota: Cabe decir que hemos puesto como ruta predeterminada: //Partidas
     * Guardadas.
     */
    private void opcionGuardar() {
        if (selc.showDialog(null, "Guardar") == JFileChooser.APPROVE_OPTION) {
            archivo = new File(selc.getSelectedFile() + ".txt");
            if (archivo.getName().endsWith("txt")) {
                String mensaje = guardar(archivo);
                if (mensaje != null) {
                    JOptionPane.showMessageDialog(null, mensaje);
                } else {
                    JOptionPane.showMessageDialog(null, "Archivo no valido");
                }
            } else {
                JOptionPane.showConfirmDialog(null, "Guardar documento de texto");
            }
        }
        barra.repaint();
    }

    /**
     * Método para cargar partidas guardadas. Si el usuario desea relizar esta
     * acción miraremos si el archivo seleccionado corresponde al formato
     * correspondiente (.txt); si es correspondiente se abrirá. En caso
     * contrario se le notificará al usuario.
     */
    private void opcionAbrir() {
        if (selc.showDialog(null, "Abrir") == JFileChooser.APPROVE_OPTION) {
            archivo = selc.getSelectedFile();
            if (archivo.canRead()) {
                if (archivo.getName().endsWith("txt")) {
                    abrir(archivo);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Archivo no compatible");
            }
        }
    }

    /**
     * Método para guardar un objeto Tablero. Y retorna un String como archivo
     * guardado.
     *
     * @param f
     * @return
     */
    private String guardar(File f) {
        try {
            TableroOutputStream tos = new TableroOutputStream(f);
            tos.escribir(t);
            tos.cierre();

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder al fichero.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar el fichero.");
        }
        return "Archivo Guardado.";
    }

    /**
     * Método para leer un objeto Tablero y así poder cargar la partida. Al
     * obtener la partida creamos un nuevo Tablero a partir del Tablero leído.
     *
     * @param f
     */
    private void abrir(File f) {
        try {
            this.remove(t);
            TableroInputStream tis = new TableroInputStream(f);
            t = new Tablero(tis.lectura(), ancho, alto);
            tis.cierre();

        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error al abrir al fichero.");
        }

        t.setBounds(0, 20, ancho + 14, alto + 58);
        this.getContentPane().add(t);
        t.repaint();

        finish = false;
    }

    /**
     * Método para crear una nueva partida. Volvemos a crear un nuevo Tablero.
     */
    private void opcionNuevo() {
        this.remove(t);
        t = new Tablero(ancho, alto);

        t.setBounds(0, 20, ancho + 14, alto + 58);
        this.getContentPane().add(t);
        t.repaint();
        barra.repaint();

        finish = false;
    }

    /**
     * Método que resuelve la partida, es decir hacemos un recorrido de toda la
     * matriz del tablero y según el valor de cada "casilla" mostramos la imagen
     * correspondiente.
     */
    private void opcionResolver() {
        if (!finish) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {

                    int valorc = t.getValorTablero(i, j);

                    if (valorc == t.MINA) {
                        if (t.getGirada(i, j)) {
                            t.setNombre("Explosion", i, j);
                            t.setGirada(i, j);
                        } else {
                            t.setNombre("Mina", i, j);
                            t.setGirada(i, j);
                        }
                    } else {
                        t.setNombre(Integer.toString(valorc), i, j);
                        t.setGirada(i, j);
                    }
                }
            }
            t.repaint();
            finish = true;
        }
    }

    /**
     * Método que retorna un booleano conforme si el usuario ha ganado o no.
     * Hacemos un recorrido de todas las Casillas del Tablero, si están giradas
     * y no son bombas vamos decrementando el contador del número de casillas
     * que hay en el tablero; si llega al final saldrá un mensaje notificado que
     * el usuaio ha ganado.
     *
     * @return
     */
    private boolean ganandor() {
        boolean win = false;
        int contador = 71;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int valorc = t.getValorTablero(i, j);

                if ((valorc != t.MINA) && (t.getGirada(i, j))) {
                    contador--;
                }
            }
        }

        if (contador == 0) {
            win = true;
            ImageIcon icono = new ImageIcon("IMAGENES/corona.png");
            icono = redimensionarImagen(icono, 50, 50);
            JOptionPane.showMessageDialog(null, "¡ENHORABUENA! ¡HAS GANADO!", "Partida finalizada", JOptionPane.PLAIN_MESSAGE, icono);
        }
        return win;
    }

    /**
     * Método con el que actualizamos el look and feel del JFrame para que sea
     * el mismo que la versión del ordenador que lo ejecuta.
     */
    private void updateLF() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println("ERROR al actualizar el Look and Feel.");
        }
    }

    /**
     * Método para redimensionar una imagen a partir de una imagen y pasamos por
     * parámetro el tamaño la cual la queremos redimensionar. Y retorna la
     * imagen pasada redimensionada.
     *
     * @param imagen
     * @param x
     * @param y
     * @return
     */
    private ImageIcon redimensionarImagen(ImageIcon imagen, int x, int y) {
        Image imgEscalada = imagen.getImage().getScaledInstance(x, y, java.awt.Image.SCALE_DEFAULT);
        return new ImageIcon(imgEscalada);
    }

    public static void main(String[] args) {
        PracticaFinal2020 pf = new PracticaFinal2020();
        pf.setVisible(true);
    }
}
