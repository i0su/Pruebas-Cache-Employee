package entity;

import javax.persistence.Embeddable;

@Embeddable
public class Manager {

	private int id;

	public Manager() {
		
	}
	
	public Manager(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
