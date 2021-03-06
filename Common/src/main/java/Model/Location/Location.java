package Model.Location;

import Model.Entity.Entity;
import Model.Item.Item;
import Utils.Observable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Location extends Observable implements Serializable {
    //ID for location
    private String locationID;
    //save coordinate for each tile
    private HashMap<Coordinate, Tile> Tiles = new HashMap<Coordinate, Tile>();
    //save coordinate for each item
    private HashMap<Coordinate, Item> Items = new HashMap<Coordinate, Item>();
    //save coordinate for each entity
    private Map<Entity, Coordinate> Entities = new HashMap<Entity, Coordinate>();

    public HashMap<Coordinate, Tile> getTiles() {
        return Tiles;
    }

    public void setTiles(HashMap<Coordinate, Tile> tiles) {
        Tiles = tiles;
    }

    public HashMap<Coordinate, Item> getItems() {
        return Items;
    }

    public void setItems(HashMap<Coordinate, Item> items) {
        Items = items;
        notifyObserver();
    }

    public Map<Entity, Coordinate> getEntities() {
        return Entities;
    }

    public Location(String id) {
        this.locationID = id;
    }

    public void addTile(Coordinate c, Tile t) {
        this.Tiles.put(c, t);
    }

    public void addItem(Coordinate c, Item i) {
        this.Items.put(c, i);
        notifyObserver();
    }

    public void removeItem(Coordinate c) {
        this.Items.remove(c);
        notifyObserver();
    }

    public void addEntity(Entity entity, Coordinate c) {
        this.Entities.put(entity, c);
    }

    public void removeEntity(Entity entity) {
        this.Entities.remove(entity);
    }

    public String getLocationID() {
        return locationID;
    }

    /**
     * change user coordinate value in location and notify observers
     * @param entity input entity object
     * @param coordinate input new coordinate object
     */
    public void changeUserCoordinate(Entity entity, Coordinate coordinate) {
        this.Entities.put(entity, coordinate);
        notifyObserver();
    }

}
