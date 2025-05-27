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
        System.out.println("Noeud enregistré.");
    }

    public void demanderCalcul(ServiceClient client, String sceneFile, int largeur, int hauteur) throws RemoteException {
        int nbNoeuds = noeuds.size();
        if (nbNoeuds == 0) {
            System.out.println("Aucun noeud disponible !");
            return;
        }

        int hFrag = hauteur / nbNoeuds;
        int reste = hauteur%nbNoeuds;

        for (int i = 0; i < nbNoeuds; i++) {
            ServiceNoeud noeud = noeuds.get(i);
            int y0 = i * hFrag;
            int h = (i == nbNoeuds - 1) ? (hFrag + reste) : hFrag;

            new Thread(() -> {
                try {
                    Image img = noeud.calculerFragment(0, y0, largeur, h, sceneFile, client.getLargeur(), client.getHauteur());
                    client.recevoirFragment(img, 0, y0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
