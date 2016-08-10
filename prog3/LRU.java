import java.util.List;

public class LRU extends Algorithm {

	public LRU(int nf, int np, int nr, List references) {
		super(nf, np, nr, references);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void compute() {
		int duration[];
		duration = new int[nf];
		pageCount = 0;
		for(int i = 0; i < nr; i++){
			for(int j = 0; j < nf; j++){
				duration[j]++;
			}
			if(pageTable.contains(references.get(i))){
				int l = pageTable.indexOf(references.get(i));
				duration[l] = 0;
				continue;
			}
			else if(pageCount < nf){
				pageTable.add(references.get(i));
				duration[pageCount] = 0;
				pageCount++;
				pageFault++;
			}
			else{
				int max = 0;
				for(int j = 0; j < nf; j++){
					if(duration[j] > duration[max]){
						max = j;
					}
				}
					pageTable.remove(max);
					pageTable.add(references.get(i));
					duration[max] = 0;
					pageFault++;
				}
			}
		faultRate = (double)pageFault / (double)nr;
		print();

	}

	@Override
	public void print() {
		System.out.printf("\tLRU: %d faults, fault rate = %.3f\n", pageFault, faultRate);
	}

}
