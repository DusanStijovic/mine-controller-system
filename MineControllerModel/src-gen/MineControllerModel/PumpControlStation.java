package MineControllerModel;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import InterrupSensorModel.*;
import PoolingSensorModel.*;
import etrice.api.timer.*;
import InterrupSensorModel.EventHappened.*;
import etrice.api.timer.PTimer.*;
import PoolingSensorModel.PoolingSensorCommands.*;
import MineControllerModel.PumpMotorControll.*;



public class PumpControlStation extends ActorClassBase {

	/*--------------------- begin user code ---------------------*/
	private boolean isPumpActive = false;
	private int MAX_ERROR_COUNT = 10;
	private boolean shouldGetFlow;
	private int errorCount = 0;
	private int numberOfSameMeasure = 0;
	private static int MAX_TIME_FOR_MOTOR_CHANGE_MS = 900;
	private static int PERIOD_OF_ACTIVATION_MS = 20;
	private static int NUM_OF_NEEDED_MEASURE = MAX_TIME_FOR_MOTOR_CHANGE_MS / PERIOD_OF_ACTIVATION_MS;
	private static int PERIOD_OF_CHECKING_DEVICE = 20;
	
	/*--------------------- end user code ---------------------*/

	//--------------------- ports
	protected EventHappenedConjPort lowWaterSensor = null;
	protected EventHappenedConjPort highWaterSensor = null;
	protected PumpMotorControllPort controlMotor = null;
	protected PoolingSensorCommandsPort waterFlowSensor = null;

	//--------------------- saps
	protected PTimerConjPort timingService = null;

	//--------------------- services

	//--------------------- optional actors

	//--------------------- interface item IDs
	public static final int IFITEM_lowWaterSensor = 1;
	public static final int IFITEM_highWaterSensor = 2;
	public static final int IFITEM_controlMotor = 3;
	public static final int IFITEM_waterFlowSensor = 4;
	public static final int IFITEM_timingService = 5;

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
	public static final int STATE_lowWaterLevelHandler = 3;
	public static final int STATE_highWaterHandler = 4;
	public static final int STATE_init = 5;
	public static final int STATE_errorRead = 6;
	public static final int STATE_valueRead = 7;
	public static final int STATE_askForStatus = 8;
	public static final int STATE_statusGet = 9;
	public static final int STATE_MAX = 10;
	
	/* transition chains */
	public static final int CHAIN_TRANS_INITIAL_TO__init = 1;
	public static final int CHAIN_TRANS_lowWaterHappened_FROM_lowWaterTP_TO_lowWaterLevelHandler_BY_eventHappenedlowWaterSensor = 2;
	public static final int CHAIN_TRANS_c_FROM_highWaterTP_TO_highWaterHandler_BY_eventHappenedhighWaterSensor = 3;
	public static final int CHAIN_TRANS_initialTimeOut_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_timeouttimingService_initialTimeOut = 4;
	public static final int CHAIN_TRANS_tr1_FROM_startCheckingPumpState_TO_askForStatus_BY_timeouttimingService = 5;
	public static final int CHAIN_TRANS_t_FROM_askForStatus_TO_statusGet_BY_sendStatuswaterFlowSensor = 6;
	public static final int CHAIN_TRANS_tr0_FROM_statusGet_TO_askForStatus_BY_timeouttimingService = 7;
	public static final int CHAIN_TRANS_tr2_FROM_statusGet_TO_valueRead_BY_sendValueRegisterwaterFlowSensor = 8;
	public static final int CHAIN_TRANS_tr3_FROM_statusGet_TO_errorRead_BY_sendErrorRegisterwaterFlowSensor = 9;
	public static final int CHAIN_TRANS_tr4_FROM_lowWaterLevelHandler_TO_startCheckingPumpState_BY_timeouttimingService = 10;
	public static final int CHAIN_TRANS_tr5_FROM_highWaterHandler_TO_startCheckingPumpState_BY_timeouttimingService = 11;
	
