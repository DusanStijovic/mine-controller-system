package PumpModel;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import AlarmModel.*;
import ConsoleModel.*;
import EnvironmentModel.*;
import InterrupSensorModel.*;
import PoolingSensorModel.*;
import etrice.api.timer.*;
import AlarmModel.AlarmSender.*;
import InterrupSensorModel.EventHappened.*;
import ConsoleModel.MessagingProtocol.*;
import etrice.api.timer.PTimer.*;
import PoolingSensorModel.PoolingSensorCommands.*;
import PumpModel.PumpMotorControll.*;
import EnvironmentModel.supstanceLevelEvent.*;

/*--------------------- begin user code ---------------------*/
import java.util.Date;
import java.util.Calendar;

/*--------------------- end user code ---------------------*/


public class PumpControlStation extends ActorClassBase {

	/*--------------------- begin user code ---------------------*/
	private boolean isPumpActive = false;
	private int MAX_ERROR_COUNT = 2;
	private boolean shouldGetFlow = false;
	private int errorCount = 0;
	private int numberOfSameMeasure = 0;
	private static int PERIOD_OF_ACTIVATION_MS = 100;
	private static int NUM_OF_NEEDED_MEASURE = 11;
	private static int PERIOD_OF_CHECKING_DEVICE = 50;
	private boolean highDetected = false;
	private boolean lowDetected = false;
	private  boolean highMethaneDetected = false;
	private boolean inProcess = false;
	private long waterSensorActivationInMs = 0;
	
	private int calculateWaitTime(long nextActivationInMs){
		long waitTime = nextActivationInMs - Calendar.getInstance().getTimeInMillis();
		waitTime = Math.max(waitTime, 1);
		return (int)waitTime;
	}
	
	
	/*--------------------- end user code ---------------------*/

	//--------------------- ports
	protected EventHappenedConjPort lowWaterSensor = null;
	protected EventHappenedConjPort highWaterSensor = null;
	protected PumpMotorControllPort controlMotor = null;
	protected PoolingSensorCommandsPort waterFlowSensor = null;
	protected supstanceLevelEventConjPort methaneLevel = null;
	protected AlarmSenderConjPort alarmSender = null;
	protected MessagingProtocolConjPort messageSender = null;

	//--------------------- saps
	protected PTimerConjPort timingService = null;

	//--------------------- services

	//--------------------- optional actors

	//--------------------- interface item IDs
	public static final int IFITEM_lowWaterSensor = 1;
	public static final int IFITEM_highWaterSensor = 2;
	public static final int IFITEM_controlMotor = 3;
	public static final int IFITEM_waterFlowSensor = 4;
	public static final int IFITEM_methaneLevel = 5;
	public static final int IFITEM_alarmSender = 6;
	public static final int IFITEM_messageSender = 7;
	public static final int IFITEM_timingService = 8;

	/*--------------------- attributes ---------------------*/

	/*--------------------- operations ---------------------*/


	//--------------------- construction
	public PumpControlStation(IRTObject parent, String name) {
		super(parent, name);
		setClassName("PumpControlStation");

		// initialize attributes

		// own ports
		lowWaterSensor = new EventHappenedConjPort(this, "lowWaterSensor", IFITEM_lowWaterSensor);
		highWaterSensor = new EventHappenedConjPort(this, "highWaterSensor", IFITEM_highWaterSensor);
		controlMotor = new PumpMotorControllPort(this, "controlMotor", IFITEM_controlMotor);
		waterFlowSensor = new PoolingSensorCommandsPort(this, "waterFlowSensor", IFITEM_waterFlowSensor);
		methaneLevel = new supstanceLevelEventConjPort(this, "methaneLevel", IFITEM_methaneLevel);
		alarmSender = new AlarmSenderConjPort(this, "alarmSender", IFITEM_alarmSender);
		messageSender = new MessagingProtocolConjPort(this, "messageSender", IFITEM_messageSender);

		// own saps
		timingService = new PTimerConjPort(this, "timingService", IFITEM_timingService, 0);

		// own service implementations

		// sub actors

		// wiring


		/* user defined constructor body */

	}

