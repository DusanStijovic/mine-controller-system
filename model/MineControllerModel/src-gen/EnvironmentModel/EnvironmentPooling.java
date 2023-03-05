package EnvironmentModel;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import AlarmModel.*;
import PoolingSensorModel.*;
import TcpCommunication.*;
import etrice.api.timer.*;
import AlarmModel.AlarmSender.*;
import TcpCommunication.PGuiCommunicationInterface.*;
import etrice.api.timer.PTimer.*;
import PoolingSensorModel.PoolingSensorCommands.*;
import EnvironmentModel.supstanceLevelEvent.*;

/*--------------------- begin user code ---------------------*/
import java.util.Date;
import java.util.Calendar;

/*--------------------- end user code ---------------------*/


public class EnvironmentPooling extends ActorClassBase {

	/*--------------------- begin user code ---------------------*/
					private static final int PERIOD_OF_ACTIVATION = 100;
					private static final int PERIOD_OF_CHECKING_DEVICE = 50;
					private static final int MAX_ERROR_COUNT = 2;
					
					private int currentCarbonMonoxydeErrorCount = 0;
					private int currentMethaneErrorCount = 0;
					private int currentAirFlowErrorCount = 0;
					
					private long methaneNextActivationInMs = 0;
					private long airFlowNextActivationInMs = 0;
					private long carbonNextActivationInMs = 0;
					private long calculatedParts = 0;
					
					private static final double METHANE_HIGH_LEVEL = 9.4;
					private static final double AIRFLOW_LOW_LEVEL = 0.5;
					private static final double CARBON_MONOXYDE_HIGH_LEVEL = 9.4;
					
					private long worstExecutionTimeInMs = 0;
					private long currentMethaneExecutionTimeInMs = 0;
					private long currentAirFlowExecutionTimeInMs = 0;
					private long currentCarbonExecutionTimeInMs = 0;			
					private long readStatusTime = 0;	
					
					private int calculateWaitTime(long nextActivationInMs){
						long waitTime = nextActivationInMs - System.currentTimeMillis();
						waitTime = Math.max(waitTime, 0);
						return (int)waitTime;
					}
					
					private void calculateExecutionTime(){
							calculatedParts++;
							if (calculatedParts == 3){
								long duration = currentMethaneExecutionTimeInMs + currentAirFlowExecutionTimeInMs + currentCarbonExecutionTimeInMs;
								duration += readStatusTime;
	//							System.out.println("Duration environment monitoring: " + duration);
								if (duration > worstExecutionTimeInMs){
									worstExecutionTimeInMs = duration;
									System.out.println("WCET environment monitoring: " + worstExecutionTimeInMs);
								}
								currentMethaneExecutionTimeInMs = currentAirFlowExecutionTimeInMs = currentCarbonExecutionTimeInMs = 0;
								calculatedParts = 0;
								readStatusTime = 0;
							}
					}
					private Boolean lastSendMethane = null;
					private Boolean lastSendCarbon = null;
					private Boolean lastSendAirFlow = null;
					private void sendMethane(boolean isHigh){
						if (lastSendMethane == null || isHigh != lastSendMethane){
							lastSendMethane = isHigh;
							tcpEnvironment.setMethaneLevel(isHigh);
						}
					}
					private void sendCarbon(boolean isHigh){
						if (lastSendCarbon == null || isHigh != lastSendCarbon){
							lastSendCarbon = isHigh;
							tcpEnvironment.setCarbonLevel(isHigh);
						}
					}
					private void sendAirFlow(boolean isHigh){
						if (lastSendAirFlow == null || isHigh != lastSendAirFlow){
							lastSendAirFlow = isHigh;
							tcpEnvironment.setAirFlowLevel(isHigh);
						}
					}
	
	/*--------------------- end user code ---------------------*/

	//--------------------- ports
	protected PoolingSensorCommandsPort poolingAirFlow = null;
	protected PoolingSensorCommandsPort poolingMethane = null;
	protected PoolingSensorCommandsPort poolingCarbonMonohyde = null;
	protected supstanceLevelEventPort methaneLevel = null;
	protected AlarmSenderConjPort sendAlarm = null;
	protected PGuiCommunicationInterfaceConjPort tcpEnvironment = null;

	//--------------------- saps
	protected PTimerConjPort timingService = null;

	//--------------------- services

