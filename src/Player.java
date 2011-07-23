import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Player extends PhysicalEntity {
	private int width;
	private int height;

	private Image p1Still;
	private Animation p1RunRight;
	private Renderable p1;
	private float runSpeed;
	private float jumpSpeed;
	private float startingJumpSpeed;

	private boolean jumping = false;
	private boolean jumpingLeft = false;
	private boolean jumpingRight = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;

	private int recentlyPressedDirection = 0; // 0 left, 1 right

	// todo: player should jump with it's chest out and legs back to control the ball, rather than the sort of leaping thing it does now.

	public Player() throws SlickException {
		super( 100, 370 );

		runSpeed = 0.6f; 
		startingJumpSpeed = 0.75f;
		jumpSpeed = startingJumpSpeed;

		Image[] p1frames = {
			new Image(SoccerTennis.RESOURCE_DIR + "run0.png").getScaledCopy( 0.5f ), //.getScaledCopy( width, height );
			new Image(SoccerTennis.RESOURCE_DIR + "run1.png").getScaledCopy( 0.5f ), //.getScaledCopy( width, height );
			new Image(SoccerTennis.RESOURCE_DIR + "run3.png").getScaledCopy( 0.5f ), //.getScaledCopy( width, height );
			new Image(SoccerTennis.RESOURCE_DIR + "run6.png").getScaledCopy( 0.5f ), //.getScaledCopy( width, height );
			new Image(SoccerTennis.RESOURCE_DIR + "run7.png").getScaledCopy( 0.5f ), //.getScaledCopy( width, height );
			new Image(SoccerTennis.RESOURCE_DIR + "run8.png").getScaledCopy( 0.5f ), //.getScaledCopy( width, height );
			new Image(SoccerTennis.RESOURCE_DIR + "run9.png").getScaledCopy( 0.5f ), //.getScaledCopy( width, height );
			new Image(SoccerTennis.RESOURCE_DIR + "run10.png").getScaledCopy( 0.5f ), //.getScaledCopy( width, height );
		};
		p1RunRight = new Animation( p1frames, 200 );
		p1Still = new Image(SoccerTennis.RESOURCE_DIR + "still.gif").getScaledCopy( 0.5f ); //.getScaledCopy( width, height );
		p1 = p1Still;

		width = p1RunRight.getWidth();//52;
		height = p1RunRight.getHeight();//87;

		this.collisionShape = new Rectangle( x, y, width, height );
	}

	public void draw() {
		p1.draw( x, y );
	}

	public void update( GameContainer gc, int delta ) {
		pollInput( gc.getInput() );

		updateMoving( delta, gc.getWidth(), gc.getHeight() );
		updateJump( delta );
		p1RunRight.update( delta );

		if ( movingRight ) {
			p1 = p1RunRight;
		} else {
			p1 = p1Still;
		}
	}

	private void updateJump( int delta ) {
		if ( jumping ) {
			jumpSpeed -= SoccerTennis.GRAVITY * delta;
			y -= jumpSpeed * delta;
		}

		// land
		if ( y > 370 ) {
			y = 370;
			jumping = false;
			jumpingRight = false;
			jumpingLeft = false;
			jumpSpeed = startingJumpSpeed;
		}
	}	

	private void updateMoving( int delta, int screenWidth, int screenHeight ) {
		if ( movingLeft ) {
			if ( x - runSpeed > -50 ) { // hack (-50 should be 0)
				x -= runSpeed * delta;
			} else {
				x = -50;
			}
		} 
		
		if ( movingRight ) {
			if ( x + runSpeed < (screenWidth / 2) - width ) {
				x += runSpeed * delta;
			} else {
				x = (screenWidth / 2) - width;
			}
		}
	}

	private void pollInput( Input input ) {
		// todo: turn this into a keyboard listener
		// todo: can't jump if you're holding left and right down at the same time

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
