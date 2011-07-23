/**
 * oh god no
 * todo: use this class to reuse code that is currently pretty much duplicated in Player and Ball
 */
public abstract class PhysicalEntity {
	// todo: replace x and y with point?
	protected int x;
	protected int y;

	public PhysicalEntity( int x, int y ) {
		this.x = x;
		this.y = y;
	}
}
