import java.util.Random;

public class arrayTest {
    static final int ARRAY_SIZE = 200_000_000;
    static final int THREADS = 4;

    public static void main(String args[]){

       int[] numbers = generateRandomNumbers(ARRAY_SIZE, 1, 10);

        //Compute Multi Thread sun
       long multiThreadStartTime = System.currentTimeMillis();
       long multiThreadSum = computeParalellSum(numbers);
       long multiThreadEndTime = System.currentTimeMillis();
       long multiThreadExecutionTime = multiThreadEndTime - multiThreadStartTime;

       //Compute single thread sum
        long singleStartTime = System.currentTimeMillis();
        long singleSum = computeSingleSum(numbers);
        long singleEndTime = System.currentTimeMillis();
        long singleExecutuionTime = singleEndTime - singleStartTime;

        //results multi-thread
        System.out.println("Parallel Sum: " + multiThreadSum);
        System.out.println("Parallel Execution Time: " + multiThreadExecutionTime);

        //results single thread
        System.out.println("Single Sum: " + singleSum);
        System.out.println("Single Execution Time: " + singleExecutuionTime);

        }

    private static int[] generateRandomNumbers(int size, int min, int max) {
        int[] numbers = new int[size];
        Random random = new Random();

        for (int i = 0; i < 10; i++){
            numbers[i] = random.nextInt(max - min + 1) + min;
    }
        return numbers;

}

    private static long computeParalellSum(int[] numbers){

        long[] partialSums = new long[THREADS];
        Thread[] threads = new Thread[THREADS];

        int segmentSize = numbers.length / THREADS;

        for (int i = 0; i < THREADS; i++) {
            final int threadIndex = i;
            threads[i] = new Thread(() -> {
                int start = threadIndex * segmentSize;
                int end = (threadIndex == THREADS - 1) ? numbers.length : start + segmentSize;
                long sum = 0;

                for (int j = start; j < end; j++) {
                    sum += numbers[j];
                }

                partialSums[threadIndex] = sum;
            });
            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        long totalSum = 0;
        for (long partialSum : partialSums){
            totalSum += partialSum;
        }
        return totalSum;
    }

    private static long computeSingleSum(int[] numbers){
        long sum = 0;
         for (int number : numbers){
             sum += number;
         }
         return sum;
    }


}
