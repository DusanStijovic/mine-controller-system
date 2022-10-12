package MineControllerModel;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import PoolingSensorModel.*;
import etrice.api.timer.*;
import etrice.api.timer.PTimer.*;
import PoolingSensorModel.PoolingSensorCommands.*;
import PoolingSensorModel.PoolingSensorSample.*;



public class MethaneSensor extends PoolingSensor {


	//--------------------- ports

	//--------------------- saps

	//--------------------- services

	//--------------------- optional actors

	//--------------------- interface item IDs

	/*--------------------- attributes ---------------------*/

	/*--------------------- operations ---------------------*/


	//--------------------- construction
	public MethaneSensor(IRTObject parent, String name) {
		super(parent, name);
		setClassName("MethaneSensor");

		// initialize attributes

		// own ports

		// own saps

		// own service implementations

		// sub actors

		// wiring


		/* user defined constructor body */

	}

	/* --------------------- attribute setters and getters */


	//--------------------- port getters

	//--------------------- lifecycle functions
	public void stop(){
		super.stop();
	}

	public void destroy(){
		/* user defined destructor body */
		DebuggingService.getInstance().addMessageActorDestroy(this);
		super.destroy();
	}


};
