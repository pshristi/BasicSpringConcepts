package com.example.learningspring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.lang.System.currentTimeMillis;

public class LearningJava {
    public record RateCardLineItemPortingMessage(Integer entityId, Integer entityTypeId,
                                                 Integer sourceEntityId,
                                                 Integer sourceEntityTypeId,
                                                 Integer clientId,
                                                 String user,
                                                 Integer userId,
                                                 boolean generateRU) {}

    public static void main(String[]args){
        /*
        //Enhaced switch
        System.out.println("Hello World " + getQuarter("January"));
         */
        /*
        //Records
        try {
            checkRecord();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
         */
        /*
        //List, ArrayList
        checkList();
         */
        /*
        //Lambda expression
        checkLambda();
         */
        /*
        Date currentDate = new Date();
        System.out.println("Current Date and Time: " + currentDate);
         */
        /*
        //Method reference
        checkMethodReference();
         */
        /*
        //Some common functional interfaces
        checkFunctionalInterfaces();
         */
        /*
        //Java Streams
        checkStreams();
         */
        /*
        //Threads and concurrency
        checkThreadsInterruptAndJoin();
         */
        /*
        //Threads and concurrency
        Test test = new Test();
        test.checkThreadsVolatile();
         */
        /*
        //Threads and concurrency
        //Reentrant Lock and Deadlock control with wait and notify
        checkDeadlock();
         */
        //Threads and concurrency
        //concurrent package and ExecutorService
        checkExecutorService();
    }

    //Array, List
    private static void checkList() {
        String[] str = {"1", "2"};
        List<String> immutableList = List.of(str);
        ArrayList<String> mutableList = new ArrayList<>(List.of("1", "2", "3", ""));
        List<String> nonresizeablebutmutableList1 = Arrays.asList(str);
        ArrayList<String> mutableList1 = new ArrayList<>(Arrays.asList("1", "2", "3", ""));

        String[] str1 = mutableList.toArray(new String[mutableList.size()]);
        var str2 = mutableList1.toArray(new String[0]);
    }

    //Record
    /*
    public static void checkRecord() throws JsonProcessingException {
        com.sirionlabs.pulsar.RateCardLineItemPortingMessage message;
        message = new com.sirionlabs.pulsar.RateCardLineItemPortingMessage();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        byte[] data = mapper.writeValueAsBytes(message);

        //com.sirionlabs.pulsar.RateCardLineItemPortingMessage rateCardLineItemPortingMessage = JsonUtils.fromJson(data, com.sirionlabs.pulsar.RateCardLineItemPortingMessage.class);
        //System.out.println(rateCardLineItemPortingMessage);
    }

     */
    //switch
    public static String getQuarter(String month) {
        return switch (month) {
            case "January", "February", "March" -> {yield "1st";}
            case "April", "May", "June" -> "2nd";
            case "July", "August", "September" -> "3rd";
            case "October", "November", "December" -> "4th";
            default -> "Invalid month";
        };
    }

    //Lambda expression
    private static void checkLambda() {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        list1.forEach(element -> System.out.println(element));

        BinaryOperator<Integer> add = (x, y) -> x + y;
        add.apply(1, 2);
    }

    private static void checkMethodReference() {
        //Static method reference
        Function<String, Integer> function = Integer::parseInt;
        Integer functionResult = function.apply("123");

        //Bounded method reference
        String str = "Hello World";
        Supplier<Integer> function1 = str::length;
        Integer function1Result = function1.get();

        //Unbounded method reference
        Function<String, String> function2 = String::toUpperCase;
        String function2Result = function2.apply(str);

        //Constructor reference
        Supplier<String> function3 = String::new;
        String function3Result = function3.get();

        Consumer<String> function4 = System.out::println;
        function4.accept(str);
    }

    private static void checkFunctionalInterfaces() {
        //Function
        String str = "Hello";
        Function<String, String> function1 = str::concat;
        String result = function1.apply("World");

        //Supplier
        Supplier<List<Integer>> function2 = ArrayList::new;
        List<Integer> result1 = function2.get();

        //Consumer
        Consumer<String> function3 = System.out::println;
        function3.accept(str);

        //Predicate
        Predicate<String> function4 = String::isEmpty;
        Boolean result2 = function4.test(str);
    }

    //Check streams
    private static void checkStreams() {
        //Concat streams
        var stream1 = Stream.of("a", "b", "c")
                .map(String::toUpperCase);
        var stream2 = Arrays.stream(new String[]{"d", "e", "f"})
                .map(a -> a + "-");
        Stream.concat(stream1, stream2)
                .filter(a -> a.length() > 1)
                .forEach(System.out::println);

        //Generate infinite stream
        Random random = new Random();
        Stream.generate(random::nextInt)
                .limit(5)
                .forEach(System.out::println);

        Stream.iterate(0, n -> n + 1)
                .limit(5)
                .forEach(System.out::println);

    }

