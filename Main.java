public class Main { // Clase principal que contiene el punto de entrada del programa
    public static void main(String[] args) { // Método main que se ejecuta al iniciar el programa
        Torneo torneo = new Torneo(); // Crea una instancia nueva de la clase Torneo para manejar la competencia
        torneo.registrarEquipos(); // Llama al método para registrar los equipos participantes
        torneo.jugarEtapa1(); // Ejecuta la primera etapa del torneo (todos contra todos)
        torneo.mostrarMarcadoresEtapa1(); // Muestra los resultados/marcadores de todos los partidos de la etapa 1
        torneo.mostrarTabla("TABLA ETAPA 1"); // Muestra la tabla de posiciones después de la etapa 1 con un título personalizado
        torneo.jugarFaseGruposYFinal(); // Inicia la fase de grupos y las etapas finales del torneo
    }
}
