	

/**
 * @author YOUR NAME THE STUDENT IN 201
 * 
 * Simulation program for the NBody assignment
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class NBody {
	
	/**
	 * Read the specified file and return the radius
	 * @param fname is name of file that can be open
	 * @return the radius stored in the file
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static double readRadius(String fname) throws FileNotFoundException  {
		Scanner s = new Scanner(new File(fname));

		s.nextLine();
		double rad = s.nextDouble();
		
		s.close();
		return rad;
	}
	
	/**
	 * Read all data in file, return array of Celestial Bodies
	 * read by creating an array of Body objects from data read.
	 * @param fname is name of file that can be open
	 * @return array of Body objects read
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static CelestialBody[] readBodies(String fname) throws FileNotFoundException {
		
			Scanner s = new Scanner(new File(fname));

			int nb = s.nextInt(); // # bodies to be read
			CelestialBody[] bodies = new CelestialBody[nb];
			s.nextLine();
			s.nextLine();
			for(int k=0; k < nb; k++) {
				double xPos = s.nextDouble();
				double yPos = s.nextDouble();
				double xVel = s.nextDouble();
				double yVel = s.nextDouble();
				double mass = s.nextDouble();
				String name = s.next();
				CelestialBody body = new CelestialBody(xPos, yPos, xVel, yVel, mass, name);
				bodies[k] = body;
			}
			
			s.close();
			
			// TODO: return array of body objects read
			return bodies;
	}
	public static void main(String[] args) throws FileNotFoundException{
		double totalTime = 39447000.0;
		double dt = 25000.0;
		
		String fname= "data/kaleidoscope.txt";
		if (args.length > 2) {
			totalTime = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			fname = args[2];
		}	
		
		CelestialBody[] bodies = readBodies(fname);
		double radius = readRadius(fname);

		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0,0,"images/starfield.jpg");
		//StdAudio.play("images/2001.wav");
	
		// run simulation until time up

		for(double t = 0.0; t < totalTime; t += dt) {
			
			// TODO: create double arrays xforces and yforces
			double[] xforces = new double[bodies.length];
			double[] yforces = new double[bodies.length];
			// TODO: loop over all bodies, calculate netForcesX and Y
			for (int i = 0; i < bodies.length; i++) {
				xforces[i] = bodies[i].calcNetForceExertedByX(bodies);
				yforces[i] = bodies[i].calcNetForceExertedByY(bodies);
			}
			
			// TODO: loop over all bodies and call update
			// with dt and corresponding xforces, yforces values
			for (int i = 0; i < bodies.length; i++) {
				bodies[i].update(dt, xforces[i], yforces[i]);
			}
			
			StdDraw.picture(0,0,"images/starfield.jpg");
			
			// TODO: loop over all bodies and call draw on each one
			for (int i = 0; i < bodies.length; i++) {
				bodies[i].draw();
			}

			StdDraw.show();
			StdDraw.pause(10);
		}
		
		// prints final values after simulation
		
		System.out.printf("%d\n", bodies.length);
		System.out.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
		    System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		   		              bodies[i].getX(), bodies[i].getY(), 
		                      bodies[i].getXVel(), bodies[i].getYVel(), 
		                      bodies[i].getMass(), bodies[i].getName());	
		}
	}
}
