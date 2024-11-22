package zelda.engine;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.HashMap;
import zelda.items.Heart;
import zelda.items.Rupee;

/**
 * A GObject is something that gets drawn on the View, checks if it collides and animates itself.
 *
 * @author maartenhus
 */
public abstract class GObject implements DrawAble {
	protected Game game;
	protected boolean alive = true;

	protected int x;
	protected int y;
	protected int z = 0;
	
	protected int width;
	protected int height;

	protected boolean isCollision = true;
	protected boolean liquid = false;
	protected boolean screenAdjust = true;

	protected Sprite sprite;
	protected static HashMap<String, Rectangle> spriteLoc = new HashMap<String, Rectangle>();
	protected String[] animation;
	protected int animationCounter = 0;
	protected long animationInterval;
	protected long lastAnimation = System.currentTimeMillis();

	public GObject(Game game, int x, int y, int width, int height, String image) {
		animationInterval = 90;
		this.game = game;

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		sprite = new Sprite(image);
	}

	/**
	 * What the GObject is supposed to do in the loop.
	 */
	public void doInLoop() {};

	/**
	 * What the GObject needs to do pre animation.
	 */
	protected void preAnimation() {};

	/**
	 * What the GObject needs to do post animation.
	 */
	protected void postAnimation() {};

	/**
	 * What the GObject does when it has a collision.
	 * @param hitObject
	 */
	protected void collision(GObject hitObject) {};
    protected void wallCollision() {};
    
	public void animate() {
		if (System.currentTimeMillis() > lastAnimation + animationInterval) {
			if (animationCounter == animation.length) {
				animationCounter = 0;
			}

			try {
				sprite.setSprite(spriteLoc.get(animation[animationCounter]));
			} catch (Exception e) {
				animationCounter = 0;
			}

			animationCounter += 1;
			lastAnimation = System.currentTimeMillis();

			postAnimation();
		}
	}

	public void draw(Graphics2D g) {
		Image img = sprite.getImage();
		g.drawImage(img, x, y, sprite.getWidth(), sprite.getHeight(), null);
	}

	private boolean isCollision(int newX, int newY) {
		Rectangle rect = new Rectangle(newX, newY, width, height);
		boolean collision = false;

		for (Polygon poly : game.getScene().getSolids()) {
			final Area area = new Area();
			area.add(new Area(rect));
			area.intersect(new Area(poly));

			if (!area.isEmpty()) {
				collision = true;
                wallCollision();
			}
		}

		for (GObject obj : game.getScene().getGObjects()) {
			if (obj.isCollision()) {
				final Area area = new Area();
				area.add(new Area(rect));
				area.intersect(new Area(obj.getRectangle()));

				if (!area.isEmpty() && this != obj) {
					collision(obj);
					obj.collision(this);

					if (!obj.isLiquid()) {
						collision = true;
					}
				}
			}
		}

		return collision;
	}

	public int getX() {
		return x;
	}

	public void setX(int newX) {
		if (!isCollision || !isCollision(newX, y)) {
			x = newX;
		}
	}

	public int getY() {
		return y;
	}

	public void setY(int newY) {
		if (!isCollision || !isCollision(x, newY)) {
			y = newY;
		}
	}

	public int getZ() {
		return z;
	}

    public void setYHardCore(int y) {
		this.y = y;
	}

    public void setXHardCore(int x) {
		this.x = x;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Game getGame() {
		return game;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}

	public String[] getAnimation() {
		return animation;
	}

	public void setAnimation(String[] animation) {
		this.animation = animation;
	}

	public void resetAnimationCounter() {
		animationCounter = 0;
	}

	public int getAnimationCounter() {
		return animationCounter;
	}

	public long getAnimationInterval() {
		return animationInterval;
	}

	public void setAnimationInterval(long animationInterval) {
		this.animationInterval = animationInterval;
	}

	public boolean isCollision() {
		return isCollision;
	}

	public void setCollision(boolean collision) {
		this.isCollision = collision;
	}

	public boolean isLiquid() {
		return liquid;
	}

	public void setLiquid(boolean liquid) {
		this.liquid = liquid;
	}

	public boolean isScreenAdjust() {
		return screenAdjust;
	}

	public void setScreenAdjust(boolean screenAdjust) {
		this.screenAdjust = screenAdjust;
	}

    public void randomGoodie() {
        int r = (int) (Math.random() * 200);

        if (r < 50) {
            if (r < 25) {
                game.getScene().addNewGObject(new Heart (game, x, y));
            } else {
                game.getScene().addNewGObject(new Rupee (game, x, y));
            }
        }
    }
}
