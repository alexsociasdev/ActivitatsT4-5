package TresEnRaya.src;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.UUID;

public class Jugador implements JugadorInterface, Serializable {

    public UUID uuid;
    Tablero.FICHA ficha;
    private boolean fin;

    protected Jugador() throws RemoteException {
        super();
        uuid = UUID.randomUUID();
    }
    public UUID getUUID() {
        return uuid;
    }

    public static void main(String[] args) {
        ServerInterface tictactoe = null;
        Jugador jugador = null;

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 8889);
            tictactoe = (ServerInterface) registry.lookup("TicTacToe");

            jugador = new Jugador();
            tictactoe.addUser(jugador);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (tictactoe != null) {
            String orden;
            int row;
            int column;

            try {
                boolean partidaFinalizada = true;
                boolean esperandoJugadores = false;

                while (partidaFinalizada) {
                    Scanner sc = new Scanner(System.in);

                    if (!esperandoJugadores && tictactoe.getNumUsers() < 2) {
                        System.out.println("Esperando a que se unan más jugadores...");
                        esperandoJugadores = true;
                    }

                    if (tictactoe.getNumUsers() < 2) {
                        continue;
                    }

                    esperandoJugadores = false;

                    System.out.println("Ver ID: 1 " + "\n" +
                            "Ver Tabla: 2 " + "\n" +
                            "Rendirse : 3 " + "\n" +
                            "Poner Ficha: 4 " + "\n");

                    orden = sc.nextLine();

                    switch (orden.toUpperCase()) {
                        case "1":
                            System.out.println("Jugador Conectado: " + jugador.getUUID());
                            break;
                        case "2":
                            tictactoe.seeTable();
                            break;
                        case "3":
                            tictactoe.salir(jugador.getUUID());
                            System.exit(0);
                            break;
                        case "4":
                            System.out.println("Introduce la coordenada de la Fila: ");
                            row = sc.nextInt();
                            System.out.println("Introduce la coordenada de la Columna: ");
                            column = sc.nextInt();
                            tictactoe.getPositionToSetFicha(row, column, jugador.getUUID());
                            tictactoe.checkWin(jugador.getUUID());
                            break;
                        default:
                            System.out.println("Orden no reconocida. Inténtalo de nuevo.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}