package EnvironmentModel;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import AlarmModel.*;
import PoolingSensorModel.*;
import TcpCommunication.*;
import etrice.api.timer.*;
import AlarmModel.AlarmSender.*;
import TcpCommunication.PGuiCommunicationInterface.*;
import etrice.api.timer.PTimer.*;
import PoolingSensorModel.PoolingSensorCommands.*;
import PoolingSensorModel.PoolingSensorSample.*;
import EnvironmentModel.supstanceLevelEvent.*;



public class EnvironmentMonitoringStation extends ActorClassBase {


	//--------------------- ports

	//--------------------- saps

	//--------------------- services

	//--------------------- optional actors

	//--------------------- interface item IDs

	/*--------------------- attributes ---------------------*/

	/*--------------------- operations ---------------------*/


	//--------------------- construction
	public EnvironmentMonitoringStation(IRTObject parent, String name) {
		super(parent, name);
		setClassName("EnvironmentMonitoringStation");

		// initialize attributes

		// own ports

		// own saps

		// own service implementations

		// sub actors
		DebuggingService.getInstance().addMessageActorCreate(this, "carboniteMonoxideSensor");
		new CarboniteMonoxideSensor(this, "carboniteMonoxideSensor");
		DebuggingService.getInstance().addMessageActorCreate(this, "airFlowSensor");
		new AirFlowSensor(this, "airFlowSensor");
		DebuggingService.getInstance().addMessageActorCreate(this, "environmentPooling");
		new EnvironmentPooling(this, "environmentPooling");
		DebuggingService.getInstance().addMessageActorCreate(this, "methaneSensor");
		new MethaneSensor(this, "methaneSensor");
		DebuggingService.getInstance().addMessageActorCreate(this, "airSimulator");
		new AirSimulator(this, "airSimulator");

		// wiring
		InterfaceItemBase.connect(this, "environmentPooling/poolingAirFlow", "airFlowSensor/poolingSensorCommands");
		InterfaceItemBase.connect(this, "environmentPooling/poolingCarbonMonohyde", "carboniteMonoxideSensor/poolingSensorCommands");
		InterfaceItemBase.connect(this, "methaneSensor/poolingSensorCommands", "environmentPooling/poolingMethane");
		InterfaceItemBase.connect(this, "airSimulator/airflowSample", "airFlowSensor/poolingSensorSample");
		InterfaceItemBase.connect(this, "airSimulator/methaneSample", "methaneSensor/poolingSensorSample");
		InterfaceItemBase.connect(this, "airSimulator/caarbonMonoxydeSample", "carboniteMonoxideSensor/poolingSensorSample");


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

	//--------------------- no state machine
	public void receiveEvent(InterfaceItemBase ifitem, int evt, Object data) {
		handleSystemEvent(ifitem, evt, data);
	}

	public void executeInitTransition() {}

};
