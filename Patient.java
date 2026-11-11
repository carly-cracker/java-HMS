public class Patient {
    //creating data with private final so that other classes cannot have write access to the patients 'encapsulation'
    private final int id;
    private final String name;
    private final String condition;

    public Patient(int id, String name, String condition) {
        this.id = id;
        this.name = name;
        this.condition = condition;

       } 
    //using getters to allow other classes to read the data but not write to it
    public int getId(){return id;}
    public String getName(){return name;}
    public String getCondition(){return condition;}

    @Override 
    public String toString() {
        return "patient# " + id + " " + name + " (" + condition + ")" ;
    }
}
