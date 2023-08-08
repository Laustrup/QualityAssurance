# Quality Assurance for Java Maven classes

Class attributes are starting with _ as a syntax and enums with uppercase spelling.
Uses Java 18.

### These classes can be used to improve the testing experience of JUnit with:

* The Laustrup Printer utility displaying
  * The items of ARRANGE
  * Performance of ACT
* More clean AAA code, though the algorithms would be needed to understand.
* Attributes available:
  * _random, which is a simple Random for generating random values
  * _password, which is a password, that will be generated at each beforeEach
  * _addings and _adding, some objects that can be reused to insert into the acting method
  * _expected, the object that is to be expected
  * _exception, any kind of exception

### How to use

Firstly, the class used for the testing, needs to extend the Tester class

```class Tests extends Tester<T>```

The T is the return type.

Now Tester's methods as well as superclasses such as Arranger, Actor and Asserter are available for use.

There is a @beforeEach method that are regenerating values such as _password.
In order to have another @beforeEach annotated method, the TestEditor interface will be needed to be implemented in the constructor, here is an example:

```
public TestConstructor() {
    _editor = new TestEditor() {
        @Override
        public void beforeEach() {
            // Algorithm
            ...
        }
    };
}
```

Lambda functions are used to include Suppliers, Runnables and Functions in each step, the method itself needs to be as a lambda function.

```
void canTest() {
  test(() ->
    ...
  );
}
```

Each step itself is including a lambda function, which can be like

```
void canTest() {
  test(() -> {
    T[] arrangement = arrange(() -> {0,1});
    T input = arrangement[0], expected = arrangement[1];
    
    T actual = act(() -> increment(input));
    
    asserting(expected, actual);
    
    return TestMessage.SUCCESS.get_content();
  });
}
```

It is possible, but not needed, for the callable input in the test method to make a return as a TestMessage enum,
if it is not a SUCCESS enum, it will fail the test with the message given to the specific enum or a different return as a String,
otherwise if it will return a SUCCESS or nothing it will finish the test with a print of details.