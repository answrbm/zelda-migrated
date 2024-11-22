package zelda;

import zelda.engine.GObject;
import zelda.engine.Game;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The Controller is reponsible for the gameloop.
 * And it handles user keyinput for the game.
 *
 * @author maartenhus
 */
public class Controller implements Runnable, KeyListener {
	private Thread thread;
	private Game game;
	private View view;

	public Controller(Game game, View view, JFrame frame) {
		this.game = game;
		this.view = view;

		frame.addMouseListener(new PolyCreator(game));
		frame.addKeyListener(this);

		thread = new Thread(this, "GameLoop");
		thread.start();
	}

	public void run() {
		while (game.isRunning()) {
			try {
				if (!game.isPaused()) {
					game.getScene().handleInput();
						
					game.getLink().handleInput();

					for (GObject obj : game.getScene().getGObjects()) {
						obj.doInLoop();
					}
				}

                try {
                    view.draw();
                } catch (Exception e) {
					e.printStackTrace();
				}
				
				Thread.sleep(game.getGameSpeed());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		view.exitFullScreen();
		game.quit();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			game.setRunning(false);
		}

		if (e.getKeyCode() == KeyEvent.VK_P) {
			game.setPaused(!game.isPaused());
		}

		switch (e.getKeyCode()) {
			case KeyEvent.VK_A -> game.setaPressed(true);
			case KeyEvent.VK_D -> game.setdPressed(true);
			case KeyEvent.VK_W -> game.setwPressed(true);
			case KeyEvent.VK_S -> game.setsPressed(true);
			case KeyEvent.VK_J -> game.setjPressed(true);
			case KeyEvent.VK_K -> game.setkPressed(true);
			case KeyEvent.VK_L -> game.setlPressed(true);
			case KeyEvent.VK_ENTER -> game.setEnterPressed(true);
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_A -> game.setaPressed(false);
			case KeyEvent.VK_D -> game.setdPressed(false);
			case KeyEvent.VK_W -> game.setwPressed(false);
			case KeyEvent.VK_S -> game.setsPressed(false);
			case KeyEvent.VK_J -> game.setjPressed(false);
			case KeyEvent.VK_K -> game.setkPressed(false);
			case KeyEvent.VK_L -> game.setlPressed(false);
			case KeyEvent.VK_ENTER -> game.setEnterPressed(false);
		}
	}

	public void keyTyped(KeyEvent e) {}
}
