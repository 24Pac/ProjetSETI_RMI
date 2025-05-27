import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import raytracer.Scene;
import raytracer.Image;

public class NoeudCalcul implements ServiceNoeud {
    public Image calculerFragment(int x0, int y0, int largeur, int hauteur, String sceneFile,int largeurTotale,int hauteurTotale) throws RemoteException {
        Scene scene = new Scene(sceneFile, largeurTotale, hauteurTotale);
        return scene.compute(x0, y0, largeur, hauteur);
    }

    public static void main(String[] args) throws Exception {
        NoeudCalcul n = new NoeudCalcul();
        ServiceNoeud stub = (ServiceNoeud) UnicastRemoteObject.exportObject(n, 0);
        Registry reg = LocateRegistry.getRegistry("localhost", 1099);
        ServiceCalcul svc = (ServiceCalcul) reg.lookup("ServiceCalcul");
        svc.enregistrerNoeud(stub);
        System.out.println("Nœud connecté.");
    }
}