package com.siba.searchmvvmpractice.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * SSong-develop
 *
 * Annotations are means of attaching metadata to code.
 * To declare an annotation, put the [annotation] modifier in front of a class
 *
 * Additional attributes of the annotation can be specified by annotating the annotation class with meta-annotations
 *
 * @Target : Specifies the possible kinds of elements which can be annotated with the annotation(such as classes, functions, properties , expressions)
 *
 * @Retention : specifies whether the annotation is stored in the compiled class files and whether it's visible through reflection at runtime
 *
 * @MustBeDocumented : specifies that the annotation is part of the public API and should be included in the class or method signature shown in the generated API documentation
 *
 * Dagger를 통해서 ViewModel을 inject 시키는 과정을 생각해보자
 *
 * ViewModel을 @Bind 를 통해서 연결해줘야할텐데
 */
@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)