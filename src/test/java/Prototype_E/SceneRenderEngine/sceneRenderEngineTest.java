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
    int viewW = 500;
    int viewH = 300;
    int bSize = 100;

    @Rule
    public ExpectedException expEx = ExpectedException.none();

    @Before
    public void init () {
        BufferedImage viewCanvas = new BufferedImage(viewW, viewH, BufferedImage.TYPE_INT_RGB);
        scre = new sceneRenderEngine ((Graphics2D)viewCanvas.getGraphics(), viewW, viewH);
        scre.setSDM(new sceneDataStorage() {
            @Override
            public void loadMap(String mapFile) {
                mapArr = new int[20][50];
                for (int i = 0; i < 20; ++ i) {
                    for (int j = 0; j < 50; ++ j) {
                        mapArr[i][j] = i % 5 + 1;
                    }
                }
            }
        });
    }

    @Test
    public void renderScene_TestViewBiggerThanMap () throws Exception {
        expEx.expect(Exception.class);
        expEx.expectMessage("Error: View width or view height is bigger than the map.");

        scre.setViewH(2000);
        scre.setViewW(2000);
        scre.setSDM(new sceneDataStorage());
        scre.renderScene();
    }

    @Test
    public void renderScene_TestInvalidLocation () throws Exception {
        expEx.expect(NullPointerException.class);
        expEx.expectMessage("Error: Character location is invalid.");

        scre.setDOM(new dynamicObjectModule () {
            @Override
            public Point getVirtualCharacterXY () {
                return null;
            }
        });

        scre.renderScene();
    }

    @Test
    public void renderScene_TestNoDOM () throws Exception {
        expEx.expect (NullPointerException.class);
        expEx.expectMessage("Error: No DOM Link Exist.");

        scre.setDOM(null);
        scre.renderScene();
    }
}
