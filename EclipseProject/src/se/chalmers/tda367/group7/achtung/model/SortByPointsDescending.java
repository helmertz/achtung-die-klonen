package se.chalmers.tda367.group7.achtung.model;

import java.util.Comparator;

public class SortByPointsDescending implements Comparator<Player> {

	@Override
	public int compare(Player o1, Player o2) {
		if(o1.getPoints() > o2.getPoints()) {
			return -1;
		} else if(o1.getPoints() < o2.getPoints()) {
			return 1;
		} else {
			return 0;
		}
	}

}
