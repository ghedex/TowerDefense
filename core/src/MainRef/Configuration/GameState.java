package MainRef.Configuration;

import com.badlogic.gdx.Gdx;

public class GameState {
    public enum game_state {
        PAUSED, RUNNING
    }
    public enum level_completion_state {
        LEVEL_ONE_CLEARED(true),
        LEVEL_TWO_CLEARED(true),
        LEVEL_THREE_CLEARED(true);

        level_completion_state(boolean b) {
            Gdx.app.log("Hey", String.valueOf(b));
        }
    }
    void updateGameState(){

    }
}
