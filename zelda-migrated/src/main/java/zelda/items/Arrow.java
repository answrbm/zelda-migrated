package zelda.items;

import java.awt.Rectangle;
import zelda.collision.Hittable;
import zelda.collision.Weapon;
import zelda.engine.GObject;
import zelda.engine.Game;
import zelda.character.Direction;
import zelda.link.Link;

/**
 *
 * @author frankie
 */
public class Arrow extends GObject {
    private final static String[] arrowRight    = {"arrowRight"};
	private final static String[] arrowLeft     = {"arrowLeft"};
	private final static String[] arrowDown     = {"arrowDown"};
	private final static String[] arrowUp       = {"arrowUp"};

    private final static String[] arrowHitDown	= {"arrowDown1","arrowDown2","arrowDown3","arrowDown1","arrowDown2","arrowDown3"};
    private final static String[] arrowHitUp	= {"arrowUp1","arrowUp2","arrowUp3","arrowUp1","arrowUp2","arrowUp3"};
    private final static String[] arrowHitLeft	= {"arrowLeft1","arrowLeft2","arrowLeft3","arrowLeft1","arrowLeft2","arrowLeft3"};
    private final static String[] arrowHitRight	= {"arrowRight1","arrowRight2","arrowRight3","arrowRight1","arrowRight2","arrowRight3"};

    private final static int SPEED = 3;
    private boolean hit = false;

    private Direction direction;
	
    public Arrow(Game game, int x, int y) {
        super(game, x, y, 13, 4, "/images/arrows.png");

        // Arrow Direction only
        spriteLoc.put("arrowRight", new Rectangle(75, 0, 17, 6));
        spriteLoc.put("arrowLeft", new Rectangle(50, 0, 17, 6));
        spriteLoc.put("arrowDown", new Rectangle(0, 0, 6, 17));
        spriteLoc.put("arrowUp", new Rectangle(25, 0, 6, 17));
        spriteLoc.put("arrowDown1", new Rectangle(0, 25, 7, 12));
        spriteLoc.put("arrowDown2", new Rectangle(25, 25, 7, 12));
        spriteLoc.put("arrowDown3", new Rectangle(50, 25, 7, 12));
        spriteLoc.put("arrowUp1", new Rectangle(0, 50, 7, 12));
        spriteLoc.put("arrowUp2", new Rectangle(25, 50, 7, 12));
        spriteLoc.put("arrowUp3", new Rectangle(50, 50, 7, 12));
        spriteLoc.put("arrowRight1", new Rectangle(0, 75, 12, 7));
        spriteLoc.put("arrowRight2", new Rectangle(25, 75, 12, 7));
        spriteLoc.put("arrowRight3", new Rectangle(50, 75, 12, 7));
        spriteLoc.put("arrowLeft1", new Rectangle(0, 100, 12, 7));
        spriteLoc.put("arrowLeft2", new Rectangle(25, 100, 12, 7));
        spriteLoc.put("arrowLeft3", new Rectangle(50, 100, 12, 7));

        setAnimationInterval(140);

        direction = game.getLink().getDirection();

        switch (direction) {
            case UP -> {
                sprite.setSprite(spriteLoc.get("arrowUp"));
                this.setAnimation(arrowUp);
                this.setHeight(13);
                this.setWidth(4);
            }
            case DOWN -> {
                sprite.setSprite(spriteLoc.get("arrowDown"));
                this.setAnimation(arrowDown);
                this.setHeight(13);
                this.setWidth(4);
            }
            case LEFT -> {
                sprite.setSprite(spriteLoc.get("arrowLeft"));
                this.setAnimation(arrowLeft);
            }
            case RIGHT -> {
                sprite.setSprite(spriteLoc.get("arrowRight"));
                this.setAnimation(arrowRight);
            }
        }

		game.playFx("/sounds/bowArrow.mp3");
    }

	@Override
    public void doInLoop() {
		if (x > game.getScene().getSprite().getWidth() || y > game.getScene().getSprite().getWidth() || x < 0 || y < 0) {
			alive = false;
			return;
		}

        switch (direction) {
            case UP -> setY(getY() - SPEED);
            case DOWN -> setY(getY() + SPEED);
            case LEFT -> setX(getX() - SPEED);
            case RIGHT -> setX(getX() + SPEED);
        }
    }

	@Override
    public void preAnimation() {
        if (hit) {
            liquid = true;
            if (animationCounter == animation.length) {
                setAlive(false);
            }
        }
    }

	@Override
    public void wallCollision() {
        arrowHitSomething();
    }

	@Override
	public void collision(GObject obj) {
		if (obj instanceof Hittable hittable && !(obj instanceof Link) && !(obj instanceof Bush)) {
            hittable.hitBy(Weapon.ARROW);
            alive = false;
			arrowHitSomething();
        }

		if (obj instanceof Guard) {
			arrowHitSomething();
		}

		if (obj instanceof Bush) {
            switch (direction) {
                case UP -> setYHardCore(getY() - SPEED);
                case DOWN -> setYHardCore(getY() + SPEED);
                case LEFT -> setXHardCore(getX() - SPEED);
                case RIGHT -> setXHardCore(getX() + SPEED);
            }
		}
	}

    private void arrowHitSomething() {
        switch (direction) {
            case UP -> this.setAnimation(arrowHitUp);
            case DOWN -> this.setAnimation(arrowHitDown);
            case LEFT -> this.setAnimation(arrowHitLeft);
            case RIGHT -> this.setAnimation(arrowHitRight);
        }

        hit = true;
    }
}