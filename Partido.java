package torneo; // Define el paquete donde se encuentra la clase Partido

import torneo.Equipo; // Importa la clase Equipo para usarla en Partido
import java.util.Random; // Importa la clase Random para generación de números aleatorios

public class Partido { // Clase que representa un partido entre dos equipos
    Equipo equipo1; // Referencia al primer equipo que juega el partido
    Equipo equipo2; // Referencia al segundo equipo que juega el partido
    int golesEquipo1; // Goles anotados por el primer equipo en este partido
    int golesEquipo2; // Goles anotados por el segundo equipo en este partido
    String primerGolJugador = ""; // Nombre del jugador que hizo el primer gol en el partido
    int primerGolMinuto = 0; // Minuto en el que se anotó el primer gol

    // Lista estática de jugadores famosos, usados para asignar aleatoriamente el autor del primer gol
    static String[] jugadores = {
        "Messi", "Ronaldo", "Mbappe", "Neymar", "Haaland",
        "Benzema", "Modric", "Kane", "Vinicius", "Salah",
        "Griezmann", "De Bruyne", "Lewandowski", "Foden", "Rodri"
    };

    public Partido(Equipo e1, Equipo e2) { // Constructor que recibe los dos equipos que jugarán el partido
        this.equipo1 = e1; // Asigna el primer equipo
        this.equipo2 = e2; // Asigna el segundo equipo
    }

    public void jugar() { // Método que simula el desarrollo y resultado del partido
        Random rand = new Random(); // Crea una instancia para generar números aleatorios

        golesEquipo1 = rand.nextInt(11); // Genera goles aleatorios para el equipo1 entre 0 y 10
        golesEquipo2 = rand.nextInt(11); // Genera goles aleatorios para el equipo2 entre 0 y 10
        int totalGoles = golesEquipo1 + golesEquipo2; // Calcula el total de goles del partido

        // Asignación de puntos según el resultado
        if (golesEquipo1 > golesEquipo2) equipo1.puntos += 3; // Si gana equipo1, suma 3 puntos
        else if (golesEquipo2 > golesEquipo1) equipo2.puntos += 3; // Si gana equipo2, suma 3 puntos
        else { // Si empatan, ambos equipos suman 1 punto
            equipo1.puntos++;
            equipo2.puntos++;
        }

        // Actualización de estadísticas de goles a favor y en contra para ambos equipos
        equipo1.golesAFavor += golesEquipo1;
        equipo1.golesEnContra += golesEquipo2;
        equipo2.golesAFavor += golesEquipo2;
        equipo2.golesEnContra += golesEquipo1;

        // Si hubo goles en el partido, asignar aleatoriamente el primer gol
        if (totalGoles > 0) {
            primerGolMinuto = rand.nextInt(90) + 1; // Minuto aleatorio entre 1 y 90
            String jugador = jugadores[rand.nextInt(jugadores.length)]; // Jugador aleatorio de la lista

            if (rand.nextBoolean()) { // Decide aleatoriamente si el gol lo hizo equipo1 o equipo2
                equipo1.primerGolJugador = jugador; // Asigna el jugador al equipo1
                equipo1.primerGolMinuto = primerGolMinuto; // Actualiza el minuto del primer gol para equipo1
                primerGolJugador = jugador + " (" + equipo1.nombre + ")"; // Guarda el dato para mostrarlo
            } else {
                equipo2.primerGolJugador = jugador; // Asigna el jugador al equipo2
                equipo2.primerGolMinuto = primerGolMinuto; // Actualiza el minuto del primer gol para equipo2
                primerGolJugador = jugador + " (" + equipo2.nombre + ")"; // Guarda el dato para mostrarlo
            }
        }
    }
}
