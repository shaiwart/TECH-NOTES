public class SubArrayMaxSum {
    public static void main(String[] args) {
        int[] arr = {-1, -7, -3, -2,-5,-1,-8, -9};
        int max=arr[0];
        int start=0, end=0, sum=arr[0];
        for(int i=0; i<arr.length; i++){
            sum=0;
            for(int j=i; j<arr.length; j++){
                sum+=arr[j];
                if(sum>max){
                    start=i;
                    end=j;
                    max=sum;
                }
            }
        }
        System.out.println("The array is: ");
        for(int i=0; i<arr.length; i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
        System.out.println("The max. sum of the subarray is: "+max);
        System.out.println("The subarray is: ");
        for(int i=start; i<=end; i++){
            System.out.print(arr[i]+" ");
        }
    }
}
