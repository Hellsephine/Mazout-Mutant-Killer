package mmk.model.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2Test {

    @Test
    public void getX_withConstructor_returnX() {
        Vector2 v = new Vector2(10, 5);

        assertEquals(10, v.getX());
    }


    @Test
    public void getY_withConstructor_returnY() {
        Vector2 v = new Vector2(10, 5);

        assertEquals(5, v.getY());
    }


    @Test
    public void getX_withEmptyConstructor_return0() {
        Vector2 v = new Vector2();

        assertEquals(0, v.getX());
    }


    @Test
    public void getY_withEmptyConstructor_return0() {
        Vector2 v = new Vector2();

        assertEquals(0, v.getY());
    }
}
