package mmk.model.personnage;

import mmk.model.APersonnageImplementation;
import mmk.model.item.equipable.Weapon;
import mmk.model.personnage.monster.Monster;

import static org.junit.jupiter.api.Assertions.*;

import mmk.model.util.DBConnection;
import mmk.model.util.Vector2;
import mmk.model.world.Board;
import org.junit.jupiter.api.*;


public class APersonnageTest {

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
    public void isDead_notDead_returnsFalse() {
        APersonnageImplementation personnage = new APersonnageImplementation();

        assertFalse(personnage.isDead());
    }

    @Test
    public void isDead_dead_returnsTrue() {
        APersonnageImplementation personnage = new APersonnageImplementation();

        personnage.removeHp(100);

        assertTrue(personnage.isDead());
    }

    @Test
    public void takeDamage_notBlocking_removeHP() {
        APersonnageImplementation personnageImplementation = new APersonnageImplementation();

        personnageImplementation.removeHp(5);

        assertEquals(95, personnageImplementation.getHp());
    }

    @Test
    public void getInRange_inRange_returnsEnemy() {
        APersonnageImplementation personnage = new APersonnageImplementation();
        personnage.setDirection(new Vector2(1, 0));

        Monster enemy = new Monster(1);
        enemy.setDirection(new Vector2(-1, 0));

        Board board = new Board(5, 5);
        board.addPersonnage(personnage, new Vector2(1, 0));
        board.addPersonnage(enemy, new Vector2(2, 0));

        assertSame(enemy, personnage.getInRange(board));
    }


    @Test
    public void getInRange_notInRange_returnsNull() {
        APersonnageImplementation personnage = new APersonnageImplementation();
        personnage.setDirection(new Vector2(1, 0));

        Board board = new Board(5, 5);
        board.addPersonnage(personnage, new Vector2(1, 0));

        assertNull(personnage.getInRange(board));
    }

    @Test
    public void attack_hitEnemy_EnemyLostPv() {
        APersonnageImplementation personnage = new APersonnageImplementation();
        personnage.setDirection(new Vector2(1, 0));

        APersonnageImplementation enemy = new APersonnageImplementation();

        Board board = new Board(5, 5);
        board.addPersonnage(personnage, new Vector2(1, 0));
        board.addPersonnage(enemy, new Vector2(2, 0));

        personnage.attack(board);
       

        assertEquals(85, enemy.getHp());
    }

    @Test
    public void attack_noEnemyInRange_Nothing() {
        APersonnageImplementation personnage = new APersonnageImplementation();
        new Weapon(1, personnage);
        Board board = new Board(5, 5);
        board.addPersonnage(personnage, new Vector2(1, 0));

        assertDoesNotThrow(() -> personnage.attack(board));
    }
}
