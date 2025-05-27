import raytracer.Image;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface ServiceNoeud extends Remote {
    Image calculerFragment(int x0, int y0, int largeur, int hauteur,String sceneFile) throws RemoteException;
}
