package systems.altura.util;

/**
 * @author caelvaso
 */
public class Log {

    static boolean VISIBLE = true;

    public static void i(String msg) {
        if (VISIBLE)
            System.out.println(getInfo() + ": " + msg);
    }

    public static void e(Exception e) {
        if (VISIBLE) {
            e(e.getMessage(), e);
        }
    }

    public static void e(String msg) {
        if (VISIBLE) {
            System.err.println(getInfo() + ": " + msg);
        }
    }

    public static void e(String msg, Exception e) {
        if (VISIBLE) {
            System.err.println(getInfo() + ": " + msg);
            e.printStackTrace();
        }
    }

    public static String getInfo() {
        StackTraceElement[] ss = Thread.currentThread().getStackTrace();
        StackTraceElement s = ss[ss.length - 1];
        String fullClassName = s.getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = s.getMethodName();
        int lineNumber = s.getLineNumber();
        return "LOG: " + fullClassName + "." + methodName + "(" + className + ".java" + ":" + lineNumber + ")";
    }
}