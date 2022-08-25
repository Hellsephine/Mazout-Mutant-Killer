package mmk.model.action;

import mmk.model.personnage.APersonnage;
import mmk.model.personnage.hero.Hero;
import mmk.model.world.Board;

public class Consume implements IAction{


    @Override
    public void doo(APersonnage personnage, Board board, int... args) {
        if (personnage instanceof Hero hero)
            hero.consume(args[0]);
    }
}
