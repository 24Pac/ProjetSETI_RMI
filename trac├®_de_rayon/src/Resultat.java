import java.io.Serializable;
import raytracer.Image;

public class Resultat implements Serializable {
    public Image image;
    public int x0, y0;

    public Resultat(Image img, int x0, int y0) {
        this.image = img; this.x0 = x0; this.y0 = y0;
    }
}
