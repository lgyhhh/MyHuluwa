public class Changshe implements Formation {
    public void Do(int x,int y,Field s,Creature []creatures,int length){
        for(int i=0;i<length;i++){
            s.removeCreature(x,y+i);
            s.putCreature(creatures[i],x,y+i);
        }
    }
}
