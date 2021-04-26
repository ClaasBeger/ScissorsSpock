package de.tum.in.ase.eist.car;

public class SlowCar extends Car {
	
	public static String DEFAULT_SLOW_CAR_IMAGE_FILE = "SlowCar.gif";

	/**
	 * Constructor for a SlowCar
	 * 
	 * @param maxX Maximum x coordinate (width) of the game board
	 * @param maxY Maximum y coordinate (height) of the game board
	 */
	public SlowCar(int maxX, int maxY) {
		super(maxX, maxY);
		this.MIN_SPEED = 3;
		this.MAX_SPEED = 7;
		this.setRandomSpeed();
		this.setIcon(DEFAULT_SLOW_CAR_IMAGE_FILE);
	}
}
