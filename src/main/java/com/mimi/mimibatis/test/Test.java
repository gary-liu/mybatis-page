package com.mimi.mimibatis.test;


import com.mimi.mimibatis.product.mapper.ProductMapper;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * 需要咨询java高级VIP课程的同学可以加安其拉老师的QQ：3164703201
 * 需要往期视频的同学可以加木兰老师的QQ：2746251334
 * author：鲁班学院-商鞅老师
 */
@Component
public class Test {


    public static  CountDownLatch countDownLatch = new CountDownLatch(200);

//    public static void main(String[] args) {
//        AnnotationConfigApplicationContext appConfig
//                =new AnnotationConfigApplicationContext(MqConfig.class);
//        ProductMapper bean = appConfig.getBean(ProductMapper.class);
//        for (int i = 0 ;i<200;i++ ){
//            new Thread(new test(bean,i)).start();
//            countDownLatch.countDown();
//        }
////        appConfig.close();
//    }


    public  static  void main(String[] args) {
        char c = '你';

        System.out.println((int)c);
    }

    public  class  test implements Runnable{

        ProductMapper productMapper;
        int i = 0;

        public test(ProductMapper productMapper,int i){
            this.productMapper = productMapper;
            this.i = i;
            System.out.println(i);
        }


         @Override
         public void run() {
             try {
                 countDownLatch.await();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
//             List<Map<String, Object>> productByArrayPage = productMapper.getProductByArrayPage();
//             System.out.println(productByArrayPage.size());
         }


     }
}
