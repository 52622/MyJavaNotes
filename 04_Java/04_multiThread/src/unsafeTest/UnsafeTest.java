package unsafeTest;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeTest {
  private static Unsafe unsafe;

  static {
    try {
      //通过反射获取rt.jar下的Unsafe类
      Field field = Unsafe.class.getDeclaredField("theUnsafe");
      field.setAccessible(true);
      unsafe = (Unsafe) field.get(null);
    } catch (Exception e) {
      System.out.println("Get Unsafe instance occur error" + e);
    }
  }

  /**
   public final int getAndAddInt(Object var1, long var2, int var4) {
   int var5;
   do {
   var5 = this.getIntVolatile(var1, var2);
   } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));

   return var5;
   }

   var1指向当前对象地址
   var2执行要修改的值相对于当前对象地址的偏移量
   var5是旧的预期值
   compareAndSwapInt 如果内存地址var1+var2的值等于var5，那么就把var5+var4的值赋给var1+var2的内存地址
   */

  public static void main(String[] args) throws Exception {
    Class clazz = Target.class;
    Field[] fields = clazz.getDeclaredFields();
    System.out.println("fieldName:fieldOffset");
    for (Field f : fields) {
      // 获取属性偏移量，可以通过这个偏移量给属性设置
      System.out.println(f.getName() + ":" + unsafe.objectFieldOffset(f));
    }
    Target target = new Target();
    Field intFiled = clazz.getDeclaredField("intParam");
    int a = (Integer) intFiled.get(target);
    System.out.println("原始值是:" + a);
    //intParam的字段偏移是12 原始值是3 我们要改为10
    System.out.println(unsafe.compareAndSwapInt(target, 12, 3, 10));
    int b = (Integer) intFiled.get(target);
    System.out.println("改变之后的值是:" + b);

    //这个时候已经改为10了,所以会返回false
    System.out.println(unsafe.compareAndSwapInt(target, 12, 3, 10));

    System.out.println(unsafe.compareAndSwapObject(target, 24, null, "5"));
  }


}
