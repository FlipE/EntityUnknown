package tests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.FadeIn;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;

/**
*
* @author Seth Sectioner
*/
public class MainMenuScreen extends GameScreen {

    private TextButton btnStart;
    private TextButton btnMap;
    private TextButton btnSettings;
    private Window window;

    public MainMenuScreen(Game game) {
        super(game, "uiskin.json", "uiskin.png");
    }
    
    @Override
    public void create() {
        setupComponents();
        setupLayout();
    }

    public void setupComponents() {
        this.btnStart = new TextButton("Start Game", skin.getStyle(TextButtonStyle.class));
        this.btnStart.setClickListener(new ClickListener() {

            @Override
            public void click(Actor actor, float x, float y) {
                nextScreen(new MainMenuScreen(game));
            }
        });
        this.btnMap = new TextButton("Map Editor", skin.getStyle(TextButtonStyle.class));
        this.btnMap.setClickListener(new ClickListener() {

            @Override
            public void click(Actor actor, float x, float y) {
                nextScreen(new MainMenuScreen(game));
            }
        });
        this.btnSettings = new TextButton("Settings", skin.getStyle(TextButtonStyle.class));
        this.btnSettings.setClickListener(new ClickListener() {

            @Override
            public void click(Actor actor, float x, float y) {
                nextScreen(new MainMenuScreen(game));
            }
        });
        this.window = new Window("", skin.getStyle(WindowStyle.class));
        
        this.window.setMovable(false);
        this.window.color.a = 0;
    }

    public void setupLayout() {
        window.defaults().pad(1, 30, 1, 30);
        window.defaults().fill(2f, 0f);

        this.window.add(btnStart);
        this.window.row();
        this.window.add(btnMap);
        this.window.row();
        this.window.add(btnSettings);
        this.window.pack();

        this.window.x = (Gdx.graphics.getWidth() / 2) - (window.width / 2);
        this.window.y = (Gdx.graphics.getHeight() / 2) - (window.height / 2);

        this.stage.addActor(window);
        this.window.action(FadeIn.$(2));
    }
}