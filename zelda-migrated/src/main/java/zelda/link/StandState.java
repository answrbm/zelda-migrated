package zelda.link;

/**
 * State for when link is standing still.
 *
 * @author maartenhus
 */
public class StandState extends LinkState {
	private final static String[] downAnimation	= {"Link stand down"};
	private final static String[] upAnimation	= {"Link stand up"};
	private final static String[] leftAnimation	= {"Link stand left"};
	private final static String[] rightAnimation= {"Link stand right"};

	public StandState(Link link) {
		super(link);
		name = "StandState";

		switch (link.getDirection()) {
			case UP -> link.setAnimation(upAnimation);
			case DOWN -> link.setAnimation(downAnimation);
			case LEFT -> link.setAnimation(leftAnimation);
			case RIGHT -> link.setAnimation(rightAnimation);
		}
	}

	@Override
	public void handleInput() {
		if (game.isjPressed()) {
			link.setState(new SwordState(link));
		} else if(game.islPressed()) {
            link.dropBomb();
        } else if(game.iskPressed()) {
            link.shootArrow();
        } else {
			if (link.moveInput())
				link.setState(new WalkState(link));
		}
	}
}
