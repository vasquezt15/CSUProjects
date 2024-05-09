/*
Name : Tomas Vasquez
Email : vasquezt@coloststate.edu
Project : HW5
Date : 12-OCT-2021
*/



public class Buffer {

    //Necessary variables and object declaration
    private OutputTupple[] buffer;
    private int producerIndex;
    private int consumerIndex;
    private int bufferSize;
    private int maxBufferSize;

    //Contructor for Buffer
    public Buffer(int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
        this.bufferSize = 0;
        this.producerIndex = 0;
        this.consumerIndex = 0;
        this.buffer = new OutputTupple[maxBufferSize];
    }

    //Getter method which tells us whether the Buffer is Empty or not
    public boolean isBufferEmpty() {
        return this.bufferSize == 0;
    }

    //Getter method which tells us whether the Buffer is Full or not
    public boolean isBufferFull() {
        return this.bufferSize == this.maxBufferSize;
    }

    //This method is called by the producer so that the number can be inserted into the buffer
    //If the Buffer is full, we wait until some number is consumerd by the consumer
    public void insert(OutputTupple tupple, int id) {
        synchronized (this.buffer) {

            while (this.isBufferFull()) {
                try {
                    //STEP 3
                    this.buffer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            char letter = tupple.getCharacter();
	    this.buffer[this.producerIndex] = tupple;
            
            //STEP 5
            System.out.format("\033[0;4mProducer %3d inserted %c at index %3d at time\033[0;0m %s\n", id, letter, this.producerIndex, Coordinator.getTime());
            System.out.flush();
            this.producerIndex = (this.producerIndex + 1) % this.maxBufferSize;
            this.bufferSize++;
            this.buffer.notifyAll();
        }
    }

    //This method is called by the consumer so that the number can be removed from the buffer
    //If the Buffer is empty, we wait until some number is produced by the producer
    public OutputTupple delete(int id) {
        synchronized (this.buffer) {
           while (this.isBufferEmpty()) {
                try {
                    //STEP 4
                    this.buffer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
           }
           
           OutputTupple deletedTuple = buffer[this.consumerIndex];
           
           //STEP 5
           System.out.format("Consumer %3d consumed %c at index %3d at time %s\n", id, deletedTuple.getCharacter(), this.consumerIndex, Coordinator.getTime());
           System.out.flush();
           this.consumerIndex = (this.consumerIndex + 1) % this.maxBufferSize;
           this.bufferSize--;
           this.buffer.notifyAll();
           return deletedTuple;
        }
    }
}

