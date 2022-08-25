package mmk.model.item.deck.card;

import mmk.model.APersonnageImplementation;
import mmk.model.util.DBConnection;
import mmk.model.util.Vector2;
import mmk.model.world.Board;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrengthForHealingCardTest {
    
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
        perso.getDeck().addCard(new StrenghtForHealingCard());
        Board board = new Board(10, 2);
        board.addPersonnage(perso, new Vector2(1, 1));
        perso.addStrength(11);
        perso.removeHp(80);
        perso.getDeck().getCards()[0].effect(board);
        perso.getDeck().getCards()[0].effect(board);

        assertEquals(22, perso.getHp());
        assertEquals(21, perso.getStrength());
    }

}
