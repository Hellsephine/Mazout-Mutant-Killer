package mmk.model.item.equipable.enchantment.weapon;

import mmk.model.item.equipable.enchantment.AEnchantment;
import mmk.model.item.equipable.enchantment.IEnchantment;
import mmk.model.personnage.APersonnage;

import mmk.model.personnage.EPersonnageState;
import mmk.model.util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * représente l'enchangement fireAspect, qui met en feu la personne touché (ajoute des dégat au coup donné)
 */
public class FireAspect extends AEnchantment {

    private static final int idEnchantment = 1;

    /**
     * constructeur de FireAspect
     * @param weapon l'arme à enchanter
     */
    public FireAspect(IEnchantment weapon) {
        super(weapon);
    }

    @Override
    public int use(APersonnage personnage, int degattheorique) {
        degattheorique = degattheorique + 5;
        personnage.addPersonnageState(EPersonnageState.BURN);
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
