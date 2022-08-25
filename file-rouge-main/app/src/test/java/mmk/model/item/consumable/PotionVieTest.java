package mmk.model.item.consumable;

import mmk.model.APersonnageImplementation;
import org.junit.jupiter.api.*;

import mmk.model.util.DBConnection;

import static org.junit.jupiter.api.Assertions.*;

public class PotionVieTest {
     
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
    public void save_savePotionVie() {
        PotionVie paul = new PotionVie();

        int id = paul.save();

        assertNotEquals(-1, id);
    }


    @Test
    public void consumePotionVieTest(){
        PotionVie p = new PotionVie();
        APersonnageImplementation perso = new APersonnageImplementation();
        perso.removeHp(10);
        p.consume(perso);
        assertEquals(perso.getMaxHp(), perso.getHp());
    }
}
