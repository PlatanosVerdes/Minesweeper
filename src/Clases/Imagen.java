/**
 * Imagen:
 *
 * - Clase para cargar imagenes des de un directorio y pintarlas. Con los
 * métodos y atributos correspondientes.
 */
package Clases;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 *
 * @author Luis Clar Fiol y Jorge González Pascual
 */
public class Imagen extends javax.swing.JPanel {

    //ATRIBUTOS DE LA CLASE//
    private String nombre;
    private int x;
    private int y;

    //CONSTRUCTOR DE LA CLASE//
    public Imagen(int x, int y, int width, int height) {
        //se selecciona el tamaño del panel
        this.setBounds(x, y, width, height);
        this.x = x;
        this.y = y;
    }

    @Override
    public void paintComponent(Graphics grafico) {
        Dimension height = getSize();

        //Se selecciona la imagen que tenemos en el paquete de la //ruta del programa
        ImageIcon Img = new ImageIcon("IMAGENES/" + nombre + ".png");

        //Se dibuja la imagen segun el nombre del atributo "nombre"
        grafico.drawImage(Img.getImage(), x, y, height.width, height.height, null);

        setOpaque(false);
        super.paintComponent(grafico);
    }

    //GETTERS & SETTERS//
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
