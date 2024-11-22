package zelda.enemy.armos;

import zelda.character.Direction;
import zelda.character.Character;
import zelda.character.CharacterState;

/**
 *
 * @author vincentklarholz
 */
public class TransState extends CharacterState {
    private final String[] animationHit = {"hit 1", "hit 2", "hit 3", "hit 4", "hit 5", "hit 6", "hit 7", "hit 8", "hit 9", "hit 10"};

    private Direction direction;

    public TransState(Character armosKnight, Direction direction) {
        super(armosKnight);
		name = "TransState";
        character.setAnimationInterval(10);

        this.direction = direction;
    }

    public void left() {
        character.setAnimation(animationHit);
		character.setX(character.getX() + 8);
	}

	public void right() {
        character.setAnimation(animationHit);
		character.setX(character.getX() - 8);
	}

	public void up() {
        character.setAnimation(animationHit);
        character.setY(character.getY() + 8);
	}

	public void down() {
        character.setAnimation(animationHit);
		character.setY(character.getY() - 8);
	}

    @Override
	public void handleAnimation() {
		int animationCounter = character.getAnimationCounter();

        if (animationCounter == character.getAnimation().length) {
            character.setAnimationInterval(90);
            character.setState(new AttackState(character));
        }

        switch (direction) {
            case UP -> down();
            case DOWN -> up();
            case LEFT -> right();
            case RIGHT -> left();
        }
    }
}
