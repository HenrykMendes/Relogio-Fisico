import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class Server extends Thread {
    private final ServerSocket socket;
    private long tempRecebimento; // O tempo para haver o recebimento da mensagem do cliente
    private long tempoEnvio; // O tempo para o envio a mensagem para o cliente

    public Servidor (int port) throws IOException {
        servidorSocket = new ServerSocket (port);
    }

    @Override
    public void run () {
        while (true){
            try {
                // Nome do servidor
                String nome
            }
        }
    }
}
