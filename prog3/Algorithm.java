import java.util.ArrayList;
import java.util.List;

public abstract class Algorithm {
	
	/* the number of page frames the process is allowed to use (a <= nf <= 30) */
	protected int nf;
	/* the number of pages in the process (1 <= np <= 300) */
	protected int np;
	/* the length of the reference string (1 <= nr <= 5000) */
	protected int nr;
	/* int to hold the amounts of page faults */
	protected int pageFault = 0;
	/* double to hold the fault rate */
	protected double faultRate = 0;
	/* array which holds the reference string */
	List<Integer> references = new ArrayList<Integer>();
	
	public Algorithm (int nf, int np, int nr, List references) {
		this.nf = nf;
		this.np = np;
		this.nr = nr;
		this.references = references;
	}
	
	public abstract void compute();
	public abstract void print();
	
}
