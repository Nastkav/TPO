import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {
    static File file;
    static FileWriter fileWriter;
    static int MAX_RANDOM_NUM = 500;
    static int MIN_RANDOM_NUM = -500;

    public static void generateMatrix(int rows, int columns, MatrixType matrixType, int scalar, String fileName) {
        try {
            file = new File(fileName);
            fileWriter = new FileWriter(file);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    fileWriter.write(getValueForMatrixType(i, j, matrixType, scalar) + " ");
                }
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }


    public static void generateMatrix(int rows, int columns, MatrixType matrixType, String fileName) {
        generateMatrix(rows, columns, matrixType, 0, fileName);
    }

    private static int getValueForMatrixType(int i, int j, MatrixType matrixType, int scalar) {
        if (matrixType == MatrixType.SCALAR) {
            return i == j ? scalar : 0;
        } else if (matrixType == MatrixType.UNIT) {
            return i == j ? 1 : 0;
        } else if (matrixType == MatrixType.DIAGONAL) {
            return i == j ? (scalar == 0 ? getRandomNumber() : scalar) : 0;
        } else if (matrixType == MatrixType.TRIANGLE) {
            return i >= j ? (scalar == 0 ? getRandomNumber() : scalar) : 0;
        } else if (matrixType == MatrixType.RANDOM) {
            return getRandomNumber();
        } else {
            return 0;
        }
    }

    private static int getRandomNumber() {
        return ThreadLocalRandom.current().nextInt(MIN_RANDOM_NUM, MAX_RANDOM_NUM + 1);
    }
}