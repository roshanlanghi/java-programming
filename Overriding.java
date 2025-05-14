class Employee {
    void display() {
        System.out.println("Employee details:\n name:abcd\n company:xyz");
    }
}

class Manager extends Employee {
    @Override
    void display() {
        System.out.println("Manager details:\n name:abc\ncompany:cocacola");
    }
}

public class Overriding{
    public static void main(String[] args) {
        System.out.println("");
        Employee e = new Manager();  
        Employee s = new Employee(); 
        
        e.display(); 
        System.out.println("");
        s.display();
    }
}
