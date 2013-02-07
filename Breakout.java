/*
 * File: Breakout.java
 * -------------------
 * Name: Kiana Hui
 * Section Leader: Arthur Brant
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;
	
	private static final int BALL_DIAMETER = BALL_RADIUS * 2;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;
	
	/* Pause time for the ball */
	private static final int PAUSE_TIME = 10;
	
	private static final int BALL_PAUSE_TIME = 100;
	
	/* Audio file for bouncing */
	AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
	
	/* Method: run() */
	/** Runs the Breakout program. */
	
	/* Instance variable to make the paddle accessible */
	private GRect paddle;
	
	/* Instance variable to make the ball accessible */
	private GOval ball;
	
	/* Instance variable to make the brick accessible */
	private GRect brick;
	
	/* Instance variable to count the bricks remaining */
	private int bricksRemaining;
	
	/* Instance variable to keep track of the velocity of the ball */
	private double vx, vy;
	
	/* Instance variable to generate random numbers */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	public void run() {
		addMouseListeners();
	/*	GLabel label = new GLabel("TIME TO PLAY BREAKOUT!");
		label.setFont("SansSerif-28");
		label.setColor(Color.RED);
		double x = (getWidth() - label.getWidth()) / 2;
		double y = (getHeight() + label.getAscent()) / 2;
		label.setLocation(x, y);
		add(label);
		pause(1000);
		removeAll();*/
		createGame();
		playGame();

	}
	
	
	/*
	 * 
	 */
	private void playGame() {
		/*
		 * Creates a variable for the number of turns remaining,
		 * set at the total number of turns here.
		 */
		int turnsRemaining = NTURNS;
		/*
		 * A while loop that continues while the number
		 * of turns remaining is greater than 0 and the number
		 * of bricks remaining is greater than 0.
		 * 
		 * First, the loop checks for collisions and moves the ball.
		 * If the ball hits the window frame, it reverses direction.
		 * If the ball hits the ground, it is removed, and the user
		 * must click to make a new ball appear that will pause until it
		 * moves again. The number of turns remaining is subtracted by 1
		 * every time the ball hits the ground.
		 */
		while (turnsRemaining > 0 && bricksRemaining > 0) {
			checkForCollisions();
			moveBall();
			if (isBallAboveCeiling(ball)) {
				bounceClip.play();
				vy = -vy;
			} else if (isBallOffRight(ball) || isBallOffLeft(ball)) {
				bounceClip.play();
				vx = -vx;
			} else if (isBallBelowGround(ball)) {
				turnsRemaining--;
				remove(ball);
				GLabel label = new GLabel("Click for new ball. " + turnsRemaining + " turns left.");
				label.setFont("SansSerif-15");
				label.setColor(Color.MAGENTA);
				double x = (getWidth() - label.getWidth()) / 2;
				double y = (getHeight() + label.getAscent()) / 2;
				label.setLocation(x, y);
				add(label);
				waitForClick();
				remove(label);
				makeBall();
				pause(BALL_PAUSE_TIME);
			}
		}
		/*
		 * Once the game is over, the screen is cleared,
		 * and text is displayed showing if the user won or lost.
		 */
		removeAll();
		if (turnsRemaining == 0) {
			endingGame("GAME OVER!", Color.RED);
		}
		if (bricksRemaining == 0) {
			endingGame("YOU WIN!", Color.BLUE);
		}
	}
	
	/*
	 * A method for ending the game that makes a label.
	 */
	private void endingGame(String phrase, Color color) {
		GLabel label = new GLabel(phrase);
		label.setFont("SansSerif-20");
		label.setColor(color);
		double x = (getWidth() - label.getWidth()) / 2;
		double y = (getHeight() + label.getAscent()) / 2;
		label.setLocation(x, y);
		add(label);
	}

	/*
	 * A method that checks for collisions by setting an object
	 * to something that has collided. The ball bounces off the
	 * paddle and deletes bricks if hit.
	 */
	private void checkForCollisions() {
		GObject collider = getCollidingObject();
		if (collider != null) {
			if (collider == paddle) {
				bounceClip.play();
				vy = -vy;
			} else {
				remove(collider);
				bounceClip.play();
				vy = -vy;
				bricksRemaining--;
			}
		}

	}
	
	/*
	 * A method to return the object the ball has collided with at a given point.
	 * If a corner of the ball hit something, the method will return what the ball
	 * has hit. If not, it will return null.
	 */
	private GObject getCollidingObject() {
		if (getElementAt(ball.getX(), ball.getY()) != null) {
			return getElementAt(ball.getX(), ball.getY());
		} else if (getElementAt(ball.getX() + BALL_DIAMETER, ball.getY()) != null) {
			return getElementAt(ball.getX() + BALL_DIAMETER, ball.getY());
		} else if (getElementAt(ball.getX(), ball.getY() + BALL_DIAMETER) != null) {
			return getElementAt(ball.getX(), ball.getY() + BALL_DIAMETER);
		} else if (getElementAt(ball.getX() + BALL_DIAMETER, ball.getY() + BALL_DIAMETER) != null) {
			return getElementAt(ball.getX() + BALL_DIAMETER, ball.getY() + BALL_DIAMETER);
		} else {
			return null;
		}
	}
	
	/*
	 * Checks if the ball goes above the ceiling.
	 */
	private boolean isBallAboveCeiling(GOval ball) {
		double topOfBall = ball.getY();
		return topOfBall <= 0;
	}
	
	/*
	 * Checks if the ball goes beyond the right wall.
	 */
	private boolean isBallOffRight(GOval ball) {
		double sideOfBall = ball.getX() + BALL_DIAMETER;
		return sideOfBall >= WIDTH;
	}
	
	/*
	 * Checks if the ball goes beyond the left wall.
	 */
	private boolean isBallOffLeft(GOval ball) {
		double sideOfBall = ball.getX();
		return sideOfBall <= 0;
	}
	
	/*
	 * Checks if the ball goes below the ground.
	 */
	private boolean isBallBelowGround(GOval ball) {
		double bottomOfBall = ball.getY() + BALL_DIAMETER;
		return bottomOfBall >= HEIGHT;
	}
	
	/*
	 * Creates the setting for the game and sets the number
	 * of bricks remaining to the total bricks present.
	 */
	private void createGame() {
		buildBricks();
		bricksRemaining = NBRICK_ROWS * NBRICKS_PER_ROW;
		buildPaddle();	
		makeBall();
	}
	
	/*
	 * Moves the ball.
	 */
	private void moveBall() {
		ball.move(vx, vy);
		//pause(PAUSE_TIME);
	}

	/*
	 * Builds the bricks by building successive rows. Colors the bricks
	 * by identifying i in the for loop. 
	 */
	private void buildBricks() {
		double height = 0;
		Color color = Color.RED;
		for (int i = 0; i < NBRICK_ROWS; i++) {
			buildBrickRow((WIDTH - (BRICK_WIDTH * NBRICKS_PER_ROW) - 
					      (BRICK_SEP * (NBRICKS_PER_ROW - 1))) / 2, 
			               BRICK_Y_OFFSET + height * (BRICK_SEP + BRICK_HEIGHT), 
			               color);
			height ++;
			if (i > 0 && i < 2) {
				color = Color.ORANGE;
			} else if (i > 2 && i < 4) {
				color = Color.YELLOW;
			} else if (i > 4 && i < 6) {
				color = Color.GREEN;
			} else if (i > 6 && i < 8) {
				color = Color.CYAN;		
			} else if (i > 8 && i <10) {
				color = Color.RED;
			}
		}
	}
	
	/*
	 * Builds a row of bricks by building bricks next to each other
	 * with the set separation space in between.
	 */
	private void buildBrickRow(double x, double y, Color color) {
		for (int i = NBRICKS_PER_ROW; i > 0; i--) {
			brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
			brick.setFilled(true);
			brick.setColor(color);
			add(brick);
			x = BRICK_WIDTH + BRICK_SEP + x;
		}
	}

	/*
	 * Builds the paddle, originally placed in the center of the window.
	 */
	private void buildPaddle() {
		paddle = new GRect (PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setLocation((WIDTH - paddle.getWidth()) / 2, HEIGHT - PADDLE_Y_OFFSET);
		paddle.setFilled(true);
		add(paddle);
	}
	
	/*
	 * Makes the ball, originally placed in the center of the window.
	 */
	private void makeBall() {
		ball = new GOval (BALL_DIAMETER, BALL_DIAMETER);
		ball.setLocation((WIDTH / 2) - BALL_RADIUS, (HEIGHT / 2) - BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
	}
	
	/*
	 * When the mouse is moved, the paddle follows. The cursor
	 * is set to be in the middle of the paddle.
	 */
	public void mouseMoved(MouseEvent e) {
		double x = e.getX() - (PADDLE_WIDTH / 2);
		double y = HEIGHT - PADDLE_Y_OFFSET;
		if (x < (WIDTH - PADDLE_WIDTH) && x > 0){
			paddle.setLocation(x, y);
		}
	}
	
	/*
	 * When the mouse is pressed, the velocity of the ball changes
	 * by a random number generator.
	 */
	public void mousePressed(MouseEvent e) {
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) vx = -vx;
		vy = 5.0;
	}

}
