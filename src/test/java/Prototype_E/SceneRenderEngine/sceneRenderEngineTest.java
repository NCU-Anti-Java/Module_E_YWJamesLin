package Prototype_E.SceneRenderEngine;

/**
 * Created by User on 2015/12/17.
 */

import Prototype_E.DynamicObjectModule.dynamicObjectModule;
import Prototype_E.SceneDataModule.sceneDataStorage;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.*;
import java.awt.image.BufferedImage;

public class sceneRenderEngineTest {

    sceneRenderEngine scre;
    sceneDataStorage defStorage, storage;
    dynamicObjectModule defDom, dom;
    int viewW = 500;
    int viewH = 300;

    @Rule
    public ExpectedException expEx = ExpectedException.none();

    @Before
    public void Init () {
        BufferedImage viewCanvas = new BufferedImage(viewW, viewH, BufferedImage.TYPE_INT_RGB);
        scre = new sceneRenderEngine ((Graphics2D)viewCanvas.getGraphics(), viewW, viewH);

        defStorage = new sceneDataStorage("", "/images/", ".jpg", "imageData.txt") {
            @Override
            public void loadMap() {
                mapArr = new int[20][50];
                for (int i = 0; i < 20; ++i) {
                    for (int j = 0; j < 50; ++j) {
                        mapArr[i][j] = i % 5 + 1;
                    }
                }
            }
            public int getMapRow () {
                return 20;
            }
            public int getMapCol () {
                return 50;
            }
            public int getMapW () {
                return 5000;
            }
            public int getMapH () {
                return 2000;
            }
        };
        defDom = new dynamicObjectModule() {
            @Override
            public Point getVirtualCharacterXY () {
                return new Point (1500, 2000);
            }
        };
        storage = new sceneDataStorage("/maps/Map1.txt", "/images/", ".jpg", "imageData.txt");

        scre.setSDM(defStorage);
        scre.setDOM(defDom);
    }

    @Test
    public void renderScene_TestNoDOM () throws Exception {
        expEx.expect (NullPointerException.class);
        expEx.expectMessage("Error: No DOM Link Exist.");

        scre.setDOM(null);
        scre.renderScene();
    }

    @Test
    public void renderScene_TestInvalidLocation () throws Exception {
        expEx.expect(NullPointerException.class);
        expEx.expectMessage("Error: Character location is invalid.");

        defStorage.loadAll();
        scre.setDOM(new dynamicObjectModule () {
            @Override
            public Point getVirtualCharacterXY () {
                return null;
            }
        });
        scre.renderScene();
    }

    @Test
    public void renderScene_TestViewBiggerThanMap () throws Exception {
        expEx.expect(Exception.class);
        expEx.expectMessage("Error: View width or view height is bigger than the map.");

        scre.setViewH(2000);
        scre.setViewW(2000);

        scre.setSDM(storage);
        storage.loadAll();
        scre.setDOM(new dynamicObjectModule() {
            @Override
            public Point getVirtualCharacterXY() {
                return new Point(150, 200);
            }
        });
        scre.renderScene();
    }

    @Test
    public void renderScene_TestViewOutOfRange1 () throws Exception {
        expEx.expect(IndexOutOfBoundsException.class);
        expEx.expectMessage("Error: View Window is out of bound.");

        scre.setSDM(defStorage);
        storage.loadAll();
        scre.setDOM(new dynamicObjectModule() {
            @Override
            public Point getVirtualCharacterXY() {
                return new Point(249, 150);
            }
        });
        scre.renderScene();
    }

    @Test
    public void renderScene_TestViewOutOfRange2 () throws Exception {
        expEx.expect(IndexOutOfBoundsException.class);
        expEx.expectMessage("Error: View Window is out of bound.");

        scre.setSDM(defStorage);
        storage.loadAll();
        scre.setDOM(new dynamicObjectModule() {
            @Override
            public Point getVirtualCharacterXY() {
                return new Point(250, 149);
            }
        });
        scre.renderScene();
    }
    @Test
    public void renderScene_TestViewOutOfRange3 () throws Exception {
        expEx.expect(IndexOutOfBoundsException.class);
        expEx.expectMessage("Error: View Window is out of bound.");

        scre.setSDM(defStorage);
        storage.loadAll();
        scre.setDOM(new dynamicObjectModule() {
            @Override
            public Point getVirtualCharacterXY() {
                return new Point(4751, 150);
            }
        });
        scre.renderScene();
    }
    @Test
    public void renderScene_TestViewOutOfRange4 () throws Exception {
        expEx.expect(IndexOutOfBoundsException.class);
        expEx.expectMessage("Error: View Window is out of bound.");

        scre.setSDM(defStorage);
        storage.loadAll();
        scre.setDOM(new dynamicObjectModule() {
            @Override
            public Point getVirtualCharacterXY() {
                return new Point(250, 1851);
            }
        });
        scre.renderScene();
    }

    @Test
    public void renderScene_TestViewOutOfRangeCompare () throws Exception {
        scre.setSDM(defStorage);
        storage.loadAll();
        scre.setDOM(new dynamicObjectModule() {
            @Override
            public Point getVirtualCharacterXY() {
                return new Point(250, 150);
            }
        });
        scre.renderScene();
    }
}
