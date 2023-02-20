package InterrupSensorModel;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import etrice.api.timer.*;
import InterrupSensorModel.EventHappened.*;
import etrice.api.timer.PTimer.*;



public class InterruptSensor extends ActorClassBase {

	/*--------------------- begin user code ---------------------*/
	private static int PERIOD_OF_ACTIVATION_MS = 5000;
	private boolean eventHappened = false;
	
	/*--------------------- end user code ---------------------*/

	//--------------------- ports
	protected EventHappenedConjPort inputEvent = null;
	protected EventHappenedPort outputEvent = null;

	//--------------------- saps
	protected PTimerConjPort timingService = null;

	//--------------------- services

	//--------------------- optional actors

	//--------------------- interface item IDs
	public static final int IFITEM_inputEvent = 1;
	public static final int IFITEM_outputEvent = 2;
	public static final int IFITEM_timingService = 3;

	/*--------------------- attributes ---------------------*/

	/*--------------------- operations ---------------------*/


	//--------------------- construction
	public InterruptSensor(IRTObject parent, String name) {
		super(parent, name);
		setClassName("InterruptSensor");

		// initialize attributes

		// own ports
		inputEvent = new EventHappenedConjPort(this, "inputEvent", IFITEM_inputEvent);
		outputEvent = new EventHappenedPort(this, "outputEvent", IFITEM_outputEvent);

		// own saps
		timingService = new PTimerConjPort(this, "timingService", IFITEM_timingService, 0);

		// own service implementations

		// sub actors

		// wiring


		/* user defined constructor body */

	}

	/* --------------------- attribute setters and getters */


	//--------------------- port getters
	public EventHappenedConjPort getInputEvent (){
		return this.inputEvent;
	}
	public EventHappenedPort getOutputEvent (){
		return this.outputEvent;
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
	public static final int STATE_waitForEvent = 2;
	public static final int STATE_MAX = 3;
	
	/* transition chains */
	public static final int CHAIN_TRANS_INITIAL_TO__waitForEvent = 1;
	public static final int CHAIN_TRANS_eventHappened_FROM_waitForEvent_TO_waitForEvent_BY_eventHappenedinputEvent_eventHappened = 2;
	public static final int CHAIN_TRANS_tr0_FROM_waitForEvent_TO_waitForEvent_BY_timeouttimingService_tr0 = 3;
	
	/* triggers */
	public static final int POLLING = 0;
	public static final int TRIG_inputEvent__eventHappened = IFITEM_inputEvent + EVT_SHIFT*EventHappened.OUT_eventHappened;
	public static final int TRIG_inputEvent__normalLevel = IFITEM_inputEvent + EVT_SHIFT*EventHappened.OUT_normalLevel;
	public static final int TRIG_timingService__timeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_timeout;
	public static final int TRIG_timingService__internalTimer = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimer;
	public static final int TRIG_timingService__internalTimeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimeout;
	
	// state names
	protected static final String stateStrings[] = {
		"<no state>",
		"<top>",
		"waitForEvent"
	};
	
	// history
	protected int history[] = {NO_STATE, NO_STATE, NO_STATE};
	
	private void setState(int new_state) {
		DebuggingService.getInstance().addActorState(this,stateStrings[new_state]);
		this.state = new_state;
	}
	
	/* Entry and Exit Codes */
	
	/* Action Codes */
	protected void action_TRANS_INITIAL_TO__waitForEvent() {
		timingService.startTimeout(PERIOD_OF_ACTIVATION_MS);                        
	}
	protected void action_TRANS_eventHappened_FROM_waitForEvent_TO_waitForEvent_BY_eventHappenedinputEvent_eventHappened(InterfaceItemBase ifitem) {
		eventHappened = true;
	}
	protected void action_TRANS_tr0_FROM_waitForEvent_TO_waitForEvent_BY_timeouttimingService_tr0(InterfaceItemBase ifitem) {
		if(eventHappened){
		    outputEvent.eventHappened();
		    eventHappened = false;
		}
		timingService.startTimeout(PERIOD_OF_ACTIVATION_MS);						
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
				case STATE_waitForEvent:
					this.history[STATE_TOP] = STATE_waitForEvent;
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
			case InterruptSensor.CHAIN_TRANS_INITIAL_TO__waitForEvent:
			{
				action_TRANS_INITIAL_TO__waitForEvent();
				return STATE_waitForEvent;
			}
			case InterruptSensor.CHAIN_TRANS_eventHappened_FROM_waitForEvent_TO_waitForEvent_BY_eventHappenedinputEvent_eventHappened:
			{
				action_TRANS_eventHappened_FROM_waitForEvent_TO_waitForEvent_BY_eventHappenedinputEvent_eventHappened(ifitem);
				return STATE_waitForEvent;
			}
			case InterruptSensor.CHAIN_TRANS_tr0_FROM_waitForEvent_TO_waitForEvent_BY_timeouttimingService_tr0:
			{
				action_TRANS_tr0_FROM_waitForEvent_TO_waitForEvent_BY_timeouttimingService_tr0(ifitem);
				return STATE_waitForEvent;
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
		if (state__et >= STATE_MAX) {
			state__et =  (state__et - STATE_MAX);
		}
		while (true) {
			switch (state__et) {
				case STATE_waitForEvent:
					/* in leaf state: return state id */
					return STATE_waitForEvent;
				case STATE_TOP:
					state__et = this.history[STATE_TOP];
					break;
				default:
					/* should not occur */
					break;
			}
		}
		/* return NO_STATE; // required by CDT but detected as unreachable by JDT because of while (true) */
	}
	
	public void executeInitTransition() {
		int chain__et = InterruptSensor.CHAIN_TRANS_INITIAL_TO__waitForEvent;
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
				case STATE_waitForEvent:
					switch(trigger__et) {
						case TRIG_inputEvent__eventHappened:
							{
								chain__et = InterruptSensor.CHAIN_TRANS_eventHappened_FROM_waitForEvent_TO_waitForEvent_BY_eventHappenedinputEvent_eventHappened;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_timingService__timeout:
							{
								chain__et = InterruptSensor.CHAIN_TRANS_tr0_FROM_waitForEvent_TO_waitForEvent_BY_timeouttimingService_tr0;
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
