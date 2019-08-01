package Controller.Observer;

import Controller.ClientMediator;
import Controller.ReactToController;
import Model.Entity.Entity;
import Model.Entity.NPC;
import Model.Entity.Shop;
import Model.Entity.User;
import Model.Location.Coordinate;
import Model.Location.Location;
import Utils.Observer;
import javafx.concurrent.Task;

import java.util.Map;

public class ReactToObserver implements Observer {

    private ClientMediator clientMediator;
    private String commuWith;
    private String userID;

    public ReactToObserver(ClientMediator clientMediator){
        this.clientMediator = clientMediator;
    }

    public void update(){

        Task<Void> progressTask = new Task<Void>(){

            @Override
            protected Void call() throws Exception {
                userID = clientMediator.getUserName();
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                if(clientMediator.getReactTo() != null){
                    Entity entity = null;
                    Location currLocation = clientMediator.getWorld().getEntityLocation(userID);
                    for(Map.Entry<Entity, Coordinate> entry : currLocation.getEntities().entrySet()){
                        if(entry.getKey().getEntityID().equals(clientMediator.getReactTo())){
                            entity = entry.getKey();
                            break;
                        }
                    }

                    if(entity != null){
                        if(entity instanceof NPC){
                            String result = ((NPC)entity).reactTo(clientMediator.getWorld().getEntity(userID));
                            String message = "You are communicate with "+ commuWith + ", and you " + result ;
                            clientMediator.getNPCView().updateNpcView(message);
                        }
                        else if(entity instanceof User || entity instanceof Shop){
                            clientMediator.getTransactionView().updateTransaction(entity.getBag(),clientMediator.getReactTo(),clientMediator.getWorld().getEntity(userID).getBag(),clientMediator.getWorld().getEntity(userID).getCoin());
                        }

                    }

                }
            }

        };

        new Thread(progressTask).start();
    }
}
