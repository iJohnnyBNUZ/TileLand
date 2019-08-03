package Controller;

import Controller.Command.*;
import Controller.Network.ClientListener;
import Controller.Network.ClientUpdater;
import Controller.Observer.*;
import Model.Entity.Entity;
import Model.Location.Coordinate;
import Model.Location.Location;
import Model.World;
import Network.Events.Event;
import Utils.Observer;
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
	private World world = new World();
	private ClientUpdater clientUpdater;
	private ClientListener clientListener;
	private String userName = null;
	private String reactTo = null;
	private String reactResult = null;

	private Boolean isInit = Boolean.FALSE;

	private Set<Observer> observerSet = new HashSet<>();
	private LinkedList<Event> eventQueue = new LinkedList<>(); //User actions waiting to be send to the sever.
	
	
	private IndexView indexView= null;
	private View view = null;
	private LocationView locationView = null;
    private BagView bagView = null;
    private PostView postView = null;
    private EntityView entityView = null;
    private ItemView itemView = null;
    private NPCView npsView = null;
    private TransactionView transactionView = null;
    
	private Stage primaryStage = null;

	private LocationController locationController =null;
	private ItemController itemController= null;
	//private CommunicationController communicationController= null;
	private UserController userController= null;
	private TransactionMessageController transactionMessageController = null;
	private PostController postController = null;
	private ReactToController reactToController = null;
	private ReactToNpcController reactToNpcController = null;
	
	private LocationObserver locationObserver = null;
	private ItemObserver itemObserver= null;
	private PostObserver postObserver = null;
	private EntityObserver entityObserver = null;
	private BagObserver bagObserver = null;
	private ReactToObserver reactToObserver = null;
	
	private MoveCommand moveCommand = null;   
	private PickUpCommand pickUpCommand = null;
	private PutDownCommand putDownCommand = null;
	private EatCommand eatCommand = null;
	private TransactionCommand transactionCommand = null;
	//private BuyCommand buyCommand = null;
	//private SellCommand sellCommand = null;
	private CloseReactToCommand closeReactToCommand = null;
	private ReactToCommand reactToCommand = null;
	private PostCommand postCommand = null;
	private StartGameCommand startGameCommand = null;
	private SaveGameCommand saveGameCommand = null;
	private LogOutCommand logOutCommand = null;
	private OpenDoorCommand openDoorCommand = null;

	private boolean haveObservers = false;

	public ClientMediator() {
		this.world = new World();
	}

	public void createClient() throws Exception{
		clientUpdater = new ClientUpdater(this);
	}

	public ClientUpdater getClientUpdater() {
		return clientUpdater;
	}

	public void setClientUpdater(ClientUpdater clientUpdater) {
		this.clientUpdater = clientUpdater;
	}

	public World getWorld() {
		return world;
	}

	public void initWorld(World newWorld){
		this.world.setObserverSet(observerSet);
		for(Location location: this.world.getLocations()){
			location.setObserverSet(observerSet);
		}
		for (Entity entity: this.world.getEntities()){
			entity.setObserverSet(observerSet);
		}
		this.haveObservers=true;
		System.out.println("initial finished!!!!!!!!!!!!!!!!");
	}

	/**
	 * If the world is changed, the observers will be notified to update views.
	 * If the user have not been created, the view won't be updated.
	 * @param newWorld the world received from the server 
	 */
	public void setWorld(World newWorld) {
		if(newWorld.getEntityLocation(userName)!= null){
			System.out.println("one");
			this.world = newWorld;
			initWorld(newWorld);
            this.notifyObservers();
		}else{
			System.out.println("two");
			this.world = newWorld;
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReactTo() {
		return reactTo;
	}

	public void setReactTo(String reactTo) {
		this.reactTo = reactTo;
	}

	public String getReactResult() { return reactResult; }

	public void setReactResult(String reactResult) { this.reactResult = reactResult; }
	/**
	 * Tell all observers to update views.
	 */
	public void notifyObservers() {
		/*for(Observer observer: observers) {
			observer.update();
		}*/
		locationObserver.update();
		itemObserver.update();
		entityObserver.update();
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

	public PostView getPostView() {
		return postView;
	}

	public void setPostView(PostView chat) {
		this.postView = chat;
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

	public NPCView getNpsView() {
		return npsView;
	}

	public void setNpsView(NPCView npsView) {
		this.npsView = npsView;
	}

	public TransactionView getTransactionView() {
		return transactionView;
	}

	public void setTransactionView(TransactionView transactionView) {
		this.transactionView = transactionView;
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

	/*
	public CommunicationController getCommunicationController() {
		return communicationController;
	}

	public void setCommunicationController(CommunicationController communicationController) {
		this.communicationController = communicationController;
	}
	*/

	public PostController getPostController() {
		return postController;
	}

	public void setPostController(PostController postController){
		this.postController = postController;
	}

	public UserController getUserController() {
		return userController;
	}

	public ReactToController getReactToController() {
		return reactToController;
	}

	public void setReactToController(ReactToController reactToController){
		this.reactToController = reactToController;
	}

	public ReactToNpcController getReactToNpcController() {
		return reactToNpcController;
	}

	public void setReactToNpcController(ReactToNpcController reactToNpcController){
		this.reactToNpcController = reactToNpcController;
	}

	public void setUserController(UserController userController) {
		this.userController = userController;
	}

	public LocationObserver getLocationObserver() {
		return locationObserver;
	}

	public void setLocationObserver(LocationObserver locationObserver) {
		this.locationObserver = locationObserver;
	}

	public ItemObserver getItemObserver() {
		return itemObserver;
	}

	public void setItemObserver(ItemObserver itemObserver) {
		this.itemObserver = itemObserver;
	}

	public PostObserver getCommunicationObserverr() {
		return postObserver;
	}

	public void setCommunicationObserverr(PostObserver postObserverr) {
		this.postObserver = postObserverr;
	}

	public ReactToObserver getReactToObserver() {
		return reactToObserver;
	}

	public void setReactToController(ReactToObserver reactToController) {
		this.reactToObserver = reactToObserver;
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

	public TransactionCommand getTransactionCommand() {
		return transactionCommand;
	}

	public void setTransactionCommand(TransactionCommand transactionCommand){
		this.transactionCommand = transactionCommand;
	}

	/*
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
	*/

	public CloseReactToCommand getCloseReactToCommand() {
		return closeReactToCommand;
	}

	public void setCloseReactToCommand(CloseReactToCommand closeReactToCommand){
		this.closeReactToCommand = closeReactToCommand;
	}

	public ReactToCommand getReactToCommand() {
		return reactToCommand;
	}

	public void setReactToCommand(ReactToCommand reactToCommand) {
		this.reactToCommand = reactToCommand;
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

	public OpenDoorCommand getOpenDoorCommand() {
		return openDoorCommand;
	}

	public void setOpenDoorCommand(OpenDoorCommand openDoorCommand) {
		this.openDoorCommand = openDoorCommand;
	}

	public SaveGameCommand getSaveGameCommand() {
		return saveGameCommand;
	}

	public void setSaveGameCommand(SaveGameCommand saveGameCommand) {
		this.saveGameCommand = saveGameCommand;
	}

	public LinkedList<Event> getEventQueue() {
		return eventQueue;
	}

	/**
	 * Create the instances of controllers, observers and commands.
	 * The controller is the parameter of the Command's constructor.
	 */
	public void initialController() {
		this.locationController = new LocationController(this);
		this.itemController = new ItemController(this);
		//this.communicationController = new CommunicationController(this);
		this.userController = new UserController(this);
		this.transactionMessageController = new TransactionMessageController(this);
		this.postController = new PostController(this);
		this.reactToController = new ReactToController(this);
		this.reactToNpcController = new ReactToNpcController(this);

		this.locationObserver = new LocationObserver(this);
		this.itemObserver = new ItemObserver(this);
		this.postObserver = new PostObserver(this);
		this.entityObserver = new EntityObserver(this);
		this.bagObserver =  new BagObserver(this);
		this.reactToObserver = new ReactToObserver(this);
		
		observerSet.add(this.locationObserver);
		observerSet.add(this.itemObserver);
		observerSet.add(this.postObserver);
		observerSet.add(this.entityObserver);
		observerSet.add(this.bagObserver);
		observerSet.add(this.reactToObserver);

		
		this.moveCommand = new MoveCommand(locationController,this);
		this.openDoorCommand = new OpenDoorCommand(locationController,this);
		this.startGameCommand = new StartGameCommand(userController);
		this.putDownCommand = new PutDownCommand(itemController,this);
		this.eatCommand = new EatCommand(itemController,this);
		this.pickUpCommand = new PickUpCommand(itemController,this);
		this.reactToCommand = new ReactToCommand(reactToController,this);
		this.transactionCommand = new TransactionCommand(transactionMessageController,itemController,this);
		//this.buyCommand = new BuyCommand(transactionMessageController,itemController,this);
		//this.sellCommand = new SellCommand(transactionMessageController,itemController,this);
		this.closeReactToCommand = new CloseReactToCommand(this);
		this.postCommand = new PostCommand(postController,this);
		this.saveGameCommand = new SaveGameCommand(this);
		this.logOutCommand = new LogOutCommand(this);

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
    	postView = new PostView(this.view);
    	entityView = new EntityView(this.view);
    	itemView = new ItemView(this.view);
    	npsView = new NPCView(this.view);
    	transactionView = new TransactionView(this.view);

	}
	
	/**
	 * Bind the commands to the views (related to the game).
	 */
	public void bindViewCommand() {
		view.setMoveCommand(moveCommand);
		view.setPickUpCommand(pickUpCommand);
		view.setReactToCommand(reactToCommand);
		view.setOpenDoorCommand(openDoorCommand);
		view.setSaveGameCommand(saveGameCommand);
		view.setLogOutCommand(logOutCommand);
		itemView.setPickUpCommand(pickUpCommand);
		entityView.setCommunicationCommand(reactToCommand);
		bagView.setEatCommand(eatCommand);
		bagView.setPutDownCommand(putDownCommand);
		postView.setPostCommand(postCommand);
		transactionView.setTransactionCommand(transactionCommand);
		//tansactionView.setBuyCommand(buyCommand);
		//tansactionView.setSellCommand(sellCommand);
		transactionView.setCloseReactToCommand(closeReactToCommand);
		npsView.setCloseReactToCommand(closeReactToCommand);
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
        //setTestData();
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
        //locationView.update(tmp);
		
	}
}
