public class Practicas {
    private int duracion;
    private String empresa;
    private int horas_dia;

    public int getDuracion() {
        return duracion;
    }

    public String getEmpresa(){
        return empresa;
    }

    public int getHoras_dia(){
        return horas_dia;
    }

    public Practicas(int duracion, String empresa, int horas_dia){
        this.duracion = duracion;
        this.empresa = empresa;
        this.horas_dia = horas_dia;
    }

    @Override
    public String toString(){
        return ", Duracion "+ duracion +
                ", Empresa = " + empresa+"\"" +
                ", Horas DIa = " + horas_dia+"\"";
    }
}
