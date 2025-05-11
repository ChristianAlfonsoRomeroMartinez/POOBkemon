package test;

import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PoobkemonTests {
    private HumanCoach coach1;
    private HumanCoach coach2;
    private BattleArenaNormal battleArena;
    private Poobkemon poobkemon;

    @BeforeEach
    public void setup() throws PoobkemonException {
        // Crear Pokémon y asignar ataques
        ArrayList<String> pokemons1 = new ArrayList<>(List.of("Charizard", "Blastoise"));
        ArrayList<String> pokemons2 = new ArrayList<>(List.of("Venusaur", "Gengar"));

        Attack[][] attacks1 = {
            {new PhysicalAttack("Garra Dragón", "Dragon", 80, 10, 100)},
            {new SpecialAttack("Hidrobomba", "Agua", 110, 5, 80)}
        };

        Attack[][] attacks2 = {
            {new SpecialAttack("Hoja Mágica", "Planta", 60, 10, 100)},
            {new StatusAttack("Fuego Fatuo", "Fuego", 0, 5, 85, "Burn", 5)}
        };

        // Crear ítems
        ArrayList<String> items1 = new ArrayList<>(List.of("Poción", "Superpoción"));
        ArrayList<String> items2 = new ArrayList<>(List.of("Revive"));

        // Configurar entrenadores
        coach1 = new HumanCoach("Ash", pokemons1, items1);
        coach2 = new HumanCoach("Gary", pokemons2, items2);

        coach1.setPokemonAttacks(attacks1);
        coach2.setPokemonAttacks(attacks2);

        // Configurar arena de batalla
        battleArena = new BattleArenaNormal();
        battleArena.setupCoaches("Ash", "Gary", pokemons1, pokemons2, items1, items2, attacks1, attacks2);
    
        poobkemon = new Poobkemon();
        poobkemon.startBattleNormal("Ash", "Gary", pokemons1, pokemons2, items1, items2, attacks1, attacks2);
    }

    @Test
    public void shouldApplyItemOnDamagedPokemon() throws PoobkemonException {
        Pokemon activePokemon = coach1.getActivePokemon();
        activePokemon.setPs(50); // Reducir PS del Pokémon activo
        Item potion = new Item("Poción", "Restaura 20 PS", 20, Item.AttributeType.HP);

        coach1.useItem(potion);

        assertEquals(70, activePokemon.getPs(), "El PS del Pokémon debería haber aumentado en 20.");
    }

    @Test
    public void shouldFleeBattle() {
        poobkemon.flee();
        // Verificar que la batalla haya terminado
        assertTrue(battleArena.isBattleFinished(), "La batalla debería haber terminado después de huir.");
    }

    @Test
    public void shouldNotApplyItemOnFaintedPokemon() {
        Pokemon activePokemon = coach1.getActivePokemon();
        activePokemon.setPs(0); // Pokémon debilitado
        Item potion = new Item("Poción", "Restaura 20 PS", 20, Item.AttributeType.HP);

        assertThrows(IllegalStateException.class, () -> coach1.useItem(potion), "No se puede usar un ítem en un Pokémon debilitado.");
    }

    @Test
    public void shouldSwitchToAnotherPokemon() throws PoobkemonException {
        coach1.switchToPokemon(1); // Cambiar al segundo Pokémon
        assertEquals("Blastoise", coach1.getActivePokemon().getName(), "El Pokémon activo debería ser Blastoise.");
    }

    @Test
    public void shouldNotSwitchToFaintedPokemon() {
        Pokemon secondPokemon = coach1.getPokemons().get(1);
        secondPokemon.setPs(0); // Debilitar al segundo Pokémon

        assertThrows(PoobkemonException.class, () -> coach1.switchToPokemon(1), "No se puede cambiar a un Pokémon debilitado.");
    }

    @Test
    public void shouldAttackOpponentPokemon() {
        Pokemon attacker = coach1.getActivePokemon();
        Pokemon defender = coach2.getActivePokemon();
        Attack attack = attacker.getAtaques().get(0);

        attacker.attack(defender, attack);

        assertTrue(defender.getPs() < defender.getTotalPs(), "El PS del Pokémon defensor debería haber disminuido.");
    }

    @Test
    public void shouldApplyStatusEffectOnPokemon() {
        Pokemon defender = coach2.getActivePokemon();
        Attack statusAttack = coach1.getActivePokemon().getAtaques().get(0);

        defender.setStatus(3); // Quemado
        defender.applyEffectDamage();

        assertTrue(defender.getPs() < defender.getTotalPs(), "El PS del Pokémon debería haber disminuido debido al efecto de quemadura.");
    }
    
    @Test
    public void shouldNotAllowItemOnPokemonWithFullHealth() {
        Item potion = new Item("Poción", "Restaura 20 PS", 20, Item.AttributeType.HP);

        assertThrows(PoobkemonException.class, () -> coach1.useItem(potion), "");
    }
    
    @Test
    public void shouldNotAllowMoreThanMaxHealth() {
        Pokemon activePokemon = coach1.getActivePokemon();
        activePokemon.setPs(activePokemon.getTotalPs() + 50); // Intentar establecer PS mayores al máximo

        assertEquals(activePokemon.getTotalPs(), activePokemon.getPs(), "El PS no debería exceder el máximo.");
    }

    @Test
    public void shouldHandleTurnTimeout() {
        Pokemon activePokemon = coach1.getActivePokemon();

        // Obtener los PP iniciales de los ataques
        List<Integer> initialPP = activePokemon.getAtaques().stream()
            .map(Attack::getPowerPoint)
            .toList();

        // Llamar a handleTurnTimeout
        coach1.handleTurnTimeout();

        // Verificar que los PP hayan disminuido
        for (int i = 0; i < activePokemon.getAtaques().size(); i++) {
            assertTrue(activePokemon.getAtaques().get(i).getPowerPoint() < initialPP.get(i), "El PP del ataque debería haberse reducido.");
        }
    }

    @Test
    public void shouldNotFinishBattleWhenPokemonAreStillAlive() {
        // Asegurarse de que ambos entrenadores tienen Pokémon vivos
        coach1.getPokemons().get(0).setPs(50);
        coach2.getPokemons().get(0).setPs(50);

        assertFalse(battleArena.isBattleFinished(), "La batalla no debería terminar si ambos entrenadores tienen Pokémon vivos.");
    }

    @Test
    public void shouldMarkCoachAsFled() {
        // Asegurarse de que el entrenador no haya huido inicialmente
        assertFalse(coach1.getHasFled(), "El entrenador no debería haber huido inicialmente.");

        // Hacer que el entrenador huya
        coach1.fleeBattle();

        // Verificar que el entrenador haya sido marcado como que ha huido
        assertTrue(coach1.getHasFled(), "El entrenador debería haber huido de la batalla.");
    }
}



