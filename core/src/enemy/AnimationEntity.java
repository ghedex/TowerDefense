package enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationEntity {

    private TextureAtlas atlas;

    public AnimationEntity(String path){
        atlas = new TextureAtlas(Gdx.files.internal(path));
        Animation animation = new Animation(1 / 30f, atlas.getRegions());
    }


}
