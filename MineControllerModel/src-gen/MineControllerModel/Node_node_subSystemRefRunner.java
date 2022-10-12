/**
 * @author generated by eTrice
 *
 * this class contains the main function running component subSystemRef
 * it instantiates subSystemRef and starts and ends the lifecycle
 */

package MineControllerModel;

import org.eclipse.etrice.runtime.java.modelbase.RTSystem;
import org.eclipse.etrice.runtime.java.modelbase.SubSystemRunnerBase;

public class Node_node_subSystemRefRunner extends SubSystemRunnerBase {

	/**
     * main function
     * creates the main component and starts and stops the lifecycle
     */
	public static void main(String[] args) {
		// instantiate the main component
		RTSystem sys = new RTSystem("LogSys");
		Node_node_subSystemRef main_component = new Node_node_subSystemRef(sys, "subSystemRef");
		
		run(main_component, args);
	}
};
