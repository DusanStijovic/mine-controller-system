package WaterTankModel;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import PoolingSensorModel.*;
import WaterTankModel.DrainWater.*;
import PoolingSensorModel.PoolingSensorSample.*;



public class waterPumpTunnel extends ActorClassBase {


	//--------------------- ports
	protected DrainWaterConjPort waterTenkPump = null;
	protected DrainWaterPort waterTenk = null;
	protected PoolingSensorSampleConjPort wateflowSensor = null;

	//--------------------- saps

	//--------------------- services

	//--------------------- optional actors

	//--------------------- interface item IDs
	public static final int IFITEM_waterTenkPump = 1;
	public static final int IFITEM_waterTenk = 2;
	public static final int IFITEM_wateflowSensor = 3;

	/*--------------------- attributes ---------------------*/

	/*--------------------- operations ---------------------*/


	//--------------------- construction
	public waterPumpTunnel(IRTObject parent, String name) {
		super(parent, name);
		setClassName("waterPumpTunnel");

		// initialize attributes

		// own ports
		waterTenkPump = new DrainWaterConjPort(this, "waterTenkPump", IFITEM_waterTenkPump);
		waterTenk = new DrainWaterPort(this, "waterTenk", IFITEM_waterTenk);
		wateflowSensor = new PoolingSensorSampleConjPort(this, "wateflowSensor", IFITEM_wateflowSensor);

		// own saps

		// own service implementations

		// sub actors

		// wiring


		/* user defined constructor body */

	}

	/* --------------------- attribute setters and getters */


	//--------------------- port getters
	public DrainWaterConjPort getWaterTenkPump (){
		return this.waterTenkPump;
	}
	public DrainWaterPort getWaterTenk (){
		return this.waterTenk;
	}
	public PoolingSensorSampleConjPort getWateflowSensor (){
		return this.wateflowSensor;
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
	public static final int STATE_initial = 2;
	public static final int STATE_MAX = 3;
	
	/* transition chains */
	public static final int CHAIN_TRANS_INITIAL_TO__initial = 1;
	public static final int CHAIN_TRANS_tr0_FROM_initial_TO_initial_BY_drainWaterwaterTenkPump_tr0 = 2;
	
	/* triggers */
	public static final int POLLING = 0;
	public static final int TRIG_waterTenkPump__drainWater = IFITEM_waterTenkPump + EVT_SHIFT*DrainWater.OUT_drainWater;
	
	// state names
	protected static final String stateStrings[] = {
		"<no state>",
		"<top>",
		"initial"
	};
	
	// history
	protected int history[] = {NO_STATE, NO_STATE, NO_STATE};
	
	private void setState(int new_state) {
		DebuggingService.getInstance().addActorState(this,stateStrings[new_state]);
		this.state = new_state;
	}
	
	/* Entry and Exit Codes */
	
	/* Action Codes */
	protected void action_TRANS_tr0_FROM_initial_TO_initial_BY_drainWaterwaterTenkPump_tr0(InterfaceItemBase ifitem, double transitionData) {
		waterTenk.drainWater(transitionData);
		wateflowSensor.sendSample(transitionData);
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
				case STATE_initial:
					this.history[STATE_TOP] = STATE_initial;
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
			case waterPumpTunnel.CHAIN_TRANS_INITIAL_TO__initial:
			{
				return STATE_initial;
			}
			case waterPumpTunnel.CHAIN_TRANS_tr0_FROM_initial_TO_initial_BY_drainWaterwaterTenkPump_tr0:
			{
				double transitionData = (Double) generic_data__et;
				action_TRANS_tr0_FROM_initial_TO_initial_BY_drainWaterwaterTenkPump_tr0(ifitem, transitionData);
				return STATE_initial;
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
				case STATE_initial:
					/* in leaf state: return state id */
					return STATE_initial;
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
		int chain__et = waterPumpTunnel.CHAIN_TRANS_INITIAL_TO__initial;
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
				case STATE_initial:
					switch(trigger__et) {
						case TRIG_waterTenkPump__drainWater:
							{
								chain__et = waterPumpTunnel.CHAIN_TRANS_tr0_FROM_initial_TO_initial_BY_drainWaterwaterTenkPump_tr0;
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
