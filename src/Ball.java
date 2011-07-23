import java.lang.Math;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Ball extends PhysicalEntity {
	private Vector2f velocity;
	private Image image;

	//todo: this should be somewhere else
	private static Vector2f gravity = new Vector2f( 0.0f, SoccerTennis.GRAVITY );

	public Ball( int x, int y ) throws SlickException {
		super( x, y );
		
		this.velocity = new Vector2f( 1.0f, 0.0f ); // I guess the initial velocity will be 0 in the future
		this.image = new Image(SoccerTennis.RESOURCE_DIR + "ball.png");
	}

	// todo: create collidable interface or add collisions to PhysicalEntity or something
	private boolean collidingWith( Player player ) {
		return false;
	}

	public void update( Player player, int delta ) {
		//updateForPlayerCollision( player );
		// collision = world.getCollision( this );
		if ( x > 1100 || this.collidingWith( player ) ) {
			if ( x > 1100 ) 
				x = 1100;

			float tempx = velocity.getX();
			float tempy = velocity.getY();
			velocity.set( -1 * tempx, tempy );
		}

		//updateForGroundCollision();
		// todo: figuring out this collision should really be the responsibility of a World class or something
		if ( y > 370 ) {
			// do we just negate the y velocity here?  i'm shit at physics, in case you couldn't tell.
			y = 370;
			float tempx = velocity.getX();
			float tempy = velocity.getY();
			velocity.set( tempx, -1 * Math.abs( tempy ) ); // abs just to make sure we're going up.  it's silly, i know
		}

		//updateForGravity();
		velocity.add( gravity.copy().scale( delta ) );

		// update for inertia
		x += velocity.getX() * delta;
		y += velocity.getY() * delta;

		// simulate drag?
	}

	public void draw() {
		image.draw( x, y );
	}
}
