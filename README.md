# IceKick
[Icepick][1]-esque Android Instance State for Kotlin, inspired by [Kotter Knife][2].

```kotlin
class MyActivity : Activity() {
  // There out-of-the-box support for Serializable
  // A state that can't be null, needs a default value
  private var nonNullState: Int by serialState(0)

  // A state that can be null
  private var nullableState: Int? by serialState()

  // A state that behaves as if the variable had the 'lateinit' modifier
  private var lateinitState: String by serialLateState()

  // And for Parcelable too
  private var resourceUri: Uri by parcelState(MY_DEFAULT_URI)
  private var nullableIntentState: Intent? by parcelState()
  private val lateinitParcelState: Intent by parcelLateState()
}
```

These methods are available on subclasses of `Activity`, `Fragment`, the support library `Fragment`, and `View`.

If you need to save/restore an object that can't be made `Serializable` or `Parcelable`, a custom `Bundler` can be specified:

```kotlin
class MyFragment : Fragment() {
  // Your custom object
  class MyData(var value: Int)

  // Your Bundler
  class MyDataBundler : Bundler<MyData> {
    override fun save(bundle: Bundle, key: String, value: MyData?) {
      val myData = value as? MyData ?: return
      bundle.putInt(key, myData.value)
    }

    override fun load(bundle: Bundle, key: String): MyData? {
      return if (bundle.containsKey(key)) MyData(bundle.getInt(key)) ?: null
    }
  }

  // Usage
  private var myData: MyData by state(MyData(100), MyDataBundler())
  private var nullableMyData: MyData? by state(MyDataBundler())
  private var lateinitMyData: MyData by lateState(MyDataBundler())
}
```

Download
-------

Currently available via [JitPack][3].

To use it, add the jitpack maven repository to your build.gradle file:
```gradle
repositories {
  ...
  maven { url "https://jitpack.io" }
  ...
}
```
and add the dependency:
```gradle
dependencies {
  ...
  compile 'com.github.tinsukE:icekick:0.2'
  ...
}
```


Setup
-------

To enable it, add these to your classes:

```kotlin
class MyActivity : Activity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    unfreezeInstanceState(savedInstanceState)
  }
  
  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    freezeInstanceState(outState)
  }
}
```

```kotlin
class MyFragment : Fragment() {
  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    freezeInstanceState(outState)
  }
  
  override fun onViewStateRestored(savedInstanceState: Bundle?) {
    super.onViewStateRestored(savedInstanceState)
    unfreezeInstanceState(savedInstanceState)
  }
}
```

```kotlin
class MyView : View {
  override fun onSaveInstanceState(): Parcelable? {
    return freezeInstanceState(super.onSaveInstanceState())
  }
  
  override fun onRestoreInstanceState(state: Parcelable) {
    super.onRestoreInstanceState(unfreezeInstanceState(state))
  }
}
```

License
-------

    Copyright 2016 Angelo Suzuki

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: https://github.com/frankiesardo/icepick
[2]: https://github.com/JakeWharton/kotterknife
[3]: https://jitpack.io
