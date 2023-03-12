package TcpCommunication;

import org.eclipse.etrice.runtime.java.messaging.*;
import org.eclipse.etrice.runtime.java.modelbase.*;
import org.eclipse.etrice.runtime.java.debugging.*;

import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;

import etrice.api.tcp.*;
import TcpCommunication.PGuiCommunicationInterface.*;
import etrice.api.tcp.PTcpControl.*;
import etrice.api.tcp.PTcpPayload.*;



public class GuiCommuncitaion extends ActorClassBase {


	//--------------------- ports
	protected PGuiCommunicationInterfacePort fct = null;
	protected PTcpControlConjPort ctrl = null;
	protected PTcpPayloadConjPort payload = null;

	//--------------------- saps

	//--------------------- services

	//--------------------- optional actors

	//--------------------- interface item IDs
	public static final int IFITEM_fct = 1;
	public static final int IFITEM_ctrl = 2;
	public static final int IFITEM_payload = 3;

	/*--------------------- attributes ---------------------*/

	/*--------------------- operations ---------------------*/
	public  void sendPayloadString(String data) {
						DTcpPayload tcpPackage = new DTcpPayload();
						data += "\n";
						tcpPackage.setData(data.getBytes());
						tcpPackage.setLength(data.length());
		//				System.out.println(data);
						payload.send(tcpPackage);
	}
	public  void sendOnOffCommand(String message, boolean status) {
						DTcpPayload tcpPackage = new DTcpPayload();
						String finalMessage = message;
						if (!status){
							finalMessage += "=off\n";
						}
						else {
							finalMessage += "=on\n";
						}
						tcpPackage.setData(finalMessage.getBytes());
						tcpPackage.setLength(finalMessage.length());
		//				System.out.println("package=" + tcpPackage.getData());
						payload.send(tcpPackage);
	}


	//--------------------- construction
	public GuiCommuncitaion(IRTObject parent, String name) {
		super(parent, name);
		setClassName("GuiCommuncitaion");

		// initialize attributes

		// own ports
		fct = new PGuiCommunicationInterfacePort(this, "fct", IFITEM_fct);
		ctrl = new PTcpControlConjPort(this, "ctrl", IFITEM_ctrl);
		payload = new PTcpPayloadConjPort(this, "payload", IFITEM_payload);

		// own saps

		// own service implementations

		// sub actors

		// wiring


		/* user defined constructor body */

	}

	/* --------------------- attribute setters and getters */


