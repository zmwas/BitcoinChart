# BitCoinChart

An app that displays the price of bitcoin and allows you to filter by timespan and rolling average.

Language:

- Kotlin 

Libraries used: 

- RxJava2, RxAndroid for handling streams of data and multithreading

- Android architecture components (Viewmodel)

- MPAndroidChart to display the graph

- Mockito to mock dependencies

- Junit to run unit tests

- Dagger2 for dependency injection

Architecture:

Clean Architecture with a data, domain and presentation layer. Each layer has it's own gradle module.

On the presentation layer MVVM is used to separate concerns and enable testing of implementation details in the view model

![image](screenshots/Screenshot_1572764766.png)

![image](screenshots/Screenshot_1572764792.png)

![image](screenshots/Screenshot_1572764800.png)

![image](screenshots/Screenshot_1572764812.png)

