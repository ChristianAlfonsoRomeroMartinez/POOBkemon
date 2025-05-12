package test;

import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PoobkemonTests {
    private Poobkemon poobkemon;

    @BeforeEach
    public void setup() throws PoobkemonException {
        // Crear Pokémon y asignar ataques
        ArrayList<String> pokemons1 = new ArrayList<>(List.of("Charizard", "Blastoise"));
        ArrayList<String> pokemons2 = new ArrayList<>(List.of("Venusaur", "Blastoise"));

        String[][] attacks1 = {
            {"Garra dragón"},
            {"Finta"}
        };

        String[][] attacks2 = {
            {"Esfuerzo"},
            {"Finta"}
        };

        // Crear ítems
        ArrayList<String> items1 = new ArrayList<>(List.of("Poción", "Superpoción"));
        ArrayList<String> items2 = new ArrayList<>(List.of("Revive"));

        // Configurar la clase Poobkemon
        poobkemon = new Poobkemon();
        poobkemon.startBattleNormal("Ash", "Gary", pokemons1, pokemons2, items1, items2, attacks1, attacks2);
    }

    @Test
    public void shouldApplyItemOnDamagedPokemon() throws PoobkemonException {
        // Reducir PS del Pokémon activo
        poobkemon.getBattleArena().getCoaches()[0].getActivePokemon().setPs(50);

        // Usar un ítem
        poobkemon.useItem("Poción");

        // Verificar que el PS haya aumentado
        assertEquals(70, poobkemon.getBattleArena().getCoaches()[0].getActivePokemon().getPs(),
                "El PS del Pokémon debería haber aumentado en 20.");
    }

    @Test
    public void shouldFleeBattle() {
        // Huir de la batalla
        poobkemon.flee();

        // Verificar que la batalla haya terminado
        assertTrue(poobkemon.getBattleArena().isBattleFinished(),
                "La batalla debería haber terminado después de huir.");
    }

    @Test
    public void shouldNotApplyItemOnFaintedPokemon() {
        // Debilitar al Pokémon activo
        poobkemon.getBattleArena().getCoaches()[0].getActivePokemon().setPs(0);

        // Intentar usar un ítem
        assertThrows(PoobkemonException.class, () -> poobkemon.useItem("Poción"),
                "No se puede usar un ítem en un Pokémon debilitado.");
    }

    @Test
    public void shouldSwitchToAnotherPokemon() throws PoobkemonException {
        // Cambiar al segundo Pokémon
        poobkemon.switchToPokemon(1);

        // Verificar que el Pokémon activo sea el segundo
        assertEquals("Blastoise", poobkemon.getBattleArena().getCoaches()[0].getActivePokemon().getName(),
                "El Pokémon activo debería ser Blastoise.");
    }


    @Test
    public void shouldApplyStatusEffectOnPokemon() {
        // Aplicar un efecto de estado
        poobkemon.getBattleArena().getCoaches()[1].getActivePokemon().setStatus(3); // Quemado
        poobkemon.getBattleArena().getCoaches()[1].getActivePokemon().applyEffectDamage();

        // Verificar que el PS haya disminuido
        assertTrue(poobkemon.getBattleArena().getCoaches()[1].getActivePokemon().getPs() <
                poobkemon.getBattleArena().getCoaches()[1].getActivePokemon().getTotalPs(),
                "El PS del Pokémon debería haber disminuido debido al efecto de quemadura.");
    }

    @Test
    public void shouldNotAllowItemOnPokemonWithFullHealth() {
        // Intentar usar un ítem en un Pokémon con PS completos
        assertThrows(PoobkemonException.class, () -> poobkemon.useItem("Poción"),
                "No se puede usar un ítem en un Pokémon con PS completos.");
    }

    @Test
    public void shouldNotAllowMoreThanMaxHealth() {
        // Intentar establecer PS mayores al máximo
        poobkemon.getBattleArena().getCoaches()[0].getActivePokemon().setPs(
                poobkemon.getBattleArena().getCoaches()[0].getActivePokemon().getTotalPs() + 50);

        // Verificar que el PS no exceda el máximo
        assertEquals(poobkemon.getBattleArena().getCoaches()[0].getActivePokemon().getTotalPs(),
                poobkemon.getBattleArena().getCoaches()[0].getActivePokemon().getPs(),
                "El PS no debería exceder el máximo.");
    }

    

    @Test
    public void shouldNotFinishBattleWhenPokemonAreStillAlive() {
        // Asegurarse de que ambos entrenadores tienen Pokémon vivos
        poobkemon.getBattleArena().getCoaches()[0].getActivePokemon().setPs(50);
        poobkemon.getBattleArena().getCoaches()[1].getActivePokemon().setPs(50);

        // Verificar que la batalla no haya terminado
        assertFalse(poobkemon.getBattleArena().isBattleFinished(),
                "La batalla no debería terminar si ambos entrenadores tienen Pokémon vivos.");
    }

    @Test
    public void shouldMarkCoachAsFled() {
        // Hacer que el entrenador huya
        poobkemon.flee();

        // Verificar que el entrenador haya sido marcado como que ha huido
        assertTrue(poobkemon.getBattleArena().getCoaches()[0].getHasFled(),
                "El entrenador debería haber huido de la batalla.");
    }
}



