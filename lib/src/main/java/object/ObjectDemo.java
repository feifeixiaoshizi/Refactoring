package object;

/**
 * 7.方法的调用
 * 8.方法的所属
 * 8.1 搬移函数
 * 在该函数最常引用的类中建立一个有着类似行为的新函数。将旧函数变成一个单纯的委托函数，或是将旧函数完全移除。
 * 如果一个类有太多行为，或如果一个类与另一个类有太多合作而形成高度耦合，又或者使用另一个对象的次数比使用自己所驻对象的次数还多。
 */
public class ObjectDemo {
    //1搬移函数
    public class MoveMethodDemo {
        class Account {
            private AccountType _accountType;
            private int _daysOverdrawn;

            /// <summary>
            /// 透支金额计费规则
            /// </summary>
            /// <returns></returns>

            /**
             * 1.观察被OverdraftCharge使用的每一项特性(字段，函数)，考虑是否一起搬移
             * 1.1.检查源类的子类和超类。
             * 1.2 调用它的函数和它调用的函数。
             * 2.搬移的新函数与源类之间的关系
             * 2.1 将源类的特性搬移到目标类
             * 2.2 建立或使用一个从目标类到源类的引用关系
             * 2.3 将源对象当作参数传递给目标函数（需要的特性特别多时）
             * 2.4 如果所需的特性是个变量，将它作为参数传递给函数。
             * 2.5 函数异常的处理权
             * 3.搬移函数后，源函数委托目标函数。
             *
             * @r.eturn
             */
            double OverdraftCharge() {
                if (_accountType.IsPremium()) {
                    double result = 10;
                    if (_daysOverdrawn > 7) {
                        result += (_daysOverdrawn - 7) * 0.85;
                    }
                    return result;
                }
                return _daysOverdrawn * 1.75;
            }

            double BankCharge() {
                double result = 4.5;
                if (_daysOverdrawn > 0) {
                    result += OverdraftCharge();
                }
                return result;
            }
        }

        class AccountType {
            public boolean IsPremium() {
                return true;
            }
        }


        class AccountType1 {
            //需要使用到源类的daysOverdrawn特性，作为参数传递进来。(***)
            public double OverdraftCharge(int daysOverdrawn) {
                if (IsPremium()) {
                    double result = 10;
                    if (daysOverdrawn > 7) {
                        result += (daysOverdrawn - 7) * 0.85;
                    }
                    return result;
                }
                return daysOverdrawn * 1.75;
            }


            public boolean IsPremium() {
                return true;
            }
        }
    }

    //2搬移字段

    /**
     * 在类之间移动状态和行为，是重构中必不可少的措施。随着系统的发展，我们会发现自己需要新的类，并需要将现有的工作责任拖到新的类中。
     */
    public class MoveFieldDemo {
        class Account {
            private AccountType _accountType;

            private double _interestRate;

            double GetInterestForAmountByDays(double amount, int days) {
                return _interestRate * amount * days / 365;
            }
        }

        /**
         * 1.在目标类中建立与源字段相同的字段，同时建立setXXX和getXXX的方法。
         * 2.在源类中使用目标类中提供的字段。
         */
        class AccountType {

            private double _interestRate;

            public double get_interestRate() {
                return _interestRate;
            }

            public void set_interestRate(double _interestRate) {
                this._interestRate = _interestRate;
            }
        }

        class Account1 {
            private AccountType _accountType;

            double GetInterestForAmountByDays(double amount, int days) {
                return _accountType.get_interestRate() * amount * days / 365;
            }
        }


        /********Self_Encapsulatoin*/
        class Account2 {
            private AccountType _accountType;

            private double _interestRate;

            double GetInterestForAmountByDays(double amount, int days) {
                return _interestRate * amount * days / 365;
            }

            double GetInterestForAmountByDays1(double amount, int days) {
                return _interestRate * amount * days / 365;
            }

            //自我封装，然后用方法替换字段。
            public double get_interestRate() {
                return _interestRate;
            }

            public void set_interestRate(double _interestRate) {
                this._interestRate = _interestRate;
            }
        }

        class Account3 {
            private AccountType _accountType;


            double GetInterestForAmountByDays(double amount, int days) {
                return get_interestRate() * amount * days / 365;
            }

            double GetInterestForAmountByDays1(double amount, int days) {
                return get_interestRate() * amount * days / 365;
            }

            //自我封装，然后用方法替换字段。
            public double get_interestRate() {
                return _accountType.get_interestRate();
            }

            public void set_interestRate(double _interestRate) {
                this._accountType.set_interestRate(_interestRate);
            }
        }


    }

    //3提炼类

    /**
     * 如果一个类中有大量的函数和数据，这个类往往太大而且不易理解。这时候就需要考虑哪些部分可以分离出去，并将它们分离到一个单独的类中
     */
    public class ExtractClassDemo {
        class Person {
            private String name;
            private String officeAreaCode;
            private String officeNumber;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOfficeAreaCode() {
                return officeAreaCode;
            }

            public void setOfficeAreaCode(String officeAreaCode) {
                this.officeAreaCode = officeAreaCode;
            }

            public String getOfficeNumber() {
                return officeNumber;
            }

            public void setOfficeNumber(String officeNumber) {
                this.officeNumber = officeNumber;
            }
        }


