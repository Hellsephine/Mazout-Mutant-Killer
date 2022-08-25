package mmk.model.action;

import mmk.model.personnage.APersonnage;
import mmk.model.world.Board;

/**
 * représente les actions possibles par les personnages
 */
public interface IAction {

    /**
     * faire l'action
     * @param personnage le APersonnage faisant l'action
     * @param board le plateau où l'action s'effectue
     * @param args les arguments de l'actions
     */
    void doo(APersonnage personnage, Board board, int... args);
}
