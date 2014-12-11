package museo;
import java.util.concurrent.Semaphore;
/**
 *
 * @author javi
 */
public class Datos extends Thread{
    public static final int MAX_SALA_ESPERA = 20;
    public static final int MAX_EDIFICIO = 12;
    public static final int MAX_GUIA = 6;
    public static final int MAX_ENCUESTA = 5;
    public static Semaphore colaSalaEspera;
    public static Semaphore colaEdificio;
    public static Semaphore cabinaFotos;
    public static Semaphore guia;
    public static Semaphore guiaEsperando;
    public static Semaphore encuesta;
    public static int visitantesSalaEspera = 0;
    public static int visitantesEdificio = 0;
    public static int visitantesGuia = 0;
    public static int visitantesEncuesta = 0;
    public static boolean esperaLlena = false;
    public static int identificadorVisitante = 0;

    public Datos() {
        colaSalaEspera = new Semaphore(MAX_SALA_ESPERA, true);
        colaEdificio = new Semaphore(MAX_EDIFICIO, true);
        cabinaFotos = new Semaphore(1, true);
        guia = new Semaphore(0, true);
        guiaEsperando = new Semaphore(0, esperaLlena);
        encuesta = new Semaphore(MAX_ENCUESTA, true);
    }
}