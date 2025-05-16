package test;

import model.Trainer;
import model.DefensiveTrainer;
import model.AttackingTrainer;
import model.TrainerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrainerGameTests {
    private TrainerFactory trainerFactory;

    @BeforeEach
    public void setup() {
        trainerFactory = new TrainerFactory();
    }

    @Test
    public void testCreateDefensiveTrainer() {
        Trainer defensiveTrainer = trainerFactory.createTrainer("Defensive", "Ash");
        assertTrue(defensiveTrainer instanceof DefensiveTrainer, "Trainer should be an instance of DefensiveTrainer");
        assertEquals("Ash", defensiveTrainer.getName(), "Trainer name should match");
    }

    @Test
    public void testCreateAttackingTrainer() {
        Trainer attackingTrainer = trainerFactory.createTrainer("Attacking", "Gary");
        assertTrue(attackingTrainer instanceof AttackingTrainer, "Trainer should be an instance of AttackingTrainer");
        assertEquals("Gary", attackingTrainer.getName(), "Trainer name should match");
    }

    @Test
    public void testDefensiveTrainerMoveSelection() {
        DefensiveTrainer defensiveTrainer = (DefensiveTrainer) trainerFactory.createTrainer("Defensive", "Misty");
        String move = defensiveTrainer.selectMove();
        assertNotNull(move, "Defensive trainer should select a move");
        // Additional assertions can be added based on specific move logic
    }

    @Test
    public void testAttackingTrainerMoveSelection() {
        AttackingTrainer attackingTrainer = (AttackingTrainer) trainerFactory.createTrainer("Attacking", "Brock");
        String move = attackingTrainer.selectMove();
        assertNotNull(move, "Attacking trainer should select a move");
        // Additional assertions can be added based on specific move logic
    }
}