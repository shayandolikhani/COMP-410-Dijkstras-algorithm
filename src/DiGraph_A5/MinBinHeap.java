package DiGraph_A5;

public class MinBinHeap implements Heap_Interface {
  private EntryPair[] array; //load this array
  private int size = 0;
  private static final int arraySize = 10000; //Everything in the array will initially 
                                              //be null. This is ok! Just build out 
                                              //from array[1]

  public MinBinHeap() {
    this.array = new EntryPair[arraySize];
    array[0] = new EntryPair(null, -100000); //0th will be unused for simplicity 
                                             //of child/parent computations...
                                             //the book/animation page both do this.
  }
    
  //Please do not remove or modify this method! Used to test your entire Heap.
  @Override
  public EntryPair[] getHeap() { 
    return this.array;
  }

@Override
public void insert(EntryPair entry) {
	if (entry == null) {
		return;
	}
	
	array[this.size + 1] = entry;
	this.size++;
	if (this.size != 1) {
	for (int i = this.size; i > 1; i /= 2) {
		if (array[i/2].getPriority() > entry.getPriority()) {
			EntryPair temp = array[i/2];
			array[i/2] = entry;
			array[i] = temp;
			}
		
		}
	}
}

@Override
public void delMin() {
	
	if (size == 1) {
		array[1] = null;
		size--;
		return;
	}
	
	else if (size > 1) {
	array[1] = null;
	EntryPair temp = array[size];
	array[size] = null;
	
	
	int i = 1;
	while (array[i*2] != null && array[i*2 + 1] != null && 
			(array[i*2].getPriority() < temp.getPriority() || array[i*2 +1].getPriority() < temp.getPriority())) {
		
		if (array[i*2].getPriority() < array[i*2 + 1].getPriority()) {
			if (array[i*2].getPriority() < temp.getPriority()) {
				EntryPair temp2= array[i];
				array[i] = array[i*2];
				array[i*2] = temp2;
				i *= 2;
				}
			}
		
		else if (array[i*2 +1].getPriority() < temp.getPriority()) {
				EntryPair temp2= array[i];
				array[i] = array[i*2 +1];
				array[i*2 + 1] = temp2;
				i = i *2 +1;
				}
	}
	
	while ((array[i*2] != null) && array[i*2].getPriority() < temp.getPriority()) {
			EntryPair temp2= array[i];
			array[i] = array[i*2];
			array[i*2] = temp2;
			i *= 2;
			}
		
		array[i] = temp;
		size--;
	}
	
	
}


@Override
public EntryPair getMin() {
	return array[1];
}

@Override
public int size() {
	return this.size;
}

@Override
public void build(EntryPair[] entries) {
	if (size > entries.length) {
		for (int i = entries.length; i < size; i++) {
			array[i] = null;
		}
	}
	this.size = 0;
	
	for (int i = 0; i < entries.length; i++) {
		if (entries[i] != null) {
		array[i + 1] = entries[i];
		this.size++;
		}
	}

	for (int i = this.size/2; i > 0; i--) {
		int current = i;
		while (current*2 + 1 <= size && 
		(array[current*2].getPriority() < array[current].getPriority() || array[current*2 +1].getPriority() < array[current].getPriority())) {
		
			
		if (array[current*2].getPriority() < array[current*2 + 1].getPriority()) {
				EntryPair temp2= array[current];
				array[current] = array[current*2];
				array[current*2] = temp2;
				current*= 2;
			}
		
		else  {
				EntryPair temp2= array[current];
				array[current] = array[current*2 +1];
				array[current*2 + 1] = temp2;
				current = current*2 +1;
				}
		}
	
	
	while (current*2 <= size && array[current*2].getPriority() < array[current].getPriority()) {
			EntryPair temp2= array[current];
			array[current] = array[current*2];
			array[current*2] = temp2;
			current *=2;
			}
	}
}
	






}