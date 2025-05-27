import java.rmi.Remote;
import java.rmi.RemoteException;
import raytracer.Image;

public interface ServiceClient extends Remote {
    void recevoirFragment(Image img, int x0, int y0) throws RemoteException;
    int getLargeur() throws RemoteException;
    int getHauteur() throws RemoteException;
}
