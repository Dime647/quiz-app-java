public class Main{
    public static void main(String[] args){
        int [] daysInMonth= {31,28,31,30,31,30,31,31,30,31,30,31};
        String[] monthNames= {"Jan","Feb","March","April","May","June","July","August","Septemeber","Novemeber","December"};


        for (int i=0;i<daysInMonth.length;i++){
            System.out.println(monthNames[i]+":"+daysInMonth[i]+"days");
        }
    }
}