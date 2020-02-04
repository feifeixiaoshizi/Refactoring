package function;

/**
 * 方法的构造
 * 1.方法权限
 * 2.方法修饰符
 * 3.方法名称 （见名知意）
 * 4.方法参数（不要超过三个）
 * 5.方法的返回值
 * 6.方法体（函数的粒度）
 * 7.方法的调用（inline ）
 * 8.方法的所属 （函数对象替换函数）
 * 9.方法的临时变量（***）
 * 10.方法的注释（提取方法）
 * 11.函数分解和再组合
 * 12.函数组成的表达式
 */
public class FunctionDemo {
    /**
     * main方法
     * @param args
     */
    public static void main(String[] args) {
        //打印
        int a = 10;
        printValue(a);
    }

    private static void printValue(int a) {
        System.out.println("testException"+a);
    }
}
