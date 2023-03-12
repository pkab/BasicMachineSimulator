class CacheData{
    public short key;
    public short val;
}
public class Cache extends Converter{
    public CacheData[] lines;
    public int front,rear;
    public Cache(){
        this.front = this.rear = 0;
        lines = new CacheData[16];
        for(int i=0;i<16;i++)
            lines[i] = new CacheData();
    }
    public void push(short key,short val){
        if(this.rear == 16){
            //front++;
            //if(front==16) front = front%16;
            for(int i=0;i<15;i++){
                lines[i].key=lines[i+1].key;
                lines[i].val=lines[i+1].val;
            }
            lines[15].key = key;
            lines[15].val = val;
            return ;
        }
        if(rear==0){
            lines[rear].key=key;
            lines[rear].val=val;
            rear++;
        }else{
            lines[rear].key = key;
            lines[rear].val = val;
            rear++;
        }
    }
}
