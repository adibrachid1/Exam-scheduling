package adv_algo;

import java.util.LinkedList;
import java.util.Queue;

public class Day {
	int date;
	int time;
	int course;
	Queue<Exam> exams = new LinkedList<Exam>();

	public Day(int d) {
		date = d;
	}

	public boolean conform_conditions(int hour, String course_name, int number_students, String id_students[],
			int nbr_spaces) {
		/*
		 * conditions: space for n students student + conflict with another exam
		 * no more than 2 exams for a student
		 */
		// this table to see how much a student in this course have exams in
		// this day
		// id_students are the id of students of a specific course
		int tmp_id_students[][] = new int[2][id_students.length];
		for (int i = 0; i < id_students.length; i++) {
			tmp_id_students[0][i] = Integer.parseInt(id_students[i]);
			tmp_id_students[1][i] = 0;
		}

		int taken_places = 0;
		for (Exam item : exams) {
			if (item.start_hour == hour) {
				for (String id : item.id_students) {
					//check if it conflicts student more than one exam in same hour
					if (is_in_conflict_with_another_exam(id, id_students))
						return false;
				}
				taken_places += item.number_students;
			}
			for (String id : item.id_students) {
				//check if students have more than 2 exams per day
				count_occurance(tmp_id_students, id, id_students.length);
			}
		}
		// if no more spaces in rooms available
		if (nbr_spaces - taken_places < number_students)
			return false;
		for (int i = 0; i < id_students.length; i++) {
			//more than 2 exams per day we cannot add it
			if (tmp_id_students[1][i] >= 2) {
				return false;
			}
		}
		// if all conditions meet
		return true;
	}

	// count occurance of exams for each student
	public void count_occurance(int[][] tab, String id, int length) {
		for (int i = 0; i < length; i++) {
			if (id.equals(Integer.toString(tab[0][i]))) {
				tab[1][i]++;
			}
		}
	}

	// check conflict with id
	public boolean is_in_conflict_with_another_exam(String id, String[] id_students) {
		for (int i = 0; i < id_students.length; i++) {
			if (id.equals(id_students[i]))
				return true;
		}
		return false;
	}

	// add the course to a specified day in the queue exams of the day
	public void add(int hour, String course, int students, String id_students[]) {
		Exam tmp = new Exam(course, students, hour, id_students);
		exams.add(tmp);
	}

	public void print_exams() {
		System.out.println(this.exams.size());
		// for(Exam item : exams)
		// System.out.println(item.name);
	}

	public void print(int i) {
	    for (Exam item : exams)
	      System.out.println("Day " + i + " " + item.name + "  " + item.start_hour + " to "
	          + (item.start_hour + 1));
	  }

	  public String print_string(int i) {
	    String tmp = "";
	    for (Exam item : exams)
	      tmp += "Day " + i + " " + item.name + "  " + item.start_hour + " to "
	          + (item.start_hour + 1) + "\n";
	    return tmp;
	  }
}
