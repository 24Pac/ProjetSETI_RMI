import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import raytracer.Image;

public class ServiceCentral implements ServiceCalcul {
    private final List<ServiceNoeud> noeuds = new ArrayList<>();

    public synchronized void enregistrerNoeud(ServiceNoeud noeud) throws RemoteException {
        noeuds.add(noeud);
        System.out.println("Nœud enregistré.");
    }

    public void demanderCalcul(ServiceClient client, String sceneFile, int largeur, int hauteur) throws RemoteException {
        int nbNoeuds = noeuds.size();
        int hFrag = hauteur / nbNoeuds;

        for (int i = 0; i < nbNoeuds; i++) {
            final int idx = i;
            new Thread(() -> {
                try {
                    ServiceNoeud noeud = noeuds.get(idx);
                    int x0 = 0, y0 = idx * hFrag, l = largeur, h = (idx == nbNoeuds-1) ? hauteur - y0 : hFrag;
                    Image img = noeud.calculerFragment(x0, y0, l, h, sceneFile);
                    client.recevoirFragment(img, x0, y0);
                } catch (Exception e) { e.printStackTrace(); }
            }).start();
        }
    }

    public static void main(String[] args) throws Exception {
        ServiceCentral svc = new ServiceCentral();
        ServiceCalcul stub = (ServiceCalcul) UnicastRemoteObject.exportObject(svc, 0);
        Registry reg = LocateRegistry.createRegistry(1099);
        reg.rebind("ServiceCalcul", stub);
        System.out.println("Service central prêt.");
    }
}
