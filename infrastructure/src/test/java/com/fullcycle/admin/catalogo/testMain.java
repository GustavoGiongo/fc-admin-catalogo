package com.fullcycle.admin.catalogo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class testMain {
    @Test
    public void testMain(){
        Assertions.assertNotNull(new Main());
        Main.main(new String[]{});
    }
}
