package com.example.sphinx.mix.dagger.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Sphinx on 17/3/27.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
