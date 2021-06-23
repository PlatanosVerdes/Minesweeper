/**
 * Tablero:
 *
 * - Definimos un tablero, una matriz de 9x9 formado po Casillas con
 * extensión JPanel.
 * - Contiene los métodos y atributos necesarios para la realización del
 * ejercicio propuesto.
 */
package Clases;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Luis Clar Fiol y Jorge González Pascual
 */
public class Tablero extends JPanel implements Serializable {

    //ATRIBUTOS DE LA CLASE//
    //"public" para accecer al valor de este directamente,
    //siempre será el mismo.
    public static final int MINA = 9;
    public static final int FILAS = 9;
    public static final int COLUMNAS = 9;

    private int[][] tablero = new int[FILAS][COLUMNAS];
    private final int numMinas = 10;
    private final int dimencasx;
    private final int dimencasy;
    private Casilla[][] arrcas = new Casilla[FILAS][COLUMNAS];

    //CONSTRUCTORES DE LA CLASE//
    public Tablero(int alto, int ancho) {
        dimencasx = alto / 9;
        dimencasy = ancho / 9;

        this.setBackground(Color.LIGHT_GRAY);
        colocarMinas();
        colocarCasillas();
        this.setVisible(true);
    }

    public Tablero(Tablero t, int ancho, int alto) {
        this.setBackground(Color.LIGHT_GRAY);
        this.tablero = t.tablero;
        this.dimencasy = t.dimencasy;
        this.dimencasx = t.dimencasx;
        this.arrcas = t.arrcas;
        this.setVisible(true);
    }

