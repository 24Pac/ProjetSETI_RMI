import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import raytracer.Image;
import raytracer.Disp;

public class Client implements ServiceClient {
    private Disp disp;
    private int largeur, hauteur;

    public Client(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        disp = new Disp("Résultat Raytracer", largeur, hauteur);
    }

    public void recevoirFragment(Image img, int x0, int y0) throws RemoteException {
        disp.setImage(img, x0, y0);
        System.out.println("Fragment reçu : (" + x0 + "," + y0 + ")");
    }

    public static void main(String[] args) throws Exception {
        String sceneFile = "../../simple.txt";
        int largeur = 1024, hauteur = 1024;

        Client client = new Client(largeur, hauteur);
        ServiceClient stub = (ServiceClient) UnicastRemoteObject.exportObject(client, 0);

        Registry reg = LocateRegistry.getRegistry("localhost", 1099);
        ServiceCalcul svc = (ServiceCalcul) reg.lookup("ServiceCalcul");

        svc.demanderCalcul(stub, sceneFile, largeur, hauteur);
        System.out.println("Demande envoyée.");
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }
}
