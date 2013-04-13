package co.uk.cbaker.eu;

import co.uk.cbaker.eu.screens.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class EntityUnknownGame extends Game {
	
	private Screen playScreen;
	private Screen shadowMapTest;
	private Screen StillModelTest;
	private Screen keyframedModelTest;
	
	/** 
	 * Creates all the screens that the game will need, then switches to the main menu. 
	 */
	@Override
	public void create() {
		this.playScreen = new PlayScreen<EntityUnknownGame>(this);
		setScreen(this.playScreen);
		
		//this.shadowMapTest = new ShadowMappingTest<EntityUnknownGame>(this);
		//setScreen(this.shadowMapTest);
		
		//this.StillModelTest = new StillModelTest<EntityUnknownGame>(this);
		//setScreen(this.StillModelTest);
		
//		this.keyframedModelTest = new KeyframedModelTest<EntityUnknownGame>(this);
//		setScreen(this.keyframedModelTest);
	}
}