package Controller.Observer;

import Controller.ClientMediator;
import Utils.Observer;
import javafx.concurrent.Task;

public class BagObserver implements Observer {

    private ClientMediator clientMediator;
    private String userID;

    public BagObserver(ClientMediator clientMediator){
        this.clientMediator = clientMediator;
    }


    /**
     * Get the items in user's bag and user's money to update the bagView
     */
    public void update(){
        Task<Void> progressTask = new Task<Void>(){

            @Override
            protected Void call() {
                userID = clientMediator.getUserName();
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                clientMediator.getBagView().updateBag(clientMediator.getWorld().getEntity(userID).getBag(),clientMediator.getWorld().getEntity(userID).getCoin());
            }

        };

        new Thread(progressTask).start();

    }

}
