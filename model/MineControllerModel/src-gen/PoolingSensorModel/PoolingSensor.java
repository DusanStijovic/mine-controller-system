package PoolingSensorModel;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import etrice.api.timer.*;
import etrice.api.timer.PTimer.*;
import PoolingSensorModel.PoolingSensorCommands.*;
import PoolingSensorModel.PoolingSensorSample.*;



public class PoolingSensor extends ActorClassBase {

	/*--------------------- begin user code ---------------------*/
	private static final int SLEEP_IF_CONVERSION_NOT_DONE_MS = 10;
	private static final int MAX_TIME_FOR_CONVERSION_MS = 50;
	private static final int MIN_TIME_FOR_CONVERSION_MS = 20;
	private int neededTimeMsForConversion;
	private boolean conversionInProgress;
	private PoolingSensorCommands.Status status;
	private double value = 0;
	private String error = "Some error";
	
	private int getTimeForConversion(){
		return (int)(MIN_TIME_FOR_CONVERSION_MS +
				 Math.random() * (MAX_TIME_FOR_CONVERSION_MS - MIN_TIME_FOR_CONVERSION_MS));
	}
	
	private boolean shouldBeError(){
		return Math.random()>= 0.85;
	}
	
	/*--------------------- end user code ---------------------*/

	//--------------------- ports
	protected PoolingSensorCommandsConjPort poolingSensorCommands = null;
	protected PoolingSensorSamplePort poolingSensorSample = null;

	//--------------------- saps
	protected PTimerConjPort timingService = null;

	//--------------------- services

	//--------------------- optional actors

	//--------------------- interface item IDs
	public static final int IFITEM_poolingSensorCommands = 1;
	public static final int IFITEM_poolingSensorSample = 2;
	public static final int IFITEM_timingService = 3;

	/*--------------------- attributes ---------------------*/

	/*--------------------- operations ---------------------*/


	//--------------------- construction
	public PoolingSensor(IRTObject parent, String name) {
		super(parent, name);
		setClassName("PoolingSensor");

		// initialize attributes

		// own ports
		poolingSensorCommands = new PoolingSensorCommandsConjPort(this, "poolingSensorCommands", IFITEM_poolingSensorCommands);
		poolingSensorSample = new PoolingSensorSamplePort(this, "poolingSensorSample", IFITEM_poolingSensorSample);

		// own saps
		timingService = new PTimerConjPort(this, "timingService", IFITEM_timingService, 0);

		// own service implementations

		// sub actors

		// wiring


		/* user defined constructor body */

	}

	/* --------------------- attribute setters and getters */


