package mmk.model.item.equipable;

import mmk.model.APersonnageImplementation;
import org.junit.jupiter.api.*;

import mmk.model.personnage.hero.Hero;
import mmk.model.util.DBConnection;

import static org.junit.jupiter.api.Assertions.*;

public class WeaponTest {
    
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
    public void save_saveWeapon() {
        Hero hero = new Hero(1);
        Weapon w = new Weapon(1, hero);

        int id = w.save();

        assertNotEquals(-1, id);
    }

    @Test
    public void load_saveWeaponBeforLoad() {
        Hero hero = new Hero(1);
        Weapon w = new Weapon(1, hero);

        int id = w.save();
        Weapon w2 = new Weapon(id, hero);
        assertTrue(w.equals(w2));
    }


    @Test
    public void unequipWeaponTest(){
        APersonnageImplementation perso = new APersonnageImplementation();

        perso.getWeapon().unequip(perso);

        assertEquals(new Weapon(1, perso), perso.getWeapon());
    }

    @Test 
    public void equipWeaponTest(){
        Hero h = new Hero(1);
        Weapon coton =new Weapon(1, h);

        coton.equip(h);
        assertTrue(h.getWeapon().equals(coton));
    }
}
