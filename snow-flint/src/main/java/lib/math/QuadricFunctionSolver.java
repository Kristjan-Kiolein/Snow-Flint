package lib.math;

import java.util.Scanner;

public class QuadricFunctionSolver {
	public static void main(String[] args) {
		double discr, root1, root2;
		System.out.println("Applying the quadratic formula");
		Scanner input = new Scanner(System.in);
		double a = input.nextDouble();
		double b = input.nextDouble();
		double c = input.nextDouble();

		// Solve the discriminant (SQRT (b^2 - 4ac)
		discr = Math.sqrt((b * b) - (4 * a * c));
		System.out.println("Discriminant = " + discr);

		// Determine number of roots
		// if discr> 0 equation has 2 real roots
		// if discr == 0 equation has a repeated real root
		// if discr< 0 equation has imaginary roots
		// if discr is NaN equation has no roots

		if (Double.isNaN(discr))
			System.out.println("Equation has no roots");
		if (discr > 0) {
			System.out.println("Equation has 2 roots");
			root1 = (-b + discr) / 2 * a;
			root2 = (-b - discr) / 2 * a;
			System.out.println("First root = " + root1);
			System.out.println("Second root = " + root2);
		}
		if (discr == 0) {
			System.out.println("Equation has 1 root");
			root1 = (-b + discr) / 2 * a;
			System.out.println("Root = " + root1);
		}
		if (discr < 0)
			System.out.println("Equation has imaginary roots");
	}
}