	/* --------------------- attribute setters and getters */


	//--------------------- port getters
	public EventHappenedConjPort getLowWaterSensor (){
		return this.lowWaterSensor;
	}
	public EventHappenedConjPort getHighWaterSensor (){
		return this.highWaterSensor;
	}
	public PumpMotorControllPort getControlMotor (){
		return this.controlMotor;
	}
	public PoolingSensorCommandsPort getWaterFlowSensor (){
		return this.waterFlowSensor;
	}
	public supstanceLevelEventConjPort getMethaneLevel (){
		return this.methaneLevel;
	}
	public AlarmSenderConjPort getAlarmSender (){
		return this.alarmSender;
	}
	public MessagingProtocolConjPort getMessageSender (){
		return this.messageSender;
	}
	public PTimerConjPort getTimingService (){
		return this.timingService;
	}

	//--------------------- lifecycle functions
	public void stop(){
		super.stop();
	}

	public void destroy(){
		/* user defined destructor body */
		DebuggingService.getInstance().addMessageActorDestroy(this);
		super.destroy();
	}

	/* state IDs */
	public static final int STATE_startCheckingPumpState = 2;
	public static final int STATE_askForStatus = 3;
	public static final int STATE_statusGet = 4;
	public static final int STATE_MAX = 5;
	
	/* transition chains */
	public static final int CHAIN_TRANS_initialTimeOut_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_timeouttimingService_initialTimeOut = 1;
	public static final int CHAIN_TRANS_tr1_FROM_startCheckingPumpState_TO_askForStatus_BY_timeouttimingService = 2;
	public static final int CHAIN_TRANS_t_FROM_askForStatus_TO_statusGet_BY_sendStatuswaterFlowSensor = 3;
	public static final int CHAIN_TRANS_tr0_FROM_statusGet_TO_askForStatus_BY_timeouttimingService = 4;
	public static final int CHAIN_TRANS_tr6_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_eventHappenedlowWaterSensor_tr6 = 5;
	public static final int CHAIN_TRANS_tr8_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_eventHappenedhighWaterSensor_tr8 = 6;
	public static final int CHAIN_TRANS_tr9_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_highLevelmethaneLevel_tr9 = 7;
	public static final int CHAIN_TRANS_tr11_FROM_statusGet_TO_startCheckingPumpState_BY_sendValueRegisterwaterFlowSensor = 8;
	public static final int CHAIN_TRANS_tr2_FROM_statusGet_TO_startCheckingPumpState_BY_sendErrorRegisterwaterFlowSensor = 9;
	public static final int CHAIN_TRANS_tr3_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_normalLevelmethaneLevel_tr3 = 10;
	public static final int CHAIN_TRANS_INITIAL_TO__startCheckingPumpState = 11;
	
	/* triggers */
	public static final int POLLING = 0;
	public static final int TRIG_lowWaterSensor__eventHappened = IFITEM_lowWaterSensor + EVT_SHIFT*EventHappened.OUT_eventHappened;
	public static final int TRIG_lowWaterSensor__normalLevel = IFITEM_lowWaterSensor + EVT_SHIFT*EventHappened.OUT_normalLevel;
	public static final int TRIG_highWaterSensor__eventHappened = IFITEM_highWaterSensor + EVT_SHIFT*EventHappened.OUT_eventHappened;
	public static final int TRIG_highWaterSensor__normalLevel = IFITEM_highWaterSensor + EVT_SHIFT*EventHappened.OUT_normalLevel;
	public static final int TRIG_waterFlowSensor__sendStatus = IFITEM_waterFlowSensor + EVT_SHIFT*PoolingSensorCommands.IN_sendStatus;
	public static final int TRIG_waterFlowSensor__sendValueRegister = IFITEM_waterFlowSensor + EVT_SHIFT*PoolingSensorCommands.IN_sendValueRegister;
	public static final int TRIG_waterFlowSensor__sendErrorRegister = IFITEM_waterFlowSensor + EVT_SHIFT*PoolingSensorCommands.IN_sendErrorRegister;
	public static final int TRIG_methaneLevel__highLevel = IFITEM_methaneLevel + EVT_SHIFT*supstanceLevelEvent.OUT_highLevel;
	public static final int TRIG_methaneLevel__normalLevel = IFITEM_methaneLevel + EVT_SHIFT*supstanceLevelEvent.OUT_normalLevel;
	public static final int TRIG_timingService__timeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_timeout;
	public static final int TRIG_timingService__internalTimer = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimer;
	public static final int TRIG_timingService__internalTimeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimeout;
	
