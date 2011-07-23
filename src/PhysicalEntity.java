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
	protected Shape collisionShape; // todo: generate from image by default?

	public PhysicalEntity( int x, int y ) {
		this.x = x;
		this.y = y;
	}

	public Shape getCollisionShape() {
		return collisionShape.transform( Transform.createTranslateTransform(x, y) );
	}

	// todo: put update and draw in as abstract methods?
}
