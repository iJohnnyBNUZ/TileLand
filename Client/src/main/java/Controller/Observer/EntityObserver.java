package Controller.Observer;

import java.util.HashMap;
import java.util.Map;

import Controller.ClientMediator;
import Model.Entity.User;
import Model.Location.Coordinate;
import Model.Location.Location;
import View.EntityView;

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
		for(String entity: curLocation.getEntities().keySet()) {
			if(entity.contains("user")) {
				users.put(entity, curLocation.getEntities().get(entity));
			}else if(entity.contains("npc")) {
				npcs.put(entity, curLocation.getEntities().get(entity));
			}else if(entity.contains("store")){
				stores.put(entity, curLocation.getEntities().get(entity));
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