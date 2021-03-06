package Controller.Command;


import Controller.ClientMediator;
import Controller.Controller;
import Controller.LocationController;
import Network.Events.MovementEvent;
import javafx.concurrent.Task;

import java.io.IOException;

public class MoveCommand implements Command {
	private LocationController locationController;
    private ClientMediator clientMediator;

    public MoveCommand(Controller locationController2, ClientMediator clientMediator){
        this.locationController = (LocationController) locationController2;
        this.clientMediator = clientMediator;
    }

    /**
     *  Invoke the moveTo methods in the locationController.
     *  Add the LocationEvent to the queue if this action is processed successfully.
     * @param direction the direction the user want to move.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void excute(String direction) throws IOException, ClassNotFoundException {
        Task<Void> progressTask = new Task<Void>(){
            String message = null;
            @Override
            protected Void call() throws Exception {
                // run in the current thread.
                String uName = clientMediator.getUserName();
                message = locationController.moveTo(uName, direction.toLowerCase());
                return null;
            }

            @Override
            protected void succeeded() {
                // run in the JavaFx thread.
                if(message!=null){
                    clientMediator.getView().showAlert(message,null);
                }else{
                    clientMediator.getEventQueue().add(new MovementEvent(clientMediator.getUserName(), direction));
                }
            }

        };

        new Thread(progressTask).start();
    }

}
