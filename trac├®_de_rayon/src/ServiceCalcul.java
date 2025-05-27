import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceCalcul extends Remote {
    void demanderCalcul(ServiceClient client, String sceneFile, int largeur, int hauteur) throws RemoteException;
    void enregistrerNoeud(ServiceNoeud noeud) throws RemoteException;
}