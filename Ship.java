public class Ship {
    private int x;
    private int y;
    private int hp = 3;
    private int shipNum;
    public Ship(int x, int y, int shipNum){
        this.setX(x);
        this.setY(y);
        this.setShipNum(shipNum);
    }
    public int getShipNum() {
        return shipNum;
    }
    public void setShipNum(int shipNum) {
        this.shipNum = shipNum;
    }
    public int getHp() {
        return hp;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public boolean isAlive(){
        if(this.hp>0){
            return true;
        }
        return false;
    }
    public void damaged() {
        this.hp--;
    }
}