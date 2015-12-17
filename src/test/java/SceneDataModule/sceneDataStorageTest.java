package SceneDataModule;

/**
 * Created by User on 2015/12/17.
 */
import Prototype_E.SceneDataModule.sceneDataStorage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;
public class sceneDataStorageTest {
    public sceneDataStorage scre;

    @Rule
    public ExpectedException expEx = ExpectedException.none();

    @Before
    public void Init () {
        scre = new sceneDataStorage();
    }

    @Test
    public void loadMap_TestErrorFile () throws Exception {
        expEx.expect(NullPointerException.class);
        scre.loadMap("/yee.txt");
    }

    @Test
    public void loadMap_TestCorrectFileAndData () throws Exception {
        scre.loadMap("/maps/Map1.txt");
        assertEquals(3, scre.getMapCol());
        assertEquals(4, scre.getMapRow());
        for (int i = 0, j; i < 4; ++ i) {
            for (j = 0; j < 3; ++ j) {
                assertEquals((j + 3 * i) % 5 + 1, scre.getImageType(i, j));
            }
        }
    }

    //this tests with index less than 0
    @Test
    public void loadMap_TestInvalidIndex () throws Exception {
        expEx.expect(IndexOutOfBoundsException.class);
        scre.loadMap("/maps/Map2.txt");
    }

    //this tests with image index out of range
    @Test
    public void loadMap_TestInvalidImageIndex () throws Exception {
        expEx.expect(IndexOutOfBoundsException.class);
        scre.loadMap("/maps/Map3.txt");
    }

}
