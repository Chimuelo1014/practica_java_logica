import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        Torneo torneo = new Torneo();
        torneo.registrarEquipos();
        torneo.jugarEtapa1();
        torneo.mostrarTabla("TABLA ETAPA 1");
        torneo.jugarFaseGruposYFinal();
    }
}