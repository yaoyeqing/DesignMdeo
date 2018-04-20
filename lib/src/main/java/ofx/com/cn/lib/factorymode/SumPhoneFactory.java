package ofx.com.cn.lib.factorymode;

/**
 * Created by OFX040 on 2018/3/29.
 */

public class SumPhoneFactory extends Factroy {
    @Override
    Phone creatProduct() {
        return new SumPhone();
    }
}
