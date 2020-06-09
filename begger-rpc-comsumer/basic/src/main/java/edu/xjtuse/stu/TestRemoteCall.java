package edu.xjtuse.stu;

import edu.xjtuse.stu.remote.BasicService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 失了秩
 * @date 2020/6/9 13:20
 * @description
 */
@Configuration
@ComponentScan("edu.xjtuse.stu")
public class TestRemoteCall {

    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(TestRemoteCall.class);
        BasicService bean = context.getBean(BasicService.class);
        bean.testSaveUser();
    }
}
