package zelda.enemy;

import java.util.Random;
import zelda.character.Direction;

/**
 *
 * @author Christiaan
 */
public class RandomBehavior extends Behavior {
    private WhiteSoldier soldier;
    private long inputInterval = 5000;
	private long lastInput = System.currentTimeMillis();

    public RandomBehavior(WhiteSoldier soldier) {
        this.soldier = soldier;
    }
    
    public void behave() {
        if (System.currentTimeMillis() > lastInput + inputInterval) {
            Random random = new Random();
            int r = random.nextInt(4);

            switch (r) {
                case 0 -> soldier.setDirection(Direction.UP);
                case 1 -> soldier.setDirection(Direction.LEFT);
                case 2 -> soldier.setDirection(Direction.RIGHT);
                case 3 -> soldier.setDirection(Direction.DOWN);
            }

            lastInput = System.currentTimeMillis();
        }
    }
}
