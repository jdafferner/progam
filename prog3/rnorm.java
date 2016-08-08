/*------------------------------------------------------------------*/
/* rnorm.java                                                       */
/*                                                                  */
/* Illustrate the generation of normally distributed random numbers */
/* with a specified mean and standard deviation. The generator is   */
/* adapted from Algorithm 712, Collected Algorithms of the ACM.     */
/*                                                                  */
/* For portability, we use a specific version of the rand function  */
/* that is part of the standard C library. To avoid conflicts with  */
/* the standard names, we use the following name substitutions:     */
/*                                                                  */
/*	rand	  -->	xrand                                       */
/*	srand	  -->	xsrand                                      */
/*	RAND_MAX  -->	XRAND_MAX                                   */
/*                                                                  */
/* The behavior of these functions, and the value of the RAND_MAX   */
/* constant, is expected to be the same as the standard C library.  */
/*------------------------------------------------------------------*/

import java.io.*;

public class rnorm {

    //----------------------------------------------
    // Portable version of srand, rand, and RAND_MAX
    //----------------------------------------------
    static final int XRAND_MAX = 65535;
    static int seed = 1;	// initial seed, last gen'd number

    static void xsrand(int xseed)	// set random number seed to xseed
    {
	seed = xseed;
    }

    static int xrand()			// generate next pseudo-random number
    {
        seed = seed * 1103515245 + 12345;
        return (seed >> 16) & XRAND_MAX;
    }

    static double sqr(double x)		// square a number
    {
	return x * x;
    }


    /*---------------------------------------------------------------------*/
    /* Return a pseudo-random number from a normal distribution with mean  */
    /* of 0 and variance of 1.                                             */
    /*---------------------------------------------------------------------*/
    static double rnorm ()
    {
	double s = 0.449871;
	double t = -0.386595;
	double a = 0.19600;
	double b = 0.25472;
	double r1 = 0.27597;
	double r2 = 0.27846;
	double u, v, x, y, q;

	for(;;) {
	    u = xrand() / (double)XRAND_MAX;	// u in 0..1
	    v = xrand() / (double)XRAND_MAX;	// v in 0..1
	    v = 1.7156 * (v - 0.5);

	    // Evaluate the quadratic form.
	    x = u - s;
	    y = Math.abs(v) - t;
	    q = sqr(x) + y * (a * y - b * x);

	    // Accept p if inside inner elipse
	    if (q < r1)
		break;
	    if (q <= r2 && sqr(v) < -4.0 * Math.log(u) * sqr(u))
		break;
	}

	// Return ratio of p's coordinates as the normal deviate.
	return v / u;
    }

    // Return a pseudo-random number from a normal distribution with mean
    // of 'mean' and standard deviation of 'stdev'.
    static double rnormms (double mean, double stdev)
    {
	return rnorm() * stdev + mean;
    }

    //----------------------------------------------------------------------
    // Test program to generate N random data values in normal distributions
    // with various means and standard deviations. For each test, the actual
    // and expected mean and standard deviation are displayed. 
    //----------------------------------------------------------------------

    static final int N = 1000000;
    static double amean = 0.0;		// mean (from calc)
    static double astdev = 0.0;		// standard deviation (from calc)

    //-------------------------------------------------------------------
    // Perform the actual calculations for the test program. The expected
    // mean and standard deviation are 'mean' and 'stdev'; the calculated
    // values are stored in global variables 'amean' and 'astdev'.
    //-------------------------------------------------------------------
    static void calc(double mean, double stdev)
    {
	int i;
	double v;
	double sum, deviation;

	sum = 0;
	deviation = 0;
	for (i=0;i<N;i++) {
	    v = rnormms(mean,stdev);
	    sum += v;
	    deviation += sqr(mean - v);
	}
	amean = sum / N;
	astdev = Math.sqrt(deviation / N);
    }

    static final int NCASES = 6;

 /*   public static void main(String args[])
    {
	// Test cases: each row has desired mean and standard deviation
	double[] cases = {
	     0.0, 1.0,
	     10.0, 5.0,
	     -2.5, 2.5,
	     5.0, 1.0,
	     50.0, 25.0,
	     99.0, 0.5
	};

	int i;

	// Display headings
	System.out.println("   Expected    Expected      Actual      Actual");
	System.out.println("       Mean       Stdev        Mean       Stdev");
	System.out.println("----------- ----------- ----------- -----------");

	// For each desired mean and standard deviation, generate N
	// pseudo-random numbers which are expected to have the desired
	// mean and standard deviation; this is done by the calc function.
	// That function also computes the actual mean and standard deviation
	// of the generated values. Here we display the desired mean and
	// standard deviation and the actual mean and standard deviation.

	for (i=0;i<NCASES;i++) {
	    calc(cases[2*i], cases[2*i+1]);
	    System.out.format("%11.8f %11.8f %11.8f %11.8f",
		cases[2*i], cases[2*i+1], amean, astdev);
	    System.out.println();
	}
    } */
}
