package Controller.Observer;

import java.util.HashMap;
import java.util.Map;

import Controller.ClientMediator;
import Model.Location.Coordinate;
import Model.Location.Location;

public class ItemObserver implements Observer {

	private ClientMediator clientMediator;

	public ItemObserver(ClientMediator clientMediator) {
		this.clientMediator = clientMediator;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		String uId = clientMediator.getUserName();
		Location curLocation = clientMediator.getWorld().getEntityLocation(uId);
		Map<String, Coordinate> items = new HashMap<String,Coordinate>();

		for(Coordinate cor: curLocation.getItems().keySet()) {
			items.put(curLocation.getItems().get(cor).getItemID(), cor);
		}
		
		clientMediator.getItemView().update(items);

	}
}
