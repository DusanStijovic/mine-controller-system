package WaterTankModel;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import PoolingSensorModel.*;
import TcpCommunication.*;
import etrice.api.timer.*;
import WaterTankModel.DrainWater.*;
import etrice.api.timer.PTimer.*;
import TcpCommunication.PTrafficLightInterface.*;
import PoolingSensorModel.PoolingSensorSample.*;



public class waterPumpTunnel extends ActorClassBase {

	/*--------------------- begin user code ---------------------*/
	private static final int PUMP_FLOW_VALID_TIME = 110;
	private double waterFlow = 0;
	private boolean resetWaterFlow = false;
	private double lastSentPumpFlow = -1;
	
	/*--------------------- end user code ---------------------*/

	//--------------------- ports
	protected DrainWaterConjPort waterTenkPump = null;
	protected DrainWaterPort waterTenk = null;
	protected PoolingSensorSampleConjPort wateflowSensor = null;
	protected PTrafficLightInterfaceConjPort tcpPumpTunel = null;

	//--------------------- saps
	protected PTimerConjPort timingService = null;

	//--------------------- services

	//--------------------- optional actors

	//--------------------- interface item IDs
	public static final int IFITEM_waterTenkPump = 1;
	public static final int IFITEM_waterTenk = 2;
	public static final int IFITEM_wateflowSensor = 3;
	public static final int IFITEM_tcpPumpTunel = 4;
	public static final int IFITEM_timingService = 5;

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
		tcpPumpTunel = new PTrafficLightInterfaceConjPort(this, "tcpPumpTunel", IFITEM_tcpPumpTunel);

		// own saps
		timingService = new PTimerConjPort(this, "timingService", IFITEM_timingService, 0);

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
	public PTrafficLightInterfaceConjPort getTcpPumpTunel (){
		return this.tcpPumpTunel;
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
	public static final int STATE_initial = 2;
	public static final int STATE_connect = 3;
	public static final int STATE_MAX = 4;
	
	/* transition chains */
	public static final int CHAIN_TRANS_tr0_FROM_initial_TO_initial_BY_drainWaterwaterTenkPump_tr0 = 1;
	public static final int CHAIN_TRANS_tr2_FROM_initial_TO_initial_BY_timeouttimingService_tr2 = 2;
	public static final int CHAIN_TRANS_init0_FROM_connect_TO_initial_BY_connectedtcpPumpTunel = 3;
	public static final int CHAIN_TRANS_INITIAL_TO__connect = 4;
	
	/* triggers */
	public static final int POLLING = 0;
	public static final int TRIG_waterTenkPump__drainWater = IFITEM_waterTenkPump + EVT_SHIFT*DrainWater.OUT_drainWater;
	public static final int TRIG_tcpPumpTunel__connected = IFITEM_tcpPumpTunel + EVT_SHIFT*PTrafficLightInterface.OUT_connected;
	public static final int TRIG_tcpPumpTunel__setPumpFlow = IFITEM_tcpPumpTunel + EVT_SHIFT*PTrafficLightInterface.OUT_setPumpFlow;
	public static final int TRIG_tcpPumpTunel__setWaterTenkFlow = IFITEM_tcpPumpTunel + EVT_SHIFT*PTrafficLightInterface.OUT_setWaterTenkFlow;
	public static final int TRIG_tcpPumpTunel__turnOnPump = IFITEM_tcpPumpTunel + EVT_SHIFT*PTrafficLightInterface.OUT_turnOnPump;
	public static final int TRIG_tcpPumpTunel__turnOffPump = IFITEM_tcpPumpTunel + EVT_SHIFT*PTrafficLightInterface.OUT_turnOffPump;
	public static final int TRIG_timingService__timeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_timeout;
	public static final int TRIG_timingService__internalTimer = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimer;
	public static final int TRIG_timingService__internalTimeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimeout;
	
	// state names
	protected static final String stateStrings[] = {
		"<no state>",
		"<top>",
		"initial",
		"connect"
	};
	
	// history
	protected int history[] = {NO_STATE, NO_STATE, NO_STATE, NO_STATE};
	
	private void setState(int new_state) {
		DebuggingService.getInstance().addActorState(this,stateStrings[new_state]);
		this.state = new_state;
	}
	
	/* Entry and Exit Codes */
	
	/* Action Codes */
	protected void action_TRANS_tr0_FROM_initial_TO_initial_BY_drainWaterwaterTenkPump_tr0(InterfaceItemBase ifitem, double transitionData) {
		//System.out.println("WaterPumpTunnelFlow:" + transitionData);
		waterTenk.drainWater(transitionData);
		waterFlow = transitionData;
		timingService.kill();
		resetWaterFlow = true;
		wateflowSensor.sendSample(waterFlow);
		timingService.startTimeout(PUMP_FLOW_VALID_TIME);
		if (lastSentPumpFlow != waterFlow){
			lastSentPumpFlow = waterFlow;
			tcpPumpTunel.setPumpFlow(waterFlow);
		}
	}
	protected void action_TRANS_tr2_FROM_initial_TO_initial_BY_timeouttimingService_tr2(InterfaceItemBase ifitem) {
		waterFlow = 0;				
		resetWaterFlow = false;
		wateflowSensor.sendSample(waterFlow);
		tcpPumpTunel.setPumpFlow(0);
		lastSentPumpFlow = 0;
	}
	protected void action_TRANS_INITIAL_TO__connect() {
		tcpPumpTunel.connect(4020);
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
				case STATE_connect:
					this.history[STATE_TOP] = STATE_connect;
					current__et = STATE_TOP;
					break;
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
			case waterPumpTunnel.CHAIN_TRANS_INITIAL_TO__connect:
			{
				action_TRANS_INITIAL_TO__connect();
				return STATE_connect;
			}
			case waterPumpTunnel.CHAIN_TRANS_init0_FROM_connect_TO_initial_BY_connectedtcpPumpTunel:
			{
				return STATE_initial;
			}
			case waterPumpTunnel.CHAIN_TRANS_tr0_FROM_initial_TO_initial_BY_drainWaterwaterTenkPump_tr0:
			{
				double transitionData = (Double) generic_data__et;
				action_TRANS_tr0_FROM_initial_TO_initial_BY_drainWaterwaterTenkPump_tr0(ifitem, transitionData);
				return STATE_initial;
			}
			case waterPumpTunnel.CHAIN_TRANS_tr2_FROM_initial_TO_initial_BY_timeouttimingService_tr2:
			{
				action_TRANS_tr2_FROM_initial_TO_initial_BY_timeouttimingService_tr2(ifitem);
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
				case STATE_connect:
					/* in leaf state: return state id */
					return STATE_connect;
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
		int chain__et = waterPumpTunnel.CHAIN_TRANS_INITIAL_TO__connect;
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
				case STATE_connect:
					switch(trigger__et) {
						case TRIG_tcpPumpTunel__connected:
							{
								chain__et = waterPumpTunnel.CHAIN_TRANS_init0_FROM_connect_TO_initial_BY_connectedtcpPumpTunel;
								catching_state__et = STATE_TOP;
							}
						break;
						default:
							/* should not occur */
							break;
					}
					break;
				case STATE_initial:
					switch(trigger__et) {
						case TRIG_timingService__timeout:
							{ 
							if (resetWaterFlow)
							{
								chain__et = waterPumpTunnel.CHAIN_TRANS_tr2_FROM_initial_TO_initial_BY_timeouttimingService_tr2;
								catching_state__et = STATE_TOP;
							}
							}
						break;
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
