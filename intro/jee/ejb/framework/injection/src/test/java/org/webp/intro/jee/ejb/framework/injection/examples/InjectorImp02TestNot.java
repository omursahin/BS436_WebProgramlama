package org.webp.intro.jee.ejb.framework.injection.examples;

import org.webp.intro.jee.ejb.framework.injection.InjectorBaseSuite;
import org.webp.intro.jee.ejb.framework.injection.Injector;


public class InjectorImp02TestNot extends InjectorBaseSuite {

    @Override
    protected Injector getInjector() {
        return new InjectorImp02();
    }
}