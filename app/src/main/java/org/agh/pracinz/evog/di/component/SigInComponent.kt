//package org.agh.pracinz.evog.di.component
//
//import dagger.Component
//import dagger.android.AndroidInjectionModule
//import org.agh.pracinz.evog.di.module.NetworkModule
//import org.agh.pracinz.evog.di.module.UserModule
//import org.agh.pracinz.evog.di.module.ViewModelModule
//import org.agh.pracinz.evog.view.login.signin.AccountFragment
//import org.agh.pracinz.evog.view.login.signin.ContactInfoFragment
//import org.agh.pracinz.evog.view.login.signin.PersonalInfoFragment
//import org.agh.pracinz.evog.view.login.signin.SignInActivity
//import javax.inject.Singleton
//
//
//@Singleton
//@Component(
//    modules = arrayOf(
//        NetworkModule::class,
//        AndroidInjectionModule::class, UserModule::class, ViewModelModule::class
//    )
//)
//interface SigInComponent {
//
//    fun inject(signInActivity: SignInActivity)
//}