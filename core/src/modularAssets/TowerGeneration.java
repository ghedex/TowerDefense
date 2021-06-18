package modularAssets;

import MainRef.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import levels.PathfindingEnemy;
import levels.utils.Coin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class TowerGeneration {
    Window tower, archerTower;
    ClickListener towerPlacementListener, towerListener;
    LinkedList<ImageButton> towerList = new LinkedList<>();
    ArrayList<ImageButton> pillarTower = new ArrayList<>();
    private ArrayList<Boolean> towerCircleBool = new ArrayList<>();
    private ImageButtonStyle towerStyle;
    private float[] towerLocation_x;
    private float[] towerLocation_y;
    ArrayList<Float> towerCircle_x = new ArrayList<>();
    ArrayList<Float> towerCircle_y = new ArrayList<>();
    LinkedList<Circle> towerCollisionCircle = new LinkedList<>();
    LinkedList<ShapeRenderer> towerCollisionCircleRenderer = new LinkedList<>();
    ArrayList<ImageButton> towerArrayList = new ArrayList<>();
    ArrayList<Boolean> doesCircleExist = new ArrayList<>();
    ArrayList<Float> towerDamage = new ArrayList<>();
    Skin uiSkin = new Skin(Gdx.files.internal("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.json"), new TextureAtlas("menuAssets/mainMenuAssets/menuSkin/skin/uiskin.atlas"));

    public Window TowerMenuWindow(final LinkedList<ImageButton> towerMenuList, String name, ImageButtonStyle[] towerMenuSkins, Stage stage) {
        Window towerSelectWindow = new Window("Choose a tower to place", uiSkin);
        towerSelectWindow.setName(name);
        towerSelectWindow.setVisible(false);
        towerSelectWindow.setPosition(500,500);
        TextButton continueButton = new TextButton("Cancel", uiSkin);
        final Window finalTowerSelectWindow = towerSelectWindow;
        final Window finalTowerSelectWindow1 = towerSelectWindow;
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                for (ImageButton imageButton: towerMenuList){
                    imageButton.setChecked(false);
                }
                finalTowerSelectWindow.setVisible(!finalTowerSelectWindow1.isVisible());
            }
        });
        for (int i = 0; i <= 2; i++) {
            towerMenuList.add(i, new ImageButton(towerMenuSkins[i]));
            final int finalI = i;
            towerMenuList.get(i).addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                    Gdx.app.log("towerList: ", String.valueOf(finalI));
                }
            });
        }
        for (ImageButton towerImage : towerMenuList) {
            towerSelectWindow.add(towerImage);
        }
        towerSelectWindow.add(continueButton);
        towerSelectWindow.pack();
        stage.addActor(towerSelectWindow);
        tower = towerSelectWindow;
        return towerSelectWindow;
    }

    public void setTowerMenuList(LinkedList<ImageButton> towerMenuList) {
        towerList = towerMenuList;
    }

    public void setTowerStyle(ImageButtonStyle TowerStyle) {
        towerStyle = TowerStyle;
    }

    public void setTowerLocation(float[] location_x, float[] location_y) {
        towerLocation_x = location_x;
        towerLocation_y = location_y;
    }

    public void createTowers(final Window towerMenuList, int width, int size, final ArrayList<Integer> coinCost, final ArrayList<String> name, final LinkedList<ImageButtonStyle> towerImageStyle, Stage stage, final float radius) {
        for (int i = 0; i <= towerLocation_x.length - 1; i++) {
            doesCircleExist.add(i, false);
        }
        for (int i = 0; i <= towerLocation_x.length - 1; i++) {
            towerCircle_x.add(towerLocation_x[i]);
            towerCircle_y.add(towerLocation_y[i]);
        }

        towerListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                Gdx.app.log("towerListener", "");
            }
        };
        for (int i = 0; i <= towerLocation_x.length - 1; i++) {
            towerCollisionCircle.add(i, new Circle());
            towerCollisionCircleRenderer.add(i, new ShapeRenderer());
        }

        //this loop is for the tower ground
        for (int i = 0; i <= towerLocation_x.length - 1; i++) {
            towerArrayList.add(i, new ImageButton(towerStyle));
            towerArrayList.get(i).setPosition(towerLocation_x[i], towerLocation_y[i]);
            towerArrayList.get(i).setSize(width, size); //Level 3 -> 85 85 pillar
            //pillarArcherTower.get(i).setDebug(true);
            final int finalI = i;
            towerArrayList.get(i).addListener(towerPlacementListener = new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    Assets.manager.get(Assets.buttonClickSound, Sound.class).play(0.5f);
                    if (towerArrayList.get(finalI).isChecked()) {
                        towerArrayList.get(finalI).setChecked(false);
                        towerMenuList.setVisible(!towerMenuList.isVisible());
                        Gdx.app.log("towerList: ", String.valueOf(finalI));
                        Iterator<ImageButtonStyle> styleIterator = towerImageStyle.iterator();
                        Iterator<Integer> coinIterator = coinCost.iterator();
                        Iterator<String> nameIterator = name.iterator();
                        for (Iterator<ImageButton> iterator = towerList.iterator(); iterator.hasNext(); ) {
                            ImageButton towerIerator = iterator.next();
                            ImageButtonStyle imageStyle = styleIterator.next();
                            Integer price = coinIterator.next();
                            String towerName = nameIterator.next();
                            if (towerIerator.isChecked() && Coin.COINS >= price) {
                                towerIerator.setChecked(false);
                                towerArrayList.get(finalI).setStyle(imageStyle);
                                towerArrayList.get(finalI).setPosition(towerLocation_x[finalI], towerLocation_y[finalI]);
                                towerArrayList.get(finalI).clearListeners();
                                towerArrayList.get(finalI).addListener(towerListener);
                                towerArrayList.get(finalI).setName(towerName);
                                doesCircleExist.set(finalI, true);
                                towerCollisionCircle.get(finalI).set(towerLocation_x[finalI] + 48f, towerLocation_y[finalI] + 48f, radius);
                                Coin.COINS -= price;
                            }
                        }
                    }
                }
            });
            stage.addActor(towerArrayList.get(finalI));
        }
    }

    public void drawTowerCollisionCircle(float radius) {
        Iterator<Float> pillarCircleIterator_x = towerCircle_x.iterator();
        Iterator<Float> pillarCircleIterator_y = towerCircle_y.iterator();
        Iterator<Circle> circleIterator = towerCollisionCircle.iterator();
        Iterator<ShapeRenderer> pillarShapeRendererIterator = towerCollisionCircleRenderer.iterator();
        Iterator<ImageButton> towerIterator = towerArrayList.iterator();
        for (Iterator<Boolean> iterator = doesCircleExist.iterator(); iterator.hasNext(); ) {
            if (circleIterator.hasNext()) {
                Boolean bool = iterator.next();
                Float circle_x = pillarCircleIterator_x.next();
                Float circle_y = pillarCircleIterator_y.next();
                Circle circle = circleIterator.next();
                ShapeRenderer shape = pillarShapeRendererIterator.next();
                if (bool) {
                    shape.begin(ShapeRenderer.ShapeType.Filled);
                    shape.setColor(1, 0, 1, 0.1f);
                    shape.circle(circle_x + 48f, circle_y + 48f, radius);
                    shape.end();
                }
            }
        }
    }

    public void updateTowerToolTips(String weak, String Strong, String best, TooltipManager toolTipManager) {
        towerList.get(0).addListener(new TextTooltip(weak, toolTipManager, uiSkin));                //0.02f  SupportTower  ->  ArcherStandard
        towerList.get(1).addListener(new TextTooltip(Strong, toolTipManager, uiSkin));              //0.03f  ArcherTower   ->  ArcherStrong
        towerList.get(2).addListener(new TextTooltip(best, toolTipManager, uiSkin));                //0.05f  MagicTower    ->  Crossbow
    }

    public void setTowerDamage(ArrayList<Float> towerDamage) {
        this.towerDamage = towerDamage;
    }

    public void checkTowerRange(LinkedList<LinkedList> enemyList, ArrayList<String> name) {
        Iterator<ImageButton> imageButton = towerArrayList.iterator();
        for (Iterator<Circle> circleIterator = towerCollisionCircle.iterator(); circleIterator.hasNext(); ) {
            ImageButton tower = imageButton.next();
            Circle circle = circleIterator.next();
            if (imageButton.hasNext()) {
                for (Iterator<LinkedList> enemyIterator = enemyList.iterator(); enemyIterator.hasNext(); ) {
                    LinkedList list = enemyIterator.next();
                    for (Iterator<PathfindingEnemy> iterator = list.iterator(); iterator.hasNext(); ) {
                        PathfindingEnemy enemy = iterator.next();
                        Iterator<String> nameIterator = name.iterator();
                        for (Iterator<Float> damage = towerDamage.iterator(); damage.hasNext(); ) {
                            Float dmg = damage.next();
                            String towerName = nameIterator.next();
                            if (Intersector.overlaps(circle, enemy.getBoundingRectangle()) && tower.getName().equals(towerName)) {
                                enemy.setLifeCount(enemy.getLifeCount() - dmg);
                                Gdx.app.log(towerName, String.valueOf(dmg));
                            }
                        }
                    }
                }
            }
        }
    }
}
