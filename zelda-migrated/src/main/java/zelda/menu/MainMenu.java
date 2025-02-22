package zelda.menu;

import java.awt.Rectangle;
import zelda.engine.Game;
import zelda.engine.Scene;
import zelda.scene.HouseScene;

/**
 *
 * @author maartenhus
 */
public class MainMenu extends Scene {
	private Fairy fairy = new Fairy(game, 210, 215);

	private static int CURRENT = 0;
	private static final int NEW_GAME = 0;
	private static final int LOAD_GAME = 1;
	private static final int HELP = 2;

	private long lastInput = System.currentTimeMillis();

	public MainMenu(Game game) {
		super(game, "/images/main-menu.png", "MainMenu");

		sprite.setSprite(new Rectangle(0, 0, game.getWidth(), game.getHeight()));

		gameObjects.add(fairy);

		game.playMusic("/sounds/main-menu.mp3", false);

//		try {
//			game.stopMusic();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public void handleInput() {
		long inputInterval = 100;
		if (System.currentTimeMillis() > lastInput + inputInterval) {
			checkEnter();
			checkInput();
			setFairy();
			lastInput = System.currentTimeMillis();
		}
	}

	private void checkEnter() {
		if (game.isEnterPressed()) {
			switch (CURRENT) {
				case NEW_GAME -> game.setScene(new HouseScene(game, "GameStart"));
				case LOAD_GAME -> game.load();
				case HELP -> game.setScene(new HelpMenu(game));
			}
		}
	}

	private void checkInput() {
		if (game.issPressed()) {
			if (CURRENT == HELP) {
				CURRENT = NEW_GAME;
			} else {
				CURRENT += 1;
			}
		}

		if (game.iswPressed()) {
			if (CURRENT == NEW_GAME) {
				CURRENT = HELP;
			} else {
				CURRENT -= 1;
			}
		}
	}

	private void setFairy() {
		switch (CURRENT) {
			case NEW_GAME -> fairy.setY(220);
			case LOAD_GAME -> fairy.setY(285);
			case HELP -> fairy.setY(348);
		}
	}
}
