package TcpCommunication;

import org.eclipse.etrice.runtime.java.messaging.Message;
import org.eclipse.etrice.runtime.java.modelbase.EventMessage;
import org.eclipse.etrice.runtime.java.modelbase.EventWithDataMessage;
import org.eclipse.etrice.runtime.java.modelbase.IInterfaceItemOwner;
import org.eclipse.etrice.runtime.java.modelbase.InterfaceItemBase;
import org.eclipse.etrice.runtime.java.modelbase.PortBase;
import org.eclipse.etrice.runtime.java.modelbase.ReplicatedPortBase;
import org.eclipse.etrice.runtime.java.debugging.DebuggingService;
import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;



public class PGuiCommunicationInterface {
	// message IDs
	public static final int MSG_MIN = 0;
	public static final int OUT_connected = 1;
	public static final int OUT_setPumpFlow = 2;
	public static final int OUT_setWaterTenkFlow = 3;
	public static final int OUT_turnOnPump = 4;
	public static final int OUT_turnOffPump = 5;
	public static final int IN_connect = 6;
	public static final int IN_setPumpState = 7;
	public static final int IN_setPumpFlow = 8;
	public static final int IN_setAlarm = 9;
	public static final int IN_setWaterLevel = 10;
	public static final int IN_setMethaneLevel = 11;
	public static final int IN_setCarbonLevel = 12;
	public static final int IN_setAirFlowLevel = 13;
	public static final int IN_setHighWaterLevel = 14;
	public static final int IN_setLowWaterLevel = 15;
	public static final int MSG_MAX = 16;


	private static String messageStrings[] = {"MIN", "connected","setPumpFlow","setWaterTenkFlow","turnOnPump","turnOffPump", "connect","setPumpState","setPumpFlow","setAlarm","setWaterLevel","setMethaneLevel","setCarbonLevel","setAirFlowLevel","setHighWaterLevel","setLowWaterLevel","MAX"};

	public String getMessageString(int msg_id) {
		if (msg_id<MSG_MIN || msg_id>MSG_MAX+1){
			// id out of range
			return "Message ID out of range";
		}
		else{
			return messageStrings[msg_id];
		}
	}

	
	// port class
	static public class PGuiCommunicationInterfacePort extends PortBase {
		// constructors
		public PGuiCommunicationInterfacePort(IInterfaceItemOwner actor, String name, int localId) {
			this(actor, name, localId, 0);
		}
		public PGuiCommunicationInterfacePort(IInterfaceItemOwner actor, String name, int localId, int idx) {
			super(actor, name, localId, idx);
			DebuggingService.getInstance().addPortInstance(this);
		}
	
		public void destroy() {
			DebuggingService.getInstance().removePortInstance(this);
			super.destroy();
		}
	
