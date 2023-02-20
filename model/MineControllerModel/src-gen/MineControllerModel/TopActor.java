package MineControllerModel;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import AlarmModel.*;
import ConsoleModel.*;
import EnvironmentModel.*;
import InterrupSensorModel.*;
import PoolingSensorModel.*;
import PumpModel.*;
import WaterTankModel.*;
import etrice.api.timer.*;
import AlarmModel.AlarmSender.*;
import WaterTankModel.DrainWater.*;
import InterrupSensorModel.EventHappened.*;
import ConsoleModel.MessagingProtocol.*;
import etrice.api.timer.PTimer.*;
import PoolingSensorModel.PoolingSensorCommands.*;
import PoolingSensorModel.PoolingSensorSample.*;
import PumpModel.PumpMotorControll.*;
import EnvironmentModel.supstanceLevelEvent.*;



public class TopActor extends ActorClassBase {


	//--------------------- ports

	//--------------------- saps

	//--------------------- services

	//--------------------- optional actors

	//--------------------- interface item IDs

	/*--------------------- attributes ---------------------*/

	/*--------------------- operations ---------------------*/


	//--------------------- construction
	public TopActor(IRTObject parent, String name) {
		super(parent, name);
		setClassName("TopActor");

		// initialize attributes

		// own ports

		// own saps

		// own service implementations

		// sub actors
		DebuggingService.getInstance().addMessageActorCreate(this, "highWaterSensor");
		new HighWaterSensor(this, "highWaterSensor");
		DebuggingService.getInstance().addMessageActorCreate(this, "lowWaterSensor");
		new LowWaterSensor(this, "lowWaterSensor");
		DebuggingService.getInstance().addMessageActorCreate(this, "waterFlowSensor");
		new WaterFlowSensor(this, "waterFlowSensor");
		DebuggingService.getInstance().addMessageActorCreate(this, "pumpMotor");
		new PumpMotor(this, "pumpMotor");
		DebuggingService.getInstance().addMessageActorCreate(this, "environmentMonitoringStation");
		new EnvironmentMonitoringStation(this, "environmentMonitoringStation");
		DebuggingService.getInstance().addMessageActorCreate(this, "pumpControlStation");
		new PumpControlStation(this, "pumpControlStation");
		DebuggingService.getInstance().addMessageActorCreate(this, "waterTank");
		new WaterTank(this, "waterTank");
		DebuggingService.getInstance().addMessageActorCreate(this, "waterPumpTunnel");
		new waterPumpTunnel(this, "waterPumpTunnel");
		DebuggingService.getInstance().addMessageActorCreate(this, "Alarm");
		new Alarm(this, "Alarm");
		DebuggingService.getInstance().addMessageActorCreate(this, "console");
		new Conole(this, "console");

		// wiring
		InterfaceItemBase.connect(this, "lowWaterSensor/outputEvent", "pumpControlStation/lowWaterSensor");
		InterfaceItemBase.connect(this, "waterTank/lowWaterLevel", "lowWaterSensor/inputEvent");
		InterfaceItemBase.connect(this, "waterTank/highWaterLevel", "highWaterSensor/inputEvent");
		InterfaceItemBase.connect(this, "highWaterSensor/outputEvent", "pumpControlStation/highWaterSensor");
		InterfaceItemBase.connect(this, "pumpControlStation/controlMotor", "pumpMotor/controlMotor");
		InterfaceItemBase.connect(this, "pumpControlStation/waterFlowSensor", "waterFlowSensor/poolingSensorCommands");
		InterfaceItemBase.connect(this, "waterPumpTunnel/waterTenkPump", "pumpMotor/drainWater");
		InterfaceItemBase.connect(this, "waterPumpTunnel/waterTenk", "waterTank/drainWater");
		InterfaceItemBase.connect(this, "waterPumpTunnel/wateflowSensor", "waterFlowSensor/poolingSensorSample");
		InterfaceItemBase.connect(this, "Alarm/alarmReceiver", "pumpControlStation/alarmSender");
		InterfaceItemBase.connect(this, "environmentMonitoringStation/environmentPooling/sendAlarm", "Alarm/alarmReceiver");
		InterfaceItemBase.connect(this, "pumpControlStation/messageSender", "console/receiveMessage");
		InterfaceItemBase.connect(this, "environmentMonitoringStation/sendToConsole", "console/receiveMessage");
		InterfaceItemBase.connect(this, "environmentMonitoringStation/environmentPooling/methaneLevel", "pumpControlStation/methaneLevel");


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
