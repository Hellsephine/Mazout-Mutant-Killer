package mmk.model.util;

/**
 * classe représentant des coordonnées en deux dimensions
 */
public class Vector2 {

    //#region attributs
    protected int x;
    protected int y;
    //#endregion

    /**
     * constructeur de Vector2
     * @param x la coordonnée x du vecteur
     * @param y la coordonnée y du vecteur
     */
    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * constructeur de Vector2(0, 0)
     */
    public Vector2(){
        this.x = 0;
        this.y = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vector2 v)
            return (this.x == v.x && this.y == v.y);
        return false;
    }
    @Override
    public String toString(){
        return x+","+y;
    }
    //#region getter/setter
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    protected void setY(int y) {
        this.y = y;
    }

    public Vector2 add(Vector2 v) {
        return new Vector2(this.getX()+v.getX(), this.getY()+v.getY());
    }
    //#endregion
}
