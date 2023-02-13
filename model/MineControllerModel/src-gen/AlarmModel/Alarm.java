package AlarmModel;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import AlarmModel.AlarmSender.*;



public class Alarm extends ActorClassBase {

	/*--------------------- begin user code ---------------------*/
	
	private void printMessage(String message){
	System.out.println("#################ALARM#########################");
	System.out.println(message);
	System.out.println("###############################################");
	}
	
	/*--------------------- end user code ---------------------*/

	//--------------------- ports
	protected AlarmSenderReplPort alarmReceiver = null;

	//--------------------- saps

	//--------------------- services

	//--------------------- optional actors

	//--------------------- interface item IDs
	public static final int IFITEM_alarmReceiver = 1;

	/*--------------------- attributes ---------------------*/

	/*--------------------- operations ---------------------*/


	//--------------------- construction
	public Alarm(IRTObject parent, String name) {
		super(parent, name);
		setClassName("Alarm");

		// initialize attributes

		// own ports
		alarmReceiver = new AlarmSenderReplPort(this, "alarmReceiver", IFITEM_alarmReceiver);

		// own saps

		// own service implementations

		// sub actors

		// wiring


		/* user defined constructor body */

	}

	/* --------------------- attribute setters and getters */


	//--------------------- port getters
	public AlarmSenderReplPort getAlarmReceiver (){
		return this.alarmReceiver;
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
	public static final int CHAIN_TRANS_hghMethane_FROM_initialState_TO_initialState_BY_alarmHighMethaneLevelalarmReceiver_hghMethane = 2;
	public static final int CHAIN_TRANS_lowAirFlow_FROM_initialState_TO_initialState_BY_alarmLowAirFlowalarmReceiver_lowAirFlow = 3;
	public static final int CHAIN_TRANS_highCarbonMonoxyde_FROM_initialState_TO_initialState_BY_alarmHighCarbonMonoxydeLevelalarmReceiver_highCarbonMonoxyde = 4;
	public static final int CHAIN_TRANS_errorReadingMethane_FROM_initialState_TO_initialState_BY_alarmErrorReadingMethanealarmReceiver_errorReadingMethane = 5;
	public static final int CHAIN_TRANS_errorReadingAirFlow_FROM_initialState_TO_initialState_BY_alarmErrorReadingAirFlowalarmReceiver_errorReadingAirFlow = 6;
	public static final int CHAIN_TRANS_errorReadingCarbonMonoxyde_FROM_initialState_TO_initialState_BY_alarmErrorReadingCarbonMonoxydealarmReceiver_errorReadingCarbonMonoxyde = 7;
	public static final int CHAIN_TRANS_errorStartingPump_FROM_initialState_TO_initialState_BY_alarmErrorStartingPumpalarmReceiver_errorStartingPump = 8;
	public static final int CHAIN_TRANS_errorStoppingPump_FROM_initialState_TO_initialState_BY_alarmErrorStoppingPumpalarmReceiver_errorStoppingPump = 9;
	public static final int CHAIN_TRANS_errorReadingWaterFlow_FROM_initialState_TO_initialState_BY_alarmErrorReadingWaterFlowalarmReceiver_errorReadingWaterFlow = 10;
	
	/* triggers */
	public static final int POLLING = 0;
	public static final int TRIG_alarmReceiver__alarmHighMethaneLevel = IFITEM_alarmReceiver + EVT_SHIFT*AlarmSender.IN_alarmHighMethaneLevel;
	public static final int TRIG_alarmReceiver__alarmLowAirFlow = IFITEM_alarmReceiver + EVT_SHIFT*AlarmSender.IN_alarmLowAirFlow;
	public static final int TRIG_alarmReceiver__alarmHighCarbonMonoxydeLevel = IFITEM_alarmReceiver + EVT_SHIFT*AlarmSender.IN_alarmHighCarbonMonoxydeLevel;
	public static final int TRIG_alarmReceiver__alarmErrorReadingMethane = IFITEM_alarmReceiver + EVT_SHIFT*AlarmSender.IN_alarmErrorReadingMethane;
	public static final int TRIG_alarmReceiver__alarmErrorReadingAirFlow = IFITEM_alarmReceiver + EVT_SHIFT*AlarmSender.IN_alarmErrorReadingAirFlow;
	public static final int TRIG_alarmReceiver__alarmErrorReadingCarbonMonoxyde = IFITEM_alarmReceiver + EVT_SHIFT*AlarmSender.IN_alarmErrorReadingCarbonMonoxyde;
	public static final int TRIG_alarmReceiver__alarmErrorStartingPump = IFITEM_alarmReceiver + EVT_SHIFT*AlarmSender.IN_alarmErrorStartingPump;
	public static final int TRIG_alarmReceiver__alarmErrorStoppingPump = IFITEM_alarmReceiver + EVT_SHIFT*AlarmSender.IN_alarmErrorStoppingPump;
	public static final int TRIG_alarmReceiver__alarmErrorReadingWaterFlow = IFITEM_alarmReceiver + EVT_SHIFT*AlarmSender.IN_alarmErrorReadingWaterFlow;
	
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
	
	/* Action Codes */
	protected void action_TRANS_hghMethane_FROM_initialState_TO_initialState_BY_alarmHighMethaneLevelalarmReceiver_hghMethane(InterfaceItemBase ifitem) {
		printMessage("Visok nivo CH4");
	}
	protected void action_TRANS_lowAirFlow_FROM_initialState_TO_initialState_BY_alarmLowAirFlowalarmReceiver_lowAirFlow(InterfaceItemBase ifitem) {
		printMessage("Nizak protok vazduhas");
	}
	protected void action_TRANS_highCarbonMonoxyde_FROM_initialState_TO_initialState_BY_alarmHighCarbonMonoxydeLevelalarmReceiver_highCarbonMonoxyde(InterfaceItemBase ifitem) {
		printMessage("Visok nivo CO");
	}
	protected void action_TRANS_errorReadingMethane_FROM_initialState_TO_initialState_BY_alarmErrorReadingMethanealarmReceiver_errorReadingMethane(InterfaceItemBase ifitem) {
		printMessage("Dve greske prilikom citanja nivoa CH4");
	}
	protected void action_TRANS_errorReadingAirFlow_FROM_initialState_TO_initialState_BY_alarmErrorReadingAirFlowalarmReceiver_errorReadingAirFlow(InterfaceItemBase ifitem) {
		printMessage("Dve greske prilikom citanja protoka vazduha");
	}
	protected void action_TRANS_errorReadingCarbonMonoxyde_FROM_initialState_TO_initialState_BY_alarmErrorReadingCarbonMonoxydealarmReceiver_errorReadingCarbonMonoxyde(InterfaceItemBase ifitem) {
		printMessage("Dve greske prilikom citanja nivoa CO");
	}
	protected void action_TRANS_errorStartingPump_FROM_initialState_TO_initialState_BY_alarmErrorStartingPumpalarmReceiver_errorStartingPump(InterfaceItemBase ifitem) {
		printMessage("Greska prilikom startovanje pumpe");
	}
	protected void action_TRANS_errorStoppingPump_FROM_initialState_TO_initialState_BY_alarmErrorStoppingPumpalarmReceiver_errorStoppingPump(InterfaceItemBase ifitem) {
		printMessage("Greska prilikom gasenja pumpe");
	}
	protected void action_TRANS_errorReadingWaterFlow_FROM_initialState_TO_initialState_BY_alarmErrorReadingWaterFlowalarmReceiver_errorReadingWaterFlow(InterfaceItemBase ifitem) {
		printMessage("Dve greske prilikom citanja protoka vode");
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
			case Alarm.CHAIN_TRANS_INITIAL_TO__initialState:
			{
				return STATE_initialState;
			}
			case Alarm.CHAIN_TRANS_errorReadingAirFlow_FROM_initialState_TO_initialState_BY_alarmErrorReadingAirFlowalarmReceiver_errorReadingAirFlow:
			{
				action_TRANS_errorReadingAirFlow_FROM_initialState_TO_initialState_BY_alarmErrorReadingAirFlowalarmReceiver_errorReadingAirFlow(ifitem);
				return STATE_initialState;
			}
			case Alarm.CHAIN_TRANS_errorReadingCarbonMonoxyde_FROM_initialState_TO_initialState_BY_alarmErrorReadingCarbonMonoxydealarmReceiver_errorReadingCarbonMonoxyde:
			{
				action_TRANS_errorReadingCarbonMonoxyde_FROM_initialState_TO_initialState_BY_alarmErrorReadingCarbonMonoxydealarmReceiver_errorReadingCarbonMonoxyde(ifitem);
				return STATE_initialState;
			}
			case Alarm.CHAIN_TRANS_errorReadingMethane_FROM_initialState_TO_initialState_BY_alarmErrorReadingMethanealarmReceiver_errorReadingMethane:
			{
				action_TRANS_errorReadingMethane_FROM_initialState_TO_initialState_BY_alarmErrorReadingMethanealarmReceiver_errorReadingMethane(ifitem);
				return STATE_initialState;
			}
			case Alarm.CHAIN_TRANS_errorReadingWaterFlow_FROM_initialState_TO_initialState_BY_alarmErrorReadingWaterFlowalarmReceiver_errorReadingWaterFlow:
			{
				action_TRANS_errorReadingWaterFlow_FROM_initialState_TO_initialState_BY_alarmErrorReadingWaterFlowalarmReceiver_errorReadingWaterFlow(ifitem);
				return STATE_initialState;
			}
			case Alarm.CHAIN_TRANS_errorStartingPump_FROM_initialState_TO_initialState_BY_alarmErrorStartingPumpalarmReceiver_errorStartingPump:
			{
				action_TRANS_errorStartingPump_FROM_initialState_TO_initialState_BY_alarmErrorStartingPumpalarmReceiver_errorStartingPump(ifitem);
				return STATE_initialState;
			}
			case Alarm.CHAIN_TRANS_errorStoppingPump_FROM_initialState_TO_initialState_BY_alarmErrorStoppingPumpalarmReceiver_errorStoppingPump:
			{
				action_TRANS_errorStoppingPump_FROM_initialState_TO_initialState_BY_alarmErrorStoppingPumpalarmReceiver_errorStoppingPump(ifitem);
				return STATE_initialState;
			}
			case Alarm.CHAIN_TRANS_hghMethane_FROM_initialState_TO_initialState_BY_alarmHighMethaneLevelalarmReceiver_hghMethane:
			{
				action_TRANS_hghMethane_FROM_initialState_TO_initialState_BY_alarmHighMethaneLevelalarmReceiver_hghMethane(ifitem);
				return STATE_initialState;
			}
			case Alarm.CHAIN_TRANS_highCarbonMonoxyde_FROM_initialState_TO_initialState_BY_alarmHighCarbonMonoxydeLevelalarmReceiver_highCarbonMonoxyde:
			{
				action_TRANS_highCarbonMonoxyde_FROM_initialState_TO_initialState_BY_alarmHighCarbonMonoxydeLevelalarmReceiver_highCarbonMonoxyde(ifitem);
				return STATE_initialState;
			}
			case Alarm.CHAIN_TRANS_lowAirFlow_FROM_initialState_TO_initialState_BY_alarmLowAirFlowalarmReceiver_lowAirFlow:
			{
				action_TRANS_lowAirFlow_FROM_initialState_TO_initialState_BY_alarmLowAirFlowalarmReceiver_lowAirFlow(ifitem);
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
		if (state__et >= STATE_MAX) {
			state__et =  (state__et - STATE_MAX);
		}
		while (true) {
			switch (state__et) {
				case STATE_initialState:
					/* in leaf state: return state id */
					return STATE_initialState;
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
		int chain__et = Alarm.CHAIN_TRANS_INITIAL_TO__initialState;
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
						case TRIG_alarmReceiver__alarmErrorReadingAirFlow:
							{
								chain__et = Alarm.CHAIN_TRANS_errorReadingAirFlow_FROM_initialState_TO_initialState_BY_alarmErrorReadingAirFlowalarmReceiver_errorReadingAirFlow;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_alarmReceiver__alarmErrorReadingCarbonMonoxyde:
							{
								chain__et = Alarm.CHAIN_TRANS_errorReadingCarbonMonoxyde_FROM_initialState_TO_initialState_BY_alarmErrorReadingCarbonMonoxydealarmReceiver_errorReadingCarbonMonoxyde;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_alarmReceiver__alarmErrorReadingMethane:
							{
								chain__et = Alarm.CHAIN_TRANS_errorReadingMethane_FROM_initialState_TO_initialState_BY_alarmErrorReadingMethanealarmReceiver_errorReadingMethane;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_alarmReceiver__alarmErrorReadingWaterFlow:
							{
								chain__et = Alarm.CHAIN_TRANS_errorReadingWaterFlow_FROM_initialState_TO_initialState_BY_alarmErrorReadingWaterFlowalarmReceiver_errorReadingWaterFlow;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_alarmReceiver__alarmErrorStartingPump:
							{
								chain__et = Alarm.CHAIN_TRANS_errorStartingPump_FROM_initialState_TO_initialState_BY_alarmErrorStartingPumpalarmReceiver_errorStartingPump;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_alarmReceiver__alarmErrorStoppingPump:
							{
								chain__et = Alarm.CHAIN_TRANS_errorStoppingPump_FROM_initialState_TO_initialState_BY_alarmErrorStoppingPumpalarmReceiver_errorStoppingPump;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_alarmReceiver__alarmHighCarbonMonoxydeLevel:
							{
								chain__et = Alarm.CHAIN_TRANS_highCarbonMonoxyde_FROM_initialState_TO_initialState_BY_alarmHighCarbonMonoxydeLevelalarmReceiver_highCarbonMonoxyde;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_alarmReceiver__alarmHighMethaneLevel:
							{
								chain__et = Alarm.CHAIN_TRANS_hghMethane_FROM_initialState_TO_initialState_BY_alarmHighMethaneLevelalarmReceiver_hghMethane;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_alarmReceiver__alarmLowAirFlow:
							{
								chain__et = Alarm.CHAIN_TRANS_lowAirFlow_FROM_initialState_TO_initialState_BY_alarmLowAirFlowalarmReceiver_lowAirFlow;
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
