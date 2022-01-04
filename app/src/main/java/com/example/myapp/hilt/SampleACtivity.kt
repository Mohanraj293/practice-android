package com.example.myapp.hilt

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapp.R
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Qualifier


@AndroidEntryPoint
class SampleActivity: Fragment(R.layout.sample_activity) {

    //field injection
    @Inject
    lateinit var  a: A

    @Inject
    lateinit var b: B

    @Inject
    lateinit var someClass: SomeClass

    @Inject
    lateinit var someClass1: SomeClass1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println(a.foo())
        println(b.a1())
        println(b.a2())
        println(someClass.doAThing())
        println(someClass1.doAThing())

    }
}

//-------------------------------------------------------------------------------
class A @Inject constructor(){
    fun foo(): String {
        return "Hello Everybody"
    }
}
class G @Inject constructor(){
    fun foo(): String {
        return "Hello Everybody"
    }
}

class B @Inject constructor(private val a: A,private val g: G){// constructor injection
fun a1(): String {
    return "Hello Everyone"
}
    fun a2(): String {
        return a.foo()
    }
}

//-------------------------------------------------------------------------------
//scopes
@FragmentScoped
class C @Inject constructor(){
    fun test(): String {
        return "Hello Hi"
    }
}

@AndroidEntryPoint
class D:Fragment(){
    @Inject
    lateinit var c: C
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        c.test()
    }
}

//-------------------------------------------------------------------------------

class SomeClass
@Inject
constructor(
    private val someDependency: SomeDependency
){
    fun doAThing(): String{
        return "Look I got: ${someDependency.getAThing()}"
    }
}

class SomeDependency
@Inject
constructor(){
    fun getAThing() : String{
        return "A Thing"
    }
}
//--------
//interface
class X
@Inject constructor(
    private val interfaceImpl: IntfA
){  //third party lib
    fun hi(): String {
        return "Hello  -> ${interfaceImpl.oo()}"
    }
}
class InterfaceImpl @Inject constructor(someString: String) :IntfA{
    override fun oo(): String {
        return "Hello from interface"
    }

}
interface IntfA{
    fun oo():String
}
//--------------
@Module
@InstallIn(ActivityComponent::class) //complex way
abstract class MyModule{
    @ActivityScoped
    @Binds
    abstract fun bindImlp(interfaceImpl: InterfaceImpl):IntfA
}

@Module
@InstallIn(ActivityComponent::class)//easy way
class R{

    @ActivityScoped
    @Provides
    fun sample(): String{
        return "Hello form simple string"
    }

    @ActivityScoped
    @Provides
    fun o(someString: String):IntfA{
        return InterfaceImpl(someString)
    }

    @ActivityScoped
    @Provides
    fun ho(): Gson {
        return Gson()
    }
}
//----------------------------------------------------------------------------------

class SomeClass1
@Inject
constructor(
    @Impl1 private val someInterfaceImpl1: SomeInterface,
    @Impl2 private val someInterfaceImpl2: SomeInterface
){
    fun doAThing(): String{
        return "Look I got: ${someInterfaceImpl1.getAThing()} & ${someInterfaceImpl2.getAThing()}"
    }
}

class SomeInterfaceImpl1
@Inject
constructor(): SomeInterface {
    override fun getAThing() : String{
        return "A Thing1"
    }
}

class SomeInterfaceImpl2
@Inject
constructor(): SomeInterface {
    override fun getAThing() : String{
        return "A Thing2"
    }
}

interface SomeInterface{
    fun getAThing(): String
}

@InstallIn(FragmentComponent::class)
@Module
class MyModule1{

    @Impl1
    @FragmentScoped
    @Provides
    fun provideSomeInterface1(): SomeInterface{
        return SomeInterfaceImpl1()
    }

    @Impl2
    @FragmentScoped
    @Provides
    fun provideSomeInterface2(): SomeInterface{
        return SomeInterfaceImpl2()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2