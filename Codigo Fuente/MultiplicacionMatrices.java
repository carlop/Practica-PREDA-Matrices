import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author Francisco Carlos López Porcel
 * @version 0.1
 */
public class MultiplicacionMatrices {

    // Traza
    private Boolean traza;
    // Contenido de la traza
    private String contenidoTraza;
    // Matrices del problema
    private List<double[][]> matrices;

    public MultiplicacionMatrices(Boolean traza, List<double[][]> matrices) {
        this.traza = traza;
        this.contenidoTraza = "";
        this.matrices = matrices;
    }

    /**
     * Algoritmo de programación dinámica que calcula el número mínimo de multiplicaciones
     * @param d    Dimensiones de las matrices
     * @param n    Número de nodos del grafo
     * @param mult Matriz con el número mínimo de multiplicaciones
     * @param pos  Matriz con la posición de los parentesis
     * @return String con el resultado del ejercicio
     */
    public String multMatrices(int[] d, int n, int[][] mult, int[][] pos) {
        // Esta parte del algoritmo no es necesaria, ya que Java por defecto pone a 0 todos los elementos de la matriz
        for (int i = 0; i < n; i++) {
            mult[i][i] = 0;
        }

        // Parte principal, donde ocurre la magia
        for (int diag = 1; diag < n; diag++) {
            for (int i = 0; i < n - diag; i++) {
                generaTraza("\n  -Posicion mult[" + i + "][" + diag + "]\n");
                generaTraza("    -Llamada a minMultiple =>\n");
                int[] solucion = minMultiple(mult, d, i, i + diag);
                mult[i][i + diag] = solucion[0];
                pos[i][i + diag] = solucion[1];

                generaTraza("    -Contenido de mult en este paso:\n");
                generaTraza(imprimeMatriz(mult));
                generaTraza("    -Contenido de pos en este paso:\n");
                generaTraza(imprimeMatriz(pos));
            }
        }
        generaTraza("\nRESULTADO:\n");
        return imprimeSolucion(mult, pos, n);
    }

    /**
     * Calcula el mínimo entre múltiples valores
     * @param mult Matriz con el número mínimo de multiplicaciones
     * @param d    Dimensiones de las matrices
     * @param i    Posición en la
     * @param j
     * @return
     */
    private int[] minMultiple(int[][] mult, int[] d, int i, int j) {
        int[] solucion = new int[2];
        int minimo = Integer.MAX_VALUE;
        int posicion = i;
        for (int k = i; k < j; k++) {
            generaTraza("      -k = " + k + "\n");
            int tmp = mult[i][k] + mult[k + 1][j] + d[i]*d[k + 1]*d[j + 1];
            generaTraza("        tmp = mult["+i+"]["+k+"] + mult["+(k + 1)+"]["+j+"] + d["+i+"]*d["+(k + 1)+"]*d["+(j + 1)+"] = " + tmp + "\n");
            if (tmp < minimo) {
                generaTraza("        tmp < minimo(" + minimo + "):\n");
                generaTraza("        |-> minimo = tmp = " + tmp + "\n");
                generaTraza("        |-> posicion = " + k + "\n");
                minimo = tmp;
                posicion = k;
            } else {
                generaTraza("        tmp >= minimo\n");
            }
        }
        solucion[0] = minimo;
        solucion[1] = posicion;
        return solucion;
    }

    /**
     * Imprime la asociación de matrices
     */
    private String escribeParentizado(int[][] pos, int i, int j) {
        String solucion = "";
        int k;
        if (i == j) {
            solucion += "M" + i;
        } else {
            k = pos[i][j];
            solucion += "(";
            solucion += escribeParentizado(pos, i, k);
            solucion += "*";
            solucion += escribeParentizado(pos, k + 1, j);
            solucion += ")";
        }
        return solucion;
    }

    /**
     * Imprime la solución al problema
     * @param mult
     * @param pos
     * @param n
     * @return String con la solución al problema
     */
    private String imprimeSolucion(int[][] mult, int[][] pos, int n) {
        String solucion = "";
        if (traza) solucion = contenidoTraza;
        solucion += "Asociacion: " + escribeParentizado(pos, 0, n-1) + "\n";
        solucion += "Operaciones: " + mult[0][n-1] + "\n";
        solucion += multiplicaMatrices();
        return solucion;
    }

    public String imprimeCabeceraTraza(List<double[][]> matrices, int[] dimensionesMatrices) {
        String solucion = "";
        solucion = "Datos del problema: \n";
        solucion += "  - Numero de matrices: " + matrices.size() + "\n";
        solucion += "  - Matrices : \n";
        for (int i = 0; i < matrices.size(); i++) {
            double[][] matriz = matrices.get(i);
            solucion += "    - M" + i + " (" + matriz.length + "x" + matriz[0].length +"): \n";
            solucion += imprimeMatriz(matriz);
        }
        solucion += "  - Tabla con el numero minimo de multiplicaciones: mult[][]\n";
        solucion += "  - Tabla con la posicion de los parentesis: pos[][]\n";
        solucion += "\nAplicacion del algoritmo: \n";
        return solucion;
    }

    /**
     * Imprime el contenido de la traza
     * @param texto Texto a imprimir con la traza
     */
    private void generaTraza(String texto) {
        if (traza) {
            this.contenidoTraza += texto;
        }
    }

    /**
     *
     * @return
     */
    private String multiplicaMatrices() {
        String solucion = "Matriz resultado de dimensiones ";
        int ii = 0;
        double[][] matrizA = matrices.get(ii);
        while (ii < matrices.size() - 1) {
            double[][] matrizB = matrices.get(ii+1);
            double[][] matrizTmp = new double[matrizA.length][matrizB[0].length];
            for (int i = 0; i < matrizA.length; i++) {
                for (int j = 0; j < matrizB[0].length; j++) {
                    double suma = 0;
                    for (int k = 0; k < matrizA[0].length; k++) {
                        suma += matrizA[i][k]*matrizB[k][j];
                    }
                    matrizTmp[i][j] = suma;
                }
            }
            matrizA = matrizTmp;
            ii++;
        }
        solucion += matrizA.length + "x" + matrizA[0].length + ":\n";
        solucion += imprimeMatriz(matrizA);
        return solucion;
    }

    private String imprimeMatriz(int[][] jaich) {
        String solucion = "";
        for (int[] kes : jaich) {
            solucion += "      " + imprimeVector(kes) + "\n";
        }
        return solucion;
    }

    private String imprimeVector(int[] kes) {
        String solucion = "";
        for (int ch : kes) {
            solucion += String.format(" %4d", ch);
        }
        return solucion;
    }
    private String imprimeMatriz(double[][] jaich) {
        String solucion = "";
        for (double[] kes : jaich) {
            solucion += "   " + imprimeVector(kes) + "\n";
        }
        return solucion;
    }

    private String imprimeVector(double[] kes) {
        String solucion = "";
        for (double ch : kes) {
            solucion += String.format("%8.2f", ch);
        }
        return solucion;
    }

}