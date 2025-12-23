public class Kardex {
    private Materia[] materias;
    private int indice;

    public Kardex() {
        this.materias = new Materia[6];
        this.indice = 0;
    }

    public void agregarMateria(Materia m) {
        if (indice < 6) {
            materias[indice] = m;
            indice++;
        } else {
            System.out.println("Kardex lleno. No se pueden agregar más materias.");
        }
    }

    public int getNumeroMaterias() {
        return indice;
    }

    public Materia getMateria(int i) {
        if (i >= 0 && i < indice) {
            return materias[i];
        }
        return null;
    }
}