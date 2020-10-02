package adv_algo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

//we did continue working on this class to show the partition!- to be continued later

public class Room {
	int capacity;
	int room_number;
	int available_space;
	Queue<Combination> queue = new LinkedList<Combination>();

	public Room(int av,int num, int n) {
		available_space=av;
		capacity = n;
		room_number = num;
	}

	public void print() {
		String tableau[][] = new String[3][this.queue.size()];
		int j = 0;
		Iterator it = queue.iterator();
		Combination a;
		while (it.hasNext()) {
			a = (Combination) it.next();
			if (!contains(a.exam.name, tableau, queue.size())) {
				tableau[0][j] = a.exam.name;
				tableau[3][j++]=Integer.toString(a.exam.start_hour);
			}
		}
		int i = 0;
		int sum;
		while (tableau[0][i] != null) {
			Iterator itt = this.queue.iterator();
			sum = 0;
			while (itt.hasNext()) {
				a = (Combination) itt.next();
				if (a.exam.name.equals(tableau[0][i]))
					sum++;
			}tableau[1][i]=Integer.toString(sum);i++;
		}
		for(i=0;tableau[0][i]!=null;i++){
			System.out.println(" ROOM NUMBER : "+this.room_number+" course: "+tableau[0][i]+" number of students: "+tableau[1][i]);
		}
	}

	public boolean contains(String a, String[][] tableau, int length) {
		for (int i = 0; i < length; i++) {
			if (tableau[0][i]!=null && tableau[0][i].equals(a))
				return true;
		}
		return false;
	}
}
