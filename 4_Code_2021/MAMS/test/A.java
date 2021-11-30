import java.util.Date;



public class A {
  
  static public void main(String[] args){
    Date d = new Date();
    System.out.println(d.getTime());
    try{
      Thread.sleep(1000);
    }catch(Exception ex){

    }
    System.out.println(new Date().getTime());
  }
}