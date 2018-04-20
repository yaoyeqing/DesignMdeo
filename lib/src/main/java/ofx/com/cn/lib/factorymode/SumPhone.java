package ofx.com.cn.lib.factorymode;

/**
 * Created by OFX040 on 2018/3/29.
 */

public class SumPhone extends Phone {
    @Override
    void create() {
        System.out.println("Create an SUM Phone");
    }
}
