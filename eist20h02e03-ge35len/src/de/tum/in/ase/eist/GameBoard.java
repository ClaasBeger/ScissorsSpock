package de.tum.in.ase.eist;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import de.tum.in.ase.eist.audio.AudioPlayerInterface;
import de.tum.in.ase.eist.car.Car;
import de.tum.in.ase.eist.car.FastCar;
import de.tum.in.ase.eist.car.LizardCar;
import de.tum.in.ase.eist.car.PaperCar;
import de.tum.in.ase.eist.car.RockCar;
import de.tum.in.ase.eist.car.ScissorsCar;
import de.tum.in.ase.eist.car.SlowCar;
import de.tum.in.ase.eist.car.SpockCar;
import de.tum.in.ase.eist.collision.Collision;
import de.tum.in.ase.eist.collision.CustomCollision;

/**
 * Creates all car objects, detects collisions, updates car positions, notifies
 * player about victory or defeat
 *
 */
public class GameBoard {

	// list of all active cars, does not contain player car
	private List<Car> cars = new ArrayList<>();

	// the player object with player car object
	private Player player;

	private AudioPlayerInterface audioPlayer;

	private Dimension2D size;

	// list of all loser cars (needed for testing, DO NOT DELETE THIS)
	private List<Car> loserCars = new ArrayList<>();

	// true if game is running, false if game is stopped
	private boolean isRunning;

	private Boolean gameWon;

	// constants
	public static int NUMBER_OF_SLOW_CARS = 0;
	public static int NUMBER_OF_TESLA_CARS = 0;
	public int NUMBER_OF_SPOCK_CARS = 1;
	public int NUMBER_OF_SCISSORS_CARS = 1;
	public int NUMBER_OF_ROCK_CARS = 1;
	public int NUMBER_OF_PAPER_CARS = 1;
	public int NUMBER_OF_LIZARD_CARS = 1;

	/**
	 * Constructor, creates the gameboard based on size
	 * 
	 * @param size of the gameboard
	 */
	public GameBoard(Dimension2D size) {
		Car playerCar = new RockCar(250, 30);
		this.player = new Player(playerCar);
		this.size = size;
		this.addCars();
	}

	/**
	 * Adds specified number of cars to the cars list, creates new object for each
	 * car
	 */
	public void addCars() {
		for (int i = 0; i < NUMBER_OF_SLOW_CARS; i++) {
			this.cars.add(new SlowCar(this.size.getWidth(), this.size.getHeight()));
		}
		for (int i = 0; i < NUMBER_OF_TESLA_CARS; i++) {
			this.cars.add(new FastCar(this.size.getWidth(), this.size.getHeight()));
		}
		for (int i = 0; i < NUMBER_OF_SPOCK_CARS; i++) {
			this.cars.add(new SpockCar(this.size.getWidth(), this.size.getHeight()));
		}
		for (int i = 0; i < NUMBER_OF_SCISSORS_CARS; i++) {
			this.cars.add(new ScissorsCar(this.size.getWidth(), this.size.getHeight()));
		}
		for (int i = 0; i < NUMBER_OF_ROCK_CARS; i++) {
			this.cars.add(new RockCar(this.size.getWidth(), this.size.getHeight()));
		}
		for (int i = 0; i < NUMBER_OF_PAPER_CARS; i++) {
			this.cars.add(new PaperCar(this.size.getWidth(), this.size.getHeight()));
		}
		for (int i = 0; i < NUMBER_OF_LIZARD_CARS; i++) {
			this.cars.add(new LizardCar(this.size.getWidth(), this.size.getHeight()));
		}
	}

	/**
	 * Removes all existing cars from the car list, resets the position of the
	 * player car Invokes the creation of new car objects by calling addCars()
	 */
	public void resetCars() {
		this.getPlayer().getCar().reset(this.size.getHeight());
		this.cars.clear();
		addCars();
	}

	/**
	 * Checks if game is currently running by checking if the thread is running
	 * 
	 * @return boolean isRunning
	 */
	public boolean isRunning() {
		return this.isRunning;
	}

	/**
	 * Used for testing only
	 */
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	/**
	 * 
	 * @return null if the game is running; true if the player has won; false if the
	 *         player has lost
	 */
	public Boolean hasWon() {
		return this.gameWon;
	}

	/**
	 * @return list of cars
	 */
	public List<Car> getCars() {
		return this.cars;
	}

