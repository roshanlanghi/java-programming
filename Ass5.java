class Th extends Thread{
    public void run()
    {
       
        for(int i=0;i<10;i++)
        {
            System.out.println("thread1:"+i);
        }


    }

}
class Th2 extends Thread{
  
    publ+3525ic void run()
    {
        
        for(int i=0;i<10;i++)
        {
            System.out.println("thread 2:"+ i);
        }


    }

}


public class Ass5 {
    public static void main(String[] args)
    {
        Th s1=new Th();
        Th2 s2= new Th2();
        s1.start(); s2.start();
        
        

    }
    
}
