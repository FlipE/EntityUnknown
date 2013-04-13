package co.uk.cbaker.eu.game.view;

import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.HUDFocus;
import co.uk.cbaker.eu.game.entity.components.Health;
import co.uk.cbaker.eu.game.entity.components.Physics2D;
import co.uk.cbaker.eu.game.entity.components.WeaponManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class HUDView implements View {

	//private SpriteBatch batch;
	private Stage stage;
	private Skin skin;
	private EntityManager em;
	private int focussedEntity;
	
	// ui components
	private Label fps;
	private Label health;
	//private Label armour;
	private Label x;
	private Label y;
	private Label gun;
	private Label ammoClip;
	private Label ammoPool;
	
	public HUDView(Stage stage, SpriteBatch batch, EntityManager em) {
		this.stage = stage;
		//this.batch = batch;
		this.em = em;
		this.initialise();
	}

	private void initialise() {
		
		// get the entity id which has the hud focus component
		for(int e : em.getAllEntitiesPossessingComponent(HUDFocus.class)) {
			if(em.getComponent(e, HUDFocus.class).isHUDFocus()) {
				focussedEntity = e;
			}			
		}
		
		skin = new Skin(Gdx.files.internal("uiskin.json")); // Gdx.files.internal("uiskin.png")
		
		// Add the widgets to the root layout table
		Table table = new Table(skin);
		table.setFillParent(true);
		table.add(this.initialiseTop()).left();
		table.row();
		table.add(this.initialiseMain()).expand().bottom().left();

		// add the root table to the stage
		stage.addActor(table);

		// turn on debug draw
		//table.debug(); // turn on all debug lines (table, cell, and widget)
	}

	private Table initialiseTop() {
		final Table table = new Table(skin);
		health = new Label("Health:", skin);
		gun = new Label("", skin);
		ammoClip = new Label("Ammo Clip: ", skin);
		ammoPool = new Label("Ammo Pool: ", skin);
				
		table.add(health).width(100);
		table.add(gun).width(120);
		table.add(ammoClip).width(160);
		table.add(ammoPool).width(100);
		return table;
	}
	
	private Table initialiseMain() {
		final Table table = new Table(skin);
		fps = new Label("fps:", skin);		
		x = new Label("X: 0,", skin);
		y = new Label("Y: 0", skin);
		
		table.add(fps).width(75);
		table.add(x).width(75);
		table.add(y).width(75);		
		return table;
	}

	/**
	 * render the ui
	 */
	public void render() {
		// update the view ui components
		this.updateFPS();
		this.updateHealth();
		this.updateLocation();
		this.updateGun();
		this.updateAmmo();
		
		stage.draw();
		//Table.drawDebug(stage);
	}

	private void updateGun() {
		this.gun.setText(em.getComponent(focussedEntity, WeaponManager.class).getName());
	}

	// TODO this generates garbage with string creation, fix it with string buffers
	private void updateAmmo() {
		int capacity = em.getComponent(focussedEntity, WeaponManager.class).getClipCapacity();
		int ammo = em.getComponent(focussedEntity, WeaponManager.class).getAmmoInClip();
		int pool = em.getComponent(focussedEntity, WeaponManager.class).getAmmoPool();
		
		this.ammoClip.setText("Ammo Clip: " + ammo + " / " + capacity);
		this.ammoPool.setText("Ammo Pool: " + pool);
	}

	/**
	 * Updates and formats the fps label
	 */
	// TODO this generates garbage with string creation, fix it with string buffers
	private void updateFPS() {
		fps.setText("fps: " + Gdx.graphics.getFramesPerSecond());
	}

	/**
	 * updates and formats the health label
	 */
	// TODO this generates garbage with string creation, fix it with string buffers
	private void updateHealth() {
		health.setText("Health: " + em.getComponent(focussedEntity, Health.class).getCurrentHealth());
	}	
	
	/**
	 * Updates and formats the x and y location for the label
	 */
	// TODO this generates garbage with string creation, fix it with string buffers
	private void updateLocation() {
		double x = Math.round(em.getComponent(focussedEntity, Physics2D.class).getX() * 10) / 10.0d;
		double y = Math.round(em.getComponent(focussedEntity, Physics2D.class).getY() * 10) / 10.0d;
		
		this.x.setText("X: " + x + ",");
		this.y.setText("Y: " + y);
	}
	
}