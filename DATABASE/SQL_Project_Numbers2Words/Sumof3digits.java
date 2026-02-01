public class Sumof3digits{
    public static void main(String[] args) {
        int num;
        for(int i=0; i<10000000; i++){
            if((num=sumOfDigits(i))*3==i)
                System.out.println(i);
        }
    }

    public static int sumOfDigits(int num){
        int sum=0;
        int units;
        while(num>0){
            units=num%10;
            sum+=units;
            num/=10;
        }
        return sum;
    }
}