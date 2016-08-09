import java.util.ArrayList;
import java.util.List;

public class FIFO extends Algorithm {
	
	private int pageCount;
	private int pageFault;
	List<Integer> pageTable;
	
	public FIFO(int nf, int np, int nr, List references) {
		super(nf, np, nr, references);
		pageTable = new ArrayList<Integer>(nf);
	}

	@Override
	public void compute() {
		pageCount = 0;
		if(pageTable.contains(references.get(inc))){
			continue;
		}
		else if(pageCount < nf){
			pageTable.add(pageCount, references.get(inc));
			pageCount++;
			pageFault++;	
		}
		else{
			
		}
		

	}

	@Override
	public void print() {
		// TODO Auto-generated method stub

	}

}
