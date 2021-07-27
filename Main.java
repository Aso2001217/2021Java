import java.util.Random;
import java.util.Scanner;


public class Main{
    public static void main(String[] args) {
        int[][] field = new int[5][5];
        Ship[] s = new Ship[3];
        for(int i=0; i<s.length; i++){
            s[i] = new Ship(0,0,i+1);
        }
        boolean isAllDead = false;
        // Start Game
        //Title
        System.out.println("******************************");
        System.out.println("　　　　　戦艦ゲーム　　　　　　");
        System.out.println("******************************");

        int turn = 1;
        int userX, userY;
        Scanner sc = new Scanner(System.in);
        //set ships' positions
        for(int i=0; i<s.length; i++){
            generateRandom(s[i],field);
        }
        // main operations
        while(true){
            System.out.println("-----------[ターン" + turn + "]-----------");
            //showStatus
            for(int i=0; i<s.length; i++){
                System.out.printf("船%d：%s\n", i+1, showStatus(s[i].isAlive()));
            }
            //show field for debugging
            for(int i=0; i<field.length; i++){
                for(int j=0; j<field[i].length; j++){
                    System.out.print(field[i][j]+" ");
                }
                System.out.println("");
            }
            //inputPositions
            do{
                System.out.println("爆弾のX座標を入力してください(1-5)");
                userX = Integer.parseInt(sc.next());
            } while(!isRange(userX));
            do{
                System.out.println("爆弾のY座標を入力してください(1-5)");
                userY = Integer.parseInt(sc.next());
            } while(!isRange(userY));
            System.out.printf("%dターン目：%d, %dに爆弾！\n", turn, userX, userY);
            //forArrayAdjustment
            userX--;
            userY--;
            //hit operations
            System.out.printf("%dターン目：%s", turn, hitJudgement(s, userX, userY, field));
            for(int i=0; i<s.length; i++){
                if(!s[i].isAlive()){
                    isAllDead = true;
                } else {
                    isAllDead = false;
                    break;
                }
            }
            if(isAllDead){
                System.out.println("You Win!!");
            }
            turn++;
            System.out.print("0で終了：");
            if(sc.nextInt() == 0){
                break;
            }
        }
        sc.close();
    }
    public static void generateRandom(Ship s, int[][] field){
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(5);
            y = random.nextInt(5);
        }while(field[x][y] != 0);
        s.setX(x);
        s.setY(y);
        field[s.getX()][s.getY()] = s.getShipNum();
    }
    public static  boolean isRange(int n) {
        if(n<1 || n>5){
            return false;
        }
        return true;
    }
    public static String showStatus(boolean alive){
        String rtn = "DEAD!";
        if(alive){
            rtn = "生きてる!";
        }
        return rtn;
    }
    public static String hitJudgement(Ship[] s, int x, int y, int[][] field){
        String rtn = "";
        for(int i=0; i<s.length; i++){
            if(x == s[i].getX() && y == s[i].getY()){
                rtn += "船"+s[i].getShipNum()+"命中！\n";
                s[i].damaged();
                field[x][y] = 0;
                if(s[i].isAlive()){
                    generateRandom(s[i],field);
                } else {
                    s[i].setX(-1);
                    s[i].setY(-1);
                }
                break;
            }
        }
        boolean[] isNear = new boolean[3];
        nearShips(s, x, y, field, isNear);
        for(int i=0; i<s.length; i++){
            if(isNear[i]){
                rtn += "船"+s[i].getShipNum()+"波高し！\n";
            }
        }
        for(boolean b : isNear){
            b = false;
        }
        if(rtn == ""){
            rtn = "何もなし\n";
        }
        return rtn;
    }

    public static void nearShips(Ship[] s, int x, int y, int[][] field, boolean[] isNear){
        int posX = x++;
        int negX = x--;
        int posY = y++;
        int negY = y--;
        for(int i=0; i<s.length; i++){
            if(x==s[i].getX() && (posY == s[i].getY() || negY == s[i].getY())){
                    isNear[i] = true;
            } else if(y==s[i].getY() && (posX == s[i].getX() || negX == s[i].getX())){
                    isNear[i] = true;
            }
        }
    }
}