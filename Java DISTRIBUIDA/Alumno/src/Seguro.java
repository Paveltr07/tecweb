import java.io.Serializable;

public class Seguro implements Serializable {
    private String dependencia;
    private int numero;
    private int vigencia;

    public String getDependencia() {
        return dependencia;
    }

    public int getNumero() {
        return numero;
    }

    public int getVigencia() {
        return vigencia;
    }

    public Seguro(String dependencia, int numero, int vigencia){
        this.dependencia = dependencia;
        this.numero = numero;
        this.vigencia = vigencia;
    }

    @Override
    public String toString(){
        return ", Dependencia "+ dependencia +
                ", Numero = " + numero+"\"" +
                ", Vigencia = " + vigencia+"\"";
    }
}
