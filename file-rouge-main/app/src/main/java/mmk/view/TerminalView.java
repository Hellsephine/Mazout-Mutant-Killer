package mmk.view;

import mmk.controller.TerminalController;
import mmk.model.personnage.APersonnage;
import mmk.model.util.eventmanagement.EEventType;
import mmk.model.util.eventmanagement.EventListener;

import java.util.Scanner;

public class TerminalView implements EventListener {

    private final Scanner scan = new Scanner(System.in);
    private final TerminalController controller;

    public TerminalView(TerminalController controller) {
        this.controller = controller;
    }


    public void getInput() {
        String input = this.scan.nextLine();
        this.controller.action(input);
    }

    public void print(APersonnage[][] personnages) {
        int i=1;
        for (int y=0;y<5;y++) {
            for (int x=0;x<10;x++) {
                if (personnages[y][x] != null)
                    System.out.print(personnages[y][x].getId() + " ");
                else
                    System.out.print(0 + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    @Override
    public void update(EEventType eventType, Object data) {
        if (EEventType.MODIFY_APERSONNAGE_BOARD.equals(eventType))
            this.print((APersonnage[][]) data);
        if (EEventType.ASK_TERMINAL_INPUT.equals(eventType))
            this.getInput();
    }
}
