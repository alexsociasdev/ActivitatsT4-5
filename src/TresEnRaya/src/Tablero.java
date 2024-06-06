package TresEnRaya.src;

public class Tablero {

    enum FICHA {
        X, O, EMPTY
    };

    static FICHA[][] tablero = new FICHA[3][3];
    /*
     * 0.0, 0.1, 0.2
     * 1.0, 1.1, 1.2
     * 2.0, 2.1, 2.2
     */

    public static int[][][] posicionesGanadoras = {
            { { 0, 0 }, { 0, 1 }, { 0, 2 } },
            { { 1, 0 }, { 1, 1 }, { 1, 2 } },
            { { 2, 0 }, { 2, 1 }, { 2, 2 } },
            { { 0, 0 }, { 1, 0 }, { 2, 0 } },
            { { 0, 1 }, { 1, 1 }, { 2, 1 } },
            { { 0, 2 }, { 1, 2 }, { 2, 2 } },
            { { 0, 0 }, { 1, 1 }, { 2, 2 } },
            { { 0, 2 }, { 1, 1 }, { 2, 0 } }
    };

    public void createTablero() {
        for (int row = 0; row < tablero.length; row++) {
            for (int column = 0; column < tablero.length; column++) {
                tablero[row][column] = null;
            }
        }
    }

    public static void getTablero() {
        System.out.println("----------------------");
        for (int row = 0; row < tablero.length; row++) {
            System.out.print("| ");
            for (int column = 0; column < tablero[row].length; column++) {
                System.out.print(tablero[row][column] + " | ");
            }
            System.out.println();
            System.out.println("----------------------");
        }
    }

    public static void setFicha(int fila, int columna, FICHA ficha) {
        if (fila >= 0 && fila < tablero.length && columna >= 0 && columna < tablero[0].length) {
            tablero[fila][columna] = ficha;
        } else {
            System.out.println("Coordenadas inválidas.");
        }
    }

    public static int getPosition(int fila, int columna) {
        if(tablero[fila][columna] == null){
            return 1;
        }
        return 2;
    }

    public static boolean isTableroLleno() {
        for (FICHA[] fila : tablero) {
            for (FICHA ficha : fila) {
                if (ficha == null) {
                    return false;
                }
            }
        }
        return true;
    }

}
