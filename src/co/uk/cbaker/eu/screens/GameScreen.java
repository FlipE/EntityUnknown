package co.uk.cbaker.eu.screens;

import com.badlogic.gdx.Screen;

public abstract class GameScreen<T> implements Screen  {

	protected T game;

	public GameScreen (T game) {
		this.game = game;
	}

	@Override
	public abstract void render (float delta);

	@Override
	public abstract void resize (int width, int height);

	@Override
	public abstract void pause ();
	
	@Override
	public abstract void resume ();

	@Override
	public abstract void show ();
	
	@Override
	public abstract void hide ();
	
	@Override
	public abstract void dispose ();
}