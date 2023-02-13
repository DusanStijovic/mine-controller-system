package AlarmModel;

import org.eclipse.etrice.runtime.java.messaging.Message;
import org.eclipse.etrice.runtime.java.modelbase.EventMessage;
import org.eclipse.etrice.runtime.java.modelbase.EventWithDataMessage;
import org.eclipse.etrice.runtime.java.modelbase.IInterfaceItemOwner;
import org.eclipse.etrice.runtime.java.modelbase.InterfaceItemBase;
import org.eclipse.etrice.runtime.java.modelbase.PortBase;
import org.eclipse.etrice.runtime.java.modelbase.ReplicatedPortBase;
import org.eclipse.etrice.runtime.java.debugging.DebuggingService;
import static org.eclipse.etrice.runtime.java.etunit.EtUnit.*;



public class AlarmSender {
	// message IDs
	public static final int MSG_MIN = 0;
	public static final int IN_alarmHighMethaneLevel = 1;
	public static final int IN_alarmLowAirFlow = 2;
	public static final int IN_alarmHighCarbonMonoxydeLevel = 3;
	public static final int IN_alarmErrorReadingMethane = 4;
	public static final int IN_alarmErrorReadingAirFlow = 5;
	public static final int IN_alarmErrorReadingCarbonMonoxyde = 6;
	public static final int IN_alarmErrorStartingPump = 7;
	public static final int IN_alarmErrorStoppingPump = 8;
	public static final int IN_alarmErrorReadingWaterFlow = 9;
	public static final int MSG_MAX = 10;


	private static String messageStrings[] = {"MIN",  "alarmHighMethaneLevel","alarmLowAirFlow","alarmHighCarbonMonoxydeLevel","alarmErrorReadingMethane","alarmErrorReadingAirFlow","alarmErrorReadingCarbonMonoxyde","alarmErrorStartingPump","alarmErrorStoppingPump","alarmErrorReadingWaterFlow","MAX"};

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
	static public class AlarmSenderPort extends PortBase {
		// constructors
		public AlarmSenderPort(IInterfaceItemOwner actor, String name, int localId) {
			this(actor, name, localId, 0);
		}
		public AlarmSenderPort(IInterfaceItemOwner actor, String name, int localId, int idx) {
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
	}
	
	// replicated port class
	static public class AlarmSenderReplPort extends ReplicatedPortBase {
	
		public AlarmSenderReplPort(IInterfaceItemOwner actor, String name, int localId) {
			super(actor, name, localId);
		}
	
		public int getReplication() {
			return getNInterfaceItems();
		}
	
		public int getIndexOf(InterfaceItemBase ifitem){
				return ifitem.getIdx();
		}
	
		public AlarmSenderPort get(int idx) {
			return (AlarmSenderPort) getInterfaceItem(idx);
		}
	
		protected InterfaceItemBase createInterfaceItem(IInterfaceItemOwner rcv, String name, int lid, int idx) {
			return new AlarmSenderPort(rcv, name, lid, idx);
		}
	
