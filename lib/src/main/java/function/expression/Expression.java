package function.expression;

import java.util.HashMap;

import static function.expression.Expression.ReplaceConditionalWithPolymorphism.Employee.ENGINEER;
import static function.expression.Expression.ReplaceConditionalWithPolymorphism.Employee.MANAGER;

public class Expression {
    /**
     * 1.decompose conditional 分解表达式
     * 从if else中分别提炼函数
     */
    public static class DecomposeConditional {
        private int age;

        private int getMoney() {

            if (age <= 0 || age > 200) {
                return -1;
            } else {
                return 1;
            }
        }

        //从if中提炼函数
        private boolean isValidateAge() {
            return age <= 0 || age > 200;
        }

    }

    /**
     * 2.合并表达式
     */
    public static class ConsolidateConditionalExpressoin {
        private int age;
        private String name;

        double disabilityAmout() {
            if (age <= 0) {
                return 0;
            }
            if (name.length() == 0) {
                return 0;
            }
            return 100;
        }

        //合并条件表达式
        private boolean isInValidate() {
            if (age <= 0) {
                return true;
            }
            if (name.length() == 0) {
                return true;
            }
            return false;
        }

        double disabilityAmout1() {
            if (isInValidate()) {
                return 0;
            }
            return 100;
        }


    }

    /**
     * 3. 合并重复的条件片段
     * 将这段重复代码移除到表达式以外。
     */
    public static class ConsolidateDuplicate {
        private boolean isTrue;
        private int total;

        //before
        public void test() {
            if (isTrue) {
                total++;
                send();
            } else {
                total--;
                send();
            }
        }

        //after
        public void test1() {
            if (isTrue) {
                total++;
            } else {
                total--;
            }
            send();
        }

        public void send() {
            System.out.println("total:" + total);
        }
    }

    /**
     * 4.移除控制标记
     * 以break或者return语句取代控制标记
     */
    public static class RemoveControlFlag {
        public boolean checkSecurity(String[] peoples) {
            boolean found = false;
            for (int i = 0; i < peoples.length; i++) {
                if (!found) {
                    if (peoples[i].equals("lijiansheng")) {
                        found = true;
                    }
                }

            }
            return found;
        }

        public boolean checkSecurityReturn(String[] peoples) {
            for (int i = 0; i < peoples.length; i++) {
                //以return直接结束循环并返回结果。
                if (peoples[i].equals("lijiansheng")) {
                    return true;
                }

            }
            return false;
        }

    }

    /**
     * 5.以卫语句取代嵌套条件表达式
     */
    public static class ReplaceNestedConditinalWithGuardClauses {
        private boolean isDead;
        private boolean isSeparated;

        double getPayAmount() {
            double result;
            if (isDead) {
                result = deadAmount();
            } else {
                if (isSeparated) {
                    result = separete();
                } else {
                    result = normal();
                }
            }
            return result;
        }


        //after

        /**
         * 减少嵌套层次
         *
         * @return
         */
        double getPayAmount1() {
            if (isDead) {
                return deadAmount();
            }
            if (isSeparated) {
                return separete();
            }
            return normal();
        }

        private double normal() {
            return 0;
        }

        private double separete() {
            return 1;
        }

        private double deadAmount() {
            return -1;
        }
    }

    /**
     * 6.以多态取代条件表达式（*****）
     * 将这个条件表达式的每个分支放进一个子类内的复写函数中，然后将原始函数声明为抽象函数。
     * ReplaceTypeCodeWithSubClass在中已经把type作为成员变量封装到了子类，在这里再把相关的操作
     * 封装到子类里面。
     * type+type对应的行为 == 子类对象
     */
    public static class ReplaceConditionalWithPolymorphism {
       /* public static class Employee {
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
*/

        public abstract static class EmployeeType {
            abstract int getTypeCode();

            public int playAmout(Employee employee) {
                switch (getTypeCode()) {
                    case ENGINEER:
                        return employee.monthlySalary;
                    case MANAGER:
                        return employee.monthlySalary + employee.bonus;

                }
                return -1;
            }
        }

        static class Engineer extends EmployeeType {

            @Override
            int getTypeCode() {
                return ENGINEER;
            }

            @Override
            public int playAmout(Employee employee) {
                return employee.monthlySalary;
            }
        }

        static class Manager extends EmployeeType {

            @Override
            int getTypeCode() {
                return MANAGER;
            }

            @Override
            public int playAmout(Employee employee) {
                return employee.monthlySalary + employee.bonus;
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


        static class EmployeeTypeFactoryCollections {
            static HashMap<Integer, EmployeeType> sEmployeeTypes = new HashMap<>();

            static {
                sEmployeeTypes.put(ENGINEER, new Engineer());
                sEmployeeTypes.put(MANAGER, new Manager());
                sEmployeeTypes.put(MANAGER, new Manager());
            }

            public static EmployeeType create(int type) {
                return sEmployeeTypes.get(type);
            }
        }

        public static class Employee {
            static final int ENGINEER = 0;
            static final int MANAGER = 1;
            private EmployeeType type;
            private int monthlySalary;
            private int bonus;

            public Employee(int type) {
                this.type = EmployeeTypeFactory.create(type);
            }

            /**
             * 成功的把switch通过多态分散到子类中。
             * 好处：
             * 1.符合开关原则，对修改关闭，对扩展开放。
             *
             * 疑问解答：
             *
             * 问：这里的switch是没了，但是EmployeeTypeFactory有了switch？
             * 答：不是说一点也不能用swtich，而是要简单明了的使用switch，降低switch的复杂性。
             *
             * 问：如何把EmployeeTypeFactory中的switch也去掉？
             * 答：通过容器取代。
             *
             * @return
             */
            int playAmout() {
                return type.playAmout(this);
            }
        }

    }

    /**
     * 7.introduce null object 引入空对象
     */
    public static class NullObject{
        public static class Customer{
            private String name;

            public Customer(String name) {
                this.name = name;
            }
        }

        /**
         * 构建一个空对象替代null的情况。
         */
        public static class  NullCustomer extends  Customer{
            public NullCustomer(String name) {
                super(name);
            }
        }
    }


}
