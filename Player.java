import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

public class Player {
	private int x;
	private int y;
	private int width;
	private int height;
	private int speed;

	private Image p1Still;
	private Animation p1RunRight;
	private Renderable p1;
	private boolean jumping;
	private int jumpSpeed;
	private int startingJumpSpeed;

	private boolean jumpingLeft = false;
	private boolean jumpingRight = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;

	private int recentlyPressedDirection = 0; // 0 left, 1 right

	public Player() throws SlickException {
		x = 100;
		y = 370;
		speed = 8; //todo acceleration. or don't bother?
		jumping = false;
		startingJumpSpeed = 15;
		jumpSpeed = startingJumpSpeed;

		Image[] p1frames = {
			new Image("run0.gif").getScaledCopy( 0.5f ), //.getScaledCopy( width, height );
			new Image("run1.gif").getScaledCopy( 0.5f ), //.getScaledCopy( width, height );
			new Image("run2.gif").getScaledCopy( 0.5f ), //.getScaledCopy( width, height );
			new Image("run3.gif").getScaledCopy( 0.5f ), //.getScaledCopy( width, height );
			new Image("run4.gif").getScaledCopy( 0.5f ), //.getScaledCopy( width, height );
			new Image("run5.gif").getScaledCopy( 0.5f ), //.getScaledCopy( width, height );
			new Image("run6.gif").getScaledCopy( 0.5f ), //.getScaledCopy( width, height );
			new Image("run7.gif").getScaledCopy( 0.5f ), //.getScaledCopy( width, height );
		};
		p1RunRight = new Animation( p1frames, 200 );
		p1Still = new Image("still.gif").getScaledCopy( 0.5f ); //.getScaledCopy( width, height );
		p1 = p1Still;

		width = p1RunRight.getWidth();//52;
		height = p1RunRight.getHeight();//87;
	}

	// not used yet iirc
	public Renderable getRenderable() {
		return p1;
	}

	public void draw() {
		p1.draw( x, y );
	}

	private void updateP1Jump() {
		if ( jumping ) {
			y -= jumpSpeed;
			jumpSpeed -= SoccerTennis.gravity;// uppercase globals.  (actually get rid of globals)
		}

		if ( y > 370 ) {
			y = 370;
			jumping = false;
			jumpingRight = false;
			jumpingLeft = false;
			jumpSpeed = startingJumpSpeed;
		}
	}	

	public void update(int delta) {
		updateP1Jump();
		p1RunRight.update( delta );

		if ( movingRight ) {
			p1 = p1RunRight;
		} else {
			p1 = p1Still;
		}
	}

	public void pollInput(GameContainer gc) {
		Input input = gc.getInput();

		// If left and right are both down, go with the most recently pressed
		if ( input.isKeyDown(Input.KEY_LEFT) && input.isKeyDown(Input.KEY_RIGHT) ) {
			if ( recentlyPressedDirection == 0 ) {
				movingLeft = false;
				movingRight = true;
			} else {
				movingLeft = true;
				movingRight = false;
			}
		} else {
			if ( input.isKeyDown(Input.KEY_LEFT) ) { //|| jumpingLeft ) {
				movingLeft = true;
				recentlyPressedDirection = 0;
			} else {
				movingLeft = false;
			}

			if ( input.isKeyDown(Input.KEY_RIGHT) ) { //|| jumpingRight ) {
				movingRight = true;
				recentlyPressedDirection = 1;
			} else {
				movingRight = false;
			}
		}

		if ( movingLeft ) {
			if ( x - speed > -50 ) { // hack (-50 should be 0)
				x = x - speed;
			} else {
				x = -50;
			}
		} 
		
		if ( movingRight ) {
			if ( x + speed < (gc.getWidth() / 2) - width ) {
				x = x + speed;
			} else {
				x = (gc.getWidth() / 2) - width;
			}
		}

		if ( input.isKeyDown(Input.KEY_SPACE) ) {
			if ( !jumping && input.isKeyDown(Input.KEY_LEFT) ) {
				jumpingLeft = true;
			}

			if ( !jumping && input.isKeyDown(Input.KEY_RIGHT) ) {
				jumpingRight = true;
			}

			if ( !jumping ) {
				jumping = true;
				// todo: figure out how to get stopAt() working.
				p1RunRight.setSpeed( 0.5f ); //.stop();
			}
		} else if ( ( !jumping || y > 330 ) ) { //&& p1RunRight.isStopped() ) {
			p1RunRight.setSpeed( 1.0f ); //.start();
		}
	}
}
