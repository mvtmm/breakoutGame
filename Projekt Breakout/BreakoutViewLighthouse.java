package programming.projekt.breakout;

import java.io.IOException;

/**
 * Diese Klasse skaliert und erstellt das Spiel auf der Lighthouse API.
 *
 */
public class BreakoutViewLighthouse {

	private int a = 0;
	byte[] windows = new byte[1176];
	Ball ballObject = new Ball();

	LighthouseDisplay lighthouse = new LighthouseDisplay("marvin-_timm", "API-TOK_v0WD-oFdb-ZFRW-dcvA-/vLF", 1);
	{
		try {
			lighthouse.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Width = 84 bytes (3 bytes pro fenster)
	// Height = 42 bytes
	// Insgesamt = 1176 bytes

	/**
	 * Hier wird jedes Byte auf 0 gesetzt um die gewünschte Hintergrundfarbe
	 * "schwarz" zu erzeugen.
	 */
	void initializeLighthouse() {

		while (true) {

			for (int i = 0; i < windows.length; i += 3) { // Für jedes Fenster werden die bytes auf 0 gesetzt.
				if (a > 391) {
					break;
				}
				windows[i] = (byte) 0;
				windows[i + 1] = (byte) 0;
				windows[i + 2] = (byte) 0;
				a++;

			}
			if (a > 391) {
				break;

			}

		}

		try {
			lighthouse.send(windows); // Sendet das gesamte Array.
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Diese Methode "baut" die einzelnen Bricks in das Lighthouse ein. Pro Brick
	 * werden sieben Fenster genutzt, weshalb auch die for-Schleife um je 21 Stellen
	 * addiert wird, da sieben Fenster aus insgesamt 21 bytes bestehen.
	 */
	void createBricks() {

		for (int i = 0; i <= 251; i += 21) {
			if (i >= 0 && i < 1176) {
				windows[i + 0] = (byte) 0;
				windows[i + 1] = (byte) 255; // Fenster 1
				windows[i + 2] = (byte) 0;

				windows[i + 3] = (byte) 0;
				windows[i + 4] = (byte) 255; // Fenster 2
				windows[i + 5] = (byte) 0;

				windows[i + 6] = (byte) 0;
				windows[i + 7] = (byte) 255; // Fenster 3
				windows[i + 8] = (byte) 0;

				windows[i + 9] = (byte) 0;
				windows[i + 10] = (byte) 255; // Fenster 4
				windows[i + 11] = (byte) 0;

				windows[i + 12] = (byte) 0;
				windows[i + 13] = (byte) 255; // Fenster 5
				windows[i + 14] = (byte) 0;

				windows[i + 15] = (byte) 0;
				windows[i + 16] = (byte) 255; // Fenster 6
				windows[i + 17] = (byte) 0;

				windows[i + 18] = (byte) 0;
				windows[i + 19] = (byte) 255; // Fenster 7
				windows[i + 20] = (byte) 0;
			}

			try {
				lighthouse.send(windows); // Sendet das gesamte Array.
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Der "Ball", welcher genau ein Fenster groß sein soll, wird hier an
	 * gewünschter Position erstellt und die Farbe gewählt.
	 */
	void createBall() {

		int ball = BreakoutModel.yL + BreakoutModel.xL;

		if (ball > 0 && ball < 1177) {
			windows[ball] = (byte) 255;
			windows[ball + 1] = (byte) 0;
			windows[ball + 2] = (byte) 0;
			System.out.println("Ball created");
			try {
				lighthouse.send(windows); // Sendet das gesamte Array.
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sobald sich der Ball bewegt, wird als erstes das Fenster, an dem der Ball
	 * sich befindet auf schwarz gesetzt und dann das Fenster, wohin sich der Ball
	 * bewegt, auf weiß gesetzt.
	 */
	void updateBall() {

		int ball = BreakoutModel.yL + BreakoutModel.xL; // RICHTIGE BALLKOORDINATEN BERECHNEN!!!!

		if (ball > 0 && ball < 1176) {
			windows[ball] = (byte) 0;
			windows[ball + 1] = (byte) 0;
			windows[ball + 2] = (byte) 0;
			System.out.println("Ball created");
		}
		if (Ball.speedX == -1 && BreakoutModel.xL >= 3) {
			BreakoutModel.xL -= 3;
		}
		if (Ball.speedX == 1 && BreakoutModel.xL <= 81) {
			BreakoutModel.xL += 3;
		}
		if (Ball.speedY == -1 && BreakoutModel.yL >= 84) {
			BreakoutModel.yL -= 84;
		}
		if (Ball.speedY == 1 && BreakoutModel.yL <= 1092) {
			BreakoutModel.yL += 84;
		}
		ball = BreakoutModel.yL + BreakoutModel.xL;
		windows[ball] = (byte) 255;
		windows[ball + 1] = (byte) 0;
		windows[ball + 2] = (byte) 0;
		System.out.println("Ball update");
		try {
			lighthouse.send(windows); // Sendet das gesamte Array.
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Checkt ob der Brick an einer bestimmten Stelle noch vorhanden ist, wenn
	 * nicht, setzt er den gesamten Brick wieder auf schwarz.
	 */
	void updateBricks() {
		for (int i = 0; i < BreakoutModel.BRICK_ROW; i++) {
			for (int j = 0; j < BreakoutModel.BRICK_COLUMN; j++) {
				if (!BreakoutModel.BRICKS[i][j]) {
					int k = (i * 84 + j * 21);
					if (k > 0 && k < 1177) {
						windows[k] = (byte) 0;
						windows[k + 1] = (byte) 0;
						windows[k + 2] = (byte) 0;

						windows[k + 3] = (byte) 0;
						windows[k + 4] = (byte) 0; // Fenster
						windows[k + 5] = (byte) 0;

						windows[k + 6] = (byte) 0;
						windows[k + 7] = (byte) 0; // Fenster
						windows[k + 8] = (byte) 0;

						windows[k + 9] = (byte) 0;
						windows[k + 10] = (byte) 0; // Fenster
						windows[k + 11] = (byte) 0;

						windows[k + 12] = (byte) 0;
						windows[k + 13] = (byte) 0; // Fenster
						windows[k + 14] = (byte) 0;

						windows[k + 15] = (byte) 0;
						windows[k + 16] = (byte) 0; // Fenster
						windows[k + 17] = (byte) 0;

						windows[k + 18] = (byte) 0;
						windows[k + 19] = (byte) 0; // Fenster
						windows[k + 20] = (byte) 0;

					}

				}
			}
		}
	}

	/**
	 * Erstellt ein Paddle an einer vorgegebenen Position und mit der Breite von
	 * fünf Fenstern / 15 bytes.
	 */
	void createPaddle() {
		int paddle = BreakoutModel.paddlexLStart + BreakoutModel.paddleyLStart;

		windows[paddle] = (byte) 0;
		windows[paddle + 1] = (byte) 255;
		windows[paddle + 2] = (byte) 255;

		windows[paddle + 3] = (byte) 0;
		windows[paddle + 4] = (byte) 255;
		windows[paddle + 5] = (byte) 255;

		windows[paddle + 6] = (byte) 0;
		windows[paddle + 7] = (byte) 255;
		windows[paddle + 8] = (byte) 255;

		windows[paddle + 9] = (byte) 0;
		windows[paddle + 10] = (byte) 255;
		windows[paddle + 11] = (byte) 255;

		windows[paddle + 12] = (byte) 0;
		windows[paddle + 13] = (byte) 255;
		windows[paddle + 14] = (byte) 255;

		System.out.println("paddle created");

		try {
			lighthouse.send(windows); // Sendet das gesamte Array.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Wenn sich das Paddle nach Links bewegt, wird das äußerste rechte Fenster, wo
	 * sich das Paddle vorher noch befand auf schwarz gesetzt und das äußerste Linke
	 * Fenster von der neuen Position des Paddles auf die Paddle-Farbe gesetzt.
	 */
	void updatePaddleLeft() {
		int paddle = BreakoutModel.paddlexL + BreakoutModel.paddleyL;

		windows[paddle] = (byte) 0;
		windows[paddle + 1] = (byte) 255;
		windows[paddle + 2] = (byte) 255;

		windows[paddle + 3] = (byte) 0;
		windows[paddle + 4] = (byte) 255;
		windows[paddle + 5] = (byte) 255;

		windows[paddle + 6] = (byte) 0;
		windows[paddle + 7] = (byte) 255;
		windows[paddle + 8] = (byte) 255;

		windows[paddle + 9] = (byte) 0;
		windows[paddle + 10] = (byte) 255;
		windows[paddle + 11] = (byte) 255;

		windows[paddle + 12] = (byte) 0;
		windows[paddle + 13] = (byte) 255;
		windows[paddle + 14] = (byte) 255;

		windows[paddle + 15] = (byte) 0;
		windows[paddle + 16] = (byte) 0;
		windows[paddle + 17] = (byte) 0;

		windows[paddle + 18] = (byte) 0;
		windows[paddle + 19] = (byte) 0;
		windows[paddle + 20] = (byte) 0;

		System.out.println("paddle created");

		try {
			lighthouse.send(windows); // Sendet das gesamte Array.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Wenn sich das Paddle nach Rechts bewegt, wird das äußerste linke Fenster, wo
	 * sich das Paddle vorher noch befand auf schwarz gesetzt und das äußerste
	 * rechte Fenster von der neuen Position des Paddles auf die Paddle-Farbe
	 * gesetzt.
	 */
	void updatePaddleRight() {
		int paddle = BreakoutModel.paddlexL + BreakoutModel.paddleyL;

		windows[paddle - 3] = (byte) 0;
		windows[paddle - 2] = (byte) 0;
		windows[paddle - 1] = (byte) 0;

		windows[paddle] = (byte) 0;
		windows[paddle + 1] = (byte) 0;
		windows[paddle + 2] = (byte) 0;

		windows[paddle + 3] = (byte) 255;
		windows[paddle + 4] = (byte) 255;
		windows[paddle + 5] = (byte) 0;

		windows[paddle + 6] = (byte) 255;
		windows[paddle + 7] = (byte) 255;
		windows[paddle + 8] = (byte) 0;

		windows[paddle + 9] = (byte) 255;
		windows[paddle + 10] = (byte) 255;
		windows[paddle + 11] = (byte) 0;

		windows[paddle + 12] = (byte) 255;
		windows[paddle + 13] = (byte) 255;
		windows[paddle + 14] = (byte) 0;
		if (paddle < 1160) {

			windows[paddle + 15] = (byte) 255;
			windows[paddle + 16] = (byte) 255;
			windows[paddle + 17] = (byte) 0;
		}

		System.out.println("paddle created");

		try {
			lighthouse.send(windows); // Sendet das gesamte Array.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
