// Clase para representar un tenedor
class Tenedor {
    private boolean enUso = false;

    public synchronized void tomar() throws InterruptedException {
        while (enUso) {
            wait();
        }
        enUso = true;
    }

    public synchronized void soltar() {
        enUso = false;
        notify();
    }
}

// Clase para representar un filósofo
class Filosofo extends Thread {
    private int id;
    private Tenedor tenedorIzquierdo;
    private Tenedor tenedorDerecho;

    public Filosofo(int id, Tenedor izquierdo, Tenedor derecho) {
        this.id = id;
        this.tenedorIzquierdo = izquierdo;
        this.tenedorDerecho = derecho;
    }

    private void pensar() throws InterruptedException {
        System.out.println("Filósofo " + id + " está pensando...");
        Thread.sleep((int) (Math.random() * 1000));
    }

    private void comer() throws InterruptedException {
        System.out.println("Filósofo " + id + " está intentando comer...");
        tenedorIzquierdo.tomar();
        tenedorDerecho.tomar();

        System.out.println("Filósofo " + id + " está comiendo...");
        Thread.sleep((int) (Math.random() * 1000));

        tenedorIzquierdo.soltar();
        tenedorDerecho.soltar();
        System.out.println("Filósofo " + id + " ha terminado de comer.");
    }

    @Override
    public void run() {
        try {
            while (true) {
                pensar();
                comer();
            }
        } catch (InterruptedException e) {
            System.out.println("Filósofo " + id + " fue interrumpido.");
        }
    }
}

// Clase principal
public class Filosofos {
    public static void main(String[] args) {
        int numeroFilosofos = 5;
        Tenedor[] tenedores = new Tenedor[numeroFilosofos];
        Filosofo[] filosofos = new Filosofo[numeroFilosofos];

        // Inicializar tenedores
        for (int i = 0; i < numeroFilosofos; i++) {
            tenedores[i] = new Tenedor();
        }

        // Crear filósofos y asignarles tenedores
        for (int i = 0; i < numeroFilosofos; i++) {
            Tenedor izquierdo = tenedores[i];
            Tenedor derecho = tenedores[(i + 1) % numeroFilosofos];

            // Para evitar interbloqueo, el último filósofo toma los tenedores en orden inverso
            if (i == numeroFilosofos - 1) {
                filosofos[i] = new Filosofo(i, derecho, izquierdo);
            } else {
                filosofos[i] = new Filosofo(i, izquierdo, derecho);
            }

            filosofos[i].start();
        }
    }
}