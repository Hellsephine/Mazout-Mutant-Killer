package mmk.model.action;

import java.util.LinkedList;
import java.util.Queue;

public enum EActionState {
    ATTACK(new Attack()),
    BLOCK(new Block()),
    MOVE(new Move()),
    CONSUME(new Consume());

    public final IAction action;
    private final Queue<Integer> arguments;

    EActionState(IAction action) {
        this.action = action;
        this.arguments = new LinkedList<>();
    }

    public EActionState setArguments(int args) {
        this.arguments.add(args);
        return this;
    }

    public int getArguments() {
        if (this.arguments.size() > 0)
            return this.arguments.poll();
        else
            return 0;
    }
}
