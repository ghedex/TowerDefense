package enemy.scorpionEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import enemy.Entity;


public class Scorpion extends Sprite {

    public static final int WIDTH = 75;
    public static final int HEIGHT = 75;

    Texture tex;
    Sprite spr;

    public Scorpion() {
        //super(WIDTH, HEIGHT, 50, 100, "assetsPack/scorpions/scorpionRunning/scorpionPack.atlas");
        //tex = new Texture("core/assets/2_enemies_1_attack_000.png");
        //spr = new Sprite(tex);
        super( new Texture( Gdx.files.internal( "core/assets/assetsPack/scorpions/scorpionRunning/1_enemies_1_run_000.png" ) ) );
        //spr = new Sprite(new Texture("core/assets/2_enemies_1_attack_000.png"));
        super.setSize(90, 90);
        //super.setPosition( posX, posY );
    }

    public void update(float delta){
        this.setPosition(30 * delta, 20 * delta);
    }


    //TO DO: Skorpion muss schon animiert sein, damit ich nur den Skorpion callen muss

}