	//--------------------- optional actors

	//--------------------- interface item IDs
	public static final int IFITEM_poolingAirFlow = 1;
	public static final int IFITEM_poolingMethane = 2;
	public static final int IFITEM_poolingCarbonMonohyde = 3;
	public static final int IFITEM_methaneLevel = 4;
	public static final int IFITEM_sendAlarm = 5;
	public static final int IFITEM_tcpEnvironment = 6;
	public static final int IFITEM_timingService = 7;

	/*--------------------- attributes ---------------------*/

	/*--------------------- operations ---------------------*/


	//--------------------- construction
	public EnvironmentPooling(IRTObject parent, String name) {
		super(parent, name);
		setClassName("EnvironmentPooling");

		// initialize attributes

		// own ports
		poolingAirFlow = new PoolingSensorCommandsPort(this, "poolingAirFlow", IFITEM_poolingAirFlow);
		poolingMethane = new PoolingSensorCommandsPort(this, "poolingMethane", IFITEM_poolingMethane);
		poolingCarbonMonohyde = new PoolingSensorCommandsPort(this, "poolingCarbonMonohyde", IFITEM_poolingCarbonMonohyde);
		methaneLevel = new supstanceLevelEventPort(this, "methaneLevel", IFITEM_methaneLevel);
		sendAlarm = new AlarmSenderConjPort(this, "sendAlarm", IFITEM_sendAlarm);
		tcpEnvironment = new PGuiCommunicationInterfaceConjPort(this, "tcpEnvironment", IFITEM_tcpEnvironment);

		// own saps
		timingService = new PTimerConjPort(this, "timingService", IFITEM_timingService, 0);

		// own service implementations

		// sub actors

		// wiring


		/* user defined constructor body */

	}

	/* --------------------- attribute setters and getters */


