import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Yeye extends Creature  {
    Yeye(String name, Field a)
    {
        super(name,a);
        isGood=true;
        level=1;
        URL loc = this.getClass().getClassLoader().getResource("grandpa.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
    public void move() {

        int Rawx=this.pos.getX();
        int Rawy=this.pos.getY();
        if(Rawx<14) {
            if (field.p[Rawx + 1][Rawy].getHolder() == null)//右边没有东西
                field.putCreature(this, Rawx + 1, Rawy);
            else if (field.p[Rawx + 1][Rawy].getHolder().isGood == false) {
                field.battle(this, field.p[Rawx + 1][Rawy].getHolder());
            }
        }
    }

}
