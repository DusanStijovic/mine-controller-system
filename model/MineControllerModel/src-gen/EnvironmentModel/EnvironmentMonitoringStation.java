package EnvironmentModel;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import AlarmModel.*;
import ConsoleModel.*;
import PoolingSensorModel.*;
import etrice.api.timer.*;
import AlarmModel.AlarmSender.*;
import ConsoleModel.MessagingProtocol.*;
import etrice.api.timer.PTimer.*;
import PoolingSensorModel.PoolingSensorCommands.*;
import PoolingSensorModel.PoolingSensorSample.*;
import EnvironmentModel.supstanceLevelEvent.*;



public class EnvironmentMonitoringStation extends ActorClassBase {


	//--------------------- ports
	protected MessagingProtocolConjPort sendToConsole = null;

	//--------------------- saps

	//--------------------- services

	//--------------------- optional actors

	//--------------------- interface item IDs
	public static final int IFITEM_sendToConsole = 1;

	/*--------------------- attributes ---------------------*/

	/*--------------------- operations ---------------------*/


	//--------------------- construction
	public EnvironmentMonitoringStation(IRTObject parent, String name) {
		super(parent, name);
		setClassName("EnvironmentMonitoringStation");

		// initialize attributes

		// own ports
		sendToConsole = new MessagingProtocolConjPort(this, "sendToConsole", IFITEM_sendToConsole);

		// own saps

		// own service implementations

		// sub actors
		DebuggingService.getInstance().addMessageActorCreate(this, "carboniteMonoxideSensor");
		new CarboniteMonoxideSensor(this, "carboniteMonoxideSensor");
		DebuggingService.getInstance().addMessageActorCreate(this, "airFlowSensor");
		new AirFlowSensor(this, "airFlowSensor");
		DebuggingService.getInstance().addMessageActorCreate(this, "environmentPooling");
		new EnvironmentPooling(this, "environmentPooling");
		DebuggingService.getInstance().addMessageActorCreate(this, "methaneSensor");
		new MethaneSensor(this, "methaneSensor");
		DebuggingService.getInstance().addMessageActorCreate(this, "airSimulator");
		new AirSimulator(this, "airSimulator");

		// wiring
		InterfaceItemBase.connect(this, "environmentPooling/poolingAirFlow", "airFlowSensor/poolingSensorCommands");
		InterfaceItemBase.connect(this, "environmentPooling/poolingCarbonMonohyde", "carboniteMonoxideSensor/poolingSensorCommands");
		InterfaceItemBase.connect(this, "methaneSensor/poolingSensorCommands", "environmentPooling/poolingMethane");
		InterfaceItemBase.connect(this, "airSimulator/airflowSample", "airFlowSensor/poolingSensorSample");
		InterfaceItemBase.connect(this, "airSimulator/methaneSample", "methaneSensor/poolingSensorSample");
		InterfaceItemBase.connect(this, "airSimulator/caarbonMonoxydeSample", "carboniteMonoxideSensor/poolingSensorSample");


		/* user defined constructor body */

	}

	/* --------------------- attribute setters and getters */


	//--------------------- port getters
	public MessagingProtocolConjPort getSendToConsole (){
		return this.sendToConsole;
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
	public static final int STATE_InitialState = 2;
	public static final int STATE_MAX = 3;
	
	/* transition chains */
	public static final int CHAIN_TRANS_INITIAL_TO__InitialState = 1;
	
	/* triggers */
	public static final int POLLING = 0;
	
	// state names
	protected static final String stateStrings[] = {
		"<no state>",
		"<top>",
		"InitialState"
	};
	
	// history
	protected int history[] = {NO_STATE, NO_STATE, NO_STATE};
	
	private void setState(int new_state) {
		DebuggingService.getInstance().addActorState(this,stateStrings[new_state]);
		this.state = new_state;
	}
	
	/* Entry and Exit Codes */
	
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
				case STATE_InitialState:
					this.history[STATE_TOP] = STATE_InitialState;
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
			case EnvironmentMonitoringStation.CHAIN_TRANS_INITIAL_TO__InitialState:
			{
				return STATE_InitialState;
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
				case STATE_InitialState:
					/* in leaf state: return state id */
					return STATE_InitialState;
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
		int chain__et = EnvironmentMonitoringStation.CHAIN_TRANS_INITIAL_TO__InitialState;
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
				case STATE_InitialState:
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