	//--------------------- port getters
	public PoolingSensorCommandsConjPort getPoolingSensorCommands (){
		return this.poolingSensorCommands;
	}
	public PoolingSensorSamplePort getPoolingSensorSample (){
		return this.poolingSensorSample;
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
	public static final int STATE_conversion = 2;
	public static final int STATE_MAX = 3;
	
	/* transition chains */
	public static final int CHAIN_TRANS_INITIAL_TO__conversion = 1;
	public static final int CHAIN_TRANS_tr0_FROM_conversion_TO_conversion_BY_startADConverstionpoolingSensorCommands_tr0 = 2;
	public static final int CHAIN_TRANS_tr1_FROM_conversion_TO_conversion_BY_timeouttimingService_tr1 = 3;
	public static final int CHAIN_TRANS_tr2_FROM_conversion_TO_conversion_BY_readStatuspoolingSensorCommands_tr2 = 4;
	public static final int CHAIN_TRANS_tr3_FROM_conversion_TO_conversion_BY_readValueRegisterpoolingSensorCommands_tr3 = 5;
	public static final int CHAIN_TRANS_tr4_FROM_conversion_TO_conversion_BY_readErrorRegisterpoolingSensorCommands_tr4 = 6;
	public static final int CHAIN_TRANS_tr5_FROM_conversion_TO_conversion_BY_sendSamplepoolingSensorSample_tr5 = 7;
	
	/* triggers */
	public static final int POLLING = 0;
	public static final int TRIG_poolingSensorCommands__startADConverstion = IFITEM_poolingSensorCommands + EVT_SHIFT*PoolingSensorCommands.OUT_startADConverstion;
	public static final int TRIG_poolingSensorCommands__readStatus = IFITEM_poolingSensorCommands + EVT_SHIFT*PoolingSensorCommands.OUT_readStatus;
	public static final int TRIG_poolingSensorCommands__readValueRegister = IFITEM_poolingSensorCommands + EVT_SHIFT*PoolingSensorCommands.OUT_readValueRegister;
	public static final int TRIG_poolingSensorCommands__readErrorRegister = IFITEM_poolingSensorCommands + EVT_SHIFT*PoolingSensorCommands.OUT_readErrorRegister;
	public static final int TRIG_poolingSensorSample__sendSample = IFITEM_poolingSensorSample + EVT_SHIFT*PoolingSensorSample.IN_sendSample;
	public static final int TRIG_timingService__timeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_timeout;
	public static final int TRIG_timingService__internalTimer = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimer;
	public static final int TRIG_timingService__internalTimeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimeout;
	
	// state names
	protected static final String stateStrings[] = {
		"<no state>",
		"<top>",
		"conversion"
	};
	
	// history
	protected int history[] = {NO_STATE, NO_STATE, NO_STATE};
	
	private void setState(int new_state) {
		DebuggingService.getInstance().addActorState(this,stateStrings[new_state]);
		this.state = new_state;
	}
	
	/* Entry and Exit Codes */
	
	/* Action Codes */
	protected void action_TRANS_tr0_FROM_conversion_TO_conversion_BY_startADConverstionpoolingSensorCommands_tr0(InterfaceItemBase ifitem) {
		if (conversionInProgress == false){
			conversionInProgress = true;
			neededTimeMsForConversion =  getTimeForConversion();
			status = PoolingSensorCommands.Status.NOT_READY;
			timingService.kill();
			timingService.startTimeout(neededTimeMsForConversion);
		} else {
			System.out.println("Konverzija vec u toku");
		}
	}
	protected void action_TRANS_tr1_FROM_conversion_TO_conversion_BY_timeouttimingService_tr1(InterfaceItemBase ifitem) {
		conversionInProgress = false;
		if (shouldBeError()){
			error = "Greska pri konverziji";
			status = PoolingSensorCommands.Status.ERROR;
		} else {
			status = PoolingSensorCommands.Status.READY;
		}
	}
	protected void action_TRANS_tr2_FROM_conversion_TO_conversion_BY_readStatuspoolingSensorCommands_tr2(InterfaceItemBase ifitem) {
		poolingSensorCommands.sendStatus((byte)status.ordinal());
	}
	protected void action_TRANS_tr3_FROM_conversion_TO_conversion_BY_readValueRegisterpoolingSensorCommands_tr3(InterfaceItemBase ifitem) {
		poolingSensorCommands.sendValueRegister(value);
	}
	protected void action_TRANS_tr4_FROM_conversion_TO_conversion_BY_readErrorRegisterpoolingSensorCommands_tr4(InterfaceItemBase ifitem) {
		poolingSensorCommands.sendErrorRegister(error);
	}
	protected void action_TRANS_tr5_FROM_conversion_TO_conversion_BY_sendSamplepoolingSensorSample_tr5(InterfaceItemBase ifitem, double transitionData) {
		value = transitionData;
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
				case STATE_conversion:
					this.history[STATE_TOP] = STATE_conversion;
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
			case PoolingSensor.CHAIN_TRANS_INITIAL_TO__conversion:
			{
				return STATE_conversion;
			}
			case PoolingSensor.CHAIN_TRANS_tr0_FROM_conversion_TO_conversion_BY_startADConverstionpoolingSensorCommands_tr0:
			{
				action_TRANS_tr0_FROM_conversion_TO_conversion_BY_startADConverstionpoolingSensorCommands_tr0(ifitem);
				return STATE_conversion;
			}
			case PoolingSensor.CHAIN_TRANS_tr1_FROM_conversion_TO_conversion_BY_timeouttimingService_tr1:
			{
				action_TRANS_tr1_FROM_conversion_TO_conversion_BY_timeouttimingService_tr1(ifitem);
				return STATE_conversion;
			}
			case PoolingSensor.CHAIN_TRANS_tr2_FROM_conversion_TO_conversion_BY_readStatuspoolingSensorCommands_tr2:
			{
				action_TRANS_tr2_FROM_conversion_TO_conversion_BY_readStatuspoolingSensorCommands_tr2(ifitem);
				return STATE_conversion;
			}
			case PoolingSensor.CHAIN_TRANS_tr3_FROM_conversion_TO_conversion_BY_readValueRegisterpoolingSensorCommands_tr3:
			{
				action_TRANS_tr3_FROM_conversion_TO_conversion_BY_readValueRegisterpoolingSensorCommands_tr3(ifitem);
				return STATE_conversion;
			}
			case PoolingSensor.CHAIN_TRANS_tr4_FROM_conversion_TO_conversion_BY_readErrorRegisterpoolingSensorCommands_tr4:
			{
				action_TRANS_tr4_FROM_conversion_TO_conversion_BY_readErrorRegisterpoolingSensorCommands_tr4(ifitem);
				return STATE_conversion;
			}
			case PoolingSensor.CHAIN_TRANS_tr5_FROM_conversion_TO_conversion_BY_sendSamplepoolingSensorSample_tr5:
			{
				double transitionData = (Double) generic_data__et;
				action_TRANS_tr5_FROM_conversion_TO_conversion_BY_sendSamplepoolingSensorSample_tr5(ifitem, transitionData);
				return STATE_conversion;
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
				case STATE_conversion:
					/* in leaf state: return state id */
					return STATE_conversion;
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
		int chain__et = PoolingSensor.CHAIN_TRANS_INITIAL_TO__conversion;
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
				case STATE_conversion:
					switch(trigger__et) {
						case TRIG_poolingSensorCommands__readErrorRegister:
							{
								chain__et = PoolingSensor.CHAIN_TRANS_tr4_FROM_conversion_TO_conversion_BY_readErrorRegisterpoolingSensorCommands_tr4;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_poolingSensorCommands__readStatus:
							{
								chain__et = PoolingSensor.CHAIN_TRANS_tr2_FROM_conversion_TO_conversion_BY_readStatuspoolingSensorCommands_tr2;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_poolingSensorCommands__readValueRegister:
							{
								chain__et = PoolingSensor.CHAIN_TRANS_tr3_FROM_conversion_TO_conversion_BY_readValueRegisterpoolingSensorCommands_tr3;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_poolingSensorCommands__startADConverstion:
							{
								chain__et = PoolingSensor.CHAIN_TRANS_tr0_FROM_conversion_TO_conversion_BY_startADConverstionpoolingSensorCommands_tr0;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_poolingSensorSample__sendSample:
							{
								chain__et = PoolingSensor.CHAIN_TRANS_tr5_FROM_conversion_TO_conversion_BY_sendSamplepoolingSensorSample_tr5;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_timingService__timeout:
							{
								chain__et = PoolingSensor.CHAIN_TRANS_tr1_FROM_conversion_TO_conversion_BY_timeouttimingService_tr1;
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
