package test;

import javassist.*;

import java.io.IOException;

public class HelloHacked {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, IOException {
        ClassPool cp = ClassPool.getDefault();
        //pool.insertClassPath("/usr/local/javalib");

        CtClass chello = cp.get("test.Hello");
        CtMethod cmethodSay = chello.getDeclaredMethod("say");
        cmethodSay.setBody("{System.out.println(\"shit \");}");
        cmethodSay.insertBefore("System.out.println(\"fuck\");");
        Class<Hello> helloClazz = chello.toClass();
        Hello h = (Hello)helloClazz.newInstance();
        h.say();

        //chello.writeFile(); // update the class file

        System.out.println(chello.toString());
        System.out.println(new String(chello.toBytecode(), "UTF-8"));
    }
}
