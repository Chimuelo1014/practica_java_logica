public class Equipo { // Clase que representa a un equipo participante en el torneo
    String nombre; // Nombre del equipo
    int puntos = 0; // Puntos acumulados por el equipo durante el torneo, inicia en 0
    int golesAFavor = 0; // Total de goles que el equipo ha anotado a favor
    int golesEnContra = 0; // Total de goles que el equipo ha recibido en contra
    String primerGolJugador = ""; // Nombre del jugador que hizo el primer gol del equipo en el torneo
    int primerGolMinuto = 999; // Minuto en que se anotó el primer gol del equipo, inicializado alto para buscar el mínimo

    public Equipo(String nombre) { // Constructor que inicializa el equipo con su nombre
        this.nombre = nombre; // Asigna el nombre recibido al atributo nombre
    }

    public int diferenciaGoles() { // Método que calcula la diferencia de goles del equipo
        return golesAFavor - golesEnContra; // Retorna la resta entre goles a favor y en contra
    }
}
