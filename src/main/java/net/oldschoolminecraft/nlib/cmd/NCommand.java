package net.oldschoolminecraft.nlib.cmd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NCommand
{
    String name() default "cmd";
    String syntax() default "<arg1>";
    String description() default "Hello, World!";
    String permission() default "change.me";
    String[] permissions() default {};
    boolean operatorBypass() default true;
}
