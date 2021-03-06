package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class ComponentFilterAppConfigTest {

    @Test
    public void filterScan() throws Exception {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        Assertions.assertThat(beanA).isNotNull(); // null이 아닌 값이 조회가 된다.

        // BeanB beanB = ac.getBean("beanB", BeanB.class);

        org.junit.jupiter.api.Assertions.assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class)
        );
    }

    @Configuration
    @ComponentScan(
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = MyIncludeComponent.class
        ),
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = MyExcludeComponent.class
        )
    )
    static class ComponentFilterAppConfig{

    }
}
