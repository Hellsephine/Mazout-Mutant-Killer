package mmk.model.item.equipable.enchantment.armor;

import mmk.model.item.equipable.enchantment.AEnchantment;
import mmk.model.item.equipable.enchantment.IEnchantment;
import mmk.model.personnage.APersonnage;
import mmk.model.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * représente l'enchantement Thorns, renvoie une partie des degats subi
 */
public class Thorns extends AEnchantment {

    public static final int idEnchantment = 101;

    /**
     * constructeur de Thorns
     * @param armor l'armure à enchanter
     */
    public Thorns(IEnchantment armor) {
        super(armor);
    }

    @Override
    public int use(APersonnage personnage, int degattheorique) {
        if(degattheorique != 0){
            personnage.removeHp((int) (degattheorique*(10/100.0f)));
        }
        return super.use(personnage, degattheorique);
    }

    @Override
    public int save() {
        int id = super.save();

        ResultSet rs = DBConnection.executeSelect("SELECT COUNT(*) " +
                "FROM weapon_enchantment " +
                "where weapon_id = "+id+" "+
                "and enchantment_id = "+this.idEnchantment);

        try {
            rs.next();
            return id;
        } catch (SQLException error) {
            try {
                String query = "INSERT INTO weapon_enchantment (weapon_id, enchantment_id) VALUES (?,?)";
                PreparedStatement p = DBConnection.createPreperedStatment(query);
                p.setInt(1, id);
                p.setInt(2, this.idEnchantment);

                DBConnection.executeInsert(p);
            } catch (SQLException err) {
                err.printStackTrace();
                throw new RuntimeException("pb requette");
            }
        }

        return id;
    }

}
