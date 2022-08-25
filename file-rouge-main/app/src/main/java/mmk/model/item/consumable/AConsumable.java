package mmk.model.item.consumable;

import mmk.model.item.AItem;
import mmk.model.util.DBConnection;
import mmk.model.personnage.hero.Hero;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * représente des objets consomable
 */
public abstract class AConsumable extends AItem {

    public static final PotionPA POTION_PA = new PotionPA();
    public static final PotionCleanse POTION_CLEANSE = new PotionCleanse();
    public static final PotionVie POTION_VIE = new PotionVie();


    public AConsumable(int id) {
        this.setId(id);
        try {
            String query = "SELECT * FROM static_consumable where id = " + id;
            ResultSet res = DBConnection.executeSelect(query);
            res.next();
            this.setName(res.getString("name"));
            this.setIcon(res.getString("iconurl"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("probleme avec la requete");
        }
    }

    public abstract void consume(Hero hero);
}
