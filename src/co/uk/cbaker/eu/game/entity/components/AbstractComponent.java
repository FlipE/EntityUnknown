/**
 * 
 */
package co.uk.cbaker.eu.game.entity.components;

import com.badlogic.gdx.utils.Pool;


/**
 * AbstractComponent.java
 *
 * @author 	Chris B
 * @date	24 Mar 2013
 * @version	1.0
 */
public abstract class AbstractComponent<T extends Component> implements Component {

	protected static Pool<? extends Component> components = new Pool<Component>() {

		@Override
		protected Component newObject() {
			return null;
		}
		
	};
	
}