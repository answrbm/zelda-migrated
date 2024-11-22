package zelda.character;

/**
 * This is the superclass for states. 
 *
 * @author maartenhus
 */
public abstract class CharacterState {
	protected Character character;
	protected String name;

	public CharacterState(Character character) {
		this.character = character;
		character.resetAnimationCounter();
	}

	public void handleInput() {};
	public void handleAnimation() {};

	@Override
	public String toString() {
		return name;
	}
}
