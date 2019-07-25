package Controller.Observer;

import Controller.ClientMediator;
import Model.Entity.Entity;
import Model.Entity.NPC;
import Model.Entity.Shop;
import Model.Entity.User;
import Model.Location.Coordinate;
import Model.Location.Location;
import View.EntityView;

import java.util.HashMap;
import java.util.Map;

public class EntityObserver implements Observer{
	private ClientMediator clientMediator;

	public EntityObserver(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

		String uId = clientMediator.getUserName();
		Location curLocation = clientMediator.getWorld().getEntityLocation(uId);
		
		Map<String,Coordinate> users = new HashMap<String,Coordinate>();
		Map<String,Coordinate> npcs = new HashMap<String,Coordinate>();
		Map<String,Coordinate> stores = new HashMap<String,Coordinate>();
		int energyPoints = clientMediator.getWorld().getEntity(uId).getEnergy();
		int coins = clientMediator.getWorld().getEntity(uId).getCoin();
		System.out.println(curLocation.getEntities().keySet().size());
		for(Entity entity: curLocation.getEntities().keySet()) {
			if(entity instanceof User) {
				users.put(entity.getEntityID(), curLocation.getEntities().get(entity));
			}else if(entity instanceof NPC) {
				npcs.put(entity.getEntityID(), curLocation.getEntities().get(entity));
			}else if(entity instanceof Shop){
				stores.put(entity.getEntityID(), curLocation.getEntities().get(entity));
			}
		}
		
		EntityView entityView = clientMediator.getEntityView();
		entityView.updateCoin(coins);
		entityView.updateEnergy(energyPoints);
		entityView.updateNPC(npcs);
		entityView.updateStore(stores);
		entityView.updateUser(users);

	}
}
