import java.util.*;

public class FiboHeapPrinter {

    public static class HeapNodeEntry {

        private Integer key;
        private Integer rank;

        public HeapNodeEntry(Integer key, Integer rank) {
            this.key=key;
            this.rank=rank;
        }

        public Integer getKey() {
            return key;
        }

        public Integer getRank() {
            return rank;
        }
    }

    private static void prepLevels(FibonacciHeap.HeapNode node, int curLevel, ArrayList<HeapNodeEntry>[] levels) {
        if (node == null) return;
        FibonacciHeap.HeapNode cur = node;
        if (levels[curLevel]==null) levels[curLevel]=new ArrayList<>();
        do {
            levels[curLevel].add(new HeapNodeEntry(cur.getKey(), cur.getRank()));
            if (cur.getChild() != null) prepLevels(cur.getChild(), curLevel + 1, levels);
            cur = cur.getNext();
        } while (cur != node);
    }

    private static void printLevels(ArrayList<HeapNodeEntry>[] levelValues) {
        StringBuilder sb;
        for (int level=0;level<levelValues.length;level++) {
            sb = new StringBuilder();
            for (HeapNodeEntry entry: levelValues[level]) {
                sb.append(String.format("%s(%s)", entry.getKey(), entry.getRank()));
                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            System.out.println(String.format("%1$4s: "+ sb.toString(), level));
        }
    }

    public static void printTree(FibonacciHeap.HeapNode treeRoot) {
        ArrayList<HeapNodeEntry>[] levelValues = new ArrayList[treeRoot.getRank()+1];
        levelValues[0] = new ArrayList<>();
        levelValues[0].add(new HeapNodeEntry(treeRoot.getKey(), treeRoot.getRank()));
        prepLevels(treeRoot.getChild(), 1, levelValues);
        printLevels(levelValues);
    }

    public static void printHeap(FibonacciHeap heap) {
        FibonacciHeap.HeapNode cur = heap.getFirst();
        do {
            printTree(cur);
            System.out.println("------------------------");
            cur = cur.getNext();
        } while (cur != heap.getFirst());
    }
}
