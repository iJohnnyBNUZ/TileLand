package GUI;

import Controller.Command.EatCommand;
import Controller.Command.PutDownCommand;
import Model.Item.Food;
import Model.Item.Item;
import Model.Item.Key;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class BagView{

	private Label numOfCoins;
	private TabPane tabBagView;
	private VBox bagFoodVbox;
	private VBox bagKeysVbox;
	private Button eatFood;
	private Button putDownFood;
	private Button closeFood;
	private Button putDownKey;
	private Button closeKey;
	private Button closeCoins;

	private EatCommand eatCommand;
	private PutDownCommand putDownCommand;
	private String selectedItemId;

	private double image_h = 50.0;
	private double image_w = 50.0;
	private List<GridPane> gridPaneFoodList = new LinkedList<GridPane>();
	private List<GridPane> gridPaneKeyList = new LinkedList<GridPane>();

	public BagView(View view) {
		this.numOfCoins = view.getNumOfCoins();
		this.tabBagView = view.getTabBagView();
		this.eatFood = view.getEatFood();
		this.putDownFood = view.getPutDownFood();
		this.closeFood = view.getCloseFood();
		this.bagFoodVbox = view.getBagFoodVbox();
		this.putDownKey = view.getPutDownKey();
		this.closeKey= view.getCloseKey();
		this.bagKeysVbox = view.getBagKeysVbox();
		this.closeCoins = view.getCloseCoins();

		closeFood.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				tabBagView.setVisible(false);
			}
		});

		closeKey.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				tabBagView.setVisible(false);
			}
		});

		closeCoins.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				tabBagView.setVisible(false);
			}
		});

	}


	public void setEatCommand(EatCommand eatCommand){
		this.eatCommand = eatCommand;
	}

	public void setPutDownCommand(PutDownCommand putDownCommand){
		this.putDownCommand = putDownCommand;
	}


	/**
	 * Show the view of user's bag to the user
	 * @param bag A list that contains all the items in user's bag
	 * @param money User's money
	 */
	public void updateBag(List<Item> bag,int money) {
		bagFoodVbox.getChildren().clear();
		bagKeysVbox.getChildren().clear();
		numOfCoins.setText(String.valueOf(money));

		/* get List<String> foodItems of food and keyItems of key */
		List<String> foodItems = new LinkedList<String>();
		List<String> keyItems = new LinkedList<String>();
		for (Item item : bag) {
			if (item instanceof Food) {
				String foodString = item.getItemID().replaceAll("[0-9]", "");
				foodItems.add(foodString);
			} else if (item instanceof Key) {
				String keyString = item.getItemID().replaceAll("[0-9]", "");
				keyItems.add(keyString.toString());
			}
		}

		/* preparation for displaysing */
		final DropShadow effect = new DropShadow();

		/* display food in the bag */
		if (foodItems != null) {
			Set<String> uniqueFoodSet = new HashSet<String>(foodItems);
			for (final String tmp_food_name : uniqueFoodSet) {

				final ImageView food_item_img = new ImageView();
				URL url = this.getClass().getResource("/images/" + tmp_food_name + ".png");
				final Image food_image = new Image(url.toString(), image_h, image_w, false, false);
				food_item_img.setImage(food_image);
				final Label numOfFood = new Label();
				numOfFood.setText(String.valueOf(Collections.frequency(foodItems, tmp_food_name)));
				numOfFood.setPrefWidth(80);
				numOfFood.setAlignment(Pos.CENTER);
				final Label energy = new Label();
				for (Item item : bag) {
					final String foodString = item.getItemID().replaceAll("[0-9]", "");
					if (foodString.toString().equals(tmp_food_name)) {
						final Food food = (Food) item;
						energy.setText(String.valueOf(food.getEnergy()));
						break;
					}
				}
				energy.setPrefWidth(100);
				energy.setAlignment(Pos.CENTER);
				final GridPane foodBox = new GridPane();
				foodBox.add(food_item_img, 0, 0);
				foodBox.add(numOfFood, 1, 0);
				foodBox.add(energy, 2, 0);
				bagFoodVbox.getChildren().add(foodBox);
				gridPaneFoodList.add(foodBox);
				food_item_img.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						if (food_item_img.getEffect() == effect) {
							food_item_img.setEffect(null);
							selectedItemId = null;
						} else {
							cleanSelect(gridPaneFoodList, 0);
							food_item_img.setEffect(effect);
							//set selectedItemId
							for (Item item : bag) {
								String foodString = item.getItemID().replaceAll("[0-9]", "");
								if (foodString.toString().equals(tmp_food_name)) {
									selectedItemId = item.getItemID();
									break;
								}
							}
						}
					}
				});

			}
		}


		/* display keys in the bag */
		if (keyItems != null) {
			Set<String> uniqueKeySet = new HashSet<String>(keyItems);
			for (final String tmp_key_name : uniqueKeySet) {

				final Label blank = new Label();
				blank.setPrefWidth(25);
				final ImageView key_item_img = new ImageView();
				URL url = this.getClass().getResource("/images/" + tmp_key_name + ".png");
				final Image key_image = new Image(url.toString(), image_h, image_w, false, false);
				key_item_img.setImage(key_image);
				final Label numOfKey = new Label();
				numOfKey.setText(String.valueOf(Collections.frequency(keyItems, tmp_key_name)));
				numOfKey.setPrefWidth(180);
				numOfKey.setAlignment(Pos.CENTER);

				final GridPane keyBox = new GridPane();
				keyBox.add(blank, 0, 0);
				keyBox.add(key_item_img, 1, 0);
				keyBox.add(numOfKey, 2, 0);
				bagKeysVbox.getChildren().add(keyBox);
				gridPaneKeyList.add(keyBox);
				key_item_img.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						if (key_item_img.getEffect() == effect) {
							key_item_img.setEffect(null);
							selectedItemId = null;
						} else {
							cleanSelect(gridPaneKeyList, 1);
							key_item_img.setEffect(effect);
							//set selectedItemId
							for (Item item : bag) {
								String keyString = item.getItemID().replaceAll("[0-9]", "");
								if (keyString.toString().equals(tmp_key_name)) {
									selectedItemId = item.getItemID();
									break;
								}
							}
						}
					}
				});


			}
		}

		eatFood.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				eatCommand.execute(selectedItemId);
			}
		});

		putDownFood.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				putDownCommand.execute(selectedItemId);
			}
		});

		putDownKey.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				putDownCommand.execute(selectedItemId);
			}
		});

	}


	/**
	 * Clear the effect of the Kth children ImageView in each GridPane container.
	 * @param gridPaneList A list that contains all the GridPanes which need to be checked
	 * @param k The Kth children in GridPane
	 */
	private void cleanSelect(List<GridPane> gridPaneList, int k){
		for (GridPane gridPane : gridPaneList) {
			ImageView imageView = (ImageView) gridPane.getChildren().get(k);
			if (imageView.getEffect() != null)
				imageView.setEffect(null);
		}
	}


	/**
	 * Return the number of types of food
	 * @return the number of types of food
	 */
	public int getGridPaneFoodListSize(){
		return gridPaneFoodList.size();
	}

	/**
	 * Return the number of types of keys
	 * @return the number of types of keys
	 */
	public int getGridPaneKeyListSize(){
		return gridPaneKeyList.size();
	}


}

