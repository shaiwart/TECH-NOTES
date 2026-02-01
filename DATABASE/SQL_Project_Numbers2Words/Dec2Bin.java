public class Dec2Bin{
    public static int Dec2BinFun(int num){
        if(num<2)
            return num;
        return (Dec2BinFun(num/2)*10)+(num%2);
    }
    public static void main(String[] args) {
        System.out.println("+---------------+---------------+");
        System.out.println("| Decimal\t| Binary\t|");
        System.out.println("+---------------+---------------+");
        for(int num=0; num<31; num++){
            if(num<=15) System.out.println("| "+num+"\t\t| "+Dec2BinFun(num)+" \t\t|");
            else System.out.println("| "+num+"\t\t| "+Dec2BinFun(num)+" \t|");
        }
        System.out.println("+---------------+---------------+");
    }
}