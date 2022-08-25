package mmk.model.action;

import mmk.model.personnage.APersonnage;
import mmk.model.world.Board;

public class Attack implements IAction {

    @Override
    public void doo(APersonnage personnage, Board board, int... args) {
        personnage.attack(board);
    }
}
