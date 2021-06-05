package MainRef;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Prefs {
    private final Preferences prefs;
    private boolean soundOn;

    public Prefs(){
        prefs = Gdx.app.getPreferences("My Preferences");
        soundOn = prefs.getBoolean("soundOn", soundOn);

    }
    public void setSound(boolean soundOn){
        this.soundOn = soundOn;
        prefs.putBoolean("soundOn", soundOn);
        prefs.flush();
    }
    public boolean isSoundOn(){
        return soundOn;
    }
}
