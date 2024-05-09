/*
Name : Tomas Vasquez
Email : vasquezt@coloststate.edu
Project : HW5
Date : 12-OCT-2021
*/
import java.util.Random;
import java.time.Instant;
import java.time.Clock;
import java.time.Duration;
import java.io.*;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;
import java.util.ArrayList;


class Coordinator {
	public static void main(String[] args) {
		//Error checking whether correct number of arguments are passed or not
		if(args.length != 2){
			System.out.println("Incorrect Number of Arguments");
			System.out.println("Correct Way to run the program:");
			System.out.println("java <Program Name> <Seed>");
			System.exit(1);
		}
		//Assgining passed paramerts to variables
		int seed = Integer.parseInt(args[0]);

		Random randomGenerator = new Random(seed);
		int bufferSize = randomGenerator.nextInt(10-5+1) + 5;
		int numProdCon = randomGenerator.nextInt(5-2+1) + 2;
		int numConsuCon =randomGenerator.nextInt(5-2+1) + 2;
		int numberConsumers = numConsuCon;
		int numberProducers = numProdCon;
		List <String> splits = new ArrayList<>();
		int totalItems =0;
		// open file, look through it and split the file into equal size lines such that each producer receives an equal share of the file.
		try {
			File file = new File(args[1]);
			long file_size = file.length();
			totalItems += (int) file_size;
			int numberPerProducer = totalItems / numberProducers;
		
			Reader reader = new FileReader(file);
			int allocated=0;
			//read one character at a time. 
			for(int i=0; i< numberProducers;i++){
				String temp = new String();
					for(int k =0; k<numberPerProducer;k++){
						allocated++;
						temp += (char)reader.read(); //place in temporary string
			
				}
				//the last one gets its share plus the extras
				if(i == (numberProducers-1) && allocated != totalItems){
					for(int k =0; k<totalItems-allocated;k++){
						temp += (char)reader.read(); //place in temporary stirng
					}
				}
				splits.add(temp); //add string into split array
				
			}
			reader.close();

		}catch (IOException e){
			e.printStackTrace();
		}
		//end of code for handling the files and generating input splits

		//transform the Array of strings into an array of tuple arrays
		ArrayList <ArrayList<OutputTupple>> producerInput = new ArrayList< ArrayList<OutputTupple>>();
		int index = 0;

		for (int i=0; i<numberProducers; i++){
			ArrayList<OutputTupple> temp = new ArrayList<>();
			for (int k =0; k<splits.get(i).length(); k++){
				temp.add(new OutputTupple(splits.get(i).charAt(k),index++));
		}
		producerInput.add(temp);
		}
		//end of transformation code
		System.out.println("[Coordinator] Buffer Size: "+bufferSize);
		System.out.println("[Coordinator] Total Items: "+totalItems);
		System.out.format("[Coordinator] No. of Producers %d No. of Consumers: %d\n", numProdCon, numConsuCon);

		//STEP 2: Calcuate variables which are needed later
		int numberPerProducer = totalItems / numberProducers;
		int extraNumberProducer = totalItems % numberProducers + numberPerProducer;
		int numberPerConsumer = totalItems / numberConsumers;
		int extraNumberConsumer = totalItems % numberConsumers + numberPerConsumer;
		//Create a new Buffer
		Buffer buffer = new Buffer(bufferSize);

		//Create a array of producers and start the thread on each of the producer
		Producer[] producers = createProducers(numberProducers, buffer, numberPerProducer, extraNumberProducer, seed, producerInput);

		//Create a array of consumers and start the thread on each of the consumer
		Consumer[] consumers = createConsumers(numberConsumers, buffer, numberPerConsumer, extraNumberConsumer);

		//Join all Producers and Consumers once the Threads are finish running
		
		joinProducers(numberProducers, producers);
		joinConsumers(numberConsumers, consumers);

		//Printing the check sum for Producers and Consumers
		ArrayList<OutputTupple> producerOutput = producerOutput(producers, numberProducers);
		System.out.println("Producer's output:");
		printOutput(producerOutput);
		ArrayList<OutputTupple> consumerOutput = consumerOutput(consumers, numberConsumers);
		System.out.println("Consumer's output:");
		printOutput(consumerOutput);
	}

