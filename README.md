// ==================== Equipo.java ====================

// Clase que representa un equipo de fútbol con estadísticas básicas
public class Equipo {
    // Nombre del equipo
    private String nombre;
    // Puntos acumulados en el torneo
    private int puntos;
    // Goles que el equipo ha anotado
    private int golesAFavor;
    // Goles que el equipo ha recibido
    private int golesEnContra;

    // Constructor que inicializa el equipo con su nombre y estadísticas en cero
    public Equipo(String nombre) {
        this.nombre = nombre; // Asigna el nombre proporcionado al equipo
        this.puntos = 0; // Inicializa los puntos a 0
        this.golesAFavor = 0; // Inicializa los goles a favor a 0
        this.golesEnContra = 0; // Inicializa los goles en contra a 0
    }

    // Devuelve el nombre del equipo
    public String getNombre() { return nombre; }

    // Devuelve los puntos acumulados del equipo
    public int getPuntos() { return puntos; }

    // Devuelve los goles a favor del equipo
    public int getGolesAFavor() { return golesAFavor; }

    // Devuelve los goles en contra del equipo
    public int getGolesEnContra() { return golesEnContra; }

    // Actualiza las estadísticas del equipo luego de un partido
    public void actualizarEstadisticas(int golesFavor, int golesContra) {
        // Suma los goles a favor del partido actual al total del equipo
        golesAFavor += golesFavor;
        // Suma los goles en contra del partido actual al total del equipo
        golesEnContra += golesContra;
        // Si el equipo ganó el partido, suma 3 puntos
        if (golesFavor > golesContra) puntos += 3;
        // Si el partido fue un empate, suma 1 punto
        else if (golesFavor == golesContra) puntos += 1;
        // Si el equipo perdió, no suma puntos
    }

    // Método para asignar un punto adicional en caso de victoria por penales
    public void sumarVictoriaPorPenales() {
        puntos += 1; // Se puede ajustar dependiendo de las reglas del torneo
    }
}

// ==================== Partido.java ====================
public class Partido {
    private Equipo equipo1;
    private Equipo equipo2;
    private int goles1;
    private int goles2;
    private String fecha;
    private int duracion;
    private boolean definidoPorPenales;
    private Equipo ganadorPenales;

    public Partido(Equipo e1, Equipo e2, int g1, int g2, String fecha, int duracion) {
        this.equipo1 = e1;
        this.equipo2 = e2;
        this.goles1 = g1;
        this.goles2 = g2;
        this.fecha = fecha;
        this.duracion = duracion;
        this.definidoPorPenales = false;

        // Validaciones
        if (g1 < 0 || g2 < 0) {
            throw new IllegalArgumentException("Los goles no pueden ser negativos.");
        }

        if (duracion > 90) {
            throw new IllegalArgumentException("La duración del partido no puede exceder 90 minutos.");
        }

        equipo1.actualizarEstadisticas(g1, g2);
        equipo2.actualizarEstadisticas(g2, g1);
    }

    public String getFecha() { return fecha; }
    public Equipo getEquipo1() { return equipo1; }
    public Equipo getEquipo2() { return equipo2; }
    public int getDuracion() { return duracion; }
    public int getGoles1() { return goles1; }
    public int getGoles2() { return goles2; }
    public boolean fueDefinidoPorPenales() { return definidoPorPenales; }
    public Equipo getGanadorPenales() { return ganadorPenales; }

    public void definirPorPenales(Equipo ganador) {
        definidoPorPenales = true;
        ganadorPenales = ganador;
        ganador.sumarVictoriaPorPenales();
    }
}

// ==================== Torneo.java ====================
import java.util.Scanner;

public class Torneo {
    private Equipo[] equipos = new Equipo[10];
    private Partido[] partidos = new Partido[100];
    private int numEquipos = 0;
    private int numPartidos = 0;
    private Scanner sc = new Scanner(System.in);

    public boolean registrarEquipo(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        if (buscarEquipo(nombre) != null) return false; // equipo repetido
        if (numEquipos >= equipos.length) return false;

        equipos[numEquipos++] = new Equipo(nombre);
        return true;
    }

    public Equipo buscarEquipo(String nombre) {
        for (int i = 0; i < numEquipos; i++) {
            if (equipos[i].getNombre().equalsIgnoreCase(nombre)) {
                return equipos[i];
            }
        }
        return null;
    }

