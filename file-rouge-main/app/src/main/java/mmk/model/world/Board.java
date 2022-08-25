package mmk.model.world;


import mmk.model.personnage.APersonnage;
import mmk.model.util.ASavable;
import mmk.model.util.DBConnection;
import mmk.model.util.Vector2;
import mmk.model.personnage.hero.Hero;
import mmk.model.personnage.monster.Monster;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * classe représentant le pplateau de jeu
 */
public class Board extends ASavable {

    //#region attribut
    protected int[][] backgroundBoard;
    protected APersonnage[][] apersonnageBoard;
    protected int nbTour;
    //#endregion

    /**
     * constructeur de Board
     * @param width la lageur du plateau
     * @param height la hauteur du plateau
     */
    public Board(int width, int height) {
        this.backgroundBoard = new int[height][width];
        this.apersonnageBoard = new APersonnage[height][width];

        for (int y=0;y<height;y++) {
            this.backgroundBoard[y] = new int[width];
            this.apersonnageBoard[y] = new APersonnage[width];
        }

        this.nbTour = 0;
    }

    public Board(int id) {
        String query = "SELECT * FROM save WHERE id = " + id;
        try {
            ResultSet res = DBConnection.executeSelect(query);
            res.next();
            this.id = id;
            this.backgroundBoard = this.stringToBackgroundBoard(res.getString("backgroundBoard"),
                    res.getInt("width"),
                    res.getInt("height"));
            this.apersonnageBoard = this.stringToAPersonnageBoard(res.getString("apersonnageBoard"),
                    res.getInt("width"),
                    res.getInt("height"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * permet de bouger un personnage d'une case à l'autre
     * @param from la position de départ
     * @param to la position d'arrivée
     */
    public void move(Vector2 from, Vector2 to) {
        try {
            if (this.getAPersonnage(to) == null) {
                this.setAPersonnage(this.getAPersonnage(from), to);
                this.removeAPersonnage(from);
            }
        } catch (IndexOutOfBoundsException ignore) {}
    }

    public void move(APersonnage personnage, int direction) {
        Vector2 from = this.getApersonnagePosition(personnage);
        if (from != null)
            switch (direction) {
                case 0 -> this.move(from, new Vector2(from.getX()-1, from.getY()));
                case 1 -> this.move(from, new Vector2(from.getX()+1, from.getY()));
                default -> throw new RuntimeException("pas de dirrection");
            }
    }

    public void move(APersonnage personnage) {
        Vector2 position = this.getApersonnagePosition(personnage);
        this.move(position, position.add(personnage.getDirection()));
    }

    public void move(APersonnage personnage, Vector2 direction) {
        Vector2 position = this.getApersonnagePosition(personnage);
        this.move(position, position.add(direction));
    }

    public Vector2 getApersonnagePosition(APersonnage personnage) {
        for (int y=0;y<this.apersonnageBoard.length;y++)
            for (int x=0;x<this.apersonnageBoard[0].length;x++)
                if (personnage.equals(this.apersonnageBoard[y][x]))
                    return new Vector2(x, y);
        return null;
    }

    /**
     * retour le personnage à la position pos, null si personne
     * @param pos la position
     * @return un personnage ou null
     */
    public APersonnage getAtPosition(Vector2 pos) {
        if (0 <= pos.getY() && pos.getY() < this.apersonnageBoard.length)
            if (0 <= pos.getX() && pos.getX() < this.apersonnageBoard[0].length)
                return this.apersonnageBoard[pos.getY()][pos.getX()];
        return null;
    }

    /**
     * permet d'ajouté un personnage sur le plateau
     * @param personnage le personnage à ajouter
     * @param position la position du personnage
     */
    public void addPersonnage(APersonnage personnage, Vector2 position){
        if (this.getAPersonnage(position) == null) {
            this.setAPersonnage(personnage, position);
        }
    }

    @Override
    public int save() {

        for (APersonnage[] row : this.apersonnageBoard)
            for (APersonnage personnage : row)
                if (personnage != null)
                    personnage.save();

        String query;
        if (this.id > 0) // si deja en bdd = update
            query = "UPDATE save SET" +
                    " `nbTour` = ?," +
                    " `backgroundBoard` = ?," +
                    " `apersonnageBoard` = ?" +
                    " where id = ?";
        else // si pas en bdd = isert
            query = "INSERT INTO save " +
                    "(`nbTour`, `backgroundBoard`, `apersonnageBoard`, `width`, `height`, `blackGold`) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = DBConnection.createPreperedStatment(query);
        try {
            statement.setInt(1, nbTour);
            statement.setString(2, this.backgroundBoardToString());
            statement.setString(3, this.apersonnageBoardToString());
            if (id > 0)
                statement.setInt(4, this.id);
            else {
                statement.setInt(4, this.apersonnageBoard[0].length);
                statement.setInt(5, this.apersonnageBoard.length);
                statement.setInt(6, 0);
            }

            if (this.id == 0)
                this.id = DBConnection.executeInsert(statement);
            else
                DBConnection.executeUpdate(statement);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return this.id;
    }

    /**
     * permet de transformet le backgroundBoard en String
     * @return un représentation en String du backgroundBoard
     */
    protected String backgroundBoardToString() {
        StringBuilder res = new StringBuilder();
        for (int[] row : this.backgroundBoard)
            for (int value : row)
                res.append(value).append(",");

        res.delete(res.length()-1, res.length()); // suppression de la derniere virgule
        return res.toString();
    }

    /**
     * permet de transformer un String en board
     * @param board la représentation en String du board
     * @param width la largeur du board
     * @param height la hauteur du board
     * @return une représentation en matrice de taille [height][width]
     */
    protected int[][] stringToBackgroundBoard(String board, int width, int height) {
        int[][] res = new int[height][width];
        String[] splitedBoard = board.split(",");

        int i = 0;
        for (String value : splitedBoard)
            res[i/width][i++%width] = Integer.parseInt(value);

        return res;
    }

    /**
     * permet de transformet le apersonnageBoard en String
     * @return un représentation en String du apersonnageBoard
     */
    protected String apersonnageBoardToString() {
        StringBuilder res = new StringBuilder();
        for (APersonnage[] row : this.apersonnageBoard)
            for(APersonnage personnage : row)
                if (personnage != null)
                    res.append(personnage.getType()).append(personnage.getId()).append(",");
                else
                    res.append("0").append(",");

        res.delete(res.length()-1, res.length()); // suppression de la derniere virgule
        return res.toString();
    }

    protected APersonnage[][] stringToAPersonnageBoard(String board, int width, int height) {
        APersonnage[][] res = new APersonnage[height][width];
        String[] splitedBoard = board.split(",");

        int i = 0;
        for (String value : splitedBoard) {
            if (value.equals("0")){
                i++;
                continue;
            }
            int type = Character.getNumericValue(value.charAt(0));
            int idPersonnage = Integer.parseInt(value.substring(1));
            switch (type) {
                case 1 -> res[i / width][i++ % width] = new Hero(idPersonnage);
                case 2 -> res[i / width][i++ % width] = new Monster(idPersonnage);
            }
        }

        return res;
    }


    //#region getter/setter
    public Hero[] getHeros() {
        ArrayList<Hero> res = new ArrayList<>();
        for (APersonnage[] row : this.apersonnageBoard)
            for (APersonnage personnage : row)
                if (personnage != null)
                    if (personnage instanceof Hero hero)
                        res.add(hero);

        return res.toArray(new Hero[0]);
    }

    public Monster[] getMonsters() {
        ArrayList<Monster> res = new ArrayList<>();
        for (APersonnage[] row : this.apersonnageBoard)
            for (APersonnage personnage : row)
                if (personnage != null)
                    if (personnage instanceof Monster monster)
                        res.add(monster);

        return res.toArray(new Monster[0]);
    }

    /**
     * permet de recuperer un personnage a la position demandé /!\ peut renvoyer null
     * @param position la position à recuperer
     * @return le personnage à la position ou null si vide
     */
    public APersonnage getAPersonnage(Vector2 position) {
        return this.apersonnageBoard[position.getY()][position.getX()];
    }

    protected void setAPersonnage(APersonnage personnage, Vector2 position) {
        this.apersonnageBoard[position.getY()][position.getX()] = personnage;
    }

    protected void removeAPersonnage(Vector2 position) {
        this.apersonnageBoard[position.getY()][position.getX()] = null;
    }

    public void removeAPersonnage(APersonnage personnage) {
        this.removeAPersonnage(this.getApersonnagePosition(personnage));
    }

    public APersonnage[][] getAPersonnageBoard() {
        return this.apersonnageBoard;
    }

    public int[][] getBackgroundBoard() {
        return this.backgroundBoard;
    }

    protected int getNbTour() {
        return nbTour;
    }

    protected void setNbTour(int nbTour) {
        this.nbTour = nbTour;
    }

    //#endregion
}