	//Create a array of producers and start the thread on each of the producer
	private static Producer[] createProducers(int numberProducers, Buffer buffer, int numberPerProducer, int extraNumberProducer, int seed, ArrayList <ArrayList<OutputTupple>> splits) {
		Producer[] producers = new Producer[numberProducers];
		for (int i = 0; i < numberProducers; i++) {
			if(i != (numberProducers-1)){
				producers[i] = new Producer(buffer, numberPerProducer, (i + 1), seed, splits.get(i));
			}
			else{
				producers[i] = new Producer(buffer, extraNumberProducer, (i + 1), seed,splits.get(i));
			}
			producers[i].start();
		}

		return producers;
	}

	//Create a array of consumers and start the thread on each of the consumer
	private static Consumer[] createConsumers(int numberConsumers, Buffer buffer, int numberPerConsumer, int extraNumberConsumer) {
		Consumer[] consumers = new Consumer[numberConsumers];
		for (int i = 0; i < numberConsumers; i++) {
			if(i != (numberConsumers-1)){
				consumers[i] = new Consumer(buffer, numberPerConsumer, (i + 1));
			}
			else{
				consumers[i] = new Consumer(buffer, extraNumberConsumer, (i + 1));
			}
			consumers[i].start();
		}

		return consumers;
	}

	//Join all Producers once the Threads are finish running
	private static void joinProducers(int numberProducers, Producer[] producers) {
		for (int i = 0; i < numberProducers; i++) {
			try {
				producers[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	//Join all Consumers once the Threads are finish running
	private static void joinConsumers(int numberConsumers, Consumer[] consumers) {
		for (int i = 0; i < numberConsumers; i++) {
			try {
				consumers[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	//Calculates total sum for the Producers
	private static ArrayList<OutputTupple> producerOutput(Producer[] producers, int numberProducers) {
		ArrayList<OutputTupple> finalProduced = new ArrayList<OutputTupple>();
		for (int i = 0; i < numberProducers; i++) {
			ArrayList<OutputTupple> temp = producers[i].getTupples();
			for(int j=0; j<temp.size(); j++ ){
				OutputTupple temp1 = new OutputTupple(temp.get(j).getCharacter(), temp.get(j).getIndex());
				finalProduced.add(temp1);
			}
		}
		return finalProduced;
	}

	//Calculates total sum for the Consumers
	private static ArrayList<OutputTupple> consumerOutput(Consumer[] consumers, int numberConsumers) {
		ArrayList<OutputTupple> finalProduced = new ArrayList<OutputTupple>();
		for (int i = 0; i < numberConsumers; i++) {
			ArrayList<OutputTupple> temp = consumers[i].getTupples();
			for(int j=0; j<temp.size(); j++ ){
				OutputTupple temp1 = new OutputTupple(temp.get(j).getCharacter(), temp.get(j).getIndex());
				finalProduced.add(temp1);
			}
		}
		return finalProduced;
	}

	private static void printOutput(ArrayList<OutputTupple> input) {
		Map<Integer, Character> OutMap = new TreeMap<>();
		for(int i=0; i<input.size(); i++){
			OutMap.put(input.get(i).getIndex(), input.get(i).getCharacter());
		}
		for(Map.Entry<Integer, Character> entry : OutMap.entrySet()) {
			int index = entry.getKey();
			char value = entry.getValue();
			System.out.format("%c", value);			
		  }
		  System.out.println();

	}

	//Call this function from your producer or your consumer to get the time stamp to be displayed
	public static String getTime() {
		Clock offsetClock = Clock.offset(Clock.systemUTC(), Duration.ofHours(-9));
		Instant time = Instant.now(offsetClock);
		String timeString = time.toString();
		timeString = timeString.replace('T', ' ');
		timeString = timeString.replace('Z', ' ');
		return(timeString);
	}
}
