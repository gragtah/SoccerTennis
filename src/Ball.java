import java.lang.Math;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.Graphics;

public class Ball extends PhysicalEntity {
	// to simulate the loss of energy when the ball bounces. (currently only used for bounces off the ground.)
	private static final float RESTITUTION_COEFFICIENT = 0.9f;

	private Vector2f velocity;
	private Image image;

	//todo: this should be somewhere else
	private static Vector2f gravity = new Vector2f( 0.0f, SoccerTennis.GRAVITY );

	public Ball( int x, int y ) throws SlickException {
		super( x, y );
		
		this.velocity = new Vector2f( 0.6f, 0.0f ); // I guess the initial velocity will be 0 in the future
		this.image = new Image(SoccerTennis.RESOURCE_DIR + "ball.png");

		// todo: collision shape gets center x/y set, while image is drawn from minX/minY
		this.setCollisionShape(new Circle( 0, 0, 11.0f ));
	}
	
	// todo: create collidable interface or add collisions to PhysicalEntity or something
	private boolean collidingWith( Player player ) {
		return this.getCollisionShape().intersects( player.getCollisionShape() );
	}

	private void updateForCollisions( Player player ) {
		float tempx = velocity.getX();
		float tempy = velocity.getY();

		if ( SoccerTennis.DEBUG_BOUNCE_OFF_WALLS ) {
			if ( x > 1100 ) {
				x = 1100;
				velocity.set( -1 * Math.abs( tempx ), tempy );
			}

			if ( x < 0 ) {
				x = 0;
				velocity.set( Math.abs( tempx ), tempy );
			}
		}

		//updateForPlayerCollision( player );
		// collision = world.getCollision( this );
		if ( this.collidingWith( player ) ) {
			velocity.set( Math.abs( tempx ), tempy );
		}

		//updateForGroundCollision();
		// todo: figuring out this collision should really be the responsibility of a World class or something
		if ( y > 500 ) {
			// do we just negate the y velocity here?  i'm shit at physics, in case you couldn't tell.
			y = 500;

			// abs just to make sure we're going up.  it's silly, i know
			velocity.set( tempx, -1 * Math.abs( tempy ) * RESTITUTION_COEFFICIENT ); 
		 }
	}

	public void update( Player player, int delta ) {
		updateForCollisions( player );

		//updateForGravity();
		velocity.add( gravity.copy().scale( delta ) );

		// update for inertia
		x += velocity.getX() * delta;
		y += velocity.getY() * delta;

		// simulate drag?
	}

	public void draw(Graphics graphics) {
		image.draw( x, y );
	}
}
