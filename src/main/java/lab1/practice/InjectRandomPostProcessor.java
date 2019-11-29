package lab1.practice;

import lab1.example.InjectInt;
import lab1.example.IntServiceLocator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * класс спрингового пост процессора, должен имплементировать интерфейс
 *
 * @see BeanPostProcessor
 *
 * Класс отвечает за логику инжекта случайного числа в поле проаннотированное, специально обученной аннотацией
 */
@Service
public class InjectRandomPostProcessor implements BeanPostProcessor{

    @Override
    public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
        Class clazz = bean.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            RandomCount annotation = field.getDeclaredAnnotation(RandomCount.class);
            if (annotation != null) {
                Random r = new Random();
                Integer newValue = r.nextInt(20);
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, newValue);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String s) throws BeansException {
        return bean;
    }
}
