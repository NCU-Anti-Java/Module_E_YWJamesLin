package Prototype_E.SceneRenderEngine;

import Prototype_E.SceneDataModule.sceneDataStorage;
import Prototype_E.DynamicObjectModule.dynamicObjectModule;

import java.awt.*;

/**
 * Created by User on 2015/12/17.
 */
public class sceneRenderEngine {
    private sceneDataStorage storage;
    private dynamicObjectModule dom;
    private Graphics2D grap;
    double viewW, viewH;

    public sceneRenderEngine (Graphics2D g, double x, double y) {
        this.grap = g;
        this.viewW = x;
        this.viewH = y;
    }

    public void renderScene () throws Exception {
        if(this.dom == null) { throw new NullPointerException("Error: No DOM Link Exist."); }
        if (viewW > storage.getMapX() || viewH > storage.getMapY()) { throw new Exception ("Error: View width or view height is bigger than the map."); }
        Point charXY = dom.getVirtualCharacterXY();
        if (charXY == null) { throw new NullPointerException ("Error: Character location is invalid."); }
        double x = charXY.getX();
        double y = charXY.getY();
        double lbx, lby, ubx, uby, begx, begy, bSize = storage.getMapBlockSize();

        lbx = x - viewW / 2 - (x - viewW / 2) % bSize;
        lby = y - viewH / 2 - (y - viewH / 2) % bSize;

        ubx = x + viewW / 2;
        if (ubx % bSize == 0) { ubx -= bSize; }
        else { ubx -= (x + viewW / 2) % bSize; }
        uby = y + viewH / 2;
        if (uby % bSize == 0) { uby -= bSize; }
        else { uby -= (y + viewH / 2) % bSize; }

        begx = lbx - (x - viewW / 2);
        begy = lby - (y - viewH / 2);
         int i, j, k, l, drawi, drawj;
        for (i = (int) lbx, k = (int) begx; i <= ubx; i += bSize, k += bSize) {
            for (j = (int) lby, l = (int) begy; j <= uby; j += bSize, l += bSize) {
                drawi = i / 100;
                drawj = j / 100;
                drawImage(grap, drawj, drawi, k, l);
                System.out.println("(" + i + "," + j + "),(" + k + "," + l + "),(" + drawi + "," + drawj + ")" );
            }
        }

    }

    public void drawImage (Graphics2D g, int drawj, int drawi, int k, int l) {
        g.drawImage (storage.getImage(drawj, drawi), k, l, null);
    }

    public void setSDM (sceneDataStorage outst) {
        this.storage = outst;
    }

    public void setDOM (dynamicObjectModule d) {
        this.dom = d;
    }

    public void setViewW (double in) {
        viewW = in;
    }

    public void setViewH (double in) {
        viewH = in;
    }
}
