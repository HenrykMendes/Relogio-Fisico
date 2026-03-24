import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class Server extends Thread {
    private final ServerSocket servidorSocket;
    private long tempRecebimento; // O tempo para haver o recebimento da mensagem do cliente
    private long tempoEnvio; // O tempo para o envio a mensagem para o cliente

    public Server (int port) throws IOException {
        servidorSocket = new ServerSocket (port);
    }

    public void run() {
        while (true) {
            try {
                //Saída do nome do servidor
                String nomeHost = java.net.InetAddress.getLocalHost().getHostName();
                System.out.println("--------------------------------------------");
                System.out.println("Nome do Servidor: " + nomeHost);

                System.out.println("Esperado cliente na porta " + servidorSocket.getLocalPort() + "...");

                //Aceite uma conexão de clientes
                Socket server = servidorSocket.accept();
                System.out.println("Conectado em: " + server.getRemoteSocketAddress());

                //Receber mensagem de clientes
                DataInputStream in = new DataInputStream(server.getInputStream());
                System.out.println(in.readUTF());
                tempRecebimento = System.currentTimeMillis();

                //Enviar mensagem de volta aos clientes
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                long tempoServidor = System.currentTimeMillis();
                tempoEnvio = System.currentTimeMillis();

                out.writeLong(tempRecebimento);      //Envia o tempo quando recebeu a mensagem do cliente
                out.writeLong(tempoServidor);  //Envia o tempo atual do servidor
                out.writeLong(tempoEnvio);     //Envia o tempo de envio para o cliente

                //Fecha a conexão
                server.close();
            } catch (SocketTimeoutException s) {
                System.out.println("Socket expirou!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

