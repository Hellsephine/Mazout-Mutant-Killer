package mmk.model.item.deck.card;

import mmk.model.APersonnageImplementation;
import mmk.model.util.DBConnection;
import mmk.model.util.Vector2;
import mmk.model.world.Board;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrengthIfHurtCardTest {
    
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
    public void effect_effectMaxHp_Nothing() {
        APersonnageImplementation perso = new APersonnageImplementation();
        perso.getDeck().addCard(new StrenghtIfHurtCard());
        Board board = new Board(10, 2);
        board.addPersonnage(perso, new Vector2(1, 1));

        perso.getDeck().getCards()[0].effect(board);

        assertEquals(10, perso.getStrength());
    }

    @Test
    public void effect_effectLose80Hp() {
        APersonnageImplementation perso = new APersonnageImplementation();
        perso.getDeck().addCard(new StrenghtIfHurtCard());
        Board board = new Board(10, 2);
        board.addPersonnage(perso, new Vector2(1, 1));
        perso.removeHp(80);
        perso.getDeck().getCards()[0].effect(board);

        
        
        assertEquals(15, perso.getStrength());
    }
}
