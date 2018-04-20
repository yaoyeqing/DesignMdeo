package ofx.com.cn.lib.factorymode;

import ofx.com.cn.lib.utils.XMLUtil;

public class FactoryModeTest {
    public static void main(String[] arg){

        try {
            String clazzNam = XMLUtil.getClassName("./lib/src/main/java/ofx/com/cn/lib/factorymode/config");
            Class clazz = Class.forName(clazzNam);
            Factroy factroy = (Factroy) clazz.newInstance();
            Phone phone = factroy.creatProduct();
            phone.create();



        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
