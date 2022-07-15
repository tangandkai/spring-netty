package com.tang.queue.DIY;

/**
 * 定义队列的接口，定义泛型，可以让使用者放任意类型到队列中去
 * @param <T>
 * @date 2021-06-21 17:43
 * @author tangwenkai
 */
public interface Queue <T>{

    /**
     * 放数据
     * @param item
     * @return
     */
    boolean put(T item);

    /**
     * 拿数据，返回一个泛型值
     * @return
     */
    T take();

    class Node<T>{
        /**
         * 数据本身
         */
        T item;

        /**
         * 下一个节点
         */
        Node<T> next;

        public Node(T item){
            this.item = item;
        }
    }
}
