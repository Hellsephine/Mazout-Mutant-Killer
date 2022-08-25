package mmk.model.world;

import mmk.model.personnage.APersonnage;
import mmk.model.util.DBConnection;
import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void backgroundBoardToString_noVide() {
        Board board = new Board(3, 2);

        assertEquals("0,0,0,0,0,0", board.backgroundBoardToString());
    }

    @Test
    public void stringToBackgroundBoard_noVide() {
        Board board = new Board(10, 10);

        int[][] res = board.stringToBackgroundBoard("3,0,0,1,1,1", 3, 2);
        int[][] expected = {{3, 0, 0}, {1, 1, 1}};

        assertTrue(Arrays.deepEquals(expected, res));
    }

    @Test
    public void apersonnageBoardToString_noVide() {
        Board board = new Board(3, 2);

        assertEquals("0,0,0,0,0,0", board.apersonnageBoardToString());
    }

    @Test
    public void stringToAPersonnageBoard_noVide() {
        Board board = new Board(10, 10);

        APersonnage[][] res = board.stringToAPersonnageBoard("0,0,0,0,0,0", 3, 2);
        APersonnage[][] expected = {{null, null, null}, {null, null, null}};

        assertTrue(Arrays.deepEquals(expected, res));
    }

    @Test
    public void save_saved() {
        Board board = new Board(10, 5);

        assertDoesNotThrow(() -> {board.save();});
    }

    @Test
    public void save_withUpdate() {
        Board board = new Board(10, 5);

        board.save();

        assertDoesNotThrow(() -> {board.save();});
    }

    @Test
    public void load_loaded() {
        Board  board = new Board(10, 5);

        int index = board.save();

        Board board1 = new Board(index);

        assertEquals(board.backgroundBoardToString(), board1.backgroundBoardToString());
    }

    @Test
    public void addPersonnage_add_added() {
        Board board = new Board(10, 5);

    }


    @BeforeAll
    public static void init() {
        DBConnection.init();
    }

    @BeforeEach
    public void setUp() {
        DBConnection.beginTransaction();
    }

    @AfterEach
    public void tear_down() {
        DBConnection.rollBack();
    }

    @AfterAll
    public static void close() {
        DBConnection.close();
    }
}
