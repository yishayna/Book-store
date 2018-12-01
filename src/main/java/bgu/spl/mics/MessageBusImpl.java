package bgu.spl.mics;

import javafx.util.Pair;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	private static MessageBusImpl instance = null;
	private ConcurrentHashMap <MicroService, Pair<Vector<Class<? extends Broadcast>> ,Vector<Class<? extends Event>>>> serviceQueue;
	private ConcurrentHashMap <MicroService, LinkedBlockingQueue<Message>>messagesQueue;
	private ConcurrentHashMap <Event,Future> future;

	private MessageBusImpl(){
		this.serviceQueue=new ConcurrentHashMap<>();
		this.future=new ConcurrentHashMap<>();
	}

	public static MessageBusImpl getInstance() {
		if(instance == null) {
			instance = new MessageBusImpl();
		}
		return instance;
	}


	@Override	//Done
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		serviceQueue.get(m).getValue().add(type);
	}

	@Override	//Done
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		serviceQueue.get(m).getKey().add(type);
	}

	@Override	// TO DO: we need to notify the Message-Bus that the event was handled
	public <T> void complete(Event<T> e, T result) {
		future.get(e).resolve(result);

	}

	@Override	//Done
	public void sendBroadcast(Broadcast b) {
		//send the message to all other subscribers of this broadcast
		for(Map.Entry<MicroService, Pair<Vector<Class<? extends Broadcast>> ,Vector<Class<? extends Event>>>> p	:serviceQueue.entrySet()){
			if (p.getValue().getKey().contains(b.getClass())){  //the micro-service p.getKey() subscribing this type
				messagesQueue.get(p.getValue()).add((b));
			}
		}
	}


	@Override
	public <T> Future<T> sendEvent(Event<T> e) {

		return null;
	}

	@Override	//Done
	public void register(MicroService m) {
		Vector<Class<? extends Event>> events=new Vector<>();
		Vector<Class<? extends Broadcast>> broadcasts =new Vector<>();
		Pair<Vector<Class<? extends Broadcast>>,Vector<Class<? extends Event>> > pair=new Pair<>(broadcasts,events);
		serviceQueue.put(m, pair);
	}

	@Override	//Done
	public void unregister(MicroService m) {
		serviceQueue.remove(m);
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	public LinkedBlockingQueue<Message> getMessageQueue(MicroService m){
		return messagesQueue.get(m);
	}

}
