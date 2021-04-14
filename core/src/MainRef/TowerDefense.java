package MainRef;

import com.badlogic.gdx.Game;
import levels.menu.testMainMenu;

public class TowerDefense extends Game {

    @Override
    public void create() {
        this.setScreen(new testMainMenu(this));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
