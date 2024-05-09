README

This program is my take on the consumer/producer bounded buffer problem. I proposed this assignment as an extra credit opportunity while i was the TA for an operating systems course at Colorado State University.
====================================================================================================================================

Files:


Coordinator.java [This is the driver program which will be used to create an instance of the Circular buffer, open the file, generate the tupples to be passed to the producer, and create and wait for the producer and consumer threads.]

Producer.java [Each instance of a producer inserts a tupple into the buffer, and reports the location and the time the number was inserted into the buffer] 

Consumer.java [Each instance of a consumer reads a tupple from the buffer and reports the location and the time the number was read from the buffer.]

Buffer.java [Creates a circular buffer whose size is passed as an argument to it.]

OutputTupple.java [class to hold the key value pairs to be used in the assignment]

Makefile [For compiling, and cleaning]

====================================================================================================================================
Instructions:

systemprompt>  make
systemprompt> java Coordinator 7 input.txt


====================================================================================================================================

====================================================================================================================================
C. Sample Output (NOTE: Consumer lines will be underlined when you run the program. (.txt file don't allow underlined text so there are no underlines in this sample output)
input.txt contains: HelloWorld HereWe Are Again !@

$ java Coordinator 10 input.txt
[Coordinator] Buffer Size: 8
[Coordinator] Total Items: 30
[Coordinator] No. of Producers 3 No. of Consumers: 3
Producer   1 inserted H at index   0 at time 2021-10-14 12:47:27.084819 
Consumer   3 consumed H at index   0 at time 2021-10-14 12:47:27.088406 
Producer   3 inserted e at index   1 at time 2021-10-14 12:47:27.088822 
Producer   3 inserted   at index   2 at time 2021-10-14 12:47:27.089253 
Producer   2 inserted   at index   3 at time 2021-10-14 12:47:27.089591 
Producer   3 inserted A at index   4 at time 2021-10-14 12:47:27.089953 
Consumer   1 consumed e at index   1 at time 2021-10-14 12:47:27.090394 
Consumer   2 consumed   at index   2 at time 2021-10-14 12:47:27.090754 
Consumer   2 consumed   at index   3 at time 2021-10-14 12:47:27.091220 
Consumer   3 consumed A at index   4 at time 2021-10-14 12:47:27.091552 
Producer   1 inserted e at index   5 at time 2021-10-14 12:47:27.091904 
Consumer   3 consumed e at index   5 at time 2021-10-14 12:47:27.092199 
Producer   1 inserted l at index   6 at time 2021-10-14 12:47:27.092535 
Consumer   2 consumed l at index   6 at time 2021-10-14 12:47:27.092836 
Producer   3 inserted g at index   7 at time 2021-10-14 12:47:27.093177 
Producer   2 inserted H at index   0 at time 2021-10-14 12:47:27.093500 
Producer   3 inserted a at index   1 at time 2021-10-14 12:47:27.093813 
Consumer   1 consumed g at index   7 at time 2021-10-14 12:47:27.094143 
Consumer   2 consumed H at index   0 at time 2021-10-14 12:47:27.094471 
Producer   1 inserted l at index   2 at time 2021-10-14 12:47:27.094785 
Consumer   3 consumed a at index   1 at time 2021-10-14 12:47:27.095083 
Producer   1 inserted o at index   3 at time 2021-10-14 12:47:27.095368 
Consumer   2 consumed l at index   2 at time 2021-10-14 12:47:27.095648 
Consumer   1 consumed o at index   3 at time 2021-10-14 12:47:27.095927 
Producer   3 inserted i at index   4 at time 2021-10-14 12:47:27.096207 
Producer   2 inserted e at index   5 at time 2021-10-14 12:47:27.096495 
Producer   3 inserted n at index   6 at time 2021-10-14 12:47:27.096827 
Producer   3 inserted   at index   7 at time 2021-10-14 12:47:27.097085 
Consumer   1 consumed i at index   4 at time 2021-10-14 12:47:27.097348 
Consumer   2 consumed e at index   5 at time 2021-10-14 12:47:27.097624 
Producer   1 inserted W at index   0 at time 2021-10-14 12:47:27.097894 
Consumer   3 consumed n at index   6 at time 2021-10-14 12:47:27.098168 
Producer   1 inserted o at index   1 at time 2021-10-14 12:47:27.098481 
Consumer   2 consumed   at index   7 at time 2021-10-14 12:47:27.098734 
Consumer   1 consumed W at index   0 at time 2021-10-14 12:47:27.098959 
Producer   3 inserted ! at index   2 at time 2021-10-14 12:47:27.099189 
Producer   2 inserted r at index   3 at time 2021-10-14 12:47:27.099453 
Producer   3 inserted @ at index   4 at time 2021-10-14 12:47:27.099668 
Consumer   1 consumed o at index   1 at time 2021-10-14 12:47:27.099921 
Consumer   1 consumed ! at index   2 at time 2021-10-14 12:47:27.100330 
Consumer   1 consumed r at index   3 at time 2021-10-14 12:47:27.100543 
Consumer   1 consumed @ at index   4 at time 2021-10-14 12:47:27.100735 
Producer   1 inserted r at index   5 at time 2021-10-14 12:47:27.100926 
Consumer   3 consumed r at index   5 at time 2021-10-14 12:47:27.101164 
Producer   1 inserted l at index   6 at time 2021-10-14 12:47:27.101369 
Consumer   1 consumed l at index   6 at time 2021-10-14 12:47:27.101576 
Producer   2 inserted e at index   7 at time 2021-10-14 12:47:27.101809 
Consumer   2 consumed e at index   7 at time 2021-10-14 12:47:27.102045 
Producer   2 inserted W at index   0 at time 2021-10-14 12:47:27.102256 
Producer   1 inserted d at index   1 at time 2021-10-14 12:47:27.102461 
Consumer   3 consumed W at index   0 at time 2021-10-14 12:47:27.102661 
Producer   2 inserted e at index   2 at time 2021-10-14 12:47:27.102879 
Producer   2 inserted   at index   3 at time 2021-10-14 12:47:27.103073 
Consumer   2 consumed d at index   1 at time 2021-10-14 12:47:27.103258 
Producer   2 inserted A at index   4 at time 2021-10-14 12:47:27.103442 
Consumer   3 consumed e at index   2 at time 2021-10-14 12:47:27.103620 
Producer   2 inserted r at index   5 at time 2021-10-14 12:47:27.103858 
Consumer   2 consumed   at index   3 at time 2021-10-14 12:47:27.104087 
Consumer   3 consumed A at index   4 at time 2021-10-14 12:47:27.104495 
Consumer   3 consumed r at index   5 at time 2021-10-14 12:47:27.104805 
Producer's output:
HelloWorld HereWe Are Again !@
Consumer's output:
HelloWorld HereWe Are Again !@
    
====================================================================================================================================

====================================================================================================================================
D. Questions:

1. The problem of producer consumer is solved using SEMAPHORES. - 1 point
	a. Mutex Locks
	b. Semaphores <-

2. What two functions defined in Java are used for synchronization between producer and consumers in your program? wait() and notify(). - 2 points

3. In which function do you override the body to define the new body of a thread in java? run(). - 1 point

4. Which function is used to wait for a thread to finish and come back to calling program i.e. for a thread to die? join(). - 1 point
====================================================================================================================================

====================================================================================================================================
E. NOTE:

Lab systems by default use Java 11 which supports nanosecond timestamp. If after logging in and running 'java -version', it doesn't output version 11, use the following two commands:

	$ export PATH=/usr/lib/jvm/jre-11-openjdk/bin/:$PATH

	$ export LD_LIBRARY_PATH=/usr/lib/jvm/jre-11-openjdk/lib/:$LD_LIBRARY_PATH
====================================================================================================================================