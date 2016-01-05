package ann.neat;

public class Main {

	public static void main(String[] args) {
		Genome g = new Genome(3,2);
		System.out.println(g.highestInnovationNumber());
		
		Genome h = new Genome(g);
		System.out.println(h.highestInnovationNumber());
	}

}
