import java.util.*;
interface Database<K, V>{
    public void add(K key, V value) throws Exception;
    public V query(K key) throws Exception;
    public Map<K, V> get();
    public void remove(K key);
}
