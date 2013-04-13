package co.uk.cbaker.eu.game.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import co.uk.cbaker.eu.game.entity.components.Component;

/**
 * Standard design: c.f. http://entity-systems.wikidot.com/rdbms-with-code-in-systems
 * 
 * Modified in java to use Generics: instead of having a "ComponentType" enum, we use the class type
 * of each subclass instead. This is safer.
 */

public class EntityManager
{
	private int lowestUnassignedEntityID=1;
	private List<Integer> allEntities;
	private HashMap<Class<?>, HashMap<Integer, ? extends Component>> componentStores;
	
	public EntityManager()
	{
		allEntities = new LinkedList<Integer>();
		componentStores = new HashMap<Class<?>, HashMap<Integer,? extends Component>>();
	}
	
	/**
	 * get the component of the given entity
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> T getComponent( int entity, Class<T> componentType)
	{
	   HashMap<Integer, ? extends Component> store = componentStores.get( componentType );
	   
	   if( store == null)
		   return null;
	   	//throw new IllegalArgumentException( "GET FAIL: there are no entities with a Component of class: "+componentType );
	   
		   
	   T result = (T) store.get( entity );
	   if( result == null )
		   return null;
	      //throw new IllegalArgumentException( "GET FAIL: "+entity+" does not possess Component of class\n   missing: "+componentType );

	   return result;
	}

	/**
	 * return a list of all components of the given type
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> List<T> getAllComponentsOfType( Class<T> componentType )
	{
		HashMap<Integer, ? extends Component> store = componentStores.get( componentType );
		
		if( store == null)
	   	return new LinkedList<T>();
		
		return (List<T>) store.values();
	}
	
	/**
	 * return a set of all entity id's which are mapped to a component
	 */
	public <T extends Component> Set<Integer> getAllEntitiesPossessingComponent( Class<T> componentType )
	{
		HashMap<Integer, ? extends Component> store = componentStores.get( componentType );
		
		if( store == null)
	   	return new HashSet<Integer>();
		
		return store.keySet();
	}
	
	/**
	 * map a component to an entity
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> void addComponent( int entity, T component )
	{
		HashMap<Integer, ? extends Component> store = componentStores.get( component.getClass() );
		
		if( store == null )
		{
			store = new HashMap<Integer, T>();
			componentStores.put(component.getClass(), store );
		}
		
		((HashMap<Integer, T>)store).put(entity, component);
	}
	
	/**
	 * create a new entity adding it to the list
	 */
	public int createEntity()
	{

		int newID = generateNewEntityID();
		
		if( newID < 1 )
		{
			/**
			 Fatal error...
			 */
			return 0;
		}
		else
		{
			allEntities.add(newID);
		
			return newID;
		}
	}
	
	/**
	 * remove an entity freeing up its id.
	 */
	public void killEntity( int entity )
	{
		synchronized( this ) // prevent it generating two entities with same ID at once
		{
			allEntities.remove(entity);
		}
	}
	
	/**
	 * get a new entity id. If there are no new id's then try to find a second hand one.
	 */
	public int generateNewEntityID()
	{
		synchronized( this ) // prevent it generating two entities with same ID at once
		{
		if( lowestUnassignedEntityID < Integer.MAX_VALUE )
		{
			return lowestUnassignedEntityID++;
		}
		else
		{
			for( int i=1; i<Integer.MAX_VALUE; i++ )
			{
				if( ! allEntities.contains(i) )
					return i;
			}
			
			throw new Error("ERROR: no available Entity IDs; too many entities!" );
		}
		}
	}
}