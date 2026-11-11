public class LabTech{
    private final String name;

    public LabTech (String name){
        this.name = name;
    }

    public void process(LabRequest request){
        System.out.println("Labtech" + name + "processed" + request );
    }
}