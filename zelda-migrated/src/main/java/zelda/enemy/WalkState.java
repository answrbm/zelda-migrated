package zelda.enemy;

import zelda.character.Direction;
import zelda.character.Character;
import zelda.character.CharacterState;

/**
 *
 * @author maartenhus
 */
public class WalkState extends CharacterState {
	private final String[] downAnimation	= {"Stand down", "Walk down 1", "Walk down 2", "Walk down 3"};
	private final String[] upAnimation		= {"Stand up", "Walk up 1", "Walk up 2"};
	private final String[] leftAnimation	= {"Stand left", "Walk left 1", "Walk left 2"};
	private final String[] rightAnimation	= {"Stand right", "Walk right 1", "Walk right 2"};

    private static final int WALK_SPEED = 2;

    public WalkState(Character soldier) {
		super(soldier);
		name = "WalkState";

        character.setAnimationInterval(90);
    }

	@Override
	public void handleInput() {
		switch (character.getDirection()) {
			case UP -> up();
			case DOWN -> down();
			case LEFT -> left();
			case RIGHT -> right();
		}
	}

	public void left() {
		if (character.getAnimation() != leftAnimation) {
			character.setAnimation(leftAnimation);
		}

		if (character.getDirection() != Direction.LEFT) {
			character.setDirection(Direction.LEFT);
		}

		character.setWidth(29);
		character.setX(character.getX() - WALK_SPEED);
	}

	public void right() {
		if (character.getAnimation() != rightAnimation) {
			character.setAnimation(rightAnimation);
		}

		if (character.getDirection() != Direction.RIGHT) {
			character.setDirection(Direction.RIGHT);
		}

		character.setWidth(29);
		character.setX(character.getX() + WALK_SPEED);
	}

	public void up() {
		if (character.getAnimation() != upAnimation) {
			character.setAnimation(upAnimation);
		}

		if (character.getDirection() != Direction.UP) {
			character.setDirection(Direction.UP);
		}

		character.setWidth(22);
		character.setY(character.getY() - WALK_SPEED);
	}

	public void down() {
		if (character.getAnimation() != downAnimation) {
			character.setAnimation(downAnimation);
		}

		if (character.getDirection() != Direction.DOWN) {
			character.setDirection(Direction.DOWN);
		}

		character.setWidth(22);
		character.setY(character.getY() + WALK_SPEED);
	}

    @Override
	public void handleAnimation() {
		int animationCounter = character.getAnimationCounter();

        Direction dir = character.getDirection();

		if (animationCounter != character.getAnimation().length) {
			if (dir == Direction.LEFT) {
				switch (animationCounter) {
					case 1 -> character.setX(character.getX() - 5);
					case 2 -> character.setX(character.getX() + 5);
				}
			}
		}
    }
}
