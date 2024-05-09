/*
Name : Tomas Vasquez
Email : vasquezt@coloststate.edu
Project : HW5
Date : 12-OCT-2021
*/

import java.util.ArrayList;
import java.util.List;
public class Consumer extends java.lang.Thread {
	//Necessary variables and object declaration
	private Buffer buff;
	private int count;
	private int id;
	private int sum;
	private String strConsumer= new String();
	private ArrayList<OutputTupple> output;

	//Contructor for consumer
	public Consumer(Buffer buff, int count, int id) {
		//Assign values to the variables
		this.buff = buff;
		this.count = count;
		this.id = id;
		this.sum = 0;
		this.output = new ArrayList<OutputTupple>();
	}

	//Getter method which returns the sum of the numbers consumed by the consumer
	public String getConsumedStr() {
		return strConsumer;
	}
	public ArrayList<OutputTupple> getTupples(){
		return this.output;
	}

	//This is a method which gets called when a Thread is started.
	//This method calls the buffer to remove a number into a buffer so that it can be consumed
	@Override
	public void run() {
		
		for(int i = 0; i < this.count; i++) {
			OutputTupple tupple = this.buff.delete(this.id);
			this.strConsumer += tupple.getCharacter();
			this.sum += tupple.getCharacter();
			this.output.add(tupple);
		}
	}
}

