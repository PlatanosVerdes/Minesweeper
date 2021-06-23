/**
 * TableroInputStream:
 *
 * - En esta clase encontramos los atributos y métodos necesarios para la
 * escritura del objeto Tablero.
 */
package Clases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author Luis Clar Fiol y Jorge González Pascual
 */
public class TableroInputStream {

    //ATRIBUTO DE LA CLASE//
    private ObjectInputStream ois;

    //CONSTRUCTOR DE LA CLASE//
    public TableroInputStream(File s) throws FileNotFoundException, IOException {
        ois = new ObjectInputStream(new FileInputStream(s));
    }

    //MÉTODOS DE LA CLASE//
    /**
     * Método para leer desde un fichero un objeto Tablero.
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Tablero lectura() throws IOException, ClassNotFoundException {
        return (Tablero) ois.readObject();
    }

    /**
     * Método para cerrar el fichero.
     *
     * @throws IOException
     */
    public void cierre() throws IOException {
        ois.close();
    }

}
