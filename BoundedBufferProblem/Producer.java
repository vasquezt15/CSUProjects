/*
Name : Tomas Vasquez
Email : vasquezt@coloststate.edu
Project : HW5
Date : 12-OCT-2021
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Producer extends java.lang.Thread {
	//Necessary variables and object declaration
	Random randomWithSeed;
	private Buffer buff;
	private int count;
	private int id;
	private int sum;
	private String strProduce = new String();
	ArrayList<OutputTupple> tupples;
	//private String strProduce = "helloall";
	
	//Contructor for producer
	public Producer(Buffer buff, int count, int id, int seed, ArrayList<OutputTupple> tupples) {
		//Assign values to the variables
		this.randomWithSeed = new Random(seed);
		this.buff = buff;
		this.count = count;
		this.id = id;
		this.sum = 0;
		this.tupples = tupples;
	}

	//Getter method which returns the sum of the numbers prodcued by the producer
	public String getProducedStr() {
		return strProduce;
	}
	public ArrayList<OutputTupple> getTupples(){
		for(int i=0; i< tupples.size(); i++){
			//System.out.println(tupples.get(i).getIndex());
		}
		return this.tupples;
	}
	//This is a method which gets called when a Thread is started.
	//This method calls the buffer to insert a number into a buffer so that it can be consumed later
	@Override
	public void run() {

		for(int i = 0; i < this.tupples.size(); i++) {
			OutputTupple tupple = this.tupples.get(i);
			this.buff.insert(tupple, this.id);
			this.strProduce += tupple.getCharacter();
		}
	}
}

