package mmk.model.item.equipable.enchantment.weapon;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import mmk.model.item.equipable.enchantment.IEnchantment;
import org.junit.jupiter.api.*;

import mmk.model.item.equipable.Armor;
import mmk.model.item.equipable.Weapon;
import mmk.model.personnage.EPersonnageState;
import mmk.model.personnage.hero.Hero;
import mmk.model.util.DBConnection;

public class FireAspectTest {
    
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
    public void testSave_FireAspect(){
        Hero h = new Hero(1);
        Weapon baton = new Weapon(1, h);
        FireAspect t = new FireAspect(baton);
        int res =t.save();
        
        assertTrue(res>0);
    }
    @Test
    public void testLoad_FireAspect(){
        Hero h = new Hero(1);
        new Weapon(1, h);

        IEnchantment a = h.getIEnchantmentWeapon();
        assertTrue(a instanceof FireAspect);
    }
    @Test
    public void testUse_FireAspect(){
        Hero h = new Hero(1);
        Weapon baton = new Weapon(1, h);
        FireAspect t = new FireAspect(baton);
        h.setWeapon(t);

        new Armor(1, h);
        Hero h1 = new Hero(1);
        new Armor(1, h1);
        new Weapon(1, h);

        h1.takeDomage(h, h.getIEnchantmentWeapon().use(h1, 0));

        assertNotEquals(h.getPersonnageState(), EPersonnageState.BURN);

    }
    // premier test design partern
    @Test
    public void testUse_DeuxFireAspect(){
        Hero h = new Hero(1);
        Weapon baton = new Weapon(1,h);
        FireAspect t = new FireAspect(baton);



        Hero h1 = new Hero(1);
        new Armor(1, h1);

        h1.takeDomage(h, h.getIEnchantmentWeapon().use(h1, 0));
    }
}
