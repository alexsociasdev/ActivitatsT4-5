package TresEnRaya.src;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Servidor implements ServerInterface {

    public static ArrayList<JugadorInterface> usuarios = new ArrayList<>();
    private static Map<JugadorInterface, Tablero.FICHA> fichasAsignadas = new HashMap<>();
    private static JugadorInterface jugadorConElTurno;

    Tablero tablero;

    public void addUser(JugadorInterface jugador) throws RemoteException {
        if (usuarios.size() < 2) {
            usuarios.add(jugador);
            System.out.println("Jugador conectado: " + jugador.getUUID());
            if (usuarios.size() == 2) {
                System.out.println(usuarios.toString());
                setFichasToPlayers();
                asignarTurno();
            }
        } else {
            System.out.println("Faltan jugadores");
        }
    }

    public void asignarTurno() {
        Random rand = new Random();
        jugadorConElTurno = usuarios.get(rand.nextInt(2));
        System.out.println("Comienza el jugador con UUID: " + jugadorConElTurno.getUUID());
    }

    public void setFichasToPlayers() throws RemoteException {
        Tablero.FICHA[] fichasDisponibles = { Tablero.FICHA.X, Tablero.FICHA.O };

        for (int i = 0; i < usuarios.size(); i++) {
            JugadorInterface jugador = usuarios.get(i);
            Tablero.FICHA fichaAsignada = fichasDisponibles[i];
            fichasAsignadas.put(jugador, fichaAsignada);
            System.out.println("Ficha asignada a Jugador " + jugador.getUUID() + ": " + fichaAsignada);
        }
    }

    public void getPositionToSetFicha(int fila, int columna, UUID jugadorUUID) throws RemoteException {
        JugadorInterface jugador = findJugadorByUUID(jugadorUUID);
        if (jugador != null) {
            if (jugador.getUUID().equals(jugadorConElTurno.getUUID())) {
                Tablero.FICHA ficha = getAssignedFicha(jugador);
                if (ficha != null) {
                    int validator = Tablero.getPosition(fila, columna);
                    if (validator == 2) {
                        System.out.println("Posicion ocupada");
                        return;
                    }
                    Tablero.setFicha(fila, columna, ficha);
                    checkWin(jugadorUUID);
                    cambiarTurno();
                } else {
                    System.out.println("No se encontro una ficha asignada para el jugador con UUID: " + jugadorUUID);
                }
            } else {
                System.out.println("No es tu turno para jugar.");
            }
        } else {
            System.out.println("No se encontro ningún jugador con UUID: " + jugadorUUID);
        }
    }

    private JugadorInterface findJugadorByUUID(UUID jugadorUUID) {
        for (JugadorInterface jugador : usuarios) {
            if (jugador.getUUID().equals(jugadorUUID)) {
                return jugador;
            }
        }
        return null;
    }

    public Tablero.FICHA getAssignedFicha(JugadorInterface jugador) throws RemoteException {
        return fichasAsignadas.get(jugador);
    }

    public void seeTable() throws RemoteException {
        Tablero.getTablero();
    }

    public void checkWin(UUID jugadorUUID) throws RemoteException {
        JugadorInterface jugador = findJugadorByUUID(jugadorUUID);

        if (jugador == null) {
            System.out.println("No se encontró ningún jugador con UUID: " + jugadorUUID);
            return;
        }

        for (int[][] combinacion : Tablero.posicionesGanadoras) {
            Tablero.FICHA fichaPrimeraCasilla = Tablero.tablero[combinacion[0][0]][combinacion[0][1]];

            if (fichaPrimeraCasilla != null) {
                boolean victoria = true;

                for (int i = 1; i < combinacion.length; i++) {
                    int fila = combinacion[i][0];
                    int columna = combinacion[i][1];

                    if (Tablero.tablero[fila][columna] != fichaPrimeraCasilla) {
                        victoria = false;
                        break;
                    }
                }

                if (victoria) {
                    System.out.println("¡Han ganado las " + fichaPrimeraCasilla + "!");
                    seeTable();
                    System.exit(0);
                }
            }

        }

        if (Tablero.isTableroLleno()) {
            System.out.println("¡Habeis empatado!");
            return;
        }

    }

    public void salir(UUID jugadorUUID) throws RemoteException {
        tablero = new Tablero();
        JugadorInterface jugador = findJugadorByUUID(jugadorUUID);
        if (jugador != null) {
            usuarios.remove(jugador);
            System.out.println("Jugador desconectado: " + jugadorUUID);
            tablero.createTablero();
            seeTable();
            fichasAsignadas.clear();
        } else {
            System.out.println("No se encontró ningún jugador con UUID: " + jugadorUUID);
        }
    }

    public int getNumUsers() throws RemoteException {
        return usuarios.size();
    }

    public void cambiarTurno() {
        jugadorConElTurno = (jugadorConElTurno.equals(usuarios.get(0))) ? usuarios.get(1) : usuarios.get(0);
        System.out.println("Turno cambiado. Ahora es el turno del jugador con UUID: " + jugadorConElTurno.getUUID());
    }

    public static void main(String[] args) {
        Servidor serverObject = new Servidor();

        Registry reg = null;

        try {
            reg = LocateRegistry.createRegistry(8889);
        } catch (Exception e) {
            System.out.println("ERROR: No se ha podido crear el registro");
            e.printStackTrace();
        }

        try {
            reg.bind("TicTacToe", (ServerInterface) UnicastRemoteObject.exportObject(serverObject, 0));
        } catch (Exception e) {
            System.out.println("ERROR: No se ha podido inscribit el objeto servidor");
        }
    }

}
