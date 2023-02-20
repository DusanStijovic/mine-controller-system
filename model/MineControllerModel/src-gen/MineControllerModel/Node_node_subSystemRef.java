package MineControllerModel;

import org.eclipse.etrice.runtime.java.config.IVariableService;
import org.eclipse.etrice.runtime.java.debugging.DebuggingService;
import org.eclipse.etrice.runtime.java.messaging.IRTObject;
import org.eclipse.etrice.runtime.java.messaging.IMessageService;
import org.eclipse.etrice.runtime.java.messaging.MessageService;
import org.eclipse.etrice.runtime.java.messaging.MessageServiceController;
import org.eclipse.etrice.runtime.java.messaging.RTServices;
import org.eclipse.etrice.runtime.java.modelbase.ActorClassBase;
import org.eclipse.etrice.runtime.java.modelbase.DataPortBase;
import org.eclipse.etrice.runtime.java.modelbase.OptionalActorInterfaceBase;
import org.eclipse.etrice.runtime.java.modelbase.IOptionalActorFactory;
import org.eclipse.etrice.runtime.java.modelbase.SubSystemClassBase;
import org.eclipse.etrice.runtime.java.modelbase.InterfaceItemBase;
import org.eclipse.etrice.runtime.java.modelbase.InterfaceItemBroker;

import AlarmModel.*;
import ConsoleModel.*;
import EnvironmentModel.*;
import InterrupSensorModel.*;
import PoolingSensorModel.*;
import PumpModel.*;
import WaterTankModel.*;
import etrice.api.timer.*;


public class Node_node_subSystemRef extends SubSystemClassBase {

	public static final int THREAD_DEFAULTPHYSICALTHREAD = 0;


	public Node_node_subSystemRef(IRTObject parent, String name) {
		super(parent, name);
	}

	@Override
	public void receiveEvent(InterfaceItemBase ifitem, int evt, Object data){
	}

	@Override
	public void instantiateMessageServices() {

		IMessageService msgService;
		msgService = new MessageService(this, MessageService.ExecMode.MIXED, 100000000L, 0, THREAD_DEFAULTPHYSICALTHREAD, "MessageService_DefaultPhysicalThread" /*, thread_prio */);
		RTServices.getInstance().getMsgSvcCtrl().addMsgSvc(msgService);
	}

	@Override
	public void instantiateActors() {

		// thread mappings

		// sub actors
		DebuggingService.getInstance().addMessageActorCreate(this, "topActor");
		new TopActor(this, "topActor");
		DebuggingService.getInstance().addMessageActorCreate(this, "timingService");
		new ATimerService(this, "timingService");

		// create service brokers in optional actor interfaces

		// wiring
		InterfaceItemBase.connect(this, "timingService/timer", "topActor/highWaterSensor/timingService");
		InterfaceItemBase.connect(this, "timingService/timer", "topActor/lowWaterSensor/timingService");
		InterfaceItemBase.connect(this, "timingService/timer", "topActor/waterFlowSensor/timingService");
		InterfaceItemBase.connect(this, "timingService/timer", "topActor/pumpMotor/timingService");
		InterfaceItemBase.connect(this, "timingService/timer", "topActor/environmentMonitoringStation/carboniteMonoxideSensor/timingService");
		InterfaceItemBase.connect(this, "timingService/timer", "topActor/environmentMonitoringStation/airFlowSensor/timingService");
		InterfaceItemBase.connect(this, "timingService/timer", "topActor/environmentMonitoringStation/environmentPooling/timingService");
		InterfaceItemBase.connect(this, "timingService/timer", "topActor/environmentMonitoringStation/methaneSensor/timingService");
		InterfaceItemBase.connect(this, "timingService/timer", "topActor/environmentMonitoringStation/airSimulator/timingService");
		InterfaceItemBase.connect(this, "timingService/timer", "topActor/pumpControlStation/timingService");
		InterfaceItemBase.connect(this, "timingService/timer", "topActor/waterTank/timingService");
		InterfaceItemBase.connect(this, "timingService/timer", "topActor/waterPumpTunnel/timingService");

		// apply instance attribute configurations
	}

	@Override
	public void init(){
		DebuggingService.getInstance().addVisibleComment("begin sub system initialization");
		super.init();
		DebuggingService.getInstance().addVisibleComment("done sub system initialization");
	}

	@Override
	public void stop(){
		super.stop();
	}

	@Override
	public boolean hasGeneratedMSCInstrumentation() {
		return true;
	}

	@Override
	public void destroy() {
		DebuggingService.getInstance().addVisibleComment("begin sub system destruction");
		super.destroy();
		DebuggingService.getInstance().addVisibleComment("done sub system destruction");
	}

	public IOptionalActorFactory getFactory(String optionalActorClass, String actorClass) {

		return null;
	}
};
