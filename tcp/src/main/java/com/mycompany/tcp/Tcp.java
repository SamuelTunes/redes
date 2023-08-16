package com.mycompany.tcp;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Samuel
 */
public class Tcp {
    public static void main(String[] args) {
        // Criação das máquinas da rede
        MaquinaLan maquina1 = new MaquinaLan("192.168.0.1");
        MaquinaLan maquina2 = new MaquinaLan("192.168.0.2");
        // Colocando as máquinas na rede
        List<MaquinaLan> rede = new ArrayList<MaquinaLan>();
        rede.add(maquina1);
        rede.add(maquina2);   
        // Implementando a topologia em anel, de "esquerda" e "direita"
        maquina1.setProximaMaquina(maquina2);
        maquina2.setProximaMaquina(maquina1);
        // Envio de mensagem da primeira máquina para a segunda
        maquina1.enviarMensagem("Olá, máquina 2!");
        // Impressão do equivalente a um dump de sniffer de rede
        for (MaquinaLan maquina : rede) {
            System.out.println(maquina.getEnderecoIP() + ":");
            System.out.println(maquina.getDumpSniffer());
        }
    }
}

//criação da classe maquinaLan
class MaquinaLan {
    private String enderecoIP;
    private MaquinaLan proximaMaquina;
    private List<String> pacotesRecebidos;
    
    public MaquinaLan(String enderecoIP) {
        this.enderecoIP = enderecoIP;
        this.pacotesRecebidos = new ArrayList<String>();
    }
    
    public String getEnderecoIP() {
        return this.enderecoIP;
    }
    
    public void setProximaMaquina(MaquinaLan proximaMaquina) {
        this.proximaMaquina = proximaMaquina;
    }
    
    // é responsável por enviar uma mensagem para a próxima máquina da LAN
    public void enviarMensagem(String mensagem) {
        PacoteTCP pacote = new PacoteTCP(this.enderecoIP, this.proximaMaquina.getEnderecoIP(), mensagem);
        this.proximaMaquina.receberPacote(pacote);
    }
    //envia o pacote para a próxima máquina receber.
    public void receberPacote(PacoteTCP pacote) {
        this.pacotesRecebidos.add(pacote.toString());
    }
    
    //criação do método getDumpSniffer, onde será o banco que salvará o caminho feito pela troca de mensagens das maquinas
    public String getDumpSniffer() {
        StringBuilder dump = new StringBuilder();
        for (String pacote : this.pacotesRecebidos) {
            dump.append(pacote).append("\n");
            
        }
        return dump.toString();
    }
}
//criação da classe PacoteTCP
class PacoteTCP {
    private String enderecoOrigem;
    private String enderecoDestino;
    private String mensagem;
    
    public PacoteTCP(String enderecoOrigem, String enderecoDestino, String mensagem) {
        this.enderecoOrigem = enderecoOrigem;
        this.enderecoDestino = enderecoDestino;
        this.mensagem = mensagem;
    }
    
    public String toString() {
        return "Endereço de origem: " + this.enderecoOrigem + 
                ", Endereço de destino: " + this.enderecoDestino + 
                ", Mensagem: " + this.mensagem;
    }
}