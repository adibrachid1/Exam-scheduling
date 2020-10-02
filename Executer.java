package adv_algo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/*
 * Exam Scheduling 
 * 
 * Project done by:
 * 
 * Rachid Adib
 * Rachwan John
 * 
 * This project is an exam scheduling.
 * We did it in a way to alternate exams in x=10 DAYS!
 * 
 * Inputs: 2 files (rooms with their capacity and students showing each student which exam he has)
 * Output: on the screen available places,each day the courses and their time)+ many other outputs but commented to reduce execution time
 * 
 * Execution finishes when saying Done transferring to file results
 * 
 * Files should be on the desktop - change the path of 4 files on your machine!
 * We did not continue to show for each courses the partition in classes so we commented the code to becontinued later
 */
public class Executer {
	/*
	 * public static PriorityQueue<Room> rooms = new PriorityQueue<Room>(20, new
	 * Comparator<Room>() { public int compare(Room i, Room j) { if
	 * ((i.available_space > j.available_space)) { return 1; } else if
	 * (i.available_space < j.available_space) { return -1; } else { return 0; }
	 * } });
	 */

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// ================================================================
		// =====================READ FROM FILES============================
		// ================================================================
		String rooms[][];
		String students[][];
		int nbr_rooms = 0;
		int nbr_students = 0;
		int nbr_distinct_courses = 0;
		int i = 0;
		int index = 0;// index for distinct courses
		int nbr_of_places;// sum of available places in all rooms
		// count lines of rooms

		BufferedReader br = new BufferedReader(new FileReader(new File("C:/Users/User/Desktop/rooms.txt")));
		Scanner s = new Scanner(br);
		while (s.hasNext()) {
			nbr_rooms++;// count nbr of lines
			s.nextLine();// read next line
		}
		s.close();
		rooms = new String[2][nbr_rooms];// initialise the table

		// complete the table of rooms

		br = new BufferedReader(new FileReader(new File("C:/Users/User/Desktop/rooms.txt")));
		s = new Scanner(br);
		while (s.hasNext()) {
			rooms[0][i] = s.next();
			rooms[1][i++] = s.next();
		}
		s.close();
		i = 0;

		// count lines of students
		br = new BufferedReader(new FileReader(new File("C:/Users/User/Desktop/students.txt")));
		s = new Scanner(br);
		// put the information into the table
		while (s.hasNext()) {
			nbr_students++;
			s.nextLine();
		}
		s.close();
		students = new String[2][nbr_students];

		// complete the table of students
		br = new BufferedReader(new FileReader(new File("C:/Users/User/Desktop/students.txt")));
		s = new Scanner(br);
		while (s.hasNext()) {
			students[0][i] = s.next();
			students[1][i++] = s.next();
		}
		s.close();
		nbr_of_places = get_all_places(nbr_rooms, rooms);
		System.out.println("Available places:" + nbr_of_places);
		// =====================================================================
		// =====================================================================
		// =====================================================================

		// get number of distinct courses and create a table showing disctinct
		// courses.
		String courses[][] = new String[2][nbr_students];
		i = 0;
		int j = 0;
		for (i = 0; i < nbr_students; i++) {
			if (!found(courses, nbr_students, students[1][i])) {
				courses[0][j++] = students[1][i];
				nbr_distinct_courses++;
			}
		}
		i = 0;

		// number of students for each course
		for (j = 0; j < nbr_distinct_courses; j++) {
			courses[1][j] = count_students(courses[0][j], nbr_students, students);
		}

		// sort form biggest number of students until smallest (decremental way
		// of number of students in courses)
		sort(courses, nbr_distinct_courses);

		// display for each course how many students

		/*
		 * System.out.println("\nCourse - Nbr of students\n Descending order\n"
		 * ); for (j = 0; j < nbr_distinct_courses; j++) {
		 * System.out.println(courses[0][j] + " " + courses[1][j]); }
		 */

		// =============================================================================
		// check conflicts for students=> for each student how many exams for
		// debugging.
		// =============================================================================
		/*
		 * int distinct_students[][] = new int[2][nbr_students]; int tmp_id; int
		 * cmpt = 0; for (j = 0; j < nbr_students; j++) { tmp_id =
		 * Integer.parseInt(students[0][j]); cmpt = 0; for (int jj = 0; jj <
		 * nbr_students; jj++) { if
		 * (Integer.toString(tmp_id).equals(students[0][jj])) cmpt++; }
		 * distinct_students[0][j] = tmp_id; distinct_students[1][j] = cmpt; }
		 * System.out.println("\n\n\n"); for (j = 0; j < nbr_students; j++) {
		 * System.out.println(distinct_students[0][j] + "   have at max " +
		 * distinct_students[1][j]); } //find the maximum of it
		 * System.out.println(max(distinct_students,nbr_students));
		 */
		// =============================================================================
		// =============================================================================
		// =============================================================================

		// create rooms;
		/*
		 * for (j = 0; j < nbr_rooms; j++) { Filealgo.rooms.add(new
		 * Room(nbr_of_places, Integer.parseInt(rooms[0][j]),
		 * Integer.parseInt(rooms[1][j]))); }
		 */

