package org.agh.pracinz.evog.view.common

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable


abstract class RxActivity : AppCompatActivity() {
    protected val disposables: CompositeDisposable = CompositeDisposable()
}