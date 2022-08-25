package mmk.model.item.equipable.enchantment.armor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import mmk.model.item.equipable.enchantment.IEnchantment;
import org.junit.jupiter.api.*;

import mmk.model.item.equipable.Armor;
import mmk.model.item.equipable.Weapon;
import mmk.model.personnage.hero.Hero;
import mmk.model.util.DBConnection;

public class ThornsTest {
    
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
    public void testSave_Thors(){
        Hero h = new Hero(1);
        Armor pull = new Armor(1, h);
        Thorns t = new Thorns(pull);
        int res =t.save();
        
        assertTrue(res>0);
    }
    @Test
    public void testLoad_Thors(){
        Hero h = new Hero(1);
        new Armor(3, h);

        IEnchantment a = h.getIEnchantmentArmor();
        assertTrue(a instanceof Thorns);
    }
    @Test
    public void testUse_Thors(){
        Hero h = new Hero(1);
        Armor pull = new Armor(1, h);
        Thorns t = new Thorns(pull);
        Weapon baton = new Weapon(1,h);
        Hero h1 = new Hero(1);
        new Armor(1, h1);
        h.setWeapon(t);
        new Armor(1, h);
        new Weapon(1, h);
        h1.setArmor(t);

        h1.takeDomage(h, h.getIEnchantmentWeapon().use(h1, 0));
               
        assertEquals(98, h.getHp());
    }
    // premier test design partern
    @Test
    public void testUse_DeuxThors(){
        Hero h = new Hero(1);
        Armor pull = new Armor(1, h);
        Thorns t = new Thorns(new Thorns(pull));
        Weapon baton = new Weapon(1, h);

        Hero h1 = new Hero(2);
        new Armor(1, h1);
        new Weapon(1, h1);

        h1.takeDomage(h, h.getIEnchantmentWeapon().use(h1, 0));
               
        assertEquals(h1.getMaxHp()-10, h1.getHp());
    }

}
