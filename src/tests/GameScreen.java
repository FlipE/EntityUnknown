package tests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.OnActionCompleted;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.FadeIn;
import com.badlogic.gdx.scenes.scene2d.actions.FadeOut;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * 
 * @author Seth Sectioner
 */
public abstract class GameScreen implements Screen {
	public Game game;
	public Stage stage;
	public Skin skin;

	public GameScreen(Game game, String json, String png) {
		this.game = game;
		this.stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		this.skin = new Skin(Gdx.files.internal(json), Gdx.files.internal(png));

		Gdx.input.setInputProcessor(stage);
		this.stage.getRoot().action(FadeIn.$(1));
		create();
	}

	public GameScreen(Game game, Skin skin) {
		this.game = game;
		this.stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		this.skin = skin;

		Gdx.input.setInputProcessor(stage);
		this.stage.getRoot().color.a = 0;
		this.stage.getRoot().action(FadeIn.$(1));
		create();
	}

	public void nextScreen(final Screen screen) {
		stage.getRoot().action(FadeOut.$(1).setCompletionListener(new OnActionCompleted() {

			@Override
			public void completed(Action action) {
				game.setScreen(screen);
			}
		}));
	}

	public abstract void create();

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}
}