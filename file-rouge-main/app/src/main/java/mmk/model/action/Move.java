package mmk.model.action;

import mmk.model.personnage.APersonnage;
import mmk.model.world.Board;

public class Move implements IAction {

    @Override
    public void doo(APersonnage personnage, Board board, int... args) {
        board.move(personnage, args[0]);
    }
}
