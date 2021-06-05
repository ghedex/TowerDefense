package MainRef;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static final AssetManager manager = new AssetManager();
    //backgroundImages
    public static final String mainMenuBackground = "core/assets/menuAssets/mainMenuAssets/bg2.png";
    public static final String levelSelectionBackground = "core/assets/menuAssets/mainMenuAssets/menuSkin/LevelSelection/bgLevelSelection.png";
    public static final String tutorialBackground = "core/assets/menuAssets/mainMenuAssets/menuSkin/Tutorial/tutorialBackground.png";
    //menuButtonImages
    public static final String menuStartButton = "core/assets/menuAssets/mainMenuAssets/buttonAssets/button_play.png";
    public static final String menuSoundButton = "core/assets/menuAssets/mainMenuAssets/buttonAssets/button_sound.png";
    public static final String menuCloseButton = "core/assets/menuAssets/mainMenuAssets/menuSkin/LevelSelection/button_close.png";
    public static final String menuReturnButton = "core/assets/menuAssets/mainMenuAssets/menuSkin/LevelSelection/button_left.png";
    public static final String levelOneButton = "core/assets/menuAssets/mainMenuAssets/menuSkin/LevelSelection/level1.png";
    public static final String levelTwoButton = "core/assets/menuAssets/mainMenuAssets/menuSkin/LevelSelection/level2ButtonImage.png";
    public static final String menuTutorialButton = "core/assets/menuAssets/mainMenuAssets/buttonAssets/tutorialButton.png";
    //music/sounds
    public static final String buttonClickSound = "core/assets/menuAssets/mainMenuAssets/buttonAssets/buttonClick.mp3";
    public static final String levelOneBackgroundMusic = "core/assets/menuAssets/mainMenuAssets/music/levelOneBackgroundMusic.mp3";
    public static final String gameOverSound = "core/assets/menuAssets/mainMenuAssets/music/gameOver.mp3";
    public static final String victorySound = "core/assets/menuAssets/mainMenuAssets/music/victoryPlaceholder.mp3";
    public static final String hitMarkerSound = "core/assets/menuAssets/mainMenuAssets/music/hitSoundEffect.mp3";
    public static final String scorpionDeathSound = "core/assets/assetsPack/scorpions/scorpionDeath/deathSound.mp3";
    //enemies
    public static final String scorpionEnemy = "core/assets/assetsPack/scorpions/scorpionRunning/scorpionPack.atlas";
    public static final String wizardEnemy = "core/assets/assetsPack/wizard/wizardRun/wizardRun.atlas";
    public static final String levelOneBossWalk = "core/assets/assetsPack/levelOneBoss/bossWalk.atlas"; // tree
    public static final String levelOneBossTransformation = "core/assets/assetsPack/levelOneBoss/bossTransformation.atlas"; // tree -> sapling
    public static final String levelOneBossSapling = "core/assets/assetsPack/levelOneBoss/bossSapling.atlas"; //sapling
    public static final String yetiBoss = ""; // yeti -> ice warrior
    //HUD -> Ingame Buttons / Skins
    public static final String towerPack = "core/assets/background/tower/towerPack/towerPack.atlas";
    public static final String fireAbilityPack = "core/assets/abilities/abilitesSkin/fire/fireAbilitySkin.atlas";

    public static void load(){
        //load backgroundImages
        manager.load(mainMenuBackground, Texture.class);
        manager.load(levelSelectionBackground, Texture.class);
        manager.load(tutorialBackground, Texture.class);
        //load buttonImages
        manager.load(menuStartButton, Texture.class);
        manager.load(menuCloseButton, Texture.class);
        manager.load(menuReturnButton, Texture.class);
        manager.load(menuTutorialButton, Texture.class);
        manager.load(menuSoundButton, Texture.class);
        manager.load(levelOneButton, Texture.class);
        manager.load(levelTwoButton, Texture.class);
        //load sounds
        manager.load(buttonClickSound, Sound.class);
        manager.load(gameOverSound, Sound.class);
        manager.load(victorySound, Sound.class);
        manager.load(hitMarkerSound, Sound.class);
        manager.load(scorpionDeathSound, Sound.class);
        //load music
        manager.load(levelOneBackgroundMusic, Music.class);
    }
    public static void loadLevelOne(){
        //load HUD -> menuItems
        manager.load(towerPack, TextureAtlas.class);
        manager.load(fireAbilityPack, TextureAtlas.class);
        //load enemies
        manager.load(scorpionEnemy, TextureAtlas.class);
        manager.load(wizardEnemy, TextureAtlas.class);
        manager.load(levelOneBossWalk, TextureAtlas.class);
        manager.load(levelOneBossTransformation, TextureAtlas.class);
        manager.load(levelOneBossSapling, TextureAtlas.class);
    }
    public static void dispose(){
        manager.dispose();
    }
    public static void levelOneDispose(){
        manager.get(towerPack, TextureAtlas.class).dispose();
        manager.get(fireAbilityPack, TextureAtlas.class).dispose();
        //load enemies
        manager.get(scorpionEnemy, TextureAtlas.class).dispose();
        manager.get(wizardEnemy, TextureAtlas.class).dispose();
        manager.get(levelOneBossWalk, TextureAtlas.class).dispose();
        manager.get(levelOneBossTransformation, TextureAtlas.class).dispose();
        manager.get(levelOneBossSapling, TextureAtlas.class).dispose();
    }
}