	/* triggers */
	public static final int POLLING = 0;
	public static final int TRIG_lowWaterSensor__eventHappened = IFITEM_lowWaterSensor + EVT_SHIFT*EventHappened.OUT_eventHappened;
	public static final int TRIG_highWaterSensor__eventHappened = IFITEM_highWaterSensor + EVT_SHIFT*EventHappened.OUT_eventHappened;
	public static final int TRIG_waterFlowSensor__sendStatus = IFITEM_waterFlowSensor + EVT_SHIFT*PoolingSensorCommands.IN_sendStatus;
	public static final int TRIG_waterFlowSensor__sendValueRegister = IFITEM_waterFlowSensor + EVT_SHIFT*PoolingSensorCommands.IN_sendValueRegister;
	public static final int TRIG_waterFlowSensor__sendErrorRegister = IFITEM_waterFlowSensor + EVT_SHIFT*PoolingSensorCommands.IN_sendErrorRegister;
	public static final int TRIG_timingService__timeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_timeout;
	public static final int TRIG_timingService__internalTimer = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimer;
	public static final int TRIG_timingService__internalTimeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimeout;
	
	// state names
	protected static final String stateStrings[] = {
		"<no state>",
		"<top>",
		"startCheckingPumpState",
		"lowWaterLevelHandler",
		"highWaterHandler",
		"init",
		"errorRead",
		"valueRead",
		"askForStatus",
		"statusGet"
	};
	
	// history
	protected int history[] = {NO_STATE, NO_STATE, NO_STATE, NO_STATE, NO_STATE, NO_STATE, NO_STATE, NO_STATE, NO_STATE, NO_STATE};
	
	private void setState(int new_state) {
		DebuggingService.getInstance().addActorState(this,stateStrings[new_state]);
		this.state = new_state;
	}
	
	/* Entry and Exit Codes */
	protected void entry_startCheckingPumpState() {
		if (numberOfSameMeasure <= NUM_OF_NEEDED_MEASURE){
			numberOfSameMeasure++;
			timingService.kill();
			timingService.startTimeout(PERIOD_OF_ACTIVATION_MS);
			System.out.println("dalje racunaj");
		} else {
			numberOfSameMeasure = 0;
			waterFlowSensor.startADConverstion();
			shouldGetFlow = true;
			timingService.kill();
			timingService.startTimeout(PERIOD_OF_CHECKING_DEVICE);
			System.out.println("daj racun");
			
		}
	}
	protected void entry_lowWaterLevelHandler() {
		if (isPumpActive){
			controlMotor.turnOffPump();
			isPumpActive = false;
			numberOfSameMeasure = 0;
			shouldGetFlow = false;
			timingService.kill();
			timingService.startTimeout(PERIOD_OF_ACTIVATION_MS);
		}
	}
	protected void entry_highWaterHandler() {
		if (!isPumpActive){
			controlMotor.turnOnPump();
			isPumpActive = true;
			numberOfSameMeasure = 0;
			shouldGetFlow = false;
			timingService.startTimeout(PERIOD_OF_ACTIVATION_MS);
		}
	}
	protected void entry_askForStatus() {
		waterFlowSensor.readStatus();
	}
	
