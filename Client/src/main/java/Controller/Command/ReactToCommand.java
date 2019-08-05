package Controller.Command;

import Controller.ClientMediator;
import Controller.Controller;
import Controller.ReactToController;
import Network.Events.ReactToEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReactToCommand implements Command {
	//private CommunicationController communicationController = null;
	private ClientMediator clientMediator = null;
	private ReactToController reactToController = null;
	
	public ReactToCommand(Controller reactToController, ClientMediator clientMediator) {
		this.reactToController = (ReactToController) reactToController;
		this.clientMediator = clientMediator;
	}

	//There are something wrong since the communication between two user and between user and shop will only
	//open the transaction view, the model won't changed, but the interaction between user and npc will change the user energy,
	//this action need to be added to the queue.
	public void execute() {
		String userID = clientMediator.getUserName();
		String id = reactToController.communicateWith(userID);
		if(id != null){
			clientMediator.setReactTo(id);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String message = reactToController.reactToEntity(id,userID);
			String result = "You just interacted with " + id + ", so you " + message + " at " + df.format(new Date()).toString();
			clientMediator.setReactResult(result);
			clientMediator.getEventQueue().add(new ReactToEvent(id,userID));
			//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//clientMediator.getView().showAlert("You just interacted with " + id + ", so you " + message + " at " + df.format(new Date()).toString());
		}
	}
}
