package function;

/**
 * 1.只读局部变量,局部变量作为函数的一个参数
 * 2.对局部变量再赋值
 * 2.1仅仅在提炼的方法内使用，声明移动到提炼的方法内部，无需返回函数值。（临时变量i）
 * 2.2在提炼的方法外部也要使用，作为返回值返回。（临时变量sum）
 * 3.inlie temp 内联临时变量
 * 4.replace temp with query 以查询替换临时变量
 * 5.split temporary variable 分解临时变量
 * 6.remove assignments to parameters 移除对参数的赋值
 * 7.replace method with method object (以函数对象取代函数)
 */
public class FuctionLocalVariable {
    void print() {
        int sum = 0;
        int i = 0;
        for (i = 0; i < 10; i++) {
            sum += i;
        }
        //printSumValue(sum);
        System.out.println("print the sum value");
        System.out.println("sum is:" + sum);
    }


    //1只读局部变量
    private void printSumValue(int sum) {
        System.out.println("print the sum value");
        System.out.println("sum is:" + sum);
    }

    //2对局部变量再赋值
    private int getSum() {
        int i = 0;
        int sum = 0;
        for (i = 0; i < 10; i++) {
            sum += i;
        }
        return sum;
    }

    //3 inline temp 内联临时变量
    void print1() {
        printSumValue(sum());
    }

    private int sum() {
        return getSum();
    }


    void print2() {
        //以查询替换临时变量sum
        printSumValue(getSum());
    }

    //4.1 replace temp with query
    // (一个无参无返回值的函数是最好用的)
    int _quantity = 100;
    int _itemPrice = 20;

    //before
    double getPrice() {
        double basePrice = _quantity * _itemPrice;
        if (basePrice > 100) {
            return basePrice * 0.98;
        } else {
            return basePrice * 0.99;
        }
    }

    //after
    double getPrice1() {
        if (basePrice() > 100) {
            return basePrice() * 0.98;
        } else {
            return basePrice() * 0.99;
        }
    }

    private double basePrice() {
        return _quantity * _itemPrice;
    }


    //4.2 replace temp with query 两个临时变量的替换
    double getPrice2() {
        double basePrice = _quantity * _itemPrice;
        double discountFactor;
        discountFactor = getDiscountFactor(basePrice);
        return basePrice * discountFactor;
    }

    private double getDiscountFactor(double basePrice) {
        double discountFactor;
        if (basePrice > 1000) {
            discountFactor = 0.95;
        } else {
            discountFactor = 0.98;
        }
        return discountFactor;
    }

    //查询替换临时变量
    double getPrice21() {
        double basePrice = basePrice();
        double discountFactor;
        if (basePrice > 1000) {
            discountFactor = 0.95;
        } else {
            discountFactor = 0.98;
        }
        return basePrice * discountFactor;
    }

    //内联临时变量
    double getPrice22() {
        double discountFactor;
        if (basePrice() > 1000) {
            discountFactor = 0.95;
        } else {
            discountFactor = 0.98;
        }
        return basePrice() * discountFactor;
    }

    //提取函数
    double getPrice23() {
        double discountFactor = getDiscountFactor();
        return basePrice() * discountFactor;
    }

    /**
     * 没有临时变量的代码是最容易提炼函数且不容易出错的代码（*****）
     *
     * @return
     */
    private double getDiscountFactor() {
        double discountFactor;
        if (basePrice() > 1000) {
            discountFactor = 0.95;
        } else {
            discountFactor = 0.98;
        }
        return discountFactor;
    }

    //最终的函数由两个子函数实现。
    double getPrice24() {
        return basePrice() * getDiscountFactor();
    }
    //5分解临时变量
    /**
     * 如果它们被赋值超过一次，就意味着它们在函数中承担了一个以上的责任。
     * 如果临时变量承担了多个责任，它就应该被分解为多个临时变量，每个变量只承担一个责任。
     * 同一个临时变量承担两件不同的事情，会令代码阅读者糊涂
     */
    int _chickMoney, _chipMoney, _cocoaMoney, _coffeeMoney;

    double GetTotalCost() {
        double result = 0;

        double money = _chickMoney + _chipMoney;//第一次赋值

        result += money;

        money = _cocoaMoney + _coffeeMoney;//第二次赋值

        result += money;

        return result;
    }

    //分解临时变量
    double GetTotalCost1() {
        double result = 0;

        double mealmoney = _chickMoney + _chipMoney;//第一次赋值

        result += mealmoney;

        double money = _cocoaMoney + _coffeeMoney;//第二次赋值

        result += money;

        return result;
    }

    //分解临时变量后便于提取方法
    double GetTotalCost2() {
        return getMealmoney() + getMoney();
    }

    private int getMoney() {
        return _cocoaMoney + _coffeeMoney;
    }

    private int getMealmoney() {
        return _chickMoney + _chipMoney;
    }

    //6.移除对参数的赋值
    //如果参数只表示“被传递进来的东西”，那么代码会很清晰
    //可以避免参数造成歧义。（值传递和引用传递）
    int GetDiscount(int inputVal, int quantity, int yearToDate) {
        if (inputVal > 50) {
            inputVal -= 2;
        }
        if (quantity > 100) {
            inputVal -= 1;
        }
        if (yearToDate > 1000) {
            inputVal -= 4;
        }
        return inputVal;
    }

    //为了避免"对参数赋值"，可以使用关键字final修饰。来强制遵循
    int GetDiscount1(final int inputVal, final int quantity, final int yearToDate) {
        int result = inputVal;

        if (inputVal > 50) {
            result -= 2;
        }
        if (quantity > 100) {
            result -= 1;
        }
        if (yearToDate > 1000) {
            result -= 4;
        }
        return result;
    }

    //7.以函数对象取代函数

    /**
     * 将这个函数放进一个单独对象中，如此一来局部变量就成了对象内的字段。然后你可以在同一个对象中将这个大型函数分解为多个小型函数
     */

    class Account {
        int gamma(int inputVal, int quantity, int yearToDate) {
            int importantValue1 = inputVal * quantity + Delta();
            int importantValue2 = inputVal * yearToDate + 100;
            if (yearToDate - importantValue1 > 100) {
                importantValue2 -= 20;
            }
            int importantValue3 = importantValue2 * 7;
            //return new Gamma(this,inputVal,quantity,yearToDate).Compute();
            //and so on...
            return importantValue3 - 2 * importantValue1;
        }

        public int Delta() {
            return 100;
        }
    }

    //把gamma的方法提取到一个类中
    class Gamma {

        private Account _account;

        private int _inputVal;

        private int _quantity;

        private int _yearToDate;

        private int _importantValue1;

        private int _importantValue2;

        private int _importantValue3;

        public Gamma(Account account, int inputVal, int quantity, int yearToDate) {
            _account = account;
            _inputVal = inputVal;
            _quantity = quantity;
            _yearToDate = yearToDate;
        }

        public int Compute() {
            _importantValue1 = _inputVal * _quantity + _account.Delta();
            _importantValue2 = _inputVal * _yearToDate + 100;
            if (_yearToDate - _importantValue1 > 100) {
                _importantValue2 -= 20;
            }
            _importantValue3 = _importantValue2 * 7;
            //and so on...
            return _importantValue3 - 2 * _importantValue1;
        }
    }

}
