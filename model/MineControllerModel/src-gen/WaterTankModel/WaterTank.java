package WaterTankModel;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import InterrupSensorModel.*;
import TcpCommunication.*;
import etrice.api.timer.*;
import WaterTankModel.DrainWater.*;
import InterrupSensorModel.EventHappened.*;
import etrice.api.timer.PTimer.*;
import TcpCommunication.PTrafficLightInterface.*;



public class WaterTank extends ActorClassBase {

	/*--------------------- begin user code ---------------------*/
	private static double WATER_LEVEL_INCREASE = 5;
	private static final int SLEEP_TIME_IN_MS = 100;
	private static final double WATER_TANK_CAPACITY_IN_ML = 400;
	private static final double HIGH_WATER_LIMIT_PERCENTAGE = 70;
	private static final double LOW_WATER_LIMIT_PERCENTAGE = 30;
	private double waterLevel;
	private boolean  isWaterLevelHigh(){
		return waterLevel >= HIGH_WATER_LIMIT_PERCENTAGE * WATER_TANK_CAPACITY_IN_ML / 100.0;
	}
	
	private boolean  isWaterLevelLow(){
		return waterLevel <= LOW_WATER_LIMIT_PERCENTAGE * WATER_TANK_CAPACITY_IN_ML / 100.0;
	}
	private double sentWaterLevel = 0;
	
	private Boolean sentIsLow = null;
	private Boolean sentIsHigh = null;
	
	private void sendHighLevel(boolean isHigh){
		if (sentIsHigh == null || sentIsHigh != isHigh){
			sentIsHigh = isHigh;
			tcpWaterTenk.setHighWaterLevel(isHigh);
		}
	}
	private void sendLowLevel(boolean isLow){
		if (sentIsLow == null || sentIsLow != isLow){
			sentIsLow = isLow;
			tcpWaterTenk.setLowWaterLevel(isLow);
		}
	}
	
	/*--------------------- end user code ---------------------*/

	//--------------------- ports
	protected EventHappenedPort highWaterLevel = null;
	protected EventHappenedPort lowWaterLevel = null;
	protected DrainWaterConjPort drainWater = null;
	protected PTrafficLightInterfaceConjPort tcpWaterTenk = null;

	//--------------------- saps
	protected PTimerConjPort timingService = null;

	//--------------------- services

	//--------------------- optional actors

	//--------------------- interface item IDs
	public static final int IFITEM_highWaterLevel = 1;
	public static final int IFITEM_lowWaterLevel = 2;
	public static final int IFITEM_drainWater = 3;
	public static final int IFITEM_tcpWaterTenk = 4;
	public static final int IFITEM_timingService = 5;

	/*--------------------- attributes ---------------------*/

	/*--------------------- operations ---------------------*/


	//--------------------- construction
	public WaterTank(IRTObject parent, String name) {
		super(parent, name);
		setClassName("WaterTank");

		// initialize attributes

		// own ports
		highWaterLevel = new EventHappenedPort(this, "highWaterLevel", IFITEM_highWaterLevel);
		lowWaterLevel = new EventHappenedPort(this, "lowWaterLevel", IFITEM_lowWaterLevel);
		drainWater = new DrainWaterConjPort(this, "drainWater", IFITEM_drainWater);
		tcpWaterTenk = new PTrafficLightInterfaceConjPort(this, "tcpWaterTenk", IFITEM_tcpWaterTenk);

		// own saps
		timingService = new PTimerConjPort(this, "timingService", IFITEM_timingService, 0);

		// own service implementations

		// sub actors

		// wiring


		/* user defined constructor body */

	}

	/* --------------------- attribute setters and getters */


