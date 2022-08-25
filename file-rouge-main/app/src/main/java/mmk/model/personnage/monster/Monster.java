package mmk.model.personnage.monster;

import mmk.model.action.EActionState;
import mmk.model.item.equipable.Armor;
import mmk.model.item.equipable.Weapon;
import mmk.model.personnage.APersonnage;
import mmk.model.util.ASavable;
import mmk.model.util.DBConnection;
import mmk.model.util.Vector2;
import mmk.model.world.Board;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * classe représetant un monstre
 */
public class Monster extends APersonnage {

    private int power;
    private int type;
    /**
     * constructeur de Monster
     * @param id le nom du monstre
     */
    public Monster(int id) {
        try {
            String query = "SELECT * FROM monster " +
                    "inner join personnage " +
                    "inner join static_personnage " +
                    "inner join weapon "+
                    "inner join armor "+
                    "on personnage.id = monster.id_personnage " +
                    "and personnage.id_personnage = static_personnage.id " +
                    "and personnage.id_personnage = weapon.id_personnage "+
                    "and personnage.id_personnage = armor.id_personnage "+
                    "where monster.id = " + id;
            ResultSet res = DBConnection.executeSelect(query);
            res.next();
            this.setId(id);
            this.setName(res.getString("name"));
            this.setPngURL(res.getString("iconurl"));
            this.setHp(res.getInt("hp"));
            this.setMaxHp(res.getInt("maxHp"));
            this.setStrength(res.getInt("strength"));
            String dir = res.getString("direction");
            String[] split = dir.split(",");
            this.setDirection(new Vector2(Integer.parseInt(split[0]), Integer.parseInt((split[1]))));
            this.setPersonnageState(res.getInt("personnageState"));
            this.setPa(res.getInt("pa"));
            this.setType(res.getInt("type"));
            this.setPower(res.getInt("power_id"));
            new Armor(res.getInt("armor.id"), this);
            new Weapon(res.getInt("weapon.id"), this);
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("probleme avec la requette");
        }
    }


    @Override
    public int save() {
        PreparedStatement stmt;
        String query;
        try{
            query = "UPDATE monster SET `power_id` = ? , `type`= ? WHERE id_personnage = " + this.getId();
            stmt = DBConnection.createPreperedStatment(query);
           
            stmt.setInt(1, getPower());
            stmt.setInt(2, getType());
            DBConnection.executeUpdate(stmt);

            query = "UPDATE personnage SET `pa`= ?, `maxhp`= ?, `hp`= ?, " +
                    "`strength`= ?, `direction`= ?, `personnageState`= ? where id = " + this.getId();
            stmt = DBConnection.createPreperedStatment(query);
            stmt.setInt(1, this.getPa());
            stmt.setInt(2, this.getMaxHp());
            stmt.setInt(3, this.getHp());
            stmt.setInt(4, this.getStrength());
            stmt.setString(5, this.getDirection().toString());
            stmt.setInt(6, this.getPersonnageState());
            DBConnection.executeUpdate(stmt);

        }catch(SQLException e){
            e.printStackTrace();
        }
        // Gestion de La save Weapon Armor
        if(this.getIEnchantmentArmor()!=null){
          ((ASavable)this.getIEnchantmentArmor()).save();
        }
        if(this.getIEnchantmentWeapon() !=null){
            ((ASavable)this.getIEnchantmentWeapon()).save();
        }
       

        return id;
    }

    public void IsMonsterDead(){
        if(this.isDead()){
            try{
                if(this.id <=0){
                    throw new Exception("Tentative de supprimer un monstre qui est pas en BDD");
                }
                PreparedStatement stmt = DBConnection.createPreperedStatment("DELETE FROM monster WHERE id=?");
                stmt.setInt(1, this.getId());
                if(DBConnection.executeUpdate(stmt) < 1)
                    throw new Exception("Aucune suppression faite");

                
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }


    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    /**
     * fait le choix des actionState pour un monstre
     */
    public void setActionState(Board board) {
        APersonnage target = this.getInRange(board);
        if(target != null){
            this.actionState[0] = EActionState.ATTACK;
        }else{
            this.actionState[0] = EActionState.MOVE.setArguments(0);
            //TODO changement pour toi Antoine (IA complexe)
        }
    }
    public int getPower() {
        return power;
    }
    public void setPower(int power) {
        this.power = power;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

}
