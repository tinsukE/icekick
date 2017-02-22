# IceKick
[Icepick][1]-esque Android Instance State for Kotlin, inspired by [Kotter Knife][2].

```kotlin
class MyActivity : Activity() {
  // A state that can't be null, needs a default value
  private var nonNullState: Int by state(0)

  // A state that can be null
  private var nullableState: Int? by nullableState(Int::class.java)
  
  // A state that behaves as if the variable had `lateinit` modifier
  private var lateinitState: String by lateState(String::class.java)

  // Lazier developers can even shorten them
  private var nonNullState by state(0) // Type inferred by the default value
  private var nullableState by nullableState(Int::class.java) // Needs the type information
  private var lateinitState by lateState(String::class.java)  // Same
}
```

These methods work for `Serializable` and `Parcelable` objects and are available on subclasses of `Activity`, `Fragment`,
the support library `Fragment`, and `View`.

If you need to save/restore an object that can't be made `Serializable` or `Parcelable`, a custom `Bundler` can be specified:

```kotlin
class MyFragment : Fragment() {
  // Your custom object
  class MyData(var value: Int)

  // Your Bundler
  class MyDataBundler : Bundler {
    override fun save(bundle: Bundle, key: String, value: Any?) {
      val myData = value as? MyData ?: return
      bundle.putInt(key, myData.value)
    }

    override fun load(bundle: Bundle, key: String): Any? {
      return MyData(bundle.getInt(key))
    }
  }

  // Usage
  private var myData by state(MyData(100), MyDataBundler())
  private var nullableMyData by nullableState(MyDataBundler())
  private var lateinitMyData by lateState(MyDataBundler())
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
  compile 'com.github.tinsukE:icekick:0.1'
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

    Copyright 2014 Angelo Suzuki

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
