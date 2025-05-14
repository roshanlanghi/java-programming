import java.util.Scanner;

public class exception3 {



    public static void result(int marks)
    {
       
         if(marks<18)
         {
            
            throw new ArithmeticException("student is fail");
         }
         else{
            System.out.println("student is pass");
         }




    }

     public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter marks: ");
        int marks = sc.nextInt();

        result(marks); 
        try {
            result(marks);
        } catch (ArithmeticException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
}