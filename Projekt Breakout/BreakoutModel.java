package programming.projekt.breakout;

public class BreakoutModel {

	// Commons
	static final int BRICK_WIDTH = 70;
	static final int BRICK_HEIGHT = 10;
	static final int BRICK_ROW = 3;
	static final int BRICK_COLUMN = 4;

	static final int BALL_SIZE = 8;
	static final int PADDLE_OFFSET = 1;
	static final int PADDLE_LENGTH = 50;
	static final int PADDLE_HEIGHT = 10;
	static final int WIDTH = 280;
	static final int HEIGHT = 140;

	static boolean[][] BRICKS = new boolean[3][4];

	static int brickCount = BRICK_ROW * BRICK_COLUMN;
	static int startBallPX = 140;
	static int startBallPY = 100;
	static int startPaddlePX = 124;
	static int startPaddlePY = 133;
	static int x = 140; // Die x Koordinate des Balls.
	static int y = 100; // Die y Koordinate des Balls.
	static int paddleX = 124;
	static int paddleY = 133;
	static boolean wincondition;
	
	// Lighthouse Skalierung
	static int xL = 42; // Die x koordinate des Balls im lighthouse als index des Byte arrays.
	static int yL = 756; // Die y koordinate des Balls im lighthouse als index des Byte arrays.
	static int paddleyLStart = 30;
	static int paddlexLStart = 1092;
	static int paddlexL = 30;
	static int paddleyL = 1092;

	private int a = 0;// Wird fürs Lighthouse gebraucht, siehe @Line128.

	static Ball ball = new Ball();

	BreakoutView view1 = new BreakoutView();
	BreakoutViewLighthouse viewL = new BreakoutViewLighthouse();

	/**
	 * Setzt das gesamte boolean Array auf true, damit die Bricks erstellt werden
	 * können. Sobald ein Brick zerstoert wird ´, wird der am entsprechenden index
	 * false eingegeben.
	 */
	void initializeGame() {

		for (int i = 0; i < BRICK_ROW; i++) {
			for (int j = 0; j < BRICK_COLUMN; j++) {
				BRICKS[i][j] = true;
			}
		}
		// Initialisiert die LighthouseView
		viewL.initializeLighthouse();
		viewL.createBricks();
		viewL.createBall();
		viewL.createPaddle();

	}

	/**
	 * Bewegt das Paddle 10 Pixel nach rechts., und um 1 im Lighthouse.
	 */
	void movePaddleRight() {
		if (paddleX < 260) {
			paddleX += 10;
			view1.updatePaddle();
		}
		if (paddlexL < 69) {// die 69 ist der index im Bytearray, der das 6te fenster von rechts darstellt.
							// Dadurch kann das Paddle nicht nach rechts rauslaufen.
			paddlexL += 3;
			viewL.updatePaddleRight();

		}
	}

	/**
	 * Bewegt das Paddle 10 Pixel nach links.
	 */
	void movePaddleLeft() {
		if (paddleX > 0) {
			paddleX -= 10;
			view1.updatePaddle();
		}
		if (paddlexL > 0) { // Durch die 0 kann das Paddle die linke seite im LightHouse nicht verelassen.
			paddlexL -= 3;
			viewL.updatePaddleLeft();
		}

		// viewL.createPaddle();

	}

	/**
	 * Diese Methode bewegt den Ball und erkennt ob der Ball gegen eine Wand oder
	 * ein Spielobjekt stoßt und ändert die Bewegung dementprechend.
	 */
	void move() {

		double time = System.currentTimeMillis();

		while (brickCount > -1) {// Bewegt den Ball solange Bricks existieren.

			if (System.currentTimeMillis() > time + 20) {

				if (y > HEIGHT) {// wenn der Ball unter die Spielfeldgröße fällt verlirt man.
					wincondition = false;
					break;
				}
				if (brickCount == 0) {// Wenn alle Bricks zerstört sind gewinnt man.
					wincondition = true;
					break;
				}
				if (System.currentTimeMillis() > time + 20) {// Der Ball wird alle 20 millisekunden bewegt.
					ball.moveBall();
					view1.updateBall();
					a++;
					if (a % 10 == 0) { // Im LighthouseView wird der Ball um ein Pixel bewegt, wenn der ball sich im
										// model 10. mal bewegt hat.
						viewL.updateBall();

					}
					time = System.currentTimeMillis();
					// System.out.println("Versuch: " + versuch + " " + time);
					// versuch += 1;
				}
				if (BreakoutView.paddle.contains(x, y + BALL_SIZE / 2)) { // Prüft , ob der Ball das Paddle berührt und
																			// lässt diesen wieder nach Oben abprallen.
					Ball.speedY = -Ball.speedY;
				}
				if (y <= 0) { // Kollision der oberen Wand.
					Ball.speedY = -Ball.speedY;
				}
				if (x <= 0) { // Kollision an der linken Wand.
					Ball.speedX = -Ball.speedX;
				}
				if (x >= 280 - BALL_SIZE / 2) { // Kollision an der rechten Wand.
					Ball.speedX = -Ball.speedX;
				}
				for (int i = BRICK_ROW - 1; i >= 0; i--) { // prüft, ob an den Koordinaten des Balls ein Brick ist,
					for (int j = 0; j < BRICK_COLUMN; j++) { // wenn ja wird dieser entfernt und der Ball ändert seine
																// Richting.
						if (BreakoutView.bricks[i][j].contains(x, y)) {
							BRICKS[i][j] = false;
							view1.updateBricks();
							viewL.updateBricks();
							brickCount -= 1;
							System.out.println(brickCount);
							Ball.speedY = -Ball.speedY;
						}
					}
				}
			}
		}
	}
}
