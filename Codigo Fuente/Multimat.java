import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal.
 *
 * @author Francisco Carlos López Porcel
 * @version 0.1
 */
public class Multimat {

    /**
     * Método main
     * @throws IOException 
     */
    public static void main (String[] args) throws IOException {

        // Lee los argumentos de entrada
        ProcesadorArgumentos argumentos = new ProcesadorArgumentos(args);
        // Procesa los argumentos
        argumentos.procesarArgumentos();
        // Archivo de entrada
        String archivoEntrada = argumentos.getArchivoEntrada();
        // Archivo de salida
        String archivoSalida = argumentos.getArchivoSalida();

        // Número de matrices
        int numeroMatrices;
        // Dimensiones de las matrices
        int[] d;
        // Número mínimo de multiplicaciones
        int[][] mult;
        // Posicion de los parentesis
        int[][] pos;
        
        // Muestra la ayuda
        if (argumentos.getAyuda()) {
            argumentos.mostrarAyuda();
        }
        
        // Si hay archivos de entrada se opera
        if (archivoEntrada != null) {
            // Contenido de la solución
            String contenidoSolucion = "";

            // Lee el archivo de entrada
            List<String> contenidoArchivo = ManejadorArchivos.leerArchivo(archivoEntrada);

            // Lee el número de matrices, primera linea del archivo de entrada
            numeroMatrices = Integer.parseInt(contenidoArchivo.get(0));

            // Inicia la estructura de datos para guardar las dimensiones de las matrices
            d = new int[numeroMatrices+1];

            // Inicia la estructura de datos para guardar el número mínimo de operaciones
            mult = new int[numeroMatrices][numeroMatrices];

            // Inicia la estructura de datos para guardar la posicion de los parentesis
            pos = new int[numeroMatrices][numeroMatrices];

            // Guarda una lista con cada una de las matrices
            List<double[][]> matrices = new ArrayList<double[][]>();

            // Lee el contenido del archivo de entrada y guarda las matrices en una lista
            int i = 1;
            int j = 0;
            int columnaAnterior = 0; // Guarda el tamaño de la columna anterior para comprobar que se pueden multiplicar
            boolean failed = false; // Si se pone a true es que las dimensiones de las matrices están mal y no se pueden multiplicar
            while (i < contenidoArchivo.size() && !failed) {
                String data = contenidoArchivo.get(i);
                String datos[] = data.split("\\s");
                int fila = Integer.parseInt(datos[0]);
                int columna = Integer.parseInt(datos[1]);

                // Comprueba que las matrices tienen las dimensiones correctas
                // para poder multiplicarse, sino termina la ejecución del
                // programa
                if (i > 1 && columnaAnterior != fila) {
                    contenidoSolucion = "Las matrices no tienen las dimensiones correctas para poder multiplicarse";
                    failed = true;
                }
                columnaAnterior = columna;

                if (!failed) {
                    d[j] = fila;
                    d[j+1] = columna;

                    double[][] matrizTmp = new double[fila][columna];
                    for (int k = 0; k < fila; k++) {
                        String dataTmp = contenidoArchivo.get(i+1+k);
                        String datosTmp[] = dataTmp.split("\\s");
                        for (int p = 0; p < columna; p++) {
                            matrizTmp[k][p] = Double.parseDouble(datosTmp[p]);
                        }
                    }
                    matrices.add(matrizTmp);
                }

                i = i + fila + 1;
                j++;
            }

            if (!failed) {
                // Inicia el algoritmo
                MultiplicacionMatrices multiplicacionMatrices = new MultiplicacionMatrices(argumentos.getTraza(), matrices);
                // Imprimimos la cabecera de la traza con la información del problema
                if (argumentos.getTraza()) contenidoSolucion += multiplicacionMatrices.imprimeCabeceraTraza(matrices, d);
                // Resolvemos el problema
                contenidoSolucion += multiplicacionMatrices.multMatrices(d, numeroMatrices, mult, pos);
            }

            // Si hay hay archivo de salida guarda del resultado en él si no lo muestra en pantalla
            if (archivoSalida != null) {
                ManejadorArchivos.guardarArchivo(archivoSalida, contenidoSolucion);
            } else {
                System.out.println(contenidoSolucion);
            }
        }
    }
}
