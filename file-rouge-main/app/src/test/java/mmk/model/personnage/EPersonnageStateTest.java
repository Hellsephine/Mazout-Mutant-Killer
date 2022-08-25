package mmk.model.personnage;

import mmk.model.personnage.hero.Hero;
import mmk.model.util.DBConnection;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EPersonnageStateTest {

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
    public void addPersonnageState_add1State() {
        Hero hero = new Hero(1);

        hero.addPersonnageState(EPersonnageState.BURN);
        assertEquals(EPersonnageState.BURN.value, hero.getPersonnageState());
    }

    @Test
    public void addPersonnageState_add2States() {
        Hero hero = new Hero(1);

        hero.addPersonnageState(EPersonnageState.BURN);
        hero.addPersonnageState(EPersonnageState.BLEEDING);

        assertTrue((hero.getPersonnageState() & EPersonnageState.BLEEDING.value) > 0);
    }

    @Test
    public void removePersonnageState() {
        Hero hero = new Hero(1);

        hero.addPersonnageState(EPersonnageState.BURN);
        hero.addPersonnageState(EPersonnageState.POISON);

        hero.removeAllPersonnageState();

        assertEquals(EPersonnageState.NEUTRAL.value, hero.getPersonnageState());
    }

    @Test void removePersonnageState_remove1State() {
        Hero hero = new Hero(1);

        hero.addPersonnageState(EPersonnageState.BURN);
        hero.addPersonnageState(EPersonnageState.POISON);

        hero.removePersonnageState(EPersonnageState.POISON);

        assertEquals(EPersonnageState.BURN.value, hero.getPersonnageState());
    }
}
