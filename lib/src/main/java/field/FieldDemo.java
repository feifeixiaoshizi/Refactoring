package field;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static field.FieldDemo.ReplaceTypeCodeWithStateOrStrategy.Employee.MANAGER;
import static field.FieldDemo.ReplaceTypeCodeWithSubClass.Employee.ENGINEER;

public class FieldDemo {
    /**
     * 1.自封装字段
     */
    public class SelfEncapsulateField {
        private int low, hight;

        //直接调用
        public int test() {
            return low + hight;
        }

        //封装后调用
        public int test1() {
            return getHight() + getLow();
        }

        public int getLow() {
            return low;
        }

        public void setLow(int low) {
            this.low = low;
        }

        public int getHight() {
            return hight;
        }

        public void setHight(int hight) {
            this.hight = hight;
        }
    }

    /**
     * 2.以对象取代数据值
     * 你有一个数据项，需要和其他数据一起使用才有意义。
     */
    public class RepalaceDataWithObjcet {
        public class Order {
            private String customer;

            public Order(String customer) {
                this.customer = customer;
            }

            public String getCustomer() {
                return customer;
            }

            public void setCustomer(String customer) {
                this.customer = customer;
            }
        }

        public class Cutomer {
            private final String name;

            public Cutomer(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }
        }

        public class Order1 {
            //customer是个值对象，每次更改都是新new一个对象。（***）
            private Cutomer customer;

            public Order1(String customer) {
                this.customer = new Cutomer(customer);
            }

            public String getCustomer() {
                return customer.getName();
            }

            public void setCustomer(String customer) {
                this.customer = new Cutomer(customer);
            }
        }

    }

    /**
     * 3.将值对象改为引用对象
     * 做到一改全改，比如聊天的头像，所有的一个人的聊天消息公用一个用户信息。
     */
    public static class ChangeValueToReference {
        public static class Cutomer {
            public static Cutomer create(String name) {
                return new Cutomer(name);
            }

            private final String name;

            public Cutomer(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }
        }

        public class Order {
            //customer是个值对象，每次更改都是新new一个对象。（***）
            private Cutomer customer;

            public Order(String customer) {
                this.customer = Cutomer.create(customer);
            }

            public String getCustomer() {
                return customer.getName();
            }

            public void setCustomer(String customer) {
                this.customer = Cutomer.create(customer);
            }
        }
    }

    /**
     * 4.将引用对象改为值对象
     * 一个引用对象，很小且不可变，而且不容易管理。（比如线程安全性）
     */

    /**
     * 5.以对象取代数组
     * 以对象取代数组，对于数组中的每个元素使用一个字段来表示。
     */
    public class RepalceArrayWithObject {
        String[] row = new String[]{"name", "12"};