	//--------------------- port getters
	public EventHappenedPort getHighWaterLevel (){
		return this.highWaterLevel;
	}
	public EventHappenedPort getLowWaterLevel (){
		return this.lowWaterLevel;
	}
	public DrainWaterConjPort getDrainWater (){
		return this.drainWater;
	}
	public PTrafficLightInterfaceConjPort getTcpWaterTenk (){
		return this.tcpWaterTenk;
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
	public static final int STATE_SimulacijaVode = 2;
	public static final int STATE_connect = 3;
	public static final int STATE_MAX = 4;
	
	/* transition chains */
	public static final int CHAIN_TRANS_INITIAL_TO__connect = 1;
	public static final int CHAIN_TRANS_napuniVodu_FROM_SimulacijaVode_TO_SimulacijaVode_BY_timeouttimingService_napuniVodu = 2;
	public static final int CHAIN_TRANS_tr0_FROM_SimulacijaVode_TO_SimulacijaVode_BY_drainWaterdrainWater_tr0 = 3;
	public static final int CHAIN_TRANS_tr1_FROM_connect_TO_SimulacijaVode_BY_connectedtcpWaterTenk = 4;
	public static final int CHAIN_TRANS_tr2_FROM_SimulacijaVode_TO_SimulacijaVode_BY_setWaterTenkFlowtcpWaterTenk_tr2 = 5;
	
	/* triggers */
	public static final int POLLING = 0;
	public static final int TRIG_drainWater__drainWater = IFITEM_drainWater + EVT_SHIFT*DrainWater.OUT_drainWater;
	public static final int TRIG_tcpWaterTenk__connected = IFITEM_tcpWaterTenk + EVT_SHIFT*PTrafficLightInterface.OUT_connected;
	public static final int TRIG_tcpWaterTenk__setPumpFlow = IFITEM_tcpWaterTenk + EVT_SHIFT*PTrafficLightInterface.OUT_setPumpFlow;
	public static final int TRIG_tcpWaterTenk__setWaterTenkFlow = IFITEM_tcpWaterTenk + EVT_SHIFT*PTrafficLightInterface.OUT_setWaterTenkFlow;
	public static final int TRIG_tcpWaterTenk__turnOnPump = IFITEM_tcpWaterTenk + EVT_SHIFT*PTrafficLightInterface.OUT_turnOnPump;
	public static final int TRIG_tcpWaterTenk__turnOffPump = IFITEM_tcpWaterTenk + EVT_SHIFT*PTrafficLightInterface.OUT_turnOffPump;
	public static final int TRIG_timingService__timeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_timeout;
	public static final int TRIG_timingService__internalTimer = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimer;
	public static final int TRIG_timingService__internalTimeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimeout;
	
	// state names
	protected static final String stateStrings[] = {
		"<no state>",
		"<top>",
		"SimulacijaVode",
		"connect"
	};
	
	// history
	protected int history[] = {NO_STATE, NO_STATE, NO_STATE, NO_STATE};
	
	private void setState(int new_state) {
		DebuggingService.getInstance().addActorState(this,stateStrings[new_state]);
		this.state = new_state;
	}
	
	/* Entry and Exit Codes */
	protected void entry_SimulacijaVode() {
		if (isWaterLevelHigh()){
			//System.out.println("HIGH");
			//System.out.println("Water Level: " + waterLevel);
			highWaterLevel.eventHappened();
			sendHighLevel(true);
		} else {
			sendHighLevel(false);
		}
		if (isWaterLevelLow()){
			//System.out.println("LOW");
			//System.out.println("Water Level: " + waterLevel);
			lowWaterLevel.eventHappened();
			sendLowLevel(true);
		} else {
			sendLowLevel(false);
		}
	}
	
	/* Action Codes */
	protected void action_TRANS_INITIAL_TO__connect() {
		tcpWaterTenk.connect(4022);
	}
	protected void action_TRANS_napuniVodu_FROM_SimulacijaVode_TO_SimulacijaVode_BY_timeouttimingService_napuniVodu(InterfaceItemBase ifitem) {
								double increaseValue = WATER_LEVEL_INCREASE;
		//						System.out.println("Puni se za: " + increaseValue);
								waterLevel += increaseValue;
								if (waterLevel != sentWaterLevel){
									tcpWaterTenk.setWaterLevel(waterLevel);
									sentWaterLevel = waterLevel;
								}
								timingService.kill();
								timingService.startTimeout(SLEEP_TIME_IN_MS);
	}
	protected void action_TRANS_tr0_FROM_SimulacijaVode_TO_SimulacijaVode_BY_drainWaterdrainWater_tr0(InterfaceItemBase ifitem, double transitionData) {
		//						System.out.println("Umanjenje za: " + transitionData);
								waterLevel -= transitionData;
								if (waterLevel <= 0){
									waterLevel = 0;
								}
								if (waterLevel != sentWaterLevel){
									tcpWaterTenk.setWaterLevel(waterLevel);
									sentWaterLevel = waterLevel;
								}
	}
	protected void action_TRANS_tr1_FROM_connect_TO_SimulacijaVode_BY_connectedtcpWaterTenk(InterfaceItemBase ifitem) {
		timingService.kill();
		timingService.startTimeout(SLEEP_TIME_IN_MS);
	}
	protected void action_TRANS_tr2_FROM_SimulacijaVode_TO_SimulacijaVode_BY_setWaterTenkFlowtcpWaterTenk_tr2(InterfaceItemBase ifitem, double transitionData) {
		System.out.println("Porstavljen water tank flow na: " + transitionData);
		WATER_LEVEL_INCREASE = transitionData;
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
				case STATE_SimulacijaVode:
					this.history[STATE_TOP] = STATE_SimulacijaVode;
					current__et = STATE_TOP;
					break;
				case STATE_connect:
					this.history[STATE_TOP] = STATE_connect;
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
			case WaterTank.CHAIN_TRANS_INITIAL_TO__connect:
			{
				action_TRANS_INITIAL_TO__connect();
				return STATE_connect;
			}
			case WaterTank.CHAIN_TRANS_napuniVodu_FROM_SimulacijaVode_TO_SimulacijaVode_BY_timeouttimingService_napuniVodu:
			{
				action_TRANS_napuniVodu_FROM_SimulacijaVode_TO_SimulacijaVode_BY_timeouttimingService_napuniVodu(ifitem);
				return STATE_SimulacijaVode;
			}
			case WaterTank.CHAIN_TRANS_tr0_FROM_SimulacijaVode_TO_SimulacijaVode_BY_drainWaterdrainWater_tr0:
			{
				double transitionData = (Double) generic_data__et;
				action_TRANS_tr0_FROM_SimulacijaVode_TO_SimulacijaVode_BY_drainWaterdrainWater_tr0(ifitem, transitionData);
				return STATE_SimulacijaVode;
			}
			case WaterTank.CHAIN_TRANS_tr1_FROM_connect_TO_SimulacijaVode_BY_connectedtcpWaterTenk:
			{
				action_TRANS_tr1_FROM_connect_TO_SimulacijaVode_BY_connectedtcpWaterTenk(ifitem);
				return STATE_SimulacijaVode;
			}
			case WaterTank.CHAIN_TRANS_tr2_FROM_SimulacijaVode_TO_SimulacijaVode_BY_setWaterTenkFlowtcpWaterTenk_tr2:
			{
				double transitionData = (Double) generic_data__et;
				action_TRANS_tr2_FROM_SimulacijaVode_TO_SimulacijaVode_BY_setWaterTenkFlowtcpWaterTenk_tr2(ifitem, transitionData);
				return STATE_SimulacijaVode;
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
				case STATE_SimulacijaVode:
					if (!(skip_entry__et)) entry_SimulacijaVode();
					/* in leaf state: return state id */
					return STATE_SimulacijaVode;
				case STATE_connect:
					/* in leaf state: return state id */
					return STATE_connect;
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
		int chain__et = WaterTank.CHAIN_TRANS_INITIAL_TO__connect;
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
				case STATE_SimulacijaVode:
					switch(trigger__et) {
						case TRIG_drainWater__drainWater:
							{
								chain__et = WaterTank.CHAIN_TRANS_tr0_FROM_SimulacijaVode_TO_SimulacijaVode_BY_drainWaterdrainWater_tr0;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_tcpWaterTenk__setWaterTenkFlow:
							{
								chain__et = WaterTank.CHAIN_TRANS_tr2_FROM_SimulacijaVode_TO_SimulacijaVode_BY_setWaterTenkFlowtcpWaterTenk_tr2;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_timingService__timeout:
							{
								chain__et = WaterTank.CHAIN_TRANS_napuniVodu_FROM_SimulacijaVode_TO_SimulacijaVode_BY_timeouttimingService_napuniVodu;
								catching_state__et = STATE_TOP;
							}
						break;
						default:
							/* should not occur */
							break;
					}
					break;
				case STATE_connect:
					switch(trigger__et) {
						case TRIG_tcpWaterTenk__connected:
							{
								chain__et = WaterTank.CHAIN_TRANS_tr1_FROM_connect_TO_SimulacijaVode_BY_connectedtcpWaterTenk;
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
