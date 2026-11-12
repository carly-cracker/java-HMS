package demoTests;
import java.util.concurrent.atomic.AtomicInteger;

import MedicineInventory;

public class DispenseDemo {
    public static void main(String[] args) throws InterruptedException {
        MedicineInventory inventory = new MedicineInventory();
        inventory.addStock("Paracetamol", 1000);
        AtomicInteger successes = new AtomicInteger();

        Runnable pharmacistWork = () -> {
            for (int i = 0; i < 200; i++) {
                if (inventory.dispenseMedicine("Paracetamol", 1)) {
                    successes.incrementAndGet();
                }
            }
        };

        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(pharmacistWork, "Pharmacist-" + i);
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }

        int left = inventory.getStock("Paracetamol");
        System.out.println("Dispensed: " + successes.get());
        System.out.println("Stock left: " + left);
        System.out.println("Total: " + (successes.get() + left) + " (should be 1000)");
    }
}