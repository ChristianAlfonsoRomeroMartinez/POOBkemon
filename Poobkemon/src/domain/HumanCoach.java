package domain;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;


/**
 * Entrenador controlado por el usuario.
 */
public class HumanCoach extends Coach {
    private Map<Integer, Consumer<HumanCoach>> actionMap = new HashMap<>();
    private String name;

    public HumanCoach(String name) {
        this.name = name;
        // Inicializar mapeo de acciones
        actionMap.put(0, HumanCoach::performAttack);
        actionMap.put(1, HumanCoach::performItem);
        actionMap.put(2, HumanCoach::performSwitch);
        actionMap.put(3, HumanCoach::performFlee);
    }

    @Override
    public void doAction(int actionIndex) throws PoobkemonException {
        Consumer<HumanCoach> handler = actionMap.get(actionIndex);
        //if (handler == null) throw new PoobkemonException(PoobkemonException.INVALID_ACTION);
        handler.accept(this);
    }

    @Override
    public void handleTurnTimeout() {
        Pokemon active = getActivePokemon();
        active.setPs(active.getPs() / 2);
    }

    // Métodos manejadores de cada acción:
    private void performAttack() {
        int moveIdx = getSelectedMoveIndexFromUI();
        Attack atk = getActivePokemon().getAtaques().get(moveIdx);
        getActivePokemon().attack(getOpponentPokemon(), atk);
    }

    private void performItem() {
        int itemIdx = getSelectedItemIndexFromUI();
        Item item = getInventory().get(itemIdx);
        item.applyItemEffect(getActivePokemon());
    }

    @Override
    private void switchToPokemon() {
        int idx = getSelectedSwitchIndexFromUI();
        switchToPokemon(idx);
    }

    private void performFlee() {
        throw new PoobkemonException(PoobkemonException.CANNOT_FLEE);
    }

    // Stubs UI:
    private int getSelectedMoveIndexFromUI() { throw new UnsupportedOperationException(); }
    private int getSelectedItemIndexFromUI() { throw new UnsupportedOperationException(); }
    private int getSelectedSwitchIndexFromUI() { throw new UnsupportedOperationException(); }
}
