import java.lang.Math;

public class Main {
    public static int countDigits(int num){
        int count=0;
        while(num>0){
            num/=10;
            count++;
        }
        return count;
    }

    public static String concatWords(int num, int digits, int count){
        //count will start from 1 till 
        String temp;
       if(count<=digits){
            if(count== 1){
                temp=returnWordTens(num%100);
                return concatWords(num/100, digits, count+2)+temp;
            }
            else if (count==3){
                temp=returnWordUnits(num%10);
                return concatWords(num/10, digits, count+1)+temp+" hundread and ";
            }
            else if (count==4){
                temp=returnWordTens(num%100);
                return concatWords(num/100, digits, count+2)+temp+" thousand ";
            }
            else if (count==6){
                temp=returnWordTens(num%100);
                return concatWords(num/100, digits, count+2)+temp+" lakh ";
            }
            else if (count==8){
                temp=returnWordTens(num%100);
                return temp+" crore ";
            }
            else
                return "";
       }
        else
            return "";
    }

    public static String returnWordUnits(int num){
        // num range [0-9]
        if(num==1) return "one";
        else if(num==2) return "two";
        else if(num==3) return "three";
        else if(num==4) return "four";
        else if(num==5) return "five";
        else if(num==6) return "six";
        else if(num==7) return "seven";
        else if(num==8) return "eight";
        else if(num==9) return "nine";
        else return "";
    }

    public static String returnWordTens(int num){
        //range of num[10-99]
        int tens=num/10;
        int units=num%10;
        if(tens==2) return "twenty "+returnWordUnits(units);
        else if(tens==3) return "thirty "+returnWordUnits(units);
        else if(tens==4) return "fourty "+returnWordUnits(units);
        else if(tens==5) return "fifty "+returnWordUnits(units);
        else if(tens==6) return "sixty "+returnWordUnits(units);
        else if(tens==7) return "seventy "+returnWordUnits(units);
        else if(tens==8) return "eighty "+returnWordUnits(units);
        else if(tens==9) return "ninty "+returnWordUnits(units);
        else if(tens==1){
            if(units==1) return "eleven";
            else if(units==2) return "tweleve";
            else if(units==3) return "thirteen";
            else if(units==4) return "fourteen";
            else if(units==5) return "fifteen";
            else if(units==6) return "sixteen";
            else if(units==7) return "seventeen";
            else if(units==8) return "eighteen";
            else if(units==9) return "nineteen";
            else return "";
        }
        else return returnWordUnits(units);
    }

    public static void main(String[] args) {
        float num=-132465.456f, actualNum;
        boolean minusflag=false;

        actualNum = num;

        if(num<0){
            num=num*-1;
            minusflag=true;
        }

        int digits=countDigits((int)num);

        System.out.println("Number: "+actualNum);
        System.out.println("Digits: "+digits);

        if(num==0)
            System.out.println("Words: Zero");
        else if(digits>8)
            System.out.println("Cannot show word format for numbers more than 8 digits.");
        else if(minusflag)
            System.out.println("Word: minus "+concatWords((int)num, digits, 1));
        else
            System.out.println("Word: "+concatWords((int)num, digits, 1));
        System.out.println();
    }
}
