# IceKick
[Icepick][1]-esque Android Instance State for Kotlin, inspired by [Kotter Knife][2].

```kotlin
class MyActivity : Activity() {
  // A state that can be null
  private var nullableState: Int? by state()
  
  // A state that can't be null, needs a default value
  private var nonNullState: Int by state(0)
  
  // A shorter version of the above, with type inferred by the default value
  private var nonNullShorterState by state(0)
}
```

These methods are available on subclasses of `Activity`, `Fragment`,
the support library `Fragment`, and `View`.

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
