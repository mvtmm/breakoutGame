package programming.projekt.breakout;

public class Ball {

	Ball() {
		BreakoutModel.x = BreakoutModel.startBallPX;
		BreakoutModel.y = BreakoutModel.startBallPY;
	}

	final int RADIUS = 2;

	static int speedX = 1;
	static int speedY = -1;

	void moveBall() {

		BreakoutModel.x += speedX;
		BreakoutModel.y += speedY;
	}

	void resetBall() {
		BreakoutModel.x = BreakoutModel.startBallPX;
		BreakoutModel.y = BreakoutModel.startBallPY;
	}
}