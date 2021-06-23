/**
 * TableroOutputStream:
 *
 * - En esta clase encontramos los atributos y métodos necesarios para la
 * lectura del objeto Tablero.
 */
package Clases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author Luis Clar Fiol y Jorge González Pascual
 */
public class TableroOutputStream {

    //ATRIBUTO DE LA CLASE//
    private ObjectOutputStream oos;

    //CONSTRUCTOR DE LA CLASE//
    public TableroOutputStream(File s) throws FileNotFoundException, IOException {
        oos = new ObjectOutputStream(new FileOutputStream(s));
    }

    //MÉTODOS DE LA CLASE//
    /**
     * Método para escribir un objeto Tablero en un fichero.
     *
     * @param t
     * @throws IOException
     */
    public void escribir(Tablero t) throws IOException {
        oos.writeObject(t);
    }

    /**
     * Método para cerrar el fichero.
     *
     * @throws IOException
     */
    public void cierre() throws IOException {
        oos.close();
    }
}
