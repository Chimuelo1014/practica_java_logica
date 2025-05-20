import javax.swing.JOptionPane;

public class Equipo {
    String nombre;
    String pais;
    int puntos = 0;
    int golesAFavor = 0;
    int golesEnContra = 0;
    int primerGolMinuto = 999;

    public Equipo(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
    }

    public int diferenciaGoles() {
        return golesAFavor - golesEnContra;
    }
}