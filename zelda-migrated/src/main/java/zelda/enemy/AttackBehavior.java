package zelda.enemy;

import zelda.character.Direction;
import zelda.character.Character;
import zelda.link.Link;

/**
 *
 * @author vincentklarholz
 */
public class AttackBehavior extends Behavior {

    private Character soldier;
    private Link link;

    private int valueX;
    private int valueY;

	public AttackBehavior(Character soldier) {
		this.soldier = soldier;
        link = soldier.getGame().getLink();
	}

	public void behave() {
        valueX = Math.abs(link.getX() - soldier.getX());
        valueY = Math.abs(link.getY() - soldier.getY());

        if (valueX < valueY) {
            if (link.getY() < soldier.getY()) {
                soldier.setY(soldier.getY() - 1);
                if (soldier.getDirection() != Direction.UP) {
                    soldier.setDirection(Direction.UP);
                }

            } else if (link.getY() > soldier.getY()) {
                soldier.setY(soldier.getY() + 1);
                if (soldier.getDirection() != Direction.DOWN) {
                    soldier.setDirection(Direction.DOWN);
                }
            }
        } else {
            if (link.getX() < soldier.getX()) {
                soldier.setX(soldier.getX() - 1);
                if (soldier.getDirection() != Direction.LEFT) {
                  soldier.setDirection(Direction.LEFT);
                }
            } else if(link.getX() > soldier.getX()) {
                soldier.setX(soldier.getX() + 1);
                if (soldier.getDirection() != Direction.RIGHT) {
                    soldier.setDirection(Direction.RIGHT);
                }
            }
        }

        if (link.getX() < soldier.getX()) {
            soldier.setX(soldier.getX() - 1);
        } else if(link.getX() > soldier.getX()) {
            soldier.setX(soldier.getX() + 1);
        }

        if (link.getY() < soldier.getY()) {
            soldier.setY(soldier.getY() - 1);
        } else if(link.getY() > soldier.getY()) {
            soldier.setY(soldier.getY() + 1);
        }
    }
}
