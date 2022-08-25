package mmk.model.item.deck;

import mmk.model.item.deck.card.ACard;
import mmk.model.util.ASavable;
import mmk.model.util.DBConnection;
import mmk.model.util.Manager;
import mmk.model.world.Board;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * représente un deck de carte
 */
public class Deck extends ASavable {
    protected HashMap<ACard, Integer> deck;
    protected int id=0;
    protected int idPersonnage=0;

    public Deck(int id_hero){
        this.deck = new HashMap<>();
        this.idPersonnage = id_hero;
        try {
            String query = "SELECT * FROM deck WHERE id_hero = " + id_hero;
            ResultSet res = DBConnection.executeSelect(query);
            while (res.next()) {
                this.addCard(Manager.createCard(res.getInt("id_card")));
                this.id = res.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Probleme de requete");
        }
    }

    public Deck(){}

    public void check(Board board)
    {
        for (ACard c : this.getCards())
            c.effect(board);
    }

    public ACard[] getCards() {
        ArrayList<ACard> cards = new ArrayList<>();
        for (ACard card : this.deck.keySet()) {
            for (int i=0;i<this.deck.get(card);i++) {
                cards.add(card);
            }
        }
        return cards.toArray(new ACard[0]);
    }

    public void addCard(ACard card) {
        if (this.deck.containsKey(card))
            this.deck.put(card, this.deck.get(card)+1);
        else
            this.deck.put(card, 1);
    }
    

    @Override
    public int save() {
        try {
            String query = "DELETE FROM deck WHERE id = " + this.getId();
            DBConnection.executeUpdate(DBConnection.createPreperedStatment(query));

            for (ACard card : this.deck.keySet()) {
                // TODO Antoine faire une stringBuilder pour faire qu'une requete
                query = "INSERT INTO deck (id, id_card, amount, id_hero) VALUES (?,?,?,?)";
                PreparedStatement statement = DBConnection.createPreperedStatment(query);

                statement.setInt(1, this.getId());
                statement.setInt(2, card.getId());
                statement.setInt(3, this.deck.get(card));
                statement.setInt(4, this.idPersonnage);

                DBConnection.executeInsert(statement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("probleme de requete");
        }

        return this.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
