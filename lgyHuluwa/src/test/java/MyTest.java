import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MyTest {
 @Test
    public void testInit(){
     Creature c=new Huluwa(1,new Field(),"huluwa1.png");
     assertEquals(c.isAlive,true);
     assertEquals(c.getName(),"老大");
 }
 @Test
    public void testPos(){
        Field field=new Field();
        assertEquals(field.getCreature(16,0),null);
        Creature c=new Xiezijing("xx",field);
        field.putCreature(c,3,4);
        assertEquals(field.getCreature(3,4),c);
        field.removeCreature(3,4);
        assertEquals(field.getCreature(3,4),null);
 }
 @Test
    public void testBattle(){
        Field field=new Field();
        assertEquals(field.isOver(),false);
        assertEquals(field.isEnemy(field.p[0][4].getHolder()),2);
 }


}
