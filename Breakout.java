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
	
	private Color color = Color.RED;
	
	public void run() {
		buildBricks(0);
		addMouseListeners();
	}
	
	private void buildBricks(double height) {
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
			}
		}
	}
	
/*	private void buildBricks(double height) {
		for (int i = NBRICK_ROWS; i > 0; i--) {
			buildBrickRow((WIDTH - (BRICK_WIDTH * NBRICKS_PER_ROW) - 
					      (BRICK_SEP * (NBRICKS_PER_ROW - 1))) / 2, 
			               BRICK_Y_OFFSET + height * (BRICK_SEP + BRICK_HEIGHT), 
			               color);
			height ++;
			if (i > 2 && i < 4) {
				color = Color.CYAN;
			} 
			if (i > 4 && i < 6) {
				color = Color.GREEN;
			}
			if (i > 6 && i < 8) {
				color = Color.ORANGE;
			}		
			if (i > 8 && i < 10) {
				color = Color.YELLOW;
			} 
			}
	}
	*/
	private void buildBrickRow(double x, double y, Color color) {
		for (int i = NBRICKS_PER_ROW; i > 0; i--) {
			GRect brick =  new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
			brick.setFilled(true);
			brick.setColor(color);
			add(brick);
			x = BRICK_WIDTH + BRICK_SEP + x;
		}
	}
	
	public void mousePressed(MouseEvent e) {
		
	}
	
}
