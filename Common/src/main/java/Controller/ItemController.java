package Controller;

import Model.Entity.Entity;
import Model.Item.Food;
import Model.Item.Item;
import Model.Location.Coordinate;
import Model.Location.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemController implements Controller{
    private GameMediator gameMediator;


    public ItemController(GameMediator gameMediator){

        this.gameMediator = gameMediator;
    }

    public void pickUp(String userID, Coordinate coordinate){
        // delete from location
        Location location = this.gameMediator.getWorld().getEntityLocation(userID);
        Item item = null;
        if (location.getItems().get(coordinate) != null){
            item = location.getItems().get(coordinate);
            location.getItems().remove(coordinate);
        }

        // add to user's bag
        Entity entity = this.gameMediator.getWorld().getEntity(userID);
        entity.pickUp(item);
    }

    public void drop(String userID,String itemID){
        // delete from bag
        Entity entity = this.gameMediator.getWorld().getEntity(userID);
        Item item = entity.putDown(itemID);

        //add to location
        Location location = this.gameMediator.getWorld().getEntityLocation(userID);
        Coordinate userCoordinate = location.getEntities().get(userID);
        if (item != null){
            location.addItem(userCoordinate, item);
        }

    }

    /*
    public void exchange(String itemID, String sellerID, String buyerID){
        //delete from seller's bag
        Entity seller = this.gameMediator.getWorld().getEntity(sellerID);
        Item item = seller.putDown(itemID);

        //add to buyer's bag
        Entity buyer = this.gameMediator.getWorld().getEntity(buyerID);
        if (item != null){
            buyer.pickUp(item);
        }
    }
    */

    public void eat(String userID,String itemID){
        Entity user = this.gameMediator.getWorld().getEntity(userID);
        if (user == null)
            return;

        List<Item> items = user.getBag();
        if(items == null)
            return;

        for(Item item : items){
            if(item.getItemID().equals(itemID) && item.getType().equals("food")){
                Food food = (Food)item;
                //add user's energy
                food.use(this.gameMediator.getWorld().getEntity(userID));
                //delete from user's bag
                this.gameMediator.getWorld().getEntity(userID).removeFromBag(food);
                break;
            }
        }

    }

    public void buyItems(String userID,String usershopName,HashMap<String, Integer> buyList, int buyValue){

        //if user has enough money to buy,decrease user's money,current user add item,other user or shop remove item
        this.gameMediator.getWorld().getEntity(userID).decreaseCoin(buyValue);
        this.gameMediator.getWorld().getEntity(usershopName).increaseCoin(buyValue);
        for(Item item:this.gameMediator.getWorld().getEntity(usershopName).getBag()){
            final String itemName = item.getItemID().replaceAll("[0-9]","");
            if(buyList.containsKey(itemName) && buyList.get(itemName)>=1){
                //current user add items
                this.gameMediator.getWorld().getEntity(userID).addToBag(item);
                //other user or shop remove item
                this.gameMediator.getWorld().getEntity(usershopName).removeFromBag(item);
                buyList.put(itemName,buyList.get(itemName)-1);
            }
        }
    }

    public void sellItems(String userID,String usershopName,HashMap<String,Integer> sellList,int sellValue){
        this.gameMediator.getWorld().getEntity(userID).increaseCoin(sellValue);
        this.gameMediator.getWorld().getEntity(usershopName).decreaseCoin(sellValue);
        for(Item item:this.gameMediator.getWorld().getEntity(userID).getBag()){
            final String itemString = item.getItemID().replaceAll("[0-9]","");
            if(sellList.containsKey(itemString) && sellList.get(itemString)>=1){
                this.gameMediator.getWorld().getEntity(usershopName).addToBag(item);
                this.gameMediator.getWorld().getEntity(userID).removeFromBag(item);
                sellList.put(itemString,sellList.get(itemString)-1);
            }
        }
    }
}
