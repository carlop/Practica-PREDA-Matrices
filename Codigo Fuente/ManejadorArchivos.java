import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Lee y escribe los ficheros de texto que se pasan al programa como argumentos.
 *
 * @author Francisco Carlos López Porcel
 * @version 0.1
 */
public class ManejadorArchivos {
    /**
     * Lee el archivo y devuelve una lista con su contenido
     * @return lista con los elementos que contiene archivo
     * @throws IOException 
     */
    public static List<String> leerArchivo(String archivo) throws IOException {
        List<String> listaElementos = new ArrayList<String>();
        FileReader lector = null;
        BufferedReader buffer = null;
        try {
            lector = new FileReader(archivo);
            buffer = new BufferedReader(lector);
            String lineaActual;
            while ((lineaActual = buffer.readLine()) != null) {
                listaElementos.add(lineaActual);
            }
        }
        catch (IOException e) {
            System.err.println("¡Error leyendo el archivo!");
            e.printStackTrace();
        }
        finally {
            buffer.close();
        }
        return listaElementos;
    }
    
    /**
     * Guarda el archivo con el resultado de la operación
     * @params nombre del archivo
     */
    public static void guardarArchivo(String archivo, String contenido)
            throws IOException {
        try {
            if (archivo != null) {
                File file = new File(archivo);
                if (file.exists()) {
                    System.out.println("El fichero de salida ya existe");
                    System.exit(0);
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(contenido);
                bw.close();
            } else {
                System.out.println(contenido);
            }
        } catch (IOException e) {
            System.err.println("¡Error guardando el archivo!");
            e.printStackTrace();
        }
    }
}