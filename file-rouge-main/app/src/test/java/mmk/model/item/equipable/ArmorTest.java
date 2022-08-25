package mmk.model.item.equipable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import mmk.model.personnage.hero.Hero;
import org.junit.jupiter.api.*;

import mmk.model.util.DBConnection;

public class ArmorTest {
    
    @BeforeAll
    public static void setUp() {
        DBConnection.init();
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
    public void save_saveArmor() {
        Hero hero = new Hero(1);
        Armor pull = new Armor(1, hero);

        int id = pull.save();

        assertNotEquals(0, id);
    }

    @Test
    public void load_saveArmorBeforLoad() {
        Hero hero = new Hero(1);
        Armor paul = new Armor(1, hero);

        paul.save();

        Armor paul2 = new Armor(paul.getId(), hero);
        assertTrue(paul.equals(paul2));
    }
    @Test
    public void unequipArmorTest(){
        Hero h = new Hero(1);
        Armor pull =new Armor(1, h);

        pull.unequip(h);

        assertTrue(h.getArmor().equals(new Armor(1, h)));
    }
    @Test 
    public void equipArmorTest(){
        Hero h = new Hero(1);
        h.setId(1);
        Armor pull = new Armor(1,h);
        pull.equip(h);
        assertTrue(h.getArmor().equals(pull));
    }
}
