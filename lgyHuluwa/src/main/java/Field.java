import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Field extends JPanel implements Runnable {
    protected Position [][]p;
    ImageIcon icon;
    Image img;
    ExecutorService executor = Executors.newCachedThreadPool();
    private ArrayList creatures=new ArrayList();
    private File profile;
    private BufferedReader reader = null;
    private BufferedWriter writer = null;
    private int mode = 0;
    private Thread myThread;
    private final int hangMSec = 100;
    /*
    0: unstarted
    1: play is on
    2: replay is on
    3: completed
    */
    Field()
    {
        p=new Position[15][10];
        for(int i=0;i<15;i++)
            for(int j=0;j<10;j++)
            {
                p[i][j]=new Position(i,j);
            }
        icon=new ImageIcon(getClass().getResource("b.png"));
        img=icon.getImage();
        init();
        // setFocusable(true);
        //initWorld();
    }
    public void screenShot()
    {
        synchronized (p) {
            try {
                for (int i = 0; i < 15; i++)
                    for (int j = 0; j < 10; j++)
                        if (p[i][j].getHolder() != null)
                            writer.write(p[i][j].getHolder().getName() + "\t" + i + "\t" + j + "\t");
                writer.write("\n");
                writer.flush();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
    public void paintComponent(Graphics g)
    {
        if (mode == 1)
            screenShot();
        super.paintComponent(g);
        g.drawImage(img, 0, 0,this.getWidth(), this.getHeight(), this);//img是整体的背景
        for(int i=0;i<creatures.size();i++)//
        {
            Creature c = (Creature) creatures.get(i);
            if(c.isAlive==true)
                g.drawImage(c.getImage(),c.pos.getX()*100,c.pos.getY()*100,100,100,this);
        }
    }

    public void init() {
        myThread = new Thread(this);
        Creature shejing = new Shejing("蛇精", this);
        creatures.add(shejing);
        Creature xiezijing = new Xiezijing("蝎精", this);
        creatures.add(xiezijing);
        Creature yeye = new Yeye("爷爷", this);
        creatures.add(yeye);
        Creature[] loulu = new Creature[7];
        Creature[] huluwa = new Creature[7];
        for (int i = 0; i < 7; i++) {
            int tt = i + 1;
            String name = "huluwa" + tt + ".png";
            huluwa[i] = new Huluwa(i + 1, this, name);
            creatures.add(huluwa[i]);
        }
        for (int i = 0; i < 7; i++) {
            loulu[i] = new Loulu("喽啰", this);
            creatures.add(loulu[i]);
        }
        new Changshe().Do(0, 2, this, huluwa, 7);
        this.putCreature(xiezijing, 8, 5);
        new Heyi().Do(8, 4, this, loulu, 7);
        this.putCreature(yeye, 0, 1);
        this.putCreature(shejing, 8, 3);
        //field.print();
/*
      for (Creature c : loulu) {
         executor.execute(c);
      }
      for (Creature d : huluwa) {
         executor.execute(d);
      }
      executor.execute(yeye);
      executor.execute(shejing);
      executor.execute(xiezijing);
      //if()
*/
    }

    public void init2()
    {
        if (mode != 0)
            return;
        mode = 1;
        profile = new File(new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()));
        try
        {
            if (!profile.exists())
                profile.createNewFile();
            writer = new BufferedWriter(new FileWriter(profile.getAbsoluteFile()));
        } catch (IOException e) {System.err.println(e.getMessage());writer = null;}
        myThread.start();
        for(int i=0;i<creatures.size();i++)
        {
            Creature c=(Creature)creatures.get(i);
            executor.execute(c);
        }
    }

    public void init3()
    {
        if (mode != 0)
            return;
        mode = 2;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setDialogTitle("选择存档");
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            profile = fileChooser.getSelectedFile();
            try
            {
                reader = new BufferedReader(new FileReader(profile.getAbsoluteFile()));
            } catch (IOException e) {System.err.println(e.getMessage());reader = null;}
        }
        myThread.start();
    }
    public void run()
    {
        try
        {
            while(!Thread.interrupted()) {
                repaint();
                TimeUnit.MILLISECONDS.sleep(hangMSec);
                if (isOver()) {
                    executor.shutdownNow();
                    repaint();
                    screenShot();
                    mode = 3;
                    if (writer != null)
                        try {
                            writer.close();
                        } catch (IOException e) {
                        }
                    if (reader != null)
                        try {
                            reader.close();
                        } catch (IOException e) {
                        }
                    myThread.interrupt();
                } else if (mode == 2) {
                    for (int i = 0; i < 15; i++)
                        for (int j = 0; j < 10; j++)
                            p[i][j].setHolder(null);
                    String lineRec = null;
                    String name;
                    int positionX;
                    int positionY;
                    try {
                        lineRec = reader.readLine();
                    } catch (IOException iOException) {
                    }
                    if (lineRec == null)
                        continue;
                    creatures = new ArrayList();
                    try {
                        while (true) {
                            name = lineRec.substring(0, lineRec.indexOf("\t"));
                            lineRec = lineRec.substring(lineRec.indexOf("\t") + 1);
                            positionX = Integer.valueOf(lineRec.substring(0, lineRec.indexOf("\t")));
                            lineRec = lineRec.substring(lineRec.indexOf("\t") + 1);
                            positionY = Integer.valueOf(lineRec.substring(0, lineRec.indexOf("\t")));
                            lineRec = lineRec.substring(lineRec.indexOf("\t") + 1);
                            Creature c;
                            switch (name) {
                                case "老大":
                                    c = new Huluwa(1, this, "huluwa1.png");
                                    putCreature(c, positionX, positionY);
                                    creatures.add(c);
                                    break;
                                case "老二":
                                    c = new Huluwa(2, this, "huluwa2.png");
                                    putCreature(c, positionX, positionY);
                                    creatures.add(c);
                                    break;
                                case "老三":
                                    c = new Huluwa(3, this, "huluwa3.png");
                                    putCreature(c, positionX, positionY);
                                    creatures.add(c);
                                    break;
                                case "老四":
                                    c = new Huluwa(4, this, "huluwa4.png");
                                    putCreature(c, positionX, positionY);
                                    creatures.add(c);
                                    break;
                                case "老五":
                                    c = new Huluwa(5, this, "huluwa5.png");
                                    putCreature(c, positionX, positionY);
                                    creatures.add(c);
                                    break;
                                case "老六":
                                    c = new Huluwa(6, this, "huluwa6.png");
                                    putCreature(c, positionX, positionY);
                                    creatures.add(c);
                                    break;
                                case "老七":
                                    c = new Huluwa(7, this, "huluwa7.png");
                                    putCreature(c, positionX, positionY);
                                    creatures.add(c);
                                    break;
                                case "爷爷":
                                    c = new Yeye(name, this);
                                    putCreature(c, positionX, positionY);
                                    creatures.add(c);
                                    break;
                                case "喽啰":
                                    c = new Loulu(name, this);
                                    putCreature(c, positionX, positionY);
                                    creatures.add(c);
                                    break;
                                case "蛇精":
                                    c = new Shejing(name, this);
                                    putCreature(c, positionX, positionY);
                                    creatures.add(c);
                                    break;
                                case "蝎精":
                                    c = new Xiezijing(name, this);
                                    putCreature(c, positionX, positionY);
                                    creatures.add(c);
                                    break;
                                default:
                                    throw new Exception("文件损坏");
                            }
                            if (lineRec.indexOf("\t") < 0)
                                break;
                        }
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        myThread.interrupt();
                    }
                }
            }
        } catch (InterruptedException e) {}
    }
    public Boolean isOver()
    {
        int g_num=0;
        int f_num=0;
        for(int i=0;i<15;i++)
            for(int j=0;j<10;j++)
            {
                if(p[i][j].getHolder()!=null)
                {
                    if(p[i][j].getHolder().isGood==true)
                        g_num++;
                    else
                        f_num++;
                }
            }
        if(g_num==0||f_num==0)
        {
            return true;
        }
        else
            return false;

    }
    public int isEnemy(Creature c)//判断creature所在行是否有其他的对手
    {
        int lnum=0,rnum=0;
        int y=c.getPosition().getY();
        for(int i=0;i<15;i++)
        {
            if(p[i][y].getHolder()!=null&&p[i][y].getHolder().isGood!=c.isGood)
            {
                if(i<c.getPosition().getX())
                    lnum++;
                else
                    rnum++;
            }
        }
        if(lnum+rnum==0)
            return -1;
        else if(lnum>rnum)
            return 1;
        else
            return 2;//-1表示没有其他对手，1表示左边有敌人，2表示右边有敌人


    }

    public Boolean Isup(Creature c)
    {
        int y=c.getPosition().getY();
        int upnum=0;
        int downnum=0;
        for(int i=0;i<15;i++)
        {
            if(y>=1&&p[i][y-1].getHolder()!=null&&p[i][y-1].getHolder().isGood!=c.isGood )
                upnum++;
            if(y<=8&&p[i][y+1].getHolder()!=null&&p[i][y+1].getHolder().isGood!=c.isGood)
                downnum++;
        }
        if(upnum>=downnum)
            return true;
        else
            return false;
    }


    public void battle(Creature c1,Creature c2)
    {
        if(c2.isAlive) {
            int level1 = c1.getLevel();
            int level2 = c2.getLevel();
            Random rand = new Random();
            int r = rand.nextInt(level1 + level2);
            if (r < level1)
                c2.setAlive();//让C2死
            else
                c1.setAlive();
        }
    }

    public void print()
    {
        for(int i=0;i<15;i++) {
            for (int j = 0; j < 10; j++) {
                if (p[i][j].getHolder() == null)//位置上没有东西
                    System.out.print("     ");
                else
                    p[i][j].getHolder().report();
            }
            System.out.println();
        }
    }
    public void putCreature(Creature c,int x,int y)//将生物放在x,y上
    {
        if(x<0||x>=15||y<0||y>=10)
            return;
        if(c.getPosition()!=null)
            c.freePosition();//首先将生物离开原来的位置
        c.setPosition(p[x][y]);
    }
    public Creature getCreature(int x,int y)//在（x,y）上的生物
    {
        if(x<0||x>=15||y<0||y>=10)
            return null;
        return p[x][y].getHolder();

    }
    public void removeCreature(int x,int y)//将x,y上面的生物移开
    {
        if(x<0||x>=15||y<0||y>=10)
            return;
        if(p[x][y].getHolder()!=null)
            p[x][y].getHolder().freePosition();

    }
    public void updateCreature(Creature ...creatures)
    {
        for(int i=0;i<creatures.length;i++)
        {
            if(creatures[i].getPosition()!=null)
                creatures[i].freePosition();
        }
    }
/*
   public void record() throws IOException{
      StringBuffer record=new StringBuffer();
      String name;
      String temp;
      int x,y;
      Boolean z;
      for(int i=0;i<this.creatures.size();i++)
      {
         Creature c=(Creature)this.creatures.get(0);
         name=c.getName();
         x=c.getPosition().getX();
         y=c.getPosition().getY();
         z=c.isAlive;
         temp=name+" "+x+" "+y+" ";
         record.append(temp);
         record.append(z);
      }
   }
   */
}
