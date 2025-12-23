import java.io.Serializable;

public class Kardex implements Serializable { //Ibstanciar kardex xq pasa a Alumno
    private int Asig_aprobadas;

    private float promedio;

    public Kardex(int Asig_aprobadas, float promedio){
        this.Asig_aprobadas = Asig_aprobadas;
        this.promedio = promedio;
    }


    @Override
    public String toString(){
        return ", Promedio = " + promedio +
        " Asignaturas aprobadas : "+ Asig_aprobadas;
    }
}
