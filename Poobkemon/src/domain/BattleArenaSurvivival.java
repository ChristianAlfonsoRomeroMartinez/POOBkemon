package domain;

public class BattleArenaSurvivival extends BattleArena {
    
    public BattleArenaSurvivival() {
        super();
    }

    @Override
    public void attack(String moveName, String itself) throws PoobkemonException {
        Coach currentCoach = getCurrentCoach();
        Coach opponentCoach = getOpponentCoach();

        Pokemon attacker = currentCoach.getActivePokemon();
        Pokemon defender = opponentCoach.getActivePokemon();

        // Encuentra el ataque por nombre
        Attack attack = attacker.getAtaques().stream()
            .filter(a -> a.getName().equals(moveName))
            .findFirst()
            .orElse(null); // Si no encuentra el ataque, devuelve null

        if (attack.getPowerPoint() > 0){
            if ("itself".equals(itself)) {
            // Realiza el ataque sobre sí mismo
            
            attacker.attack(attacker, attack);
            } else {
            // Realiza el ataque sobre el oponente
            attacker.attack(defender, attack);
            }
        } else {
            System.out.println("No puedes usar este ataque, no tienes PP.");
        }
    }
}
