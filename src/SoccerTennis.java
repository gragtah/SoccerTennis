import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/*
 * I'm trying out the write-terrible-code philosophy where you assume you will just
 * refactor later when you can be bothered.
 */

public class SoccerTennis extends BasicGame {
	private Image stage;
	private Player p1;
	private Ball ball;

	public static final boolean DEBUG_BOUNCE_OFF_WALLS = true;
	public static final boolean DEBUG_DRAW_COLLISION_BOUNDARIES	 = true;

	// Acceleration in some distance unit per millisecond, I guess.
	public static final float GRAVITY = 0.0027f; 
	public static final String RESOURCE_DIR = "res/";

	private static final int WIN_WIDTH = 1200;
	private static final int WIN_HEIGHT = 600;

	private void pollInput( GameContainer gc ) {
		Input input = gc.getInput();

		if ( input.isKeyDown( Input.KEY_Q ) ) {
			gc.exit();
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		stage = new Image(SoccerTennis.RESOURCE_DIR + "stage0.png").getScaledCopy( WIN_WIDTH, WIN_HEIGHT );
		p1 = new Player();
		ball = new Ball( 0, 400 );

		gc.setVSync( true ); //setTargetFrameRate( 60 );
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		pollInput( gc );

		// I don't like this business of passing the gamecontainer around all the time.
		p1.update( gc, delta );
		ball.update( p1, delta );
	}

	public SoccerTennis() {
		super("Untitled ST");
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		stage.draw( 0, 0 );
		p1.draw();
		ball.draw(g);

		if ( DEBUG_DRAW_COLLISION_BOUNDARIES ) {
			g.draw(p1.getCollisionShape());
			g.draw(ball.getCollisionShape());
		}
	}

	public static void main (String[] argv) throws SlickException {
		AppGameContainer app = new AppGameContainer( new SoccerTennis() );
		 
		app.setDisplayMode( WIN_WIDTH, WIN_HEIGHT, false );
		app.start();
	}
}
