package mmk.model.personnage.hero;

import mmk.model.item.consumable.AConsumable;
import mmk.model.util.DBConnection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


public class HeroTest {

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
    public void save_saveHero() {
        Hero paul = new Hero(1);

        int id = paul.save();

        assertNotEquals(-1, id);
    }

    @Test
    public void load_saveHeroBeforLoad() {
        Hero paul = new Hero(1);

        int id = paul.save();
        Hero paul2 = new Hero(id);


        assertTrue(paul.equals(paul2));
    }

    @Test
    public void loadConsumable() {
        Hero paul = new Hero(1);

        assertTrue(paul.getConsumables().size() > 0);
    }


    @Test
    public void saveConsumable() {
        Hero paul = new Hero(1);

        HashMap<AConsumable, Integer> map = paul.getConsumables();
        int nb = map.get(AConsumable.POTION_CLEANSE);

        paul.addConsumable(AConsumable.POTION_CLEANSE);

        paul.save();

        Hero paul2 = new Hero(1);

        assertEquals(nb+1, paul2.getConsumables().get(AConsumable.POTION_CLEANSE));
    }
}
