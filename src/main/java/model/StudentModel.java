package model;

public class StudentModel {
    private int id;
    private String name;

    public StudentModel(final String name){
        // Student s1 = new Student("ahmad");
        // Student s2 = new Student("ihab");
        // int x = 10;
        // identity(1, 1)
        //" "
        setName(name);
    }

    public StudentModel(final int id, final String name){
        this.id = id;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.id;
    }

    public void setName(final String name){
        if(name == null || name.isBlank()){//""
            throw new IllegalArgumentException("name is empty");
        }
        if(name.length() > 50) throw new IllegalArgumentException("name is too big Max(50)");
        this.name = name;
    }

    @Override
    public String toString() {
        // s1.toString();
        return "id: "+this.id
                +" Name: "+this.name;
    }
}