		@Override
		public void receive(Message m) {
			if (!(m instanceof EventMessage))
				return;
			EventMessage msg = (EventMessage) m;
			if (0 < msg.getEvtId() && msg.getEvtId() < MSG_MAX) {
				DebuggingService.getInstance().addMessageAsyncIn(getPeerAddress(), getAddress(), messageStrings[msg.getEvtId()]);
				if (msg instanceof EventWithDataMessage)
					getActor().receiveEvent(this, msg.getEvtId(), ((EventWithDataMessage)msg).getData());
				else
					getActor().receiveEvent(this, msg.getEvtId(), null);
			}
	}
	
	
		// sent messages
		public void connected() {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[OUT_connected]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventMessage(getPeerAddress(), OUT_connected));
		}
		public void setPumpFlow(double transitionData) {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[OUT_setPumpFlow]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventWithDataMessage(getPeerAddress(), OUT_setPumpFlow, transitionData));
		}
		public void setWaterTenkFlow(double transitionData) {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[OUT_setWaterTenkFlow]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventWithDataMessage(getPeerAddress(), OUT_setWaterTenkFlow, transitionData));
		}
		public void turnOnPump() {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[OUT_turnOnPump]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventMessage(getPeerAddress(), OUT_turnOnPump));
		}
		public void turnOffPump() {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[OUT_turnOffPump]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventMessage(getPeerAddress(), OUT_turnOffPump));
		}
	}
	
	// replicated port class
	static public class PGuiCommunicationInterfaceReplPort extends ReplicatedPortBase {
	
		public PGuiCommunicationInterfaceReplPort(IInterfaceItemOwner actor, String name, int localId) {
			super(actor, name, localId);
		}
	
		public int getReplication() {
			return getNInterfaceItems();
		}
	
		public int getIndexOf(InterfaceItemBase ifitem){
				return ifitem.getIdx();
		}
	
		public PGuiCommunicationInterfacePort get(int idx) {
			return (PGuiCommunicationInterfacePort) getInterfaceItem(idx);
		}
	
		protected InterfaceItemBase createInterfaceItem(IInterfaceItemOwner rcv, String name, int lid, int idx) {
			return new PGuiCommunicationInterfacePort(rcv, name, lid, idx);
		}
	
		// outgoing messages
		public void connected(){
			for (InterfaceItemBase item : getItems()) {
				((PGuiCommunicationInterfacePort)item).connected();
			}
		}
		public void setPumpFlow(double transitionData){
			for (InterfaceItemBase item : getItems()) {
				((PGuiCommunicationInterfacePort)item).setPumpFlow( transitionData);
			}
		}
		public void setWaterTenkFlow(double transitionData){
			for (InterfaceItemBase item : getItems()) {
				((PGuiCommunicationInterfacePort)item).setWaterTenkFlow( transitionData);
			}
		}
		public void turnOnPump(){
			for (InterfaceItemBase item : getItems()) {
				((PGuiCommunicationInterfacePort)item).turnOnPump();
			}
		}
		public void turnOffPump(){
			for (InterfaceItemBase item : getItems()) {
				((PGuiCommunicationInterfacePort)item).turnOffPump();
			}
		}
	}
	
	
	// port class
	static public class PGuiCommunicationInterfaceConjPort extends PortBase {
		// constructors
		public PGuiCommunicationInterfaceConjPort(IInterfaceItemOwner actor, String name, int localId) {
			this(actor, name, localId, 0);
		}
		public PGuiCommunicationInterfaceConjPort(IInterfaceItemOwner actor, String name, int localId, int idx) {
			super(actor, name, localId, idx);
			DebuggingService.getInstance().addPortInstance(this);
		}
	
		public void destroy() {
			DebuggingService.getInstance().removePortInstance(this);
			super.destroy();
		}
	
		@Override
		public void receive(Message m) {
			if (!(m instanceof EventMessage))
				return;
			EventMessage msg = (EventMessage) m;
			if (0 < msg.getEvtId() && msg.getEvtId() < MSG_MAX) {
				DebuggingService.getInstance().addMessageAsyncIn(getPeerAddress(), getAddress(), messageStrings[msg.getEvtId()]);
				if (msg instanceof EventWithDataMessage)
					getActor().receiveEvent(this, msg.getEvtId(), ((EventWithDataMessage)msg).getData());
				else
					getActor().receiveEvent(this, msg.getEvtId(), null);
			}
	}
	
	
		// sent messages
		public void connect(int transitionData) {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_connect]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventWithDataMessage(getPeerAddress(), IN_connect, transitionData));
		}
		public void setPumpState(boolean transitionData) {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_setPumpState]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventWithDataMessage(getPeerAddress(), IN_setPumpState, transitionData));
		}
		public void setPumpFlow(double transitionData) {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_setPumpFlow]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventWithDataMessage(getPeerAddress(), IN_setPumpFlow, transitionData));
		}
		public void setAlarm(String transitionData) {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_setAlarm]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventWithDataMessage(getPeerAddress(), IN_setAlarm, transitionData));
		}
		public void setWaterLevel(double transitionData) {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_setWaterLevel]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventWithDataMessage(getPeerAddress(), IN_setWaterLevel, transitionData));
		}
		public void setMethaneLevel(boolean transitionData) {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_setMethaneLevel]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventWithDataMessage(getPeerAddress(), IN_setMethaneLevel, transitionData));
		}
		public void setCarbonLevel(boolean transitionData) {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_setCarbonLevel]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventWithDataMessage(getPeerAddress(), IN_setCarbonLevel, transitionData));
		}
		public void setAirFlowLevel(boolean transitionData) {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_setAirFlowLevel]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventWithDataMessage(getPeerAddress(), IN_setAirFlowLevel, transitionData));
		}
		public void setHighWaterLevel(boolean transitionData) {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_setHighWaterLevel]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventWithDataMessage(getPeerAddress(), IN_setHighWaterLevel, transitionData));
		}
		public void setLowWaterLevel(boolean transitionData) {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_setLowWaterLevel]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventWithDataMessage(getPeerAddress(), IN_setLowWaterLevel, transitionData));
		}
	}
	
	// replicated port class
	static public class PGuiCommunicationInterfaceConjReplPort extends ReplicatedPortBase {
	
		public PGuiCommunicationInterfaceConjReplPort(IInterfaceItemOwner actor, String name, int localId) {
			super(actor, name, localId);
		}
	
		public int getReplication() {
			return getNInterfaceItems();
		}
	
		public int getIndexOf(InterfaceItemBase ifitem){
				return ifitem.getIdx();
		}
	
		public PGuiCommunicationInterfaceConjPort get(int idx) {
			return (PGuiCommunicationInterfaceConjPort) getInterfaceItem(idx);
		}
	
		protected InterfaceItemBase createInterfaceItem(IInterfaceItemOwner rcv, String name, int lid, int idx) {
			return new PGuiCommunicationInterfaceConjPort(rcv, name, lid, idx);
		}
	
		// incoming messages
		public void connect(int transitionData){
			for (InterfaceItemBase item : getItems()) {
				((PGuiCommunicationInterfaceConjPort)item).connect( transitionData);
			}
		}
		public void setPumpState(boolean transitionData){
			for (InterfaceItemBase item : getItems()) {
				((PGuiCommunicationInterfaceConjPort)item).setPumpState( transitionData);
			}
		}
		public void setPumpFlow(double transitionData){
			for (InterfaceItemBase item : getItems()) {
				((PGuiCommunicationInterfaceConjPort)item).setPumpFlow( transitionData);
			}
		}
		public void setAlarm(String transitionData){
			for (InterfaceItemBase item : getItems()) {
				((PGuiCommunicationInterfaceConjPort)item).setAlarm( transitionData);
			}
		}
		public void setWaterLevel(double transitionData){
			for (InterfaceItemBase item : getItems()) {
				((PGuiCommunicationInterfaceConjPort)item).setWaterLevel( transitionData);
			}
		}
		public void setMethaneLevel(boolean transitionData){
			for (InterfaceItemBase item : getItems()) {
				((PGuiCommunicationInterfaceConjPort)item).setMethaneLevel( transitionData);
			}
		}
		public void setCarbonLevel(boolean transitionData){
			for (InterfaceItemBase item : getItems()) {
				((PGuiCommunicationInterfaceConjPort)item).setCarbonLevel( transitionData);
			}
		}
		public void setAirFlowLevel(boolean transitionData){
			for (InterfaceItemBase item : getItems()) {
				((PGuiCommunicationInterfaceConjPort)item).setAirFlowLevel( transitionData);
			}
		}
		public void setHighWaterLevel(boolean transitionData){
			for (InterfaceItemBase item : getItems()) {
				((PGuiCommunicationInterfaceConjPort)item).setHighWaterLevel( transitionData);
			}
		}
		public void setLowWaterLevel(boolean transitionData){
			for (InterfaceItemBase item : getItems()) {
				((PGuiCommunicationInterfaceConjPort)item).setLowWaterLevel( transitionData);
			}
		}
	}
	
}
