package PoolingSensorModel;

import org.eclipse.etrice.runtime.java.messaging.Message;
import org.eclipse.etrice.runtime.java.modelbase.EventMessage;
import org.eclipse.etrice.runtime.java.modelbase.EventWithDataMessage;
import org.eclipse.etrice.runtime.java.modelbase.IInterfaceItemOwner;
import org.eclipse.etrice.runtime.java.modelbase.InterfaceItemBase;
import org.eclipse.etrice.runtime.java.modelbase.PortBase;
import org.eclipse.etrice.runtime.java.modelbase.ReplicatedPortBase;
import org.eclipse.etrice.runtime.java.debugging.DebuggingService;
import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;



public class PoolingSensorCommands {
	// message IDs
	public static final int MSG_MIN = 0;
	public static final int OUT_startADConverstion = 1;
	public static final int OUT_readStatus = 2;
	public static final int OUT_readValueRegister = 3;
	public static final int OUT_readErrorRegister = 4;
	public static final int IN_sendStatus = 5;
	public static final int IN_sendValueRegister = 6;
	public static final int IN_sendErrorRegister = 7;
	public static final int MSG_MAX = 8;

	/*--------------------- begin user code ---------------------*/
	public enum Status {
		NOT_READY, READY, ERROR
	}
	public static final int MAX_SENSOR_FINISH_TIME_MS = 50;
	
	
	/*--------------------- end user code ---------------------*/

	private static String messageStrings[] = {"MIN", "startADConverstion","readStatus","readValueRegister","readErrorRegister", "sendStatus","sendValueRegister","sendErrorRegister","MAX"};

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
	static public class PoolingSensorCommandsPort extends PortBase {
		// constructors
		public PoolingSensorCommandsPort(IInterfaceItemOwner actor, String name, int localId) {
			this(actor, name, localId, 0);
		}
		public PoolingSensorCommandsPort(IInterfaceItemOwner actor, String name, int localId, int idx) {
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
		public void startADConverstion() {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[OUT_startADConverstion]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventMessage(getPeerAddress(), OUT_startADConverstion));
		}
		public void readStatus() {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[OUT_readStatus]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventMessage(getPeerAddress(), OUT_readStatus));
		}
		public void readValueRegister() {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[OUT_readValueRegister]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventMessage(getPeerAddress(), OUT_readValueRegister));
		}
		public void readErrorRegister() {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[OUT_readErrorRegister]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventMessage(getPeerAddress(), OUT_readErrorRegister));
		}
	}
	
	// replicated port class
	static public class PoolingSensorCommandsReplPort extends ReplicatedPortBase {
	
		public PoolingSensorCommandsReplPort(IInterfaceItemOwner actor, String name, int localId) {
			super(actor, name, localId);
		}
	
		public int getReplication() {
			return getNInterfaceItems();
		}
	
		public int getIndexOf(InterfaceItemBase ifitem){
				return ifitem.getIdx();
		}
	
		public PoolingSensorCommandsPort get(int idx) {
			return (PoolingSensorCommandsPort) getInterfaceItem(idx);
		}
	
		protected InterfaceItemBase createInterfaceItem(IInterfaceItemOwner rcv, String name, int lid, int idx) {
			return new PoolingSensorCommandsPort(rcv, name, lid, idx);
		}
	
		// outgoing messages
		public void startADConverstion(){
			for (InterfaceItemBase item : getItems()) {
				((PoolingSensorCommandsPort)item).startADConverstion();
			}
		}
		public void readStatus(){
			for (InterfaceItemBase item : getItems()) {
				((PoolingSensorCommandsPort)item).readStatus();
			}
		}
		public void readValueRegister(){
			for (InterfaceItemBase item : getItems()) {
				((PoolingSensorCommandsPort)item).readValueRegister();
			}
		}
		public void readErrorRegister(){
			for (InterfaceItemBase item : getItems()) {
				((PoolingSensorCommandsPort)item).readErrorRegister();
			}
		}
	}
	
	
	// port class
	static public class PoolingSensorCommandsConjPort extends PortBase {
		// constructors
		public PoolingSensorCommandsConjPort(IInterfaceItemOwner actor, String name, int localId) {
			this(actor, name, localId, 0);
		}
		public PoolingSensorCommandsConjPort(IInterfaceItemOwner actor, String name, int localId, int idx) {
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
		public void sendStatus(byte transitionData) {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_sendStatus]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventWithDataMessage(getPeerAddress(), IN_sendStatus, transitionData));
		}
		public void sendValueRegister(double transitionData) {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_sendValueRegister]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventWithDataMessage(getPeerAddress(), IN_sendValueRegister, transitionData));
		}
		public void sendErrorRegister(String transitionData) {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_sendErrorRegister]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventWithDataMessage(getPeerAddress(), IN_sendErrorRegister, transitionData));
		}
	}
	
	// replicated port class
	static public class PoolingSensorCommandsConjReplPort extends ReplicatedPortBase {
	
		public PoolingSensorCommandsConjReplPort(IInterfaceItemOwner actor, String name, int localId) {
			super(actor, name, localId);
		}
	
		public int getReplication() {
			return getNInterfaceItems();
		}
	
		public int getIndexOf(InterfaceItemBase ifitem){
				return ifitem.getIdx();
		}
	
		public PoolingSensorCommandsConjPort get(int idx) {
			return (PoolingSensorCommandsConjPort) getInterfaceItem(idx);
		}
	
		protected InterfaceItemBase createInterfaceItem(IInterfaceItemOwner rcv, String name, int lid, int idx) {
			return new PoolingSensorCommandsConjPort(rcv, name, lid, idx);
		}
	
		// incoming messages
		public void sendStatus(byte transitionData){
			for (InterfaceItemBase item : getItems()) {
				((PoolingSensorCommandsConjPort)item).sendStatus( transitionData);
			}
		}
		public void sendValueRegister(double transitionData){
			for (InterfaceItemBase item : getItems()) {
				((PoolingSensorCommandsConjPort)item).sendValueRegister( transitionData);
			}
		}
		public void sendErrorRegister(String transitionData){
			for (InterfaceItemBase item : getItems()) {
				((PoolingSensorCommandsConjPort)item).sendErrorRegister( transitionData);
			}
		}
	}
	
}
