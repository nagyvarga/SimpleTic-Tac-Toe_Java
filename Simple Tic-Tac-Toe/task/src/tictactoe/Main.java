package tictactoe;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static boolean readCoordinates(int[] coordinates) {
        Scanner scanner = new Scanner(System.in);
        boolean successed = true;
        System.out.print("Enter the coordinates: ");
        try {
            coordinates[0] = scanner.nextInt();
            coordinates[1] = scanner.nextInt();
        } catch (InputMismatchException ex) {
            successed = false;
        }
        return successed;
    }

    public static void readCoords(int[] coordinates) {
        while (!readCoordinates(coordinates)) {
            System.out.println("You should enter numbers!");
        }
    }

    public static void processRound(char[][] table, int size, char sign) {
        int[] coordinates = new int[2];
        boolean error = true;
        while (error) {
            readCoords(coordinates);
            if (isOverIndexed(size, coordinates)) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else {
                if (isEmptyCell(table, coordinates)) {
                    error = false;
                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                }
            }
        }
        addCellToTable(table, sign, coordinates);
    }

    public static void addCellToTable(char[][] table, char cell, int[] coordinates) {
        table[coordinates[0] - 1][coordinates[1] - 1] = cell;
    }

    public static void printTable(char[][] table, int size) {
        System.out.println("---------");
        for (int row = 0; row < size; row++) {
            System.out.print("| ");
            for (int column = 0; column < size; column++) {
                System.out.print(table[row][column] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public static void initialTable(char[][] table, int size) {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                table[row][column] = ' ';
            }
        }
    }

    public static boolean isEmptyCell(char[][] table, int[] coordinates) {
        boolean isEmpty = true;
        char cell = table[coordinates[0] - 1][coordinates[1] - 1];
        if (cell == 'O' || cell == 'X') {
            isEmpty = false;
        }
        return isEmpty;
    }

    public static boolean isOverIndexed(int size, int[] coordinates) {
        return coordinates[0] < 1 || coordinates[0] > size || coordinates[1] < 1 || coordinates[1] > size;
    }

    public static void countCells(char[][] table, int[][] countTable, int size, char sign) {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (table[row][column] == sign) {
                    countTable[0][row]++;
                    countTable[1][column]++;
                    if (row == column) {
                        countTable[2][0]++;
                    }
                    if (size - 1 - column == row) {
                        countTable[2][1]++;
                    }
                }
            }
        }
    }

    public static boolean isRow(int[][] countTable, int size) {
        boolean threeInRow = false;
        for (int[] row : countTable) {
            for (int cell : row) {
                if (cell == size) {
                    threeInRow = true;
                    break;
                }
            }
        }
        return threeInRow;
    }

    public static int sumCells(int[][] countTable, int size) {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += countTable[0][i];
        }
        return sum;
    }

    public static void countAllCells(char[][] table, int[][] countOTable, int[][] countXTable, int size) {
        countCells(table, countOTable, size, 'O');
        countCells(table, countXTable, size, 'X');
    }

    public static boolean checkEnds(char[][] table, int size) {
        int[][] countOTable = new int[size][size];
        int[][] countXTable = new int[size][size];
        countAllCells(table, countOTable, countXTable, size);

        boolean threeXExist = isRow(countXTable, size);
        boolean threeOExist = isRow(countOTable, size);
        int sumX = sumCells(countXTable, size);
        int sumO = sumCells(countOTable, size);

        boolean isEnd = false;

        if (!threeOExist && !threeXExist && sumO + sumX == size * size) {
            System.out.println("Draw");
            isEnd = true;
        } else if (threeOExist) {
            System.out.println("O wins");
            isEnd = true;
        } else if (threeXExist) {
            System.out.println("X wins");
            isEnd = true;
        }

        return isEnd;
    }

    public static void main(String[] args) {
        final int size = 3;
        char[][] table = new char[size][size];

        initialTable(table, size);
        printTable(table, size);

        char[] signs = {'X', 'O'};
        int i = 0;
        boolean isEnd = false;
        while (!isEnd) {
            processRound(table, size, signs[i % 2]);
            printTable(table, size);
            isEnd = checkEnds(table, size);
            i++;
        }
    }
}
