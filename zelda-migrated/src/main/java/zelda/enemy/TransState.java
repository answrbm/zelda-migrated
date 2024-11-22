package zelda.enemy;

import zelda.character.Direction;
import zelda.character.Character;
import zelda.character.CharacterState;

/**
 *
 * @author vincentklarholz
 */
public class TransState extends CharacterState {
    private final static String[] downAnimation	= {"Stand down", "Stand down", "Stand down", "Stand down", "Stand down", "Stand down", "Stand down", "Stand down", "Stand down", "Stand down"};
	private final static String[] upAnimation	= {"Stand up", "Stand up", "Stand up", "Stand up", "Stand up", "Stand up", "Stand up", "Stand up", "Stand up", "Stand up"};
	private final static String[] leftAnimation	= {"Stand left", "Stand left", "Stand left", "Stand left", "Stand left", "Stand left", "Stand left", "Stand left", "Stand left", "Stand left"};
	private final static String[] rightAnimation= {"Stand right", "Stand right", "Stand right", "Stand right", "Stand right", "Stand right", "Stand right", "Stand right", "Stand right", "Stand right"};

    private Direction direction;

    public TransState(Character soldier, Direction direction) {
        super(soldier);
		name = "TransState";
        character.setAnimationInterval(40);

        this.direction = direction;
    }

    public void left() {
        character.setAnimation(leftAnimation);
		character.setX(character.getX() + 4);
	}

	public void right() {
        character.setAnimation(rightAnimation);
		character.setX(character.getX() - 4);
	}

	public void up() {
        character.setAnimation(upAnimation);
        character.setY(character.getY() + 4);
	}

	public void down() {
        character.setAnimation(downAnimation);
		character.setY(character.getY() - 4);
	}

    @Override
	public void handleAnimation() {
		int animationCounter = character.getAnimationCounter();

        if (animationCounter == character.getAnimation().length) {
            character.setAnimationInterval(90);
            character.setState(new WalkState(character));
        }

        switch (direction) {
            case UP -> down();
            case DOWN -> up();
            case LEFT -> right();
            case RIGHT -> left();
        }
    }
}
