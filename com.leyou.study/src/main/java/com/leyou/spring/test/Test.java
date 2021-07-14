package com.leyou.spring.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Auther: litaixu
 * @Date: 2021/7/14 - 07 - 14 - 21:05
 * @Description: com.leyou.spring.test
 * @version: 1.0
 */
public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext
                configApplicationContext = new AnnotationConfigApplicationContext(App.class);
        final App bean = configApplicationContext.getBean(App.class);
        System.out.println(bean);
    }
}