	/* Action Codes */
	protected void action_TRANS_t_FROM_askForStatus_TO_statusGet_BY_sendStatuswaterFlowSensor(InterfaceItemBase ifitem, byte transitionData) {
		int status = transitionData;
		if (status == PoolingSensorCommands.Status.NOT_READY.ordinal()){
				errorCount = 0;
				waterFlowSensor.readErrorRegister();			
		}
		if (status == PoolingSensorCommands.Status.ERROR.ordinal()){
			System.out.println("Error while geting water flow");
			errorCount++;
			if (errorCount >= MAX_ERROR_COUNT && isPumpActive){
				System.out.println("Too mutch errors while getting flow, turrning off pump");
				errorCount = 0;
				controlMotor.turnOffPump();
				waterFlowSensor.startADConverstion();
				timingService.kill();
				timingService.startTimeout(PERIOD_OF_CHECKING_DEVICE);		
			}		
		}
		if (status == PoolingSensorCommands.Status.READY.ordinal()){
			 waterFlowSensor.readValueRegister();
		}
	}
	protected void action_TRANS_tr2_FROM_statusGet_TO_valueRead_BY_sendValueRegisterwaterFlowSensor(InterfaceItemBase ifitem, double transitionData) {
		double waterFlow = transitionData;
		if (isPumpActive && waterFlow == 0){
			System.out.println("Pumpa aktivna a protok vode ne postoji");
		}
		if (!isPumpActive && waterFlow != 0){
			System.out.println("Pumpa iskljucena a protok vode postoji");
		}
		if (isPumpActive && waterFlow != 0){
			System.out.println("Pumpa radi i protoka ima");
		}
		if (!isPumpActive && waterFlow == 0){
			System.out.println("Pumpa ne radi  i nema protoka");
		}
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
				case STATE_errorRead:
					this.history[STATE_TOP] = STATE_errorRead;
					current__et = STATE_TOP;
					break;
				case STATE_highWaterHandler:
					this.history[STATE_TOP] = STATE_highWaterHandler;
					current__et = STATE_TOP;
					break;
				case STATE_init:
					this.history[STATE_TOP] = STATE_init;
					current__et = STATE_TOP;
					break;
				case STATE_lowWaterLevelHandler:
					this.history[STATE_TOP] = STATE_lowWaterLevelHandler;
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
				case STATE_valueRead:
					this.history[STATE_TOP] = STATE_valueRead;
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
			case PumpControlStation.CHAIN_TRANS_INITIAL_TO__init:
			{
				return STATE_init;
			}
			case PumpControlStation.CHAIN_TRANS_c_FROM_highWaterTP_TO_highWaterHandler_BY_eventHappenedhighWaterSensor:
			{
				return STATE_highWaterHandler;
			}
			case PumpControlStation.CHAIN_TRANS_initialTimeOut_FROM_startCheckingPumpState_TO_startCheckingPumpState_BY_timeouttimingService_initialTimeOut:
			{
				return STATE_startCheckingPumpState;
			}
			case PumpControlStation.CHAIN_TRANS_lowWaterHappened_FROM_lowWaterTP_TO_lowWaterLevelHandler_BY_eventHappenedlowWaterSensor:
			{
				return STATE_lowWaterLevelHandler;
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
			case PumpControlStation.CHAIN_TRANS_tr1_FROM_startCheckingPumpState_TO_askForStatus_BY_timeouttimingService:
			{
				return STATE_askForStatus;
			}
			case PumpControlStation.CHAIN_TRANS_tr2_FROM_statusGet_TO_valueRead_BY_sendValueRegisterwaterFlowSensor:
			{
				double transitionData = (Double) generic_data__et;
				action_TRANS_tr2_FROM_statusGet_TO_valueRead_BY_sendValueRegisterwaterFlowSensor(ifitem, transitionData);
				return STATE_valueRead;
			}
			case PumpControlStation.CHAIN_TRANS_tr3_FROM_statusGet_TO_errorRead_BY_sendErrorRegisterwaterFlowSensor:
			{
				String transitionData = (String) generic_data__et;
				return STATE_errorRead;
			}
			case PumpControlStation.CHAIN_TRANS_tr4_FROM_lowWaterLevelHandler_TO_startCheckingPumpState_BY_timeouttimingService:
			{
				return STATE_startCheckingPumpState;
			}
			case PumpControlStation.CHAIN_TRANS_tr5_FROM_highWaterHandler_TO_startCheckingPumpState_BY_timeouttimingService:
			{
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
				case STATE_errorRead:
					/* in leaf state: return state id */
					return STATE_errorRead;
				case STATE_highWaterHandler:
					if (!(skip_entry__et)) entry_highWaterHandler();
					/* in leaf state: return state id */
					return STATE_highWaterHandler;
				case STATE_init:
					/* in leaf state: return state id */
					return STATE_init;
				case STATE_lowWaterLevelHandler:
					if (!(skip_entry__et)) entry_lowWaterLevelHandler();
					/* in leaf state: return state id */
					return STATE_lowWaterLevelHandler;
				case STATE_startCheckingPumpState:
					if (!(skip_entry__et)) entry_startCheckingPumpState();
					/* in leaf state: return state id */
					return STATE_startCheckingPumpState;
				case STATE_statusGet:
					/* in leaf state: return state id */
					return STATE_statusGet;
				case STATE_valueRead:
					/* in leaf state: return state id */
					return STATE_valueRead;
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
		int chain__et = PumpControlStation.CHAIN_TRANS_INITIAL_TO__init;
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
						case TRIG_highWaterSensor__eventHappened:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_c_FROM_highWaterTP_TO_highWaterHandler_BY_eventHappenedhighWaterSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_lowWaterSensor__eventHappened:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_lowWaterHappened_FROM_lowWaterTP_TO_lowWaterLevelHandler_BY_eventHappenedlowWaterSensor;
								catching_state__et = STATE_TOP;
							}
						break;
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
				case STATE_errorRead:
					switch(trigger__et) {
						case TRIG_highWaterSensor__eventHappened:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_c_FROM_highWaterTP_TO_highWaterHandler_BY_eventHappenedhighWaterSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_lowWaterSensor__eventHappened:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_lowWaterHappened_FROM_lowWaterTP_TO_lowWaterLevelHandler_BY_eventHappenedlowWaterSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						default:
							/* should not occur */
							break;
					}
					break;
				case STATE_highWaterHandler:
					switch(trigger__et) {
						case TRIG_highWaterSensor__eventHappened:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_c_FROM_highWaterTP_TO_highWaterHandler_BY_eventHappenedhighWaterSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_lowWaterSensor__eventHappened:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_lowWaterHappened_FROM_lowWaterTP_TO_lowWaterLevelHandler_BY_eventHappenedlowWaterSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_timingService__timeout:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_tr5_FROM_highWaterHandler_TO_startCheckingPumpState_BY_timeouttimingService;
								catching_state__et = STATE_TOP;
							}
						break;
						default:
							/* should not occur */
							break;
					}
					break;
				case STATE_init:
					switch(trigger__et) {
						case TRIG_highWaterSensor__eventHappened:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_c_FROM_highWaterTP_TO_highWaterHandler_BY_eventHappenedhighWaterSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_lowWaterSensor__eventHappened:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_lowWaterHappened_FROM_lowWaterTP_TO_lowWaterLevelHandler_BY_eventHappenedlowWaterSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						default:
							/* should not occur */
							break;
					}
					break;
				case STATE_lowWaterLevelHandler:
					switch(trigger__et) {
						case TRIG_highWaterSensor__eventHappened:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_c_FROM_highWaterTP_TO_highWaterHandler_BY_eventHappenedhighWaterSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_lowWaterSensor__eventHappened:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_lowWaterHappened_FROM_lowWaterTP_TO_lowWaterLevelHandler_BY_eventHappenedlowWaterSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_timingService__timeout:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_tr4_FROM_lowWaterLevelHandler_TO_startCheckingPumpState_BY_timeouttimingService;
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
								chain__et = PumpControlStation.CHAIN_TRANS_c_FROM_highWaterTP_TO_highWaterHandler_BY_eventHappenedhighWaterSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_lowWaterSensor__eventHappened:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_lowWaterHappened_FROM_lowWaterTP_TO_lowWaterLevelHandler_BY_eventHappenedlowWaterSensor;
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
						case TRIG_highWaterSensor__eventHappened:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_c_FROM_highWaterTP_TO_highWaterHandler_BY_eventHappenedhighWaterSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_lowWaterSensor__eventHappened:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_lowWaterHappened_FROM_lowWaterTP_TO_lowWaterLevelHandler_BY_eventHappenedlowWaterSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_timingService__timeout:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_tr0_FROM_statusGet_TO_askForStatus_BY_timeouttimingService;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_waterFlowSensor__sendErrorRegister:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_tr3_FROM_statusGet_TO_errorRead_BY_sendErrorRegisterwaterFlowSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_waterFlowSensor__sendValueRegister:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_tr2_FROM_statusGet_TO_valueRead_BY_sendValueRegisterwaterFlowSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						default:
							/* should not occur */
							break;
					}
					break;
				case STATE_valueRead:
					switch(trigger__et) {
						case TRIG_highWaterSensor__eventHappened:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_c_FROM_highWaterTP_TO_highWaterHandler_BY_eventHappenedhighWaterSensor;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_lowWaterSensor__eventHappened:
							{
								chain__et = PumpControlStation.CHAIN_TRANS_lowWaterHappened_FROM_lowWaterTP_TO_lowWaterLevelHandler_BY_eventHappenedlowWaterSensor;
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
