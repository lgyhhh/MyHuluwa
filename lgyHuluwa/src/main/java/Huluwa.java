import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

enum Name{
    老大,老二,老三,老四,老五,老六,老七
}
public class Huluwa extends Creature{

    int rank;
    Huluwa(int a,Field b,String picture)
    {
        super(Name.values()[a-1].toString(),b);
        this.rank=a;
        isAlive=true;
        isGood=true;
        level=10;
        URL loc = this.getClass().getClassLoader().getResource(picture);
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
    public void move() {

        int Rawx=this.pos.getX();
        int Rawy=this.pos.getY();
        if(Rawx>=1&&field.isEnemy(this)==1) {
            if (field.p[Rawx - 1][Rawy].getHolder() == null)//左边没有东西
                field.putCreature(this, Rawx - 1, Rawy);
            else if (field.p[Rawx -1][Rawy].getHolder().isGood == false) {
                field.battle(this, field.p[Rawx - 1][Rawy].getHolder());
            }
        }
        else if(Rawx<14&&field.isEnemy(this)==2) {
            if (field.p[Rawx + 1][Rawy].getHolder() == null)//右边没有东西
                field.putCreature(this, Rawx + 1, Rawy);
            else if (field.p[Rawx + 1][Rawy].getHolder().isGood == false) {
                field.battle(this, field.p[Rawx + 1][Rawy].getHolder());
            }
        }
        else if(Rawy>=1&&field.Isup(this)==true)
        {
            if(field.p[Rawx][Rawy-1].getHolder()==null)
                field.putCreature(this,Rawx,Rawy-1);
            else if(field.p[Rawx][Rawy-1].getHolder().isGood == false)
                field.battle(this, field.p[Rawx][Rawy-1].getHolder());
        }
        else if(Rawy<=8&&field.Isup(this)==false)
        {
            if(field.p[Rawx][Rawy+1].getHolder()==null)
                field.putCreature(this,Rawx,Rawy-1);
            else if(field.p[Rawx][Rawy+1].getHolder().isGood == false)
                field.battle(this, field.p[Rawx][Rawy+1].getHolder());
        }

    }
}