		// outgoing messages
	}
	
	
	// port class
	static public class AlarmSenderConjPort extends PortBase {
		// constructors
		public AlarmSenderConjPort(IInterfaceItemOwner actor, String name, int localId) {
			this(actor, name, localId, 0);
		}
		public AlarmSenderConjPort(IInterfaceItemOwner actor, String name, int localId, int idx) {
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
		public void alarmHighMethaneLevel() {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_alarmHighMethaneLevel]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventMessage(getPeerAddress(), IN_alarmHighMethaneLevel));
		}
		public void alarmLowAirFlow() {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_alarmLowAirFlow]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventMessage(getPeerAddress(), IN_alarmLowAirFlow));
		}
		public void alarmHighCarbonMonoxydeLevel() {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_alarmHighCarbonMonoxydeLevel]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventMessage(getPeerAddress(), IN_alarmHighCarbonMonoxydeLevel));
		}
		public void alarmErrorReadingMethane() {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_alarmErrorReadingMethane]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventMessage(getPeerAddress(), IN_alarmErrorReadingMethane));
		}
		public void alarmErrorReadingAirFlow() {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_alarmErrorReadingAirFlow]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventMessage(getPeerAddress(), IN_alarmErrorReadingAirFlow));
		}
		public void alarmErrorReadingCarbonMonoxyde() {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_alarmErrorReadingCarbonMonoxyde]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventMessage(getPeerAddress(), IN_alarmErrorReadingCarbonMonoxyde));
		}
		public void alarmErrorStartingPump() {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_alarmErrorStartingPump]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventMessage(getPeerAddress(), IN_alarmErrorStartingPump));
		}
		public void alarmErrorStoppingPump() {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_alarmErrorStoppingPump]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventMessage(getPeerAddress(), IN_alarmErrorStoppingPump));
		}
		public void alarmErrorReadingWaterFlow() {
			DebuggingService.getInstance().addMessageAsyncOut(getAddress(), getPeerAddress(), messageStrings[IN_alarmErrorReadingWaterFlow]);
			if (getPeerAddress()!=null)
				getPeerMsgReceiver().receive(new EventMessage(getPeerAddress(), IN_alarmErrorReadingWaterFlow));
		}
	}
	
	// replicated port class
	static public class AlarmSenderConjReplPort extends ReplicatedPortBase {
	
		public AlarmSenderConjReplPort(IInterfaceItemOwner actor, String name, int localId) {
			super(actor, name, localId);
		}
	
		public int getReplication() {
			return getNInterfaceItems();
		}
	
		public int getIndexOf(InterfaceItemBase ifitem){
				return ifitem.getIdx();
		}
	
		public AlarmSenderConjPort get(int idx) {
			return (AlarmSenderConjPort) getInterfaceItem(idx);
		}
	
		protected InterfaceItemBase createInterfaceItem(IInterfaceItemOwner rcv, String name, int lid, int idx) {
			return new AlarmSenderConjPort(rcv, name, lid, idx);
		}
	
		// incoming messages
		public void alarmHighMethaneLevel(){
			for (InterfaceItemBase item : getItems()) {
				((AlarmSenderConjPort)item).alarmHighMethaneLevel();
			}
		}
		public void alarmLowAirFlow(){
			for (InterfaceItemBase item : getItems()) {
				((AlarmSenderConjPort)item).alarmLowAirFlow();
			}
		}
		public void alarmHighCarbonMonoxydeLevel(){
			for (InterfaceItemBase item : getItems()) {
				((AlarmSenderConjPort)item).alarmHighCarbonMonoxydeLevel();
			}
		}
		public void alarmErrorReadingMethane(){
			for (InterfaceItemBase item : getItems()) {
				((AlarmSenderConjPort)item).alarmErrorReadingMethane();
			}
		}
		public void alarmErrorReadingAirFlow(){
			for (InterfaceItemBase item : getItems()) {
				((AlarmSenderConjPort)item).alarmErrorReadingAirFlow();
			}
		}
		public void alarmErrorReadingCarbonMonoxyde(){
			for (InterfaceItemBase item : getItems()) {
				((AlarmSenderConjPort)item).alarmErrorReadingCarbonMonoxyde();
			}
		}
		public void alarmErrorStartingPump(){
			for (InterfaceItemBase item : getItems()) {
				((AlarmSenderConjPort)item).alarmErrorStartingPump();
			}
		}
		public void alarmErrorStoppingPump(){
			for (InterfaceItemBase item : getItems()) {
				((AlarmSenderConjPort)item).alarmErrorStoppingPump();
			}
		}
		public void alarmErrorReadingWaterFlow(){
			for (InterfaceItemBase item : getItems()) {
				((AlarmSenderConjPort)item).alarmErrorReadingWaterFlow();
			}
		}
	}
	
}
