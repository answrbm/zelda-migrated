package zelda.enemy;

import zelda.character.Direction;
import zelda.character.Character;
import zelda.character.CharacterState;

/**
 *
 * @author maartenhus
 */
public class StandState extends CharacterState {
	private final static String[] downAnimation	= {"Stand down"};
	private final static String[] upAnimation	= {"Stand up"};
	private final static String[] leftAnimation	= {"Stand left"};
	private final static String[] rightAnimation= {"Stand right"};

	private Direction oldDirection;;

	public StandState(Character soldier) {
		super(soldier);
		name = "StandState";

		oldDirection = soldier.getDirection();

		switch (oldDirection) {
			case UP -> soldier.setAnimation(upAnimation);
			case DOWN -> soldier.setAnimation(downAnimation);
			case LEFT -> soldier.setAnimation(leftAnimation);
			case RIGHT -> soldier.setAnimation(rightAnimation);
		}
	}

	@Override
	public void handleInput() {
		if (oldDirection != character.getDirection()) {
            character.setState(new WalkState(character));
        }
	}
}
