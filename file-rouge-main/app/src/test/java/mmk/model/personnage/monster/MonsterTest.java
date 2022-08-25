package mmk.model.personnage.monster;


import mmk.model.action.EActionState;
import mmk.model.util.DBConnection;
import mmk.model.util.Vector2;
import mmk.model.world.Board;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class MonsterTest {


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
        Monster enemy = new Monster(1);

        int id = enemy.save();

        assertNotEquals(-1, id);
    }

    @Test
    public void load_saveHeroBeforLoad() {
        Monster enemy = new Monster(1);

        int id = enemy.save();
        Monster enemy2 = new Monster(id);
        assertEquals(enemy, enemy2);
    }
    @Test
    public void IsMonsterDeadTest(){
        Monster ennemy = new Monster(1);
        ennemy.setHp(0);
        ennemy.IsMonsterDead();
        assertThrows(RuntimeException.class,  () -> {
            new Monster(1);
        });
        

    }

    @Test
    public void DoactionStateMove_MonsterTest(){
        Monster ennemy = new Monster(1);
        ennemy.setActionState(new Board(1));
        assertEquals(EActionState.MOVE,  ennemy.getActionState()[0]); 
    }
    
    @Test
    public void DoactionStateAttack_MonsterTest(){
        Monster ennemy = new Monster(1);
        Board board =new Board(1);
        board.move(board.getApersonnagePosition(ennemy), new Vector2(2, 1));
        ennemy.setActionState(board);
        assertEquals(EActionState.ATTACK, ennemy.getActionState()[0]);
    }

}
