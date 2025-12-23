public class Materia {
    private String nombre;
    private String nrc;
    private int creditos;

    public Materia(String nombre, String nrc, int creditos) {
        this.nombre = nombre;
        this.nrc = nrc;
        this.creditos = creditos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNrc() {
        return nrc;
    }

    public int getCreditos() {
        return creditos;
    }
}