	// state names
	protected static final String stateStrings[] = {
		"<no state>",
		"<top>",
		"startCheckingPumpState",
		"askForStatus",
		"statusGet"
	};
	
	// history
	protected int history[] = {NO_STATE, NO_STATE, NO_STATE, NO_STATE, NO_STATE};
	
	private void setState(int new_state) {
		DebuggingService.getInstance().addActorState(this,stateStrings[new_state]);
		this.state = new_state;
	}
	
	/* Entry and Exit Codes */
	protected void entry_startCheckingPumpState() {
		waterSensorActivationInMs += PERIOD_OF_ACTIVATION_MS;
		if (!inProcess){
		    if (highDetected){
		        highDetected = false;
		        inProcess = true;
		        System.out.println("Usao high nakon provere");
		        inProcess = true;
		        controlMotor.turnOnPump();
		        isPumpActive = true;
		        numberOfSameMeasure = 0;
		        shouldGetFlow = false;
		        timingService.kill();
		        timingService.startTimeout(calculateWaitTime(waterSensorActivationInMs));
		    } else if (lowDetected){
		        lowDetected = false;
		        inProcess = true;
		        System.out.println("Usao low nakon provere");
		        controlMotor.turnOffPump();
		        isPumpActive = false;
		        numberOfSameMeasure = 0;
		        shouldGetFlow = false;
		        timingService.kill();
		        timingService.startTimeout(calculateWaitTime(waterSensorActivationInMs));
		    } else if (highMethaneDetected){
		        highMethaneDetected = false;
		        inProcess = true;
		        System.out.println("Usao high metan nakon provere");
		        controlMotor.turnOffPump();
		        isPumpActive = false;
		        numberOfSameMeasure = 0;
		        shouldGetFlow = false;
		        timingService.kill();
		        timingService.startTimeout(calculateWaitTime(waterSensorActivationInMs));
		    } else {
		        inProcess = true;
		        // System.out.println("Normalna provera");
		        shouldGetFlow = true;
		        timingService.kill();
		        waterFlowSensor.startADConverstion();
		        timingService.startTimeout(calculateWaitTime(waterSensorActivationInMs));
		        // System.out.println("Posalji status odbirka");
		    }
		} else {              
		    if (numberOfSameMeasure <= NUM_OF_NEEDED_MEASURE){
		        shouldGetFlow = false;
		        numberOfSameMeasure++;
		        timingService.kill();
		        timingService.startTimeout(calculateWaitTime(waterSensorActivationInMs));
		       // System.out.println("ne racunaj jos uvek");
		    } else {
		        System.out.println("Usao u proveru");
		        numberOfSameMeasure = 0;
		        shouldGetFlow = true;
		        timingService.kill();
		        waterFlowSensor.startADConverstion();
		        timingService.startTimeout(calculateWaitTime(waterSensorActivationInMs));
		        System.out.println("Posalji status odbirka");
		    }
		}
	}
	protected void entry_askForStatus() {
		waterFlowSensor.readStatus();
	}
	
