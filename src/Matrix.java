public class Matrix {
    /**
     * This function swaps 2 rows in a matrix.
     *
     * |a , b|           |c , d|
     * |c , d|    --->   |a , b|
     *
     * @param Matrix
     * @param a
     * @param b
     */
    public static void swapRow(double[][]Matrix, int a, int b){
        double[] temp = Matrix[a];
        Matrix[a] = Matrix[b];
        Matrix[b] = temp;
    }

    /**
     * This function adds a row of the matrix to another row of the matrix a times.
     * @param Matrix
     * @param a
     * @param row1
     * @param row2
     */
    public static void addRow(double[][]Matrix, double a , int row1, int row2)
    {
        for (int i = 0; i < Matrix[row1].length; i++)
            Matrix[row2][i] += Matrix[row1][i] * a;
    }
}
