package mmk.model.util;

/**
 * classe représentant une classe dont des données peuvent êtres sauvegarder
 */
public abstract class ASavable {

    protected int id = 0;

    /**
     * permet de sauvegarder des données en bdd
     * @return l'id de la ligne créée, -1 si probleme
     */
    public abstract int save();

    /**
     * permet de changer des données depuis la bdd
     * @param id l'id de la ligne de la bdd
     */
    //public abstract void load(int id);

}
