/*
Name : Tomas Vasquez
Email : vasquezt@coloststate.edu
Project : HW5
Date : 12-OCT-2021
*/

public class OutputTupple {
    private char character;
    private int index; //non static
    

    public OutputTupple(char character, int index){
        this.character = character;
        this.index = index; //this.index
    }

    public char getCharacter() { 
        return this.character;
    }
    public int getIndex() { 
        return this.index;
    }
    
}