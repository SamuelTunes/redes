/*Equipe: Maria Amélia
			Samuel*/
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Interface stub = (Interface) registry.lookup("Boanoite");
            String response = stub.teste();

            System.out.println("Resposta do servidor: " + response);
        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.toString());
        }
    }
}
