



## Lifecycle

https://developer.android.com/topic/libraries/architecture/lifecycle.html

ライフサイクルは、コンポーネント（アクティビティやフラグメントなど）のライフサイクル状態に関する情報を保持し、他のオブジェクトがこの状態を観察できるようにするクラスです。


Lifecycle のキモは、ライフサイクルメソッドのオーバーライドはもうやめましょう、ということ。 

* LifecycleOwner
 * ライフサイクルを持つことを示すinterface
* LifecycleRegistry
* LifecycleRegistryOwner
* LifecycleObserver
 * ライフサイクルを観測するためのinterface

将来的に support library fragments and activities にLifecycleOwnerが実装される予定

LifecycleActivityを使わないでLifecycleOwnerを使う

Presenterをライフサイクルと合わせて処理させたい場合には使えそう

LifecycleObserverを実装したクラスの再利用性が高い(実装したクラスがどういうことをするかにもよるけど)



### SupportActivity

* SupportActivityがLifecycleOwnerを実装してる
 * Support Library 26.1.0から
* FragmentActivityでなぜかgetLifecycleメソッドOverrideしてる

```java
    @CallSuper
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        super.onSaveInstanceState(outState);
    }
```



## LiveData


LiveDataはLifecycleを使ってる


## ViewModel

ViewModelはActivity、Fragmentよりも生存期間が長いので、ViewやActivityContextなどを参照しない様にしてください。
もしApplication contextが必要な場合はAndroidViewModelを継承してください。
AndroidViewModelはApplicationをコンストラクタの引数に持ちます。


## 雑メモ

View → Presenter → ViewModel(State) → View みたいなやつやりたくて、そこでArchitecture ComponentsのViewModel使えないかなー