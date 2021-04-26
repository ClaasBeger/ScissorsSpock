package de.tum.in.ase.eist.car;

public class RockCar extends Car {
	
	public static String DEFAULT_ROCK_CAR_IMAGE_FILE = "Rock.gif";

	/**
	 * Constructor for a SlowCar
	 * 
	 * @param maxX Maximum x coordinate (width) of the game board
	 * @param maxY Maximum y coordinate (height) of the game board
	 */
	
	@Override
	protected void setRandomSpeed() {
		int initialSpeed = (int) (Math.random() * (this.MAX_SPEED-3));
		if (initialSpeed < this.MIN_SPEED) {
			initialSpeed = this.MIN_SPEED;
		}
		this.speed = initialSpeed;
	}
	
	public RockCar(int maxX, int maxY) {
		super(maxX, maxY);
		type = "Rock";
		this.MIN_SPEED = 3;
		this.MAX_SPEED = 10;
		this.setRandomSpeed();
		this.setIcon(DEFAULT_ROCK_CAR_IMAGE_FILE);
	}
}