public class Partido {
    Equipo equipo1;
    Equipo equipo2;
    int golesEquipo1;
    int golesEquipo2;

    public Partido(Equipo e1, Equipo e2) {
        this.equipo1 = e1;
        this.equipo2 = e2;
    }

    public void jugar(int goles1, int goles2, int minuto) {
        this.golesEquipo1 = goles1;
        this.golesEquipo2 = goles2;

        equipo1.golesAFavor += goles1;
        equipo1.golesEnContra += goles2;
        equipo2.golesAFavor += goles2;
        equipo2.golesEnContra += goles1;

        if (goles1 > goles2) equipo1.puntos += 3;
        else if (goles2 > goles1) equipo2.puntos += 3;
        else {
            equipo1.puntos++;
            equipo2.puntos++;
        }

        if (goles1 > 0 && minuto < equipo1.primerGolMinuto)
            equipo1.primerGolMinuto = minuto;
        if (goles2 > 0 && minuto < equipo2.primerGolMinuto)
            equipo2.primerGolMinuto = minuto;
    }
}