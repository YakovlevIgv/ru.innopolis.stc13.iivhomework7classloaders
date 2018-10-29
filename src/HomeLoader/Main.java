package HomeLoader;

import java.lang.reflect.Method;

/**
 * Необходимо написать программу, выполняющую следующее:
 * 1. Программа с консоли построчно считывает код метода doWord. Код не должен требовать импорта дополнительных классов.
 * 2. После ввода пустой строки считывание прекращается и считанные строки добавляются в тело метода public void doWord()
 *    в файле SomeClass.java.
 * 3. Файл SomeClass.java компилируется программой (в рантайме) в файл SomeClass.class.
 * 4. Полученный файл подгружается в программу с помощью кастомного загрузчика
 * 5. Метод, введенный с консоли, исполняется в рантайме (вызывается у экземпляра объекта подгруженного класса)
 */
public class Main {
    public static void main(String[] args) throws Exception {
        CurClassLoader loader = new CurClassLoader();

        loader.readDoWord();

        loader.setTextInFile("/ClassLoaders/src/ru/innopolis/classloader/yakovlev/i/vl/SomeClass.java", loader.addedText);


        Process compile = Runtime.getRuntime().exec(" javac  -d "  +
                "                                          /ClassLoaders/out/production/ClassLoaders"
                +
                "                                          /ClassLoaders/src/ru/innopolis/classloader/yakovlev/i/vl/SomeClass.java");
        compile.waitFor();
        Process compileClass = Runtime.getRuntime().exec("java " + "SomeClass");
        compileClass.waitFor();

        Class<?> c = loader.findClass("ru.innopolis.classloader.yakovlev.i.vl.SomeClass");
        Object ob = c.newInstance();
        Method md = c.getMethod("doWord");
        md.invoke(ob);
    }
}

