/**
 * Casilla:
 * 
 * - Definimos un cuadrado.
 * - Contiene los métodos y atributos necesarios para la realización del
 * ejercicio propuesto.
 */
package Clases;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 *
 * @author Luis Clar Fiol y Jorge González Pascual
 */
public class Casilla implements Serializable {

    //ATRIBUTOS DE LA CLASE//
    private int height;
    private int width;
    private int posx;
    private int posy;
    private String n;
    private boolean girada;
    private boolean marcada;
    private Imagen im;
    private Rectangle2D.Float r;
    private final Color c = new Color(213, 213, 213);

    //CONSTRUCTORES DE LA CLASE//
    public Casilla(Rectangle2D.Float r, boolean girada) {
        this.girada = girada;
        this.r = new Rectangle2D.Float((int) r.x + 3, (int) r.y + 3, (int) r.width - 5, (int) r.height - 5);
        im = new Imagen((int) r.x + 3, (int) r.y + 3, (int) r.width - 5, (int) r.height - 5);
        im.setNombre("Tapa");
    }

    //MÉTODOS DE LA CLASE//
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(c);
        g2d.fill(r);
        im.paintComponent(g2d);
    }

    //GETTERS & SETTERS//
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
        im.setNombre(n);
    }

    public boolean isGirada() {
        return girada;
    }

    public boolean isMarcada() {
        return marcada;
    }

    public void setMarcada(boolean marcada) {
        this.marcada = marcada;
    }

    public void setGirada(boolean girada) {
        this.girada = girada;
    }

}
