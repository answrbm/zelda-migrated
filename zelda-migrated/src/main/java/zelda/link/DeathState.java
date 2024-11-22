package zelda.link;

import zelda.character.Direction;
import zelda.scene.HouseScene;

/**
 * @author Bas Harteveld
 */
public class DeathState extends LinkState {
    private final String[] deathRightAnimation	= {"Link hit right", "Link death right", "Link death right 2"};
    private final String[] deathLeftAnimation	= {"Link hit left", "Link death left", "Link death left 2"};

    private long oldAnimationInterval;

    public DeathState(Link link, Direction direction) {
        super(link);
        name = "DeathState";

        link.setAnimationInterval(700);

        switch (direction) {
            case UP, LEFT -> link.setAnimation(deathLeftAnimation);
            case DOWN, RIGHT -> link.setAnimation(deathRightAnimation);
        }
    }

    @Override
	public void handleAnimation() {
		int animationCounter = link.getAnimationCounter();
		
		if (animationCounter == link.getAnimation().length) {
			link.setAnimationInterval(oldAnimationInterval);			
			link.setState(new StandState(link));
			link.setHealth(2);
			game.setScene(new HouseScene(game, "GameStart"));
		}
    }
}