        class TelephoneNumber {
            private String officeAreaCode;
            private String officeNumber;

            public String getOfficeAreaCode() {
                return officeAreaCode;
            }

            public void setOfficeAreaCode(String officeAreaCode) {
                this.officeAreaCode = officeAreaCode;
            }

            public String getOfficeNumber() {
                return officeNumber;
            }

            public void setOfficeNumber(String officeNumber) {
                this.officeNumber = officeNumber;
            }
        }

        class Person1 {
            private String name;
            //建立从旧类到访问新类的连接关系(*****),不要建立从新类到旧类的连接关系。
            private TelephoneNumber telephoneNumber;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOfficeAreaCode() {
                return telephoneNumber.getOfficeAreaCode();
            }

            public String getOfficeNumber() {
                return telephoneNumber.getOfficeNumber();
            }


        }


    }

    //4将类内联

    /**
     * 将这个类的所有特性搬移到另一个类中，然后移除原类。
     */
    public class InlineClassDemo {

    }

    //5 隐藏委托关系

    /**
     * 客户对委托人依赖比较少。
     * 最少知道原则
     * 如果某个客户先通过服务对象的字段或者属性得到另一个对象，然后调用后者的函数，那么客户就必须知晓这一层委托关系。万一委托关系发生变化，客户也得相应变化。
     * 你可以在服务对象上放置一个简单的委托关系，将委托关系隐藏起来，从而去除这种依赖。这么一来，即便将来发生委托关系上的变化，变化也将被限制在服务对象中，不会波及客户
     */
    public class HideDelegateDemo {
        class Person {
            String name;
            private Departmet departmet;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Departmet getDepartmet() {
                return departmet;
            }

            public void setDepartmet(Departmet departmet) {
                this.departmet = departmet;
            }

            //直接暴漏一个方法出来。
            public Person getManager() {
                return departmet.getManager();
            }
        }

        class Departmet {
            private String chargeCode;
            private Person manager;

            public String getChargeCode() {
                return chargeCode;
            }

            public void setChargeCode(String chargeCode) {
                this.chargeCode = chargeCode;
            }

            public Person getManager() {
                return manager;
            }

            public void setManager(Person manager) {
                this.manager = manager;
            }
        }

    }

    //6 移除中间人

    /**
     * 即：每当客户要使用受托类的新特性时，就必须在服务端添加一个简单委托函数。随着受托类的特性越来越多，这一过程将非常痛苦。服务类完全成了“中间人”，这时候就需要让客户直接调用受托类。
     */
    public class RemoveMiddleManDemo {
        class Person {
            String name;
            private Departmet departmet;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Departmet getDepartmet() {
                return departmet;
            }

            public void setDepartmet(Departmet departmet) {
                this.departmet = departmet;
            }

            /**
             * 以下几个方法都需要委托Departmet，这时候明显每次都中转是比较麻烦的，就需要隐藏中间人
             **/
            public Person getManager() {
                return departmet.getManager();
            }

            public String getDepartMentName() {
                return departmet.getDepartMentName();
            }

            public String getDepartMentCoe() {
                return departmet.getDepartMentCoe();
            }
        }

        class Departmet {
            private String chargeCode;
            private Person manager;
            private String departMentName;
            private String departMentCoe;

            public String getDepartMentName() {
                return departMentName;
            }

            public void setDepartMentName(String departMentName) {
                this.departMentName = departMentName;
            }

            public String getDepartMentCoe() {
                return departMentCoe;
            }

            public void setDepartMentCoe(String departMentCoe) {
                this.departMentCoe = departMentCoe;
            }

            public String getChargeCode() {
                return chargeCode;
            }

            public void setChargeCode(String chargeCode) {
                this.chargeCode = chargeCode;
            }

            public Person getManager() {
                return manager;
            }

            public void setManager(Person manager) {
                this.manager = manager;
            }
        }
    }

    //7引入外加函数
    /**
     * 在客户类中建立一个函数，并以第一参数形式传入一个服务类的实例。
     * 就是扩展函数
     */

    //8 引入本地扩展

    /**
     * 建立一个新类，使它包含这些额外函数，让这个扩展类成为子类或者包装类。
     * 类的作者无法预知未来，他们常常没能为你预先准备一些有用的函数。如果只需要一两个函数，可以使用Introduce Foreign Method。
     * 但是如果需要的额外函数超过两个，外加函数就很难控制它们了。所以将这些函数组织在一起，让其成为源类的子类。这个子类称为本地扩展。
     */

    public class IntroduceLocalExtensionDemo {
        public class Source {
            private int source;

            public Source(int source) {
                this.source = source;
            }

            public int sum(int b) {
                return source + b;
            }

            public int getSource() {
                return source;
            }

            public void setSource(int source) {
                this.source = source;
            }
        }

        /**
         * 通过继承扩展函数
         */
        public class SubSource extends Source {

            public SubSource(int source) {
                super(source);
            }

            public int subduction(int b) {
                return getSource() - b;
            }
        }


        /**
         * 通过包装扩展函数
         */
        public class SourceWrapper {
            private Source source;

            public SourceWrapper(Source source) {
                this.source = source;
            }

            public int subduction(int b) {
                return source.getSource() - b;
            }
        }
    }


}
