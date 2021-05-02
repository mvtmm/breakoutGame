package programming.projekt.breakout;

import java.awt.Color;
import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;

/**
 * Diese Klasse erstellt das view für den ComputerBildschirm.
 * 
 * @author marvin Timm & Gunnar Leetz.
 *
 */
@SuppressWarnings("serial")
public class BreakoutView extends GCompound {

	private final int WIDTH = BreakoutModel.WIDTH;
	private final int HEIGHT = BreakoutModel.HEIGHT;
	static GRect[][] bricks = new GRect[3][4];
	static GOval ball;
	static GRect paddle;

	/**
	 * Konstruiert eine View mit der festgelegten Fenstergröße.
	 */
	BreakoutView() {
		GRect background = new GRect(WIDTH, HEIGHT);

		add(background);

	}

	/**
	 * Konstruiert einen Ball in der Mitte des Bildschirms.
	 */
	void createBall() {
		ball = new GOval(BreakoutModel.startBallPX, BreakoutModel.startBallPY, BreakoutModel.BALL_SIZE,
				BreakoutModel.BALL_SIZE);
		ball.setFilled(true);
		add(ball);
	}

	/**
	 * hohlt sich die Koordinaten des Balls aus dem Model und bewegt den Ball
	 * dementsprechend an die Stelle.
	 */
	void updateBall() {

		ball.setLocation(BreakoutModel.x, BreakoutModel.y);
	}

	/**
	 * Konstruiert das Paddle unten in der Mitte des Bildschirms.
	 */
	void createPaddle() {
		paddle = new GRect(BreakoutModel.startPaddlePX, BreakoutModel.startPaddlePY, BreakoutModel.PADDLE_LENGTH,
				BreakoutModel.PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
	}

	/**
	 * Besorgt sich die Koordinaten des Paddles und setzt es dementsprechend an die
	 * stelle.
	 */
	void updatePaddle() {
		paddle.setLocation(BreakoutModel.paddleX, BreakoutModel.paddleY);
	}

	/**
	 * Konstruiert die 12 Bricks die der Spieler zerstören muss.
	 */
	void createBricks() {
		for (int i = 0; i < BreakoutModel.BRICK_ROW; i++) {
			for (int j = 0; j < BreakoutModel.BRICK_COLUMN; j++) {
				if (BreakoutModel.BRICKS[i][j]) {
					bricks[i][j] = new GRect(BreakoutModel.BRICK_WIDTH * j, BreakoutModel.BRICK_HEIGHT * i,
							BreakoutModel.BRICK_WIDTH, BreakoutModel.BRICK_HEIGHT);
					bricks[i][j].setFilled(true);
					if (i == 0) {
						bricks[i][j].setFillColor(Color.black);
					}
					if (i == 1) {
						bricks[i][j].setFillColor(Color.red);
					}
					if (i == 2) {
						bricks[i][j].setFillColor(Color.yellow);
					}
					add(bricks[i][j]);
				}

			}
		}
	}

	/**
	 * Überprüft das Brickarray im Model und versetzt alle Bricks, die nicht mehr
	 * existieren sollen aus dem Spielfeld heraus. (Aus einem uns nicht erklärbaren
	 * Grund konnten wir "remove(bricks[i][j]" nicht benutzen, deshalb setzen wir
	 * sie einfach an einen Ort wo es keiner sieht.)
	 * 
	 */
	void updateBricks() {
		for (int i = 0; i < BreakoutModel.BRICK_ROW; i++) {
			for (int j = 0; j < BreakoutModel.BRICK_COLUMN; j++) {
				if (!BreakoutModel.BRICKS[i][j]) {

					bricks[i][j].setLocation(11000000, 11111100);

				}
			}
		}
	}
	/**
	 * Wenn man alle Bricks zerstört hat wird einem gesagt das man gewonnen hat. 
	 */
	void winner() {

		removeAll();// Nimmt alle Objekte aus dem Bildschirm.

		GLabel win = new GLabel("You won!");
		win.setLocation(BreakoutModel.WIDTH / 2, BreakoutModel.HEIGHT / 2);
		win.setLocation(BreakoutModel.WIDTH / 2 - 130, BreakoutModel.HEIGHT / 2);
		win.setColor(Color.green);
		win.setFont(win.getFont().deriveFont(64f));
		add(win);
	}
/**
 * Falls es vorkommt das der Spieler es nicht schafft zu gewinnen verliert er, dies wird durch diese Methode angezeigt.
 * Der Spieler verliert sobald der Ball das Spielfeld verlässt.
 */
	void looser() {

		removeAll();

		GLabel loose = new GLabel("You lost!");
		loose.setLocation(BreakoutModel.WIDTH / 2, BreakoutModel.HEIGHT / 2);
		loose.setLocation(BreakoutModel.WIDTH / 2 - 130, BreakoutModel.HEIGHT / 2);
		loose.setColor(Color.red);
		loose.setFont(loose.getFont().deriveFont(64f));
		add(loose);
	}
	void reset(){
		removeAll();
	}

}
