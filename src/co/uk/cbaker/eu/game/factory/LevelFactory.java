package co.uk.cbaker.eu.game.factory;

import co.uk.cbaker.eu.game.Assets;
import co.uk.cbaker.eu.game.Config;
import co.uk.cbaker.eu.game.ai.soldier.GlobalState;
import co.uk.cbaker.eu.game.ai.soldier.IdleState;
import co.uk.cbaker.eu.game.ai.soldier.SoldierStateMachine;
import co.uk.cbaker.eu.game.entity.EntityManager;
import co.uk.cbaker.eu.game.entity.components.AIBrain;
import co.uk.cbaker.eu.game.entity.components.AIHearing;
import co.uk.cbaker.eu.game.entity.components.AIVision;
import co.uk.cbaker.eu.game.entity.components.CameraFocus;
import co.uk.cbaker.eu.game.entity.components.CollisionTag;
import co.uk.cbaker.eu.game.entity.components.Controllable;
import co.uk.cbaker.eu.game.entity.components.HUDFocus;
import co.uk.cbaker.eu.game.entity.components.Health;
import co.uk.cbaker.eu.game.entity.components.Physics2D;
import co.uk.cbaker.eu.game.entity.components.RenderableStatic;
import co.uk.cbaker.eu.game.entity.components.WeaponManager;
import co.uk.cbaker.eu.game.model.weapons.Weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g3d.loaders.ModelLoaderRegistry;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.materials.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class LevelFactory {

	public static void load(World world, EntityManager em) {
		// TODO: this shuld be done with xml
		// load some textures
		Assets.load();
		
		//initBox(world, em);
		initPlayer(world, em, 8, 112);
		initEnemy(world, em, 30, 112);
		initEnemy(world, em, 35, 112);
		
		//initEnemy(world, em, 20, 9);
		//initEnemy(world, em, 20, 11);		
		//initEnemy(world, em, 20, 13);
	}

	public static void initphysicswall(World world, EntityManager em, float x, float y) {
		PolygonShape groundPoly = new PolygonShape();
		groundPoly.setAsBox(1, 1);

		// next we create the body for the ground platform. It's
		// simply a static body.
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyType.StaticBody;
		groundBodyDef.position.x = x;
		groundBodyDef.position.y = y;
		Body groundBody = world.createBody(groundBodyDef);
		
		// finally we add a fixture to the body using the polygon
		// defined above. Note that we have to dispose PolygonShapes
		// and CircleShapes once they are no longer used. This is the
		// only time you have to care explicitly for memory management.
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = groundPoly;
		fixtureDef.filter.groupIndex = 0;
		groundBody.createFixture(fixtureDef);
		groundPoly.dispose();
	}
	
	public static void initWall(World world, EntityManager em, float x, float y) {

		// Render stuff
		StillModel model = ModelLoaderRegistry.loadStillModel(Gdx.files.internal("tall-cube.obj"));
		Texture texture = new Texture(Gdx.files.internal("Panel-1.jpg"), true);
		texture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);		
		//mesh.setMaterial(new Material("whatever", new TextureAttribute(texture, 1, "whatever")));
		Material material = new Material();
	    material.addAttribute(new TextureAttribute(texture, 0, "u_texture"));
	    model.setMaterial(material);
		
		// Phsysics Stuff
		PolygonShape boxPoly = new PolygonShape();
		boxPoly.setAsBox(1, 1);

		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = BodyType.StaticBody;

		// box starting position
		boxBodyDef.position.x = x;
		boxBodyDef.position.y = y;
		Body body = world.createBody(boxBodyDef);

		FixtureDef fixture = new FixtureDef();
		fixture.shape = boxPoly;
		body.createFixture(fixture);

		
		// Entity Stuff
		
		// create the entity id
		int entity = em.createEntity();

		// create a tag with collision id and entity id
		CollisionTag collisionTag = new CollisionTag(entity, Config.PICKUP);

		// set the user data on the fixture
		body.getFixtureList().get(0).setUserData(collisionTag);

		// every entity has a tag
		em.addComponent(entity, collisionTag);
		em.addComponent(entity, new Physics2D(body, 0, false));
		em.addComponent(entity, new Controllable(false));
		em.addComponent(entity, new RenderableStatic(model));
		
		boxPoly.dispose();
	}

	public static void initPlayer(World world, EntityManager em, float x, float y) {
		
		// Render stuff
		StillModel model = ModelLoaderRegistry.loadStillModel(Gdx.files.internal("Female Textured Hair.obj"));
		Texture texture = new Texture(Gdx.files.internal("256_Female_DIFF.png"), true);
		texture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
		Material material = new Material();
	    material.addAttribute(new TextureAttribute(texture, 0, "u_texture"));
	    model.setMaterial(material);
		
		// Physics stuff
		PolygonShape boxPoly = new PolygonShape();
		boxPoly.setAsBox(0.5f, 1.0f);
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = BodyType.DynamicBody;
		
		// box starting position
		boxBodyDef.position.x = x;
		boxBodyDef.position.y = y;
		Body body = world.createBody(boxBodyDef);

		body.createFixture(boxPoly, 15f);

		
		// Entity stuff
		
		// create the entity id
		int entity = em.createEntity();

		// create a tag with collision id and entity id
		CollisionTag collisionTag = new CollisionTag(entity, Config.PLAYER);

		// set the user data on the fixture
		body.getFixtureList().get(0).setUserData(collisionTag);

		// create a weapon manager and add a few weapons
		WeaponManager weaponManager = new WeaponManager(Weapon.assualtRifle());
		weaponManager.add(Weapon.pistol());
		
		// every entity has a tag
		em.addComponent(entity, collisionTag);
		em.addComponent(entity, new Physics2D(body, 20, false));
		em.addComponent(entity, new Controllable(true));
		em.addComponent(entity, new CameraFocus(true));
		em.addComponent(entity, new HUDFocus(true));
		em.addComponent(entity, weaponManager);
		em.addComponent(entity, new Health(100));
		em.addComponent(entity, new RenderableStatic(model));

		boxPoly.dispose();
	}

	public static void initEnemy(World world, EntityManager em, float x, float y) {

		// Render stuff
		//Mesh mesh = ModelLoaderOld.loadObj(Gdx.files.internal("player.obj").read());
		//Texture texture = new Texture(Gdx.files.internal("grey.png"), true);
		StillModel model = ModelLoaderRegistry.loadStillModel(Gdx.files.internal("Female Textured Hair.obj"));
		Texture texture = new Texture(Gdx.files.internal("256_Female_DIFF.png"), true);
		texture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
		
		Material material = new Material();
	    material.addAttribute(new TextureAttribute(texture, 0, "u_texture"));
	    model.setMaterial(material);
		
		// Physics stuff
		PolygonShape boxPoly = new PolygonShape();
		boxPoly.setAsBox(0.5f, 1.0f);

		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = BodyType.DynamicBody;

		// box starting position
		boxBodyDef.position.x = x;
		boxBodyDef.position.y = y;
		Body body = world.createBody(boxBodyDef);

		body.createFixture(boxPoly, 15f);

		
		// Entity stuff
		
		// create the entity id
		int entity = em.createEntity();

		// create a tag with collision id and entity id
		CollisionTag collisionTag = new CollisionTag(entity, Config.AI_BOT);

		// set the user data on the fixture
		body.getFixtureList().get(0).setUserData(collisionTag);

		// every entity has a tag
		em.addComponent(entity, collisionTag);
		em.addComponent(entity, new Physics2D(body, 24, false));
		em.addComponent(entity, new AIBrain(new SoldierStateMachine(em, entity, IdleState.getInsatnce(), GlobalState.getInsatnce())));
		em.addComponent(entity, new AIHearing(sensor(world, em, entity, x, y, 12)));
		em.addComponent(entity, new AIVision());
		em.addComponent(entity, new Health(100));
		em.addComponent(entity, new WeaponManager(Weapon.pistol()));
		em.addComponent(entity, new Controllable(false));
		em.addComponent(entity, new CameraFocus(false));
		em.addComponent(entity, new RenderableStatic(model));
		
		boxPoly.dispose();
	}

	public static Body sensor(World world, EntityManager em, int entity, float x, float y, int radius) {

		CircleShape circle = new CircleShape();
		circle.setRadius(radius);
		//circle.setRadius(8);
		
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = BodyType.DynamicBody;

		// box starting position
		boxBodyDef.position.x = x;
		boxBodyDef.position.y = y;
		Body body = world.createBody(boxBodyDef);

		FixtureDef fixture = new FixtureDef();
		fixture.isSensor = true;
		fixture.density = 0f;
		fixture.shape = circle;
		body.createFixture(fixture);

		// create a tag with collision id and entity id
		CollisionTag collisionTag = new CollisionTag(entity, Config.AI_HEARING);

		// set the user data on the fixture
		body.getFixtureList().get(0).setUserData(collisionTag);

		circle.dispose();

		return body;
	}

}