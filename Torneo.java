import javax.swing.JOptionPane;

public class Torneo {
    Equipo[] equipos = new Equipo[10];
    Partido[][] partidos = new Partido[10][10];

    public void registrarEquipos() {
        for (int i = 0; i < 10; i++) {
            String nombre, pais;
            do {
                nombre = JOptionPane.showInputDialog(null, "Nombre del equipo #" + (i + 1));
            } while (nombre == null || nombre.trim().isEmpty());

            do {
                pais = JOptionPane.showInputDialog(null, "País del equipo #" + (i + 1));
            } while (pais == null || pais.trim().isEmpty());

            equipos[i] = new Equipo(nombre.trim(), pais.trim());
        }
    }

    public int pedirNumero(String mensaje) {
        while (true) {
            try {
                String entrada = JOptionPane.showInputDialog(null, mensaje);
                if (entrada == null) continue;
                int numero = Integer.parseInt(entrada);
                if (numero >= 0) return numero;
            } catch (Exception e) {}
            JOptionPane.showMessageDialog(null, "Por favor ingrese un número válido.");
        }
    }

    public void jugarEtapa1() {
        for (int i = 0; i < 10; i++) {
            for (int j = i + 1; j < 10; j++) {
                int g1 = pedirNumero("Goles de " + equipos[i].nombre + " contra " + equipos[j].nombre);
                int g2 = pedirNumero("Goles de " + equipos[j].nombre + " contra " + equipos[i].nombre);
                int minuto = pedirNumero("Minuto del primer gol del partido");

                Partido p = new Partido(equipos[i], equipos[j]);
                p.jugar(g1, g2, minuto);
                partidos[i][j] = p;
                partidos[j][i] = p;
            }
        }
    }

    public void mostrarTabla(String titulo) {
        ordenar();
        String tabla = "";
        for (int i = 0; i < 10; i++) {
            Equipo e = equipos[i];
            tabla += (i + 1) + ". " + e.nombre + " (" + e.pais + ") - Pts: " + e.puntos + ", GF: " + e.golesAFavor +
                     ", GC: " + e.golesEnContra + ", DG: " + e.diferenciaGoles() + "\n";
        }
        JOptionPane.showMessageDialog(null, tabla, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public void ordenar() {
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 10; j++) {
                if (!mejorQue(equipos[i], equipos[j])) {
                    Equipo tmp = equipos[i];
                    equipos[i] = equipos[j];
                    equipos[j] = tmp;
                }
            }
        }
    }

    public boolean mejorQue(Equipo a, Equipo b) {
        if (a.puntos != b.puntos) return a.puntos > b.puntos;
        if (a.diferenciaGoles() != b.diferenciaGoles()) return a.diferenciaGoles() > b.diferenciaGoles();
        if (a.golesAFavor != b.golesAFavor) return a.golesAFavor > b.golesAFavor;
        return a.primerGolMinuto < b.primerGolMinuto;
    }

    public void jugarFaseGruposYFinal() {
        Equipo[] clasificados = new Equipo[8];
        for (int i = 0; i < 8; i++) clasificados[i] = equipos[i];

        Equipo[] grupoA = {clasificados[1], clasificados[3], clasificados[5], clasificados[7]};
        Equipo[] grupoB = {clasificados[0], clasificados[2], clasificados[4], clasificados[6]};

        Equipo ganadorA = jugarGrupo(grupoA, "Grupo A");
        Equipo ganadorB = jugarGrupo(grupoB, "Grupo B");

        jugarFinal(ganadorA, ganadorB);
    }

    public Equipo jugarGrupo(Equipo[] grupo, String nombreGrupo) {
        int[] puntos = new int[4];
        int[] gf = new int[4];
        int[] gc = new int[4];
        int[] primerGol = new int[4];
        for (int i = 0; i < 4; i++) primerGol[i] = 999;

        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                int g1 = pedirNumero("[" + nombreGrupo + "] " + grupo[i].nombre + " goles contra " + grupo[j].nombre);
                int g2 = pedirNumero("[" + nombreGrupo + "] " + grupo[j].nombre + " goles contra " + grupo[i].nombre);
                int min = pedirNumero("Minuto del primer gol");

                gf[i] += g1; gc[i] += g2;
                gf[j] += g2; gc[j] += g1;

                if (g1 > g2) puntos[i] += 3;
                else if (g2 > g1) puntos[j] += 3;
                else { puntos[i]++; puntos[j]++; }

                if (g1 > 0 && min < primerGol[i]) primerGol[i] = min;
                if (g2 > 0 && min < primerGol[j]) primerGol[j] = min;
            }
        }

        int mejor = 0;
        for (int i = 1; i < 4; i++) {
            if (puntos[i] > puntos[mejor]) mejor = i;
            else if (puntos[i] == puntos[mejor]) {
                int dg1 = gf[i] - gc[i];
                int dg2 = gf[mejor] - gc[mejor];
                if (dg1 > dg2) mejor = i;
                else if (dg1 == dg2) {
                    if (gf[i] > gf[mejor]) mejor = i;
                    else if (gf[i] == gf[mejor] && primerGol[i] < primerGol[mejor]) mejor = i;
                }
            }
        }

        return grupo[mejor];
    }

    public void jugarFinal(Equipo e1, Equipo e2) {
        int g1 = pedirNumero("FINAL: Goles de " + e1.nombre);
        int g2 = pedirNumero("FINAL: Goles de " + e2.nombre);

        String resultado = "FINAL: " + e1.nombre + " " + g1 + " - " + g2 + " " + e2.nombre + "\n";

        if (g1 != g2) {
            resultado += "CAMPEÓN: " + (g1 > g2 ? e1.nombre : e2.nombre);
        } else {
            resultado += "Empate. Lanzamiento de penales.\n";
            while (true) {
                int p1 = pedirNumero("Penal: " + e1.nombre + " (1=gol, 0=falla)");
                int p2 = pedirNumero("Penal: " + e2.nombre + " (1=gol, 0=falla)");
                if (p1 != p2) {
                    resultado += "CAMPEÓN POR PENALES: " + (p1 > p2 ? e1.nombre : e2.nombre);
                    break;
                }
            }
        }

        JOptionPane.showMessageDialog(null, resultado, "RESULTADO FINAL", JOptionPane.INFORMATION_MESSAGE);
    }
}