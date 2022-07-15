package com.tang.netty.codec;

import com.tang.netty.codec.protolbuf.MyDataInfoo;

import java.util.Random;

/**
 * messgae辅助工具
 * @author tangwenkai
 * @date 2021-06-10 20:32
 */
public final class MessageUtils {

    private MessageUtils(){}
    /**
     * 构造message
     * @return
     */
    public static MyDataInfoo.MyMessage getMessage(){
        int nextInt = new Random().nextInt(3);
        MyDataInfoo.MyMessage message = null;
        if (nextInt==0){//发送一个学生对象
            message = MyDataInfoo
                    .MyMessage.newBuilder()
                    .setDataType(MyDataInfoo.MyMessage.DateType.StudentType)
                    .setStudent(MyDataInfoo.Student.newBuilder().setId(1)
                            .setName("学生孙悟空").build()).build();
        }else {
            //发送一个worker对象
            message = MyDataInfoo
                    .MyMessage.newBuilder()
                    .setDataType(MyDataInfoo.MyMessage.DateType.WorkerType)
                    .setWorker(MyDataInfoo.Worker.newBuilder().setName("工人唐三藏")
                            .setAge(9999999).build()).build();
        }
        return message;
    }

    /**
     * 解析message
     * @param msg
     */
    public static void parseMessage(Object msg) {
        //读取从服务端发送的数据
        MyDataInfoo.MyMessage message = (MyDataInfoo.MyMessage) msg;
        //根据data_type显示不同的信息
        MyDataInfoo.MyMessage.DateType dataType = message.getDataType();
        if (dataType == MyDataInfoo.MyMessage.DateType.StudentType) {
            MyDataInfoo.Student student = message.getStudent();
            System.out.println("学生id: " + student.getId() + "\t" + "名字: " + student.getName());
        } else if (dataType == MyDataInfoo.MyMessage.DateType.WorkerType) {
            MyDataInfoo.Worker worker = message.getWorker();
            System.out.println("工人名字: " + worker.getName() + "\t" + "年龄: " + worker.getAge());
        } else {
            System.out.println("传输的类型不对...........");
        }
    }
}
