package Controller;

import Controller.Command.*;
import Controller.Network.Client;
import Controller.Observer.*;
import Controller.Observer.Observer;
import Controller.Save.SaveUser;
import Model.Location.Coordinate;
import Model.Location.Location;
import Model.World;
import View.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ClientMediator implements GameMediator {
	private World world;
	private Client client = null;
	private String userName = null;
	
	private ArrayList<Observer> observers  = new ArrayList<Observer>();
	private ArrayList<String> queue = new ArrayList<String>(); //User actions waiting to be send to the sever.
	
	
	private IndexView indexView= null;
	private View view = null;
	private LocationView locationView = null;
    private BagView bagView = null;
    private ChatView chatView = null;
    private EntityView entityView = null;
    private ItemView itemView = null;
    private NPCView npsView = null;
    private TransactionView tansactionView = null;
    
	private Stage primaryStage = null;
	
	private LocationController locationController =null;
	private ItemController itemController= null;
	private CommunicationController communicationController= null;
	private UserController userController= null;
	private SaveUser saveUser = null;
	
	
	private Observer locationObserver = null;
	private Observer itemObserver= null;
	private Observer communicationObserver= null;
	private Observer entityObserver = null;
	
	private MoveCommand moveCommand = null;   
	private PickUpCommand pickUpCommand = null;
	private PutDownCommand putDownCommand = null;
	private EatCommand eatCommand = null;
	private BuyCommand buyCommand = null;
	private SellCommand sellCommand = null;
	private CommunicationCommand communicationCommand = null;
	private PostCommand postCommand = null;
	private StartGameCommand startGameCommand = null;
	private SaveGameCommand saveGameCommand = null;


	
	public ClientMediator() {
		this.world = new World();
	}

	public void createClient(ClientMediator clientMediator) throws Exception{
		client = new Client(clientMediator);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public World getWorld() {
		return world;
	}

	/**
	 * If the world is changed, the observers will be notified to update views.
	 * If the user have not been created, the view won't be updated.
	 * @param newWorld the world received from the server 
	 */
	public void setWorld(World newWorld) {
		if(newWorld.getEntityLocation(userName)!=null){
			if(!this.world.equals(newWorld)) {
				this.world = newWorld;
				this.notifyObservers();
			}
			
		}else{
			this.world = newWorld;
		}


	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Tell all observers to update views.
	 */
	private void notifyObservers() {
		// TODO Auto-generated method stub
		/*for(Observer observer: observers) {
			observer.update();
		}*/
		
		locationObserver.update();
		itemObserver.update();
	}

	public IndexView getIndexView() {
		return indexView;
	}

	public void setIndexView(IndexView indexView) {
		this.indexView = indexView;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}
	
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	
	public LocationView getLocationView() {
		return locationView;
	}

	public void setLocationView(LocationView location) {
		this.locationView = location;
	}

	public BagView getBagView() {
		return bagView;
	}

	public void setBagView(BagView bag) {
		this.bagView = bag;
	}

	public ChatView getChatView() {
		return chatView;
	}

	public void setChatView(ChatView chat) {
		this.chatView = chat;
	}

	public EntityView getEntityView() {
		return entityView;
	}

	public void setEntityView(EntityView entity) {
		this.entityView = entity;
	}

	public ItemView getItemView() {
		return itemView;
	}

	public void setItemView(ItemView item) {
		this.itemView = item;
	}

	public NPCView getNPCView() {
		return npsView;
	}

	public void setNPCView(NPCView nps) {
		this.npsView = nps;
	}

	public TransactionView getTransactionView() {
		return tansactionView;
	}

	public void setTransactionView(TransactionView tansaction) {
		this.tansactionView = tansaction;
	}


	public NPCView getNpsView() {
		return npsView;
	}

	public void setNpsView(NPCView npsView) {
		this.npsView = npsView;
	}

	public TransactionView getTansactionView() {
		return tansactionView;
	}

	public void setTansactionView(TransactionView tansactionView) {
		this.tansactionView = tansactionView;
	}

	public LocationController getLocationController() {
		return locationController;
	}

	public void setLocationController(LocationController locationController) {
		this.locationController = locationController;
	}

	public ItemController getItemController() {
		return itemController;
	}

	public void setItemController(ItemController itemController) {
		this.itemController = itemController;
	}

	public CommunicationController getCommunicationController() {
		return communicationController;
	}

	public void setCommunicationController(CommunicationController communicationController) {
		this.communicationController = communicationController;
	}

	public UserController getUserController() {
		return userController;
	}

	public void setUserController(UserController userController) {
		this.userController = userController;
	}

	public Observer getLocationObserver() {
		return locationObserver;
	}

	public void setLocationObserver(Observer locationObserver) {
		this.locationObserver = locationObserver;
	}

	public Observer getItemObserver() {
		return itemObserver;
	}

	public void setItemObserver(Observer itemObserver) {
		this.itemObserver = itemObserver;
	}

	public Observer getCommunicationObserverr() {
		return communicationObserver;
	}

	public void setCommunicationObserverr(Observer communicationObserverr) {
		this.communicationObserver = communicationObserverr;
	}

	public MoveCommand getMoveCommand() {
		return moveCommand;
	}

	public void setMoveCommand(MoveCommand moveCommand) {
		this.moveCommand = moveCommand;
	}

	public PickUpCommand getPickUpCommand() {
		return pickUpCommand;
	}

	public void setPickUpCommand(PickUpCommand pickUpCommand) {
		this.pickUpCommand = pickUpCommand;
	}

	public PutDownCommand getPutDownCommand() {
		return putDownCommand;
	}

	public void setPutDownCommand(PutDownCommand putDownCommand) {
		this.putDownCommand = putDownCommand;
	}

	public EatCommand getEatCommand() {
		return eatCommand;
	}

	public void setEatCommand(EatCommand eatCommand) {
		this.eatCommand = eatCommand;
	}

	public BuyCommand getBuyCommand() {
		return buyCommand;
	}

	public void setBuyCommand(BuyCommand buyCommand) {
		this.buyCommand = buyCommand;
	}

	public SellCommand getSellCommand() {
		return sellCommand;
	}

	public void setSellCommand(SellCommand sellCommand) {
		this.sellCommand = sellCommand;
	}

	public CommunicationCommand getCommunicationCommand() {
		return communicationCommand;
	}

	public void setCommunicationCommand(CommunicationCommand communicationCommand) {
		this.communicationCommand = communicationCommand;
	}

	public PostCommand getPostCommand() {
		return postCommand;
	}

	public void setPostCommand(PostCommand postCommand) {
		this.postCommand = postCommand;
	}

	public StartGameCommand getStartGameCommand() {
		return startGameCommand;
	}

	public void setStartGameCommand(StartGameCommand startGameCommand) {
		this.startGameCommand = startGameCommand;
	}



	public SaveGameCommand getSaveGameCommand() {
		return saveGameCommand;
	}

	public void setSaveGameCommand(SaveGameCommand saveGameCommand) {
		this.saveGameCommand = saveGameCommand;
	}

	
	
	public ArrayList<String> getQueue() {
		return queue;
	}

	/**
	 * Add the user's action to the queue.
	 * @param action the user intended action (may plus the item ID).
	 */
	public void addAction(String action) {
		this.queue.add(action);
	}
	
	/**
	 * Clean the actions stored in the queue.
	 */
	public void cleanQueue() {
		this.queue.clear();
	}

	/**
	 * Create the instances of controllers, observers and commands.
	 * The controller is the parameter of the Command's constructor.
	 */
	public void initialController() {
		this.locationController = new LocationController(this);
		this.itemController = new ItemController(this);
		this.communicationController = new CommunicationController(this);
		this.userController = new UserController(this);
		this.saveUser = new SaveUser(this);
		
		this.locationObserver = new LocationObserver(this);
		this.itemObserver = new ItemObserver(this);
		this.communicationObserver = new CommunicationObserver(this);
		this.entityObserver = new EntityObserver(this);
		
		observers.add(this.locationObserver);
		observers.add(this.itemObserver);
		observers.add(this.communicationObserver);
		observers.add(this.entityObserver);
		
		
		this.moveCommand = new MoveCommand(locationController);
		this.startGameCommand = new StartGameCommand(userController);
		this.putDownCommand = new PutDownCommand(itemController);
		this.eatCommand = new EatCommand(itemController);
		this.pickUpCommand = new PickUpCommand(itemController);
		this.communicationCommand = new CommunicationCommand(communicationController);
		this.buyCommand = new BuyCommand(itemController);
		this.sellCommand = new SellCommand(itemController);
		this.postCommand = new PostCommand(communicationController);
		this.saveGameCommand = new SaveGameCommand(this.saveUser);
	}
	
	/**
	 * bind the Command to the index view
	 */
	public void bindIndexCommand() {
		indexView.setStartGame(startGameCommand);
	}
	
	
	/**
	 * Initial the view related to the game.
	 */
	public void initialGameView() {
		locationView = new LocationView(this.view);
    	bagView = new BagView(this.view);
    	chatView = new ChatView(this.view);
    	entityView = new EntityView(this.view);
    	itemView = new ItemView(this.view);
    	npsView = new NPCView(this.view);
    	tansactionView = new TransactionView(this.view);

	}
	
	/**
	 * Bind the commands to the views (related to the game).
	 */
	public void bindViewCommand() {
		view.setMoveCommand(moveCommand);
		view.setSaveGameCommand(saveGameCommand);
		itemView.setPickUpCommand(pickUpCommand);
		entityView.setCommunicationCommand(communicationCommand);
		bagView.setEatCommand(eatCommand);
		bagView.setPutDownCommand(putDownCommand);
		chatView.setPostCommand(postCommand);
		tansactionView.setBuyCommand(buyCommand);
		tansactionView.setSellCommand(sellCommand);
	}
	
	/**
	 * Show the game interface.
	 * @throws IOException
	 */
	
	public void	enterGame() throws IOException {
		URL location = View.class.getResource("/view/sample.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        this.primaryStage.setScene(new Scene(root, 900, 720));
        this.view = (View) fxmlLoader.getController();
        //Used for get the screen size and set to canvas.
        this.view.bindScene(this.primaryStage.getScene());
        this.view.setWindowsCloseAction(this.primaryStage);
        this.primaryStage.show();
        
        initialGameView();
        bindViewCommand();
        setTestData();
	}

	

	/**
	 * only for testing!
	 */
	public void setTestData() {
		Location l1 = new Location("location1");
		this.getWorld().addLocation(l1);

		
		Map<String,Coordinate> tmp = new HashMap<String,Coordinate>();
		int num=0;
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				Coordinate tmp_cor = new Coordinate(i, j);
				if(j%2 == 0 && i%2 == 0) {
					tmp.put("water"+num, tmp_cor);
				}else {
					tmp.put("grass"+num, tmp_cor);
				}
				
				num++;
			}
		}
		System.out.println("tiles size"+ tmp.size());
		locationView.update(tmp);
		
	}
	

}
