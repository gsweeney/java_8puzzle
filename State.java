/* Greg Sweeney U78564659
 * Intro to AI - Assignment 2
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class State {

	// static goal
	static int[] goal_state = {1,2,3,8,0,4,7,6,5};
	
	// data members
	State parent = null;
	int[] puzzle = new int[9];
	int heuristics= 0;// h(n)
	int cost_f = 0; // f(n)
	int empty_index;
	int move_cost = 0;
	
	void setHeuristics(){
		// find # of displaced tiles
		for(int i = 0; i < 9; i++){
			if(puzzle[i] != goal_state[i] && (puzzle[i] != 0)){
				heuristics++;
			}
		}
	}
	
	void setCost(){
		cost_f += heuristics;
	}
	
	//constructor
	State( State current, int swap_index){
		// copy puzzle input
		for(int i =0; i<9; i++){
			this.puzzle[i] = current.puzzle[i];
		}
		
		// swaps the empty_index with swap_index
		int temp = this.puzzle[swap_index];
		this.puzzle[current.empty_index] = temp;
		this.puzzle[swap_index] = 0;
		move_cost = temp;
		
		// set internal info
		this.setEmpty();
		this.setHeuristics();
		this.parent = current;
	}// close constructor
	
	State(File file){
		// create 2D array, puzzle,  from file
		try{
			// BufferedReader reads lines from file
			BufferedReader br = new BufferedReader(new FileReader(file));
				
			// store each line of text in a String, line
			String line;
			int i = 0;	
			
			// read lines until there are none
			while ((line = br.readLine()) != null) {
				Scanner scanner = new Scanner(line);
				
				while(scanner.hasNextInt()){
					puzzle[i] = scanner.nextInt();
					i++;
				}// close inner while
				
			scanner.close();
			}// close outer while
		br.close();
		
		this.setEmpty();
		this.setHeuristics();
		}// close try
		catch(IOException e)
		{
				e.printStackTrace();
		}
	}// close constructor
	
	// get position of empty tile
	void setEmpty(){
		for(int i = 0; i< 9; i++){
			if(this.puzzle[i] == 0){
				this.empty_index = i;
			}
	
		}
	}// close getEmpty
	
	// display 8-puzzle
	public void print(){
		
		for(int i = 0; i < 9; i++){
			System.out.print(puzzle[i] + " ");
			
			if(i == 2 || i == 5 || i == 8){
				System.out.println();
			}
		}
		System.out.println();
	}// close print
}// close class

