package system;

import java.util.ArrayList;

import people.Colaborator;

public class Service {
	
	private String name;
	private String description;
	private long ID;
	private int avrgDuration;
	private ArrayList<Colaborator> colaborators = new ArrayList<>();
	
	public Service(String name, String description, int avrgDuration) {
		super();
		this.name = name;
		this.description = description;
		this.avrgDuration = avrgDuration;
		this.ID = System.currentTimeMillis();
	}
	
	public String toString() {
		return name + ", ID" + this.ID + ", " + this.avrgDuration + " minutos";
	}
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public long getID() {
		return ID;
	}
	public int getAvrgDuration() {
		return avrgDuration;
	}
	public ArrayList<Colaborator> getColaborators() {
		return colaborators;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setAvrgDuration(int avrgDuration) {
		this.avrgDuration = avrgDuration;
	}
	
}
