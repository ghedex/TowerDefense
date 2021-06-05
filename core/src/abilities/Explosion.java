package abilities;

import enemy.Entity;

public class Explosion extends Entity {

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    public Explosion(){
        super(WIDTH, HEIGHT, 1, "core/assets/abilities/abilitesSkin/explosion/explosion.atlas");
    }

}
