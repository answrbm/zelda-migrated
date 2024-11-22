package zelda;

import zelda.engine.GObject;
import zelda.engine.Game;
import zelda.scene.ZeldaScene;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsConfiguration;
import java.awt.DisplayMode;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * This class handles all the drawing.
 *
 * @author maartenhus
 */
public class View {
	private Game game;

	private BufferStrategy buffer;
	private BufferedImage bi;
	private GraphicsDevice gd;

	private int displayWidth   = 800;
	private int displayHeight  = 600;

	private int x;
	private int y;

	public View(Game game, JFrame frame) {
		this.game = game;

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();

		if (!game.isDebug()) {
			gd.setFullScreenWindow(frame);
		}

		if (gd.isDisplayChangeSupported() && !game.isDebug()) {
			gd.setDisplayMode(new DisplayMode(displayWidth, displayHeight, 32, 60));
		}

		frame.createBufferStrategy(2);
		frame.setBackground(Color.BLACK);
		buffer = frame.getBufferStrategy();
		bi = gc.createCompatibleImage(game.getWidth(), game.getHeight());
		
		if (!game.isDebug()) {
			x = (displayWidth - game.getWidth()) /2;
			y = (displayHeight - game.getHeight()) /2;
		}
	}	

	public void draw() {
		Graphics graphics = buffer.getDrawGraphics();
		Graphics2D g2 = bi.createGraphics();

		g2.setColor(Color.black);
		g2.fillRect(0, 0, bi.getWidth(), bi.getHeight());

		game.getScene().draw(g2);

		g2.setColor(Color.red);

		for (GObject obj : game.getScene().getGObjects()) {
			if (game.isDebug())
				g2.draw(obj.getRectangle());

			if (!game.isPaused()) {
				obj.animate();
			} else {
				g2.setColor(Color.white);
				g2.drawString("-- Pauzed --", game.getWidth() / 2 - 30, game.getHeight() / 2);
				g2.setColor(Color.red);
			}

			obj.draw(g2);
		}

		if (game.isDebug()) {
			for (Shape s : game.getScene().getSolids()) {
				g2.draw(s);
			}

			for (Rectangle r : game.getScene().getHitters()) {
				g2.setColor(Color.blue);
				g2.draw(r);
			}

			for (Shape v : game.getScene().getEyeViews()) {
				g2.setColor(Color.green);
				g2.draw(v);
			}

			if (game.getScene() instanceof ZeldaScene zeldaScene) {
				for (Shape v : zeldaScene.getExits()) {
					g2.setColor(Color.magenta);
					g2.draw(v);
				}
			}
		}

		if (!game.isDebug()) {
			graphics.drawImage(bi, x, y, null);
		} else {
			graphics.drawImage(bi, 0, 0, null);
		}

		if (!buffer.contentsLost())
			buffer.show();


		graphics.dispose();
		g2.dispose();
	}

	public void exitFullScreen() {
		gd.setFullScreenWindow(null);
	}
}