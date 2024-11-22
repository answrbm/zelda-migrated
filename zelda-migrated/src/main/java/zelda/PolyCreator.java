package zelda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zelda.engine.Game;

import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class is a 'development' only class. It lets you get coordinates on the 'screen'
 * these can be used to populate the solid objects in the Scene's.
 *
 * @author maartenhus
 */
public class PolyCreator extends MouseAdapter {
	private Logger logger;
	private Game game;
	private Polygon poly = new Polygon();

	public PolyCreator(Game game) {
		this.logger = LoggerFactory.getLogger(PolyCreator.class);
		this.game = game;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.isControlDown()) {
			logger.info("int[] xpos = {");
			for (int i = 0; i < poly.xpoints.length; i++) {
				logger.info(poly.xpoints[i] + ", ");
			}
			logger.info("};");

			logger.info("int[] ypos = {");
			for (int i = 0; i < poly.ypoints.length; i++) {
				logger.info(poly.ypoints[i] + ", ");
			}
			logger.info("};");
		} else {
			int x = game.getScene().getSprite().getX() + e.getX();
			int y = game.getScene().getSprite().getY() + e.getY();

			if(x != 0 && y != 0) {
				poly.addPoint(x, y);
				logger.info("Relative:" + x + " " + y);
				logger.info("Real:" + e.getX() + " " + e.getY());
			}
		}
	}
}
