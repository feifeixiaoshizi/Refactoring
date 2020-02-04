package function;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 方法的构造
 * * 1.方法权限
 * * 2.方法修饰符
 * * 3.方法名称 （见名知意）
 * * 4.方法参数（不要超过三个）
 * * 5.方法的返回值
 * * 6.方法体（函数的粒度）
 * * 7.方法的调用（inline ）
 * * 8.方法的所属 （函数对象替换函数）
 * * 9.方法的临时变量（***）
 * * 10.方法的注释（提取方法）
 * * 11.函数分解和再组合
 * * 12.函数组成的表达式
 */
public class FunctionInvoke {
    /**
     * 1.方法改名
     */
    public static class RenameMethod {

    }

    /**
     * 2.添加参数
     * 某个函数需要从调用端获取更多的信息，为此函数添加一个对象参数，让该对象带进函数所需的信息。
     * 使用场景：作为需求的添加上报的时候，啊啊啊，需要修改原本完美的函数添加上报所需的参数。
     */
    public static class AddParameter {
        public void test(String name) {
            System.out.println("name:" + name);
        }

        public void test(String name, int age) {
            System.out.println("name:" + name + "age:" + age);
        }

        public void test(String name, int age, int sex) {
            System.out.println("name:" + name + "age:" + age + "sex:" + sex);
        }

        public static class Person {
            private String name;
            private int age;
            private int sex;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }
        }

