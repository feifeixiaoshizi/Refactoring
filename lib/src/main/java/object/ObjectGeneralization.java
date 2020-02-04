package object;

import java.util.Date;
import java.util.Stack;
import java.util.Vector;

/**
 * 处理概括关系
 */
public class ObjectGeneralization {

    /**
     * 1.字段上移
     * 两个子类拥有相同的字段。将该字段移至基类。
     */
    public static class PullUpField {

    }

    /**
     * 2.函数上移
     * 有些函数，在各个子类中产生完全相同的结果。将该函数移至基类。
     *
     */


    /**
     * 3.构造函数本体上移
     */
    public static class PullUpConstructorBody {
        public static class Employee {
            protected String name;
            protected int age;

            /*public Employee(String name, int age) {
                this.name = name;
                this.age = age;
            }*/
        }

        public static class Manager extends Employee {

            private int grade;

            public Manager(String name, int age) {
                // super(name,age);
                this.name = name;
                this.age = age;
            }

            public Manager(String name, int age, int grade) {
                // super(name,age);
                this.name = name;
                this.age = age;
                this.grade = grade;
            }
        }
    }

    /**
     * 4.函数下移
     * 基类中的某个函数只与部分（并非全部）子类有关
     * 将这个函数移到相关的那些子类去。
     */
    public static class PushDownMethod {
        public static class Employee {
            //管理方法下移
            public void managerProject() {

            }
        }

        public static class Manager extends Employee {
           /* public void managerProject(){

            }*/
        }

        public static class Engineer extends Employee {

        }
    }

    /**
     * 5.字段下移
     * 基类中的某个字段只被部分（并非全部）子类用到。
     *
     * 将这个字段移到需要它的那些子类去。
     */
    public static class PushDownField{

    }

    /**
     * 6.提炼子类
     * 类中的某些特性只被某些（而非全部）实例用到。
     *
     * 新建一个子类，将上面所说的那一部分特性转移到子类中。
     *
     * 部分公用，部分非公用，公用的在基类，非公用的在子类。（***）
     *
     */
    public static class ExtractSubClass{


        public class Registration {
            //只有未注册的情况下使用
            public String NoReginstratonAction ;
            public String NoReginstratonNotes ;

            public int RegistrationTotal ;
            public String RegistrationDescription ;
            public Date RegistrationDate ;

            public String getNoReginstratonAction() {
                return NoReginstratonAction;
            }

            public void setNoReginstratonAction(String noReginstratonAction) {
                NoReginstratonAction = noReginstratonAction;
            }

            public String getNoReginstratonNotes() {
                return NoReginstratonNotes;
            }

            public void setNoReginstratonNotes(String noReginstratonNotes) {
                NoReginstratonNotes = noReginstratonNotes;
            }

            public int getRegistrationTotal() {
                return RegistrationTotal;
            }

            public void setRegistrationTotal(int registrationTotal) {
                RegistrationTotal = registrationTotal;
            }

            public String getRegistrationDescription() {
                return RegistrationDescription;
            }

            public void setRegistrationDescription(String registrationDescription) {
                RegistrationDescription = registrationDescription;
            }

            public Date getRegistrationDate() {
                return RegistrationDate;
            }

            public void setRegistrationDate(Date registrationDate) {
                RegistrationDate = registrationDate;
            }
        }



        public static class Registration1 {

            public int RegistrationTotal ;
            public String RegistrationDescription ;
            public Date RegistrationDate ;


            public int getRegistrationTotal() {
                return RegistrationTotal;
            }

            public void setRegistrationTotal(int registrationTotal) {
                RegistrationTotal = registrationTotal;
            }

            public String getRegistrationDescription() {
                return RegistrationDescription;
            }

            public void setRegistrationDescription(String registrationDescription) {
                RegistrationDescription = registrationDescription;
            }

            public Date getRegistrationDate() {
                return RegistrationDate;
            }

            public void setRegistrationDate(Date registrationDate) {
                RegistrationDate = registrationDate;
            }
        }


        public static class NoRegistraton extends Registration1 {
            //只有未注册的情况下使用
            public String NoReginstratonAction ;
            public String NoReginstratonNotes ;

            public String getNoReginstratonAction() {
                return NoReginstratonAction;
            }

            public void setNoReginstratonAction(String noReginstratonAction) {
                NoReginstratonAction = noReginstratonAction;
            }

            public String getNoReginstratonNotes() {
                return NoReginstratonNotes;
            }

            public void setNoReginstratonNotes(String noReginstratonNotes) {
                NoReginstratonNotes = noReginstratonNotes;
            }
        }

    }


    /**
     * 7.提炼超类
     * 两个类有相似特性。为这两个类建立一个基类，将相同特性移至基类。
     */
    public static class ExtractSuperClass{

    }


    /**
     * 8.提炼接口
     * 若干客户使用类接口中的同一子集，或者两个类的接口有部分相同。将相同的子集提炼到一个独立接口中。
     * 如果某个类在不同环境下扮演截然不同的角色，使用接口就是个好主意。你可以针对每个角色以Extract Interface提炼出相应接口。
     * 另一种可以用Extract Interface的情况是：你想要描述一个类的外部依赖接口（outbound interface，即这个类要求服务提供方提供的操作）。
     * 如果你打算将来加入其它种类的服务对象。只需要求它们实现这个接口即可。
     */
    public static class  ExtractInterface{

    }

    /*
    9.折叠继承体系
    基类和子类之间无太大区别。将它们合为一体。
     */
    public static class CollapseHierarchy{
        public class Website
        {
            public String Title ;
            public String Description ;
        }

        public class StudentWebsite extends Website
        {
            public Boolean IsActive ;
        }

        //折叠继承体系
        public class Website1
        {
            public String Title ;
            public String Description ;
            Boolean IsActive ;
        }

    }

    /**
     * 10.塑造模版函数（？？？）
     * 你有一些子类，其中相应的某些函数以相同的顺序执行类似的操作，但各个操作的细节不同。
     * 将这些操作分别放进独立的函数中，并保持它们都有相同的签名，于是原函数也就变得相同了，然后将原函数上移至基类。
     */
    public static class FormTemplateMethod{

    }

    /**
     *
     * 11.以委托取代继承
     * 好处：
     * 1.继承实现类型匹配，委托负责功能扩展。（装饰模式，可以动态灵活的扩展功能，经典例子，io流）
     * 2.避免继承自己不需要的字段和功能造成浪费。
     *
     * 某个子类只使用基类接口中的一部分，或是根本不需要继承而来的数据。
     * 在子类中新建一个字段用以保存基类；调整子类函数，令它改而委托基类；然后去掉两者之间的继承体系。
     */
    public static class ReplaceInheritanceWithDelegation{
       Stack stack = new Stack();
    }

    /**
     * 12.以继承取代委托
     * 你在两个类之间使用委托关系，并经常为整个接口编写许多极简单的委托函数。让委托类继承受托类。
     *
     */
    public static class ReplaceDelegationWithInheritance{

    }
}