	//--------------------- port getters
	public PoolingSensorCommandsPort getPoolingAirFlow (){
		return this.poolingAirFlow;
	}
	public PoolingSensorCommandsPort getPoolingMethane (){
		return this.poolingMethane;
	}
	public PoolingSensorCommandsPort getPoolingCarbonMonohyde (){
		return this.poolingCarbonMonohyde;
	}
	public supstanceLevelEventPort getMethaneLevel (){
		return this.methaneLevel;
	}
	public AlarmSenderConjPort getSendAlarm (){
		return this.sendAlarm;
	}
	public PGuiCommunicationInterfaceConjPort getTcpEnvironment (){
		return this.tcpEnvironment;
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
	public static final int STATE_processChecking = 2;
	public static final int STATE_connect = 3;
	public static final int STATE_MAX = 4;
	
	/* transition chains */
	public static final int CHAIN_TRANS_tr0_FROM_processChecking_TO_processChecking_BY_timeouttimingService_tr0 = 1;
	public static final int CHAIN_TRANS_tr2_FROM_processChecking_TO_processChecking_BY_sendStatuspoolingMethane_tr2 = 2;
	public static final int CHAIN_TRANS_tr3_FROM_processChecking_TO_processChecking_BY_sendStatuspoolingCarbonMonohyde_tr3 = 3;
	public static final int CHAIN_TRANS_tr1_FROM_processChecking_TO_processChecking_BY_sendStatuspoolingAirFlow_tr1 = 4;
	public static final int CHAIN_TRANS_tr4_FROM_processChecking_TO_processChecking_BY_sendValueRegisterpoolingAirFlow_tr4 = 5;
	public static final int CHAIN_TRANS_tr5_FROM_processChecking_TO_processChecking_BY_sendValueRegisterpoolingMethane_tr5 = 6;
	public static final int CHAIN_TRANS_tr6_FROM_processChecking_TO_processChecking_BY_sendValueRegisterpoolingCarbonMonohyde_tr6 = 7;
	public static final int CHAIN_TRANS_init0_FROM_connect_TO_processChecking_BY_connectedtcpEnvironment = 8;
	public static final int CHAIN_TRANS_INITIAL_TO__connect = 9;
	
	/* triggers */
	public static final int POLLING = 0;
	public static final int TRIG_poolingAirFlow__sendStatus = IFITEM_poolingAirFlow + EVT_SHIFT*PoolingSensorCommands.IN_sendStatus;
	public static final int TRIG_poolingAirFlow__sendValueRegister = IFITEM_poolingAirFlow + EVT_SHIFT*PoolingSensorCommands.IN_sendValueRegister;
	public static final int TRIG_poolingAirFlow__sendErrorRegister = IFITEM_poolingAirFlow + EVT_SHIFT*PoolingSensorCommands.IN_sendErrorRegister;
	public static final int TRIG_poolingMethane__sendStatus = IFITEM_poolingMethane + EVT_SHIFT*PoolingSensorCommands.IN_sendStatus;
	public static final int TRIG_poolingMethane__sendValueRegister = IFITEM_poolingMethane + EVT_SHIFT*PoolingSensorCommands.IN_sendValueRegister;
	public static final int TRIG_poolingMethane__sendErrorRegister = IFITEM_poolingMethane + EVT_SHIFT*PoolingSensorCommands.IN_sendErrorRegister;
	public static final int TRIG_poolingCarbonMonohyde__sendStatus = IFITEM_poolingCarbonMonohyde + EVT_SHIFT*PoolingSensorCommands.IN_sendStatus;
	public static final int TRIG_poolingCarbonMonohyde__sendValueRegister = IFITEM_poolingCarbonMonohyde + EVT_SHIFT*PoolingSensorCommands.IN_sendValueRegister;
	public static final int TRIG_poolingCarbonMonohyde__sendErrorRegister = IFITEM_poolingCarbonMonohyde + EVT_SHIFT*PoolingSensorCommands.IN_sendErrorRegister;
	public static final int TRIG_tcpEnvironment__connected = IFITEM_tcpEnvironment + EVT_SHIFT*PGuiCommunicationInterface.OUT_connected;
	public static final int TRIG_tcpEnvironment__setPumpFlow = IFITEM_tcpEnvironment + EVT_SHIFT*PGuiCommunicationInterface.OUT_setPumpFlow;
	public static final int TRIG_tcpEnvironment__setWaterTenkFlow = IFITEM_tcpEnvironment + EVT_SHIFT*PGuiCommunicationInterface.OUT_setWaterTenkFlow;
	public static final int TRIG_tcpEnvironment__turnOnPump = IFITEM_tcpEnvironment + EVT_SHIFT*PGuiCommunicationInterface.OUT_turnOnPump;
	public static final int TRIG_tcpEnvironment__turnOffPump = IFITEM_tcpEnvironment + EVT_SHIFT*PGuiCommunicationInterface.OUT_turnOffPump;
	public static final int TRIG_timingService__timeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_timeout;
	public static final int TRIG_timingService__internalTimer = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimer;
	public static final int TRIG_timingService__internalTimeout = IFITEM_timingService + EVT_SHIFT*PTimer.OUT_internalTimeout;
	
	// state names
	protected static final String stateStrings[] = {
		"<no state>",
		"<top>",
		"processChecking",
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
	protected void action_TRANS_tr0_FROM_processChecking_TO_processChecking_BY_timeouttimingService_tr0(InterfaceItemBase ifitem) {
		long timeStart = System.currentTimeMillis();
			poolingAirFlow.readStatus();
			poolingMethane.readStatus();
			poolingCarbonMonohyde.readStatus();
			long timeEnd = System.currentTimeMillis();
			readStatusTime = timeEnd - timeStart;
	}
	protected void action_TRANS_tr2_FROM_processChecking_TO_processChecking_BY_sendStatuspoolingMethane_tr2(InterfaceItemBase ifitem, byte transitionData) {
		long timeStart = System.currentTimeMillis();
		methaneNextActivationInMs += PERIOD_OF_ACTIVATION;
		int status = transitionData;               
		if (status == PoolingSensorCommands.Status.ERROR.ordinal()){
		    System.out.println("Error while geting methane level");
		    currentMethaneErrorCount++;
		    if (currentMethaneErrorCount >= MAX_ERROR_COUNT){
		        currentMethaneErrorCount = 0;
		  		sendAlarm.alarmErrorReadingMethane();
		    }
		    poolingMethane.startADConverstion();
		    timingService.kill();
		    timingService.startTimeout(calculateWaitTime(methaneNextActivationInMs));
			calculateExecutionTime();
		} else {
			if (status == PoolingSensorCommands.Status.READY.ordinal()){
			     poolingMethane.readValueRegister();
			}
			long timeEnd = System.currentTimeMillis();
			currentMethaneExecutionTimeInMs += timeEnd - timeStart;
		}
	}
	protected void action_TRANS_tr3_FROM_processChecking_TO_processChecking_BY_sendStatuspoolingCarbonMonohyde_tr3(InterfaceItemBase ifitem, byte transitionData) {
		long timeStart = System.currentTimeMillis();
		carbonNextActivationInMs += PERIOD_OF_ACTIVATION;
		int status = transitionData;               
		if (status == PoolingSensorCommands.Status.NOT_READY.ordinal()){
		     	timingService.kill();
		        timingService.startTimeout(PERIOD_OF_CHECKING_DEVICE);      
		}
		if (status == PoolingSensorCommands.Status.ERROR.ordinal()){
		    System.out.println("Error while geting carbon-monohye level");
		    currentCarbonMonoxydeErrorCount++;
		    if (currentCarbonMonoxydeErrorCount >= MAX_ERROR_COUNT){
		        currentCarbonMonoxydeErrorCount = 0;
		        sendAlarm.alarmErrorReadingCarbonMonoxyde();
		     }
		    poolingCarbonMonohyde.startADConverstion();
		    timingService.kill();
		    timingService.startTimeout(calculateWaitTime(carbonNextActivationInMs));
		    long timeEnd = System.currentTimeMillis();
			currentMethaneExecutionTimeInMs += timeEnd - timeStart;
			calculateExecutionTime();
		} else {
			if (status == PoolingSensorCommands.Status.READY.ordinal()){
			     poolingCarbonMonohyde.readValueRegister();
			}
			long timeEnd = System.currentTimeMillis();
			currentCarbonExecutionTimeInMs += timeEnd - timeStart;
		}
	}
	protected void action_TRANS_tr1_FROM_processChecking_TO_processChecking_BY_sendStatuspoolingAirFlow_tr1(InterfaceItemBase ifitem, byte transitionData) {
		long timeStart = System.currentTimeMillis();
			airFlowNextActivationInMs += PERIOD_OF_ACTIVATION;
			int status = transitionData;               
			if (status == PoolingSensorCommands.Status.NOT_READY.ordinal()){
			     	timingService.kill();
			        timingService.startTimeout(PERIOD_OF_CHECKING_DEVICE);      
			}
			if (status == PoolingSensorCommands.Status.ERROR.ordinal()){
			    System.out.println("Error while geting airflow level");
			    currentAirFlowErrorCount++;
			    if (currentAirFlowErrorCount >= MAX_ERROR_COUNT){
			        currentAirFlowErrorCount = 0;
			        sendAlarm.alarmErrorReadingAirFlow();
		        }
			    poolingAirFlow.startADConverstion();
			    timingService.kill();
			    timingService.startTimeout(calculateWaitTime(airFlowNextActivationInMs));     
				calculateExecutionTime();
			} else {
				if (status == PoolingSensorCommands.Status.READY.ordinal()){
				     poolingAirFlow.readValueRegister();
				}
				long timeEnd = System.currentTimeMillis();
				currentAirFlowExecutionTimeInMs += timeEnd - timeStart;
			}
	}
	protected void action_TRANS_tr4_FROM_processChecking_TO_processChecking_BY_sendValueRegisterpoolingAirFlow_tr4(InterfaceItemBase ifitem, double transitionData) {
								long timeStart = System.currentTimeMillis();
								airFlowNextActivationInMs += PERIOD_OF_ACTIVATION;
		//						System.out.println("Izmeren airflow: " + transitionData);
								double value = transitionData;
								if (value <= AIRFLOW_LOW_LEVEL){
									sendAlarm.alarmLowAirFlow();
									sendAirFlow(true);
									
								} else {
									sendAirFlow(false);
								}
								poolingAirFlow.startADConverstion();
								timingService.kill();
								timingService.startTimeout(calculateWaitTime(airFlowNextActivationInMs));
								currentAirFlowErrorCount = 0;
								long timeEnd = System.currentTimeMillis();
								currentAirFlowExecutionTimeInMs += timeEnd - timeStart;
								calculateExecutionTime();
	}
	protected void action_TRANS_tr5_FROM_processChecking_TO_processChecking_BY_sendValueRegisterpoolingMethane_tr5(InterfaceItemBase ifitem, double transitionData) {
								long timeStart = System.currentTimeMillis();
								methaneNextActivationInMs += PERIOD_OF_ACTIVATION;
		//						System.out.println("Izmeren metan: " + transitionData);
								double value = transitionData;
								if (value >= METHANE_HIGH_LEVEL){
									sendAlarm.alarmHighMethaneLevel();
									methaneLevel.highLevel();
									sendMethane(true);
								} else {
									methaneLevel.normalLevel();
									sendMethane(false);
								}
								currentMethaneErrorCount = 0;
								timingService.startTimeout(calculateWaitTime(methaneNextActivationInMs));
								timingService.kill();
								timingService.startTimeout(PERIOD_OF_ACTIVATION);
								long timeEnd = System.currentTimeMillis();
								currentMethaneExecutionTimeInMs += timeEnd - timeStart;
								calculateExecutionTime();
	}
	protected void action_TRANS_tr6_FROM_processChecking_TO_processChecking_BY_sendValueRegisterpoolingCarbonMonohyde_tr6(InterfaceItemBase ifitem, double transitionData) {
								long timeStart = System.currentTimeMillis();
								carbonNextActivationInMs += PERIOD_OF_ACTIVATION;
		//						System.out.println("Izmeren carbon monohyde: " + transitionData);
								double value = transitionData;
								if (value >= CARBON_MONOXYDE_HIGH_LEVEL){
									sendAlarm.alarmHighCarbonMonoxydeLevel();
									sendCarbon(true);
								} else {
									sendCarbon(false);
								}
								poolingCarbonMonohyde.startADConverstion();
								timingService.kill();
								currentCarbonMonoxydeErrorCount = 0;
								timingService.startTimeout(calculateWaitTime(carbonNextActivationInMs));
								long timeEnd = System.currentTimeMillis();
								currentCarbonExecutionTimeInMs += timeEnd - timeStart;
								calculateExecutionTime();
	}
	protected void action_TRANS_init0_FROM_connect_TO_processChecking_BY_connectedtcpEnvironment(InterfaceItemBase ifitem) {
								long timeStart = System.currentTimeMillis();
								methaneNextActivationInMs = timeStart + PERIOD_OF_ACTIVATION;
								airFlowNextActivationInMs = methaneNextActivationInMs;
								carbonNextActivationInMs = methaneNextActivationInMs;
								poolingMethane.startADConverstion();
								poolingAirFlow.startADConverstion();
								poolingCarbonMonohyde.startADConverstion();
								timingService.startTimeout(calculateWaitTime(methaneNextActivationInMs));
								long timeEnd = System.currentTimeMillis();
								long duration = timeEnd - timeStart;
		//						System.out.println("Duration environment monitoring: " + duration);
								if (duration > worstExecutionTimeInMs){
									worstExecutionTimeInMs = duration;
									System.out.println("WCET environment monitoring: " + worstExecutionTimeInMs);
								}
	}
	protected void action_TRANS_INITIAL_TO__connect() {
		tcpEnvironment.connect(4020);
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
				case STATE_processChecking:
					this.history[STATE_TOP] = STATE_processChecking;
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
			case EnvironmentPooling.CHAIN_TRANS_INITIAL_TO__connect:
			{
				action_TRANS_INITIAL_TO__connect();
				return STATE_connect;
			}
			case EnvironmentPooling.CHAIN_TRANS_init0_FROM_connect_TO_processChecking_BY_connectedtcpEnvironment:
			{
				action_TRANS_init0_FROM_connect_TO_processChecking_BY_connectedtcpEnvironment(ifitem);
				return STATE_processChecking;
			}
			case EnvironmentPooling.CHAIN_TRANS_tr0_FROM_processChecking_TO_processChecking_BY_timeouttimingService_tr0:
			{
				action_TRANS_tr0_FROM_processChecking_TO_processChecking_BY_timeouttimingService_tr0(ifitem);
				return STATE_processChecking;
			}
			case EnvironmentPooling.CHAIN_TRANS_tr1_FROM_processChecking_TO_processChecking_BY_sendStatuspoolingAirFlow_tr1:
			{
				byte transitionData = (Byte) generic_data__et;
				action_TRANS_tr1_FROM_processChecking_TO_processChecking_BY_sendStatuspoolingAirFlow_tr1(ifitem, transitionData);
				return STATE_processChecking;
			}
			case EnvironmentPooling.CHAIN_TRANS_tr2_FROM_processChecking_TO_processChecking_BY_sendStatuspoolingMethane_tr2:
			{
				byte transitionData = (Byte) generic_data__et;
				action_TRANS_tr2_FROM_processChecking_TO_processChecking_BY_sendStatuspoolingMethane_tr2(ifitem, transitionData);
				return STATE_processChecking;
			}
			case EnvironmentPooling.CHAIN_TRANS_tr3_FROM_processChecking_TO_processChecking_BY_sendStatuspoolingCarbonMonohyde_tr3:
			{
				byte transitionData = (Byte) generic_data__et;
				action_TRANS_tr3_FROM_processChecking_TO_processChecking_BY_sendStatuspoolingCarbonMonohyde_tr3(ifitem, transitionData);
				return STATE_processChecking;
			}
			case EnvironmentPooling.CHAIN_TRANS_tr4_FROM_processChecking_TO_processChecking_BY_sendValueRegisterpoolingAirFlow_tr4:
			{
				double transitionData = (Double) generic_data__et;
				action_TRANS_tr4_FROM_processChecking_TO_processChecking_BY_sendValueRegisterpoolingAirFlow_tr4(ifitem, transitionData);
				return STATE_processChecking;
			}
			case EnvironmentPooling.CHAIN_TRANS_tr5_FROM_processChecking_TO_processChecking_BY_sendValueRegisterpoolingMethane_tr5:
			{
				double transitionData = (Double) generic_data__et;
				action_TRANS_tr5_FROM_processChecking_TO_processChecking_BY_sendValueRegisterpoolingMethane_tr5(ifitem, transitionData);
				return STATE_processChecking;
			}
			case EnvironmentPooling.CHAIN_TRANS_tr6_FROM_processChecking_TO_processChecking_BY_sendValueRegisterpoolingCarbonMonohyde_tr6:
			{
				double transitionData = (Double) generic_data__et;
				action_TRANS_tr6_FROM_processChecking_TO_processChecking_BY_sendValueRegisterpoolingCarbonMonohyde_tr6(ifitem, transitionData);
				return STATE_processChecking;
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
				case STATE_processChecking:
					/* in leaf state: return state id */
					return STATE_processChecking;
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
		int chain__et = EnvironmentPooling.CHAIN_TRANS_INITIAL_TO__connect;
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
						case TRIG_tcpEnvironment__connected:
							{
								chain__et = EnvironmentPooling.CHAIN_TRANS_init0_FROM_connect_TO_processChecking_BY_connectedtcpEnvironment;
								catching_state__et = STATE_TOP;
							}
						break;
						default:
							/* should not occur */
							break;
					}
					break;
				case STATE_processChecking:
					switch(trigger__et) {
						case TRIG_poolingAirFlow__sendStatus:
							{
								chain__et = EnvironmentPooling.CHAIN_TRANS_tr1_FROM_processChecking_TO_processChecking_BY_sendStatuspoolingAirFlow_tr1;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_poolingAirFlow__sendValueRegister:
							{
								chain__et = EnvironmentPooling.CHAIN_TRANS_tr4_FROM_processChecking_TO_processChecking_BY_sendValueRegisterpoolingAirFlow_tr4;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_poolingCarbonMonohyde__sendStatus:
							{
								chain__et = EnvironmentPooling.CHAIN_TRANS_tr3_FROM_processChecking_TO_processChecking_BY_sendStatuspoolingCarbonMonohyde_tr3;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_poolingCarbonMonohyde__sendValueRegister:
							{
								chain__et = EnvironmentPooling.CHAIN_TRANS_tr6_FROM_processChecking_TO_processChecking_BY_sendValueRegisterpoolingCarbonMonohyde_tr6;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_poolingMethane__sendStatus:
							{
								chain__et = EnvironmentPooling.CHAIN_TRANS_tr2_FROM_processChecking_TO_processChecking_BY_sendStatuspoolingMethane_tr2;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_poolingMethane__sendValueRegister:
							{
								chain__et = EnvironmentPooling.CHAIN_TRANS_tr5_FROM_processChecking_TO_processChecking_BY_sendValueRegisterpoolingMethane_tr5;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_timingService__timeout:
							{
								chain__et = EnvironmentPooling.CHAIN_TRANS_tr0_FROM_processChecking_TO_processChecking_BY_timeouttimingService_tr0;
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
