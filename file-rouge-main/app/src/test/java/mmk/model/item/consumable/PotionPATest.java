package mmk.model.item.consumable;

import mmk.model.APersonnageImplementation;
import org.junit.jupiter.api.*;

import mmk.model.util.DBConnection;

import static org.junit.jupiter.api.Assertions.*;

public class PotionPATest {
    
    
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
    public void save_savePotionPA() {
        PotionPA paul = new PotionPA();

        int id = paul.save();

        assertNotEquals(-1, id);
    }

    @Test
    public void load_savePotionPABeforLoad() {
        PotionPA paul = new PotionPA();

        int id = paul.save();

        assertNotEquals(-1, id);
    }
    @Test
    public void consumeTestPotionPA(){
        PotionPA pa = new PotionPA();
        APersonnageImplementation perso = new APersonnageImplementation();
        pa.consume(perso);
        assertEquals(2, perso.getPa());
    }
    

}
