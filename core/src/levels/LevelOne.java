package levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class LevelOne extends levelGenerator {

    public LevelOne(String level) {
        super(level);
    }

    public static Array<Vector2> levelOnePath(){
        Array<Vector2> path = new Array<Vector2>();
        path.add(new Vector2(0, 150));
        path.add(new Vector2(250, 150));
        path.add(new Vector2(360, 175));
        path.add(new Vector2(410, 225));
        path.add(new Vector2(440, 300));
        path.add(new Vector2(500, 360));
        path.add(new Vector2(525, 375));
        path.add(new Vector2(625, 350));
        path.add(new Vector2(700, 325));
        path.add(new Vector2(750, 325));
        path.add(new Vector2(800, 375));
        path.add(new Vector2(850, 500));
        path.add(new Vector2(900, 550));
        path.add(new Vector2(1000, 550));
        path.add(new Vector2(1100, 550));
        path.add(new Vector2(1200, 500));
        path.add(new Vector2(1400, 500));
        return path;
    }

}
