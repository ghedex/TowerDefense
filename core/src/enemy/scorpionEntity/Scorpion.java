package enemy.scorpionEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import enemy.Entity;
import levels.LevelOne;
import levels.PathfindingEnemy;


public class Scorpion extends Entity {


    public Scorpion() {

        super(90, 90, 100, "core/assets/assetsPack/scorpions/scorpionRunning/scorpionPack.atlas");

        super.setSize(90, 90);
        super.setPosition(LevelOne.levelOnePath().first().x, LevelOne.levelOnePath().first().y);
        //super.setPosition(posX, posY);

    }


    //TO DO: Skorpion muss schon animiert sein, damit ich nur den Skorpion callen muss

}
