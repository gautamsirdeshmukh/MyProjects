

/**
 * Celestial Body class for NBody
 * @author Gautam Sirdeshmukh
 *
 */
public class CelestialBody {

	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;

	/**
	 * Create a Body from parameters	
	 * @param xp initial x position
	 * @param yp initial y position
	 * @param xv initial x velocity
	 * @param yv initial y velocity
	 * @param mass of object
	 * @param filename of image for object animation
	 */
	public CelestialBody(double xp, double yp, double xv,
			             double yv, double mass, String filename){
		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;

	}

	/**
	 * Copy constructor: copy instance variables from one
	 * body to this body
	 * @param b used to initialize this body
	 */
	public CelestialBody(CelestialBody b){
		myXPos = getX();
		myYPos = getY();
		myXVel = getXVel();
		myYVel = getYVel();
		myMass = getMass();
		myFileName = getName();
	}
	/**
	 * Return x-position of this Body.
	 * @return value of x-position.
	 */
	public double getX() {
		return myXPos;
	}
	/**
	 * Return y-position of this Body.
	 * @return value of y-position.
	 */
	public double getY() {
		return myYPos;
	}
	/**
	 * Return x-velocity of this Body.
	 * @return value of x-velocity.
	 */
	public double getXVel() {
		return myXVel;
	}
	/**
	 * Return y-velocity of this Body.
	 * @return value of y-velocity.
	 */
	public double getYVel() {
		return myYVel;
	}
	
	public double getMass() {
		return myMass;
	}
	public String getName() {
		return myFileName;
	}

	/**
	 * Return the distance between this body and another
	 * @param b the other body to which distance is calculated
	 * @return distance between this body and b
	 */
	public double calcDistance(CelestialBody b) {
		double ret;
		ret = ((myXPos - b.getX()) * (myXPos - b.getX())) + ((myYPos - b.getY()) * (myYPos - b.getY()));
		return Math.sqrt(ret);
	}
	/**
	 * Return the force exerted by the body
	 * @param b the body of which force exerted is calculated
	 * @return force exerted by the body
	 */
	public double calcForceExertedBy(CelestialBody b) {
		double G = 6.67E-11;
		double m1 = myMass;
		double m2 = b.getMass();
		double rad = calcDistance(b);
		return ((G * m1 * m2) / (rad * rad));
	}
	/**
	 * Return the force exerted by the body in the x direction
	 * @param b the body of which force exerted is calculated
	 * @return force exerted by the body in the x direction
	 */
	public double calcForceExertedByX(CelestialBody b) {
		double rad = calcDistance(b);
		double F = calcForceExertedBy(b);
		return ((F * (b.getX() - myXPos)) / rad);
	}
	/**
	 * Return the force exerted by the body in the y direction
	 * @param b the body of which force exerted is calculated
	 * @return force exerted by the body in the y direction
	 */
	public double calcForceExertedByY(CelestialBody b) {
		double rad = calcDistance(b);
		double F = calcForceExertedBy(b);
		return ((F * (b.getY() - myYPos)) / rad);
	}
	/**
	 * Return the net force exerted by all bodies in the x direction
	 * @param bodies the bodies of which force exerted is calculated
	 * @return net force exerted by all bodies in the x direction
	 */
	public double calcNetForceExertedByX(CelestialBody[] bodies) {
		double total = 0;
		for (CelestialBody body : bodies) {
			if (! body.equals(this)) {
				total = total + calcForceExertedByX(body);
			}
		}
		return total;
	}
	/**
	 * Return the net force exerted by all bodies in the y direction
	 * @param bodies the bodies of which force exerted is calculated
	 * @return net force exerted by all bodies in the y direction
	 */
	public double calcNetForceExertedByY(CelestialBody[] bodies) {
		double total = 0;
		for (CelestialBody body : bodies) {
			if (! body.equals(this)) {
				total = total + calcForceExertedByY(body);
			}
		}
		return total;
	}
	/**
	 * Updates the parameters of the bodies
	 * @param deltaT the time steps
	 * @param xforce net force of all bodies in the x direction
	 * @param yforce net force of all bodies in the y direction
	 * no @return
	 */
	public void update(double deltaT, 
			           double xforce, double yforce) {
		double fx = xforce;
		double ax = (fx / myMass);
		double nvx = myXVel + (deltaT * ax);
		double nx = myXPos + (deltaT * nvx);
		double fy = yforce;
		double ay = (fy / myMass);
		double nvy = myYVel + (deltaT * ay);
		double ny = myYPos + (deltaT * nvy);
		myXPos = nx;
		myYPos = ny;
		myXVel = nvx;
		myYVel = nvy;

	}
	/**
	 * Draws the celestial bodies
	 * no @param
	 * no @return
	 */
	public void draw() {
		StdDraw.picture(myXPos, myYPos, "images/"+myFileName);
	}
}
