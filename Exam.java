package adv_algo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Exam {
	String name;
	int number_students;
	Queue<String> id_students=new LinkedList<String>();
	int start_hour;

	public Exam(String n, int nbr) {
		name = n;
		number_students = nbr;
	}
	public Exam(String n, int nbr,int hour,String id_student[]) {
		name = n;
		number_students = nbr;
		start_hour=hour;
		for(int i=0;i<id_student.length;i++){
			id_students.add(id_student[i]);
		}
		
		//put students in rooms
		/*int i=0;
		Iterator it = Executer.rooms.iterator();
		Room a;
		while (it.hasNext()) {
			a=(Room) it.next();
			while(i<id_student.length && a.available_space>0){
				a.queue.add(new Combination(this,id_student[i]));
				a.available_space--;
				i++;
			}
		}*/
		
	}
}
