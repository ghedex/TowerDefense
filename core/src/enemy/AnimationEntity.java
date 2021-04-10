package enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationEntity extends Sprite {

    private TextureAtlas atlas;
    public String PATH;

    public AnimationEntity(String path){
        atlas = new TextureAtlas(Gdx.files.internal(path));
        Animation animation = new Animation(1 / 30f, atlas.getRegions());
    }

    public Object returnPath(){
        return Gdx.files.internal(this.PATH);
    }


}