    //MÉTODOS DE LA CLASE//
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                arrcas[j][i].paintComponent(g);
            }
        }

    }

    /**
     * Método recorre todo el Tablero colocando minas (Casillas con valor MINA)
     * aleatoriamente.
     */
    private void colocarMinas() {
        int x;
        int y;
        Random rnd = new Random();

        for (int i = 0; i < numMinas; i++) {
            x = rnd.nextInt(FILAS);
            y = rnd.nextInt(COLUMNAS);

            while (tablero[x][y] == MINA) {

                x = rnd.nextInt(FILAS);
                y = rnd.nextInt(COLUMNAS);
            }

            tablero[x][y] = MINA;

            calcularNumeros(x, y);
        }

    }

    /**
     * Método recorre todo el Tablero colocando casillas.
     */
    private void colocarCasillas() {
        int x = 0;
        int y = 0;

        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                Rectangle2D.Float r = new Rectangle2D.Float(x, y, dimencasx, dimencasy);
                arrcas[j][i] = new Casilla(r, false);
                x += dimencasx;
            }
            x = 0;
            y += dimencasy;

        }
    }

    /**
     * Calculamos los números de las casillas adyacentes. De posición de la
     * casilla pasada por parámetro.
     *
     * @param i
     * @param j
     */
    private void calcularNumeros(int i, int j) {

        int k = i;
        int l = j;

        if ((i != 0) && (i != (FILAS - 1)) && (j != 0) && (j != (COLUMNAS - 1))) {

            k--;
            l--;

            /*
            0 0 0 <-- aumentamos en uno
            0 x 0
            0 0 0
             */
            aumentarFila(k, l);
            k++;

            /*
            1 1 1 
            0 x 0 <-- aumentamos en uno
            0 0 0 
             */
            aumentarFila(k, l);
            k++;

            /*
            1 1 1 
            1 x 1 
            0 0 0 <-- aumentamos en uno
             */
            aumentarFila(k, l);

        } else if ((i == 0) && (j != 0) && (j != (COLUMNAS - 1))) {

            /*
            0 x 0 <-- aumentamos en uno
            0 0 0 
            0 0 0 
             */
            l--;

            aumentarFila(k, l);

            /*
            1 x 1 
            0 0 0 <-- aumentamos en uno
            0 0 0 
             */
            k++;

            aumentarFila(k, l);

        } else if ((i == (FILAS - 1)) && (j != 0) && (j != (COLUMNAS - 1))) {

            /*
            0 0 0 
            0 0 0 
            0 x 0 <-- aumentamos en uno
             */
            l--;

            aumentarFila(k, l);

            /*
            0 0 0 
            0 0 0 <-- aumentamos en uno
            1 x 1 
             */
            k--;

            aumentarFila(k, l);

        } else if ((j == 0) && (i != 0) && (i != (FILAS - 1))) {

            l++;
            k--;
            /*  
            0 0 0 
            x 0 0 
            0 0 0 
              ^ aumentamos en uno
             */
            aumentarColumna(k, l);

            /*  
            0 1 0 
            x 1 0 
            0 1 0 
            ^ aumentamos en uno
             */
            l--;

            aumentarColumna(k, l);

        } else if ((j == (COLUMNAS - 1)) && (i != 0) && (i != (FILAS - 1))) {

            /*  
            0 0 0 
            0 0 x 
            0 0 0 
              ^ aumentamos en uno
             */
            l--;
            k--;

            aumentarColumna(k, l);

            /*  
            0 1 0 
            0 1 x 
            0 1 0 
                ^ aumentamos en uno
             */
            l++;

            aumentarColumna(k, l);

        } else if ((i == 0) && (j == 0)) {

            /*   
            Esquina:
            x 0 
            0 0
             */
            if (tablero[k + 1][l] < MINA) {
                tablero[k + 1][l]++;
            }

            if (tablero[k][l + 1] < MINA) {
                tablero[k][l + 1]++;
            }

            if (tablero[k + 1][l + 1] < MINA) {
                tablero[k + 1][l + 1]++;
            }


            /*
            Esquina:
            0 x 
            0 0
             */
        } else if ((i == 0) && (j == (COLUMNAS - 1))) {

            if (tablero[k + 1][l] < MINA) {
                tablero[k + 1][l]++;
            }

            if (tablero[k][l - 1] < MINA) {
                tablero[k][l - 1]++;
            }

            if (tablero[k + 1][l - 1] < MINA) {
                tablero[k + 1][l - 1]++;
            }

            /*
            Esquina:
            0 0 
            x 0
             */
        } else if ((i == (FILAS - 1)) && (j == 0)) {

            if (tablero[k - 1][l] < MINA) {
                tablero[k - 1][l]++;
            }

            if (tablero[k][l + 1] < MINA) {
                tablero[k][l + 1]++;
            }

            if (tablero[k - 1][l + 1] < MINA) {
                tablero[k - 1][l + 1]++;
            }

            /*
            Esquina:
            0 0 
            0 x
             */
        } else if ((i == (FILAS - 1)) && (j == (COLUMNAS - 1))) {

            if (tablero[k - 1][l] < MINA) {
                tablero[k - 1][l]++;
            }

            if (tablero[k][l - 1] < MINA) {
                tablero[k][l - 1]++;
            }

            if (tablero[k - 1][l - 1] < MINA) {
                tablero[k - 1][l - 1]++;
            }

        }

    }

    /**
     * Incrementa en 1, 3 casillas en la fila "k", a partir de la columna "l".
     * Si estas no representan una mina.
     *
     * @param k
     * @param l
     */
    private void aumentarFila(int k, int l) {

        int acabar = 0;
        int ll = l;

        while (acabar < 3) {
            if (tablero[k][ll] < MINA) {
                tablero[k][ll]++;
            }
            acabar++;
            ll++;
        }

    }

    /**
     * Incrementa en 1, 3 casillas en la columna "l", a partir de la fila "k".
     * Si estas no representan una mina.
     *
     * @param k
     * @param l
     */
    private void aumentarColumna(int k, int l) {

        int acabar = 0;
        int kk = k;

        while (acabar < 3) {
            if (tablero[kk][l] < MINA) {
                tablero[kk][l]++;
            }
            acabar++;
            kk++;
        }
    }

    //GETTERS & SETTERS//
    public boolean getGirada(int i, int j) {

        return arrcas[i][j].isGirada();
    }

    public void setGirada(int i, int j) {

        arrcas[i][j].setGirada(true);
    }

    public boolean getMarcada(int i, int j) {

        return arrcas[i][j].isMarcada();
    }

    public void setMarcada(boolean b, int i, int j) {

        arrcas[i][j].setMarcada(b);
    }

    public int getValorTablero(int i, int j) {

        return tablero[i][j];
    }

    public void setValorTablero(int[][] tablero) {

        this.tablero = tablero;
    }

    public void setNombre(String s, int i, int j) {

        arrcas[i][j].setN(s);
    }

    public int getDimencasx() {
        return dimencasx;
    }

    public int getDimencasy() {
        return dimencasy;
    }

}
