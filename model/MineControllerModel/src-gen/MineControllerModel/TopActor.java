package MineControllerModel;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import AlarmModel.*;
import EnvironmentModel.*;
import InterrupSensorModel.*;
import PoolingSensorModel.*;
import PumpModel.*;
import TcpCommunication.*;
import WaterTankModel.*;
import etrice.api.tcp.*;
import etrice.api.timer.*;
import AlarmModel.AlarmSender.*;
import WaterTankModel.DrainWater.*;
import InterrupSensorModel.EventHappened.*;
import etrice.api.tcp.PTcpControl.*;
import etrice.api.tcp.PTcpPayload.*;
import etrice.api.timer.PTimer.*;
import TcpCommunication.PTrafficLightInterface.*;
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
		DebuggingService.getInstance().addMessageActorCreate(this, "waterTenk");
		new TrafficLightInterface(this, "waterTenk");
		DebuggingService.getInstance().addMessageActorCreate(this, "ref0");
		new ATcpClient(this, "ref0");
		DebuggingService.getInstance().addMessageActorCreate(this, "pumpTcp");
		new TrafficLightInterface(this, "pumpTcp");
		DebuggingService.getInstance().addMessageActorCreate(this, "ref1");
		new ATcpClient(this, "ref1");
		DebuggingService.getInstance().addMessageActorCreate(this, "alarmTcp");
		new TrafficLightInterface(this, "alarmTcp");
		DebuggingService.getInstance().addMessageActorCreate(this, "ref2");
		new ATcpClient(this, "ref2");
		DebuggingService.getInstance().addMessageActorCreate(this, "environmentTcp");
		new TrafficLightInterface(this, "environmentTcp");
		DebuggingService.getInstance().addMessageActorCreate(this, "ref3");
		new ATcpClient(this, "ref3");
		DebuggingService.getInstance().addMessageActorCreate(this, "tcpPumpControl");
		new TrafficLightInterface(this, "tcpPumpControl");
		DebuggingService.getInstance().addMessageActorCreate(this, "ref4");
		new ATcpClient(this, "ref4");
		DebuggingService.getInstance().addMessageActorCreate(this, "ref5");
		new ATcpClient(this, "ref5");
		DebuggingService.getInstance().addMessageActorCreate(this, "tcpWaterTunel");
		new TrafficLightInterface(this, "tcpWaterTunel");

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
		InterfaceItemBase.connect(this, "environmentMonitoringStation/environmentPooling/methaneLevel", "pumpControlStation/methaneLevel");
		InterfaceItemBase.connect(this, "ref0/ControlPort", "waterTenk/ctrl");
		InterfaceItemBase.connect(this, "ref0/PayloadPort", "waterTenk/payload");
		InterfaceItemBase.connect(this, "waterTenk/fct", "waterTank/tcpWaterTenk");
		InterfaceItemBase.connect(this, "ref1/PayloadPort", "pumpTcp/payload");
		InterfaceItemBase.connect(this, "pumpTcp/ctrl", "ref1/ControlPort");
		InterfaceItemBase.connect(this, "pumpMotor/pump", "pumpTcp/fct");
		InterfaceItemBase.connect(this, "ref2/ControlPort", "alarmTcp/ctrl");
		InterfaceItemBase.connect(this, "ref2/PayloadPort", "alarmTcp/payload");
		InterfaceItemBase.connect(this, "alarmTcp/fct", "Alarm/p0");
		InterfaceItemBase.connect(this, "ref3/ControlPort", "environmentTcp/ctrl");
		InterfaceItemBase.connect(this, "ref3/PayloadPort", "environmentTcp/payload");
		InterfaceItemBase.connect(this, "environmentTcp/fct", "environmentMonitoringStation/environmentPooling/tcpEnvironment");
		InterfaceItemBase.connect(this, "tcpPumpControl/payload", "ref4/PayloadPort");
		InterfaceItemBase.connect(this, "tcpPumpControl/ctrl", "ref4/ControlPort");
		InterfaceItemBase.connect(this, "pumpControlStation/tcpPumpControll", "tcpPumpControl/fct");
		InterfaceItemBase.connect(this, "tcpWaterTunel/payload", "ref5/PayloadPort");
		InterfaceItemBase.connect(this, "tcpWaterTunel/ctrl", "ref5/ControlPort");
		InterfaceItemBase.connect(this, "waterPumpTunnel/tcpPumpTunel", "tcpWaterTunel/fct");


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