        //替换数组
        public class Row {
            private String name;
            private String age;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }
        }
    }

    /**
     * 6.将单向关联改为双向关联
     */
    public class ChangeUnidirectionalAssocialtionToBidirectional {
        public class Order {
            private Customer customer;

            public Customer getCustomer() {
                return customer;
            }

            public void setCustomer(Customer customer) {
                this.customer = customer;
            }
        }

        public class Customer {
            private Order order;

            public Order getOrder() {
                return order;
            }

            public void setOrder(Order order) {
                this.order = order;
            }
        }
    }

    /**
     * 7.将双向关联改为单向关联
     * 去除不必要的关联
     */
    public class ChangeBidirectionalAssocialtionToUnidirectional {
        public class Order {

        }

        public class Customer {
            private Order order;

            public Order getOrder() {
                return order;
            }

            public void setOrder(Order order) {
                this.order = order;
            }
        }
    }


    /**
     * 8.以字面常量取代魔法数
     * 去除不必要的关联
     */
    public class ReplaceMgicNumber {
        double charge(double mass, double height) {
            return mass * 0.98 * height;
        }

        static final double GRAVITATIONAL_CONSTANT = 0.98;

        double charge1(double mass, double height) {
            return mass * GRAVITATIONAL_CONSTANT * height;
        }
    }

    /**
     * 9.封装字段
     * 将它声明为private，并提供相应的访问函数，好处在于对变量的修改和访问便于控制。
     * 尤其在解决线程安全性时。
     * <p>
     * 利于move method的执行
     */
    public class EncapsulateField {
        //public String name;
        private String name;

        public synchronized String getName() {
            return name;
        }

        public synchronized void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 10.封装集合
     * 让函数返回一个集合的只读副本，并在这个类中提供添加和移除集合元素的函数。
     */
    public class EncapsulateCollection {
        public class Person {
            private Set<Course> courses = new HashSet<>();

            public void addCourse(Course course) {
                courses.add(course);
            }

            public void removeCourse(Course course) {
                courses.remove(course);
            }

            public Set<Course> getCourses() {
                //返回只读对象
                return Collections.unmodifiableSet(courses);
            }
        }

        public class Course {
            private String name;

            public Course(String name) {
                this.name = name;
            }
        }
    }

    /*
     11.以类取代类型码
     类中有一个数值类型，但它并不影响类的行为，以一个新的类替换改数值类型码。
     编译器可以进行类型检查。
     */
    public class ReplaceTypeCodeWithClass {
        public class Person {
            public static final int O = 0;
            public static final int A = 1;
            public static final int B = 2;
            public static final int AB = 3;

            private int bloodGroup;

            public int getBloodGroup() {
                return bloodGroup;
            }

            public void setBloodGroup(int bloodGroup) {
                this.bloodGroup = bloodGroup;
            }
        }

        public class BloodGroup {
            public static final int O = 0;
            public static final int A = 1;
            public static final int B = 2;
            public static final int AB = 3;

            private int code;

            public BloodGroup(int code) {
                this.code = code;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }
        }

        public class Person1 {
            private BloodGroup bloodGroup;

            public int getBloodGroup() {
                return bloodGroup.getCode();
            }

            public void setBloodGroup(int bloodGroup) {
                this.bloodGroup = new BloodGroup(bloodGroup);
            }
        }


    }

    /**
     * 12.以子类取代类型码
     * 有一个不可以变的类型码，它会影响类的行为，以子类取代这个类型码。
     */
    public static class ReplaceTypeCodeWithSubClass {
        public class Employee {
            static final int ENGINEER = 0;
            static final int MANAGER = 1;

            private int type;

            public Employee(int type) {
                this.type = type;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }


        public static abstract class Employee1 {
            public abstract int getype();
        }

        /**
         * 把每个类型码都转为一个子类。
         * 好处：对修改关闭，对扩展开放，新增的类型扩子类就可以。
         */
        public static class Engineer extends Employee1 {
            @Override
            public int getype() {
                return 0;
            }
        }

        public static class Manager extends Employee1 {

            @Override
            public int getype() {
                return 1;
            }
        }

        public static class EmployeeFactory {
            // ugly code bug的来源
            public static Employee1 create(int type) {
                switch (type) {
                    case 0:
                        return new Engineer();
                    case 1:
                        return new Manager();
                }
                return null;
            }
        }
    }


    /**
     * 13.以状态或者策略取代类型码
     * 一个类型码，会影响类的行为，你无法通过继承来消磨它。
     */
    public static class ReplaceTypeCodeWithStateOrStrategy {
        public static class Employee {
            private int type;
            static final int ENGINEER = 0;
            static final int MANAGER = 1;
            private int monthlySalary;
            private int bonus;

            public Employee(int type) {
                this.type = type;
            }

            int playAmout() {
                switch (type) {
                    case ENGINEER:
                        return monthlySalary;
                    case MANAGER:
                        return monthlySalary + bonus;

                }
                return -1;
            }
        }


        public abstract static class EmployeeType {
            abstract int getTypeCode();
        }

        static class Engineer extends EmployeeType {

            @Override
            int getTypeCode() {
                return ENGINEER;
            }
        }

        static class Manager extends EmployeeType {

            @Override
            int getTypeCode() {
                return MANAGER;
            }
        }

        static class EmployeeTypeFactory {
            public static EmployeeType create(int type) {
                switch (type) {
                    case ENGINEER:
                        return new Engineer();
                    case MANAGER:
                        return new Manager();

                }
                return null;
            }
        }

        public static class Employee1 {
            private EmployeeType type;
            private int monthlySalary;
            private int bonus;

            public Employee1(int type) {
                this.type = EmployeeTypeFactory.create(type);
            }
            int playAmout() {
                switch (type.getTypeCode()) {
                    case ENGINEER:
                        return monthlySalary;
                    case MANAGER:
                        return monthlySalary + bonus;

                }
                return -1;
            }
        }


    }

}
