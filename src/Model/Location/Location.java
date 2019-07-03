package Model.Location;

import Model.Item.Item;

import java.util.HashMap;
import java.util.Map;

public class Location {
    private String locationID;
    private HashMap<Coordinate, Tile> Tiles = new HashMap<Coordinate, Tile>();
    private HashMap<Coordinate, Item> Items = new HashMap<Coordinate, Item>();
    private Map<String, Coordinate> Entities = new HashMap<String, Coordinate>();

    public Location(String id) {
        this.locationID = id;
    }

    public void addTile(Coordinate c, Tile t) {
        this.Tiles.put(c, t);
    }

    public void removeTile(Coordinate c) {
        this.Tiles.remove(c);
    }

    public void addItem(Coordinate c, Item i) {
        this.Items.put(c, i);
    }

    public void removeItem(Coordinate c) {
        this.Items.remove(c);
    }

    public void addEntity(String id, Coordinate c) {
        this.Entities.put(id, c);
    }

    public void removeEntity(String id) {
        this.Entities.remove(id);
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }
}
