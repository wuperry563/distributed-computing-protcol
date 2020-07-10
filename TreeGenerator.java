import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class TreeGenerator {
    ArrayList<Integer>[] neighbors;
    HashMap<Integer, TNode> treeNodes;
    int localId = 0;

    public TreeGenerator(int localId, ArrayList<Integer>[] nbs){
        neighbors = nbs;
        this.localId = localId;
        treeNodes = new HashMap<Integer, TNode>();
    }

    public TNode getCurTreeNode(){
        return treeNodes.get(localId);
    }

    public void generate(){
        TNode root = new TNode(0,null);
        treeNodes.put(0, root);
        TNode parent = root;

        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.addLast(0);
        while(!queue.isEmpty()){
            int curId = queue.pollFirst();
            parent = treeNodes.get(curId);
            for(int j = 0; j < neighbors[curId].size(); j++){
                int nbId = neighbors[curId].get(j);
                if(treeNodes.containsKey(nbId))  continue;
                TNode child = new TNode(nbId, parent);
                treeNodes.put(nbId, child);
                parent.childern.add(child);
                queue.addLast(nbId);
            }
        }
    }


}
