package de.tum.in.ase.eist.car;

public class SpockCar extends Car{
	public static String DEFAULT_SPOCK_CAR_IMAGE_FILE = "Spock.gif";
	
	@Override
	protected void setRandomSpeed() {
		int initialSpeed = (int) (Math.random() * (this.MAX_SPEED-3));
		if (initialSpeed < this.MIN_SPEED) {
			initialSpeed = this.MIN_SPEED;
		}
		this.speed = initialSpeed;
	}
	
	public SpockCar(int maxX, int maxY) {
		super(maxX, maxY);
		type = "Spock";
		this.MIN_SPEED = 3;
		this.MAX_SPEED =10;
		this.setRandomSpeed();
		this.setIcon(DEFAULT_SPOCK_CAR_IMAGE_FILE);
	}

}
