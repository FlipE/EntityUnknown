/**
 * 
 */
package co.uk.cbaker.eu.game.entity;

import co.uk.cbaker.eu.game.entity.components.Component;

import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Pool;

/**
 * ComponentManager.java
 *
 * @author 	Chris B
 * @date	24 Mar 2013
 * @version	1.0
 */
public class ComponentManager {

	private ArrayMap<Class<? extends Component>, Pool<? extends Component>> componentPools;
	
	private static ComponentManager instance = new ComponentManager();
	
	public static ComponentManager instance() {
		return instance;
	}
	
	private ComponentManager() {}
	
	/**
	 * 
	 * @param componentType the type of component to register
	 */
	private <T extends Component> void registerComponentType(final Class<T> componentType) {
		if(!componentPools.containsKey(componentType)) {
			
			componentPools.put(componentType, new Pool<T>() {

				@Override
				protected T newObject() {
					try {
						return componentType.newInstance();
					}
					catch (InstantiationException | IllegalAccessException e) {
						// TODO wtf to do here?
						//e.printStackTrace();
						return null;
					}
				}				
			});			
		}
	}
	
	/**
	 * Get a new component instance of the class given. If there is no
	 * pool found then one is created first.
	 * 
	 * @param componentType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> T newComponentOfType(final Class<T> componentType) {
		if(!componentPools.containsKey(componentType)) {
			this.registerComponentType(componentType);			
		}
		return (T) componentPools.get(componentType).obtain();
	}
	
}