class Th implements Thread{
    public void run()
    {
        System.out.println("this is code of multithreading");
    }

}
public class Ass4{
public static void main(String[] args)
{

    Th s1= new Th();
    s1.run();
}

}