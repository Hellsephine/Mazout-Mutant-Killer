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
 * classe représentant une arme
 */
public class Weapon extends AItem implements IEquipable, IEnchantment {

    //#region attribut
    protected int damage;
    protected double cc;
    protected int range;
    //#endregion

    /**
     * le constructeur pour Weapon
     * @param id l'id de la base static
     * @param personnage le personnage associé a l'arme
     */
    public Weapon(int id, APersonnage personnage){
        try {
            String query = "SELECT * from weapon " +
                    "inner join static_weapon " +
                    "on weapon.id_weapon = static_weapon.id " +
                    "where weapon.id = " + id;
            ResultSet rs = DBConnection.executeSelect(query);
            rs.next();
            this.setId(id);
            this.setDamage(rs.getInt("damage"));
            this.setCc(rs.getInt("cc") / 100.0f);
            this.setName(rs.getString("name"));
            this.setIcon(rs.getString("iconurl"));
            this.setRarity(rs.getInt("rarity"));
            this.setIdPersonnage(rs.getInt("id_personnage"));
            this.setRange(rs.getInt("weapon.range"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("probleme avec la requette");
        }

        // load des enchantments
        IEnchantment equipment = this;
        try {
            String query = "SELECT enchantment_id FROM weapon_enchantment where weapon_id = " + id;
            ResultSet rs = DBConnection.executeSelect(query);
            while (rs.next()) {
                int enchantment_id = rs.getInt("enchantment_id");
                equipment = Manager.setDecoration(enchantment_id, equipment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("probleme avec la requette");
        }

        personnage.setWeapon(equipment);
    }

    protected Weapon() {

    }

    @Override
    public void equip(APersonnage target) {
        target.setWeapon(this);
        this.setIdPersonnage(target.getId());
    }

    @Override
    public void unequip(APersonnage target) {
        target.setWeapon(new Weapon(1, target));
    }

    @Override
    public int use(APersonnage personnage, int degattheorique) {
        return degattheorique+damage;
    }

    @Override
    public int save() {
        PreparedStatement stmt;
        String query;
       if(id > 0){
           query = "UPDATE weapon " +
                   "SET `damage` = ?, " +
                   "`cc` = ?, " +
                   "`rarity` = ?, " +
                   "`id_personnage` = ?, " +
                   "`range` = ? " +
                   "WHERE `id` = " + id;
           stmt =DBConnection.createPreperedStatment(query);
       }else{
           query = "INSERT INTO weapon " +
                   "(`damage`, `cc`, `rarity`, `id_personnage`, `range`) " +
                   "VALUES(?,?,?,?,?)";
           stmt =DBConnection.createPreperedStatment(query);
       }
        try{
            stmt.setInt(1,this.getDamage());
            stmt.setInt(2,(int) (this.getCc()*100));
            stmt.setInt(3, this.rarity);
            stmt.setInt(4, this.getIdPersonnage());
            stmt.setInt(5, this.getRange());
            if(id == 0){
                this.id = DBConnection.executeInsert(stmt);
            }else{
                DBConnection.executeUpdate(stmt); 
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Weapon w))
            return false;


        if (this.getCc() != w.getCc())
            return false;
        if (this.getDamage() != w.getDamage())
            return false;
        if (this.getRange() != w.getRange())
            return false;
        if (!this.getName().equals(w.getName()))
            return false;
        if (!this.getIconURL().equals(w.getIconURL()))
            return false;

        return true;
    }

    //#region Getter et Setter
    public int getRange() {
        return range;
    }
    public void setRange(int range) {
        this.range = range;
    }

    protected int getDamage() {
        return damage;
    }

    protected void setDamage(int damage) {
        this.damage = damage;
    }

    protected double getCc() {
        return cc;
    }

    protected void setCc(double cc) {
        this.cc = cc;
    }

    //#endregion
}
