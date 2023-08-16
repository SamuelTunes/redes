/*Equipe: Maria Amélia
			Samuel*/
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Interface {

    public Server() {
        super();
    }

    public String teste() throws RemoteException {
        return "Comunicação simples até o client!";
    }

    public static void main(String[] args) {
        try {
          
            Server server = new Server();
            Interface stub = (Interface) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Boanoite", stub);
			
            System.out.println("Servidor RMI pronto para receber requisições.");
        }catch (Exception e) {
            System.err.println("Erro no servidor: " + e.toString());
        }
    }
}
