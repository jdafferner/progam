import java.util.ArrayList;
import java.util.List;

public class OPT2 extends Algorithm {
	
	/* int to hold furthest referenced page */
	private int lastRef;
	/* int to hold number of current pages */
	private int pageCount;
	/* page table array */
	int[][] pageTable;
	
	/* Constructor accepts all arguments necessary for an algorithm and initializes the page table array */
	public OPT2(int nf, int np, int nr, List<Integer> references) {
		super(nf, np, nr, references);
		pageTable = new int[nf][2];
	}
	
	/** This method computes the amount of page faults which would occur by using the OPT (optimal page
	 *  transfered) algorithm.
	 */
	@Override
	public void compute() {
		pageCount = 0;
		for(int inc = 0; inc < nr; inc++) {
			if(contains(references.get(inc)) != -1) {
				continue;
			}
			else if(pageCount < nf) {
				pageTable[pageCount][0] = references.get(inc);
				pageTable[pageCount][1] = getLastRef(pageTable[pageCount][0], inc);
				/* TODO : set index for page to be replaced */
				pageCount++;
				pageFault++;
			}
			else { /* TODO : rework so that each time a page is replaced, the page with the farthest reference
							 point is updated in real time as is the index for the next page to be replaced */
				/* index of the page table to be replaced */
				int index = 0;
				/* copy of the current page table */
				int currentPageTable[][] = new int[nf][2];
				for (int i = 0; i < nf; i++) {
					currentPageTable[i][0] = pageTable.get(i);
					currentPageTable[i][1] = -1;
				}
				/* copy of the current reference string */
				int currentRefString[] = new int[nr-inc];
				for (int i = 0; i < nr - inc; i++) {
					currentRefString[i] = references.get(inc+i);
				}
				/* fill the second row of the matrix with the amount of iterations until finding the corresponding
				 * first row's page number. If the reference is not found, currentPageTable[i][1] remains -1
				 */
				for (int i = 0; i < currentPageTable.length; i++) {
					lastRef = 0;
					for (int j = 0; j < currentRefString.length; j++) {
						if ( currentPageTable[i][0] == currentRefString[j] ) {
							currentPageTable[i][1] = lastRef;
							break;
						}
						else {
							lastRef++;
						}
					}
				}
				lastRef = 0;
				/* loop through the array to find the page referenced furthest away */
				for (int i = 0; i < currentPageTable.length; i++) {
					/* if the reference was never found, this page can be safely replaced */
					if (currentPageTable[i][1] == -1) {
						index = i;
						break;
					}
					/* otherwise, find the page with the furthest reference point to replace */
					else if (currentPageTable[i][1] >= lastRef) {
						lastRef = currentPageTable[i][1];
						index = i;
					}
				}
				/* replace the page table entry */
				pageTable[index][0] = references.get(inc);
				/* log the fault */
				pageFault++;
			}
		}
		faultRate = (double) pageFault / (double) nr;
	}
	
	private int getLastRef(int val, int inc) {
		int currentRefString[] = new int[nr-inc];
		for (int i = 0; i < nr - inc; i++) {
			currentRefString[i] = references.get(inc+i);
		}
		int x = 0;
		for (int i = 0; i < currentRefString.length; i++) {
			if ( val == currentRefString[i] )
				return x;
			else
				x++;
		}
		return -1;
	}

	public int contains(int var) {
		for(int i = 0; i < pageTable.length; i++) {
			if (pageTable[i][0] == var)
				return i;
		}
		return -1;	
	}
	
	@Override
	public void print() {
		System.out.printf("\tOPT: %d faults, fault rate = %.3f\n", pageFault, faultRate);
		
	}
	
}