    //Threads and concurrency
    private static void checkThreadsInterruptAndJoin() {

        long time1 = currentTimeMillis();
        //Thread interrupt and join
        Thread thread1 = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Thread 1");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            for(int i = 0; i < 10; i++) {
                try {
                    System.out.println("Thread 2");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread3 = new Thread(() -> {
            while (true) {
                //Interrupt thread 1 after 5 seconds
                if((currentTimeMillis() - time1) > 5000) {
                    thread1.interrupt();
                    break;
                }
            }
        });
        thread1.start();
        thread3.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(!thread1.isInterrupted()) {
            thread2.start();
        }
    }

    class TestVolatile {
        boolean flag = false;
        public void toggleFlag() {
            System.out.println(flag);
            this.flag = !flag;
        }
        public boolean isFlag() {
            return flag;
        }
    }

    private void checkThreadsVolatile() {
        TestVolatile testVolatile = new TestVolatile();
        Thread thread1 = new Thread(() -> {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            testVolatile.toggleFlag();
        });
        Thread thread2 = new Thread(() -> {
            while (!testVolatile.isFlag()) {
            }
            System.out.println("Thread 2");
        });
        thread1.start();
        thread2.start();
    }

    private static void printThreadState(Thread thread) {
        System.out.println("---------------------");
        System.out.println("THREAD ID" + " : " + thread.getId());
        System.out.println("THREAD NAME" + " : " + thread.getName());
        System.out.println("THREAD GROUP" + " : " + thread.getThreadGroup());
        System.out.println("THREAD PRIORITY" + " : " + thread.getPriority());
        System.out.println("THREAD STATE" + " : " + thread.getState());
        System.out.println("THREAD ISALIVE" + " : " + thread.isAlive());
        System.out.println("---------------------");
    }

    //Consumer Producer
    public class MessageRepo {
        private String message;
        private boolean hasMessage;

        public synchronized String read() {
            while(!hasMessage) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            hasMessage = false;
            System.out.println(message);
            notifyAll();
            return message;
        }

        public synchronized void write(String message) {
            while(hasMessage) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            hasMessage = true;
            this.message = message;
            notifyAll();
        }
    }
    private void checkDeadlock() {
        MessageRepo messageRepo = new MessageRepo();
        String text = """
                Line 1
                Line 2
                Line 3
                """;
        String[] textArray = text.split("\n");
        Thread reader = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String message = messageRepo.read();
                if("Finished".equals(message)) {
                    break;
                }
            }
        });
        Thread writer = new Thread(() -> {
            for (int i = 0; i < textArray.length; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                messageRepo.write(textArray[i]);
            }
            messageRepo.write("Finished");
        });
        reader.start();
        writer.start();
    }

    //Executor service
    private static void checkExecutorService() {
        var executor1 = Executors.newSingleThreadExecutor();
        executor1.execute(() -> {
            System.out.println("Thread 1");
        });
        executor1.shutdown();
        //Equivalent to call thread1.join();
        try {
            executor1.awaitTermination(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        var executor2 = Executors.newSingleThreadExecutor();
        executor1.execute(() -> {
            System.out.println("Thread 2");
        });
        executor2.shutdown();
        //Workers Fixed Thread pool (keep alive till lifecycle of app)
        var executor3 = Executors.newFixedThreadPool(3);
        //Submitting tasks to thread pool (will be executed in FIFO order via thread pool manager)
        for(int i = 0; i < 6; i++) {
            int finalI = i;
            executor3.submit(() -> {System.out.println("Thread " + finalI);});
        }
        executor3.shutdown();
        //Cached thread pool
        var executor4 = Executors.newCachedThreadPool();
        for(int i = 0; i < 2; i++) {
            int finalI = i;
            executor4.submit(() -> {System.out.println("Thread " + finalI);});
        }
        executor4.shutdown();
        //Executor with Future
        //By default future get() is blocking
        var executor5 = Executors.newCachedThreadPool();
        List<Callable<String>> tasks = new ArrayList<>();
        tasks.add(() -> {return "Thread1";});
        tasks.add(() -> {return "Thread2";});
        tasks.add(() -> {return "Thread3";});
        try{
            //Can use submit() in a loop for each task
            var results = executor5.invokeAll(tasks);
            for(var result : results) {
                System.out.println(result.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor5.shutdown();
        }
        //Scheduled executor
        //Executor6 tasks will run with a total delay of 3 (2+1) seconds
        ScheduledExecutorService executor6 = Executors.newScheduledThreadPool(2);
        ScheduledFuture result = executor6.schedule(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread 1");
                },
                2,
                TimeUnit.SECONDS);
        long time = currentTimeMillis();
        while(currentTimeMillis() - time < 5000) {
            if(result.isDone()) {
                try {
                    System.out.println(result.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        result.cancel(true);
    }


}
