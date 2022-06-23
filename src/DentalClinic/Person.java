package DentalClinic;

public class Person {

    private String Name = null;
    private String time = null;

    public Person() {
    }

    public Person(String Name, String time) {
        this.Name = Name;
        this.time = time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
