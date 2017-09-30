package org.sl;

import org.junit.Test;
import org.sl.util.md5.MyMd5;

/**
 * Created by hasee on 2017/9/30.
 */
public class md5Test {
    @Test
    public void test(){
        System.out.println(MyMd5.toMd5String("123456"));
    }
}
