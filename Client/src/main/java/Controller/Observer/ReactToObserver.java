package Controller.Observer;

import Controller.ClientMediator;
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
    private String userID;

    public ReactToObserver(ClientMediator clientMediator){
        this.clientMediator = clientMediator;
    }


    /**
     * If the user is interacting with someone, update the view according to the entity's type
     * If the user is interacting with NPC, get the result of the interaction and update the npcView
     * If the user is interacting other users or shops, get the entity's bag and name, and the user's bag and money to updater the transactionView
     */
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
                            String result = clientMediator.getReactResult();
                            clientMediator.getNPCView().updateNpcView(result);
                        }
                        else if(entity instanceof User || entity instanceof Shop){
                            //String result = clientMediator.getReactResult();
                            clientMediator.getTransactionView().updateTransaction(entity.getBag(),clientMediator.getReactTo(),clientMediator.getWorld().getEntity(userID).getBag(),clientMediator.getWorld().getEntity(userID).getCoin());
                        }

                    }

                }
            }

        };

        new Thread(progressTask).start();
    }
}
