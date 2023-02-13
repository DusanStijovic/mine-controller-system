package PumpModel;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import WaterTankModel.*;
import etrice.api.timer.*;
import WaterTankModel.DrainWater.*;
import etrice.api.timer.PTimer.*;
import PumpModel.PumpMotorControll.*;



public class PumpMotor extends ActorClassBase {

	/*--------------------- begin user code ---------------------*/
	private static double WATER_DRAIN_ML_PER_MS = 10;
	private static int MOTOR_CHANGE_MODE_TIME_MS = 900;
	private static int SLEEEP_TIME_IN_MS = 10;
	private int timeElapsedToTurnOf = 0;
	private boolean shouldChangeToTurnOff = false;
	
	
	private boolean shouldIgnoreCommand(){
		boolean ignore =  Math.random() > 0.4;
		if (ignore){
			System.out.println("Ignorisana komanda");
		}
		return ignore;
	}
	
	/*--------------------- end user code ---------------------*/

	//--------------------- ports
	protected PumpMotorControllConjPort controlMotor = null;
	protected DrainWaterPort drainWater = null;

	//--------------------- saps
	protected PTimerConjPort timingService = null;

	//--------------------- services

	//--------------------- optional actors

	//--------------------- interface item IDs
	public static final int IFITEM_controlMotor = 1;
	public static final int IFITEM_drainWater = 2;
	public static final int IFITEM_timingService = 3;

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
	public static final int STATE_MAX = 6;
	
	/* transition chains */
	public static final int CHAIN_TRANS_INITIAL_TO__initState = 1;
	public static final int CHAIN_TRANS_tr0_FROM_initState_TO_motorTurnOn_BY_turnOnPumpcontrolMotor = 2;
	public static final int CHAIN_TRANS_tr1_FROM_initState_TO_motorTurnOff_BY_turnOffPumpcontrolMotor = 3;
	public static final int CHAIN_TRANS_tr2_FROM_motorTurnOff_TO_motorTurnOn_BY_turnOnPumpcontrolMotor = 4;
	public static final int CHAIN_TRANS_tr3_FROM_motorTurnOn_TO_changeModeFromOnToOff_BY_turnOffPumpcontrolMotor = 5;
	public static final int CHAIN_TRANS_tr4_FROM_motorTurnOn_TO_motorTurnOn_BY_timeouttimingService_tr4 = 6;
	public static final int CHAIN_TRANS_tr5_FROM_changeModeFromOnToOff_TO_changeModeFromOnToOff_BY_timeouttimingService_tr5 = 7;
	public static final int CHAIN_TRANS_tr6_FROM_changeModeFromOnToOff_TO_motorTurnOff_BY_timeouttimingService = 8;
	public static final int CHAIN_TRANS_tr7_FROM_changeModeFromOnToOff_TO_motorTurnOn_BY_turnOnPumpcontrolMotor = 9;
	
	/* triggers */
	public static final int POLLING = 0;
	public static final int TRIG_controlMotor__turnOnPump = IFITEM_controlMotor + EVT_SHIFT*PumpMotorControll.OUT_turnOnPump;
	public static final int TRIG_controlMotor__turnOffPump = IFITEM_controlMotor + EVT_SHIFT*PumpMotorControll.OUT_turnOffPump;
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
		"changeModeFromOnToOff"
	};
	
	// history
	protected int history[] = {NO_STATE, NO_STATE, NO_STATE, NO_STATE, NO_STATE, NO_STATE};
	
	private void setState(int new_state) {
		DebuggingService.getInstance().addActorState(this,stateStrings[new_state]);
		this.state = new_state;
	}
	
	/* Entry and Exit Codes */
	protected void entry_initState() {
	}
	protected void entry_motorTurnOff() {
		//					System.out.println("Motor se iskljucuje");
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
		drainWater.drainWater(WATER_DRAIN_ML_PER_MS * SLEEEP_TIME_IN_MS);
		timingService.kill();
		timingService.startTimeout(SLEEEP_TIME_IN_MS);
	}
	protected void action_TRANS_tr5_FROM_changeModeFromOnToOff_TO_changeModeFromOnToOff_BY_timeouttimingService_tr5(InterfaceItemBase ifitem) {
		timingService.kill();
		timingService.startTimeout(SLEEEP_TIME_IN_MS);
		timeElapsedToTurnOf += SLEEEP_TIME_IN_MS;
		drainWater.drainWater(WATER_DRAIN_ML_PER_MS * SLEEEP_TIME_IN_MS);
		if (timeElapsedToTurnOf == MOTOR_CHANGE_MODE_TIME_MS){
			shouldChangeToTurnOff = true;
		}
	}
	protected void action_TRANS_tr7_FROM_changeModeFromOnToOff_TO_motorTurnOn_BY_turnOnPumpcontrolMotor(InterfaceItemBase ifitem) {
		timingService.kill();
		timingService.startTimeout(MOTOR_CHANGE_MODE_TIME_MS);
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
			case PumpMotor.CHAIN_TRANS_INITIAL_TO__initState:
			{
				return STATE_initState;
			}
			case PumpMotor.CHAIN_TRANS_tr0_FROM_initState_TO_motorTurnOn_BY_turnOnPumpcontrolMotor:
			{
				action_TRANS_tr0_FROM_initState_TO_motorTurnOn_BY_turnOnPumpcontrolMotor(ifitem);
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
				case STATE_initState:
					if (!(skip_entry__et)) entry_initState();
					/* in leaf state: return state id */
					return STATE_initState;
				case STATE_motorTurnOff:
					if (!(skip_entry__et)) entry_motorTurnOff();
					/* in leaf state: return state id */
					return STATE_motorTurnOff;
				case STATE_motorTurnOn:
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
		int chain__et = PumpMotor.CHAIN_TRANS_INITIAL_TO__initState;
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
