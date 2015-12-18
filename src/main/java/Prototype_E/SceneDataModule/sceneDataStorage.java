package Prototype_E.SceneDataModule;

/**
 * Created by User on 2015/12/17.
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class sceneDataStorage {
    private int mapBlockSize, maxImageTypes;
    private int mapW, mapH, mapRow, mapCol;

    private String mapPath, imagePath, imageSub, imageData;

    protected int[][] mapArr;
    private java.awt.Image[] imageArr;

    public sceneDataStorage (String mPath, String iPath, String iSub, String iData) {
        mapPath = mPath;
        imagePath = iPath;
        imageSub = iSub;
        imageData = iData;
    }

    public void loadAll () throws Exception {
        loadImage ();
        loadMap ();
    }

    public void loadMap () throws Exception {
        if (imageArr == null) { throw new NullPointerException("Image should be preloaded."); }

        int tmp;
        File file;
        Scanner in;

        file = new File (sceneDataStorage.class.getResource(mapPath).toURI());
        in = new Scanner (file);

        tmp = in.nextInt();
        if (tmp <= 0) { throw new IndexOutOfBoundsException ("Index is too small"); }
        mapRow = tmp;
        mapW = mapBlockSize * mapRow;

        tmp = in.nextInt();
        if (tmp <= 0) { throw new IndexOutOfBoundsException ("Index is too small"); }
        mapCol = tmp;
        mapH = mapBlockSize * mapCol;

        mapArr = new int[mapRow][mapCol];
        for (int i = 0, j; i < mapRow; ++ i) {
            for (j = 0; j < mapCol; ++ j) {
                tmp = in.nextInt();
                if (tmp < 1 || tmp > maxImageTypes) { throw new IndexOutOfBoundsException("Not such image index found."); }
                mapArr[i][j] = tmp;
            }
        }
    }

    public void loadImage () throws Exception {
        int imageBegin, imageEnd, tmp;
        String fname;
        File file;
        Scanner in;

        fname = imagePath + imageData;
        file = new File (sceneDataStorage.class.getResource(fname).toURI());
        in = new Scanner (file);

        imageBegin = in.nextInt();
        imageEnd = in.nextInt();
        mapBlockSize = in.nextInt();
        maxImageTypes = imageEnd - imageBegin + 1;
        in.close();

        imageArr = new Image[maxImageTypes];
        tmp = 0;
        for (int i = imageBegin; i <= imageEnd; ++ i) {
            fname = imagePath + Integer.toString(i) + imageSub;
            imageArr[tmp] = ImageIO.read (new File(sceneDataStorage.class.getResource(fname).toURI()));
            ++ tmp;
        }
    }

    public Image getImage (int y, int x) throws NullPointerException {
        if ( imageArr == null) {
            throw new NullPointerException ("Images are not loaded.");
        }
        return imageArr[mapArr[y][x] - 1];
    }

    public void setMapPath (String str) {
        mapPath = str;
    }

    public void setImagePath (String str) {
        imagePath = str;
    }

    public void setImageSub (String str) {
        imageSub = str;
    }
    public void setImageDataFileName (String str) {
        imageData = str;
    }

    public int getMaxImageTypes () {
        return maxImageTypes;
    }

    public int getMapBlockSize () {
        return mapBlockSize;
    }

    public Image getSampleImage () {
        return imageArr[0];
    }

    public boolean ImageLoaded () {
        if (imageArr != null) { return true; }
        else { return false; }
    }

    public int getImageArrSize () {
        return imageArr.length;
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

    public int getMapW  () {
        return mapW;
    }

    public int getMapH  () {
        return mapH;
    }

}