	/**
	 * @return the player's car
	 */
	public Car getPlayerCar() {
		return this.getPlayer().getCar();
	}

	/**
	 * @return the gameboard's instance of AudioPlayer
	 */
	public AudioPlayerInterface getAudioPlayer() {
		return this.audioPlayer;
	}

	public void setAudioPlayer(AudioPlayerInterface audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

	/**
	 * Updates the position of each car
	 */
	public void update() {
		moveCars();
	}

	/**
	 * Starts the game. Cars start to move and background music starts to play.
	 */
	public void startGame() {
		playMusic();
		this.isRunning = true;
	}

	/**
	 * Stops the game. Cars stop moving and background music stops playing.
	 */
	public void stopGame() {
		stopMusic();
		this.isRunning = false;
	}

	/**
	 * Starts the background music
	 */
	public void playMusic() {
		this.audioPlayer.playBackgroundMusic();
	}

	/**
	 * Stops the background music
	 */
	public void stopMusic() {
		this.audioPlayer.stopBackgroundMusic();
	}

	/**
	 * @return list of loser cars
	 */
	public List<Car> getLoserCars() {
		return this.loserCars;
	}

	/**
	 * Iterate through list of cars (without the player car) and update each car's
	 * position Update player car afterwards separately
	 */
	public void moveCars() {

		List<Car> cars = getCars();

		// maximum x and y values a car can have depending on the size of the game board
		int maxX = (int) size.getWidth();
		int maxY = (int) size.getHeight();

		// update the positions of the player car and the autonomous cars
		for (Car car : cars) {
			car.updatePosition(maxX, maxY);
		}

		getPlayer().getCar().updatePosition(maxX, maxY);

		// iterate through all cars (except player car) and check if it is crunched
		for (Car car : cars) {
			if (car.isCrunched()) {
				continue; // because there is no need to check for a collision
			}

			// TODO 4: Add a new collision type!
			// Hint: Make sure to create a subclass of the class Collision
			// and store it in the new Collision package.
			// Create a new collision object
			// and check if the collision between player car and autonomous car evaluates as
			// expected
			CustomCollision collision = new CustomCollision(getPlayer().getCar(), car);

			if (collision.isCollision) {
				Car winner = collision.evaluate();
				Car loser = collision.evaluateLoser();
				if (winner != null) {
					loserCars.add(loser);
					audioPlayer.playCrashSound();
					if (loser.equals(getPlayerCar())) {
						this.gameWon = false;
					} else if (isWinner()) {
						this.gameWon = true;
					}
				}
				// TODO 3: The player gets notified when he looses or wins the game
				// Hint: you should set the attribute gameWon accordingly, use 'isWinner()'
				// below for your implementation

			}
		}
	}

	// Change Playercars type every 10 seconds to enable him to win
	public void updatePlayerCar() {
		int randomNum = ThreadLocalRandom.current().nextInt(0, 5);
		if (randomNum == 0) {
			this.getPlayer().getCar().setIcon("Lizard.gif");
			this.getPlayer().getCar().type = "Lizard";
		} else if (randomNum == 1) {
			this.getPlayer().getCar().setIcon("Rock.gif");
			this.getPlayer().getCar().type = "Rock";
		} else if (randomNum == 2) {
			this.getPlayer().getCar().setIcon("Paper.gif");
			this.getPlayer().getCar().type = "Paper";
		} else if (randomNum == 3) {
			this.getPlayer().getCar().setIcon("Scissors.gif");
			this.getPlayer().getCar().type = "Scissors";
		} else {
			this.getPlayer().getCar().setIcon("Spock.gif");
			this.getPlayer().getCar().type = "Spock";
		}

	}

	public void pacManChange() { // change cars in pacManMode
		if (this.getPlayerCar().type.equals("Spock")) {
			this.getPlayer().getCar().setIcon("Scissors.gif");
			this.getPlayer().getCar().type = "Scissors";
		} else {
			this.getPlayer().getCar().setIcon("Spock.gif");
			this.getPlayer().getCar().type = "Spock";
		}
	}

	/**
	 * If all cars are crunched, the player wins.
	 * 
	 * @return true if game is won
	 * @return false if game is not (yet) won
	 */
	private boolean isWinner() {
		for (Car car : getCars()) {
			if (!car.isCrunched()) {
				return false;
			}
		}
		return true;
	}

	public Player getPlayer() {
		return player;
	}
}
