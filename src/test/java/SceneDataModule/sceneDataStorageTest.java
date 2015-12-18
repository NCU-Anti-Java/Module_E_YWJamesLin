package SceneDataModule;

/**
 * Created by User on 2015/12/17.
 */

import Prototype_E.SceneDataModule.sceneDataStorage;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class sceneDataStorageTest {
    public sceneDataStorage scds;

    @Rule
    public ExpectedException expEx = ExpectedException.none();

    @Before
    public void Init () {
        scds = new sceneDataStorage("/maps/MainMap.txt", "/images/", ".jpg", "imageData.txt");
    }


    @After
    public void clear () {
        scds = null;
    }

    @Test
    public void loadImage_TestNoImageDataFile () throws Exception {
        expEx.expect(NullPointerException.class);

        scds.setImageDataFileName("yee.txt");
        scds.loadAll();
    }

    @Test
    public void loadImage_TestImageParTooFew () throws Exception {
        expEx.expect(NoSuchElementException.class);

        scds.setImageDataFileName("wData.txt");
        scds.loadImage();
    }

    @Test
    public void loadImage_TestCorrectFile () throws Exception {
        scds.loadImage();
        assertEquals(5, scds.getImageArrSize());
        assertEquals(100, scds.getMapBlockSize());
        assertTrue(scds.ImageLoaded());
    }


    @Test
    public void loadMap_TestWithoutLoadImage () throws Exception {
        expEx.expect (NullPointerException.class);
        expEx.expectMessage("Image should be preloaded.");

        scds.loadMap();
    }

    @Test
    public void loadMap_TestErrorFile () throws Exception {
        expEx.expect(NullPointerException.class);
        scds.setMapPath("/maps/yee.txt");
        scds.loadAll();
    }

    @Test
    public void loadMap_TestCorrectFileAndData () throws Exception {
        scds.setMapPath("/maps/Map1.txt");
        scds.loadAll();

        assertEquals(3, scds.getMapCol());
        assertEquals(4, scds.getMapRow());
        for (int i = 0, j; i < 4; ++ i) {
            for (j = 0; j < 3; ++ j) {
                assertEquals((j + 3 * i) % 5 + 1, scds.getImageType(i, j));
            }
        }
    }

    //this tests with index less than 0
    @Test
    public void loadMap_TestInvalidIndex () throws Exception {
        expEx.expect(IndexOutOfBoundsException.class);
        expEx.expectMessage("Index is too small");

        scds.setMapPath("/maps/Map2.txt");
        scds.loadAll();
    }

    //this tests with image index out of range
    @Test
    public void loadMap_TestInvalidImageIndex () throws Exception {
        expEx.expect(IndexOutOfBoundsException.class);
        expEx.expectMessage("Not such image index found.");

        scds.setMapPath("/maps/Map3.txt");
        scds.loadAll();
    }

    @Test
    public void loadMap_TestInvalidIndexN () throws Exception {
        expEx.expect(NoSuchElementException.class);

        scds.setMapPath("/maps/Map4.txt");
        scds.loadAll();
    }
}
