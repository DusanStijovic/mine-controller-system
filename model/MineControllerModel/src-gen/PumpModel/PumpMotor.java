package PumpModel;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import TcpCommunication.*;
import WaterTankModel.*;
import etrice.api.timer.*;
import WaterTankModel.DrainWater.*;
import etrice.api.timer.PTimer.*;
import TcpCommunication.PTrafficLightInterface.*;
import PumpModel.PumpMotorControll.*;



public class PumpMotor extends ActorClassBase {

	/*--------------------- begin user code ---------------------*/
	private static double WATER_DRAIN_ML = 20;
	private static int MOTOR_CHANGE_MODE_TIME_MS = 900;
	private static int SLEEEP_TIME_IN_MS = 100;
	private int timeElapsedToTurnOf = 0;
	private boolean shouldChangeToTurnOff = false;
	
	
	private boolean shouldIgnoreCommand(){
		boolean ignore =  Math.random() > 0.75;
		if (ignore){
			System.out.println("Ignorisana komanda");
		}
		return ignore;
	}
	
	/*--------------------- end user code ---------------------*/

	//--------------------- ports
	protected PumpMotorControllConjPort controlMotor = null;
	protected DrainWaterPort drainWater = null;
	protected PTrafficLightInterfaceConjPort pump = null;

	//--------------------- saps
	protected PTimerConjPort timingService = null;

	//--------------------- services

	//--------------------- optional actors

	//--------------------- interface item IDs
	public static final int IFITEM_controlMotor = 1;
	public static final int IFITEM_drainWater = 2;
	public static final int IFITEM_pump = 3;
	public static final int IFITEM_timingService = 4;

	/*--------------------- attributes ---------------------*/

	/*--------------------- operations ---------------------*/


	//--------------------- construction
	public PumpMotor(IRTObject parent, String name) {
		super(parent, name);
		setClassName("PumpMotor");

		// initialize attributes

		// own ports
		controlMotor = new PumpMotorControllConjPort(this, "controlMotor", IFITEM_controlMotor);
		drainWater = new DrainWaterPort(this, "drainWater", IFITEM_drainWater);
		pump = new PTrafficLightInterfaceConjPort(this, "pump", IFITEM_pump);

		// own saps
		timingService = new PTimerConjPort(this, "timingService", IFITEM_timingService, 0);

		// own service implementations

		// sub actors

		// wiring


		/* user defined constructor body */

	}

	/* --------------------- attribute setters and getters */


