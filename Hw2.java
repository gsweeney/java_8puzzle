/* Greg Sweeney U78564659
 * Intro to AI - Assignment 2
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Hw2 {

	static ArrayList<State> fringe = new ArrayList<State>();
	static ArrayList<int[]> closed = new ArrayList<int[]>();
	
	public static void main(String[] args) {

		// goal state: {1, 2, 3, 8, 0, 4, 7, 6, 5}
		boolean goalFound = false;
		
		// get the input file. the user inputs the name.
		Input input = new Input();
		File file = input.getInput();
		
		// create an 8 puzzle from input file
		State start_state = new State(file);
		//start_state.print();
		
		fringe.add(start_state);
		
		while(!goalFound){
			// use A* to find the lowest cost state in the fringe
			State next_state = aStar();
			
			// remove current_state from fringe
			fringe.remove(next_state);// this isn't working?
			
			// add chosen state's puzzle to the closed list
			closed.add(next_state.puzzle);
			
			// find the possible next states and add them to the fringe
			expand(next_state);
			
			// if current state is the goal state, stop
			if(Arrays.equals(next_state.puzzle, State.goal_state)){
				goalFound = true;
				System.out.println("\nCheapest Sequence of Moves:");
				printMoves(next_state);
				System.out.println("The shortest path cost = " + pathCost(next_state));
			}
			
		}// close while
		
	}// close main

	static void expand(State state){
		
		ArrayList<State> nextStates = new ArrayList<State>();
			
		// create next possible states using the position of empty_index
		switch(state.empty_index){
			// center tile
			case 4:
				// use indexes 1, 3, 5, 7
				// copy the current state and swap empty and given index
				State top4 = new State(state, 1);
				nextStates.add(top4);
				State right4 = new State(state, 5);
				nextStates.add(right4);
				State left4 = new State(state, 3);
				nextStates.add(left4);
				State bottom4 = new State(state, 7);
				nextStates.add(bottom4);
				break;
			// corner
			case 0:
				// use indexes 1, 3
				State right0 = new State(state, 1);
				nextStates.add(right0);
				State bottom0 = new State(state, 3);
				nextStates.add(bottom0);
				break;
			case 2:
				// use indexes 1, 5
				State left2 = new State(state, 1);
				nextStates.add(left2);
				State bottom2 = new State(state, 5);
				nextStates.add(bottom2);
				break;
			case 6:
				// use indexes 3, 7
				State top6 = new State(state, 3);
				nextStates.add(top6);
				State right6 = new State(state, 7);
				nextStates.add(right6);
				break;
			case 8:
				// use indexes 5, 7
				State top8 = new State(state, 5);
				nextStates.add(top8);
				State left8 = new State(state, 7);
				nextStates.add(left8);
				break;
			// cross
			case 1:
				// use indexes 0, 2, 4
				State right1 = new State(state, 2);
				nextStates.add(right1);
				State left1 = new State(state, 0);
				nextStates.add(left1);
				State bottom1 = new State(state, 4);
				nextStates.add(bottom1);
				break;
			case 3:
				// use indexes 0. 4. 6
				State right3 = new State(state, 4);
				nextStates.add(right3);
				State top3 = new State(state, 0);
				nextStates.add(top3);
				State bottom3 = new State(state, 6);
				nextStates.add(bottom3);
				break;
			case 5:
				// use indexes 2 ,4 ,8
				State left5 = new State(state, 4);
				nextStates.add(left5);
				State top5 = new State(state, 2);
				nextStates.add(top5);
				State bottom5 = new State(state, 8);
				nextStates.add(bottom5);
				break;
			case 7:
				// use indexes 4, 6, 8
				State top7 = new State(state, 4);
				nextStates.add(top7);
				State right7 = new State(state, 8);
				nextStates.add(right7);
				State left7 = new State(state, 6);
				nextStates.add(left7);
				break;
			default:
				System.out.println("something went wrong expanding");
				break;
			
		}
	
		// loop through nextStates
		for(State s: nextStates){
			// if next possible state is not in the closed list
			boolean inClosedList = false;
			for(int[] arr : closed){
				if(Arrays.equals(arr, s.puzzle)){
					inClosedList = true;
				}

			}
			if(!inClosedList){
				fringe.add(s);
			}
		}// close for
	}// close expand
	

	static State aStar(){
		State cheap_state = null;
		int lowest_cost = 999;
		
		for(State state: fringe){
			state.cost_f = state.heuristics + state.move_cost + pathCost(state);
			if(state.cost_f < lowest_cost){
				cheap_state = state;
				lowest_cost = state.cost_f;
			}
		}
		return cheap_state;
	}// close aStar
	
	static int pathCost(State state){
		// base case
		if(state.parent == null){
			return 0;
		}
		// recursive case
		else{
			return state.move_cost + pathCost(state.parent);	
		}
	}// close pathCost
	
	static void printMoves(State state){
		
		// base case
		if(state.parent == null){
			state.print();
		}
		// recursive case
		else{
			printMoves(state.parent);
			state.print();
		}
	}// close pathCost
	
}// close class


class Input {

	// get input from user for name of file
	File getInput(){
		
		boolean valid = false;
		File file = null;
		System.out.println("Please enter the full name of the input file, including the .txt extension.\n");

		
		while(!valid){
			
			System.out.print("Enter Input File (input1.txt or input2.txt): ");
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			String fileName = scanner.nextLine();
			file = new File(fileName);
			if(file.isFile()){
			    valid = true;
			    System.out.println("input file found");
			 }
			 else
			    System.out.println("input file not found");
			
		}
		return file;
	}// close getInput()
}// close class
