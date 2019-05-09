package Q3;

import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        MyQueue<Integer> shares = new MyQueue<>();
        MyQueue<Integer> prices = new MyQueue<>();
        int totalCapital = 0;
        
        while(true){
            System.out.println("Enter your query (Buy / Sell 'x' Shares at $'y' each) : ");
            String query = s.nextLine();
            if(query.isEmpty()){
                System.out.println("Final Capital Gain / Loss : " + totalCapital);
                break;
            }
            
            switch(query.charAt(0)){
                case 'B':
                case 'b':
                    System.out.println("Buying now...");
                    shares.enqueue(getShares(query));
                    prices.enqueue(getPrices(query));
                    break;
                
                case 'S':
                case 's':
                    int soldShare = getShares(query);
                    int soldPrice = getPrices(query);
                    System.out.println("Selling the shares now...");
                    while(true){
                        if(shares.peek()==null){
                            System.out.println("No shares to sell!");
                            break;
                        }
                        
                        if(soldShare>shares.peek()){
                            int removedShares = shares.dequeue();
                            totalCapital += (removedShares*(soldPrice-prices.dequeue()));
                            System.out.println("Total Capital Gain/Loss : " + totalCapital);
                            soldShare -= removedShares;
                        } else if(soldShare<shares.peek()){
                            totalCapital += (soldShare*(soldPrice-prices.peek()));
                            shares.replace(shares.peek()-soldShare);
                            System.out.println("Total Capital Gain/Loss : " + totalCapital);
                            break;
                        } else if(soldShare==shares.peek()){
                            totalCapital += (shares.dequeue()*(soldPrice-prices.dequeue()));
                            System.out.println("Total Capital Gain/Loss : " + totalCapital);
                            break;
                        }
                    }
                    break;
                
                default:
                    System.out.println("Wrong query format!");
                    break;
            }
            
            System.out.println(shares);
            System.out.println(prices);
        }
    }
    
    public static int getShares(String query){
        String [] words = query.split("\\s");
        for(String word : words){
            if(word.matches("[0-9]+")){
                return Integer.parseInt(word);
            }
        }
        return 0;
    }
    
    public static int getPrices(String query){
        String [] words = query.split("\\s");
        for(String word : words){
            if(word.charAt(0)=='$'){
                return Integer.parseInt(word.substring(1));
            }
        }
        return 0;
    }
}
