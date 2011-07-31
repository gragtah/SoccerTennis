import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

/**
 * oh god no
 * todo: use this class to reuse code that is currently pretty much duplicated in Player and Ball
 */
public abstract class PhysicalEntity {
	// todo: replace x and y with vector? also, they should probably be floats
	protected int x;
	protected int y;
	public Shape collisionShape; // todo: generate from image by default?

	public PhysicalEntity( int x, int y ) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	protected Shape getCollisionShape() {
		// should we just update the shape's x/y every time we update the entity's?
		return collisionShape.transform( Transform.createTranslateTransform( x, y ) );
	}

	protected void setCollisionShape( Shape collisionShape ) {
		this.collisionShape = collisionShape.transform( Transform.createTranslateTransform( 0, 0 ) );
	}

	// todo: put update and draw in as abstract methods?
}
