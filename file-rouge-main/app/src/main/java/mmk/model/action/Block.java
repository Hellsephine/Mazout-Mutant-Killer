package mmk.model.action;

import mmk.model.personnage.APersonnage;
import mmk.model.personnage.EPersonnageState;
import mmk.model.world.Board;

public class Block implements IAction {

    @Override
    public void doo(APersonnage personnage, Board board, int... args) {
        personnage.addPersonnageState(EPersonnageState.BLOCK);
    }
}
