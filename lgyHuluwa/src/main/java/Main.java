import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main extends JFrame{
    public Main()
    {
        Field field=new Field();
        //init(field);
        add(field);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500,1000);
        setLocationRelativeTo(null);
        setTitle("葫芦娃大战妖精");
        setVisible(true);

        addKeyListener(new KeyAdapter(){
           public void keyPressed(KeyEvent e)
           {
               switch (e.getKeyCode())
               {
                   case KeyEvent.VK_SPACE:
                       field.init2();
                       break;
                   case KeyEvent.VK_L:
                       field.init3();
               }
           }
        });

    }

    public static void main(String[] args) {

        Main main=new Main();
    }

}
