import java.util.HashMap;
import java.util.Map;

public class MedicineInventory{
    private final Map<String, Integer> stock = new HashMap<> ();

    public void addStock(String medicine, int quantity){
        stock.put(medicine, stock.getOrDefault(medicine, 0) + quantity);
    }

    public int getStock(String medicine){
        return stock.getOrDefault(medicine, 0);
    }

    // Returns true if dispensed, false if there wasn't enough stock
    public boolean dispenseMedicine(String medicine, int quantity ){
        int current = getStock(medicine);
        if (current >= quantity){
            stock.put(medicine, current-quantity);
            return true;
        }
        return false;
    }
}