import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.*;
import java.text.SimpleDateFormat;


public class Client {
    private String nomeDoServidor; // Nome do servidor
    private int portaDoServidor; // Valor da porta
    private static int count; // Número de "clientes"(conexões)
    private Timer temporizador;
    private PrintWriter logDescricao;
    private long tempo0; //O momento em que envia o pedido ao servidor
    private long tempo3; //O momento em que recebe a resposta do servidor

    //  Método Construtor
    public Client(String nomeDoServidor, int portaDoServidor, Timer temporizador) {
        this.nomeDoServidor = nomeDoServidor;
        this.portaDoServidor = portaDoServidor;
        this.temporizador = new Timer();
        Client.count = 0;
        try {
            // Caminho onde será salvo o
            this.logDescricao = new PrintWriter("C:\\Users\\CLIENTE\\OneDrive\\Documentos\\6. InteliJj\\dis.txt");
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    class Conversation extends TimerTask {

        @Override
        public void run() { // Número de
            if (count < 3) {
                try {
                    System.out.println("--------------------------------------------");
                    System.out.println("Conectando a ... " + nomeDoServidor + " na porta " + portaDoServidor);

                    //Conecta ao servidor
                    Socket cliente = new Socket(nomeDoServidor, portaDoServidor);
                    System.out.println("Conectado a " + cliente.getRemoteSocketAddress());

                    //Envia mensagem para o servidor
                    tempo0 = System.currentTimeMillis();
                    OutputStream outToServer = cliente.getOutputStream();
                    DataOutputStream out = new DataOutputStream(outToServer);
                    out.writeUTF("Solicito a hora" + cliente.getLocalSocketAddress());

                    //Recebe mensagem do servidor
                    InputStream inFromServer = cliente.getInputStream();
                    DataInputStream in = new DataInputStream(inFromServer);
                    long tempo1 = in.readLong();   //Recebe o quando o servidor recebeu a mensagem
                    long tempoS = in.readLong();   //Recebe o tempo do servidor
                    long tempo2 = in.readLong();   //Recebe o tempo de envio no servidor
                    tempo3 = System.currentTimeMillis();

                    // fecha a conexão
                    cliente.close();

                    count++;

                    tempo2 += 1500; // Tempo simulando do atraso da resposta do servidor
                    tempo3 += 2000; // Tempo simulando um atraso no recebimento da resposta do servidor para o cliente

                    // Cálculo de Cristian
                    long tempoRespostaRequisicao = tempo3 - tempo0; // Tempo que o cliente esperou até o recebimento da resposta do s ervidor
                    long tempoRecebimentoMensagem01 = tempo1 - tempo0; // Tempo que a mensagem leva do CLIENTE para o SERVIDOR
                    long tempoRecebimentoMensagem02 = tempo3 - tempo2; // Tempo que a mensagem leva do SERVIDOR para o CLIENTE

                    long tempoCristian = tempoS + ((tempoRespostaRequisicao + tempoRecebimentoMensagem02 - tempoRecebimentoMensagem01)/2);

                    //Salvando os dados no arquivo
                    logDescricao.println("----------------------------------------------------");
                    logDescricao.println("Tempo envio Cliente: " + formataData(tempo0));
                    logDescricao.println("Tempo recebimento Servidor: " + formataData(tempo1));
                    logDescricao.println("Tempo envio Servidor: " + formataData(tempo2));
                    logDescricao.println("Tempo recebimento Cliente: " + formataData(tempo3));

                    logDescricao.println("\nTempo marcado no servidor: " + formataData(tempoS));
                    logDescricao.println("Tempo Algoritmo de Cristian: " + formataData(tempoCristian));

                    //Imprimindo no console
                    System.out.println("\nTempo envio Cliente: " + formataData(tempo0));
                    System.out.println("Tempo recebimento Servidor: " + formataData(tempo1));
                    System.out.println("Tempo envio Servidor: " + formataData(tempo2));
                    System.out.println("Tempo recebimento Cliente: " + formataData(tempo3));

                    System.out.println("\nTempo marcado no servidor: " + formataData(tempoS));
                    System.out.println("Tempo Algoritmo de Cristian: " + formataData(tempoCristian));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                logDescricao.close(); // Fecha e libera o arquivo
                temporizador.cancel();
                temporizador.purge();
            }
        }
    }

}
