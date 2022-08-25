package mmk.model.item.deck.card;

import mmk.model.item.AItem;
import mmk.model.util.DBConnection;
import mmk.model.world.Board;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ACard extends AItem {
    protected int nbTours = 0;

    public ACard(int id) {
        this.id = id;
        try {
            String query = "SELECT * FROM static_card WHERE id = " + id;
            ResultSet res = DBConnection.executeSelect(query);
            res.next();
            this.setName(res.getString("name"));
            this.setIcon(res.getString("iconurl"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("probleme de requete");
        }
    }

    public abstract void effect(Board board);
}
