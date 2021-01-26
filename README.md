在德兴铜矿有主要有几种倒班方式：

- 长白班
- 三班倒
- 两班倒
- 其它无规律倒班

### 长白班与其它无规律倒班

**长白班**实际就是以普通的工作周工作，周一至周五工作，周六、周日休息，通常无需特别计算；

**其它无规律倒班**通常只有白天会上班，所以有时也会称为长白班，但实际只是混淆了而已。这种倒班通常提前一个前月进行手工排班，排班具有不确定性；

因此，这两种倒班这里不做考虑。

### 三班倒（三班两运转）

|班次|前天+1|前天|昨天|今天|明天|后天|后天+1|后天+2|
|---|---|---|---|---|---|---|---|---|
|晚班(0-8)  |一班|一班|四班|四班|三班|三班|二班|二班|
|早班(8-16) |二班|二班|一班|一班|四班|四班|三班|三班|
|中班(16-24)|三班|三班|二班|二班|一班|一班|四班|四班|
|休息       |四班|四班|三班|三班|二班|二班|一班|一班|

从这里可以看出任何一个班（班级）上班的规律是：

- 第1个晚班
- 第2个晚班
- 第1个早班
- 第2个早班
- 第1个中班
- 第2个中班
- 第1个休息
- 第2个休息

这种倒班的班次要重复上一个班次(repetition)，才进入下一个班次；而其周期(cycle)是8天一循环。其次是，在德兴铜矿三班倒是统一的，也就是说无论哪个单位，如果上早班的是一班，那么其它单位上早班的也一定是一班。

### 两班倒

> 下图是正确的，但两班倒代码未修正
|班次|昨天|今天|明天|后天|
|---|---|---|---|---|
|夜班|一班|四班|三班|二班|
|休息|二班|一班|四班|三班|
|白班|三班|二班|一班|四班|
|休息|四班|三班|二班|一班|

这种倒班的班次无重复；而其周期是4天一循环。但是，在德兴铜矿两班倒是不统一的，也就是说不同的单位，如果上早班的是一班，那么其它单位上早班的不一定是一班。

### 倒班算法

```java
//这里只计算一班（第一个班级）的倒班情况
int getShift(Calendar cal) {
    // 注意下行代码未考虑时区问题
    long timeInMillis = cal.getTimeInMillis(); 
    int dayInMillis = 24 * 60 * 60 * 1000;
    int cycle = 8;

    int result = (timeInMillis / dayInMillis) % cycle;
    return result;
}
```

这个result的值：0、1、2、3、4、5、6、7，就可以代表第1个晚班、第2个晚班、第1个早班、第2个早班、第1个中班、第2个中班、第1个休息、第2个休息，同理两班倒也可以用此法计算，但是都会有一个问题:

#### 正常情况

|result|0|1|2|3|4|5|6|7|
|---|---|---|---|---|---|---|---|---|
|班次|晚班1|晚班2|早班1|早班2|中班1|中班2|休息1|休息2|

#### 错位情况

|result|0|1|2|3|4|5|6|7|
|---|---|---|---|---|---|---|---|---|
|班次|晚班2|早班1|早班2|中班1|中班2|休息1|休息2|晚班1|
|班次|早班1|早班2|中班1|中班2|休息1|休息2|晚班1|晚班2|
|班次|早班2|中班1|中班2|休息1|休息2|晚班1|晚班2|早班1|
|班次|...|...|...|...|...|...|...|...|

所以还需要修正（delta）:

```java
//这里只计算一班的倒班情况
int getShift(Calendar cal) {
    // 注意下行代码未考虑时区问题
    long timeInMillis = cal.getTimeInMillis(); 
    int dayInMillis = 24 * 60 * 60 * 1000;
    int cycle = 8;
    int delta;

    int result = ((timeInMillis / dayInMillis) + delta) % cycle;
    return result;
}
```

### ShiftsTable接口

这个接口用来表示一个倒班表和一些重要参数；

```java
public interface ShiftsTable {
    int getRepetition(); //要重复班次的次数
    int getDelta(); // 修正数
    int getCycle(); //循环周期

    int getAmountOfClasses(); //班级数量， 三班倒和两班倒都只有4个班
    ...
}
```

这里有两个实现：

- 三班倒倒班表
```java
public class SanbandaoTable implements ShiftsTable {
    
    private Shift[] shiftGroup = {
        new Shift(0, "晚班1", "晚", "0-8"),
        new Shift(1, "晚班2", "晚", "0-8"),
        new Shift(2, "早班1", "早", "8-16"),
        new Shift(3, "早班2", "早", "8-16"),
        new Shift(4, "中班1", "中", "16-24"),
        new Shift(5, "中班2", "中", "16-24"),
        new Shift(6, "休息1", "休", ""),
        new Shift(7, "休息2", "休", "")
    };

    private ClassName[] classNames = {
        new ClassName(0, "一班", "甲班"),
        new ClassName(1, "二班", "乙班"),
        new ClassName(2, "三班", "丙班"),
        new ClassName(3, "四班", "丁班")
    };

    @Override
    public int getRepetition() {
        return 1;
    }

    @Override
    public int getDelta() {
        return 0;
    }

    @Override
    public int getCycle() {
        return shiftGroup.length;
    }

    @Override
    public int getAmountOfClasses() {
        return classNames.length;
    }

    ...
}
```
- 两班倒倒班表

```java
public class LiangbandaoTable implements ShiftsTable {
    
    private Shift[] shiftGroup = {
        new Shift(0, "白班", "白", "8-20"),
        new Shift(1, "夜班", "夜", "20-8"),
        new Shift(2, "休息1", "休", ""),
        new Shift(3, "休息2", "休", "")
    };

    private ClassName[] classNames = {
        new ClassName(0, "一班", "甲班"),
        new ClassName(1, "二班", "乙班"),
        new ClassName(2, "三班", "丙班"),
        new ClassName(3, "四班", "丁班")
    };

    @Override
    public int getRepetition() {
        return 0;
    }

    @Override
    // 前面说过，两班倒在德兴铜矿不是统一的，所以不同单位的不同情况，需要在这里调节，进行重写
    public int getDelta() {
        return 2;
    }

    @Override
    public int getCycle() {
        return shiftGroup.length;
    }

    @Override
    public int getAmountOfClasses() {
        return classNames.length;
    }

    ...
}
```

### API

```java
Calendar date = Calendar.getInstance();

// 三班倒（sanbandao）
ShiftsTable sanbandaoTable = new SanbandaoTable();
ShiftsHelper sanbandaoHelper = new RegularShiftsHelper(sanbandaoTable);

// 0表示一班
ClassName className = sanbandaoTable.getClassName(0);
//根据时间（date）和班别（classNmae）推算班次（shift）
Shift shiftResult = sanbandaoHelper.getShift(date, className);

// 0表示晚班1
Shift shift = sanbandaoTable.getShift(0);
//根据时间（date）和班次（shift）推算班别（className）
ClassName classNameResult = sanbandaoHelper.getClassName(date, shift);


// 两班倒同理，不再赘述
ShiftsTable liangbandaoTable = new LiangbandaoTable();
ShiftsHelper liangbandaoHelper = new RegularShiftsHelper(liangbandaoTable);
```
