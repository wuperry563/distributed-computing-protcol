
import java.util.LinkedList;
import java.util.ListIterator;

public class TNode {
    public int nodeId;
    public TNode parent;
    public LinkedList<TNode> childern;

    public TNode(int nodeId, TNode par){
        this.nodeId = nodeId;
        parent = par;
        childern = new LinkedList<TNode>();
    }


    public String toString(){
        String res = "";
        res += "id: " + nodeId + "\n";
        if(parent!=null){
            res += "p: " + parent.nodeId + "\n";
        }else{
            res += "p: null \n";
        }

        ListIterator<TNode> it = childern.listIterator();
        if(childern.isEmpty()){
            res += "c: null\n";
            return res;
        }
        res += "c: ";
        while(it.hasNext()){
            res += it.next().nodeId + " ";
        }
        res += "\n";
        return res;
    }

    public static void display(TNode root){
        if(root==null) return;
        System.out.println(root.toString());
        for(int i = 0; i < root.childern.size(); i++){
            display(root.childern.get(i));
        }
    }

}