        public void test(Person person) {
            System.out.println("name:" + person.name + "age:" + person.age + "sex:" + person.sex);
        }
    }


    /**
     * 3.移除参数
     * 函数不需要的参数对象要移除。
     *
     */

    /***
     * 4.将查询函数和修改函数分离
     *  某个函数也有返回值，也有修改对象状态。
     *  分离出来便于控制。（线程安全性）
     */
    public static class SepareateQueryFromModifier {
        public static class Customer {
            private String name;

            public String getAndSetName(String name) {
                this.name = name;
                return name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

    }

    /**
     * 5.令函数携带参数
     * 若干个函数做了类似的工作，但在函数本体中包含了不同的值。
     * 建立单一函数，一参数表达哪些不同的值。
     */
    public static class ParameterizeMethod {
        public static String getMediumUri(String uri) {
            return getSizeUri(uri, "_mm");
        }

        public static String getLowUri(String uri) {
            return getSizeUri(uri, "_ss");
        }

        private static String getSizeUri(String uri, String sizeType) {
            if (!uri.contains(".")) {
                return "";
            }
            int index = uri.lastIndexOf(".");
            String prex = uri.substring(0, index);
            String hou = uri.substring(index);
            uri = prex + sizeType + hou;
            return uri;
        }

    }


    /**
     * 6.以明确的函数取代参数
     */
    public static class ReplaceParameterWithExplicitMethods {
        private int height;
        private int width;

        void setValue(String name, int value) {
            if (name.equals("height")) {
                height = value;
                return;
            }

            if (name.equals("width")) {
                width = value;
                return;
            }
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }

    /**
     * 7.保持对象的完整性
     * 你从某个对象中取出若干个值，将他们做为某个函数调用时的参数。
     * (1)使参数列更加稳固；(2)提高代码的可读性。
     */
    public static class PreserveWholeObject {
        class Room {
            public boolean WithinPlan(HeatingPlan plan) {
                int low = DaysTempRange().GetLow();
                int high = DaysTempRange().GetHigh();
                return plan.WithinRange(low, high);
            }

            public TempRange DaysTempRange() {
                return new TempRange();
            }
        }

        class HeatingPlan {
            private TempRange _range;

            public boolean WithinRange(int low, int high) {
                return low >= _range.GetLow() && high <= _range.GetHigh();
            }
        }

        class TempRange {
            public int GetLow() {
                return 6;
            }

            public int GetHigh() {
                return 28;
            }
        }


        //after
        class Room1 {
            public boolean WithinPlan(HeatingPlan1 plan) {
                return plan.WithinRange(DaysTempRange());
            }

            public TempRange DaysTempRange() {
                return new TempRange();
            }
        }

        class HeatingPlan1 {
            private TempRange _range;

            public boolean WithinRange(TempRange roomRange) {
                return roomRange.GetLow() >= _range.GetLow() && roomRange.GetHigh() <= _range.GetHigh();
            }
        }

        class TempRange1 {
            public int GetLow() {
                return 6;
            }

            public int GetHigh() {
                return 28;
            }
        }
    }

    /**
     * 8.以函数取代参数
     * 对象调用某个函数，并将所得结果做为参数，传递给另一个函数。而接受参数的函数本身也能够调用前一个函数。
     * <p>
     * 让参数接受者去除该项参数，并直接调用前一个函数。
     */
    public static class ReplaceParameterWithMethods {
        class Price {
            public int Quantity;

            public int ItemPrice;

            public double GetPrice() {
                int basePrice = Quantity * ItemPrice;
                int discountLevel = Quantity > 100 ? 2 : 1;
                double finalPrice = GetDiscountedPrice(basePrice, discountLevel);
                return finalPrice;
            }

            private double GetDiscountedPrice(int basePrice, int discountLevel) {
                if (discountLevel == 2) {
                    return basePrice * 0.1;
                }
                return basePrice * 0.05;
            }


            private int GetDiscountLevel() {
                return Quantity > 100 ? 2 : 1;
            }

            //after
            private double GetDiscountedPrice(int basePrice) {
                if (GetDiscountLevel() == 2) {
                    return basePrice * 0.1;
                }
                return basePrice * 0.05;
            }
        }

        class Price1 {
            public int Quantity;

            public int ItemPrice;

            public double GetPrice() {
                if (GetDiscountLevel() == 2) {
                    return GetBasePrice() * 0.1;
                }
                return GetBasePrice() * 0.05;
            }

            private int GetBasePrice() {
                return Quantity * ItemPrice;
            }

            private int GetDiscountLevel() {
                return Quantity > 100 ? 2 : 1;
            }

        }

    }

    /**
     * 9.引入参数对象
     * 某些参数总是很自然地同时出现。以一个对象取代这些参数
     */
    public static class IntroduceParamterObject {

        class Entry {
            public Date date;

            public double Value;

            public Entry(double value, Date chargeDate) {
                Value = value;
                date = chargeDate;
            }
        }

        class Account {
            private List<Entry> _entries = new ArrayList<>();

            public double GetFlowBetween(Date start, Date end) {
                int result = 0;
                for (Entry entry : _entries) {
                    if (entry.date.after(start) && entry.date.before(end)) {
                        result += entry.Value;
                    }
                }
                return result;
            }
        }


        class DateRange {
            public final Date Start;

            public final Date End;

            public DateRange(Date start, Date end) {
                Start = start;
                End = end;
            }
        }

        class Account1 {
            private List<Entry> _entries = new ArrayList<>();

            public double GetFlowBetween(DateRange dateRange) {
                int result = 0;
                for (Entry entry : _entries) {
                    if (entry.date.after(dateRange.Start) && entry.date.before(dateRange.End)) {
                        result += entry.Value;
                    }
                }
                return result;
            }
        }
    }

    /**
     * 10.移除设置函数
     * 去掉该字段或者属性的所有设值函数。
     */
    public static class RemoveSettingMethod {

    }

    /**
     * 11.隐藏函数
     * 当你在另一个类中移除对某个函数的调用时，就应该检查有没有可能降低这个函数的可见度（使它私有化）
     */
    public static class HideMethod {

    }

    /**
     * 12.以工厂函数替代构造函数
     * 你希望在创建对象时不仅仅是做简单的建构动作。将构造函数替换为工厂函数。
     * 解耦避免每次创建对象都是new，导致对象创建过于分散，屏蔽掉创建的真实对象（***）
     */
    public static class ReplaceConstructorWithFactoryMethod {

    }

    /**
     * 13.以异常取代错误码
     * 异常能清楚地将“普通程序”和“错误处理”分开了，这使得程序更容易理解。
     * 别人可能不关心你的错误码，但是如果你抛出异常它就必须的处理。
     */
    public static class ReplaceErrorCodeWithException {
        class Account {
            private int _balance;

            public int Withdraw(int amount) {
                if (amount > _balance) {
                    return -1;
                }
                _balance -= amount;
                return 0;
            }

            public boolean CanWithdraw(int amount) {
                return amount <= _balance;
            }

            public void HandOverdran() {

            }

            public void DoTheUsualThing() {

            }

            public int Withdraw1(int amount) {
                if (amount > _balance) {
                    //异常替换错误码
                    throw new IllegalArgumentException("amount is invalidate!");
                }
                _balance -= amount;
                return 0;
            }
        }
    }

    /**
     * 14.以测试取代异常
     */
    public static class ReplaceExceptionWithTest {
        List<String> values = new ArrayList<>();

        public String getValueInPosition(int position) {
            String va = null;
            try {
                va = values.get(position);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            return va;
        }

        public String getValueInPosition1(int position) {
            String va = null;
            //增加判断
            if (position < 0 || position >= values.size()) {
                return null;
            }
            va = values.get(position);
            return va;
        }
    }


}
