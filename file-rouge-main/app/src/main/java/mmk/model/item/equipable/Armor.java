package mmk.model.item.equipable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mmk.model.item.AItem;

import mmk.model.item.equipable.enchantment.IEnchantment;
import mmk.model.personnage.APersonnage;
import mmk.model.util.DBConnection;
import mmk.model.util.Manager;

/**
 * classe représentant une armure
 */
public class Armor extends AItem implements IEquipable, IEnchantment {

    //#region attributs
    protected int defence;
    //#endregion

    //#region Constructeur
    /**
     * constructeur de la classe Armor
     * @param id l'id de l'armure dans la base static
     * @param personnage le personnage qui a l'arme
     */
    public Armor(int id, APersonnage personnage) {
        try {
            String query = "SELECT * FROM armor " +
                    "inner join static_armor " +
                    "on armor.id_armor = static_armor.id " +
                    "WHERE armor.id = " + id;
            ResultSet res = DBConnection.executeSelect(query);
            res.next();
            this.setId(id);
            this.setDefence(res.getInt("defence"));
            this.setName(res.getString("name"));
            this.setIcon(res.getString("iconurl"));
            this.setIdPersonnage(res.getInt("id_personnage"));
            this.setRarity(res.getInt("rarity"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("probleme avec la requette");
        }

        // load des enchantments
        IEnchantment equipment = this;
        try {
            String query = "SELECT enchantment_id FROM armor_enchantment where armor_id = " + id;
            ResultSet rs = DBConnection.executeSelect(query);
            while (rs.next()) {
                int enchantment_id = rs.getInt("enchantment_id");
                equipment = Manager.setDecoration(enchantment_id, equipment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("probleme avec la requette");
        }

        personnage.setArmor(equipment);
    }

    public Armor() {

    }


    //#region Getter/Setter
    public int getDefence() {
        return defence;
    }
    public void setDefence(int defence) {
        this.defence = defence;
    }
    //#endregion

      /**
     * permet d'équiper cette Armor à un APersonnage
     * @param target le personnage qui équip this
     */
    @Override
    public void equip(APersonnage target) {
        target.setArmor(this);
        this.setIdPersonnage(target.getId());
        
    }

    /**
     * permet déequiper cette Armor à un APersonnage
     * @param target le personnage qui retire this  
     * Définit l'Armor avec AUCUNE_ARMOR    
     */
    @Override
    public void unequip(APersonnage target) {
        target.setArmor(new Armor(1, target));
    }


    @Override
    public int use(APersonnage personnage, int degattheorique) {
        return degattheorique- defence;
    }

    //#region  Partie Sql -> save + load
    @Override
    public int save() {
        PreparedStatement stmt;
        String query;
        if(id>0){
            query = "UPDATE armor SET " +
                    "`defence` = ?, " +
                    "`rarity`= ?, " +
                    "`id_personnage`= ? " +
                    "WHERE id = " + id;
           stmt =DBConnection.createPreperedStatment(query);
        }else {
            query = "INSERT INTO armor " +
                    "(`defence`, `rarity`, `id_personnage`) " +
                    "VALUES(?,?,?)";
           stmt =DBConnection.createPreperedStatment(query);
        }
            try{

                stmt.setInt(1,this.defence);
                stmt.setInt(2, this.rarity);
                stmt.setInt(3, this.idPersonnage);
                if(id==0){
                    id = DBConnection.executeInsert(stmt);
                }else {
                     DBConnection.executeUpdate(stmt);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }


        return this.id;
    }


    //#endregion
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Armor a))
            return false;


        if (this.getDefence() != a.getDefence())
            return false;
        if (!this.getIconURL().equals(a.getIconURL()))
            return false;
        if (!this.getName().equals(a.getName()))
            return false;

        return true;
    }
    
   
}
