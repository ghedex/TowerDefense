package enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;

public class AnimationEntity {
    String atlasPath;



    private Animation<TextureRegion> animation;
    private TextureAtlas entityAtlas;
    private float timePassed = 0;
    SpriteBatch batch;

    public AnimationEntity(String atlasPath) {
        this.atlasPath = atlasPath;
    }

    public void createAnimation(){
        entityAtlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        animation = new Animation<TextureRegion>(1/40f, entityAtlas.getRegions());
    }
    public void renderAnimation(int x, int y){
        batch = new SpriteBatch();
        timePassed += Gdx.graphics.getDeltaTime();
        TextureRegion currentIdleFrame = animation.getKeyFrame(timePassed, true);
        batch.begin();
        batch.draw(currentIdleFrame, x, y);
        batch.end();

    }
    public void disposeAnimation(){
        entityAtlas.dispose();
        batch.dispose();
    }



}
