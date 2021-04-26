package de.tum.in.ase.eist.collision;

import de.tum.in.ase.eist.car.Car;
import de.tum.in.ase.eist.car.LizardCar;
import de.tum.in.ase.eist.car.PaperCar;
import de.tum.in.ase.eist.car.RockCar;
import de.tum.in.ase.eist.car.ScissorsCar;
import de.tum.in.ase.eist.car.SpockCar;

public class CustomCollision extends Collision {

	public CustomCollision(Car car1, Car car2) {
		super(car1, car2);

	}

	@Override
	public Car evaluate() {
		if (car1.type.equals("Rock") || car2.type.equals("Rock")) {
			if (car2.type.equals("Rock") && !(car1.type.equals("Rock"))) {
				Car temp;
				temp = car1;
				car1 = car2;
				car2 = temp;
			}
			if (car2.type.equals("Paper")) {
				car1.setCrunched();
				this.losercar = car1;
				return car2;
			}
			if (car2.type.equals("Rock")) {
				return null;
			}
			if (car2.type.equals("Scissors")) {
				car2.setCrunched();
				this.losercar = car2;
				return car1;
			}
			if (this.car2.type.equals("Lizard")) {
				car2.setCrunched();
				this.losercar = car2;
				return car1;
			}
			if (this.car2.type.equals("Spock")) {
				car1.setCrunched();
				this.losercar = car1;
				return car2;
			}
		}
		if (car1.type.equals("Paper") || car2.type.equals("Paper")) {
			if (car2.type.equals("Paper") && !(car1.type.equals("Paper"))) {
				Car temp;
				temp = car1;
				car1 = car2;
				car2 = temp;
			}
			if (car2.type.equals("Paper")) {
				return null;
			}
			if (car2.type.equals("Scissors")) {
				car1.setCrunched();
				this.losercar = car1;
				return car2;
			}
			if (this.car2.type.equals("Lizard")) {
				car1.setCrunched();
				this.losercar = car1;
				return car2;
			}
			if (this.car2.type.equals("Spock")) {
				car2.setCrunched();
				this.losercar = car2;
				return car1;
			}
		}
		if (car1.type.equals("Scissors") || car2.type.equals("Scissors")) {
			if (car2.type.equals("Scissors") && !(car1.type.equals("Scissors"))) {
				Car temp;
				temp = car1;
				car1 = car2;
				car2 = temp;
			}
			if (car2.type.equals("Scissors")) {
				return null;
			}
			if (this.car2.type.equals("Lizard")) {
				car2.setCrunched();
				this.losercar = car2;
				return car1;
			}
			if (this.car2.type.equals("Spock")) {
				car1.setCrunched();
				this.losercar = car1;
				return car2;
			}
		}
		if (car1.type.equals("Lizard") || car2.type.equals("Lizard")) {
			if (car2.type.equals("Lizard") && !(car1.type.equals("Lizard"))) {
				Car temp;
				temp = car1;
				car1 = car2;
				car2 = temp;
			}
			if (this.car2.type.equals("Lizard")) {
				return null;
			}
			if (this.car2.type.equals("Spock")) {
				car2.setCrunched();
				this.losercar = car2;
				return car1;
			}
		} else {
			return null;
		}
		return null;
	}

	@Override
	public Car evaluateLoser() {
		return this.losercar;
	}
}