    public boolean crearPartido(String nombre1, String nombre2, int g1, int g2, String fecha, int duracion) {
        Equipo e1 = buscarEquipo(nombre1);
        Equipo e2 = buscarEquipo(nombre2);
        if (e1 == null || e2 == null || e1 == e2) return false;
        if (g1 < 0 || g2 < 0 || duracion > 90) return false;

        for (int i = 0; i < numPartidos; i++) {
            Partido p = partidos[i];
            if (p.getFecha().equals(fecha) && 
               (p.getEquipo1() == e1 || p.getEquipo2() == e1 ||
                p.getEquipo1() == e2 || p.getEquipo2() == e2)) {
                return false;
            }
        }

        Partido nuevo = new Partido(e1, e2, g1, g2, fecha, duracion);

        if (g1 == g2) {
            System.out.println("\nEmpate detectado. Se procede a definición por penales (5 cada uno).");
            int golesPenales1 = 0;
            int golesPenales2 = 0;
            for (int i = 1; i <= 5; i++) {
                System.out.print("¿Equipo " + e1.getNombre() + " marcó penal " + i + "? (s/n): ");
                if (sc.nextLine().equalsIgnoreCase("s")) golesPenales1++;
                System.out.print("¿Equipo " + e2.getNombre() + " marcó penal " + i + "? (s/n): ");
                if (sc.nextLine().equalsIgnoreCase("s")) golesPenales2++;
            }

            while (golesPenales1 == golesPenales2) {
                System.out.println("Penales empatados. Muerte súbita.");
                System.out.print("¿Equipo " + e1.getNombre() + " marcó penal?: ");
                boolean marca1 = sc.nextLine().equalsIgnoreCase("s");
                System.out.print("¿Equipo " + e2.getNombre() + " marcó penal?: ");
                boolean marca2 = sc.nextLine().equalsIgnoreCase("s");
                if (marca1 && !marca2) { golesPenales1++; break; }
                if (!marca1 && marca2) { golesPenales2++; break; }
            }

            if (golesPenales1 > golesPenales2) {
                nuevo.definirPorPenales(e1);
                System.out.println("Ganador por penales: " + e1.getNombre());
            } else {
                nuevo.definirPorPenales(e2);
                System.out.println("Ganador por penales: " + e2.getNombre());
            }
        }

        partidos[numPartidos++] = nuevo;
        return true;
    }

    public void mostrarTablaPosiciones() {
        System.out.println("Equipo\tPuntos\tGF\tGC");
        for (int i = 0; i < numEquipos; i++) {
            Equipo e = equipos[i];
            System.out.println(e.getNombre() + "\t" + e.getPuntos() + "\t" + e.getGolesAFavor() + "\t" + e.getGolesEnContra());
        }
    }

    public void mostrarResultados() {
        for (int i = 0; i < numPartidos; i++) {
            Partido p = partidos[i];
            String resultado = p.getFecha() + ": " + p.getEquipo1().getNombre() + " " + p.getGoles1() + " - " + p.getGoles2() + " " + p.getEquipo2().getNombre();
            if (p.fueDefinidoPorPenales()) {
                resultado += " (Ganador por penales: " + p.getGanadorPenales().getNombre() + ")";
            }
            System.out.println(resultado);
        }
    }
}

// ==================== Main.java ====================
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Torneo torneo = new Torneo();
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n1. Registrar equipo\n2. Crear partido\n3. Mostrar tabla\n4. Mostrar resultados\n5. Salir");
            opcion = sc.nextInt(); sc.nextLine();
            switch(opcion) {
                case 1:
                    System.out.print("Nombre del equipo: ");
                    String nombre = sc.nextLine();
                    if (torneo.registrarEquipo(nombre))
                        System.out.println("Equipo registrado.");
                    else
                        System.out.println("Error: equipo ya existe, nombre inválido o límite alcanzado.");
                    break;
                case 2:
                    System.out.print("Equipo 1: ");
                    String e1 = sc.nextLine();
                    System.out.print("Equipo 2: ");
                    String e2 = sc.nextLine();
                    System.out.print("Goles equipo 1: ");
                    int g1 = sc.nextInt();
                    System.out.print("Goles equipo 2: ");
                    int g2 = sc.nextInt();
                    System.out.print("Duración del partido (minutos): ");
                    int duracion = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Fecha (dd/mm/aaaa): ");
                    String fecha = sc.nextLine();
                    if (torneo.crearPartido(e1, e2, g1, g2, fecha, duracion))
                        System.out.println("Partido creado.");
                    else
                        System.out.println("Error al crear partido (datos inválidos, conflicto de fecha o duración > 90).");
                    break;
                case 3:
                    torneo.mostrarTablaPosiciones();
                    break;
                case 4:
                    torneo.mostrarResultados();
                    break;
            }
        } while(opcion != 5);
    }
}
