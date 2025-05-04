package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class HumanCoach extends Coach {
    private Map<Integer, Consumer<HumanCoach>> actionMap = new HashMap<>();
    private String name;
    
    private Coach opponent;

    public HumanCoach(String name, ArrayList<Pokemon> pokemons, ArrayList<Item> items) {
        super(pokemons, items);
        this.name = name;

        initializeActionMap();
    }

    private void initializeActionMap() {
        actionMap.put(0, hc -> performAttack());
        actionMap.put(1, hc -> performItem());
        actionMap.put(2, hc -> performSwitch());
        actionMap.put(3, hc -> performFlee());
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void doAction(int actionIndex) throws PoobkemonException {
        if (!actionMap.containsKey(actionIndex)) {
           // throw new PoobkemonException(PoobkemonException.INVALID_ACTION);
        }
        actionMap.get(actionIndex).accept(this);
    }

    @Override
    public void handleTurnTimeout() {
        Pokemon active = getActivePokemon();
        if (active != null) {
            active.reducePP(); // Método a implementar en Pokemon
        }
    }

    // Métodos auxiliares
    private void performAttack() throws PoobkemonException {
        Pokemon attacker = getActivePokemon();
        int moveIdx = getSelectedMoveIndexFromUI();
        
        if (moveIdx < 0 || moveIdx >= attacker.getAtaques().size()) {
            //throw new PoobkemonException(PoobkemonException.INVALID_MOVE);
        }
        
        Attack atk = attacker.getAtaques().get(moveIdx);
        attacker.attack(opponent.getActivePokemon(), atk);
    }

    private void performItem() throws PoobkemonException {
        int itemIdx = getSelectedItemIndexFromUI();
        
        
        useItem(item);
        itms.remove(itemIdx);
    }

    private void performSwitch() throws PoobkemonException {
        int idx = getSelectedSwitchIndexFromUI();
        switchToPokemon(idx); // Usar método de la clase padre
    }

    //private void performFlee() throws PoobkemonException {
        //throw new PoobkemonException(PoobkemonException.CANNOT_FLEE);
    //}

    // Métodos de UI (versión básica)
    private int getSelectedMoveIndexFromUI() { 
        // Implementación temporal
        return 0; 
    }
    
    private int getSelectedItemIndexFromUI() { 
        // Implementación temporal
        return 0; 
    }
    
    private int getSelectedSwitchIndexFromUI() { 
        // Implementación temporal
        return 0; 
    }

    public void setOpponent(Coach opponent) {
        this.opponent = opponent;
    }

    public String getName() {
        return name;
    }
}