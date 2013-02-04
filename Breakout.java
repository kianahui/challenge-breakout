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

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	/* Method: run() */
	/** Runs the Breakout program. */
	
	//private Color color = Color.RED;
	
	private GRect paddle;
	
	public void run() {
		buildBricks();
		buildPaddle();		
		addMouseListeners();
	}
	
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
	
	
	private void buildBrickRow(double x, double y, Color color) {
		for (int i = NBRICKS_PER_ROW; i > 0; i--) {
			GRect brick =  new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
			brick.setFilled(true);
			brick.setColor(color);
			add(brick);
			x = BRICK_WIDTH + BRICK_SEP + x;
		}
	}

	
	private void buildPaddle() {
		paddle = new GRect (PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setLocation((WIDTH - paddle.getWidth()) / 2, HEIGHT - PADDLE_Y_OFFSET);
		paddle.setFilled(true);
		add(paddle);
	}
	
	public void mouseMoved(MouseEvent e) {
		double x = e.getX() - (paddle.getWidth() / 2);
		double y = HEIGHT - PADDLE_Y_OFFSET;
		if (x < (WIDTH - paddle.getWidth()) && x > 0){
			paddle.setLocation(x, y);
		}
	}
	
}
