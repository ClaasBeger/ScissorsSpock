package de.tum.in.ase.eist.car;

import de.tum.in.ase.eist.Dimension2D;
import de.tum.in.ase.eist.Point2D;

/**
 * Abstract class for cars. Objects for this class cannot be instantiated.
 */
public abstract class Car {

	public int MAX_SPEED = 10;
	public int MIN_SPEED = 2;
	protected int speed = this.MIN_SPEED;
    public String type;
    private String iconLocation;
	protected Point2D position;
	private Dimension2D size = new Dimension2D(50, 25);
	
	// the direction is seen as degree within a circle
	private int direction = 90;
	private boolean isCrunched = false;

	/**
	 * Constructor, taking the maximum coordinates of the game board. Each car
	 * gets a random X and Y coordinate, a random direction and a random speed
	 * 
	 * The position of the car cannot be larger then the parameters, i.e. the
	 * dimensions of the game board
	 * 
	 * @param maxX Maximum x coordinate (width) of the game board
	 * @param maxY Maximum y coordinate (height) of the game board
	 */
	public Car(int maxX, int maxY) {
		int carX = (int) (Math.random() * (maxX - getSize().getWidth()));
		int carY = (int) (Math.random() * (maxY - getSize().getHeight()));
		this.position = new Point2D(carX, carY);
		if (carY < getSize().getHeight()) {
			this.position = new Point2D(carX, getSize().getHeight());
		}
		setDirection((int) (Math.random() * 360));
		setRandomSpeed();
		this.isCrunched = false;
	}

	/**
	 * The car's position is reset to the top left corner of the game. The speed
	 * is set to 5 and the directions points to 90 degrees.
	 * 
	 * @param maxY Top left corner of the game board
	 */
	public void reset(int maxY) {
		this.position = new Point2D(0, maxY);
		setDirection(90);
		this.speed = 5;
		this.isCrunched = false;
	}

	/**
	 * Sets the speed of the car to a random value based on its initial speed
	 */
	protected void setRandomSpeed() {
		int initialSpeed = (int) (Math.random() * this.MAX_SPEED);
		if (initialSpeed < this.MIN_SPEED) {
			initialSpeed = this.MIN_SPEED;
		}
		this.speed = initialSpeed;
	}

	/**
	 * Sets the car's direction
	 * 
	 * @param direction
	 * @throws IllegalArgumentException
	 */
	public void setDirection(int direction) throws IllegalArgumentException {
		if (direction < 0 || direction > 360) {
			throw new IllegalArgumentException("Direction must be between 0 and 360");
		}
		this.direction = direction;
	}

	public int getDirection() {
		return this.direction;
	}

	public int getSpeed() {
		return this.speed;
	}

	/**
	 * Increments the car's speed, won't exceed the maximum speed
	 */
	public void incrementSpeed() {
		if (this.speed < this.MAX_SPEED) {
			this.speed++;
		}
	}

	/**
	 * Decrements the car's speed, won't fall below the minimum speed
	 */
	public void decrementSpeed() {
		if (this.speed > this.MIN_SPEED) {
			this.speed--;
		}
	}

    public String getIconLocation() {
        return this.iconLocation;
    }

    /**
     * Sets the image of the car
     *
     * @param iconLocation path of the image file
     * @throws IllegalArgumentException
     */
    //protected
    public void setIcon(String iconLocation) throws IllegalArgumentException {
        if (iconLocation == null) {
            throw new IllegalArgumentException("The chassis image of a car connot be null.");
        }
        this.iconLocation = iconLocation;
    }

	public Point2D getPosition() {
		return this.position;
	}

	/**
	 * Sets the car's position
	 * 
	 * @param x the position along the x-axes
	 * @param y the position along the y-axes
	 */
	public void setPosition(int x, int y) {
		this.position = new Point2D(x, y);
	}

	public Dimension2D getSize() {
		return this.size;
	}

	public void setCrunched() {
		this.isCrunched = true;
		this.speed = 0;
	}

	public boolean isCrunched() {
		return this.isCrunched;
	}

	/**
	 * Calculates the new X and Y coordinations based on the current position,
	 * direction and speed
	 * 
	 * @param maxX the current position along the x-axes
	 * @param maxY the current position along the y-axes
	 */
	public void updatePosition(int maxX, int maxY) {
		if (this.isCrunched)
			return;
		// calculate delta between old coordinates and new ones based on speed and direction
		float delta_x = this.speed * (float) Math.sin(Math.toRadians(this.direction));
		float delta_y = this.speed * (float) Math.cos(Math.toRadians(this.direction));

		// set coordinates
		this.position = new Point2D(this.position.getX() + delta_x, this.position.getY() + delta_y);

		// calculate position in case the boarder of the game board has been reached
		if (this.position.getX() < 0) {
			this.position = new Point2D(0, this.position.getY());
			this.direction = 360 - this.direction;
		}

		if (this.position.getX() + this.size.getWidth() > maxX) {
			this.position = new Point2D(maxX - this.size.getWidth(), this.position.getY());
			this.direction = 360 - this.direction;
		}
		if (this.position.getY() - this.size.getHeight() < 0) {
			this.position = new Point2D(this.position.getX(), this.size.getHeight());
			this.direction = 180 - this.direction;
			if (this.direction < 0) {
				this.direction = 360 + this.direction;
			}
		}
		if (this.position.getY() > maxY) {
			this.position = new Point2D(this.position.getX(), maxY);
			this.direction = 180 - this.direction;
			if (this.direction < 0) {
				this.direction = 360 + this.direction;
			}
		}
	}

}
