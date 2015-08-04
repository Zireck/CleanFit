package com.zireck.projectk.module;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation created to improve Context injection. This annotation is used with @Inject for
 * Context class to return the current Activity context.
 *
 * This annotation can be replaced with a @Named annotation, but configure before the
 * ActivityModule.
 *
 * @author Pedro Vicente G�mez S�nchez
 */
@Qualifier
@Retention(RUNTIME)
public @interface ActivityContext {
}