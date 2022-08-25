package mmk.model.personnage.hero;

import mmk.model.item.deck.Deck;
import mmk.model.item.equipable.Armor;
import mmk.model.item.equipable.Weapon;
import mmk.model.item.consumable.AConsumable;
import mmk.model.personnage.APersonnage;
import mmk.model.util.ASavable;
import mmk.model.util.DBConnection;
import mmk.model.util.Manager;
import mmk.model.util.Vector2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


/**
 * classe représentant un hero
 */
public class Hero extends APersonnage {

    //#region attributs
    protected Deck deck;
    private HashMap<AConsumable, Integer> consumables;
    //#endregion


    public Hero(int id) {
        this.deck = new Deck(id);
        this.consumables = new HashMap<>();

        try {
            String query = "SELECT * FROM hero " +
                    "inner join personnage " +
                    "inner join static_personnage " +
                    "inner join weapon "+
                    "inner join armor "+
                    "on personnage.id = hero.id_personnage " +
                    "and personnage.id_personnage = static_personnage.id " +
                    "and personnage.id_personnage = weapon.id_personnage "+
                    "and personnage.id_personnage = armor.id_personnage "+
                    "where hero.id = " + id;
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
            new Armor(res.getInt("armor.id"), this);
            new Weapon(res.getInt("weapon.id"), this);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("probleme avec la requette");
        }

        this.loadConsumables(id);
    }

    public Hero() {}

    /**
     * consomme un consommable
     * @param id_consomable l'id du consommable
     */
    public void consume(int id_consomable) {
        AConsumable consumable = Manager.createConsumable(id_consomable);
        if (this.consumables.containsKey(consumable) && this.consumables.get(consumable)>0) {
            consumable.consume(this);
            removeConsumable(consumable);
        }
    }

    @Override
    public int save() {
        PreparedStatement stmt;
        String query;
        try{
            query = "UPDATE hero SET `deck` = ? WHERE id_personnage = " + this.getId();
            stmt = DBConnection.createPreperedStatment(query);
            this.deck.setId(this.deck.save());
            stmt.setInt(1, this.deck.getId());
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

        // save consumable
        this.saveConsumables();


        // Gestion de La save Weapon Armor
        if(this.getIEnchantmentArmor()!=null){
            ((ASavable) this.getIEnchantmentArmor()).save();

          }
          if(this.getIEnchantmentWeapon() !=null){
              ((ASavable) this.getIEnchantmentWeapon()).save();

          }
        return id;
    }

    private void saveConsumables() {

        try {
            String query = "DELETE FROM consumable WHERE id_personnage = " + this.getId();
            DBConnection.executeUpdate(DBConnection.createPreperedStatment(query));

            for (AConsumable consumable : this.consumables.keySet()) {
                // TODO Antoine faire une stringBuilder pour faire qu'une requete
                query = "INSERT INTO consumable (id_consumable, amount, id_personnage) VALUES (?,?,?)";
                PreparedStatement statement = DBConnection.createPreperedStatment(query);

                statement.setInt(1, consumable.getId());
                statement.setInt(2, this.consumables.get(consumable));
                statement.setInt(3, this.getId());

                DBConnection.executeInsert(statement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("probleme de requete");
        }
    }

    private void loadConsumables(int id) {
        try {
            String query = "SELECT * from consumable WHERE id_personnage = " + id;
            ResultSet res = DBConnection.executeSelect(query);
            while (res.next()) {
                for (int i=0;i<res.getInt("amount");i++)
                    this.addConsumable(Manager.createConsumable(res.getInt("id_consumable")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("probleme de requete");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Hero hero))
            return false;

        /*if (!hero.getDeck().equals(this.getDeck()))
            return false;
        if (!hero.getConsumables().equals(this.getConsumables()))
            return false;*/

        return super.equals(o);
    }


    //#region adder/remover
    protected void addConsumable(AConsumable consumable) {
        if (this.consumables.containsKey(consumable))
            this.consumables.put(consumable, this.consumables.get(consumable)+1);
        else
            this.consumables.put(consumable, 1);
    }

    protected void removeConsumable(AConsumable consumable) {
        this.consumables.put(consumable, this.consumables.get(consumable)-1);
    }

    protected void removeConsumable(int index) {
        this.getConsumables().remove(index);
    }
    //#endregion

    //#region getter

    public Deck getDeck() {
        return this.deck;
    }

    protected HashMap<AConsumable, Integer> getConsumables() {
        return consumables;
    }

    //endregion
}
