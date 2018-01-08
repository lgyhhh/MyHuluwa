import java.awt.*;

abstract public class Creature implements Runnable{
    private String name;
    protected Position pos;
    public Field field;
    protected boolean isAlive;
    protected boolean isGood;
    protected int level;//战斗力
    private Image image;

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image img) {
        image = img;
    }

    Creature(String name,Field a)
    {
        this.field=a;
        this.name=name;
        isAlive=true;
        pos=null;
    }

    public  void setAlive()
    {
        isAlive=false;
        if(this.pos!=null)
          this.freePosition();
    }

    public int getLevel()
    {
        return level;
    }

    public void report(){
        System.out.print(name);
    }

    public void setPosition(Position position)//设置位置
    {
        this.pos=position;
        position.setHolder(this);
    }
    public void freePosition()//离开位置
    {
        this.pos.freeHolder();
        this.pos=null;
    }

    public Position getPosition() {
        return pos;
    }

    public String getName(){
        return name;
    }
    abstract public void move();

    public void  run()
    {
        while(!Thread.interrupted()&&isAlive==true)
        {
            this.move();
            //TimeUnit.MILLISECONDS.sleep(100);
            try{
                Thread.sleep(600);
            }catch (Exception e){

            }
        }
    }

}
