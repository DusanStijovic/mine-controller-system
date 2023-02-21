package EnvironmentModel;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import PoolingSensorModel.*;
import etrice.api.timer.*;
import etrice.api.timer.PTimer.*;
import PoolingSensorModel.PoolingSensorSample.*;

/*--------------------- begin user code ---------------------*/
import  java.util.Random;

/*--------------------- end user code ---------------------*/


public class AirSimulator extends ActorClassBase {

	/*--------------------- begin user code ---------------------*/
	private static final int CALCULATION_PERIOD_IN_MS =  50;
	private static final double MAX_SAMPLE_LEVEL = 10;
	private double airFlowLevel = 0;
	private double methaneLevel = 0;
	private double carbonMonoxydeLevel = 0;
	
	private Random airFlowRandom = new Random(System.currentTimeMillis());
	private Random methaneRandom = new Random(System.currentTimeMillis());
	private Random carbonMonoxyedRandom = new Random(System.currentTimeMillis());
	
	
	private void calculateAirFlow(){
		airFlowLevel = airFlowRandom.nextDouble() * MAX_SAMPLE_LEVEL;
	}
	private void calculateMethane(){
		methaneLevel = methaneRandom.nextDouble() * MAX_SAMPLE_LEVEL;
	}
	
	private void calculateCarbonMonoxyde(){
		carbonMonoxydeLevel = carbonMonoxyedRandom.nextDouble() * MAX_SAMPLE_LEVEL;
	}
	
	
	/*--------------------- end user code ---------------------*/

	//--------------------- ports
	protected PoolingSensorSampleConjPort methaneSample = null;
	protected PoolingSensorSampleConjPort caarbonMonoxydeSample = null;
	protected PoolingSensorSampleConjPort airflowSample = null;

	//--------------------- saps
	protected PTimerConjPort timingService = null;

	//--------------------- services

	//--------------------- optional actors

	//--------------------- interface item IDs
	public static final int IFITEM_methaneSample = 1;
	public static final int IFITEM_caarbonMonoxydeSample = 2;
	public static final int IFITEM_airflowSample = 3;
	public static final int IFITEM_timingService = 4;

	/*--------------------- attributes ---------------------*/

	/*--------------------- operations ---------------------*/


	//--------------------- construction
	public AirSimulator(IRTObject parent, String name) {
		super(parent, name);
		setClassName("AirSimulator");

		// initialize attributes

		// own ports
		methaneSample = new PoolingSensorSampleConjPort(this, "methaneSample", IFITEM_methaneSample);
		caarbonMonoxydeSample = new PoolingSensorSampleConjPort(this, "caarbonMonoxydeSample", IFITEM_caarbonMonoxydeSample);
		airflowSample = new PoolingSensorSampleConjPort(this, "airflowSample", IFITEM_airflowSample);

		// own saps
		timingService = new PTimerConjPort(this, "timingService", IFITEM_timingService, 0);

		// own service implementations

		// sub actors

		// wiring


		/* user defined constructor body */

	}

	/* --------------------- attribute setters and getters */


	//--------------------- port getters
	public PoolingSensorSampleConjPort getMethaneSample (){
		return this.methaneSample;
	}
	public PoolingSensorSampleConjPort getCaarbonMonoxydeSample (){
		return this.caarbonMonoxydeSample;
	}
	public PoolingSensorSampleConjPort getAirflowSample (){
		return this.airflowSample;
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
	public static final int STATE_initialState = 2;
	public static final int STATE_MAX = 3;
	
	/* transition chains */
	public static final int CHAIN_TRANS_INITIAL_TO__initialState = 1;
	public static final int CHAIN_TRANS_tr0_FROM_initialState_TO_initialState_BY_timeouttimingService_tr0 = 2;
	
	/* triggers */
	public static final int POLLING = 0;
	public static final int TRIG_timingService__timeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_timeout;
	public static final int TRIG_timingService__internalTimer = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimer;
	public static final int TRIG_timingService__internalTimeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimeout;
	
	// state names
	protected static final String stateStrings[] = {
		"<no state>",
		"<top>",
		"initialState"
	};
	
	// history
	protected int history[] = {NO_STATE, NO_STATE, NO_STATE};
	
	private void setState(int new_state) {
		DebuggingService.getInstance().addActorState(this,stateStrings[new_state]);
		this.state = new_state;
	}
	
	/* Entry and Exit Codes */
	protected void entry_initialState() {
								calculateAirFlow();
								calculateMethane();
								calculateCarbonMonoxyde();
								methaneSample.sendSample(airFlowLevel);
								airflowSample.sendSample(methaneLevel);
								caarbonMonoxydeSample.sendSample(carbonMonoxydeLevel);
		//						System.out.println("Racunanje i slanje novih uzoraka: air: " + airFlowLevel + ", methane:" + methaneLevel + ", carbonMonoxydeLeve: " + carbonMonoxydeLevel);
								timingService.startTimeout(CALCULATION_PERIOD_IN_MS);			
	}
	
	/* Action Codes */
	
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
				case STATE_initialState:
					this.history[STATE_TOP] = STATE_initialState;
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
			case AirSimulator.CHAIN_TRANS_INITIAL_TO__initialState:
			{
				return STATE_initialState;
			}
			case AirSimulator.CHAIN_TRANS_tr0_FROM_initialState_TO_initialState_BY_timeouttimingService_tr0:
			{
				return STATE_initialState;
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
				case STATE_initialState:
					if (!(skip_entry__et)) entry_initialState();
					/* in leaf state: return state id */
					return STATE_initialState;
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
		int chain__et = AirSimulator.CHAIN_TRANS_INITIAL_TO__initialState;
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
				case STATE_initialState:
					switch(trigger__et) {
						case TRIG_timingService__timeout:
							{
								chain__et = AirSimulator.CHAIN_TRANS_tr0_FROM_initialState_TO_initialState_BY_timeouttimingService_tr0;
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