	/* Action Codes */
	protected void action_TRANS_t_FROM_askForStatus_TO_statusGet_BY_sendStatuswaterFlowSensor(InterfaceItemBase ifitem, byte transitionData) {
		int status = transitionData;
		System.out.println("Dobijanje statusa");
		
		if (status == PoolingSensorCommands.Status.NOT_READY.ordinal()){
				timingService.startTimeout(PERIOD_OF_CHECKING_DEVICE);
				System.out.println("Not ready");
		}
		if (status == PoolingSensorCommands.Status.ERROR.ordinal()){
			System.out.println("Error while geting water flow");
			errorCount++;
			if (errorCount >= MAX_ERROR_COUNT){
				System.out.println("Too mutch errors while getting flow");
				errorCount = 0;
				alarmSender.alarmErrorReadingWaterFlow();
			}
			waterFlowSensor.readErrorRegister();	
		}
		if (status == PoolingSensorCommands.Status.READY.ordinal()){
			errorCount = 0;
		 	waterFlowSensor.readValueRegister();
		}
	}
	protected void action_TRANS_tr6_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_eventHappenedlowWaterSensor_tr6(InterfaceItemBase ifitem) {
		lowDetected = true;
	}
	protected void action_TRANS_tr8_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_eventHappenedhighWaterSensor_tr8(InterfaceItemBase ifitem) {
		highDetected = true;
	}
	protected void action_TRANS_tr9_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_highLevelmethaneLevel_tr9(InterfaceItemBase ifitem) {
		highMethaneDetected = true;
	}
	protected void action_TRANS_tr11_FROM_statusGet_TO_startCheckingPumpState_BY_sendValueRegisterwaterFlowSensor(InterfaceItemBase ifitem, double transitionData) {
		double waterFlow = transitionData;
		System.out.println("WaterFlow: " + waterFlow);
		if (isPumpActive && waterFlow == 0){
		    alarmSender.alarmErrorStartingPump();
		    System.out.println("Poslat zahtev za aktiviranje pumpe, a protok vode ne postoji");
		}
		if (!isPumpActive && waterFlow != 0){
		    alarmSender.alarmErrorStoppingPump();
		    System.out.println("Poslat zahtev za gasenje pumpe, a protok vode postoji");
		}
		if (isPumpActive && waterFlow != 0){
		    System.out.println("Pumpa radi i protoka ima");
		}
		if (!isPumpActive && waterFlow == 0){
		    System.out.println("Pumpa ne radi  i nema protoka");
		}
		inProcess = false;
	}
	protected void action_TRANS_tr2_FROM_statusGet_TO_startCheckingPumpState_BY_sendErrorRegisterwaterFlowSensor(InterfaceItemBase ifitem, String transitionData) {
		inProcess = false;
	}
	protected void action_TRANS_tr3_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_normalLevelmethaneLevel_tr3(InterfaceItemBase ifitem) {
		highMethaneDetected = false;
	}
	
	/* State Switch Methods */
	/**
	 * calls exit codes while exiting from the current state to one of its
	 * parent states while remembering the history
	 * @param current__et - the current state
	 * @param to - the final parent state
	 */
	private void exitTo(int current__et, int to) {
		while (current__et!=to) {
			switch (current__et) {
				case STATE_askForStatus:
					this.history[STATE_TOP] = STATE_askForStatus;
					current__et = STATE_TOP;
					break;
				case STATE_startCheckingPumpState:
					this.history[STATE_TOP] = STATE_startCheckingPumpState;
					current__et = STATE_TOP;
					break;
				case STATE_statusGet:
					this.history[STATE_TOP] = STATE_statusGet;
					current__et = STATE_TOP;
					break;
				default:
					/* should not occur */
					break;
			}
		}
	}
	
