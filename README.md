# Architecture Components Playground

https://developer.android.com/topic/libraries/architecture/index.html

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

* LiveDataはLifecycleを使ってる
* When you update the value stored in the LiveData object, it triggers all registered observers as long as the attached LifecycleOwner is in the active state.
* 値が更新された際の通知はUIがActiveなときだけ？
* UIがActiveでない時に更新された場合、再度UIがActiveになった時に変更通知がくる？
* LiveDataのsetValueはMain Threadで呼び出さないとダメで、Worker Threadから値を更新したいならpostValue呼べと


### MutableLiveData

* LiveData which publicly exposes setValue(T) and postValue(T) method.
* ViewModelからViewに対してLiveDataを返すならMutableLiveDataではなくて、LiveData型として返したほうがいい
 * MutableLiveDataで返すとView側でsetValueとか呼び出せて、予期せぬところ(View側)で値が更新されるみたいなコードもかけてしまうから
 * 基本的に値の更新はViewModel内で行うようにする
* Stateは必ずLiveDataに保持するって意味ではいいのかなーMutableLiveData返しても
 * 入力して登録みたいなパターンだと入力内容は自然とLiveDataに反映されて、登録ボタンのActionでViewModelの何かを発火させて、ViewModelはLiveDataの値を取得する的な感じかなー


### Transformations

* LiveDataの値を変換するあれ
* 雑) Rxのoperator的なあれ


### MediatorLiveData

* 独自のTransformationを作るにはMediatorLiveDataを使う


## ViewModel

* ライフサイクルを意識した方法でUI関連のデータを格納および管理する
* 画面の回転などの構成の変更後にもデータを引き継ぐことができる
* If the activity is re-created, it receives the same MyViewModel instance that was created by the first activity. When the owner activity is finished, the framework calls the ViewModel objects's onCleared() method so that it can clean up resources.
* ViewModelはActivity、Fragmentよりも生存期間が長いので、ViewやActivityContextなどを参照しないようにする
* もしApplication contextが必要な場合はAndroidViewModelを継承して使う。AndroidViewModelはApplicationをコンストラクタの引数に持ちます
* ViewModelがあればonSaveInstanceStateしなくてもいってわけじゃないし、全てviewModelでなんとかなる話ではない
 * systemからのアプリKillとかされるとViewModelは消える。その場合はonSaveInstanceStateでどうにかせんといかん

### Create ViewModel

```java
ViewModelProviders.of(this).get(MyViewModel.class);
```


### Share data between fragments

* ActivityをScopeにアタッチしたFragment感で同一のViewModelを取得することができる = SharedViewModel的な


```java
public class MasterFragment extends Fragment {
    private SharedViewModel model;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        itemSelector.setOnClickListener(item -> {
            model.select(item);
        });
    }
}

public class DetailFragment extends LifecycleFragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedViewModel model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        model.getSelected().observe(this, { item ->
           // Update the UI.
        });
    }
}
```




## 雑メモ

* View → Presenter → ViewModel(State) → View みたいなやつやりたくて、そこでArchitecture ComponentsのViewModel使えないかなー
* LiveData/ViewModelはLifecycleの仕組みを活用していい感じにしてくれてる