		// create 10 days
		Day day_table[] = new Day[10];
		for (j = 0; j < 10; j++) {
			day_table[j] = new Day(j + 1);
		}

		/// add course by course
		System.out.println("Adding the courses and executing Loading.. Wait about 20-30sec");
		int ii = 0;
		int jjj = 0;
		for (j = 0; j < nbr_distinct_courses; j++) {
			add(ii, jjj, day_table, courses[0][index], Integer.parseInt(courses[1][index++]), nbr_students, students,
					nbr_of_places);
			ii = (ii + 2) % 10;
			jjj = (jjj + 1) % 12;// to alternate between each course so that we
									// dont have most exams in first day and
									// least int the ladt day
			// we can change it if the algorithm don t find a solution
			// this is a big factor for the speed of the algorithm!
		}
		System.out.println("Number of Distinct courses : " + nbr_distinct_courses);
		//print for each day the courses
		for (j = 0; j < 10; j++) {
			System.out.println("Day " + (j + 1) + " : " + day_table[j].exams.size() + " exams");
			day_table[j].print(j + 1);
		}
		System.out.println("Transferring to file results- please wait this wont take long");
		
		// write results to file.
		FileWriter a = new FileWriter("C:/Users/User/Desktop/results.txt");
		a.write(" EXAM SCHEDULING BY ADIB RACHID AND JOHN RACHWAN\n");
		a.write("Number of Distinct courses : " + nbr_distinct_courses + "\n");
		for (j = 0; j < 10; j++)
			a.write("Day " + (j + 1) + " : " + day_table[j].exams.size() + " exams\n");

		a.write("\n\n\n\n\n\n FORMAT Day x - Course - from - to \n");
		for (j = 0; j < 10; j++) {
			a.write(day_table[j].print_string(j + 1));
		}
		a.close();
		System.out.println("Done transferring to file results");
		System.out.println("DONE EXECUTING");

	}

	// add a course to the schedule
	public static void add(int ii, int jjj, Day day_table[], String course, int students, int nbr_students,
			String[][] stud, int nbr_spaces) {
		String students_id[] = new String[students];
		int j = 0;
		int jj = 0;
		for (int i = 0; i < nbr_students; i++) {
			if (stud[1][i].equals(course)) {
				students_id[j++] = stud[0][i];
			}
		}
		int gg = 8;
		for (int i = ii; jj < 10; i = (i + 1) % 10) {// looping between days
			jj++;
			gg = 8;
			for (j = jjj; gg++ <= 19; j = ((j + 1) % 12)) {// looping between
															// hours
				if (day_table[i].conform_conditions(j + 8, course, students, students_id, nbr_spaces)) {
					day_table[i].add(j + 8, course, students, students_id);
					return;
				} // System.out.println(course +" jjj "+jjj+" gg "+gg+" et
					// j"+(j+8));
			}
		}
		System.out.println("Could not add a class so I did system.exit- Review the capacity of rooms or amout of days given ");
		//if this way of looping doesn't not find a solution we can change the way we are incrementing i or j
		System.exit(5);
	}

	// find repeated courses in the same table
	public static boolean found(String tab[][], int length, String a) {
		for (int i = 0; i < length; i++) {
			if (tab[0][i] != null && tab[0][i].equals(a))
				return true;
		}
		return false;
	}

	// count number of students for each course
	public static String count_students(String course, int length, String[][] tab) {
		int n = 0;
		for (int i = 0; i < length; i++) {
			if (tab[1][i].equals(course)) {
				n++;
			}
		}
		return Integer.toString(n);
	}

	// sort table decroissant
	public static void sort(String tab[][], int n) {
		int i, j, val;
		String val1;
		for (i = 1; i < n; i++) {
			val = Integer.parseInt(tab[1][i]);
			val1 = tab[0][i];
			for (j = i - 1; (j >= 0) && (Integer.parseInt(tab[1][j]) < val); j--) {
				tab[1][j + 1] = tab[1][j];
				tab[0][j + 1] = tab[0][j];
			}
			tab[1][j + 1] = Integer.toString(val);
			tab[0][j + 1] = val1;
		}

	}

	public static int get_all_places(int nbr_rooms, String tab[][]) {
		int n = 0;
		for (int i = 0; i < nbr_rooms; i++) {
			n += Integer.parseInt(tab[1][i]);
		}
		return n;
	}

	//find maximum of the table distinct students to see maximum exams per day
	/*
	public static String max(int[][] tab, int n) {
		int id = tab[0][0];
		int max = tab[0][1];
		for (int i = 1; i < n; i++) {
			if (tab[1][i] >= max) {
				id = tab[0][i];
				max = tab[1][i];
			}
		}

		return get_nbr_of_students(tab, n, max) + " students have the most exams with " + max + " exams";
	}*/

	public static int get_nbr_of_students(int[][] tab, int n, int max) {
		int cmp = 0;
		for (int i = 0; i < n; i++) {
			if (tab[1][i] == max)
				cmp++;
		}
		return cmp;
	}
}