	/**
	 * calls action, entry and exit codes along a transition chain. The generic data are cast to typed data
	 * matching the trigger of this chain. The ID of the final state is returned
	 * @param chain__et - the chain ID
	 * @param generic_data__et - the generic data pointer
	 * @return the +/- ID of the final state either with a positive sign, that indicates to execute the state's entry code, or a negative sign vice versa
	 */
	private int executeTransitionChain(int chain__et, InterfaceItemBase ifitem, Object generic_data__et) {
		switch (chain__et) {
			case PumpControlStation.CHAIN_TRANS_INITIAL_TO__startCheckingPumpState:
			{
				return STATE_startCheckingPumpState;
			}
			case PumpControlStation.CHAIN_TRANS_initialTimeOut_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_timeouttimingService_initialTimeOut:
			{
				return STATE_startCheckingPumpState;
			}
			case PumpControlStation.CHAIN_TRANS_t_FROM_askForStatus_TO_statusGet_BY_sendStatuswaterFlowSensor:
			{
				byte transitionData = (Byte) generic_data__et;
				action_TRANS_t_FROM_askForStatus_TO_statusGet_BY_sendStatuswaterFlowSensor(ifitem, transitionData);
				return STATE_statusGet;
			}
			case PumpControlStation.CHAIN_TRANS_tr0_FROM_statusGet_TO_askForStatus_BY_timeouttimingService:
			{
				return STATE_askForStatus;
			}
			case PumpControlStation.CHAIN_TRANS_tr11_FROM_statusGet_TO_startCheckingPumpState_BY_sendValueRegisterwaterFlowSensor:
			{
				double transitionData = (Double) generic_data__et;
				action_TRANS_tr11_FROM_statusGet_TO_startCheckingPumpState_BY_sendValueRegisterwaterFlowSensor(ifitem, transitionData);
				return STATE_startCheckingPumpState;
			}
			case PumpControlStation.CHAIN_TRANS_tr1_FROM_startCheckingPumpState_TO_askForStatus_BY_timeouttimingService:
			{
				return STATE_askForStatus;
			}
			case PumpControlStation.CHAIN_TRANS_tr2_FROM_statusGet_TO_startCheckingPumpState_BY_sendErrorRegisterwaterFlowSensor:
			{
				String transitionData = (String) generic_data__et;
				action_TRANS_tr2_FROM_statusGet_TO_startCheckingPumpState_BY_sendErrorRegisterwaterFlowSensor(ifitem, transitionData);
				return STATE_startCheckingPumpState;
			}
			case PumpControlStation.CHAIN_TRANS_tr3_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_normalLevelmethaneLevel_tr3:
			{
				action_TRANS_tr3_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_normalLevelmethaneLevel_tr3(ifitem);
				return STATE_startCheckingPumpState;
			}
			case PumpControlStation.CHAIN_TRANS_tr6_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_eventHappenedlowWaterSensor_tr6:
			{
				action_TRANS_tr6_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_eventHappenedlowWaterSensor_tr6(ifitem);
				return STATE_startCheckingPumpState;
			}
			case PumpControlStation.CHAIN_TRANS_tr8_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_eventHappenedhighWaterSensor_tr8:
			{
				action_TRANS_tr8_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_eventHappenedhighWaterSensor_tr8(ifitem);
				return STATE_startCheckingPumpState;
			}
			case PumpControlStation.CHAIN_TRANS_tr9_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_highLevelmethaneLevel_tr9:
			{
				action_TRANS_tr9_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_highLevelmethaneLevel_tr9(ifitem);
				return STATE_startCheckingPumpState;
			}
				default:
					/* should not occur */
					break;
		}
		return NO_STATE;
	}
	
