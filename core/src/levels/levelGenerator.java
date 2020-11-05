package levels;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import enemy.scorpionEntity.Scorpion;

import java.util.ArrayList;
import java.util.Vector;

public class levelGenerator extends ApplicationAdapter {
    private final String level;

    SpriteBatch batch;
    Texture img;
    Texture scorp;
    public levelGenerator(String level){
        this.level = level;
    }

    Scorpion scorpionMob = new Scorpion();
    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture(level);
        scorp = new Texture(scorpionMob.entityFile);

    }
    @Override
    public void render () {
        batch.begin();
        //last both params allow to scale image to window size
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(scorp, 0,125, 90, 90); //need to create algorithm to compute size and position
        batch.end();
        /*Following Code is just for pathfinding)*/
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            Array<Integer> coords = new Array<>();
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();

            coords.add(x, y);
            System.out.println(coords);
        }

        /* Path for Enemy-Map1 (oberer Weg): [197, 563]
        [356, 553]
        [413, 507]
        [432, 419]
        [501, 353]
        [605, 352]
        [692, 402]
        [770, 374]
        [801, 316]
        [816, 233]
        [893, 170]
        [1009, 158]
        [1109, 165]
        [1193, 202]
        [1261, 229]*/

    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
        scorp.dispose();
    }
}
