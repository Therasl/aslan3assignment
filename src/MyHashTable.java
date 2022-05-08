import java.util.ArrayList;
import java.util.Objects;
public class MyHashTable <K, V>{
    private class HashNode<K, V>{
        private K key;
        private V val;
        private HashNode<K, V> next;
        final int hashCode;
        public HashNode(){
            hashCode = 0;
            next = null;
        }
        public HashNode(K key, V val, int hashCode){
            this.key = key;
            this.val = val;
            this.hashCode = hashCode;
        }
        @Override
        public String toString() {
            return "{" + key + " " + val + "}";
        }
    }

    private ArrayList<HashNode<K,V>> chainArray = new ArrayList<>();
    private int M = 11;
    private int size = 0;

    public MyHashTable() {
        for (int i = 0; i < M; i++) chainArray.add(null);
    }

    public MyHashTable(int M) {
        this.M = M;
        for (int i = 0; i < M; i++) chainArray.add(null);
    }

    private int hash(K key){
        return Objects.hashCode(key);
    }

    private int getIndex(K key){
        int hash = hash(key);
        int index = hash % M;
        if (index < 0) {
            index *= -1;
        }
        return index;
    }

    public void put(K key, V val){
        int index = getIndex(key);
        int hash = hash(key);

        HashNode<K, V> head = chainArray.get(index);

        while(null != head){
            if (head.key.equals(key) && (head.hashCode == hash)){
                head.val = val;
                return;
            }
            head = head.next;
        }
        size++;
        head = chainArray.get(index);
        HashNode<K, V> newNode;
        newNode = new HashNode<>(key, val, hash);
        newNode.next = head;
        chainArray.set(index, newNode);

        if (0.6 < (double) (size / M)){
            System.out.println("LOAD FACTOR " + key + " " + val);
            M *= 2;
            ArrayList<HashNode<K, V>> oldList;
            oldList = chainArray;
            chainArray = new ArrayList<>();
            size = 0;
            for (int i = 0; i < M; i ++){
                chainArray.add(null);
            }
            for (HashNode<K, V> start : oldList) {
                while (start != null) {
                    put(start.key, start.val);
                    start = start.next;
                }
            }
        }

    }

    public void print(){
        for (int i = 0; i < M; i ++){
            HashNode<K, V> head = chainArray.get(i);
            if (head == null) {
                continue;
            }
            while(head != null){
                System.out.print("{" + head.key + "," + head.val + "}  ");
                head = head.next;
            }
            System.out.println();
        }
    }
    public V get(K key){
        int index = getIndex(key);
        int hash = hash(key);
        HashNode<K, V> head = chainArray.get(index);
        while(head != null){
            if (head.key.equals(key) && !(head.hashCode != hash)){
                return head.val;
            }
            head = head.next;
        }
        return null;
    }

