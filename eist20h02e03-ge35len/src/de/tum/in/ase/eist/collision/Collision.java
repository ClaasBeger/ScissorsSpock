package de.tum.in.ase.eist.collision;

import de.tum.in.ase.eist.Dimension2D;
import de.tum.in.ase.eist.Point2D;
import de.tum.in.ase.eist.car.Car;

/**
 * This class defines the behavior when two cars collide.
 */
public class Collision {

	protected Car car1;
	protected Car car2;
	public boolean isCollision;
	protected Car losercar = null;

	public Collision(Car car1, Car car2) {
		this.car1 = car1;
		this.car2 = car2;
		this.isCollision = detectCollision();
	}

	/**
	 * Checks whether two cars have collided. The translation transformation shifts
	 * a node from one place to another along one of the axes relative to its
	 * initial position.
	 * 
	 * @return boolean - true if collision is detected.
	 */
	public boolean detectCollision() {
		Point2D p1 = car1.getPosition();
		Dimension2D d1 = car1.getSize();

		Point2D p2 = car2.getPosition();
		Dimension2D d2 = car2.getSize();

		if (p1.getY() + d1.getHeight() < p2.getY() || p1.getY() > p2.getY() + d2.getHeight())
			return false;

		return !(p1.getX() + d1.getWidth() < p2.getX()) && !(p1.getX() > p2.getX() + d2.getWidth());
	}

	/**
	 * Evaluates winner of the collision
	 * 
	 * @return winner Car
	 */
	public Car evaluate() {

		// TODO 1: Collisions follow the "right before left" rule, and thus right-most
		// cars win the collisions

		Point2D positionCar1 = this.car1.getPosition();
		Point2D positionCar2 = this.car2.getPosition();
		Car winnerCar = null;
		if (positionCar1.getX() > positionCar2.getX()) {
			winnerCar = this.car1;
			this.car2.setCrunched();
			this.losercar = this.car2;
		} else {
			winnerCar = this.car2;
			this.car1.setCrunched();
			this.losercar = this.car1;
		}
		return winnerCar;
	}

	/**
	 * Evaluates loser of the collision
	 * 
	 * @return loser Car
	 */
	public Car evaluateLoser() {
		return this.losercar;
	}
}
