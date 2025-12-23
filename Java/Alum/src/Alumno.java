import java.io.Serializable;

public class Alumno implements Serializable {
    private int matricula;
    private String nombre;

    //Formato transient = transitorio no se serealicen
    //private transient String facultad;

    private String facultad;

    private Kardex K_alumno;

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setMatricula(int matricula){
        this.matricula = matricula;
    }

    public void setFacultad(String facultad){
        this.facultad = facultad;
    }

    public void setK_alumno(Kardex k_alumno){
        this.K_alumno = k_alumno;
    }

    @Override
    public String toString(){
        return "Alumno : " + nombre +
                ", Facultad = " + facultad +
                ", Matricula = " + matricula+
                K_alumno;
    }

}
