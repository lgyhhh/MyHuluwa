import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Loulu extends Creature {
    Loulu(String name, Field a)
    {
        super(name,a);
        isAlive=true;
        isGood=false;
        level=3;
        URL loc = this.getClass().getClassLoader().getResource("loulu.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }

    public void move() {
        //if(this.pos)
        int Rawx=this.pos.getX();
        int Rawy=this.pos.getY();
        /*if(Rawx>=1) {
            if (field.p[Rawx - 1][Rawy].getHolder() == null)//左边没有东西
                field.putCreature(this, Rawx - 1, Rawy);
            else if (field.p[Rawx - 1][Rawy].getHolder().isGood) {
                field.battle(this, field.p[Rawx - 1][Rawy].getHolder());
            }
        }*/

        if(Rawx>=1&&field.isEnemy(this)==1) {
            if (field.p[Rawx - 1][Rawy].getHolder() == null)//左边没有东西
                field.putCreature(this, Rawx - 1, Rawy);
            else if (field.p[Rawx -1][Rawy].getHolder().isGood == true) {
                field.battle(this, field.p[Rawx - 1][Rawy].getHolder());
            }
        }
        else if(Rawx<14&&field.isEnemy(this)==2) {
            if (field.p[Rawx + 1][Rawy].getHolder() == null)//右边没有东西
                field.putCreature(this, Rawx + 1, Rawy);
            else if (field.p[Rawx + 1][Rawy].getHolder().isGood == true) {
                field.battle(this, field.p[Rawx + 1][Rawy].getHolder());
            }
        }
        else if(Rawy>=1&&field.Isup(this)==true)
        {
            if(field.p[Rawx][Rawy-1].getHolder()==null)
                field.putCreature(this,Rawx,Rawy-1);
            else if(field.p[Rawx][Rawy-1].getHolder().isGood == true)
                field.battle(this, field.p[Rawx][Rawy-1].getHolder());
        }
        else if(Rawy<=8&&field.Isup(this)==false)
        {
            if(field.p[Rawx][Rawy+1].getHolder()==null)
                field.putCreature(this,Rawx,Rawy-1);
            else if(field.p[Rawx][Rawy+1].getHolder().isGood == true)
                field.battle(this, field.p[Rawx][Rawy+1].getHolder());
        }
    }

}
