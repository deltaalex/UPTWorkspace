package upt.noc.structures;

import java.util.Vector;

public class NocNode 
{
	public int id;
    public int randomLink;
    private Vector<Integer> directions;
    public double rate;

    public NocNode(int id, int randomLink)
    {
        this.id = id;
        this.randomLink = randomLink;
    }

    public NocNode(int id)
    {
        this.id = id;
        randomLink = -1;
    }

    public int getId() {
		return id;
	}
    
    public int getDestId() {
		return randomLink;
	}
    
    public void setRandomLink(int randomLink) {
		this.randomLink = randomLink;
	}
    
    public double getRate() {
		return rate;
	}
    
    public void setRate(double rate) {
		this.rate = rate;
	}
    
    public void addDirection(int direction)
    {
        if (directions == null)
            directions = new Vector<Integer>();

        directions.add(direction);
    }

    public Vector<Integer> getDirections()
    {
        if (directions != null && directions.size() > 0)
            return directions;
        else
            return null;
    }
	
}
