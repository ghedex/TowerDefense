package MainRef;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Prefs {
    private final Preferences prefs;
    private boolean soundOn;
    private int levelsFinished = 0;

    public Prefs(){
        prefs = Gdx.app.getPreferences("My Preferences");
        soundOn = prefs.getBoolean("soundOn", soundOn);
        levelsFinished = prefs.getInteger("levelsFinished", levelsFinished);
    }
    public void setSound(boolean soundOn){
        this.soundOn = soundOn;
        prefs.putBoolean("soundOn", soundOn);
        prefs.flush();
    }
    public void setLevelsFinished(int currentLevel){
        this.levelsFinished = currentLevel;
        prefs.putInteger("levelsFinished", levelsFinished);
        prefs.flush();
    }
    public int getLevelsFinished(){
        return levelsFinished;
    }
    public boolean isSoundOn(){
        return soundOn;
    }
}
