package mmk.model.item.consumable;

import mmk.model.APersonnageImplementation;
import org.junit.jupiter.api.*;

import mmk.model.personnage.EPersonnageState;
import mmk.model.util.DBConnection;

import static org.junit.jupiter.api.Assertions.*;

public class PotionCleanseTest {
      
    @BeforeAll
    public static void setUp() {
        DBConnection.init();
        DBConnection.beginTransaction();
    }

    @BeforeEach
    public void beginTransaction() {
        DBConnection.beginTransaction();
    }

    @AfterEach
    public void rollBack() {
        DBConnection.rollBack();
    }

    @AfterAll
    public static void tearDown(){
        DBConnection.close();
    }

    @Test
    public void save_savePotionCleanse() {
        PotionCleanse paul = new PotionCleanse();

        int id = paul.save();

        assertNotEquals(-1, id);
    }


    @Test
    public void consumePotionCleanseTest(){
        APersonnageImplementation perso = new APersonnageImplementation();
        PotionCleanse p = new PotionCleanse();
        perso.addPersonnageState(EPersonnageState.POISON);
        p.consume(perso);
        assertEquals(0, perso.getPersonnageState());
    }

    
}
