package co.uk.cbaker.eu.screens;

import co.uk.cbaker.eu.game.model.Simulation;
import co.uk.cbaker.eu.game.view.SimulationView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;

public class PlayScreen<T> extends GameScreen<T> {

	public static final int MAX_UPDATE_ITERATIONS = 5;
	public static final float fixedTimeStep = 1 / 60f;
	public static final int tweenTimeStep = (int) (fixedTimeStep * 1000);

	private float accum = 0;
	private int iterations = 0;
	
	// TODO: work out the net code for multiplayer

	// 1. Put code in the server and client to send and receive a ping.
	// 2. Send a ping from the client to the server every time the client connects.
	// 3. Record the current time as te send time.
	// 4. When the client receives the ping, record the current time at the receive time.
	// 5. Get the time delay by subtracting the receive time from the send time and divide it by 2.
	// 6. Have the server send the current time to the client.
	// 7. Subtract the time difference from the time server sent.
	// 8. Set the client time to the time from step 6.
	
	// simulation loop counter
	// this helps to sync the client with the server. allowing the server to track
	// how far behind the client is. Server periodically sends simulation snapshot to
	// client. client updates all positions but does not want to interpolate backwards.
	// likewise the client sends input to server which can calculate shiz
	// see article @ https://developer.valvesoftware.com/wiki/Source_Multiplayer_Networking
	//private int ping;
	
	// using a client system to accept and queue an update component command
	// updateComponent(int entity, Class component, ComponentVisitor updater)
	
	// updates should be time stamped so things like position can be adjusted
	
	// set a number id for each type of update. this can be sent as a byte id
	// each id will correspond to an update ie 
	// id=1 update physics x,y,velocity,rotation
	// id=2 increase health amount
	// id=3 decrease health amount
	// id=4 kill
	// id=5 shoot weaponId locX locY directionX directionY
	// id=6 increase ammo
	// id=7 decrease ammo
	// ...and etc.
	
	// think how to sync entity id's over network how will bullets work etc
	//	Global - Networked objects
	//	Static - Non-networked objects that are known by both client and server.
	//	Local  - Non-networked objects that are known only by client or server, but not both.
	
	// simulation
	private Simulation simulation;
	private SimulationView simulationView;
	private InputMultiplexer im;
	
	// network shiz
	// deleted
	
	public PlayScreen(T game) {
		super(game);
		initialise();
	}

	private void initialise() {
		// network shiz
		// this.server = new GameServer();
		// this.client = new GameClient();
		
		// simulation
		this.simulation = new Simulation();
		this.simulationView = new SimulationView(simulation);
		
		// send the stage then simulation to the input multiplexer so that stage is on top of the main context 
		im = new InputMultiplexer(simulationView.getInputProcessor(), simulation.getInputProcessor());		
		Gdx.input.setInputProcessor(im);
	}

	/**
	 * Update loops until accumulated delta time has been used up by the fixed
	 * time step Max update iterations avoids spiral of death.
	 */
	@Override
	public void render(float delta) {

		// Update
		accum += delta;
		iterations = 0;
		while (accum > fixedTimeStep && iterations < MAX_UPDATE_ITERATIONS) {
			// tweenManager.update(tweenTimeStep);
			simulation.update(fixedTimeStep);
			accum -= fixedTimeStep;
			iterations++;
		}

		// Render
		simulationView.render();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
