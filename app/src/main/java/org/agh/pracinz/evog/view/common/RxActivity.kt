package org.agh.pracinz.evog.view.common

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable


abstract class RxActivity : AppCompatActivity() {
    protected val disposables: CompositeDisposable = CompositeDisposable()
}

abstract class RxFragment : Fragment() {
    protected val disposables: CompositeDisposable = CompositeDisposable()
}