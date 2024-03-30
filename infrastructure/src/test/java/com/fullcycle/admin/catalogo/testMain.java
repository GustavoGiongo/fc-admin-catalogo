package com.fullcycle.admin.catalogo;

import com.fullcycle.admin.catalogo.infrastructure.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.AbstractEnvironment;

public class testMain {
    @Test
    public void testMain(){
        Assertions.assertNotNull(new Main());
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "test");
        Main.main(new String[]{});
    }


}
