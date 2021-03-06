package net.oldschoolminecraft.nlib.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Module
{
    String name() default "CHANGEME";
    boolean enabled() default true;
}
