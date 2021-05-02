//
//	Lighthouse Projekt: Breakout.				
//
//	Von Marvin Timm (stu210063) und Gunnar Leetz (stu209378)
//
//	Unser Projekt "Breakout" beinhaltet ein graphisches Spiel, welches 
//	aus mehreren "Bricks", einem "Ball" und einem "Paddle" besteht. Das Paddle 
//	befindet sich an dem unteren Spielfeld Rand und versucht immer wieder, auf
//	die Position des Balls zu kommen, um den wieder nach oben gegen die dort 
//	aufgestellten Bricks zu prallen, welche nach der Kollision mit dem Ball
//	gel�scht werden. 
//	Das Ziel des Spiels ist es, das zu Schluss keine Bricks mehr �brig sind. 
//	Falls das Paddle den Ball jedoch durchfallen l�sst, verliert der Spieler
//	Spiel.
//
//	Unser Spiel ist parallel auf einem g�ngigen Computer sowie auf dem Light-
//	house spielbar.
//
//	In unserem Projekt haben wir das MVC-Pattern verwendet, welches wir in 
//	f�nf unterschiedlichen Klassen umgesetzt haben. Das Projekt besteht aus
//	einer "BreakoutModel", einer "BreakoutView", einer "BreakoutControl", einer
//	"Ball" und einer "BreakoutViewLighthouse" Klasse.
//
//	In unsererem "BreakoutModel" initialisieren wir alle Konstanten und 
//	Klassen-Variablen, die f�r das Paddle, die Bricks und den Ball genutzt
//	werden. Zudem befinden sich in der Klasse alle Methoden, welche die 
//	Berechnung der Positionen beinhalten. Auch die Methode um zu ermitteln, ob es
//	sich um eine Kollision handelt ist in dieser Klasse implementiert.
//
//	In der "BreaoutControl" werden Befehle des Users entgegengenommen und
//	weitergeleitet, wie z.B. die "waitForClick()" Methode oder das Dr�cken
//	der einzelnen Kn�pfe um das Paddle zu bewegen.
//
//	Die "BreakoutView" arbeitet mit den Konstanten und Variablen die in dem 
//	Model bestimmt wurden und erstellt so Graphische Objekt auf einem 
//	GCompound. Durch das wiederholte aufrufen von Methoden, die die Positionen
//	einzelner Objekte ver�ndern, bewegen sich die Objekte dann auf dem GCompound.
//	Die Berechnung der Positionen findet allerdings in dem BreakoutModel statt,
//	die Daten werden dann nur an das BreakoutView weitergereicht.
//	Diese View-Klasse ist daf�r geeignet, auf einem g�ngigen Computer das Spiel
//	graphisch darzustellen, nicht f�r das Lighthouse.
//
//	Unsere "BreakoutViewLighthouse" Klasse dient einzig und allein der 
//	Darstellung des Spiels auf dem Lighthouse. Die Runterskalierung der einzelnen
//	Werte findet in dem BreakoutModel statt, welches dann die Wert wieder weiter 
//	an die BreakoutViewLighthouse Klasse gibt. Hier arbeiten wir nicht mehr mit
//	graphischen Objekten, sondern mit einem byte Array. Ein Fester des 
//	Lighthouses umfasst immer genau drei bytes, welche die Farbe bestimmen. Diese
//	28*14*3 bytes m�ssen f�r das darstellen der "Objekte" nun immer wieder 
//	angesprochen und ver�ndert werden. Wie genau wir das umgesetzt haben, 
//	findet man in unseren Kommentaren der BreakoutViewLighthouse- Klasse.
//
//	P.S.
//
//	Danke f�r die M�glichkeit, an so einem Projekt teilnehmen zu k�nnen! Es hat
//	uns, nach einigen Anfangsschwierigkeiten, doch sehr viel Spa� gebracht, 
//	mitzumachen!





package programming.projekt.breakout;

import java.awt.event.KeyEvent;

import acm.program.GraphicsProgram;

@SuppressWarnings("serial")
public class BreakoutControl extends GraphicsProgram {

	BreakoutView view = new BreakoutView();// Erstellt ein View und ein Model auf das der Controller zugreifen kann.
	BreakoutModel model = new BreakoutModel();

	/**
	 * Startet das Programm und wartet auf 2 Mausklicks. Beim ersten Mausklick wird
	 * das Spielfeld erstellt und beim zweitem Mausklick wird das Spiel gestartet.
	 * Das Spiel l�uft nun solange bis die wincondition einen Wert zugewiesen
	 * bekommt, dann wird einem angezeigt ob man das Spiel gewonnen oder verloren
	 * hat. Man kann das Spiel dann mit einem Mausklick neustarten.
	 * 
	 */
	public void run() {

		setSize(BreakoutModel.WIDTH, BreakoutModel.HEIGHT);

		waitForClick();

		model.initializeGame();

		addKeyListeners();

		add(view);

		view.createBall();
		view.createBricks();
		view.createPaddle();

		waitForClick();

		model.move();

		if (!BreakoutModel.wincondition) {
			view.looser();
		}
		if (BreakoutModel.wincondition) {
			view.winner();

		}
		waitForClick();
		view.reset();

	}

	/* 
	 * Sendet ein Signal an die movePaddle[..] Methode, wenn ein bestimmter Knopf gedr�ckt wird.
	 */
	public void keyPressed(KeyEvent e) {

		int keyCode = e.getKeyCode();

		switch (keyCode) {
		case KeyEvent.VK_LEFT:
			model.movePaddleLeft();
			break;
		case KeyEvent.VK_RIGHT:
			model.movePaddleRight();
			break;

		}

	}
}
