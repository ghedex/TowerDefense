package MainRef;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {
    public static final AssetManager manager = new AssetManager();
    //backgroundImages
    public static final String mainMenuBackground = "core/assets/menuAssets/mainMenuAssets/bg2.png";
    public static final String levelSelectionBackground = "core/assets/menuAssets/mainMenuAssets/menuSkin/LevelSelection/bgLevelSelection.png";
    public static final String tutorialBackground = "core/assets/menuAssets/mainMenuAssets/menuSkin/Tutorial/tutorialBackground.png";
    //music/sounds
    public static final String buttonClickSound = "core/assets/menuAssets/mainMenuAssets/buttonAssets/buttonClick.mp3";
    public static final String levelOneBackgroundMusic = "core/assets/menuAssets/mainMenuAssets/music/levelOneBackgroundMusic.mp3";
    public static final String gameOverSound = "core/assets/menuAssets/mainMenuAssets/music/gameOver.mp3";
    public static final String victorySound = "core/assets/menuAssets/mainMenuAssets/music/victoryPlaceholder.mp3";
    public static final String hitMarkerSound = "core/assets/menuAssets/mainMenuAssets/music/hitSoundEffect.mp3";
    public static final String scorpionDeathSound = "core/assets/assetsPack/scorpions/scorpionDeath/deathSound.mp3";
    public static final String bossLevelOneMusic = "core/assets/menuAssets/mainMenuAssets/music/BossMusicLevelOne.mp3";
    //background / enemies -> Level 1
    public static final String levelOneBackground = "core/assets/game_background_1.png";
    public static final String scorpionEnemy = "core/assets/assetsPack/scorpions/scorpionRunning/scorpionPack.atlas";
    public static final String wizardEnemy = "core/assets/assetsPack/wizard/wizardRun/wizardRun.atlas";
    public static final String levelOneBossWalk = "core/assets/assetsPack/levelOneBoss/bossWalk.atlas"; // tree
    public static final String levelOneBossSapling = "core/assets/assetsPack/levelOneBoss/bossSapling.atlas"; //sapling

    //background / enemies -> Level 2
    public static final String levelTwoBackground = "core/assets/background/levelBackgrounds/level_2_background.png";
    public static final String impEnemy = "core/assets/assetsPack/imp/impWalking.atlas";
    public static final String warriorEnemy = "core/assets/assetsPack/warrior/warriorWalking.atlas";
    public static final String levelTwoBossCommander = "core/assets/assetsPack/levelTwoBoss/bossWarriorWalking.atlas";

    //background / enemies -> level 3
    public static final String levelThreeBackground = "core/assets/level_3_background.png";
    public static final String yetiBoss = ""; // yeti -> ice warrior

    //HUD -> Ingame Buttons / Skins
    public static final String menuSkin = "core/assets/menuAssets/mainMenuAssets/menuSkin/menuSkin/menuSkin.atlas";
    public static final String towerPack = "core/assets/background/tower/towerPack/towerPack.atlas";
    public static final String snowTowerPack = "core/assets/background/tower/snowTowerPack/snowTowerSkin.atlas";
    public static final String snowPillarArcherPack = "core/assets/background/tower/snowTowerPack/snowArcherTowerPack.atlas";
    public static final String fireAbilityPack = "core/assets/abilities/abilitesSkin/fire/fireAbilitySkin.atlas";
    public static final String timeAbilityPack = "core/assets/abilities/abilitesSkin/time/timeAbility.atlas";

    public static void load(){
        //load backgroundImages
        manager.load(mainMenuBackground, Texture.class);
        manager.load(levelSelectionBackground, Texture.class);
        manager.load(tutorialBackground, Texture.class);
        //load buttonImages
        manager.load(menuSkin, TextureAtlas.class);
        //load sounds
        manager.load(buttonClickSound, Sound.class);
        manager.load(gameOverSound, Sound.class);
        manager.load(victorySound, Sound.class);
        manager.load(hitMarkerSound, Sound.class);
        manager.load(scorpionDeathSound, Sound.class);

        //load music
        manager.load(levelOneBackgroundMusic, Music.class);
        manager.load(bossLevelOneMusic, Music.class);
    }
    public static void loadLevelOne(){
        //load HUD -> menuItems
        manager.load(towerPack, TextureAtlas.class);
        manager.load(fireAbilityPack, TextureAtlas.class);
        manager.load(levelOneBackground, Texture.class);
        //load enemies
        manager.load(scorpionEnemy, TextureAtlas.class);
        manager.load(wizardEnemy, TextureAtlas.class);
        manager.load(levelOneBossWalk, TextureAtlas.class);
        manager.load(levelOneBossSapling, TextureAtlas.class);
        //load music
        manager.load(levelOneBackgroundMusic, Music.class);
        manager.load(bossLevelOneMusic, Music.class);
    }
    public static void loadLevelTwo(){
        //load HUD -> menuItems
        manager.load(towerPack, TextureAtlas.class);
        manager.load(fireAbilityPack, TextureAtlas.class);
        manager.load(levelTwoBackground, Texture.class);
        manager.load(timeAbilityPack, TextureAtlas.class);
        //load enemies
        manager.load(impEnemy, TextureAtlas.class);
        manager.load(warriorEnemy, TextureAtlas.class);
        manager.load(levelTwoBossCommander, TextureAtlas.class);
        //load music
        manager.load(levelOneBackgroundMusic, Music.class);
        manager.load(bossLevelOneMusic, Music.class);
    }
    public static void loadLevelThree(){
        manager.load(towerPack, TextureAtlas.class);
        manager.load(snowTowerPack, TextureAtlas.class);
        manager.load(snowPillarArcherPack, TextureAtlas.class);
        manager.load(fireAbilityPack, TextureAtlas.class);
        manager.load(levelThreeBackground, Texture.class);
    }
    public static void dispose(){
        manager.dispose();
    }
    public static void menuDispose(){
        manager.unload(menuSkin);
        manager.unload(mainMenuBackground);
        manager.unload(levelSelectionBackground);
        manager.unload(tutorialBackground);
    }
    public static void levelOneDispose(){
        manager.unload(towerPack);
        manager.unload(fireAbilityPack);
        manager.unload(levelOneBackground);
        //unload enemies
        manager.unload(scorpionEnemy);
        manager.unload(wizardEnemy);
        manager.unload(levelOneBossWalk);
        manager.unload(levelOneBossSapling);
    }
    public static void levelTwoDispose(){
        manager.unload(towerPack);
        manager.unload(fireAbilityPack);
        manager.unload(timeAbilityPack);
        manager.unload(levelTwoBackground);
        //unload enemies
        manager.unload(impEnemy);
        manager.unload(warriorEnemy);
        manager.unload(levelTwoBossCommander);
    }
    public static void levelThreeDispose(){
        manager.unload(towerPack);
        manager.unload(snowTowerPack);
        manager.unload(fireAbilityPack);
        manager.unload(levelThreeBackground);
        //unload enemies
    }
}
