import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.*;
import java.text.SimpleDateFormat;


public class Client {
    private String nomeDoServidor; // Nome do servidor
    private int PortaDoServidor; // Valor da porta
    private static int count; // Número de "clientes"(conexões)
    private Timer temporizador;
    private PrintWriter logDescricao;
    private long tempo1;
    private long tempo2;

    //  Método Construtor
    public Client(String nomeDoServidor, int portaDoServidor, Timer temporizador) {
        this.nomeDoServidor = nomeDoServidor;
        this.PortaDoServidor = portaDoServidor;
        this.temporizador = new Timer();
        Client.count = 0;
    }
}
