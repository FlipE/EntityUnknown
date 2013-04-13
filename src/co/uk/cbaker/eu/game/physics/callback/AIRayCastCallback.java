package co.uk.cbaker.eu.game.physics.callback;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

/**
 * Callback for ray casts
 */
public class AIRayCastCallback implements RayCastCallback {

	public Fixture fixture;
	public Vector2 point;
	public Vector2 normal;
	public float fraction;
	
	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
		
		this.reset();
		
		if(fixture.isSensor()) {
			return -1;
		}
		
		this.fixture = fixture;
		this.point = point;
		this.normal = normal;
		this.fraction = fraction;
		
		return fraction;
	}
	
	public void reset() {
		this.fixture = null;
		this.point = null;
		this.normal = null;
		this.fraction = 1;
	}
}