	//--------------------- port getters
	public PumpMotorControllConjPort getControlMotor (){
		return this.controlMotor;
	}
	public DrainWaterPort getDrainWater (){
		return this.drainWater;
	}
	public PTrafficLightInterfaceConjPort getPump (){
		return this.pump;
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
	public static final int STATE_initState = 2;
	public static final int STATE_motorTurnOn = 3;
	public static final int STATE_motorTurnOff = 4;
	public static final int STATE_changeModeFromOnToOff = 5;
	public static final int STATE_connect = 6;
	public static final int STATE_MAX = 7;
	
	/* transition chains */
	public static final int CHAIN_TRANS_tr0_FROM_initState_TO_motorTurnOn_BY_turnOnPumpcontrolMotor = 1;
	public static final int CHAIN_TRANS_tr1_FROM_initState_TO_motorTurnOff_BY_turnOffPumpcontrolMotor = 2;
	public static final int CHAIN_TRANS_tr2_FROM_motorTurnOff_TO_motorTurnOn_BY_turnOnPumpcontrolMotor = 3;
	public static final int CHAIN_TRANS_tr3_FROM_motorTurnOn_TO_changeModeFromOnToOff_BY_turnOffPumpcontrolMotor = 4;
	public static final int CHAIN_TRANS_tr4_FROM_motorTurnOn_TO_motorTurnOn_BY_timeouttimingService_tr4 = 5;
	public static final int CHAIN_TRANS_tr5_FROM_changeModeFromOnToOff_TO_changeModeFromOnToOff_BY_timeouttimingService_tr5 = 6;
	public static final int CHAIN_TRANS_tr6_FROM_changeModeFromOnToOff_TO_motorTurnOff_BY_timeouttimingService = 7;
	public static final int CHAIN_TRANS_tr7_FROM_changeModeFromOnToOff_TO_motorTurnOn_BY_turnOnPumpcontrolMotor = 8;
	public static final int CHAIN_TRANS_init0_FROM_connect_TO_initState_BY_connectedpump = 9;
	public static final int CHAIN_TRANS_INITIAL_TO__connect = 10;
	public static final int CHAIN_TRANS_tr8_FROM_motorTurnOn_TO_changeModeFromOnToOff_BY_turnOffPumppump = 11;
	public static final int CHAIN_TRANS_tr9_FROM_motorTurnOff_TO_motorTurnOn_BY_turnOnPumppump = 12;
	public static final int CHAIN_TRANS_tr10_FROM_initState_TO_motorTurnOff_BY_turnOffPumppump = 13;
	public static final int CHAIN_TRANS_tr11_FROM_initState_TO_motorTurnOn_BY_turnOnPumppump = 14;
	public static final int CHAIN_TRANS_tr12_FROM_initState_TO_initState_BY_setPumpFlowpump_tr12 = 15;
	public static final int CHAIN_TRANS_tr13_FROM_motorTurnOff_TO_motorTurnOff_BY_setPumpFlowpump_tr13 = 16;
	public static final int CHAIN_TRANS_tr14_FROM_changeModeFromOnToOff_TO_changeModeFromOnToOff_BY_setPumpFlowpump_tr14 = 17;
	public static final int CHAIN_TRANS_tr15_FROM_motorTurnOn_TO_motorTurnOn_BY_setPumpFlowpump_tr15 = 18;
	
	/* triggers */
	public static final int POLLING = 0;
	public static final int TRIG_controlMotor__turnOnPump = IFITEM_controlMotor + EVT_SHIFT*PumpMotorControll.OUT_turnOnPump;
	public static final int TRIG_controlMotor__turnOffPump = IFITEM_controlMotor + EVT_SHIFT*PumpMotorControll.OUT_turnOffPump;
	public static final int TRIG_pump__connected = IFITEM_pump + EVT_SHIFT*PTrafficLightInterface.OUT_connected;
	public static final int TRIG_pump__setPumpFlow = IFITEM_pump + EVT_SHIFT*PTrafficLightInterface.OUT_setPumpFlow;
	public static final int TRIG_pump__setWaterTenkFlow = IFITEM_pump + EVT_SHIFT*PTrafficLightInterface.OUT_setWaterTenkFlow;
	public static final int TRIG_pump__turnOnPump = IFITEM_pump + EVT_SHIFT*PTrafficLightInterface.OUT_turnOnPump;
	public static final int TRIG_pump__turnOffPump = IFITEM_pump + EVT_SHIFT*PTrafficLightInterface.OUT_turnOffPump;
	public static final int TRIG_timingService__timeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_timeout;
	public static final int TRIG_timingService__internalTimer = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimer;
	public static final int TRIG_timingService__internalTimeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimeout;
	
	// state names
	protected static final String stateStrings[] = {
		"<no state>",
		"<top>",
		"initState",
		"motorTurnOn",
		"motorTurnOff",
		"changeModeFromOnToOff",
		"connect"
	};
	
	// history
	protected int history[] = {NO_STATE, NO_STATE, NO_STATE, NO_STATE, NO_STATE, NO_STATE, NO_STATE};
	
	private void setState(int new_state) {
		DebuggingService.getInstance().addActorState(this,stateStrings[new_state]);
		this.state = new_state;
	}
	
	/* Entry and Exit Codes */
	protected void entry_initState() {
		pump.setPumpState(false);    
	}
	protected void entry_motorTurnOn() {
		pump.setPumpState(true);
	}
	protected void entry_motorTurnOff() {
		//                  System.out.println("Motor se iskljucuje");
		pump.setPumpState(false);
	}
	
	/* Action Codes */
	protected void action_TRANS_tr0_FROM_initState_TO_motorTurnOn_BY_turnOnPumpcontrolMotor(InterfaceItemBase ifitem) {
		timingService.kill();
		timingService.startTimeout(MOTOR_CHANGE_MODE_TIME_MS);
	}
	protected void action_TRANS_tr1_FROM_initState_TO_motorTurnOff_BY_turnOffPumpcontrolMotor(InterfaceItemBase ifitem) {
		timingService.kill();
		timingService.startTimeout(MOTOR_CHANGE_MODE_TIME_MS);
	}
	protected void action_TRANS_tr2_FROM_motorTurnOff_TO_motorTurnOn_BY_turnOnPumpcontrolMotor(InterfaceItemBase ifitem) {
		timingService.kill();
		timingService.startTimeout(MOTOR_CHANGE_MODE_TIME_MS);
	}
	protected void action_TRANS_tr3_FROM_motorTurnOn_TO_changeModeFromOnToOff_BY_turnOffPumpcontrolMotor(InterfaceItemBase ifitem) {
		timeElapsedToTurnOf = 0;
		shouldChangeToTurnOff = false;
		timingService.kill();
		timingService.startTimeout(SLEEEP_TIME_IN_MS);
	}
	protected void action_TRANS_tr4_FROM_motorTurnOn_TO_motorTurnOn_BY_timeouttimingService_tr4(InterfaceItemBase ifitem) {
		drainWater.drainWater(WATER_DRAIN_ML);
		timingService.kill();
		timingService.startTimeout(SLEEEP_TIME_IN_MS);
	}
	protected void action_TRANS_tr5_FROM_changeModeFromOnToOff_TO_changeModeFromOnToOff_BY_timeouttimingService_tr5(InterfaceItemBase ifitem) {
		timingService.kill();
		timingService.startTimeout(SLEEEP_TIME_IN_MS);
		timeElapsedToTurnOf += SLEEEP_TIME_IN_MS;
		drainWater.drainWater(WATER_DRAIN_ML);
		if (timeElapsedToTurnOf == MOTOR_CHANGE_MODE_TIME_MS){
			shouldChangeToTurnOff = true;
		}
	}
	protected void action_TRANS_tr7_FROM_changeModeFromOnToOff_TO_motorTurnOn_BY_turnOnPumpcontrolMotor(InterfaceItemBase ifitem) {
		timingService.kill();
		timingService.startTimeout(MOTOR_CHANGE_MODE_TIME_MS);
	}
	protected void action_TRANS_INITIAL_TO__connect() {
		pump.connect(4021);
	}
	protected void action_TRANS_tr8_FROM_motorTurnOn_TO_changeModeFromOnToOff_BY_turnOffPumppump(InterfaceItemBase ifitem) {
		timeElapsedToTurnOf = 0;
		shouldChangeToTurnOff = false;
		timingService.kill();
		timingService.startTimeout(SLEEEP_TIME_IN_MS);
	}
	protected void action_TRANS_tr9_FROM_motorTurnOff_TO_motorTurnOn_BY_turnOnPumppump(InterfaceItemBase ifitem) {
		timingService.kill();
		timingService.startTimeout(MOTOR_CHANGE_MODE_TIME_MS);
	}
	protected void action_TRANS_tr10_FROM_initState_TO_motorTurnOff_BY_turnOffPumppump(InterfaceItemBase ifitem) {
		timingService.kill();
		timingService.startTimeout(MOTOR_CHANGE_MODE_TIME_MS);
	}
	protected void action_TRANS_tr12_FROM_initState_TO_initState_BY_setPumpFlowpump_tr12(InterfaceItemBase ifitem, double transitionData) {
		WATER_DRAIN_ML = transitionData;
		System.out.println("Promenjeno na: " + transitionData);
	}
	protected void action_TRANS_tr13_FROM_motorTurnOff_TO_motorTurnOff_BY_setPumpFlowpump_tr13(InterfaceItemBase ifitem, double transitionData) {
		WATER_DRAIN_ML = transitionData;
		System.out.println("Promenjeno na: " + transitionData);
	}
	protected void action_TRANS_tr14_FROM_changeModeFromOnToOff_TO_changeModeFromOnToOff_BY_setPumpFlowpump_tr14(InterfaceItemBase ifitem, double transitionData) {
		WATER_DRAIN_ML = transitionData;
		System.out.println("Promenjeno na: " + transitionData);
	}
	protected void action_TRANS_tr15_FROM_motorTurnOn_TO_motorTurnOn_BY_setPumpFlowpump_tr15(InterfaceItemBase ifitem, double transitionData) {
		WATER_DRAIN_ML = transitionData;
		System.out.println("Promenjeno na: " + transitionData);
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
				case STATE_changeModeFromOnToOff:
					this.history[STATE_TOP] = STATE_changeModeFromOnToOff;
					current__et = STATE_TOP;
					break;
				case STATE_connect:
					this.history[STATE_TOP] = STATE_connect;
					current__et = STATE_TOP;
					break;
				case STATE_initState:
					this.history[STATE_TOP] = STATE_initState;
					current__et = STATE_TOP;
					break;
				case STATE_motorTurnOff:
					this.history[STATE_TOP] = STATE_motorTurnOff;
					current__et = STATE_TOP;
					break;
				case STATE_motorTurnOn:
					this.history[STATE_TOP] = STATE_motorTurnOn;
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
			case PumpMotor.CHAIN_TRANS_INITIAL_TO__connect:
			{
				action_TRANS_INITIAL_TO__connect();
				return STATE_connect;
			}
			case PumpMotor.CHAIN_TRANS_init0_FROM_connect_TO_initState_BY_connectedpump:
			{
				return STATE_initState;
			}
			case PumpMotor.CHAIN_TRANS_tr0_FROM_initState_TO_motorTurnOn_BY_turnOnPumpcontrolMotor:
			{
				action_TRANS_tr0_FROM_initState_TO_motorTurnOn_BY_turnOnPumpcontrolMotor(ifitem);
				return STATE_motorTurnOn;
			}
			case PumpMotor.CHAIN_TRANS_tr10_FROM_initState_TO_motorTurnOff_BY_turnOffPumppump:
			{
				action_TRANS_tr10_FROM_initState_TO_motorTurnOff_BY_turnOffPumppump(ifitem);
				return STATE_motorTurnOff;
			}
			case PumpMotor.CHAIN_TRANS_tr11_FROM_initState_TO_motorTurnOn_BY_turnOnPumppump:
			{
				return STATE_motorTurnOn;
			}
			case PumpMotor.CHAIN_TRANS_tr12_FROM_initState_TO_initState_BY_setPumpFlowpump_tr12:
			{
				double transitionData = (Double) generic_data__et;
				action_TRANS_tr12_FROM_initState_TO_initState_BY_setPumpFlowpump_tr12(ifitem, transitionData);
				return STATE_initState;
			}
			case PumpMotor.CHAIN_TRANS_tr13_FROM_motorTurnOff_TO_motorTurnOff_BY_setPumpFlowpump_tr13:
			{
				double transitionData = (Double) generic_data__et;
				action_TRANS_tr13_FROM_motorTurnOff_TO_motorTurnOff_BY_setPumpFlowpump_tr13(ifitem, transitionData);
				return STATE_motorTurnOff;
			}
			case PumpMotor.CHAIN_TRANS_tr14_FROM_changeModeFromOnToOff_TO_changeModeFromOnToOff_BY_setPumpFlowpump_tr14:
			{
				double transitionData = (Double) generic_data__et;
				action_TRANS_tr14_FROM_changeModeFromOnToOff_TO_changeModeFromOnToOff_BY_setPumpFlowpump_tr14(ifitem, transitionData);
				return STATE_changeModeFromOnToOff;
			}
			case PumpMotor.CHAIN_TRANS_tr15_FROM_motorTurnOn_TO_motorTurnOn_BY_setPumpFlowpump_tr15:
			{
				double transitionData = (Double) generic_data__et;
				action_TRANS_tr15_FROM_motorTurnOn_TO_motorTurnOn_BY_setPumpFlowpump_tr15(ifitem, transitionData);
				return STATE_motorTurnOn;
			}
			case PumpMotor.CHAIN_TRANS_tr1_FROM_initState_TO_motorTurnOff_BY_turnOffPumpcontrolMotor:
			{
				action_TRANS_tr1_FROM_initState_TO_motorTurnOff_BY_turnOffPumpcontrolMotor(ifitem);
				return STATE_motorTurnOff;
			}
			case PumpMotor.CHAIN_TRANS_tr2_FROM_motorTurnOff_TO_motorTurnOn_BY_turnOnPumpcontrolMotor:
			{
				action_TRANS_tr2_FROM_motorTurnOff_TO_motorTurnOn_BY_turnOnPumpcontrolMotor(ifitem);
				return STATE_motorTurnOn;
			}
			case PumpMotor.CHAIN_TRANS_tr3_FROM_motorTurnOn_TO_changeModeFromOnToOff_BY_turnOffPumpcontrolMotor:
			{
				action_TRANS_tr3_FROM_motorTurnOn_TO_changeModeFromOnToOff_BY_turnOffPumpcontrolMotor(ifitem);
				return STATE_changeModeFromOnToOff;
			}
			case PumpMotor.CHAIN_TRANS_tr4_FROM_motorTurnOn_TO_motorTurnOn_BY_timeouttimingService_tr4:
			{
				action_TRANS_tr4_FROM_motorTurnOn_TO_motorTurnOn_BY_timeouttimingService_tr4(ifitem);
				return STATE_motorTurnOn;
			}
			case PumpMotor.CHAIN_TRANS_tr5_FROM_changeModeFromOnToOff_TO_changeModeFromOnToOff_BY_timeouttimingService_tr5:
			{
				action_TRANS_tr5_FROM_changeModeFromOnToOff_TO_changeModeFromOnToOff_BY_timeouttimingService_tr5(ifitem);
				return STATE_changeModeFromOnToOff;
			}
			case PumpMotor.CHAIN_TRANS_tr6_FROM_changeModeFromOnToOff_TO_motorTurnOff_BY_timeouttimingService:
			{
				return STATE_motorTurnOff;
			}
			case PumpMotor.CHAIN_TRANS_tr7_FROM_changeModeFromOnToOff_TO_motorTurnOn_BY_turnOnPumpcontrolMotor:
			{
				action_TRANS_tr7_FROM_changeModeFromOnToOff_TO_motorTurnOn_BY_turnOnPumpcontrolMotor(ifitem);
				return STATE_motorTurnOn;
			}
			case PumpMotor.CHAIN_TRANS_tr8_FROM_motorTurnOn_TO_changeModeFromOnToOff_BY_turnOffPumppump:
			{
				action_TRANS_tr8_FROM_motorTurnOn_TO_changeModeFromOnToOff_BY_turnOffPumppump(ifitem);
				return STATE_changeModeFromOnToOff;
			}
			case PumpMotor.CHAIN_TRANS_tr9_FROM_motorTurnOff_TO_motorTurnOn_BY_turnOnPumppump:
			{
				action_TRANS_tr9_FROM_motorTurnOff_TO_motorTurnOn_BY_turnOnPumppump(ifitem);
				return STATE_motorTurnOn;
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
				case STATE_changeModeFromOnToOff:
					/* in leaf state: return state id */
					return STATE_changeModeFromOnToOff;
				case STATE_connect:
					/* in leaf state: return state id */
					return STATE_connect;
				case STATE_initState:
					if (!(skip_entry__et)) entry_initState();
					/* in leaf state: return state id */
					return STATE_initState;
				case STATE_motorTurnOff:
					if (!(skip_entry__et)) entry_motorTurnOff();
					/* in leaf state: return state id */
					return STATE_motorTurnOff;
				case STATE_motorTurnOn:
					if (!(skip_entry__et)) entry_motorTurnOn();
					/* in leaf state: return state id */
					return STATE_motorTurnOn;
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
		int chain__et = PumpMotor.CHAIN_TRANS_INITIAL_TO__connect;
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
				case STATE_changeModeFromOnToOff:
					switch(trigger__et) {
						case TRIG_controlMotor__turnOnPump:
							{
								chain__et = PumpMotor.CHAIN_TRANS_tr7_FROM_changeModeFromOnToOff_TO_motorTurnOn_BY_turnOnPumpcontrolMotor;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_pump__setPumpFlow:
							{
								chain__et = PumpMotor.CHAIN_TRANS_tr14_FROM_changeModeFromOnToOff_TO_changeModeFromOnToOff_BY_setPumpFlowpump_tr14;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_timingService__timeout:
							{ 
							if (!shouldChangeToTurnOff)
							{
								chain__et = PumpMotor.CHAIN_TRANS_tr5_FROM_changeModeFromOnToOff_TO_changeModeFromOnToOff_BY_timeouttimingService_tr5;
								catching_state__et = STATE_TOP;
							} else 
							if (shouldChangeToTurnOff)
							{
								chain__et = PumpMotor.CHAIN_TRANS_tr6_FROM_changeModeFromOnToOff_TO_motorTurnOff_BY_timeouttimingService;
								catching_state__et = STATE_TOP;
							}
							}
						break;
						default:
							/* should not occur */
							break;
					}
					break;
				case STATE_connect:
					switch(trigger__et) {
						case TRIG_pump__connected:
							{
								chain__et = PumpMotor.CHAIN_TRANS_init0_FROM_connect_TO_initState_BY_connectedpump;
								catching_state__et = STATE_TOP;
							}
						break;
						default:
							/* should not occur */
							break;
					}
					break;
				case STATE_initState:
					switch(trigger__et) {
						case TRIG_controlMotor__turnOffPump:
							{
								chain__et = PumpMotor.CHAIN_TRANS_tr1_FROM_initState_TO_motorTurnOff_BY_turnOffPumpcontrolMotor;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_controlMotor__turnOnPump:
							{
								chain__et = PumpMotor.CHAIN_TRANS_tr0_FROM_initState_TO_motorTurnOn_BY_turnOnPumpcontrolMotor;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_pump__setPumpFlow:
							{
								chain__et = PumpMotor.CHAIN_TRANS_tr12_FROM_initState_TO_initState_BY_setPumpFlowpump_tr12;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_pump__turnOffPump:
							{
								chain__et = PumpMotor.CHAIN_TRANS_tr10_FROM_initState_TO_motorTurnOff_BY_turnOffPumppump;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_pump__turnOnPump:
							{
								chain__et = PumpMotor.CHAIN_TRANS_tr11_FROM_initState_TO_motorTurnOn_BY_turnOnPumppump;
								catching_state__et = STATE_TOP;
							}
						break;
						default:
							/* should not occur */
							break;
					}
					break;
				case STATE_motorTurnOff:
					switch(trigger__et) {
						case TRIG_controlMotor__turnOnPump:
							{ 
							if (!shouldIgnoreCommand())
							{
								chain__et = PumpMotor.CHAIN_TRANS_tr2_FROM_motorTurnOff_TO_motorTurnOn_BY_turnOnPumpcontrolMotor;
								catching_state__et = STATE_TOP;
							}
							}
						break;
						case TRIG_pump__setPumpFlow:
							{
								chain__et = PumpMotor.CHAIN_TRANS_tr13_FROM_motorTurnOff_TO_motorTurnOff_BY_setPumpFlowpump_tr13;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_pump__turnOnPump:
							{
								chain__et = PumpMotor.CHAIN_TRANS_tr9_FROM_motorTurnOff_TO_motorTurnOn_BY_turnOnPumppump;
								catching_state__et = STATE_TOP;
							}
						break;
						default:
							/* should not occur */
							break;
					}
					break;
				case STATE_motorTurnOn:
					switch(trigger__et) {
						case TRIG_controlMotor__turnOffPump:
							{
								chain__et = PumpMotor.CHAIN_TRANS_tr3_FROM_motorTurnOn_TO_changeModeFromOnToOff_BY_turnOffPumpcontrolMotor;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_pump__setPumpFlow:
							{
								chain__et = PumpMotor.CHAIN_TRANS_tr15_FROM_motorTurnOn_TO_motorTurnOn_BY_setPumpFlowpump_tr15;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_pump__turnOffPump:
							{
								chain__et = PumpMotor.CHAIN_TRANS_tr8_FROM_motorTurnOn_TO_changeModeFromOnToOff_BY_turnOffPumppump;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_timingService__timeout:
							{
								chain__et = PumpMotor.CHAIN_TRANS_tr4_FROM_motorTurnOn_TO_motorTurnOn_BY_timeouttimingService_tr4;
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