	/**
	 * calls entry codes while entering a state's history. The ID of the final leaf state is returned
	 * @param state__et - the state which is entered
	 * @return - the ID of the final leaf state
	 */
	private int enterHistory(int state__et) {
		boolean skip_entry__et = false;
		if (state__et >= STATE_MAX) {
			state__et =  (state__et - STATE_MAX);
			skip_entry__et = true;
		}
		while (true) {
			switch (state__et) {
				case STATE_askForStatus:
					if (!(skip_entry__et)) entry_askForStatus();
					/* in leaf state: return state id */
					return STATE_askForStatus;
				case STATE_startCheckingPumpState:
					if (!(skip_entry__et)) entry_startCheckingPumpState();
					/* in leaf state: return state id */
					return STATE_startCheckingPumpState;
				case STATE_statusGet:
					/* in leaf state: return state id */
					return STATE_statusGet;
				case STATE_TOP:
					state__et = this.history[STATE_TOP];
					break;
				default:
					/* should not occur */
					break;
			}
			skip_entry__et = false;
		}
		/* return NO_STATE; // required by CDT but detected as unreachable by JDT because of while (true) */
	}
	
	public void executeInitTransition() {
		int chain__et = PumpControlStation.CHAIN_TRANS_INITIAL_TO__startCheckingPumpState;
		int next__et = executeTransitionChain(chain__et, null, null);
		next__et = enterHistory(next__et);
		setState(next__et);
	}
	
	/* receiveEvent contains the main implementation of the FSM */
	public void receiveEventInternal(InterfaceItemBase ifitem, int localId, int evt, Object generic_data__et) {
		int trigger__et = localId + EVT_SHIFT*evt;
		int chain__et = NOT_CAUGHT;
		int catching_state__et = NO_STATE;
	
		if (!handleSystemEvent(ifitem, evt, generic_data__et)) {
			switch (getState()) {
				case STATE_askForStatus:
					switch(trigger__et) {
						case TRIG_waterFlowSensor__sendStatus:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_t_FROM_askForStatus_TO_statusGet_BY_sendStatuswaterFlowSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						default:
							/* should not occur */
							break;
					}
					break;
				case STATE_startCheckingPumpState:
					switch(trigger__et) {
						case TRIG_highWaterSensor__eventHappened:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_tr8_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_eventHappenedhighWaterSensor_tr8;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_lowWaterSensor__eventHappened:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_tr6_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_eventHappenedlowWaterSensor_tr6;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_methaneLevel__highLevel:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_tr9_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_highLevelmethaneLevel_tr9;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_methaneLevel__normalLevel:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_tr3_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_normalLevelmethaneLevel_tr3;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_timingService__timeout:
							{ 
							if (!shouldGetFlow)
							{
								chain__et = PumpControlStation.CHAIN_TRANS_initialTimeOut_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_timeouttimingService_initialTimeOut;
								catching_state__et = STATE_TOP;
							} else 
							if (shouldGetFlow)
							{
								chain__et = PumpControlStation.CHAIN_TRANS_tr1_FROM_startCheckingPumpState_TO_askForStatus_BY_timeouttimingService;
								catching_state__et = STATE_TOP;
							}
							}
						break;
						default:
							/* should not occur */
							break;
					}
					break;
				case STATE_statusGet:
					switch(trigger__et) {
						case TRIG_timingService__timeout:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_tr0_FROM_statusGet_TO_askForStatus_BY_timeouttimingService;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_waterFlowSensor__sendErrorRegister:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_tr2_FROM_statusGet_TO_startCheckingPumpState_BY_sendErrorRegisterwaterFlowSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_waterFlowSensor__sendValueRegister:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_tr11_FROM_statusGet_TO_startCheckingPumpState_BY_sendValueRegisterwaterFlowSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						default:
							/* should not occur */
							break;
					}
					break;
				default:
					/* should not occur */
					break;
			}
		}
		if (chain__et != NOT_CAUGHT) {
			exitTo(getState(), catching_state__et);
			{
				int next__et = executeTransitionChain(chain__et, ifitem, generic_data__et);
				next__et = enterHistory(next__et);
				setState(next__et);
			}
		}
	}
	public void receiveEvent(InterfaceItemBase ifitem, int evt, Object generic_data__et) {
		int localId = (ifitem==null)? 0 : ifitem.getLocalId();
		receiveEventInternal(ifitem, localId, evt, generic_data__et);
	}

};
