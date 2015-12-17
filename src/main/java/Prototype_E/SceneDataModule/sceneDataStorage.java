package Prototype_E.SceneDataModule;

/**
 * Created by User on 2015/12/17.
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class sceneDataStorage {
    int imageN = 5;
    int mapX, mapY;
    int mapRow, mapCol;
    int mapBlockSize = 100;
    int tmp;

    private java.awt.Image[] imageArr;
    protected int[][] mapArr;

    public sceneDataStorage () {
        try {
            loadMap("/maps/MainMap.txt");
            loadImage("/images", ".jpg", 5);
        } catch (Exception e) {}
    }

    public void loadMap (String mapFile) throws Exception {
        File file = new File (sceneDataStorage.class.getResource(mapFile).toURI());
        Scanner in = new Scanner (file);
        tmp = in.nextInt();
        if (tmp <= 0) { throw new IndexOutOfBoundsException ("Index is too small"); }
        mapRow = tmp;
        mapX = mapBlockSize * mapRow;
        tmp = in.nextInt();
        if (tmp <= 0) { throw new IndexOutOfBoundsException ("Index is too small"); }
        mapCol = tmp;
        mapY = mapBlockSize * mapCol;

        mapArr = new int[mapRow][mapCol];
        for (int i = 0; i < mapRow; ++ i) {
            for (int j = 0; j < mapCol; ++ j) {
                tmp = in.nextInt();
                if (tmp < 1 || tmp > imageN) { throw new IndexOutOfBoundsException("Not such image index found."); }
                mapArr[i][j] = tmp;
            }
        }
    }

    public void loadImage (String base, String sub, int num) throws FileNotFoundException {
        String fname;
        imageArr = new Image[num];
        for (int i = 0; i < num; ++ i) {
            fname = base + Integer.toString(i + 1) + sub;
            try {
                imageArr[i] = ImageIO.read(new File(fname));
            } catch (IOException e) {}
        }
    }

    public Image getImage (int y, int x) {
        return imageArr[mapArr[y][x] - 1];
    }

    public int getImageType (int y, int x) {
        return mapArr[y][x];
    }

    public int getMapRow () {
        return mapRow;
    }

    public int getMapCol () {
        return mapCol;
    }

    public int getMapBlockSize () {
        return mapBlockSize;
    }

    public int getMapX  () {
        return mapX;
    }

    public int getMapY  () {
        return mapY;
    }
}
