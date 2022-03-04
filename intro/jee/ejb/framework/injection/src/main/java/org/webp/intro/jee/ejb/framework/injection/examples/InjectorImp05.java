package org.webp.intro.jee.ejb.framework.injection.examples;

import org.webp.intro.jee.ejb.framework.injection.AnnotatedForInjection;
import org.webp.intro.jee.ejb.framework.injection.Injector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class InjectorImp05 implements Injector {


    public <T> T createInstance(Class<T> klass) throws IllegalArgumentException {

        if(klass == null){
            throw new IllegalArgumentException("Null input");
        }

        T t;

        try {
            t = klass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to instantiate object for "+klass.getName()+" : "+e.getMessage());
        }


        for(Field field : klass.getDeclaredFields()){

            Annotation annotation = field.getAnnotation(AnnotatedForInjection.class);
            if(annotation==null){
                //alan injection için işaretlenmediğinden burayı geç
                continue;
            }

            try {
                Class<?> typeToInject = field.getType();
                Object objectToInject = createInstance(typeToInject); //sonsuz rekürsifliğe dikkat et...
                field.setAccessible(true);  //ihtiyaç bulunmaktadır yoksa private metotlar hataya düşer
                field.set(t, objectToInject);
            } catch (Exception e){
                throw new IllegalArgumentException("Not possible to inject "+field.getName()+" due to: "+e.getMessage());
            }
        }

        return t;
    }
}
