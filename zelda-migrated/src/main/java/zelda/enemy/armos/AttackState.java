/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zelda.enemy.armos;

import zelda.character.Character;
import zelda.character.CharacterState;

/**
 *
 * @author Christiaan
 */
public class AttackState extends CharacterState {

    private final String[] animation = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    public AttackState(Character armosKnight) {
        super(armosKnight);
        name = "AttackState";

        armosKnight.setAnimationInterval(90);
    }

    public void handleInput() {
        switch (character.getDirection()) {
            case UP, DOWN, LEFT, RIGHT -> handleAnimation();
        }
    }

    @Override
    public void handleAnimation() {
        character.setAnimation(animation);
    }
}