	//--------------------- port getters
	public PGuiCommunicationInterfacePort getFct (){
		return this.fct;
	}
	public PTcpControlConjPort getCtrl (){
		return this.ctrl;
	}
	public PTcpPayloadConjPort getPayload (){
		return this.payload;
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
	public static final int STATE_Connecting = 2;
	public static final int STATE_Connected = 3;
	public static final int STATE_Disconnected = 4;
	public static final int STATE_MAX = 5;
	
	/* transition chains */
	public static final int CHAIN_TRANS_INITIAL_TO__Disconnected = 1;
	public static final int CHAIN_TRANS_tr0_FROM_Connected_TO_Connected_BY_setPumpStatefct_tr0 = 2;
	public static final int CHAIN_TRANS_tr1_FROM_Connecting_TO_Connected_BY_establishedctrl = 3;
	public static final int CHAIN_TRANS_tr2_FROM_Disconnected_TO_Connecting_BY_connectfct = 4;
	public static final int CHAIN_TRANS_tr3_FROM_Connected_TO_Connected_BY_setAlarmfct_tr3 = 5;
	public static final int CHAIN_TRANS_tr4_FROM_Connected_TO_Connected_BY_setPumpFlowfct_tr4 = 6;
	public static final int CHAIN_TRANS_tr5_FROM_Connected_TO_Connected_BY_setWaterLevelfct_tr5 = 7;
	public static final int CHAIN_TRANS_tr6_FROM_Connected_TO_Connected_BY_errorctrl_tr6 = 8;
	public static final int CHAIN_TRANS_tr7_FROM_Connected_TO_Connected_BY_setMethaneLevelfct_tr7 = 9;
	public static final int CHAIN_TRANS_tr8_FROM_Connected_TO_Connected_BY_setCarbonLevelfct_tr8 = 10;
	public static final int CHAIN_TRANS_tr9_FROM_Connected_TO_Connected_BY_setAirFlowLevelfct_tr9 = 11;
	public static final int CHAIN_TRANS_tr10_FROM_Connected_TO_Connected_BY_setLowWaterLevelfct_tr10 = 12;
	public static final int CHAIN_TRANS_tr11_FROM_Connected_TO_Connected_BY_setHighWaterLevelfct_tr11 = 13;
	public static final int CHAIN_TRANS_tr12_FROM_Connected_TO_Connected_BY_receivepayload_tr12 = 14;
	
	/* triggers */
	public static final int POLLING = 0;
	public static final int TRIG_fct__connect = IFITEM_fct + EVT_SHIFT*PGuiCommunicationInterface.IN_connect;
	public static final int TRIG_fct__setPumpState = IFITEM_fct + EVT_SHIFT*PGuiCommunicationInterface.IN_setPumpState;
	public static final int TRIG_fct__setPumpFlow = IFITEM_fct + EVT_SHIFT*PGuiCommunicationInterface.IN_setPumpFlow;
	public static final int TRIG_fct__setAlarm = IFITEM_fct + EVT_SHIFT*PGuiCommunicationInterface.IN_setAlarm;
	public static final int TRIG_fct__setWaterLevel = IFITEM_fct + EVT_SHIFT*PGuiCommunicationInterface.IN_setWaterLevel;
	public static final int TRIG_fct__setMethaneLevel = IFITEM_fct + EVT_SHIFT*PGuiCommunicationInterface.IN_setMethaneLevel;
	public static final int TRIG_fct__setCarbonLevel = IFITEM_fct + EVT_SHIFT*PGuiCommunicationInterface.IN_setCarbonLevel;
	public static final int TRIG_fct__setAirFlowLevel = IFITEM_fct + EVT_SHIFT*PGuiCommunicationInterface.IN_setAirFlowLevel;
	public static final int TRIG_fct__setHighWaterLevel = IFITEM_fct + EVT_SHIFT*PGuiCommunicationInterface.IN_setHighWaterLevel;
	public static final int TRIG_fct__setLowWaterLevel = IFITEM_fct + EVT_SHIFT*PGuiCommunicationInterface.IN_setLowWaterLevel;
	public static final int TRIG_ctrl__established = IFITEM_ctrl + EVT_SHIFT*PTcpControl.OUT_established;
	public static final int TRIG_ctrl__error = IFITEM_ctrl + EVT_SHIFT*PTcpControl.OUT_error;
	public static final int TRIG_payload__receive = IFITEM_payload + EVT_SHIFT*PTcpPayload.OUT_receive;
	
	// state names
	protected static final String stateStrings[] = {
		"<no state>",
		"<top>",
		"Connecting",
		"Connected",
		"Disconnected"
	};
	
	// history
	protected int history[] = {NO_STATE, NO_STATE, NO_STATE, NO_STATE, NO_STATE};
	
	private void setState(int new_state) {
		DebuggingService.getInstance().addActorState(this,stateStrings[new_state]);
		this.state = new_state;
	}
	
	/* Entry and Exit Codes */
	
	/* Action Codes */
	protected void action_TRANS_tr0_FROM_Connected_TO_Connected_BY_setPumpStatefct_tr0(InterfaceItemBase ifitem, boolean transitionData) {
		sendOnOffCommand("setPumpState", transitionData);
	}
	protected void action_TRANS_tr1_FROM_Connecting_TO_Connected_BY_establishedctrl(InterfaceItemBase ifitem) {
		fct.connected();
	}
	protected void action_TRANS_tr2_FROM_Disconnected_TO_Connecting_BY_connectfct(InterfaceItemBase ifitem, int transitionData) {
		System.out.println("tli: connecting to port " + (int)transitionData);
		DTcpControl config = new DTcpControl("localhost", transitionData);
		ctrl.open(config);
	}
	protected void action_TRANS_tr3_FROM_Connected_TO_Connected_BY_setAlarmfct_tr3(InterfaceItemBase ifitem, String transitionData) {
		sendPayloadString("setAlarm=" + transitionData);
	}
	protected void action_TRANS_tr4_FROM_Connected_TO_Connected_BY_setPumpFlowfct_tr4(InterfaceItemBase ifitem, double transitionData) {
		sendPayloadString("setPumpFlow=" + transitionData);
	}
	protected void action_TRANS_tr5_FROM_Connected_TO_Connected_BY_setWaterLevelfct_tr5(InterfaceItemBase ifitem, double transitionData) {
		sendPayloadString("setWaterLevel=" + transitionData);
	}
	protected void action_TRANS_tr6_FROM_Connected_TO_Connected_BY_errorctrl_tr6(InterfaceItemBase ifitem) {
		System.out.println("Greska:");
	}
	protected void action_TRANS_tr7_FROM_Connected_TO_Connected_BY_setMethaneLevelfct_tr7(InterfaceItemBase ifitem, boolean transitionData) {
		sendOnOffCommand("setMethaneLevel", transitionData);
	}
	protected void action_TRANS_tr8_FROM_Connected_TO_Connected_BY_setCarbonLevelfct_tr8(InterfaceItemBase ifitem, boolean transitionData) {
		sendOnOffCommand("setCarbonLevel", transitionData);
	}
	protected void action_TRANS_tr9_FROM_Connected_TO_Connected_BY_setAirFlowLevelfct_tr9(InterfaceItemBase ifitem, boolean transitionData) {
		sendOnOffCommand("setAirFlowLevel", transitionData);
	}
	protected void action_TRANS_tr10_FROM_Connected_TO_Connected_BY_setLowWaterLevelfct_tr10(InterfaceItemBase ifitem, boolean transitionData) {
		sendOnOffCommand("setLowWaterLevel", transitionData);
	}
	protected void action_TRANS_tr11_FROM_Connected_TO_Connected_BY_setHighWaterLevelfct_tr11(InterfaceItemBase ifitem, boolean transitionData) {
		sendOnOffCommand("setHighWaterLevel", transitionData);
	}
	protected void action_TRANS_tr12_FROM_Connected_TO_Connected_BY_receivepayload_tr12(InterfaceItemBase ifitem, DTcpPayload transitionData) {
		String data = new String(transitionData.getData());
		if (data.startsWith("setPumpFlow=")) {
			double newPumpFlow = Double.parseDouble(data.split("=")[1]);
			fct.setPumpFlow(newPumpFlow);
		} else if (data.startsWith("setWaterTenkFlow=")) {
			double newWaterFlow = Double.parseDouble(data.split("=")[1]);
			fct.setWaterTenkFlow(newWaterFlow);
		} else if (data.startsWith("turnOnPump")){
			fct.turnOnPump();
		} else if (data.startsWith("turnOffPump")){
			fct.turnOffPump();	
		}
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
				case STATE_Connected:
					this.history[STATE_TOP] = STATE_Connected;
					current__et = STATE_TOP;
					break;
				case STATE_Connecting:
					this.history[STATE_TOP] = STATE_Connecting;
					current__et = STATE_TOP;
					break;
				case STATE_Disconnected:
					this.history[STATE_TOP] = STATE_Disconnected;
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
			case GuiCommuncitaion.CHAIN_TRANS_INITIAL_TO__Disconnected:
			{
				return STATE_Disconnected;
			}
			case GuiCommuncitaion.CHAIN_TRANS_tr0_FROM_Connected_TO_Connected_BY_setPumpStatefct_tr0:
			{
				boolean transitionData = (Boolean) generic_data__et;
				action_TRANS_tr0_FROM_Connected_TO_Connected_BY_setPumpStatefct_tr0(ifitem, transitionData);
				return STATE_Connected;
			}
			case GuiCommuncitaion.CHAIN_TRANS_tr10_FROM_Connected_TO_Connected_BY_setLowWaterLevelfct_tr10:
			{
				boolean transitionData = (Boolean) generic_data__et;
				action_TRANS_tr10_FROM_Connected_TO_Connected_BY_setLowWaterLevelfct_tr10(ifitem, transitionData);
				return STATE_Connected;
			}
			case GuiCommuncitaion.CHAIN_TRANS_tr11_FROM_Connected_TO_Connected_BY_setHighWaterLevelfct_tr11:
			{
				boolean transitionData = (Boolean) generic_data__et;
				action_TRANS_tr11_FROM_Connected_TO_Connected_BY_setHighWaterLevelfct_tr11(ifitem, transitionData);
				return STATE_Connected;
			}
			case GuiCommuncitaion.CHAIN_TRANS_tr12_FROM_Connected_TO_Connected_BY_receivepayload_tr12:
			{
				DTcpPayload transitionData = (DTcpPayload) generic_data__et;
				action_TRANS_tr12_FROM_Connected_TO_Connected_BY_receivepayload_tr12(ifitem, transitionData);
				return STATE_Connected;
			}
			case GuiCommuncitaion.CHAIN_TRANS_tr1_FROM_Connecting_TO_Connected_BY_establishedctrl:
			{
				action_TRANS_tr1_FROM_Connecting_TO_Connected_BY_establishedctrl(ifitem);
				return STATE_Connected;
			}
			case GuiCommuncitaion.CHAIN_TRANS_tr2_FROM_Disconnected_TO_Connecting_BY_connectfct:
			{
				int transitionData = (Integer) generic_data__et;
				action_TRANS_tr2_FROM_Disconnected_TO_Connecting_BY_connectfct(ifitem, transitionData);
				return STATE_Connecting;
			}
			case GuiCommuncitaion.CHAIN_TRANS_tr3_FROM_Connected_TO_Connected_BY_setAlarmfct_tr3:
			{
				String transitionData = (String) generic_data__et;
				action_TRANS_tr3_FROM_Connected_TO_Connected_BY_setAlarmfct_tr3(ifitem, transitionData);
				return STATE_Connected;
			}
			case GuiCommuncitaion.CHAIN_TRANS_tr4_FROM_Connected_TO_Connected_BY_setPumpFlowfct_tr4:
			{
				double transitionData = (Double) generic_data__et;
				action_TRANS_tr4_FROM_Connected_TO_Connected_BY_setPumpFlowfct_tr4(ifitem, transitionData);
				return STATE_Connected;
			}
			case GuiCommuncitaion.CHAIN_TRANS_tr5_FROM_Connected_TO_Connected_BY_setWaterLevelfct_tr5:
			{
				double transitionData = (Double) generic_data__et;
				action_TRANS_tr5_FROM_Connected_TO_Connected_BY_setWaterLevelfct_tr5(ifitem, transitionData);
				return STATE_Connected;
			}
			case GuiCommuncitaion.CHAIN_TRANS_tr6_FROM_Connected_TO_Connected_BY_errorctrl_tr6:
			{
				action_TRANS_tr6_FROM_Connected_TO_Connected_BY_errorctrl_tr6(ifitem);
				return STATE_Connected;
			}
			case GuiCommuncitaion.CHAIN_TRANS_tr7_FROM_Connected_TO_Connected_BY_setMethaneLevelfct_tr7:
			{
				boolean transitionData = (Boolean) generic_data__et;
				action_TRANS_tr7_FROM_Connected_TO_Connected_BY_setMethaneLevelfct_tr7(ifitem, transitionData);
				return STATE_Connected;
			}
			case GuiCommuncitaion.CHAIN_TRANS_tr8_FROM_Connected_TO_Connected_BY_setCarbonLevelfct_tr8:
			{
				boolean transitionData = (Boolean) generic_data__et;
				action_TRANS_tr8_FROM_Connected_TO_Connected_BY_setCarbonLevelfct_tr8(ifitem, transitionData);
				return STATE_Connected;
			}
			case GuiCommuncitaion.CHAIN_TRANS_tr9_FROM_Connected_TO_Connected_BY_setAirFlowLevelfct_tr9:
			{
				boolean transitionData = (Boolean) generic_data__et;
				action_TRANS_tr9_FROM_Connected_TO_Connected_BY_setAirFlowLevelfct_tr9(ifitem, transitionData);
				return STATE_Connected;
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
				case STATE_Connected:
					/* in leaf state: return state id */
					return STATE_Connected;
				case STATE_Connecting:
					/* in leaf state: return state id */
					return STATE_Connecting;
				case STATE_Disconnected:
					/* in leaf state: return state id */
					return STATE_Disconnected;
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
		int chain__et = GuiCommuncitaion.CHAIN_TRANS_INITIAL_TO__Disconnected;
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
				case STATE_Connected:
					switch(trigger__et) {
						case TRIG_ctrl__error:
							{
								chain__et = GuiCommuncitaion.CHAIN_TRANS_tr6_FROM_Connected_TO_Connected_BY_errorctrl_tr6;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_fct__setAirFlowLevel:
							{
								chain__et = GuiCommuncitaion.CHAIN_TRANS_tr9_FROM_Connected_TO_Connected_BY_setAirFlowLevelfct_tr9;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_fct__setAlarm:
							{
								chain__et = GuiCommuncitaion.CHAIN_TRANS_tr3_FROM_Connected_TO_Connected_BY_setAlarmfct_tr3;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_fct__setCarbonLevel:
							{
								chain__et = GuiCommuncitaion.CHAIN_TRANS_tr8_FROM_Connected_TO_Connected_BY_setCarbonLevelfct_tr8;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_fct__setHighWaterLevel:
							{
								chain__et = GuiCommuncitaion.CHAIN_TRANS_tr11_FROM_Connected_TO_Connected_BY_setHighWaterLevelfct_tr11;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_fct__setLowWaterLevel:
							{
								chain__et = GuiCommuncitaion.CHAIN_TRANS_tr10_FROM_Connected_TO_Connected_BY_setLowWaterLevelfct_tr10;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_fct__setMethaneLevel:
							{
								chain__et = GuiCommuncitaion.CHAIN_TRANS_tr7_FROM_Connected_TO_Connected_BY_setMethaneLevelfct_tr7;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_fct__setPumpFlow:
							{
								chain__et = GuiCommuncitaion.CHAIN_TRANS_tr4_FROM_Connected_TO_Connected_BY_setPumpFlowfct_tr4;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_fct__setPumpState:
							{
								chain__et = GuiCommuncitaion.CHAIN_TRANS_tr0_FROM_Connected_TO_Connected_BY_setPumpStatefct_tr0;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_fct__setWaterLevel:
							{
								chain__et = GuiCommuncitaion.CHAIN_TRANS_tr5_FROM_Connected_TO_Connected_BY_setWaterLevelfct_tr5;
								catching_state__et = STATE_TOP;
							}
						break;
						case TRIG_payload__receive:
							{
								chain__et = GuiCommuncitaion.CHAIN_TRANS_tr12_FROM_Connected_TO_Connected_BY_receivepayload_tr12;
								catching_state__et = STATE_TOP;
							}
						break;
						default:
							/* should not occur */
							break;
					}
					break;
				case STATE_Connecting:
					switch(trigger__et) {
						case TRIG_ctrl__established:
							{
								chain__et = GuiCommuncitaion.CHAIN_TRANS_tr1_FROM_Connecting_TO_Connected_BY_establishedctrl;
								catching_state__et = STATE_TOP;
							}
						break;
						default:
							/* should not occur */
							break;
					}
					break;
				case STATE_Disconnected:
					switch(trigger__et) {
						case TRIG_fct__connect:
							{
								chain__et = GuiCommuncitaion.CHAIN_TRANS_tr2_FROM_Disconnected_TO_Connecting_BY_connectfct;
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
